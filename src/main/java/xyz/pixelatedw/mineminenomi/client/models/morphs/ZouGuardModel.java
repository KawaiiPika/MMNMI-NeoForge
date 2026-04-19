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
import net.minecraft.world.entity.player.Player;

public class ZouGuardModel<T extends LivingEntity> extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "zou_guard"), "main");

    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart leftFrontLeg;
    private final ModelPart rightFrontLeg;
    private final ModelPart leftHindLeg;
    private final ModelPart rightHindLeg;
    private final ModelPart snout;
    private final ModelPart snout2;
    private final ModelPart snout3;
    private final ModelPart leftEar;
    private final ModelPart rightEar;
    private final ModelPart tail;

    public ZouGuardModel(ModelPart root) {
        this.root = root;
        this.head = root.getChild("head");
        this.body = root.getChild("body");
        this.leftFrontLeg = root.getChild("left_front_leg");
        this.rightFrontLeg = root.getChild("right_front_leg");
        this.leftHindLeg = root.getChild("left_hind_leg");
        this.rightHindLeg = root.getChild("right_hind_leg");
        this.snout = head.getChild("snout");
        this.snout2 = snout.getChild("snout2");
        this.snout3 = snout2.getChild("snout3");
        this.leftEar = head.getChild("leftEar");
        this.rightEar = head.getChild("rightEar");
        this.tail = body.getChild("tail");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition pd = mesh.getRoot();

        PartDefinition body = pd.addOrReplaceChild("body", CubeListBuilder.create().texOffs(36, 25).addBox(-7.5F, -12.0F, -8.0F, 15.0F, 15.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 9.0F, -4.0F));
        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(108, 0).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -7.0F, 15.75F, 0.3491F, 0.0F, 0.0F));
        tail.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(113, 0).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 5.5F, 0.0F));
        
        body.addOrReplaceChild("body4", CubeListBuilder.create().texOffs(61, 49).addBox(-6.5F, -7.0F, -0.5F, 13.0F, 14.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -4.5F, 16.0F));
        body.addOrReplaceChild("body5", CubeListBuilder.create().texOffs(61, 49).addBox(-6.5F, -7.0F, -0.5F, 13.0F, 14.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -4.5F, -8.25F));
        body.addOrReplaceChild("body3", CubeListBuilder.create().texOffs(36, 0).addBox(-6.5F, -1.0F, -11.0F, 13.0F, 2.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.5F, 4.0F));
        body.addOrReplaceChild("body2", CubeListBuilder.create().texOffs(36, 0).addBox(-6.5F, -13.0F, -7.0F, 13.0F, 2.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        pd.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(0, 46).addBox(-2.25F, 0.0F, -2.75F, 5.0F, 13.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, 11.0F, 9.5F));
        pd.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(0, 46).addBox(-2.75F, 0.0F, -2.75F, 5.0F, 13.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, 11.0F, 9.5F));
        pd.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(0, 46).addBox(-2.25F, 0.0F, -2.25F, 5.0F, 13.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, 11.0F, -9.5F));
        pd.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(0, 46).addBox(-2.75F, 0.0F, -2.25F, 5.0F, 13.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, 11.0F, -9.5F));

        PartDefinition head = pd.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -6.0F, -7.0F, 8.0F, 11.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.875F, -12.4375F));
        PartDefinition snout = head.addOrReplaceChild("snout", CubeListBuilder.create().texOffs(108, 8).addBox(-2.0F, -3.671F, -2.9673F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.5F)), PartPose.offsetAndRotation(0.0F, 4.0F, -5.5F, -0.3054F, 0.0F, 0.0F));
        PartDefinition snout2 = snout.addOrReplaceChild("snout2", CubeListBuilder.create().texOffs(108, 8).addBox(-2.0F, -0.875F, -1.625F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(0.0F, 3.602F, -1.3293F, 0.2618F, 0.0F, 0.0F));
        snout2.addOrReplaceChild("snout3", CubeListBuilder.create().texOffs(108, 20).addBox(-2.0F, -0.9353F, -1.554F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 6.4876F, -0.0527F, 0.1745F, 0.0F, 0.0F));
        
        head.addOrReplaceChild("rightEar", CubeListBuilder.create().texOffs(0, 22).addBox(-8.0F, -4.5F, -0.5F, 8.0F, 9.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.5F, -2.5F, -2.75F, -0.1368F, 0.4707F, -0.2946F));
        head.addOrReplaceChild("leftEar", CubeListBuilder.create().texOffs(0, 22).mirror().addBox(0.0F, -4.5F, -0.5F, 8.0F, 9.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.5F, -2.5F, -2.75F, -0.1368F, -0.4707F, 0.2946F));
        
        PartDefinition leftTusk = head.addOrReplaceChild("leftTusk", CubeListBuilder.create().texOffs(0, 33).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.3F, 3.0F, -6.0F, -0.3491F, 0.2094F, 0.0F));
        leftTusk.addOrReplaceChild("leftTusk2", CubeListBuilder.create().texOffs(0, 33).addBox(-0.461F, -0.1505F, -0.5192F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.039F, 4.0647F, 4.0E-4F, -0.1745F, 0.0F, 0.0F));
        
        PartDefinition rightTusk = head.addOrReplaceChild("rightTusk", CubeListBuilder.create().texOffs(0, 33).mirror().addBox(-0.5F, 0.0F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.3F, 3.0F, -6.0F, -0.3491F, -0.2094F, 0.0F));
        rightTusk.addOrReplaceChild("rightTusk2", CubeListBuilder.create().texOffs(0, 33).mirror().addBox(-0.539F, -0.1505F, -0.5192F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.039F, 4.0647F, 4.0E-4F, -0.1745F, 0.0F, 0.0F));

        return LayerDefinition.create(mesh, 128, 64);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        root.getAllParts().forEach(ModelPart::resetPose);
        this.head.yRot = netHeadYaw * ((float)Math.PI / 180F);
        this.head.xRot = headPitch * ((float)Math.PI / 180F);

        float limbSpeed = 0.6662F;
        this.rightFrontLeg.xRot = Mth.cos(limbSwing * limbSpeed) * 0.3F * limbSwingAmount;
        this.leftFrontLeg.xRot = Mth.cos(limbSwing * limbSpeed + (float)Math.PI) * 0.3F * limbSwingAmount;
        this.rightHindLeg.xRot = Mth.cos(limbSwing * limbSpeed) * 0.4F * limbSwingAmount;
        this.leftHindLeg.xRot = Mth.cos(limbSwing * limbSpeed + (float)Math.PI) * 0.4F * limbSwingAmount;

        if (entity.isSprinting()) {
            this.tail.xRot = 0.6F + Mth.cos(limbSwing * 0.6662F) * 0.2F * limbSwingAmount;
            this.leftEar.zRot = -0.3F - Mth.cos(limbSwing * 0.6662F) * 0.2F * limbSwingAmount;
            this.rightEar.zRot = 0.3F + Mth.cos(limbSwing * 0.6662F) * 0.2F * limbSwingAmount;
        }

        boolean isFlying = false;
        if (entity instanceof Player player) {
            isFlying = player.getAbilities().flying;
        }

        if (isFlying) {
            this.rightEar.xRot = 1.2F;
            this.rightEar.yRot = 0.1F;
            this.rightEar.zRot = Mth.cos(ageInTicks) * 0.6F;
            this.leftEar.xRot = 1.2F;
            this.leftEar.yRot = -0.1F;
            this.leftEar.zRot = -Mth.cos(ageInTicks) * 0.6F;
        }
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        root.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }
}
