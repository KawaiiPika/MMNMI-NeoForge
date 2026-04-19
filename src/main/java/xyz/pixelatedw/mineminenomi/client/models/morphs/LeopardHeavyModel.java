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

public class LeopardHeavyModel<T extends LivingEntity> extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "leopard_heavy"), "main");

    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart rightArm;
    private final ModelPart leftArm;
    private final ModelPart rightLeg;
    private final ModelPart leftLeg;
    private final ModelPart tail;
    private final ModelPart tail2;
    private final ModelPart tail3;

    public LeopardHeavyModel(ModelPart root) {
        this.root = root;
        this.head = root.getChild("head");
        this.body = root.getChild("body");
        this.rightArm = root.getChild("right_arm");
        this.leftArm = root.getChild("left_arm");
        this.rightLeg = root.getChild("right_leg");
        this.leftLeg = root.getChild("left_leg");
        this.tail = body.getChild("tail");
        this.tail2 = tail.getChild("tail2");
        this.tail3 = tail2.getChild("tail3");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition pd = mesh.getRoot();

        PartDefinition head = pd.addOrReplaceChild("head", CubeListBuilder.create().texOffs(72, 0).addBox(-2.9F, -3.2847F, -4.6892F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.55F)), PartPose.offset(-0.1F, -9.5941F, -1.6107F));
        head.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(81, 13).addBox(-0.5F, 0.0F, -0.8F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.1F, -0.2097F, -4.6267F, -0.2618F, 0.0F, 0.0F));
        head.addOrReplaceChild("leftEye", CubeListBuilder.create().texOffs(80, 28).mirror().addBox(-0.975F, -1.5F, 0.015F, 3.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(1.875F, -0.8847F, -5.2743F));
        head.addOrReplaceChild("rightEye", CubeListBuilder.create().texOffs(80, 28).addBox(-2.1F, -1.5F, 0.015F, 3.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.6F, -0.8847F, -5.2743F));
        head.addOrReplaceChild("leftEar", CubeListBuilder.create().texOffs(80, 23).addBox(-1.0317F, -1.1108F, -0.5993F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.7725F, -4.0157F, -1.2526F, 0.2004F, -0.562F, 0.0775F));
        head.addOrReplaceChild("rightEar", CubeListBuilder.create().texOffs(80, 18).addBox(-1.9683F, -1.1108F, -0.5993F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.5225F, -4.0157F, -1.2526F, 0.2004F, 0.562F, -0.0775F));

        PartDefinition body = pd.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 117).addBox(-5.5F, -3.9375F, -2.375F, 11.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 7.9375F, 2.0F));
        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(99, 25).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.9375F, 3.4375F));
        PartDefinition tail2 = tail.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(103, 13).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 9.0F, new CubeDeformation(0.001F)), PartPose.offset(0.0F, 0.0F, 10.5F));
        tail2.addOrReplaceChild("tail3", CubeListBuilder.create().texOffs(102, 0).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 8.5F));
        
        PartDefinition lowerBody = body.addOrReplaceChild("lowerBody", CubeListBuilder.create().texOffs(0, 95).addBox(-6.5F, -13.9472F, -4.1392F, 13.0F, 14.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, 1.0625F, 0.1396F, 0.0F, 0.0F));
        lowerBody.addOrReplaceChild("upperBody", CubeListBuilder.create().texOffs(0, 75).addBox(-7.5F, -5.8931F, -4.8572F, 15.0F, 12.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -8.6875F, -0.8125F, 0.0873F, 0.0F, 0.0F));

        PartDefinition leftLeg = pd.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(3, 0).addBox(-1.5F, -1.8125F, -3.375F, 4.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.5F, 8.7843F, 1.985F, -0.3491F, 0.0F, 0.0F));
        PartDefinition leftLeg2 = leftLeg.addOrReplaceChild("leftLeg2", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 4.9749F, -2.2979F, -0.0349F, 0.0F, 0.0F));
        leftLeg2.addOrReplaceChild("lowerleftleg1_r1", CubeListBuilder.create().texOffs(4, 14).addBox(2.5F, -1.5F, -2.5F, 3.0F, 3.0F, 5.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(-3.5F, -0.2936F, 2.5072F, -0.1309F, 0.0F, 0.0F));
        PartDefinition leftLeg3 = leftLeg2.addOrReplaceChild("leftLeg3", CubeListBuilder.create().texOffs(7, 23).addBox(-1.0F, -0.5005F, -0.9999F, 2.0F, 9.0F, 3.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(0.5F, 0.5156F, 3.7208F, 0.5236F, 0.0F, 0.0F));
        leftLeg3.addOrReplaceChild("leftFoot", CubeListBuilder.create().texOffs(0, 36).addBox(-2.0625F, -0.3125F, -6.0F, 4.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0625F, 7.7493F, -0.4146F, -0.1309F, 0.0F, 0.0F));

        PartDefinition rightLeg = pd.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(3, 0).mirror().addBox(-2.5F, -1.8125F, -3.375F, 4.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-3.5F, 8.7843F, 1.985F, -0.3491F, 0.0F, 0.0F));
        PartDefinition rightLeg2 = rightLeg.addOrReplaceChild("rightLeg2", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 4.9749F, -2.2979F, -0.0349F, 0.0F, 0.0F));
        rightLeg2.addOrReplaceChild("lowerrightleg_r1", CubeListBuilder.create().texOffs(4, 14).mirror().addBox(-5.5F, -1.5F, -2.5F, 3.0F, 3.0F, 5.0F, new CubeDeformation(0.2F)).mirror(false), PartPose.offsetAndRotation(3.5F, -0.2936F, 2.5072F, -0.1309F, 0.0F, 0.0F));
        PartDefinition rightLeg3 = rightLeg2.addOrReplaceChild("rightLeg3", CubeListBuilder.create().texOffs(7, 23).mirror().addBox(-1.0F, -0.5005F, -0.9999F, 2.0F, 9.0F, 3.0F, new CubeDeformation(0.2F)).mirror(false), PartPose.offsetAndRotation(-0.5F, 0.5156F, 3.7208F, 0.5236F, 0.0F, 0.0F));
        rightLeg3.addOrReplaceChild("rightFoot", CubeListBuilder.create().texOffs(0, 36).mirror().addBox(-1.9375F, -0.3125F, -6.0F, 4.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.0625F, 7.7493F, -0.4146F, -0.1309F, 0.0F, 0.0F));

        PartDefinition leftArm = pd.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(25, 0).addBox(-1.0F, -1.1142F, -3.4625F, 6.0F, 10.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0F, -6.4813F, -5.0E-4F, 0.1745F, 0.0F, 0.0F));
        leftArm.addOrReplaceChild("lowerLeftArm", CubeListBuilder.create().texOffs(27, 17).addBox(-2.5F, -0.3074F, -2.2925F, 5.0F, 11.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 7.6401F, -0.3356F, -0.3054F, 0.0F, 0.0F));
        
        PartDefinition rightArm = pd.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(25, 0).mirror().addBox(-5.0F, -1.1142F, -3.4625F, 6.0F, 10.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-8.0F, -6.4813F, -5.0E-4F, 0.1745F, 0.0F, 0.0F));
        rightArm.addOrReplaceChild("lowerRightArm", CubeListBuilder.create().texOffs(27, 17).mirror().addBox(-2.5F, -0.3074F, -2.2925F, 5.0F, 11.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-2.0F, 7.6401F, -0.3356F, -0.3054F, 0.0F, 0.0F));

        return LayerDefinition.create(mesh, 128, 128);
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

        this.tail.yRot += Math.sin(ageInTicks * 0.06) / 5.0F;
        this.tail.xRot += Math.sin(ageInTicks * 0.06) / 5.0F;
        this.tail2.yRot += Math.cos(ageInTicks * 0.06) / 7.0F;
        this.tail2.xRot += Math.cos(ageInTicks * 0.06) / 2.0F;
        this.tail3.yRot += Math.sin(ageInTicks * 0.06) / 7.0F;
        this.tail3.xRot += Math.sin(ageInTicks * 0.06) / 2.0F;

        if (entity.isCrouching()) {
            this.head.z = -6.0F;
            this.head.y = -5.0F;
            this.body.xRot = 0.2F;
            this.body.y = 11.0F;
            this.body.z = 1.0F;
            this.rightArm.xRot += 0.4F;
            this.rightArm.z = -4.0F;
            this.rightArm.y = -3.0F;
            this.leftArm.xRot += 0.4F;
            this.leftArm.z = -4.0F;
            this.leftArm.y = -3.0F;
            this.rightLeg.y = 13.0F;
            this.leftLeg.y = 13.0F;
        }
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        root.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }
}