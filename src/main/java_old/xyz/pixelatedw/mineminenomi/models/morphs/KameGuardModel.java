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
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.api.morph.QuadrupedMorphModel;

public class KameGuardModel<T extends LivingEntity> extends QuadrupedMorphModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "kame_guard"), "main");

   public KameGuardModel(ModelPart root) {
      super(root);
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = QuadrupedModel.m_170864_(0, CubeDeformation.f_171458_);
      PartDefinition partdefinition = meshdefinition.m_171576_();
      partdefinition.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-4.0F, -2.0F, -5.0F, 10.0F, 3.0F, 12.0F, new CubeDeformation(0.0F)).m_171514_(0, 29).m_171488_(-3.0F, -3.0F, -4.0F, 8.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)).m_171514_(0, 16).m_171488_(-3.0F, 1.0F, -4.5F, 8.0F, 1.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(-1.0F, 22.0F, -1.0F));
      partdefinition.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(33, 0).m_171488_(-2.0F, -1.0F, -4.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 22.0F, -5.0F));
      partdefinition.m_171599_("left_hind_leg", CubeListBuilder.m_171558_().m_171514_(28, 16).m_171488_(-2.0F, 0.0F, 0.0F, 3.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(3.0F, 23.0F, 5.25F));
      partdefinition.m_171599_("right_hind_leg", CubeListBuilder.m_171558_().m_171514_(27, 29).m_171488_(-3.0F, 1.0F, 6.0F, 3.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(-1.0F, 22.0F, -0.75F));
      partdefinition.m_171599_("right_front_leg", CubeListBuilder.m_171558_().m_171514_(28, 23).m_171488_(-9.0F, 1.0F, -4.0F, 6.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(-1.0F, 22.0F, -1.0F));
      partdefinition.m_171599_("left_front_leg", CubeListBuilder.m_171558_().m_171514_(33, 8).m_171488_(5.0F, 0.85F, -4.0F, 6.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(-1.0F, 22.0F, -1.0F));
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   public void setupAnim(LivingEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      RendererHelper.resetModelToDefaultPivots(this);
      if (entity.m_6047_()) {
         this.f_103492_.f_104207_ = false;
         this.f_170854_.f_104207_ = false;
         this.f_170855_.f_104207_ = false;
         this.f_170852_.f_104207_ = false;
         this.f_170853_.f_104207_ = false;
      } else {
         this.f_103492_.f_104207_ = true;
         this.f_170854_.f_104207_ = true;
         this.f_170855_.f_104207_ = true;
         this.f_170852_.f_104207_ = true;
         this.f_170853_.f_104207_ = true;
      }

      this.f_103492_.f_104203_ = headPitch * ((float)Math.PI / 180F);
      this.f_103492_.f_104204_ = netHeadYaw * ((float)Math.PI / 180F);
      this.f_170854_.f_104203_ = Mth.m_14089_(limbSwing * 0.4F) * 0.1F;
      this.f_170854_.f_104205_ = Mth.m_14031_(limbSwing * 0.4F) * 0.1F;
      this.f_170855_.f_104203_ = -Mth.m_14089_(limbSwing * 0.4F) * 0.1F;
      this.f_170855_.f_104205_ = -Mth.m_14031_(limbSwing * 0.4F) * 0.1F;
      this.f_170852_.f_104204_ = Mth.m_14089_(limbSwing * 0.4F) * 0.1F;
      this.f_170852_.f_104205_ = Mth.m_14031_(limbSwing * 0.4F) * 0.1F;
      this.f_170853_.f_104204_ = Mth.m_14089_(limbSwing * 0.4F) * 0.2F;
      this.f_170853_.f_104205_ = -Mth.m_14031_(limbSwing * 0.4F) * 0.1F;
      this.f_102608_ = (float)entity.f_20913_;
      if (this.f_102608_ > 0.0F) {
         this.f_103492_.f_104204_ = Mth.m_14031_((float)((double)Mth.m_14116_(this.f_102608_) * Math.PI)) * -0.2F;
         this.f_103492_.f_104205_ = Mth.m_14089_((float)((double)Mth.m_14116_(this.f_102608_) * Math.PI)) * -0.2F;
      }

   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      super.m_7695_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }

   public void m_6002_(HumanoidArm side, PoseStack poseStack) {
      if (!this.f_103492_.f_104207_) {
         poseStack.m_85841_(-10.0F, -10.0F, -10.0F);
      }

      this.f_103492_.m_104299_(poseStack);
      poseStack.m_252781_(Axis.f_252403_.m_252977_(90.0F));
      poseStack.m_252781_(Axis.f_252529_.m_252977_(260.0F));
      poseStack.m_85841_(0.5F, 0.5F, 0.5F);
      poseStack.m_85837_(0.2, (double)0.0F, 0.4);
   }

   public ModelPart m_5585_() {
      return this.f_103492_;
   }
}
