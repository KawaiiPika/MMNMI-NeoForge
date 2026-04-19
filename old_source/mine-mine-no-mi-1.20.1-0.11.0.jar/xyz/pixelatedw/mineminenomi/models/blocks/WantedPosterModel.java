package xyz.pixelatedw.mineminenomi.models.blocks;

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

public class WantedPosterModel extends EntityModel<Entity> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "wanted_poster"), "main");
   public static final ModelLayerLocation FACE_LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "wanted_poster_face"), "main");
   public static final ModelLayerLocation FACE_OVERLAY_LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "wanted_poster_face_overlay"), "main");
   private final ModelPart poster;

   public WantedPosterModel(ModelPart root) {
      this.poster = root.m_171324_("poster");
   }

   public static LayerDefinition createLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      partdefinition.m_171599_("poster", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-10.0F, -15.0F, 0.0F, 20.0F, 30.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 1.0F, 0.0F));
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   public static LayerDefinition createFaceLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      partdefinition.m_171599_("poster", CubeListBuilder.m_171558_().m_171514_(7, 7).m_171488_(0.0F, 0.0F, 0.0F, 8.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 1.0F, 0.0F));
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   public static LayerDefinition createFaceOverlayLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      partdefinition.m_171599_("poster", CubeListBuilder.m_171558_().m_171514_(39, 7).m_171488_(0.0F, 0.0F, 0.0F, 8.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 1.0F, 0.0F));
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   public void m_6973_(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.poster.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }
}
