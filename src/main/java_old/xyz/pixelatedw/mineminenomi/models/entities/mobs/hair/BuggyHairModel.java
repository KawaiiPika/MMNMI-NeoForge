package xyz.pixelatedw.mineminenomi.models.entities.mobs.hair;

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

public class BuggyHairModel<T extends LivingEntity> extends HumanoidModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "buggy_hair"), "main");
   private final ModelPart leftHair;
   private final ModelPart leftHair2;
   private final ModelPart leftHair4;
   private final ModelPart leftHair9;
   private final ModelPart leftHair8;
   private final ModelPart leftHair6;
   private final ModelPart leftHair7;
   private final ModelPart leftHair5;
   private final ModelPart leftHair3;
   private final ModelPart rightHair;
   private final ModelPart rightHair2;
   private final ModelPart rightHair3;
   private final ModelPart rightHair4;
   private final ModelPart rightHair5;
   private final ModelPart rightHair6;
   private final ModelPart rightHair7;
   private final ModelPart rightHair8;
   private final ModelPart rightHair9;

   public BuggyHairModel(ModelPart root) {
      super(root);
      this.leftHair = this.f_102808_.m_171324_("leftHair");
      this.leftHair2 = this.leftHair.m_171324_("leftHair2");
      this.leftHair4 = this.leftHair2.m_171324_("leftHair4");
      this.leftHair9 = this.leftHair2.m_171324_("leftHair9");
      this.leftHair8 = this.leftHair2.m_171324_("leftHair8");
      this.leftHair6 = this.leftHair2.m_171324_("leftHair6");
      this.leftHair7 = this.leftHair2.m_171324_("leftHair7");
      this.leftHair5 = this.leftHair2.m_171324_("leftHair5");
      this.leftHair3 = this.leftHair2.m_171324_("leftHair3");
      this.rightHair = this.f_102808_.m_171324_("rightHair");
      this.rightHair2 = this.rightHair.m_171324_("rightHair2");
      this.rightHair3 = this.rightHair2.m_171324_("rightHair3");
      this.rightHair4 = this.rightHair2.m_171324_("rightHair4");
      this.rightHair5 = this.rightHair2.m_171324_("rightHair5");
      this.rightHair6 = this.rightHair2.m_171324_("rightHair6");
      this.rightHair7 = this.rightHair2.m_171324_("rightHair7");
      this.rightHair8 = this.rightHair2.m_171324_("rightHair8");
      this.rightHair9 = this.rightHair2.m_171324_("rightHair9");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = HumanoidModel.m_170681_(CubeDeformation.f_171458_, 0.0F);
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition head = partdefinition.m_171599_("head", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, -2.5F, 0.0F));
      PartDefinition leftHair = head.m_171599_("leftHair", CubeListBuilder.m_171558_().m_171514_(0, 11).m_171488_(-2.0F, -5.0F, -2.0F, 3.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(4.5F, -7.5F, 0.0F, 0.0F, 0.0F, 0.9599F));
      PartDefinition leftHair2 = leftHair.m_171599_("leftHair2", CubeListBuilder.m_171558_().m_171514_(9, 2).m_171488_(-1.85F, -2.9F, -2.0F, 3.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.25F, -4.5F, 0.0F, 0.0F, 0.0F, 0.5236F));
      leftHair2.m_171599_("leftHair4", CubeListBuilder.m_171558_().m_171514_(0, 4).m_171488_(-1.35F, -3.9F, -1.5F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-0.5F, -1.5F, -0.25F, 0.2477F, -0.1198F, 0.2333F));
      leftHair2.m_171599_("leftHair9", CubeListBuilder.m_171558_().m_171514_(0, 4).m_171488_(-1.35F, -3.9F, -1.5F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.5F, -1.5F, 1.25F, -0.3768F, 0.0288F, 0.2172F));
      leftHair2.m_171599_("leftHair8", CubeListBuilder.m_171558_().m_171514_(0, 4).m_171488_(-1.35F, -3.9F, -1.5F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-0.5F, -1.5F, 1.25F, -0.289F, -0.0337F, 0.0081F));
      leftHair2.m_171599_("leftHair6", CubeListBuilder.m_171558_().m_171514_(0, 4).m_171488_(-1.35F, -3.9F, -1.5F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-0.5F, -1.5F, 0.25F, 0.1135F, -0.0827F, -0.1597F));
      leftHair2.m_171599_("leftHair7", CubeListBuilder.m_171558_().m_171514_(0, 4).m_171488_(-1.35F, -3.9F, -1.5F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-0.25F, -1.5F, 0.75F, -0.0577F, -0.1198F, 0.2333F));
      leftHair2.m_171599_("leftHair5", CubeListBuilder.m_171558_().m_171514_(0, 4).m_171488_(-1.35F, -3.9F, -1.5F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.5F, -1.5F, 0.25F, 0.0512F, -0.1307F, 0.4089F));
      leftHair2.m_171599_("leftHair3", CubeListBuilder.m_171558_().m_171514_(0, 4).m_171488_(-1.35F, -3.9F, -1.5F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.5F, -1.5F, -0.25F, 0.4659F, -0.1198F, 0.2333F));
      PartDefinition rightHair = head.m_171599_("rightHair", CubeListBuilder.m_171558_().m_171514_(0, 11).m_171480_().m_171488_(-1.0F, -5.0F, -1.0F, 3.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(-4.5F, -7.5F, -1.0F, 0.0F, 0.0F, -0.9599F));
      PartDefinition rightHair2 = rightHair.m_171599_("rightHair2", CubeListBuilder.m_171558_().m_171514_(9, 2).m_171480_().m_171488_(-1.15F, -2.9F, -1.0F, 3.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(-0.25F, -4.5F, 0.0F, 0.0F, 0.0F, -0.5236F));
      rightHair2.m_171599_("rightHair3", CubeListBuilder.m_171558_().m_171514_(0, 4).m_171480_().m_171488_(-0.65F, -3.9F, -0.5F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(0.5F, -1.5F, -0.25F, 0.2477F, 0.1198F, -0.2333F));
      rightHair2.m_171599_("rightHair4", CubeListBuilder.m_171558_().m_171514_(0, 4).m_171480_().m_171488_(-0.65F, -3.9F, -0.5F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(-0.5F, -1.5F, 1.25F, -0.3768F, -0.0288F, -0.2172F));
      rightHair2.m_171599_("rightHair5", CubeListBuilder.m_171558_().m_171514_(0, 4).m_171480_().m_171488_(-0.65F, -3.9F, -0.5F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(0.5F, -1.5F, 1.25F, -0.289F, 0.0337F, -0.0081F));
      rightHair2.m_171599_("rightHair6", CubeListBuilder.m_171558_().m_171514_(0, 4).m_171480_().m_171488_(-0.65F, -3.9F, -0.5F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(0.5F, -1.5F, 0.25F, 0.1135F, 0.0827F, 0.1597F));
      rightHair2.m_171599_("rightHair7", CubeListBuilder.m_171558_().m_171514_(0, 4).m_171480_().m_171488_(-0.65F, -3.9F, -0.5F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(0.25F, -1.5F, 0.75F, -0.0577F, 0.1198F, -0.2333F));
      rightHair2.m_171599_("rightHair8", CubeListBuilder.m_171558_().m_171514_(0, 4).m_171480_().m_171488_(-0.65F, -3.9F, -0.5F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(-0.5F, -1.5F, 0.25F, 0.0512F, 0.1307F, -0.4089F));
      rightHair2.m_171599_("rightHair9", CubeListBuilder.m_171558_().m_171514_(0, 4).m_171480_().m_171488_(-0.65F, -3.9F, -0.5F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(-0.5F, -1.5F, -0.25F, 0.4659F, 0.1198F, -0.2333F));
      return LayerDefinition.m_171565_(meshdefinition, 32, 32);
   }

   public void m_6973_(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      super.m_6973_(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.f_102808_.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }
}
