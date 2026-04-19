package xyz.pixelatedw.mineminenomi.models.entities.projectiles;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
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

public class HeartModel<T extends Entity> extends EntityModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "heart"), "main");
   private final ModelPart pellicle;
   private final ModelPart heart4;
   private final ModelPart heart15;
   private final ModelPart heart9;
   private final ModelPart heart11;
   private final ModelPart heart16;
   private final ModelPart heart2;
   private final ModelPart heart6;
   private final ModelPart heart12;
   private final ModelPart heart7;
   private final ModelPart heart5;
   private final ModelPart heart13;
   private final ModelPart heart18;
   private final ModelPart heart8;
   private final ModelPart heart14;
   private final ModelPart heart1;
   private final ModelPart heart17;
   private final ModelPart heart3;
   private final ModelPart heart10;

   public HeartModel(ModelPart root) {
      this.pellicle = root.m_171324_("pellicle");
      this.heart4 = this.pellicle.m_171324_("heart4");
      this.heart15 = this.pellicle.m_171324_("heart15");
      this.heart9 = this.pellicle.m_171324_("heart9");
      this.heart11 = this.pellicle.m_171324_("heart11");
      this.heart16 = this.pellicle.m_171324_("heart16");
      this.heart2 = this.pellicle.m_171324_("heart2");
      this.heart6 = this.pellicle.m_171324_("heart6");
      this.heart12 = this.pellicle.m_171324_("heart12");
      this.heart7 = this.pellicle.m_171324_("heart7");
      this.heart5 = this.pellicle.m_171324_("heart5");
      this.heart13 = this.pellicle.m_171324_("heart13");
      this.heart18 = this.pellicle.m_171324_("heart18");
      this.heart8 = this.pellicle.m_171324_("heart8");
      this.heart14 = this.pellicle.m_171324_("heart14");
      this.heart1 = this.pellicle.m_171324_("heart1");
      this.heart17 = this.pellicle.m_171324_("heart17");
      this.heart3 = this.pellicle.m_171324_("heart3");
      this.heart10 = this.pellicle.m_171324_("heart10");
   }

   public static LayerDefinition createLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition pellicle = partdefinition.m_171599_("pellicle", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      pellicle.m_171599_("heart4", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-0.5F, -2.5F, -0.5F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(4.7045F, 6.7034F, 0.0F, 0.0F, 0.0F, -2.4958F));
      pellicle.m_171599_("heart15", CubeListBuilder.m_171558_().m_171514_(10, 0).m_171488_(-0.5F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.m_171423_(-3.1279F, -7.2051F, 0.0F, 0.0F, 0.0F, -1.0123F));
      pellicle.m_171599_("heart9", CubeListBuilder.m_171558_().m_171514_(5, 0).m_171488_(-0.5F, -2.0F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-9.3162F, -4.8406F, 0.0F, 0.0F, 0.0F, -2.8972F));
      pellicle.m_171599_("heart11", CubeListBuilder.m_171558_().m_171514_(10, 0).m_171488_(-0.5F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.m_171423_(-7.803F, -7.523F, 0.0F, 0.0F, 0.0F, -2.3213F));
      pellicle.m_171599_("heart16", CubeListBuilder.m_171558_().m_171514_(10, 0).m_171488_(-0.5F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(3.1279F, -7.2051F, 0.0F, 0.0F, 0.0F, 1.0123F));
      pellicle.m_171599_("heart2", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-0.5F, -2.5F, -0.5F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.m_171423_(1.4983F, 10.2634F, 0.0F, 0.0F, 0.0F, -2.3387F));
      pellicle.m_171599_("heart6", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-0.5F, -2.5F, -0.5F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(7.3737F, 2.6926F, 0.0F, 0.0F, 0.0F, -2.6093F));
      pellicle.m_171599_("heart12", CubeListBuilder.m_171558_().m_171514_(10, 0).m_171488_(-0.5F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(7.803F, -7.523F, 0.0F, 0.0F, 0.0F, 2.3213F));
      pellicle.m_171599_("heart7", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-0.5F, -2.0F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.m_171423_(-9.1513F, -1.2225F, 0.0F, 0.0F, 0.0F, 2.8623F));
      pellicle.m_171599_("heart5", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-0.5F, -2.5F, -0.5F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-7.3737F, 2.6926F, 0.0F, 0.0F, 0.0F, 2.6093F));
      pellicle.m_171599_("heart13", CubeListBuilder.m_171558_().m_171514_(10, 0).m_171480_().m_171488_(-0.5F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(-5.6185F, -8.1653F, 0.0F, 0.0F, 0.0F, -1.4137F));
      pellicle.m_171599_("heart18", CubeListBuilder.m_171558_().m_171514_(10, 0).m_171488_(-0.5F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.m_171423_(0.8509F, -5.5358F, 0.0F, 0.0F, 0.0F, 0.8727F));
      pellicle.m_171599_("heart8", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-0.5F, -2.0F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(9.1513F, -1.2225F, 0.0F, 0.0F, 0.0F, -2.8623F));
      pellicle.m_171599_("heart14", CubeListBuilder.m_171558_().m_171514_(10, 0).m_171488_(-0.5F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.m_171423_(5.6185F, -8.1653F, 0.0F, 0.0F, 0.0F, 1.4137F));
      pellicle.m_171599_("heart1", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171480_().m_171488_(-0.5F, -2.5F, -0.5F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(-1.4983F, 10.2634F, 0.0F, 0.0F, 0.0F, 2.3387F));
      pellicle.m_171599_("heart17", CubeListBuilder.m_171558_().m_171514_(10, 0).m_171488_(-0.5F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-0.8509F, -5.5358F, 0.0F, 0.0F, 0.0F, -0.8727F));
      pellicle.m_171599_("heart3", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-0.5F, -2.5F, -0.5F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.m_171423_(-4.7045F, 6.7034F, 0.0F, 0.0F, 0.0F, 2.4958F));
      pellicle.m_171599_("heart10", CubeListBuilder.m_171558_().m_171514_(5, 0).m_171488_(-0.5F, -2.0F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.m_171423_(9.3162F, -4.8406F, 0.0F, 0.0F, 0.0F, 2.8972F));
      return LayerDefinition.m_171565_(meshdefinition, 64, 32);
   }

   public void m_6973_(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.pellicle.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }
}
