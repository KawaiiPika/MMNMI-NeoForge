package xyz.pixelatedw.mineminenomi.models.entities.mobs;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.entities.mobs.PacifistaEntity;

public class PacifistaModel<T extends PacifistaEntity> extends HumanoidModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "pacifista"), "main");

   public PacifistaModel(ModelPart root) {
      super(root);
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = HumanoidModel.m_170681_(new CubeDeformation(0.0F), 0.0F);
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      super.m_6973_(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
      this.f_102810_.f_233553_ = 1.3F;
      this.f_102810_.f_233554_ = 1.2F;
      this.f_102810_.f_233555_ = 1.6F;
      this.f_102811_.f_233554_ = 1.2F;
      this.f_102812_.f_233554_ = 1.2F;
      this.f_102813_.f_233554_ = 0.9F;
      this.f_102814_.f_233554_ = 0.9F;
      this.f_102810_.f_104201_ = -0.4F;
      this.f_102811_.f_104200_ = -6.0F;
      this.f_102811_.f_104201_ = 2.0F;
      this.f_102812_.f_104200_ = 6.0F;
      this.f_102812_.f_104201_ = 2.0F;
      this.f_102813_.f_104201_ = 14.0F;
      this.f_102814_.f_104201_ = 14.0F;
      this.f_102808_.f_104201_ = -0.0F;
      this.f_102809_.f_104201_ = -0.0F;
   }

   public void prepareMobModel(T entity, float p_102862_, float p_102863_, float p_102864_) {
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      super.m_7695_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }
}
