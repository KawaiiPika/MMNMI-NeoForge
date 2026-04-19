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

public class TomoeDrumsModel<T extends LivingEntity> extends HumanoidArmorModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "tomoe_drums"), "main");
   private final ModelPart tomoeDrums;
   private final ModelPart bars;
   private final ModelPart bars6;
   private final ModelPart bars7;
   private final ModelPart bars8;
   private final ModelPart bars9;
   private final ModelPart bars10;
   private final ModelPart bars1;
   private final ModelPart bars2;
   private final ModelPart bars3;
   private final ModelPart bars4;
   private final ModelPart bars5;
   private final ModelPart drums;
   private final ModelPart drum2;
   private final ModelPart drum4;
   private final ModelPart drum1;
   private final ModelPart drum3;

   public TomoeDrumsModel(ModelPart root) {
      super(root);
      this.tomoeDrums = root.m_171324_("tomoeDrums");
      this.bars = this.tomoeDrums.m_171324_("bars");
      this.bars6 = this.bars.m_171324_("bars6");
      this.bars7 = this.bars.m_171324_("bars7");
      this.bars8 = this.bars.m_171324_("bars8");
      this.bars9 = this.bars.m_171324_("bars9");
      this.bars10 = this.bars.m_171324_("bars10");
      this.bars1 = this.bars.m_171324_("bars1");
      this.bars2 = this.bars.m_171324_("bars2");
      this.bars3 = this.bars.m_171324_("bars3");
      this.bars4 = this.bars.m_171324_("bars4");
      this.bars5 = this.bars.m_171324_("bars5");
      this.drums = this.tomoeDrums.m_171324_("drums");
      this.drum2 = this.drums.m_171324_("drum2");
      this.drum4 = this.drums.m_171324_("drum4");
      this.drum1 = this.drums.m_171324_("drum1");
      this.drum3 = this.drums.m_171324_("drum3");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = HumanoidModel.m_170681_(new CubeDeformation(0.0F), 0.0F);
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition tomoeDrums = partdefinition.m_171599_("tomoeDrums", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 1.0F, 3.0F));
      PartDefinition bars = tomoeDrums.m_171599_("bars", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-1.5F, -18.07F, 3.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.01F)), PartPose.m_171419_(0.0F, 4.0F, -1.0F));
      bars.m_171599_("bars6", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171480_().m_171488_(5.7F, -34.6F, 2.0F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(23.0F, -46.0F, 1.0F, 0.0F, 0.0F, -2.7925F));
      bars.m_171599_("bars7", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171480_().m_171488_(7.0F, -30.5F, 2.0F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(38.0F, -20.75F, 1.0F, 0.0F, 0.0F, -2.0944F));
      bars.m_171599_("bars8", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171480_().m_171488_(-2.45F, -0.5501F, -0.5F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.01F)).m_171555_(false), PartPose.m_171423_(8.5F, -9.4999F, 3.5F, 0.0F, 0.0F, -1.5708F));
      bars.m_171599_("bars9", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171480_().m_171488_(-2.5F, -0.5F, -0.5F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(7.0392F, -5.4773F, 3.5F, 0.0F, 0.0F, -0.8727F));
      bars.m_171599_("bars10", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171480_().m_171488_(1.1F, -21.05F, 1.5019F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.01F)).m_171555_(false), PartPose.m_171423_(10.0F, 17.25F, 1.0F, 0.0F, -0.0873F, -0.4887F));
      bars.m_171599_("bars1", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-10.7F, -34.6F, 2.0F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-23.0F, -46.0F, 1.0F, 0.0F, 0.0F, 2.7925F));
      bars.m_171599_("bars2", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-12.0F, -30.5F, 2.0F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-38.0F, -20.75F, 1.0F, 0.0F, 0.0F, 2.0944F));
      bars.m_171599_("bars3", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-2.55F, -0.5501F, -0.5F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.01F)), PartPose.m_171423_(-8.5F, -9.4999F, 3.5F, 0.0F, 0.0F, 1.5708F));
      bars.m_171599_("bars4", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-2.5F, -0.5F, -0.5F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-7.0392F, -5.4773F, 3.5F, 0.0F, 0.0F, 0.8727F));
      bars.m_171599_("bars5", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-6.1F, -21.05F, 1.5019F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.01F)), PartPose.m_171423_(-10.0F, 17.25F, 1.0F, 0.0F, 0.0873F, 0.4887F));
      PartDefinition drums = tomoeDrums.m_171599_("drums", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 2.0F, -1.0F));
      drums.m_171599_("drum2", CubeListBuilder.m_171558_().m_171514_(0, 5).m_171488_(-1.5F, -1.5F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(6.5F, -13.5F, 3.5F));
      drums.m_171599_("drum4", CubeListBuilder.m_171558_().m_171514_(0, 5).m_171488_(-1.5F, -1.5F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(8.5F, -5.5F, 3.5F));
      drums.m_171599_("drum1", CubeListBuilder.m_171558_().m_171514_(0, 5).m_171488_(-1.5F, -1.5F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(-8.5F, -5.5F, 3.5F));
      drums.m_171599_("drum3", CubeListBuilder.m_171558_().m_171514_(0, 5).m_171488_(-1.5F, -1.5F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(-6.5F, -13.5F, 3.5F));
      return LayerDefinition.m_171565_(meshdefinition, 16, 16);
   }

   public void m_6973_(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.tomoeDrums.m_104315_(this.f_102810_);
      this.tomoeDrums.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }
}
