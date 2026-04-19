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

public class KameWalkModel<T extends LivingEntity> extends HumanoidModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "kame_walk"), "main");
   private final ModelPart shell;

   public KameWalkModel(ModelPart root) {
      super(root);
      this.shell = root.m_171324_("shell");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = HumanoidModel.m_170681_(new CubeDeformation(0.0F), 0.0F);
      PartDefinition partdefinition = meshdefinition.m_171576_();
      partdefinition.m_171599_("shell", CubeListBuilder.m_171558_().m_171514_(0, 18).m_171488_(-5.5F, -1.0F, 1.0F, 11.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).m_171514_(0, 13).m_171488_(-5.5F, 10.0F, 1.0F, 11.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).m_171514_(10, 23).m_171488_(-5.5F, 2.0F, 1.0F, 3.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)).m_171514_(0, 23).m_171488_(2.5F, 2.0F, 1.0F, 3.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)).m_171514_(0, 0).m_171488_(-4.5F, 0.0F, 3.0F, 9.0F, 12.0F, 1.0F, new CubeDeformation(0.0F)).m_171514_(20, 0).m_171488_(-3.5F, 1.0F, 4.0F, 7.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   public void m_6973_(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.shell.m_104315_(this.f_102810_);
      this.shell.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }
}
