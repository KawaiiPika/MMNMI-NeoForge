package xyz.pixelatedw.mineminenomi.animations;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;

public class SpinToWinAnimation extends BodyRotateAnimation {
   public SpinToWinAnimation(AnimationId<SpinToWinAnimation> animId, float speed) {
      super(animId, speed);
      this.setAnimationAngles(this::angles);
      this.setAnimationHeldItem(this::heldItem);
   }

   public void setup(LivingEntity entity, AgeableListModel<LivingEntity> entityModel, PoseStack matrixStack, MultiBufferSource buffer, int packedLight, float partialTicks, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      super.setup(entity, entityModel, matrixStack, buffer, packedLight, partialTicks, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
      matrixStack.m_85837_((double)0.0F, (double)0.0F, (double)0.4F);
   }

   private void angles(LivingEntity entity, AgeableListModel<LivingEntity> model, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      if (model instanceof HumanoidModel humanoidModel) {
         humanoidModel.f_102811_.f_104203_ = (float)Math.toRadians((double)-90.0F);
         humanoidModel.f_102811_.f_104205_ = (float)Math.toRadians((double)20.0F);
         humanoidModel.f_102811_.f_104204_ = (float)Math.toRadians((double)-20.0F);
         humanoidModel.f_102812_.f_104203_ = (float)Math.toRadians((double)-90.0F);
         humanoidModel.f_102812_.f_104205_ = (float)Math.toRadians((double)-20.0F);
         humanoidModel.f_102812_.f_104204_ = (float)Math.toRadians((double)20.0F);
         humanoidModel.f_102810_.f_104201_ = 0.5F;
         humanoidModel.f_102810_.f_104203_ = (float)Math.toRadians((double)-15.0F);
         humanoidModel.f_102813_.f_104202_ = -3.0F;
         humanoidModel.f_102813_.f_104203_ = (float)Math.toRadians((double)-15.0F);
         humanoidModel.f_102814_.f_104202_ = -3.0F;
         humanoidModel.f_102814_.f_104203_ = (float)Math.toRadians((double)-15.0F);
      }

   }

   private void heldItem(LivingEntity entity, ItemStack stack, ItemDisplayContext transformType, HumanoidArm handSide, PoseStack matrixStack, MultiBufferSource renderBuffer, int packedLight) {
      matrixStack.m_85837_(-0.15, -0.1, -0.2);
      matrixStack.m_252781_(Axis.f_252529_.m_252977_(-85.0F));
      matrixStack.m_252781_(Axis.f_252436_.m_252977_(20.0F));
      matrixStack.m_252781_(Axis.f_252403_.m_252977_(-16.0F));
   }
}
