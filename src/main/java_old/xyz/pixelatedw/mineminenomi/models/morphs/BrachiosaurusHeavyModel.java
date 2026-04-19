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
import xyz.pixelatedw.mineminenomi.api.morph.HumanoidMorphModel;

public class BrachiosaurusHeavyModel<T extends LivingEntity> extends HumanoidMorphModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "brachiosaurus_heavy"), "main");
   private final ModelPart headBase;
   private final ModelPart headBase3_r1;
   private final ModelPart headBase1_r1;
   private final ModelPart neck;
   private final ModelPart neck2;
   private final ModelPart neck3;
   private final ModelPart neck4;
   private final ModelPart neck5;
   private final ModelPart upperMouth;
   private final ModelPart upperTeeth;
   private final ModelPart lowerMouth;
   private final ModelPart lowerTeeth;
   private final ModelPart leftArm2;
   private final ModelPart leftArm3;
   private final ModelPart rightArm2;
   private final ModelPart rightArm3;
   private final ModelPart rightLeg2;
   private final ModelPart rightLeg3;
   private final ModelPart leftLeg2;
   private final ModelPart leftLeg3;
   private final ModelPart tail;
   private final ModelPart tail2;

   public BrachiosaurusHeavyModel(ModelPart root) {
      super(root);
      this.headBase = this.f_102808_.m_171324_("headBase");
      this.headBase3_r1 = this.headBase.m_171324_("headBase3_r1");
      this.headBase1_r1 = this.headBase.m_171324_("headBase1_r1");
      this.neck = this.f_102808_.m_171324_("neck");
      this.neck2 = this.neck.m_171324_("neck2");
      this.neck3 = this.neck.m_171324_("neck3");
      this.neck4 = this.neck.m_171324_("neck4");
      this.neck5 = this.neck.m_171324_("neck5");
      this.upperMouth = this.f_102808_.m_171324_("upperMouth");
      this.upperTeeth = this.upperMouth.m_171324_("upperTeeth");
      this.lowerMouth = this.f_102808_.m_171324_("lowerMouth");
      this.lowerTeeth = this.lowerMouth.m_171324_("lowerTeeth");
      this.leftArm2 = this.f_102812_.m_171324_("leftArm2");
      this.leftArm3 = this.f_102812_.m_171324_("leftArm3");
      this.rightArm2 = this.f_102811_.m_171324_("rightArm2");
      this.rightArm3 = this.f_102811_.m_171324_("rightArm3");
      this.rightLeg2 = this.f_102813_.m_171324_("rightLeg2");
      this.rightLeg3 = this.f_102813_.m_171324_("rightLeg3");
      this.leftLeg2 = this.f_102814_.m_171324_("leftLeg2");
      this.leftLeg3 = this.f_102814_.m_171324_("leftLeg3");
      this.tail = root.m_171324_("tail");
      this.tail2 = this.tail.m_171324_("tail2");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = HumanoidMorphModel.m_170681_(CubeDeformation.f_171458_, 0.0F);
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition head = partdefinition.m_171599_("head", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, -2.0F, -5.75F));
      PartDefinition headBase = head.m_171599_("headBase", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-2.5F, -11.75F, -9.0F, 5.0F, 6.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      headBase.m_171599_("headBase3_r1", CubeListBuilder.m_171558_().m_171514_(0, 60).m_171488_(-2.5F, -3.5F, -1.5728F, 5.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, -8.0F, -9.0F, -0.0873F, 0.0F, 0.0F));
      headBase.m_171599_("headBase1_r1", CubeListBuilder.m_171558_().m_171514_(78, 31).m_171488_(-2.0F, -1.4992F, -2.5824F, 4.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, -8.0392F, -6.2777F, 1.3963F, 0.0F, 0.0F));
      PartDefinition neck = head.m_171599_("neck", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 0.0F, -0.1878F));
      neck.m_171599_("neck2", CubeListBuilder.m_171558_().m_171514_(29, 65).m_171488_(-2.5F, -4.0723F, -2.3627F, 5.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, -0.3992F, 0.1235F, 0.48F, 0.0F, 0.0F));
      neck.m_171599_("neck3", CubeListBuilder.m_171558_().m_171514_(93, 51).m_171488_(-2.0F, -3.7189F, -0.6203F, 4.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, -1.9203F, -2.5618F, 0.5672F, 0.0F, 0.0F));
      neck.m_171599_("neck4", CubeListBuilder.m_171558_().m_171514_(0, 96).m_171488_(-2.0F, -2.8466F, -1.9423F, 4.0F, 3.0F, 4.0F, new CubeDeformation(-0.01F)), PartPose.m_171423_(0.0F, -5.3328F, -2.302F, 0.9599F, 0.0F, 0.0F));
      neck.m_171599_("neck5", CubeListBuilder.m_171558_().m_171514_(92, 23).m_171488_(-2.0F, -2.2568F, -1.8199F, 4.0F, 2.0F, 5.0F, new CubeDeformation(0.01F)), PartPose.m_171423_(0.0F, -6.4513F, -4.0017F, 1.2217F, 0.0F, 0.0F));
      PartDefinition upperMouth = head.m_171599_("upperMouth", CubeListBuilder.m_171558_().m_171514_(0, 35).m_171488_(-2.0F, -2.4731F, -4.5535F, 4.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).m_171514_(0, 30).m_171488_(-2.0F, -2.4731F, -6.5535F, 4.0F, 3.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.m_171423_(0.0F, -7.0F, -8.0F, -0.1309F, 0.0F, 0.0F));
      upperMouth.m_171599_("upperTeeth", CubeListBuilder.m_171558_().m_171514_(0, 3).m_171488_(1.75F, 1.5F, -5.75F, 0.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)).m_171514_(0, 2).m_171488_(-1.75F, 1.5F, -5.75F, 0.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)).m_171514_(0, 13).m_171488_(-2.0F, 1.5F, -5.75F, 4.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, -1.4731F, -0.5535F));
      PartDefinition lowerMouth = head.m_171599_("lowerMouth", CubeListBuilder.m_171558_().m_171514_(0, 45).m_171488_(-1.5F, -0.5F, -3.0F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).m_171514_(0, 40).m_171488_(-2.0F, -0.5F, -1.0F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).m_171514_(0, 10).m_171488_(-2.0F, -0.5F, 1.0F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, -5.3928F, -11.2171F, 0.2182F, 0.0F, 0.0F));
      lowerMouth.m_171599_("lowerTeeth", CubeListBuilder.m_171558_().m_171514_(8, 44).m_171488_(-1.756F, -0.5F, 0.4F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).m_171514_(8, 43).m_171488_(-1.256F, -0.5F, -1.6F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).m_171514_(10, 9).m_171488_(1.764F, -0.5F, 0.4F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).m_171514_(10, 8).m_171488_(1.244F, -0.5F, -1.6F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).m_171514_(8, 13).m_171488_(-1.496F, -0.5F, -1.6F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(-0.004F, -0.4557F, -1.2455F));
      PartDefinition leftArm = partdefinition.m_171599_("left_arm", CubeListBuilder.m_171558_(), PartPose.m_171423_(7.1414F, -1.5F, 0.7938F, 0.0F, 0.0F, -0.2618F));
      leftArm.m_171599_("leftArm2", CubeListBuilder.m_171558_().m_171514_(0, 81).m_171488_(-1.5331F, -4.4646F, -2.0528F, 5.0F, 9.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(1.3917F, 3.4646F, -2.2409F, 0.1745F, 0.0F, 0.0F));
      leftArm.m_171599_("leftArm3", CubeListBuilder.m_171558_().m_171514_(22, 81).m_171488_(-1.1991F, -4.5881F, -2.0528F, 5.0F, 9.0F, 6.0F, new CubeDeformation(-0.01F)), PartPose.m_171423_(1.0578F, 11.3381F, -2.2409F, -0.0873F, 0.0F, 0.0F));
      PartDefinition rightArm = partdefinition.m_171599_("right_arm", CubeListBuilder.m_171558_(), PartPose.m_171423_(-6.8586F, -1.5F, -1.4562F, 0.0F, 0.0F, 0.2618F));
      rightArm.m_171599_("rightArm2", CubeListBuilder.m_171558_().m_171514_(0, 81).m_171488_(-1.1859F, -4.4382F, -1.7573F, 5.0F, 9.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-3.9555F, 3.4382F, -1.0365F, 0.1745F, 0.0F, 0.0F));
      rightArm.m_171599_("rightArm3", CubeListBuilder.m_171558_().m_171514_(22, 81).m_171488_(-0.8519F, -4.5616F, -1.2573F, 5.0F, 9.0F, 6.0F, new CubeDeformation(-0.01F)), PartPose.m_171423_(-4.2895F, 11.3116F, -1.0365F, -0.0873F, 0.0F, 0.0F));
      PartDefinition rightLeg = partdefinition.m_171599_("right_leg", CubeListBuilder.m_171558_().m_171514_(60, 92).m_171488_(-2.0F, 12.0F, -3.25F, 4.0F, 4.0F, 5.0F, new CubeDeformation(0.01F)), PartPose.m_171419_(-6.5F, 8.0F, -1.0F));
      rightLeg.m_171599_("rightLeg2", CubeListBuilder.m_171558_().m_171514_(38, 90).m_171488_(-14.0F, -31.0F, 27.0F, 5.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(11.5F, 27.0F, -35.0F, -0.1745F, 0.0F, 0.0F));
      rightLeg.m_171599_("rightLeg3", CubeListBuilder.m_171558_().m_171514_(92, 13).m_171488_(-13.5F, -29.1483F, 28.6858F, 4.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(11.5F, 41.6483F, -27.1858F, 0.1745F, 0.0F, 0.0F));
      PartDefinition leftLeg = partdefinition.m_171599_("left_leg", CubeListBuilder.m_171558_().m_171514_(60, 92).m_171488_(-2.0F, 12.0F, -3.25F, 4.0F, 4.0F, 5.0F, new CubeDeformation(0.01F)), PartPose.m_171419_(6.5F, 8.0F, -1.0F));
      leftLeg.m_171599_("leftLeg2", CubeListBuilder.m_171558_().m_171514_(38, 90).m_171488_(-14.0F, -31.0F, 27.0F, 5.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(11.5F, 27.0F, -35.0F, -0.1745F, 0.0F, 0.0F));
      leftLeg.m_171599_("leftLeg3", CubeListBuilder.m_171558_().m_171514_(92, 13).m_171488_(-13.5F, -29.1483F, 28.6858F, 4.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(11.5F, 41.6483F, -27.1858F, 0.1745F, 0.0F, 0.0F));
      PartDefinition tail = partdefinition.m_171599_("tail", CubeListBuilder.m_171558_().m_171514_(0, 60).m_171488_(-3.5F, -1.5F, -0.1667F, 7.0F, 6.0F, 15.0F, new CubeDeformation(-0.01F)), PartPose.m_171423_(0.0F, 11.5F, 4.1667F, -0.3491F, 0.0F, 0.0F));
      tail.m_171599_("tail2", CubeListBuilder.m_171558_().m_171514_(39, 41).m_171488_(-2.5F, -1.0F, 0.5F, 5.0F, 5.0F, 19.0F, new CubeDeformation(-0.01F)), PartPose.m_171423_(0.0F, 0.25F, 13.3333F, 0.2182F, 0.0F, 0.0F));
      partdefinition.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-8.5F, -7.8571F, -4.7857F, 17.0F, 16.0F, 14.0F, new CubeDeformation(0.0F)).m_171514_(0, 45).m_171488_(-8.0F, -9.3571F, -4.2857F, 16.0F, 2.0F, 13.0F, new CubeDeformation(0.0F)).m_171514_(50, 18).m_171488_(-7.5F, -10.1071F, -3.5357F, 15.0F, 1.0F, 12.0F, new CubeDeformation(0.0F)).m_171514_(44, 65).m_171488_(-7.0F, -10.8571F, -3.0357F, 14.0F, 1.0F, 11.0F, new CubeDeformation(0.0F)).m_171514_(0, 30).m_171488_(-8.0F, 7.1429F, -4.2857F, 16.0F, 2.0F, 13.0F, new CubeDeformation(0.0F)).m_171514_(48, 0).m_171488_(-7.5F, 8.8929F, -3.7857F, 15.0F, 1.0F, 12.0F, new CubeDeformation(0.0F)).m_171514_(45, 31).m_171488_(-6.0F, -11.8571F, -2.0357F, 12.0F, 1.0F, 9.0F, new CubeDeformation(0.0F)).m_171514_(68, 41).m_171488_(-7.0F, -6.8571F, -5.7857F, 14.0F, 14.0F, 1.0F, new CubeDeformation(0.0F)).m_171514_(44, 77).m_171488_(-6.5F, -5.8571F, -6.5357F, 13.0F, 12.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 5.8571F, -3.2143F));
      return LayerDefinition.m_171565_(meshdefinition, 128, 128);
   }

   public void m_6973_(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      RendererHelper.resetModelToDefaultPivots(this);
      this.setupHeadRotation(entity, headPitch, netHeadYaw);
      float f = 1.0F;
      this.f_102811_.f_104203_ = Mth.m_14089_(limbSwing * 0.6662F + (float)Math.PI) * 0.4F * limbSwingAmount * 0.5F / f;
      this.f_102812_.f_104203_ = Mth.m_14089_(limbSwing * 0.6662F) * 0.4F * limbSwingAmount * 0.5F / f;
      this.f_102813_.f_104203_ = Mth.m_14089_(limbSwing * 0.6662F) * 0.3F * limbSwingAmount / f;
      this.f_102814_.f_104203_ = Mth.m_14089_(limbSwing * 0.6662F + (float)Math.PI) * 0.3F * limbSwingAmount / f;
      if (entity.m_20142_()) {
         this.tail.f_104203_ = Mth.m_14089_(limbSwing * 0.6662F) * 0.2F * limbSwingAmount;
      }

      this.m_7884_(entity, ageInTicks);
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.f_102808_.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.f_102812_.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.f_102811_.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.f_102813_.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.f_102814_.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.tail.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.f_102810_.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }

   public void m_6002_(HumanoidArm side, PoseStack stack) {
      super.m_6002_(side, stack);
      stack.m_85837_((double)0.0F, 0.45, -0.05);
      stack.m_252781_(Axis.f_252403_.m_252977_(side == HumanoidArm.RIGHT ? 5.0F : -5.0F));
   }

   public void renderFirstPersonArm(PoseStack stack, VertexConsumer vertex, int packedLight, int overlay, float red, float green, float blue, float alpha, HumanoidArm side, boolean isLeg) {
      float sideMod = side == HumanoidArm.RIGHT ? 1.0F : -1.0F;
      stack.m_85837_((double)0.5F * (double)sideMod, 0.2, 0.1);
      stack.m_252781_(Axis.f_252529_.m_252977_(20.0F));
      if (isLeg) {
         stack.m_85837_(-0.1 * (double)sideMod, (double)0.0F, (double)-0.5F);
      }

      super.renderFirstPersonArm(stack, vertex, packedLight, overlay, red, green, blue, alpha, side, isLeg);
   }
}
