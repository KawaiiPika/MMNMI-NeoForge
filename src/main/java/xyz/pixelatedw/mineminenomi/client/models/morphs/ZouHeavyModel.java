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

public class ZouHeavyModel<T extends LivingEntity> extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "zou_heavy"), "main");

    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart rightArm;
    private final ModelPart leftArm;
    private final ModelPart rightLeg;
    private final ModelPart leftLeg;
    private final ModelPart snout;
    private final ModelPart snout2;
    private final ModelPart snout3;

    public ZouHeavyModel(ModelPart root) {
        this.root = root;
        this.head = root.getChild("head");
        this.body = root.getChild("body");
        this.rightArm = root.getChild("right_arm");
        this.leftArm = root.getChild("left_arm");
        this.rightLeg = root.getChild("right_leg");
        this.leftLeg = root.getChild("left_leg");
        this.snout = head.getChild("snout");
        this.snout2 = snout.getChild("snout2");
        this.snout3 = snout2.getChild("snout3");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition pd = mesh.getRoot();

        PartDefinition head = pd.addOrReplaceChild("head", CubeListBuilder.create().texOffs(94, 0).addBox(-4.0F, -6.0F, -7.0F, 8.0F, 11.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -9.875F, -1.4375F));
        PartDefinition snout = head.addOrReplaceChild("snout", CubeListBuilder.create().texOffs(94, 21).addBox(-2.0F, -3.671F, -2.9673F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.5F)), PartPose.offsetAndRotation(0.0F, 4.0F, -5.5F, -0.3054F, 0.0F, 0.0F));
        PartDefinition snout2 = snout.addOrReplaceChild("snout2", CubeListBuilder.create().texOffs(94, 21).addBox(-2.0F, -0.875F, -1.625F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(0.0F, 3.602F, -1.3293F, 0.2618F, 0.0F, 0.0F));
        snout2.addOrReplaceChild("snout3", CubeListBuilder.create().texOffs(94, 33).addBox(-2.0F, -0.9353F, -1.554F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 6.4876F, -0.0527F, 0.1745F, 0.0F, 0.0F));
        
        head.addOrReplaceChild("rightEar", CubeListBuilder.create().texOffs(52, 0).addBox(-8.0F, -4.5F, -0.5F, 8.0F, 9.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.5F, -2.5F, -0.5F, -0.1368F, 0.4707F, -0.2946F));
        head.addOrReplaceChild("leftEar", CubeListBuilder.create().texOffs(52, 0).mirror().addBox(0.0F, -4.5F, -0.5F, 8.0F, 9.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.5F, -2.5F, -0.5F, -0.1368F, -0.4707F, 0.2946F));
        
        head.addOrReplaceChild("leftTusk", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.3F, 3.0F, -6.0F, -0.3491F, 0.2094F, 0.0F));
        head.addOrReplaceChild("rightTusk", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-0.5F, 0.0F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.3F, 3.0F, -6.0F, -0.3491F, -0.2094F, 0.0F));

        PartDefinition body = pd.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 31).addBox(-8.5F, -12.3571F, -8.2065F, 17.0F, 14.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 11.1931F, 0.9075F, -0.0436F, 0.0F, 0.0F));
        PartDefinition body2 = body.addOrReplaceChild("body2", CubeListBuilder.create().texOffs(0, 0).addBox(-9.5F, -8.8743F, -6.9373F, 19.0F, 17.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -8.8009F, 0.5263F, 0.1309F, 0.0F, 0.0F));
        body2.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(84, 50).addBox(-6.0F, -4.0902F, -4.2988F, 12.0F, 4.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -7.3672F, -1.0712F, -0.0349F, 0.0F, 0.0F));

        pd.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(66, 2).addBox(-1.1044F, -1.9756F, -3.4356F, 6.0F, 10.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.6032F, -4.0641F, 1.0F, 0.0F, 0.0F, -0.1745F));
        pd.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(66, 2).mirror().addBox(-4.8956F, -1.9756F, -3.4356F, 6.0F, 10.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-9.6032F, -4.0641F, 1.0F, 0.0F, 0.0F, 0.1745F));

        pd.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(65, 36).addBox(-3.0F, 0.0262F, -2.9993F, 6.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, 11.1F, 1.0F, -0.1396F, 0.0F, 0.0F));
        pd.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(65, 36).mirror().addBox(-3.0F, 0.0262F, -2.9993F, 6.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-5.0F, 11.1F, 1.0F, -0.1396F, 0.0F, 0.0F));

        return LayerDefinition.create(mesh, 128, 64);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        root.getAllParts().forEach(ModelPart::resetPose);
        this.head.yRot = netHeadYaw * ((float)Math.PI / 180F);
        this.head.xRot = headPitch * ((float)Math.PI / 180F);

        float limbSpeed = 0.6662F;
        this.rightArm.xRot = Mth.cos(limbSwing * limbSpeed + (float)Math.PI) * 0.8F * limbSwingAmount * 0.5F;
        this.leftArm.xRot = Mth.cos(limbSwing * limbSpeed) * 0.8F * limbSwingAmount * 0.5F;
        this.rightLeg.xRot = -0.15F + Mth.cos(limbSwing * limbSpeed) * 0.7F * limbSwingAmount;
        this.leftLeg.xRot = -0.15F + Mth.cos(limbSwing * limbSpeed + (float)Math.PI) * 0.7F * limbSwingAmount;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        root.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }
}
