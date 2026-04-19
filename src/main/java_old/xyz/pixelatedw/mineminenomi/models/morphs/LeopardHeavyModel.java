package xyz.pixelatedw.mineminenomi.models.morphs;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
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

public class LeopardHeavyModel<T extends LivingEntity> extends HumanoidMorphModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "leopard_heavy"), "main");
   private final ModelPart nose;
   private final ModelPart leftEye;
   private final ModelPart rightEye;
   private final ModelPart leftEar;
   private final ModelPart rightEar;
   private final ModelPart tail;
   private final ModelPart tail2;
   private final ModelPart tail3;
   private final ModelPart lowerBody;
   private final ModelPart upperBody;
   private final ModelPart leftLeg2;
   private final ModelPart leftLeg3;
   private final ModelPart leftFoot;
   private final ModelPart rightLeg2;
   private final ModelPart rightLeg3;
   private final ModelPart rightFoot;
   private final ModelPart lowerLeftArm;
   private final ModelPart lowerRightArm;

   public LeopardHeavyModel(ModelPart root) {
      super(root);
      this.nose = this.f_102808_.m_171324_("nose");
      this.leftEye = this.f_102808_.m_171324_("leftEye");
      this.rightEye = this.f_102808_.m_171324_("rightEye");
      this.leftEar = this.f_102808_.m_171324_("leftEar");
      this.rightEar = this.f_102808_.m_171324_("rightEar");
      this.tail = this.f_102810_.m_171324_("tail");
      this.tail2 = this.tail.m_171324_("tail2");
      this.tail3 = this.tail2.m_171324_("tail3");
      this.lowerBody = this.f_102810_.m_171324_("lowerBody");
      this.upperBody = this.lowerBody.m_171324_("upperBody");
      this.leftLeg2 = this.f_102814_.m_171324_("leftLeg2");
      this.leftLeg3 = this.leftLeg2.m_171324_("leftLeg3");
      this.leftFoot = this.leftLeg3.m_171324_("leftFoot");
      this.rightLeg2 = this.f_102813_.m_171324_("rightLeg2");
      this.rightLeg3 = this.rightLeg2.m_171324_("rightLeg3");
      this.rightFoot = this.rightLeg3.m_171324_("rightFoot");
      this.lowerLeftArm = this.f_102812_.m_171324_("lowerLeftArm");
      this.lowerRightArm = this.f_102811_.m_171324_("lowerRightArm");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = HumanoidMorphModel.m_170681_(CubeDeformation.f_171458_, 0.0F);
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition head = partdefinition.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(72, 0).m_171488_(-2.9F, -3.2847F, -4.6892F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.55F)), PartPose.m_171419_(-0.1F, -9.5941F, -1.6107F));
      head.m_171599_("nose", CubeListBuilder.m_171558_().m_171514_(81, 13).m_171488_(-0.5F, 0.0F, -0.8F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.1F, -0.2097F, -4.6267F, -0.2618F, 0.0F, 0.0F));
      head.m_171599_("leftEye", CubeListBuilder.m_171558_().m_171514_(80, 28).m_171480_().m_171488_(-0.975F, -1.5F, 0.015F, 3.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171419_(1.875F, -0.8847F, -5.2743F));
      head.m_171599_("rightEye", CubeListBuilder.m_171558_().m_171514_(80, 28).m_171488_(-2.1F, -1.5F, 0.015F, 3.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(-1.6F, -0.8847F, -5.2743F));
      head.m_171599_("leftEar", CubeListBuilder.m_171558_().m_171514_(80, 23).m_171488_(-1.0317F, -1.1108F, -0.5993F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(2.7725F, -4.0157F, -1.2526F, 0.2004F, -0.562F, 0.0775F));
      head.m_171599_("rightEar", CubeListBuilder.m_171558_().m_171514_(80, 18).m_171488_(-1.9683F, -1.1108F, -0.5993F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-2.5225F, -4.0157F, -1.2526F, 0.2004F, 0.562F, -0.0775F));
      PartDefinition body = partdefinition.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(0, 117).m_171488_(-5.5F, -3.9375F, -2.375F, 11.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 7.9375F, 2.0F));
      PartDefinition tail = body.m_171599_("tail", CubeListBuilder.m_171558_().m_171514_(99, 25).m_171488_(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, -0.9375F, 3.4375F));
      PartDefinition tail2 = tail.m_171599_("tail2", CubeListBuilder.m_171558_().m_171514_(103, 13).m_171488_(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 9.0F, new CubeDeformation(0.001F)), PartPose.m_171419_(0.0F, 0.0F, 10.5F));
      tail2.m_171599_("tail3", CubeListBuilder.m_171558_().m_171514_(102, 0).m_171488_(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 0.0F, 8.5F));
      PartDefinition lowerBody = body.m_171599_("lowerBody", CubeListBuilder.m_171558_().m_171514_(0, 95).m_171488_(-6.5F, -13.9472F, -4.1392F, 13.0F, 14.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, -3.0F, 1.0625F, 0.1396F, 0.0F, 0.0F));
      lowerBody.m_171599_("upperBody", CubeListBuilder.m_171558_().m_171514_(0, 75).m_171488_(-7.5F, -5.8931F, -4.8572F, 15.0F, 12.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, -8.6875F, -0.8125F, 0.0873F, 0.0F, 0.0F));
      PartDefinition leftLeg = partdefinition.m_171599_("left_leg", CubeListBuilder.m_171558_().m_171514_(3, 0).m_171488_(-1.5F, -1.8125F, -3.375F, 4.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(3.5F, 8.7843F, 1.985F, -0.3491F, 0.0F, 0.0F));
      PartDefinition leftLeg2 = leftLeg.m_171599_("leftLeg2", CubeListBuilder.m_171558_(), PartPose.m_171423_(0.0F, 4.9749F, -2.2979F, -0.0349F, 0.0F, 0.0F));
      leftLeg2.m_171599_("lowerleftleg1_r1", CubeListBuilder.m_171558_().m_171514_(4, 14).m_171488_(2.5F, -1.5F, -2.5F, 3.0F, 3.0F, 5.0F, new CubeDeformation(0.2F)), PartPose.m_171423_(-3.5F, -0.2936F, 2.5072F, -0.1309F, 0.0F, 0.0F));
      PartDefinition leftLeg3 = leftLeg2.m_171599_("leftLeg3", CubeListBuilder.m_171558_().m_171514_(7, 23).m_171488_(-1.0F, -0.5005F, -0.9999F, 2.0F, 9.0F, 3.0F, new CubeDeformation(0.2F)), PartPose.m_171423_(0.5F, 0.5156F, 3.7208F, 0.5236F, 0.0F, 0.0F));
      leftLeg3.m_171599_("leftFoot", CubeListBuilder.m_171558_().m_171514_(0, 36).m_171488_(-2.0625F, -0.3125F, -6.0F, 4.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0625F, 7.7493F, -0.4146F, -0.1309F, 0.0F, 0.0F));
      PartDefinition rightLeg = partdefinition.m_171599_("right_leg", CubeListBuilder.m_171558_().m_171514_(3, 0).m_171480_().m_171488_(-2.5F, -1.8125F, -3.375F, 4.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(-3.5F, 8.7843F, 1.985F, -0.3491F, 0.0F, 0.0F));
      PartDefinition rightLeg2 = rightLeg.m_171599_("rightLeg2", CubeListBuilder.m_171558_(), PartPose.m_171423_(0.0F, 4.9749F, -2.2979F, -0.0349F, 0.0F, 0.0F));
      rightLeg2.m_171599_("lowerrightleg_r1", CubeListBuilder.m_171558_().m_171514_(4, 14).m_171480_().m_171488_(-5.5F, -1.5F, -2.5F, 3.0F, 3.0F, 5.0F, new CubeDeformation(0.2F)).m_171555_(false), PartPose.m_171423_(3.5F, -0.2936F, 2.5072F, -0.1309F, 0.0F, 0.0F));
      PartDefinition rightLeg3 = rightLeg2.m_171599_("rightLeg3", CubeListBuilder.m_171558_().m_171514_(7, 23).m_171480_().m_171488_(-1.0F, -0.5005F, -0.9999F, 2.0F, 9.0F, 3.0F, new CubeDeformation(0.2F)).m_171555_(false), PartPose.m_171423_(-0.5F, 0.5156F, 3.7208F, 0.5236F, 0.0F, 0.0F));
      rightLeg3.m_171599_("rightFoot", CubeListBuilder.m_171558_().m_171514_(0, 36).m_171480_().m_171488_(-1.9375F, -0.3125F, -6.0F, 4.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(-0.0625F, 7.7493F, -0.4146F, -0.1309F, 0.0F, 0.0F));
      PartDefinition leftArm = partdefinition.m_171599_("left_arm", CubeListBuilder.m_171558_().m_171514_(25, 0).m_171488_(-1.0F, -1.1142F, -3.4625F, 6.0F, 10.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(8.0F, -6.4813F, -5.0E-4F, 0.1745F, 0.0F, 0.0F));
      leftArm.m_171599_("lowerLeftArm", CubeListBuilder.m_171558_().m_171514_(27, 17).m_171488_(-2.5F, -0.3074F, -2.2925F, 5.0F, 11.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(2.0F, 7.6401F, -0.3356F, -0.3054F, 0.0F, 0.0F));
      PartDefinition rightArm = partdefinition.m_171599_("right_arm", CubeListBuilder.m_171558_().m_171514_(25, 0).m_171480_().m_171488_(-5.0F, -1.1142F, -3.4625F, 6.0F, 10.0F, 6.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(-8.0F, -6.4813F, -5.0E-4F, 0.1745F, 0.0F, 0.0F));
      rightArm.m_171599_("lowerRightArm", CubeListBuilder.m_171558_().m_171514_(27, 17).m_171480_().m_171488_(-2.5F, -0.3074F, -2.2925F, 5.0F, 11.0F, 5.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(-2.0F, 7.6401F, -0.3356F, -0.3054F, 0.0F, 0.0F));
      return LayerDefinition.m_171565_(meshdefinition, 128, 128);
   }

   public void m_6973_(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      RendererHelper.resetModelToDefaultPivots(this);
      this.setupHeadRotation(entity, headPitch, netHeadYaw);
      float f = 1.0F;
      this.f_102811_.f_104203_ = Mth.m_14089_(limbSwing * 0.6662F + (float)Math.PI) * 0.8F * limbSwingAmount * 0.5F / f;
      this.f_102812_.f_104203_ = Mth.m_14089_(limbSwing * 0.6662F) * 0.8F * limbSwingAmount * 0.5F / f;
      this.f_102813_.f_104203_ = -0.34F + Mth.m_14089_(limbSwing * 0.6662F) * 0.7F * limbSwingAmount / f;
      this.f_102814_.f_104203_ = -0.34F + Mth.m_14089_(limbSwing * 0.6662F + (float)Math.PI) * 0.7F * limbSwingAmount / f;
      ModelPart var10000 = this.tail;
      var10000.f_104204_ = (float)((double)var10000.f_104204_ + Math.sin((double)ageInTicks * 0.06) / (double)5.0F);
      var10000 = this.tail;
      var10000.f_104203_ = (float)((double)var10000.f_104203_ + Math.sin((double)ageInTicks * 0.06) / (double)5.0F);
      var10000 = this.tail2;
      var10000.f_104204_ = (float)((double)var10000.f_104204_ + Math.cos((double)ageInTicks * 0.06) / (double)7.0F);
      var10000 = this.tail2;
      var10000.f_104203_ = (float)((double)var10000.f_104203_ + Math.cos((double)ageInTicks * 0.06) / (double)2.0F);
      var10000 = this.tail3;
      var10000.f_104204_ = (float)((double)var10000.f_104204_ + Math.sin((double)ageInTicks * 0.06) / (double)7.0F);
      var10000 = this.tail3;
      var10000.f_104203_ = (float)((double)var10000.f_104203_ + Math.sin((double)ageInTicks * 0.06) / (double)2.0F);
      this.setupAttackAnimation(entity, ageInTicks, 3.0F);
      if (entity.m_6047_()) {
         this.f_102808_.f_104202_ = -6.0F;
         this.f_102808_.f_104201_ = -5.0F;
         this.f_102810_.f_104203_ = 0.2F;
         this.f_102810_.f_104201_ = 11.0F;
         this.f_102810_.f_104202_ = 1.0F;
         var10000 = this.f_102811_;
         var10000.f_104203_ += 0.4F;
         this.f_102811_.f_104202_ = -4.0F;
         this.f_102811_.f_104201_ = -3.0F;
         var10000 = this.f_102812_;
         var10000.f_104203_ += 0.4F;
         this.f_102812_.f_104202_ = -4.0F;
         this.f_102812_.f_104201_ = -3.0F;
         this.f_102813_.f_104201_ = 13.0F;
         this.f_102814_.f_104201_ = 13.0F;
      }

   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.f_102808_.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.f_102810_.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.f_102814_.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.f_102813_.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.f_102812_.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.f_102811_.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }

   public void m_6002_(HumanoidArm side, PoseStack stack) {
      super.m_6002_(side, stack);
      float sideMod = side == HumanoidArm.RIGHT ? 1.0F : -1.0F;
      stack.m_85837_(-0.05 * (double)sideMod, (double)0.5F, (double)-0.25F);
   }

   public void renderFirstPersonArm(PoseStack stack, VertexConsumer vertex, int packedLight, int overlay, float red, float green, float blue, float alpha, HumanoidArm side, boolean isLeg) {
      float sideMod = side == HumanoidArm.RIGHT ? 1.0F : -1.0F;
      stack.m_85837_(0.3 * (double)sideMod, 0.3, 0.2);
      stack.m_252781_(Axis.f_252529_.m_252977_(20.0F));
      if (isLeg) {
         stack.m_85837_(-0.1 * (double)sideMod, (double)0.0F, (double)-0.5F);
      }

      super.renderFirstPersonArm(stack, vertex, packedLight, overlay, red, green, blue, alpha, side, isLeg);
   }
}
