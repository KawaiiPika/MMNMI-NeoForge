package xyz.pixelatedw.mineminenomi.models.morphs.partials;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
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

public class SkypieanModel<T extends LivingEntity> extends HumanoidModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "skypiean_partial"), "main");
   private final ModelPart antennas;
   private final ModelPart leftAntenna;
   private final ModelPart leftAntennaTip;
   private final ModelPart rightAntenna;
   private final ModelPart rightAntennaTip;
   private final ModelPart wings;
   private final ModelPart leftWing;
   private final ModelPart rightWing;

   public SkypieanModel(ModelPart root) {
      super(root);
      this.antennas = root.m_171324_("antennas");
      this.leftAntenna = this.antennas.m_171324_("leftAntenna");
      this.leftAntennaTip = this.leftAntenna.m_171324_("leftAntennaTip");
      this.rightAntenna = this.antennas.m_171324_("rightAntenna");
      this.rightAntennaTip = this.rightAntenna.m_171324_("rightAntennaTip");
      this.wings = root.m_171324_("wings");
      this.leftWing = this.wings.m_171324_("leftWing");
      this.rightWing = this.wings.m_171324_("rightWing");
   }

   public static LayerDefinition createLayer() {
      MeshDefinition meshdefinition = HumanoidModel.m_170681_(new CubeDeformation(0.0F), 0.0F);
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition antennas = partdefinition.m_171599_("antennas", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      PartDefinition leftAntenna = antennas.m_171599_("leftAntenna", CubeListBuilder.m_171558_().m_171514_(0, 1).m_171488_(-1.0F, -4.0F, 0.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-2.0F, -7.25F, 0.0F, 0.0F, 0.0F, -0.5236F));
      leftAntenna.m_171599_("leftAntennaTip", CubeListBuilder.m_171558_().m_171514_(6, 2).m_171488_(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(-0.5F, -5.0F, 0.5F));
      PartDefinition rightAntenna = antennas.m_171599_("rightAntenna", CubeListBuilder.m_171558_().m_171514_(0, 1).m_171480_().m_171488_(0.0F, -4.0F, 0.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(2.0F, -7.25F, 0.0F, 0.0F, 0.0F, 0.5236F));
      rightAntenna.m_171599_("rightAntennaTip", CubeListBuilder.m_171558_().m_171514_(6, 2).m_171480_().m_171488_(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171419_(0.5F, -5.0F, 0.5F));
      PartDefinition wings = partdefinition.m_171599_("wings", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 0.0F, 0.25F));
      wings.m_171599_("leftWing", CubeListBuilder.m_171558_().m_171514_(16, 0).m_171488_(-10.9727F, -2.9604F, -0.005F, 11.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-2.5F, 2.25F, 2.0F, 0.0F, 0.2618F, 0.1309F));
      wings.m_171599_("rightWing", CubeListBuilder.m_171558_().m_171514_(16, 0).m_171480_().m_171488_(-0.0273F, -2.9604F, -0.005F, 11.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(2.5F, 2.25F, 2.0F, 0.0F, -0.2618F, -0.1309F));
      return LayerDefinition.m_171565_(meshdefinition, 64, 32);
   }

   public void m_6973_(LivingEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.antennas.m_104315_(this.f_102808_);
      this.wings.m_104315_(this.f_102810_);
      this.antennas.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.wings.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }
}
