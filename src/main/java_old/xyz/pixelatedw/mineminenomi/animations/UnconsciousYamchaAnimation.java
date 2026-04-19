package xyz.pixelatedw.mineminenomi.animations;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;

public class UnconsciousYamchaAnimation extends Animation<LivingEntity, EntityModel<LivingEntity>> {
   public UnconsciousYamchaAnimation(AnimationId<UnconsciousYamchaAnimation> animId) {
      super(animId);
      this.setAnimationPostAngles(UnconsciousYamchaAnimation::postAngles);
      this.setAnimationAngles(UnconsciousYamchaAnimation::angles);
   }

   public static void angles(LivingEntity entity, EntityModel<LivingEntity> entityModel, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      if (entity.m_20159_()) {
         if (entityModel instanceof HumanoidModel) {
            HumanoidModel bipedModel = (HumanoidModel)entityModel;
            bipedModel.f_102808_.f_104201_ = 1.5F;
            bipedModel.f_102808_.f_104203_ = (float)Math.toRadians((double)50.0F);
            ModelPart var10000 = bipedModel.f_102811_;
            var10000.f_104203_ += (-(float)Math.PI / 5F);
            var10000 = bipedModel.f_102812_;
            var10000.f_104203_ += (-(float)Math.PI / 5F);
            bipedModel.f_102813_.f_104203_ = -1.4137167F;
            bipedModel.f_102813_.f_104204_ = ((float)Math.PI / 10F);
            bipedModel.f_102813_.f_104205_ = 0.07853982F;
            bipedModel.f_102814_.f_104203_ = -1.4137167F;
            bipedModel.f_102814_.f_104204_ = (-(float)Math.PI / 10F);
            bipedModel.f_102814_.f_104205_ = -0.07853982F;
         }

      } else {
         if (entityModel instanceof HumanoidModel) {
            HumanoidModel bipedModel = (HumanoidModel)entityModel;
            bipedModel.f_102814_.f_104200_ = 1.0F;
            bipedModel.f_102814_.f_104203_ = (float)Math.toRadians((double)5.0F);
            bipedModel.f_102814_.f_104204_ = (float)Math.toRadians((double)90.0F);
            bipedModel.f_102813_.f_104200_ = -4.0F;
            bipedModel.f_102813_.f_104201_ = 10.0F;
            bipedModel.f_102813_.f_104203_ = (float)Math.toRadians((double)10.0F);
            bipedModel.f_102813_.f_104204_ = (float)Math.toRadians((double)90.0F);
            bipedModel.f_102812_.f_104200_ = 1.5F;
            bipedModel.f_102812_.f_104202_ = -1.2F;
            bipedModel.f_102812_.f_104201_ = 5.0F;
            bipedModel.f_102812_.f_104203_ = (float)Math.toRadians((double)-75.0F);
            bipedModel.f_102812_.f_104204_ = (float)Math.toRadians((double)90.0F);
            bipedModel.f_102811_.f_104202_ = -1.0F;
            bipedModel.f_102811_.f_104203_ = (float)Math.toRadians((double)-160.0F);
            bipedModel.f_102810_.f_104204_ = (float)Math.toRadians((double)30.0F);
            bipedModel.f_102810_.f_104205_ = (float)Math.toRadians((double)5.0F);
            bipedModel.f_102808_.f_104203_ = 0.0F;
            bipedModel.f_102808_.f_104204_ = (float)Math.toRadians((double)40.0F);
            bipedModel.f_102808_.f_104205_ = 0.0F;
         }

      }
   }

   public static void postAngles(LivingEntity entity, EntityModel<LivingEntity> entityModel, PoseStack matrixStack, MultiBufferSource buffer, int packedLight, float partialTicks, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      if (entity.m_20159_()) {
         Entity vehicle = entity.m_20202_();
         entity.m_146922_(vehicle.m_146908_());
         entity.m_146926_(vehicle.m_146909_());
         matrixStack.m_252781_(Axis.f_252436_.m_252977_(entity.m_146908_() + 90.0F));
      } else {
         matrixStack.m_252781_(Axis.f_252529_.m_252977_(90.0F));
         matrixStack.m_85837_((double)0.0F, (double)-0.5F, -1.35);
      }
   }
}
