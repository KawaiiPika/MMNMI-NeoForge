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

public class ZouHeavyModel<T extends LivingEntity> extends HumanoidMorphModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "zou_heavy"), "main");
   private final ModelPart snout;
   private final ModelPart snout2;
   private final ModelPart snout3;
   private final ModelPart rightEar;
   private final ModelPart leftEar;
   private final ModelPart leftTusk;
   private final ModelPart leftTusk2;
   private final ModelPart rightTusk;
   private final ModelPart rightTusk2;
   private final ModelPart lowerLeftArm;
   private final ModelPart lowerRightArm;
   private final ModelPart body2;
   private final ModelPart neck;
   private final ModelPart lowerleftleg;
   private final ModelPart lowerrightleg;

   public ZouHeavyModel(ModelPart root) {
      super(root);
      this.snout = this.f_102808_.m_171324_("snout");
      this.snout2 = this.snout.m_171324_("snout2");
      this.snout3 = this.snout2.m_171324_("snout3");
      this.rightEar = this.f_102808_.m_171324_("rightEar");
      this.leftEar = this.f_102808_.m_171324_("leftEar");
      this.leftTusk = this.f_102808_.m_171324_("leftTusk");
      this.leftTusk2 = this.leftTusk.m_171324_("leftTusk2");
      this.rightTusk = this.f_102808_.m_171324_("rightTusk");
      this.rightTusk2 = this.rightTusk.m_171324_("rightTusk2");
      this.lowerLeftArm = this.f_102812_.m_171324_("lowerLeftArm");
      this.lowerRightArm = this.f_102811_.m_171324_("lowerRightArm");
      this.body2 = this.f_102810_.m_171324_("body2");
      this.neck = this.body2.m_171324_("neck");
      this.lowerleftleg = this.f_102814_.m_171324_("lowerleftleg");
      this.lowerrightleg = this.f_102813_.m_171324_("lowerrightleg");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = HumanoidModel.m_170681_(CubeDeformation.f_171458_, 0.0F);
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition head = partdefinition.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(94, 0).m_171488_(-4.0F, -6.0F, -7.0F, 8.0F, 11.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, -9.875F, -1.4375F));
      PartDefinition snout = head.m_171599_("snout", CubeListBuilder.m_171558_().m_171514_(94, 21).m_171488_(-2.0F, -3.671F, -2.9673F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.5F)), PartPose.m_171423_(0.0F, 4.0F, -5.5F, -0.3054F, 0.0F, 0.0F));
      PartDefinition snout2 = snout.m_171599_("snout2", CubeListBuilder.m_171558_().m_171514_(94, 21).m_171488_(-2.0F, -0.875F, -1.625F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.2F)), PartPose.m_171423_(0.0F, 3.602F, -1.3293F, 0.2618F, 0.0F, 0.0F));
      snout2.m_171599_("snout3", CubeListBuilder.m_171558_().m_171514_(94, 33).m_171488_(-2.0F, -0.9353F, -1.554F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 6.4876F, -0.0527F, 0.1745F, 0.0F, 0.0F));
      head.m_171599_("rightEar", CubeListBuilder.m_171558_().m_171514_(52, 0).m_171488_(-8.0F, -4.5F, -0.5F, 8.0F, 9.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-2.5F, -2.5F, -0.5F, -0.1368F, 0.4707F, -0.2946F));
      head.m_171599_("leftEar", CubeListBuilder.m_171558_().m_171514_(52, 0).m_171480_().m_171488_(0.0F, -4.5F, -0.5F, 8.0F, 9.0F, 0.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(2.5F, -2.5F, -0.5F, -0.1368F, -0.4707F, 0.2946F));
      PartDefinition leftTusk = head.m_171599_("leftTusk", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-0.5F, 0.0F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-2.3F, 3.0F, -6.0F, -0.3491F, 0.2094F, 0.0F));
      leftTusk.m_171599_("leftTusk2", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-0.461F, -0.1505F, -0.5192F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-0.039F, 4.0647F, 4.0E-4F, -0.1745F, 0.0F, 0.0F));
      PartDefinition rightTusk = head.m_171599_("rightTusk", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171480_().m_171488_(-0.5F, 0.0F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(2.3F, 3.0F, -6.0F, -0.3491F, -0.2094F, 0.0F));
      rightTusk.m_171599_("rightTusk2", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171480_().m_171488_(-0.539F, -0.1505F, -0.5192F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(0.039F, 4.0647F, 4.0E-4F, -0.1745F, 0.0F, 0.0F));
      PartDefinition leftArm = partdefinition.m_171599_("left_arm", CubeListBuilder.m_171558_().m_171514_(66, 2).m_171488_(-1.1044F, -1.9756F, -3.4356F, 6.0F, 10.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(9.6032F, -4.0641F, 1.0F, 0.0F, 0.0F, -0.1745F));
      leftArm.m_171599_("lowerLeftArm", CubeListBuilder.m_171558_().m_171514_(66, 19).m_171488_(-2.3255F, -0.0899F, -3.0076F, 5.0F, 10.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(1.7196F, 6.8649F, 0.0625F, -0.1745F, 0.0F, 0.0873F));
      PartDefinition rightArm = partdefinition.m_171599_("right_arm", CubeListBuilder.m_171558_().m_171514_(66, 2).m_171480_().m_171488_(-4.8956F, -1.9756F, -3.4356F, 6.0F, 10.0F, 7.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(-9.6032F, -4.0641F, 1.0F, 0.0F, 0.0F, 0.1745F));
      rightArm.m_171599_("lowerRightArm", CubeListBuilder.m_171558_().m_171514_(66, 19).m_171480_().m_171488_(-2.6745F, -0.0899F, -3.0076F, 5.0F, 10.0F, 6.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(-1.7196F, 6.8649F, 0.0625F, -0.1745F, 0.0F, -0.0873F));
      PartDefinition body = partdefinition.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(0, 31).m_171488_(-8.5F, -12.3571F, -8.2065F, 17.0F, 14.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 11.1931F, 0.9075F, -0.0436F, 0.0F, 0.0F));
      PartDefinition body2 = body.m_171599_("body2", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-9.5F, -8.8743F, -6.9373F, 19.0F, 17.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, -8.8009F, 0.5263F, 0.1309F, 0.0F, 0.0F));
      body2.m_171599_("neck", CubeListBuilder.m_171558_().m_171514_(84, 50).m_171488_(-6.0F, -4.0902F, -4.2988F, 12.0F, 4.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, -7.3672F, -1.0712F, -0.0349F, 0.0F, 0.0F));
      PartDefinition leftLeg = partdefinition.m_171599_("left_leg", CubeListBuilder.m_171558_().m_171514_(65, 36).m_171488_(-3.0F, 0.0262F, -2.9993F, 6.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(5.0F, 11.1F, 1.0F, -0.1396F, 0.0F, 0.0F));
      leftLeg.m_171599_("lowerleftleg", CubeListBuilder.m_171558_().m_171514_(64, 50).m_171488_(-2.5F, -1.0041F, -2.6571F, 5.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 6.7959F, 0.1571F, 0.1396F, 0.0F, 0.0F));
      PartDefinition rightLeg = partdefinition.m_171599_("right_leg", CubeListBuilder.m_171558_().m_171514_(65, 36).m_171480_().m_171488_(-3.0F, 0.0262F, -2.9993F, 6.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(-5.0F, 11.1F, 1.0F, -0.1396F, 0.0F, 0.0F));
      rightLeg.m_171599_("lowerrightleg", CubeListBuilder.m_171558_().m_171514_(64, 50).m_171480_().m_171488_(-2.5F, -1.0041F, -2.6571F, 5.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(0.0F, 6.7959F, 0.1571F, 0.1396F, 0.0F, 0.0F));
      return LayerDefinition.m_171565_(meshdefinition, 128, 64);
   }

   public void m_6973_(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      RendererHelper.resetModelToDefaultPivots(this);
      this.setupHeadRotation(entity, headPitch, netHeadYaw);
      float f = 1.0F;
      this.f_102811_.f_104203_ = Mth.m_14089_(limbSwing * 0.6662F + (float)Math.PI) * 0.8F * limbSwingAmount * 0.5F / f;
      this.f_102812_.f_104203_ = Mth.m_14089_(limbSwing * 0.6662F) * 0.8F * limbSwingAmount * 0.5F / f;
      this.f_102813_.f_104203_ = -0.15F + Mth.m_14089_(limbSwing * 0.6662F) * 0.7F * limbSwingAmount / f;
      this.f_102814_.f_104203_ = -0.15F + Mth.m_14089_(limbSwing * 0.6662F + (float)Math.PI) * 0.7F * limbSwingAmount / f;
      this.setupAttackAnimation(entity, ageInTicks, 5.0F);
      if (entity.m_6047_()) {
         this.f_102810_.f_104203_ = 0.5F;
         ModelPart var10000 = this.f_102810_;
         var10000.f_104202_ -= 4.0F;
         var10000 = this.f_102811_;
         var10000.f_104203_ += 0.4F;
         var10000 = this.f_102811_;
         var10000.f_104202_ -= 8.5F;
         ++this.f_102811_.f_104201_;
         var10000 = this.f_102812_;
         var10000.f_104203_ += 0.4F;
         var10000 = this.f_102812_;
         var10000.f_104202_ -= 8.5F;
         ++this.f_102812_.f_104201_;
         this.f_102813_.f_104202_ = 1.5F;
         this.f_102814_.f_104202_ = 1.5F;
         this.f_102813_.f_104201_ = 9.0F;
         this.f_102814_.f_104201_ = 9.0F;
         var10000 = this.f_102808_;
         var10000.f_104202_ -= 15.0F;
         var10000 = this.f_102808_;
         var10000.f_104201_ += 5.0F;
      }

   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      poseStack.m_85837_((double)0.0F, 0.01, (double)0.0F);
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
      float dir = side == HumanoidArm.RIGHT ? 1.0F : -1.0F;
      stack.m_85837_(-0.1 * (double)dir, 0.35, -0.1);
      stack.m_252781_(Axis.f_252403_.m_252977_(-10.0F * dir));
   }

   public void renderFirstPersonArm(PoseStack stack, VertexConsumer vertex, int packedLight, int overlay, float red, float green, float blue, float alpha, HumanoidArm side, boolean isLeg) {
      float sideMod = side == HumanoidArm.RIGHT ? 1.0F : -1.0F;
      stack.m_85837_((double)0.0F, -0.3, -0.3);
      stack.m_252781_(Axis.f_252529_.m_252977_(20.0F));
      super.renderFirstPersonArm(stack, vertex, packedLight, overlay, red, green, blue, alpha, side, isLeg);
   }
}
