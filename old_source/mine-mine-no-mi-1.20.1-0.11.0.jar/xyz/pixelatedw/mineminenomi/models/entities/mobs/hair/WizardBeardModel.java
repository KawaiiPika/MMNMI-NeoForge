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

public class WizardBeardModel<T extends LivingEntity> extends HumanoidModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "wizard_beard"), "main");
   private final ModelPart beard1;
   private final ModelPart beard2;
   private final ModelPart beardExtra;
   private final ModelPart leftFancyBeard1;
   private final ModelPart rightFancyBeard1;

   public WizardBeardModel(ModelPart root) {
      super(root);
      this.beard1 = this.f_102808_.m_171324_("beard1");
      this.beard2 = this.beard1.m_171324_("beard2");
      this.beardExtra = this.beard1.m_171324_("beardExtra");
      this.leftFancyBeard1 = this.beardExtra.m_171324_("leftFancyBeard1");
      this.rightFancyBeard1 = this.beardExtra.m_171324_("rightFancyBeard1");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = HumanoidModel.m_170681_(CubeDeformation.f_171458_, 0.0F);
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition head = partdefinition.m_171599_("head", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      PartDefinition beard1 = head.m_171599_("beard1", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-2.5F, 0.0F, 0.0F, 5.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, -2.0F, -5.0F));
      beard1.m_171599_("beard2", CubeListBuilder.m_171558_().m_171514_(1, 9).m_171488_(-1.5F, -1.5F, -0.5F, 3.0F, 3.0F, 1.0F, new CubeDeformation(-0.001F)), PartPose.m_171423_(0.0F, 7.0F, 0.5F, 0.0F, 0.0F, 0.7854F));
      PartDefinition beardExtra = beard1.m_171599_("beardExtra", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      beardExtra.m_171599_("leftFancyBeard1", CubeListBuilder.m_171558_().m_171514_(1, 9).m_171488_(0.0F, -2.0F, 0.0F, 4.0F, 3.0F, 1.0F, new CubeDeformation(-0.001F)), PartPose.m_171423_(0.8F, 1.0F, 0.0F, 0.0F, 0.0F, 1.0472F));
      beardExtra.m_171599_("rightFancyBeard1", CubeListBuilder.m_171558_().m_171514_(1, 9).m_171488_(-4.0F, -2.0F, 0.0F, 4.0F, 3.0F, 1.0F, new CubeDeformation(-0.001F)), PartPose.m_171423_(-0.8F, 1.0F, 0.0F, 0.0F, 0.0F, -1.0472F));
      return LayerDefinition.m_171565_(meshdefinition, 16, 16);
   }

   public void m_6973_(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      super.m_6973_(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.f_102808_.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }
}
