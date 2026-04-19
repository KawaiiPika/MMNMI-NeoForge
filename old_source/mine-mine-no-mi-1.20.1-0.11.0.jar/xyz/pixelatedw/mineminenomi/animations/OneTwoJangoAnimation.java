package xyz.pixelatedw.mineminenomi.animations;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;

public class OneTwoJangoAnimation extends Animation<LivingEntity, HumanoidModel<LivingEntity>> {
   public OneTwoJangoAnimation(AnimationId<OneTwoJangoAnimation> animId) {
      super(animId);
      this.setAnimationAngles(this::angles);
      this.setAnimationHeldItem(this::heldItem);
   }

   public void angles(LivingEntity player, HumanoidModel<LivingEntity> model, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      ModelPart arm = model.f_102811_;
      arm.f_104203_ = arm.f_104203_ * 0.5F - (float)Math.PI + 1.5F;
      arm.f_104204_ = 0.2F;
   }

   public void heldItem(LivingEntity entity, ItemStack stack, ItemDisplayContext transformType, HumanoidArm handSide, PoseStack matrixStack, MultiBufferSource renderBuffer, int packedLight) {
      if (entity.m_5737_() == handSide) {
         float angle = (float)Math.toDegrees(Math.sin((double)(0.6F * (float)entity.f_19797_) + Math.PI)) * 0.15F;
         matrixStack.m_252781_(Axis.f_252403_.m_252977_(angle));
         matrixStack.m_85837_(0.1, -0.4, 0.05);
         matrixStack.m_252781_(Axis.f_252529_.m_252977_(-110.0F));
         matrixStack.m_252781_(Axis.f_252436_.m_252977_(5.0F));
         matrixStack.m_252781_(Axis.f_252403_.m_252977_(-90.0F));
      }

   }
}
