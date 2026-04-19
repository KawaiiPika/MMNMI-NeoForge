package xyz.pixelatedw.mineminenomi.abilities.hiso;

import java.util.Collection;
import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundUpdateMobEffectPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.data.entity.hisolookout.HisoLookoutCapability;
import xyz.pixelatedw.mineminenomi.data.entity.hisolookout.IHisoLookoutData;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class LookoutAbility extends Ability {
   private static final Component MESSAGE_RECENTLY_SEEN;
   private static final String MESSAGE_RECENTLY_SEEN_TARGET;
   private static final int COOLDOWN = 200;
   private static final int RANGE = 10;
   public static final RegistryObject<AbilityCore<LookoutAbility>> INSTANCE;
   private final RangeComponent rangeComponent = new RangeComponent(this);

   public LookoutAbility(AbilityCore<LookoutAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.rangeComponent});
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      List<LivingEntity> targets = this.rangeComponent.getTargetsInArea(entity, 10.0F, ForewarnAbility.TARGETS_PREDICATE);

      for(LivingEntity target : targets) {
         if (target instanceof Animal animal) {
            IHisoLookoutData props = (IHisoLookoutData)HisoLookoutCapability.get(animal).orElse((Object)null);
            if (props != null) {
               Collection<IHisoLookoutData.HisoLookoutTarget> seenSet = props.getSeenTargets().values();
               if (seenSet.size() > 0) {
                  WyHelper.sendMessage(entity, MESSAGE_RECENTLY_SEEN, true);

                  for(IHisoLookoutData.HisoLookoutTarget seen : seenSet) {
                     long seenTime = (entity.m_9236_().m_46467_() - seen.time()) / 20L / 60L;
                     WyHelper.sendMessage(entity, Component.m_237110_(MESSAGE_RECENTLY_SEEN_TARGET, new Object[]{seen.username(), seenTime, WyHelper.getDirectionLocalizedName(seen.direction())}), true);
                  }
               }
            }
         }
      }

      if (targets.size() > 0) {
         LivingEntity target = (LivingEntity)targets.get(0);
         target.m_7292_(new MobEffectInstance((MobEffect)ModEffects.ANIMAL_LOOKOUT.get(), 9999, 0, true, false));
         if (entity instanceof ServerPlayer) {
            ServerPlayer player = (ServerPlayer)entity;
            player.f_8906_.m_9829_(new ClientboundUpdateMobEffectPacket(target.m_19879_(), target.m_21124_((MobEffect)ModEffects.ANIMAL_LOOKOUT.get())));
         }
      }

      this.cooldownComponent.startCooldown(entity, 200.0F);
   }

   static {
      MESSAGE_RECENTLY_SEEN = Component.m_237115_(ModRegistry.registerName(ModRegistry.I18nCategory.GUI, "recently_seen", "Recently Seen:"));
      MESSAGE_RECENTLY_SEEN_TARGET = ModRegistry.registerName(ModRegistry.I18nCategory.GUI, "recently_seen_target", "%s was around here %s minutes ago going towards %s");
      INSTANCE = ModRegistry.registerAbility("animal_lookout", "Animal Lookout", (id, name) -> {
         Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Allows the user to communicate with nearby animals and learn if other players passed near them.", (Object)null));
         return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, LookoutAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(200.0F), RangeComponent.getTooltip(10.0F, RangeComponent.RangeType.AOE)).build("mineminenomi");
      });
   }
}
