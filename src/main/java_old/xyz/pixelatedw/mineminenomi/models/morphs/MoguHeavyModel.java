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

public class MoguHeavyModel<T extends LivingEntity> extends HumanoidMorphModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "mogu_heavy"), "main");
   private final ModelPart leftEye;
   private final ModelPart rightEye;
   private final ModelPart nose;
   private final ModelPart nose2;
   private final ModelPart nose3;
   private final ModelPart lowerLeftArm;
   private final ModelPart leftArmClaw1;
   private final ModelPart leftArmClaw1b;
   private final ModelPart leftArmClaw1c;
   private final ModelPart leftArmClaw2;
   private final ModelPart leftArmClaw2b;
   private final ModelPart leftArmClaw2c;
   private final ModelPart leftArmClaw3;
   private final ModelPart leftArmClaw3b;
   private final ModelPart leftArmClaw3c;
   private final ModelPart leftArmClaw4;
   private final ModelPart leftArmClaw4b;
   private final ModelPart leftArmClaw4c;
   private final ModelPart lowerRightArm;
   private final ModelPart rightArmClaw1;
   private final ModelPart rightArmClaw1b;
   private final ModelPart rightArmClaw1c;
   private final ModelPart rightArmClaw2;
   private final ModelPart rightArmClaw2b;
   private final ModelPart rightArmClaw2c;
   private final ModelPart rightArmClaw3;
   private final ModelPart rightArmClaw3b;
   private final ModelPart rightArmClaw3c;
   private final ModelPart rightArmClaw4;
   private final ModelPart rightArmClaw4b;
   private final ModelPart rightArmClaw4c;
   private final ModelPart lowerLeftLeg;
   private final ModelPart leftFoot;
   private final ModelPart lowerRightLeg;
   private final ModelPart rightFoot;

   public MoguHeavyModel(ModelPart root) {
      super(root);
      this.leftEye = this.f_102808_.m_171324_("leftEye");
      this.rightEye = this.f_102808_.m_171324_("rightEye");
      this.nose = this.f_102808_.m_171324_("nose");
      this.nose2 = this.nose.m_171324_("nose2");
      this.nose3 = this.nose2.m_171324_("nose3");
      this.lowerLeftArm = this.f_102812_.m_171324_("lowerLeftArm");
      this.leftArmClaw1 = this.lowerLeftArm.m_171324_("leftArmClaw1");
      this.leftArmClaw1b = this.leftArmClaw1.m_171324_("leftArmClaw1b");
      this.leftArmClaw1c = this.leftArmClaw1b.m_171324_("leftArmClaw1c");
      this.leftArmClaw2 = this.lowerLeftArm.m_171324_("leftArmClaw2");
      this.leftArmClaw2b = this.leftArmClaw2.m_171324_("leftArmClaw2b");
      this.leftArmClaw2c = this.leftArmClaw2b.m_171324_("leftArmClaw2c");
      this.leftArmClaw3 = this.lowerLeftArm.m_171324_("leftArmClaw3");
      this.leftArmClaw3b = this.leftArmClaw3.m_171324_("leftArmClaw3b");
      this.leftArmClaw3c = this.leftArmClaw3b.m_171324_("leftArmClaw3c");
      this.leftArmClaw4 = this.lowerLeftArm.m_171324_("leftArmClaw4");
      this.leftArmClaw4b = this.leftArmClaw4.m_171324_("leftArmClaw4b");
      this.leftArmClaw4c = this.leftArmClaw4b.m_171324_("leftArmClaw4c");
      this.lowerRightArm = this.f_102811_.m_171324_("lowerRightArm");
      this.rightArmClaw1 = this.lowerRightArm.m_171324_("rightArmClaw1");
      this.rightArmClaw1b = this.rightArmClaw1.m_171324_("rightArmClaw1b");
      this.rightArmClaw1c = this.rightArmClaw1b.m_171324_("rightArmClaw1c");
      this.rightArmClaw2 = this.lowerRightArm.m_171324_("rightArmClaw2");
      this.rightArmClaw2b = this.rightArmClaw2.m_171324_("rightArmClaw2b");
      this.rightArmClaw2c = this.rightArmClaw2b.m_171324_("rightArmClaw2c");
      this.rightArmClaw3 = this.lowerRightArm.m_171324_("rightArmClaw3");
      this.rightArmClaw3b = this.rightArmClaw3.m_171324_("rightArmClaw3b");
      this.rightArmClaw3c = this.rightArmClaw3b.m_171324_("rightArmClaw3c");
      this.rightArmClaw4 = this.lowerRightArm.m_171324_("rightArmClaw4");
      this.rightArmClaw4b = this.rightArmClaw4.m_171324_("rightArmClaw4b");
      this.rightArmClaw4c = this.rightArmClaw4b.m_171324_("rightArmClaw4c");
      this.lowerLeftLeg = this.f_102814_.m_171324_("lowerLeftLeg");
      this.leftFoot = this.lowerLeftLeg.m_171324_("leftFoot");
      this.lowerRightLeg = this.f_102813_.m_171324_("lowerRightLeg");
      this.rightFoot = this.lowerRightLeg.m_171324_("rightFoot");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = HumanoidMorphModel.m_170681_(CubeDeformation.f_171458_, 0.0F);
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition head = partdefinition.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(2, 3).m_171488_(-2.375F, -4.9965F, -2.1219F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(-0.25F, 6.8715F, 0.2469F));
      head.m_171599_("leftEye", CubeListBuilder.m_171558_().m_171514_(18, 5).m_171488_(0.5625F, -9.1875F, -3.9375F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0625F, 5.191F, 1.7907F));
      head.m_171599_("rightEye", CubeListBuilder.m_171558_().m_171514_(18, 5).m_171480_().m_171488_(-2.5625F, -9.1875F, -3.9375F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171419_(0.1875F, 5.191F, 1.7907F));
      PartDefinition nose = head.m_171599_("nose", CubeListBuilder.m_171558_().m_171514_(0, 5).m_171488_(-1.0F, -0.661F, -0.0027F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.2F)), PartPose.m_171423_(0.125F, -1.4029F, -2.795F, -0.0873F, 0.0F, 0.0F));
      PartDefinition nose2 = nose.m_171599_("nose2", CubeListBuilder.m_171558_().m_171514_(0, 2).m_171488_(-1.0F, -0.6651F, 0.6625F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.05F)), PartPose.m_171423_(0.0F, -0.1314F, -1.4981F, -0.0873F, 0.0F, 0.0F));
      nose2.m_171599_("nose3", CubeListBuilder.m_171558_().m_171514_(1, 0).m_171488_(-0.5F, -1.0436F, -0.2019F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.m_171423_(0.0F, -0.0966F, 0.3144F, -0.0873F, 0.0F, 0.0F));
      PartDefinition leftArm = partdefinition.m_171599_("left_arm", CubeListBuilder.m_171558_().m_171514_(24, 34).m_171480_().m_171488_(-0.75F, -1.5F, -2.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(4.6326F, 7.5362F, 2.2948F, 0.0516F, 0.0091F, -0.1743F));
      PartDefinition lowerLeftArm = leftArm.m_171599_("lowerLeftArm", CubeListBuilder.m_171558_().m_171514_(23, 48).m_171480_().m_171488_(-1.9375F, -0.0214F, -2.1737F, 4.0F, 5.0F, 5.0F, new CubeDeformation(0.2F)).m_171555_(false), PartPose.m_171423_(1.2424F, 6.3714F, -0.2691F, -0.1309F, 0.0F, 0.0F));
      PartDefinition leftArmClaw1 = lowerLeftArm.m_171599_("leftArmClaw1", CubeListBuilder.m_171558_().m_171514_(18, 45).m_171480_().m_171488_(-0.5F, 0.0F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.2F)).m_171555_(false), PartPose.m_171423_(-0.7474F, 3.7877F, -2.1621F, -0.3054F, 0.0F, 0.0F));
      PartDefinition leftArmClaw1b = leftArmClaw1.m_171599_("leftArmClaw1b", CubeListBuilder.m_171558_().m_171514_(18, 41).m_171488_(-0.5507F, -0.076F, -0.5417F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.m_171423_(0.0248F, 2.9761F, 0.0163F, 0.2618F, 0.0F, 0.0F));
      leftArmClaw1b.m_171599_("leftArmClaw1c", CubeListBuilder.m_171558_().m_171514_(18, 37).m_171488_(-0.5507F, -0.5745F, -0.5325F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-0.0162F, 1.9507F, -0.0258F, 0.1309F, 0.0F, 0.0F));
      PartDefinition leftArmClaw2 = lowerLeftArm.m_171599_("leftArmClaw2", CubeListBuilder.m_171558_().m_171514_(18, 45).m_171480_().m_171488_(-0.3839F, -0.718F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.2F)).m_171555_(false), PartPose.m_171423_(1.895F, 4.3238F, -1.5287F, 0.0F, 0.0F, -0.1743F));
      PartDefinition leftArmClaw2b = leftArmClaw2.m_171599_("leftArmClaw2b", CubeListBuilder.m_171558_().m_171514_(18, 41).m_171480_().m_171488_(-0.4494F, 0.0065F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.1F)).m_171555_(false), PartPose.m_171423_(0.1062F, 2.3638F, -0.0249F, 0.0F, 0.0F, 0.1745F));
      leftArmClaw2b.m_171599_("leftArmClaw2c", CubeListBuilder.m_171558_().m_171514_(18, 37).m_171480_().m_171488_(-0.4597F, -0.3657F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(0.0026F, 2.0696F, -0.0204F, 0.0F, 0.0F, 0.2182F));
      PartDefinition leftArmClaw3 = lowerLeftArm.m_171599_("leftArmClaw3", CubeListBuilder.m_171558_().m_171514_(18, 45).m_171480_().m_171488_(-0.3839F, -0.718F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.2F)).m_171555_(false), PartPose.m_171423_(1.895F, 4.3238F, 0.2838F, 0.0F, 0.0F, -0.1743F));
      PartDefinition leftArmClaw3b = leftArmClaw3.m_171599_("leftArmClaw3b", CubeListBuilder.m_171558_().m_171514_(18, 41).m_171480_().m_171488_(-0.4494F, 0.0065F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.1F)).m_171555_(false), PartPose.m_171423_(0.1062F, 2.3638F, -0.0249F, 0.0F, 0.0F, 0.1745F));
      leftArmClaw3b.m_171599_("leftArmClaw3c", CubeListBuilder.m_171558_().m_171514_(18, 37).m_171480_().m_171488_(-0.4597F, -0.2407F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(0.0026F, 2.0696F, -0.0204F, 0.0F, 0.0F, 0.2182F));
      PartDefinition leftArmClaw4 = lowerLeftArm.m_171599_("leftArmClaw4", CubeListBuilder.m_171558_().m_171514_(18, 45).m_171480_().m_171488_(-0.3839F, -0.718F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.2F)).m_171555_(false), PartPose.m_171423_(1.895F, 4.3238F, 2.1588F, 0.0F, 0.0F, -0.1743F));
      PartDefinition leftArmClaw4b = leftArmClaw4.m_171599_("leftArmClaw4b", CubeListBuilder.m_171558_().m_171514_(18, 41).m_171480_().m_171488_(-0.4494F, 0.0065F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.1F)).m_171555_(false), PartPose.m_171423_(0.1062F, 2.3638F, -0.0249F, 0.0F, 0.0F, 0.1745F));
      leftArmClaw4b.m_171599_("leftArmClaw4c", CubeListBuilder.m_171558_().m_171514_(18, 37).m_171480_().m_171488_(-0.4597F, -0.4907F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(0.0026F, 2.0696F, -0.0204F, 0.0F, 0.0F, 0.2182F));
      PartDefinition rightArm = partdefinition.m_171599_("right_arm", CubeListBuilder.m_171558_().m_171514_(24, 34).m_171488_(-3.25F, -1.5F, -2.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-4.6326F, 7.5362F, 2.2948F, 0.0516F, -0.0091F, 0.1743F));
      PartDefinition lowerRightArm = rightArm.m_171599_("lowerRightArm", CubeListBuilder.m_171558_().m_171514_(23, 48).m_171488_(-2.0625F, -0.0214F, -2.1737F, 4.0F, 5.0F, 5.0F, new CubeDeformation(0.2F)), PartPose.m_171423_(-1.2424F, 6.3714F, -0.2691F, -0.1309F, 0.0F, 0.0F));
      PartDefinition rightArmClaw1 = lowerRightArm.m_171599_("rightArmClaw1", CubeListBuilder.m_171558_().m_171514_(18, 45).m_171488_(-0.5F, 0.0F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.2F)), PartPose.m_171423_(0.7474F, 3.7877F, -2.1621F, -0.3054F, 0.0F, 0.0F));
      PartDefinition rightArmClaw1b = rightArmClaw1.m_171599_("rightArmClaw1b", CubeListBuilder.m_171558_().m_171514_(18, 41).m_171480_().m_171488_(-0.4493F, -0.076F, -0.5417F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.1F)).m_171555_(false), PartPose.m_171423_(-0.0248F, 2.9761F, 0.0163F, 0.2618F, 0.0F, 0.0F));
      rightArmClaw1b.m_171599_("rightArmClaw1c", CubeListBuilder.m_171558_().m_171514_(18, 37).m_171480_().m_171488_(-0.4493F, -0.5745F, -0.5325F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(0.0162F, 1.9507F, -0.0258F, 0.1309F, 0.0F, 0.0F));
      PartDefinition rightArmClaw2 = lowerRightArm.m_171599_("rightArmClaw2", CubeListBuilder.m_171558_().m_171514_(18, 45).m_171488_(-0.6161F, -0.718F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.2F)), PartPose.m_171423_(-1.895F, 4.3238F, -1.5287F, 0.0F, 0.0F, 0.1743F));
      PartDefinition rightArmClaw2b = rightArmClaw2.m_171599_("rightArmClaw2b", CubeListBuilder.m_171558_().m_171514_(18, 41).m_171488_(-0.5505F, 0.0065F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.m_171423_(-0.1062F, 2.3638F, -0.0249F, 0.0F, 0.0F, -0.1745F));
      rightArmClaw2b.m_171599_("rightArmClaw2c", CubeListBuilder.m_171558_().m_171514_(18, 37).m_171488_(-0.5403F, -0.3657F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-0.0026F, 2.0696F, -0.0204F, 0.0F, 0.0F, -0.2182F));
      PartDefinition rightArmClaw3 = lowerRightArm.m_171599_("rightArmClaw3", CubeListBuilder.m_171558_().m_171514_(18, 45).m_171488_(-0.6161F, -0.718F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.2F)), PartPose.m_171423_(-1.895F, 4.3238F, 0.2838F, 0.0F, 0.0F, 0.1743F));
      PartDefinition rightArmClaw3b = rightArmClaw3.m_171599_("rightArmClaw3b", CubeListBuilder.m_171558_().m_171514_(18, 41).m_171488_(-0.5505F, 0.0065F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.m_171423_(-0.1062F, 2.3638F, -0.0249F, 0.0F, 0.0F, -0.1745F));
      rightArmClaw3b.m_171599_("rightArmClaw3c", CubeListBuilder.m_171558_().m_171514_(18, 37).m_171488_(-0.5403F, -0.2407F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-0.0026F, 2.0696F, -0.0204F, 0.0F, 0.0F, -0.2182F));
      PartDefinition rightArmClaw4 = lowerRightArm.m_171599_("rightArmClaw4", CubeListBuilder.m_171558_().m_171514_(18, 45).m_171488_(-0.6161F, -0.718F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.2F)), PartPose.m_171423_(-1.895F, 4.3238F, 2.1588F, 0.0F, 0.0F, 0.1743F));
      PartDefinition rightArmClaw4b = rightArmClaw4.m_171599_("rightArmClaw4b", CubeListBuilder.m_171558_().m_171514_(18, 41).m_171488_(-0.5505F, 0.0065F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.m_171423_(-0.1062F, 2.3638F, -0.0249F, 0.0F, 0.0F, -0.1745F));
      rightArmClaw4b.m_171599_("rightArmClaw4c", CubeListBuilder.m_171558_().m_171514_(18, 37).m_171488_(-0.5403F, -0.4907F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-0.0026F, 2.0696F, -0.0204F, 0.0F, 0.0F, -0.2182F));
      partdefinition.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(0, 28).m_171488_(-4.0F, -15.0F, -2.375F, 8.0F, 3.0F, 5.0F, new CubeDeformation(0.1F)).m_171514_(0, 14).m_171488_(-4.4375F, -21.0F, -2.9375F, 9.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 27.5625F, 2.0F));
      PartDefinition leftleg = partdefinition.m_171599_("left_leg", CubeListBuilder.m_171558_().m_171514_(0, 39).m_171488_(-2.0F, -0.5F, -2.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.1F)), PartPose.m_171419_(2.65F, 15.5F, 2.0F));
      PartDefinition lowerLeftLeg = leftleg.m_171599_("lowerLeftLeg", CubeListBuilder.m_171558_().m_171514_(2, 49).m_171488_(-1.5F, -2.5F, -1.5F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.2F)), PartPose.m_171419_(0.0F, 4.5625F, 0.0F));
      lowerLeftLeg.m_171599_("leftFoot", CubeListBuilder.m_171558_().m_171514_(0, 58).m_171488_(-1.5F, -0.5F, -3.5F, 3.0F, 1.0F, 5.0F, new CubeDeformation(0.4F)), PartPose.m_171419_(0.0F, 3.0F, 0.0F));
      PartDefinition rightleg = partdefinition.m_171599_("right_leg", CubeListBuilder.m_171558_().m_171514_(0, 39).m_171480_().m_171488_(-2.0F, -0.5F, -2.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.1F)).m_171555_(false), PartPose.m_171419_(-2.65F, 15.5F, 2.0F));
      PartDefinition lowerRightLeg = rightleg.m_171599_("lowerRightLeg", CubeListBuilder.m_171558_().m_171514_(2, 49).m_171480_().m_171488_(-1.5F, -2.5F, -1.5F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.2F)).m_171555_(false), PartPose.m_171419_(0.0F, 4.5625F, 0.0F));
      lowerRightLeg.m_171599_("rightFoot", CubeListBuilder.m_171558_().m_171514_(0, 58).m_171480_().m_171488_(-1.5F, -0.5F, -3.5F, 3.0F, 1.0F, 5.0F, new CubeDeformation(0.4F)).m_171555_(false), PartPose.m_171419_(0.0F, 3.0F, 0.0F));
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   public void m_6973_(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      RendererHelper.resetModelToDefaultPivots(this);
      this.setupHeadRotation(entity, headPitch, netHeadYaw);
      float f = 1.0F;
      this.f_102811_.f_104203_ = Mth.m_14089_(limbSwing * 0.6662F + (float)Math.PI) * 0.8F * limbSwingAmount * 0.5F / f;
      this.f_102812_.f_104203_ = Mth.m_14089_(limbSwing * 0.6662F) * 0.8F * limbSwingAmount * 0.5F / f;
      this.f_102813_.f_104203_ = Mth.m_14089_(limbSwing * 0.6662F) * 0.7F * limbSwingAmount / f;
      this.f_102814_.f_104203_ = Mth.m_14089_(limbSwing * 0.6662F + (float)Math.PI) * 0.7F * limbSwingAmount / f;
      if (entity.m_6047_()) {
         this.f_102808_.f_104201_ = 11.0F;
         this.f_102808_.f_104202_ = -3.0F;
         this.f_102810_.f_104203_ = 0.5F;
         this.f_102810_.f_104202_ = 9.0F;
         this.f_102813_.f_104201_ = 17.0F;
         this.f_102814_.f_104201_ = 17.0F;
         this.f_102811_.f_104201_ = 10.0F;
         this.f_102811_.f_104202_ = 0.0F;
         this.f_102812_.f_104201_ = 10.0F;
         this.f_102812_.f_104202_ = 0.0F;
      }

      this.m_7884_(entity, ageInTicks);
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.f_102808_.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.f_102812_.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.f_102811_.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.f_102810_.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.f_102814_.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.f_102813_.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }

   public void m_6002_(HumanoidArm side, PoseStack stack) {
      super.m_6002_(side, stack);
      stack.m_85837_((double)0.0F, 0.2, -0.1);
      stack.m_252781_(Axis.f_252403_.m_252977_(side == HumanoidArm.RIGHT ? 5.0F : -5.0F));
   }

   public void renderFirstPersonArm(PoseStack stack, VertexConsumer vertex, int packedLight, int overlay, float red, float green, float blue, float alpha, HumanoidArm side, boolean isLeg) {
      stack.m_85837_((double)0.0F, -0.3, -0.3);
      stack.m_252781_(Axis.f_252529_.m_252977_(20.0F));
      super.renderFirstPersonArm(stack, vertex, packedLight, overlay, red, green, blue, alpha, side, isLeg);
   }
}
