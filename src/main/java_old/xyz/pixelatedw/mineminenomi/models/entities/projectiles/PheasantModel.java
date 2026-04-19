package xyz.pixelatedw.mineminenomi.models.entities.projectiles;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class PheasantModel<T extends Entity> extends EntityModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "pheasant"), "main");
   private final ModelPart body1;
   private final ModelPart body2;
   private final ModelPart body3;
   private final ModelPart body4;
   private final ModelPart body5;
   private final ModelPart head;
   private final ModelPart tuft1;
   private final ModelPart tuft2;
   private final ModelPart tuft3;
   private final ModelPart beak1;
   private final ModelPart beak2;
   private final ModelPart beak3;
   private final ModelPart beak4;
   private final ModelPart tail1;
   private final ModelPart tail2;
   private final ModelPart tail3;
   private final ModelPart rightleg1;
   private final ModelPart rightleg2;
   private final ModelPart rightfoot1;
   private final ModelPart leftleg1;
   private final ModelPart leftleg2;
   private final ModelPart leftfoot1;
   private final ModelPart rightWing1;
   private final ModelPart rightWing3;
   private final ModelPart rightWing5;
   private final ModelPart rightWing2;
   private final ModelPart rightWing4;
   private final ModelPart leftWing1;
   private final ModelPart leftWing3;
   private final ModelPart leftWing5;
   private final ModelPart leftWing2;
   private final ModelPart leftWing4;
   private static final float[] WING_ANIMATION_FRAMES = new float[]{5.0F, 10.0F, 15.0F, 20.0F, 25.0F, 30.0F, 35.0F, 30.0F, 25.0F, 20.0F, 15.0F, 10.0F, 5.0F, 0.0F, -5.0F, -10.0F, -15.0F, -10.0F, -5.0F, 0.0F, 5.0F};

   public PheasantModel(ModelPart root) {
      this.body1 = root.m_171324_("body1");
      this.body2 = this.body1.m_171324_("body2");
      this.body3 = this.body1.m_171324_("body3");
      this.body4 = this.body1.m_171324_("body4");
      this.body5 = this.body1.m_171324_("body5");
      this.head = root.m_171324_("head");
      this.tuft1 = root.m_171324_("tuft1");
      this.tuft2 = root.m_171324_("tuft2");
      this.tuft3 = root.m_171324_("tuft3");
      this.beak1 = root.m_171324_("beak1");
      this.beak2 = root.m_171324_("beak2");
      this.beak3 = root.m_171324_("beak3");
      this.beak4 = root.m_171324_("beak4");
      this.tail1 = root.m_171324_("tail1");
      this.tail2 = root.m_171324_("tail2");
      this.tail3 = root.m_171324_("tail3");
      this.rightleg1 = root.m_171324_("rightleg1");
      this.rightleg2 = root.m_171324_("rightleg2");
      this.rightfoot1 = root.m_171324_("rightfoot1");
      this.leftleg1 = root.m_171324_("leftleg1");
      this.leftleg2 = root.m_171324_("leftleg2");
      this.leftfoot1 = root.m_171324_("leftfoot1");
      this.rightWing1 = root.m_171324_("rightWing1");
      this.rightWing3 = this.rightWing1.m_171324_("rightWing3");
      this.rightWing5 = this.rightWing3.m_171324_("rightWing5");
      this.rightWing2 = this.rightWing1.m_171324_("rightWing2");
      this.rightWing4 = this.rightWing2.m_171324_("rightWing4");
      this.leftWing1 = root.m_171324_("leftWing1");
      this.leftWing3 = this.leftWing1.m_171324_("leftWing3");
      this.leftWing5 = this.leftWing3.m_171324_("leftWing5");
      this.leftWing2 = this.leftWing1.m_171324_("leftWing2");
      this.leftWing4 = this.leftWing2.m_171324_("leftWing4");
   }

   public static LayerDefinition createLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition body1 = partdefinition.m_171599_("body1", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-3.5F, -6.5F, -5.0F, 7.0F, 7.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, -1.0F, 0.0F));
      body1.m_171599_("body2", CubeListBuilder.m_171558_().m_171514_(0, 18).m_171488_(-3.0F, -3.0F, 5.0F, 6.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, -3.0F, -0.5F));
      body1.m_171599_("body3", CubeListBuilder.m_171558_().m_171514_(0, 27).m_171488_(-3.0F, -3.0F, -1.0F, 6.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, -3.5F, -5.7F, -0.0524F, 0.0F, 0.0F));
      body1.m_171599_("body4", CubeListBuilder.m_171558_().m_171514_(0, 36).m_171488_(-2.5F, -2.5F, -1.0F, 5.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, -4.0F, -7.5F, -0.0524F, 0.0F, 0.0F));
      body1.m_171599_("body5", CubeListBuilder.m_171558_().m_171514_(0, 44).m_171488_(-2.0F, -2.5F, -1.5F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, -3.5F, -8.7F));
      partdefinition.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(0, 51).m_171488_(-1.5F, -6.4874F, -12.6075F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, -1.0F, 0.0F, 0.0873F, 0.0F, 0.0F));
      partdefinition.m_171599_("tuft1", CubeListBuilder.m_171558_().m_171514_(13, 51).m_171488_(-0.5F, -12.3649F, -6.362F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, -1.0F, 0.0F, 0.6981F, 0.0F, 0.0F));
      partdefinition.m_171599_("tuft2", CubeListBuilder.m_171558_().m_171514_(22, 51).m_171488_(0.7103F, -12.1338F, -6.5559F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, -1.0F, 0.0F, 0.6981F, 0.0F, -0.3491F));
      partdefinition.m_171599_("tuft3", CubeListBuilder.m_171558_().m_171514_(29, 51).m_171488_(-1.7103F, -12.1338F, -6.5559F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, -1.0F, 0.0F, 0.6981F, 0.0F, 0.3491F));
      partdefinition.m_171599_("beak1", CubeListBuilder.m_171558_().m_171514_(0, 58).m_171488_(-4.2311F, -7.411F, -12.648F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, -1.0F, 0.0F, 0.2665F, -0.2618F, -0.0181F));
      partdefinition.m_171599_("beak2", CubeListBuilder.m_171558_().m_171514_(0, 61).m_171488_(3.2311F, -7.411F, -12.648F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, -1.0F, 0.0F, 0.2665F, 0.2618F, 0.0181F));
      partdefinition.m_171599_("beak3", CubeListBuilder.m_171558_().m_171514_(7, 58).m_171488_(-0.5F, -7.568F, -13.2824F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, -1.0F, 0.0F, 0.2618F, 0.0F, 0.0F));
      partdefinition.m_171599_("beak4", CubeListBuilder.m_171558_().m_171514_(7, 61).m_171488_(-0.5F, -1.2612F, -14.5378F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, -1.0F, 0.0F, -0.1745F, 0.0F, 0.0F));
      partdefinition.m_171599_("tail1", CubeListBuilder.m_171558_().m_171514_(17, 18).m_171488_(-1.0F, -2.8108F, 6.8308F, 2.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, -1.0F, 0.0F, 0.2618F, 0.0F, 0.0F));
      partdefinition.m_171599_("tail2", CubeListBuilder.m_171558_().m_171514_(29, 12).m_171488_(-1.6471F, -2.8637F, 6.6334F, 3.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, -1.0F, 0.0F, 0.2618F, -0.2618F, 0.0F));
      partdefinition.m_171599_("tail3", CubeListBuilder.m_171558_().m_171514_(29, 12).m_171480_().m_171488_(-1.3529F, -2.8637F, 6.6334F, 3.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(0.0F, -1.0F, 0.0F, 0.2618F, 0.2618F, 0.0F));
      partdefinition.m_171599_("rightleg1", CubeListBuilder.m_171558_().m_171514_(25, 0).m_171488_(-2.6224F, 0.5776F, 1.1989F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, -1.0F, 0.0F, 0.7854F, -0.1745F, 0.0F));
      partdefinition.m_171599_("rightleg2", CubeListBuilder.m_171558_().m_171514_(38, 0).m_171488_(-2.1224F, 4.5776F, 1.6989F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, -1.0F, 0.0F, 0.7854F, -0.1745F, 0.0F));
      partdefinition.m_171599_("rightfoot1", CubeListBuilder.m_171558_().m_171514_(38, 5).m_171488_(-2.0956F, 5.7364F, -2.1297F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, -1.0F, 0.0F, 1.309F, -0.1745F, 0.0F));
      partdefinition.m_171599_("leftleg1", CubeListBuilder.m_171558_().m_171514_(25, 0).m_171488_(-0.3776F, 0.5776F, 1.1989F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, -1.0F, 0.0F, 0.7854F, 0.1745F, 0.0F));
      partdefinition.m_171599_("leftleg2", CubeListBuilder.m_171558_().m_171514_(38, 0).m_171488_(0.1224F, 4.5776F, 1.6989F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, -1.0F, 0.0F, 0.7854F, 0.1745F, 0.0F));
      partdefinition.m_171599_("leftfoot1", CubeListBuilder.m_171558_().m_171514_(38, 5).m_171488_(0.0956F, 5.7364F, -2.1297F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, -1.0F, 0.0F, 1.309F, 0.1745F, 0.0F));
      PartDefinition rightWing1 = partdefinition.m_171599_("rightWing1", CubeListBuilder.m_171558_().m_171514_(47, 0).m_171488_(-8.6204F, -5.8085F, -3.6132F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, -1.0F, 0.0F, -0.0373F, -0.2592F, 0.1445F));
      PartDefinition rightWing3 = rightWing1.m_171599_("rightWing3", CubeListBuilder.m_171558_().m_171514_(17, 33).m_171488_(-6.5F, 0.0F, 0.0F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-16.6203F, -6.0085F, -4.1132F, 0.0F, 0.2443F, 0.0F));
      rightWing3.m_171599_("rightWing5", CubeListBuilder.m_171558_().m_171514_(10, 44).m_171488_(-6.2F, 0.5F, 1.0F, 6.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 0.0F, 0.0F, -0.0175F, 0.0F, 0.0F));
      PartDefinition rightWing2 = rightWing1.m_171599_("rightWing2", CubeListBuilder.m_171558_().m_171514_(17, 29).m_171488_(-9.5F, 0.0F, 0.5F, 9.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-7.7203F, -5.4085F, -3.6132F, -0.0358F, -0.1004F, 0.0607F));
      rightWing2.m_171599_("rightWing4", CubeListBuilder.m_171558_().m_171514_(10, 37).m_171488_(-8.8F, 0.5F, 1.0F, 12.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 0.0F, 0.0F, 0.0175F, 0.0456F, 0.0F));
      PartDefinition leftWing1 = partdefinition.m_171599_("leftWing1", CubeListBuilder.m_171558_().m_171514_(47, 0).m_171488_(4.6204F, -5.8082F, -3.6137F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, -1.0F, 0.0F, -0.0374F, 0.2592F, -0.1445F));
      PartDefinition leftWing3 = leftWing1.m_171599_("leftWing3", CubeListBuilder.m_171558_().m_171514_(17, 33).m_171488_(0.5F, 0.0F, 0.0F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(16.6203F, -5.9082F, -4.1137F, 0.0F, -0.2443F, 0.0F));
      leftWing3.m_171599_("leftWing5", CubeListBuilder.m_171558_().m_171514_(10, 44).m_171488_(0.5F, 0.5F, 1.0F, 6.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-0.15F, -0.1F, 0.0F, -0.0175F, 0.0F, 0.0F));
      PartDefinition leftWing2 = leftWing1.m_171599_("leftWing2", CubeListBuilder.m_171558_().m_171514_(17, 29).m_171488_(0.5F, 0.0F, 0.5F, 9.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(7.6204F, -5.4082F, -3.6137F, -0.0358F, 0.1004F, -0.0607F));
      leftWing2.m_171599_("leftWing4", CubeListBuilder.m_171558_().m_171514_(10, 37).m_171488_(-2.8F, 0.5F, 1.0F, 12.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.1F, -0.1F, 0.0F, 0.0175F, -0.0456F, 0.0F));
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   public void m_6973_(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      int cycleIndexFly = (int)((double)entity.f_19797_ * 1.85 % (double)WING_ANIMATION_FRAMES.length);
      if (!Minecraft.m_91087_().m_91104_()) {
         this.rightWing1.f_104205_ = (float)Math.toRadians((double)WING_ANIMATION_FRAMES[cycleIndexFly]);
         this.leftWing1.f_104205_ = (float)(Math.toRadians((double)WING_ANIMATION_FRAMES[cycleIndexFly]) * (double)-1.0F);
      }

   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.body1.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.head.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.tuft1.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.tuft2.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.tuft3.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.beak1.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.beak2.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.beak3.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.beak4.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.tail1.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.tail2.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.tail3.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.rightleg1.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.rightleg2.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.rightfoot1.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.leftleg1.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.leftleg2.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.leftfoot1.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.rightWing1.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.leftWing1.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }
}
