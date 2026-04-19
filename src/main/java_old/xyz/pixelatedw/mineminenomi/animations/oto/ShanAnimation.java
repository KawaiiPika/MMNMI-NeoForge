package xyz.pixelatedw.mineminenomi.animations.oto;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;

public class ShanAnimation extends Animation<LivingEntity, HumanoidModel<LivingEntity>> {
   private static final float SPEED = 20.0F;

   public ShanAnimation(AnimationId<ShanAnimation> animId) {
      super(animId);
      this.setAnimationAngles(this::angles);
   }

   public void angles(LivingEntity player, HumanoidModel<LivingEntity> model, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      float angle = Mth.m_14036_(-70.0F + (float)this.getTime() * 20.0F, -70.0F, -10.0F);
      model.f_102812_.f_104205_ = (float)Math.toRadians((double)180.0F);
      model.f_102812_.f_104204_ = (float)Math.toRadians((double)90.0F);
      model.f_102812_.f_104203_ = (float)Math.toRadians((double)angle);
      model.f_102811_.f_104205_ = (float)Math.toRadians((double)180.0F);
      model.f_102811_.f_104204_ = (float)Math.toRadians((double)-90.0F);
      model.f_102811_.f_104203_ = (float)Math.toRadians((double)angle);
   }
}
