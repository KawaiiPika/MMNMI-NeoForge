package xyz.pixelatedw.mineminenomi.models.entities.mobs.animals;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.HeadedModel;
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
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.AbstractDugongEntity;

public class DugongModel<T extends AbstractDugongEntity> extends AgeableListModel<T> implements ArmedModel, HeadedModel {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "dugong"), "main");
   private final ModelPart head;
   private final ModelPart snout;
   private final ModelPart headShell;
   private final ModelPart body;
   private final ModelPart shell;
   private final ModelPart lowerBody;
   private final ModelPart lowerBody2;
   public final ModelPart tailBase;
   private final ModelPart tail2;
   private final ModelPart tail3;
   private final ModelPart tail4;
   private final ModelPart tailTip1;
   private final ModelPart tailTip2;
   public final ModelPart rightArm;
   public final ModelPart leftArm;

   public DugongModel(ModelPart root) {
      this.head = root.m_171324_("head");
      this.snout = this.head.m_171324_("snout");
      this.headShell = this.head.m_171324_("headShell");
      this.body = root.m_171324_("body");
      this.shell = this.body.m_171324_("shell");
      this.lowerBody = root.m_171324_("lowerBody");
      this.lowerBody2 = this.lowerBody.m_171324_("lowerBody2");
      this.tailBase = root.m_171324_("tailBase");
      this.tail2 = this.tailBase.m_171324_("tail2");
      this.tail3 = this.tail2.m_171324_("tail3");
      this.tail4 = this.tail3.m_171324_("tail4");
      this.tailTip1 = this.tail4.m_171324_("tailTip1");
      this.tailTip2 = this.tail4.m_171324_("tailTip2");
      this.rightArm = root.m_171324_("rightArm");
      this.leftArm = root.m_171324_("leftArm");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition head = partdefinition.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(0, 9).m_171488_(-2.5F, -4.75F, -2.8F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 10.75F, -2.2F));
      head.m_171599_("snout", CubeListBuilder.m_171558_().m_171514_(0, 19).m_171488_(-1.5F, -1.0F, -3.0F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, -1.25F, -0.7F));
      head.m_171599_("headShell", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-3.0F, -2.5F, -2.0F, 6.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, -2.55F, 1.7F));
      PartDefinition body = partdefinition.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(20, 13).m_171488_(-4.0F, -4.0F, -2.5F, 8.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 15.0F, -2.0F));
      body.m_171599_("shell", CubeListBuilder.m_171558_().m_171514_(19, 0).m_171488_(-4.5F, -0.5F, -3.5F, 9.0F, 9.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, -4.0F, 4.0F));
      PartDefinition lowerBody = partdefinition.m_171599_("lowerBody", CubeListBuilder.m_171558_().m_171514_(43, 23).m_171488_(-3.0F, -3.0F, -2.0F, 6.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 20.7189F, -0.9321F, 0.4363F, 0.0F, 0.0F));
      lowerBody.m_171599_("lowerBody2", CubeListBuilder.m_171558_().m_171514_(11, 27).m_171488_(-3.0F, -0.75F, -2.0079F, 6.0F, 2.0F, 3.0F, new CubeDeformation(-0.01F)), PartPose.m_171423_(0.0F, 1.25F, 0.0F, 0.6981F, 0.0F, 0.0F));
      PartDefinition tailBase = partdefinition.m_171599_("tailBase", CubeListBuilder.m_171558_().m_171514_(44, 0).m_171488_(-3.0F, -2.0F, -2.0F, 6.0F, 4.0F, 4.0F, new CubeDeformation(0.01F)), PartPose.m_171423_(0.0F, 22.1689F, 1.8179F, 1.5708F, 0.0F, 0.0F));
      PartDefinition tail2 = tailBase.m_171599_("tail2", CubeListBuilder.m_171558_().m_171514_(48, 9).m_171488_(-2.5F, -0.0912F, -1.3767F, 5.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 1.1491F, -0.3358F, 0.6981F, 0.0F, 0.0F));
      PartDefinition tail3 = tail2.m_171599_("tail3", CubeListBuilder.m_171558_().m_171514_(51, 17).m_171488_(-2.0F, 0.0F, -1.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 2.7088F, 0.1233F));
      PartDefinition tail4 = tail3.m_171599_("tail4", CubeListBuilder.m_171558_().m_171514_(43, 10).m_171488_(-0.5F, 0.2856F, -2.0321F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 4.2169F, -1.2119F, -2.9671F, 0.0F, 0.0F));
      tail4.m_171599_("tailTip1", CubeListBuilder.m_171558_().m_171514_(42, 15).m_171488_(-1.4162F, 0.208F, -2.0917F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-1.5314F, -0.4396F, -0.3374F, 0.0F, -0.2269F, 0.3491F));
      tail4.m_171599_("tailTip2", CubeListBuilder.m_171558_().m_171514_(42, 15).m_171488_(-1.5838F, 0.208F, -2.0917F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(1.5371F, -0.478F, -0.3502F, 0.0F, 0.2269F, -0.3491F));
      partdefinition.m_171599_("rightArm", CubeListBuilder.m_171558_().m_171514_(0, 22).m_171488_(-1.5F, -1.0F, -1.5F, 2.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(-4.5F, 12.3F, -2.0F));
      partdefinition.m_171599_("leftArm", CubeListBuilder.m_171558_().m_171514_(0, 22).m_171480_().m_171488_(-0.5F, -1.0F, -1.5F, 2.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171419_(4.5F, 12.3F, -2.0F));
      return LayerDefinition.m_171565_(meshdefinition, 64, 32);
   }

   public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.head.f_104202_ = -2.2F;
      this.head.f_104201_ = 10.75F;
      this.head.f_104203_ = 0.0F;
      this.rightArm.f_104200_ = -4.5F;
      this.rightArm.f_104201_ = 12.3F;
      this.rightArm.f_104202_ = -1.5F;
      this.leftArm.f_104200_ = 4.5F;
      this.leftArm.f_104201_ = 12.3F;
      this.leftArm.f_104202_ = -1.5F;
      this.rightArm.f_104204_ = this.rightArm.f_104205_ = 0.0F;
      this.leftArm.f_104204_ = this.leftArm.f_104205_ = 0.0F;
      this.body.f_104202_ = -2.0F;
      this.lowerBody.f_104202_ = -0.9321F;
      this.lowerBody.f_104203_ = 0.4363F;
      this.tailBase.f_104202_ = 1.8179F;
      this.tailBase.f_104201_ = 22.1689F;
      this.tailBase.f_104203_ = 1.5708F;
      this.tail2.f_104203_ = 0.69F;
      this.rightArm.f_104203_ = Mth.m_14089_(limbSwing * 0.6662F + (float)Math.PI) * 2.0F * limbSwingAmount * 0.5F;
      this.leftArm.f_104203_ = Mth.m_14089_(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F;
      this.head.f_104203_ = headPitch * ((float)Math.PI / 180F);
      this.head.f_104204_ = netHeadYaw * ((float)Math.PI / 180F);
      if (entity.isCheering()) {
         this.rightArm.f_104203_ = (float)Math.toRadians((double)180.0F) + Mth.m_14031_((float)((double)ageInTicks * Math.PI * (double)0.3F)) * 0.2F;
         this.leftArm.f_104203_ = (float)Math.toRadians((double)180.0F) + Mth.m_14031_((float)((double)ageInTicks * Math.PI * (double)0.3F)) * 0.2F;
      }

      if (entity.isResting()) {
         this.head.f_104203_ = (float)Math.toRadians((double)-90.0F);
         this.head.f_104202_ = -4.0F;
         this.head.f_104201_ = 9.0F;
         this.rightArm.f_104204_ = (float)Math.toRadians((double)90.0F);
         this.rightArm.f_104203_ = (float)Math.toRadians((double)-45.0F);
         this.rightArm.f_104202_ = -3.5F;
         this.leftArm.f_104204_ = (float)Math.toRadians((double)-90.0F);
         this.leftArm.f_104203_ = (float)Math.toRadians((double)-45.0F);
         this.leftArm.f_104202_ = -3.5F;
         this.lowerBody.f_104203_ = 0.0F;
         this.lowerBody.f_104202_ = -2.0F;
         this.tailBase.f_104203_ = 0.0F;
         this.tailBase.f_104201_ = 23.0F;
         this.tailBase.f_104202_ = -1.95F;
         this.tail2.f_104203_ = 0.0F;
      }

      if (entity.isTraining()) {
         switch (entity.getTrainingMode()) {
            case PUSHUPS:
               this.rightArm.f_104203_ = (float)Math.toRadians((double)-90.0F);
               this.leftArm.f_104203_ = (float)Math.toRadians((double)-90.0F);
               this.tailBase.f_104203_ = (float)Math.toRadians((double)30.0F);
               this.lowerBody.f_104203_ = (float)Math.toRadians((double)0.0F);
               float bodyMovement = -Mth.m_14089_(ageInTicks * 0.5F);
               this.lowerBody.f_104202_ = -2.0F;
               this.tailBase.f_104202_ = -1.0F;
               this.tailBase.f_104201_ = 24.0F;
               this.body.f_104202_ = this.head.f_104202_ = -0.7F * (3.5F - bodyMovement);
               break;
            case SHADOW_BOXING:
               float rightArmMovement = -Mth.m_14089_(ageInTicks * 1.2F);
               this.rightArm.f_104202_ = -2.5F * (0.5F - rightArmMovement);
               this.rightArm.f_104203_ = (float)Math.toRadians((double)-90.0F);
               this.rightArm.f_104204_ = 0.0F;
               float leftArmMovement = Mth.m_14089_(ageInTicks * 1.2F);
               this.leftArm.f_104202_ = -2.5F * (0.5F - leftArmMovement);
               this.leftArm.f_104203_ = (float)Math.toRadians((double)-90.0F);
               this.leftArm.f_104204_ = 0.0F;
         }
      }

      if (entity.isHappy()) {
         this.tail2.f_104203_ = 0.4F * (0.7F + Mth.m_14089_(ageInTicks * 0.4F));
         this.tail2.f_104205_ = this.tail2.f_104204_ = 0.0F;
         this.tail3.f_104203_ = 0.6F * this.tail2.f_104203_;
         this.tail3.f_104205_ = this.tail3.f_104204_ = 0.0F;
      } else {
         this.tail2.f_104203_ = 0.69F;
         this.tail3.f_104203_ = (float)Math.toRadians((double)0.0F);
         this.tail2.f_104205_ = Mth.m_14089_(limbSwing * 0.4F + (float)Math.PI) * 2.0F * limbSwingAmount * 0.3F;
         this.tail3.f_104205_ = Mth.m_14089_(limbSwing * 0.4F + (float)Math.PI) * 2.0F * limbSwingAmount * 0.3F;
      }

      this.setupAttackAnimation(entity, ageInTicks);
      Animation.animationAngles(entity, this, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
   }

   protected void setupAttackAnimation(T entity, float ageInTicks) {
      if (this.f_102608_ > 0.0F) {
         float f = this.f_102608_;
         this.body.f_104204_ = Mth.m_14031_(Mth.m_14116_(f) * ((float)Math.PI * 2F)) * 0.2F;
         ModelPart var10000 = this.rightArm;
         var10000.f_104204_ += this.body.f_104204_;
         var10000 = this.leftArm;
         var10000.f_104204_ += this.body.f_104204_;
         var10000 = this.leftArm;
         var10000.f_104203_ += this.body.f_104204_;
         f = 1.0F - this.f_102608_;
         f *= f;
         f *= f;
         f = 1.0F - f;
         float f1 = Mth.m_14031_(f * (float)Math.PI);
         float f2 = Mth.m_14031_(this.f_102608_ * (float)Math.PI) * -(this.head.f_104203_ - 0.7F) * 0.75F;
         this.rightArm.f_104203_ = (float)((double)this.rightArm.f_104203_ - ((double)f1 * 1.2 + (double)f2));
         var10000 = this.rightArm;
         var10000.f_104204_ += this.body.f_104204_ * 2.0F;
         var10000 = this.rightArm;
         var10000.f_104205_ += Mth.m_14031_(this.f_102608_ * (float)Math.PI) * -0.4F;
      }

   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      if (this.f_102610_) {
         poseStack.m_85841_(0.5F, 0.5F, 0.5F);
         poseStack.m_85837_((double)0.0F, (double)1.5F, (double)0.0F);
      }

      this.head.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.body.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.lowerBody.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.tailBase.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.rightArm.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.leftArm.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }

   public ModelPart m_5585_() {
      return this.head;
   }

   public void m_6002_(HumanoidArm side, PoseStack matrixStack) {
      this.getArm(side).m_104299_(matrixStack);
      matrixStack.m_85837_(side == HumanoidArm.RIGHT ? 0.03 : -0.03, -0.2, (double)0.0F);
   }

   protected ModelPart getArm(HumanoidArm side) {
      return side == HumanoidArm.LEFT ? this.leftArm : this.rightArm;
   }

   protected Iterable<ModelPart> m_5607_() {
      return ImmutableList.of(this.head);
   }

   protected Iterable<ModelPart> m_5608_() {
      return ImmutableList.of(this.body, this.lowerBody, this.rightArm, this.leftArm, this.tailBase);
   }
}
