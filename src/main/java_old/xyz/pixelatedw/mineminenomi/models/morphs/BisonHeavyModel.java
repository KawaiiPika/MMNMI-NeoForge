package xyz.pixelatedw.mineminenomi.models.morphs;

import com.google.common.collect.ImmutableList;
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
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.api.morph.HumanoidMorphModel;

public class BisonHeavyModel<T extends LivingEntity> extends HumanoidMorphModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "bison_heavy"), "main");
   private final ModelPart lowerRightArm;
   private final ModelPart lowerLeftArm;
   private final ModelPart snout;
   private final ModelPart leftHorn;
   private final ModelPart leftHornTip;
   private final ModelPart rightHorn;
   private final ModelPart rightHornTip;
   private final ModelPart torso;
   private final ModelPart hump;
   private final ModelPart lowerBody;
   private final ModelPart lowerRightLeg;
   private final ModelPart lowerRightLeg2;
   private final ModelPart rightFoot;
   private final ModelPart lowerLeftLeg;
   private final ModelPart lowerLeftLeg2;
   private final ModelPart leftFoot;

   public BisonHeavyModel(ModelPart root) {
      super(root);
      this.lowerRightArm = this.f_102811_.m_171324_("lowerRightArm");
      this.lowerLeftArm = this.f_102812_.m_171324_("lowerLeftArm");
      this.snout = this.f_102808_.m_171324_("snout");
      this.leftHorn = this.f_102808_.m_171324_("leftHorn");
      this.leftHornTip = this.leftHorn.m_171324_("leftHornTip");
      this.rightHorn = this.f_102808_.m_171324_("rightHorn");
      this.rightHornTip = this.rightHorn.m_171324_("rightHornTip");
      this.torso = this.f_102810_.m_171324_("torso");
      this.hump = this.f_102810_.m_171324_("hump");
      this.lowerBody = this.f_102810_.m_171324_("lowerBody");
      this.lowerRightLeg = this.f_102813_.m_171324_("lowerRightLeg");
      this.lowerRightLeg2 = this.lowerRightLeg.m_171324_("lowerRightLeg2");
      this.rightFoot = this.lowerRightLeg2.m_171324_("rightFoot");
      this.lowerLeftLeg = this.f_102814_.m_171324_("lowerLeftLeg");
      this.lowerLeftLeg2 = this.lowerLeftLeg.m_171324_("lowerLeftLeg2");
      this.leftFoot = this.lowerLeftLeg2.m_171324_("leftFoot");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = HumanoidModel.m_170681_(new CubeDeformation(0.0F), 0.0F);
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition rightArm = partdefinition.m_171599_("right_arm", CubeListBuilder.m_171558_().m_171514_(14, 32).m_171488_(-3.8091F, -0.4864F, -2.336F, 4.0F, 8.0F, 4.0F, new CubeDeformation(-0.01F)), PartPose.m_171423_(-4.75F, 1.0F, 1.0F, 0.2182F, 0.0F, 0.192F));
      rightArm.m_171599_("lowerRightArm", CubeListBuilder.m_171558_().m_171514_(16, 45).m_171488_(-1.6014F, -0.3577F, -1.5625F, 3.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-1.7862F, 6.913F, -0.336F, -0.2618F, 0.0F, -0.192F));
      PartDefinition leftArm = partdefinition.m_171599_("left_arm", CubeListBuilder.m_171558_().m_171514_(14, 32).m_171480_().m_171488_(-0.1909F, -0.4864F, -2.336F, 4.0F, 8.0F, 4.0F, new CubeDeformation(-0.01F)).m_171555_(false), PartPose.m_171423_(4.75F, 1.0F, 1.0F, 0.2182F, 0.0F, -0.192F));
      leftArm.m_171599_("lowerLeftArm", CubeListBuilder.m_171558_().m_171514_(16, 45).m_171480_().m_171488_(-1.3986F, -0.3577F, -1.5625F, 3.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(1.7862F, 6.913F, -0.336F, -0.2618F, 0.0F, 0.192F));
      PartDefinition head = partdefinition.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(32, 31).m_171488_(-2.5F, -3.5236F, -4.3875F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 0.0F, 0.0F, 0.1745F, 0.0F, 0.0F));
      head.m_171599_("snout", CubeListBuilder.m_171558_().m_171514_(37, 4).m_171488_(-1.5F, -1.5F, -2.0F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, -0.2736F, -3.075F, 0.0873F, 0.0F, 0.0F));
      PartDefinition leftHorn = head.m_171599_("leftHorn", CubeListBuilder.m_171558_(), PartPose.m_171423_(2.2197F, -3.1571F, -1.2264F, -0.1309F, 0.0F, -0.6981F));
      leftHorn.m_171599_("lefthorn1_r1", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171480_().m_171488_(-0.5F, -0.5F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -1.5708F));
      leftHorn.m_171599_("leftHornTip", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171480_().m_171488_(-0.49F, -2.115F, -0.51F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171419_(0.99F, -0.385F, 0.01F));
      PartDefinition rightHorn = head.m_171599_("rightHorn", CubeListBuilder.m_171558_(), PartPose.m_171423_(-2.2197F, -3.1571F, -1.2264F, -0.1309F, 0.0F, 0.6981F));
      rightHorn.m_171599_("righthorn_r1", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-0.5F, -0.5F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.5708F));
      rightHorn.m_171599_("rightHornTip", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-0.51F, -2.115F, -0.51F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(-0.99F, -0.385F, 0.01F));
      PartDefinition body = partdefinition.m_171599_("body", CubeListBuilder.m_171558_(), PartPose.m_171423_(0.0F, 10.0F, 2.0F, 0.0873F, 0.0F, 0.0F));
      body.m_171599_("torso", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-5.5F, -10.9425F, -3.2198F, 11.0F, 11.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      body.m_171599_("hump", CubeListBuilder.m_171558_().m_171514_(31, 13).m_171488_(-4.5F, -5.1132F, -3.5123F, 9.0F, 10.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, -7.0F, 2.5F, 0.4363F, 0.0F, 0.0F));
      body.m_171599_("lowerBody", CubeListBuilder.m_171558_().m_171514_(0, 23).m_171488_(-4.0F, -0.4634F, -2.6114F, 8.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 0.0F, 0.0F, -0.1745F, 0.0F, 0.0F));
      PartDefinition rightLeg = partdefinition.m_171599_("right_leg", CubeListBuilder.m_171558_().m_171514_(0, 33).m_171488_(-1.5F, -0.7362F, -1.7242F, 3.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-3.0F, 12.0F, 2.0F, -0.3491F, 0.0F, 0.0F));
      PartDefinition lowerRightLeg = rightLeg.m_171599_("lowerRightLeg", CubeListBuilder.m_171558_().m_171514_(2, 43).m_171488_(-1.0F, -1.2214F, -0.3413F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.2F)), PartPose.m_171423_(0.0F, 5.9517F, 0.4088F, 1.4399F, 0.0F, 0.0F));
      PartDefinition lowerRightLeg2 = lowerRightLeg.m_171599_("lowerRightLeg2", CubeListBuilder.m_171558_().m_171514_(2, 49).m_171488_(-1.0F, -0.2661F, -1.3023F, 2.0F, 5.0F, 2.0F, new CubeDeformation(-0.01F)), PartPose.m_171423_(0.0F, 1.9941F, 0.3566F, -1.5184F, 0.0F, 0.0F));
      lowerRightLeg2.m_171599_("rightFoot", CubeListBuilder.m_171558_().m_171514_(1, 57).m_171488_(-1.0F, -0.3186F, -1.9769F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.1F)), PartPose.m_171423_(0.0F, 4.3025F, -0.1388F, 0.4276F, 0.0F, 0.0F));
      PartDefinition leftLeg = partdefinition.m_171599_("left_leg", CubeListBuilder.m_171558_().m_171514_(0, 33).m_171480_().m_171488_(-1.5F, -0.7362F, -1.7242F, 3.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(3.0F, 12.0F, 2.0F, -0.3491F, 0.0F, 0.0F));
      PartDefinition lowerLeftLeg = leftLeg.m_171599_("lowerLeftLeg", CubeListBuilder.m_171558_().m_171514_(2, 43).m_171480_().m_171488_(-1.0F, -1.2214F, -0.3413F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.2F)).m_171555_(false), PartPose.m_171423_(0.0F, 5.9517F, 0.4088F, 1.4399F, 0.0F, 0.0F));
      PartDefinition lowerLeftLeg2 = lowerLeftLeg.m_171599_("lowerLeftLeg2", CubeListBuilder.m_171558_().m_171514_(2, 49).m_171480_().m_171488_(-1.0F, -0.2661F, -1.3023F, 2.0F, 5.0F, 2.0F, new CubeDeformation(-0.01F)).m_171555_(false), PartPose.m_171423_(0.0F, 1.9941F, 0.3566F, -1.5184F, 0.0F, 0.0F));
      lowerLeftLeg2.m_171599_("leftFoot", CubeListBuilder.m_171558_().m_171514_(1, 57).m_171480_().m_171488_(-1.0F, -0.3186F, -1.9769F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.1F)).m_171555_(false), PartPose.m_171423_(0.0F, 4.3025F, -0.1388F, 0.4276F, 0.0F, 0.0F));
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   public void m_6973_(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      RendererHelper.resetModelToDefaultPivots(this);
      this.setupHeadRotation(entity, headPitch, netHeadYaw);
      float f = 1.0F;
      this.f_102811_.f_104203_ = Mth.m_14089_(limbSwing * 0.6662F + (float)Math.PI) * 0.8F * limbSwingAmount * 0.5F / f;
      this.f_102812_.f_104203_ = Mth.m_14089_(limbSwing * 0.6662F) * 0.8F * limbSwingAmount * 0.5F / f;
      this.f_102813_.f_104203_ = -0.34F + Mth.m_14089_(limbSwing * 0.6662F) * 0.7F * limbSwingAmount / f;
      this.f_102814_.f_104203_ = -0.34F + Mth.m_14089_(limbSwing * 0.6662F + (float)Math.PI) * 0.7F * limbSwingAmount / f;
      this.m_7884_(entity, ageInTicks);
      if (entity.m_6047_()) {
         this.f_102808_.f_104202_ = -7.0F;
         this.f_102808_.f_104201_ = 4.0F;
         this.f_102810_.f_104203_ = 0.5F;
         this.f_102810_.f_104201_ = 11.0F;
         this.f_102810_.f_104202_ = 1.0F;
         ModelPart var10000 = this.f_102811_;
         var10000.f_104203_ += 0.4F;
         this.f_102811_.f_104202_ = -4.0F;
         this.f_102811_.f_104201_ = 3.0F;
         var10000 = this.f_102812_;
         var10000.f_104203_ += 0.4F;
         this.f_102812_.f_104202_ = -4.0F;
         this.f_102812_.f_104201_ = 3.0F;
         this.f_102813_.f_104201_ = 13.0F;
         this.f_102814_.f_104201_ = 13.0F;
      }

   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      super.m_7695_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }

   protected Iterable<ModelPart> m_5607_() {
      return ImmutableList.of(this.f_102808_);
   }

   protected Iterable<ModelPart> m_5608_() {
      return ImmutableList.of(this.f_102810_, this.f_102811_, this.f_102812_, this.f_102814_, this.f_102813_);
   }

   public void m_6002_(HumanoidArm side, PoseStack stack) {
      super.m_6002_(side, stack);
      float sideMod = side == HumanoidArm.RIGHT ? 1.0F : -1.0F;
      stack.m_85837_(-0.05 * (double)sideMod, 0.35, -0.1);
      stack.m_252781_(Axis.f_252403_.m_252977_(side == HumanoidArm.RIGHT ? -10.0F : 10.0F));
   }

   public void renderFirstPersonArm(PoseStack stack, VertexConsumer vertex, int packedLight, int overlay, float red, float green, float blue, float alpha, HumanoidArm side, boolean isLeg) {
      float sideMod = side == HumanoidArm.RIGHT ? 1.0F : -1.0F;
      stack.m_85837_(-0.2 * (double)sideMod, -0.4, -0.4);
      stack.m_252781_(Axis.f_252529_.m_252977_(20.0F));
      if (isLeg) {
         stack.m_85837_(0.2 * (double)sideMod, 0.1, 0.2);
      }

      super.renderFirstPersonArm(stack, vertex, packedLight, overlay, red, green, blue, alpha, side, isLeg);
   }
}
