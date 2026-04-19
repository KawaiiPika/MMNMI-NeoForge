package xyz.pixelatedw.mineminenomi.entities.ai.controllers;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.control.MoveControl.Operation;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;

public class FishSwimMoveController<T extends Mob> extends MoveControl {
   private final T entity;

   public FishSwimMoveController(T entity) {
      super(entity);
      this.entity = entity;
   }

   public void m_8126_() {
      if (this.entity.m_20069_()) {
         AbilityHelper.setDeltaMovement(this.entity, this.entity.m_20184_().m_82520_((double)0.0F, 0.005, (double)0.0F));
      }

      if (this.f_24981_ == Operation.MOVE_TO && !this.entity.m_21573_().m_26571_()) {
         double d0 = this.f_24975_ - this.entity.m_20185_();
         double d1 = this.f_24976_ - this.entity.m_20186_();
         double d2 = this.f_24977_ - this.entity.m_20189_();
         double d3 = d0 * d0 + d1 * d1 + d2 * d2;
         if (d3 < (double)2.5000003E-7F) {
            this.f_24974_.m_21564_(0.0F);
         } else {
            float f = (float)(Mth.m_14136_(d2, d0) * (double)(180F / (float)Math.PI)) - 90.0F;
            this.entity.m_146922_(this.m_24991_(this.entity.m_146908_(), f, 10.0F));
            this.entity.f_20883_ = this.entity.m_146908_();
            this.entity.f_20885_ = this.entity.m_146908_();
            float f1 = (float)(this.f_24978_ * this.entity.m_21133_(Attributes.f_22279_));
            if (this.entity.m_20069_()) {
               this.entity.m_7910_(f1 * 0.02F);
               float f2 = -((float)(Mth.m_14136_(d1, Math.sqrt(d0 * d0 + d2 * d2)) * (double)(180F / (float)Math.PI)));
               f2 = Mth.m_14036_(Mth.m_14177_(f2), -85.0F, 85.0F);
               this.entity.m_146926_(this.m_24991_(this.entity.m_146909_(), f2, 5.0F));
               float f3 = Mth.m_14089_(this.entity.m_146909_() * ((float)Math.PI / 180F));
               float f4 = Mth.m_14031_(this.entity.m_146909_() * ((float)Math.PI / 180F));
               this.entity.f_20902_ = f3 * f1;
               this.entity.f_20901_ = -f4 * f1;
            } else {
               this.entity.m_7910_(f1 * 0.1F);
            }
         }
      } else {
         this.entity.m_7910_(0.0F);
         this.entity.m_21570_(0.0F);
         this.entity.m_21567_(0.0F);
         this.entity.m_21564_(0.0F);
      }

   }
}
