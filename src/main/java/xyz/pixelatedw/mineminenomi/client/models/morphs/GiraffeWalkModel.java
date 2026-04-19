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

public class GiraffeWalkModel<T extends LivingEntity> extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "giraffe_walk"), "main");

    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart neck2;
    private final ModelPart face;
    private final ModelPart face2;
    private final ModelPart leftEar;
    private final ModelPart rightEar;
    private final ModelPart body;
    private final ModelPart tail1;
    private final ModelPart leftFrontLeg;
    private final ModelPart rightFrontLeg;
    private final ModelPart leftHindLeg;
    private final ModelPart rightHindLeg;
    private final ModelPart leftLowerFrontLeg;
    private final ModelPart rightLowerFrontLeg;
    private final ModelPart leftLowerHindLeg;
    private final ModelPart rightLowerHindLeg;

    public GiraffeWalkModel(ModelPart root) {
        this.root = root;
        this.head = root.getChild("head");
        this.neck2 = head.getChild("neck2");
        this.face = neck2.getChild("face");
        this.face2 = face.getChild("face2");
        this.leftEar = face2.getChild("leftEar");
        this.rightEar = face2.getChild("rightEar");
        this.body = root.getChild("body");
        this.tail1 = body.getChild("tail1");
        this.leftFrontLeg = root.getChild("left_front_leg");
        this.rightFrontLeg = root.getChild("right_front_leg");
        this.leftHindLeg = root.getChild("left_hind_leg");
        this.rightHindLeg = root.getChild("right_hind_leg");
        this.leftLowerFrontLeg = leftFrontLeg.getChild("leftLowerFrontLeg");
        this.rightLowerFrontLeg = rightFrontLeg.getChild("rightLowerFrontLeg");
        this.leftLowerHindLeg = leftHindLeg.getChild("leftLowerHindLeg");
        this.rightLowerHindLeg = rightHindLeg.getChild("rightLowerHindLeg");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition pd = mesh.getRoot();

        PartDefinition body = pd.addOrReplaceChild("body", CubeListBuilder.create().texOffs(18, 18).addBox(-6.0F, -10.4346F, -6.7471F, 10.0F, 16.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 7.0F, 3.0F, 1.4835F, 0.0F, 0.0F));
        body.addOrReplaceChild("tail1", CubeListBuilder.create().texOffs(58, 28).addBox(-0.5F, 0.4821F, -0.5745F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 4.8247F, 1.0142F, -1.0472F, 0.0F, 0.0F));
        body.addOrReplaceChild("hunch", CubeListBuilder.create().texOffs(32, 45).addBox(0.02F, 0.5745F, -0.4821F, 10.0F, 9.0F, 6.0F, new CubeDeformation(-0.02F)), PartPose.offsetAndRotation(-6.02F, -10.75F, 2.25F, -0.5236F, 0.0F, 0.0F));

        PartDefinition head = pd.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -11.9506F, -3.0052F, 4.0F, 11.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.75F, -3.25F, 0.7418F, 0.0F, 0.0F));
        head.addOrReplaceChild("mane", CubeListBuilder.create().texOffs(14, 17).addBox(0.0F, -5.5F, -0.5F, 0.0F, 11.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -6.4507F, 1.4949F));
        
        PartDefinition neck2 = head.addOrReplaceChild("neck2", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-2.0F, -11.8809F, -2.2082F, 4.0F, 11.0F, 4.0F, new CubeDeformation(-0.01F)).mirror(false), PartPose.offsetAndRotation(0.0F, -10.3F, -1.05F, -0.1309F, 0.0F, 0.0F));
        neck2.addOrReplaceChild("mane2", CubeListBuilder.create().texOffs(14, 17).addBox(0.0F, -6.75F, -0.5F, 0.0F, 12.0F, 1.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, -6.381F, 2.2919F));
        
        PartDefinition face = neck2.addOrReplaceChild("face", CubeListBuilder.create().texOffs(17, 0).addBox(-4.0F, -3.25F, -6.0F, 4.0F, 3.0F, 7.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(2.0F, -9.6613F, -1.1778F, -0.6109F, 0.0F, 0.0F));
        PartDefinition face2 = face.addOrReplaceChild("face2", CubeListBuilder.create().texOffs(18, 11).addBox(0.0F, 0.75F, 0.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.01F)), PartPose.offset(-4.0F, -6.0F, -3.0F));
        face2.addOrReplaceChild("rightHorn", CubeListBuilder.create().texOffs(49, 19).addBox(-0.5F, -1.25F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.01F)), PartPose.offset(0.75F, 0.0F, 3.0F));
        face2.addOrReplaceChild("leftHorn", CubeListBuilder.create().texOffs(49, 19).addBox(-0.5F, -1.25F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.01F)), PartPose.offset(3.25F, 0.0F, 3.0F));
        face2.addOrReplaceChild("rightEar", CubeListBuilder.create().texOffs(49, 23).addBox(-2.099F, -0.0129F, 0.0F, 3.0F, 2.0F, 0.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-0.25F, 1.25F, 3.0F, 0.0F, 0.0F, 0.2618F));
        face2.addOrReplaceChild("leftEar", CubeListBuilder.create().texOffs(49, 23).mirror().addBox(-0.4183F, -0.3709F, 0.0F, 3.0F, 2.0F, 0.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offsetAndRotation(4.0F, 1.7F, 3.0F, 0.0F, 0.0F, -0.2618F));

        PartDefinition leftFrontLeg = pd.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(0, 34).addBox(-1.0F, 0.6375F, -0.8567F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(3.5F, 11.5F, -6.0F, -0.1745F, 0.0F, 0.0F));
        PartDefinition leftLowerFrontLeg = leftFrontLeg.addOrReplaceChild("leftLowerFrontLeg", CubeListBuilder.create().texOffs(9, 34).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 6.6375F, 0.1433F, 0.1745F, 0.0F, 0.0F));
        leftLowerFrontLeg.addOrReplaceChild("leftFrontHoof1", CubeListBuilder.create().texOffs(54, 19).addBox(0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.0F, -1.5F, -0.1211F, -0.4883F, -0.0394F));
        leftLowerFrontLeg.addOrReplaceChild("leftFrontHoof2", CubeListBuilder.create().texOffs(54, 19).addBox(-1.0F, 4.0F, -1.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 4.0F, -1.0F, -0.1211F, 0.4883F, 0.0394F));

        PartDefinition rightFrontLeg = pd.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(0, 34).mirror().addBox(-1.0F, 0.6375F, -0.8567F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.2F)).mirror(false), PartPose.offsetAndRotation(-3.5F, 11.5F, -6.0F, -0.1745F, 0.0F, 0.0F));
        PartDefinition rightLowerFrontLeg = rightFrontLeg.addOrReplaceChild("rightLowerFrontLeg", CubeListBuilder.create().texOffs(9, 34).mirror().addBox(-1.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 6.6375F, 0.1433F, 0.1745F, 0.0F, 0.0F));
        rightLowerFrontLeg.addOrReplaceChild("rightFrontHoof1", CubeListBuilder.create().texOffs(54, 19).mirror().addBox(-1.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 4.0F, -1.5F, -0.1211F, 0.4883F, 0.0394F));
        rightLowerFrontLeg.addOrReplaceChild("rightFrontHoof2", CubeListBuilder.create().texOffs(54, 19).mirror().addBox(-1.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.0F, 4.0F, -1.0F, -0.1211F, -0.4883F, -0.0394F));

        PartDefinition leftHindLeg = pd.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(0, 34).addBox(-1.0F, 0.2557F, -1.1307F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(3.5F, 13.5F, 6.25F, -0.0873F, 0.0F, 0.0F));
        PartDefinition leftLowerHindLeg = leftHindLeg.addOrReplaceChild("leftLowerHindLeg", CubeListBuilder.create().texOffs(9, 34).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5.5057F, -0.1307F, 0.0873F, 0.0F, 0.0F));
        leftLowerHindLeg.addOrReplaceChild("leftRearHoof1", CubeListBuilder.create().texOffs(54, 19).addBox(0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.0F, -1.5F, -0.1211F, -0.4883F, -0.0394F));
        leftLowerHindLeg.addOrReplaceChild("leftRearHoof2", CubeListBuilder.create().texOffs(54, 19).addBox(-1.0F, 3.0F, -1.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 3.0F, -1.0F, -0.1211F, 0.4883F, 0.0394F));

        PartDefinition rightHindLeg = pd.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(0, 34).mirror().addBox(-1.0F, 0.2557F, -1.1307F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.25F)).mirror(false), PartPose.offsetAndRotation(-3.5F, 13.5F, 6.25F, -0.0873F, 0.0F, 0.0F));
        PartDefinition rightLowerHindLeg = rightHindLeg.addOrReplaceChild("rightLowerHindLeg", CubeListBuilder.create().texOffs(9, 34).mirror().addBox(-1.0F, -1.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 5.5057F, -0.1307F, 0.0873F, 0.0F, 0.0F));
        rightLowerHindLeg.addOrReplaceChild("rightRearHoof1", CubeListBuilder.create().texOffs(54, 19).mirror().addBox(-1.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 3.0F, -1.5F, -0.1211F, 0.4883F, 0.0394F));
        rightLowerHindLeg.addOrReplaceChild("rightRearHoof2", CubeListBuilder.create().texOffs(54, 19).mirror().addBox(-1.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.0F, 3.0F, -1.0F, -0.1211F, -0.4883F, -0.0394F));

        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        root.getAllParts().forEach(ModelPart::resetPose);
        this.head.yRot = netHeadYaw * ((float)Math.PI / 180F);
        this.head.xRot = headPitch * ((float)Math.PI / 180F);

        float limbSpeed = 0.6662F;
        this.rightFrontLeg.xRot = -0.15F + Mth.cos(limbSwing * limbSpeed) * 0.3F * limbSwingAmount;
        this.leftFrontLeg.xRot = -0.15F + Mth.cos(limbSwing * limbSpeed) * 0.4F * limbSwingAmount;
        this.rightHindLeg.xRot = -0.1F + Mth.cos(limbSwing * limbSpeed + (float)Math.PI) * 0.3F * limbSwingAmount;
        this.leftHindLeg.xRot = -0.1F + Mth.cos(limbSwing * limbSpeed + (float)Math.PI) * 0.4F * limbSwingAmount;

        if (entity.isSprinting()) {
            this.tail1.xRot = 0.2F + Mth.cos(limbSwing * 0.6662F) * 0.2F * limbSwingAmount;
            this.leftEar.zRot = -0.3F - Mth.cos(limbSwing * 0.6662F) * 0.2F * limbSwingAmount;
            this.rightEar.zRot = 0.3F + Mth.cos(limbSwing * 0.6662F) * 0.2F * limbSwingAmount;
        }

        if (entity.isCrouching()) {
            this.head.xRot = 0.9F;
            this.head.y = -1.0F;
            this.head.z = 9.0F;
            this.head.x += 0.2F;
            this.head.y -= 0.3F;
            this.head.z += 0.2F;
            this.neck2.y = -1.2F;
            this.neck2.z = -7.0F;
            this.face.x += 0.2F;
            this.face.y += 0.3F;
            this.face.z += 0.2F;
            this.body.z = 11.0F;
            this.rightFrontLeg.z = 13.0F;
            this.rightLowerFrontLeg.z = 5.0F;
            this.leftFrontLeg.z = 13.0F;
            this.leftLowerFrontLeg.z = 5.0F;
            this.leftHindLeg.z = 14.0F;
            this.leftLowerHindLeg.z = 5.0F;
            this.rightHindLeg.z = 14.0F;
            this.rightLowerHindLeg.z = 5.0F;
        }
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        root.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }
}