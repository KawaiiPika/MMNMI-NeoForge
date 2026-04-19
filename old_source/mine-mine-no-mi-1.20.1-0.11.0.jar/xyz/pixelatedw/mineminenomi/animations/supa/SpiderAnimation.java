package xyz.pixelatedw.mineminenomi.animations.supa;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;

public class SpiderAnimation extends Animation<LivingEntity, HumanoidModel<LivingEntity>> {
   public SpiderAnimation(AnimationId<SpiderAnimation> animId) {
      super(animId);
      this.setAnimationAngles(this::angles);
   }

   public void angles(LivingEntity entity, HumanoidModel<LivingEntity> model, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      ModelPart var10000 = model.f_102811_;
      var10000.f_104200_ -= 5.0F;
      var10000 = model.f_102811_;
      var10000.f_104202_ -= 4.0F;
      var10000 = model.f_102811_;
      var10000.f_104201_ += 5.0F;
      model.f_102811_.f_104203_ = 0.0F;
      model.f_102811_.f_104204_ = (float)Math.toRadians((double)180.0F);
      model.f_102811_.f_104205_ = (float)Math.toRadians((double)-90.0F);
      var10000 = model.f_102812_;
      var10000.f_104200_ += 5.0F;
      var10000 = model.f_102812_;
      var10000.f_104202_ -= 4.0F;
      var10000 = model.f_102812_;
      var10000.f_104201_ += 5.0F;
      model.f_102812_.f_104203_ = 0.0F;
      model.f_102812_.f_104204_ = (float)Math.toRadians((double)180.0F);
      model.f_102812_.f_104205_ = (float)Math.toRadians((double)90.0F);
      model.f_102808_.f_104201_ = 3.0F;
      model.f_102810_.f_104201_ = 3.0F;
      model.f_102813_.f_104200_ = -6.0F;
      model.f_102813_.f_104202_ = 0.0F;
      model.f_102813_.f_104201_ = 12.0F;
      model.f_102814_.f_104200_ = 6.0F;
      model.f_102814_.f_104202_ = 0.0F;
      model.f_102814_.f_104201_ = 12.0F;
   }
}
