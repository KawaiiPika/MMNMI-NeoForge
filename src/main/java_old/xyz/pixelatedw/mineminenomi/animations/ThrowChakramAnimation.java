package xyz.pixelatedw.mineminenomi.animations;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;

public class ThrowChakramAnimation extends Animation<LivingEntity, HumanoidModel<LivingEntity>> {
   public ThrowChakramAnimation(AnimationId<ThrowChakramAnimation> animId) {
      super(animId);
      this.setAnimationAngles(this::angles);
   }

   public void angles(LivingEntity entity, HumanoidModel<LivingEntity> model, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      long time = this.getTime();
      time = WyHelper.clamp(time, 0L, 40L) * 4L;
      double xRot = (double)(-130L - time);
      xRot = Mth.m_14008_(xRot, (double)-170.0F, (double)-130.0F);
      model.f_102811_.f_104202_ = -3.0F;
      model.f_102811_.f_104201_ = 3.5F;
      model.f_102811_.f_104204_ = 0.0F;
      model.f_102811_.f_104205_ = (float)Math.toRadians((double)95.0F);
      model.f_102811_.f_104203_ = (float)Math.toRadians(xRot);
      model.f_102812_.f_104203_ = 0.0F;
      model.f_102812_.f_104204_ = 0.0F;
      model.f_102812_.f_104205_ = 0.0F;
   }
}
