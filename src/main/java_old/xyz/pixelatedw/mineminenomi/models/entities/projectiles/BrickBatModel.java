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
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class BrickBatModel<T extends Entity> extends EntityModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "brick_bat"), "main");
   private final ModelPart body;
   private final ModelPart body3;
   private final ModelPart leftWing;
   private final ModelPart leftWing2;
   private final ModelPart rightWing;
   private final ModelPart rightWing2;
   private final ModelPart body2;
   private final ModelPart body4;
   private final ModelPart leftEar;
   private final ModelPart rightEar;

   public BrickBatModel(ModelPart root) {
      this.body = root.m_171324_("body");
      this.body3 = this.body.m_171324_("body3");
      this.leftWing = this.body.m_171324_("leftWing");
      this.leftWing2 = this.leftWing.m_171324_("leftWing2");
      this.rightWing = this.body.m_171324_("rightWing");
      this.rightWing2 = this.rightWing.m_171324_("rightWing2");
      this.body2 = this.body.m_171324_("body2");
      this.body4 = this.body.m_171324_("body4");
      this.leftEar = this.body.m_171324_("leftEar");
      this.rightEar = this.body.m_171324_("rightEar");
   }

   public static LayerDefinition createLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition body = partdefinition.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-2.5F, -2.5F, -2.5F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      body.m_171599_("body3", CubeListBuilder.m_171558_().m_171514_(44, 0).m_171488_(-2.0F, -2.0F, -3.0F, 4.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      PartDefinition leftWing = body.m_171599_("leftWing", CubeListBuilder.m_171558_().m_171514_(41, 12).m_171488_(0.0F, 0.0F, 0.0F, 6.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(1.0F, 0.0F, -1.0F, 0.8727F, 0.0F, -0.4363F));
      leftWing.m_171599_("leftWing2", CubeListBuilder.m_171558_().m_171514_(54, 12).m_171488_(0.0F, 0.0F, 0.0F, 5.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(6.0F, 0.0F, 0.0F, 0.0F, 0.5236F, 0.0F));
      PartDefinition rightWing = body.m_171599_("rightWing", CubeListBuilder.m_171558_().m_171514_(41, 12).m_171480_().m_171488_(-6.0F, 0.0F, 0.0F, 6.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(-1.0F, 0.0F, -1.0F, 0.8727F, 0.0F, 0.4363F));
      rightWing.m_171599_("rightWing2", CubeListBuilder.m_171558_().m_171514_(54, 12).m_171480_().m_171488_(-5.0F, 0.0F, 0.0F, 5.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(-6.0F, 0.0F, 0.0F, 0.0F, -0.5236F, 0.0F));
      body.m_171599_("body2", CubeListBuilder.m_171558_().m_171514_(21, 0).m_171488_(-3.0F, -2.0F, -2.0F, 6.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      body.m_171599_("body4", CubeListBuilder.m_171558_().m_171514_(24, 9).m_171488_(-2.0F, -3.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      body.m_171599_("leftEar", CubeListBuilder.m_171558_().m_171514_(0, 11).m_171488_(-0.5F, -1.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(1.9F, -2.6F, 0.0F, 0.0F, 0.0F, 0.5236F));
      body.m_171599_("rightEar", CubeListBuilder.m_171558_().m_171514_(0, 11).m_171480_().m_171488_(-0.5F, -1.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(-1.9F, -2.6F, 0.0F, 0.0F, 0.0F, -0.5236F));
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   public void m_6973_(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.rightWing.f_104205_ = 0.3F + Mth.m_14089_((float)entity.f_19797_) * 0.8F;
      this.leftWing.f_104205_ = -0.3F - Mth.m_14089_((float)entity.f_19797_) * 0.8F;
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.body.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }
}
