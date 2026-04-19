package xyz.pixelatedw.mineminenomi.client.models.morphs;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;

public class AllosaurusWalkModel<T extends LivingEntity> extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "allosaurus_walk"), "main");
    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart rightHindLeg;
    private final ModelPart rightLeg2;
    private final ModelPart leftHindLeg;
    private final ModelPart leftLeg2;
    private final ModelPart tail;
    private final ModelPart tail2;
    private final ModelPart tail3;
    private final ModelPart tail4;
    private final ModelPart tail5;
    private final ModelPart tail6;
    private final ModelPart tail7;
    private final ModelPart tail8;

    public AllosaurusWalkModel(ModelPart root) {
        this.root = root;
        this.head = root.getChild("head");
        this.rightHindLeg = root.getChild("right_hind_leg");
        this.rightLeg2 = rightHindLeg.getChild("rightLeg2");
        this.leftHindLeg = root.getChild("left_hind_leg");
        this.leftLeg2 = leftHindLeg.getChild("leftLeg2");
        this.tail = root.getChild("tail");
        this.tail2 = tail.getChild("tail2");
        this.tail3 = tail2.getChild("tail3");
        this.tail4 = tail3.getChild("tail4");
        this.tail5 = tail4.getChild("tail5");
        this.tail6 = tail5.getChild("tail6");
        this.tail7 = tail6.getChild("tail7");
        this.tail8 = tail7.getChild("tail8");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition pd = mesh.getRoot();

        PartDefinition leftHindLeg = pd.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(52, 80).addBox(0.0F, -12.0F, -7.0F, 7.0F, 24.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.5F, -10.564F, 16.0191F, 0.2182F, 0.0F, 0.0F));
        PartDefinition leftLeg2 = leftHindLeg.addOrReplaceChild("leftLeg2", CubeListBuilder.create().texOffs(46, 120).addBox(0.5F, -4.6299F, 0.6175F, 6.0F, 27.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 9.4613F, -5.3088F, -0.6981F, 0.0F, 0.0F));
        PartDefinition leftFeet = leftLeg2.addOrReplaceChild("leftFeet", CubeListBuilder.create().texOffs(95, 92).addBox(-1.0F, -2.5F, -5.0F, 9.0F, 5.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 21.6027F, 5.2897F, 0.48F, 0.0F, 0.0F));
        PartDefinition leftFeet2 = leftFeet.addOrReplaceChild("leftFeet2", CubeListBuilder.create().texOffs(94, 109).addBox(-1.5F, -2.0F, -5.0F, 10.0F, 4.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.5F, -3.0F));
        leftFeet2.addOrReplaceChild("leftToe1", CubeListBuilder.create().texOffs(104, 125).addBox(1.8812F, -1.6739F, -3.3871F, 3.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.8689F, 1.01F, -6.0793F, 0.2075F, -0.2612F, -0.0036F));
        leftFeet2.addOrReplaceChild("leftToe2", CubeListBuilder.create().texOffs(104, 125).addBox(2.0F, -1.5F, -2.5F, 3.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.9569F, -6.7225F, 0.1745F, 0.0F, 0.0F));
        leftFeet2.addOrReplaceChild("leftToe3", CubeListBuilder.create().texOffs(104, 125).addBox(1.9468F, -1.3945F, -2.4015F, 3.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.7618F, 0.7739F, -5.8025F, 0.2188F, 0.1744F, 0.0077F));
        leftFeet2.addOrReplaceChild("leftToe4", CubeListBuilder.create().texOffs(104, 125).addBox(2.0F, -1.5F, -1.5F, 3.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.4597F, 7.6541F, -0.5672F, 0.0F, 0.0F));

        PartDefinition rightHindLeg = pd.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(52, 80).addBox(0.0F, -12.0F, -7.0F, 7.0F, 24.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-13.5F, -10.564F, 16.0191F, 0.2182F, 0.0F, 0.0F));
        PartDefinition rightLeg2 = rightHindLeg.addOrReplaceChild("rightLeg2", CubeListBuilder.create().texOffs(46, 120).addBox(0.5F, -4.6299F, 0.6175F, 6.0F, 27.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 9.4613F, -5.3088F, -0.6981F, 0.0F, 0.0F));
        PartDefinition rightFeet = rightLeg2.addOrReplaceChild("rightFeet", CubeListBuilder.create().texOffs(95, 92).addBox(-1.0F, -2.5F, -5.0F, 9.0F, 5.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 21.6027F, 5.2897F, 0.48F, 0.0F, 0.0F));
        PartDefinition rightFeet2 = rightFeet.addOrReplaceChild("rightFeet2", CubeListBuilder.create().texOffs(94, 109).addBox(-1.5F, -2.0F, -5.0F, 10.0F, 4.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.5F, -3.0F));
        rightFeet2.addOrReplaceChild("rightToe1", CubeListBuilder.create().texOffs(104, 125).addBox(1.8812F, -1.6739F, -3.3871F, 3.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.8689F, 1.01F, -6.0793F, 0.2075F, -0.2612F, -0.0036F));
        rightFeet2.addOrReplaceChild("rightToe2", CubeListBuilder.create().texOffs(104, 125).addBox(2.0F, -1.5F, -2.5F, 3.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.9569F, -6.7225F, 0.1745F, 0.0F, 0.0F));
        rightFeet2.addOrReplaceChild("rightToe3", CubeListBuilder.create().texOffs(104, 125).addBox(1.9468F, -1.3945F, -2.4015F, 3.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.7618F, 0.7739F, -5.8025F, 0.2188F, 0.1744F, 0.0077F));
        rightFeet2.addOrReplaceChild("rightToe4", CubeListBuilder.create().texOffs(104, 125).addBox(2.0F, -1.5F, -1.5F, 3.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.4597F, 7.6541F, -0.5672F, 0.0F, 0.0F));

        PartDefinition rightFrontLeg = pd.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(68, 169).addBox(0.5F, -2.5559F, -3.1355F, 6.0F, 11.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-13.0F, -7.0F, -16.0F, 0.3054F, 0.0F, 0.0F));
        PartDefinition rightArm2 = rightFrontLeg.addOrReplaceChild("rightArm2", CubeListBuilder.create().texOffs(69, 187).addBox(1.0F, -2.6022F, -3.2765F, 5.0F, 11.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 7.4026F, -1.1698F, -1.309F, 0.0F, 0.0F));
        PartDefinition rightHand = rightArm2.addOrReplaceChild("rightHand", CubeListBuilder.create().texOffs(69, 205).addBox(0.0F, -2.5F, -1.5F, 7.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 9.3131F, -0.4006F, 0.6545F, 0.0F, 0.0F));
        rightHand.addOrReplaceChild("rightFinger1", CubeListBuilder.create().texOffs(70, 215).addBox(2.5F, -1.0F, -2.5F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.25F, 4.4447F, 0.6321F, -1.2217F, 0.0F, 0.0F));
        rightHand.addOrReplaceChild("rightFinger2", CubeListBuilder.create().texOffs(70, 215).addBox(2.5F, -1.0F, -2.5F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.5397F, 0.4151F, -1.2217F, 0.0F, 0.0F));
        rightHand.addOrReplaceChild("rightFinger3", CubeListBuilder.create().texOffs(70, 215).addBox(2.5F, -1.0F, -2.5F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.25F, 4.3628F, 0.7644F, -1.2217F, 0.0F, 0.0F));

        PartDefinition leftFrontLeg = pd.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(68, 169).addBox(0.5F, -2.5559F, -3.1355F, 6.0F, 11.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.0F, -7.0F, -16.0F, 0.3054F, 0.0F, 0.0F));
        PartDefinition leftArm2 = leftFrontLeg.addOrReplaceChild("leftArm2", CubeListBuilder.create().texOffs(69, 187).addBox(1.0F, -2.6022F, -3.2765F, 5.0F, 11.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 7.4026F, -1.1698F, -1.309F, 0.0F, 0.0F));
        PartDefinition leftHand = leftArm2.addOrReplaceChild("leftHand", CubeListBuilder.create().texOffs(69, 205).addBox(0.0F, -2.5F, -1.5F, 7.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 9.3131F, -0.4006F, 0.6545F, 0.0F, 0.0F));
        leftHand.addOrReplaceChild("leftFinger1", CubeListBuilder.create().texOffs(70, 215).addBox(2.5F, -1.0F, -2.5F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.25F, 4.4447F, 0.6321F, -1.2217F, 0.0F, 0.0F));
        leftHand.addOrReplaceChild("leftFinger2", CubeListBuilder.create().texOffs(70, 215).addBox(2.5F, -1.0F, -2.5F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.5397F, 0.4151F, -1.2217F, 0.0F, 0.0F));
        leftHand.addOrReplaceChild("leftFinger3", CubeListBuilder.create().texOffs(70, 215).addBox(2.5F, -1.0F, -2.5F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.25F, 4.3628F, 0.7644F, -1.2217F, 0.0F, 0.0F));

        pd.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-7.0F, -13.0714F, -10.7857F, 21.0F, 27.0F, 16.0F, new CubeDeformation(0.0F)).texOffs(1, 156).addBox(-6.0F, -12.0714F, -16.7857F, 19.0F, 24.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(3, 188).addBox(-5.0F, -11.0714F, -21.7857F, 17.0F, 21.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(8, 217).addBox(-3.5F, -10.0714F, -23.7857F, 14.0F, 18.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(0, 43).addBox(-6.0F, -12.0714F, 5.2143F, 19.0F, 25.0F, 12.0F, new CubeDeformation(0.0F)).texOffs(0, 81).addBox(-5.5F, -11.5714F, 17.2143F, 18.0F, 24.0F, 7.0F, new CubeDeformation(0.0F)).texOffs(0, 115).addBox(-4.5F, -10.5714F, 24.2143F, 16.0F, 22.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.5F, -14.9286F, -4.2143F));
        PartDefinition head = pd.addOrReplaceChild("head", CubeListBuilder.create().texOffs(75, 146).addBox(-3.5F, -14.1985F, -23.0413F, 14.0F, 15.0F, 5.0F, new CubeDeformation(0.03F)), PartPose.offset(-3.5F, -19.3015F, -23.9587F));
        head.addOrReplaceChild("neck3", CubeListBuilder.create().texOffs(106, 52).addBox(-6.5F, -3.5F, -7.0F, 13.0F, 7.0F, 14.0F, new CubeDeformation(0.02F)), PartPose.offsetAndRotation(3.5F, -6.3693F, -16.9473F, 1.3963F, 0.0F, 0.0F));
        head.addOrReplaceChild("neck2", CubeListBuilder.create().texOffs(74, 0).addBox(-6.5F, -4.5F, -7.0F, 13.0F, 9.0F, 14.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(3.5F, -4.5383F, -10.4505F, 1.2217F, 0.0F, 0.0F));
        head.addOrReplaceChild("neck1", CubeListBuilder.create().texOffs(62, 29).addBox(-6.5F, -9.5F, -7.0F, 13.0F, 19.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.5F, 1.606F, -0.0608F, 0.9599F, 0.0F, 0.0F));
        PartDefinition upperHead = head.addOrReplaceChild("upperHead", CubeListBuilder.create().texOffs(117, 149).addBox(-3.5F, -5.1947F, -6.7478F, 14.0F, 12.0F, 7.0F, new CubeDeformation(0.0F)).texOffs(116, 173).addBox(-2.5F, -4.1947F, -12.7478F, 12.0F, 11.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(117, 194).addBox(-1.5F, -3.1947F, -20.7478F, 10.0F, 10.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -9.0255F, -22.121F, 0.1309F, 0.0F, 0.0F));
        upperHead.addOrReplaceChild("upperTeeth", CubeListBuilder.create().texOffs(102, 20).addBox(7.5F, -1.5F, -8.7143F, 0.0F, 3.0F, 20.0F, new CubeDeformation(0.0F)).texOffs(102, 17).addBox(-0.5F, -1.5F, -8.7143F, 0.0F, 3.0F, 20.0F, new CubeDeformation(0.0F)).texOffs(58, 10).addBox(0.0F, -1.5F, -8.7143F, 7.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 7.8053F, -11.0335F));
        PartDefinition lowerHead = upperHead.addOrReplaceChild("lowerHead", CubeListBuilder.create().texOffs(92, 241).addBox(-0.5F, -0.7355F, -21.9123F, 8.0F, 3.0F, 9.0F, new CubeDeformation(0.0F)).texOffs(94, 230).addBox(-1.5F, -0.7355F, -12.9123F, 10.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(93, 219).addBox(-2.5F, -0.7355F, -6.9123F, 12.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 7.0898F, 2.1358F, 0.2182F, 0.0F, 0.0F));
        lowerHead.addOrReplaceChild("lowerTeeth", CubeListBuilder.create().texOffs(102, 14).addBox(7.25F, -1.5F, -8.7143F, 0.0F, 3.0F, 20.0F, new CubeDeformation(0.0F)).texOffs(102, 11).addBox(-0.25F, -1.5F, -8.7143F, 0.0F, 3.0F, 20.0F, new CubeDeformation(0.0F)).texOffs(58, 13).addBox(0.0F, -1.5F, -8.7143F, 7.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.7355F, -12.198F));

        PartDefinition tail = pd.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(166, 4).addBox(-3.5F, -7.5F, -37.625F, 14.0F, 15.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.5F, -15.5F, 62.625F));
        PartDefinition tail2 = tail.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(167, 26).addBox(-2.0F, -6.0F, 0.375F, 11.0F, 12.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -33.0F));
        PartDefinition tail3 = tail2.addOrReplaceChild("tail3", CubeListBuilder.create().texOffs(165, 47).addBox(-1.5F, -5.0F, -0.625F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 7.0F));
        PartDefinition tail4 = tail3.addOrReplaceChild("tail4", CubeListBuilder.create().texOffs(165, 69).addBox(-1.0F, -4.5F, -0.125F, 9.0F, 9.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 9.0F));
        PartDefinition tail5 = tail4.addOrReplaceChild("tail5", CubeListBuilder.create().texOffs(160, 93).addBox(-0.5F, -4.0F, -0.125F, 8.0F, 8.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 11.0F));
        PartDefinition tail6 = tail5.addOrReplaceChild("tail6", CubeListBuilder.create().texOffs(161, 119).addBox(0.0F, -3.5F, -0.625F, 7.0F, 7.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 16.0F));
        PartDefinition tail7 = tail6.addOrReplaceChild("tail7", CubeListBuilder.create().texOffs(163, 144).addBox(0.5F, -3.0F, 0.375F, 6.0F, 6.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 14.0F));
        tail7.addOrReplaceChild("tail8", CubeListBuilder.create().texOffs(161, 169).addBox(1.0F, -2.5F, 0.375F, 5.0F, 5.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 15.0F));

        return LayerDefinition.create(mesh, 256, 256);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        root.getAllParts().forEach(ModelPart::resetPose);
        float limbSpeed = 0.3F;
        if (entity.isSprinting()) {
            limbSpeed = 0.5F;
        }

        this.rightHindLeg.xRot = 0.2F + Mth.cos(limbSwing * limbSpeed) * 0.5F * limbSwingAmount;
        this.rightLeg2.xRot = -0.7F - Mth.sin(limbSwing * limbSpeed) * 0.5F * limbSwingAmount;
        this.leftHindLeg.xRot = 0.2F + Mth.cos(limbSwing * limbSpeed + (float)Math.PI) * 0.5F * limbSwingAmount;
        this.leftLeg2.xRot = -0.7F - Mth.sin(limbSwing * limbSpeed + (float)Math.PI) * 0.5F * limbSwingAmount;

        if (entity.isSprinting()) {
            this.tail3.xRot = Mth.cos(limbSwing * 0.6F) * 0.1F * limbSwingAmount;
            float tailAge = ageInTicks * 0.06F;
            float tailAgeSlow = ageInTicks * 0.02F;
            this.tail5.yRot = Mth.sin(tailAge) / 20.0F;
            this.tail5.xRot = Mth.sin(tailAgeSlow) / 10.0F;
            this.tail6.yRot = Mth.sin(tailAge) / 20.0F;
            this.tail6.xRot = Mth.sin(tailAgeSlow) / 10.0F;
            this.tail7.yRot = Mth.sin(tailAge) / 20.0F;
            this.tail7.xRot = Mth.sin(tailAgeSlow) / 10.0F;
            this.tail8.yRot = Mth.sin(tailAge) / 20.0F;
            this.tail8.xRot = Mth.sin(tailAgeSlow) / 10.0F;
        } else {
            this.tail2.yRot = Mth.sin(ageInTicks * 0.01F) / 20.0F;
            this.tail2.xRot = Mth.sin(ageInTicks * 0.005F) / 10.0F;
            this.tail3.yRot = Mth.sin(ageInTicks * 0.01F) / 20.0F;
            this.tail3.xRot = Mth.sin(ageInTicks * 0.005F) / 10.0F;
            this.tail4.yRot = Mth.sin(ageInTicks * 0.01F) / 20.0F;
            this.tail4.xRot = Mth.sin(ageInTicks * 0.005F) / 10.0F;
            this.tail5.yRot = Mth.sin(ageInTicks * 0.06F) / 20.0F;
            this.tail5.xRot = Mth.sin(ageInTicks * 0.02F) / 10.0F;
            this.tail6.yRot = Mth.sin(ageInTicks * 0.06F) / 20.0F;
            this.tail6.xRot = Mth.sin(ageInTicks * 0.02F) / 10.0F;
            this.tail7.yRot = Mth.sin(ageInTicks * 0.06F) / 20.0F;
            this.tail7.xRot = Mth.sin(ageInTicks * 0.02F) / 10.0F;
            this.tail8.yRot = Mth.sin(ageInTicks * 0.06F) / 20.0F;
            this.tail8.xRot = Mth.sin(ageInTicks * 0.02F) / 10.0F;
        }

        this.head.yRot = netHeadYaw * ((float)Math.PI / 180F);
        this.head.xRot = headPitch * ((float)Math.PI / 180F);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        root.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }
}