package xyz.pixelatedw.mineminenomi.entities.clouds;

import java.util.function.Predicate;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.abilities.MH5Ability;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.entities.CloudEntity;
import xyz.pixelatedw.mineminenomi.init.ModArmors;
import xyz.pixelatedw.mineminenomi.init.ModDamageSources;
import xyz.pixelatedw.mineminenomi.init.ModEntities;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class MH5CloudEntity extends CloudEntity {
   public MH5CloudEntity(EntityType<? extends MH5CloudEntity> type, Level world) {
      super(type, world);
   }

   public MH5CloudEntity(Level world, LivingEntity owner) {
      super((EntityType)ModEntities.MH5_CLOUD.get(), world, owner);
   }

   public void m_142687_(Entity.RemovalReason reason) {
      for(LivingEntity target : WyHelper.getNearbyLiving(this.m_20182_(), this.m_9236_(), (double)100.0F, (Predicate)null)) {
         if (!target.m_5842_()) {
            boolean hasGasMask = ItemsHelper.hasItemInSlot(target, EquipmentSlot.HEAD, (Item)ModArmors.MH5_GAS_MASK.get());
            if (!hasGasMask) {
               target.m_6469_(ModDamageSources.getInstance().ability(this, (AbilityCore)MH5Ability.INSTANCE.get()), Float.MAX_VALUE);
            }
         }
      }

      super.m_142687_(reason);
   }

   public void m_8119_() {
      super.m_8119_();
      if (!this.m_9236_().f_46443_ && this.f_19797_ % 2 == 0) {
         WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.MH5_GAS.get(), this, this.m_20185_(), this.m_20186_(), this.m_20189_());
      }

   }
}
