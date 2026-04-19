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

public class LeopardWalkModel<T extends LivingEntity> extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "leopard_walk"), "main");

    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart rightFrontLeg;
    private final ModelPart leftFrontLeg;
    private final ModelPart rightHindLeg;
    private final ModelPart leftHindLeg;
    private final ModelPart tail;
    private final ModelPart tail2;
    private final ModelPart tail3;

    public LeopardWalkModel(ModelPart root) {
        this.root = root;
        this.head = root.getChild("head");
        this.body = root.getChild("body");
        this.rightFrontLeg = root.getChild("right_front_leg");
        this.leftFrontLeg = root.getChild("left_front_leg");
        this.rightHindLeg = root.getChild("right_hind_leg");
        this.leftHindLeg = root.getChild("left_hind_leg");
        this.tail = body.getChild("tail");
        this.tail2 = tail.getChild("tail2");
        this.tail3 = tail2.getChild("tail3");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition pd = mesh.getRoot();

        PartDefinition head = pd.addOrReplaceChild("head", CubeListBuilder.create().texOffs(37, 0).addBox(-2.5F, -2.0627F, -5.2758F, 5.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.125F, 13.3127F, -5.4117F, 0.1309F, 0.0F, 0.0F));
        head.addOrReplaceChild("leftEye", CubeListBuilder.create().texOffs(1, 0).mirror().addBox(-1.0F, -0.5F, 0.0F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(1.4F, -0.3627F, -5.2883F));
        head.addOrReplaceChild("rightEye", CubeListBuilder.create().texOffs(1, 0).addBox(-1.0F, -0.5F, 0.0F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.4F, -0.3627F, -5.2883F));
        head.addOrReplaceChild("upperMouth", CubeListBuilder.create().texOffs(20, 5).addBox(-1.5F, -1.8903F, -2.424F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.9146F, -5.2678F, 0.0873F, 0.0F, 0.0F));
        head.addOrReplaceChild("lowerMouth", CubeListBuilder.create().texOffs(20, 12).addBox(-1.5F, -0.1364F, -2.4333F, 3.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.1589F, -5.237F, 0.0873F, 0.0F, 0.0F));
        head.addOrReplaceChild("rightEar", CubeListBuilder.create().texOffs(0, 2).mirror().addBox(-1.947F, -0.9366F, -0.6477F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.7725F, -1.7151F, -1.7654F, 0.2004F, 0.5236F, -0.0775F));
        head.addOrReplaceChild("leftEar", CubeListBuilder.create().texOffs(0, 2).addBox(-1.053F, -0.9366F, -0.6477F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0225F, -1.7151F, -1.7654F, 0.2004F, -0.5236F, 0.0775F));

        PartDefinition body = pd.addOrReplaceChild("body", CubeListBuilder.create().texOffs(9, 21).addBox(-3.0F, -3.4063F, -7.0F, 6.0F, 7.0F, 10.0F, new CubeDeformation(0.0F)).texOffs(33, 12).addBox(-2.5F, -3.0938F, -3.0F, 5.0F, 6.0F, 10.0F, new CubeDeformation(0.2F)), PartPose.offset(-0.0625F, 14.4688F, 1.75F));
        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(3, 0).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.125F, -1.5938F, 6.5625F));
        PartDefinition tail2 = tail.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(0, 9).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 7.0F, new CubeDeformation(-0.01F)), PartPose.offset(0.0F, 0.0F, 5.5F));
        tail2.addOrReplaceChild("tail3", CubeListBuilder.create().texOffs(1, 19).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 6.5F));

        PartDefinition leftFrontLeg = pd.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(15, 55).addBox(-1.4564F, -1.5186F, -1.7829F, 3.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.8939F, 15.2061F, -2.9046F, 0.0873F, 0.0015F, -0.0174F));
        PartDefinition lowerLeftFrontLeg = leftFrontLeg.addOrReplaceChild("lowerLeftFrontLeg", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0858F, 3.0291F, -0.1806F, -0.096F, 0.0F, -0.0175F));
        lowerLeftFrontLeg.addOrReplaceChild("lowerfrontleftleg1_r1", CubeListBuilder.create().texOffs(18, 46).addBox(-1.0F, -3.3125F, -2.25F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(0.0102F, 2.5856F, 0.8352F, -0.2269F, 0.0F, -0.0175F));
        lowerLeftFrontLeg.addOrReplaceChild("leftFrontPaw", CubeListBuilder.create().texOffs(17, 41).addBox(-1.0F, -0.5F, -2.5F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.3F)), PartPose.offset(0.0102F, 4.9606F, -0.6023F));
        
        PartDefinition rightFrontLeg = pd.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(0, 55).addBox(-1.5436F, -1.5186F, -1.7829F, 3.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.8939F, 15.2061F, -2.9046F, 0.0873F, -0.0015F, 0.0174F));
        PartDefinition lowerRightFrontLeg = rightFrontLeg.addOrReplaceChild("lowerRightFrontLeg", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.0858F, 3.0291F, -0.1806F, -0.096F, 0.0F, 0.0175F));
        lowerRightFrontLeg.addOrReplaceChild("lowerfrontrightleg1_r1", CubeListBuilder.create().texOffs(3, 46).addBox(-1.0F, -3.3125F, -2.25F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(-0.0102F, 2.5856F, 0.8352F, -0.2269F, 0.0F, 0.0175F));
        lowerRightFrontLeg.addOrReplaceChild("rightFrontPaw", CubeListBuilder.create().texOffs(2, 41).addBox(-1.0F, -0.5F, -2.5F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.3F)), PartPose.offset(-0.0102F, 4.9606F, -0.6023F));

        PartDefinition leftHindLeg = pd.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(43, 54).addBox(-1.3687F, -0.4976F, -2.0755F, 3.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.2437F, 13.1851F, 6.0755F, -0.1046F, -0.0055F, -0.0521F));
        PartDefinition lowerLeftHindLeg = leftHindLeg.addOrReplaceChild("lowerLeftHindLeg", CubeListBuilder.create().texOffs(45, 46).addBox(-1.0011F, -0.517F, -0.8984F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(0.1654F, 5.7725F, 0.179F, -0.2264F, 0.0157F, 0.0506F));
        lowerLeftHindLeg.addOrReplaceChild("leftHindPaw", CubeListBuilder.create().texOffs(44, 41).addBox(-1.0011F, -0.1875F, -1.8806F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.3F)), PartPose.offsetAndRotation(0.0184F, 4.2382F, -0.2545F, 0.3316F, 0.0F, 0.0F));
        
        PartDefinition rightHindLeg = pd.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(30, 54).addBox(-1.6313F, -0.4976F, -2.0755F, 3.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.2437F, 13.1851F, 6.0755F, -0.1046F, 0.0055F, 0.0521F));
        PartDefinition lowrRightHindLeg = rightHindLeg.addOrReplaceChild("lowrRightHindLeg", CubeListBuilder.create().texOffs(32, 46).addBox(-0.9989F, -0.517F, -0.8984F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(-0.1654F, 5.7725F, 0.179F, -0.2264F, -0.0157F, -0.0506F));
        lowrRightHindLeg.addOrReplaceChild("rightHindPaw", CubeListBuilder.create().texOffs(31, 41).addBox(-0.9989F, -0.1875F, -1.8806F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.3F)), PartPose.offsetAndRotation(-0.0184F, 4.2382F, -0.2545F, 0.3316F, 0.0F, 0.0F));

        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        root.getAllParts().forEach(ModelPart::resetPose);
        this.head.yRot = netHeadYaw * ((float)Math.PI / 180F);
        this.head.xRot = headPitch * ((float)Math.PI / 180F);

        float spread = 0.7F;
        float speed = 0.7F;
        if (entity.isSprinting()) {
            spread = 0.9F;
            speed = 0.9F;
        }

        this.rightFrontLeg.xRot = 0.1F + Mth.cos(limbSwing * speed) * spread * limbSwingAmount;
        this.leftFrontLeg.xRot = 0.1F + Mth.cos(limbSwing * speed) * spread * limbSwingAmount;
        this.rightHindLeg.xRot = -0.1F + Mth.cos(limbSwing * speed + (float)Math.PI) * spread * limbSwingAmount;
        this.leftHindLeg.xRot = -0.1F + Mth.cos(limbSwing * speed + (float)Math.PI) * spread * limbSwingAmount;

        this.tail.yRot += Math.sin(ageInTicks * 0.06) / 5.0F;
        this.tail.xRot += Math.sin(ageInTicks * 0.06) / 5.0F;
        this.tail2.yRot += Math.cos(ageInTicks * 0.06) / 7.0F;
        this.tail2.xRot += Math.cos(ageInTicks * 0.06) / 2.0F;
        this.tail3.yRot += Math.sin(ageInTicks * 0.06) / 7.0F;
        this.tail3.xRot += Math.sin(ageInTicks * 0.06) / 2.0F;

        if (entity.isSprinting()) {
            this.tail.xRot = (float)Math.sin(ageInTicks * 0.6) / 7.0F;
            this.tail.yRot = 0.0F;
            this.tail2.xRot = (float)Math.sin(ageInTicks * 0.6) / 10.0F;
            this.tail2.yRot = 0.0F;
            this.tail3.xRot = (float)Math.sin(ageInTicks * 0.6) / 12.0F;
            this.tail3.yRot = 0.0F;
        }
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        root.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }
}