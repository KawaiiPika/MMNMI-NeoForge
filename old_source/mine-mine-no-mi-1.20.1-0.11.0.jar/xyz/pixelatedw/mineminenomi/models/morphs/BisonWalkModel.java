package xyz.pixelatedw.mineminenomi.models.morphs;

import com.google.common.collect.ImmutableList;
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

public class BisonWalkModel<T extends LivingEntity> extends QuadrupedMorphModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "bison_walk"), "main");
   private final ModelPart snout;
   private final ModelPart leftHorn;
   private final ModelPart leftHorn2;
   private final ModelPart rightHorn;
   private final ModelPart rightHorn2;
   private final ModelPart leftFrontLegLower;
   private final ModelPart rightFrontLegLower;
   private final ModelPart hump;
   private final ModelPart backBody;
   private final ModelPart tail;

   public BisonWalkModel(ModelPart root) {
      super(root);
      this.snout = this.f_103492_.m_171324_("snout");
      this.leftHorn = this.f_103492_.m_171324_("leftHorn");
      this.leftHorn2 = this.leftHorn.m_171324_("leftHorn2");
      this.rightHorn = this.f_103492_.m_171324_("rightHorn");
      this.rightHorn2 = this.rightHorn.m_171324_("rightHorn2");
      this.leftFrontLegLower = this.f_170855_.m_171324_("leftFrontLegLower");
      this.rightFrontLegLower = this.f_170854_.m_171324_("rightFrontLegLower");
      this.hump = this.f_103493_.m_171324_("hump");
      this.backBody = this.f_103493_.m_171324_("backBody");
      this.tail = this.backBody.m_171324_("tail");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = QuadrupedModel.m_170864_(0, new CubeDeformation(0.0F));
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition head = partdefinition.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(13, 29).m_171488_(-2.5F, -2.5F, -5.0F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 12.0486F, -8.5856F, 0.2618F, 0.0F, 0.0F));
      PartDefinition snout = head.m_171599_("snout", CubeListBuilder.m_171558_(), PartPose.m_171423_(0.0F, -0.0862F, -4.3249F, 0.9163F, 0.0F, 0.0F));
      snout.m_171599_("snout1_r1", CubeListBuilder.m_171558_().m_171514_(11, 40).m_171488_(-2.0F, -2.0F, -2.55F, 4.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 0.0F, -1.0F, -0.1309F, 0.0F, 0.0F));
      PartDefinition leftHorn = head.m_171599_("leftHorn", CubeListBuilder.m_171558_(), PartPose.m_171423_(2.2197F, -2.1335F, -1.6514F, -0.0457F, -0.3051F, -0.6844F));
      leftHorn.m_171599_("lefthorn1_r1", CubeListBuilder.m_171558_().m_171514_(34, 5).m_171488_(-0.5F, -1.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.5F, 0.0F, 0.0F, 0.0F, 0.0F, -1.5708F));
      leftHorn.m_171599_("leftHorn2", CubeListBuilder.m_171558_().m_171514_(34, 5).m_171488_(-0.49F, -2.115F, -0.51F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.99F, -0.385F, 0.01F));
      PartDefinition rightHorn = head.m_171599_("rightHorn", CubeListBuilder.m_171558_(), PartPose.m_171423_(-2.2197F, -2.1335F, -1.6514F, -0.0457F, 0.3051F, 0.6844F));
      rightHorn.m_171599_("righthorn_r1", CubeListBuilder.m_171558_().m_171514_(34, 5).m_171480_().m_171488_(-0.5F, -1.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(-0.5F, 0.0F, 0.0F, 0.0F, 0.0F, 1.5708F));
      rightHorn.m_171599_("rightHorn2", CubeListBuilder.m_171558_().m_171514_(34, 5).m_171480_().m_171488_(-0.51F, -2.115F, -0.51F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171419_(-0.99F, -0.385F, 0.01F));
      PartDefinition leftFrontLeg = partdefinition.m_171599_("left_front_leg", CubeListBuilder.m_171558_().m_171514_(0, 29).m_171488_(-1.5F, -3.0F, -1.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(2.75F, 17.0F, -6.0625F));
      leftFrontLeg.m_171599_("leftFrontLegLower", CubeListBuilder.m_171558_().m_171514_(0, 21).m_171488_(-1.0F, -1.0F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 3.0F, 0.0F));
      PartDefinition rightFrontLeg = partdefinition.m_171599_("right_front_leg", CubeListBuilder.m_171558_().m_171514_(0, 29).m_171488_(-1.5F, -3.0F, -1.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(-2.75F, 17.0F, -6.0625F));
      rightFrontLeg.m_171599_("rightFrontLegLower", CubeListBuilder.m_171558_().m_171514_(0, 21).m_171488_(-1.0F, -1.0F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 3.0F, 0.0F));
      partdefinition.m_171599_("left_hind_leg", CubeListBuilder.m_171558_().m_171514_(0, 39).m_171488_(-1.0F, -3.1875F, -1.0F, 2.0F, 10.0F, 2.0F, new CubeDeformation(0.2F)), PartPose.m_171419_(2.75F, 16.6875F, 5.9375F));
      partdefinition.m_171599_("right_hind_leg", CubeListBuilder.m_171558_().m_171514_(0, 39).m_171488_(-1.0F, -3.1875F, -1.0F, 2.0F, 10.0F, 2.0F, new CubeDeformation(0.2F)), PartPose.m_171419_(-2.75F, 16.6875F, 5.9375F));
      PartDefinition body = partdefinition.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(0.5F, 0.0625F, -7.0F, 8.0F, 8.0F, 11.0F, new CubeDeformation(0.3F)), PartPose.m_171419_(-4.5F, 9.0F, -2.0625F));
      body.m_171599_("hump", CubeListBuilder.m_171558_().m_171514_(32, 17).m_171488_(-3.5F, -2.5F, -4.5F, 7.0F, 5.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(4.5F, 1.0181F, -2.1693F, -0.3927F, 0.0F, 0.0F));
      PartDefinition backBody = body.m_171599_("backBody", CubeListBuilder.m_171558_().m_171514_(32, 33).m_171488_(-4.0F, -3.7298F, -1.6068F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(4.5F, 3.9173F, 4.1693F, -0.0873F, 0.0F, 0.0F));
      backBody.m_171599_("tail", CubeListBuilder.m_171558_().m_171514_(29, 2).m_171488_(-0.5F, -0.1272F, -0.4762F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, -2.3923F, 6.0182F, 0.3491F, 0.0F, 0.0F));
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      RendererHelper.resetModelToDefaultPivots(this);
      this.setupHeadRotation(entity, headPitch, netHeadYaw);
      this.f_170854_.f_104203_ = Mth.m_14089_(limbSwing * 0.9F) * 0.5F * limbSwingAmount;
      this.f_170855_.f_104203_ = Mth.m_14089_(limbSwing * 0.9F) * 0.5F * limbSwingAmount;
      this.f_170852_.f_104203_ = Mth.m_14089_(limbSwing * 0.9F + (float)Math.PI) * 0.5F * limbSwingAmount;
      this.f_170853_.f_104203_ = Mth.m_14089_(limbSwing * 0.9F + (float)Math.PI) * 0.5F * limbSwingAmount;
      if (entity.m_20142_()) {
         this.tail.f_104203_ = 1.2F + Mth.m_14089_(limbSwing * 0.6662F) * 0.2F * limbSwingAmount;
      }

      this.setupAttackAnimation(entity, ageInTicks);
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      super.m_7695_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }

   public ModelPart m_5585_() {
      return this.f_103492_;
   }

   public void m_6002_(HumanoidArm side, PoseStack stack) {
      this.f_103492_.m_104299_(stack);
      stack.m_252781_(Axis.f_252403_.m_252977_(90.0F));
      stack.m_252781_(Axis.f_252529_.m_252977_(260.0F));
      stack.m_85837_((double)0.25F, -0.2, (double)0.0F);
   }

   protected Iterable<ModelPart> m_5607_() {
      return ImmutableList.of(this.f_103492_);
   }

   protected Iterable<ModelPart> m_5608_() {
      return ImmutableList.of(this.f_103493_, this.f_170854_, this.f_170855_, this.f_170852_, this.f_170853_);
   }
}
