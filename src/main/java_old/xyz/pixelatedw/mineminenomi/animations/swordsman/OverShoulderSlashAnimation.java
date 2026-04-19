package xyz.pixelatedw.mineminenomi.animations.swordsman;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.api.math.EasingFunctionHelper;

public class OverShoulderSlashAnimation extends Animation<LivingEntity, HumanoidModel<LivingEntity>> {
   private static final float DEFAULT_TIME = 20.0F;
   private static final float FIRST_PHASE_PER = 0.3F;
   private static final float THIRD_PHASE_PER = 0.6F;
   private static final float SECOND_PHASE_PER = 0.3F;

   public OverShoulderSlashAnimation(AnimationId<OverShoulderSlashAnimation> animId) {
      super(animId);
      this.setAnimationAngles(this::angles);
      this.setAnimationHeldItem(this::heldItem);
   }

   public void angles(LivingEntity player, HumanoidModel<LivingEntity> model, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      float duration = Math.min((float)this.getAnimationInitialDuration(), 20.0F);
      if (duration < 0.0F) {
         duration = 20.0F;
      }

      float completion = Math.min((float)this.getTime() / duration, 1.0F);
      if (!(completion >= 1.0F)) {
         float mainPhaseDuration = duration * 0.3F;
         float mainPhaseCompletion = Math.min((float)this.getTime(), mainPhaseDuration) / mainPhaseDuration;
         float transitionEase = 1.0F;
         float ease = EasingFunctionHelper.easeOutCubic(mainPhaseCompletion);
         float rightArmYRot = 0.0F;
         float rightArmZRot = (float)Math.toRadians((double)10.0F);
         float leftArmZRot = (float)Math.toRadians((double)-10.0F);
         if (completion > 0.6F) {
            mainPhaseDuration = duration * 0.6F;
            mainPhaseCompletion = Math.max(duration - (float)this.getTime(), 0.0F) / mainPhaseDuration;
            rightArmYRot = (float)Math.toRadians((double)-30.0F);
            rightArmZRot = (float)Math.toRadians((double)50.0F);
            leftArmZRot = (float)Math.toRadians((double)20.0F);
            ease = EasingFunctionHelper.easeOutBack(mainPhaseCompletion);
         } else if (completion > 0.3F) {
            mainPhaseDuration = completion * 0.3F;
            mainPhaseCompletion = 1.0F;
            rightArmYRot = (float)Math.toRadians((double)-30.0F);
            rightArmZRot = (float)Math.toRadians((double)50.0F);
            leftArmZRot = (float)Math.toRadians((double)20.0F);
            float transitionTime = Math.min((completion - 0.3F) * 5.0F, 1.0F);
            transitionEase = EasingFunctionHelper.easeOutSine(transitionTime);
            ease = 1.0F;
         }

         model.f_102811_.f_104200_ = -5.0F + 1.0F * ease;
         model.f_102811_.f_104201_ = 2.0F + ease;
         model.f_102811_.f_104202_ = -5.0F * ease;
         model.f_102811_.f_104204_ = rightArmYRot * ease * transitionEase;
         model.f_102811_.f_104203_ = (float)Math.toRadians((double)-170.0F) * ease;
         model.f_102811_.f_104205_ = rightArmZRot * ease * transitionEase;
         model.f_102812_.f_104200_ = 5.0F + -1.0F * ease;
         model.f_102812_.f_104201_ = 2.0F + ease;
         model.f_102812_.f_104202_ = -5.0F * ease;
         model.f_102812_.f_104203_ = (float)Math.toRadians((double)-170.0F) * ease;
         model.f_102812_.f_104205_ = leftArmZRot * ease * transitionEase;
         model.f_102808_.f_104201_ = 3.0F * ease;
         model.f_102808_.f_104202_ = -1.0F * ease;
         model.f_102810_.f_104202_ = -2.0F * ease;
         model.f_102810_.f_104201_ = 3.0F * ease;
         model.f_102810_.f_104203_ = (float)Math.toRadians((double)5.0F) * ease;
         model.f_102813_.f_104202_ = -2.0F * ease;
         model.f_102813_.f_104203_ = (float)Math.toRadians((double)-5.0F) * ease;
         model.f_102814_.f_104202_ = -0.5F * ease;
         model.f_102814_.f_104203_ = (float)Math.toRadians((double)15.0F) * ease;
         model.f_102814_.f_104204_ = (float)Math.toRadians((double)-10.0F) * ease;
         model.f_102814_.f_104205_ = (float)Math.toRadians((double)-5.0F) * ease;
      }
   }

   public void heldItem(LivingEntity entity, ItemStack stack, ItemDisplayContext transformType, HumanoidArm handSide, PoseStack matrixStack, MultiBufferSource renderBuffer, int packedLight) {
      float duration = Math.min((float)this.getAnimationInitialDuration(), 20.0F);
      if (duration < 0.0F) {
         duration = 20.0F;
      }

      float completion = Math.min((float)this.getTime() / duration, 1.0F);
      float rotX = 25.0F;
      float ease = 1.0F;
      if (!(completion >= 1.0F)) {
         if (completion > 0.7F) {
            rotX = -25.0F;
            ease = EasingFunctionHelper.easeOutCubic(completion - 0.7F);
         }

         matrixStack.m_85837_((double)0.0F, -0.1, -0.05);
         matrixStack.m_252781_(Axis.f_252529_.m_252977_(rotX * ease));
         matrixStack.m_252781_(Axis.f_252403_.m_252977_(15.0F));
      }
   }
}
