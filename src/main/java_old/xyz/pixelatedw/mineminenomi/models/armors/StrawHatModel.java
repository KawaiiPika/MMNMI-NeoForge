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

public class StrawHatModel<T extends LivingEntity> extends HumanoidArmorModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "straw_hat"), "main");
   private final ModelPart strawhat;

   public StrawHatModel(ModelPart root) {
      super(root);
      this.strawhat = root.m_171324_("strawhat");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = HumanoidModel.m_170681_(new CubeDeformation(0.0F), 0.0F);
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition strawhat = partdefinition.m_171599_("strawhat", CubeListBuilder.m_171558_().m_171514_(-17, 47).m_171488_(-8.5F, -6.0F, -8.5F, 17.0F, 0.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, -0.275F, 0.0F));
      PartDefinition top = strawhat.m_171599_("top", CubeListBuilder.m_171558_().m_171514_(0, 22).m_171488_(-5.0F, -34.155F, -4.0F, 9.0F, 3.0F, 9.0F, new CubeDeformation(0.15F)), PartPose.m_171419_(0.5F, 25.0F, -0.5F));
      top.m_171599_("band", CubeListBuilder.m_171558_().m_171514_(0, 36).m_171488_(-5.0F, -32.255F, -4.0F, 9.0F, 1.0F, 9.0F, new CubeDeformation(0.25F)), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   public void m_6973_(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.strawhat.m_104315_(this.f_102808_);
      this.strawhat.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }
}
