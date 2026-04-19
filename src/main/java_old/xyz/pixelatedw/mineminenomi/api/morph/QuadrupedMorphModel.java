package xyz.pixelatedw.mineminenomi.api.morph;

import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.QuadrupedModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;

public abstract class QuadrupedMorphModel<T extends LivingEntity> extends QuadrupedModel<T> implements IMorphModel, ArmedModel, HeadedModel {
   public float swimAmount;

   public QuadrupedMorphModel(ModelPart root) {
      super(root, false, 0.0F, 0.0F, 0.0F, 0.0F, 0);
   }

   public void prepareMobModel(T entity, float limbSwing, float limbSwingAmount, float partialTicks) {
      this.swimAmount = entity.m_20998_(partialTicks);
   }

   public void setupHeadRotation(T entity, float headPitch, float netHeadYaw) {
      boolean flag = entity.m_21256_() > 4;
      boolean flag1 = entity.m_6067_();
      this.f_103492_.f_104204_ = netHeadYaw * ((float)Math.PI / 180F);
      if (flag) {
         this.f_103492_.f_104203_ = (-(float)Math.PI / 4F);
      } else if (this.swimAmount > 0.0F) {
         if (flag1) {
            this.f_103492_.f_104203_ = this.rotlerpRad(this.f_103492_.f_104203_, (-(float)Math.PI / 4F), this.swimAmount);
         } else {
            this.f_103492_.f_104203_ = this.rotlerpRad(this.f_103492_.f_104203_, headPitch * ((float)Math.PI / 180F), this.swimAmount);
         }
      } else {
         this.f_103492_.f_104203_ = headPitch * ((float)Math.PI / 180F);
         if ((double)this.f_103492_.f_104203_ > 0.6) {
            this.f_103492_.f_104203_ = 0.6F;
         } else if ((double)this.f_103492_.f_104203_ < -0.6) {
            this.f_103492_.f_104203_ = -0.6F;
         }
      }

   }

   public void setupAttackAnimation(T entity, float partialTicks) {
      if (this.f_102608_ > 0.0F) {
         ModelPart var10000 = this.f_103492_;
         var10000.f_104204_ += this.f_103493_.f_104204_;
         float f1 = 1.0F - this.f_102608_;
         f1 *= f1;
         f1 *= f1;
         f1 = 1.0F - f1;
         float f2 = Mth.m_14031_(f1 * (float)Math.PI);
         float f3 = Mth.m_14031_(this.f_102608_ * (float)Math.PI) * -(this.f_103492_.f_104203_ - 0.1F) * 0.15F;
         this.m_5585_().f_104203_ = (float)((double)this.f_103492_.f_104203_ + (double)f2 * 0.8 + (double)f3);
         var10000 = this.m_5585_();
         var10000.f_104205_ -= Mth.m_14031_(this.f_102608_ * (float)Math.PI) * -0.8F;
      }

   }

   public float rotlerpRad(float p_102836_, float p_102837_, float p_102838_) {
      float f = (p_102838_ - p_102837_) % ((float)Math.PI * 2F);
      if (f < -(float)Math.PI) {
         f += ((float)Math.PI * 2F);
      }

      if (f >= (float)Math.PI) {
         f -= ((float)Math.PI * 2F);
      }

      return p_102837_ + p_102836_ * f;
   }
}
