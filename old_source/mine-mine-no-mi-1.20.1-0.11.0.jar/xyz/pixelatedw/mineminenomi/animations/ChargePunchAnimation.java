package xyz.pixelatedw.mineminenomi.animations;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;

public class ChargePunchAnimation extends Animation<LivingEntity, HumanoidModel<LivingEntity>> {
   public ChargePunchAnimation(AnimationId<ChargePunchAnimation> animId) {
      super(animId);
      this.setAnimationAngles(this::angles);
   }

   public void angles(LivingEntity entity, HumanoidModel<LivingEntity> model, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      float time = (float)this.getTime() * 0.1F;
      ModelPart var10000 = model.f_102811_;
      var10000.f_104202_ += 4.0F;
      model.f_102811_.f_104204_ = (float)Math.toRadians((double)20.0F);
      model.f_102811_.f_104203_ = (float)Math.toRadians((double)-90.0F) + Mth.m_14031_((float)((double)time * Math.PI * 1.8)) / 7.0F;
      model.f_102811_.f_104205_ = Mth.m_14089_((float)((double)time * Math.PI * 1.8)) / 7.0F;
      model.f_102812_.f_104203_ = (float)Math.toRadians((double)-80.0F);
      model.f_102812_.f_104204_ = (float)Math.toRadians((double)15.0F);
   }
}
