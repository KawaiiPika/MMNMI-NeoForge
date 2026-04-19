package xyz.pixelatedw.mineminenomi.models.armors;

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

public class WizardHatModel<T extends LivingEntity> extends HumanoidModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "wizard_hat"), "main");
   private final ModelPart hat1;
   private final ModelPart hat2;
   private final ModelPart hat3;
   private final ModelPart hat4;
   private final ModelPart hat5;

   public WizardHatModel(ModelPart root) {
      super(root);
      this.hat1 = this.f_102808_.m_171324_("hat1");
      this.hat2 = this.hat1.m_171324_("hat2");
      this.hat3 = this.hat2.m_171324_("hat3");
      this.hat4 = this.hat3.m_171324_("hat4");
      this.hat5 = this.hat4.m_171324_("hat5");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = HumanoidModel.m_170681_(CubeDeformation.f_171458_, 0.0F);
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition head = partdefinition.m_171599_("head", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      PartDefinition hat1 = head.m_171599_("hat1", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-4.5F, 0.0F, -4.5F, 9.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, -8.5F, 0.0F));
      PartDefinition hat2 = hat1.m_171599_("hat2", CubeListBuilder.m_171558_().m_171514_(0, 11).m_171488_(-4.0F, -2.0F, -4.0F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      PartDefinition hat3 = hat2.m_171599_("hat3", CubeListBuilder.m_171558_().m_171514_(0, 21).m_171488_(-3.5F, -5.0F, -3.5F, 7.0F, 3.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      PartDefinition hat4 = hat3.m_171599_("hat4", CubeListBuilder.m_171558_().m_171514_(0, 31).m_171488_(-3.0F, -7.0F, -3.0F, 6.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 0.5F, 0.0F));
      hat4.m_171599_("hat5", CubeListBuilder.m_171558_().m_171514_(0, 39).m_171488_(-2.5F, -9.0F, -2.5F, 5.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   public void m_6973_(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      super.m_6973_(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.f_102808_.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }
}
