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

public class KameGuardModel<T extends LivingEntity> extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "kame_guard"), "main");

    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart leftFrontLeg;
    private final ModelPart rightFrontLeg;
    private final ModelPart leftHindLeg;
    private final ModelPart rightHindLeg;

    public KameGuardModel(ModelPart root) {
        this.root = root;
        this.head = root.getChild("head");
        this.body = root.getChild("body");
        this.leftFrontLeg = root.getChild("left_front_leg");
        this.rightFrontLeg = root.getChild("right_front_leg");
        this.leftHindLeg = root.getChild("left_hind_leg");
        this.rightHindLeg = root.getChild("right_hind_leg");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition pd = mesh.getRoot();
        pd.addOrReplaceChild("body", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-4.0F, -2.0F, -5.0F, 10.0F, 3.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(0, 29).addBox(-3.0F, -3.0F, -4.0F, 8.0F, 1.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(0, 16).addBox(-3.0F, 1.0F, -4.5F, 8.0F, 1.0F, 11.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-1.0F, 22.0F, -1.0F));
        pd.addOrReplaceChild("head", CubeListBuilder.create().texOffs(33, 0).addBox(-2.0F, -1.0F, -4.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 22.0F, -5.0F));
        pd.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(28, 16).addBox(-2.0F, 0.0F, 0.0F, 3.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, 23.0F, 5.25F));
        pd.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(27, 29).addBox(-3.0F, 1.0F, 6.0F, 3.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, 22.0F, -0.75F));
        pd.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(28, 23).addBox(-9.0F, 1.0F, -4.0F, 6.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, 22.0F, -1.0F));
        pd.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(33, 8).addBox(5.0F, 0.85F, -4.0F, 6.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, 22.0F, -1.0F));
        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        root.getAllParts().forEach(ModelPart::resetPose);
        boolean crouching = entity.isCrouching();
        head.visible = !crouching;
        leftFrontLeg.visible = !crouching;
        rightFrontLeg.visible = !crouching;
        leftHindLeg.visible = !crouching;
        rightHindLeg.visible = !crouching;
        head.xRot = headPitch * ((float)Math.PI / 180F);
        head.yRot = netHeadYaw * ((float)Math.PI / 180F);
        leftFrontLeg.xRot  = Mth.cos(limbSwing * 0.4F) * 0.1F;
        leftFrontLeg.zRot  = Mth.sin(limbSwing * 0.4F) * 0.1F;
        rightFrontLeg.xRot = -Mth.cos(limbSwing * 0.4F) * 0.1F;
        rightFrontLeg.zRot = -Mth.sin(limbSwing * 0.4F) * 0.1F;
        leftHindLeg.yRot   = Mth.cos(limbSwing * 0.4F) * 0.1F;
        leftHindLeg.zRot   = Mth.sin(limbSwing * 0.4F) * 0.1F;
        rightHindLeg.yRot  = Mth.cos(limbSwing * 0.4F) * 0.2F;
        rightHindLeg.zRot  = -Mth.sin(limbSwing * 0.4F) * 0.1F;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        root.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }
}
