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

public class PointWeaponAnimation extends Animation<LivingEntity, HumanoidModel<LivingEntity>> {
   public PointWeaponAnimation(AnimationId<PointWeaponAnimation> animId) {
      super(animId);
      this.setAnimationAngles(this::angles);
      this.setAnimationHeldItem(this::heldItem);
   }

   public void angles(LivingEntity player, HumanoidModel<LivingEntity> model, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      ModelPart var10000 = model.f_102811_;
      var10000.f_104202_ += 3.0F;
      var10000 = model.f_102811_;
      var10000.f_104203_ = (float)((double)var10000.f_104203_ + Math.toRadians((double)-70.0F));
      model.f_102811_.f_104204_ = 0.0F;
      var10000 = model.f_102812_;
      var10000.f_104203_ = (float)((double)var10000.f_104203_ + Math.toRadians((double)-90.0F));
      var10000 = model.f_102812_;
      var10000.f_104204_ = (float)((double)var10000.f_104204_ + Math.toRadians((double)60.0F));
   }

   public void heldItem(LivingEntity entity, ItemStack stack, ItemDisplayContext transformType, HumanoidArm handSide, PoseStack matrixStack, MultiBufferSource renderBuffer, int packedLight) {
      matrixStack.m_85837_((double)0.0F, -0.2, -0.05);
      matrixStack.m_252781_(Axis.f_252529_.m_252977_(-75.0F));
   }
}
