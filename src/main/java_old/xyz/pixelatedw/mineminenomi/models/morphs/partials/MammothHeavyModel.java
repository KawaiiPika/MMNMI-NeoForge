package xyz.pixelatedw.mineminenomi.models.morphs.partials;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
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

public class MammothHeavyModel<T extends LivingEntity> extends QuadrupedMorphModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "mammoth_heavy"), "main");
   private final ModelPart trunk;
   private final ModelPart trunk2;
   private final ModelPart trunk3;
   private final ModelPart leftTusk;
   private final ModelPart leftTusk2;
   private final ModelPart rightTusk;
   private final ModelPart rightTusk2;
   private final ModelPart lowerBody;
   private final ModelPart body2;
   private final ModelPart body3;
   private final ModelPart tail;
   private final ModelPart tail2;

   public MammothHeavyModel(ModelPart root) {
      super(root);
      this.trunk = this.f_103492_.m_171324_("trunk");
      this.trunk2 = this.trunk.m_171324_("trunk2");
      this.trunk3 = this.trunk2.m_171324_("trunk3");
      this.leftTusk = this.f_103492_.m_171324_("leftTusk");
      this.leftTusk2 = this.leftTusk.m_171324_("leftTusk2");
      this.rightTusk = this.f_103492_.m_171324_("rightTusk");
      this.rightTusk2 = this.rightTusk.m_171324_("rightTusk2");
      this.lowerBody = root.m_171324_("lowerBody");
      this.body2 = this.lowerBody.m_171324_("body2");
      this.body3 = this.body2.m_171324_("body3");
      this.tail = this.body3.m_171324_("tail");
      this.tail2 = this.tail.m_171324_("tail2");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = QuadrupedMorphModel.m_170864_(0, CubeDeformation.f_171458_);
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition upperBody = partdefinition.m_171599_("body", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 6.2546F, -11.9465F));
      upperBody.m_171599_("upperbody1_r1", CubeListBuilder.m_171558_().m_171514_(3, 2).m_171488_(-7.0F, -3.5625F, -7.5F, 14.0F, 17.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, -6.8162F, -0.2802F, 0.3927F, 0.0F, 0.0F));
      PartDefinition head = partdefinition.m_171599_("head", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, -6.55F, -18.0F));
      PartDefinition trunk = head.m_171599_("trunk", CubeListBuilder.m_171558_().m_171514_(15, 31).m_171488_(-2.0F, -3.671F, -2.9673F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.5F)), PartPose.m_171423_(0.0F, 3.0F, -2.5625F, -0.3054F, 0.0F, 0.0F));
      PartDefinition trunk2 = trunk.m_171599_("trunk2", CubeListBuilder.m_171558_().m_171514_(32, 31).m_171488_(-2.0F, -0.875F, -1.625F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.2F)), PartPose.m_171423_(0.0F, 3.602F, -1.3293F, 0.3491F, 0.0F, 0.0F));
      trunk2.m_171599_("trunk3", CubeListBuilder.m_171558_().m_171514_(48, 32).m_171488_(-2.0F, -0.9353F, -1.554F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 6.4876F, -0.0527F, 0.2182F, 0.0F, 0.0F));
      PartDefinition leftTusk = head.m_171599_("leftTusk", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171480_().m_171488_(-1.0151F, -2.6722F, -0.7564F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(2.8172F, 0.2807F, -4.1907F, -1.1781F, -0.2094F, 0.0F));
      leftTusk.m_171599_("leftTusk2", CubeListBuilder.m_171558_().m_171514_(0, 1).m_171480_().m_171488_(-1.0F, -0.0387F, -0.8341F, 2.0F, 7.0F, 2.0F, new CubeDeformation(-0.1F)).m_171555_(false), PartPose.m_171423_(-0.0151F, 4.6636F, 0.1887F, -0.1745F, 0.0F, 0.0F));
      PartDefinition rightTusk = head.m_171599_("rightTusk", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171480_().m_171488_(-0.9849F, -2.6722F, -0.7564F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(-2.8172F, 0.2807F, -4.1907F, -1.1781F, 0.2094F, 0.0F));
      rightTusk.m_171599_("rightTusk2", CubeListBuilder.m_171558_().m_171514_(0, 1).m_171480_().m_171488_(-1.0F, -0.0387F, -0.8341F, 2.0F, 7.0F, 2.0F, new CubeDeformation(-0.1F)).m_171555_(false), PartPose.m_171423_(0.0151F, 4.6636F, 0.1887F, -0.1745F, 0.0F, 0.0F));
      PartDefinition lowerBody = partdefinition.m_171599_("lowerBody", CubeListBuilder.m_171558_().m_171514_(1, 0).m_171488_(-8.0F, -9.0F, -6.5F, 16.0F, 17.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 8.4874F, -8.199F, 0.0873F, 0.0F, 0.0F));
      PartDefinition body2 = lowerBody.m_171599_("body2", CubeListBuilder.m_171558_().m_171514_(3, 2).m_171480_().m_171488_(-7.5F, -8.6873F, -5.4916F, 15.0F, 16.0F, 11.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(0.0F, 0.5488F, 8.615F, -0.0873F, 0.0F, 0.0F));
      PartDefinition body3 = body2.m_171599_("body3", CubeListBuilder.m_171558_().m_171514_(2, 1).m_171488_(-7.1F, -6.754F, 1.6015F, 14.0F, 15.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, -1.625F, 0.0F, -0.0873F, 0.0F, 0.0F));
      PartDefinition tail = body3.m_171599_("tail", CubeListBuilder.m_171558_().m_171514_(0, 32).m_171488_(-0.5F, 0.0F, -0.5F, 1.0F, 9.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, -3.4112F, 14.084F, 0.3491F, 0.0F, 0.0F));
      tail.m_171599_("tail2", CubeListBuilder.m_171558_().m_171514_(5, 31).m_171488_(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 8.0F, 0.0F));
      partdefinition.m_171599_("right_front_leg", CubeListBuilder.m_171558_().m_171514_(0, 38).m_171480_().m_171488_(-2.5F, -0.5F, -2.5F, 5.0F, 10.0F, 5.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171419_(-5.0F, 14.5F, -9.0F));
      partdefinition.m_171599_("left_hind_leg", CubeListBuilder.m_171558_().m_171514_(0, 38).m_171488_(-2.5F, -0.475F, -2.5F, 5.0F, 10.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(3.75F, 14.475F, 10.0F));
      partdefinition.m_171599_("right_hind_leg", CubeListBuilder.m_171558_().m_171514_(0, 38).m_171480_().m_171488_(-2.5F, -0.475F, -2.5F, 5.0F, 10.0F, 5.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171419_(-4.0F, 14.475F, 10.0F));
      partdefinition.m_171599_("left_front_leg", CubeListBuilder.m_171558_().m_171514_(0, 38).m_171488_(-2.5F, -0.5F, -2.5F, 5.0F, 10.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(5.0F, 14.5F, -9.0F));
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      RendererHelper.resetModelToDefaultPivots(this);
      super.m_6973_(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
      this.f_170854_.f_104203_ = Mth.m_14089_(limbSwing * 0.4F) * 0.3F * limbSwingAmount;
      this.f_170855_.f_104203_ = Mth.m_14089_(limbSwing * 0.4F + (float)Math.PI) * 0.3F * limbSwingAmount;
      this.f_170852_.f_104203_ = Mth.m_14089_(limbSwing * 0.4F) * 0.4F * limbSwingAmount;
      this.f_170853_.f_104203_ = Mth.m_14089_(limbSwing * 0.4F + (float)Math.PI) * 0.4F * limbSwingAmount;
      if (entity.m_20142_()) {
         this.tail.f_104203_ = 0.5F + Mth.m_14089_(limbSwing * 0.8F) * 0.1F * limbSwingAmount;
      }

   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.f_103492_.f_104201_ = -8.0F;
      this.leftTusk.f_104201_ = -0.5F;
      this.rightTusk.f_104201_ = -0.5F;
      this.f_103492_.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.f_103493_.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.lowerBody.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.f_170854_.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.f_170853_.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.f_170852_.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.f_170855_.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }

   public void m_6002_(HumanoidArm p_102108_, PoseStack p_102109_) {
   }

   public ModelPart m_5585_() {
      return this.f_103492_;
   }
}
