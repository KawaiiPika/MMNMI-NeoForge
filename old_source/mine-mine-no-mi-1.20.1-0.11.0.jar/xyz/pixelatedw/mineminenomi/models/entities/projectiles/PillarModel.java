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

public class PillarModel<T extends Entity> extends EntityModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "pillar"), "main");
   private final ModelPart pillar;

   public PillarModel(ModelPart root) {
      this.pillar = root.m_171324_("pillar");
   }

   public static LayerDefinition createLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      partdefinition.m_171599_("pillar", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-4.0F, -4.0F, 3.0F, 8.0F, 8.0F, 21.0F, new CubeDeformation(0.0F)).m_171514_(0, 29).m_171488_(-3.0F, -3.0F, -10.0F, 6.0F, 6.0F, 13.0F, new CubeDeformation(0.0F)).m_171514_(0, 0).m_171488_(-2.0F, -2.0F, -16.0F, 4.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)).m_171514_(0, 13).m_171488_(-1.0F, -1.0F, -21.0F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   public void m_6973_(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.pillar.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }
}
