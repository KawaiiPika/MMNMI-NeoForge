package xyz.pixelatedw.mineminenomi.api.entities.revenge;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.damagesources.IDamageSourceHandler;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.init.ModDamageTypes;

public class PhysicalHitRevengeCheck extends BaseRevengeCheck implements IHitRevengeCheck {
   private final int revengeGain;
   private float prevHealth;
   private int hitCount;

   public PhysicalHitRevengeCheck(int revengeGain) {
      this.revengeGain = revengeGain;
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

   public int getHits() {
      return this.hitCount;
   }

   public int revengeMeterGain() {
      return this.revengeGain;
   }
}
