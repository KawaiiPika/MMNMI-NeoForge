package xyz.pixelatedw.mineminenomi.animations.blackleg;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.api.math.EasingFunctionHelper;

public class AntiMannerKickCourseAnimation extends Animation<LivingEntity, HumanoidModel<LivingEntity>> {
   public AntiMannerKickCourseAnimation(AnimationId<AntiMannerKickCourseAnimation> animId) {
      super(animId);
      this.setAnimationAngles(this::angles);
   }

   public void angles(LivingEntity entity, HumanoidModel<LivingEntity> model, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      float max = (float)this.getAnimationInitialDuration() * 0.7F;
      float c = Math.min((float)this.getTime(), max) / max;
      float ease1 = EasingFunctionHelper.easeInSine(c);
      float ease2 = EasingFunctionHelper.easeInCubic(c);
      model.f_102808_.f_104201_ = 1.0F;
      model.f_102808_.f_104202_ = 3.5F;
      model.f_102810_.f_104200_ = 1.0F;
      model.f_102810_.f_104201_ = 1.0F;
      model.f_102810_.f_104202_ = 2.5F;
      model.f_102810_.f_104203_ = (float)Math.toRadians((double)-5.0F) * ease1;
      model.f_102810_.f_104204_ = (float)Math.toRadians((double)25.0F) * ease1;
      model.f_102811_.f_104200_ = -4.5F;
      model.f_102811_.f_104202_ = 4.0F;
      model.f_102811_.f_104204_ = (float)Math.toRadians((double)25.0F) * ease1;
      model.f_102811_.f_104205_ = (float)Math.toRadians((double)25.0F) * ease1;
      model.f_102811_.f_104203_ = 0.0F;
      model.f_102812_.f_104202_ = -2.0F;
      model.f_102812_.f_104203_ = (float)Math.toRadians((double)-190.0F) * ease1;
      model.f_102812_.f_104204_ = (float)Math.toRadians((double)-20.0F) * ease1;
      model.f_102812_.f_104205_ = (float)Math.toRadians((double)-20.0F) * ease1;
      model.f_102813_.f_104202_ = -3.0F;
      model.f_102813_.f_104201_ = 8.0F;
      model.f_102813_.f_104203_ = (float)Math.toRadians((double)180.0F) * -ease2;
      model.f_102813_.f_104204_ = 0.0F;
      model.f_102813_.f_104205_ = 0.0F;
   }
}
