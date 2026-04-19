package xyz.pixelatedw.mineminenomi.models.entities.mobs;

import com.google.common.collect.ImmutableList;
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
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;

public class CelestialDragonSlaveRideModel<T extends LivingEntity> extends HumanoidModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "celestial_dragon_slave_ride"), "main");
   private final ModelPart nose;
   private final ModelPart headwear;
   private final ModelPart headwear2;
   private final ModelPart bodywear;
   private final ModelPart arms;
   private final ModelPart mirrored;

   public CelestialDragonSlaveRideModel(ModelPart root) {
      super(root);
      this.nose = root.m_171324_("nose");
      this.headwear = root.m_171324_("headwear");
      this.headwear2 = root.m_171324_("headwear2");
      this.bodywear = root.m_171324_("bodywear");
      this.arms = root.m_171324_("arms");
      this.mirrored = this.arms.m_171324_("mirrored");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = HumanoidModel.m_170681_(CubeDeformation.f_171458_, 0.0F);
      PartDefinition partdefinition = meshdefinition.m_171576_();
      partdefinition.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 17.0F, -9.0F, 0.2618F, 0.0F, 0.0F));
      partdefinition.m_171599_("nose", CubeListBuilder.m_171558_().m_171514_(24, 0).m_171488_(-1.0F, -1.0F, -6.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 15.0F, -9.5F, 0.2618F, 0.0F, 0.0F));
      partdefinition.m_171599_("headwear", CubeListBuilder.m_171558_().m_171514_(32, 0).m_171488_(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F, new CubeDeformation(0.51F)), PartPose.m_171423_(0.0F, 17.0F, -9.0F, 0.2618F, 0.0F, 0.0F));
      partdefinition.m_171599_("headwear2", CubeListBuilder.m_171558_().m_171514_(30, 47).m_171488_(-8.0F, -8.0F, -6.0F, 16.0F, 16.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 17.0F, -9.0F, -1.309F, 0.0F, 0.0F));
      partdefinition.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(16, 20).m_171488_(-4.0F, 0.0F, -3.0F, 8.0F, 12.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 17.0F, -7.0F, 1.5708F, 0.0F, 0.0F));
      partdefinition.m_171599_("bodywear", CubeListBuilder.m_171558_().m_171514_(0, 38).m_171488_(-4.0F, 0.0F, -3.0F, 8.0F, 20.0F, 6.0F, new CubeDeformation(0.5F)), PartPose.m_171423_(0.0F, 17.0F, -7.0F, 1.5708F, 0.0F, 0.0F));
      PartDefinition arms = partdefinition.m_171599_("arms", CubeListBuilder.m_171558_().m_171514_(40, 38).m_171488_(-4.0F, 2.0F, -2.0F, 8.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).m_171514_(44, 22).m_171488_(-8.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 18.95F, -7.05F, -0.7505F, 0.0F, 0.0F));
      arms.m_171599_("mirrored", CubeListBuilder.m_171558_().m_171514_(44, 22).m_171480_().m_171488_(4.0F, -23.05F, -3.05F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171419_(0.0F, 21.05F, 1.05F));
      partdefinition.m_171599_("right_leg", CubeListBuilder.m_171558_().m_171514_(0, 22).m_171488_(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-3.0F, 22.0F, 1.0F, 1.5708F, 0.0F, 0.0F));
      partdefinition.m_171599_("left_leg", CubeListBuilder.m_171558_().m_171514_(0, 22).m_171488_(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(3.0F, 22.0F, 1.0F, 1.5708F, 0.0F, 0.0F));
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   public void m_6973_(LivingEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      RendererHelper.resetModelToDefaultPivots(this);
      limbSwing *= 2.0F;
      limbSwingAmount *= 2.0F;
      this.arms.f_104202_ = -7.0F + Mth.m_14089_(limbSwing * 0.5F + (float)Math.PI) * 1.6F * limbSwingAmount;
      this.f_102813_.f_104202_ = Mth.m_14089_(limbSwing * 0.96F + (float)Math.PI) * 2.6F * limbSwingAmount;
      this.f_102814_.f_104202_ = Mth.m_14089_(limbSwing * 0.96F) * 2.6F * limbSwingAmount;
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.f_102808_.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.nose.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.headwear.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.headwear2.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.f_102810_.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.bodywear.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.arms.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.f_102813_.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.f_102814_.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }

   public Iterable<ModelPart> m_5607_() {
      return ImmutableList.of(this.f_102808_, this.nose, this.headwear, this.headwear2);
   }

   public Iterable<ModelPart> m_5608_() {
      return ImmutableList.of(this.f_102810_, this.bodywear, this.arms, this.f_102813_, this.f_102814_);
   }
}
