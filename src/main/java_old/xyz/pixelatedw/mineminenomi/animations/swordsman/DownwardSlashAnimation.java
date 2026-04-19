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

public class DownwardSlashAnimation extends Animation<LivingEntity, HumanoidModel<LivingEntity>> {
   private static final float DEFAULT_TIME = 20.0F;
   private static final float PHASE_PER = 0.6F;

   public DownwardSlashAnimation(AnimationId<DownwardSlashAnimation> animId) {
      super(animId);
      this.setAnimationAngles(this::angles);
      this.setAnimationHeldItem(this::heldItem);
   }

   public void angles(LivingEntity player, HumanoidModel<LivingEntity> model, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      float duration = Math.min((float)(this.getAnimationInitialDuration() + 4), 20.0F);
      if (duration < 0.0F) {
         duration = 20.0F;
      }

      float completion = Math.min((float)this.getTime() / duration, 1.0F);
      float mainPhaseDuration = duration * 0.6F;
      float mainPhaseCompletion = Math.min((float)this.getTime(), mainPhaseDuration) / mainPhaseDuration;
      if (completion > 0.6F) {
         mainPhaseDuration = duration * 0.6F;
         mainPhaseCompletion = Math.max(duration - (float)this.getTime(), 0.0F) / mainPhaseDuration;
      }

      float ease = EasingFunctionHelper.easeInOutCubic(mainPhaseCompletion);
      model.f_102811_.f_104200_ = -5.0F + 1.0F * ease;
      model.f_102811_.f_104201_ = 2.0F + ease;
      model.f_102811_.f_104202_ = -5.0F * ease;
      model.f_102811_.f_104203_ = (float)Math.toRadians((double)-170.0F) * ease;
      model.f_102811_.f_104205_ = (float)Math.toRadians((double)10.0F) * ease;
      model.f_102812_.f_104200_ = 5.0F + -1.0F * ease;
      model.f_102812_.f_104201_ = 2.0F + ease;
      model.f_102812_.f_104202_ = -5.0F * ease;
      model.f_102812_.f_104203_ = (float)Math.toRadians((double)-170.0F) * ease;
      model.f_102812_.f_104205_ = (float)Math.toRadians((double)-10.0F) * ease;
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

         matrixStack.m_85837_(-0.15, 0.2, 0.05);
         matrixStack.m_252781_(Axis.f_252529_.m_252977_(rotX * ease));
         matrixStack.m_252781_(Axis.f_252436_.m_252977_(-5.0F));
      }
   }
}
