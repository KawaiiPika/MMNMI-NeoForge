package xyz.pixelatedw.mineminenomi.models.entities.projectiles;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class PawModel<T extends Entity> extends EntityModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "paw"), "main");
   private final ModelPart paw;

   public PawModel(ModelPart root) {
      this.paw = root.m_171324_("paw");
   }

   public static LayerDefinition createLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      partdefinition.m_171599_("paw", CubeListBuilder.m_171558_().m_171514_(48, 21).m_171488_(8.0F, -11.119F, -2.5F, 3.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)).m_171514_(0, 2).m_171488_(-8.0F, -6.619F, -4.0F, 16.0F, 14.0F, 8.0F, new CubeDeformation(0.0F)).m_171514_(29, 26).m_171488_(-5.5F, -13.119F, -1.5F, 5.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).m_171514_(48, 0).m_171488_(1.0F, -13.619F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).m_171514_(48, 0).m_171488_(-11.5F, -11.619F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).m_171514_(2, 0).m_171488_(-7.0F, -7.619F, -4.0F, 14.0F, 16.0F, 8.0F, new CubeDeformation(0.0F)).m_171514_(48, 21).m_171488_(1.5F, -13.119F, -2.5F, 3.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)).m_171514_(50, 10).m_171488_(1.5F, -14.119F, -1.5F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)).m_171514_(48, 0).m_171488_(-5.0F, -13.619F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).m_171514_(0, 0).m_171488_(-7.5F, -7.119F, -4.5F, 15.0F, 15.0F, 9.0F, new CubeDeformation(0.0F)).m_171514_(0, 0).m_171488_(-7.0F, -6.619F, -5.0F, 14.0F, 14.0F, 10.0F, new CubeDeformation(0.0F)).m_171514_(29, 26).m_171488_(7.0F, -11.119F, -1.5F, 5.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).m_171514_(48, 21).m_171488_(-4.5F, -13.119F, -2.5F, 3.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)).m_171514_(50, 10).m_171488_(-11.0F, -12.119F, -1.5F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)).m_171514_(29, 26).m_171488_(0.5F, -13.119F, -1.5F, 5.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).m_171514_(50, 10).m_171488_(-4.5F, -14.119F, -1.5F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)).m_171514_(29, 26).m_171488_(-12.0F, -11.119F, -1.5F, 5.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).m_171514_(48, 21).m_171488_(-11.0F, -11.119F, -2.5F, 3.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)).m_171514_(48, 0).m_171488_(7.5F, -11.619F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).m_171514_(0, 0).m_171488_(-6.5F, -6.119F, -5.5F, 13.0F, 13.0F, 11.0F, new CubeDeformation(0.0F)).m_171514_(50, 10).m_171488_(8.0F, -12.119F, -1.5F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, -0.381F, 0.0F));
      return LayerDefinition.m_171565_(meshdefinition, 64, 32);
   }

   public void m_6973_(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.paw.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }
}
