package xyz.pixelatedw.mineminenomi.animations;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;

public class HandStandSpinAnimation extends BodyRotateAnimation {
   public HandStandSpinAnimation(AnimationId<HandStandSpinAnimation> animId) {
      super(animId, 35.0F);
      this.setAnimationPostAngles(this::setup);
      this.setAnimationAngles(this::angles);
   }

   public void angles(LivingEntity entity, AgeableListModel<LivingEntity> model, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      if (model instanceof HumanoidModel humanoidModel) {
         humanoidModel.f_102812_.f_104203_ = (float)Math.toRadians((double)180.0F);
         humanoidModel.f_102812_.f_104205_ = (float)Math.toRadians((double)10.0F);
         humanoidModel.f_102811_.f_104203_ = (float)Math.toRadians((double)180.0F);
         humanoidModel.f_102811_.f_104205_ = (float)Math.toRadians((double)-10.0F);
         humanoidModel.f_102813_.f_104205_ = (float)Math.toRadians((double)90.0F);
         humanoidModel.f_102814_.f_104205_ = (float)Math.toRadians((double)-90.0F);
      }

   }

   public void setup(LivingEntity entity, AgeableListModel<LivingEntity> entityModel, PoseStack matrixStack, MultiBufferSource buffer, int packedLight, float partialTicks, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      if (entity instanceof Player) {
         matrixStack.m_252880_(0.0F, 1.0F, 0.0F);
      }

      matrixStack.m_252781_(Axis.f_252529_.m_252977_(180.0F));
      super.setup(entity, entityModel, matrixStack, buffer, packedLight, partialTicks, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
   }
}
