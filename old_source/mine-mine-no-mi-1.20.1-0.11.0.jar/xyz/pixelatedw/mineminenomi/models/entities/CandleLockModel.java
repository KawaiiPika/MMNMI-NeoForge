package xyz.pixelatedw.mineminenomi.models.entities;

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
import net.minecraft.world.entity.LivingEntity;

public class CandleLockModel<T extends LivingEntity> extends EntityModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "candle_lock"), "main");
   private final ModelPart candleLock1;
   private final ModelPart candleLock3;
   private final ModelPart candleLock2;

   public CandleLockModel(ModelPart root) {
      this.candleLock1 = root.m_171324_("candleLock1");
      this.candleLock3 = this.candleLock1.m_171324_("candleLock3");
      this.candleLock2 = this.candleLock1.m_171324_("candleLock2");
   }

   public static LayerDefinition createLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition candleLock1 = partdefinition.m_171599_("candleLock1", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-7.5F, -3.5F, -3.5F, 15.0F, 7.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 18.9F, 0.0F));
      candleLock1.m_171599_("candleLock3", CubeListBuilder.m_171558_().m_171514_(0, 17).m_171488_(7.5F, -4.0F, -4.0F, 4.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      candleLock1.m_171599_("candleLock2", CubeListBuilder.m_171558_().m_171514_(0, 17).m_171488_(-11.5F, -4.0F, -4.0F, 4.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   public void setupAnim(LivingEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.candleLock1.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }
}
