package xyz.pixelatedw.mineminenomi.animations;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;

public class UnicycleRidingAnimation extends Animation<LivingEntity, HumanoidModel<LivingEntity>> {
   public UnicycleRidingAnimation(AnimationId<UnicycleRidingAnimation> animId) {
      super(animId);
      this.setAnimationAngles(this::angles);
   }

   public void angles(LivingEntity entity, HumanoidModel<LivingEntity> model, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      float rightLegY = 10.0F;
      float rightLegX = 0.0F;
      float leftLegY = 12.0F;
      float leftLegX = 0.0F;
      double xDiff = Math.abs(entity.m_20185_() - entity.f_19790_);
      double zDiff = Math.abs(entity.m_20189_() - entity.f_19792_);
      boolean isMoving = xDiff != (double)0.0F || zDiff != (double)0.0F;
      if (isMoving || entity.f_20902_ != 0.0F) {
         if (entity instanceof Player) {
            float swingSpeed = 7.5F;
            rightLegY = 11.0F + -Mth.m_14089_(limbSwing * swingSpeed) * 12.9F * limbSwingAmount / 1.0F;
            rightLegX = Mth.m_14031_(limbSwing * swingSpeed + (float)Math.PI) * 1.9F * limbSwingAmount / 1.0F;
            leftLegY = 11.0F + Mth.m_14089_(limbSwing * swingSpeed) * 12.9F * limbSwingAmount / 1.0F;
            leftLegX = -Mth.m_14031_(limbSwing * swingSpeed + (float)Math.PI) * 1.9F * limbSwingAmount / 1.0F;
         } else {
            float swingSpeed = 0.8F;
            rightLegY = 11.0F + -Mth.m_14089_(limbSwing * swingSpeed) * 0.9F * limbSwingAmount / 1.0F;
            rightLegX = Mth.m_14031_(limbSwing * swingSpeed + (float)Math.PI) * 0.3F * limbSwingAmount / 1.0F;
            leftLegY = 11.0F + Mth.m_14089_(limbSwing * swingSpeed) * 0.9F * limbSwingAmount / 1.0F;
            leftLegX = -Mth.m_14031_(limbSwing * swingSpeed + (float)Math.PI) * 0.3F * limbSwingAmount / 1.0F;
         }
      }

      model.f_102813_.f_104202_ = -0.5F;
      model.f_102813_.f_104201_ = rightLegY;
      model.f_102813_.f_104200_ = -2.5F;
      model.f_102813_.f_104203_ = rightLegX;
      model.f_102813_.f_104204_ = 0.2F;
      model.f_102813_.f_104205_ = 0.02F;
      model.f_102814_.f_104202_ = -0.5F;
      model.f_102814_.f_104201_ = leftLegY;
      model.f_102814_.f_104200_ = 2.5F;
      model.f_102814_.f_104203_ = leftLegX;
      model.f_102814_.f_104204_ = -0.2F;
      model.f_102814_.f_104205_ = -0.02F;
   }
}
