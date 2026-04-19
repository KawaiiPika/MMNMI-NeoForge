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

public class RingModel<T extends Entity> extends EntityModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "ring"), "main");
   private final ModelPart ring;
   private final ModelPart circle1;
   private final ModelPart circle2;
   private final ModelPart circle3;
   private final ModelPart circle4;
   private final ModelPart circle5;
   private final ModelPart circle6;
   private final ModelPart circle7;
   private final ModelPart circle8;
   private final ModelPart circle9;
   private final ModelPart circle10;
   private final ModelPart circle11;
   private final ModelPart circle12;
   private final ModelPart pellicle;

   public RingModel(ModelPart root) {
      this.ring = root.m_171324_("ring");
      this.circle1 = this.ring.m_171324_("circle1");
      this.circle2 = this.ring.m_171324_("circle2");
      this.circle3 = this.ring.m_171324_("circle3");
      this.circle4 = this.ring.m_171324_("circle4");
      this.circle5 = this.ring.m_171324_("circle5");
      this.circle6 = this.ring.m_171324_("circle6");
      this.circle7 = this.ring.m_171324_("circle7");
      this.circle8 = this.ring.m_171324_("circle8");
      this.circle9 = this.ring.m_171324_("circle9");
      this.circle10 = this.ring.m_171324_("circle10");
      this.circle11 = this.ring.m_171324_("circle11");
      this.circle12 = this.ring.m_171324_("circle12");
      this.pellicle = this.ring.m_171324_("pellicle");
   }

   public static LayerDefinition createLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition ring = partdefinition.m_171599_("ring", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      ring.m_171599_("circle1", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-1.5F, -5.6F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.m_171423_(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.2618F));
      ring.m_171599_("circle2", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-1.5F, -5.6F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.2618F));
      ring.m_171599_("circle3", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-1.5F, -5.6F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.m_171423_(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.7854F));
      ring.m_171599_("circle4", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-1.5F, -5.6F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.309F));
      ring.m_171599_("circle5", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-1.5F, -5.6F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.m_171423_(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.8326F));
      ring.m_171599_("circle6", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-1.5F, -5.6F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 2.3562F));
      ring.m_171599_("circle7", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-1.6F, -5.6F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.m_171423_(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 2.8972F));
      ring.m_171599_("circle8", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-1.5F, -5.6F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -2.8972F));
      ring.m_171599_("circle9", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-1.6F, -5.6F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.m_171423_(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -2.3562F));
      ring.m_171599_("circle10", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-1.5F, -0.5F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-4.9003F, 1.3166F, 0.0F, 0.0F, 0.0F, -1.8326F));
      ring.m_171599_("circle11", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-1.5F, -5.6F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.m_171423_(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -1.309F));
      ring.m_171599_("circle12", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-1.5F, -5.6F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.7854F));
      ring.m_171599_("pellicle", CubeListBuilder.m_171558_().m_171514_(5, 10).m_171488_(-5.0F, -3.0F, 0.0F, 10.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)).m_171514_(9, 0).m_171488_(-3.0F, -5.0F, 0.0F, 6.0F, 2.0F, 0.0F, new CubeDeformation(0.001F)).m_171514_(9, 0).m_171488_(-3.0F, 3.0F, 0.0F, 6.0F, 2.0F, 0.0F, new CubeDeformation(0.001F)).m_171514_(6, 8).m_171488_(3.0F, 3.0F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.001F)).m_171514_(6, 8).m_171488_(3.0F, -4.0F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.001F)).m_171514_(6, 8).m_171488_(-4.0F, 3.0F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.001F)).m_171514_(6, 8).m_171488_(-4.0F, -4.0F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      return LayerDefinition.m_171565_(meshdefinition, 32, 16);
   }

   public void m_6973_(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.ring.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }
}
