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
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.BananawaniEntity;

public class BananawaniModel<T extends BananawaniEntity> extends EntityModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "bananawani"), "main");
   private final ModelPart body;
   private final ModelPart tail;
   private final ModelPart tail2;
   private final ModelPart tail3;
   private final ModelPart tail4;
   private final ModelPart tail5;
   private final ModelPart tail6;
   private final ModelPart tail7;
   private final ModelPart tailOrnament;
   private final ModelPart tailOrnament2;
   private final ModelPart tailOrnament3;
   private final ModelPart tailOrnament4;
   private final ModelPart tailOrnament5;
   private final ModelPart head;
   private final ModelPart lowerMouth;
   private final ModelPart upperMouth;
   private final ModelPart ornament;
   private final ModelPart ornament2;
   private final ModelPart ornament3;
   private final ModelPart ornament4;
   private final ModelPart ornament5;
   private final ModelPart leftBackLeg;
   private final ModelPart leftBackLeg2;
   private final ModelPart leftBackPaw;
   private final ModelPart leftBackPaw2;
   private final ModelPart rightBackLeg;
   private final ModelPart rightBackLeg2;
   private final ModelPart rightBackPaw;
   private final ModelPart rightBackPaw2;
   private final ModelPart leftFrontLeg;
   private final ModelPart leftFrontLeg2;
   private final ModelPart leftFrontPaw;
   private final ModelPart leftFrontPaw2;
   private final ModelPart rightFrontLeg;
   private final ModelPart rightFrontLeg2;
   private final ModelPart rightFrontPaw;
   private final ModelPart rightFrontPaw2;
   protected float biteAnimationProgress;

   public BananawaniModel(ModelPart root) {
      this.body = root.m_171324_("body");
      this.tail = root.m_171324_("tail");
      this.tail2 = this.tail.m_171324_("tail2");
      this.tail3 = this.tail2.m_171324_("tail3");
      this.tail4 = this.tail3.m_171324_("tail4");
      this.tail5 = this.tail4.m_171324_("tail5");
      this.tail6 = this.tail5.m_171324_("tail6");
      this.tail7 = this.tail6.m_171324_("tail7");
      this.tailOrnament = this.tail7.m_171324_("tailOrnament");
      this.tailOrnament2 = this.tailOrnament.m_171324_("tailOrnament2");
      this.tailOrnament3 = this.tailOrnament2.m_171324_("tailOrnament3");
      this.tailOrnament4 = this.tailOrnament3.m_171324_("tailOrnament4");
      this.tailOrnament5 = this.tailOrnament.m_171324_("tailOrnament5");
      this.head = root.m_171324_("head");
      this.lowerMouth = this.head.m_171324_("lowerMouth");
      this.upperMouth = this.head.m_171324_("upperMouth");
      this.ornament = this.upperMouth.m_171324_("ornament");
      this.ornament2 = this.ornament.m_171324_("ornament2");
      this.ornament3 = this.ornament2.m_171324_("ornament3");
      this.ornament4 = this.ornament3.m_171324_("ornament4");
      this.ornament5 = this.ornament.m_171324_("ornament5");
      this.leftBackLeg = root.m_171324_("leftBackLeg");
      this.leftBackLeg2 = this.leftBackLeg.m_171324_("leftBackLeg2");
      this.leftBackPaw = this.leftBackLeg2.m_171324_("leftBackPaw");
      this.leftBackPaw2 = this.leftBackPaw.m_171324_("leftBackPaw2");
      this.rightBackLeg = root.m_171324_("rightBackLeg");
      this.rightBackLeg2 = this.rightBackLeg.m_171324_("rightBackLeg2");
      this.rightBackPaw = this.rightBackLeg2.m_171324_("rightBackPaw");
      this.rightBackPaw2 = this.rightBackPaw.m_171324_("rightBackPaw2");
      this.leftFrontLeg = root.m_171324_("leftFrontLeg");
      this.leftFrontLeg2 = this.leftFrontLeg.m_171324_("leftFrontLeg2");
      this.leftFrontPaw = this.leftFrontLeg2.m_171324_("leftFrontPaw");
      this.leftFrontPaw2 = this.leftFrontPaw.m_171324_("leftFrontPaw2");
      this.rightFrontLeg = root.m_171324_("rightFrontLeg");
      this.rightFrontLeg2 = this.rightFrontLeg.m_171324_("rightFrontLeg2");
      this.rightFrontPaw = this.rightFrontLeg2.m_171324_("rightFrontPaw");
      this.rightFrontPaw2 = this.rightFrontPaw.m_171324_("rightFrontPaw2");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      partdefinition.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(0, 1).m_171488_(-8.0F, -7.0F, -12.0F, 16.0F, 9.0F, 24.0F, new CubeDeformation(0.0F)).m_171514_(0, 35).m_171488_(-7.5F, 2.0F, -12.0F, 15.0F, 1.0F, 24.0F, new CubeDeformation(0.0F)).m_171514_(52, 63).m_171488_(-7.0F, -9.0F, -12.0F, 14.0F, 2.0F, 24.0F, new CubeDeformation(0.0F)).m_171514_(0, 62).m_171488_(-6.0F, -10.0F, -11.0F, 12.0F, 1.0F, 22.0F, new CubeDeformation(0.0F)).m_171514_(51, 91).m_171488_(-7.0F, -7.0F, -16.0F, 14.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)).m_171514_(59, 18).m_171488_(-6.0F, -8.25F, -15.5F, 12.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 18.0F, 3.0F));
      PartDefinition tail = partdefinition.m_171599_("tail", CubeListBuilder.m_171558_().m_171514_(60, 37).m_171488_(-7.0F, -4.5F, 0.0F, 14.0F, 9.0F, 8.0F, new CubeDeformation(0.0F)).m_171514_(60, 0).m_171488_(-6.0F, -5.5F, 0.5F, 12.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 15.5F, 14.5F));
      PartDefinition tail2 = tail.m_171599_("tail2", CubeListBuilder.m_171558_().m_171514_(92, 0).m_171488_(-6.0F, -5.0F, 0.5F, 12.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 1.25F, 6.75F, 0.2182F, 0.0F, 0.0F));
      PartDefinition tail3 = tail2.m_171599_("tail3", CubeListBuilder.m_171558_().m_171514_(100, 15).m_171488_(-5.0F, -3.25F, 0.5F, 10.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, -1.0F, 5.5F, 0.0873F, 0.0F, 0.0F));
      PartDefinition tail4 = tail3.m_171599_("tail4", CubeListBuilder.m_171558_().m_171514_(106, 27).m_171488_(-4.0F, -2.5F, 0.0F, 8.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 0.0F, 3.75F, 0.1745F, 0.0F, 0.0F));
      PartDefinition tail5 = tail4.m_171599_("tail5", CubeListBuilder.m_171558_().m_171514_(110, 37).m_171488_(-3.0F, -2.5F, 0.0F, 6.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 0.75F, 2.75F, 0.0873F, 0.0F, 0.0F));
      PartDefinition tail6 = tail5.m_171599_("tail6", CubeListBuilder.m_171558_().m_171514_(116, 46).m_171488_(-2.0F, -2.0F, 0.0F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 0.25F, 2.75F, 0.1745F, 0.0F, 0.0F));
      PartDefinition tail7 = tail6.m_171599_("tail7", CubeListBuilder.m_171558_().m_171514_(120, 53).m_171488_(-1.0F, -1.5F, 0.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 0.25F, 1.5F, 0.2182F, 0.0F, 0.0F));
      PartDefinition tailOrnament = tail7.m_171599_("tailOrnament", CubeListBuilder.m_171558_(), PartPose.m_171423_(-0.4837F, -0.7728F, 1.0F, -1.0036F, 0.0F, 0.0F));
      tailOrnament.m_171599_("tailOrnament_r1", CubeListBuilder.m_171558_().m_171514_(106, 58).m_171488_(-3.0477F, -2.3929F, -0.5F, 4.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0814F, -0.0843F, 0.0F, 0.0F, 0.0F, 0.3491F));
      PartDefinition tailOrnament2 = tailOrnament.m_171599_("tailOrnament2", CubeListBuilder.m_171558_().m_171514_(106, 58).m_171488_(-3.0F, -2.5F, -0.5F, 4.0F, 3.0F, 3.0F, new CubeDeformation(-0.001F)), PartPose.m_171423_(3.4837F, -0.7772F, 0.0F, 0.0F, 0.0F, -0.3491F));
      PartDefinition tailOrnament3 = tailOrnament2.m_171599_("tailOrnament3", CubeListBuilder.m_171558_().m_171514_(106, 58).m_171488_(-2.5F, -2.5F, -0.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.95F, -0.35F, 0.0F, 0.0F, 0.0F, 0.7854F));
      tailOrnament3.m_171599_("tailOrnament4", CubeListBuilder.m_171558_().m_171514_(106, 54).m_171488_(-2.5F, -2.5F, -1.5F, 3.0F, 3.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(-0.05F, -3.05F, -1.0F));
      tailOrnament.m_171599_("tailOrnament5", CubeListBuilder.m_171558_().m_171514_(106, 58).m_171488_(-2.5F, -2.5F, -0.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(-0.001F)), PartPose.m_171423_(-1.2163F, -2.3772F, 0.0F, 0.0F, 0.0F, -0.5672F));
      PartDefinition head = partdefinition.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(59, 7).m_171488_(-5.0F, -3.9667F, -7.75F, 10.0F, 3.0F, 7.0F, new CubeDeformation(0.0F)).m_171514_(49, 106).m_171488_(-6.0F, -1.7667F, -8.75F, 12.0F, 6.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 15.2667F, -12.25F));
      head.m_171599_("lowerMouth", CubeListBuilder.m_171558_().m_171514_(0, 103).m_171488_(-5.5F, -0.25F, -12.25F, 11.0F, 3.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 0.9833F, -8.5F));
      PartDefinition upperMouth = head.m_171599_("upperMouth", CubeListBuilder.m_171558_().m_171514_(0, 87).m_171488_(-5.5F, -1.7667F, -12.25F, 11.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 0.5F, -8.5F));
      PartDefinition ornament = upperMouth.m_171599_("ornament", CubeListBuilder.m_171558_(), PartPose.m_171419_(-0.4837F, -2.2894F, -10.0F));
      ornament.m_171599_("ornament_r1", CubeListBuilder.m_171558_().m_171514_(1, 93).m_171488_(-1.0477F, -0.3929F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0814F, -0.0843F, 0.0F, 0.0F, 0.0F, 0.3491F));
      PartDefinition ornament2 = ornament.m_171599_("ornament2", CubeListBuilder.m_171558_().m_171514_(1, 93).m_171488_(-1.0F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.001F)), PartPose.m_171423_(1.4837F, -0.0272F, 0.0F, 0.0F, 0.0F, -0.3491F));
      PartDefinition ornament3 = ornament2.m_171599_("ornament3", CubeListBuilder.m_171558_().m_171514_(1, 93).m_171488_(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.95F, -0.35F, 0.0F, 0.0F, 0.0F, 0.7854F));
      ornament3.m_171599_("ornament4", CubeListBuilder.m_171558_().m_171514_(1, 91).m_171488_(-0.5F, -0.5F, -1.5F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(-0.05F, -0.8F, 0.0F));
      ornament.m_171599_("ornament5", CubeListBuilder.m_171558_().m_171514_(1, 93).m_171488_(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.001F)), PartPose.m_171423_(-0.9663F, -0.6272F, 0.0F, 0.0F, 0.0F, -0.5672F));
      PartDefinition leftBackLeg = partdefinition.m_171599_("leftBackLeg", CubeListBuilder.m_171558_(), PartPose.m_171423_(7.25F, 18.0F, 9.75F, 0.0F, -0.7418F, 0.0F));
      leftBackLeg.m_171599_("leftBackLeg_r1", CubeListBuilder.m_171558_().m_171514_(105, 95).m_171488_(-4.723F, -2.0F, -0.5F, 7.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(1.223F, 1.361F, -2.2513F, 0.1309F, 0.6981F, 0.48F));
      PartDefinition leftBackLeg2 = leftBackLeg.m_171599_("leftBackLeg2", CubeListBuilder.m_171558_().m_171514_(105, 105).m_171488_(-1.9113F, -2.5F, -1.6398F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(3.3395F, 2.9488F, -2.9942F, -0.0886F, 0.1739F, -0.0154F));
      PartDefinition leftBackPaw = leftBackLeg2.m_171599_("leftBackPaw", CubeListBuilder.m_171558_().m_171514_(105, 116).m_171488_(-1.3182F, -1.0F, -1.8327F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.01F)), PartPose.m_171423_(-0.5931F, 3.2271F, 0.2929F, 0.0873F, 0.0F, 0.0F));
      leftBackPaw.m_171599_("leftBackPaw2", CubeListBuilder.m_171558_().m_171514_(108, 125).m_171488_(-1.3182F, 0.0F, -2.6827F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.02F)), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      PartDefinition rightBackLeg = partdefinition.m_171599_("rightBackLeg", CubeListBuilder.m_171558_(), PartPose.m_171423_(-7.25F, 18.0F, 9.75F, 0.0F, 0.7418F, 0.0F));
      rightBackLeg.m_171599_("rightBackLeg_r1", CubeListBuilder.m_171558_().m_171514_(105, 95).m_171480_().m_171488_(-2.277F, -2.0F, -0.5F, 7.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(-1.223F, 1.361F, -2.2513F, 0.1309F, -0.6981F, -0.48F));
      PartDefinition rightBackLeg2 = rightBackLeg.m_171599_("rightBackLeg2", CubeListBuilder.m_171558_().m_171514_(105, 105).m_171480_().m_171488_(-2.0887F, -2.5F, -1.6398F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(-3.3395F, 2.9488F, -2.9942F, -0.0886F, -0.1739F, 0.0154F));
      PartDefinition rightBackPaw = rightBackLeg2.m_171599_("rightBackPaw", CubeListBuilder.m_171558_().m_171514_(105, 116).m_171480_().m_171488_(-2.6818F, -1.0F, -1.8327F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.01F)).m_171555_(false), PartPose.m_171423_(0.5931F, 3.2271F, 0.2929F, 0.0873F, 0.0F, 0.0F));
      rightBackPaw.m_171599_("rightBackPaw2", CubeListBuilder.m_171558_().m_171514_(108, 125).m_171480_().m_171488_(-2.6818F, 0.0F, -2.6827F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.02F)).m_171555_(false), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      PartDefinition leftFrontLeg = partdefinition.m_171599_("leftFrontLeg", CubeListBuilder.m_171558_(), PartPose.m_171423_(7.25F, 18.0F, -4.25F, 0.0F, -0.5236F, 0.0F));
      leftFrontLeg.m_171599_("leftFrontLeg_r1", CubeListBuilder.m_171558_().m_171514_(105, 95).m_171488_(-4.723F, -2.0F, -0.5F, 7.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(1.223F, 1.361F, -2.2513F, 0.1309F, 0.6981F, 0.48F));
      PartDefinition leftFrontLeg2 = leftFrontLeg.m_171599_("leftFrontLeg2", CubeListBuilder.m_171558_().m_171514_(105, 105).m_171488_(-1.9113F, -2.5F, -1.6398F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(3.3395F, 2.9488F, -2.9942F, -0.1007F, 0.5214F, -0.0503F));
      PartDefinition leftFrontPaw = leftFrontLeg2.m_171599_("leftFrontPaw", CubeListBuilder.m_171558_().m_171514_(105, 116).m_171488_(-1.3182F, -1.0F, -1.8327F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.01F)), PartPose.m_171423_(-0.5931F, 3.2271F, 0.2929F, 0.0873F, 0.0F, 0.0F));
      leftFrontPaw.m_171599_("leftFrontPaw2", CubeListBuilder.m_171558_().m_171514_(108, 125).m_171488_(-1.3182F, 0.0F, -2.6827F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.02F)), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      PartDefinition rightFrontLeg = partdefinition.m_171599_("rightFrontLeg", CubeListBuilder.m_171558_(), PartPose.m_171423_(-7.25F, 18.0F, -4.25F, 0.0F, 0.5236F, 0.0F));
      rightFrontLeg.m_171599_("rightFrontLeg_r1", CubeListBuilder.m_171558_().m_171514_(105, 95).m_171480_().m_171488_(-2.277F, -2.0F, -0.5F, 7.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(-1.223F, 1.361F, -2.2513F, 0.1309F, -0.6981F, -0.48F));
      PartDefinition rightFrontLeg2 = rightFrontLeg.m_171599_("rightFrontLeg2", CubeListBuilder.m_171558_().m_171514_(105, 105).m_171480_().m_171488_(-2.0887F, -2.5F, -1.6398F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(-3.3395F, 2.9488F, -2.9942F, -0.1007F, -0.5214F, 0.0503F));
      PartDefinition rightFrontPaw = rightFrontLeg2.m_171599_("rightFrontPaw", CubeListBuilder.m_171558_().m_171514_(105, 116).m_171480_().m_171488_(-2.6818F, -1.0F, -1.8327F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.01F)).m_171555_(false), PartPose.m_171423_(0.5931F, 3.2271F, 0.2929F, 0.0873F, 0.0F, 0.0F));
      rightFrontPaw.m_171599_("rightFrontPaw2", CubeListBuilder.m_171558_().m_171514_(108, 125).m_171480_().m_171488_(-2.6818F, 0.0F, -2.6827F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.02F)).m_171555_(false), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      return LayerDefinition.m_171565_(meshdefinition, 128, 128);
   }

   public void prepareMobModel(T entity, float limbSwing, float limbSwingAmount, float partialTicks) {
      if (entity instanceof BananawaniEntity) {
         this.biteAnimationProgress = entity.getBiteAnimationProgress(partialTicks);
      }

   }

   public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      float f = 1.0F;
      float speed = 0.25F;
      float spread = 0.4F;
      if (!entity.m_6109_()) {
         speed = 0.8F;
      }

      this.rightFrontLeg.f_104203_ = Mth.m_14089_(limbSwing * speed) * spread * limbSwingAmount / f;
      this.leftFrontLeg.f_104203_ = Mth.m_14089_(limbSwing * speed + (float)Math.PI) * spread * limbSwingAmount / f;
      this.rightBackLeg.f_104203_ = Mth.m_14031_(limbSwing * speed) * spread * limbSwingAmount / f;
      this.leftBackLeg.f_104203_ = Mth.m_14031_(limbSwing * speed + (float)Math.PI) * spread * limbSwingAmount / f;
      if (this.biteAnimationProgress < 1.0F) {
         float angle = Mth.m_14031_(this.biteAnimationProgress * 0.5F * (float)Math.PI) * 0.7F;
         this.upperMouth.f_104203_ = (float)Math.toRadians((double)-40.0F) + angle;
         this.lowerMouth.f_104203_ = (float)Math.toRadians((double)40.0F) - angle;
      } else if (this.biteAnimationProgress >= 1.0F) {
         entity.f_20911_ = false;
         this.upperMouth.f_104203_ = (float)Math.toRadians((double)0.0F);
         this.lowerMouth.f_104203_ = (float)Math.toRadians((double)0.0F);
      }

      this.tail.f_104204_ = Mth.m_14031_(ageInTicks * 0.05F) * 0.1F;
      this.tail3.f_104204_ = Mth.m_14031_(ageInTicks * 0.05F) * 0.1F;
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.body.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.tail.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.head.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.leftBackLeg.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.rightBackLeg.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.leftFrontLeg.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.rightFrontLeg.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }
}
