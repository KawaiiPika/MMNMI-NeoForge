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

public class FishmanGarfishModel<T extends LivingEntity> extends HumanoidModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "fishman_garfish"), "main");
   private final ModelPart lips;

   public FishmanGarfishModel(ModelPart root) {
      super(root);
      this.lips = this.f_102808_.m_171324_("lips");
   }

   public static LayerDefinition createLayer() {
      MeshDefinition meshdefinition = HumanoidModel.m_170681_(CubeDeformation.f_171458_, 0.0F);
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition head = partdefinition.m_171599_("head", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      head.m_171599_("lips", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-0.5F, -0.75F, -2.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).m_171514_(0, 4).m_171488_(-1.5F, -2.05F, -2.001F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, -1.5F, -4.0F));
      return LayerDefinition.m_171565_(meshdefinition, 8, 8);
   }

   public void m_6973_(LivingEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.f_102808_.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }
}
