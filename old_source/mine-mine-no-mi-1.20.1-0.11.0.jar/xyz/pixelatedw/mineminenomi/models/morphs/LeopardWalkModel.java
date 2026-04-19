package xyz.pixelatedw.mineminenomi.models.morphs;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
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

public class LeopardWalkModel<T extends LivingEntity> extends QuadrupedMorphModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "leopard_walk"), "main");
   private final ModelPart lowerLeftFrontLeg;
   private final ModelPart leftFrontPaw;
   private final ModelPart lowerRightFrontLeg;
   private final ModelPart rightFrontPaw;
   private final ModelPart lowerLeftHindLeg;
   private final ModelPart leftHindPaw;
   private final ModelPart lowrRightHindLeg;
   private final ModelPart rightHindPaw;
   private final ModelPart tail;
   private final ModelPart tail2;
   private final ModelPart tail3;
   private final ModelPart leftEye;
   private final ModelPart rightEye;
   private final ModelPart upperMouth;
   private final ModelPart lowerMouth;
   private final ModelPart rightEar;
   private final ModelPart leftEar;

   public LeopardWalkModel(ModelPart root) {
      super(root);
      this.lowerLeftFrontLeg = this.f_170855_.m_171324_("lowerLeftFrontLeg");
      this.leftFrontPaw = this.lowerLeftFrontLeg.m_171324_("leftFrontPaw");
      this.lowerRightFrontLeg = this.f_170854_.m_171324_("lowerRightFrontLeg");
      this.rightFrontPaw = this.lowerRightFrontLeg.m_171324_("rightFrontPaw");
      this.lowerLeftHindLeg = this.f_170853_.m_171324_("lowerLeftHindLeg");
      this.leftHindPaw = this.lowerLeftHindLeg.m_171324_("leftHindPaw");
      this.lowrRightHindLeg = this.f_170852_.m_171324_("lowrRightHindLeg");
      this.rightHindPaw = this.lowrRightHindLeg.m_171324_("rightHindPaw");
      this.tail = this.f_103493_.m_171324_("tail");
      this.tail2 = this.tail.m_171324_("tail2");
      this.tail3 = this.tail2.m_171324_("tail3");
      this.leftEye = this.f_103492_.m_171324_("leftEye");
      this.rightEye = this.f_103492_.m_171324_("rightEye");
      this.upperMouth = this.f_103492_.m_171324_("upperMouth");
      this.lowerMouth = this.f_103492_.m_171324_("lowerMouth");
      this.rightEar = this.f_103492_.m_171324_("rightEar");
      this.leftEar = this.f_103492_.m_171324_("leftEar");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = QuadrupedMorphModel.m_170864_(0, CubeDeformation.f_171458_);
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition leftFrontLeg = partdefinition.m_171599_("left_front_leg", CubeListBuilder.m_171558_().m_171514_(15, 55).m_171488_(-1.4564F, -1.5186F, -1.7829F, 3.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(2.8939F, 15.2061F, -2.9046F, 0.0873F, 0.0015F, -0.0174F));
      PartDefinition lowerLeftFrontLeg = leftFrontLeg.m_171599_("lowerLeftFrontLeg", CubeListBuilder.m_171558_(), PartPose.m_171423_(0.0858F, 3.0291F, -0.1806F, -0.096F, 0.0F, -0.0175F));
      lowerLeftFrontLeg.m_171599_("lowerfrontleftleg1_r1", CubeListBuilder.m_171558_().m_171514_(18, 46).m_171488_(-1.0F, -3.3125F, -2.25F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.2F)), PartPose.m_171423_(0.0102F, 2.5856F, 0.8352F, -0.2269F, 0.0F, -0.0175F));
      lowerLeftFrontLeg.m_171599_("leftFrontPaw", CubeListBuilder.m_171558_().m_171514_(17, 41).m_171488_(-1.0F, -0.5F, -2.5F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.3F)), PartPose.m_171419_(0.0102F, 4.9606F, -0.6023F));
      PartDefinition rightFrontLeg = partdefinition.m_171599_("right_front_leg", CubeListBuilder.m_171558_().m_171514_(0, 55).m_171488_(-1.5436F, -1.5186F, -1.7829F, 3.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-2.8939F, 15.2061F, -2.9046F, 0.0873F, -0.0015F, 0.0174F));
      PartDefinition lowerRightFrontLeg = rightFrontLeg.m_171599_("lowerRightFrontLeg", CubeListBuilder.m_171558_(), PartPose.m_171423_(-0.0858F, 3.0291F, -0.1806F, -0.096F, 0.0F, 0.0175F));
      lowerRightFrontLeg.m_171599_("lowerfrontrightleg1_r1", CubeListBuilder.m_171558_().m_171514_(3, 46).m_171488_(-1.0F, -3.3125F, -2.25F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.2F)), PartPose.m_171423_(-0.0102F, 2.5856F, 0.8352F, -0.2269F, 0.0F, 0.0175F));
      lowerRightFrontLeg.m_171599_("rightFrontPaw", CubeListBuilder.m_171558_().m_171514_(2, 41).m_171488_(-1.0F, -0.5F, -2.5F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.3F)), PartPose.m_171419_(-0.0102F, 4.9606F, -0.6023F));
      PartDefinition leftHindLeg = partdefinition.m_171599_("left_hind_leg", CubeListBuilder.m_171558_().m_171514_(43, 54).m_171488_(-1.3687F, -0.4976F, -2.0755F, 3.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(2.2437F, 13.1851F, 6.0755F, -0.1046F, -0.0055F, -0.0521F));
      PartDefinition lowerLeftHindLeg = leftHindLeg.m_171599_("lowerLeftHindLeg", CubeListBuilder.m_171558_().m_171514_(45, 46).m_171488_(-1.0011F, -0.517F, -0.8984F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.2F)), PartPose.m_171423_(0.1654F, 5.7725F, 0.179F, -0.2264F, 0.0157F, 0.0506F));
      lowerLeftHindLeg.m_171599_("leftHindPaw", CubeListBuilder.m_171558_().m_171514_(44, 41).m_171488_(-1.0011F, -0.1875F, -1.8806F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.3F)), PartPose.m_171423_(0.0184F, 4.2382F, -0.2545F, 0.3316F, 0.0F, 0.0F));
      PartDefinition rightHindLeg = partdefinition.m_171599_("right_hind_leg", CubeListBuilder.m_171558_().m_171514_(30, 54).m_171488_(-1.6313F, -0.4976F, -2.0755F, 3.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-2.2437F, 13.1851F, 6.0755F, -0.1046F, 0.0055F, 0.0521F));
      PartDefinition lowrRightHindLeg = rightHindLeg.m_171599_("lowrRightHindLeg", CubeListBuilder.m_171558_().m_171514_(32, 46).m_171488_(-0.9989F, -0.517F, -0.8984F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.2F)), PartPose.m_171423_(-0.1654F, 5.7725F, 0.179F, -0.2264F, -0.0157F, -0.0506F));
      lowrRightHindLeg.m_171599_("rightHindPaw", CubeListBuilder.m_171558_().m_171514_(31, 41).m_171488_(-0.9989F, -0.1875F, -1.8806F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.3F)), PartPose.m_171423_(-0.0184F, 4.2382F, -0.2545F, 0.3316F, 0.0F, 0.0F));
      PartDefinition body = partdefinition.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(9, 21).m_171488_(-3.0F, -3.4063F, -7.0F, 6.0F, 7.0F, 10.0F, new CubeDeformation(0.0F)).m_171514_(33, 12).m_171488_(-2.5F, -3.0938F, -3.0F, 5.0F, 6.0F, 10.0F, new CubeDeformation(0.2F)), PartPose.m_171419_(-0.0625F, 14.4688F, 1.75F));
      PartDefinition tail = body.m_171599_("tail", CubeListBuilder.m_171558_().m_171514_(3, 0).m_171488_(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(-0.125F, -1.5938F, 6.5625F));
      PartDefinition tail2 = tail.m_171599_("tail2", CubeListBuilder.m_171558_().m_171514_(0, 9).m_171488_(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 7.0F, new CubeDeformation(-0.01F)), PartPose.m_171419_(0.0F, 0.0F, 5.5F));
      tail2.m_171599_("tail3", CubeListBuilder.m_171558_().m_171514_(1, 19).m_171488_(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 0.0F, 6.5F));
      PartDefinition head = partdefinition.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(37, 0).m_171488_(-2.5F, -2.0627F, -5.2758F, 5.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-0.125F, 13.3127F, -5.4117F, 0.1309F, 0.0F, 0.0F));
      head.m_171599_("leftEye", CubeListBuilder.m_171558_().m_171514_(1, 0).m_171480_().m_171488_(-1.0F, -0.5F, 0.0F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171419_(1.4F, -0.3627F, -5.2883F));
      head.m_171599_("rightEye", CubeListBuilder.m_171558_().m_171514_(1, 0).m_171488_(-1.0F, -0.5F, 0.0F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(-1.4F, -0.3627F, -5.2883F));
      head.m_171599_("upperMouth", CubeListBuilder.m_171558_().m_171514_(20, 5).m_171488_(-1.5F, -1.8903F, -2.424F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 1.9146F, -5.2678F, 0.0873F, 0.0F, 0.0F));
      head.m_171599_("lowerMouth", CubeListBuilder.m_171558_().m_171514_(20, 12).m_171488_(-1.5F, -0.1364F, -2.4333F, 3.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 2.1589F, -5.237F, 0.0873F, 0.0F, 0.0F));
      head.m_171599_("rightEar", CubeListBuilder.m_171558_().m_171514_(0, 2).m_171480_().m_171488_(-1.947F, -0.9366F, -0.6477F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(-1.7725F, -1.7151F, -1.7654F, 0.2004F, 0.5236F, -0.0775F));
      head.m_171599_("leftEar", CubeListBuilder.m_171558_().m_171514_(0, 2).m_171488_(-1.053F, -0.9366F, -0.6477F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(2.0225F, -1.7151F, -1.7654F, 0.2004F, -0.5236F, 0.0775F));
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      RendererHelper.resetModelToDefaultPivots(this);
      this.setupHeadRotation(entity, headPitch, netHeadYaw);
      float spread = 0.7F;
      float speed = 0.7F;
      if (entity.m_20142_()) {
         spread = 0.9F;
         speed = 0.9F;
      }

      this.f_170854_.f_104203_ = 0.1F + Mth.m_14089_(limbSwing * speed) * spread * limbSwingAmount;
      this.f_170855_.f_104203_ = 0.1F + Mth.m_14089_(limbSwing * speed) * spread * limbSwingAmount;
      this.f_170852_.f_104203_ = -0.1F + Mth.m_14089_(limbSwing * speed + (float)Math.PI) * spread * limbSwingAmount;
      this.f_170853_.f_104203_ = -0.1F + Mth.m_14089_(limbSwing * speed + (float)Math.PI) * spread * limbSwingAmount;
      ModelPart var10000 = this.tail;
      var10000.f_104204_ = (float)((double)var10000.f_104204_ + Math.sin((double)ageInTicks * 0.06) / (double)5.0F);
      var10000 = this.tail;
      var10000.f_104203_ = (float)((double)var10000.f_104203_ + Math.sin((double)ageInTicks * 0.06) / (double)5.0F);
      var10000 = this.tail2;
      var10000.f_104204_ = (float)((double)var10000.f_104204_ + Math.cos((double)ageInTicks * 0.06) / (double)7.0F);
      var10000 = this.tail2;
      var10000.f_104203_ = (float)((double)var10000.f_104203_ + Math.cos((double)ageInTicks * 0.06) / (double)2.0F);
      var10000 = this.tail3;
      var10000.f_104204_ = (float)((double)var10000.f_104204_ + Math.sin((double)ageInTicks * 0.06) / (double)7.0F);
      var10000 = this.tail3;
      var10000.f_104203_ = (float)((double)var10000.f_104203_ + Math.sin((double)ageInTicks * 0.06) / (double)2.0F);
      if (entity.m_20142_()) {
         this.tail.f_104203_ = (float)Math.sin((double)ageInTicks * 0.6) / 7.0F;
         this.tail.f_104204_ = 0.0F;
         this.tail2.f_104203_ = (float)Math.sin((double)ageInTicks * 0.6) / 10.0F;
         this.tail2.f_104204_ = 0.0F;
         this.tail3.f_104203_ = (float)Math.sin((double)ageInTicks * 0.6) / 12.0F;
         this.tail3.f_104204_ = 0.0F;
      }

      this.setupAttackAnimation(entity, ageInTicks);
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.f_170855_.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.f_170854_.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.f_170853_.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.f_170852_.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.f_103493_.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.f_103492_.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }

   public void m_6002_(HumanoidArm arm, PoseStack poseStack) {
      this.f_103492_.m_104299_(poseStack);
      poseStack.m_252781_(Axis.f_252403_.m_252977_(90.0F));
      poseStack.m_252781_(Axis.f_252529_.m_252977_(260.0F));
      poseStack.m_85837_((double)0.25F, -0.2, (double)0.0F);
   }

   public ModelPart m_5585_() {
      return this.f_103492_;
   }
}
