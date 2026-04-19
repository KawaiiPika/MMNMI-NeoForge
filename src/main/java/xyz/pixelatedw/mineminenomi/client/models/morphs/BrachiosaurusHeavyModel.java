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

public class BrachiosaurusHeavyModel<T extends LivingEntity> extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "brachiosaurus_heavy"), "main");
    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart rightArm;
    private final ModelPart leftArm;
    private final ModelPart rightLeg;
    private final ModelPart leftLeg;
    private final ModelPart tail;
    private final ModelPart tail2;

    public BrachiosaurusHeavyModel(ModelPart root) {
        this.root = root;
        this.head = root.getChild("head");
        this.body = root.getChild("body");
        this.rightArm = root.getChild("right_arm");
        this.leftArm = root.getChild("left_arm");
        this.rightLeg = root.getChild("right_leg");
        this.leftLeg = root.getChild("left_leg");
        this.tail = root.getChild("tail");
        this.tail2 = tail.getChild("tail2");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition pd = mesh.getRoot();

        PartDefinition head = pd.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, -2.0F, -5.75F));
        PartDefinition headBase = head.addOrReplaceChild("headBase", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -11.75F, -9.0F, 5.0F, 6.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        headBase.addOrReplaceChild("headBase3_r1", CubeListBuilder.create().texOffs(0, 60).addBox(-2.5F, -3.5F, -1.5728F, 5.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -8.0F, -9.0F, -0.0873F, 0.0F, 0.0F));
        headBase.addOrReplaceChild("headBase1_r1", CubeListBuilder.create().texOffs(78, 31).addBox(-2.0F, -1.4992F, -2.5824F, 4.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -8.0392F, -6.2777F, 1.3963F, 0.0F, 0.0F));

        PartDefinition neck = head.addOrReplaceChild("neck", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -0.1878F));
        neck.addOrReplaceChild("neck2", CubeListBuilder.create().texOffs(29, 65).addBox(-2.5F, -4.0723F, -2.3627F, 5.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.3992F, 0.1235F, 0.48F, 0.0F, 0.0F));
        neck.addOrReplaceChild("neck3", CubeListBuilder.create().texOffs(93, 51).addBox(-2.0F, -3.7189F, -0.6203F, 4.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.9203F, -2.5618F, 0.5672F, 0.0F, 0.0F));
        neck.addOrReplaceChild("neck4", CubeListBuilder.create().texOffs(0, 96).addBox(-2.0F, -2.8466F, -1.9423F, 4.0F, 3.0F, 4.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(0.0F, -5.3328F, -2.302F, 0.9599F, 0.0F, 0.0F));
        neck.addOrReplaceChild("neck5", CubeListBuilder.create().texOffs(92, 23).addBox(-2.0F, -2.2568F, -1.8199F, 4.0F, 2.0F, 5.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, -6.4513F, -4.0017F, 1.2217F, 0.0F, 0.0F));

        PartDefinition upperMouth = head.addOrReplaceChild("upperMouth", CubeListBuilder.create().texOffs(0, 35).addBox(-2.0F, -2.4731F, -4.5535F, 4.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(0, 30).addBox(-2.0F, -2.4731F, -6.5535F, 4.0F, 3.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, -7.0F, -8.0F, -0.1309F, 0.0F, 0.0F));
        upperMouth.addOrReplaceChild("upperTeeth", CubeListBuilder.create().texOffs(0, 3).addBox(1.75F, 1.5F, -5.75F, 0.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(0, 2).addBox(-1.75F, 1.5F, -5.75F, 0.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(0, 13).addBox(-2.0F, 1.5F, -5.75F, 4.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.4731F, -0.5535F));

        PartDefinition lowerMouth = head.addOrReplaceChild("lowerMouth", CubeListBuilder.create().texOffs(0, 45).addBox(-1.5F, -0.5F, -3.0F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(0, 40).addBox(-2.0F, -0.5F, -1.0F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(0, 10).addBox(-2.0F, -0.5F, 1.0F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.3928F, -11.2171F, 0.2182F, 0.0F, 0.0F));
        lowerMouth.addOrReplaceChild("lowerTeeth", CubeListBuilder.create().texOffs(8, 44).addBox(-1.756F, -0.5F, 0.4F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(8, 43).addBox(-1.256F, -0.5F, -1.6F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(10, 9).addBox(1.764F, -0.5F, 0.4F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(10, 8).addBox(1.244F, -0.5F, -1.6F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(8, 13).addBox(-1.496F, -0.5F, -1.6F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.004F, -0.4557F, -1.2455F));

        PartDefinition leftArm = pd.addOrReplaceChild("left_arm", CubeListBuilder.create(), PartPose.offsetAndRotation(7.1414F, -1.5F, 0.7938F, 0.0F, 0.0F, -0.2618F));
        leftArm.addOrReplaceChild("leftArm2", CubeListBuilder.create().texOffs(0, 81).addBox(-1.5331F, -4.4646F, -2.0528F, 5.0F, 9.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.3917F, 3.4646F, -2.2409F, 0.1745F, 0.0F, 0.0F));
        leftArm.addOrReplaceChild("leftArm3", CubeListBuilder.create().texOffs(22, 81).addBox(-1.1991F, -4.5881F, -2.0528F, 5.0F, 9.0F, 6.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(1.0578F, 11.3381F, -2.2409F, -0.0873F, 0.0F, 0.0F));

        PartDefinition rightArm = pd.addOrReplaceChild("right_arm", CubeListBuilder.create(), PartPose.offsetAndRotation(-6.8586F, -1.5F, -1.4562F, 0.0F, 0.0F, 0.2618F));
        rightArm.addOrReplaceChild("rightArm2", CubeListBuilder.create().texOffs(0, 81).addBox(-1.1859F, -4.4382F, -1.7573F, 5.0F, 9.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.9555F, 3.4382F, -1.0365F, 0.1745F, 0.0F, 0.0F));
        rightArm.addOrReplaceChild("rightArm3", CubeListBuilder.create().texOffs(22, 81).addBox(-0.8519F, -4.5616F, -1.2573F, 5.0F, 9.0F, 6.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(-4.2895F, 11.3116F, -1.0365F, -0.0873F, 0.0F, 0.0F));

        PartDefinition rightLeg = pd.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(60, 92).addBox(-2.0F, 12.0F, -3.25F, 4.0F, 4.0F, 5.0F, new CubeDeformation(0.01F)), PartPose.offset(-6.5F, 8.0F, -1.0F));
        PartDefinition leftLeg = pd.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(60, 92).addBox(-2.0F, 12.0F, -3.25F, 4.0F, 4.0F, 5.0F, new CubeDeformation(0.01F)), PartPose.offset(6.5F, 8.0F, -1.0F));

        pd.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-8.5F, -7.8571F, -4.7857F, 17.0F, 16.0F, 14.0F, new CubeDeformation(0.0F)).texOffs(0, 45).addBox(-8.0F, -9.3571F, -4.2857F, 16.0F, 2.0F, 13.0F, new CubeDeformation(0.0F)).texOffs(50, 18).addBox(-7.5F, -10.1071F, -3.5357F, 15.0F, 1.0F, 12.0F, new CubeDeformation(0.0F)).texOffs(44, 65).addBox(-7.0F, -10.8571F, -3.0357F, 14.0F, 1.0F, 11.0F, new CubeDeformation(0.0F)).texOffs(0, 30).addBox(-8.0F, 7.1429F, -4.2857F, 16.0F, 2.0F, 13.0F, new CubeDeformation(0.0F)).texOffs(48, 0).addBox(-7.5F, 8.8929F, -3.7857F, 15.0F, 1.0F, 12.0F, new CubeDeformation(0.0F)).texOffs(45, 31).addBox(-6.0F, -11.8571F, -2.0357F, 12.0F, 1.0F, 9.0F, new CubeDeformation(0.0F)).texOffs(68, 41).addBox(-7.0F, -6.8571F, -5.7857F, 14.0F, 14.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(44, 77).addBox(-6.5F, -5.8571F, -6.5357F, 13.0F, 12.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 5.8571F, -3.2143F));

        PartDefinition tail = pd.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 60).addBox(-3.5F, -1.5F, -0.1667F, 7.0F, 6.0F, 15.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(0.0F, 11.5F, 4.1667F, -0.3491F, 0.0F, 0.0F));
        tail.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(39, 41).addBox(-2.5F, -1.0F, 0.5F, 5.0F, 5.0F, 19.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(0.0F, 0.25F, 13.3333F, 0.2182F, 0.0F, 0.0F));

        return LayerDefinition.create(mesh, 128, 128);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        root.getAllParts().forEach(ModelPart::resetPose);
        this.rightArm.xRot = Mth.cos(limbSwing * 0.6662F + (float)Math.PI) * 0.4F * limbSwingAmount * 0.5F;
        this.leftArm.xRot = Mth.cos(limbSwing * 0.6662F) * 0.4F * limbSwingAmount * 0.5F;
        this.rightLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 0.3F * limbSwingAmount;
        this.leftLeg.xRot = Mth.cos(limbSwing * 0.6662F + (float)Math.PI) * 0.3F * limbSwingAmount;

        if (entity.isSprinting()) {
            this.tail.xRot = -0.3491F + Mth.cos(limbSwing * 0.6662F) * 0.2F * limbSwingAmount;
        }

        this.head.yRot = netHeadYaw * ((float)Math.PI / 180F);
        this.head.xRot = headPitch * ((float)Math.PI / 180F);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        root.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }
}