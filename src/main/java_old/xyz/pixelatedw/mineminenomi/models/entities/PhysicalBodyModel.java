package xyz.pixelatedw.mineminenomi.models.entities;

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
import xyz.pixelatedw.mineminenomi.entities.PhysicalBodyEntity;

public class PhysicalBodyModel extends HumanoidModel<PhysicalBodyEntity> {
   public static final ModelLayerLocation WIDE_LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "physical_body_wide"), "main");
   public static final ModelLayerLocation SLIM_LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "physical_body_slim"), "main");

   public PhysicalBodyModel(ModelPart root) {
      super(root);
   }

   public static LayerDefinition createBodyLayer(boolean isSlim) {
      MeshDefinition meshdefinition = HumanoidModel.m_170681_(new CubeDeformation(0.0F), 0.0F);
      PartDefinition partdefinition = meshdefinition.m_171576_();
      float rightArmX1 = isSlim ? -2.0F : -3.0F;
      float rightArmX2 = isSlim ? 3.0F : 4.0F;
      partdefinition.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)).m_171514_(32, 0).m_171488_(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.5F)), PartPose.m_171423_(0.0F, 12.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
      partdefinition.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(16, 16).m_171488_(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).m_171514_(16, 32).m_171488_(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.m_171419_(0.0F, 12.0F, 0.0F));
      partdefinition.m_171599_("right_arm", CubeListBuilder.m_171558_().m_171514_(40, 16).m_171488_(rightArmX1, -2.0F, -2.0F, rightArmX2, 12.0F, 4.0F, new CubeDeformation(0.0F)).m_171514_(40, 32).m_171488_(rightArmX1, -2.0F, -2.0F, rightArmX2, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.m_171419_(-5.0F, 14.0F, 0.0F));
      partdefinition.m_171599_("left_arm", CubeListBuilder.m_171558_().m_171514_(32, 48).m_171488_(-1.0F, -2.0F, -2.0F, rightArmX2, 12.0F, 4.0F, new CubeDeformation(0.0F)).m_171514_(48, 48).m_171488_(-1.0F, -2.0F, -2.0F, rightArmX2, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.m_171419_(5.0F, 14.0F, 0.0F));
      partdefinition.m_171599_("right_leg", CubeListBuilder.m_171558_().m_171514_(0, 16).m_171488_(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).m_171514_(0, 32).m_171488_(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.m_171423_(-1.9F, 22.0F, -2.0F, -1.5708F, 0.0F, 0.0F));
      partdefinition.m_171599_("left_leg", CubeListBuilder.m_171558_().m_171514_(16, 48).m_171488_(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).m_171514_(0, 48).m_171488_(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.m_171423_(1.9F, 22.0F, -2.0F, -1.5708F, 0.0F, 0.0F));
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   public void setupAnim(PhysicalBodyEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      super.m_7695_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }
}
