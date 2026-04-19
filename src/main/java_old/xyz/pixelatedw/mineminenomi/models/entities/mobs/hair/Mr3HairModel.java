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

public class Mr3HairModel<T extends LivingEntity> extends HumanoidModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "mr3_hair"), "main");
   private final ModelPart hair;

   public Mr3HairModel(ModelPart root) {
      super(root);
      this.hair = root.m_171324_("hair");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = HumanoidModel.m_170681_(CubeDeformation.f_171458_, 0.0F);
      PartDefinition partdefinition = meshdefinition.m_171576_();
      partdefinition.m_171599_("hair", CubeListBuilder.m_171558_().m_171514_(0, 2).m_171488_(-1.0F, -9.0F, 0.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).m_171514_(0, 0).m_171488_(-2.0F, -17.0F, 0.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).m_171514_(7, 3).m_171488_(0.0F, -13.0F, 0.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).m_171514_(9, 1).m_171488_(-1.0F, -13.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.2F)).m_171514_(4, 4).m_171488_(2.0F, -12.25F, 0.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).m_171514_(0, 4).m_171488_(2.0F, -16.75F, 0.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).m_171514_(8, 5).m_171488_(-3.0F, -9.25F, 0.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).m_171514_(0, 9).m_171488_(-4.0F, -9.5F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).m_171514_(7, 8).m_171488_(-3.0F, -16.5F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      return LayerDefinition.m_171565_(meshdefinition, 16, 16);
   }

   public void m_6973_(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      super.m_6973_(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.hair.m_104315_(this.f_102808_);
      this.hair.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }
}
