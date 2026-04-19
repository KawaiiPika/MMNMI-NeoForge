package xyz.pixelatedw.mineminenomi.animations;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.api.math.EasingFunctionHelper;

public class CartwheelAnimation extends Animation<LivingEntity, HumanoidModel<LivingEntity>> {
   public CartwheelAnimation(AnimationId<CartwheelAnimation> animId) {
      super(animId);
      this.setAnimationPostAngles(this::postAngles);
   }

   public void postAngles(LivingEntity entity, HumanoidModel<LivingEntity> entityModel, PoseStack poseStack, MultiBufferSource buffer, int packedLight, float partialTicks, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      entity.f_20883_ = entity.f_20885_;
      float max = (float)this.getAnimationInitialDuration() * 0.8F;
      float completion = Math.min((float)this.getTime(), max) / max;
      float limbMax = (float)this.getAnimationInitialDuration() * 0.6F;
      float limbCompletion = Math.min((float)this.getTime(), limbMax) / limbMax;
      float axisMax = (float)this.getAnimationInitialDuration() * 0.7F;
      float axisCompletion = Math.min((float)this.getTime(), axisMax) / axisMax;
      if (this.getAnimationCompletion() >= 0.7F) {
         axisMax = (float)this.getAnimationInitialDuration() * 0.3F;
         axisCompletion = (float)Math.max((long)this.getAnimationInitialDuration() - this.getTime(), 0L) / axisMax;
      }

      if (this.getAnimationCompletion() >= 0.6F) {
         limbMax = (float)this.getAnimationInitialDuration() * 0.4F;
         limbCompletion = (float)Math.max((long)this.getAnimationInitialDuration() - this.getTime(), 0L) / limbMax;
      }

      float bodyEase = EasingFunctionHelper.easeInBack(completion);
      float axisEase = EasingFunctionHelper.easeInCubic(axisCompletion);
      float limbEase = EasingFunctionHelper.easeInSine(limbCompletion);
      poseStack.m_85837_((double)0.0F, 0.7 * (double)axisEase, (double)0.0F);
      poseStack.m_252781_(Axis.f_252529_.m_252977_(360.0F * bodyEase));
      poseStack.m_252781_(Axis.f_252436_.m_252977_(90.0F));
      entityModel.f_102808_.f_104203_ = 0.0F;
      entityModel.f_102813_.f_104203_ = 0.0F;
      entityModel.f_102813_.f_104204_ = 0.0F;
      entityModel.f_102813_.f_104205_ = (float)Math.toRadians((double)(45.0F * limbEase));
      entityModel.f_102813_.f_104200_ = -2.0F + -1.0F * limbEase;
      entityModel.f_102813_.f_104201_ = 12.0F - 2.0F * limbEase;
      entityModel.f_102814_.f_104203_ = 0.0F;
      entityModel.f_102814_.f_104204_ = 0.0F;
      entityModel.f_102814_.f_104205_ = (float)Math.toRadians((double)(-65.0F * limbEase));
      entityModel.f_102814_.f_104200_ = 2.0F + 1.5F * limbEase;
      entityModel.f_102814_.f_104201_ = 12.0F - 1.0F * limbEase;
      entityModel.f_102811_.f_104203_ = 0.0F;
      entityModel.f_102811_.f_104204_ = 0.0F;
      entityModel.f_102811_.f_104205_ = (float)Math.toRadians((double)(135.0F * limbEase));
      entityModel.f_102811_.f_104201_ = 2.0F + -3.0F * limbEase;
      entityModel.f_102812_.f_104203_ = 0.0F;
      entityModel.f_102812_.f_104204_ = 0.0F;
      entityModel.f_102812_.f_104205_ = (float)Math.toRadians((double)(-135.0F * limbEase));
      entityModel.f_102812_.f_104201_ = 2.0F + -3.0F * limbEase;
      if (entityModel instanceof PlayerModel playerModel) {
         playerModel.f_103377_.m_104315_(playerModel.f_102813_);
         playerModel.f_103376_.m_104315_(playerModel.f_102814_);
         playerModel.f_103375_.m_104315_(playerModel.f_102811_);
         playerModel.f_103374_.m_104315_(playerModel.f_102812_);
      }

   }
}
