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

public class MoguHeavyModel<T extends LivingEntity> extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "mogu_heavy"), "main");

    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart rightArm;
    private final ModelPart leftArm;
    private final ModelPart rightLeg;
    private final ModelPart leftLeg;

    public MoguHeavyModel(ModelPart root) {
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

        PartDefinition head = pd.addOrReplaceChild("head", CubeListBuilder.create().texOffs(2, 3).addBox(-2.375F, -4.9965F, -2.1219F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.25F, 6.8715F, 0.2469F));
        head.addOrReplaceChild("leftEye", CubeListBuilder.create().texOffs(18, 5).addBox(0.5625F, -9.1875F, -3.9375F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0625F, 5.191F, 1.7907F));
        head.addOrReplaceChild("rightEye", CubeListBuilder.create().texOffs(18, 5).mirror().addBox(-2.5625F, -9.1875F, -3.9375F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.1875F, 5.191F, 1.7907F));
        PartDefinition nose = head.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(0, 5).addBox(-1.0F, -0.661F, -0.0027F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(0.125F, -1.4029F, -2.795F, -0.0873F, 0.0F, 0.0F));
        PartDefinition nose2 = nose.addOrReplaceChild("nose2", CubeListBuilder.create().texOffs(0, 2).addBox(-1.0F, -0.6651F, 0.6625F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.05F)), PartPose.offsetAndRotation(0.0F, -0.1314F, -1.4981F, -0.0873F, 0.0F, 0.0F));
        nose2.addOrReplaceChild("nose3", CubeListBuilder.create().texOffs(1, 0).addBox(-0.5F, -1.0436F, -0.2019F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(0.0F, -0.0966F, 0.3144F, -0.0873F, 0.0F, 0.0F));

        PartDefinition leftArm = pd.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(24, 34).mirror().addBox(-0.75F, -1.5F, -2.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(4.6326F, 7.5362F, 2.2948F, 0.0516F, 0.0091F, -0.1743F));
        PartDefinition lowerLeftArm = leftArm.addOrReplaceChild("lowerLeftArm", CubeListBuilder.create().texOffs(23, 48).mirror().addBox(-1.9375F, -0.0214F, -2.1737F, 4.0F, 5.0F, 5.0F, new CubeDeformation(0.2F)).mirror(false), PartPose.offsetAndRotation(1.2424F, 6.3714F, -0.2691F, -0.1309F, 0.0F, 0.0F));
        
        PartDefinition leftArmClaw1 = lowerLeftArm.addOrReplaceChild("leftArmClaw1", CubeListBuilder.create().texOffs(18, 45).mirror().addBox(-0.5F, 0.0F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.2F)).mirror(false), PartPose.offsetAndRotation(-0.7474F, 3.7877F, -2.1621F, -0.3054F, 0.0F, 0.0F));
        PartDefinition leftArmClaw1b = leftArmClaw1.addOrReplaceChild("leftArmClaw1b", CubeListBuilder.create().texOffs(18, 41).addBox(-0.5507F, -0.076F, -0.5417F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0248F, 2.9761F, 0.0163F, 0.2618F, 0.0F, 0.0F));
        leftArmClaw1b.addOrReplaceChild("leftArmClaw1c", CubeListBuilder.create().texOffs(18, 37).addBox(-0.5507F, -0.5745F, -0.5325F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0162F, 1.9507F, -0.0258F, 0.1309F, 0.0F, 0.0F));

        PartDefinition leftArmClaw2 = lowerLeftArm.addOrReplaceChild("leftArmClaw2", CubeListBuilder.create().texOffs(18, 45).mirror().addBox(-0.3839F, -0.718F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.2F)).mirror(false), PartPose.offsetAndRotation(1.895F, 4.3238F, -1.5287F, 0.0F, 0.0F, -0.1743F));
        PartDefinition leftArmClaw2b = leftArmClaw2.addOrReplaceChild("leftArmClaw2b", CubeListBuilder.create().texOffs(18, 41).mirror().addBox(-0.4494F, 0.0065F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(0.1062F, 2.3638F, -0.0249F, 0.0F, 0.0F, 0.1745F));
        leftArmClaw2b.addOrReplaceChild("leftArmClaw2c", CubeListBuilder.create().texOffs(18, 37).mirror().addBox(-0.4597F, -0.3657F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0026F, 2.0696F, -0.0204F, 0.0F, 0.0F, 0.2182F));

        PartDefinition leftArmClaw3 = lowerLeftArm.addOrReplaceChild("leftArmClaw3", CubeListBuilder.create().texOffs(18, 45).mirror().addBox(-0.3839F, -0.718F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.2F)).mirror(false), PartPose.offsetAndRotation(1.895F, 4.3238F, 0.2838F, 0.0F, 0.0F, -0.1743F));
        PartDefinition leftArmClaw3b = leftArmClaw3.addOrReplaceChild("leftArmClaw3b", CubeListBuilder.create().texOffs(18, 41).mirror().addBox(-0.4494F, 0.0065F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(0.1062F, 2.3638F, -0.0249F, 0.0F, 0.0F, 0.1745F));
        leftArmClaw3b.addOrReplaceChild("leftArmClaw3c", CubeListBuilder.create().texOffs(18, 37).mirror().addBox(-0.4597F, -0.2407F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0026F, 2.0696F, -0.0204F, 0.0F, 0.0F, 0.2182F));

        PartDefinition leftArmClaw4 = lowerLeftArm.addOrReplaceChild("leftArmClaw4", CubeListBuilder.create().texOffs(18, 45).mirror().addBox(-0.3839F, -0.718F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.2F)).mirror(false), PartPose.offsetAndRotation(1.895F, 4.3238F, 2.1588F, 0.0F, 0.0F, -0.1743F));
        PartDefinition leftArmClaw4b = leftArmClaw4.addOrReplaceChild("leftArmClaw4b", CubeListBuilder.create().texOffs(18, 41).mirror().addBox(-0.4494F, 0.0065F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(0.1062F, 2.3638F, -0.0249F, 0.0F, 0.0F, 0.1745F));
        leftArmClaw4b.addOrReplaceChild("leftArmClaw4c", CubeListBuilder.create().texOffs(18, 37).mirror().addBox(-0.4597F, -0.4907F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0026F, 2.0696F, -0.0204F, 0.0F, 0.0F, 0.2182F));

        PartDefinition rightArm = pd.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(24, 34).addBox(-3.25F, -1.5F, -2.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.6326F, 7.5362F, 2.2948F, 0.0516F, -0.0091F, 0.1743F));
        PartDefinition lowerRightArm = rightArm.addOrReplaceChild("lowerRightArm", CubeListBuilder.create().texOffs(23, 48).addBox(-2.0625F, -0.0214F, -2.1737F, 4.0F, 5.0F, 5.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(-1.2424F, 6.3714F, -0.2691F, -0.1309F, 0.0F, 0.0F));
        
        PartDefinition rightArmClaw1 = lowerRightArm.addOrReplaceChild("rightArmClaw1", CubeListBuilder.create().texOffs(18, 45).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(0.7474F, 3.7877F, -2.1621F, -0.3054F, 0.0F, 0.0F));
        PartDefinition rightArmClaw1b = rightArmClaw1.addOrReplaceChild("rightArmClaw1b", CubeListBuilder.create().texOffs(18, 41).mirror().addBox(-0.4493F, -0.076F, -0.5417F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(-0.0248F, 2.9761F, 0.0163F, 0.2618F, 0.0F, 0.0F));
        rightArmClaw1b.addOrReplaceChild("rightArmClaw1c", CubeListBuilder.create().texOffs(18, 37).mirror().addBox(-0.4493F, -0.5745F, -0.5325F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0162F, 1.9507F, -0.0258F, 0.1309F, 0.0F, 0.0F));

        PartDefinition rightArmClaw2 = lowerRightArm.addOrReplaceChild("rightArmClaw2", CubeListBuilder.create().texOffs(18, 45).addBox(-0.6161F, -0.718F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(-1.895F, 4.3238F, -1.5287F, 0.0F, 0.0F, 0.1743F));
        PartDefinition rightArmClaw2b = rightArmClaw2.addOrReplaceChild("rightArmClaw2b", CubeListBuilder.create().texOffs(18, 41).addBox(-0.5505F, 0.0065F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-0.1062F, 2.3638F, -0.0249F, 0.0F, 0.0F, -0.1745F));
        rightArmClaw2b.addOrReplaceChild("rightArmClaw2c", CubeListBuilder.create().texOffs(18, 37).addBox(-0.5403F, -0.3657F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0026F, 2.0696F, -0.0204F, 0.0F, 0.0F, -0.2182F));

        PartDefinition rightArmClaw3 = lowerRightArm.addOrReplaceChild("rightArmClaw3", CubeListBuilder.create().texOffs(18, 45).addBox(-0.6161F, -0.718F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(-1.895F, 4.3238F, 0.2838F, 0.0F, 0.0F, 0.1743F));
        PartDefinition rightArmClaw3b = rightArmClaw3.addOrReplaceChild("rightArmClaw3b", CubeListBuilder.create().texOffs(18, 41).addBox(-0.5505F, 0.0065F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-0.1062F, 2.3638F, -0.0249F, 0.0F, 0.0F, -0.1745F));
        rightArmClaw3b.addOrReplaceChild("rightArmClaw3c", CubeListBuilder.create().texOffs(18, 37).addBox(-0.5403F, -0.2407F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0026F, 2.0696F, -0.0204F, 0.0F, 0.0F, -0.2182F));

        PartDefinition rightArmClaw4 = lowerRightArm.addOrReplaceChild("rightArmClaw4", CubeListBuilder.create().texOffs(18, 45).addBox(-0.6161F, -0.718F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(-1.895F, 4.3238F, 2.1588F, 0.0F, 0.0F, 0.1743F));
        PartDefinition rightArmClaw4b = rightArmClaw4.addOrReplaceChild("rightArmClaw4b", CubeListBuilder.create().texOffs(18, 41).addBox(-0.5505F, 0.0065F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-0.1062F, 2.3638F, -0.0249F, 0.0F, 0.0F, -0.1745F));
        rightArmClaw4b.addOrReplaceChild("rightArmClaw4c", CubeListBuilder.create().texOffs(18, 37).addBox(-0.5403F, -0.4907F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0026F, 2.0696F, -0.0204F, 0.0F, 0.0F, -0.2182F));

        pd.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 28).addBox(-4.0F, -15.0F, -2.375F, 8.0F, 3.0F, 5.0F, new CubeDeformation(0.1F)).texOffs(0, 14).addBox(-4.4375F, -21.0F, -2.9375F, 9.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 27.5625F, 2.0F));

        PartDefinition leftLeg = pd.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 39).addBox(-2.0F, -0.5F, -2.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.1F)), PartPose.offset(2.65F, 15.5F, 2.0F));
        PartDefinition lowerLeftLeg = leftLeg.addOrReplaceChild("lowerLeftLeg", CubeListBuilder.create().texOffs(2, 49).addBox(-1.5F, -2.5F, -1.5F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.2F)), PartPose.offset(0.0F, 4.5625F, 0.0F));
        lowerLeftLeg.addOrReplaceChild("leftFoot", CubeListBuilder.create().texOffs(0, 58).addBox(-1.5F, -0.5F, -3.5F, 3.0F, 1.0F, 5.0F, new CubeDeformation(0.4F)), PartPose.offset(0.0F, 3.0F, 0.0F));

        PartDefinition rightLeg = pd.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 39).mirror().addBox(-2.0F, -0.5F, -2.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offset(-2.65F, 15.5F, 2.0F));
        PartDefinition lowerRightLeg = rightLeg.addOrReplaceChild("lowerRightLeg", CubeListBuilder.create().texOffs(2, 49).mirror().addBox(-1.5F, -2.5F, -1.5F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.2F)).mirror(false), PartPose.offset(0.0F, 4.5625F, 0.0F));
        lowerRightLeg.addOrReplaceChild("rightFoot", CubeListBuilder.create().texOffs(0, 58).mirror().addBox(-1.5F, -0.5F, -3.5F, 3.0F, 1.0F, 5.0F, new CubeDeformation(0.4F)).mirror(false), PartPose.offset(0.0F, 3.0F, 0.0F));

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
        this.rightLeg.xRot = Mth.cos(limbSwing * limbSpeed) * 0.7F * limbSwingAmount;
        this.leftLeg.xRot = Mth.cos(limbSwing * limbSpeed + (float)Math.PI) * 0.7F * limbSwingAmount;

        if (entity.isCrouching()) {
            this.head.y = 11.0F;
            this.head.z = -3.0F;
            this.body.y = 9.0F;
            this.body.xRot = 0.5F;
            this.rightLeg.y = 17.0F;
            this.leftLeg.y = 17.0F;
            this.rightArm.y = 10.0F;
            this.rightArm.z = 0.0F;
            this.leftArm.y = 10.0F;
            this.leftArm.z = 0.0F;
        }
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        root.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }
}