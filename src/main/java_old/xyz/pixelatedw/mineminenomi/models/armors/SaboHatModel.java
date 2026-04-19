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

public class SaboHatModel<T extends LivingEntity> extends HumanoidArmorModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "sabo_hat"), "main");
   private final ModelPart saboHat;
   private final ModelPart band;
   private final ModelPart goggles;
   private final ModelPart leftGoggle;
   private final ModelPart leftGoggle1;
   private final ModelPart leftGoggle2;
   private final ModelPart leftGoggle3;
   private final ModelPart leftGoggle4;
   private final ModelPart leftGoggleRim1;
   private final ModelPart leftGoggleRim3;
   private final ModelPart leftGoggleRim2;
   private final ModelPart leftGoggleRim5;
   private final ModelPart leftGoggleRim6;
   private final ModelPart leftGoggleRim4;
   private final ModelPart rightGoggle;
   private final ModelPart rightGoggle1;
   private final ModelPart rightGoggle2;
   private final ModelPart rightGoggle3;
   private final ModelPart rightGoggle4;
   private final ModelPart rightGoggleRim1;
   private final ModelPart rightGoggleRim3;
   private final ModelPart rightGoggleRim2;
   private final ModelPart rightGoggleRim5;
   private final ModelPart rightGoggleRim6;
   private final ModelPart rightGoggleRim4;

   public SaboHatModel(ModelPart root) {
      super(root);
      this.saboHat = root.m_171324_("saboHat");
      this.band = this.saboHat.m_171324_("band");
      this.goggles = this.saboHat.m_171324_("goggles");
      this.leftGoggle = this.goggles.m_171324_("leftGoggle");
      this.leftGoggle1 = this.leftGoggle.m_171324_("leftGoggle1");
      this.leftGoggle2 = this.leftGoggle.m_171324_("leftGoggle2");
      this.leftGoggle3 = this.leftGoggle.m_171324_("leftGoggle3");
      this.leftGoggle4 = this.leftGoggle.m_171324_("leftGoggle4");
      this.leftGoggleRim1 = this.leftGoggle.m_171324_("leftGoggleRim1");
      this.leftGoggleRim3 = this.leftGoggle.m_171324_("leftGoggleRim3");
      this.leftGoggleRim2 = this.leftGoggle.m_171324_("leftGoggleRim2");
      this.leftGoggleRim5 = this.leftGoggle.m_171324_("leftGoggleRim5");
      this.leftGoggleRim6 = this.leftGoggle.m_171324_("leftGoggleRim6");
      this.leftGoggleRim4 = this.leftGoggle.m_171324_("leftGoggleRim4");
      this.rightGoggle = this.goggles.m_171324_("rightGoggle");
      this.rightGoggle1 = this.rightGoggle.m_171324_("rightGoggle1");
      this.rightGoggle2 = this.rightGoggle.m_171324_("rightGoggle2");
      this.rightGoggle3 = this.rightGoggle.m_171324_("rightGoggle3");
      this.rightGoggle4 = this.rightGoggle.m_171324_("rightGoggle4");
      this.rightGoggleRim1 = this.rightGoggle.m_171324_("rightGoggleRim1");
      this.rightGoggleRim3 = this.rightGoggle.m_171324_("rightGoggleRim3");
      this.rightGoggleRim2 = this.rightGoggle.m_171324_("rightGoggleRim2");
      this.rightGoggleRim5 = this.rightGoggle.m_171324_("rightGoggleRim5");
      this.rightGoggleRim6 = this.rightGoggle.m_171324_("rightGoggleRim6");
      this.rightGoggleRim4 = this.rightGoggle.m_171324_("rightGoggleRim4");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = HumanoidModel.m_170681_(new CubeDeformation(0.0F), 0.0F);
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition saboHat = partdefinition.m_171599_("saboHat", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-7.0F, -6.0F, -7.0F, 14.0F, 1.0F, 14.0F, new CubeDeformation(0.0F)).m_171514_(0, 16).m_171488_(-4.0F, -12.0F, -4.0F, 8.0F, 6.0F, 8.0F, new CubeDeformation(0.6F)).m_171514_(0, 16).m_171488_(-4.0F, -17.55F, -4.0F, 8.0F, 5.0F, 8.0F, new CubeDeformation(0.8F)), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      saboHat.m_171599_("band", CubeListBuilder.m_171558_().m_171514_(0, 44).m_171488_(-5.0F, -0.5F, -0.925F, 11.0F, 2.0F, 10.0F, new CubeDeformation(-0.3F)), PartPose.m_171419_(-0.5F, -9.25F, -3.875F));
      PartDefinition goggles = saboHat.m_171599_("goggles", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 0.5F, 0.0F));
      PartDefinition leftGoggle = goggles.m_171599_("leftGoggle", CubeListBuilder.m_171558_().m_171514_(0, 33).m_171488_(-1.34F, -0.9F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.19F)).m_171514_(0, 33).m_171488_(2.74F, -0.9F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.19F)).m_171514_(0, 6).m_171488_(-1.26F, -1.4F, 0.1F, 5.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(-3.5F, -9.35F, -4.75F));
      leftGoggle.m_171599_("leftGoggle1", CubeListBuilder.m_171558_().m_171514_(-1, 33).m_171488_(-0.5005F, -1.4324F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.m_171423_(0.2305F, -1.0176F, 0.0F, 0.0F, 0.0F, 1.1345F));
      leftGoggle.m_171599_("leftGoggle2", CubeListBuilder.m_171558_().m_171514_(-1, 33).m_171480_().m_171488_(-0.4995F, -1.4324F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(-0.2F)).m_171555_(false), PartPose.m_171423_(2.1695F, -1.0176F, 0.0F, 0.0F, 0.0F, -1.1345F));
      leftGoggle.m_171599_("leftGoggle3", CubeListBuilder.m_171558_().m_171514_(0, 33).m_171488_(-0.5F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(-0.201F)), PartPose.m_171423_(0.1578F, 1.2026F, 0.0F, 0.0F, 0.0F, -1.1345F));
      leftGoggle.m_171599_("leftGoggle4", CubeListBuilder.m_171558_().m_171514_(0, 33).m_171480_().m_171488_(-0.5F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(-0.201F)).m_171555_(false), PartPose.m_171423_(2.2422F, 1.2026F, 0.0F, 0.0F, 0.0F, 1.1345F));
      leftGoggle.m_171599_("leftGoggleRim1", CubeListBuilder.m_171558_().m_171514_(2, 2).m_171488_(-1.4794F, -0.9959F, 0.0F, 3.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.2494F, -0.5191F, 0.1F, 0.0F, 0.0F, -0.4363F));
      leftGoggle.m_171599_("leftGoggleRim3", CubeListBuilder.m_171558_().m_171514_(2, 2).m_171488_(-1.4994F, -0.9909F, 0.0F, 3.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.2494F, 0.7309F, 0.1F, 0.0F, 0.0F, -2.7053F));
      leftGoggle.m_171599_("leftGoggleRim2", CubeListBuilder.m_171558_().m_171514_(2, 2).m_171488_(-1.5444F, -0.9709F, 0.0F, 3.0F, 2.0F, 0.0F, new CubeDeformation(0.01F)), PartPose.m_171423_(2.1994F, -0.5191F, 0.1F, 0.0F, 0.0F, 0.4363F));
      leftGoggle.m_171599_("leftGoggleRim5", CubeListBuilder.m_171558_().m_171514_(2, 4).m_171488_(-0.7103F, -0.9966F, 0.0F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(2.8703F, 0.0866F, 0.1F, 0.0F, 0.0F, 1.5708F));
      leftGoggle.m_171599_("leftGoggleRim6", CubeListBuilder.m_171558_().m_171514_(2, 4).m_171480_().m_171488_(-0.9732F, -0.4863F, 0.0F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.01F)).m_171555_(false), PartPose.m_171423_(-0.9668F, 0.3763F, 0.1F, 0.0F, 0.0F, -1.5708F));
      leftGoggle.m_171599_("leftGoggleRim4", CubeListBuilder.m_171558_().m_171514_(2, 2).m_171488_(-1.5444F, -1.0009F, 0.0F, 3.0F, 2.0F, 0.0F, new CubeDeformation(0.01F)), PartPose.m_171423_(2.0994F, 0.7309F, 0.1F, 0.0F, 0.0F, 2.7053F));
      PartDefinition rightGoggle = goggles.m_171599_("rightGoggle", CubeListBuilder.m_171558_().m_171514_(0, 33).m_171480_().m_171488_(0.34F, -0.9F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.19F)).m_171555_(false).m_171514_(0, 33).m_171480_().m_171488_(-3.74F, -0.9F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.19F)).m_171555_(false).m_171514_(0, 6).m_171488_(-3.74F, -1.4F, 0.1F, 5.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(3.5F, -9.35F, -4.75F));
      rightGoggle.m_171599_("rightGoggle1", CubeListBuilder.m_171558_().m_171514_(-1, 33).m_171480_().m_171488_(-0.4995F, -1.4324F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(-0.2F)).m_171555_(false), PartPose.m_171423_(-0.2305F, -1.0176F, 0.0F, 0.0F, 0.0F, -1.1345F));
      rightGoggle.m_171599_("rightGoggle2", CubeListBuilder.m_171558_().m_171514_(-1, 33).m_171488_(-0.5005F, -1.4324F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.m_171423_(-2.1695F, -1.0176F, 0.0F, 0.0F, 0.0F, 1.1345F));
      rightGoggle.m_171599_("rightGoggle3", CubeListBuilder.m_171558_().m_171514_(0, 33).m_171480_().m_171488_(-0.5F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(-0.201F)).m_171555_(false), PartPose.m_171423_(-0.1578F, 1.2026F, 0.0F, 0.0F, 0.0F, 1.1345F));
      rightGoggle.m_171599_("rightGoggle4", CubeListBuilder.m_171558_().m_171514_(0, 33).m_171488_(-0.5F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(-0.201F)), PartPose.m_171423_(-2.2422F, 1.2026F, 0.0F, 0.0F, 0.0F, -1.1345F));
      rightGoggle.m_171599_("rightGoggleRim1", CubeListBuilder.m_171558_().m_171514_(2, 2).m_171480_().m_171488_(-1.5206F, -0.9959F, 0.0F, 3.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(-0.2494F, -0.5191F, 0.1F, 0.0F, 0.0F, 0.4363F));
      rightGoggle.m_171599_("rightGoggleRim3", CubeListBuilder.m_171558_().m_171514_(2, 2).m_171480_().m_171488_(-1.5006F, -0.9909F, 0.0F, 3.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(-0.2494F, 0.7309F, 0.1F, 0.0F, 0.0F, 2.7053F));
      rightGoggle.m_171599_("rightGoggleRim2", CubeListBuilder.m_171558_().m_171514_(2, 2).m_171480_().m_171488_(-1.4556F, -0.9709F, 0.0F, 3.0F, 2.0F, 0.0F, new CubeDeformation(0.01F)).m_171555_(false), PartPose.m_171423_(-2.1994F, -0.5191F, 0.1F, 0.0F, 0.0F, -0.4363F));
      rightGoggle.m_171599_("rightGoggleRim5", CubeListBuilder.m_171558_().m_171514_(2, 4).m_171480_().m_171488_(-1.2897F, -0.9966F, 0.0F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(-2.8703F, 0.0866F, 0.1F, 0.0F, 0.0F, -1.5708F));
      rightGoggle.m_171599_("rightGoggleRim6", CubeListBuilder.m_171558_().m_171514_(2, 4).m_171488_(-1.0268F, -0.4863F, 0.0F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.01F)), PartPose.m_171423_(0.9668F, 0.3763F, 0.1F, 0.0F, 0.0F, 1.5708F));
      rightGoggle.m_171599_("rightGoggleRim4", CubeListBuilder.m_171558_().m_171514_(2, 2).m_171480_().m_171488_(-1.4556F, -1.0009F, 0.0F, 3.0F, 2.0F, 0.0F, new CubeDeformation(0.01F)).m_171555_(false), PartPose.m_171423_(-2.0994F, 0.7309F, 0.1F, 0.0F, 0.0F, -2.7053F));
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   public void m_6973_(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.saboHat.m_104315_(this.f_102808_);
      this.saboHat.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }
}
