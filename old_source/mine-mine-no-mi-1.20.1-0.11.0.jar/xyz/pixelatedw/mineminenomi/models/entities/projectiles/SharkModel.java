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

public class SharkModel<T extends Entity> extends EntityModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "shark"), "main");
   private final ModelPart head1;
   private final ModelPart head3;
   private final ModelPart head4;
   private final ModelPart teeth1;
   private final ModelPart teeth2;
   private final ModelPart body1;
   private final ModelPart body2;
   private final ModelPart body3;
   private final ModelPart body4;
   private final ModelPart body5;
   private final ModelPart tail1;
   private final ModelPart tail2;
   private final ModelPart tail3;
   private final ModelPart tail4;
   private final ModelPart fin1;
   private final ModelPart rightFin;
   private final ModelPart leftFin;

   public SharkModel(ModelPart root) {
      this.head1 = root.m_171324_("head1");
      this.head3 = root.m_171324_("head3");
      this.head4 = root.m_171324_("head4");
      this.teeth1 = root.m_171324_("teeth1");
      this.teeth2 = root.m_171324_("teeth2");
      this.body1 = root.m_171324_("body1");
      this.body2 = root.m_171324_("body2");
      this.body3 = root.m_171324_("body3");
      this.body4 = root.m_171324_("body4");
      this.body5 = root.m_171324_("body5");
      this.tail1 = root.m_171324_("tail1");
      this.tail2 = root.m_171324_("tail2");
      this.tail3 = root.m_171324_("tail3");
      this.tail4 = root.m_171324_("tail4");
      this.fin1 = root.m_171324_("fin1");
      this.rightFin = root.m_171324_("rightFin");
      this.leftFin = root.m_171324_("leftFin");
   }

   public static LayerDefinition createLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      partdefinition.m_171599_("head1", CubeListBuilder.m_171558_().m_171514_(37, 0).m_171488_(-2.5F, -1.0F, -6.25F, 5.0F, 2.0F, 6.0F, new CubeDeformation(0.001F)), PartPose.m_171423_(0.0F, -2.0F, -6.0F, 0.3491F, 0.0F, 0.0F));
      partdefinition.m_171599_("head3", CubeListBuilder.m_171558_().m_171514_(37, 16).m_171488_(-2.5F, -1.0F, -5.0F, 5.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 3.2F, -6.4F, 0.0873F, 0.0F, 0.0F));
      partdefinition.m_171599_("head4", CubeListBuilder.m_171558_().m_171514_(37, 23).m_171488_(-2.5F, -1.0F, -3.0F, 5.0F, 2.0F, 3.0F, new CubeDeformation(-0.01F)), PartPose.m_171419_(0.0F, -0.9F, -6.0F));
      partdefinition.m_171599_("teeth1", CubeListBuilder.m_171558_().m_171514_(37, 29).m_171488_(-2.5F, -0.5F, -6.0F, 5.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 0.6F, -5.9F));
      partdefinition.m_171599_("teeth2", CubeListBuilder.m_171558_().m_171514_(37, 37).m_171488_(-2.5F, -2.0F, -5.0F, 5.0F, 1.0F, 5.0F, new CubeDeformation(0.01F)), PartPose.m_171423_(0.0F, 3.2F, -6.2F, 0.1571F, 0.0F, 0.0F));
      partdefinition.m_171599_("body1", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-4.0F, -4.0F, -3.5F, 8.0F, 8.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      partdefinition.m_171599_("body2", CubeListBuilder.m_171558_().m_171514_(0, 16).m_171488_(-3.5F, -3.5F, 0.0F, 7.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 0.0F, 3.5F));
      partdefinition.m_171599_("body3", CubeListBuilder.m_171558_().m_171514_(0, 28).m_171488_(-3.0F, -3.0F, 0.0F, 6.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 0.0F, 7.5F));
      partdefinition.m_171599_("body4", CubeListBuilder.m_171558_().m_171514_(0, 38).m_171488_(-2.5F, -2.5F, 0.0F, 5.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 0.0F, 10.5F));
      partdefinition.m_171599_("body5", CubeListBuilder.m_171558_().m_171514_(0, 47).m_171488_(-3.5F, -3.5F, 0.0F, 7.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 0.0F, -6.5F));
      partdefinition.m_171599_("tail1", CubeListBuilder.m_171558_().m_171514_(0, 58).m_171488_(-0.5F, -2.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, -1.5F, 13.0F, -0.9774F, 0.0F, 0.0F));
      partdefinition.m_171599_("tail2", CubeListBuilder.m_171558_().m_171514_(7, 58).m_171488_(-0.5F, -3.1333F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.01F)), PartPose.m_171423_(0.0F, -3.6F, 14.0F, -2.3213F, 0.0F, 0.0F));
      partdefinition.m_171599_("tail3", CubeListBuilder.m_171558_().m_171514_(0, 58).m_171488_(-0.5F, -2.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 1.5F, 13.0F, -2.0595F, 0.0F, 0.0F));
      partdefinition.m_171599_("tail4", CubeListBuilder.m_171558_().m_171514_(7, 58).m_171488_(-0.5F, -3.0F, -1.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.01F)), PartPose.m_171423_(0.0F, 3.8F, 14.8F, -0.7854F, 0.0F, 0.0F));
      partdefinition.m_171599_("fin1", CubeListBuilder.m_171558_().m_171514_(70, 0).m_171488_(-0.5F, -1.5F, 0.0F, 1.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, -2.5F, -2.0F, 0.5585F, 0.0F, 0.0F));
      partdefinition.m_171599_("rightFin", CubeListBuilder.m_171558_().m_171514_(83, 0).m_171488_(-5.0F, 0.0F, 0.0F, 5.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-3.5F, 2.0F, -2.0F, -0.2745F, 0.5148F, -0.2009F));
      partdefinition.m_171599_("leftFin", CubeListBuilder.m_171558_().m_171514_(83, 5).m_171488_(0.0F, 0.0F, 0.0F, 5.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(3.5F, 2.0F, -2.0F, 0.0746F, -0.5148F, 0.2009F));
      return LayerDefinition.m_171565_(meshdefinition, 128, 64);
   }

   public void m_6973_(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.head1.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.head3.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.head4.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.teeth1.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.teeth2.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.body1.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.body2.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.body3.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.body4.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.body5.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.tail1.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.tail2.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.tail3.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.tail4.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.fin1.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.rightFin.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.leftFin.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }
}
