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

public class GiraffeWalkModel<T extends LivingEntity> extends QuadrupedMorphModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "giraffe_walk"), "main");
   private final ModelPart tail1;
   private final ModelPart hunch;
   private final ModelPart neck2;
   private final ModelPart mane2;
   private final ModelPart face;
   private final ModelPart face2;
   private final ModelPart rightHorn;
   private final ModelPart leftHorn;
   private final ModelPart rightEar;
   private final ModelPart leftEar;
   private final ModelPart mane;
   private final ModelPart leftLowerFrontLeg;
   private final ModelPart leftFrontHoof1;
   private final ModelPart leftFrontHoof2;
   private final ModelPart rightLowerFrontLeg;
   private final ModelPart rightFrontHoof1;
   private final ModelPart rightFrontHoof2;
   private final ModelPart leftLowerHindLeg;
   private final ModelPart leftRearHoof1;
   private final ModelPart leftRearHoof2;
   private final ModelPart rightLowerHindLeg;
   private final ModelPart rightRearHoof1;
   private final ModelPart rightRearHoof2;

   public GiraffeWalkModel(ModelPart root) {
      super(root);
      this.tail1 = this.f_103493_.m_171324_("tail1");
      this.hunch = this.f_103493_.m_171324_("hunch");
      this.neck2 = this.f_103492_.m_171324_("neck2");
      this.mane2 = this.neck2.m_171324_("mane2");
      this.face = this.neck2.m_171324_("face");
      this.face2 = this.face.m_171324_("face2");
      this.rightHorn = this.face2.m_171324_("rightHorn");
      this.leftHorn = this.face2.m_171324_("leftHorn");
      this.rightEar = this.face2.m_171324_("rightEar");
      this.leftEar = this.face2.m_171324_("leftEar");
      this.mane = this.f_103492_.m_171324_("mane");
      this.leftLowerFrontLeg = this.f_170855_.m_171324_("leftLowerFrontLeg");
      this.leftFrontHoof1 = this.leftLowerFrontLeg.m_171324_("leftFrontHoof1");
      this.leftFrontHoof2 = this.leftLowerFrontLeg.m_171324_("leftFrontHoof2");
      this.rightLowerFrontLeg = this.f_170854_.m_171324_("rightLowerFrontLeg");
      this.rightFrontHoof1 = this.rightLowerFrontLeg.m_171324_("rightFrontHoof1");
      this.rightFrontHoof2 = this.rightLowerFrontLeg.m_171324_("rightFrontHoof2");
      this.leftLowerHindLeg = this.f_170853_.m_171324_("leftLowerHindLeg");
      this.leftRearHoof1 = this.leftLowerHindLeg.m_171324_("leftRearHoof1");
      this.leftRearHoof2 = this.leftLowerHindLeg.m_171324_("leftRearHoof2");
      this.rightLowerHindLeg = this.f_170852_.m_171324_("rightLowerHindLeg");
      this.rightRearHoof1 = this.rightLowerHindLeg.m_171324_("rightRearHoof1");
      this.rightRearHoof2 = this.rightLowerHindLeg.m_171324_("rightRearHoof2");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = QuadrupedMorphModel.m_170864_(0, CubeDeformation.f_171458_);
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition body = partdefinition.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(18, 18).m_171488_(-6.0F, -10.4346F, -6.7471F, 10.0F, 16.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(1.0F, 7.0F, 3.0F, 1.4835F, 0.0F, 0.0F));
      body.m_171599_("tail1", CubeListBuilder.m_171558_().m_171514_(58, 28).m_171488_(-0.5F, 0.4821F, -0.5745F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-1.0F, 4.8247F, 1.0142F, -1.0472F, 0.0F, 0.0F));
      body.m_171599_("hunch", CubeListBuilder.m_171558_().m_171514_(32, 45).m_171488_(0.02F, 0.5745F, -0.4821F, 10.0F, 9.0F, 6.0F, new CubeDeformation(-0.02F)), PartPose.m_171423_(-6.02F, -10.75F, 2.25F, -0.5236F, 0.0F, 0.0F));
      PartDefinition head = partdefinition.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-2.0F, -11.9506F, -3.0052F, 4.0F, 11.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 3.75F, -3.25F, 0.7418F, 0.0F, 0.0F));
      PartDefinition neck2 = head.m_171599_("neck2", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171480_().m_171488_(-2.0F, -11.8809F, -2.2082F, 4.0F, 11.0F, 4.0F, new CubeDeformation(-0.01F)).m_171555_(false), PartPose.m_171423_(0.0F, -10.3F, -1.05F, -0.1309F, 0.0F, 0.0F));
      neck2.m_171599_("mane2", CubeListBuilder.m_171558_().m_171514_(14, 17).m_171488_(0.0F, -6.75F, -0.5F, 0.0F, 12.0F, 1.0F, new CubeDeformation(0.01F)), PartPose.m_171419_(0.0F, -6.381F, 2.2919F));
      PartDefinition face = neck2.m_171599_("face", CubeListBuilder.m_171558_().m_171514_(17, 0).m_171488_(-4.0F, -3.25F, -6.0F, 4.0F, 3.0F, 7.0F, new CubeDeformation(0.01F)), PartPose.m_171423_(2.0F, -9.6613F, -1.1778F, -0.6109F, 0.0F, 0.0F));
      PartDefinition face2 = face.m_171599_("face2", CubeListBuilder.m_171558_().m_171514_(18, 11).m_171488_(0.0F, 0.75F, 0.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.01F)), PartPose.m_171419_(-4.0F, -6.0F, -3.0F));
      face2.m_171599_("rightHorn", CubeListBuilder.m_171558_().m_171514_(49, 19).m_171488_(-0.5F, -1.25F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.01F)), PartPose.m_171419_(0.75F, 0.0F, 3.0F));
      face2.m_171599_("leftHorn", CubeListBuilder.m_171558_().m_171514_(49, 19).m_171488_(-0.5F, -1.25F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.01F)), PartPose.m_171419_(3.25F, 0.0F, 3.0F));
      face2.m_171599_("rightEar", CubeListBuilder.m_171558_().m_171514_(49, 23).m_171488_(-2.099F, -0.0129F, 0.0F, 3.0F, 2.0F, 0.0F, new CubeDeformation(0.01F)), PartPose.m_171423_(-0.25F, 1.25F, 3.0F, 0.0F, 0.0F, 0.2618F));
      face2.m_171599_("leftEar", CubeListBuilder.m_171558_().m_171514_(49, 23).m_171480_().m_171488_(-0.4183F, -0.3709F, 0.0F, 3.0F, 2.0F, 0.0F, new CubeDeformation(0.01F)).m_171555_(false), PartPose.m_171423_(4.0F, 1.7F, 3.0F, 0.0F, 0.0F, -0.2618F));
      head.m_171599_("mane", CubeListBuilder.m_171558_().m_171514_(14, 17).m_171488_(0.0F, -5.5F, -0.5F, 0.0F, 11.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, -6.4507F, 1.4949F));
      PartDefinition leftFrontLeg = partdefinition.m_171599_("left_front_leg", CubeListBuilder.m_171558_().m_171514_(0, 34).m_171488_(-1.0F, 0.6375F, -0.8567F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.2F)), PartPose.m_171423_(3.5F, 11.5F, -6.0F, -0.1745F, 0.0F, 0.0F));
      PartDefinition leftLowerFrontLeg = leftFrontLeg.m_171599_("leftLowerFrontLeg", CubeListBuilder.m_171558_().m_171514_(9, 34).m_171488_(-1.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 6.6375F, 0.1433F, 0.1745F, 0.0F, 0.0F));
      leftLowerFrontLeg.m_171599_("leftFrontHoof1", CubeListBuilder.m_171558_().m_171514_(54, 19).m_171488_(0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 4.0F, -1.5F, -0.1211F, -0.4883F, -0.0394F));
      leftLowerFrontLeg.m_171599_("leftFrontHoof2", CubeListBuilder.m_171558_().m_171514_(54, 19).m_171488_(0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-1.0F, 4.0F, -1.0F, -0.1211F, 0.4883F, 0.0394F));
      PartDefinition rightFrontLeg = partdefinition.m_171599_("right_front_leg", CubeListBuilder.m_171558_().m_171514_(0, 34).m_171480_().m_171488_(-1.0F, 0.6375F, -0.8567F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.2F)).m_171555_(false), PartPose.m_171423_(-3.5F, 11.5F, -6.0F, -0.1745F, 0.0F, 0.0F));
      PartDefinition rightLowerFrontLeg = rightFrontLeg.m_171599_("rightLowerFrontLeg", CubeListBuilder.m_171558_().m_171514_(9, 34).m_171480_().m_171488_(-1.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(0.0F, 6.6375F, 0.1433F, 0.1745F, 0.0F, 0.0F));
      rightLowerFrontLeg.m_171599_("rightFrontHoof1", CubeListBuilder.m_171558_().m_171514_(54, 19).m_171480_().m_171488_(-1.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(0.0F, 4.0F, -1.5F, -0.1211F, 0.4883F, 0.0394F));
      rightLowerFrontLeg.m_171599_("rightFrontHoof2", CubeListBuilder.m_171558_().m_171514_(54, 19).m_171480_().m_171488_(-1.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(1.0F, 4.0F, -1.0F, -0.1211F, -0.4883F, -0.0394F));
      PartDefinition leftHindLeg = partdefinition.m_171599_("left_hind_leg", CubeListBuilder.m_171558_().m_171514_(0, 34).m_171488_(-1.0F, 0.2557F, -1.1307F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.25F)), PartPose.m_171423_(3.5F, 13.5F, 6.25F, -0.0873F, 0.0F, 0.0F));
      PartDefinition leftLowerHindLeg = leftHindLeg.m_171599_("leftLowerHindLeg", CubeListBuilder.m_171558_().m_171514_(9, 34).m_171488_(-1.0F, -1.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 5.5057F, -0.1307F, 0.0873F, 0.0F, 0.0F));
      leftLowerHindLeg.m_171599_("leftRearHoof1", CubeListBuilder.m_171558_().m_171514_(54, 19).m_171488_(0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 3.0F, -1.5F, -0.1211F, -0.4883F, -0.0394F));
      leftLowerHindLeg.m_171599_("leftRearHoof2", CubeListBuilder.m_171558_().m_171514_(54, 19).m_171488_(0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-1.0F, 3.0F, -1.0F, -0.1211F, 0.4883F, 0.0394F));
      PartDefinition rightHindLeg = partdefinition.m_171599_("right_hind_leg", CubeListBuilder.m_171558_().m_171514_(0, 34).m_171480_().m_171488_(-1.0F, 0.2557F, -1.1307F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.25F)).m_171555_(false), PartPose.m_171423_(-3.5F, 13.5F, 6.25F, -0.0873F, 0.0F, 0.0F));
      PartDefinition rightLowerHindLeg = rightHindLeg.m_171599_("rightLowerHindLeg", CubeListBuilder.m_171558_().m_171514_(9, 34).m_171480_().m_171488_(-1.0F, -1.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(0.0F, 5.5057F, -0.1307F, 0.0873F, 0.0F, 0.0F));
      rightLowerHindLeg.m_171599_("rightRearHoof1", CubeListBuilder.m_171558_().m_171514_(54, 19).m_171480_().m_171488_(-1.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(0.0F, 3.0F, -1.5F, -0.1211F, 0.4883F, 0.0394F));
      rightLowerHindLeg.m_171599_("rightRearHoof2", CubeListBuilder.m_171558_().m_171514_(54, 19).m_171480_().m_171488_(-1.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(1.0F, 3.0F, -1.0F, -0.1211F, -0.4883F, -0.0394F));
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      RendererHelper.resetModelToDefaultPivots(this);
      this.f_170854_.f_104203_ = -0.15F + Mth.m_14089_(limbSwing * 0.6662F) * 0.3F * limbSwingAmount;
      this.f_170855_.f_104203_ = -0.15F + Mth.m_14089_(limbSwing * 0.6662F) * 0.4F * limbSwingAmount;
      this.f_170852_.f_104203_ = -0.1F + Mth.m_14089_(limbSwing * 0.6662F + (float)Math.PI) * 0.3F * limbSwingAmount;
      this.f_170853_.f_104203_ = -0.1F + Mth.m_14089_(limbSwing * 0.6662F + (float)Math.PI) * 0.4F * limbSwingAmount;
      if (entity.m_20142_()) {
         this.tail1.f_104203_ = 0.2F + Mth.m_14089_(limbSwing * 0.6662F) * 0.2F * limbSwingAmount;
         this.leftEar.f_104204_ = -0.3F - Mth.m_14089_(limbSwing * 0.6662F) * 0.2F * limbSwingAmount;
         this.rightEar.f_104204_ = 0.3F + Mth.m_14089_(limbSwing * 0.6662F) * 0.2F * limbSwingAmount;
      }

      this.setupAttackAnimation(entity, headPitch);
      if (entity.m_6047_()) {
         this.f_103492_.f_104203_ = 0.9F;
         this.f_103492_.f_104202_ = -1.0F;
         this.f_103492_.f_104201_ = 9.0F;
         ModelPart var10000 = this.f_103492_;
         var10000.f_233553_ += 0.2F;
         var10000 = this.f_103492_;
         var10000.f_233554_ -= 0.3F;
         var10000 = this.f_103492_;
         var10000.f_233555_ += 0.2F;
         this.neck2.f_104202_ = -1.2F;
         this.neck2.f_104201_ = -7.0F;
         var10000 = this.face;
         var10000.f_233553_ += 0.2F;
         var10000 = this.face;
         var10000.f_233554_ += 0.3F;
         var10000 = this.face;
         var10000.f_233555_ += 0.2F;
         this.f_103493_.f_104201_ = 11.0F;
         this.f_170854_.f_104201_ = 13.0F;
         this.rightLowerFrontLeg.f_104201_ = 5.0F;
         this.f_170855_.f_104201_ = 13.0F;
         this.leftLowerFrontLeg.f_104201_ = 5.0F;
         this.f_170853_.f_104201_ = 14.0F;
         this.leftLowerHindLeg.f_104201_ = 5.0F;
         this.f_170852_.f_104201_ = 14.0F;
         this.rightLowerHindLeg.f_104201_ = 5.0F;
      }

   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      super.m_7695_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }

   public void m_6002_(HumanoidArm side, PoseStack poseStack) {
      this.f_103492_.m_104299_(poseStack);
      poseStack.m_252781_(Axis.f_252403_.m_252977_(90.0F));
      poseStack.m_252781_(Axis.f_252436_.m_252977_(50.0F));
      poseStack.m_252781_(Axis.f_252529_.m_252977_(270.0F));
      poseStack.m_85837_(-0.7, 0.8, (double)0.0F);
   }

   public ModelPart m_5585_() {
      return this.f_103492_;
   }
}
