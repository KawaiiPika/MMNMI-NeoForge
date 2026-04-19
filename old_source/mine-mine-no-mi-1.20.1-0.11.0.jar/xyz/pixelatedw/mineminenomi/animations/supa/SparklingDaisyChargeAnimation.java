package xyz.pixelatedw.mineminenomi.animations.supa;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;

public class SparklingDaisyChargeAnimation extends Animation<LivingEntity, HumanoidModel<LivingEntity>> {
   private static final float SPEED = 0.07F;

   public SparklingDaisyChargeAnimation(AnimationId<SparklingDaisyChargeAnimation> animId) {
      super(animId);
      this.setAnimationAngles(this::angles);
   }

   public void angles(LivingEntity entity, HumanoidModel<LivingEntity> model, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      float time = (float)this.getTime() * 0.07F;
      float waitTime = 0.7F;
      if (time < waitTime) {
         model.f_102811_.f_104203_ = (float)Math.toRadians((double)300.0F);
         model.f_102811_.f_104204_ = (float)Math.toRadians((double)30.0F);
         model.f_102811_.f_104205_ = (float)Math.toRadians((double)-30.0F);
         model.f_102812_.f_104203_ = (float)Math.toRadians((double)300.0F);
         model.f_102812_.f_104204_ = (float)Math.toRadians((double)-30.0F);
         model.f_102812_.f_104205_ = (float)Math.toRadians((double)30.0F);
      } else {
         double t2 = (double)(time - waitTime);
         model.f_102811_.f_104203_ = (float)Math.toRadians((double)300.0F);
         model.f_102811_.f_104204_ = (float)Mth.m_14008_(t2 + (double)((float)Math.toRadians((double)30.0F)), (double)0.0F, (double)2.5F);
         model.f_102811_.f_104205_ = (float)Math.toRadians((double)-30.0F);
         model.f_102812_.f_104203_ = (float)Math.toRadians((double)300.0F);
         model.f_102812_.f_104204_ = (float)Mth.m_14008_(-t2 + (double)((float)Math.toRadians((double)-30.0F)), (double)-2.5F, (double)0.0F);
         model.f_102812_.f_104205_ = (float)Math.toRadians((double)30.0F);
      }

   }
}
