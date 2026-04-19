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

public class PteranodonFlyModel<T extends LivingEntity> extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "pteranodon_fly"), "main");

    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart neck;
    private final ModelPart rightWing;
    private final ModelPart leftWing;
    private final ModelPart rightWingSegment2;
    private final ModelPart rightWingSegment3;
    private final ModelPart leftWingSegment2;
    private final ModelPart leftWingSegment3;
    private final ModelPart rightLeg;
    private final ModelPart leftLeg;

    public PteranodonFlyModel(ModelPart root) {
        this.root = root;
        this.head = root.getChild("head");
        this.body = root.getChild("body");
        this.neck = root.getChild("neck");
        this.rightWing = root.getChild("right_front_leg");
        this.leftWing = root.getChild("left_front_leg");
        this.rightWingSegment2 = rightWing.getChild("rightWingSegment2");
        this.rightWingSegment3 = rightWingSegment2.getChild("rightWingSegment3");
        this.leftWingSegment2 = leftWing.getChild("leftWingSegment2");
        this.leftWingSegment3 = leftWingSegment2.getChild("leftWingSegment3");
        this.rightLeg = root.getChild("right_hind_leg");
        this.leftLeg = root.getChild("left_hind_leg");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition pd = mesh.getRoot();

        PartDefinition head = pd.addOrReplaceChild("head", CubeListBuilder.create().texOffs(30, 14).addBox(-1.5F, -1.2522F, -5.8546F, 3.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 16.8298F, -10.5783F, 0.0873F, 0.0F, 0.0F));
        head.addOrReplaceChild("rightEye", CubeListBuilder.create().texOffs(43, 6).mirror().addBox(0.0F, -1.0F, -1.75F, 0.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-1.525F, 0.2478F, -1.1046F));
        head.addOrReplaceChild("leftEye", CubeListBuilder.create().texOffs(43, 6).mirror().addBox(0.0F, -1.0F, -1.75F, 0.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(1.525F, 0.2478F, -1.1046F));
        
        PartDefinition horn = head.addOrReplaceChild("horn", CubeListBuilder.create().texOffs(30, 26).addBox(-1.0F, -0.5F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.9656F, -2.4745F, 0.5236F, 0.0F, 0.0F));
        PartDefinition horn2 = horn.addOrReplaceChild("horn2", CubeListBuilder.create().texOffs(30, 29).addBox(-1.0F, -1.6862F, -1.1801F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.739F, 2.3141F, 0.3491F, 0.0F, 0.0F));
        PartDefinition horn3 = horn2.addOrReplaceChild("horn3", CubeListBuilder.create().texOffs(31, 33).addBox(-0.5F, -1.1862F, -1.1801F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.4484F, 1.351F));
        PartDefinition horn4 = horn3.addOrReplaceChild("horn4", CubeListBuilder.create().texOffs(31, 36).addBox(-0.5F, 0.0554F, -0.7619F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.304F, 1.4869F, 0.0873F, 0.0F, 0.0F));
        PartDefinition horn5 = horn4.addOrReplaceChild("horn5", CubeListBuilder.create().texOffs(31, 39).addBox(-0.5F, 1.4156F, -0.3558F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.382F, 1.382F, 0.0873F, 0.0F, 0.0F));
        horn5.addOrReplaceChild("horn6", CubeListBuilder.create().texOffs(31, 42).addBox(-0.5F, 2.9544F, 0.1402F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.5115F, 1.1598F, 0.0873F, 0.0F, 0.0F));
        
        head.addOrReplaceChild("upperBeak", CubeListBuilder.create().texOffs(54, 0).addBox(-1.0F, -1.1625F, -2.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.1F)).texOffs(38, 0).addBox(-0.5F, -1.0625F, -13.0F, 1.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.7478F, -5.8546F));
        head.addOrReplaceChild("lowerBeak", CubeListBuilder.create().texOffs(54, 5).addBox(-1.0F, -0.5625F, -2.0F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(38, 14).addBox(-0.5F, -0.5625F, -13.0F, 1.0F, 1.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.2478F, -5.8546F));

        pd.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(12, 16).addBox(-0.5F, -1.6712F, -7.1834F, 1.0F, 3.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 18.625F, -4.0F, -0.1309F, 0.0F, 0.0F));

        PartDefinition rightWing = pd.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(-9, 0).addBox(-5.75F, 0.0F, 1.9375F, 6.0F, 0.0F, 9.0F, new CubeDeformation(0.0F)).texOffs(0, 9).addBox(-6.75F, -0.5F, -1.0625F, 7.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(19, 11).addBox(-5.75F, -0.5F, 0.9375F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.75F, 18.5F, -2.9375F));
        PartDefinition rightWingSegment2 = rightWing.addOrReplaceChild("rightWingSegment2", CubeListBuilder.create().texOffs(16, 10).addBox(-8.75F, -0.5F, -4.0625F, 9.0F, 1.0F, 2.0F, new CubeDeformation(0.01F)).texOffs(3, 0).addBox(-8.75F, 0.0F, -2.0625F, 9.0F, 0.0F, 9.0F, new CubeDeformation(0.01F)), PartPose.offset(-5.75F, 0.0F, 5.0F));
        rightWingSegment2.addOrReplaceChild("rightWingSegment3", CubeListBuilder.create().texOffs(0, 12).addBox(-6.75F, -0.5F, -3.0625F, 7.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(18, 13).addBox(-7.75F, -0.5F, -1.0625F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(22, 0).addBox(-7.65F, 0.0F, -1.0625F, 8.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(-8.75F, 0.0F, 0.0F));

        PartDefinition leftWing = pd.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(-9, 0).mirror().addBox(-0.25F, 0.0F, 1.9375F, 6.0F, 0.0F, 9.0F, new CubeDeformation(0.0F)).mirror(false).texOffs(0, 9).mirror().addBox(-0.25F, -0.5F, -1.0625F, 7.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false).texOffs(19, 11).mirror().addBox(-0.25F, -0.5F, 0.9375F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(2.75F, 18.5F, -2.9375F));
        PartDefinition leftWingSegment2 = leftWing.addOrReplaceChild("leftWingSegment2", CubeListBuilder.create().texOffs(16, 10).mirror().addBox(-0.25F, -0.5F, -4.0625F, 9.0F, 1.0F, 2.0F, new CubeDeformation(0.01F)).mirror(false).texOffs(3, 0).mirror().addBox(-0.25F, 0.0F, -2.0625F, 9.0F, 0.0F, 9.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offset(5.75F, 0.0F, 5.0F));
        leftWingSegment2.addOrReplaceChild("leftWingSegment3", CubeListBuilder.create().texOffs(0, 12).mirror().addBox(-0.25F, -0.5F, -3.0625F, 7.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false).texOffs(18, 13).mirror().addBox(2.75F, -0.5F, -1.0625F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false).texOffs(22, 0).mirror().addBox(-0.35F, 0.0F, -1.0625F, 8.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(8.75F, 0.0F, 0.0F));

        PartDefinition rightLeg = pd.addOrReplaceChild("right_hind_leg", CubeListBuilder.create(), PartPose.offsetAndRotation(-3.0F, 20.0F, 13.7F, 1.3526F, 0.0F, 0.0F));
        PartDefinition rightLowerLeg = rightLeg.addOrReplaceChild("rightLowerLeg", CubeListBuilder.create().texOffs(0, 23).addBox(-5.5F, 0.6958F, -20.7223F, 1.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(0, 15).addBox(-5.5F, 0.6958F, -15.7223F, 1.0F, 4.0F, 1.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(5.0F, 12.1847F, 7.6754F, -1.0472F, 0.0F, 0.0F));
        rightLowerLeg.addOrReplaceChild("rightFoot", CubeListBuilder.create().texOffs(9, 15).addBox(-7.5F, 4.6958F, -15.7354F, 5.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(0, 31).addBox(-6.5F, 3.93F, -15.7223F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        rightLeg.addOrReplaceChild("rightUpperThight", CubeListBuilder.create().texOffs(0, 15).addBox(-6.0F, 18.0963F, -11.8567F, 2.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, 13.8689F, 7.5424F, -2.2253F, 0.0F, 0.0F));

        PartDefinition leftLeg = pd.addOrReplaceChild("left_hind_leg", CubeListBuilder.create(), PartPose.offsetAndRotation(3.0F, 20.0F, 13.7F, 1.3526F, 0.0F, 0.0F));
        PartDefinition leftLowerLeg = leftLeg.addOrReplaceChild("leftLowerLeg", CubeListBuilder.create().texOffs(0, 23).mirror().addBox(4.5F, 0.6958F, -20.7223F, 1.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false).texOffs(0, 15).mirror().addBox(4.5F, 0.6958F, -15.7223F, 1.0F, 4.0F, 1.0F, new CubeDeformation(-0.01F)).mirror(false), PartPose.offsetAndRotation(-5.0F, 12.1847F, 7.6754F, -1.0472F, 0.0F, 0.0F));
        leftLowerLeg.addOrReplaceChild("leftFoot", CubeListBuilder.create().texOffs(9, 15).mirror().addBox(2.5F, 4.6958F, -15.7354F, 5.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false).texOffs(0, 31).mirror().addBox(3.5F, 3.93F, -15.7223F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));
        leftLeg.addOrReplaceChild("leftUpperThight", CubeListBuilder.create().texOffs(0, 15).mirror().addBox(4.0F, 18.0963F, -11.8567F, 2.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-5.0F, 13.8689F, 7.5424F, -2.2253F, 0.0F, 0.0F));

        pd.addOrReplaceChild("body", CubeListBuilder.create().texOffs(2, 41).addBox(-3.0F, -1.4344F, -2.2227F, 6.0F, 5.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 17.9344F, -2.7773F));
        pd.addOrReplaceChild("lowerBody", CubeListBuilder.create().texOffs(2, 30).addBox(-2.5F, -1.3995F, -0.3291F, 5.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)).texOffs(0, 41).addBox(-1.5F, -0.9995F, 6.6709F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.2172F, 4.7364F, -0.0873F, 0.0F, 0.0F));

        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        root.getAllParts().forEach(ModelPart::resetPose);
        this.head.yRot = netHeadYaw * ((float)Math.PI / 180F);
        this.head.xRot = headPitch * ((float)Math.PI / 180F);

        this.leftWing.zRot = Mth.cos(ageInTicks * 0.4F) * 0.5F;
        this.leftWingSegment2.zRot = Mth.cos(ageInTicks * 0.4F) * 0.5F;
        this.leftWingSegment3.zRot = Mth.cos(ageInTicks * 0.4F) * 0.3F;
        this.rightWing.zRot = Mth.cos(ageInTicks * 0.4F + (float)Math.PI) * 0.5F;
        this.rightWingSegment2.zRot = Mth.cos(ageInTicks * 0.4F + (float)Math.PI) * 0.5F;
        this.rightWingSegment3.zRot = Mth.cos(ageInTicks * 0.4F + (float)Math.PI) * 0.3F;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        head.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
        neck.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
        rightWing.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
        leftWing.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
        rightLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
        leftLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
        body.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }
}