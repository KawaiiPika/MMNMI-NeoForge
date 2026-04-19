package xyz.pixelatedw.mineminenomi.animations;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.AbstractGorillaEntity;
import xyz.pixelatedw.mineminenomi.models.entities.mobs.animals.GorillaModel;

public class WeaponSlamAnimation extends Animation<LivingEntity, HumanoidModel<LivingEntity>> {
   public WeaponSlamAnimation(AnimationId<WeaponSlamAnimation> animId) {
      super(animId);
      this.setAnimationPostAngles(this::setup);
      this.setAnimationAngles(this::angles);
      this.setAnimationHeldItem(this::heldItem);
   }

   public void setup(LivingEntity entity, EntityModel<LivingEntity> entityModel, PoseStack matrixStack, MultiBufferSource buffer, int packedLight, float partialTicks, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      int animTime = (int)((float)((int)this.getTime()) * 4.5F);
      animTime = Mth.m_14045_(animTime, 0, 50) - 20;
      matrixStack.m_252781_(Axis.f_252529_.m_252977_((float)animTime));
   }

   public void angles(LivingEntity entity, HumanoidModel<LivingEntity> model, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      double groundDistance = WyHelper.getDifferenceToFloor(entity);
      if (groundDistance > (double)4.0F) {
         if (model instanceof GorillaModel) {
            model.f_102811_.f_104203_ = (float)Math.toRadians((double)150.0F);
            model.f_102811_.f_104205_ = (float)Math.toRadians((double)0.0F);
            model.f_102812_.f_104203_ = (float)Math.toRadians((double)150.0F);
            model.f_102812_.f_104205_ = (float)Math.toRadians((double)0.0F);
         } else {
            model.f_102811_.f_104203_ = (float)Math.toRadians((double)150.0F);
            model.f_102811_.f_104205_ = (float)Math.toRadians((double)20.0F);
            model.f_102812_.f_104203_ = (float)Math.toRadians((double)150.0F);
            model.f_102812_.f_104205_ = (float)Math.toRadians((double)-20.0F);
         }

         model.f_102813_.f_104203_ = 1.2F;
         model.f_102813_.f_104204_ = -0.1F;
         model.f_102813_.f_104202_ = -1.0F;
         model.f_102814_.f_104203_ = 1.2F;
         model.f_102814_.f_104204_ = 0.1F;
         model.f_102814_.f_104202_ = -1.0F;
      } else {
         model.f_102811_.f_104203_ = (float)Math.toRadians((double)-50.0F);
         model.f_102811_.f_104205_ = (float)Math.toRadians((double)-20.0F);
         model.f_102812_.f_104203_ = (float)Math.toRadians((double)-50.0F);
         model.f_102812_.f_104205_ = (float)Math.toRadians((double)20.0F);
         model.f_102813_.f_104203_ = -1.2F;
         model.f_102813_.f_104204_ = 0.3F;
         model.f_102813_.f_104202_ = 1.0F;
         model.f_102814_.f_104203_ = -1.2F;
         model.f_102814_.f_104204_ = -0.3F;
         model.f_102814_.f_104202_ = 1.0F;
      }

   }

   public void heldItem(LivingEntity entity, ItemStack stack, ItemDisplayContext transformType, HumanoidArm handSide, PoseStack matrixStack, MultiBufferSource renderBuffer, int packedLight) {
      double groundDistance = WyHelper.getDifferenceToFloor(entity);
      if (!(entity instanceof AbstractGorillaEntity)) {
         matrixStack.m_85837_(-0.15, -0.2, -0.2);
      }

      if (groundDistance > (double)2.0F) {
         matrixStack.m_252781_(Axis.f_252529_.m_252977_(-55.0F));
      } else {
         matrixStack.m_252781_(Axis.f_252529_.m_252977_(-75.0F));
      }

      matrixStack.m_252781_(Axis.f_252436_.m_252977_(-20.0F));
      matrixStack.m_252781_(Axis.f_252403_.m_252977_(-7.0F));
   }
}
