package xyz.pixelatedw.mineminenomi.api.entities.revenge;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.damagesources.IDamageSourceHandler;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.init.ModDamageTypes;

public class PhysicalDamageDealtRevengeCheck extends BaseRevengeCheck implements IHitRevengeCheck {
   private float prevHealth;
   private float lostHealth;
   private float multiplier = 1.0F;

   public PhysicalDamageDealtRevengeCheck() {
   }

   public PhysicalDamageDealtRevengeCheck(float multipler) {
      this.multiplier = multipler;
   }

   public boolean check(LivingEntity entity, DamageSource source) {
      if (this.prevHealth == 0.0F) {
         this.prevHealth = entity.m_21223_();
      }

      boolean isPhysical = false;
      if (source != null) {
         isPhysical |= source.m_276093_(DamageTypes.f_268464_) || source.m_276093_(DamageTypes.f_268566_);
         if (source.m_276093_(ModDamageTypes.ABILITY)) {
            IDamageSourceHandler handler = IDamageSourceHandler.getHandler(source);
            isPhysical = handler.hasType(SourceType.PHYSICAL);
         }
      }

      if (isPhysical && entity.m_21223_() < this.prevHealth) {
         this.lostHealth = this.prevHealth - entity.m_21223_();
         this.prevHealth = entity.m_21223_();
         return true;
      } else {
         return false;
      }
   }

   public void resetMarkers() {
      super.resetMarkers();
      this.prevHealth = 0.0F;
      this.lostHealth = 0.0F;
   }

   public int revengeMeterGain() {
      return Math.round(this.lostHealth * this.multiplier);
   }

   public float getDamagePool() {
      return this.lostHealth;
   }
}
