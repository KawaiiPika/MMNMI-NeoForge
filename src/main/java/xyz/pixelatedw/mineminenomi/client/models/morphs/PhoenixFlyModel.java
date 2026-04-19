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

public class PhoenixFlyModel<T extends LivingEntity> extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "phoenix_fly"), "main");

    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart rightWing;
    private final ModelPart leftWing;
    private final ModelPart rightWing2;
    private final ModelPart rightWing3;
    private final ModelPart rightWing4;
    private final ModelPart leftWing2;
    private final ModelPart leftWing3;
    private final ModelPart leftWing4;
    private final ModelPart upperLeftTail;
    private final ModelPart upperRightTail;
    private final ModelPart lowerLeftTail;
    private final ModelPart lowerRightTail;
    private final ModelPart upperLeftTail2;
    private final ModelPart upperRightTail2;
    private final ModelPart lowerLeftTail2;
    private final ModelPart lowerRightTail2;
    private final ModelPart upperLeftTail3;
    private final ModelPart upperRightTail3;
    private final ModelPart lowerLeftTail3;
    private final ModelPart lowerRightTail3;

    public PhoenixFlyModel(ModelPart root) {
        this.root = root;
        this.head = root.getChild("head");
        this.body = root.getChild("body");
        this.rightWing = root.getChild("right_front_leg");
        this.leftWing = root.getChild("left_front_leg");
        this.rightWing2 = rightWing.getChild("rightWing2");
        this.rightWing3 = rightWing2.getChild("rightWing3");
        this.rightWing4 = rightWing3.getChild("rightWing4");
        this.leftWing2 = leftWing.getChild("leftWing2");
        this.leftWing3 = leftWing2.getChild("leftWing3");
        this.leftWing4 = leftWing3.getChild("leftWing4");
        
        ModelPart butt = body.getChild("mainBody").getChild("bodysection3").getChild("butt");
        this.upperLeftTail = butt.getChild("upperLeftTail");
        this.upperRightTail = butt.getChild("upperRightTail");
        this.lowerLeftTail = butt.getChild("lowerLeftTail");
        this.lowerRightTail = butt.getChild("lowerRightTail");
        this.upperLeftTail2 = upperLeftTail.getChild("upperLeftTail2");
        this.upperRightTail2 = upperRightTail.getChild("upperRightTail2");
        this.lowerLeftTail2 = lowerLeftTail.getChild("lowerLeftTail2");
        this.lowerRightTail2 = lowerRightTail.getChild("lowerRightTail2");
        this.upperLeftTail3 = upperLeftTail2.getChild("upperLeftTail3");
        this.upperRightTail3 = upperRightTail2.getChild("upperRightTail3");
        this.lowerLeftTail3 = lowerLeftTail2.getChild("lowerLeftTail3");
        this.lowerRightTail3 = lowerRightTail2.getChild("lowerRightTail3");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition pd = mesh.getRoot();

        PartDefinition head = pd.addOrReplaceChild("head", CubeListBuilder.create().texOffs(18, 30).addBox(-1.0F, -0.9797F, -3.6589F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.3F)).texOffs(5, 30).addBox(-1.5F, -1.4797F, -6.6589F, 3.0F, 3.0F, 3.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(0.0F, 11.5908F, -16.2659F, 0.0524F, 0.0F, 0.0F));
        head.addOrReplaceChild("head3_r1", CubeListBuilder.create().texOffs(15, 40).addBox(-5.2719F, 8.378F, 15.4429F, 2.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, -9.8578F, -21.6642F, 0.0F, 0.2618F, 0.0F));
        head.addOrReplaceChild("head2_r1", CubeListBuilder.create().texOffs(15, 40).addBox(3.2719F, 8.378F, 15.4429F, 2.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -9.8578F, -21.6642F, 0.0F, -0.2618F, 0.0F));
        head.addOrReplaceChild("headFlame", CubeListBuilder.create().texOffs(91, 35).addBox(-2.5F, -0.0265F, 0.0492F, 5.0F, 0.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.4773F, -4.6476F, 1.0472F, 0.0F, 0.0F));
        head.addOrReplaceChild("forehead", CubeListBuilder.create().texOffs(3, 37).addBox(-1.5F, -1.0F, -2.5F, 3.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.3523F, -2.3351F, 0.3491F, 0.0F, 0.0F));
        PartDefinition beak = head.addOrReplaceChild("beak", CubeListBuilder.create().texOffs(0, 84).addBox(-0.5F, -0.985F, -2.4099F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.495F, -5.991F, 0.2618F, 0.0F, 0.0F));
        beak.addOrReplaceChild("beakTip", CubeListBuilder.create().texOffs(1, 90).addBox(-0.5F, -0.5787F, -1.3924F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.05F)), PartPose.offsetAndRotation(0.0F, 0.1341F, -2.357F, 0.8727F, 0.0F, 0.0F));

        PartDefinition body = pd.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 62).addBox(-2.5F, -1.9563F, -0.5478F, 5.0F, 4.0F, 14.0F, new CubeDeformation(-0.4F)), PartPose.offsetAndRotation(0.0F, 11.5548F, -16.3574F, -0.0611F, 0.0F, 0.0F));
        body.addOrReplaceChild("neckFlame1", CubeListBuilder.create().texOffs(100, 57).addBox(-0.5221F, -8.9658F, -7.0F, 0.0F, 10.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0787F, -1.9034F, 6.6488F, 0.0F, 0.0436F, 0.1309F));
        body.addOrReplaceChild("neckFlame2", CubeListBuilder.create().texOffs(100, 45).addBox(0.0F, -9.0F, -7.0F, 0.0F, 10.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.5953F, 6.6488F));
        body.addOrReplaceChild("neckFlame3", CubeListBuilder.create().texOffs(100, 33).addBox(0.5221F, -8.9658F, -7.0F, 0.0F, 10.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0787F, -1.9034F, 6.6488F, 0.0F, -0.0436F, -0.1309F));
        
        PartDefinition mainBody = body.addOrReplaceChild("mainBody", CubeListBuilder.create().texOffs(13, 50).addBox(-2.0F, -1.6558F, 0.153F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, -0.0391F, 12.9329F, -0.0436F, 0.0F, 0.0F));
        PartDefinition bodysection3 = mainBody.addOrReplaceChild("bodysection3", CubeListBuilder.create().texOffs(0, 109).addBox(-3.5F, -2.2073F, 0.1809F, 7.0F, 5.0F, 12.0F, new CubeDeformation(0.25F)).texOffs(0, 82).addBox(-4.5F, -2.4315F, 8.1765F, 9.0F, 6.0F, 13.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, 0.0954F, 3.3462F, 0.0436F, 0.0F, 0.0F));
        bodysection3.addOrReplaceChild("backFlame1", CubeListBuilder.create().texOffs(55, 52).addBox(-0.4564F, -9.4701F, -10.5199F, 0.0F, 12.0F, 21.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.3902F, -2.7139F, 10.8726F, 0.0F, 0.0436F, 0.1309F));
        bodysection3.addOrReplaceChild("backFlame2", CubeListBuilder.create().texOffs(55, 37).addBox(0.0F, -10.0F, -10.5F, 0.0F, 12.0F, 21.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.6516F, 10.8698F));
        bodysection3.addOrReplaceChild("backFlame3", CubeListBuilder.create().texOffs(55, 22).addBox(0.4564F, -9.4701F, -10.5199F, 0.0F, 12.0F, 21.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.3902F, -2.7139F, 10.8726F, 0.0F, -0.0436F, -0.1309F));
        
        PartDefinition butt = bodysection3.addOrReplaceChild("butt", CubeListBuilder.create().texOffs(40, 114).addBox(-4.5F, -2.965F, -0.4453F, 9.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(43, 103).addBox(-3.5F, -2.4836F, 3.4001F, 7.0F, 5.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, 0.6256F, 21.0066F, 0.1484F, 0.0F, 0.0F));
        PartDefinition upperLeftTail = butt.addOrReplaceChild("upperLeftTail", CubeListBuilder.create().texOffs(108, 31).addBox(-1.2343F, 0.0F, -0.2642F, 3.0F, 0.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.2688F, -2.1408F, 5.6721F, 0.0F, 0.2618F, 0.0F));
        PartDefinition upperLeftTail2 = upperLeftTail.addOrReplaceChild("upperLeftTail2", CubeListBuilder.create().texOffs(108, 31).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 0.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.2657F, 0.0F, 11.7358F));
        upperLeftTail2.addOrReplaceChild("upperLeftTail3", CubeListBuilder.create().texOffs(108, 31).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 0.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 12.0F));
        PartDefinition upperRightTail = butt.addOrReplaceChild("upperRightTail", CubeListBuilder.create().texOffs(108, 31).addBox(-1.7657F, 0.0F, -0.2642F, 3.0F, 0.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.2688F, -2.1408F, 5.6721F, 0.0F, -0.2618F, 0.0F));
        PartDefinition upperRightTail2 = upperRightTail.addOrReplaceChild("upperRightTail2", CubeListBuilder.create().texOffs(108, 31).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 0.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.2657F, 0.0F, 11.7358F));
        upperRightTail2.addOrReplaceChild("upperRightTail3", CubeListBuilder.create().texOffs(108, 31).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 0.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 12.0F));
        PartDefinition lowerLeftTail = butt.addOrReplaceChild("lowerLeftTail", CubeListBuilder.create().texOffs(108, 31).addBox(-1.1515F, 0.0F, -0.9466F, 3.0F, 0.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.3771F, -1.3908F, 6.0934F, 0.0F, 0.0873F, 0.0F));
        PartDefinition lowerLeftTail2 = lowerLeftTail.addOrReplaceChild("lowerLeftTail2", CubeListBuilder.create().texOffs(108, 31).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 0.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.3485F, 0.0F, 11.0534F));
        lowerLeftTail2.addOrReplaceChild("lowerLeftTail3", CubeListBuilder.create().texOffs(108, 31).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 0.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 12.0F));
        PartDefinition lowerRightTail = butt.addOrReplaceChild("lowerRightTail", CubeListBuilder.create().texOffs(108, 31).addBox(-1.8485F, 0.0F, -0.9466F, 3.0F, 0.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.3771F, -1.3908F, 6.0934F, 0.0F, -0.0873F, 0.0F));
        PartDefinition lowerRightTail2 = lowerRightTail.addOrReplaceChild("lowerRightTail2", CubeListBuilder.create().texOffs(108, 31).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 0.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.3485F, 0.0F, 11.0534F));
        lowerRightTail2.addOrReplaceChild("lowerRightTail3", CubeListBuilder.create().texOffs(108, 31).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 0.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 12.0F));

        PartDefinition rightWing = pd.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(-26, 0).addBox(-13.25F, 0.0125F, 0.0F, 15.0F, 0.0F, 26.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.75F, 13.5842F, 2.295F));
        PartDefinition rightWing2 = rightWing.addOrReplaceChild("rightWing2", CubeListBuilder.create().texOffs(7, 0).addBox(-15.0F, 0.0F, 0.0F, 15.0F, 0.0F, 26.0F, new CubeDeformation(0.0F)), PartPose.offset(-13.25F, 0.0125F, 0.0F));
        PartDefinition rightWing3 = rightWing2.addOrReplaceChild("rightWing3", CubeListBuilder.create().texOffs(39, 0).addBox(-15.0F, 0.0F, 0.0F, 15.0F, 0.0F, 26.0F, new CubeDeformation(0.0F)), PartPose.offset(-15.0F, 0.0F, 0.0F));
        rightWing3.addOrReplaceChild("rightWing4", CubeListBuilder.create().texOffs(71, 0).addBox(-15.0F, 0.0F, 0.0F, 15.0F, 0.0F, 26.0F, new CubeDeformation(0.0F)), PartPose.offset(-15.0F, 0.0F, 0.0F));

        PartDefinition leftWing = pd.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(-26, 0).mirror().addBox(-1.75F, 0.0125F, 0.0F, 15.0F, 0.0F, 26.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(3.75F, 13.5842F, 2.295F));
        PartDefinition leftWing2 = leftWing.addOrReplaceChild("leftWing2", CubeListBuilder.create().texOffs(7, 0).mirror().addBox(0.0F, 0.0F, 0.0F, 15.0F, 0.0F, 26.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(13.25F, 0.0125F, 0.0F));
        PartDefinition leftWing3 = leftWing2.addOrReplaceChild("leftWing3", CubeListBuilder.create().texOffs(39, 0).mirror().addBox(0.0F, 0.0F, 0.0F, 15.0F, 0.0F, 26.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(15.0F, 0.0F, 0.0F));
        leftWing3.addOrReplaceChild("leftWing4", CubeListBuilder.create().texOffs(71, 0).mirror().addBox(0.0F, 0.0F, 0.0F, 15.0F, 0.0F, 26.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(15.0F, 0.0F, 0.0F));

        pd.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(43, 87).addBox(-1.5F, -1.6084F, -1.6823F, 3.0F, 5.0F, 3.0F, new CubeDeformation(-0.001F)), PartPose.offsetAndRotation(2.75F, 16.5272F, 24.3203F, 0.48F, 0.0F, 0.0F));
        pd.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(43, 87).mirror().addBox(-1.5F, -2.2539F, -1.8395F, 3.0F, 5.0F, 3.0F, new CubeDeformation(-0.001F)).mirror(false), PartPose.offsetAndRotation(-2.75F, 17.0272F, 24.7578F, 0.48F, 0.0F, 0.0F));

        return LayerDefinition.create(mesh, 128, 128);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        root.getAllParts().forEach(ModelPart::resetPose);
        this.head.yRot = netHeadYaw * ((float)Math.PI / 180F);
        this.head.xRot = headPitch * ((float)Math.PI / 180F);

        this.upperLeftTail.yRot += Math.sin(ageInTicks * 0.04) / 10.0F;
        this.upperLeftTail.xRot += Math.sin(ageInTicks * 0.01) / 8.0F;
        this.upperRightTail.yRot -= Math.sin(ageInTicks * 0.08) / 10.0F;
        this.upperRightTail.xRot -= Math.sin(ageInTicks * 0.05) / 8.0F;
        this.lowerLeftTail.yRot += Math.sin(ageInTicks * 0.08) / 10.0F;
        this.lowerLeftTail.xRot += Math.sin(ageInTicks * 0.05) / 8.0F;
        this.lowerRightTail.yRot += Math.sin(ageInTicks * 0.06) / 10.0F;
        this.lowerRightTail.xRot += Math.sin(ageInTicks * 0.02) / 8.0F;
        
        this.upperLeftTail2.yRot += Math.sin(ageInTicks * 0.06) / 10.0F;
        this.upperLeftTail2.xRot += Math.sin(ageInTicks * 0.02) / 5.0F;
        this.upperRightTail2.yRot += Math.sin(ageInTicks * 0.06) / 10.0F;
        this.upperRightTail2.xRot += Math.sin(ageInTicks * 0.02) / 5.0F;
        this.lowerLeftTail2.yRot -= Math.sin(ageInTicks * 0.04) / 10.0F;
        this.lowerLeftTail2.xRot -= Math.sin(ageInTicks * 0.01) / 5.0F;
        this.lowerRightTail2.yRot -= Math.sin(ageInTicks * 0.05) / 10.0F;
        this.lowerRightTail2.xRot -= Math.sin(ageInTicks * 0.01) / 5.0F;
        
        this.upperLeftTail3.yRot += Math.sin(ageInTicks * 0.08) / 10.0F;
        this.upperLeftTail3.xRot += Math.sin(ageInTicks * 0.05) / 8.0F;
        this.upperRightTail3.yRot += Math.sin(ageInTicks * 0.08) / 10.0F;
        this.upperRightTail3.xRot += Math.sin(ageInTicks * 0.05) / 8.0F;
        this.lowerLeftTail3.yRot += Math.sin(ageInTicks * 0.08) / 10.0F;
        this.lowerLeftTail3.xRot += Math.sin(ageInTicks * 0.05) / 8.0F;
        this.lowerRightTail3.yRot += Math.sin(ageInTicks * 0.08) / 10.0F;
        this.lowerRightTail3.xRot += Math.sin(ageInTicks * 0.04) / 8.0F;

        float wingSpeed = 0.3F;
        float wingRot = Mth.cos(ageInTicks * wingSpeed + (float)Math.PI) * 0.3F;
        float wingZ = Mth.cos(ageInTicks * wingSpeed + (float)Math.PI) * 0.4F;
        
        this.leftWing.yRot -= 0.1F + wingRot;
        this.leftWing.zRot -= wingZ;
        this.leftWing2.zRot -= wingZ / 3.0F;
        this.leftWing3.zRot -= wingZ / 3.0F;
        this.leftWing4.zRot -= wingZ / 3.0F;
        
        this.rightWing.yRot += 0.1F + wingRot;
        this.rightWing.zRot += wingZ;
        this.rightWing2.zRot += wingZ / 3.0F;
        this.rightWing3.zRot += wingZ / 3.0F;
        this.rightWing4.zRot += wingZ / 3.0F;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        poseStack.pushPose();
        poseStack.translate(0.0, 0.0, -0.4);
        root.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
        poseStack.popPose();
    }
}