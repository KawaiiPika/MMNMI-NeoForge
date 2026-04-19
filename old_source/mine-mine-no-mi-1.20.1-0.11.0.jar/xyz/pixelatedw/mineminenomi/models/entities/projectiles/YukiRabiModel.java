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

public class YukiRabiModel<T extends Entity> extends EntityModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "yuki_rabi"), "main");
   private final ModelPart rabi;
   private final ModelPart body1;
   private final ModelPart body2;
   private final ModelPart body3;
   private final ModelPart body4;
   private final ModelPart rightEar;
   private final ModelPart leftEar;

   public YukiRabiModel(ModelPart root) {
      this.rabi = root.m_171324_("rabi");
      this.body1 = this.rabi.m_171324_("body1");
      this.body2 = this.rabi.m_171324_("body2");
      this.body3 = this.rabi.m_171324_("body3");
      this.body4 = this.rabi.m_171324_("body4");
      this.rightEar = this.rabi.m_171324_("rightEar");
      this.leftEar = this.rabi.m_171324_("leftEar");
   }

   public static LayerDefinition createLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition rabi = partdefinition.m_171599_("rabi", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      rabi.m_171599_("body1", CubeListBuilder.m_171558_().m_171514_(25, 18).m_171488_(-3.5F, -3.5F, -3.5F, 7.0F, 7.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      rabi.m_171599_("body2", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-4.0F, -3.0F, -3.0F, 8.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      rabi.m_171599_("body3", CubeListBuilder.m_171558_().m_171514_(20, 4).m_171488_(-3.0F, -3.0F, -4.0F, 6.0F, 6.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      rabi.m_171599_("body4", CubeListBuilder.m_171558_().m_171514_(0, 14).m_171488_(-3.0F, -4.0F, -3.0F, 6.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      rabi.m_171599_("rightEar", CubeListBuilder.m_171558_().m_171514_(41, 1).m_171488_(-1.5F, -0.7F, -0.25F, 2.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-1.0F, -3.3F, -0.5F, 0.3665F, -0.0873F, -0.1745F));
      rabi.m_171599_("leftEar", CubeListBuilder.m_171558_().m_171514_(41, 1).m_171480_().m_171488_(-0.5F, -0.7F, -0.25F, 2.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(1.0F, -3.3F, -0.5F, 0.3665F, 0.0873F, 0.1745F));
      return LayerDefinition.m_171565_(meshdefinition, 64, 32);
   }

   public void m_6973_(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.rabi.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }
}
