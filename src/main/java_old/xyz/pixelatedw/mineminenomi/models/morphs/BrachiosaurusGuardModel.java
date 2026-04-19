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

public class BrachiosaurusGuardModel<T extends LivingEntity> extends QuadrupedMorphModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "brachiosaurus_guard"), "main");
   private final ModelPart rightHindLeg2;
   private final ModelPart rightHindLeg3;
   private final ModelPart leftHindLeg2;
   private final ModelPart leftHindLeg3;
   private final ModelPart rightFrontLeg2;
   private final ModelPart rightFrontLeg3;
   private final ModelPart leftFrontLeg2;
   private final ModelPart leftFrontLeg3;
   private final ModelPart tail;
   private final ModelPart tail2;
   private final ModelPart tail3;
   private final ModelPart mouth;
   private final ModelPart upperMouth;
   private final ModelPart upperMouthTeeth;
   private final ModelPart lowerMouth;
   private final ModelPart lowerMouthTeeth;
   private final ModelPart neck;
   private final ModelPart neck2;
   private final ModelPart neck3;
   private final ModelPart neck4;
   private final ModelPart neck5;
   private final ModelPart neck6;
   private final ModelPart neck7;
   private final ModelPart neck8;
   private final ModelPart neck9;

   public BrachiosaurusGuardModel(ModelPart root) {
      super(root);
      this.rightHindLeg2 = this.f_170852_.m_171324_("rightHindLeg2");
      this.rightHindLeg3 = this.f_170852_.m_171324_("rightHindLeg3");
      this.leftHindLeg2 = this.f_170853_.m_171324_("leftHindLeg2");
      this.leftHindLeg3 = this.f_170853_.m_171324_("leftHindLeg3");
      this.rightFrontLeg2 = this.f_170854_.m_171324_("rightFrontLeg2");
      this.rightFrontLeg3 = this.f_170854_.m_171324_("rightFrontLeg3");
      this.leftFrontLeg2 = this.f_170855_.m_171324_("leftFrontLeg2");
      this.leftFrontLeg3 = this.f_170855_.m_171324_("leftFrontLeg3");
      this.tail = this.f_103493_.m_171324_("tail");
      this.tail2 = this.tail.m_171324_("tail2");
      this.tail3 = this.tail2.m_171324_("tail3");
      this.mouth = this.f_103492_.m_171324_("mouth");
      this.upperMouth = this.mouth.m_171324_("upperMouth");
      this.upperMouthTeeth = this.upperMouth.m_171324_("upperMouthTeeth");
      this.lowerMouth = this.mouth.m_171324_("lowerMouth");
      this.lowerMouthTeeth = this.lowerMouth.m_171324_("lowerMouthTeeth");
      this.neck = this.f_103492_.m_171324_("neck");
      this.neck2 = this.neck.m_171324_("neck2");
      this.neck3 = this.neck.m_171324_("neck3");
      this.neck4 = this.neck.m_171324_("neck4");
      this.neck5 = this.neck.m_171324_("neck5");
      this.neck6 = this.neck.m_171324_("neck6");
      this.neck7 = this.neck.m_171324_("neck7");
      this.neck8 = this.neck.m_171324_("neck8");
      this.neck9 = this.neck.m_171324_("neck9");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = QuadrupedMorphModel.m_170864_(0, CubeDeformation.f_171458_);
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition rightHindLeg = partdefinition.m_171599_("right_hind_leg", CubeListBuilder.m_171558_().m_171514_(21, 119).m_171488_(-4.0F, 9.8079F, -2.2404F, 4.0F, 4.0F, 5.0F, new CubeDeformation(-0.01F)), PartPose.m_171419_(-3.0F, 10.3009F, 6.3044F));
      rightHindLeg.m_171599_("rightHindLeg2", CubeListBuilder.m_171558_().m_171514_(22, 92).m_171488_(-2.0F, -3.5F, -3.0F, 4.0F, 7.0F, 6.0F, new CubeDeformation(0.01F)), PartPose.m_171423_(-2.0F, 1.0F, 0.0F, -0.1745F, 0.0F, 0.0F));
      rightHindLeg.m_171599_("rightHindLeg3", CubeListBuilder.m_171558_().m_171514_(21, 106).m_171488_(-2.0F, -3.5F, -2.5F, 4.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-2.0F, 6.9763F, -0.3474F, 0.1745F, 0.0F, 0.0F));
      PartDefinition leftHindLeg = partdefinition.m_171599_("left_hind_leg", CubeListBuilder.m_171558_().m_171514_(21, 119).m_171488_(0.0F, 9.8079F, -2.2404F, 4.0F, 4.0F, 5.0F, new CubeDeformation(-0.01F)), PartPose.m_171419_(3.0F, 10.3009F, 6.3044F));
      leftHindLeg.m_171599_("leftHindLeg2", CubeListBuilder.m_171558_().m_171514_(22, 92).m_171488_(-2.0F, -3.5F, -3.0F, 4.0F, 7.0F, 6.0F, new CubeDeformation(0.01F)), PartPose.m_171423_(2.0F, 1.0F, 0.0F, -0.1745F, 0.0F, 0.0F));
      leftHindLeg.m_171599_("leftHindLeg3", CubeListBuilder.m_171558_().m_171514_(21, 106).m_171488_(-2.0F, -3.5F, -2.5F, 4.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(2.0F, 6.9763F, -0.3474F, 0.1745F, 0.0F, 0.0F));
      PartDefinition rightFrontLeg = partdefinition.m_171599_("right_front_leg", CubeListBuilder.m_171558_().m_171514_(0, 118).m_171488_(-4.0F, 9.9532F, -2.9875F, 4.0F, 4.0F, 5.0F, new CubeDeformation(0.01F)), PartPose.m_171419_(-4.0F, 10.9056F, -7.0765F));
      rightFrontLeg.m_171599_("rightFrontLeg2", CubeListBuilder.m_171558_().m_171514_(0, 91).m_171488_(-1.5F, -3.5F, -3.0F, 4.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-2.5F, 1.0F, 0.0F, 0.1745F, 0.0F, 0.0F));
      rightFrontLeg.m_171599_("rightFrontLeg3", CubeListBuilder.m_171558_().m_171514_(0, 105).m_171488_(-2.0F, -3.5F, -2.5F, 4.0F, 7.0F, 5.0F, new CubeDeformation(0.01F)), PartPose.m_171423_(-2.0F, 6.9404F, 0.0823F, -0.1745F, 0.0F, 0.0F));
      PartDefinition leftFrontLeg = partdefinition.m_171599_("left_front_leg", CubeListBuilder.m_171558_().m_171514_(0, 118).m_171488_(0.0F, 9.9532F, -2.9875F, 4.0F, 4.0F, 5.0F, new CubeDeformation(0.01F)), PartPose.m_171419_(4.0F, 10.9056F, -7.0765F));
      leftFrontLeg.m_171599_("leftFrontLeg2", CubeListBuilder.m_171558_().m_171514_(0, 91).m_171488_(-1.5F, -3.5F, -3.0F, 4.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(1.5F, 1.0F, 0.0F, 0.1745F, 0.0F, 0.0F));
      leftFrontLeg.m_171599_("leftFrontLeg3", CubeListBuilder.m_171558_().m_171514_(0, 105).m_171488_(-2.0F, -3.5F, -2.5F, 4.0F, 7.0F, 5.0F, new CubeDeformation(0.01F)), PartPose.m_171423_(2.0F, 6.9404F, 0.0823F, -0.1745F, 0.0F, 0.0F));
      PartDefinition body = partdefinition.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-5.0F, -8.375F, -4.0F, 10.0F, 17.0F, 14.0F, new CubeDeformation(0.0F)).m_171514_(0, 32).m_171488_(-4.5F, -7.375F, -7.0F, 9.0F, 15.0F, 3.0F, new CubeDeformation(0.0F)).m_171514_(0, 51).m_171488_(-4.0F, -6.875F, -9.0F, 8.0F, 13.0F, 2.0F, new CubeDeformation(0.0F)).m_171514_(50, 0).m_171488_(-4.5F, -7.375F, 9.0F, 9.0F, 15.0F, 3.0F, new CubeDeformation(0.0F)).m_171514_(50, 19).m_171488_(-4.0F, -6.875F, 12.0F, 8.0F, 13.0F, 3.0F, new CubeDeformation(0.0F)).m_171514_(28, 33).m_171488_(-3.5F, -6.375F, 15.0F, 7.0F, 12.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 7.375F, -4.0F));
      PartDefinition tail = body.m_171599_("tail", CubeListBuilder.m_171558_().m_171514_(77, 1).m_171488_(-2.0F, -3.0F, -1.0F, 4.0F, 6.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 0.0F, 18.0F));
      PartDefinition tail2 = tail.m_171599_("tail2", CubeListBuilder.m_171558_().m_171514_(74, 18).m_171488_(-1.0F, -2.0F, 0.0F, 3.0F, 5.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(-0.5F, -0.75F, 6.0F));
      tail2.m_171599_("tail3", CubeListBuilder.m_171558_().m_171514_(72, 38).m_171488_(0.0F, -1.0F, 0.0F, 2.0F, 4.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(-0.5F, -0.75F, 8.0F));
      PartDefinition head = partdefinition.m_171599_("head", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 4.0F, -11.0F));
      PartDefinition mouth = head.m_171599_("mouth", CubeListBuilder.m_171558_(), PartPose.m_171419_(-1.0F, -109.0F, -14.0F));
      PartDefinition upperMouth = mouth.m_171599_("upperMouth", CubeListBuilder.m_171558_().m_171514_(68, 100).m_171488_(-2.5F, -3.0999F, -2.6049F, 5.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)).m_171514_(68, 108).m_171488_(-2.0F, -2.0999F, -3.6049F, 4.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).m_171514_(68, 113).m_171488_(-1.5F, -1.0999F, -5.6049F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(1.0F, 86.1616F, 4.9651F, -0.1309F, 0.0F, 0.0F));
      upperMouth.m_171599_("upperMouthTeeth", CubeListBuilder.m_171558_().m_171514_(68, 118).m_171488_(2.25F, 79.8469F, 18.1993F, 0.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)).m_171514_(68, 118).m_171488_(-0.25F, 79.8469F, 18.1993F, 0.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)).m_171514_(72, 126).m_171488_(0.0F, 79.8469F, 18.1993F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(-1.0F, -79.1968F, -23.5542F));
      PartDefinition lowerMouth = mouth.m_171599_("lowerMouth", CubeListBuilder.m_171558_().m_171514_(85, 104).m_171488_(-2.5F, 0.0434F, -1.3357F, 5.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).m_171514_(85, 109).m_171488_(-2.0F, 0.0434F, -2.3357F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).m_171514_(84, 112).m_171488_(-1.5F, 0.0434F, -5.3357F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(1.0F, 87.2041F, 4.72F, 0.2182F, 0.0F, 0.0F));
      lowerMouth.m_171599_("lowerMouthTeeth", CubeListBuilder.m_171558_().m_171514_(84, 118).m_171488_(6.3F, 139.9865F, 14.3257F, 0.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)).m_171514_(84, 118).m_171488_(3.7F, 139.9865F, 14.3257F, 0.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)).m_171514_(87, 126).m_171488_(4.0F, 139.9865F, 14.3257F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(-5.0F, -140.6931F, -19.4114F));
      PartDefinition neck = head.m_171599_("neck", CubeListBuilder.m_171558_(), PartPose.m_171419_(-0.5F, -1.7431F, -0.7119F));
      neck.m_171599_("neck2", CubeListBuilder.m_171558_().m_171514_(45, 112).m_171488_(-2.0F, -4.0F, -2.5F, 5.0F, 10.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, -1.5359F, -2.0F, 0.5236F, 0.0F, 0.0F));
      neck.m_171599_("neck3", CubeListBuilder.m_171558_().m_171514_(45, 100).m_171488_(-2.0F, -1.25F, -1.75F, 5.0F, 6.0F, 5.0F, new CubeDeformation(-0.01F)), PartPose.m_171423_(0.0F, -8.4641F, -6.0F, 0.3491F, 0.0F, 0.0F));
      neck.m_171599_("neck4", CubeListBuilder.m_171558_().m_171514_(45, 90).m_171488_(-2.0F, -1.0F, -2.5F, 5.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, -11.8793F, -5.6723F, 0.0436F, 0.0F, 0.0F));
      neck.m_171599_("neck5", CubeListBuilder.m_171558_().m_171514_(45, 76).m_171488_(-2.0F, -1.2296F, -3.6273F, 5.0F, 7.0F, 5.0F, new CubeDeformation(0.01F)), PartPose.m_171423_(0.0F, -16.3313F, -2.5418F, -0.4363F, 0.0F, 0.0F));
      neck.m_171599_("neck6", CubeListBuilder.m_171558_().m_171514_(46, 77).m_171488_(-2.5F, -2.5F, -1.4F, 5.0F, 4.0F, 4.0F, new CubeDeformation(-0.01F)), PartPose.m_171419_(0.5F, -18.0554F, -3.3864F));
      neck.m_171599_("neck7", CubeListBuilder.m_171558_().m_171514_(45, 67).m_171488_(-2.5F, -1.5F, -2.5F, 5.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.5F, -20.3554F, -3.6864F, 0.5672F, 0.0F, 0.0F));
      neck.m_171599_("neck8", CubeListBuilder.m_171558_().m_171514_(45, 49).m_171488_(-2.5F, -1.5F, -2.5F, 5.0F, 3.0F, 5.0F, new CubeDeformation(0.017F)), PartPose.m_171423_(0.5F, -21.3674F, -4.847F, 1.1344F, 0.0F, 0.0F));
      neck.m_171599_("neck9", CubeListBuilder.m_171558_().m_171514_(45, 58).m_171488_(-2.5F, -1.5F, -2.5F, 5.0F, 3.0F, 5.0F, new CubeDeformation(0.015F)), PartPose.m_171423_(0.5F, -21.7145F, -6.6078F, 1.6144F, 0.0F, 0.0F));
      return LayerDefinition.m_171565_(meshdefinition, 128, 128);
   }

   public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      RendererHelper.resetModelToDefaultPivots(this);
      float limbSpeed = 0.3F;
      if (entity.m_20142_()) {
         limbSpeed = 0.4F;
      }

      this.f_170854_.f_104203_ = Mth.m_14089_(limbSwing * limbSpeed) * 0.3F * limbSwingAmount;
      this.f_170855_.f_104203_ = Mth.m_14089_(limbSwing * limbSpeed) * 0.4F * limbSwingAmount;
      this.f_170852_.f_104203_ = Mth.m_14089_(limbSwing * limbSpeed + (float)Math.PI) * 0.3F * limbSwingAmount;
      this.f_170853_.f_104203_ = Mth.m_14089_(limbSwing * limbSpeed + (float)Math.PI) * 0.4F * limbSwingAmount;
      ModelPart var10000 = this.tail;
      var10000.f_104204_ = (float)((double)var10000.f_104204_ + Math.sin((double)ageInTicks * 0.01) / (double)20.0F);
      var10000 = this.tail;
      var10000.f_104203_ = (float)((double)var10000.f_104203_ + Math.sin((double)ageInTicks * 0.005) / (double)10.0F);
      var10000 = this.tail2;
      var10000.f_104204_ = (float)((double)var10000.f_104204_ + Math.sin((double)ageInTicks * 0.01) / (double)10.0F);
      var10000 = this.tail2;
      var10000.f_104203_ = (float)((double)var10000.f_104203_ + Math.sin((double)ageInTicks * 0.005) / (double)5.0F);
      var10000 = this.tail3;
      var10000.f_104204_ = (float)((double)var10000.f_104204_ + Math.sin((double)ageInTicks * 0.01) / (double)10.0F);
      var10000 = this.tail3;
      var10000.f_104203_ = (float)((double)var10000.f_104203_ + Math.sin((double)ageInTicks * 0.005) / (double)5.0F);
      this.setupAttackAnimation(entity, ageInTicks);
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.f_170852_.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.f_170853_.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.f_170854_.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.f_170855_.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.f_103493_.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.f_103492_.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }

   public void m_6002_(HumanoidArm arm, PoseStack poseStack) {
      this.f_103492_.m_104299_(poseStack);
      poseStack.m_252781_(Axis.f_252436_.m_252977_(-90.0F));
      poseStack.m_252781_(Axis.f_252403_.m_252977_(90.0F));
      poseStack.m_85837_(-1.3, (double)0.25F, -0.05);
   }

   public ModelPart m_5585_() {
      return this.f_103492_;
   }
}
