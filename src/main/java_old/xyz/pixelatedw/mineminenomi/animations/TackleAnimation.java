package xyz.pixelatedw.mineminenomi.animations;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.models.entities.mobs.animals.SeaCowModel;

public class TackleAnimation extends Animation<LivingEntity, EntityModel<LivingEntity>> {
   private HumanoidArm side;

   public TackleAnimation(AnimationId<TackleAnimation> animId, HumanoidArm side) {
      super(animId);
      this.side = HumanoidArm.RIGHT;
      this.side = side;
      this.setAnimationAngles(this::angles);
   }

   public void angles(LivingEntity player, EntityModel<LivingEntity> model, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      if (model instanceof HumanoidModel bipedModel) {
         ModelPart mainArm = bipedModel.f_102811_;
         ModelPart oppositeArm = bipedModel.f_102812_;
         int sideMod = 1;
         if (this.side.equals(HumanoidArm.LEFT)) {
            mainArm = bipedModel.f_102812_;
            oppositeArm = bipedModel.f_102811_;
            sideMod = -1;
         }

         bipedModel.f_102810_.f_104204_ = -0.5F;
         mainArm.f_104202_ = -1.2F;
         mainArm.f_104200_ = -3.0F;
         mainArm.f_104203_ = -1.9415928F;
         mainArm.f_104204_ = -1.5F * (float)sideMod;
         mainArm.f_104205_ = 0.8F * (float)sideMod;
         oppositeArm.f_104202_ = 2.2F;
         oppositeArm.f_104203_ = 0.3F;
         oppositeArm.f_104205_ = -0.2F;
      } else if (model instanceof SeaCowModel seaCowModel) {
         seaCowModel.head.f_104203_ = (float)Math.toRadians((double)90.0F);
      }

   }
}
