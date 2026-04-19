package xyz.pixelatedw.mineminenomi.models.morphs.partials;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
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
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;

public class GearFifthRunningModel<T extends LivingEntity> extends HumanoidModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "gear_fifth_running"), "main");
   private final ModelPart leftLegs;
   private final ModelPart leftLeg;
   private final ModelPart leftLeg2;
   private final ModelPart leftLeg3;
   private final ModelPart leftLeg4;
   private final ModelPart leftLeg5;
   private final ModelPart leftLeg6;
   private final ModelPart rightLegs;
   private final ModelPart rightLeg;
   private final ModelPart rightLeg2;
   private final ModelPart rightLeg3;
   private final ModelPart rightLeg4;
   private final ModelPart rightLeg5;
   private final ModelPart rightLeg6;
   private final ModelPart leftOverlays;
   private final ModelPart leftPants;
   private final ModelPart leftPants2;
   private final ModelPart leftPants3;
   private final ModelPart leftPants4;
   private final ModelPart leftPants5;
   private final ModelPart leftPants6;
   private final ModelPart rightOverlays;
   private final ModelPart rightPants;
   private final ModelPart rightPants2;
   private final ModelPart rightPants3;
   private final ModelPart rightPants4;
   private final ModelPart rightPants5;
   private final ModelPart rightPants6;

   public GearFifthRunningModel(ModelPart root) {
      super(root);
      this.leftLegs = root.m_171324_("left_leg");
      this.leftLeg = this.leftLegs.m_171324_("left_leg1");
      this.leftLeg2 = this.leftLegs.m_171324_("left_leg2");
      this.leftLeg3 = this.leftLegs.m_171324_("left_leg3");
      this.leftLeg4 = this.leftLegs.m_171324_("left_leg4");
      this.leftLeg5 = this.leftLegs.m_171324_("left_leg5");
      this.leftLeg6 = this.leftLegs.m_171324_("left_leg6");
      this.rightLegs = root.m_171324_("right_leg");
      this.rightLeg = this.rightLegs.m_171324_("right_leg1");
      this.rightLeg2 = this.rightLegs.m_171324_("right_leg2");
      this.rightLeg3 = this.rightLegs.m_171324_("right_leg3");
      this.rightLeg4 = this.rightLegs.m_171324_("right_leg4");
      this.rightLeg5 = this.rightLegs.m_171324_("right_leg5");
      this.rightLeg6 = this.rightLegs.m_171324_("right_leg6");
      this.leftOverlays = root.m_171324_("left_pants");
      this.leftPants = this.leftOverlays.m_171324_("left_pants1");
      this.leftPants2 = this.leftOverlays.m_171324_("left_pants2");
      this.leftPants3 = this.leftOverlays.m_171324_("left_pants3");
      this.leftPants4 = this.leftOverlays.m_171324_("left_pants4");
      this.leftPants5 = this.leftOverlays.m_171324_("left_pants5");
      this.leftPants6 = this.leftOverlays.m_171324_("left_pants6");
      this.rightOverlays = root.m_171324_("right_pants");
      this.rightPants = this.rightOverlays.m_171324_("right_pants1");
      this.rightPants2 = this.rightOverlays.m_171324_("right_pants2");
      this.rightPants3 = this.rightOverlays.m_171324_("right_pants3");
      this.rightPants4 = this.rightOverlays.m_171324_("right_pants4");
      this.rightPants5 = this.rightOverlays.m_171324_("right_pants5");
      this.rightPants6 = this.rightOverlays.m_171324_("right_pants6");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = HumanoidModel.m_170681_(CubeDeformation.f_171458_, 0.0F);
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition left_legs = partdefinition.m_171599_("left_leg", CubeListBuilder.m_171558_(), PartPose.m_171419_(2.0F, 17.0F, 0.0F));
      left_legs.m_171599_("left_leg1", CubeListBuilder.m_171558_().m_171514_(16, 48).m_171488_(-2.0F, -3.0F, -5.9326F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 0.0F, 0.0F, -0.9599F, 0.0F, 0.0F));
      left_legs.m_171599_("left_leg2", CubeListBuilder.m_171558_().m_171514_(16, 48).m_171488_(-2.0F, -3.0F, -5.9326F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.01F)), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      left_legs.m_171599_("left_leg3", CubeListBuilder.m_171558_().m_171514_(16, 48).m_171488_(-2.0F, -3.0F, -5.9326F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 0.0F, 0.0F, 0.9599F, 0.0F, 0.0F));
      left_legs.m_171599_("left_leg4", CubeListBuilder.m_171558_().m_171514_(16, 48).m_171488_(-2.0F, -3.0F, -5.9326F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.01F)), PartPose.m_171423_(0.0F, 0.0F, 0.0F, 1.9199F, 0.0F, 0.0F));
      left_legs.m_171599_("left_leg5", CubeListBuilder.m_171558_().m_171514_(16, 48).m_171488_(-2.0F, -3.0F, -5.9326F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 0.0F, 0.0F, 2.8798F, 0.0F, 0.0F));
      left_legs.m_171599_("left_leg6", CubeListBuilder.m_171558_().m_171514_(16, 48).m_171488_(-2.0F, -3.0F, -5.9326F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.01F)), PartPose.m_171423_(0.0F, 0.0F, 0.0F, -2.4435F, 0.0F, 0.0F));
      PartDefinition right_legs = partdefinition.m_171599_("right_leg", CubeListBuilder.m_171558_(), PartPose.m_171419_(-2.0F, 17.0F, 0.0F));
      right_legs.m_171599_("right_leg1", CubeListBuilder.m_171558_().m_171514_(16, 48).m_171480_().m_171488_(-2.0F, -3.0F, -5.9326F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(0.0F, 0.0F, 0.0F, -0.9599F, 0.0F, 0.0F));
      right_legs.m_171599_("right_leg2", CubeListBuilder.m_171558_().m_171514_(16, 48).m_171480_().m_171488_(-2.0F, -3.0F, -5.9326F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.01F)).m_171555_(false), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      right_legs.m_171599_("right_leg3", CubeListBuilder.m_171558_().m_171514_(16, 48).m_171480_().m_171488_(-2.0F, -3.0F, -5.9326F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(0.0F, 0.0F, 0.0F, 0.9599F, 0.0F, 0.0F));
      right_legs.m_171599_("right_leg4", CubeListBuilder.m_171558_().m_171514_(16, 48).m_171480_().m_171488_(-2.0F, -3.0F, -5.9326F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.01F)).m_171555_(false), PartPose.m_171423_(0.0F, 0.0F, 0.0F, 1.9199F, 0.0F, 0.0F));
      right_legs.m_171599_("right_leg5", CubeListBuilder.m_171558_().m_171514_(16, 48).m_171480_().m_171488_(-2.0F, -3.0F, -5.9326F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(0.0F, 0.0F, 0.0F, 2.8798F, 0.0F, 0.0F));
      right_legs.m_171599_("right_leg6", CubeListBuilder.m_171558_().m_171514_(16, 48).m_171480_().m_171488_(-2.0F, -3.0F, -5.9326F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.01F)).m_171555_(false), PartPose.m_171423_(0.0F, 0.0F, 0.0F, -2.4435F, 0.0F, 0.0F));
      PartDefinition left_overlays = partdefinition.m_171599_("left_pants", CubeListBuilder.m_171558_(), PartPose.m_171419_(2.0F, 17.0F, 0.0F));
      left_overlays.m_171599_("left_pants1", CubeListBuilder.m_171558_().m_171514_(0, 48).m_171488_(-2.0F, -3.0F, -5.9326F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.m_171423_(0.0F, 0.0F, 0.0F, -0.9599F, 0.0F, 0.0F));
      left_overlays.m_171599_("left_pants2", CubeListBuilder.m_171558_().m_171514_(0, 48).m_171488_(-2.0F, -3.0F, -5.9326F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      left_overlays.m_171599_("left_pants3", CubeListBuilder.m_171558_().m_171514_(0, 48).m_171488_(-2.0F, -3.0F, -5.9326F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.m_171423_(0.0F, 0.0F, 0.0F, 0.9599F, 0.0F, 0.0F));
      left_overlays.m_171599_("left_pants4", CubeListBuilder.m_171558_().m_171514_(0, 48).m_171488_(-2.0F, -3.0F, -5.9326F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.m_171423_(0.0F, 0.0F, 0.0F, 1.9199F, 0.0F, 0.0F));
      left_overlays.m_171599_("left_pants5", CubeListBuilder.m_171558_().m_171514_(0, 48).m_171488_(-2.0F, -3.0F, -5.9326F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.m_171423_(0.0F, 0.0F, 0.0F, 2.8798F, 0.0F, 0.0F));
      left_overlays.m_171599_("left_pants6", CubeListBuilder.m_171558_().m_171514_(0, 48).m_171488_(-2.0F, -3.0F, -5.9326F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.m_171423_(0.0F, 0.0F, 0.0F, -2.4435F, 0.0F, 0.0F));
      PartDefinition right_overlays = partdefinition.m_171599_("right_pants", CubeListBuilder.m_171558_(), PartPose.m_171419_(-2.0F, 17.0F, 0.0F));
      right_overlays.m_171599_("right_pants1", CubeListBuilder.m_171558_().m_171514_(0, 48).m_171480_().m_171488_(-2.0F, -3.0F, -5.9326F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)).m_171555_(false), PartPose.m_171423_(0.0F, 0.0F, 0.0F, -0.9599F, 0.0F, 0.0F));
      right_overlays.m_171599_("right_pants2", CubeListBuilder.m_171558_().m_171514_(0, 48).m_171480_().m_171488_(-2.0F, -3.0F, -5.9326F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)).m_171555_(false), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      right_overlays.m_171599_("right_pants3", CubeListBuilder.m_171558_().m_171514_(0, 48).m_171480_().m_171488_(-2.0F, -3.0F, -5.9326F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)).m_171555_(false), PartPose.m_171423_(0.0F, 0.0F, 0.0F, 0.9599F, 0.0F, 0.0F));
      right_overlays.m_171599_("right_pants4", CubeListBuilder.m_171558_().m_171514_(0, 48).m_171480_().m_171488_(-2.0F, -3.0F, -5.9326F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)).m_171555_(false), PartPose.m_171423_(0.0F, 0.0F, 0.0F, 1.9199F, 0.0F, 0.0F));
      right_overlays.m_171599_("right_pants5", CubeListBuilder.m_171558_().m_171514_(0, 48).m_171480_().m_171488_(-2.0F, -3.0F, -5.9326F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)).m_171555_(false), PartPose.m_171423_(0.0F, 0.0F, 0.0F, 2.8798F, 0.0F, 0.0F));
      right_overlays.m_171599_("right_pants6", CubeListBuilder.m_171558_().m_171514_(0, 48).m_171480_().m_171488_(-2.0F, -3.0F, -5.9326F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)).m_171555_(false), PartPose.m_171423_(0.0F, 0.0F, 0.0F, -2.4435F, 0.0F, 0.0F));
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   public void m_6973_(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      RendererHelper.resetModelToDefaultPivots(this);
      this.leftLegs.f_104203_ = (float)((double)ageInTicks * Math.PI * (double)0.15F);
      this.rightLegs.f_104203_ = (float)((double)ageInTicks * Math.PI * (double)0.15F);
      this.leftOverlays.f_104203_ = (float)((double)ageInTicks * Math.PI * (double)0.15F);
      this.rightOverlays.f_104203_ = (float)((double)ageInTicks * Math.PI * (double)0.15F);
      boolean isRunning = entity.m_20142_() && entity.m_20184_().m_82553_() > (double)0.05F;
      if (isRunning) {
         this.leftLegs.f_104207_ = true;
         this.rightLegs.f_104207_ = true;
         this.leftOverlays.f_104207_ = true;
         this.rightOverlays.f_104207_ = true;
      } else {
         this.leftLegs.f_104207_ = false;
         this.rightLegs.f_104207_ = false;
         this.leftOverlays.f_104207_ = false;
         this.rightOverlays.f_104207_ = false;
      }

   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.leftLegs.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.rightLegs.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.leftOverlays.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.rightOverlays.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }
}
