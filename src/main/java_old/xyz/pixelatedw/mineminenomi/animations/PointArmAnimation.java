package xyz.pixelatedw.mineminenomi.animations;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;

public class PointArmAnimation extends Animation<LivingEntity, HumanoidModel<LivingEntity>> {
   private HumanoidArm side;

   public PointArmAnimation(AnimationId<PointArmAnimation> animId, HumanoidArm side) {
      super(animId);
      this.side = HumanoidArm.RIGHT;
      this.side = side;
      this.setAnimationAngles(this::angles);
   }

   public void angles(LivingEntity player, HumanoidModel<LivingEntity> model, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      ModelPart arm = model.f_102811_;
      int sideMod = 1;
      if (this.side.equals(HumanoidArm.LEFT)) {
         arm = model.f_102812_;
         sideMod = -1;
      }

      arm.f_104203_ = arm.f_104203_ * 0.5F - (float)Math.PI + 1.8F;
      arm.f_104204_ = 0.2F * (float)sideMod;
   }
}
