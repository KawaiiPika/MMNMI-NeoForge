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

public class BisonWalkModel<T extends LivingEntity> extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "bison_walk"), "main");
    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart rightHindLeg;
    private final ModelPart leftHindLeg;
    private final ModelPart rightFrontLeg;
    private final ModelPart leftFrontLeg;
    private final ModelPart tail;

    public BisonWalkModel(ModelPart root) {
        this.root = root;
        this.head = root.getChild("head");
        this.body = root.getChild("body");
        this.rightHindLeg = root.getChild("right_hind_leg");
        this.leftHindLeg = root.getChild("left_hind_leg");
        this.rightFrontLeg = root.getChild("right_front_leg");
        this.leftFrontLeg = root.getChild("left_front_leg");
        this.tail = body.getChild("backBody").getChild("tail");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition pd = mesh.getRoot();

        PartDefinition head = pd.addOrReplaceChild("head", CubeListBuilder.create().texOffs(13, 29).addBox(-2.5F, -2.5F, -5.0F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 12.0486F, -8.5856F, 0.2618F, 0.0F, 0.0F));
        PartDefinition snout = head.addOrReplaceChild("snout", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -0.0862F, -4.3249F, 0.9163F, 0.0F, 0.0F));
        snout.addOrReplaceChild("snout1_r1", CubeListBuilder.create().texOffs(11, 40).addBox(-2.0F, -2.0F, -2.55F, 4.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -1.0F, -0.1309F, 0.0F, 0.0F));

        PartDefinition leftHorn = head.addOrReplaceChild("leftHorn", CubeListBuilder.create(), PartPose.offsetAndRotation(2.2197F, -2.1335F, -1.6514F, -0.0457F, -0.3051F, -0.6844F));
        leftHorn.addOrReplaceChild("lefthorn1_r1", CubeListBuilder.create().texOffs(34, 5).addBox(-0.5F, -1.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 0.0F, 0.0F, 0.0F, 0.0F, -1.5708F));
        leftHorn.addOrReplaceChild("leftHorn2", CubeListBuilder.create().texOffs(34, 5).addBox(-0.49F, -2.115F, -0.51F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.99F, -0.385F, 0.01F));

        PartDefinition rightHorn = head.addOrReplaceChild("rightHorn", CubeListBuilder.create(), PartPose.offsetAndRotation(-2.2197F, -2.1335F, -1.6514F, -0.0457F, 0.3051F, 0.6844F));
        rightHorn.addOrReplaceChild("righthorn_r1", CubeListBuilder.create().texOffs(34, 5).addBox(-0.5F, -1.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 0.0F, 0.0F, 0.0F, 0.0F, 1.5708F));
        rightHorn.addOrReplaceChild("rightHorn2", CubeListBuilder.create().texOffs(34, 5).addBox(-0.51F, -2.115F, -0.51F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.99F, -0.385F, 0.01F));

        pd.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(0, 29).addBox(-1.5F, -3.0F, -1.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(2.75F, 17.0F, -6.0625F));
        pd.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(0, 29).addBox(-1.5F, -3.0F, -1.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.75F, 17.0F, -6.0625F));
        pd.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(0, 39).addBox(-1.0F, -3.1875F, -1.0F, 2.0F, 10.0F, 2.0F, new CubeDeformation(0.2F)), PartPose.offset(2.75F, 16.6875F, 5.9375F));
        pd.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(0, 39).addBox(-1.0F, -3.1875F, -1.0F, 2.0F, 10.0F, 2.0F, new CubeDeformation(0.2F)), PartPose.offset(-2.75F, 16.6875F, 5.9375F));

        PartDefinition body = pd.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(0.5F, 0.0625F, -7.0F, 8.0F, 8.0F, 11.0F, new CubeDeformation(0.3F)), PartPose.offset(-4.5F, 9.0F, -2.0625F));
        body.addOrReplaceChild("hump", CubeListBuilder.create().texOffs(32, 17).addBox(-3.5F, -2.5F, -4.5F, 7.0F, 5.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.5F, 1.0181F, -2.1693F, -0.3927F, 0.0F, 0.0F));
        PartDefinition backBody = body.addOrReplaceChild("backBody", CubeListBuilder.create().texOffs(32, 33).addBox(-4.0F, -3.7298F, -1.6068F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.5F, 3.9173F, 4.1693F, -0.0873F, 0.0F, 0.0F));
        backBody.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(29, 2).addBox(-0.5F, -0.1272F, -0.4762F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.3923F, 6.0182F, 0.3491F, 0.0F, 0.0F));

        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        root.getAllParts().forEach(ModelPart::resetPose);
        this.head.yRot = netHeadYaw * ((float)Math.PI / 180F);
        this.head.xRot = headPitch * ((float)Math.PI / 180F);

        float limbSpeed = 0.9F;
        this.rightFrontLeg.xRot = Mth.cos(limbSwing * limbSpeed) * 0.5F * limbSwingAmount;
        this.leftFrontLeg.xRot = Mth.cos(limbSwing * limbSpeed) * 0.5F * limbSwingAmount;
        this.rightHindLeg.xRot = Mth.cos(limbSwing * limbSpeed + (float)Math.PI) * 0.5F * limbSwingAmount;
        this.leftHindLeg.xRot = Mth.cos(limbSwing * limbSpeed + (float)Math.PI) * 0.5F * limbSwingAmount;

        if (entity.isSprinting()) {
            this.tail.xRot = 1.2F + Mth.cos(limbSwing * 0.6662F) * 0.2F * limbSwingAmount;
        }
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        root.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }
}