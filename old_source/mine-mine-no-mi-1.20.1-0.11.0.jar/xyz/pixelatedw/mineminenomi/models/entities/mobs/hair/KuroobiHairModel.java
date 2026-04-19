package xyz.pixelatedw.mineminenomi.models.entities.mobs.hair;

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

public class KuroobiHairModel<T extends LivingEntity> extends HumanoidModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "kuroobi_hair"), "main");
   private final ModelPart ponytail;
   private final ModelPart rightKnot;
   private final ModelPart leftKnot;

   public KuroobiHairModel(ModelPart root) {
      super(root);
      this.ponytail = this.f_102808_.m_171324_("ponytail");
      this.rightKnot = this.f_102808_.m_171324_("rightKnot");
      this.leftKnot = this.f_102808_.m_171324_("leftKnot");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = HumanoidModel.m_170681_(CubeDeformation.f_171458_, 0.0F);
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition head = partdefinition.m_171599_("head", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      head.m_171599_("ponytail", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-0.5F, 0.5F, -0.5F, 1.0F, 12.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, -0.5F, 3.5F));
      head.m_171599_("rightKnot", CubeListBuilder.m_171558_().m_171514_(4, 0).m_171488_(-0.5F, -1.5F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-2.5F, -8.25F, 0.5F, 0.0F, 0.0F, -0.2618F));
      head.m_171599_("leftKnot", CubeListBuilder.m_171558_().m_171514_(4, 0).m_171480_().m_171488_(-0.5F, -1.5F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(2.5F, -8.25F, 0.5F, 0.0F, 0.0F, 0.2618F));
      return LayerDefinition.m_171565_(meshdefinition, 8, 16);
   }

   public void m_6973_(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      super.m_6973_(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.f_102808_.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }
}
