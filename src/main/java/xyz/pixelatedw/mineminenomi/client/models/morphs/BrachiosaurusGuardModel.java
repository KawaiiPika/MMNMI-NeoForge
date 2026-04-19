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

public class BrachiosaurusGuardModel<T extends LivingEntity> extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "brachiosaurus_guard"), "main");
    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart rightHindLeg;
    private final ModelPart leftHindLeg;
    private final ModelPart rightFrontLeg;
    private final ModelPart leftFrontLeg;
    private final ModelPart tail;
    private final ModelPart tail2;
    private final ModelPart tail3;

    public BrachiosaurusGuardModel(ModelPart root) {
        this.root = root;
        this.head = root.getChild("head");
        this.body = root.getChild("body");
        this.rightHindLeg = root.getChild("right_hind_leg");
        this.leftHindLeg = root.getChild("left_hind_leg");
        this.rightFrontLeg = root.getChild("right_front_leg");
        this.leftFrontLeg = root.getChild("left_front_leg");
        this.tail = body.getChild("tail");
        this.tail2 = tail.getChild("tail2");
        this.tail3 = tail2.getChild("tail3");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition pd = mesh.getRoot();

        PartDefinition rightHindLeg = pd.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(21, 119).addBox(-4.0F, 9.8079F, -2.2404F, 4.0F, 4.0F, 5.0F, new CubeDeformation(-0.01F)), PartPose.offset(-3.0F, 10.3009F, 6.3044F));
        rightHindLeg.addOrReplaceChild("rightHindLeg2", CubeListBuilder.create().texOffs(22, 92).addBox(-2.0F, -3.5F, -3.0F, 4.0F, 7.0F, 6.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-2.0F, 1.0F, 0.0F, -0.1745F, 0.0F, 0.0F));
        rightHindLeg.addOrReplaceChild("rightHindLeg3", CubeListBuilder.create().texOffs(21, 106).addBox(-2.0F, -3.5F, -2.5F, 4.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 6.9763F, -0.3474F, 0.1745F, 0.0F, 0.0F));

        PartDefinition leftHindLeg = pd.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(21, 119).addBox(0.0F, 9.8079F, -2.2404F, 4.0F, 4.0F, 5.0F, new CubeDeformation(-0.01F)), PartPose.offset(3.0F, 10.3009F, 6.3044F));
        leftHindLeg.addOrReplaceChild("leftHindLeg2", CubeListBuilder.create().texOffs(22, 92).addBox(-2.0F, -3.5F, -3.0F, 4.0F, 7.0F, 6.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(2.0F, 1.0F, 0.0F, -0.1745F, 0.0F, 0.0F));
        leftHindLeg.addOrReplaceChild("leftHindLeg3", CubeListBuilder.create().texOffs(21, 106).addBox(-2.0F, -3.5F, -2.5F, 4.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 6.9763F, -0.3474F, 0.1745F, 0.0F, 0.0F));

        PartDefinition rightFrontLeg = pd.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(0, 118).addBox(-4.0F, 9.9532F, -2.9875F, 4.0F, 4.0F, 5.0F, new CubeDeformation(0.01F)), PartPose.offset(-4.0F, 10.9056F, -7.0765F));
        rightFrontLeg.addOrReplaceChild("rightFrontLeg2", CubeListBuilder.create().texOffs(0, 91).addBox(-1.5F, -3.5F, -3.0F, 4.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.5F, 1.0F, 0.0F, 0.1745F, 0.0F, 0.0F));
        rightFrontLeg.addOrReplaceChild("rightFrontLeg3", CubeListBuilder.create().texOffs(0, 105).addBox(-2.0F, -3.5F, -2.5F, 4.0F, 7.0F, 5.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-2.0F, 6.9404F, 0.0823F, -0.1745F, 0.0F, 0.0F));

        PartDefinition leftFrontLeg = pd.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(0, 118).addBox(0.0F, 9.9532F, -2.9875F, 4.0F, 4.0F, 5.0F, new CubeDeformation(0.01F)), PartPose.offset(4.0F, 10.9056F, -7.0765F));
        leftFrontLeg.addOrReplaceChild("leftFrontLeg2", CubeListBuilder.create().texOffs(0, 91).addBox(-1.5F, -3.5F, -3.0F, 4.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, 1.0F, 0.0F, 0.1745F, 0.0F, 0.0F));
        leftFrontLeg.addOrReplaceChild("leftFrontLeg3", CubeListBuilder.create().texOffs(0, 105).addBox(-2.0F, -3.5F, -2.5F, 4.0F, 7.0F, 5.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(2.0F, 6.9404F, 0.0823F, -0.1745F, 0.0F, 0.0F));

        PartDefinition body = pd.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -8.375F, -4.0F, 10.0F, 17.0F, 14.0F, new CubeDeformation(0.0F)).texOffs(0, 32).addBox(-4.5F, -7.375F, -7.0F, 9.0F, 15.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(0, 51).addBox(-4.0F, -6.875F, -9.0F, 8.0F, 13.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(50, 0).addBox(-4.5F, -7.375F, 9.0F, 9.0F, 15.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(50, 19).addBox(-4.0F, -6.875F, 12.0F, 8.0F, 13.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(28, 33).addBox(-3.5F, -6.375F, 15.0F, 7.0F, 12.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 7.375F, -4.0F));
        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(77, 1).addBox(-2.0F, -3.0F, -1.0F, 4.0F, 6.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 18.0F));
        PartDefinition tail2 = tail.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(74, 18).addBox(-1.0F, -2.0F, 0.0F, 3.0F, 5.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, -0.75F, 6.0F));
        tail2.addOrReplaceChild("tail3", CubeListBuilder.create().texOffs(72, 38).addBox(0.0F, -1.0F, 0.0F, 2.0F, 4.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, -0.75F, 8.0F));

        PartDefinition head = pd.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 4.0F, -11.0F));
        PartDefinition neck = head.addOrReplaceChild("neck", CubeListBuilder.create(), PartPose.offset(-0.5F, -1.7431F, -0.7119F));
        neck.addOrReplaceChild("neck2", CubeListBuilder.create().texOffs(45, 112).addBox(-2.0F, -4.0F, -2.5F, 5.0F, 10.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.5359F, -2.0F, 0.5236F, 0.0F, 0.0F));

        return LayerDefinition.create(mesh, 128, 128);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        root.getAllParts().forEach(ModelPart::resetPose);
        float limbSpeed = 0.3F;
        if (entity.isSprinting()) {
            limbSpeed = 0.4F;
        }

        this.rightFrontLeg.xRot = Mth.cos(limbSwing * limbSpeed) * 0.3F * limbSwingAmount;
        this.leftFrontLeg.xRot = Mth.cos(limbSwing * limbSpeed) * 0.4F * limbSwingAmount;
        this.rightHindLeg.xRot = Mth.cos(limbSwing * limbSpeed + (float)Math.PI) * 0.3F * limbSwingAmount;
        this.leftHindLeg.xRot = Mth.cos(limbSwing * limbSpeed + (float)Math.PI) * 0.4F * limbSwingAmount;

        this.tail.yRot += Mth.sin(ageInTicks * 0.01F) / 20.0F;
        this.tail.xRot += Mth.sin(ageInTicks * 0.005F) / 10.0F;
        this.tail2.yRot += Mth.sin(ageInTicks * 0.01F) / 10.0F;
        this.tail2.xRot += Mth.sin(ageInTicks * 0.005F) / 5.0F;
        this.tail3.yRot += Mth.sin(ageInTicks * 0.01F) / 10.0F;
        this.tail3.xRot += Mth.sin(ageInTicks * 0.005F) / 5.0F;

        this.head.yRot = netHeadYaw * ((float)Math.PI / 180F);
        this.head.xRot = headPitch * ((float)Math.PI / 180F);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        root.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }
}