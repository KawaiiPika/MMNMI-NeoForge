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

public class AllosaurusWalkModel<T extends LivingEntity> extends QuadrupedMorphModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "allosaurus_walk"), "main");
   private final ModelPart leftLeg2;
   private final ModelPart leftFeet;
   private final ModelPart leftFeet2;
   private final ModelPart leftToe1;
   private final ModelPart leftToe2;
   private final ModelPart leftToe3;
   private final ModelPart leftToe4;
   private final ModelPart rightLeg2;
   private final ModelPart rightFeet;
   private final ModelPart rightFeet2;
   private final ModelPart rightToe1;
   private final ModelPart rightToe2;
   private final ModelPart rightToe3;
   private final ModelPart rightToe4;
   private final ModelPart rightArm2;
   private final ModelPart rightHand;
   private final ModelPart rightFinger1;
   private final ModelPart rightFinger2;
   private final ModelPart rightFinger3;
   private final ModelPart leftArm2;
   private final ModelPart leftHand;
   private final ModelPart leftFinger1;
   private final ModelPart leftFinger2;
   private final ModelPart leftFinger3;
   private final ModelPart neck3;
   private final ModelPart neck2;
   private final ModelPart neck1;
   private final ModelPart upperHead;
   private final ModelPart upperTeeth;
   private final ModelPart lowerHead;
   private final ModelPart lowerTeeth;
   private final ModelPart tail;
   private final ModelPart tail2;
   private final ModelPart tail3;
   private final ModelPart tail4;
   private final ModelPart tail5;
   private final ModelPart tail6;
   private final ModelPart tail7;
   private final ModelPart tail8;

   public AllosaurusWalkModel(ModelPart root) {
      super(root);
      this.leftLeg2 = this.f_170853_.m_171324_("leftLeg2");
      this.leftFeet = this.leftLeg2.m_171324_("leftFeet");
      this.leftFeet2 = this.leftFeet.m_171324_("leftFeet2");
      this.leftToe1 = this.leftFeet2.m_171324_("leftToe1");
      this.leftToe2 = this.leftFeet2.m_171324_("leftToe2");
      this.leftToe3 = this.leftFeet2.m_171324_("leftToe3");
      this.leftToe4 = this.leftFeet2.m_171324_("leftToe4");
      this.rightLeg2 = this.f_170852_.m_171324_("rightLeg2");
      this.rightFeet = this.rightLeg2.m_171324_("rightFeet");
      this.rightFeet2 = this.rightFeet.m_171324_("rightFeet2");
      this.rightToe1 = this.rightFeet2.m_171324_("rightToe1");
      this.rightToe2 = this.rightFeet2.m_171324_("rightToe2");
      this.rightToe3 = this.rightFeet2.m_171324_("rightToe3");
      this.rightToe4 = this.rightFeet2.m_171324_("rightToe4");
      this.rightArm2 = this.f_170854_.m_171324_("rightArm2");
      this.rightHand = this.rightArm2.m_171324_("rightHand");
      this.rightFinger1 = this.rightHand.m_171324_("rightFinger1");
      this.rightFinger2 = this.rightHand.m_171324_("rightFinger2");
      this.rightFinger3 = this.rightHand.m_171324_("rightFinger3");
      this.leftArm2 = this.f_170855_.m_171324_("leftArm2");
      this.leftHand = this.leftArm2.m_171324_("leftHand");
      this.leftFinger1 = this.leftHand.m_171324_("leftFinger1");
      this.leftFinger2 = this.leftHand.m_171324_("leftFinger2");
      this.leftFinger3 = this.leftHand.m_171324_("leftFinger3");
      this.neck3 = this.f_103492_.m_171324_("neck3");
      this.neck2 = this.f_103492_.m_171324_("neck2");
      this.neck1 = this.f_103492_.m_171324_("neck1");
      this.upperHead = this.f_103492_.m_171324_("upperHead");
      this.upperTeeth = this.upperHead.m_171324_("upperTeeth");
      this.lowerHead = this.upperHead.m_171324_("lowerHead");
      this.lowerTeeth = this.lowerHead.m_171324_("lowerTeeth");
      this.tail = root.m_171324_("tail");
      this.tail2 = this.tail.m_171324_("tail2");
      this.tail3 = this.tail2.m_171324_("tail3");
      this.tail4 = this.tail3.m_171324_("tail4");
      this.tail5 = this.tail4.m_171324_("tail5");
      this.tail6 = this.tail5.m_171324_("tail6");
      this.tail7 = this.tail6.m_171324_("tail7");
      this.tail8 = this.tail7.m_171324_("tail8");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = QuadrupedModel.m_170864_(0, CubeDeformation.f_171458_);
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition leftHindLeg = partdefinition.m_171599_("left_hind_leg", CubeListBuilder.m_171558_().m_171514_(52, 80).m_171488_(0.0F, -12.0F, -7.0F, 7.0F, 24.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(6.5F, -10.564F, 16.0191F, 0.2182F, 0.0F, 0.0F));
      PartDefinition leftLeg2 = leftHindLeg.m_171599_("leftLeg2", CubeListBuilder.m_171558_().m_171514_(46, 120).m_171488_(0.5F, -4.6299F, 0.6175F, 6.0F, 27.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 9.4613F, -5.3088F, -0.6981F, 0.0F, 0.0F));
      PartDefinition leftFeet = leftLeg2.m_171599_("leftFeet", CubeListBuilder.m_171558_().m_171514_(95, 92).m_171488_(-1.0F, -2.5F, -5.0F, 9.0F, 5.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 21.6027F, 5.2897F, 0.48F, 0.0F, 0.0F));
      PartDefinition leftFeet2 = leftFeet.m_171599_("leftFeet2", CubeListBuilder.m_171558_().m_171514_(94, 109).m_171488_(-1.5F, -2.0F, -5.0F, 10.0F, 4.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 0.5F, -3.0F));
      leftFeet2.m_171599_("leftToe1", CubeListBuilder.m_171558_().m_171514_(104, 125).m_171488_(1.8812F, -1.6739F, -3.3871F, 3.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(3.8689F, 1.01F, -6.0793F, 0.2075F, -0.2612F, -0.0036F));
      leftFeet2.m_171599_("leftToe2", CubeListBuilder.m_171558_().m_171514_(104, 125).m_171488_(2.0F, -1.5F, -2.5F, 3.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 0.9569F, -6.7225F, 0.1745F, 0.0F, 0.0F));
      leftFeet2.m_171599_("leftToe3", CubeListBuilder.m_171558_().m_171514_(104, 125).m_171488_(1.9468F, -1.3945F, -2.4015F, 3.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-3.7618F, 0.7739F, -5.8025F, 0.2188F, 0.1744F, 0.0077F));
      leftFeet2.m_171599_("leftToe4", CubeListBuilder.m_171558_().m_171514_(104, 125).m_171488_(2.0F, -1.5F, -1.5F, 3.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, -2.4597F, 7.6541F, -0.5672F, 0.0F, 0.0F));
      PartDefinition rightHindLeg = partdefinition.m_171599_("right_hind_leg", CubeListBuilder.m_171558_().m_171514_(52, 80).m_171488_(0.0F, -12.0F, -7.0F, 7.0F, 24.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-13.5F, -10.564F, 16.0191F, 0.2182F, 0.0F, 0.0F));
      PartDefinition rightLeg2 = rightHindLeg.m_171599_("rightLeg2", CubeListBuilder.m_171558_().m_171514_(46, 120).m_171488_(0.5F, -4.6299F, 0.6175F, 6.0F, 27.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 9.4613F, -5.3088F, -0.6981F, 0.0F, 0.0F));
      PartDefinition rightFeet = rightLeg2.m_171599_("rightFeet", CubeListBuilder.m_171558_().m_171514_(95, 92).m_171488_(-1.0F, -2.5F, -5.0F, 9.0F, 5.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 21.6027F, 5.2897F, 0.48F, 0.0F, 0.0F));
      PartDefinition rightFeet2 = rightFeet.m_171599_("rightFeet2", CubeListBuilder.m_171558_().m_171514_(94, 109).m_171488_(-1.5F, -2.0F, -5.0F, 10.0F, 4.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 0.5F, -3.0F));
      rightFeet2.m_171599_("rightToe1", CubeListBuilder.m_171558_().m_171514_(104, 125).m_171488_(1.8812F, -1.6739F, -3.3871F, 3.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(3.8689F, 1.01F, -6.0793F, 0.2075F, -0.2612F, -0.0036F));
      rightFeet2.m_171599_("rightToe2", CubeListBuilder.m_171558_().m_171514_(104, 125).m_171488_(2.0F, -1.5F, -2.5F, 3.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 0.9569F, -6.7225F, 0.1745F, 0.0F, 0.0F));
      rightFeet2.m_171599_("rightToe3", CubeListBuilder.m_171558_().m_171514_(104, 125).m_171488_(1.9468F, -1.3945F, -2.4015F, 3.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-3.7618F, 0.7739F, -5.8025F, 0.2188F, 0.1744F, 0.0077F));
      rightFeet2.m_171599_("rightToe4", CubeListBuilder.m_171558_().m_171514_(104, 125).m_171488_(2.0F, -1.5F, -1.5F, 3.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, -2.4597F, 7.6541F, -0.5672F, 0.0F, 0.0F));
      PartDefinition rightFrontLeg = partdefinition.m_171599_("right_front_leg", CubeListBuilder.m_171558_().m_171514_(68, 169).m_171488_(0.5F, -2.5559F, -3.1355F, 6.0F, 11.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-13.0F, -7.0F, -16.0F, 0.3054F, 0.0F, 0.0F));
      PartDefinition rightArm2 = rightFrontLeg.m_171599_("rightArm2", CubeListBuilder.m_171558_().m_171514_(69, 187).m_171488_(1.0F, -2.6022F, -3.2765F, 5.0F, 11.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 7.4026F, -1.1698F, -1.309F, 0.0F, 0.0F));
      PartDefinition rightHand = rightArm2.m_171599_("rightHand", CubeListBuilder.m_171558_().m_171514_(69, 205).m_171488_(0.0F, -2.5F, -1.5F, 7.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 9.3131F, -0.4006F, 0.6545F, 0.0F, 0.0F));
      rightHand.m_171599_("rightFinger1", CubeListBuilder.m_171558_().m_171514_(70, 215).m_171488_(2.5F, -1.0F, -2.5F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(2.25F, 4.4447F, 0.6321F, -1.2217F, 0.0F, 0.0F));
      rightHand.m_171599_("rightFinger2", CubeListBuilder.m_171558_().m_171514_(70, 215).m_171488_(2.5F, -1.0F, -2.5F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 4.5397F, 0.4151F, -1.2217F, 0.0F, 0.0F));
      rightHand.m_171599_("rightFinger3", CubeListBuilder.m_171558_().m_171514_(70, 215).m_171488_(2.5F, -1.0F, -2.5F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-2.25F, 4.3628F, 0.7644F, -1.2217F, 0.0F, 0.0F));
      PartDefinition leftFrontLeg = partdefinition.m_171599_("left_front_leg", CubeListBuilder.m_171558_().m_171514_(68, 169).m_171488_(0.5F, -2.5559F, -3.1355F, 6.0F, 11.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(6.0F, -7.0F, -16.0F, 0.3054F, 0.0F, 0.0F));
      PartDefinition leftArm2 = leftFrontLeg.m_171599_("leftArm2", CubeListBuilder.m_171558_().m_171514_(69, 187).m_171488_(1.0F, -2.6022F, -3.2765F, 5.0F, 11.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 7.4026F, -1.1698F, -1.309F, 0.0F, 0.0F));
      PartDefinition leftHand = leftArm2.m_171599_("leftHand", CubeListBuilder.m_171558_().m_171514_(69, 205).m_171488_(0.0F, -2.5F, -1.5F, 7.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 9.3131F, -0.4006F, 0.6545F, 0.0F, 0.0F));
      leftHand.m_171599_("leftFinger1", CubeListBuilder.m_171558_().m_171514_(70, 215).m_171488_(2.5F, -1.0F, -2.5F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(2.25F, 4.4447F, 0.6321F, -1.2217F, 0.0F, 0.0F));
      leftHand.m_171599_("leftFinger2", CubeListBuilder.m_171558_().m_171514_(70, 215).m_171488_(2.5F, -1.0F, -2.5F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 4.5397F, 0.4151F, -1.2217F, 0.0F, 0.0F));
      leftHand.m_171599_("leftFinger3", CubeListBuilder.m_171558_().m_171514_(70, 215).m_171488_(2.5F, -1.0F, -2.5F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-2.25F, 4.3628F, 0.7644F, -1.2217F, 0.0F, 0.0F));
      partdefinition.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-7.0F, -13.0714F, -10.7857F, 21.0F, 27.0F, 16.0F, new CubeDeformation(0.0F)).m_171514_(1, 156).m_171488_(-6.0F, -12.0714F, -16.7857F, 19.0F, 24.0F, 6.0F, new CubeDeformation(0.0F)).m_171514_(3, 188).m_171488_(-5.0F, -11.0714F, -21.7857F, 17.0F, 21.0F, 5.0F, new CubeDeformation(0.0F)).m_171514_(8, 217).m_171488_(-3.5F, -10.0714F, -23.7857F, 14.0F, 18.0F, 2.0F, new CubeDeformation(0.0F)).m_171514_(0, 43).m_171488_(-6.0F, -12.0714F, 5.2143F, 19.0F, 25.0F, 12.0F, new CubeDeformation(0.0F)).m_171514_(0, 81).m_171488_(-5.5F, -11.5714F, 17.2143F, 18.0F, 24.0F, 7.0F, new CubeDeformation(0.0F)).m_171514_(0, 115).m_171488_(-4.5F, -10.5714F, 24.2143F, 16.0F, 22.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(-3.5F, -14.9286F, -4.2143F));
      PartDefinition head = partdefinition.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(75, 146).m_171488_(-3.5F, -14.1985F, -23.0413F, 14.0F, 15.0F, 5.0F, new CubeDeformation(0.03F)), PartPose.m_171419_(-3.5F, -19.3015F, -23.9587F));
      head.m_171599_("neck3", CubeListBuilder.m_171558_().m_171514_(106, 52).m_171488_(-6.5F, -3.5F, -7.0F, 13.0F, 7.0F, 14.0F, new CubeDeformation(0.02F)), PartPose.m_171423_(3.5F, -6.3693F, -16.9473F, 1.3963F, 0.0F, 0.0F));
      head.m_171599_("neck2", CubeListBuilder.m_171558_().m_171514_(74, 0).m_171488_(-6.5F, -4.5F, -7.0F, 13.0F, 9.0F, 14.0F, new CubeDeformation(0.01F)), PartPose.m_171423_(3.5F, -4.5383F, -10.4505F, 1.2217F, 0.0F, 0.0F));
      head.m_171599_("neck1", CubeListBuilder.m_171558_().m_171514_(62, 29).m_171488_(-6.5F, -9.5F, -7.0F, 13.0F, 19.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(3.5F, 1.606F, -0.0608F, 0.9599F, 0.0F, 0.0F));
      PartDefinition upperHead = head.m_171599_("upperHead", CubeListBuilder.m_171558_().m_171514_(117, 149).m_171488_(-3.5F, -5.1947F, -6.7478F, 14.0F, 12.0F, 7.0F, new CubeDeformation(0.0F)).m_171514_(116, 173).m_171488_(-2.5F, -4.1947F, -12.7478F, 12.0F, 11.0F, 6.0F, new CubeDeformation(0.0F)).m_171514_(117, 194).m_171488_(-1.5F, -3.1947F, -20.7478F, 10.0F, 10.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, -9.0255F, -22.121F, 0.1309F, 0.0F, 0.0F));
      upperHead.m_171599_("upperTeeth", CubeListBuilder.m_171558_().m_171514_(102, 20).m_171488_(7.5F, -1.5F, -8.7143F, 0.0F, 3.0F, 20.0F, new CubeDeformation(0.0F)).m_171514_(102, 17).m_171488_(-0.5F, -1.5F, -8.7143F, 0.0F, 3.0F, 20.0F, new CubeDeformation(0.0F)).m_171514_(58, 10).m_171488_(0.0F, -1.5F, -8.7143F, 7.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 7.8053F, -11.0335F));
      PartDefinition lowerHead = upperHead.m_171599_("lowerHead", CubeListBuilder.m_171558_().m_171514_(92, 241).m_171488_(-0.5F, -0.7355F, -21.9123F, 8.0F, 3.0F, 9.0F, new CubeDeformation(0.0F)).m_171514_(94, 230).m_171488_(-1.5F, -0.7355F, -12.9123F, 10.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)).m_171514_(93, 219).m_171488_(-2.5F, -0.7355F, -6.9123F, 12.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 7.0898F, 2.1358F, 0.2182F, 0.0F, 0.0F));
      lowerHead.m_171599_("lowerTeeth", CubeListBuilder.m_171558_().m_171514_(102, 14).m_171488_(7.25F, -1.5F, -8.7143F, 0.0F, 3.0F, 20.0F, new CubeDeformation(0.0F)).m_171514_(102, 11).m_171488_(-0.25F, -1.5F, -8.7143F, 0.0F, 3.0F, 20.0F, new CubeDeformation(0.0F)).m_171514_(58, 13).m_171488_(0.0F, -1.5F, -8.7143F, 7.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, -1.7355F, -12.198F));
      PartDefinition tail = partdefinition.m_171599_("tail", CubeListBuilder.m_171558_().m_171514_(166, 4).m_171488_(-3.5F, -7.5F, -37.625F, 14.0F, 15.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(-3.5F, -15.5F, 62.625F));
      PartDefinition tail2 = tail.m_171599_("tail2", CubeListBuilder.m_171558_().m_171514_(167, 26).m_171488_(-2.0F, -6.0F, 0.375F, 11.0F, 12.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 0.0F, -33.0F));
      PartDefinition tail3 = tail2.m_171599_("tail3", CubeListBuilder.m_171558_().m_171514_(165, 47).m_171488_(-1.5F, -5.0F, -0.625F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 0.0F, 7.0F));
      PartDefinition tail4 = tail3.m_171599_("tail4", CubeListBuilder.m_171558_().m_171514_(165, 69).m_171488_(-1.0F, -4.5F, -0.125F, 9.0F, 9.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 0.0F, 9.0F));
      PartDefinition tail5 = tail4.m_171599_("tail5", CubeListBuilder.m_171558_().m_171514_(160, 93).m_171488_(-0.5F, -4.0F, -0.125F, 8.0F, 8.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 0.0F, 11.0F));
      PartDefinition tail6 = tail5.m_171599_("tail6", CubeListBuilder.m_171558_().m_171514_(161, 119).m_171488_(0.0F, -3.5F, -0.625F, 7.0F, 7.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 0.0F, 16.0F));
      PartDefinition tail7 = tail6.m_171599_("tail7", CubeListBuilder.m_171558_().m_171514_(163, 144).m_171488_(0.5F, -3.0F, 0.375F, 6.0F, 6.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 0.0F, 14.0F));
      tail7.m_171599_("tail8", CubeListBuilder.m_171558_().m_171514_(161, 169).m_171488_(1.0F, -2.5F, 0.375F, 5.0F, 5.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 0.0F, 15.0F));
      return LayerDefinition.m_171565_(meshdefinition, 256, 256);
   }

   public void setupAnim(LivingEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      RendererHelper.resetModelToDefaultPivots(this);
      float limbSpeed = 0.3F;
      if (entity.m_20142_()) {
         limbSpeed = 0.5F;
      }

      this.f_170852_.f_104203_ = 0.2F + Mth.m_14089_(limbSwing * limbSpeed) * 0.5F * limbSwingAmount;
      this.rightLeg2.f_104203_ = -0.7F - Mth.m_14031_(limbSwing * limbSpeed) * 0.5F * limbSwingAmount;
      this.f_170853_.f_104203_ = 0.2F + Mth.m_14089_(limbSwing * limbSpeed + (float)Math.PI) * 0.5F * limbSwingAmount;
      this.leftLeg2.f_104203_ = -0.7F - Mth.m_14031_(limbSwing * limbSpeed + (float)Math.PI) * 0.5F * limbSwingAmount;
      if (entity.m_20142_()) {
         this.tail3.f_104203_ = Mth.m_14089_(limbSwing * 0.6F) * 0.1F * limbSwingAmount;
         this.tail5.f_104204_ = (float)Math.sin((double)ageInTicks * 0.06) / 20.0F;
         this.tail5.f_104203_ = (float)Math.sin((double)ageInTicks * 0.02) / 10.0F;
         this.tail6.f_104204_ = (float)Math.sin((double)ageInTicks * 0.06) / 20.0F;
         this.tail6.f_104203_ = (float)Math.sin((double)ageInTicks * 0.02) / 10.0F;
         this.tail7.f_104204_ = (float)Math.sin((double)ageInTicks * 0.06) / 20.0F;
         this.tail7.f_104203_ = (float)Math.sin((double)ageInTicks * 0.02) / 10.0F;
         this.tail8.f_104204_ = (float)Math.sin((double)ageInTicks * 0.06) / 20.0F;
         this.tail8.f_104203_ = (float)Math.sin((double)ageInTicks * 0.02) / 10.0F;
      } else {
         this.tail2.f_104204_ = (float)Math.sin((double)ageInTicks * 0.01) / 20.0F;
         this.tail2.f_104203_ = (float)Math.sin((double)ageInTicks * 0.005) / 10.0F;
         this.tail3.f_104204_ = (float)Math.sin((double)ageInTicks * 0.01) / 20.0F;
         this.tail3.f_104203_ = (float)Math.sin((double)ageInTicks * 0.005) / 10.0F;
         this.tail4.f_104204_ = (float)Math.sin((double)ageInTicks * 0.01) / 20.0F;
         this.tail4.f_104203_ = (float)Math.sin((double)ageInTicks * 0.005) / 10.0F;
         this.tail5.f_104204_ = (float)Math.sin((double)ageInTicks * 0.06) / 20.0F;
         this.tail5.f_104203_ = (float)Math.sin((double)ageInTicks * 0.02) / 10.0F;
         this.tail6.f_104204_ = (float)Math.sin((double)ageInTicks * 0.06) / 20.0F;
         this.tail6.f_104203_ = (float)Math.sin((double)ageInTicks * 0.02) / 10.0F;
         this.tail7.f_104204_ = (float)Math.sin((double)ageInTicks * 0.06) / 20.0F;
         this.tail7.f_104203_ = (float)Math.sin((double)ageInTicks * 0.02) / 10.0F;
         this.tail8.f_104204_ = (float)Math.sin((double)ageInTicks * 0.06) / 20.0F;
         this.tail8.f_104203_ = (float)Math.sin((double)ageInTicks * 0.02) / 10.0F;
      }

      this.f_102608_ = entity.f_20921_;
      if (this.f_102608_ > 0.0F) {
         ModelPart var10000 = this.f_103492_;
         var10000.f_104204_ += this.f_103493_.f_104204_;
         float f1 = 1.0F - this.f_102608_;
         f1 *= f1;
         f1 *= f1;
         f1 = 1.0F - f1;
         float f2 = Mth.m_14031_(f1 * (float)Math.PI);
         float f3 = Mth.m_14031_(this.f_102608_ * (float)Math.PI) * -(this.f_103492_.f_104203_ - 0.1F) * 0.15F;
         this.f_103492_.f_104203_ = (float)((double)this.f_103492_.f_104203_ + (double)f2 * 0.8 + (double)f3);
         var10000 = this.f_103492_;
         var10000.f_104205_ -= Mth.m_14031_(this.f_102608_ * (float)Math.PI) * -0.8F;
      }

   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      super.m_7695_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.tail.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }

   public void m_6002_(HumanoidArm side, PoseStack poseStack) {
      this.f_103492_.m_104299_(poseStack);
      poseStack.m_85841_(2.0F, 2.0F, 2.0F);
      poseStack.m_252781_(Axis.f_252403_.m_252977_(90.0F));
      poseStack.m_252781_(Axis.f_252529_.m_252977_(260.0F));
      poseStack.m_85837_(0.05, (double)0.5F, 0.1);
   }

   public ModelPart m_5585_() {
      return this.f_103492_;
   }
}
