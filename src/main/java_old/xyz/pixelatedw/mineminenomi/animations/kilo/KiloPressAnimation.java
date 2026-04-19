package xyz.pixelatedw.mineminenomi.animations.kilo;

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

public class KiloPressAnimation extends Animation<LivingEntity, HumanoidModel<LivingEntity>> {
   public KiloPressAnimation(AnimationId<KiloPressAnimation> animId) {
      super(animId);
      this.setAnimationAngles(this::angles);
      this.setAnimationHeldItem(this::heldItem);
   }

   public void angles(LivingEntity entity, HumanoidModel<LivingEntity> model, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      model.f_102811_.f_104203_ = (float)Math.toRadians((double)200.0F);
      model.f_102811_.f_104204_ = 0.0F;
      model.f_102812_.f_104203_ = (float)Math.toRadians((double)220.0F);
      model.f_102812_.f_104204_ = (float)Math.toRadians((double)-20.0F);
      model.f_102812_.f_104205_ = (float)Math.toRadians((double)-50.0F);
      model.f_102813_.f_104203_ = (float)Math.toRadians((double)-90.0F);
      model.f_102813_.f_104204_ = (float)Math.toRadians((double)10.0F);
      model.f_102814_.f_104203_ = (float)Math.toRadians((double)-90.0F);
      model.f_102814_.f_104204_ = (float)Math.toRadians((double)-10.0F);
   }

   public void heldItem(LivingEntity entity, ItemStack stack, ItemDisplayContext transformType, HumanoidArm handSide, PoseStack matrixStack, MultiBufferSource renderBuffer, int packedLight) {
      matrixStack.m_85837_((double)0.0F, -0.2, -0.05);
      matrixStack.m_252781_(Axis.f_252529_.m_252977_(-65.0F));
   }
}
