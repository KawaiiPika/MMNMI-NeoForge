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

public class SaiHeavyModel<T extends LivingEntity> extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "sai_heavy"), "main");

    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart rightArm;
    private final ModelPart leftArm;
    private final ModelPart rightLeg;
    private final ModelPart leftLeg;

    public SaiHeavyModel(ModelPart root) {
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

        PartDefinition head = pd.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -1.3502F, -0.0348F, -0.6545F, 0.0F, 0.0F));
        head.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(50, 30).addBox(-3.0F, -4.3982F, -4.3874F, 6.0F, 8.0F, 7.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(0.0F, 0.75F, -0.75F, -0.2182F, 0.0F, 0.0F));
        PartDefinition head2 = head.addOrReplaceChild("head2", CubeListBuilder.create().texOffs(46, 16).addBox(-3.5F, -4.1989F, -3.9161F, 7.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.6032F, -3.3915F, 0.4363F, 0.0F, 0.0F));
        
        PartDefinition leftEar = head2.addOrReplaceChild("leftEar", CubeListBuilder.create(), PartPose.offsetAndRotation(2.4911F, -4.2587F, -0.3525F, 0.4363F, 0.0F, 0.1745F));
        leftEar.addOrReplaceChild("leftear1_r1", CubeListBuilder.create().texOffs(0, 6).addBox(-1.0F, -1.5F, -0.5F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, -0.3125F));
        PartDefinition rightEar = head2.addOrReplaceChild("rightEar", CubeListBuilder.create(), PartPose.offsetAndRotation(-2.4911F, -4.2587F, -0.3525F, 0.4363F, 0.0F, -0.1745F));
        rightEar.addOrReplaceChild("rightear_r1", CubeListBuilder.create().texOffs(0, 6).mirror().addBox(-1.0F, -1.5F, -0.5F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, -1.0F, -0.3125F));
        
        head2.addOrReplaceChild("mouth", CubeListBuilder.create().texOffs(46, 0).addBox(-3.5F, -2.1467F, -8.5031F, 7.0F, 6.0F, 10.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, -0.6664F, -1.7917F, 0.3491F, 0.0F, 0.0F));
        PartDefinition frontHorn = head2.addOrReplaceChild("frontHorn", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -1.0616F, -0.9891F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.7496F, -9.1317F, 0.1745F, 0.0F, 0.0F));
        frontHorn.addOrReplaceChild("frontUpperHorn", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -1.5F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, -2.0914F, 0.3156F, -0.3054F, 0.0F, 0.0F));
        PartDefinition backHorn = head2.addOrReplaceChild("backHorn", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -1.4204F, -0.7742F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.8384F, -6.9653F, 0.0873F, 0.0F, 0.0F));
        backHorn.addOrReplaceChild("backUpperHorn", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -0.8625F, -0.3546F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, -1.2778F, -0.4983F, -0.2182F, 0.0F, 0.0F));

        PartDefinition body = pd.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 46).addBox(-5.5F, -2.8492F, -4.4129F, 11.0F, 6.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 14.3338F, 2.7771F, -0.1309F, 0.0F, 0.0F));
        PartDefinition body2 = body.addOrReplaceChild("body2", CubeListBuilder.create().texOffs(0, 0).addBox(-6.5F, -14.9331F, -2.2106F, 13.0F, 15.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0254F, -3.0212F, 0.1745F, 0.0F, 0.0F));
        body2.addOrReplaceChild("body3", CubeListBuilder.create().texOffs(0, 25).addBox(-7.0F, -6.3959F, -2.7615F, 14.0F, 10.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -9.4901F, -1.2439F, 0.1745F, 0.0F, 0.0F));

        PartDefinition leftArm = pd.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(0, 60).mirror().addBox(-0.25F, -0.9564F, -2.999F, 5.0F, 10.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(7.25F, 0.6264F, 1.0003F, 0.0873F, 0.0F, 0.0F));
        leftArm.addOrReplaceChild("leftLowerArm", CubeListBuilder.create().texOffs(22, 61).mirror().addBox(-2.0F, -1.5F, -2.5F, 4.0F, 9.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.25F, 8.6838F, 0.1654F, -0.2182F, 0.0F, 0.0F));
        
        PartDefinition rightArm = pd.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(0, 60).addBox(-4.75F, -0.9564F, -2.999F, 5.0F, 10.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.25F, 0.6264F, 1.0003F, 0.0873F, 0.0F, 0.0F));
        rightArm.addOrReplaceChild("rightLowerArm", CubeListBuilder.create().texOffs(22, 61).addBox(-2.0F, -1.5F, -2.5F, 4.0F, 9.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.25F, 8.6838F, 0.1654F, -0.2182F, 0.0F, 0.0F));

        PartDefinition leftLeg = pd.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(46, 45).addBox(-2.5F, -0.5805F, -2.2687F, 5.0F, 6.0F, 5.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(4.5F, 13.437F, 1.3738F, 0.1745F, 0.0F, 0.0F));
        PartDefinition lowerleftleg = leftLeg.addOrReplaceChild("lowerleftleg", CubeListBuilder.create().texOffs(46, 57).addBox(-2.0313F, -0.1803F, -1.7752F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(-0.0313F, 4.0089F, 0.2982F, -0.2618F, 0.0F, 0.0F));
        lowerleftleg.addOrReplaceChild("leftfoot", CubeListBuilder.create().texOffs(44, 68).addBox(-2.5625F, -0.0625F, -2.5F, 5.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0313F, 4.7541F, -0.4595F, 0.0873F, 0.0F, 0.0F));

        PartDefinition rightLeg = pd.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(46, 45).mirror().addBox(-2.5F, -0.5805F, -2.2687F, 5.0F, 6.0F, 5.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(-4.5F, 13.437F, 1.3738F, 0.1745F, 0.0F, 0.0F));
        PartDefinition lowerrightleg = rightLeg.addOrReplaceChild("lowerrightleg", CubeListBuilder.create().texOffs(46, 57).mirror().addBox(-1.9688F, -0.1803F, -1.7752F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.2F)).mirror(false), PartPose.offsetAndRotation(0.0313F, 4.0089F, 0.2982F, -0.2618F, 0.0F, 0.0F));
        lowerrightleg.addOrReplaceChild("rightfoot", CubeListBuilder.create().texOffs(44, 68).mirror().addBox(-2.4375F, -0.0625F, -2.5F, 5.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.0313F, 4.7541F, -0.4595F, 0.0873F, 0.0F, 0.0F));

        return LayerDefinition.create(mesh, 128, 128);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        root.getAllParts().forEach(ModelPart::resetPose);
        this.head.yRot = netHeadYaw * ((float)Math.PI / 180F);
        this.head.xRot = headPitch * ((float)Math.PI / 180F);

        float limbSpeed = 0.6F;
        this.rightArm.xRot = Mth.cos(limbSwing * limbSpeed + (float)Math.PI) * 0.8F * limbSwingAmount * 0.5F;
        this.leftArm.xRot = Mth.cos(limbSwing * limbSpeed) * 0.8F * limbSwingAmount * 0.5F;
        this.rightLeg.xRot = (float)Math.toRadians(10.0F) + Mth.cos(limbSwing * limbSpeed) * 0.7F * limbSwingAmount;
        this.leftLeg.xRot = (float)Math.toRadians(10.0F) + Mth.cos(limbSwing * limbSpeed + (float)Math.PI) * 0.7F * limbSwingAmount;

        if (entity.isCrouching()) {
            this.body.y = 17.0F;
            this.body.z = 5.0F;
            this.body.xRot = 0.1F;
            this.head.y = 3.0F;
            this.head.z = -2.0F;
            this.leftArm.xRot = 0.3F;
            this.leftArm.y = 4.0F;
            this.leftArm.z = 0.0F;
            this.rightArm.xRot = 0.3F;
            this.rightArm.y = 4.0F;
            this.rightArm.z = 0.0F;
        }
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        root.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }
}