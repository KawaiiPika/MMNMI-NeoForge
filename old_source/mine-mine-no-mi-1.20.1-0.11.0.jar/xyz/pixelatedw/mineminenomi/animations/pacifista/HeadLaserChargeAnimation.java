package xyz.pixelatedw.mineminenomi.animations.pacifista;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;

public class HeadLaserChargeAnimation extends Animation<LivingEntity, HumanoidModel<LivingEntity>> {
   public HeadLaserChargeAnimation(AnimationId<HeadLaserChargeAnimation> animId) {
      super(animId);
      this.setAnimationPostAngles(this::postAngles);
   }

   public void postAngles(LivingEntity entity, HumanoidModel<LivingEntity> entityModel, PoseStack matrixStack, MultiBufferSource buffer, int packedLight, float partialTicks, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      entityModel.f_102808_.f_104201_ = 2.0F;
      entityModel.f_102809_.f_104201_ = 2.0F;
      entityModel.f_102808_.f_104202_ = (float)(WyHelper.randomDouble(entity.m_217043_()) / (double)4.0F) - 0.75F;
      entityModel.f_102808_.f_104200_ = (float)(WyHelper.randomDouble(entity.m_217043_()) / (double)4.0F);
   }
}
