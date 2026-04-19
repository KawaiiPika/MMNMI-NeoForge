package xyz.pixelatedw.mineminenomi.api.morph;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import javax.annotation.Nullable;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;

public abstract class HumanoidMorphModel<T extends LivingEntity> extends HumanoidModel<T> implements IMorphModel, IFirstPersonHandReplacer {
   public HumanoidMorphModel(ModelPart root) {
      super(root);
      this.f_102809_.f_233556_ = true;
   }

   @Nullable
   public ModelPart m_102851_(HumanoidArm side) {
      return side == HumanoidArm.RIGHT ? this.f_102811_ : this.f_102812_;
   }

   @Nullable
   public ModelPart getLeg(HumanoidArm side) {
      return side == HumanoidArm.RIGHT ? this.f_102813_ : this.f_102814_;
   }

   public HumanoidArm getAttackSide(T entity) {
      HumanoidArm humanoidarm = entity.m_5737_();
      return entity.f_20912_ == InteractionHand.MAIN_HAND ? humanoidarm : humanoidarm.m_20828_();
   }

   public void renderFirstPersonArm(PoseStack stack, VertexConsumer vertex, int packedLight, int overlay, float red, float green, float blue, float alpha, HumanoidArm side, boolean isLeg) {
      ModelPart arm = isLeg ? this.getLeg(side) : this.m_102851_(side);
      if (arm != null) {
         arm.f_104203_ = arm.f_104204_ = arm.f_104205_ = 0.0F;
         arm.m_104306_(stack, vertex, packedLight, overlay, red, green, blue, alpha);
      }

   }

   public void setupHeadRotation(T entity, float headPitch, float netHeadYaw) {
      boolean flag = entity.m_21256_() > 4;
      boolean flag1 = entity.m_6067_();
      this.f_102808_.f_104204_ = netHeadYaw * ((float)Math.PI / 180F);
      if (flag) {
         this.f_102808_.f_104203_ = (-(float)Math.PI / 4F);
      } else if (this.f_102818_ > 0.0F) {
         if (flag1) {
            this.f_102808_.f_104203_ = this.m_102835_(this.f_102808_.f_104203_, (-(float)Math.PI / 4F), this.f_102818_);
         } else {
            this.f_102808_.f_104203_ = this.m_102835_(this.f_102808_.f_104203_, headPitch * ((float)Math.PI / 180F), this.f_102818_);
         }
      } else {
         this.f_102808_.f_104203_ = headPitch * ((float)Math.PI / 180F);
         if ((double)this.f_102808_.f_104203_ > 0.4) {
            this.f_102808_.f_104203_ = 0.4F;
         } else if ((double)this.f_102808_.f_104203_ < -0.4) {
            this.f_102808_.f_104203_ = -0.4F;
         }
      }

   }

   public void m_7884_(T entity, float partialTicks) {
      this.setupAttackAnimation(entity, partialTicks, 0.0F);
   }

   public void setupAttackAnimation(T entity, float partialTicks, float armDistance) {
      this.f_102608_ = entity.f_20921_;
      boolean isBlackLeg = (Boolean)EntityStatsCapability.get(entity).map((props) -> props.isBlackLeg()).orElse(false);
      HumanoidArm armSide = this.getAttackSide(entity);
      if (!entity.m_21205_().m_41619_()) {
         ModelPart var10000 = this.m_102851_(entity.m_5737_());
         var10000.f_104203_ += -0.15F;
      }

      if (this.f_102608_ > 0.0F) {
         if (isBlackLeg) {
            ModelPart legModel = this.getLeg(armSide);
            this.f_102810_.f_104204_ = Mth.m_14031_(Mth.m_14116_(this.f_102608_) * ((float)Math.PI * 2F)) * 0.2F;
            ModelPart var21 = this.f_102813_;
            var21.f_104204_ += this.f_102810_.f_104204_;
            var21 = this.f_102814_;
            var21.f_104204_ += this.f_102810_.f_104204_;
            float f1 = 1.0F - this.f_102608_;
            f1 *= f1;
            f1 *= f1;
            f1 = 1.0F - f1;
            float f2 = Mth.m_14031_(f1 * (float)Math.PI);
            float f3 = Mth.m_14031_(this.f_102608_ * (float)Math.PI) * -(this.f_102808_.f_104203_ - 0.7F) * 0.75F;
            legModel.f_104203_ = (float)((double)this.f_102811_.f_104203_ - ((double)f2 * (double)1.5F + (double)f3));
            legModel.f_104204_ += this.f_102810_.f_104204_ * 2.0F;
         } else {
            ModelPart armModel = this.m_102851_(armSide);
            float f = this.f_102608_;
            this.f_102810_.f_104204_ = Mth.m_14031_(Mth.m_14116_(f) * ((float)Math.PI * 2F)) * 0.2F;
            if (armSide == HumanoidArm.LEFT) {
               ModelPart var23 = this.f_102810_;
               var23.f_104204_ *= -1.0F;
            }

            this.f_102811_.f_104202_ = Mth.m_14031_(this.f_102810_.f_104204_) * 5.0F;
            this.f_102811_.f_104200_ = -armDistance - Mth.m_14089_(this.f_102810_.f_104204_) * 5.0F;
            this.f_102812_.f_104202_ = -Mth.m_14031_(this.f_102810_.f_104204_) * 5.0F;
            this.f_102812_.f_104200_ = armDistance + Mth.m_14089_(this.f_102810_.f_104204_) * 5.0F;
            ModelPart var24 = this.f_102811_;
            var24.f_104204_ += this.f_102810_.f_104204_;
            var24 = this.f_102812_;
            var24.f_104204_ += this.f_102810_.f_104204_;
            var24 = this.f_102812_;
            var24.f_104203_ += this.f_102810_.f_104204_;
            f = 1.0F - this.f_102608_;
            f *= f;
            f *= f;
            f = 1.0F - f;
            float f1 = Mth.m_14031_(f * (float)Math.PI);
            float f2 = Mth.m_14031_(this.f_102608_ * (float)Math.PI) * -(this.f_102808_.f_104203_ - 0.7F) * 0.75F;
            armModel.f_104203_ -= f1 * 1.2F + f2;
            armModel.f_104204_ += this.f_102810_.f_104204_ * 2.0F;
            armModel.f_104205_ += Mth.m_14031_(this.f_102608_ * (float)Math.PI) * -0.4F;
         }
      }

   }
}
