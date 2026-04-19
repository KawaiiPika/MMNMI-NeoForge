package xyz.pixelatedw.mineminenomi.animations;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;

public class LoweringArmAnimation extends Animation<LivingEntity, HumanoidModel<LivingEntity>> {
   public LoweringArmAnimation(AnimationId<LoweringArmAnimation> animId) {
      super(animId);
      this.setAnimationAngles(this::angles);
   }

   public void angles(LivingEntity entity, HumanoidModel<LivingEntity> model, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      float time = (float)this.getTime();
      float armMovement = 0.0F;
      float waitTime = 15.0F;
      if (time > waitTime) {
         armMovement = Mth.m_14036_(0.2F * (time - waitTime) * (float)Math.PI, 0.0F, 3.0F);
      }

      model.f_102811_.f_104203_ = (float)Math.toRadians((double)180.0F) + armMovement;
   }
}
