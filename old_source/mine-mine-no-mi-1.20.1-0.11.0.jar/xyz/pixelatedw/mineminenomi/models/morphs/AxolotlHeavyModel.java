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

public class AxolotlHeavyModel<T extends LivingEntity> extends HumanoidMorphModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "axolotl_heavy"), "main");
   private final ModelPart backFin;
   private final ModelPart upperBody;
   private final ModelPart hips;
   private final ModelPart lowerBody;
   private final ModelPart tail;
   private final ModelPart tail1Detail;
   private final ModelPart tail2;
   private final ModelPart tail2Detail;
   private final ModelPart tail3;
   private final ModelPart tail3Detail;
   private final ModelPart tail4;
   private final ModelPart tail4Detail;
   private final ModelPart neck;
   private final ModelPart lowerLeftLeg;
   private final ModelPart lowerRightLeg;
   private final ModelPart leftPunch;
   private final ModelPart leftArmMid;
   private final ModelPart rightPunch;
   private final ModelPart rightArmMid;

   public AxolotlHeavyModel(ModelPart root) {
      super(root);
      this.backFin = this.f_102810_.m_171324_("backFin");
      this.upperBody = this.f_102810_.m_171324_("upperBody");
      this.hips = this.f_102810_.m_171324_("hips");
      this.lowerBody = this.f_102810_.m_171324_("lowerBody");
      this.tail = root.m_171324_("tail");
      this.tail1Detail = this.tail.m_171324_("tail1Detail");
      this.tail2 = this.tail.m_171324_("tail2");
      this.tail2Detail = this.tail2.m_171324_("tail2Detail");
      this.tail3 = this.tail2.m_171324_("tail3");
      this.tail3Detail = this.tail3.m_171324_("tail3Detail");
      this.tail4 = this.tail3.m_171324_("tail4");
      this.tail4Detail = this.tail4.m_171324_("tail4Detail");
      this.neck = this.f_102808_.m_171324_("neck");
      this.lowerLeftLeg = this.f_102814_.m_171324_("lowerLeftLeg");
      this.lowerRightLeg = this.f_102813_.m_171324_("lowerRightLeg");
      this.leftPunch = this.f_102812_.m_171324_("leftPunch");
      this.leftArmMid = this.f_102812_.m_171324_("leftArmMid");
      this.rightPunch = this.f_102811_.m_171324_("rightPunch");
      this.rightArmMid = this.f_102811_.m_171324_("rightArmMid");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = HumanoidModel.m_170681_(CubeDeformation.f_171458_, 0.0F);
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition body = partdefinition.m_171599_("body", CubeListBuilder.m_171558_(), PartPose.m_171423_(4.0F, 14.0F, 35.0F, 0.3491F, 0.0F, 0.0F));
      body.m_171599_("backFin", CubeListBuilder.m_171558_().m_171514_(82, 1).m_171488_(1.0F, -12.0F, -6.4F, 0.0F, 25.0F, 23.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-5.0F, -27.0F, -19.0F, 0.0873F, 0.0F, 0.0F));
      body.m_171599_("upperBody", CubeListBuilder.m_171558_().m_171514_(0, 8).m_171488_(-13.0F, -30.0F, -32.6F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).m_171514_(0, 8).m_171488_(2.0F, -30.0F, -32.6F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).m_171514_(47, 50).m_171488_(-15.0F, -36.3F, -31.3F, 22.0F, 3.0F, 13.0F, new CubeDeformation(0.0F)).m_171514_(0, 0).m_171488_(-16.0F, -36.0F, -32.3F, 24.0F, 16.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 0.0F, 0.0F, 0.0873F, 0.0F, 0.0F));
      body.m_171599_("hips", CubeListBuilder.m_171558_().m_171514_(8, 32).m_171488_(-15.5F, -20.0F, -31.5F, 23.0F, 4.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 0.0F, 0.0F, 0.0436F, 0.0F, 0.0F));
      body.m_171599_("lowerBody", CubeListBuilder.m_171558_().m_171514_(47, 67).m_171488_(-13.0F, -10.0F, -10.0F, 18.0F, 10.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, -7.0F, -25.0F, -0.48F, 0.0F, 0.0F));
      PartDefinition tail = partdefinition.m_171599_("tail", CubeListBuilder.m_171558_(), PartPose.m_171423_(0.0F, 11.0F, 13.0F, -0.1309F, 0.0F, 0.0F));
      tail.m_171599_("tail1_r1", CubeListBuilder.m_171558_().m_171514_(39, 101).m_171488_(-3.5F, -8.5035F, -0.8045F, 7.0F, 8.0F, 19.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 3.1345F, -10.7133F, -0.3054F, 0.0F, 0.0F));
      tail.m_171599_("tail1Detail", CubeListBuilder.m_171558_().m_171514_(98, -14).m_171488_(-1.0F, -9.0F, -8.0F, 0.0F, 14.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(1.0F, 0.4965F, 2.1955F, -0.2182F, 0.0F, 0.0F));
      PartDefinition tail2 = tail.m_171599_("tail2", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 5.5F, 8.0F));
      tail2.m_171599_("tail2_r1", CubeListBuilder.m_171558_().m_171514_(98, 112).m_171488_(-2.5F, -9.0035F, 18.1955F, 5.0F, 6.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 2.2879F, -20.1948F, -0.1745F, 0.0F, 0.0F));
      tail2.m_171599_("tail2Detail", CubeListBuilder.m_171558_().m_171514_(38, 93).m_171488_(-1.0F, -8.8987F, -6.0675F, 0.0F, 14.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(1.0F, -2.5035F, 5.1955F, -0.1745F, 0.0F, 0.0F));
      PartDefinition tail3 = tail2.m_171599_("tail3", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 2.0F, 13.0F));
      tail3.m_171599_("tail3_r1_r1", CubeListBuilder.m_171558_().m_171514_(95, 78).m_171488_(-1.5F, -8.0035F, 27.1955F, 3.0F, 4.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 2.8749F, -33.7109F, -0.0873F, 0.0F, 0.0F));
      tail3.m_171599_("tail3Detail", CubeListBuilder.m_171558_().m_171514_(84, 96).m_171488_(-1.0F, -6.9629F, -6.9734F, 0.0F, 13.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(1.0F, -4.5035F, 3.1955F, -0.1309F, 0.0F, 0.0F));
      PartDefinition tail4 = tail3.m_171599_("tail4", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-0.5F, -1.0035F, -0.8045F, 1.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 1.0F, 6.0F));
      tail4.m_171599_("tail4Detail", CubeListBuilder.m_171558_().m_171514_(115, 70).m_171488_(-1.0F, -6.4629F, 4.0266F, 0.0F, 12.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(1.0F, -5.0535F, -5.8545F, -0.0436F, 0.0F, 0.0F));
      PartDefinition head = partdefinition.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(28, 51).m_171488_(-8.0F, -10.9277F, 1.4656F, 16.0F, 9.0F, 0.0F, new CubeDeformation(0.0F)).m_171514_(64, 0).m_171488_(-4.0F, -6.4476F, -3.122F, 8.0F, 5.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, -7.0F, -4.0F, 0.3491F, 0.0F, 0.0F));
      head.m_171599_("neck", CubeListBuilder.m_171558_().m_171514_(78, 13).m_171488_(-3.5F, -2.0F, -2.0F, 7.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, -1.9277F, 0.4656F, 0.1745F, 0.0F, 0.0F));
      PartDefinition leftLeg = partdefinition.m_171599_("left_leg", CubeListBuilder.m_171558_(), PartPose.m_171423_(7.0F, 14.25F, 3.0F, -0.2618F, 0.0F, 0.0F));
      leftLeg.m_171599_("leftLeg_r1", CubeListBuilder.m_171558_().m_171514_(73, 91).m_171480_().m_171488_(3.5F, -12.75F, 2.5F, 7.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(-7.0F, 5.761F, -8.6866F, -0.5672F, 0.0F, 0.0F));
      leftLeg.m_171599_("lowerLeftLeg", CubeListBuilder.m_171558_().m_171514_(105, 96).m_171480_().m_171488_(0.0F, 6.0F, -7.5F, 6.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(-3.0F, -5.0F, 2.0F, 0.2618F, 0.0F, 0.0F));
      PartDefinition rightLeg = partdefinition.m_171599_("right_leg", CubeListBuilder.m_171558_(), PartPose.m_171423_(-7.0F, 14.25F, 3.0F, -0.2618F, 0.0F, 0.0F));
      rightLeg.m_171599_("rightLeg_r1", CubeListBuilder.m_171558_().m_171514_(73, 91).m_171488_(-10.5F, -12.75F, 2.5F, 7.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(7.0F, 5.761F, -8.6866F, -0.5672F, 0.0F, 0.0F));
      rightLeg.m_171599_("lowerRightLeg", CubeListBuilder.m_171558_().m_171514_(105, 96).m_171488_(-6.0F, 6.0F, -7.5F, 6.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(3.0F, -5.0F, 2.0F, 0.2618F, 0.0F, 0.0F));
      PartDefinition leftArm = partdefinition.m_171599_("left_arm", CubeListBuilder.m_171558_(), PartPose.m_171423_(12.0F, -4.0F, -1.0F, -0.4363F, 0.0F, 0.0F));
      leftArm.m_171599_("leftArm_r1", CubeListBuilder.m_171558_().m_171514_(1, 52).m_171480_().m_171488_(12.0F, -37.0076F, -5.1743F, 9.0F, 8.0F, 9.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(-7.9475F, 29.6235F, 3.7702F, 0.0865F, 0.0114F, -0.1304F));
      leftArm.m_171599_("leftPunch", CubeListBuilder.m_171558_().m_171514_(0, 104).m_171480_().m_171488_(-6.0F, -2.1134F, -4.5979F, 10.0F, 15.0F, 9.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(6.0F, 16.0F, 2.0F, 0.0843F, -0.0226F, 0.2608F));
      leftArm.m_171599_("leftArmMid", CubeListBuilder.m_171558_().m_171514_(0, 71).m_171480_().m_171488_(-1.0F, -3.0F, -5.0F, 12.0F, 20.0F, 11.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(0.0F, 0.0F, 0.0F, 0.0865F, 0.0114F, -0.1304F));
      PartDefinition rightArm = partdefinition.m_171599_("right_arm", CubeListBuilder.m_171558_(), PartPose.m_171423_(-12.0F, -4.0F, -1.0F, -0.4363F, 0.0F, 0.0F));
      rightArm.m_171599_("rightArm_r1", CubeListBuilder.m_171558_().m_171514_(1, 52).m_171488_(-21.0F, -37.0076F, -5.1743F, 9.0F, 8.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(7.9475F, 29.6235F, 3.7702F, 0.0865F, -0.0114F, 0.1304F));
      rightArm.m_171599_("rightPunch", CubeListBuilder.m_171558_().m_171514_(0, 104).m_171488_(-4.0F, -2.1134F, -4.5979F, 10.0F, 15.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-6.0F, 16.0F, 2.0F, 0.0843F, 0.0226F, -0.2608F));
      rightArm.m_171599_("rightArmMid", CubeListBuilder.m_171558_().m_171514_(0, 71).m_171488_(-11.0F, -3.0F, -5.0F, 12.0F, 20.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 0.0F, 0.0F, 0.0865F, -0.0114F, 0.1304F));
      return LayerDefinition.m_171565_(meshdefinition, 128, 128);
   }

   public void m_6973_(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      RendererHelper.resetModelToDefaultPivots(this);
      this.setupHeadRotation(entity, headPitch, netHeadYaw);
      float f = 1.0F;
      this.f_102812_.f_104203_ = -0.34F + Mth.m_14089_(limbSwing * 0.6662F + (float)Math.PI) * 0.8F * limbSwingAmount * 0.5F / f;
      this.f_102811_.f_104203_ = -0.34F + Mth.m_14089_(limbSwing * 0.6662F + (float)Math.PI) * 0.8F * limbSwingAmount * 0.5F / f;
      this.f_102814_.f_104203_ = Mth.m_14089_(limbSwing * 0.6662F) * 0.7F * limbSwingAmount / f;
      this.f_102813_.f_104203_ = Mth.m_14089_(limbSwing * 0.6662F) * 0.7F * limbSwingAmount / f;
      this.tail.f_104204_ = (float)Math.sin((double)ageInTicks * 0.06) / 10.0F;
      this.tail.f_104203_ = (float)Math.sin((double)ageInTicks * 0.02) / 10.0F;
      this.m_7884_(entity, ageInTicks);
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      super.m_7695_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.tail.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }

   public void m_6002_(HumanoidArm side, PoseStack poseStack) {
      super.m_6002_(side, poseStack);
      poseStack.m_85837_(side == HumanoidArm.RIGHT ? -0.3 : 0.3, 1.1, 0.1);
      poseStack.m_252781_(Axis.f_252529_.m_252977_(-20.0F));
      poseStack.m_252781_(Axis.f_252403_.m_252977_(side == HumanoidArm.RIGHT ? -20.0F : 20.0F));
   }

   public void renderFirstPersonArm(PoseStack stack, VertexConsumer vertex, int packedLight, int overlay, float red, float green, float blue, float alpha, HumanoidArm side, boolean isLeg) {
      float sideMod = side == HumanoidArm.RIGHT ? 1.0F : -1.0F;
      stack.m_85837_((double)1.0F * (double)sideMod, 0.2, 0.4);
      stack.m_252781_(Axis.f_252529_.m_252977_(20.0F));
      if (isLeg) {
         stack.m_85837_(-0.6 * (double)sideMod, -0.3, (double)-1.0F);
      }

      super.renderFirstPersonArm(stack, vertex, packedLight, overlay, red, green, blue, alpha, side, isLeg);
   }
}
