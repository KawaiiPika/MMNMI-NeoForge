package xyz.pixelatedw.mineminenomi.animations.gura;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;

public class KaishinAnimation extends Animation<LivingEntity, HumanoidModel<LivingEntity>> {
   private static final double SPEED = (double)20.0F;

   public KaishinAnimation(AnimationId<KaishinAnimation> animId) {
      super(animId);
      this.setAnimationAngles(this::angles);
   }

   public void angles(LivingEntity player, HumanoidModel<LivingEntity> model, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      double angle = Mth.m_14008_((double)(80L - this.getTime()) * (double)20.0F, (double)-50.0F, (double)80.0F);
      model.f_102812_.f_104205_ = (float)Math.toRadians(angle);
      model.f_102812_.f_104203_ = (float)Math.toRadians((double)-10.0F);
      model.f_102811_.f_104205_ = (float)Math.toRadians(-angle);
      model.f_102811_.f_104203_ = (float)Math.toRadians((double)-10.0F);
   }
}
