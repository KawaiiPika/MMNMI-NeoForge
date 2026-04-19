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

public class GiraffeHeavyModel<T extends LivingEntity> extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "giraffe_heavy"), "main");

    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart neck;
    private final ModelPart face;
    private final ModelPart mouth;
    private final ModelPart leftHorn;
    private final ModelPart rightHorn;
    private final ModelPart leftEar;
    private final ModelPart rightEar;
    private final ModelPart mane;
    private final ModelPart mane2;
    private final ModelPart body;
    private final ModelPart rightArm;
    private final ModelPart leftArm;
    private final ModelPart rightArm2;
    private final ModelPart leftArm2;
    private final ModelPart rightLeg;
    private final ModelPart leftLeg;
    private final ModelPart tail;
    private final ModelPart tail2;

    public GiraffeHeavyModel(ModelPart root) {
        this.root = root;
        this.head = root.getChild("head");
        this.neck = head.getChild("neck");
        this.face = neck.getChild("face");
        this.mouth = face.getChild("mouth");
        this.leftHorn = face.getChild("leftHorn");
        this.rightHorn = face.getChild("rightHorn");
        this.leftEar = face.getChild("leftEar");
        this.rightEar = face.getChild("rightEar");
        this.mane = neck.getChild("mane");
        this.mane2 = head.getChild("mane2");
        this.body = root.getChild("body");
        this.rightArm = root.getChild("right_arm");
        this.leftArm = root.getChild("left_arm");
        this.rightArm2 = rightArm.getChild("rightArm2");
        this.leftArm2 = leftArm.getChild("leftArm2");
        this.rightLeg = root.getChild("right_leg");
        this.leftLeg = root.getChild("left_leg");
        this.tail = root.getChild("tail");
        this.tail2 = tail.getChild("tail2");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition pd = mesh.getRoot();

        PartDefinition head = pd.addOrReplaceChild("head", CubeListBuilder.create().texOffs(48, 33).mirror().addBox(0.5F, 0.75F, 0.5F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.5F)).mirror(false), PartPose.offsetAndRotation(-2.0F, -11.0F, -3.5F, 0.1745F, 0.0F, 0.0F));
        PartDefinition neck = head.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(48, 33).addBox(0.0F, 0.0F, 0.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, -7.0F, -0.5F, 0.1396F, 0.0F, 0.0F));
        PartDefinition face = neck.addOrReplaceChild("face", CubeListBuilder.create().texOffs(32, 18).addBox(-2.0F, -4.0F, -6.0F, 4.0F, 3.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 0.5F, 1.5F, -0.3142F, 0.0F, 0.0F));
        face.addOrReplaceChild("rightEar", CubeListBuilder.create().texOffs(32, 0).addBox(-3.0F, -3.5F, 0.0F, 3.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, -3.5F, 0.0F, 0.0F, 0.0F, -0.2618F));
        face.addOrReplaceChild("mouth", CubeListBuilder.create().texOffs(31, 29).addBox(0.01F, 0.0F, -4.0F, 4.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, -1.0F, 0.5F));
        face.addOrReplaceChild("leftHorn", CubeListBuilder.create().texOffs(39, 13).addBox(0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, -6.0F, -0.5F));
        face.addOrReplaceChild("rightHorn", CubeListBuilder.create().texOffs(39, 13).addBox(0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.5F, -6.0F, -0.5F));
        face.addOrReplaceChild("leftEar", CubeListBuilder.create().texOffs(32, 0).mirror().addBox(0.0F, -3.5F, 0.0F, 3.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.0F, -3.5F, 0.0F, 0.0F, 0.0F, 0.2618F));
        neck.addOrReplaceChild("mane", CubeListBuilder.create().texOffs(33, 37).addBox(0.0F, 0.0F, 0.2F, 0.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -2.0F, 3.5F, 0.0349F, 0.0F, 0.0F));
        head.addOrReplaceChild("mane2", CubeListBuilder.create().texOffs(33, 37).addBox(0.0F, 0.0F, 0.0F, 0.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.5F, 0.0F, 4.6F, 0.0175F, 0.0F, 0.0F));

        PartDefinition body = pd.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, 0.0F, -2.0F, 10.0F, 16.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, -4.0F, 0.0F));
        body.addOrReplaceChild("leftShoulder", CubeListBuilder.create().texOffs(36, 0).mirror().addBox(0.0F, 0.0F, -0.1F, 9.0F, 7.0F, 5.0F, new CubeDeformation(-0.01F)).mirror(false), PartPose.offsetAndRotation(-0.5F, 7.5F, -1.9F, 0.0F, 0.0F, -0.9599F));
        body.addOrReplaceChild("rightShoulder", CubeListBuilder.create().texOffs(36, 0).addBox(0.0F, -7.5F, -0.09F, 9.0F, 7.0F, 5.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(2.5F, 7.4F, -1.91F, 0.0F, 0.0F, -2.2689F));

        PartDefinition rightArm = pd.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(23, 30).addBox(0.0F, 0.0F, 0.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.0F, 1.0F, -1.0F, 0.0F, 0.0F, 0.2793F));
        PartDefinition rightArm2 = rightArm.addOrReplaceChild("rightArm2", CubeListBuilder.create().texOffs(23, 39).addBox(0.0F, 0.0F, 0.1F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 6.0F, 0.0F, 0.0F, 0.0F, -0.4189F));
        rightArm2.addOrReplaceChild("rightHand2", CubeListBuilder.create().texOffs(27, 2).addBox(0.0F, -0.0868F, -0.4924F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.1392F, 5.1097F, 0.8F, 0.1745F, 0.0F, -0.3491F));
        rightArm2.addOrReplaceChild("rightHand1", CubeListBuilder.create().texOffs(26, 2).addBox(-0.1F, 0.0F, 0.1F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(0.0F, 5.5F, 2.0F, 0.1745F, 1.5708F, 0.1396F));

        PartDefinition leftArm = pd.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(23, 30).addBox(-2.0F, 0.0F, 0.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.0F, 1.0F, -1.0F, 0.0F, 0.0F, -0.2793F));
        PartDefinition leftArm2 = leftArm.addOrReplaceChild("leftArm2", CubeListBuilder.create().texOffs(23, 39).addBox(-2.0F, 0.0F, 0.1F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 6.0F, 0.0F, 0.0F, 0.0F, 0.4189F));
        leftArm2.addOrReplaceChild("leftHand2", CubeListBuilder.create().texOffs(27, 2).addBox(5.9F, -0.07F, -1.4F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.4075F, 2.7019F, 1.8F, 0.1745F, 0.0F, 0.3491F));
        leftArm2.addOrReplaceChild("leftHand1", CubeListBuilder.create().texOffs(26, 2).addBox(-1.4F, 2.6065F, 1.9F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(1.8929F, 2.9691F, 1.5F, 0.1745F, -1.5708F, -0.1396F));

        PartDefinition rightLeg = pd.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(10, 30).addBox(-2.0F, 0.0F, -2.0F, 3.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 11.6F, 1.0F, -0.3491F, 0.0F, 0.0F));
        PartDefinition rightLeg3 = rightLeg.addOrReplaceChild("rightLeg3", CubeListBuilder.create().texOffs(10, 41).addBox(-2.0F, 0.0F, -2.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 5.0F, 0.0F, 1.7453F, 0.0F, 0.0F));
        PartDefinition rightLeg2 = rightLeg3.addOrReplaceChild("rightLeg2", CubeListBuilder.create().texOffs(0, 30).addBox(-2.0F, 0.0F, -2.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, 5.2065F, -0.5747F, -1.9199F, 0.0F, 0.0F));
        PartDefinition rightHoof = rightLeg2.addOrReplaceChild("rightHoof", CubeListBuilder.create().texOffs(25, 1).addBox(-2.0F, 0.0F, -2.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5.0F, -0.5F, 0.5236F, 0.0F, 0.0F));
        rightHoof.addOrReplaceChild("rightHoof2", CubeListBuilder.create().texOffs(27, 2).addBox(-1.0F, 0.0F, -2.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 0.0F, -2.5F, -0.1211F, -0.4883F, -0.0394F));
        rightHoof.addOrReplaceChild("rightHoof3", CubeListBuilder.create().texOffs(27, 2).addBox(-2.0F, 0.0F, -2.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 0.0F, -2.0F, -0.1211F, 0.4883F, 0.0394F));

        PartDefinition leftLeg = pd.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(10, 30).addBox(-2.0F, 0.0F, -2.0F, 3.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, 11.6F, 1.0F, -0.3491F, 0.0F, 0.0F));
        PartDefinition leftLeg2 = leftLeg.addOrReplaceChild("leftLeg2", CubeListBuilder.create().texOffs(10, 41).addBox(-2.0F, 0.0F, -2.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 5.0F, 0.0F, 1.7453F, 0.0F, 0.0F));
        PartDefinition leftLeg3 = leftLeg2.addOrReplaceChild("leftLeg3", CubeListBuilder.create().texOffs(0, 30).addBox(-2.0F, 0.0F, -2.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, 5.2065F, -0.5747F, -1.9199F, 0.0F, 0.0F));
        PartDefinition leftHoof = leftLeg3.addOrReplaceChild("leftHoof", CubeListBuilder.create().texOffs(25, 1).addBox(-2.0F, 0.0F, -2.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5.0F, -0.5F, 0.5236F, 0.0F, 0.0F));
        leftHoof.addOrReplaceChild("leftHoof2", CubeListBuilder.create().texOffs(27, 2).addBox(-1.0F, 0.0F, -2.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 0.0F, -2.5F, -0.1211F, -0.4883F, -0.0394F));
        leftHoof.addOrReplaceChild("leftHoof3", CubeListBuilder.create().texOffs(27, 2).addBox(-2.0F, 0.0F, -2.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 0.0F, -2.0F, -0.1211F, 0.4883F, 0.0394F));

        PartDefinition tail = pd.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(30, 12).addBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 10.0F, 3.0F, -0.733F, 0.0F, 0.0F));
        PartDefinition tail2 = tail.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(30, 12).addBox(0.0F, 0.15F, 0.0F, 1.0F, 1.0F, 5.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(0.0F, 0.0F, 4.5F, 0.4712F, 0.0F, 0.0F));
        tail2.addOrReplaceChild("tail3", CubeListBuilder.create().texOffs(43, 13).addBox(0.0F, -0.8803F, 0.1871F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 0.5422F, 4.4176F, 0.2967F, 0.0F, 0.0F));

        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        root.getAllParts().forEach(ModelPart::resetPose);
        this.head.yRot = netHeadYaw * ((float)Math.PI / 180F);
        this.head.xRot = headPitch * ((float)Math.PI / 180F);

        float limbSpeed = 0.6662F;
        this.rightArm.xRot = Mth.cos(limbSwing * limbSpeed + (float)Math.PI) * 0.8F * limbSwingAmount * 0.5F;
        this.leftArm.xRot = Mth.cos(limbSwing * limbSpeed) * 0.8F * limbSwingAmount * 0.5F;
        this.rightLeg.xRot = -0.34F + Mth.cos(limbSwing * limbSpeed) * 0.7F * limbSwingAmount;
        this.leftLeg.xRot = -0.34F + Mth.cos(limbSwing * limbSpeed + (float)Math.PI) * 0.7F * limbSwingAmount;

        if (entity.isSprinting()) {
            this.tail.xRot = Mth.cos(limbSwing * 0.6662F) * 0.2F * limbSwingAmount;
            this.leftEar.yRot = -0.3F - Mth.cos(limbSwing * 0.6662F) * 0.2F * limbSwingAmount;
            this.rightEar.yRot = 0.3F + Mth.cos(limbSwing * 0.6662F) * 0.2F * limbSwingAmount;
        }

        if (entity.isCrouching()) {
            this.head.xRot = 0.35F;
            this.head.y = -8.0F;
            this.head.z = -5.0F;
            this.head.x += 0.2F;
            this.head.y -= 0.3F;
            this.head.z += 0.2F;
            this.neck.z = -3.0F;
            this.face.x -= 0.2F;
            this.face.y += 0.3F;
            this.face.z -= 0.2F;
            this.body.x = 0.25F;
            this.body.y = -4.0F;
            this.body.z = -1.0F;
            this.rightArm.x = 0.25F;
            this.rightArm.y = -3.0F;
            this.rightArm.z = 4.0F;
            this.leftArm.x = 0.25F;
            this.leftArm.y = -3.0F;
            this.leftArm.z = 4.0F;
        }
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        root.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }
}