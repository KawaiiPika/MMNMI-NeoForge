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

public class TridentModel<T extends Entity> extends EntityModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "trident"), "main");
   private final ModelPart spear;
   private final ModelPart tip;
   private final ModelPart spearTip1;
   private final ModelPart spearTip2;
   private final ModelPart spearTip3;
   private final ModelPart spearTip4;
   private final ModelPart spearTip5;
   private final ModelPart spearTip6;
   private final ModelPart spearTip7;

   public TridentModel(ModelPart root) {
      this.spear = root.m_171324_("spear");
      this.tip = this.spear.m_171324_("tip");
      this.spearTip1 = this.tip.m_171324_("spearTip1");
      this.spearTip2 = this.tip.m_171324_("spearTip2");
      this.spearTip3 = this.tip.m_171324_("spearTip3");
      this.spearTip4 = this.tip.m_171324_("spearTip4");
      this.spearTip5 = this.tip.m_171324_("spearTip5");
      this.spearTip6 = this.tip.m_171324_("spearTip6");
      this.spearTip7 = this.tip.m_171324_("spearTip7");
   }

   public static LayerDefinition createLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition spear = partdefinition.m_171599_("spear", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-0.5F, -0.5F, -10.0F, 1.0F, 1.0F, 30.0F, new CubeDeformation(0.0F)).m_171514_(0, 0).m_171488_(-1.0F, -1.0F, -8.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).m_171514_(0, 5).m_171488_(-3.0F, -0.5F, -12.0F, 6.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      PartDefinition tip = spear.m_171599_("tip", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 14.0F, -5.0F));
      tip.m_171599_("spearTip1", CubeListBuilder.m_171558_().m_171514_(0, 9).m_171488_(-0.5F, -0.5F, -2.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, -14.0F, -9.0F));
      tip.m_171599_("spearTip2", CubeListBuilder.m_171558_().m_171514_(0, 15).m_171488_(-0.5F, -0.5F, -2.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.01F)), PartPose.m_171423_(0.4659F, -14.0F, -8.462F, 0.0F, 0.1745F, 0.0F));
      tip.m_171599_("spearTip3", CubeListBuilder.m_171558_().m_171514_(0, 22).m_171488_(-0.5F, -0.5F, -2.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.02F)), PartPose.m_171423_(-0.4659F, -14.0F, -8.462F, 0.0F, -0.1745F, 0.0F));
      tip.m_171599_("spearTip4", CubeListBuilder.m_171558_().m_171514_(13, 19).m_171488_(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.01F)), PartPose.m_171423_(-2.5F, -14.0F, -7.5F, 3.1416F, 1.5708F, 3.1416F));
      tip.m_171599_("spearTip5", CubeListBuilder.m_171558_().m_171514_(18, 19).m_171488_(-1.0F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-2.1881F, -14.0F, -8.7687F, 0.0F, 1.2217F, 0.0F));
      tip.m_171599_("spearTip6", CubeListBuilder.m_171558_().m_171514_(13, 22).m_171488_(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.01F)), PartPose.m_171423_(2.5F, -14.0F, -7.5F, 3.1416F, 1.5708F, 3.1416F));
      tip.m_171599_("spearTip7", CubeListBuilder.m_171558_().m_171514_(18, 22).m_171488_(-1.0F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(2.1881F, -14.0F, -8.7687F, 0.0F, -1.2217F, 0.0F));
      return LayerDefinition.m_171565_(meshdefinition, 64, 32);
   }

   public void m_6973_(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.spear.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }
}
