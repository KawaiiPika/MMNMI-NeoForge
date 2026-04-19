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

public class TricorneModel<T extends LivingEntity> extends HumanoidArmorModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "tricorne"), "main");
   private final ModelPart tricorne;
   private final ModelPart side1;
   private final ModelPart side12;
   private final ModelPart side2;
   private final ModelPart side22;
   private final ModelPart side3;
   private final ModelPart side32;
   private final ModelPart mid;
   private final ModelPart bone;
   private final ModelPart filling1;
   private final ModelPart filling2;

   public TricorneModel(ModelPart root) {
      super(root);
      this.tricorne = root.m_171324_("tricorne");
      this.side1 = this.tricorne.m_171324_("side1");
      this.side12 = this.side1.m_171324_("side12");
      this.side2 = this.tricorne.m_171324_("side2");
      this.side22 = this.side2.m_171324_("side22");
      this.side3 = this.tricorne.m_171324_("side3");
      this.side32 = this.side3.m_171324_("side32");
      this.mid = this.tricorne.m_171324_("mid");
      this.bone = this.mid.m_171324_("bone");
      this.filling1 = this.tricorne.m_171324_("filling1");
      this.filling2 = this.tricorne.m_171324_("filling2");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = HumanoidModel.m_170681_(new CubeDeformation(0.0F), 0.0F);
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition tricorne = partdefinition.m_171599_("tricorne", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      PartDefinition side1 = tricorne.m_171599_("side1", CubeListBuilder.m_171558_().m_171514_(21, 0).m_171488_(-0.2997F, -0.5F, -10.6117F, 1.0F, 1.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-5.8303F, -5.5F, -3.1883F, 0.0F, -0.6109F, 0.0F));
      side1.m_171599_("side12", CubeListBuilder.m_171558_().m_171514_(0, 35).m_171488_(0.0F, -2.4F, -10.4F, 0.0F, 4.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-0.0497F, -2.0F, -0.2117F, 0.0F, 0.0F, 0.096F));
      PartDefinition side2 = tricorne.m_171599_("side2", CubeListBuilder.m_171558_().m_171514_(21, 0).m_171488_(-0.5F, -0.5F, -10.25F, 1.0F, 1.0F, 20.0F, new CubeDeformation(-0.01F)), PartPose.m_171423_(5.8961F, -5.5F, -3.1225F, 0.0F, 0.6109F, 0.0F));
      side2.m_171599_("side22", CubeListBuilder.m_171558_().m_171514_(0, 35).m_171488_(-0.5F, -3.4F, -10.5F, 0.0F, 4.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.75F, -1.0F, 0.0F, 0.0F, 0.0F, -0.1309F));
      PartDefinition side3 = tricorne.m_171599_("side3", CubeListBuilder.m_171558_().m_171514_(0, 22).m_171488_(-0.5F, -0.5F, -11.0F, 1.0F, 1.0F, 22.0F, new CubeDeformation(-0.001F)), PartPose.m_171423_(0.1F, -5.5F, 4.75F, 0.0F, -1.5708F, 0.0F));
      side3.m_171599_("side32", CubeListBuilder.m_171558_().m_171514_(0, 26).m_171488_(0.5F, -3.4F, -11.0F, 0.0F, 4.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-0.25F, -1.0F, 0.0F, 0.0F, 0.0F, -0.1309F));
      PartDefinition mid = tricorne.m_171599_("mid", CubeListBuilder.m_171558_().m_171514_(44, 0).m_171488_(-7.5F, -4.5F, -10.25F, 10.0F, 5.0F, 10.0F, new CubeDeformation(-0.48F)), PartPose.m_171419_(2.5F, -5.5F, 5.15F));
      PartDefinition bone = mid.m_171599_("bone", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      tricorne.m_171599_("filling1", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-3.75F, -0.5F, -4.25F, 8.0F, 1.0F, 8.0F, new CubeDeformation(-0.05F)), PartPose.m_171423_(-0.1889F, -5.5F, -5.0154F, 0.0F, -0.7854F, 0.0F));
      tricorne.m_171599_("filling2", CubeListBuilder.m_171558_().m_171514_(26, 23).m_171488_(-16.0F, -0.5F, -6.5F, 21.0F, 1.0F, 9.0F, new CubeDeformation(-0.04F)), PartPose.m_171419_(5.5611F, -5.5F, 2.2346F));
      return LayerDefinition.m_171565_(meshdefinition, 128, 128);
   }

   public void m_6973_(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.tricorne.m_104315_(this.f_102808_);
      this.tricorne.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }
}
