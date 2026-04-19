package xyz.pixelatedw.mineminenomi.models;

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

public class SphereModel<T extends Entity> extends EntityModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "sphere"), "main");
   public final ModelPart sphere;

   public SphereModel(ModelPart root) {
      this.sphere = root.m_171324_("sphere");
   }

   public static LayerDefinition createLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      partdefinition.m_171599_("sphere", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).m_171514_(0, 8).m_171488_(-1.5F, -2.5F, -1.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).m_171514_(0, 12).m_171488_(-1.5F, 1.5F, -1.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).m_171514_(0, 16).m_171488_(-1.5F, -1.5F, -2.5F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).m_171514_(16, 0).m_171488_(-1.5F, -1.5F, 1.5F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).m_171514_(12, 8).m_171488_(-2.5F, -1.5F, -1.5F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).m_171514_(12, 14).m_171488_(1.5F, -1.5F, -1.5F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      return LayerDefinition.m_171565_(meshdefinition, 32, 32);
   }

   public void m_6973_(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.sphere.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }

   public void setRotateAngle(ModelPart model, float x, float y, float z) {
      model.f_104203_ = x;
      model.f_104204_ = y;
      model.f_104205_ = z;
   }
}
