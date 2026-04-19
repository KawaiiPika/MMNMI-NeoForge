package xyz.pixelatedw.mineminenomi.models.armors;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidArmorModel;
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

public class BicorneModel<T extends LivingEntity> extends HumanoidArmorModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "bicorne"), "main");
   private final ModelPart bicorne;
   private final ModelPart topFront;
   private final ModelPart topBack;
   private final ModelPart leftSide;
   private final ModelPart subLeftSide1;
   private final ModelPart subLeftSide3;
   private final ModelPart subLeftSide2;
   private final ModelPart rightSide;
   private final ModelPart rightSide1;
   private final ModelPart rightSide2;
   private final ModelPart rightSide3;

   public BicorneModel(ModelPart root) {
      super(root);
      this.bicorne = root.m_171324_("bicorne");
      this.topFront = this.bicorne.m_171324_("topFront");
      this.topBack = this.bicorne.m_171324_("topBack");
      this.leftSide = this.bicorne.m_171324_("leftSide");
      this.subLeftSide1 = this.leftSide.m_171324_("subLeftSide1");
      this.subLeftSide3 = this.leftSide.m_171324_("subLeftSide3");
      this.subLeftSide2 = this.leftSide.m_171324_("subLeftSide2");
      this.rightSide = this.bicorne.m_171324_("rightSide");
      this.rightSide1 = this.rightSide.m_171324_("rightSide1");
      this.rightSide2 = this.rightSide.m_171324_("rightSide2");
      this.rightSide3 = this.rightSide.m_171324_("rightSide3");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = HumanoidModel.m_170681_(new CubeDeformation(0.0F), 0.0F);
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition bicorne = partdefinition.m_171599_("bicorne", CubeListBuilder.m_171558_().m_171514_(0, 23).m_171488_(-9.0F, -8.0F, -4.0F, 18.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)).m_171514_(0, 11).m_171488_(-4.0F, -9.0F, -4.0F, 9.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)).m_171514_(0, 0).m_171488_(-3.0F, -11.0F, -4.0F, 6.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, -2.5F, 0.0F));
      bicorne.m_171599_("topFront", CubeListBuilder.m_171558_().m_171514_(0, 37).m_171488_(-4.0F, -5.0F, -4.5F, 8.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)).m_171514_(0, 34).m_171488_(-4.0F, -6.0F, -4.5F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).m_171514_(19, 40).m_171488_(-6.0F, -2.0F, -4.5F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).m_171514_(26, 38).m_171488_(6.0F, -2.0F, -4.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).m_171514_(19, 37).m_171488_(4.0F, -3.0F, -4.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).m_171514_(26, 38).m_171488_(-8.0F, -2.0F, -4.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).m_171514_(19, 37).m_171488_(-6.0F, -3.0F, -4.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).m_171514_(19, 40).m_171488_(4.0F, -2.0F, -4.5F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).m_171514_(26, 41).m_171488_(-8.0F, -1.0F, -4.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).m_171514_(26, 41).m_171488_(6.0F, -1.0F, -4.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).m_171514_(19, 34).m_171488_(-5.0F, -5.0F, -4.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).m_171514_(19, 34).m_171488_(4.0F, -5.0F, -4.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, -6.5F, -0.5F));
      bicorne.m_171599_("topBack", CubeListBuilder.m_171558_().m_171514_(0, 37).m_171488_(-4.0F, -5.0F, 3.5F, 8.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)).m_171514_(19, 40).m_171488_(-6.0F, -2.0F, 3.5F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).m_171514_(19, 40).m_171488_(4.0F, -2.0F, 3.5F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).m_171514_(26, 41).m_171488_(-8.0F, -1.0F, 3.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).m_171514_(26, 41).m_171488_(6.0F, -1.0F, 3.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).m_171514_(26, 38).m_171488_(-8.0F, -2.0F, 3.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).m_171514_(19, 37).m_171488_(-6.0F, -3.0F, 3.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).m_171514_(0, 34).m_171488_(-4.0F, -6.0F, 3.5F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).m_171514_(19, 37).m_171488_(4.0F, -3.0F, 3.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).m_171514_(19, 34).m_171488_(4.0F, -5.0F, 3.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).m_171514_(19, 34).m_171488_(-5.0F, -5.0F, 3.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).m_171514_(26, 38).m_171488_(6.0F, -2.0F, 3.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, -6.5F, 0.5F));
      PartDefinition leftSide = bicorne.m_171599_("leftSide", CubeListBuilder.m_171558_(), PartPose.m_171419_(8.75F, -8.0F, 4.0F));
      leftSide.m_171599_("subLeftSide1", CubeListBuilder.m_171558_().m_171514_(33, 41).m_171488_(-1.35F, 0.5F, -0.2F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).m_171514_(33, 38).m_171488_(-1.35F, -0.5F, -0.2F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 0.0F, -8.0F, 0.0F, -0.7854F, 0.0F));
      leftSide.m_171599_("subLeftSide3", CubeListBuilder.m_171558_().m_171514_(33, 41).m_171488_(-1.5F, 0.5F, -0.5F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).m_171514_(33, 38).m_171488_(-1.5F, -0.5F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-0.1061F, 0.0F, -0.3182F, 0.0F, 0.7854F, 0.0F));
      leftSide.m_171599_("subLeftSide2", CubeListBuilder.m_171558_().m_171514_(42, 41).m_171488_(-0.05F, 0.5F, -0.55F, 6.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).m_171514_(42, 38).m_171488_(-0.05F, -0.5F, -0.55F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.75F, 0.0F, -1.0F, 0.0F, 1.5708F, 0.0F));
      PartDefinition rightSide = bicorne.m_171599_("rightSide", CubeListBuilder.m_171558_(), PartPose.m_171419_(-8.75F, -8.0F, 4.0F));
      rightSide.m_171599_("rightSide1", CubeListBuilder.m_171558_().m_171514_(33, 41).m_171480_().m_171488_(-1.65F, 0.5F, -0.2F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).m_171555_(false).m_171514_(33, 38).m_171480_().m_171488_(-1.65F, -0.5F, -0.2F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(0.0F, 0.0F, -8.0F, 0.0F, 0.7854F, 0.0F));
      rightSide.m_171599_("rightSide2", CubeListBuilder.m_171558_().m_171514_(33, 41).m_171480_().m_171488_(-1.5F, 0.5F, -0.5F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).m_171555_(false).m_171514_(33, 38).m_171480_().m_171488_(-1.5F, -0.5F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(0.1061F, 0.0F, -0.3182F, 0.0F, -0.7854F, 0.0F));
      rightSide.m_171599_("rightSide3", CubeListBuilder.m_171558_().m_171514_(42, 41).m_171480_().m_171488_(-5.95F, 0.5F, -0.55F, 6.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).m_171555_(false).m_171514_(42, 38).m_171480_().m_171488_(-5.95F, -0.5F, -0.55F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(-0.75F, 0.0F, -1.0F, 0.0F, -1.5708F, 0.0F));
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   public void m_6973_(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.bicorne.m_104315_(this.f_102809_);
      this.bicorne.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }
}
