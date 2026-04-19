package xyz.pixelatedw.mineminenomi.models.morphs;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.HumanoidModel;
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
import xyz.pixelatedw.mineminenomi.api.morph.HumanoidMorphModel;

public class GiraffeHeavyModel<T extends LivingEntity> extends HumanoidMorphModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "giraffe_heavy"), "main");
   private final ModelPart neck;
   private final ModelPart face;
   private final ModelPart rightEar;
   private final ModelPart mouth;
   private final ModelPart leftHorn;
   private final ModelPart rightHorn;
   private final ModelPart leftEar;
   private final ModelPart mane;
   private final ModelPart mane2;
   private final ModelPart leftShoulder;
   private final ModelPart rightShoulder;
   private final ModelPart rightArm2;
   private final ModelPart rightHand2;
   private final ModelPart rightHand1;
   private final ModelPart leftArm2;
   private final ModelPart leftHand2;
   private final ModelPart leftHand1;
   private final ModelPart rightLeg3;
   private final ModelPart rightLeg2;
   private final ModelPart rightHoof;
   private final ModelPart rightHoof2;
   private final ModelPart rightHoof3;
   private final ModelPart tail;
   private final ModelPart tail2;
   private final ModelPart tail3;
   private final ModelPart leftLeg2;
   private final ModelPart leftLeg3;
   private final ModelPart leftHoof;
   private final ModelPart leftHoof2;
   private final ModelPart leftHoof3;

   public GiraffeHeavyModel(ModelPart root) {
      super(root);
      this.neck = this.f_102808_.m_171324_("neck");
      this.face = this.neck.m_171324_("face");
      this.rightEar = this.face.m_171324_("rightEar");
      this.mouth = this.face.m_171324_("mouth");
      this.leftHorn = this.face.m_171324_("leftHorn");
      this.rightHorn = this.face.m_171324_("rightHorn");
      this.leftEar = this.face.m_171324_("leftEar");
      this.mane = this.neck.m_171324_("mane");
      this.mane2 = this.f_102808_.m_171324_("mane2");
      this.leftShoulder = this.f_102810_.m_171324_("leftShoulder");
      this.rightShoulder = this.f_102810_.m_171324_("rightShoulder");
      this.rightArm2 = this.f_102811_.m_171324_("rightArm2");
      this.rightHand2 = this.rightArm2.m_171324_("rightHand2");
      this.rightHand1 = this.rightArm2.m_171324_("rightHand1");
      this.leftArm2 = this.f_102812_.m_171324_("leftArm2");
      this.leftHand2 = this.leftArm2.m_171324_("leftHand2");
      this.leftHand1 = this.leftArm2.m_171324_("leftHand1");
      this.rightLeg3 = this.f_102813_.m_171324_("rightLeg3");
      this.rightLeg2 = this.rightLeg3.m_171324_("rightLeg2");
      this.rightHoof = this.rightLeg2.m_171324_("rightHoof");
      this.rightHoof2 = this.rightHoof.m_171324_("rightHoof2");
      this.rightHoof3 = this.rightHoof.m_171324_("rightHoof3");
      this.tail = root.m_171324_("tail");
      this.tail2 = this.tail.m_171324_("tail2");
      this.tail3 = this.tail2.m_171324_("tail3");
      this.leftLeg2 = this.f_102814_.m_171324_("leftLeg2");
      this.leftLeg3 = this.leftLeg2.m_171324_("leftLeg3");
      this.leftHoof = this.leftLeg3.m_171324_("leftHoof");
      this.leftHoof2 = this.leftHoof.m_171324_("leftHoof2");
      this.leftHoof3 = this.leftHoof.m_171324_("leftHoof3");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = HumanoidModel.m_170681_(CubeDeformation.f_171458_, 0.0F);
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition head = partdefinition.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(48, 33).m_171480_().m_171488_(0.5F, 0.75F, 0.5F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.5F)).m_171555_(false), PartPose.m_171423_(-2.0F, -11.0F, -3.5F, 0.1745F, 0.0F, 0.0F));
      PartDefinition neck = head.m_171599_("neck", CubeListBuilder.m_171558_().m_171514_(48, 33).m_171488_(0.0F, 0.0F, 0.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.5F, -7.0F, -0.5F, 0.1396F, 0.0F, 0.0F));
      PartDefinition face = neck.m_171599_("face", CubeListBuilder.m_171558_().m_171514_(32, 18).m_171488_(-2.0F, -4.0F, -6.0F, 4.0F, 3.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(2.0F, 0.5F, 1.5F, -0.3142F, 0.0F, 0.0F));
      face.m_171599_("rightEar", CubeListBuilder.m_171558_().m_171514_(32, 0).m_171488_(-3.0F, 0.0F, 0.0F, 3.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-2.0F, -3.5F, 0.0F, 0.0F, 0.0F, -0.2618F));
      face.m_171599_("mouth", CubeListBuilder.m_171558_().m_171514_(31, 29).m_171488_(0.01F, 0.0F, -4.0F, 4.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(-2.0F, -1.0F, 0.5F));
      face.m_171599_("leftHorn", CubeListBuilder.m_171558_().m_171514_(39, 13).m_171488_(0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.5F, -6.0F, -0.5F));
      face.m_171599_("rightHorn", CubeListBuilder.m_171558_().m_171514_(39, 13).m_171488_(0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(-1.5F, -6.0F, -0.5F));
      face.m_171599_("leftEar", CubeListBuilder.m_171558_().m_171514_(32, 0).m_171480_().m_171488_(0.0F, 0.0F, 0.0F, 3.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(2.0F, -3.5F, 0.0F, 0.0F, 0.0F, 0.2618F));
      neck.m_171599_("mane", CubeListBuilder.m_171558_().m_171514_(33, 37).m_171488_(0.0F, 0.0F, 0.2F, 0.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(2.0F, -2.0F, 3.5F, 0.0349F, 0.0F, 0.0F));
      head.m_171599_("mane2", CubeListBuilder.m_171558_().m_171514_(33, 37).m_171488_(0.0F, 0.0F, 0.0F, 0.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(2.5F, 0.0F, 4.6F, 0.0175F, 0.0F, 0.0F));
      PartDefinition body = partdefinition.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-4.0F, 0.0F, -2.0F, 10.0F, 16.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(-0.5F, -4.0F, 0.0F));
      body.m_171599_("leftShoulder", CubeListBuilder.m_171558_().m_171514_(36, 0).m_171480_().m_171488_(0.0F, 0.0F, -0.1F, 9.0F, 7.0F, 5.0F, new CubeDeformation(-0.01F)).m_171555_(false), PartPose.m_171423_(-0.5F, 7.5F, -1.9F, 0.0F, 0.0F, -0.9599F));
      body.m_171599_("rightShoulder", CubeListBuilder.m_171558_().m_171514_(36, 0).m_171488_(0.0F, -7.5F, -0.09F, 9.0F, 7.0F, 5.0F, new CubeDeformation(-0.01F)), PartPose.m_171423_(2.5F, 7.4F, -1.91F, 0.0F, 0.0F, -2.2689F));
      PartDefinition rightArm = partdefinition.m_171599_("right_arm", CubeListBuilder.m_171558_().m_171514_(23, 30).m_171488_(0.0F, 0.0F, 0.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-8.0F, 1.0F, -1.0F, 0.0F, 0.0F, 0.2793F));
      PartDefinition rightArm2 = rightArm.m_171599_("rightArm2", CubeListBuilder.m_171558_().m_171514_(23, 39).m_171488_(0.0F, 0.0F, 0.1F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 6.0F, 0.0F, 0.0F, 0.0F, -0.4189F));
      rightArm2.m_171599_("rightHand2", CubeListBuilder.m_171558_().m_171514_(27, 2).m_171488_(0.0F, -0.0868F, -0.4924F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(1.1392F, 5.1097F, 0.8F, 0.1745F, 0.0F, -0.3491F));
      rightArm2.m_171599_("rightHand1", CubeListBuilder.m_171558_().m_171514_(26, 2).m_171488_(-0.1F, 0.0F, 0.1F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.01F)), PartPose.m_171423_(0.0F, 5.5F, 2.0F, 0.1745F, 1.5708F, 0.1396F));
      PartDefinition leftArm = partdefinition.m_171599_("left_arm", CubeListBuilder.m_171558_().m_171514_(23, 30).m_171488_(-2.0F, 0.0F, 0.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(9.0F, 1.0F, -1.0F, 0.0F, 0.0F, -0.2793F));
      PartDefinition leftArm2 = leftArm.m_171599_("leftArm2", CubeListBuilder.m_171558_().m_171514_(23, 39).m_171488_(-2.0F, 0.0F, 0.1F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 6.0F, 0.0F, 0.0F, 0.0F, 0.4189F));
      leftArm2.m_171599_("leftHand2", CubeListBuilder.m_171558_().m_171514_(27, 2).m_171488_(5.9F, -0.07F, -1.4F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-7.4075F, 2.7019F, 1.8F, 0.1745F, 0.0F, 0.3491F));
      leftArm2.m_171599_("leftHand1", CubeListBuilder.m_171558_().m_171514_(26, 2).m_171488_(-1.4F, 2.6065F, 1.9F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.01F)), PartPose.m_171423_(1.8929F, 2.9691F, 1.5F, 0.1745F, -1.5708F, -0.1396F));
      PartDefinition rightLeg = partdefinition.m_171599_("right_leg", CubeListBuilder.m_171558_().m_171514_(10, 30).m_171488_(-2.0F, 0.0F, -2.0F, 3.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-2.0F, 11.6F, 1.0F, -0.3491F, 0.0F, 0.0F));
      PartDefinition rightLeg3 = rightLeg.m_171599_("rightLeg3", CubeListBuilder.m_171558_().m_171514_(10, 41).m_171488_(-2.0F, 0.0F, -2.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.5F, 5.0F, 0.0F, 1.7453F, 0.0F, 0.0F));
      PartDefinition rightLeg2 = rightLeg3.m_171599_("rightLeg2", CubeListBuilder.m_171558_().m_171514_(0, 30).m_171488_(-2.0F, 0.0F, -2.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.m_171423_(0.0F, 5.2065F, -0.5747F, -1.9199F, 0.0F, 0.0F));
      PartDefinition rightHoof = rightLeg2.m_171599_("rightHoof", CubeListBuilder.m_171558_().m_171514_(25, 1).m_171488_(-2.0F, 0.0F, -2.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 5.0F, -0.5F, 0.5236F, 0.0F, 0.0F));
      rightHoof.m_171599_("rightHoof2", CubeListBuilder.m_171558_().m_171514_(27, 2).m_171488_(0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-1.0F, 0.0F, -2.5F, -0.1211F, -0.4883F, -0.0394F));
      rightHoof.m_171599_("rightHoof3", CubeListBuilder.m_171558_().m_171514_(27, 2).m_171488_(0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-2.0F, 0.0F, -2.0F, -0.1211F, 0.4883F, 0.0394F));
      PartDefinition tail = partdefinition.m_171599_("tail", CubeListBuilder.m_171558_().m_171514_(30, 12).m_171488_(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 10.0F, 3.0F, -0.733F, 0.0F, 0.0F));
      PartDefinition tail2 = tail.m_171599_("tail2", CubeListBuilder.m_171558_().m_171514_(30, 12).m_171488_(0.0F, 0.15F, 0.0F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.01F)), PartPose.m_171423_(0.0F, 0.0F, 4.5F, 0.4712F, 0.0F, 0.0F));
      tail2.m_171599_("tail3", CubeListBuilder.m_171558_().m_171514_(43, 13).m_171488_(0.0F, -0.8803F, 0.1871F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-0.5F, 0.5422F, 4.4176F, 0.2967F, 0.0F, 0.0F));
      PartDefinition leftLeg = partdefinition.m_171599_("left_leg", CubeListBuilder.m_171558_().m_171514_(10, 30).m_171488_(-2.0F, 0.0F, -2.0F, 3.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(4.0F, 11.6F, 1.0F, -0.3491F, 0.0F, 0.0F));
      PartDefinition leftLeg2 = leftLeg.m_171599_("leftLeg2", CubeListBuilder.m_171558_().m_171514_(10, 41).m_171488_(-2.0F, 0.0F, -2.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.5F, 5.0F, 0.0F, 1.7453F, 0.0F, 0.0F));
      PartDefinition leftLeg3 = leftLeg2.m_171599_("leftLeg3", CubeListBuilder.m_171558_().m_171514_(0, 30).m_171488_(-2.0F, 0.0F, -2.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.m_171423_(0.0F, 5.2065F, -0.5747F, -1.9199F, 0.0F, 0.0F));
      PartDefinition leftHoof = leftLeg3.m_171599_("leftHoof", CubeListBuilder.m_171558_().m_171514_(25, 1).m_171488_(-2.0F, 0.0F, -2.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 5.0F, -0.5F, 0.5236F, 0.0F, 0.0F));
      leftHoof.m_171599_("leftHoof2", CubeListBuilder.m_171558_().m_171514_(27, 2).m_171488_(0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-1.0F, 0.0F, -2.5F, -0.1211F, -0.4883F, -0.0394F));
      leftHoof.m_171599_("leftHoof3", CubeListBuilder.m_171558_().m_171514_(27, 2).m_171488_(0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-2.0F, 0.0F, -2.0F, -0.1211F, 0.4883F, 0.0394F));
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   public void m_6973_(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      RendererHelper.resetModelToDefaultPivots(this);
      this.setupHeadRotation(entity, headPitch, netHeadYaw);
      float f = 1.0F;
      this.f_102811_.f_104203_ = Mth.m_14089_(limbSwing * 0.6662F + (float)Math.PI) * 0.8F * limbSwingAmount * 0.5F / f;
      this.f_102812_.f_104203_ = Mth.m_14089_(limbSwing * 0.6662F) * 0.8F * limbSwingAmount * 0.5F / f;
      this.f_102813_.f_104203_ = -0.34F + Mth.m_14089_(limbSwing * 0.6662F) * 0.7F * limbSwingAmount / f;
      this.f_102814_.f_104203_ = -0.34F + Mth.m_14089_(limbSwing * 0.6662F + (float)Math.PI) * 0.7F * limbSwingAmount / f;
      if (entity.m_20142_()) {
         this.tail.f_104203_ = Mth.m_14089_(limbSwing * 0.6662F) * 0.2F * limbSwingAmount;
         this.leftEar.f_104204_ = -0.3F - Mth.m_14089_(limbSwing * 0.6662F) * 0.2F * limbSwingAmount;
         this.rightEar.f_104204_ = 0.3F + Mth.m_14089_(limbSwing * 0.6662F) * 0.2F * limbSwingAmount;
      }

      this.m_7884_(entity, ageInTicks);
      if (entity.m_6047_()) {
         this.f_102808_.f_104203_ = 0.35F;
         this.f_102808_.f_104202_ = -8.0F;
         this.f_102808_.f_104201_ = -5.0F;
         ModelPart var10000 = this.f_102808_;
         var10000.f_233553_ += 0.2F;
         var10000 = this.f_102808_;
         var10000.f_233554_ -= 0.3F;
         var10000 = this.f_102808_;
         var10000.f_233555_ += 0.2F;
         this.neck.f_104201_ = -3.0F;
         var10000 = this.face;
         var10000.f_233553_ -= 0.2F;
         var10000 = this.face;
         var10000.f_233554_ += 0.3F;
         var10000 = this.face;
         var10000.f_233555_ -= 0.2F;
         this.f_102810_.f_104203_ = 0.25F;
         this.f_102810_.f_104202_ = -4.0F;
         this.f_102810_.f_104201_ = -1.0F;
         this.f_102811_.f_104203_ = 0.25F;
         this.f_102811_.f_104202_ = -3.0F;
         this.f_102811_.f_104201_ = 4.0F;
         this.f_102812_.f_104203_ = 0.25F;
         this.f_102812_.f_104202_ = -3.0F;
         this.f_102812_.f_104201_ = 4.0F;
      }

   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      super.m_7695_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.tail.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }

   public void m_6002_(HumanoidArm side, PoseStack poseStack) {
      super.m_6002_(side, poseStack);
      poseStack.m_85837_(side == HumanoidArm.RIGHT ? 0.12 : -0.12, 0.2, 0.1);
      poseStack.m_252781_(Axis.f_252403_.m_252977_(side == HumanoidArm.RIGHT ? -20.0F : 20.0F));
   }

   public void renderFirstPersonArm(PoseStack stack, VertexConsumer vertex, int packedLight, int overlay, float red, float green, float blue, float alpha, HumanoidArm side, boolean isLeg) {
      float sideMod = side == HumanoidArm.RIGHT ? 1.0F : -1.0F;
      stack.m_85837_(-0.2 * (double)sideMod, -0.1, -0.3);
      stack.m_252781_(Axis.f_252529_.m_252977_(20.0F));
      if (isLeg) {
         stack.m_85837_(0.2 * (double)sideMod, (double)0.0F, (double)0.0F);
      }

      super.renderFirstPersonArm(stack, vertex, packedLight, overlay, red, green, blue, alpha, side, isLeg);
   }
}
