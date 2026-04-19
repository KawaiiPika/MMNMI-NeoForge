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

public class BisonHeavyModel<T extends LivingEntity> extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "bison_heavy"), "main");
    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart rightArm;
    private final ModelPart leftArm;
    private final ModelPart rightLeg;
    private final ModelPart leftLeg;

    public BisonHeavyModel(ModelPart root) {
        this.root = root;
        this.head = root.getChild("head");
        this.body = root.getChild("body");
        this.rightArm = root.getChild("right_arm");
        this.leftArm = root.getChild("left_arm");
        this.rightLeg = root.getChild("right_leg");
        this.leftLeg = root.getChild("left_leg");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition pd = mesh.getRoot();

        PartDefinition head = pd.addOrReplaceChild("head", CubeListBuilder.create().texOffs(32, 31).addBox(-2.5F, -3.5236F, -4.3875F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.1745F, 0.0F, 0.0F));
        head.addOrReplaceChild("snout", CubeListBuilder.create().texOffs(37, 4).addBox(-1.5F, -1.5F, -2.0F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.2736F, -3.075F, 0.0873F, 0.0F, 0.0F));

        PartDefinition leftHorn = head.addOrReplaceChild("leftHorn", CubeListBuilder.create(), PartPose.offsetAndRotation(2.2197F, -3.1571F, -1.2264F, -0.1309F, 0.0F, -0.6981F));
        leftHorn.addOrReplaceChild("lefthorn1_r1", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-0.5F, -0.5F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -1.5708F));
        leftHorn.addOrReplaceChild("leftHornTip", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-0.49F, -2.115F, -0.51F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.99F, -0.385F, 0.01F));

        PartDefinition rightHorn = head.addOrReplaceChild("rightHorn", CubeListBuilder.create(), PartPose.offsetAndRotation(-2.2197F, -3.1571F, -1.2264F, -0.1309F, 0.0F, 0.6981F));
        rightHorn.addOrReplaceChild("righthorn_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.5708F));
        rightHorn.addOrReplaceChild("rightHornTip", CubeListBuilder.create().texOffs(0, 0).addBox(-0.51F, -2.115F, -0.51F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.99F, -0.385F, 0.01F));

        PartDefinition body = pd.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 10.0F, 2.0F, 0.0873F, 0.0F, 0.0F));
        body.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(0, 0).addBox(-5.5F, -10.9425F, -3.2198F, 11.0F, 11.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        body.addOrReplaceChild("hump", CubeListBuilder.create().texOffs(31, 13).addBox(-4.5F, -5.1132F, -3.5123F, 9.0F, 10.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -7.0F, 2.5F, 0.4363F, 0.0F, 0.0F));
        body.addOrReplaceChild("lowerBody", CubeListBuilder.create().texOffs(0, 23).addBox(-4.0F, -0.4634F, -2.6114F, 8.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.1745F, 0.0F, 0.0F));

        PartDefinition rightArm = pd.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(14, 32).addBox(-3.8091F, -0.4864F, -2.336F, 4.0F, 8.0F, 4.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(-4.75F, 1.0F, 1.0F, 0.2182F, 0.0F, 0.192F));
        rightArm.addOrReplaceChild("lowerRightArm", CubeListBuilder.create().texOffs(16, 45).addBox(-1.6014F, -0.3577F, -1.5625F, 3.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.7862F, 6.913F, -0.336F, -0.2618F, 0.0F, -0.192F));

        PartDefinition leftArm = pd.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(14, 32).mirror().addBox(-0.1909F, -0.4864F, -2.336F, 4.0F, 8.0F, 4.0F, new CubeDeformation(-0.01F)).mirror(false), PartPose.offsetAndRotation(4.75F, 1.0F, 1.0F, 0.2182F, 0.0F, -0.192F));
        leftArm.addOrReplaceChild("lowerLeftArm", CubeListBuilder.create().texOffs(16, 45).mirror().addBox(-1.3986F, -0.3577F, -1.5625F, 3.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.7862F, 6.913F, -0.336F, -0.2618F, 0.0F, 0.192F));

        PartDefinition rightLeg = pd.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 33).addBox(-1.5F, -0.7362F, -1.7242F, 3.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, 12.0F, 2.0F, -0.3491F, 0.0F, 0.0F));
        PartDefinition lowerRightLeg = rightLeg.addOrReplaceChild("lowerRightLeg", CubeListBuilder.create().texOffs(2, 43).addBox(-1.0F, -1.2214F, -0.3413F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(0.0F, 5.9517F, 0.4088F, 1.4399F, 0.0F, 0.0F));
        PartDefinition lowerRightLeg2 = lowerRightLeg.addOrReplaceChild("lowerRightLeg2", CubeListBuilder.create().texOffs(2, 49).addBox(-1.0F, -0.2661F, -1.3023F, 2.0F, 5.0F, 2.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(0.0F, 1.9941F, 0.3566F, -1.5184F, 0.0F, 0.0F));
        lowerRightLeg2.addOrReplaceChild("rightFoot", CubeListBuilder.create().texOffs(1, 57).addBox(-1.0F, -0.3186F, -1.9769F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0F, 4.3025F, -0.1388F, 0.4276F, 0.0F, 0.0F));

        PartDefinition leftLeg = pd.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 33).mirror().addBox(-1.5F, -0.7362F, -1.7242F, 3.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(3.0F, 12.0F, 2.0F, -0.3491F, 0.0F, 0.0F));
        PartDefinition lowerLeftLeg = leftLeg.addOrReplaceChild("lowerLeftLeg", CubeListBuilder.create().texOffs(2, 43).mirror().addBox(-1.0F, -1.2214F, -0.3413F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.2F)).mirror(false), PartPose.offsetAndRotation(0.0F, 5.9517F, 0.4088F, 1.4399F, 0.0F, 0.0F));
        PartDefinition lowerLeftLeg2 = lowerLeftLeg.addOrReplaceChild("lowerLeftLeg2", CubeListBuilder.create().texOffs(2, 49).mirror().addBox(-1.0F, -0.2661F, -1.3023F, 2.0F, 5.0F, 2.0F, new CubeDeformation(-0.01F)).mirror(false), PartPose.offsetAndRotation(0.0F, 1.9941F, 0.3566F, -1.5184F, 0.0F, 0.0F));
        lowerLeftLeg2.addOrReplaceChild("leftFoot", CubeListBuilder.create().texOffs(1, 57).mirror().addBox(-1.0F, -0.3186F, -1.9769F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(0.0F, 4.3025F, -0.1388F, 0.4276F, 0.0F, 0.0F));

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
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        root.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }
}