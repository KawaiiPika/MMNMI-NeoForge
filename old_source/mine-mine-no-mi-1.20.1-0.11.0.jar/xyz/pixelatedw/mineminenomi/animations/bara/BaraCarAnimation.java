package xyz.pixelatedw.mineminenomi.animations.bara;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;

public class BaraCarAnimation extends Animation<LivingEntity, HumanoidModel<LivingEntity>> {
   public BaraCarAnimation(AnimationId<BaraCarAnimation> animId) {
      super(animId);
      this.setAnimationPostAngles(this::postAngles);
   }

   public void postAngles(LivingEntity entity, HumanoidModel<LivingEntity> entityModel, PoseStack poseStack, MultiBufferSource buffer, int packedLight, float partialTicks, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      entityModel.f_102808_.f_104201_ = 19.0F;
      entityModel.f_102808_.f_104202_ = -1.5F;
      entityModel.f_102810_.f_104202_ = -7.0F;
      entityModel.f_102810_.f_104201_ = 20.0F;
      entityModel.f_102810_.f_104203_ = (float)Math.toRadians((double)90.0F);
      entityModel.f_102814_.f_104200_ = 4.0F;
      entityModel.f_102814_.f_104201_ = 20.0F;
      entityModel.f_102814_.f_104202_ = 4.0F;
      entityModel.f_102814_.f_104203_ = (float)Math.toRadians((double)100.0F);
      entityModel.f_102814_.f_104204_ = (float)Math.toRadians((double)15.0F);
      entityModel.f_102814_.f_104205_ = (float)Math.toRadians((double)10.0F);
      entityModel.f_102813_.f_104200_ = -4.0F;
      entityModel.f_102813_.f_104201_ = 20.0F;
      entityModel.f_102813_.f_104202_ = 4.0F;
      entityModel.f_102813_.f_104203_ = (float)Math.toRadians((double)100.0F);
      entityModel.f_102813_.f_104204_ = (float)Math.toRadians((double)-15.0F);
      entityModel.f_102813_.f_104205_ = (float)Math.toRadians((double)-10.0F);
      entityModel.f_102812_.f_104201_ = 20.0F;
      entityModel.f_102812_.f_104202_ = -6.0F;
      entityModel.f_102812_.f_104203_ = (float)Math.toRadians((double)-75.0F);
      entityModel.f_102811_.f_104201_ = 20.0F;
      entityModel.f_102811_.f_104202_ = -6.0F;
      entityModel.f_102811_.f_104203_ = (float)Math.toRadians((double)-75.0F);
      if (entityModel instanceof PlayerModel playerModel) {
         playerModel.f_103375_.m_104315_(playerModel.f_102811_);
         playerModel.f_103374_.m_104315_(playerModel.f_102812_);
         playerModel.f_103377_.m_104315_(playerModel.f_102813_);
         playerModel.f_103376_.m_104315_(playerModel.f_102814_);
      }

   }
}
