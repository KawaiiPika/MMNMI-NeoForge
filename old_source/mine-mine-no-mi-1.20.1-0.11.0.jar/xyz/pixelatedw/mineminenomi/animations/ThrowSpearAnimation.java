package xyz.pixelatedw.mineminenomi.animations;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;

public class ThrowSpearAnimation extends Animation<LivingEntity, HumanoidModel<LivingEntity>> {
   public ThrowSpearAnimation(AnimationId<ThrowSpearAnimation> animId) {
      super(animId);
      this.setAnimationAngles(this::angles);
      this.setAnimationHeldItem(this::heldItem);
   }

   public void angles(LivingEntity player, HumanoidModel<LivingEntity> model, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      model.f_102811_.f_104205_ = (float)Math.toRadians((double)-5.0F);
      model.f_102811_.f_104203_ = (float)Math.toRadians((double)-150.0F);
   }

   public void heldItem(LivingEntity entity, ItemStack stack, ItemDisplayContext transformType, HumanoidArm handSide, PoseStack matrixStack, MultiBufferSource renderBuffer, int packedLight) {
      matrixStack.m_85837_((double)0.0F, 0.1, 0.3);
      matrixStack.m_252781_(Axis.f_252529_.m_252977_(175.0F));
   }
}
