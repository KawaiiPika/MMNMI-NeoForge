package xyz.pixelatedw.mineminenomi.models.entities.mobs.animals;

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
import net.minecraft.util.Mth;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.FlyingFishEntity;

public class FlyingFishModel<T extends FlyingFishEntity> extends EntityModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "flying_fish"), "main");
   private final ModelPart head;
   private final ModelPart body;
   private final ModelPart leftLowerFin;
   private final ModelPart rightLowerFin;
   private final ModelPart saddle;
   private final ModelPart leftFin;
   private final ModelPart rightFin;
   private final ModelPart tail;
   private final ModelPart tail2;
   private final ModelPart tail3;
   private final ModelPart tail4;
   private final ModelPart tail5;

   public FlyingFishModel(ModelPart root) {
      this.head = root.m_171324_("head");
      this.body = root.m_171324_("body");
      this.leftLowerFin = this.body.m_171324_("leftLowerFin");
      this.rightLowerFin = this.body.m_171324_("rightLowerFin");
      this.saddle = this.body.m_171324_("saddle");
      this.leftFin = this.body.m_171324_("leftFin");
      this.rightFin = this.body.m_171324_("rightFin");
      this.tail = this.body.m_171324_("tail");
      this.tail2 = this.tail.m_171324_("tail2");
      this.tail3 = this.tail2.m_171324_("tail3");
      this.tail4 = this.tail3.m_171324_("tail4");
      this.tail5 = this.tail4.m_171324_("tail5");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      partdefinition.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(0, 90).m_171488_(-6.0F, -5.625F, -25.625F, 12.0F, 15.0F, 9.0F, new CubeDeformation(0.0F)).m_171514_(0, 62).m_171488_(-6.5F, -6.125F, -19.625F, 13.0F, 16.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 8.625F, -5.375F));
      PartDefinition body = partdefinition.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(0, 22).m_171488_(-7.5F, -6.625F, -11.625F, 15.0F, 17.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 8.625F, -5.375F));
      body.m_171599_("leftLowerFin", CubeListBuilder.m_171558_().m_171514_(-16, 24).m_171488_(-0.0442F, 1.9208F, -20.0F, 10.0F, 0.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(7.5F, 6.875F, 11.375F, 0.0F, 0.0F, 0.3491F));
      body.m_171599_("rightLowerFin", CubeListBuilder.m_171558_().m_171514_(-16, 24).m_171488_(-9.9558F, 1.9208F, -20.0F, 10.0F, 0.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-7.5F, 6.875F, 11.375F, 0.0F, 0.0F, -0.3491F));
      body.m_171599_("saddle", CubeListBuilder.m_171558_().m_171514_(0, 158).m_171488_(-7.5F, -6.5F, -15.0F, 15.0F, 17.0F, 10.0F, new CubeDeformation(0.25F)).m_171514_(0, 132).m_171488_(-5.5F, -8.0F, -19.8125F, 11.0F, 2.0F, 22.0F, new CubeDeformation(0.0F)).m_171514_(46, 132).m_171488_(-5.5F, -16.0F, -9.8125F, 11.0F, 8.0F, 12.0F, new CubeDeformation(0.0F)).m_171514_(52, 158).m_171488_(-4.5F, -20.0F, -9.8125F, 9.0F, 4.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, -0.125F, 11.375F));
      body.m_171599_("leftFin", CubeListBuilder.m_171558_().m_171514_(-22, 0).m_171480_().m_171488_(-48.5F, 2.0F, -15.0F, 49.0F, 0.0F, 22.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171419_(-7.0F, 6.375F, 3.375F));
      body.m_171599_("rightFin", CubeListBuilder.m_171558_().m_171514_(-22, 0).m_171488_(-0.5F, 2.0F, -15.0F, 49.0F, 0.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(7.0F, 6.375F, 3.375F));
      PartDefinition tail = body.m_171599_("tail", CubeListBuilder.m_171558_().m_171514_(79, 23).m_171488_(-7.0F, -6.0F, -1.0F, 14.0F, 16.0F, 10.0F, new CubeDeformation(0.0F)).m_171514_(116, 98).m_171488_(0.0F, -18.0F, 3.0F, 0.0F, 24.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, -0.125F, 10.375F));
      PartDefinition tail2 = tail.m_171599_("tail2", CubeListBuilder.m_171558_().m_171514_(90, 50).m_171488_(-6.0F, -5.0F, -2.0F, 12.0F, 15.0F, 7.0F, new CubeDeformation(0.0F)).m_171514_(104, 102).m_171488_(0.0F, -15.0F, 0.0F, 0.0F, 21.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, -0.5F, 9.0F));
      PartDefinition tail3 = tail2.m_171599_("tail3", CubeListBuilder.m_171558_().m_171514_(94, 73).m_171488_(-5.5F, -5.0F, -2.0F, 11.0F, 14.0F, 6.0F, new CubeDeformation(0.0F)).m_171514_(92, 106).m_171488_(0.0F, -13.0F, 0.0F, 0.0F, 18.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 0.5F, 5.0F));
      PartDefinition tail4 = tail3.m_171599_("tail4", CubeListBuilder.m_171558_().m_171514_(99, 0).m_171488_(-4.0F, -3.25F, -0.5F, 8.0F, 11.0F, 3.0F, new CubeDeformation(0.0F)).m_171514_(84, 111).m_171488_(0.0F, -10.25F, -0.5F, 0.0F, 14.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, -0.25F, 4.5F));
      tail4.m_171599_("tail5", CubeListBuilder.m_171558_().m_171514_(42, 68).m_171488_(0.0F, -21.25F, 0.0F, 0.0F, 39.0F, 21.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 0.0F, 2.5F));
      return LayerDefinition.m_171565_(meshdefinition, 256, 256);
   }

   public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.rightFin.f_104205_ = Mth.m_14089_((float)entity.f_19797_ * 0.9F) * 0.2F;
      this.rightFin.f_104203_ = Mth.m_14089_((float)entity.f_19797_ * 0.5F) * 0.4F;
      this.leftFin.f_104205_ = -Mth.m_14089_((float)entity.f_19797_ * 0.9F) * 0.2F;
      this.leftFin.f_104203_ = Mth.m_14089_((float)entity.f_19797_ * 0.5F) * 0.4F;
      this.rightLowerFin.f_104203_ = Mth.m_14089_((float)entity.f_19797_ * 0.5F) * 0.2F;
      this.leftLowerFin.f_104203_ = Mth.m_14089_((float)entity.f_19797_ * 0.5F) * 0.2F;
      this.tail.f_104204_ = Mth.m_14089_((float)entity.f_19797_ * 0.3F) * 0.05F;
      this.tail2.f_104204_ = Mth.m_14089_((float)entity.f_19797_ * 0.3F) * 0.05F;
      this.tail3.f_104204_ = Mth.m_14089_((float)entity.f_19797_ * 0.3F) * 0.05F;
      this.tail4.f_104204_ = Mth.m_14089_((float)entity.f_19797_ * 0.3F) * 0.05F;
      this.tail5.f_104204_ = Mth.m_14089_((float)entity.f_19797_ * 0.3F) * 0.05F;
   }

   public void renderSaddle() {
      this.saddle.f_104207_ = true;
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.head.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.body.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.saddle.f_104207_ = false;
   }
}
