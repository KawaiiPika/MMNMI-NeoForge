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

public class PteranodonFlyModel<T extends LivingEntity> extends QuadrupedMorphModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "pteranodon_fly"), "main");
   private final ModelPart rightEye;
   private final ModelPart leftEye;
   private final ModelPart horn;
   private final ModelPart horn2;
   private final ModelPart horn3;
   private final ModelPart horn4;
   private final ModelPart horn5;
   private final ModelPart horn6;
   private final ModelPart upperBeak;
   public final ModelPart lowerBeak;
   private final ModelPart neck;
   private final ModelPart rightWingSegment2;
   private final ModelPart rightWingSegment3;
   private final ModelPart leftWingSegment2;
   private final ModelPart leftWingSegment3;
   private final ModelPart rightLowerLeg;
   private final ModelPart rightFoot;
   private final ModelPart rightUpperThight;
   private final ModelPart leftLowerLeg;
   private final ModelPart leftFoot;
   private final ModelPart leftUpperThight;
   private final ModelPart lowerBody;

   public PteranodonFlyModel(ModelPart root) {
      super(root);
      this.rightEye = this.f_103492_.m_171324_("rightEye");
      this.leftEye = this.f_103492_.m_171324_("leftEye");
      this.horn = this.f_103492_.m_171324_("horn");
      this.horn2 = this.horn.m_171324_("horn2");
      this.horn3 = this.horn2.m_171324_("horn3");
      this.horn4 = this.horn3.m_171324_("horn4");
      this.horn5 = this.horn4.m_171324_("horn5");
      this.horn6 = this.horn5.m_171324_("horn6");
      this.upperBeak = this.f_103492_.m_171324_("upperBeak");
      this.lowerBeak = this.f_103492_.m_171324_("lowerBeak");
      this.neck = root.m_171324_("neck");
      this.rightWingSegment2 = this.f_170854_.m_171324_("rightWingSegment2");
      this.rightWingSegment3 = this.rightWingSegment2.m_171324_("rightWingSegment3");
      this.leftWingSegment2 = this.f_170855_.m_171324_("leftWingSegment2");
      this.leftWingSegment3 = this.leftWingSegment2.m_171324_("leftWingSegment3");
      this.rightLowerLeg = this.f_170852_.m_171324_("rightLowerLeg");
      this.rightFoot = this.rightLowerLeg.m_171324_("rightFoot");
      this.rightUpperThight = this.f_170852_.m_171324_("rightUpperThight");
      this.leftLowerLeg = this.f_170853_.m_171324_("leftLowerLeg");
      this.leftFoot = this.leftLowerLeg.m_171324_("leftFoot");
      this.leftUpperThight = this.f_170853_.m_171324_("leftUpperThight");
      this.lowerBody = this.f_103493_.m_171324_("lowerBody");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = QuadrupedMorphModel.m_170864_(0, CubeDeformation.f_171458_);
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition head = partdefinition.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(30, 14).m_171488_(-1.5F, -1.2522F, -5.8546F, 3.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 16.8298F, -10.5783F, 0.0873F, 0.0F, 0.0F));
      head.m_171599_("rightEye", CubeListBuilder.m_171558_().m_171514_(43, 6).m_171480_().m_171488_(0.0F, -1.0F, -1.75F, 0.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171419_(-1.525F, 0.2478F, -1.1046F));
      head.m_171599_("leftEye", CubeListBuilder.m_171558_().m_171514_(43, 6).m_171480_().m_171488_(0.0F, -1.0F, -1.75F, 0.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171419_(1.525F, 0.2478F, -1.1046F));
      PartDefinition horn = head.m_171599_("horn", CubeListBuilder.m_171558_().m_171514_(30, 26).m_171488_(-1.0F, -0.5F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, -0.9656F, -2.4745F, 0.5236F, 0.0F, 0.0F));
      PartDefinition horn2 = horn.m_171599_("horn2", CubeListBuilder.m_171558_().m_171514_(30, 29).m_171488_(-1.0F, -1.6862F, -1.1801F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 0.739F, 2.3141F, 0.3491F, 0.0F, 0.0F));
      PartDefinition horn3 = horn2.m_171599_("horn3", CubeListBuilder.m_171558_().m_171514_(31, 33).m_171488_(-0.5F, -1.1862F, -1.1801F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, -0.4484F, 1.351F));
      PartDefinition horn4 = horn3.m_171599_("horn4", CubeListBuilder.m_171558_().m_171514_(31, 36).m_171488_(-0.5F, 0.0554F, -0.7619F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, -1.304F, 1.4869F, 0.0873F, 0.0F, 0.0F));
      PartDefinition horn5 = horn4.m_171599_("horn5", CubeListBuilder.m_171558_().m_171514_(31, 39).m_171488_(-0.5F, 1.4156F, -0.3558F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, -1.382F, 1.382F, 0.0873F, 0.0F, 0.0F));
      horn5.m_171599_("horn6", CubeListBuilder.m_171558_().m_171514_(31, 42).m_171488_(-0.5F, 2.9544F, 0.1402F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, -1.5115F, 1.1598F, 0.0873F, 0.0F, 0.0F));
      head.m_171599_("upperBeak", CubeListBuilder.m_171558_().m_171514_(54, 0).m_171488_(-1.0F, -1.1625F, -2.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.1F)).m_171514_(38, 0).m_171488_(-0.5F, -1.0625F, -13.0F, 1.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 0.7478F, -5.8546F));
      head.m_171599_("lowerBeak", CubeListBuilder.m_171558_().m_171514_(54, 5).m_171488_(-1.0F, -0.5625F, -2.0F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).m_171514_(38, 14).m_171488_(-0.5F, -0.5625F, -13.0F, 1.0F, 1.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 2.2478F, -5.8546F));
      partdefinition.m_171599_("neck", CubeListBuilder.m_171558_().m_171514_(12, 16).m_171488_(-0.5F, -1.6712F, -7.1834F, 1.0F, 3.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 18.625F, -4.0F, -0.1309F, 0.0F, 0.0F));
      PartDefinition rightWing = partdefinition.m_171599_("right_front_leg", CubeListBuilder.m_171558_().m_171514_(-9, 0).m_171488_(-5.75F, 0.0F, 1.9375F, 6.0F, 0.0F, 9.0F, new CubeDeformation(0.0F)).m_171514_(0, 9).m_171488_(-6.75F, -0.5F, -1.0625F, 7.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).m_171514_(19, 11).m_171488_(-5.75F, -0.5F, 0.9375F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(-2.75F, 18.5F, -2.9375F));
      PartDefinition rightWingSegment2 = rightWing.m_171599_("rightWingSegment2", CubeListBuilder.m_171558_().m_171514_(16, 10).m_171488_(-8.75F, -0.5F, -4.0625F, 9.0F, 1.0F, 2.0F, new CubeDeformation(0.01F)).m_171514_(3, 0).m_171488_(-8.75F, 0.0F, -2.0625F, 9.0F, 0.0F, 9.0F, new CubeDeformation(0.01F)), PartPose.m_171419_(-5.75F, 0.0F, 5.0F));
      rightWingSegment2.m_171599_("rightWingSegment3", CubeListBuilder.m_171558_().m_171514_(0, 12).m_171488_(-6.75F, -0.5F, -3.0625F, 7.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).m_171514_(18, 13).m_171488_(-7.75F, -0.5F, -1.0625F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).m_171514_(22, 0).m_171488_(-7.65F, 0.0F, -1.0625F, 8.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(-8.75F, 0.0F, 0.0F));
      PartDefinition leftWing = partdefinition.m_171599_("left_front_leg", CubeListBuilder.m_171558_().m_171514_(-9, 0).m_171480_().m_171488_(-0.25F, 0.0F, 1.9375F, 6.0F, 0.0F, 9.0F, new CubeDeformation(0.0F)).m_171555_(false).m_171514_(0, 9).m_171480_().m_171488_(-0.25F, -0.5F, -1.0625F, 7.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).m_171555_(false).m_171514_(19, 11).m_171480_().m_171488_(-0.25F, -0.5F, 0.9375F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171419_(2.75F, 18.5F, -2.9375F));
      PartDefinition leftWingSegment2 = leftWing.m_171599_("leftWingSegment2", CubeListBuilder.m_171558_().m_171514_(16, 10).m_171480_().m_171488_(-0.25F, -0.5F, -4.0625F, 9.0F, 1.0F, 2.0F, new CubeDeformation(0.01F)).m_171555_(false).m_171514_(3, 0).m_171480_().m_171488_(-0.25F, 0.0F, -2.0625F, 9.0F, 0.0F, 9.0F, new CubeDeformation(0.01F)).m_171555_(false), PartPose.m_171419_(5.75F, 0.0F, 5.0F));
      leftWingSegment2.m_171599_("leftWingSegment3", CubeListBuilder.m_171558_().m_171514_(0, 12).m_171480_().m_171488_(-0.25F, -0.5F, -3.0625F, 7.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).m_171555_(false).m_171514_(18, 13).m_171480_().m_171488_(2.75F, -0.5F, -1.0625F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).m_171555_(false).m_171514_(22, 0).m_171480_().m_171488_(-0.35F, 0.0F, -1.0625F, 8.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171419_(8.75F, 0.0F, 0.0F));
      PartDefinition rightLeg = partdefinition.m_171599_("right_hind_leg", CubeListBuilder.m_171558_(), PartPose.m_171423_(-3.0F, 20.0F, 13.7F, 1.3526F, 0.0F, 0.0F));
      PartDefinition rightLowerLeg = rightLeg.m_171599_("rightLowerLeg", CubeListBuilder.m_171558_().m_171514_(0, 23).m_171488_(-5.5F, 0.6958F, -20.7223F, 1.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)).m_171514_(0, 15).m_171488_(-5.5F, 0.6958F, -15.7223F, 1.0F, 4.0F, 1.0F, new CubeDeformation(-0.01F)), PartPose.m_171423_(5.0F, 12.1847F, 7.6754F, -1.0472F, 0.0F, 0.0F));
      rightLowerLeg.m_171599_("rightFoot", CubeListBuilder.m_171558_().m_171514_(9, 15).m_171488_(-7.5F, 4.6958F, -15.7354F, 5.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)).m_171514_(0, 31).m_171488_(-6.5F, 3.93F, -15.7223F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      rightLeg.m_171599_("rightUpperThight", CubeListBuilder.m_171558_().m_171514_(0, 15).m_171488_(-6.0F, 18.0963F, -11.8567F, 2.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(5.0F, 13.8689F, 7.5424F, -2.2253F, 0.0F, 0.0F));
      PartDefinition leftLeg = partdefinition.m_171599_("left_hind_leg", CubeListBuilder.m_171558_(), PartPose.m_171423_(3.0F, 20.0F, 13.7F, 1.3526F, 0.0F, 0.0F));
      PartDefinition leftLowerLeg = leftLeg.m_171599_("leftLowerLeg", CubeListBuilder.m_171558_().m_171514_(0, 23).m_171480_().m_171488_(4.5F, 0.6958F, -20.7223F, 1.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)).m_171555_(false).m_171514_(0, 15).m_171480_().m_171488_(4.5F, 0.6958F, -15.7223F, 1.0F, 4.0F, 1.0F, new CubeDeformation(-0.01F)).m_171555_(false), PartPose.m_171423_(-5.0F, 12.1847F, 7.6754F, -1.0472F, 0.0F, 0.0F));
      leftLowerLeg.m_171599_("leftFoot", CubeListBuilder.m_171558_().m_171514_(9, 15).m_171480_().m_171488_(2.5F, 4.6958F, -15.7354F, 5.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)).m_171555_(false).m_171514_(0, 31).m_171480_().m_171488_(3.5F, 3.93F, -15.7223F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      leftLeg.m_171599_("leftUpperThight", CubeListBuilder.m_171558_().m_171514_(0, 15).m_171480_().m_171488_(4.0F, 18.0963F, -11.8567F, 2.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(-5.0F, 13.8689F, 7.5424F, -2.2253F, 0.0F, 0.0F));
      PartDefinition body = partdefinition.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(2, 41).m_171488_(-3.0F, -1.4344F, -2.2227F, 6.0F, 5.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 17.9344F, -2.7773F));
      body.m_171599_("lowerBody", CubeListBuilder.m_171558_().m_171514_(2, 30).m_171488_(-2.5F, -1.3995F, -0.3291F, 5.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)).m_171514_(0, 41).m_171488_(-1.5F, -0.9995F, 6.6709F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 0.2172F, 4.7364F, -0.0873F, 0.0F, 0.0F));
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      RendererHelper.resetModelToDefaultPivots(this);
      if (entity.m_20184_().f_82480_ < -1.7) {
         ModelPart var10000 = this.f_170855_;
         var10000.f_104203_ = (float)((double)var10000.f_104203_ + Math.toRadians((double)-90.0F));
         var10000 = this.f_170855_;
         var10000.f_104204_ = (float)((double)var10000.f_104204_ + Math.toRadians((double)-85.0F));
         var10000 = this.f_170855_;
         var10000.f_104204_ += Mth.m_14089_((float)entity.f_19797_ * 0.9F) / 50.0F;
         var10000 = this.f_170855_;
         var10000.f_104205_ += Mth.m_14089_((float)entity.f_19797_ * 2.9F) / 50.0F;
         var10000 = this.f_170854_;
         var10000.f_104203_ = (float)((double)var10000.f_104203_ + Math.toRadians((double)-90.0F));
         var10000 = this.f_170854_;
         var10000.f_104204_ = (float)((double)var10000.f_104204_ + Math.toRadians((double)85.0F));
         var10000 = this.f_170854_;
         var10000.f_104204_ += Mth.m_14089_((float)entity.f_19797_ * 0.9F) / 50.0F;
         var10000 = this.f_170854_;
         var10000.f_104205_ += Mth.m_14089_((float)entity.f_19797_ * 2.9F) / 50.0F;
      } else {
         this.f_170855_.f_104205_ = Mth.m_14089_(ageInTicks * 0.4F) * 0.5F;
         this.leftWingSegment2.f_104205_ = Mth.m_14089_(ageInTicks * 0.4F) * 0.5F;
         this.leftWingSegment3.f_104205_ = Mth.m_14089_(ageInTicks * 0.4F) * 0.3F;
         this.f_170854_.f_104205_ = Mth.m_14089_(ageInTicks * 0.4F + (float)Math.PI) * 0.5F;
         this.rightWingSegment2.f_104205_ = Mth.m_14089_(ageInTicks * 0.4F + (float)Math.PI) * 0.5F;
         this.rightWingSegment3.f_104205_ = Mth.m_14089_(ageInTicks * 0.4F + (float)Math.PI) * 0.3F;
      }

      this.setupAttackAnimation(entity, ageInTicks);
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.f_103492_.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.neck.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.f_170854_.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.f_170855_.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.f_170852_.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.f_170853_.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.f_103493_.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }

   public void m_6002_(HumanoidArm side, PoseStack poseStack) {
      this.f_103492_.m_104299_(poseStack);
      poseStack.m_85841_(0.7F, 0.7F, 0.7F);
      poseStack.m_252781_(Axis.f_252403_.m_252977_(90.0F));
      poseStack.m_252781_(Axis.f_252529_.m_252977_(260.0F));
      poseStack.m_85837_((double)0.25F, 0.6, 0.2);
   }

   public ModelPart m_5585_() {
      return this.f_103492_;
   }
}
