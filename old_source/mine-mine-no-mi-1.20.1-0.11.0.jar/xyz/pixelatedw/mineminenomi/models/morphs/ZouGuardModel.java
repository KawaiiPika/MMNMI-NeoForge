package xyz.pixelatedw.mineminenomi.models.morphs;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.QuadrupedModel;
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
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.api.morph.QuadrupedMorphModel;

public class ZouGuardModel<T extends LivingEntity> extends QuadrupedMorphModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "zou_guard"), "main");
   private final ModelPart tail;
   private final ModelPart tail2;
   private final ModelPart body4;
   private final ModelPart body5;
   private final ModelPart body3;
   private final ModelPart body2;
   private final ModelPart snout;
   private final ModelPart snout2;
   private final ModelPart snout3;
   private final ModelPart rightEar;
   private final ModelPart leftEar;
   private final ModelPart leftTusk;
   private final ModelPart leftTusk2;
   private final ModelPart rightTusk;
   private final ModelPart rightTusk2;

   public ZouGuardModel(ModelPart root) {
      super(root);
      this.tail = this.f_103493_.m_171324_("tail");
      this.tail2 = this.tail.m_171324_("tail2");
      this.body4 = this.f_103493_.m_171324_("body4");
      this.body5 = this.f_103493_.m_171324_("body5");
      this.body3 = this.f_103493_.m_171324_("body3");
      this.body2 = this.f_103493_.m_171324_("body2");
      this.snout = this.f_103492_.m_171324_("snout");
      this.snout2 = this.snout.m_171324_("snout2");
      this.snout3 = this.snout2.m_171324_("snout3");
      this.rightEar = this.f_103492_.m_171324_("rightEar");
      this.leftEar = this.f_103492_.m_171324_("leftEar");
      this.leftTusk = this.f_103492_.m_171324_("leftTusk");
      this.leftTusk2 = this.leftTusk.m_171324_("leftTusk2");
      this.rightTusk = this.f_103492_.m_171324_("rightTusk");
      this.rightTusk2 = this.rightTusk.m_171324_("rightTusk2");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = QuadrupedModel.m_170864_(0, CubeDeformation.f_171458_);
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition body = partdefinition.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(36, 25).m_171488_(-7.5F, -12.0F, -8.0F, 15.0F, 15.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 9.0F, -4.0F));
      PartDefinition tail = body.m_171599_("tail", CubeListBuilder.m_171558_().m_171514_(108, 0).m_171488_(-0.5F, 0.0F, -0.5F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, -7.0F, 15.75F, 0.3491F, 0.0F, 0.0F));
      tail.m_171599_("tail2", CubeListBuilder.m_171558_().m_171514_(113, 0).m_171488_(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 5.5F, 0.0F));
      body.m_171599_("body4", CubeListBuilder.m_171558_().m_171514_(61, 49).m_171488_(-6.5F, -7.0F, -0.5F, 13.0F, 14.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, -4.5F, 16.0F));
      body.m_171599_("body5", CubeListBuilder.m_171558_().m_171514_(61, 49).m_171488_(-6.5F, -7.0F, -0.5F, 13.0F, 14.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, -4.5F, -8.25F));
      body.m_171599_("body3", CubeListBuilder.m_171558_().m_171514_(36, 0).m_171488_(-6.5F, -1.0F, -11.0F, 13.0F, 2.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 2.5F, 4.0F));
      body.m_171599_("body2", CubeListBuilder.m_171558_().m_171514_(36, 0).m_171488_(-6.5F, -13.0F, -7.0F, 13.0F, 2.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      partdefinition.m_171599_("right_hind_leg", CubeListBuilder.m_171558_().m_171514_(0, 46).m_171488_(-2.25F, 0.0F, -2.75F, 5.0F, 13.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(-5.0F, 11.0F, 9.5F));
      partdefinition.m_171599_("left_hind_leg", CubeListBuilder.m_171558_().m_171514_(0, 46).m_171488_(-2.75F, 0.0F, -2.75F, 5.0F, 13.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(5.0F, 11.0F, 9.5F));
      partdefinition.m_171599_("right_front_leg", CubeListBuilder.m_171558_().m_171514_(0, 46).m_171488_(-2.25F, 0.0F, -2.25F, 5.0F, 13.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(-5.0F, 11.0F, -9.5F));
      partdefinition.m_171599_("left_front_leg", CubeListBuilder.m_171558_().m_171514_(0, 46).m_171488_(-2.75F, 0.0F, -2.25F, 5.0F, 13.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(5.0F, 11.0F, -9.5F));
      PartDefinition head = partdefinition.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-4.0F, -6.0F, -7.0F, 8.0F, 11.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, -0.875F, -12.4375F));
      PartDefinition snout = head.m_171599_("snout", CubeListBuilder.m_171558_().m_171514_(108, 8).m_171488_(-2.0F, -3.671F, -2.9673F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.5F)), PartPose.m_171423_(0.0F, 4.0F, -5.5F, -0.3054F, 0.0F, 0.0F));
      PartDefinition snout2 = snout.m_171599_("snout2", CubeListBuilder.m_171558_().m_171514_(108, 8).m_171488_(-2.0F, -0.875F, -1.625F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.2F)), PartPose.m_171423_(0.0F, 3.602F, -1.3293F, 0.2618F, 0.0F, 0.0F));
      snout2.m_171599_("snout3", CubeListBuilder.m_171558_().m_171514_(108, 20).m_171488_(-2.0F, -0.9353F, -1.554F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 6.4876F, -0.0527F, 0.1745F, 0.0F, 0.0F));
      head.m_171599_("rightEar", CubeListBuilder.m_171558_().m_171514_(0, 22).m_171488_(-8.0F, -4.5F, -0.5F, 8.0F, 9.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-2.5F, -2.5F, -2.75F, -0.1368F, 0.4707F, -0.2946F));
      head.m_171599_("leftEar", CubeListBuilder.m_171558_().m_171514_(0, 22).m_171480_().m_171488_(0.0F, -4.5F, -0.5F, 8.0F, 9.0F, 0.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(2.5F, -2.5F, -2.75F, -0.1368F, -0.4707F, 0.2946F));
      PartDefinition leftTusk = head.m_171599_("leftTusk", CubeListBuilder.m_171558_().m_171514_(0, 33).m_171488_(-0.5F, 0.0F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-2.3F, 3.0F, -6.0F, -0.3491F, 0.2094F, 0.0F));
      leftTusk.m_171599_("leftTusk2", CubeListBuilder.m_171558_().m_171514_(0, 33).m_171488_(-0.461F, -0.1505F, -0.5192F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-0.039F, 4.0647F, 4.0E-4F, -0.1745F, 0.0F, 0.0F));
      PartDefinition rightTusk = head.m_171599_("rightTusk", CubeListBuilder.m_171558_().m_171514_(0, 33).m_171480_().m_171488_(-0.5F, 0.0F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(2.3F, 3.0F, -6.0F, -0.3491F, -0.2094F, 0.0F));
      rightTusk.m_171599_("rightTusk2", CubeListBuilder.m_171558_().m_171514_(0, 33).m_171480_().m_171488_(-0.539F, -0.1505F, -0.5192F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(0.039F, 4.0647F, 4.0E-4F, -0.1745F, 0.0F, 0.0F));
      return LayerDefinition.m_171565_(meshdefinition, 128, 64);
   }

   public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      RendererHelper.resetModelToDefaultPivots(this);
      boolean flag1 = entity.m_6067_();
      this.f_103492_.f_104204_ = netHeadYaw * ((float)Math.PI / 180F);
      if (this.swimAmount > 0.0F) {
         if (flag1) {
            this.f_103492_.f_104203_ = this.rotlerpRad(this.f_103492_.f_104203_, (-(float)Math.PI / 4F), this.swimAmount);
         } else {
            this.f_103492_.f_104203_ = this.rotlerpRad(this.f_103492_.f_104203_, headPitch * ((float)Math.PI / 180F), this.swimAmount);
         }
      } else {
         this.f_103492_.f_104203_ = headPitch * ((float)Math.PI / 180F);
         if ((double)this.f_103492_.f_104203_ > 0.6) {
            this.f_103492_.f_104203_ = 0.6F;
         }
      }

      this.f_170854_.f_104203_ = Mth.m_14089_(limbSwing * 0.6662F) * 0.3F * limbSwingAmount;
      this.f_170855_.f_104203_ = Mth.m_14089_(limbSwing * 0.6662F + (float)Math.PI) * 0.3F * limbSwingAmount;
      this.f_170852_.f_104203_ = Mth.m_14089_(limbSwing * 0.6662F) * 0.4F * limbSwingAmount;
      this.f_170853_.f_104203_ = Mth.m_14089_(limbSwing * 0.6662F + (float)Math.PI) * 0.4F * limbSwingAmount;
      if (entity.m_20142_()) {
         this.tail.f_104203_ = 0.6F + Mth.m_14089_(limbSwing * 0.6662F) * 0.2F * limbSwingAmount;
         this.leftEar.f_104204_ = -0.3F - Mth.m_14089_(limbSwing * 0.6662F) * 0.2F * limbSwingAmount;
         this.rightEar.f_104204_ = 0.3F + Mth.m_14089_(limbSwing * 0.6662F) * 0.2F * limbSwingAmount;
      }

      this.setupAttackAnimation(entity, ageInTicks);
      boolean isFlying = entity.m_21255_();
      if (entity instanceof Player player) {
         isFlying |= player.m_150110_().f_35935_;
      }

      if (isFlying) {
         this.rightEar.f_104203_ = 1.2F;
         this.rightEar.f_104204_ = 0.1F;
         this.rightEar.f_104205_ = Mth.m_14089_(ageInTicks) * 0.6F;
         this.leftEar.f_104203_ = 1.2F;
         this.leftEar.f_104204_ = -0.1F;
         this.leftEar.f_104205_ = -Mth.m_14089_(ageInTicks) * 0.6F;
      }

   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      super.m_7695_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }

   public void m_6002_(HumanoidArm arm, PoseStack poseStack) {
      this.f_103492_.m_104299_(poseStack);
      poseStack.m_252781_(Axis.f_252403_.m_252977_(90.0F));
      poseStack.m_252781_(Axis.f_252529_.m_252977_(260.0F));
      poseStack.m_85837_((double)1.0F, -0.2, (double)0.0F);
   }

   public ModelPart m_5585_() {
      return this.f_103492_;
   }
}
