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

public class BaraCarBodyModel<T extends LivingEntity> extends HumanoidModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "bara_car_body"), "main");

   public BaraCarBodyModel(ModelPart root) {
      super(root);
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = HumanoidModel.m_170681_(CubeDeformation.f_171458_, 0.0F);
      PartDefinition partdefinition = meshdefinition.m_171576_();
      partdefinition.m_171599_("right_pants", CubeListBuilder.m_171558_().m_171514_(0, 32).m_171488_(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.m_171423_(-3.5F, 20.0F, 4.75F, 1.7279F, -0.2618F, 0.1745F));
      partdefinition.m_171599_("right_leg", CubeListBuilder.m_171558_().m_171514_(0, 16).m_171488_(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-3.5F, 20.0F, 4.75F, 1.7279F, -0.2618F, 0.1745F));
      partdefinition.m_171599_("left_pants", CubeListBuilder.m_171558_().m_171514_(0, 48).m_171488_(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.m_171423_(3.5F, 20.0F, 4.75F, 1.7279F, 0.2618F, -0.1745F));
      partdefinition.m_171599_("left_leg", CubeListBuilder.m_171558_().m_171514_(16, 48).m_171488_(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(3.5F, 20.0F, 4.75F, 1.7279F, 0.2618F, -0.1745F));
      partdefinition.m_171599_("right_sleeve", CubeListBuilder.m_171558_().m_171514_(40, 32).m_171488_(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.m_171423_(-5.0F, 20.0F, -6.5F, -1.309F, 0.0F, 0.0F));
      partdefinition.m_171599_("right_arm", CubeListBuilder.m_171558_().m_171514_(40, 16).m_171488_(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-5.0F, 20.0F, -6.5F, -1.309F, 0.0F, 0.0F));
      partdefinition.m_171599_("left_sleeve", CubeListBuilder.m_171558_().m_171514_(48, 48).m_171488_(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.m_171423_(5.0F, 20.0F, -6.5F, -1.309F, 0.0F, 0.0F));
      partdefinition.m_171599_("left_arm", CubeListBuilder.m_171558_().m_171514_(32, 48).m_171488_(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(5.0F, 20.0F, -6.5F, -1.309F, 0.0F, 0.0F));
      partdefinition.m_171599_("jacket", CubeListBuilder.m_171558_().m_171514_(16, 32).m_171488_(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.m_171423_(0.0F, 20.0F, -7.0F, 1.5708F, 0.0F, 0.0F));
      partdefinition.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(16, 16).m_171488_(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 20.0F, -7.0F, 1.5708F, 0.0F, 0.0F));
      partdefinition.m_171599_("headwear", CubeListBuilder.m_171558_().m_171514_(32, 0).m_171488_(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.5F)), PartPose.m_171419_(0.0F, 18.0F, 0.0F));
      partdefinition.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 18.0F, 0.0F));
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   public void m_6973_(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      super.m_7695_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }
}
