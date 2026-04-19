package xyz.pixelatedw.mineminenomi.animations;

import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.models.entities.mobs.animals.DugongModel;

public class CrossedArmsAnimation extends Animation<LivingEntity, AgeableListModel<LivingEntity>> {
   public CrossedArmsAnimation(AnimationId<CrossedArmsAnimation> animId) {
      super(animId);
      this.setAnimationAngles(this::angles);
   }

   public void angles(LivingEntity entity, AgeableListModel<LivingEntity> model, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      ModelPart rightArm = null;
      ModelPart leftArm = null;
      if (model instanceof HumanoidModel<?> biped) {
         rightArm = biped.f_102811_;
         leftArm = biped.f_102812_;
      } else if (model instanceof DugongModel<?> dugong) {
         rightArm = dugong.rightArm;
         leftArm = dugong.leftArm;
         rightArm.f_104202_ = -4.0F;
         leftArm.f_104202_ = -4.0F;
      }

      if (rightArm != null) {
         rightArm.f_104203_ = -1.2415929F;
         rightArm.f_104204_ = -1.2F;
         rightArm.f_104205_ = -0.8F;
      }

      if (leftArm != null) {
         leftArm.f_104203_ = -1.2415929F;
         leftArm.f_104204_ = 1.2F;
         leftArm.f_104205_ = 0.8F;
      }

   }
}
