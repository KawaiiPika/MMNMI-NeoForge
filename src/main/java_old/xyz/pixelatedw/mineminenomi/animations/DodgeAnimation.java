package xyz.pixelatedw.mineminenomi.animations;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.api.math.EasingFunctionHelper;

public class DodgeAnimation extends Animation<LivingEntity, HumanoidModel<LivingEntity>> {
   private float dodgeDistance;
   private float dodgeRotation;

   public DodgeAnimation(AnimationId<DodgeAnimation> animId) {
      super(animId);
      this.setAnimationPostAngles(this::postAngles);
   }

   public void start(LivingEntity entity, int animDuration) {
      super.start(entity, animDuration);
      int direction = entity.m_217043_().m_188499_() ? 1 : -1;
      this.dodgeDistance = (0.3F + entity.m_217043_().m_188501_() * 0.5F) * (float)direction;
      this.dodgeRotation = (50.0F + entity.m_217043_().m_188501_() * 60.0F) * (float)direction;
   }

   public void postAngles(LivingEntity entity, HumanoidModel<LivingEntity> entityModel, PoseStack poseStack, MultiBufferSource buffer, int packedLight, float partialTicks, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      entity.f_20883_ = entity.f_20885_;
      float max = (float)this.getAnimationInitialDuration() * 0.5F;
      float completion = Math.min((float)this.getTime(), max) / max;
      if (this.getAnimationCompletion() >= 0.8F) {
         max = (float)this.getAnimationInitialDuration() * 0.3F;
         completion = (float)Math.max((long)this.getAnimationInitialDuration() - this.getTime(), 0L) / max;
      }

      float ease1 = EasingFunctionHelper.easeOutCubic(completion);
      float ease2 = EasingFunctionHelper.easeOutBack(completion);
      poseStack.m_252880_(this.dodgeDistance * ease2, 0.0F, 0.0F);
      poseStack.m_252781_(Axis.f_252436_.m_252977_(this.dodgeRotation * ease1));
   }
}
