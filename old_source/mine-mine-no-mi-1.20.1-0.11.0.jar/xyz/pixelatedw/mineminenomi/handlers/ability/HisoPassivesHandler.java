package xyz.pixelatedw.mineminenomi.handlers.ability;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.data.entity.hisolookout.HisoLookoutCapability;
import xyz.pixelatedw.mineminenomi.data.entity.hisolookout.IHisoLookoutData;
import xyz.pixelatedw.mineminenomi.entities.mobs.NotoriousEntity;
import xyz.pixelatedw.mineminenomi.init.ModEffects;

public class HisoPassivesHandler {
   public static void lookoutMarking(Animal animal) {
      if (animal.m_21023_((MobEffect)ModEffects.ANIMAL_LOOKOUT.get())) {
         IHisoLookoutData props = (IHisoLookoutData)HisoLookoutCapability.get(animal).orElse((Object)null);
         if (props != null) {
            List<LivingEntity> targets = WyHelper.<LivingEntity>getNearbyLiving(animal.m_20182_(), animal.m_9236_(), (double)10.0F, (targetx) -> targetx instanceof Player || targetx instanceof NotoriousEntity);
            targets.remove(animal);

            for(LivingEntity target : targets) {
               props.registerSeenTarget(target);
            }

         }
      }
   }

   public static void lookoutCleanup(Animal animal) {
      IHisoLookoutData props = (IHisoLookoutData)HisoLookoutCapability.get(animal).orElse((Object)null);
      if (props != null) {
         for(Map.Entry<UUID, IHisoLookoutData.HisoLookoutTarget> entry : props.getSeenTargets().entrySet()) {
            if (((IHisoLookoutData.HisoLookoutTarget)entry.getValue()).passedMinutes(animal.m_9236_().m_46467_()) > 20L) {
               props.removeSeenTarget((UUID)entry.getKey());
            }
         }

      }
   }
}
