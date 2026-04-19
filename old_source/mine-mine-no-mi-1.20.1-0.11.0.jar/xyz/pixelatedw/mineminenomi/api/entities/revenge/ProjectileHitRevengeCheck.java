package xyz.pixelatedw.mineminenomi.api.entities.revenge;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;

public class ProjectileHitRevengeCheck extends BaseRevengeCheck implements IHitRevengeCheck {
   private final int revengeGain;
   private float prevHealth;
   private int hitCount;

   public ProjectileHitRevengeCheck(int revengeGain) {
      this.revengeGain = revengeGain;
   }

   public boolean check(LivingEntity entity, DamageSource source) {
      if (this.prevHealth == 0.0F) {
         this.prevHealth = entity.m_21223_();
      }

      boolean isProjectile = false;
      if (source != null) {
         isProjectile |= source.m_276093_(DamageTypes.f_268534_);
         isProjectile |= source.m_7640_() instanceof NuProjectileEntity;
      }

      if (isProjectile && entity.m_21223_() < this.prevHealth) {
         ++this.hitCount;
         this.prevHealth = entity.m_21223_();
         return true;
      } else {
         return false;
      }
   }

   public void resetMarkers() {
      super.resetMarkers();
      this.prevHealth = 0.0F;
      this.hitCount = 0;
   }

   public int revengeMeterGain() {
      return this.revengeGain;
   }

   public int getHits() {
      return this.hitCount;
   }
}
