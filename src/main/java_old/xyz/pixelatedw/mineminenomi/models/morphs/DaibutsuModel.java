package xyz.pixelatedw.mineminenomi.models.morphs;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
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

public class DaibutsuModel extends PlayerModel<LivingEntity> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "daibutsu"), "main");

   public DaibutsuModel(ModelPart root, boolean isSlim) {
      super(root, isSlim);
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = HumanoidModel.m_170681_(CubeDeformation.f_171458_, 0.0F);
      PartDefinition partdefinition = meshdefinition.m_171576_();
      partdefinition.m_171599_("ear", CubeListBuilder.m_171558_(), PartPose.f_171404_);
      partdefinition.m_171599_("cloak", CubeListBuilder.m_171558_(), PartPose.f_171404_);
      partdefinition.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, -8.0F, 0.0F));
      partdefinition.m_171599_("headwear", CubeListBuilder.m_171558_().m_171514_(32, 0).m_171488_(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.5F)), PartPose.m_171419_(0.0F, -8.0F, 0.0F));
      partdefinition.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(16, 16).m_171488_(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(3.0F)), PartPose.m_171419_(0.0F, -5.0F, 0.0F));
      partdefinition.m_171599_("jacket", CubeListBuilder.m_171558_().m_171514_(16, 32).m_171488_(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(3.25F)), PartPose.m_171419_(0.0F, -5.0F, 0.0F));
      partdefinition.m_171599_("left_arm", CubeListBuilder.m_171558_().m_171514_(32, 48).m_171488_(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(2.0F)), PartPose.m_171419_(11.0F, -3.0F, 0.0F));
      partdefinition.m_171599_("left_sleeve", CubeListBuilder.m_171558_().m_171514_(48, 48).m_171488_(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(2.25F)), PartPose.m_171419_(11.0F, -3.0F, 0.0F));
      partdefinition.m_171599_("right_arm", CubeListBuilder.m_171558_().m_171514_(40, 16).m_171488_(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(2.0F)), PartPose.m_171419_(-11.25F, -3.0F, 0.0F));
      partdefinition.m_171599_("right_sleeve", CubeListBuilder.m_171558_().m_171514_(40, 32).m_171488_(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(2.25F)), PartPose.m_171419_(-11.25F, -3.0F, 0.0F));
      partdefinition.m_171599_("left_leg", CubeListBuilder.m_171558_().m_171514_(16, 48).m_171488_(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(1.0F)), PartPose.m_171419_(3.0F, 11.0F, 0.0F));
      partdefinition.m_171599_("left_pants", CubeListBuilder.m_171558_().m_171514_(0, 48).m_171488_(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(1.25F)), PartPose.m_171419_(3.0F, 11.0F, 0.0F));
      partdefinition.m_171599_("right_leg", CubeListBuilder.m_171558_().m_171514_(0, 16).m_171488_(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(1.0F)), PartPose.m_171419_(-3.0F, 11.0F, 0.0F));
      partdefinition.m_171599_("right_pants", CubeListBuilder.m_171558_().m_171514_(0, 32).m_171488_(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(1.25F)), PartPose.m_171419_(-3.0F, 11.0F, 0.0F));
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   public void m_6973_(LivingEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      super.m_6973_(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
      this.f_102808_.f_104201_ = -7.0F;
      this.f_102809_.f_104201_ = -7.0F;
      this.f_102810_.f_104202_ = -1.0F;
      this.f_103378_.f_104202_ = -1.0F;
      this.f_102810_.f_104201_ = -4.0F;
      this.f_103378_.f_104201_ = -4.0F;
      this.f_102814_.f_104201_ = 11.0F;
      this.f_103376_.f_104201_ = 11.0F;
      this.f_102813_.f_104201_ = 11.0F;
      this.f_103377_.f_104201_ = 11.0F;
      this.f_102811_.f_104200_ = -12.5F;
      this.f_103375_.f_104200_ = -12.5F;
      this.f_102811_.f_104201_ = -2.0F;
      this.f_103375_.f_104201_ = -2.0F;
      this.f_102812_.f_104200_ = 12.5F;
      this.f_103374_.f_104200_ = 12.5F;
      this.f_102812_.f_104201_ = -2.0F;
      this.f_103374_.f_104201_ = -2.0F;
      this.f_102810_.f_233553_ = 1.25F;
      this.f_102810_.f_233555_ = 1.5F;
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      super.m_7695_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }
}
