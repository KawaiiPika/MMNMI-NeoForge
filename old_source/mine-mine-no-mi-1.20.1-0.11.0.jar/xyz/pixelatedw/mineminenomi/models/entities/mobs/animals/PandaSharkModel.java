package xyz.pixelatedw.mineminenomi.models.entities.mobs.animals;

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
import net.minecraft.util.Mth;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.PandaSharkEntity;

public class PandaSharkModel<T extends PandaSharkEntity> extends EntityModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "panda_shark"), "main");
   private final ModelPart body;
   private final ModelPart head;
   private final ModelPart mouth;
   private final ModelPart body2;
   private final ModelPart backFin;
   private final ModelPart leftFin;
   private final ModelPart rightFin;
   private final ModelPart body3;
   private final ModelPart body4;
   private final ModelPart body5;
   private final ModelPart tail;

   public PandaSharkModel(ModelPart root) {
      this.body = root.m_171324_("body");
      this.head = this.body.m_171324_("head");
      this.mouth = this.head.m_171324_("mouth");
      this.body2 = this.body.m_171324_("body2");
      this.backFin = this.body2.m_171324_("backFin");
      this.leftFin = this.body2.m_171324_("leftFin");
      this.rightFin = this.body2.m_171324_("rightFin");
      this.body3 = this.body2.m_171324_("body3");
      this.body4 = this.body3.m_171324_("body4");
      this.body5 = this.body4.m_171324_("body5");
      this.tail = this.body5.m_171324_("tail");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition body = partdefinition.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(0, 26).m_171488_(-4.0F, -5.0F, -9.75F, 8.0F, 10.0F, 3.0F, new CubeDeformation(0.2F)), PartPose.m_171419_(0.0F, 16.0F, -3.25F));
      PartDefinition head = body.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(0, 40).m_171488_(-4.0F, -5.0F, -14.75F, 8.0F, 10.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      head.m_171599_("mouth", CubeListBuilder.m_171558_().m_171514_(0, 57).m_171488_(-3.5F, -3.5F, -12.0F, 7.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 1.0086F, -6.7946F, 0.0873F, 0.0F, 0.0F));
      PartDefinition body2 = body.m_171599_("body2", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-4.5F, -5.4386F, -9.7491F, 9.0F, 11.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, -0.0614F, 1.9991F));
      body2.m_171599_("backFin", CubeListBuilder.m_171558_().m_171514_(50, 37).m_171488_(0.0F, -11.6077F, -5.1699F, 0.0F, 12.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, -3.1886F, -1.7491F, -0.48F, 0.0F, 0.0F));
      body2.m_171599_("leftFin", CubeListBuilder.m_171558_().m_171514_(-10, 73).m_171480_().m_171488_(-1.0F, 0.0F, -5.0F, 14.0F, 0.0F, 10.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(4.5F, 3.5614F, -3.7491F, 0.0F, 0.0F, 0.4363F));
      body2.m_171599_("rightFin", CubeListBuilder.m_171558_().m_171514_(-10, 73).m_171488_(-13.0F, 0.0F, -5.0F, 14.0F, 0.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-4.5F, 3.5614F, -3.7491F, 0.0F, 0.0F, -0.4363F));
      PartDefinition body3 = body2.m_171599_("body3", CubeListBuilder.m_171558_().m_171514_(24, 26).m_171488_(-3.5F, -5.0F, -1.75F, 7.0F, 10.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 0.0614F, 4.0009F));
      PartDefinition body4 = body3.m_171599_("body4", CubeListBuilder.m_171558_().m_171514_(29, 44).m_171488_(-2.5F, -4.5F, -1.0F, 5.0F, 9.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 0.0F, 5.25F));
      PartDefinition body5 = body4.m_171599_("body5", CubeListBuilder.m_171558_().m_171514_(27, 59).m_171488_(-2.0F, -4.0F, -0.75F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 0.0F, 3.75F));
      body5.m_171599_("tail", CubeListBuilder.m_171558_().m_171514_(30, 63).m_171488_(0.0F, -11.0F, 0.0F, 0.0F, 22.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 0.0F, 3.25F));
      return LayerDefinition.m_171565_(meshdefinition, 128, 128);
   }

   public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.body.f_104203_ = headPitch * ((float)Math.PI / 180F);
      this.body.f_104204_ = netHeadYaw * ((float)Math.PI / 180F);
      this.tail.f_104204_ = 0.1F * Mth.m_14089_(ageInTicks * 0.15F);
      this.rightFin.f_104204_ = 0.1F * Mth.m_14089_(ageInTicks * 0.35F);
      this.rightFin.f_104205_ = 0.1F * Mth.m_14089_(ageInTicks * 0.35F);
      this.leftFin.f_104204_ = 0.1F * -Mth.m_14089_(ageInTicks * 0.35F);
      this.leftFin.f_104205_ = 0.1F * -Mth.m_14089_(ageInTicks * 0.35F);
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.body.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }
}
