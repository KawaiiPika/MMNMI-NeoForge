package xyz.pixelatedw.mineminenomi.animations.pteranodon;

import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.models.morphs.PteranodonFlyModel;

public class PteraOpenMouthAnimation extends Animation<LivingEntity, PteranodonFlyModel<LivingEntity>> {
   public PteraOpenMouthAnimation(AnimationId<PteraOpenMouthAnimation> animId) {
      super(animId);
      this.setAnimationAngles(this::angles);
   }

   public void angles(LivingEntity player, PteranodonFlyModel<LivingEntity> model, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      model.lowerBeak.f_104203_ = (float)Math.toRadians((double)40.0F);
   }
}
