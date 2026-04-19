package xyz.pixelatedw.mineminenomi.animations;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;

public class RaiseArmsAnimation extends Animation<LivingEntity, HumanoidModel<LivingEntity>> {
   public RaiseArmsAnimation(AnimationId<RaiseArmsAnimation> animId) {
      super(animId);
      this.setAnimationAngles(this::angles);
   }

   public void angles(LivingEntity player, HumanoidModel<LivingEntity> model, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      model.f_102811_.f_104203_ = (float)Math.toRadians((double)180.0F);
      model.f_102811_.f_104204_ = 0.0F;
      model.f_102812_.f_104203_ = (float)Math.toRadians((double)180.0F);
      model.f_102812_.f_104204_ = 0.0F;
   }
}
