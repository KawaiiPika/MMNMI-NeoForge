package xyz.pixelatedw.mineminenomi.animations.oto;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;

public class BonAnimation extends Animation<LivingEntity, HumanoidModel<LivingEntity>> {
   private static final float SPEED = 15.0F;

   public BonAnimation(AnimationId<BonAnimation> animId) {
      super(animId);
      this.setAnimationAngles(this::angles);
   }

   public void angles(LivingEntity player, HumanoidModel<LivingEntity> model, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      model.f_102812_.f_104205_ = (float)Math.toRadians((double)30.0F);
      model.f_102812_.f_104203_ = (float)Math.toRadians((double)-50.0F);
      model.f_102811_.f_104205_ = (float)Math.toRadians((double)-60.0F);
      float angle = Mth.m_14036_(0.0F + (float)this.getTime() * 15.0F, 0.0F, 30.0F);
      model.f_102811_.f_104203_ = (float)Math.toRadians((double)(-60.0F + angle));
   }
}
