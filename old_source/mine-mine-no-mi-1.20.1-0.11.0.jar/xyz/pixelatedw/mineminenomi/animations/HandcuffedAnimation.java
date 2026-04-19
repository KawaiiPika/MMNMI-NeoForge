package xyz.pixelatedw.mineminenomi.animations;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;

public class HandcuffedAnimation extends Animation<LivingEntity, HumanoidModel<LivingEntity>> {
   public HandcuffedAnimation(AnimationId<HandcuffedAnimation> animId) {
      super(animId);
      this.setAnimationAngles(this::angles);
   }

   public void angles(LivingEntity player, HumanoidModel<LivingEntity> model, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      model.f_102811_.f_104203_ = (float)Math.toRadians((double)-40.0F);
      model.f_102812_.f_104203_ = (float)Math.toRadians((double)-40.0F);
   }
}
