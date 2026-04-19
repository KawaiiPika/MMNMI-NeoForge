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

public class AxolotlWalkModel<T extends LivingEntity> extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "axolotl_walk"), "main");
    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart rightFrontLeg;
    private final ModelPart leftFrontLeg;
    private final ModelPart rightHindLeg;
    private final ModelPart leftHindLeg;
    private final ModelPart tail;

    public AxolotlWalkModel(ModelPart root) {
        this.root = root;
        this.head = root.getChild("head");
        this.body = root.getChild("body");
        this.rightFrontLeg = root.getChild("right_front_leg");
        this.leftFrontLeg = root.getChild("left_front_leg");
        this.rightHindLeg = root.getChild("right_hind_leg");
        this.leftHindLeg = root.getChild("left_hind_leg");
        this.tail = root.getChild("tail");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition pd = mesh.getRoot();

        PartDefinition head = pd.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 1).addBox(-4.0F, -3.0F, -5.0F, 8.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 18.0F, -5.0F));
        head.addOrReplaceChild("top_gills", CubeListBuilder.create().texOffs(3, 37).addBox(-4.0F, -3.0F, 0.0F, 8.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, -1.0F));
        head.addOrReplaceChild("left_gills", CubeListBuilder.create().texOffs(0, 40).addBox(-11.0F, -5.0F, 0.0F, 3.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, 0.0F, -1.0F));
        head.addOrReplaceChild("right_gills", CubeListBuilder.create().texOffs(11, 40).addBox(8.0F, -5.0F, 0.0F, 3.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, 0.0F, -1.0F));

        pd.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 11).addBox(-4.0F, -2.0F, -9.0F, 8.0F, 4.0F, 10.0F, new CubeDeformation(0.0F)).texOffs(2, 17).addBox(0.0F, -3.0F, -8.0F, 0.0F, 5.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 18.0F, 4.0F));
        pd.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(2, 13).addBox(6.0F, 0.0F, 0.0F, 3.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.5F, 19.0F, -4.0F));
        pd.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(2, 13).addBox(6.0F, 0.0F, 0.0F, 3.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.5F, 19.0F, 3.0F));
        pd.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(2, 13).addBox(-9.0F, 0.0F, 0.0F, 3.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(3.5F, 19.0F, -4.0F));
        pd.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(2, 13).addBox(-9.0F, 0.0F, 0.0F, 3.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(3.5F, 19.0F, 3.0F));
        pd.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(2, 19).addBox(0.0F, -3.0F, 0.0F, 0.0F, 5.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 18.0F, 5.0F));

        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        root.getAllParts().forEach(ModelPart::resetPose);
        this.head.yRot = netHeadYaw * ((float)Math.PI / 180F);
        this.head.xRot = headPitch * ((float)Math.PI / 180F);

        float limbSpeed = 0.6662F;
        this.rightFrontLeg.xRot = Mth.cos(limbSwing * limbSpeed + (float)Math.PI) * 0.4F * limbSwingAmount;
        this.rightHindLeg.xRot = Mth.cos(limbSwing * limbSpeed + (float)Math.PI) * 0.4F * limbSwingAmount;
        this.leftFrontLeg.xRot = Mth.cos(limbSwing * limbSpeed) * 0.35F * limbSwingAmount;
        this.leftHindLeg.xRot = Mth.cos(limbSwing * limbSpeed) * 0.35F * limbSwingAmount;

        this.tail.yRot = Mth.sin(ageInTicks * 0.06F) / 10.0F;
        this.tail.xRot = Mth.sin(ageInTicks * 0.02F) / 10.0F;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        root.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }
}