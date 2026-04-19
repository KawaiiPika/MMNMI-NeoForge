package xyz.pixelatedw.mineminenomi.animations;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;

public class PunchRushAnimation extends Animation<LivingEntity, HumanoidModel<LivingEntity>> {
   public PunchRushAnimation(AnimationId<PunchRushAnimation> animId) {
      super(animId);
      this.setAnimationAngles(this::angles);
   }

   public void angles(LivingEntity player, HumanoidModel<LivingEntity> model, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      float rightArmMovement = -Mth.m_14089_(ageInTicks * 2.2F);
      model.f_102811_.f_104202_ = 5.0F * (0.5F - rightArmMovement);
      model.f_102811_.f_104203_ = (float)Math.toRadians((double)-90.0F);
      model.f_102811_.f_104204_ = 0.0F;
      float leftArmMovement = Mth.m_14089_(ageInTicks * 2.2F);
      model.f_102812_.f_104202_ = 5.0F * (0.5F - leftArmMovement);
      model.f_102812_.f_104203_ = (float)Math.toRadians((double)-90.0F);
      model.f_102812_.f_104204_ = 0.0F;
   }
}
