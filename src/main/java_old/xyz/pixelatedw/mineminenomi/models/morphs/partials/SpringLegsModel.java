package xyz.pixelatedw.mineminenomi.models.morphs.partials;

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

public class SpringLegsModel<T extends LivingEntity> extends HumanoidModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "spring_legs"), "main");

   public SpringLegsModel(ModelPart root) {
      super(root);
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = HumanoidModel.m_170681_(new CubeDeformation(0.0F), 0.0F);
      PartDefinition partdefinition = meshdefinition.m_171576_();
      partdefinition.m_171599_("right_leg", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-1.0F, -0.25F, -2.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.01F)).m_171514_(0, 0).m_171488_(1.0F, 0.0F, -2.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).m_171514_(0, 0).m_171488_(-2.0F, 0.0F, 1.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).m_171514_(0, 0).m_171488_(-2.0F, 1.0F, -2.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)).m_171514_(0, 0).m_171488_(-2.0F, 1.5F, -2.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.01F)).m_171514_(0, 0).m_171488_(1.0F, 2.0F, -2.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)).m_171514_(0, 0).m_171488_(-2.0F, 2.5F, 1.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.01F)).m_171514_(0, 0).m_171488_(-2.0F, 3.0F, -2.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)).m_171514_(0, 0).m_171488_(-2.0F, 3.5F, -2.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.01F)).m_171514_(0, 0).m_171488_(1.0F, 4.0F, -2.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)).m_171514_(0, 0).m_171488_(-2.0F, 4.5F, 1.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.01F)).m_171514_(0, 0).m_171488_(-2.0F, 5.0F, -2.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)).m_171514_(0, 0).m_171488_(-2.0F, 5.5F, -2.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.01F)).m_171514_(0, 0).m_171488_(1.0F, 6.0F, -2.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)).m_171514_(0, 0).m_171488_(-2.0F, 6.5F, 1.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.01F)).m_171514_(0, 0).m_171488_(-2.0F, 7.0F, -2.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)).m_171514_(0, 0).m_171488_(-2.0F, 7.5F, -2.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.01F)).m_171514_(0, 0).m_171488_(1.0F, 8.0F, -2.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)).m_171514_(0, 0).m_171488_(-2.0F, 8.5F, 1.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.01F)).m_171514_(0, 0).m_171488_(-2.0F, 9.0F, -2.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)).m_171514_(0, 0).m_171488_(-2.0F, 9.5F, -2.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.01F)).m_171514_(0, 0).m_171488_(1.0F, 10.0F, -2.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)).m_171514_(0, 0).m_171488_(-2.0F, 10.5F, 1.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).m_171514_(0, 0).m_171488_(-2.0F, 11.0F, -2.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)).m_171514_(0, 0).m_171488_(-1.0F, 11.0F, -2.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(-2.0F, 12.0F, 0.0F));
      partdefinition.m_171599_("left_leg", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171480_().m_171488_(-1.0F, -0.25F, -2.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.01F)).m_171555_(false).m_171514_(0, 0).m_171480_().m_171488_(-2.0F, 0.0F, -2.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).m_171555_(false).m_171514_(0, 0).m_171480_().m_171488_(-2.0F, 0.0F, 1.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).m_171555_(false).m_171514_(0, 0).m_171480_().m_171488_(1.0F, 1.0F, -2.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)).m_171555_(false).m_171514_(0, 0).m_171480_().m_171488_(-2.0F, 1.5F, -2.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.01F)).m_171555_(false).m_171514_(0, 0).m_171480_().m_171488_(-2.0F, 2.0F, -2.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)).m_171555_(false).m_171514_(0, 0).m_171480_().m_171488_(-2.0F, 2.5F, 1.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.01F)).m_171555_(false).m_171514_(0, 0).m_171480_().m_171488_(1.0F, 3.0F, -2.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)).m_171555_(false).m_171514_(0, 0).m_171480_().m_171488_(-2.0F, 3.5F, -2.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.01F)).m_171555_(false).m_171514_(0, 0).m_171480_().m_171488_(-2.0F, 4.0F, -2.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)).m_171555_(false).m_171514_(0, 0).m_171480_().m_171488_(-2.0F, 4.5F, 1.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.01F)).m_171555_(false).m_171514_(0, 0).m_171480_().m_171488_(1.0F, 5.0F, -2.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)).m_171555_(false).m_171514_(0, 0).m_171480_().m_171488_(-2.0F, 5.5F, -2.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.01F)).m_171555_(false).m_171514_(0, 0).m_171480_().m_171488_(-2.0F, 6.0F, -2.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)).m_171555_(false).m_171514_(0, 0).m_171480_().m_171488_(-2.0F, 6.5F, 1.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.01F)).m_171555_(false).m_171514_(0, 0).m_171480_().m_171488_(1.0F, 7.0F, -2.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)).m_171555_(false).m_171514_(0, 0).m_171480_().m_171488_(-2.0F, 7.5F, -2.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.01F)).m_171555_(false).m_171514_(0, 0).m_171480_().m_171488_(-2.0F, 8.0F, -2.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)).m_171555_(false).m_171514_(0, 0).m_171480_().m_171488_(-2.0F, 8.5F, 1.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.01F)).m_171555_(false).m_171514_(0, 0).m_171480_().m_171488_(1.0F, 9.0F, -2.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)).m_171555_(false).m_171514_(0, 0).m_171480_().m_171488_(-2.0F, 9.5F, -2.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.01F)).m_171555_(false).m_171514_(0, 0).m_171480_().m_171488_(-2.0F, 10.0F, -2.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)).m_171555_(false).m_171514_(0, 0).m_171480_().m_171488_(-2.0F, 10.5F, 1.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).m_171555_(false).m_171514_(0, 0).m_171480_().m_171488_(1.0F, 11.0F, -2.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)).m_171555_(false).m_171514_(0, 0).m_171480_().m_171488_(-1.0F, 11.0F, -2.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171419_(2.0F, 12.0F, 0.0F));
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   public void m_6973_(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      if (!entity.m_20096_()) {
         limbSwing /= 4.0F;
         limbSwingAmount /= 4.0F;
         if (entity.m_20184_().f_82480_ > 0.1) {
            this.f_102813_.f_233554_ = 1.5F;
            this.f_102814_.f_233554_ = 1.5F;
         }
      }

      super.m_6973_(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.f_102813_.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.f_102814_.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }
}
