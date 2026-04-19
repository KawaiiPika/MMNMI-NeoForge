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

public class SaiWalkModel<T extends LivingEntity> extends QuadrupedMorphModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "sai_walk"), "main");
   private final ModelPart back;
   private final ModelPart tail;
   private final ModelPart tail2;
   private final ModelPart tail3;
   private final ModelPart lowerbackleftleg;
   private final ModelPart backleftfoot;
   private final ModelPart lowerbackrightleg;
   private final ModelPart backrightfoot;
   private final ModelPart leftLowerFrontLeg;
   private final ModelPart leftFoot;
   private final ModelPart rightLowerFrontLeg;
   private final ModelPart rightFoot;
   private final ModelPart head2;
   private final ModelPart leftEar;
   private final ModelPart rightEar;
   private final ModelPart mouth;
   private final ModelPart frontHorn;
   private final ModelPart frontUpperHorn;
   private final ModelPart backHorn;
   private final ModelPart backUpperHorn;

   public SaiWalkModel(ModelPart root) {
      super(root);
      this.back = this.f_103493_.m_171324_("back");
      this.tail = this.back.m_171324_("tail");
      this.tail2 = this.tail.m_171324_("tail2");
      this.tail3 = this.tail2.m_171324_("tail3");
      this.lowerbackleftleg = this.f_170853_.m_171324_("lowerbackleftleg");
      this.backleftfoot = this.lowerbackleftleg.m_171324_("backleftfoot");
      this.lowerbackrightleg = this.f_170852_.m_171324_("lowerbackrightleg");
      this.backrightfoot = this.lowerbackrightleg.m_171324_("backrightfoot");
      this.leftLowerFrontLeg = this.f_170855_.m_171324_("leftLowerFrontLeg");
      this.leftFoot = this.leftLowerFrontLeg.m_171324_("leftFoot");
      this.rightLowerFrontLeg = this.f_170854_.m_171324_("rightLowerFrontLeg");
      this.rightFoot = this.rightLowerFrontLeg.m_171324_("rightFoot");
      this.head2 = this.f_103492_.m_171324_("head2");
      this.leftEar = this.head2.m_171324_("leftEar");
      this.rightEar = this.head2.m_171324_("rightEar");
      this.mouth = this.head2.m_171324_("mouth");
      this.frontHorn = this.mouth.m_171324_("frontHorn");
      this.frontUpperHorn = this.frontHorn.m_171324_("frontUpperHorn");
      this.backHorn = this.mouth.m_171324_("backHorn");
      this.backUpperHorn = this.backHorn.m_171324_("backUpperHorn");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = QuadrupedModel.m_170864_(0, CubeDeformation.f_171458_);
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition body = partdefinition.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-6.5F, -4.1649F, -2.8546F, 13.0F, 10.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 10.5525F, -5.7809F, -0.0873F, 0.0F, 0.0F));
      PartDefinition back = body.m_171599_("back", CubeListBuilder.m_171558_().m_171514_(0, 21).m_171488_(-5.5F, -5.1071F, -1.6826F, 11.0F, 9.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 1.6509F, 7.9088F, 0.0873F, 0.0F, 0.0F));
      PartDefinition tail = back.m_171599_("tail", CubeListBuilder.m_171558_().m_171514_(0, 22).m_171488_(-1.0F, -0.2701F, -0.5734F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, -4.3698F, 8.7659F, 0.2618F, 0.0F, 0.0F));
      PartDefinition tail2 = tail.m_171599_("tail2", CubeListBuilder.m_171558_().m_171514_(0, 22).m_171488_(-1.0F, 2.7299F, 0.4266F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 0.0F, -1.0F));
      tail2.m_171599_("tail3", CubeListBuilder.m_171558_().m_171514_(0, 26).m_171488_(-1.0F, 5.7299F, 0.4266F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      PartDefinition leftHindLeg = partdefinition.m_171599_("left_hind_leg", CubeListBuilder.m_171558_().m_171514_(49, 0).m_171488_(-2.5F, -1.25F, -3.0F, 5.0F, 10.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(4.0F, 10.8132F, 7.4987F, 0.0873F, 0.0F, 0.0F));
      PartDefinition lowerbackleftleg = leftHindLeg.m_171599_("lowerbackleftleg", CubeListBuilder.m_171558_().m_171514_(48, 17).m_171488_(-2.0F, -0.9317F, -2.1749F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.01F)), PartPose.m_171423_(0.0F, 8.6102F, 0.1315F, -0.1309F, 0.0F, 0.0F));
      lowerbackleftleg.m_171599_("backleftfoot", CubeListBuilder.m_171558_().m_171514_(66, 16).m_171488_(-2.0F, -0.5625F, -2.75F, 4.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 3.2399F, -0.7598F, 0.0436F, 0.0F, 0.0F));
      PartDefinition rightHindLeg = partdefinition.m_171599_("right_hind_leg", CubeListBuilder.m_171558_().m_171514_(49, 0).m_171480_().m_171488_(-2.5F, -1.25F, -3.0F, 5.0F, 10.0F, 6.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(-4.0F, 10.8132F, 7.4987F, 0.0873F, 0.0F, 0.0F));
      PartDefinition lowerbackrightleg = rightHindLeg.m_171599_("lowerbackrightleg", CubeListBuilder.m_171558_().m_171514_(48, 17).m_171480_().m_171488_(-2.0F, -0.9317F, -2.1749F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.01F)).m_171555_(false), PartPose.m_171423_(0.0F, 8.6102F, 0.1315F, -0.1309F, 0.0F, 0.0F));
      lowerbackrightleg.m_171599_("backrightfoot", CubeListBuilder.m_171558_().m_171514_(66, 16).m_171480_().m_171488_(-2.0F, -0.5625F, -2.75F, 4.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(0.0F, 3.2399F, -0.7598F, 0.0436F, 0.0F, 0.0F));
      PartDefinition leftFrontLeg = partdefinition.m_171599_("left_front_leg", CubeListBuilder.m_171558_().m_171514_(49, 0).m_171488_(0.1039F, -1.0906F, -2.18F, 5.0F, 10.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(2.3961F, 10.0401F, -5.4796F));
      PartDefinition leftLowerFrontLeg = leftFrontLeg.m_171599_("leftLowerFrontLeg", CubeListBuilder.m_171558_().m_171514_(48, 17).m_171488_(-2.0F, -1.4802F, -2.0521F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.01F)), PartPose.m_171423_(2.6039F, 8.5295F, 0.9206F, -0.1309F, 0.0F, 0.0F));
      leftLowerFrontLeg.m_171599_("leftFoot", CubeListBuilder.m_171558_().m_171514_(66, 16).m_171488_(-2.0F, -0.4375F, -2.8125F, 4.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 3.9316F, -0.5312F, 0.1309F, 0.0F, 0.0F));
      PartDefinition rightFrontLeg = partdefinition.m_171599_("right_front_leg", CubeListBuilder.m_171558_().m_171514_(49, 0).m_171480_().m_171488_(-5.1039F, -1.0906F, -2.18F, 5.0F, 10.0F, 6.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171419_(-2.3961F, 10.0401F, -5.4796F));
      PartDefinition rightLowerFrontLeg = rightFrontLeg.m_171599_("rightLowerFrontLeg", CubeListBuilder.m_171558_().m_171514_(48, 17).m_171480_().m_171488_(-2.0F, -1.4802F, -2.0521F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.01F)).m_171555_(false), PartPose.m_171423_(-2.6039F, 8.5295F, 0.9206F, -0.1309F, 0.0F, 0.0F));
      rightLowerFrontLeg.m_171599_("rightFoot", CubeListBuilder.m_171558_().m_171514_(66, 16).m_171480_().m_171488_(-2.0F, -0.4375F, -2.8125F, 4.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(0.0F, 3.9316F, -0.5312F, 0.1309F, 0.0F, 0.0F));
      PartDefinition head = partdefinition.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(34, 41).m_171488_(-3.0F, -4.3982F, -4.3874F, 6.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 10.1988F, -7.475F, -0.5236F, 0.0F, 0.0F));
      PartDefinition head2 = head.m_171599_("head2", CubeListBuilder.m_171558_().m_171514_(45, 28).m_171488_(-3.5F, -4.1989F, -3.9161F, 7.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, -0.6032F, -3.3915F, 0.4363F, 0.0F, 0.0F));
      PartDefinition leftEar = head2.m_171599_("leftEar", CubeListBuilder.m_171558_(), PartPose.m_171423_(2.4911F, -4.2587F, -0.3525F, 0.4363F, 0.0F, 0.1745F));
      leftEar.m_171599_("leftear1_r1", CubeListBuilder.m_171558_().m_171514_(0, 6).m_171488_(-1.0F, -1.5F, -0.5F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, -1.0F, -0.3125F, 0.0F, 0.0F, 0.0F));
      PartDefinition rightEar = head2.m_171599_("rightEar", CubeListBuilder.m_171558_(), PartPose.m_171423_(-2.4911F, -4.2587F, -0.3525F, 0.4363F, 0.0F, -0.1745F));
      rightEar.m_171599_("rightear_r1", CubeListBuilder.m_171558_().m_171514_(0, 6).m_171480_().m_171488_(-1.0F, -1.5F, -0.5F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(0.0F, -1.0F, -0.3125F, 0.0F, 0.0F, 0.0F));
      PartDefinition mouth = head2.m_171599_("mouth", CubeListBuilder.m_171558_().m_171514_(0, 41).m_171488_(-3.5F, -2.1467F, -8.5031F, 7.0F, 6.0F, 10.0F, new CubeDeformation(0.01F)), PartPose.m_171423_(0.0F, -0.6664F, -1.7917F, 0.3491F, 0.0F, 0.0F));
      PartDefinition frontHorn = mouth.m_171599_("frontHorn", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-1.0F, -1.0616F, -0.9891F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, -1.8739F, -6.9549F, 0.1745F, 0.0F, 0.0F));
      frontHorn.m_171599_("frontUpperHorn", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-1.0F, -1.5F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.m_171423_(0.0F, -2.0914F, 0.3156F, -0.3054F, 0.0F, 0.0F));
      PartDefinition backHorn = mouth.m_171599_("backHorn", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-1.0F, -1.4204F, -0.7742F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, -1.9626F, -4.7885F, 0.0873F, 0.0F, 0.0F));
      backHorn.m_171599_("backUpperHorn", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-1.0F, -0.8625F, -0.3546F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.m_171423_(0.0F, -1.2778F, -0.4983F, -0.2182F, 0.0F, 0.0F));
      return LayerDefinition.m_171565_(meshdefinition, 128, 128);
   }

   public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      RendererHelper.resetModelToDefaultPivots(this);
      this.tail.f_104203_ = 0.3F;
      this.f_170854_.f_104203_ = Mth.m_14089_(limbSwing * 0.77F) * 0.5F * limbSwingAmount;
      this.f_170855_.f_104203_ = Mth.m_14089_(limbSwing * 0.75F) * 0.5F * limbSwingAmount;
      this.f_170852_.f_104203_ = 0.15F + Mth.m_14089_(limbSwing * 0.78F) * 0.5F * limbSwingAmount;
      this.f_170853_.f_104203_ = 0.15F + Mth.m_14089_(limbSwing * 0.76F) * 0.5F * limbSwingAmount;
      if (entity.m_20142_()) {
         this.tail.f_104203_ = 1.2F + Mth.m_14089_(limbSwing * 0.6662F) * 0.2F * limbSwingAmount;
      }

      this.setupAttackAnimation(entity, ageInTicks);
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      super.m_7695_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }

   public void m_6002_(HumanoidArm side, PoseStack poseStack) {
      this.f_103492_.m_104299_(poseStack);
      poseStack.m_252781_(Axis.f_252529_.m_252977_(25.0F));
      poseStack.m_252781_(Axis.f_252392_.m_252977_(90.0F));
      poseStack.m_252781_(Axis.f_252403_.m_252977_(75.0F));
      poseStack.m_85837_(0.05, 0.15, -0.1);
   }

   public ModelPart m_5585_() {
      return this.f_103492_;
   }
}
