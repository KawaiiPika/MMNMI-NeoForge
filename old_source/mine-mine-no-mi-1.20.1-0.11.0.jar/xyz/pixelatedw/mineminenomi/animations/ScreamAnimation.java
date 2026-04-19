package xyz.pixelatedw.mineminenomi.animations;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.api.math.EasingFunctionHelper;

public class ScreamAnimation extends Animation<LivingEntity, HumanoidModel<LivingEntity>> {
   public ScreamAnimation(AnimationId<ScreamAnimation> animId) {
      super(animId);
      this.setAnimationAngles(this::angles);
   }

   public void angles(LivingEntity entity, HumanoidModel<LivingEntity> model, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      entity.f_20883_ = entity.f_20885_;
      float max = (float)this.getAnimationInitialDuration() * 0.3F;
      float c = Math.min((float)this.getTime(), max) / max;
      float ease1 = EasingFunctionHelper.easeInBack(c);
      model.f_102808_.f_104202_ = -0.5F * ease1;
      model.f_102808_.f_104201_ = 1.0F * ease1;
      model.f_102808_.f_104203_ = 0.0F;
      model.f_102808_.f_104204_ = 0.0F;
      model.f_102808_.f_104205_ = 0.0F;
      model.f_102810_.f_104201_ = 1.0F * ease1;
      model.f_102811_.f_104202_ = 1.0F;
      model.f_102811_.f_104201_ = 3.0F;
      model.f_102811_.f_104203_ = (float)Math.toRadians((double)20.0F) * ease1;
      model.f_102811_.f_104205_ = (float)Math.toRadians((double)45.0F) * ease1;
      model.f_102812_.f_104202_ = 1.0F;
      model.f_102812_.f_104201_ = 3.0F;
      model.f_102812_.f_104203_ = (float)Math.toRadians((double)20.0F) * ease1;
      model.f_102812_.f_104205_ = (float)Math.toRadians((double)-45.0F) * ease1;
      model.f_102813_.f_104203_ = (float)Math.toRadians((double)5.0F) * ease1;
      model.f_102813_.f_104204_ = (float)Math.toRadians((double)10.0F) * ease1;
      model.f_102813_.f_104205_ = (float)Math.toRadians((double)10.0F) * ease1;
      model.f_102813_.f_104200_ = -2.0F - 0.5F * ease1;
      model.f_102813_.f_104202_ = 0.0F;
      model.f_102813_.f_104201_ = 12.0F;
      model.f_102814_.f_104203_ = (float)Math.toRadians((double)5.0F) * ease1;
      model.f_102814_.f_104204_ = (float)Math.toRadians((double)-10.0F) * ease1;
      model.f_102814_.f_104205_ = (float)Math.toRadians((double)-10.0F) * ease1;
      model.f_102814_.f_104200_ = 2.0F + 0.5F * ease1;
      model.f_102814_.f_104202_ = 0.0F;
      model.f_102814_.f_104201_ = 12.0F;
   }
}
