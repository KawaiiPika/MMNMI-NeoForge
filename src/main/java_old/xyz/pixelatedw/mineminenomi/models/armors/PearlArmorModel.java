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
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorItem.Type;

public class PearlArmorModel<T extends LivingEntity> extends HumanoidArmorModel<T> {
   public static final ModelLayerLocation HAT_LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "pearl_hat_gear"), "main");
   public static final ModelLayerLocation ARMOR_LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "pearl_armor_gear"), "main");
   public static final ModelLayerLocation LEGS_LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "pearl_legs_gear"), "main");
   private ModelPart leftArmGear;
   private ModelPart rightArmGear;
   private ModelPart leftLegGear;
   private ModelPart rightLegGear;
   private ModelPart bodyFace;
   private ModelPart bodyBack;
   private ModelPart headPearl;

   public PearlArmorModel(ModelPart root) {
      super(root);
      if (root.m_233562_("leftArmGear")) {
         this.leftArmGear = root.m_171324_("leftArmGear");
      }

      if (root.m_233562_("rightArmGear")) {
         this.rightArmGear = root.m_171324_("rightArmGear");
      }

      if (root.m_233562_("leftLegGear")) {
         this.leftLegGear = root.m_171324_("leftLegGear");
      }

      if (root.m_233562_("rightLegGear")) {
         this.rightLegGear = root.m_171324_("rightLegGear");
      }

      if (root.m_233562_("bodyFace")) {
         this.bodyFace = root.m_171324_("bodyFace");
      }

      if (root.m_233562_("bodyBack")) {
         this.bodyBack = root.m_171324_("bodyBack");
      }

      if (root.m_233562_("headPearl")) {
         this.headPearl = root.m_171324_("headPearl");
      }

   }

   public static LayerDefinition createBodyLayer(ArmorItem.Type slotType) {
      MeshDefinition meshdefinition = HumanoidModel.m_170681_(new CubeDeformation(0.0F), 0.0F);
      PartDefinition partdefinition = meshdefinition.m_171576_();
      if (slotType == Type.HELMET) {
         partdefinition.m_171599_("headPearl", CubeListBuilder.m_171558_().m_171514_(0, 37).m_171488_(-4.5F, -7.0F, -4.5F, 9.0F, 1.0F, 9.0F, new CubeDeformation(0.1F)).m_171514_(0, 48).m_171488_(-4.5F, -13.0F, -4.5F, 9.0F, 6.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      } else if (slotType == Type.CHESTPLATE) {
         partdefinition.m_171599_("bodyFace", CubeListBuilder.m_171558_().m_171514_(22, 1).m_171488_(-7.0F, -0.5F, -3.75F, 14.0F, 15.0F, 2.0F, new CubeDeformation(0.0F)).m_171514_(19, 19).m_171488_(-1.5F, 5.0F, -5.25F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).m_171514_(11, 1).m_171488_(5.0F, -0.5F, -2.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(-0.01F)).m_171514_(11, 1).m_171488_(-6.0F, -0.5F, -2.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(-0.01F)), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
         partdefinition.m_171599_("bodyBack", CubeListBuilder.m_171558_().m_171514_(22, 1).m_171488_(-7.0F, -0.5F, 2.25F, 14.0F, 15.0F, 2.0F, new CubeDeformation(0.0F)).m_171514_(19, 19).m_171488_(-1.5F, 5.0F, 3.75F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 0.0F, -0.5F));
         PartDefinition leftArmGear = partdefinition.m_171599_("leftArmGear", CubeListBuilder.m_171558_(), PartPose.m_171419_(5.0F, 2.0F, 0.0F));
         leftArmGear.m_171599_("leftElbowGear", CubeListBuilder.m_171558_().m_171514_(0, 7).m_171488_(-1.0F, -3.5F, -3.5F, 2.0F, 7.0F, 7.0F, new CubeDeformation(0.0F)).m_171514_(0, 0).m_171488_(1.0F, -1.5F, -1.5F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).m_171514_(0, 22).m_171488_(-5.1F, -0.75F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.1F)), PartPose.m_171419_(4.0F, 3.25F, 0.0F));
         leftArmGear.m_171599_("leftPunchGear", CubeListBuilder.m_171558_().m_171514_(0, 7).m_171488_(-1.0F, -3.5F, -3.5F, 2.0F, 7.0F, 7.0F, new CubeDeformation(0.0F)).m_171514_(0, 0).m_171488_(1.0F, -1.5F, -1.5F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(1.0F, 11.0F, 0.0F, 0.0F, 0.0F, 1.5708F));
         PartDefinition rightArmGear = partdefinition.m_171599_("rightArmGear", CubeListBuilder.m_171558_(), PartPose.m_171419_(-5.0F, 2.0F, 0.0F));
         rightArmGear.m_171599_("rightElbowGear", CubeListBuilder.m_171558_().m_171514_(0, 7).m_171488_(-1.0F, -3.5F, -3.5F, 2.0F, 7.0F, 7.0F, new CubeDeformation(0.0F)).m_171514_(0, 0).m_171488_(-2.0F, -1.5F, -1.5F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).m_171514_(0, 22).m_171488_(1.1F, -0.75F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.1F)), PartPose.m_171419_(-4.0F, 3.25F, 0.0F));
         rightArmGear.m_171599_("rightPunchGear", CubeListBuilder.m_171558_().m_171514_(0, 7).m_171488_(-1.0F, -3.5F, -3.5F, 2.0F, 7.0F, 7.0F, new CubeDeformation(0.0F)).m_171514_(0, 0).m_171488_(1.0F, -1.5F, -1.5F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-1.0F, 11.0F, 0.0F, 0.0F, 0.0F, 1.5708F));
      } else if (slotType == Type.LEGGINGS) {
         PartDefinition leftLegGear = partdefinition.m_171599_("leftLegGear", CubeListBuilder.m_171558_(), PartPose.m_171419_(1.9F, 12.0F, 0.0F));
         PartDefinition leftKneeGear = leftLegGear.m_171599_("leftKneeGear", CubeListBuilder.m_171558_().m_171514_(0, 22).m_171488_(-1.6F, -0.75F, 0.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.1F)), PartPose.m_171419_(-0.4F, 6.25F, -2.0F));
         leftKneeGear.m_171599_("leftKneeGearBase", CubeListBuilder.m_171558_().m_171514_(0, 28).m_171488_(-1.5F, -3.5F, -1.25F, 6.0F, 6.0F, 2.0F, new CubeDeformation(0.01F)).m_171514_(20, 25).m_171488_(0.0F, -2.0F, -2.0F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(-1.0F, 0.75F, -0.25F));
         PartDefinition rightLegGear = partdefinition.m_171599_("rightLegGear", CubeListBuilder.m_171558_(), PartPose.m_171419_(-5.65F, 12.0F, 0.0F));
         PartDefinition rightKneeGear = rightLegGear.m_171599_("rightKneeGear", CubeListBuilder.m_171558_().m_171514_(0, 22).m_171488_(-1.6F, -0.75F, 0.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.1F)), PartPose.m_171419_(3.4F, 6.25F, -2.0F));
         rightKneeGear.m_171599_("rightKneeGearBase", CubeListBuilder.m_171558_().m_171514_(0, 28).m_171488_(-1.5F, -3.5F, -1.25F, 6.0F, 6.0F, 2.0F, new CubeDeformation(0.01F)).m_171514_(20, 25).m_171488_(0.0F, -2.0F, -2.0F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(-1.0F, 0.75F, -0.25F));
      }

      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   public void m_6973_(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      if (this.leftArmGear != null) {
         this.leftArmGear.m_104315_(this.f_102812_);
         this.leftArmGear.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      }

      if (this.rightArmGear != null) {
         this.rightArmGear.m_104315_(this.f_102811_);
         this.rightArmGear.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      }

      if (this.leftLegGear != null) {
         this.leftLegGear.m_104315_(this.f_102813_);
         this.leftLegGear.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      }

      if (this.rightLegGear != null) {
         this.rightLegGear.m_104315_(this.f_102814_);
         ModelPart var10000 = this.rightLegGear;
         var10000.f_104200_ -= 3.5F;
         this.rightLegGear.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      }

      if (this.bodyFace != null) {
         this.bodyFace.m_104315_(this.f_102810_);
         this.bodyFace.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      }

      if (this.bodyBack != null) {
         this.bodyBack.m_104315_(this.f_102810_);
         this.bodyBack.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      }

      if (this.headPearl != null) {
         this.headPearl.m_104315_(this.f_102808_);
         this.headPearl.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      }

   }
}
