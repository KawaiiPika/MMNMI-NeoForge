package xyz.pixelatedw.mineminenomi.animations;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;

public class LowSwingArmsAnimation extends Animation<LivingEntity, HumanoidModel<LivingEntity>> {
   public LowSwingArmsAnimation(AnimationId<LowSwingArmsAnimation> animId) {
      super(animId);
      this.setAnimationAngles(this::angles);
   }

   public void angles(LivingEntity entity, HumanoidModel<LivingEntity> model, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      float spread = 0.3F;
      float speed = 0.1F;
      model.f_102811_.f_104203_ = (float)Math.toRadians((double)-10.0F);
      model.f_102811_.f_104204_ = (float)(Math.cos((double)ageInTicks * Math.PI * (double)speed) * (double)spread);
      model.f_102811_.f_104205_ = (float)(Math.sin((double)ageInTicks * Math.PI * (double)speed) * (double)spread);
      model.f_102811_.f_104202_ = -2.0F;
      model.f_102811_.f_104201_ = 2.0F;
      ModelPart var10000 = model.f_102811_;
      var10000.f_104204_ = (float)((double)var10000.f_104204_ + Math.toRadians((double)90.0F));
      model.f_102812_.f_104203_ = (float)Math.toRadians((double)-10.0F);
      model.f_102812_.f_104204_ = (float)(Math.cos((double)ageInTicks * Math.PI * (double)speed) * (double)spread);
      model.f_102812_.f_104205_ = (float)(Math.sin((double)ageInTicks * Math.PI * (double)speed) * (double)spread);
      model.f_102812_.f_104202_ = -2.0F;
      model.f_102812_.f_104201_ = 2.0F;
      var10000 = model.f_102812_;
      var10000.f_104204_ = (float)((double)var10000.f_104204_ - Math.toRadians((double)-90.0F));
   }
}
