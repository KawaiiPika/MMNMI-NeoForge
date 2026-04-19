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

public class SaiWalkModel<T extends LivingEntity> extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "sai_walk"), "main");

    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart leftFrontLeg;
    private final ModelPart rightFrontLeg;
    private final ModelPart leftHindLeg;
    private final ModelPart rightHindLeg;
    private final ModelPart tail;

    public SaiWalkModel(ModelPart root) {
        this.root = root;
        this.head = root.getChild("head");
        this.body = root.getChild("body");
        this.leftFrontLeg = root.getChild("left_front_leg");
        this.rightFrontLeg = root.getChild("right_front_leg");
        this.leftHindLeg = root.getChild("left_hind_leg");
        this.rightHindLeg = root.getChild("right_hind_leg");
        this.tail = body.getChild("back").getChild("tail");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition pd = mesh.getRoot();

        PartDefinition body = pd.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-6.5F, -4.1649F, -2.8546F, 13.0F, 10.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 10.5525F, -5.7809F, -0.0873F, 0.0F, 0.0F));
        PartDefinition back = body.addOrReplaceChild("back", CubeListBuilder.create().texOffs(0, 21).addBox(-5.5F, -5.1071F, -1.6826F, 11.0F, 9.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.6509F, 7.9088F, 0.0873F, 0.0F, 0.0F));
        PartDefinition tail = back.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 22).addBox(-1.0F, -0.2701F, -0.5734F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.3698F, 8.7659F, 0.2618F, 0.0F, 0.0F));
        PartDefinition tail2 = tail.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(0, 22).addBox(-1.0F, 2.7299F, 0.4266F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -1.0F));
        tail2.addOrReplaceChild("tail3", CubeListBuilder.create().texOffs(0, 26).addBox(-1.0F, 5.7299F, 0.4266F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        pd.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(49, 0).addBox(-2.5F, -1.25F, -3.0F, 5.0F, 10.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, 10.8132F, 7.4987F, 0.0873F, 0.0F, 0.0F));
        pd.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(49, 0).mirror().addBox(-2.5F, -1.25F, -3.0F, 5.0F, 10.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-4.0F, 10.8132F, 7.4987F, 0.0873F, 0.0F, 0.0F));
        pd.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(49, 0).addBox(0.1039F, -1.0906F, -2.18F, 5.0F, 10.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(2.3961F, 10.0401F, -5.4796F));
        pd.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(49, 0).mirror().addBox(-5.1039F, -1.0906F, -2.18F, 5.0F, 10.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-2.3961F, 10.0401F, -5.4796F));

        PartDefinition head = pd.addOrReplaceChild("head", CubeListBuilder.create().texOffs(34, 41).addBox(-3.0F, -4.3982F, -4.3874F, 6.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 10.1988F, -7.475F, -0.5236F, 0.0F, 0.0F));
        PartDefinition head2 = head.addOrReplaceChild("head2", CubeListBuilder.create().texOffs(45, 28).addBox(-3.5F, -4.1989F, -3.9161F, 7.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.6032F, -3.3915F, 0.4363F, 0.0F, 0.0F));
        head2.addOrReplaceChild("leftEar", CubeListBuilder.create().texOffs(0, 6).addBox(-1.0F, -1.5F, -0.5F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.4911F, -4.2587F, -0.3525F, 0.4363F, 0.0F, 0.1745F));
        head2.addOrReplaceChild("rightEar", CubeListBuilder.create().texOffs(0, 6).mirror().addBox(-1.0F, -1.5F, -0.5F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-2.4911F, -4.2587F, -0.3525F, 0.4363F, 0.0F, -0.1745F));
        
        PartDefinition mouth = head2.addOrReplaceChild("mouth", CubeListBuilder.create().texOffs(0, 41).addBox(-3.5F, -2.1467F, -8.5031F, 7.0F, 6.0F, 10.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, -0.6664F, -1.7917F, 0.3491F, 0.0F, 0.0F));
        PartDefinition frontHorn = mouth.addOrReplaceChild("frontHorn", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -1.0616F, -0.9891F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.8739F, -6.9549F, 0.1745F, 0.0F, 0.0F));
        frontHorn.addOrReplaceChild("frontUpperHorn", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -1.5F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, -2.0914F, 0.3156F, -0.3054F, 0.0F, 0.0F));
        PartDefinition backHorn = mouth.addOrReplaceChild("backHorn", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -1.4204F, -0.7742F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.9626F, -4.7885F, 0.0873F, 0.0F, 0.0F));
        backHorn.addOrReplaceChild("backUpperHorn", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -0.8625F, -0.3546F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, -1.2778F, -0.4983F, -0.2182F, 0.0F, 0.0F));

        return LayerDefinition.create(mesh, 128, 128);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        root.getAllParts().forEach(ModelPart::resetPose);
        this.head.yRot = netHeadYaw * ((float)Math.PI / 180F);
        this.head.xRot = headPitch * ((float)Math.PI / 180F);

        float limbSpeed = 0.77F;
        this.rightFrontLeg.xRot = Mth.cos(limbSwing * limbSpeed) * 0.5F * limbSwingAmount;
        this.leftFrontLeg.xRot = Mth.cos(limbSwing * 0.75F) * 0.5F * limbSwingAmount;
        this.rightHindLeg.xRot = 0.15F + Mth.cos(limbSwing * 0.78F) * 0.5F * limbSwingAmount;
        this.leftHindLeg.xRot = 0.15F + Mth.cos(limbSwing * 0.76F) * 0.5F * limbSwingAmount;

        if (entity.isSprinting()) {
            this.tail.xRot = 1.2F + Mth.cos(limbSwing * 0.6662F) * 0.2F * limbSwingAmount;
        }
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        root.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }
}