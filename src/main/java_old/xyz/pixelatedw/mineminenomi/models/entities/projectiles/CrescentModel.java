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

public class CrescentModel<T extends Entity> extends EntityModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "crescent"), "main");
   private final ModelPart slash;

   public CrescentModel(ModelPart root) {
      this.slash = root.m_171324_("slash");
   }

   public static LayerDefinition createLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      partdefinition.m_171599_("slash", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-0.5F, 24.5F, 6.5526F, 1.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)).m_171514_(0, 0).m_171488_(-0.5F, 23.5F, 4.5526F, 1.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)).m_171514_(0, 0).m_171488_(-0.5F, 22.4F, 3.5526F, 1.0F, 1.0F, 7.0F, new CubeDeformation(0.1F)).m_171514_(0, 0).m_171488_(-0.5F, 21.2F, 2.5526F, 1.0F, 1.0F, 7.0F, new CubeDeformation(0.1F)).m_171514_(0, 0).m_171488_(-0.5F, 20.0F, 1.5526F, 1.0F, 1.0F, 7.0F, new CubeDeformation(0.1F)).m_171514_(0, 0).m_171488_(-0.5F, 17.7F, 0.5526F, 1.0F, 2.0F, 7.0F, new CubeDeformation(0.2F)).m_171514_(0, 0).m_171488_(-0.5F, 14.3F, -0.4474F, 1.0F, 3.0F, 7.0F, new CubeDeformation(0.2F)).m_171514_(0, 0).m_171488_(-0.5F, 10.8F, -1.4474F, 1.0F, 3.0F, 7.0F, new CubeDeformation(0.3F)).m_171514_(0, 0).m_171488_(-0.5F, 6.2F, -2.4474F, 1.0F, 4.0F, 7.0F, new CubeDeformation(0.3F)).m_171514_(0, 0).m_171488_(-0.5F, -5.5F, -3.4474F, 1.0F, 11.0F, 7.0F, new CubeDeformation(0.4F)).m_171514_(0, 0).m_171488_(-0.5F, -10.2F, -2.4474F, 1.0F, 4.0F, 7.0F, new CubeDeformation(0.3F)).m_171514_(0, 0).m_171488_(-0.5F, -13.8F, -1.4474F, 1.0F, 3.0F, 7.0F, new CubeDeformation(0.3F)).m_171514_(0, 0).m_171488_(-0.5F, -17.3F, -0.4474F, 1.0F, 3.0F, 7.0F, new CubeDeformation(0.2F)).m_171514_(0, 0).m_171488_(-0.5F, -19.7F, 0.5526F, 1.0F, 2.0F, 7.0F, new CubeDeformation(0.2F)).m_171514_(0, 0).m_171488_(-0.5F, -21.0F, 1.5526F, 1.0F, 1.0F, 7.0F, new CubeDeformation(0.1F)).m_171514_(0, 0).m_171488_(-0.5F, -22.2F, 2.5526F, 1.0F, 1.0F, 7.0F, new CubeDeformation(0.1F)).m_171514_(0, 0).m_171488_(-0.5F, -23.4F, 3.5526F, 1.0F, 1.0F, 7.0F, new CubeDeformation(0.1F)).m_171514_(0, 0).m_171488_(-0.5F, -24.5F, 4.5526F, 1.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)).m_171514_(0, 0).m_171488_(-0.5F, -25.5F, 6.5526F, 1.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0526F, -1.5F, -5.0F));
      return LayerDefinition.m_171565_(meshdefinition, 16, 16);
   }

   public void m_6973_(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.slash.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }
}
