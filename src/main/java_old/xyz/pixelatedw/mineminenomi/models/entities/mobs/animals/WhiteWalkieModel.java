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
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.WhiteWalkieEntity;

public class WhiteWalkieModel<T extends WhiteWalkieEntity> extends EntityModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "white_walkie"), "main");
   private final ModelPart body;
   private final ModelPart ass;
   private final ModelPart tail;
   private final ModelPart frontLeftLeg;
   private final ModelPart frontLeftFoot;
   private final ModelPart frontRightLeg;
   private final ModelPart frontRightFoot;
   private final ModelPart backRightLeg;
   private final ModelPart backRightFoot;
   private final ModelPart backLeftLeg;
   private final ModelPart backLeftFoot;
   private final ModelPart bodyFur;
   private final ModelPart head;
   private final ModelPart upperMouth;
   private final ModelPart lowerMouth;
   private final ModelPart chinFur;
   private final ModelPart leftEar;
   private final ModelPart rightEar;
   protected float entityTick;
   protected float biteAnimationProgress;
   private boolean isSleeping;

   public WhiteWalkieModel(ModelPart root) {
      this.body = root.m_171324_("body");
      this.ass = this.body.m_171324_("ass");
      this.tail = this.body.m_171324_("tail");
      this.frontLeftLeg = this.body.m_171324_("frontLeftLeg");
      this.frontLeftFoot = this.frontLeftLeg.m_171324_("frontLeftFoot");
      this.frontRightLeg = this.body.m_171324_("frontRightLeg");
      this.frontRightFoot = this.frontRightLeg.m_171324_("frontRightFoot");
      this.backRightLeg = this.body.m_171324_("backRightLeg");
      this.backRightFoot = this.backRightLeg.m_171324_("backRightFoot");
      this.backLeftLeg = this.body.m_171324_("backLeftLeg");
      this.backLeftFoot = this.backLeftLeg.m_171324_("backLeftFoot");
      this.bodyFur = this.body.m_171324_("bodyFur");
      this.head = root.m_171324_("head");
      this.upperMouth = this.head.m_171324_("upperMouth");
      this.lowerMouth = this.head.m_171324_("lowerMouth");
      this.chinFur = this.lowerMouth.m_171324_("chinFur");
      this.leftEar = this.head.m_171324_("leftEar");
      this.rightEar = this.head.m_171324_("rightEar");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition body = partdefinition.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(6, 109).m_171488_(-11.0F, -8.3771F, -27.5417F, 22.0F, 16.0F, 5.0F, new CubeDeformation(0.0F)).m_171514_(0, 0).m_171488_(-13.0F, -9.0385F, -24.5417F, 26.0F, 20.0F, 34.0F, new CubeDeformation(0.0F)).m_171514_(0, 54).m_171488_(-11.0F, -11.0385F, -22.5417F, 22.0F, 20.0F, 30.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 3.4271F, 9.751F));
      body.m_171599_("ass", CubeListBuilder.m_171558_().m_171514_(89, 0).m_171488_(-15.0F, -10.1202F, -18.2696F, 24.0F, 18.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(3.0F, 2.5729F, 23.874F, -0.0436F, 0.0F, 0.0F));
      body.m_171599_("tail", CubeListBuilder.m_171558_().m_171514_(126, 29).m_171488_(-4.0F, -0.2505F, 1.0033F, 8.0F, 10.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, -5.4285F, 12.8316F, 0.1309F, 0.0F, 0.0F));
      PartDefinition frontLeftLeg = body.m_171599_("frontLeftLeg", CubeListBuilder.m_171558_().m_171514_(113, 61).m_171480_().m_171488_(-3.0F, -1.5847F, -3.176F, 6.0F, 10.0F, 6.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(7.0F, 10.5729F, -19.751F, -0.1309F, 0.0F, 0.0F));
      frontLeftLeg.m_171599_("frontLeftFoot", CubeListBuilder.m_171558_().m_171514_(110, 78).m_171480_().m_171488_(-3.0F, -2.0F, -4.25F, 6.0F, 4.0F, 8.0F, new CubeDeformation(-0.01F)).m_171555_(false), PartPose.m_171423_(0.0F, 8.3032F, -1.0862F, 0.1309F, 0.0F, 0.0F));
      PartDefinition frontRightLeg = body.m_171599_("frontRightLeg", CubeListBuilder.m_171558_().m_171514_(113, 61).m_171488_(-3.0F, -1.4799F, -1.7758F, 6.0F, 10.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-7.0F, 10.5729F, -21.001F, -0.1309F, 0.0F, 0.0F));
      frontRightLeg.m_171599_("frontRightFoot", CubeListBuilder.m_171558_().m_171514_(110, 78).m_171488_(-3.0F, -2.0F, -3.25F, 6.0F, 4.0F, 8.0F, new CubeDeformation(-0.01F)), PartPose.m_171423_(0.0F, 8.408F, -0.6861F, 0.1309F, 0.0F, 0.0F));
      PartDefinition backRightLeg = body.m_171599_("backRightLeg", CubeListBuilder.m_171558_().m_171514_(139, 63).m_171488_(-3.0F, -0.058F, -2.7389F, 6.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-8.0F, 10.5729F, 4.3735F, -0.1309F, 0.0F, 0.0F));
      backRightLeg.m_171599_("backRightFoot", CubeListBuilder.m_171558_().m_171514_(139, 78).m_171488_(-3.0F, -2.0F, -4.0F, 6.0F, 4.0F, 8.0F, new CubeDeformation(-0.01F)), PartPose.m_171423_(0.0F, 8.0212F, -0.7307F, 0.1309F, 0.0F, 0.0F));
      PartDefinition backLeftLeg = body.m_171599_("backLeftLeg", CubeListBuilder.m_171558_().m_171514_(139, 63).m_171480_().m_171488_(-3.0F, -0.0792F, -3.0F, 6.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(8.0F, 10.5729F, 4.6658F, -0.1309F, 0.0F, 0.0F));
      backLeftLeg.m_171599_("backLeftFoot", CubeListBuilder.m_171558_().m_171514_(139, 78).m_171480_().m_171488_(-3.0F, -2.0F, -4.0F, 6.0F, 4.0F, 8.0F, new CubeDeformation(-0.01F)).m_171555_(false), PartPose.m_171423_(0.0F, 8.0F, -0.9917F, 0.1309F, 0.0F, 0.0F));
      body.m_171599_("bodyFur", CubeListBuilder.m_171558_().m_171514_(64, 120).m_171488_(-11.0F, -2.25F, -28.0F, 22.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)).m_171514_(64, 77).m_171488_(12.0F, -2.25F, -27.0F, 0.0F, 7.0F, 32.0F, new CubeDeformation(0.0F)).m_171514_(64, 77).m_171488_(-12.0F, -2.25F, -27.0F, 0.0F, 7.0F, 32.0F, new CubeDeformation(0.0F)).m_171514_(64, 119).m_171488_(-11.0F, -3.25F, 6.0F, 22.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 11.8229F, 3.874F));
      PartDefinition head = partdefinition.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(4, 133).m_171488_(-10.0F, -9.879F, -20.199F, 20.0F, 18.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 4.5813F, -4.4598F, 0.0436F, 0.0F, 0.0F));
      head.m_171599_("upperMouth", CubeListBuilder.m_171558_().m_171514_(6, 162).m_171488_(-9.0F, -11.6868F, -16.3353F, 18.0F, 10.0F, 16.0F, new CubeDeformation(0.0F)).m_171514_(4, 170).m_171488_(3.0F, -2.3763F, -15.8167F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).m_171514_(4, 170).m_171488_(-7.0F, -2.3763F, -15.8167F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 4.7688F, -14.9009F));
      PartDefinition lowerMouth = head.m_171599_("lowerMouth", CubeListBuilder.m_171558_().m_171514_(3, 190).m_171488_(-8.0F, 0.2077F, -11.7395F, 16.0F, 4.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 2.912F, -18.2061F));
      lowerMouth.m_171599_("chinFur", CubeListBuilder.m_171558_().m_171514_(3, 203).m_171488_(7.0F, -2.4798F, -14.4479F, 0.0F, 4.0F, 10.375F, new CubeDeformation(0.0F)).m_171514_(3, 203).m_171488_(-7.0F, -2.4798F, -14.4479F, 0.0F, 4.0F, 10.375F, new CubeDeformation(0.0F)).m_171514_(3, 208).m_171488_(-7.0F, -2.4798F, -14.4479F, 14.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 4.5067F, 3.7492F));
      head.m_171599_("leftEar", CubeListBuilder.m_171558_().m_171514_(1, 164).m_171480_().m_171488_(-0.7642F, -0.5365F, -1.5F, 6.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(9.3123F, -8.7507F, -15.4047F, 0.0F, 0.0F, 0.48F));
      head.m_171599_("rightEar", CubeListBuilder.m_171558_().m_171514_(1, 164).m_171488_(-5.2358F, -0.5365F, -1.5F, 6.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-9.4837F, -8.6947F, -15.4047F, 0.0F, 0.0F, -0.48F));
      return LayerDefinition.m_171565_(meshdefinition, 256, 256);
   }

   public void prepareMobModel(T entity, float limbSwing, float limbSwingAmount, float partialTicks) {
      if (entity instanceof WhiteWalkieEntity) {
         this.biteAnimationProgress = entity.getBiteAnimationProgress(partialTicks);
      }

      this.entityTick = (float)entity.f_19797_;
      this.isSleeping = entity.m_5803_();
   }

   public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.head.f_104205_ = 0.0F;
      this.leftEar.f_104205_ = (float)Math.toRadians((double)60.0F);
      this.rightEar.f_104205_ = (float)Math.toRadians((double)-60.0F);
      float speed = 0.95F;
      float spread = 0.6F;
      if (entity.m_20142_()) {
         speed = 0.125F;
      }

      this.frontRightLeg.f_104203_ = (float)(Math.toRadians((double)-8.0F) + (double)(Mth.m_14089_(limbSwing * speed) * spread * limbSwingAmount));
      this.frontLeftLeg.f_104203_ = (float)(Math.toRadians((double)-8.0F) + (double)(Mth.m_14089_(limbSwing * speed + (float)Math.PI) * spread * limbSwingAmount));
      this.backRightLeg.f_104203_ = (float)(Math.toRadians((double)-8.0F) + (double)(Mth.m_14089_(limbSwing * speed + (float)Math.PI) * spread * limbSwingAmount));
      this.backLeftLeg.f_104203_ = (float)(Math.toRadians((double)-8.0F) + (double)(Mth.m_14089_(limbSwing * speed) * spread * limbSwingAmount));
      if (entity instanceof WhiteWalkieEntity && entity.getShakeAnimationTime() > 0.0F) {
         this.head.f_104205_ = (float)(Math.cos((double)(ageInTicks * 0.4F) + Math.PI) * (double)0.1F);
         ModelPart var10000 = this.leftEar;
         var10000.f_104205_ = (float)((double)var10000.f_104205_ + Math.cos((double)(ageInTicks * 0.8F) + Math.PI) * (double)0.2F);
         var10000 = this.rightEar;
         var10000.f_104205_ = (float)((double)var10000.f_104205_ + Math.cos((double)(ageInTicks * 0.8F) + Math.PI) * (double)0.2F);
      }

      if (this.biteAnimationProgress < 1.0F) {
         float angle = Mth.m_14031_(this.biteAnimationProgress * 0.5F * (float)Math.PI) * 0.7F;
         this.upperMouth.f_104203_ = (float)Math.toRadians((double)-40.0F) + angle;
         this.lowerMouth.f_104203_ = (float)Math.toRadians((double)40.0F) - angle;
      } else if (this.biteAnimationProgress >= 1.0F) {
         entity.f_20911_ = false;
         this.upperMouth.f_104203_ = (float)Math.toRadians((double)0.0F);
         this.lowerMouth.f_104203_ = (float)Math.toRadians((double)0.0F);
      }

   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.body.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.head.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }

   public void setSleepPosition(boolean flag) {
      this.showLegs(!flag);
      this.head.f_104201_ = flag ? 7.0F : 4.5813F;
   }

   public void showLegs(boolean showFlag) {
      if (showFlag) {
         this.frontRightLeg.f_104207_ = true;
         this.frontLeftLeg.f_104207_ = true;
         this.backRightLeg.f_104207_ = true;
         this.backLeftLeg.f_104207_ = true;
      } else {
         this.frontRightLeg.f_104207_ = false;
         this.frontLeftLeg.f_104207_ = false;
         this.backRightLeg.f_104207_ = false;
         this.backLeftLeg.f_104207_ = false;
      }

   }
}
