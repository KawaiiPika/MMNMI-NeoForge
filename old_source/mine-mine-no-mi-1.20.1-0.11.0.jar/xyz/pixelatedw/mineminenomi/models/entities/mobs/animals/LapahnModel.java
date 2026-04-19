package xyz.pixelatedw.mineminenomi.models.entities.mobs.animals;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.AgeableListModel;
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
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.LapahnEntity;

public class LapahnModel<T extends LapahnEntity> extends AgeableListModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "lapahn"), "main");
   private final ModelPart head;
   private final ModelPart leftEar;
   private final ModelPart rightEar;
   private final ModelPart wiskers;
   private final ModelPart body;
   private final ModelPart tail;
   private final ModelPart rightLeg;
   private final ModelPart rightLeg2;
   private final ModelPart rightFoot;
   private final ModelPart leftLeg;
   private final ModelPart leftLeg2;
   private final ModelPart leftFoot;
   private final ModelPart rightArm;
   private final ModelPart rightArm2;
   private final ModelPart leftArm;
   private final ModelPart leftArm2;
   protected float jumpAnimationProgress;

   public LapahnModel(ModelPart root) {
      this.head = root.m_171324_("head");
      this.leftEar = this.head.m_171324_("leftEar");
      this.rightEar = this.head.m_171324_("rightEar");
      this.wiskers = this.head.m_171324_("wiskers");
      this.body = root.m_171324_("body");
      this.tail = this.body.m_171324_("tail");
      this.rightLeg = root.m_171324_("rightLeg");
      this.rightLeg2 = this.rightLeg.m_171324_("rightLeg2");
      this.rightFoot = this.rightLeg2.m_171324_("rightFoot");
      this.leftLeg = root.m_171324_("leftLeg");
      this.leftLeg2 = this.leftLeg.m_171324_("leftLeg2");
      this.leftFoot = this.leftLeg2.m_171324_("leftFoot");
      this.rightArm = root.m_171324_("rightArm");
      this.rightArm2 = this.rightArm.m_171324_("rightArm2");
      this.leftArm = root.m_171324_("leftArm");
      this.leftArm2 = this.leftArm.m_171324_("leftArm2");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition head = partdefinition.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-3.0F, -6.0F, -1.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, -7.0F, 0.0F));
      head.m_171599_("leftEar", CubeListBuilder.m_171558_().m_171514_(25, 0).m_171488_(-0.6541F, -5.8585F, 1.4648F, 2.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(1.7F, -5.5F, 0.0F, 0.0719F, -0.1738F, 0.0887F));
      head.m_171599_("rightEar", CubeListBuilder.m_171558_().m_171514_(25, 0).m_171488_(-1.3459F, -5.8585F, 1.4648F, 2.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-1.7F, -5.5F, 0.0F, 0.0719F, 0.1738F, -0.0887F));
      head.m_171599_("wiskers", CubeListBuilder.m_171558_().m_171514_(26, 13).m_171488_(-4.5F, -6.0F, -1.0F, 9.0F, 9.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, -1.0F, -0.1F));
      PartDefinition body = partdefinition.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(35, 13).m_171488_(-8.5F, 0.0F, -4.0F, 17.0F, 10.0F, 12.0F, new CubeDeformation(0.0F)).m_171514_(35, 36).m_171488_(-8.0F, -6.0F, -3.5F, 16.0F, 6.0F, 11.0F, new CubeDeformation(0.0F)).m_171514_(80, 49).m_171488_(-7.0F, -11.0F, -3.0F, 14.0F, 5.0F, 10.0F, new CubeDeformation(0.0F)).m_171514_(84, 0).m_171488_(-6.5F, -13.0F, -2.5F, 13.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)).m_171514_(35, 0).m_171488_(-8.0F, 10.0F, -3.5F, 16.0F, 1.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 6.0F, 0.0F));
      body.m_171599_("tail", CubeListBuilder.m_171558_().m_171514_(0, 50).m_171488_(-1.0F, -1.209F, 1.989F, 2.0F, 2.0F, 2.0F, new CubeDeformation(1.0F)), PartPose.m_171423_(0.0F, 7.0F, 5.8F, -0.1047F, 0.0F, 0.0F));
      PartDefinition rightLeg = partdefinition.m_171599_("rightLeg", CubeListBuilder.m_171558_().m_171514_(0, 13).m_171488_(-4.0F, -1.5847F, -2.0874F, 8.0F, 6.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-4.8F, 14.9F, -2.2F, -0.2967F, 0.0F, 0.0F));
      PartDefinition rightLeg2 = rightLeg.m_171599_("rightLeg2", CubeListBuilder.m_171558_().m_171514_(0, 29).m_171488_(2.8F, -16.3302F, 2.1988F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-4.8F, 20.5228F, 5.2592F, 0.3316F, 0.0F, 0.0F));
      rightLeg2.m_171599_("rightFoot", CubeListBuilder.m_171558_().m_171514_(0, 37).m_171488_(-2.5F, 0.0692F, -4.9639F, 5.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(4.85F, -13.9053F, 2.0669F, -0.0349F, 0.0F, 0.0F));
      PartDefinition leftLeg = partdefinition.m_171599_("leftLeg", CubeListBuilder.m_171558_().m_171514_(0, 13).m_171488_(-4.0F, -1.5847F, -2.0874F, 8.0F, 6.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(4.8F, 14.9F, -2.2F, -0.2967F, 0.0F, 0.0F));
      PartDefinition leftLeg2 = leftLeg.m_171599_("leftLeg2", CubeListBuilder.m_171558_().m_171514_(0, 29).m_171488_(-2.0F, 0.3198F, -0.0012F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 3.8F, 2.0F, 0.3316F, 0.0F, 0.0F));
      leftLeg2.m_171599_("leftFoot", CubeListBuilder.m_171558_().m_171514_(0, 37).m_171488_(-2.5F, -0.1719F, -5.2225F, 5.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-0.05F, 3.2447F, 0.1169F, -0.0349F, 0.0F, 0.0F));
      PartDefinition rightArm = partdefinition.m_171599_("rightArm", CubeListBuilder.m_171558_().m_171514_(93, 12).m_171488_(-6.0F, -2.0F, -0.5F, 6.0F, 10.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-6.5F, -3.0F, 0.0F, 0.0F, 0.0F, 0.2618F));
      rightArm.m_171599_("rightArm2", CubeListBuilder.m_171558_().m_171514_(93, 29).m_171488_(-3.5F, 0.0F, -0.5F, 6.0F, 9.0F, 6.0F, new CubeDeformation(0.01F)), PartPose.m_171423_(-2.5F, 7.5F, 0.0F, 0.0F, 0.0F, -0.1222F));
      PartDefinition leftArm = partdefinition.m_171599_("leftArm", CubeListBuilder.m_171558_().m_171514_(93, 12).m_171488_(0.0F, -2.0F, -0.5F, 6.0F, 10.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(6.5F, -3.0F, 0.0F, 0.0F, 0.0F, -0.2618F));
      leftArm.m_171599_("leftArm2", CubeListBuilder.m_171558_().m_171514_(93, 29).m_171488_(-2.5F, -1.0F, -0.5F, 6.0F, 9.0F, 6.0F, new CubeDeformation(0.01F)), PartPose.m_171423_(2.5F, 8.5F, 0.0F, 0.0F, 0.0F, 0.1222F));
      return LayerDefinition.m_171565_(meshdefinition, 128, 64);
   }

   public void prepareMobModel(T entity, float limbSwing, float limbSwingAmount, float partialTicks) {
      this.jumpAnimationProgress = entity.getJumpAnimationProgress(partialTicks);
   }

   public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.head.m_171331_().forEach(ModelPart::m_233569_);
      this.body.m_171331_().forEach(ModelPart::m_233569_);
      this.rightArm.m_171331_().forEach(ModelPart::m_233569_);
      this.leftArm.m_171331_().forEach(ModelPart::m_233569_);
      this.rightLeg.m_171331_().forEach(ModelPart::m_233569_);
      this.leftLeg.m_171331_().forEach(ModelPart::m_233569_);
      this.head.f_104204_ = netHeadYaw * ((float)Math.PI / 180F);
      this.head.f_104203_ = headPitch * ((float)Math.PI / 180F);
      if (this.jumpAnimationProgress > 0.0F) {
         this.rightLeg.f_104203_ = Mth.m_14031_(this.jumpAnimationProgress * 2.0F * (float)Math.PI) * 0.8F;
         this.leftLeg.f_104203_ = Mth.m_14031_(this.jumpAnimationProgress * 2.0F * (float)Math.PI) * 0.8F;
      }

      boolean isRunning = entity.isChasing();
      if (isRunning) {
         float swingSpeed = 0.9F;
         float swingAmount = 0.7F;
         this.head.f_104202_ = -19.0F;
         this.head.f_104201_ = 7.0F;
         this.body.f_104201_ = 11.0F;
         this.body.f_104202_ = -4.0F;
         this.body.f_104203_ = (float)Math.toRadians((double)80.0F);
         this.rightArm.f_104202_ = -16.0F;
         this.rightArm.f_104200_ = -3.5F;
         this.rightArm.f_104201_ = 8.0F;
         this.rightArm.f_104203_ = (float)Math.toRadians((double)-20.0F) + Mth.m_14089_(limbSwing * 0.9F + (float)Math.PI) * 0.7F * limbSwingAmount * 0.7F / 1.0F;
         this.leftArm.f_104202_ = -16.0F;
         this.leftArm.f_104200_ = 3.5F;
         this.leftArm.f_104201_ = 8.0F;
         this.leftArm.f_104203_ = (float)Math.toRadians((double)-20.0F) + Mth.m_14089_(limbSwing * 0.9F + (float)Math.PI) * 0.7F * limbSwingAmount * 0.7F / 1.0F;
         this.rightLeg.f_104200_ = -7.0F;
         this.rightLeg.f_104203_ = -Mth.m_14089_(limbSwing * 0.9F + (float)Math.PI) * 0.7F * limbSwingAmount * 0.7F / 1.0F;
         this.leftLeg.f_104200_ = 7.0F;
         this.leftLeg.f_104203_ = -Mth.m_14089_(limbSwing * 0.9F + (float)Math.PI) * 0.7F * limbSwingAmount * 0.7F / 1.0F;
      }

      boolean isSitting = entity.isResting() || this.f_102609_;
      if (isSitting) {
         this.rightLeg.f_104203_ = (float)Math.toRadians((double)-90.0F);
         this.leftLeg.f_104203_ = (float)Math.toRadians((double)-90.0F);
         this.rightLeg.f_104201_ = 10.9F;
         this.leftLeg.f_104201_ = 10.9F;
      }

      if (ageInTicks % 300.0F > 0.0F && ageInTicks % 300.0F < 100.0F) {
         this.leftEar.f_104203_ = 0.1F * (0.3F + Mth.m_14089_(ageInTicks * 0.55F));
      }

   }

   public void setRotationAngle(ModelPart model, float x, float y, float z) {
      model.f_104203_ = x;
      model.f_104204_ = y;
      model.f_104205_ = z;
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.head.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.body.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.rightLeg.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.leftLeg.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.rightArm.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.leftArm.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }

   protected Iterable<ModelPart> m_5607_() {
      return ImmutableList.of(this.head);
   }

   protected Iterable<ModelPart> m_5608_() {
      return ImmutableList.of(this.body, this.rightArm, this.leftArm, this.rightLeg, this.leftLeg);
   }
}
