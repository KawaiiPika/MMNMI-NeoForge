package xyz.pixelatedw.mineminenomi.models.entities.mobs.animals;

import com.google.common.collect.ImmutableSet;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
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
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import xyz.pixelatedw.mineminenomi.api.IAgeableListModelExtension;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.AbstractGorillaEntity;

public class GorillaModel<T extends AbstractGorillaEntity> extends HumanoidModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "gorilla"), "main");
   private final ModelPart rightArm2;
   private final ModelPart rightPalm;
   private final ModelPart leftArm2;
   private final ModelPart leftPalm;
   private final ModelPart rightFeet1;
   private final ModelPart rightFeet2;
   private final ModelPart rightFeet3;
   private final ModelPart leftFeet1;
   private final ModelPart leftFeet2;
   private final ModelPart leftFeet3;

   public GorillaModel(ModelPart root) {
      super(root);
      this.rightArm2 = this.f_102811_.m_171324_("rightArm2");
      this.rightPalm = this.rightArm2.m_171324_("rightPalm");
      this.leftArm2 = this.f_102812_.m_171324_("leftArm2");
      this.leftPalm = this.leftArm2.m_171324_("leftPalm");
      this.rightFeet1 = this.f_102813_.m_171324_("rightFeet1");
      this.rightFeet2 = this.f_102813_.m_171324_("rightFeet2");
      this.rightFeet3 = this.f_102813_.m_171324_("rightFeet3");
      this.leftFeet1 = this.f_102814_.m_171324_("leftFeet1");
      this.leftFeet2 = this.f_102814_.m_171324_("leftFeet2");
      this.leftFeet3 = this.f_102814_.m_171324_("leftFeet3");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = HumanoidModel.m_170681_(new CubeDeformation(0.0F), 0.0F);
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition rightArm = partdefinition.m_171599_("right_arm", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-2.0F, 0.5F, -2.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-2.0F, 7.0F, 2.0F, 0.0F, 0.0F, 0.8727F));
      PartDefinition rightArm2 = rightArm.m_171599_("rightArm2", CubeListBuilder.m_171558_().m_171514_(18, 0).m_171488_(-2.0F, 0.25F, -2.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(-0.01F)).m_171514_(36, 0).m_171488_(-2.5F, 5.25F, -2.5F, 5.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 6.75F, 0.0F, 0.0F, 0.0F, -0.1745F));
      rightArm2.m_171599_("rightPalm", CubeListBuilder.m_171558_().m_171514_(35, 9).m_171488_(-1.25F, -1.0F, -2.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.1771F, 7.7431F, 0.0F, 0.0F, 0.0F, -0.2182F));
      PartDefinition leftArm = partdefinition.m_171599_("left_arm", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-2.0F, 0.5F, -2.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(2.0F, 7.0F, 2.0F, 0.0F, 0.0F, -0.8727F));
      PartDefinition leftArm2 = leftArm.m_171599_("leftArm2", CubeListBuilder.m_171558_().m_171514_(18, 0).m_171488_(-2.0F, 0.25F, -2.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(-0.01F)).m_171514_(36, 0).m_171488_(-2.5F, 5.25F, -2.5F, 5.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 6.75F, 0.0F, 0.0F, 0.0F, 0.1745F));
      leftArm2.m_171599_("leftPalm", CubeListBuilder.m_171558_().m_171514_(35, 9).m_171488_(-1.0F, -1.0F, -2.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-0.8229F, 7.7431F, 0.0F, 0.0F, 0.0F, 0.2182F));
      PartDefinition rightLeg = partdefinition.m_171599_("right_leg", CubeListBuilder.m_171558_().m_171514_(39, 19).m_171488_(-0.2929F, 0.1667F, -0.25F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-3.5F, 18.8333F, 1.5F, 0.0F, 0.7854F, 0.0F));
      rightLeg.m_171599_("rightFeet1", CubeListBuilder.m_171558_().m_171514_(38, 28).m_171488_(-0.5489F, 4.1667F, -2.5077F, 1.0F, 1.0F, 4.0F, new CubeDeformation(-0.01F)), PartPose.m_171423_(0.0F, 0.0F, 0.0F, 0.0F, 0.1309F, 0.0F));
      rightLeg.m_171599_("rightFeet2", CubeListBuilder.m_171558_().m_171514_(38, 28).m_171488_(0.4571F, 4.1667F, -2.5F, 1.0F, 1.0F, 4.0F, new CubeDeformation(-0.01F)), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      rightLeg.m_171599_("rightFeet3", CubeListBuilder.m_171558_().m_171514_(49, 29).m_171488_(1.1744F, 4.1667F, -1.7126F, 1.0F, 1.0F, 3.0F, new CubeDeformation(-0.01F)), PartPose.m_171423_(0.0F, 0.0F, -0.25F, 0.0F, -0.3054F, 0.0F));
      PartDefinition leftLeg = partdefinition.m_171599_("left_leg", CubeListBuilder.m_171558_().m_171514_(39, 19).m_171488_(-1.0F, 0.1667F, -0.9571F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(2.5F, 18.8333F, 1.5F, 0.0F, -0.7854F, 0.0F));
      leftLeg.m_171599_("leftFeet1", CubeListBuilder.m_171558_().m_171514_(38, 28).m_171488_(0.1577F, 4.1667F, -3.1511F, 1.0F, 1.0F, 4.0F, new CubeDeformation(-0.01F)), PartPose.m_171423_(0.0F, 0.0F, 0.0F, 0.0F, -0.1309F, 0.0F));
      leftLeg.m_171599_("leftFeet2", CubeListBuilder.m_171558_().m_171514_(38, 28).m_171488_(-0.75F, 4.1667F, -3.1571F, 1.0F, 1.0F, 4.0F, new CubeDeformation(-0.01F)), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      leftLeg.m_171599_("leftFeet3", CubeListBuilder.m_171558_().m_171514_(49, 29).m_171488_(-1.1874F, 4.1667F, -2.6744F, 1.0F, 1.0F, 3.0F, new CubeDeformation(-0.01F)), PartPose.m_171423_(0.0F, 0.0F, 0.0F, 0.0F, 0.3054F, 0.0F));
      PartDefinition body = partdefinition.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(0, 32).m_171488_(-5.5F, -3.25F, -5.5F, 11.0F, 3.0F, 10.0F, new CubeDeformation(0.0F)).m_171514_(0, 46).m_171488_(-6.0F, -0.25F, -6.0F, 12.0F, 7.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 12.25F, 1.5F));
      body.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(0, 18).m_171488_(-4.5F, -2.5F, -4.25F, 9.0F, 5.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, -5.25F, -0.25F));
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      ((IAgeableListModelExtension)this).getParts().forEach(ModelPart::m_233569_);
      boolean isMoving = entity.m_20184_().f_82479_ != (double)0.0F || entity.m_20184_().f_82481_ != (double)0.0F;
      if (isMoving) {
         this.f_102813_.f_104204_ = 0.3F;
         this.f_102814_.f_104204_ = -0.3F;
      }

      float f = 1.0F;
      this.f_102811_.f_104203_ = Mth.m_14089_(limbSwing * 0.6F + (float)Math.PI) * 2.0F * limbSwingAmount * 0.5F / f;
      this.f_102812_.f_104203_ = Mth.m_14089_(limbSwing * 0.6F) * 2.0F * limbSwingAmount * 0.5F / f;
      this.f_102813_.f_104203_ = Mth.m_14089_(limbSwing * 0.8F) * 1.4F * limbSwingAmount / f;
      this.f_102814_.f_104203_ = Mth.m_14089_(limbSwing * 0.8F + (float)Math.PI) * 1.4F * limbSwingAmount / f;
      this.m_7884_(entity, ageInTicks);
      Animation.animationAngles(entity, this, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.f_102811_.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.f_102812_.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.f_102813_.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.f_102814_.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.f_102810_.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }

   public ModelPart m_5585_() {
      return this.f_102808_;
   }

   protected ModelPart m_102851_(HumanoidArm side) {
      return side == HumanoidArm.LEFT ? this.f_102812_ : this.f_102811_;
   }

   public void m_6002_(HumanoidArm side, PoseStack matrixStack) {
      this.m_102851_(side).m_104299_(matrixStack);
      matrixStack.m_252781_(Axis.f_252403_.m_252977_(side == HumanoidArm.RIGHT ? -20.0F : 20.0F));
      matrixStack.m_85837_(side == HumanoidArm.RIGHT ? -0.12 : 0.12, 0.32, 0.15);
   }

   protected Iterable<ModelPart> m_5607_() {
      return ImmutableSet.of(this.f_102808_);
   }

   protected Iterable<ModelPart> m_5608_() {
      return ImmutableSet.of(this.f_102810_, this.f_102812_, this.f_102811_, this.f_102814_, this.f_102813_);
   }
}
