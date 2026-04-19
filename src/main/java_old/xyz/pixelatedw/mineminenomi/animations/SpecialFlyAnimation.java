package xyz.pixelatedw.mineminenomi.animations;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;

public class SpecialFlyAnimation extends Animation<LivingEntity, HumanoidModel<LivingEntity>> {
   public SpecialFlyAnimation(AnimationId<SpecialFlyAnimation> animId) {
      super(animId);
      this.setAnimationPostAngles(this::setup);
      this.setAnimationAngles(this::angles);
   }

   public void angles(LivingEntity player, HumanoidModel<LivingEntity> model, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      double posXDiff = Math.abs(player.f_19854_ - player.m_20185_());
      double posZDiff = Math.abs(player.f_19856_ - player.m_20189_());
      float angles = 0.0F;
      if (posXDiff >= 0.01 || posZDiff >= 0.01) {
         angles = (float)Math.toRadians((double)45.0F);
         model.f_102813_.f_104201_ = 10.0F;
         model.f_102813_.f_104202_ = 8.0F;
         model.f_102814_.f_104202_ = 3.0F;
         model.f_102811_.f_104202_ = 2.0F;
         model.f_102812_.f_104202_ = 2.0F;
      }

      if (posXDiff >= (double)0.5F || posZDiff >= (double)0.5F) {
         angles = (float)Math.toRadians((double)60.0F);
         model.f_102813_.f_104201_ = 8.0F;
         model.f_102813_.f_104202_ = 10.0F;
         model.f_102814_.f_104201_ = 8.0F;
         model.f_102811_.f_104201_ = 1.0F;
         model.f_102812_.f_104201_ = 1.0F;
         model.f_102811_.f_104202_ = 2.5F;
         model.f_102812_.f_104202_ = 2.5F;
      }

      model.f_102810_.f_104203_ = angles;
      if (player.f_20921_ <= 0.0F) {
         model.f_102811_.f_104203_ = angles;
         model.f_102812_.f_104203_ = angles;
      }

      if (player.m_6047_()) {
         model.f_102811_.f_104201_ = 4.5F;
         model.f_102812_.f_104201_ = 4.5F;
      }

      model.f_102813_.f_104203_ = angles;
      model.f_102814_.f_104203_ = angles;
      model.f_102813_.f_104207_ = false;
      model.f_102814_.f_104207_ = false;
      if (model instanceof PlayerModel playerModel) {
         playerModel.f_103376_.f_104207_ = false;
         playerModel.f_103377_.f_104207_ = false;
      }

   }

   public void setup(LivingEntity entity, EntityModel<LivingEntity> entityModel, PoseStack matrixStack, MultiBufferSource buffer, int packedLight, float partialTicks, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      double posXDiff = Math.abs(entity.f_19854_ - entity.m_20185_());
      double posZDiff = Math.abs(entity.f_19856_ - entity.m_20189_());
      double drop = (double)0.0F;
      if (posXDiff >= 0.01 || posZDiff >= 0.01) {
         drop = 0.1;
      }

      if (posXDiff >= (double)0.5F || posZDiff >= (double)0.5F) {
         drop = 0.2;
      }

      matrixStack.m_85837_((double)0.0F, drop, (double)0.0F);
   }
}
