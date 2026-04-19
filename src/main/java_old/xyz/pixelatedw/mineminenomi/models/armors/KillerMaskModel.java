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

public class KillerMaskModel<T extends LivingEntity> extends HumanoidArmorModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "killer_mask"), "main");
   private final ModelPart mask;

   public KillerMaskModel(ModelPart root) {
      super(root);
      this.mask = root.m_171324_("mask");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = HumanoidModel.m_170681_(new CubeDeformation(0.0F), 0.0F);
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition mask = partdefinition.m_171599_("mask", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.5F)), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      mask.m_171599_("rightEar", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-0.25F, -1.75F, -1.0F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).m_171514_(0, 16).m_171488_(-0.75F, -1.25F, -0.5F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(-5.0F, -3.5F, 0.25F));
      mask.m_171599_("leftEar", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-0.25F, -1.75F, -1.0F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).m_171514_(0, 16).m_171488_(0.25F, -1.25F, -0.5F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(4.5F, -3.5F, 0.25F));
      PartDefinition bar = mask.m_171599_("bar", CubeListBuilder.m_171558_().m_171514_(0, 30).m_171488_(-7.25F, -3.25F, 0.75F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).m_171514_(0, 30).m_171488_(4.75F, -3.25F, 0.75F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).m_171514_(0, 27).m_171488_(-7.25F, -2.0F, 1.7F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.02F)).m_171514_(0, 28).m_171488_(5.75F, -2.0F, 1.7F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.02F)).m_171514_(0, 26).m_171488_(-7.25F, -2.0F, 5.7F, 14.0F, 1.0F, 1.0F, new CubeDeformation(0.01F)), PartPose.m_171419_(0.25F, -1.0F, -0.5F));
      PartDefinition bar2 = bar.m_171599_("bar2", CubeListBuilder.m_171558_(), PartPose.m_171419_(-6.75F, -1.7932F, 1.5548F));
      bar2.m_171599_("bar2_r1", CubeListBuilder.m_171558_().m_171514_(0, 28).m_171488_(-0.5F, -1.25F, -0.25F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.01F)), PartPose.m_171423_(0.0F, 0.0F, 0.0F, 0.4363F, 0.0F, 0.0F));
      PartDefinition bar6 = bar.m_171599_("bar6", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      bar6.m_171599_("bar6_r1", CubeListBuilder.m_171558_().m_171514_(0, 28).m_171488_(12.5F, -1.25F, -0.25F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.01F)), PartPose.m_171423_(-6.75F, -1.7932F, 1.5548F, 0.4363F, 0.0F, 0.0F));
      return LayerDefinition.m_171565_(meshdefinition, 32, 32);
   }

   public void m_6973_(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.mask.m_104315_(this.f_102808_);
      this.mask.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }
}
