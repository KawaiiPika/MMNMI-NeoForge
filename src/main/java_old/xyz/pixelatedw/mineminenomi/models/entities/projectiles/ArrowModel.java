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

public class ArrowModel<T extends Entity> extends EntityModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "arrow"), "main");
   private final ModelPart body;
   private final ModelPart tail1;
   private final ModelPart tail2;
   private final ModelPart head1;
   private final ModelPart head2;

   public ArrowModel(ModelPart root) {
      this.body = root.m_171324_("body");
      this.tail1 = this.body.m_171324_("tail1");
      this.tail2 = this.body.m_171324_("tail2");
      this.head1 = this.body.m_171324_("head1");
      this.head2 = this.head1.m_171324_("head2");
   }

   public static LayerDefinition createLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition body = partdefinition.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-0.5F, -0.5F, -5.0F, 1.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 0.5F, 0.0F));
      body.m_171599_("tail1", CubeListBuilder.m_171558_().m_171514_(15, 0).m_171488_(-5.0F, -1.7F, 0.4F, 4.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, -0.5F, 0.0F, 0.0F, 1.5708F, 0.7854F));
      body.m_171599_("tail2", CubeListBuilder.m_171558_().m_171514_(15, 5).m_171488_(-5.0F, -1.8F, -0.3F, 4.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, -0.5F, 0.0F, 0.0F, 1.5708F, -0.7854F));
      PartDefinition head1 = body.m_171599_("head1", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-1.0F, -0.5F, -6.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, -0.5F, 0.0F));
      head1.m_171599_("head2", CubeListBuilder.m_171558_().m_171514_(0, 4).m_171488_(-0.5F, 0.0F, -7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      return LayerDefinition.m_171565_(meshdefinition, 32, 16);
   }

   public void m_6973_(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.body.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }
}
