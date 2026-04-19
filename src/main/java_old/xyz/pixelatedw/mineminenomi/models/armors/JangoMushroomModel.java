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

public class JangoMushroomModel<T extends LivingEntity> extends HumanoidModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "jango_mushroom"), "main");
   private final ModelPart hair;
   private final ModelPart hair2;

   public JangoMushroomModel(ModelPart root) {
      super(root);
      this.hair = root.m_171324_("hair");
      this.hair2 = this.hair.m_171324_("hair2");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = HumanoidModel.m_170681_(CubeDeformation.f_171458_, 0.0F);
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition hair = partdefinition.m_171599_("hair", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      hair.m_171599_("hair2", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-1.0F, -1.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(-0.2F)).m_171514_(0, 0).m_171488_(-1.0F, 2.5F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.3F)), PartPose.m_171423_(0.0F, 0.0F, -3.5F, -0.4363F, 0.0F, 0.0F));
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   public void m_6973_(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.hair.m_104315_(this.f_102808_);
      this.hair.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }
}
