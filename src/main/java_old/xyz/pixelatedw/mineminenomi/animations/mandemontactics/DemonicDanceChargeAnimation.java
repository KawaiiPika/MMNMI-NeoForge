package xyz.pixelatedw.mineminenomi.animations.mandemontactics;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;

public class DemonicDanceChargeAnimation extends Animation<LivingEntity, EntityModel<LivingEntity>> {
   public DemonicDanceChargeAnimation(AnimationId<DemonicDanceChargeAnimation> animId) {
      super(animId);
      this.setAnimationPostAngles(this::setup);
      this.setAnimationAngles(this::angles);
      this.setAnimationHeldItem(this::heldItem);
   }

   public void setup(LivingEntity entity, EntityModel<LivingEntity> entityModel, PoseStack matrixStack, MultiBufferSource buffer, int packedLight, float partialTicks, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
   }

   public void angles(LivingEntity entity, EntityModel<LivingEntity> model, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
   }

   public void heldItem(LivingEntity entity, ItemStack stack, ItemDisplayContext transformType, HumanoidArm handSide, PoseStack matrixStack, MultiBufferSource renderBuffer, int packedLight) {
      if (stack.m_41720_() == ModWeapons.TONFA.get()) {
         if (handSide == HumanoidArm.RIGHT) {
            matrixStack.m_85837_(-0.05, -0.15, 0.1);
            matrixStack.m_252781_(Axis.f_252403_.m_252977_(95.0F));
            matrixStack.m_252781_(Axis.f_252436_.m_252977_((float)(-this.getTime() * 40L % 360L)));
         }

      }
   }
}
