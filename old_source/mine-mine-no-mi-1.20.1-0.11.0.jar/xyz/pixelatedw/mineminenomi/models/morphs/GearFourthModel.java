package xyz.pixelatedw.mineminenomi.models.morphs;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.api.morph.HumanoidMorphModel;

public class GearFourthModel<T extends LivingEntity> extends HumanoidMorphModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "gear_fourth"), "main");
   private boolean gomuAnimations = true;

   public GearFourthModel(ModelPart root) {
      super(root);
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = HumanoidMorphModel.m_170681_(CubeDeformation.f_171458_, 0.0F);
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   public void m_7695_(PoseStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      float scale = 1.5F;
      matrixStack.m_85836_();
      matrixStack.m_85841_(scale, scale, scale);
      matrixStack.m_85837_((double)0.0F, -0.8, (double)0.0F);
      this.f_102808_.m_104306_(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
      if (this.gomuAnimations) {
         this.f_102809_.m_104315_(this.f_102808_);
         this.f_102809_.m_104306_(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
      }

      matrixStack.m_85836_();
      matrixStack.m_85841_(2.0F, 1.7F, 3.0F);
      this.f_102810_.m_104306_(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
      matrixStack.m_85849_();
      if (this.gomuAnimations) {
         float time = 0.2F;
         matrixStack.m_85836_();
         matrixStack.m_85841_(1.75F * 5.0F * time, 1.75F * 5.0F * time, 1.75F * 5.0F * time);
      } else {
         matrixStack.m_85836_();
         matrixStack.m_85841_(1.75F, 1.75F, 1.75F);
         matrixStack.m_85837_((double)0.25F, -0.07, (double)0.0F);
      }

      this.f_102811_.m_104306_(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
      matrixStack.m_85849_();
      matrixStack.m_85836_();
      matrixStack.m_85841_(1.75F, 1.75F, 1.75F);
      this.f_102812_.m_104306_(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
      matrixStack.m_85849_();
      this.f_102813_.m_104306_(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
      this.f_102814_.m_104306_(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
      matrixStack.m_85849_();
   }

   public void m_6973_(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      RendererHelper.resetModelToDefaultPivots(this);
      boolean isFlying = Math.sqrt(entity.m_20184_().f_82479_ * entity.m_20184_().f_82479_ + entity.m_20184_().f_82481_ * entity.m_20184_().f_82481_) * (double)entity.f_20902_ > (double)0.05F;
      if (this.gomuAnimations) {
         if (isFlying) {
            this.f_102811_.f_104205_ = (float)Math.toRadians((double)90.0F);
            this.f_102812_.f_104205_ = (float)Math.toRadians((double)-90.0F);
         }

         this.setupHeadRotation(entity, headPitch, netHeadYaw);
         float f = 1.0F;
         if (!isFlying) {
            this.f_102811_.f_104203_ = Mth.m_14089_(limbSwing * 0.6662F + (float)Math.PI) * 0.8F * limbSwingAmount * 0.5F / f;
            this.f_102812_.f_104203_ = Mth.m_14089_(limbSwing * 0.6662F) * 0.8F * limbSwingAmount * 0.5F / f;
         }

         float speed = 0.4F;
         if (entity.m_20142_()) {
            speed = 0.7F;
         }

         ModelPart var10000 = this.f_102813_;
         var10000.f_104201_ += -2.0F + Mth.m_14089_(ageInTicks * speed) * 2.0F;
         var10000 = this.f_102814_;
         var10000.f_104201_ += -2.0F + Mth.m_14089_(ageInTicks * speed) * 2.0F;
         if (!entity.m_21205_().m_41619_()) {
            var10000 = this.f_102811_;
            var10000.f_104203_ += -0.15F;
         }

         this.m_7884_(entity, ageInTicks);
         if (!isFlying) {
            this.f_102811_.f_104203_ = (float)Math.toRadians((double)-90.0F);
            this.f_102812_.f_104203_ = (float)Math.toRadians((double)-90.0F);
            this.f_102812_.f_104205_ = (float)Math.toRadians((double)10.0F);
            this.f_102812_.f_104204_ = (float)Math.toRadians((double)-5.0F);
            var10000 = this.f_102812_;
            var10000.f_104202_ += 3.0F;
         }

         var10000 = this.f_102813_;
         var10000.f_104201_ += 0.5F;
         var10000 = this.f_102814_;
         var10000.f_104201_ += 0.5F;
         var10000 = this.f_102813_;
         var10000.f_104200_ -= 0.5F;
         var10000 = this.f_102814_;
         var10000.f_104200_ += 0.5F;
      } else if (isFlying && entity.m_20142_()) {
         this.f_102811_.f_104205_ = (float)Math.toRadians((double)90.0F);
         this.f_102812_.f_104205_ = (float)Math.toRadians((double)-90.0F);
      }

   }

   public void renderFirstPersonArm(PoseStack matrixStack, VertexConsumer vertex, int packedLight, int overlay, float red, float green, float blue, float alpha, HumanoidArm side, boolean isLeg) {
      if (!isLeg) {
         if (side == HumanoidArm.RIGHT) {
            matrixStack.m_85837_(0.2, 0.3, (double)0.0F);
            this.f_102811_.m_104306_(matrixStack, vertex, packedLight, OverlayTexture.f_118083_, 1.0F, 0.7F, 0.0F, 1.0F);
         } else {
            matrixStack.m_85837_(-0.2, 0.3, (double)0.0F);
            this.f_102812_.m_104306_(matrixStack, vertex, packedLight, OverlayTexture.f_118083_, 1.0F, 0.7F, 0.0F, 1.0F);
         }
      } else if (side == HumanoidArm.RIGHT) {
         matrixStack.m_85837_((double)0.0F, -1.2, 0.3);
         matrixStack.m_85841_(1.5F, 1.5F, 1.5F);
         matrixStack.m_252781_(Axis.f_252436_.m_252977_(-60.0F));
         this.f_102813_.m_104306_(matrixStack, vertex, packedLight, overlay, red, green, blue, alpha);
      } else {
         matrixStack.m_85837_((double)0.0F, -1.2, 0.3);
         matrixStack.m_85841_(1.5F, 1.5F, 1.5F);
         matrixStack.m_252781_(Axis.f_252436_.m_252977_(60.0F));
         this.f_102814_.m_104306_(matrixStack, vertex, packedLight, overlay, red, green, blue, alpha);
      }

   }

   public void m_6002_(HumanoidArm side, PoseStack matrixStack) {
      super.m_6002_(side, matrixStack);
      matrixStack.m_85837_(side == HumanoidArm.RIGHT ? -0.6 : 0.6, (double)-0.5F, -0.2);
   }
}
