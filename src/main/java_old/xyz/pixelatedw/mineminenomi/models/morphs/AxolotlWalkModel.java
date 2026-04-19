package xyz.pixelatedw.mineminenomi.models.morphs;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.QuadrupedModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.api.morph.QuadrupedMorphModel;

public class AxolotlWalkModel<T extends LivingEntity> extends QuadrupedMorphModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "axolotl_walk"), "main");
   private final ModelPart top_gills;
   private final ModelPart left_gills;
   private final ModelPart right_gills;
   private final ModelPart tail;

   public AxolotlWalkModel(ModelPart root) {
      super(root);
      this.top_gills = this.f_103492_.m_171324_("top_gills");
      this.left_gills = this.f_103492_.m_171324_("left_gills");
      this.right_gills = this.f_103492_.m_171324_("right_gills");
      this.tail = root.m_171324_("tail");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = QuadrupedModel.m_170864_(0, CubeDeformation.f_171458_);
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition head = partdefinition.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(0, 1).m_171488_(-4.0F, -3.0F, -5.0F, 8.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 18.0F, -5.0F));
      head.m_171599_("top_gills", CubeListBuilder.m_171558_().m_171514_(3, 37).m_171488_(-4.0F, -3.0F, 0.0F, 8.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, -3.0F, -1.0F));
      head.m_171599_("left_gills", CubeListBuilder.m_171558_().m_171514_(0, 40).m_171488_(-11.0F, -5.0F, 0.0F, 3.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(4.0F, 0.0F, -1.0F));
      head.m_171599_("right_gills", CubeListBuilder.m_171558_().m_171514_(11, 40).m_171488_(8.0F, -5.0F, 0.0F, 3.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(-4.0F, 0.0F, -1.0F));
      partdefinition.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(0, 11).m_171488_(-4.0F, -2.0F, -9.0F, 8.0F, 4.0F, 10.0F, new CubeDeformation(0.0F)).m_171514_(2, 17).m_171488_(0.0F, -3.0F, -8.0F, 0.0F, 5.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 18.0F, 4.0F));
      partdefinition.m_171599_("left_front_leg", CubeListBuilder.m_171558_().m_171514_(2, 13).m_171488_(6.0F, 0.0F, 0.0F, 3.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(-3.5F, 19.0F, -4.0F));
      partdefinition.m_171599_("left_hind_leg", CubeListBuilder.m_171558_().m_171514_(2, 13).m_171488_(6.0F, 0.0F, 0.0F, 3.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(-3.5F, 19.0F, 3.0F));
      partdefinition.m_171599_("right_front_leg", CubeListBuilder.m_171558_().m_171514_(2, 13).m_171488_(-9.0F, 0.0F, 0.0F, 3.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(3.5F, 19.0F, -4.0F));
      partdefinition.m_171599_("right_hind_leg", CubeListBuilder.m_171558_().m_171514_(2, 13).m_171488_(-9.0F, 0.0F, 0.0F, 3.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(3.5F, 19.0F, 3.0F));
      partdefinition.m_171599_("tail", CubeListBuilder.m_171558_().m_171514_(2, 19).m_171488_(0.0F, -3.0F, 0.0F, 0.0F, 5.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 18.0F, 5.0F));
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   public void setupAnim(LivingEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      RendererHelper.resetModelToDefaultPivots(this);
      boolean flag = entity.m_21256_() > 4;
      boolean flag1 = entity.m_6067_();
      this.f_103492_.f_104204_ = netHeadYaw * ((float)Math.PI / 180F);
      if (flag) {
         this.f_103492_.f_104203_ = (-(float)Math.PI / 4F);
      } else if (this.swimAmount > 0.0F) {
         if (flag1) {
            this.f_103492_.f_104203_ = this.rotlerpRad(this.f_103492_.f_104203_, (-(float)Math.PI / 4F), this.swimAmount);
         } else {
            this.f_103492_.f_104203_ = this.rotlerpRad(this.f_103492_.f_104203_, headPitch * ((float)Math.PI / 180F), this.swimAmount);
         }
      } else {
         this.f_103492_.f_104203_ = headPitch * ((float)Math.PI / 180F);
         if ((double)this.f_103492_.f_104203_ > 0.4) {
            this.f_103492_.f_104203_ = 0.4F;
         } else if ((double)this.f_103492_.f_104203_ < -0.4) {
            this.f_103492_.f_104203_ = -0.4F;
         }
      }

      float f = 1.0F;
      this.f_170854_.f_104203_ = Mth.m_14089_(limbSwing * 0.6662F + (float)Math.PI) * 0.8F * limbSwingAmount * 0.5F / f;
      this.f_170852_.f_104203_ = Mth.m_14089_(limbSwing * 0.6662F + (float)Math.PI) * 0.8F * limbSwingAmount * 0.5F / f;
      this.f_170855_.f_104203_ = Mth.m_14089_(limbSwing * 0.6662F) * 0.7F * limbSwingAmount / f;
      this.f_170853_.f_104203_ = Mth.m_14089_(limbSwing * 0.6662F) * 0.7F * limbSwingAmount / f;
      this.tail.f_104204_ = (float)Math.sin((double)ageInTicks * 0.06) / 10.0F;
      this.tail.f_104203_ = (float)Math.sin((double)ageInTicks * 0.02) / 10.0F;
      this.f_102608_ = entity.f_20921_;
      if (this.f_102608_ > 0.0F) {
         ModelPart var10000 = this.f_103492_;
         var10000.f_104204_ += this.f_103493_.f_104204_;
         float f1 = 1.0F - this.f_102608_;
         f1 *= f1;
         f1 *= f1;
         f1 = 1.0F - f1;
         float f2 = Mth.m_14031_(f1 * (float)Math.PI);
         float f3 = Mth.m_14031_(this.f_102608_ * (float)Math.PI) * -(this.f_103492_.f_104203_ - 0.1F) * 0.15F;
         this.f_103492_.f_104203_ = (float)((double)this.f_103492_.f_104203_ - ((double)f2 * (double)1.5F + (double)f3));
         this.f_103492_.f_104205_ = Mth.m_14031_(this.f_102608_ * (float)Math.PI) * -0.4F;
      }

   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      super.m_7695_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.tail.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }

   public void m_6002_(HumanoidArm side, PoseStack poseStack) {
      this.f_103492_.m_104299_(poseStack);
      poseStack.m_252781_(Axis.f_252403_.m_252977_(90.0F));
      poseStack.m_252781_(Axis.f_252529_.m_252977_(260.0F));
      poseStack.m_85837_(0.1, -0.2, -0.03);
   }

   public ModelPart m_5585_() {
      return this.f_103492_;
   }
}
