package xyz.pixelatedw.mineminenomi.animations.donkrieg;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;

public class MH5Animation extends Animation<LivingEntity, HumanoidModel<LivingEntity>> {
   public MH5Animation(AnimationId<MH5Animation> animId) {
      super(animId);
      this.setAnimationAngles(this::angles);
   }

   public void angles(LivingEntity entity, HumanoidModel<LivingEntity> model, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      model.f_102812_.f_104203_ = (float)Math.toRadians((double)-90.0F);
      model.f_102812_.f_104204_ = (float)Math.toRadians((double)80.0F);
      ModelPart var10000 = model.f_102812_;
      var10000.f_104202_ -= 2.0F;
   }
}
