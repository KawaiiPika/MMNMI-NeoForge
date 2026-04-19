package xyz.pixelatedw.mineminenomi.animations;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;

public class CannonHandlingAnimation extends Animation<LivingEntity, HumanoidModel<LivingEntity>> {
   public CannonHandlingAnimation(AnimationId<CannonHandlingAnimation> animId) {
      super(animId);
      this.setAnimationAngles(this::angles);
   }

   public void angles(LivingEntity entity, HumanoidModel<LivingEntity> model, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      model.f_102810_.f_104203_ = 0.5F;
      ModelPart var10000 = model.f_102811_;
      var10000.f_104203_ -= 0.8F;
      var10000 = model.f_102812_;
      var10000.f_104203_ -= 0.8F;
      model.f_102813_.f_104202_ = 4.0F;
      model.f_102814_.f_104202_ = 4.0F;
      model.f_102813_.f_104201_ = 12.2F;
      model.f_102814_.f_104201_ = 12.2F;
      model.f_102808_.f_104201_ = 4.2F;
      model.f_102810_.f_104201_ = 3.2F;
      model.f_102812_.f_104201_ = 5.2F;
      model.f_102811_.f_104201_ = 5.2F;
   }
}
