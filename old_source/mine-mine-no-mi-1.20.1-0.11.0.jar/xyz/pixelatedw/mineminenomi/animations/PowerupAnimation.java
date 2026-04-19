package xyz.pixelatedw.mineminenomi.animations;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;

public class PowerupAnimation extends Animation<LivingEntity, HumanoidModel<LivingEntity>> {
   public PowerupAnimation(AnimationId<? extends PowerupAnimation> animId) {
      super(animId);
      this.setAnimationAngles(this::angles);
   }

   public void angles(LivingEntity player, HumanoidModel<LivingEntity> model, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      model.f_102811_.f_104203_ = (float)Math.toRadians((double)300.0F);
      model.f_102811_.f_104204_ = (float)Math.toRadians((double)30.0F);
      model.f_102811_.f_104205_ = (float)Math.toRadians((double)-30.0F);
      model.f_102812_.f_104203_ = (float)Math.toRadians((double)300.0F);
      model.f_102812_.f_104204_ = (float)Math.toRadians((double)-30.0F);
      model.f_102812_.f_104205_ = (float)Math.toRadians((double)30.0F);
      model.f_102810_.f_104203_ = 0.5F;
      model.f_102813_.f_104202_ = 4.0F;
      model.f_102814_.f_104202_ = 4.0F;
      model.f_102813_.f_104201_ = 12.2F;
      model.f_102814_.f_104201_ = 12.2F;
      ModelPart var10000 = model.f_102808_;
      var10000.f_104201_ += 4.2F;
      var10000 = model.f_102809_;
      var10000.f_104201_ += 4.2F;
      model.f_102810_.f_104201_ = 3.2F;
      model.f_102812_.f_104201_ = 5.2F;
      var10000 = model.f_102812_;
      var10000.f_104202_ += 3.0F;
      model.f_102811_.f_104201_ = 5.2F;
      var10000 = model.f_102811_;
      var10000.f_104202_ += 3.0F;
   }
}
