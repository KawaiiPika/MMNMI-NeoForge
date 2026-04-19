package xyz.pixelatedw.mineminenomi.animations.mandemontactics;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;

public class DemonicDanceLeapAnimation extends Animation<LivingEntity, HumanoidModel<LivingEntity>> {
   public DemonicDanceLeapAnimation(AnimationId<DemonicDanceLeapAnimation> animId) {
      super(animId);
      this.setAnimationPostAngles(this::setup);
      this.setAnimationAngles(this::angles);
      this.setAnimationHeldItem(this::heldItem);
   }

   public void setup(LivingEntity entity, EntityModel<LivingEntity> entityModel, PoseStack matrixStack, MultiBufferSource buffer, int packedLight, float partialTicks, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      if (!this.isInAir(entity)) {
         matrixStack.m_252781_(Axis.f_252529_.m_252977_(30.0F));
      }

   }

   public void angles(LivingEntity entity, HumanoidModel<LivingEntity> model, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      if (this.isInAir(entity)) {
         model.f_102811_.f_104203_ = (float)Math.toRadians((double)180.0F);
         model.f_102811_.f_104205_ = (float)Math.toRadians((double)-10.0F);
         model.f_102813_.f_104203_ = 1.2F;
         model.f_102813_.f_104204_ = -0.1F;
         model.f_102813_.f_104202_ = -1.0F;
         model.f_102814_.f_104203_ = 1.2F;
         model.f_102814_.f_104204_ = 0.1F;
         model.f_102814_.f_104202_ = -1.0F;
      } else {
         model.f_102811_.f_104203_ = (float)Math.toRadians((double)-90.0F);
         model.f_102813_.f_104203_ = -1.2F;
         model.f_102813_.f_104204_ = 0.3F;
         model.f_102813_.f_104202_ = 1.0F;
         model.f_102814_.f_104203_ = 1.2F;
         model.f_102814_.f_104204_ = 0.1F;
         model.f_102814_.f_104202_ = -1.0F;
      }

   }

   public void heldItem(LivingEntity entity, ItemStack stack, ItemDisplayContext transformType, HumanoidArm handSide, PoseStack matrixStack, MultiBufferSource renderBuffer, int packedLight) {
      if (handSide == HumanoidArm.RIGHT) {
         matrixStack.m_85837_((double)0.0F, (double)0.0F, 0.15);
         matrixStack.m_252781_(Axis.f_252436_.m_252977_(180.0F));
      }

   }

   private boolean isInAir(LivingEntity entity) {
      double groundDistance = WyHelper.getDifferenceToFloor(entity);
      return this.getTime() <= 5L || groundDistance > (double)0.8F;
   }
}
