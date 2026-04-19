package xyz.pixelatedw.mineminenomi.entities.ai.controllers;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import xyz.pixelatedw.mineminenomi.api.WyHelper;

public class LandFishLookController<T extends Mob> extends SmoothSwimmingLookControl {
   public LandFishLookController(T entity, int maxYRotFromCenter) {
      super(entity, maxYRotFromCenter);
   }

   public void m_8128_() {
      if (WyHelper.isAprilFirst()) {
         if (this.m_8106_()) {
            this.f_24937_.m_146926_(0.0F);
         }

         if (this.f_186068_ > 0) {
            --this.f_186068_;
            if (this.m_180896_().isPresent()) {
               this.f_24937_.f_20885_ = this.m_24956_(this.f_24937_.f_20885_, (Float)this.m_180896_().get(), this.f_24938_);
            }

            if (this.m_180897_().isPresent()) {
               this.f_24937_.m_146926_(this.m_24956_(this.f_24937_.m_146909_(), (Float)this.m_180897_().get(), this.f_24939_));
            }
         } else {
            this.f_24937_.f_20885_ = this.m_24956_(this.f_24937_.f_20885_, this.f_24937_.f_20883_, 10.0F);
         }

         if (!this.f_24937_.m_21573_().m_26571_()) {
            this.f_24937_.f_20885_ = Mth.m_14094_(this.f_24937_.f_20885_, this.f_24937_.f_20883_, (float)this.f_24937_.m_8085_());
         }

      } else {
         super.m_8128_();
      }
   }
}
