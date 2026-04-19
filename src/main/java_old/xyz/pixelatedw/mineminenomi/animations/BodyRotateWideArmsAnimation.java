package xyz.pixelatedw.mineminenomi.animations;

import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;

public class BodyRotateWideArmsAnimation extends BodyRotateAnimation {
   public BodyRotateWideArmsAnimation(AnimationId<BodyRotateWideArmsAnimation> animId, float speed) {
      super(animId, speed);
      this.setAnimationAngles(this::angles);
   }

   public void angles(LivingEntity player, AgeableListModel<LivingEntity> model, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      if (model instanceof HumanoidModel humanoidModel) {
         humanoidModel.f_102811_.f_104203_ = (float)Math.toRadians((double)-90.0F);
         humanoidModel.f_102811_.f_104205_ = (float)Math.toRadians((double)-90.0F);
         humanoidModel.f_102812_.f_104203_ = (float)Math.toRadians((double)90.0F);
         humanoidModel.f_102812_.f_104205_ = (float)Math.toRadians((double)-90.0F);
      }

   }
}
