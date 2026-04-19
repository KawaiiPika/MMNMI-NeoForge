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

public class MammothGuardModel<T extends LivingEntity> extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "mammoth_guard"), "main");

    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart leftFrontLeg;
    private final ModelPart rightFrontLeg;
    private final ModelPart leftHindLeg;
    private final ModelPart rightHindLeg;
    private final ModelPart trunk;
    private final ModelPart trunk2;
    private final ModelPart trunk3;
    private final ModelPart leftEar;
    private final ModelPart rightEar;
    private final ModelPart tail;

    public MammothGuardModel(ModelPart root) {
        this.root = root;
        this.head = root.getChild("head");
        this.body = root.getChild("body");
        this.leftFrontLeg = root.getChild("left_front_leg");
        this.rightFrontLeg = root.getChild("right_front_leg");
        this.leftHindLeg = root.getChild("left_hind_leg");
        this.rightHindLeg = root.getChild("right_hind_leg");
        this.trunk = head.getChild("trunk");
        this.trunk2 = trunk.getChild("trunk2");
        this.trunk3 = trunk2.getChild("trunk3");
        this.leftEar = head.getChild("leftEar");
        this.rightEar = head.getChild("rightEar");
        this.tail = body.getChild("body2").getChild("body3").getChild("tail");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition pd = mesh.getRoot();

        PartDefinition head = pd.addOrReplaceChild("head", CubeListBuilder.create().texOffs(22, 43).addBox(-5.0F, -6.0F, -7.0F, 10.0F, 12.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 4.0F, -15.75F));
        head.addOrReplaceChild("leftEye", CubeListBuilder.create().texOffs(9, 8).addBox(0.0F, -1.0F, -1.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(5.025F, -2.225F, -5.3375F));
        head.addOrReplaceChild("rightEye", CubeListBuilder.create().texOffs(9, 8).mirror().addBox(0.0F, -1.0F, -1.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-5.025F, -2.225F, -5.3375F));
        
        PartDefinition trunk = head.addOrReplaceChild("trunk", CubeListBuilder.create().texOffs(15, 31).addBox(-2.0F, -3.671F, -2.9673F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.5F)), PartPose.offsetAndRotation(0.0F, 4.0F, -5.5F, -0.3054F, 0.0F, 0.0F));
        PartDefinition trunk2 = trunk.addOrReplaceChild("trunk2", CubeListBuilder.create().texOffs(32, 31).addBox(-2.0F, -0.875F, -1.625F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(0.0F, 3.602F, -1.3293F, 0.3491F, 0.0F, 0.0F));
        trunk2.addOrReplaceChild("trunk3", CubeListBuilder.create().texOffs(48, 32).addBox(-2.0F, -0.9353F, -1.554F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 6.4876F, -0.0527F, 0.2182F, 0.0F, 0.0F));
        
        head.addOrReplaceChild("leftEar", CubeListBuilder.create().texOffs(0, 54).mirror().addBox(0.0F, -4.5F, -0.5F, 8.0F, 9.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(4.0F, -2.5F, -3.5F, -0.1368F, -0.4707F, 0.2946F));
        head.addOrReplaceChild("rightEar", CubeListBuilder.create().texOffs(0, 54).addBox(-8.0F, -4.5F, -0.5F, 8.0F, 9.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, -2.5F, -3.5F, -0.1368F, 0.4707F, -0.2946F));
        
        PartDefinition leftTusk = head.addOrReplaceChild("leftTusk", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-1.0151F, -2.6722F, -0.7564F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.8172F, 4.2807F, -7.1282F, -1.1781F, -0.2094F, 0.0F));
        leftTusk.addOrReplaceChild("leftTusk2", CubeListBuilder.create().texOffs(0, 1).mirror().addBox(-1.0F, -0.0387F, -0.8341F, 2.0F, 7.0F, 2.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(-0.0151F, 5.0636F, 0.0887F, -0.1745F, 0.0F, 0.0F));
        
        PartDefinition rightTusk = head.addOrReplaceChild("rightTusk", CubeListBuilder.create().texOffs(0, 0).addBox(-0.9849F, -2.6722F, -0.7564F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.8172F, 4.2807F, -7.1282F, -1.1781F, 0.2094F, 0.0F));
        rightTusk.addOrReplaceChild("rightTusk2", CubeListBuilder.create().texOffs(0, 1).addBox(-1.0F, -0.0387F, -0.8341F, 2.0F, 7.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0151F, 5.0636F, 0.0887F, -0.1745F, 0.0F, 0.0F));

        pd.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(0, 38).addBox(-2.5F, -0.5F, -2.5F, 5.0F, 10.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, 14.5F, -10.0F));
        pd.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(0, 38).mirror().addBox(-2.5F, -0.5F, -2.5F, 5.0F, 10.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-5.0F, 14.5F, -10.0F));
        pd.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(0, 38).addBox(-2.5F, -0.475F, -2.5F, 5.0F, 10.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(3.75F, 14.475F, 9.0F));
        pd.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(0, 38).mirror().addBox(-2.5F, -0.475F, -2.5F, 5.0F, 10.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-4.0F, 14.475F, 9.0F));

        PartDefinition body = pd.addOrReplaceChild("body", CubeListBuilder.create().texOffs(1, 0).addBox(-8.0F, -9.0F, -6.5F, 16.0F, 17.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 8.4874F, -9.199F, 0.0873F, 0.0F, 0.0F));
        PartDefinition body2 = body.addOrReplaceChild("body2", CubeListBuilder.create().texOffs(3, 2).mirror().addBox(-7.5F, -8.6873F, -5.4916F, 15.0F, 16.0F, 11.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.5488F, 8.615F, -0.0873F, 0.0F, 0.0F));
        PartDefinition body3 = body2.addOrReplaceChild("body3", CubeListBuilder.create().texOffs(2, 1).addBox(-7.1F, -6.754F, 1.6015F, 14.0F, 15.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.625F, 0.0F, -0.0873F, 0.0F, 0.0F));
        PartDefinition tail = body3.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 32).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 9.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.4112F, 14.084F, 0.3491F, 0.0F, 0.0F));
        tail.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(5, 31).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 8.0F, 0.0F));

        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        root.getAllParts().forEach(ModelPart::resetPose);
        this.head.yRot = netHeadYaw * ((float)Math.PI / 180F);
        this.head.xRot = headPitch * ((float)Math.PI / 180F);

        float limbSpeed = 0.4F;
        this.rightFrontLeg.xRot = Mth.cos(limbSwing * limbSpeed) * 0.3F * limbSwingAmount;
        this.leftFrontLeg.xRot = Mth.cos(limbSwing * limbSpeed + (float)Math.PI) * 0.3F * limbSwingAmount;
        this.rightHindLeg.xRot = Mth.cos(limbSwing * limbSpeed) * 0.4F * limbSwingAmount;
        this.leftHindLeg.xRot = Mth.cos(limbSwing * limbSpeed + (float)Math.PI) * 0.4F * limbSwingAmount;

        if (entity.isSprinting()) {
            this.tail.xRot = 0.5F + Mth.cos(limbSwing * 0.8F) * 0.1F * limbSwingAmount;
            this.leftEar.zRot = -0.3F - Mth.cos(limbSwing * 0.6662F) * 0.2F * limbSwingAmount;
            this.rightEar.zRot = 0.3F + Mth.cos(limbSwing * 0.6662F) * 0.2F * limbSwingAmount;
        }
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        root.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }
}
