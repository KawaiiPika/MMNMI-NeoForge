package xyz.pixelatedw.mineminenomi.animations;

import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.models.entities.mobs.animals.DugongModel;

public class PointArmsAnimation extends Animation<LivingEntity, AgeableListModel<LivingEntity>> {
   public PointArmsAnimation(AnimationId<PointArmsAnimation> animId) {
      super(animId);
      this.setAnimationAngles(this::angles);
   }

   public void angles(LivingEntity player, AgeableListModel<LivingEntity> model, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      ModelPart rightArm = null;
      ModelPart leftArm = null;
      if (model instanceof HumanoidModel<?> biped) {
         rightArm = biped.f_102811_;
         leftArm = biped.f_102812_;
      } else if (model instanceof DugongModel<?> dugong) {
         rightArm = dugong.rightArm;
         leftArm = dugong.leftArm;
      }

      if (rightArm != null) {
         rightArm.f_104203_ = rightArm.f_104203_ * 0.5F - (float)Math.PI + 1.8F;
         rightArm.f_104204_ = -0.2F;
      }

      if (leftArm != null) {
         leftArm.f_104203_ = leftArm.f_104203_ * 0.5F - (float)Math.PI + 1.8F;
         leftArm.f_104204_ = 0.2F;
      }

   }
}
