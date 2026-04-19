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

public class GeppoAnimation extends Animation<LivingEntity, HumanoidModel<LivingEntity>> {
   public GeppoAnimation(AnimationId<GeppoAnimation> animId) {
      super(animId);
      this.setAnimationPostAngles(this::postAngles);
   }

   public void postAngles(LivingEntity entity, HumanoidModel<LivingEntity> entityModel, PoseStack poseStack, MultiBufferSource buffer, int packedLight, float partialTicks, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      float max1 = (float)this.getAnimationInitialDuration() * 0.7F;
      float completion1 = Math.min((float)this.getTime(), max1) / max1;
      float max2 = (float)this.getAnimationInitialDuration() * 0.4F;
      float completion2 = Math.min((float)this.getTime(), max2) / max2;
      float ease1 = EasingFunctionHelper.easeOutCubic(completion1);
      float ease2 = EasingFunctionHelper.easeOutCubic(completion2);
      entityModel.f_102808_.f_104203_ = -0.4F;
      entityModel.f_102813_.f_104203_ = 0.0F;
      entityModel.f_102813_.f_104202_ = -4.0F * ease1;
      entityModel.f_102813_.f_104201_ = 2.0F + 7.0F * ease1;
      entityModel.f_102814_.f_104203_ = 0.0F;
      entityModel.f_102814_.f_104201_ = 9.0F + 2.0F * ease1;
      entityModel.f_102811_.f_104203_ = 0.5F;
      entityModel.f_102811_.f_104205_ = 0.2F;
      entityModel.f_102812_.f_104203_ = 0.5F;
      entityModel.f_102812_.f_104205_ = -0.2F;
      if (entityModel instanceof PlayerModel playerModel) {
         playerModel.f_103375_.m_104315_(playerModel.f_102811_);
         playerModel.f_103374_.m_104315_(playerModel.f_102812_);
      }

      poseStack.m_252781_(Axis.f_252529_.m_252977_(50.0F * ease2));
   }
}
