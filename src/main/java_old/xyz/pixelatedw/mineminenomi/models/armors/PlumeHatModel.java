package xyz.pixelatedw.mineminenomi.models.armors;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class PlumeHatModel<T extends LivingEntity> extends HumanoidArmorModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "plume_hat"), "main");
   private final ModelPart plumeHat;
   private final ModelPart top;
   private final ModelPart feather;
   private final ModelPart feather2;
   private final ModelPart feather3;
   private final ModelPart feather4;
   private final ModelPart feather5;
   private final ModelPart feather6;
   private final ModelPart leftSide;
   private final ModelPart rightSide;
   private final ModelPart leftSide2;
   private final ModelPart rightSide2;

   public PlumeHatModel(ModelPart root) {
      super(root);
      this.plumeHat = root.m_171324_("plumeHat");
      this.top = this.plumeHat.m_171324_("top");
      this.feather = this.plumeHat.m_171324_("feather");
      this.feather2 = this.feather.m_171324_("feather2");
      this.feather3 = this.feather.m_171324_("feather3");
      this.feather4 = this.feather.m_171324_("feather4");
      this.feather5 = this.feather.m_171324_("feather5");
      this.feather6 = this.feather.m_171324_("feather6");
      this.leftSide = this.plumeHat.m_171324_("leftSide");
      this.rightSide = this.plumeHat.m_171324_("rightSide");
      this.leftSide2 = this.plumeHat.m_171324_("leftSide2");
      this.rightSide2 = this.plumeHat.m_171324_("rightSide2");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = HumanoidModel.m_170681_(new CubeDeformation(0.0F), 0.0F);
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition plumeHat = partdefinition.m_171599_("plumeHat", CubeListBuilder.m_171558_().m_171514_(0, 26).m_171488_(-3.0F, -6.5F, -6.0F, 6.0F, 1.0F, 12.0F, new CubeDeformation(-0.001F)), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      plumeHat.m_171599_("top", CubeListBuilder.m_171558_().m_171514_(0, 50).m_171488_(-4.0F, -5.0F, -4.0F, 8.0F, 5.0F, 8.0F, new CubeDeformation(0.52F)).m_171514_(4, 41).m_171488_(-3.0F, -5.75F, -3.0F, 6.0F, 1.0F, 6.0F, new CubeDeformation(1.0F)), PartPose.m_171419_(0.0F, -6.5F, 0.0F));
      PartDefinition feather = plumeHat.m_171599_("feather", CubeListBuilder.m_171558_().m_171514_(38, 26).m_171488_(0.0F, -4.0F, 0.0F, 2.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(4.25F, -6.0F, 0.75F, 0.1745F, 0.0F, 0.0F));
      feather.m_171599_("feather2", CubeListBuilder.m_171558_().m_171514_(39, 36).m_171488_(0.0F, -4.0F, -1.0F, 2.0F, 3.0F, 5.0F, new CubeDeformation(-0.01F)), PartPose.m_171423_(0.0F, 0.0F, 0.0F, 0.2182F, 0.0F, 0.0F));
      feather.m_171599_("feather3", CubeListBuilder.m_171558_().m_171514_(40, 46).m_171488_(0.0F, -4.04F, -1.75F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.02F)), PartPose.m_171423_(0.0F, -0.25F, 0.25F, 0.6109F, 0.0F, 0.0F));
      feather.m_171599_("feather4", CubeListBuilder.m_171558_().m_171514_(39, 52).m_171488_(0.0F, -4.2F, -2.25F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.03F)), PartPose.m_171423_(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
      feather.m_171599_("feather5", CubeListBuilder.m_171558_().m_171514_(39, 16).m_171488_(0.0F, -4.0F, -1.0F, 2.0F, 3.0F, 5.0F, new CubeDeformation(-0.01F)), PartPose.m_171423_(0.0F, 0.25F, 3.75F, -0.2182F, 0.0F, 0.0F));
      feather.m_171599_("feather6", CubeListBuilder.m_171558_().m_171514_(40, 8).m_171488_(0.0F, -2.25F, -2.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 0.8602F, 8.2891F, -0.7418F, 0.0F, 0.0F));
      plumeHat.m_171599_("leftSide", CubeListBuilder.m_171558_().m_171514_(0, 13).m_171488_(-4.0F, -1.15F, -8.0F, 6.0F, 1.0F, 11.0F, new CubeDeformation(-0.01F)).m_171514_(0, 0).m_171488_(-4.0F, -1.55F, -8.0F, 6.0F, 1.0F, 11.0F, new CubeDeformation(-0.1F)), PartPose.m_171423_(-5.0F, -5.25F, -1.0F, -0.1745F, -0.4363F, 0.3491F));
      plumeHat.m_171599_("rightSide", CubeListBuilder.m_171558_().m_171514_(0, 13).m_171488_(-2.0F, -1.15F, -8.0F, 6.0F, 1.0F, 11.0F, new CubeDeformation(-0.01F)).m_171514_(0, 0).m_171488_(-2.0F, -1.55F, -8.0F, 6.0F, 1.0F, 11.0F, new CubeDeformation(-0.1F)), PartPose.m_171423_(5.0F, -5.25F, -1.0F, -0.1745F, 0.4363F, -0.3491F));
      plumeHat.m_171599_("leftSide2", CubeListBuilder.m_171558_().m_171514_(0, 13).m_171488_(-4.0F, -1.15F, -9.0F, 6.0F, 1.0F, 11.0F, new CubeDeformation(-0.01F)).m_171514_(0, 0).m_171488_(-4.0F, -1.55F, -9.0F, 6.0F, 1.0F, 11.0F, new CubeDeformation(-0.1F)), PartPose.m_171423_(-2.25F, -5.25F, 6.5F, 0.1745F, 0.4363F, 0.3491F));
      plumeHat.m_171599_("rightSide2", CubeListBuilder.m_171558_().m_171514_(0, 13).m_171488_(-2.0F, -1.15F, -9.0F, 6.0F, 1.0F, 11.0F, new CubeDeformation(-0.01F)).m_171514_(0, 0).m_171488_(-2.0F, -1.55F, -9.0F, 6.0F, 1.0F, 11.0F, new CubeDeformation(-0.1F)), PartPose.m_171423_(2.25F, -5.25F, 6.5F, 0.1745F, -0.4363F, -0.3491F));
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   public void m_6973_(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.plumeHat.m_104315_(this.f_102808_);
      this.plumeHat.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }
}
