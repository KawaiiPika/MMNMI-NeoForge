package xyz.pixelatedw.mineminenomi.models.armors;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.HumandrillEntity;

public class HumandrillArmorModel<T extends HumandrillEntity> extends HumanoidArmorModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "humandrill_armor"), "main");

   public HumandrillArmorModel(ModelPart root) {
      super(root);
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = HumanoidArmorModel.m_170681_(new CubeDeformation(0.0F), 0.0F);
      PartDefinition partdefinition = meshdefinition.m_171576_();
      partdefinition.m_171599_("left_leg", CubeListBuilder.m_171558_().m_171514_(0, 16).m_171480_().m_171488_(-2.25F, 6.0692F, -2.1596F, 4.0F, 12.0F, 4.0F, new CubeDeformation(1.8F)).m_171555_(false), PartPose.m_171419_(4.0F, 4.0F, 1.25F));
      partdefinition.m_171599_("right_leg", CubeListBuilder.m_171558_().m_171514_(0, 16).m_171488_(-1.75F, 6.0692F, -2.1596F, 4.0F, 12.0F, 4.0F, new CubeDeformation(1.8F)), PartPose.m_171419_(-4.0F, 4.0F, 1.25F));
      partdefinition.m_171599_("left_arm", CubeListBuilder.m_171558_().m_171514_(40, 16).m_171480_().m_171488_(0.0F, -2.0F, -1.95F, 4.0F, 12.0F, 4.0F, new CubeDeformation(2.4F)).m_171555_(false), PartPose.m_171419_(9.0F, -6.0F, 2.25F));
      partdefinition.m_171599_("right_arm", CubeListBuilder.m_171558_().m_171514_(40, 16).m_171488_(-4.0F, -2.0F, -1.95F, 4.0F, 12.0F, 4.0F, new CubeDeformation(2.4F)), PartPose.m_171419_(-9.0F, -6.0F, 2.25F));
      partdefinition.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(16, 16).m_171488_(-4.5F, -4.7662F, -0.4782F, 8.0F, 12.0F, 4.0F, new CubeDeformation(4.55F)), PartPose.m_171423_(0.5F, -2.0F, 1.0F, 0.0873F, 0.0F, 0.0F));
      partdefinition.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-4.0F, -9.5233F, -7.7379F, 8.0F, 8.0F, 8.0F, new CubeDeformation(-0.55F)), PartPose.m_171419_(0.0F, -9.0F, 0.0F));
      return LayerDefinition.m_171565_(meshdefinition, 64, 32);
   }

   public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.f_102814_.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.f_102813_.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.f_102812_.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.f_102811_.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.f_102810_.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.f_102808_.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }
}
