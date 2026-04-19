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

public class AxolotlHeavyModel<T extends LivingEntity> extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "axolotl_heavy"), "main");
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
    private final ModelPart tail4;

    public AxolotlHeavyModel(ModelPart root) {
        this.root = root;
        this.head = root.getChild("head");
        this.body = root.getChild("body");
        this.rightArm = root.getChild("right_arm");
        this.leftArm = root.getChild("left_arm");
        this.rightLeg = root.getChild("right_leg");
        this.leftLeg = root.getChild("left_leg");
        this.tail = root.getChild("tail");
        this.tail2 = tail.getChild("tail2");
        this.tail3 = tail2.getChild("tail3");
        this.tail4 = tail3.getChild("tail4");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition pd = mesh.getRoot();

        PartDefinition head = pd.addOrReplaceChild("head", CubeListBuilder.create().texOffs(28, 51).addBox(-8.0F, -10.9277F, 1.4656F, 16.0F, 9.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(64, 0).addBox(-4.0F, -6.4476F, -3.122F, 8.0F, 5.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -7.0F, -4.0F, 0.3491F, 0.0F, 0.0F));
        head.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(78, 13).addBox(-3.5F, -2.0F, -2.0F, 7.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.9277F, 0.4656F, 0.1745F, 0.0F, 0.0F));

        PartDefinition body = pd.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offsetAndRotation(4.0F, 14.0F, 35.0F, 0.3491F, 0.0F, 0.0F));
        body.addOrReplaceChild("backFin", CubeListBuilder.create().texOffs(82, 1).addBox(1.0F, -12.0F, -6.4F, 0.0F, 25.0F, 23.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, -27.0F, -19.0F, 0.0873F, 0.0F, 0.0F));
        body.addOrReplaceChild("upperBody", CubeListBuilder.create().texOffs(0, 8).addBox(-13.0F, -30.0F, -32.6F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(0, 8).addBox(2.0F, -30.0F, -32.6F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(47, 50).addBox(-15.0F, -36.3F, -31.3F, 22.0F, 3.0F, 13.0F, new CubeDeformation(0.0F)).texOffs(0, 0).addBox(-16.0F, -36.0F, -32.3F, 24.0F, 16.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0873F, 0.0F, 0.0F));
        body.addOrReplaceChild("hips", CubeListBuilder.create().texOffs(8, 32).addBox(-15.5F, -20.0F, -31.5F, 23.0F, 4.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0436F, 0.0F, 0.0F));
        body.addOrReplaceChild("lowerBody", CubeListBuilder.create().texOffs(47, 67).addBox(-13.0F, -10.0F, -10.0F, 18.0F, 10.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -7.0F, -25.0F, -0.48F, 0.0F, 0.0F));

        PartDefinition leftArm = pd.addOrReplaceChild("left_arm", CubeListBuilder.create(), PartPose.offsetAndRotation(12.0F, -4.0F, -1.0F, -0.4363F, 0.0F, 0.0F));
        leftArm.addOrReplaceChild("leftPunch", CubeListBuilder.create().texOffs(0, 104).mirror().addBox(-6.0F, -2.1134F, -4.5979F, 10.0F, 15.0F, 9.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(6.0F, 16.0F, 2.0F, 0.0843F, -0.0226F, 0.2608F));
        leftArm.addOrReplaceChild("leftArmMid", CubeListBuilder.create().texOffs(0, 71).mirror().addBox(-1.0F, -3.0F, -5.0F, 12.0F, 20.0F, 11.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0865F, 0.0114F, -0.1304F));

        PartDefinition rightArm = pd.addOrReplaceChild("right_arm", CubeListBuilder.create(), PartPose.offsetAndRotation(-12.0F, -4.0F, -1.0F, -0.4363F, 0.0F, 0.0F));
        rightArm.addOrReplaceChild("rightPunch", CubeListBuilder.create().texOffs(0, 104).addBox(-4.0F, -2.1134F, -4.5979F, 10.0F, 15.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.0F, 16.0F, 2.0F, 0.0843F, 0.0226F, -0.2608F));
        rightArm.addOrReplaceChild("rightArmMid", CubeListBuilder.create().texOffs(0, 71).addBox(-11.0F, -3.0F, -5.0F, 12.0F, 20.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0865F, -0.0114F, 0.1304F));

        PartDefinition leftLeg = pd.addOrReplaceChild("left_leg", CubeListBuilder.create(), PartPose.offsetAndRotation(7.0F, 14.25F, 3.0F, -0.2618F, 0.0F, 0.0F));
        leftLeg.addOrReplaceChild("lowerLeftLeg", CubeListBuilder.create().texOffs(105, 96).mirror().addBox(0.0F, 6.0F, -7.5F, 6.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-3.0F, -5.0F, 2.0F, 0.2618F, 0.0F, 0.0F));

        PartDefinition rightLeg = pd.addOrReplaceChild("right_leg", CubeListBuilder.create(), PartPose.offsetAndRotation(-7.0F, 14.25F, 3.0F, -0.2618F, 0.0F, 0.0F));
        rightLeg.addOrReplaceChild("lowerRightLeg", CubeListBuilder.create().texOffs(105, 96).addBox(-6.0F, 6.0F, -7.5F, 6.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, -5.0F, 2.0F, 0.2618F, 0.0F, 0.0F));

        PartDefinition tail = pd.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 11.0F, 13.0F, -0.1309F, 0.0F, 0.0F));
        tail.addOrReplaceChild("tail1_r1", CubeListBuilder.create().texOffs(39, 101).addBox(-3.5F, -8.5035F, -0.8045F, 7.0F, 8.0F, 19.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.1345F, -10.7133F, -0.3054F, 0.0F, 0.0F));

        PartDefinition tail2 = tail.addOrReplaceChild("tail2", CubeListBuilder.create(), PartPose.offset(0.0F, 5.5F, 8.0F));
        tail2.addOrReplaceChild("tail2_r1", CubeListBuilder.create().texOffs(98, 112).addBox(-2.5F, -9.0035F, 18.1955F, 5.0F, 6.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.2879F, -20.1948F, -0.1745F, 0.0F, 0.0F));

        PartDefinition tail3 = tail2.addOrReplaceChild("tail3", CubeListBuilder.create(), PartPose.offset(0.0F, 2.0F, 13.0F));
        tail3.addOrReplaceChild("tail3_r1_r1", CubeListBuilder.create().texOffs(95, 78).addBox(-1.5F, -8.0035F, 27.1955F, 3.0F, 4.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.8749F, -33.7109F, -0.0873F, 0.0F, 0.0F));

        PartDefinition tail4 = tail3.addOrReplaceChild("tail4", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -1.0035F, -0.8045F, 1.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, 6.0F));

        return LayerDefinition.create(mesh, 128, 128);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        root.getAllParts().forEach(ModelPart::resetPose);
        this.head.yRot = netHeadYaw * ((float)Math.PI / 180F);
        this.head.xRot = headPitch * ((float)Math.PI / 180F);

        float limbSpeed = 0.6662F;
        this.rightArm.xRot = -0.34F + Mth.cos(limbSwing * limbSpeed + (float)Math.PI) * 0.4F * limbSwingAmount;
        this.leftArm.xRot = -0.34F + Mth.cos(limbSwing * limbSpeed + (float)Math.PI) * 0.4F * limbSwingAmount;
        this.rightLeg.xRot = Mth.cos(limbSwing * limbSpeed) * 0.35F * limbSwingAmount;
        this.leftLeg.xRot = Mth.cos(limbSwing * limbSpeed) * 0.35F * limbSwingAmount;

        this.tail.yRot = Mth.sin(ageInTicks * 0.06F) / 10.0F;
        this.tail.xRot = Mth.sin(ageInTicks * 0.02F) / 10.0F;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        root.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }
}