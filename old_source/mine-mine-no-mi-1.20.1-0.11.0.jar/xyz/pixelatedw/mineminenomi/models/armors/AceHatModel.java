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

public class AceHatModel<T extends LivingEntity> extends HumanoidArmorModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "ace_hat"), "main");
   private final ModelPart aceHat;
   private final ModelPart base;
   private final ModelPart base2;
   private final ModelPart top;
   private final ModelPart symbols;
   private final ModelPart beads;
   private final ModelPart beads1;
   private final ModelPart beads2;
   private final ModelPart beads3;
   private final ModelPart beads4;
   private final ModelPart rightString;
   private final ModelPart rightString1;
   private final ModelPart rightString2;
   private final ModelPart rightString3;
   private final ModelPart leftString;
   private final ModelPart leftString1;
   private final ModelPart leftString2;
   private final ModelPart leftString3;
   private final ModelPart ornament;

   public AceHatModel(ModelPart root) {
      super(root);
      this.aceHat = root.m_171324_("aceHat");
      this.base = this.aceHat.m_171324_("base");
      this.base2 = this.base.m_171324_("base2");
      this.top = this.base.m_171324_("top");
      this.symbols = this.top.m_171324_("symbols");
      this.beads = this.base.m_171324_("beads");
      this.beads1 = this.beads.m_171324_("beads1");
      this.beads2 = this.beads.m_171324_("beads2");
      this.beads3 = this.beads.m_171324_("beads3");
      this.beads4 = this.beads.m_171324_("beads4");
      this.rightString = this.aceHat.m_171324_("rightString");
      this.rightString1 = this.rightString.m_171324_("rightString1");
      this.rightString2 = this.rightString1.m_171324_("rightString2");
      this.rightString3 = this.rightString2.m_171324_("rightString3");
      this.leftString = this.aceHat.m_171324_("leftString");
      this.leftString1 = this.leftString.m_171324_("leftString1");
      this.leftString2 = this.leftString1.m_171324_("leftString2");
      this.leftString3 = this.leftString2.m_171324_("leftString3");
      this.ornament = this.aceHat.m_171324_("ornament");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = HumanoidModel.m_170681_(new CubeDeformation(0.0F), 0.0F);
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition aceHat = partdefinition.m_171599_("aceHat", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      PartDefinition base = aceHat.m_171599_("base", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-6.0F, -1.01F, -7.0F, 12.0F, 1.0F, 14.0F, new CubeDeformation(0.02F)).m_171514_(2, 2).m_171488_(-7.0F, -1.0F, -6.0F, 14.0F, 1.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, -6.0F, 0.0F));
      base.m_171599_("base2", CubeListBuilder.m_171558_().m_171514_(0, 16).m_171488_(-6.0F, -1.0F, -5.0F, 10.0F, 1.0F, 12.0F, new CubeDeformation(0.02F)).m_171514_(2, 18).m_171488_(-7.0F, -1.0F, -4.0F, 12.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(1.0F, -1.0F, -1.0F));
      PartDefinition top = base.m_171599_("top", CubeListBuilder.m_171558_().m_171514_(0, 30).m_171488_(-4.5F, -3.0F, -4.5F, 9.0F, 1.0F, 9.0F, new CubeDeformation(0.02F)).m_171514_(28, 30).m_171488_(-3.0F, -5.0F, -3.0F, 6.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)).m_171514_(37, 22).m_171488_(-2.0F, -6.0F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      top.m_171599_("symbols", CubeListBuilder.m_171558_().m_171514_(4, 0).m_171488_(-1.0F, -2.0F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).m_171514_(4, 3).m_171488_(-4.0F, -2.0F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(1.5F, -3.0F, -4.5F, -0.3927F, 0.0F, 0.0F));
      PartDefinition beads = base.m_171599_("beads", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      beads.m_171599_("beads1", CubeListBuilder.m_171558_().m_171514_(10, 5).m_171488_(2.0F, -3.0F, -5.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).m_171514_(10, 5).m_171488_(0.5F, -3.0F, -5.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).m_171514_(10, 5).m_171488_(-1.0F, -3.0F, -5.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).m_171514_(10, 5).m_171488_(-2.5F, -3.0F, -5.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).m_171514_(10, 5).m_171488_(-4.0F, -3.0F, -5.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.5F, 0.0F, -0.5F));
      beads.m_171599_("beads2", CubeListBuilder.m_171558_().m_171514_(10, 5).m_171488_(2.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).m_171514_(10, 5).m_171488_(1.0F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).m_171514_(10, 5).m_171488_(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).m_171514_(10, 5).m_171488_(-2.0F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).m_171514_(10, 5).m_171488_(-3.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, -2.5F, 5.25F, 0.0F, 3.1416F, 0.0F));
      beads.m_171599_("beads3", CubeListBuilder.m_171558_().m_171514_(10, 5).m_171488_(2.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).m_171514_(10, 5).m_171488_(1.0F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).m_171514_(10, 5).m_171488_(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).m_171514_(10, 5).m_171488_(-2.0F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).m_171514_(10, 5).m_171488_(-3.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-5.25F, -2.5F, 0.0F, 0.0F, 1.5708F, 0.0F));
      beads.m_171599_("beads4", CubeListBuilder.m_171558_().m_171514_(10, 5).m_171488_(2.0F, -3.0F, -0.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).m_171514_(10, 5).m_171488_(0.5F, -3.0F, -0.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).m_171514_(10, 5).m_171488_(-1.0F, -3.0F, -0.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).m_171514_(10, 5).m_171488_(-2.5F, -3.0F, -0.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).m_171514_(10, 5).m_171488_(-4.0F, -3.0F, -0.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(5.5F, 0.0F, 0.5F, 0.0F, -1.5708F, 0.0F));
      PartDefinition rightString = aceHat.m_171599_("rightString", CubeListBuilder.m_171558_(), PartPose.m_171419_(-5.0F, -6.0F, -3.0F));
      PartDefinition rightString1 = rightString.m_171599_("rightString1", CubeListBuilder.m_171558_().m_171514_(0, 4).m_171488_(-0.5048F, -0.2181F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.m_171423_(-0.5F, 0.0F, 1.5F, -0.3927F, 0.0F, -0.0873F));
      PartDefinition rightString2 = rightString1.m_171599_("rightString2", CubeListBuilder.m_171558_().m_171514_(0, 4).m_171488_(-0.5F, -0.25F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.m_171423_(-0.0048F, 3.5319F, 0.0F, -0.1309F, 0.0F, -0.1309F));
      rightString2.m_171599_("rightString3", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-0.5F, -0.25F, -0.5F, 1.0F, 8.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.m_171423_(0.0F, 3.5F, 0.0F, 0.2182F, 0.0F, -0.3927F));
      PartDefinition leftString = aceHat.m_171599_("leftString", CubeListBuilder.m_171558_(), PartPose.m_171419_(5.0F, -6.0F, -3.0F));
      PartDefinition leftString1 = leftString.m_171599_("leftString1", CubeListBuilder.m_171558_().m_171514_(0, 4).m_171488_(-0.5048F, -0.2181F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.m_171423_(0.5F, 0.0F, 1.5F, -0.3927F, 0.0F, 0.0873F));
      PartDefinition leftString2 = leftString1.m_171599_("leftString2", CubeListBuilder.m_171558_().m_171514_(0, 4).m_171488_(-0.5F, -0.25F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.m_171423_(-0.0048F, 3.5319F, 0.0F, -0.1309F, 0.0F, 0.1309F));
      leftString2.m_171599_("leftString3", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-0.5F, -0.25F, -0.5F, 1.0F, 8.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.m_171423_(0.0F, 3.5F, 0.0F, 0.2182F, 0.0F, 0.3927F));
      aceHat.m_171599_("ornament", CubeListBuilder.m_171558_().m_171514_(4, 6).m_171488_(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).m_171514_(9, 8).m_171488_(-0.5F, -1.0F, -0.75F, 1.0F, 5.0F, 1.0F, new CubeDeformation(-0.1F)).m_171514_(0, 10).m_171488_(-1.0F, 1.25F, -1.25F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.3F)).m_171514_(0, 10).m_171488_(-1.0F, 2.5F, -1.25F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.3F)), PartPose.m_171419_(0.0F, 6.0F, -6.0F));
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   public void m_6973_(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.aceHat.m_104315_(this.f_102808_);
      this.aceHat.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }
}
