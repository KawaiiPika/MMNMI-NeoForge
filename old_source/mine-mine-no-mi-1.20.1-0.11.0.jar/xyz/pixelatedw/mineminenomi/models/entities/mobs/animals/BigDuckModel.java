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
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.BigDuckEntity;

public class BigDuckModel<T extends BigDuckEntity> extends EntityModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "big_duck"), "main");
   private final ModelPart body;
   private final ModelPart leftWing;
   private final ModelPart rightWing;
   private final ModelPart tail;
   private final ModelPart leftLeg;
   private final ModelPart lowerLeftLeg;
   private final ModelPart leftFoot;
   private final ModelPart rightLeg;
   private final ModelPart lowerRightLeg;
   private final ModelPart rightFoot;
   private final ModelPart head;
   private final ModelPart neck;
   private final ModelPart mainHead;
   private final ModelPart upperBeak;
   private final ModelPart lowerBeak;
   protected float quackAnimationProgress;

   public BigDuckModel(ModelPart root) {
      this.body = root.m_171324_("body");
      this.leftWing = this.body.m_171324_("leftWing");
      this.rightWing = this.body.m_171324_("rightWing");
      this.tail = this.body.m_171324_("tail");
      this.leftLeg = root.m_171324_("leftLeg");
      this.lowerLeftLeg = this.leftLeg.m_171324_("lowerLeftLeg");
      this.leftFoot = this.lowerLeftLeg.m_171324_("leftFoot");
      this.rightLeg = root.m_171324_("rightLeg");
      this.lowerRightLeg = this.rightLeg.m_171324_("lowerRightLeg");
      this.rightFoot = this.lowerRightLeg.m_171324_("rightFoot");
      this.head = root.m_171324_("head");
      this.neck = this.head.m_171324_("neck");
      this.mainHead = this.head.m_171324_("mainHead");
      this.upperBeak = this.mainHead.m_171324_("upperBeak");
      this.lowerBeak = this.mainHead.m_171324_("lowerBeak");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition body = partdefinition.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(64, 30).m_171488_(-7.0F, -5.4042F, -14.8958F, 14.0F, 11.0F, 18.0F, new CubeDeformation(0.0F)).m_171514_(60, 61).m_171488_(-6.5F, -3.6542F, -12.3958F, 13.0F, 10.0F, 15.0F, new CubeDeformation(0.0F)).m_171514_(72, 0).m_171488_(-5.5F, -5.9542F, -15.6458F, 11.0F, 11.0F, 17.0F, new CubeDeformation(0.0F)).m_171514_(61, 89).m_171488_(-6.0F, -4.7042F, 2.6042F, 12.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)).m_171514_(61, 104).m_171488_(-5.0F, -4.4042F, 5.6042F, 10.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 6.4042F, 7.8958F));
      body.m_171599_("leftWing", CubeListBuilder.m_171558_().m_171514_(8, 72).m_171480_().m_171488_(0.5F, -2.4526F, -2.2454F, 1.0F, 9.0F, 13.0F, new CubeDeformation(0.0F)).m_171555_(false).m_171514_(0, 84).m_171480_().m_171488_(0.5F, -2.4526F, 10.7546F, 1.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(6.5F, -2.9042F, -11.8958F, -0.2182F, 0.0F, 0.0F));
      body.m_171599_("rightWing", CubeListBuilder.m_171558_().m_171514_(8, 72).m_171488_(-1.5F, -2.5F, -1.8125F, 1.0F, 9.0F, 13.0F, new CubeDeformation(0.0F)).m_171514_(0, 84).m_171488_(-1.5F, -2.5F, 11.1875F, 1.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-6.5F, -2.9042F, -11.8958F, -0.2182F, 0.0F, 0.0F));
      body.m_171599_("tail", CubeListBuilder.m_171558_().m_171514_(53, 116).m_171488_(-5.0F, 0.1937F, 0.0119F, 10.0F, 0.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, -4.3853F, 7.3423F, -0.1309F, 0.0F, 0.0F));
      PartDefinition leftLeg = partdefinition.m_171599_("leftLeg", CubeListBuilder.m_171558_().m_171514_(16, 97).m_171480_().m_171488_(-1.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171419_(2.5F, 11.3125F, 3.0F));
      PartDefinition lowerLeftLeg = leftLeg.m_171599_("lowerLeftLeg", CubeListBuilder.m_171558_().m_171514_(16, 107).m_171480_().m_171488_(-1.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171419_(0.0F, 6.0F, 0.0F));
      lowerLeftLeg.m_171599_("leftFoot", CubeListBuilder.m_171558_().m_171514_(-7, 114).m_171480_().m_171488_(-2.5F, 1.0F, -7.0F, 5.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)).m_171555_(false).m_171514_(13, 117).m_171480_().m_171488_(-1.5F, 0.0F, -3.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171419_(0.0F, 5.6875F, 0.0F));
      PartDefinition rightLeg = partdefinition.m_171599_("rightLeg", CubeListBuilder.m_171558_().m_171514_(16, 97).m_171488_(-1.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(-2.5F, 11.3125F, 3.0F));
      PartDefinition lowerRightLeg = rightLeg.m_171599_("lowerRightLeg", CubeListBuilder.m_171558_().m_171514_(16, 107).m_171488_(-1.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 6.0F, 0.0F));
      lowerRightLeg.m_171599_("rightFoot", CubeListBuilder.m_171558_().m_171514_(13, 117).m_171488_(-1.5F, -0.25F, -3.0625F, 3.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)).m_171514_(-7, 114).m_171488_(-2.5F, 0.75F, -7.0625F, 5.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 5.9375F, 0.0625F));
      PartDefinition head = partdefinition.m_171599_("head", CubeListBuilder.m_171558_(), PartPose.m_171419_(-0.0789F, 0.1837F, -4.0F));
      head.m_171599_("neck", CubeListBuilder.m_171558_().m_171514_(38, 25).m_171488_(-6.9211F, -4.61F, -4.4195F, 8.0F, 11.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(3.0F, 0.0F, 1.0F, 0.4363F, 0.0F, 0.0F));
      PartDefinition mainHead = head.m_171599_("mainHead", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-5.5F, -8.5F, -9.0F, 11.0F, 11.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0789F, -2.9837F, -0.5F));
      mainHead.m_171599_("upperBeak", CubeListBuilder.m_171558_().m_171514_(0, 24).m_171488_(-5.0F, -1.5F, -5.0F, 10.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)).m_171514_(0, 35).m_171488_(-5.5F, -1.5F, -11.0F, 11.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, -2.0F, -9.0F));
      mainHead.m_171599_("lowerBeak", CubeListBuilder.m_171558_().m_171514_(0, 46).m_171488_(-4.5F, -1.0F, -11.5F, 9.0F, 2.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 0.5F, -8.0F));
      return LayerDefinition.m_171565_(meshdefinition, 128, 128);
   }

   public void prepareMobModel(T entity, float limbSwing, float limbSwingAmount, float partialTicks) {
      this.quackAnimationProgress = entity.getQuackAnimationProgress(partialTicks);
   }

   public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.head.f_104204_ = netHeadYaw * ((float)Math.PI / 180F);
      this.head.f_104203_ = headPitch * ((float)Math.PI / 180F);
      if (this.quackAnimationProgress < 1.0F) {
         this.upperBeak.f_104203_ = -Mth.m_14031_(this.quackAnimationProgress * 2.0F * (float)Math.PI) * 0.5F;
         this.upperBeak.f_104203_ = Math.min(this.upperBeak.f_104203_, 0.0F);
         this.lowerBeak.f_104203_ = Mth.m_14031_(this.quackAnimationProgress * 2.0F * (float)Math.PI) * 0.5F;
         this.lowerBeak.f_104203_ = Math.max(this.lowerBeak.f_104203_, 0.0F);
      } else {
         this.upperBeak.f_104203_ = 0.0F;
         this.lowerBeak.f_104203_ = 0.0F;
      }

      Vec3 vector3d = entity.m_20184_();
      if (!entity.m_20096_() && vector3d.f_82480_ < (double)0.0F) {
         this.leftWing.f_104203_ = (float)Math.toRadians((double)75.0F);
         this.leftWing.f_104205_ = (float)Math.toRadians((double)75.0F) + (float)(Math.sin((double)(ageInTicks * 0.75F)) * (double)0.9F);
         this.rightWing.f_104203_ = (float)Math.toRadians((double)75.0F);
         this.rightWing.f_104205_ = -((float)Math.toRadians((double)75.0F)) - (float)(Math.sin((double)(ageInTicks * 0.75F)) * (double)0.9F);
         this.tail.f_104203_ = (float)(Math.sin((double)(ageInTicks * 1.2F)) * (double)0.4F);
      } else {
         this.leftWing.f_104205_ = this.leftWing.f_104204_ = this.leftWing.f_104203_ = 0.0F;
         this.rightWing.f_104205_ = this.rightWing.f_104204_ = this.rightWing.f_104203_ = 0.0F;
         float f = 1.0F;
         this.rightLeg.f_104203_ = Mth.m_14089_(limbSwing * 0.6F) * 1.5F * limbSwingAmount / f;
         this.leftLeg.f_104203_ = Mth.m_14089_(limbSwing * 0.6F + (float)Math.PI) * 1.5F * limbSwingAmount / f;
         float tailSpeed = 0.2F;
         if (entity.m_6688_() != null && entity.m_6688_().m_20142_()) {
            tailSpeed = 0.6F;
            this.leftWing.f_104205_ = (float)Math.toRadians((double)55.0F);
            this.leftWing.f_104203_ = (float)(Math.sin((double)(ageInTicks * 0.15F)) * (double)0.05F);
            this.leftWing.f_104204_ = (float)Math.toRadians((double)25.0F);
            this.rightWing.f_104205_ = (float)Math.toRadians((double)-55.0F);
            this.rightWing.f_104203_ = (float)(Math.sin((double)(ageInTicks * 0.15F)) * (double)0.05F);
            this.rightWing.f_104204_ = (float)Math.toRadians((double)-25.0F);
         }

         this.tail.f_104203_ = (float)(Math.sin((double)(ageInTicks * tailSpeed)) * (double)0.1F);
      }

   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      if (this.f_102610_) {
         poseStack.m_85841_(0.5F, 0.5F, 0.5F);
         poseStack.m_85837_((double)0.0F, (double)1.5F, (double)0.0F);
      }

      this.body.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.leftLeg.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.rightLeg.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.head.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }
}
