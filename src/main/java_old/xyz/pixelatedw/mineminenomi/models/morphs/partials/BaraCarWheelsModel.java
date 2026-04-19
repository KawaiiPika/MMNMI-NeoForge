package xyz.pixelatedw.mineminenomi.models.morphs.partials;

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

public class BaraCarWheelsModel<T extends LivingEntity> extends EntityModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "bara_car_wheels"), "main");
   private final ModelPart frontWheels;
   private final ModelPart frontRightWheel;
   private final ModelPart frontLeftWheel;
   private final ModelPart backWheels;
   private final ModelPart backLeftWheel;
   private final ModelPart backRightWheel;

   public BaraCarWheelsModel(ModelPart root) {
      this.frontWheels = root.m_171324_("frontWheels");
      this.frontRightWheel = this.frontWheels.m_171324_("frontRightWheel");
      this.frontLeftWheel = this.frontWheels.m_171324_("frontLeftWheel");
      this.backWheels = root.m_171324_("backWheels");
      this.backLeftWheel = this.backWheels.m_171324_("backLeftWheel");
      this.backRightWheel = this.backWheels.m_171324_("backRightWheel");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition frontWheels = partdefinition.m_171599_("frontWheels", CubeListBuilder.m_171558_().m_171514_(1, 11).m_171488_(-13.0F, -1.0F, -1.0F, 26.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).m_171514_(0, 10).m_171488_(-13.0F, -0.5F, -1.5F, 26.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).m_171514_(2, 11).m_171488_(-13.0F, -1.5F, -0.5F, 26.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 23.0F, -16.0F));
      frontWheels.m_171599_("frontRightWheel", CubeListBuilder.m_171558_().m_171514_(1, 47).m_171488_(-3.0F, -4.0F, -4.0F, 3.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)).m_171514_(2, 48).m_171488_(-3.0F, -4.5F, -3.5F, 3.0F, 9.0F, 7.0F, new CubeDeformation(-0.1F)).m_171514_(0, 46).m_171488_(-3.0F, -3.5F, -4.5F, 3.0F, 7.0F, 9.0F, new CubeDeformation(-0.1F)), PartPose.m_171419_(-13.0F, 0.0F, 0.0F));
      frontWheels.m_171599_("frontLeftWheel", CubeListBuilder.m_171558_().m_171514_(1, 47).m_171488_(0.0F, -4.0F, -4.0F, 3.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)).m_171514_(2, 48).m_171488_(0.0F, -4.5F, -3.5F, 3.0F, 9.0F, 7.0F, new CubeDeformation(-0.1F)).m_171514_(0, 46).m_171488_(0.0F, -3.5F, -4.5F, 3.0F, 7.0F, 9.0F, new CubeDeformation(-0.1F)), PartPose.m_171419_(13.0F, 0.0F, 0.0F));
      PartDefinition backWheels = partdefinition.m_171599_("backWheels", CubeListBuilder.m_171558_().m_171514_(2, 1).m_171488_(-21.0F, -2.25F, -2.25F, 26.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)).m_171514_(0, 0).m_171488_(-21.0F, -1.75F, -2.75F, 26.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)).m_171514_(4, 2).m_171488_(-21.0F, -2.75F, -1.75F, 26.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(8.0F, 20.75F, 14.25F));
      backWheels.m_171599_("backLeftWheel", CubeListBuilder.m_171558_().m_171514_(0, 18).m_171488_(0.0F, -7.75F, -7.25F, 6.0F, 15.0F, 15.0F, new CubeDeformation(0.0F)).m_171514_(1, 19).m_171488_(0.0F, -8.75F, -6.75F, 6.0F, 17.0F, 14.0F, new CubeDeformation(-0.1F)).m_171514_(0, 18).m_171488_(0.0F, -7.25F, -8.25F, 6.0F, 14.0F, 17.0F, new CubeDeformation(-0.1F)), PartPose.m_171419_(5.0F, 0.0F, 0.0F));
      backWheels.m_171599_("backRightWheel", CubeListBuilder.m_171558_().m_171514_(0, 18).m_171488_(-6.0F, -7.75F, -7.25F, 6.0F, 15.0F, 15.0F, new CubeDeformation(0.0F)).m_171514_(1, 19).m_171488_(-6.0F, -8.75F, -6.75F, 6.0F, 17.0F, 14.0F, new CubeDeformation(-0.1F)).m_171514_(0, 17).m_171488_(-6.0F, -7.25F, -8.25F, 6.0F, 14.0F, 17.0F, new CubeDeformation(-0.1F)), PartPose.m_171419_(-21.0F, 0.0F, 0.0F));
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.frontRightWheel.f_104203_ = limbSwing * 0.8F;
      this.frontLeftWheel.f_104203_ = limbSwing * 0.8F;
      this.backRightWheel.f_104203_ = limbSwing * 0.8F;
      this.backLeftWheel.f_104203_ = limbSwing * 0.8F;
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.frontWheels.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.backWheels.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }
}
