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

public class SaiHeavyModel<T extends LivingEntity> extends HumanoidMorphModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "sai_heavy"), "main");
   private final ModelPart leftLowerArm;
   private final ModelPart rightLowerArm;
   private final ModelPart neck;
   private final ModelPart head2;
   private final ModelPart leftEar;
   private final ModelPart rightEar;
   private final ModelPart mouth;
   private final ModelPart frontHorn;
   private final ModelPart frontUpperHorn;
   private final ModelPart backHorn;
   private final ModelPart backUpperHorn;
   private final ModelPart body2;
   private final ModelPart body3;
   private final ModelPart lowerleftleg;
   private final ModelPart leftfoot;
   private final ModelPart lowerrightleg;
   private final ModelPart rightfoot;

   public SaiHeavyModel(ModelPart root) {
      super(root);
      this.leftLowerArm = this.f_102812_.m_171324_("leftLowerArm");
      this.rightLowerArm = this.f_102811_.m_171324_("rightLowerArm");
      this.neck = this.f_102808_.m_171324_("neck");
      this.head2 = this.f_102808_.m_171324_("head2");
      this.leftEar = this.head2.m_171324_("leftEar");
      this.rightEar = this.head2.m_171324_("rightEar");
      this.mouth = this.head2.m_171324_("mouth");
      this.frontHorn = this.head2.m_171324_("frontHorn");
      this.frontUpperHorn = this.frontHorn.m_171324_("frontUpperHorn");
      this.backHorn = this.head2.m_171324_("backHorn");
      this.backUpperHorn = this.backHorn.m_171324_("backUpperHorn");
      this.body2 = this.f_102810_.m_171324_("body2");
      this.body3 = this.body2.m_171324_("body3");
      this.lowerleftleg = this.f_102814_.m_171324_("lowerleftleg");
      this.leftfoot = this.lowerleftleg.m_171324_("leftfoot");
      this.lowerrightleg = this.f_102813_.m_171324_("lowerrightleg");
      this.rightfoot = this.lowerrightleg.m_171324_("rightfoot");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = HumanoidModel.m_170681_(CubeDeformation.f_171458_, 0.0F);
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition leftArm = partdefinition.m_171599_("left_arm", CubeListBuilder.m_171558_().m_171514_(0, 60).m_171480_().m_171488_(-0.25F, -0.9564F, -2.999F, 5.0F, 10.0F, 6.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(7.25F, 0.6264F, 1.0003F, 0.0873F, 0.0F, 0.0F));
      leftArm.m_171599_("leftLowerArm", CubeListBuilder.m_171558_().m_171514_(22, 61).m_171480_().m_171488_(-2.0F, -1.5F, -2.5F, 4.0F, 9.0F, 5.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(2.25F, 8.6838F, 0.1654F, -0.2182F, 0.0F, 0.0F));
      PartDefinition rightArm = partdefinition.m_171599_("right_arm", CubeListBuilder.m_171558_().m_171514_(0, 60).m_171488_(-4.75F, -0.9564F, -2.999F, 5.0F, 10.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-7.25F, 0.6264F, 1.0003F, 0.0873F, 0.0F, 0.0F));
      rightArm.m_171599_("rightLowerArm", CubeListBuilder.m_171558_().m_171514_(22, 61).m_171488_(-2.0F, -1.5F, -2.5F, 4.0F, 9.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-2.25F, 8.6838F, 0.1654F, -0.2182F, 0.0F, 0.0F));
      PartDefinition head = partdefinition.m_171599_("head", CubeListBuilder.m_171558_(), PartPose.m_171423_(0.0F, -1.3502F, -0.0348F, -0.6545F, 0.0F, 0.0F));
      head.m_171599_("neck", CubeListBuilder.m_171558_().m_171514_(50, 30).m_171488_(-3.0F, -4.3982F, -4.3874F, 6.0F, 8.0F, 7.0F, new CubeDeformation(0.2F)), PartPose.m_171423_(0.0F, 0.75F, -0.75F, -0.2182F, 0.0F, 0.0F));
      PartDefinition head2 = head.m_171599_("head2", CubeListBuilder.m_171558_().m_171514_(46, 16).m_171488_(-3.5F, -4.1989F, -3.9161F, 7.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, -0.6032F, -3.3915F, 0.4363F, 0.0F, 0.0F));
      PartDefinition leftEar = head2.m_171599_("leftEar", CubeListBuilder.m_171558_(), PartPose.m_171423_(2.4911F, -4.2587F, -0.3525F, 0.4363F, 0.0F, 0.1745F));
      leftEar.m_171599_("leftear1_r1", CubeListBuilder.m_171558_().m_171514_(0, 6).m_171488_(-1.0F, -1.5F, -0.5F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, -1.0F, -0.3125F, 0.0F, 0.0F, 0.0F));
      PartDefinition rightEar = head2.m_171599_("rightEar", CubeListBuilder.m_171558_(), PartPose.m_171423_(-2.4911F, -4.2587F, -0.3525F, 0.4363F, 0.0F, -0.1745F));
      rightEar.m_171599_("rightear_r1", CubeListBuilder.m_171558_().m_171514_(0, 6).m_171488_(-1.0F, -1.5F, -0.5F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, -1.0F, -0.3125F, 0.0F, 0.0F, 0.0F));
      head2.m_171599_("mouth", CubeListBuilder.m_171558_().m_171514_(46, 0).m_171488_(-3.5F, -2.1467F, -8.5031F, 7.0F, 6.0F, 10.0F, new CubeDeformation(0.01F)), PartPose.m_171423_(0.0F, -0.6664F, -1.7917F, 0.3491F, 0.0F, 0.0F));
      PartDefinition frontHorn = head2.m_171599_("frontHorn", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-1.0F, -1.0616F, -0.9891F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, -0.7496F, -9.1317F, 0.1745F, 0.0F, 0.0F));
      frontHorn.m_171599_("frontUpperHorn", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-1.0F, -1.5F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.m_171423_(0.0F, -2.0914F, 0.3156F, -0.3054F, 0.0F, 0.0F));
      PartDefinition backHorn = head2.m_171599_("backHorn", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-1.0F, -1.4204F, -0.7742F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, -0.8384F, -6.9653F, 0.0873F, 0.0F, 0.0F));
      backHorn.m_171599_("backUpperHorn", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-1.0F, -0.8625F, -0.3546F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.m_171423_(0.0F, -1.2778F, -0.4983F, -0.2182F, 0.0F, 0.0F));
      PartDefinition body = partdefinition.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(0, 46).m_171488_(-5.5F, -2.8492F, -4.4129F, 11.0F, 6.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 14.3338F, 2.7771F, -0.1309F, 0.0F, 0.0F));
      PartDefinition body2 = body.m_171599_("body2", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-6.5F, -14.9331F, -2.2106F, 13.0F, 15.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 0.0254F, -3.0212F, 0.1745F, 0.0F, 0.0F));
      body2.m_171599_("body3", CubeListBuilder.m_171558_().m_171514_(0, 25).m_171488_(-7.0F, -6.3959F, -2.7615F, 14.0F, 10.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, -9.4901F, -1.2439F, 0.1745F, 0.0F, 0.0F));
      PartDefinition leftLeg = partdefinition.m_171599_("left_leg", CubeListBuilder.m_171558_().m_171514_(46, 45).m_171488_(-2.5F, -0.5805F, -2.2687F, 5.0F, 6.0F, 5.0F, new CubeDeformation(0.1F)), PartPose.m_171423_(4.5F, 13.437F, 1.3738F, 0.1745F, 0.0F, 0.0F));
      PartDefinition lowerleftleg = leftLeg.m_171599_("lowerleftleg", CubeListBuilder.m_171558_().m_171514_(46, 57).m_171488_(-2.0313F, -0.1803F, -1.7752F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.2F)), PartPose.m_171423_(-0.0313F, 4.0089F, 0.2982F, -0.2618F, 0.0F, 0.0F));
      lowerleftleg.m_171599_("leftfoot", CubeListBuilder.m_171558_().m_171514_(44, 68).m_171488_(-2.5625F, -0.0625F, -2.5F, 5.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0313F, 4.7541F, -0.4595F, 0.0873F, 0.0F, 0.0F));
      PartDefinition rightLeg = partdefinition.m_171599_("right_leg", CubeListBuilder.m_171558_().m_171514_(46, 45).m_171480_().m_171488_(-2.5F, -0.5805F, -2.2687F, 5.0F, 6.0F, 5.0F, new CubeDeformation(0.1F)).m_171555_(false), PartPose.m_171423_(-4.5F, 13.437F, 1.3738F, 0.1745F, 0.0F, 0.0F));
      PartDefinition lowerrightleg = rightLeg.m_171599_("lowerrightleg", CubeListBuilder.m_171558_().m_171514_(46, 57).m_171480_().m_171488_(-1.9688F, -0.1803F, -1.7752F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.2F)).m_171555_(false), PartPose.m_171423_(0.0313F, 4.0089F, 0.2982F, -0.2618F, 0.0F, 0.0F));
      lowerrightleg.m_171599_("rightfoot", CubeListBuilder.m_171558_().m_171514_(44, 68).m_171480_().m_171488_(-2.4375F, -0.0625F, -2.5F, 5.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(-0.0313F, 4.7541F, -0.4595F, 0.0873F, 0.0F, 0.0F));
      return LayerDefinition.m_171565_(meshdefinition, 128, 128);
   }

   public void m_6973_(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      RendererHelper.resetModelToDefaultPivots(this);
      this.setupHeadRotation(entity, headPitch, netHeadYaw);
      float f = 1.0F;
      this.f_102811_.f_104203_ = Mth.m_14089_(limbSwing * 0.6F + (float)Math.PI) * 0.8F * limbSwingAmount * 0.5F / f;
      this.f_102812_.f_104203_ = Mth.m_14089_(limbSwing * 0.6F) * 0.8F * limbSwingAmount * 0.5F / f;
      this.f_102813_.f_104203_ = (float)Math.toRadians((double)10.0F) + Mth.m_14089_(limbSwing * 0.6F) * 0.7F * limbSwingAmount / f;
      this.f_102814_.f_104203_ = (float)Math.toRadians((double)10.0F) + Mth.m_14089_(limbSwing * 0.6F + (float)Math.PI) * 0.7F * limbSwingAmount / f;
      if (entity.m_6047_()) {
         this.f_102810_.f_104201_ = 17.0F;
         this.f_102810_.f_104202_ = 5.0F;
         this.f_102810_.f_104203_ = 0.1F;
         this.f_102808_.f_104201_ = 3.0F;
         this.f_102808_.f_104202_ = -2.0F;
         this.f_102812_.f_104203_ = 0.3F;
         this.f_102812_.f_104201_ = 4.0F;
         this.f_102812_.f_104202_ = 0.0F;
         this.f_102811_.f_104203_ = 0.3F;
         this.f_102811_.f_104201_ = 4.0F;
         this.f_102811_.f_104202_ = 0.0F;
      }

      this.m_7884_(entity, ageInTicks);
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      super.m_7695_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }

   public void m_6002_(HumanoidArm side, PoseStack poseStack) {
      super.m_6002_(side, poseStack);
      poseStack.m_85837_(side == HumanoidArm.RIGHT ? -0.1 : 0.1, 0.35, -0.1);
   }

   public void renderFirstPersonArm(PoseStack stack, VertexConsumer vertex, int packedLight, int overlay, float red, float green, float blue, float alpha, HumanoidArm side, boolean isLeg) {
      stack.m_85837_((double)0.0F, -0.3, -0.3);
      stack.m_252781_(Axis.f_252529_.m_252977_(20.0F));
      super.renderFirstPersonArm(stack, vertex, packedLight, overlay, red, green, blue, alpha, side, isLeg);
   }
}
