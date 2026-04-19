package xyz.pixelatedw.mineminenomi.models.entities.projectiles.effects;

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

public class SpiralEffectModel<T extends Entity> extends EntityModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "spiral_effect"), "main");
   private final ModelPart effect1;
   private final ModelPart effect1a;
   private final ModelPart effect1b;
   private final ModelPart effect1c;
   private final ModelPart effect1d;
   private final ModelPart effect2;
   private final ModelPart effect2a;
   private final ModelPart effect2b;
   private final ModelPart effect2c;
   private final ModelPart effect2d;
   private final ModelPart effect3;
   private final ModelPart effect3a;
   private final ModelPart effect3b;
   private final ModelPart effect3c;
   private final ModelPart effect3d;

   public SpiralEffectModel(ModelPart root) {
      this.effect1 = root.m_171324_("effect1");
      this.effect1a = this.effect1.m_171324_("effect1a");
      this.effect1b = this.effect1.m_171324_("effect1b");
      this.effect1c = this.effect1.m_171324_("effect1c");
      this.effect1d = this.effect1.m_171324_("effect1d");
      this.effect2 = root.m_171324_("effect2");
      this.effect2a = this.effect2.m_171324_("effect2a");
      this.effect2b = this.effect2.m_171324_("effect2b");
      this.effect2c = this.effect2.m_171324_("effect2c");
      this.effect2d = this.effect2.m_171324_("effect2d");
      this.effect3 = root.m_171324_("effect3");
      this.effect3a = this.effect3.m_171324_("effect3a");
      this.effect3b = this.effect3.m_171324_("effect3b");
      this.effect3c = this.effect3.m_171324_("effect3c");
      this.effect3d = this.effect3.m_171324_("effect3d");
   }

   public static LayerDefinition createLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition effect1 = partdefinition.m_171599_("effect1", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, -1.5F, 0.0F));
      effect1.m_171599_("effect1a", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-4.0F, -2.4435F, 0.2658F, 8.0F, 0.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 0.0F, 0.0F, 0.4363F, 0.0F, 0.0F));
      effect1.m_171599_("effect1b", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-4.0F, 2.4435F, 0.2658F, 8.0F, 0.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 0.0F, 0.0F, -0.4363F, 0.0F, 0.0F));
      effect1.m_171599_("effect1c", CubeListBuilder.m_171558_().m_171514_(9, 0).m_171488_(2.9435F, -4.5F, 0.2658F, 0.0F, 8.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 0.0F, 0.0F, 0.0F, 0.4363F, 0.0F));
      effect1.m_171599_("effect1d", CubeListBuilder.m_171558_().m_171514_(9, 0).m_171488_(-2.9435F, -4.5F, 0.2658F, 0.0F, 8.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 0.0F, 0.0F, 0.0F, -0.4363F, 0.0F));
      PartDefinition effect2 = partdefinition.m_171599_("effect2", CubeListBuilder.m_171558_(), PartPose.m_171423_(0.0F, -1.5F, 2.0F, 0.0F, 0.0F, 0.3491F));
      effect2.m_171599_("effect2a", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-4.0F, -2.4435F, 0.2658F, 8.0F, 0.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 0.0F, 0.0F, 0.4363F, 0.0F, 0.0F));
      effect2.m_171599_("effect2b", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-4.0F, 2.4435F, 0.2658F, 8.0F, 0.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 0.0F, 0.0F, -0.4363F, 0.0F, 0.0F));
      effect2.m_171599_("effect2c", CubeListBuilder.m_171558_().m_171514_(9, 0).m_171488_(2.9435F, -4.5F, 0.2658F, 0.0F, 8.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 0.0F, 0.0F, 0.0F, 0.4363F, 0.0F));
      effect2.m_171599_("effect2d", CubeListBuilder.m_171558_().m_171514_(9, 0).m_171488_(-2.9435F, -4.5F, 0.2658F, 0.0F, 8.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 0.0F, 0.0F, 0.0F, -0.4363F, 0.0F));
      PartDefinition effect3 = partdefinition.m_171599_("effect3", CubeListBuilder.m_171558_(), PartPose.m_171423_(0.0F, -1.5F, 5.0F, 0.0F, 0.0F, -0.3491F));
      effect3.m_171599_("effect3a", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-4.0F, -2.4435F, 0.2658F, 8.0F, 0.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 0.0F, 0.0F, 0.4363F, 0.0F, 0.0F));
      effect3.m_171599_("effect3b", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-4.0F, 2.4435F, 0.2658F, 8.0F, 0.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 0.0F, 0.0F, -0.4363F, 0.0F, 0.0F));
      effect3.m_171599_("effect3c", CubeListBuilder.m_171558_().m_171514_(9, 0).m_171488_(2.9435F, -4.5F, 0.2658F, 0.0F, 8.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 0.0F, 0.0F, 0.0F, 0.4363F, 0.0F));
      effect3.m_171599_("effect3d", CubeListBuilder.m_171558_().m_171514_(9, 0).m_171488_(-2.9435F, -4.5F, 0.2658F, 0.0F, 8.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 0.0F, 0.0F, 0.0F, -0.4363F, 0.0F));
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   public void m_6973_(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.effect1.f_104205_ = ageInTicks * 0.6F % 360.0F;
      this.effect2.f_104205_ = ageInTicks * -0.6F % 360.0F;
      this.effect3.f_104205_ = ageInTicks * 0.5F % 360.0F;
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.effect1.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.effect2.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.effect3.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }
}
