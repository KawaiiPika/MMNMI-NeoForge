package xyz.pixelatedw.mineminenomi.models.entities.vehicles;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.entities.vehicles.CannonEntity;

public class CannonModel extends EntityModel<CannonEntity> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "cannon"), "main");
   private final ModelPart cart;
   private final ModelPart cartBottom;
   private final ModelPart cartWheel1;
   private final ModelPart cartWheel3;
   private final ModelPart cartWheel2;
   private final ModelPart cartWheel4;
   private final ModelPart cartWallFront;
   private final ModelPart cartWallRight;
   private final ModelPart cartWallRight1;
   private final ModelPart cartWallRight2;
   private final ModelPart cartWallLeft;
   private final ModelPart cartWallLeft1;
   private final ModelPart cartWallLeft2;
   private final ModelPart cannon;
   private final ModelPart cannonDeco3;
   private final ModelPart cannonBack2;
   private final ModelPart cannonBackKnob;
   private final ModelPart cannonBack;
   private final ModelPart cannonDeco;
   private final ModelPart cannonThing;
   private final ModelPart cannonDeco2;

   public CannonModel(EntityRendererProvider.Context ctx) {
      ModelPart root = ctx.bakeLayer(LAYER_LOCATION);
      this.cart = root.getChild("cart");
      this.cartBottom = this.cart.getChild("cartBottom");
      this.cartWheel1 = this.cartBottom.getChild("cartWheel1");
      this.cartWheel3 = this.cartBottom.getChild("cartWheel3");
      this.cartWheel2 = this.cartBottom.getChild("cartWheel2");
      this.cartWheel4 = this.cartBottom.getChild("cartWheel4");
      this.cartWallFront = this.cart.getChild("cartWallFront");
      this.cartWallRight = this.cart.getChild("cartWallRight");
      this.cartWallRight1 = this.cartWallRight.getChild("cartWallRight1");
      this.cartWallRight2 = this.cartWallRight.getChild("cartWallRight2");
      this.cartWallLeft = this.cart.getChild("cartWallLeft");
      this.cartWallLeft1 = this.cartWallLeft.getChild("cartWallLeft1");
      this.cartWallLeft2 = this.cartWallLeft.getChild("cartWallLeft2");
      this.cannon = root.getChild("cannon");
      this.cannonDeco3 = this.cannon.getChild("cannonDeco3");
      this.cannonBack2 = this.cannon.getChild("cannonBack2");
      this.cannonBackKnob = this.cannon.getChild("cannonBackKnob");
      this.cannonBack = this.cannon.getChild("cannonBack");
      this.cannonDeco = this.cannon.getChild("cannonDeco");
      this.cannonThing = this.cannon.getChild("cannonThing");
      this.cannonDeco2 = this.cannon.getChild("cannonDeco2");
   }

   public static LayerDefinition createLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.getRoot();
      PartDefinition cart = partdefinition.addOrReplaceChild("cart", CubeListBuilder.create().texOffs(24, 44).addBox(-2.5F, 0.0F, -6.0F, 5.0F, 5.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 16.0F, 0.0F));
      PartDefinition cartBottom = cart.addOrReplaceChild("cartBottom", CubeListBuilder.create().texOffs(0, 43).addBox(-6.0F, -0.5F, -10.0F, 12.0F, 1.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 5.5F, 3.0F));
      cartBottom.addOrReplaceChild("cartWheel1", CubeListBuilder.create().texOffs(0, 12).addBox(-1.0F, -2.5F, -2.5F, 2.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-6.0F, 0.5F, -5.5F));
      cartBottom.addOrReplaceChild("cartWheel3", CubeListBuilder.create().texOffs(0, 12).addBox(-1.0F, -2.5F, -2.5F, 2.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(6.0F, 0.5F, -5.5F));
      cartBottom.addOrReplaceChild("cartWheel2", CubeListBuilder.create().texOffs(0, 12).addBox(-1.0F, -2.5F, -2.5F, 2.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-6.0F, 0.5F, 5.0F));
      cartBottom.addOrReplaceChild("cartWheel4", CubeListBuilder.create().texOffs(0, 12).addBox(-1.0F, -2.5F, -2.5F, 2.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(6.0F, 0.5F, 5.0F));
      cart.addOrReplaceChild("cartWallFront", CubeListBuilder.create().texOffs(0, 45).addBox(-6.0F, -3.0F, -0.5F, 12.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, -6.5F));
      PartDefinition cartWallRight = cart.addOrReplaceChild("cartWallRight", CubeListBuilder.create().texOffs(0, 44).addBox(-1.0F, -1.0F, -9.0F, 2.0F, 2.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, 4.0F, 3.0F));
      cartWallRight.addOrReplaceChild("cartWallRight1", CubeListBuilder.create().texOffs(0, 44).addBox(-8.0F, 15.0F, 6.0F, 2.0F, 2.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(7.0F, -18.0F, -15.0F));
      cartWallRight.addOrReplaceChild("cartWallRight2", CubeListBuilder.create().texOffs(0, 44).addBox(-8.0F, 15.0F, 6.0F, 2.0F, 2.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(7.0F, -20.0F, -15.0F));
      PartDefinition cartWallLeft = cart.addOrReplaceChild("cartWallLeft", CubeListBuilder.create().texOffs(0, 44).addBox(-1.0F, -1.0F, -9.0F, 2.0F, 2.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, 4.0F, 3.0F));
      cartWallLeft.addOrReplaceChild("cartWallLeft1", CubeListBuilder.create().texOffs(0, 44).addBox(-8.0F, 0.0F, 6.0F, 2.0F, 2.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(7.0F, -5.0F, -15.0F));
      cartWallLeft.addOrReplaceChild("cartWallLeft2", CubeListBuilder.create().texOffs(0, 44).addBox(-8.0F, 0.0F, 6.0F, 2.0F, 2.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(7.0F, -3.0F, -15.0F));
      PartDefinition cannon = partdefinition.addOrReplaceChild("cannon", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -5.5F, -18.0F, 6.0F, 6.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 15.0F, 3.0F));
      cannon.addOrReplaceChild("cannonDeco3", CubeListBuilder.create().texOffs(0, 0).addBox(-3.5F, -3.5F, -0.5F, 7.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.5F, -5.0F));
      cannon.addOrReplaceChild("cannonBack2", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -3.0F, -1.0F, 6.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.5F, 8.0F));
      cannon.addOrReplaceChild("cannonBackKnob", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.5F, 10.0F));
      cannon.addOrReplaceChild("cannonBack", CubeListBuilder.create().texOffs(0, 0).addBox(-3.5F, -3.5F, -2.0F, 7.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.5F, 5.0F));
      cannon.addOrReplaceChild("cannonDeco", CubeListBuilder.create().texOffs(46, 0).addBox(-3.5F, -3.5F, -1.0F, 7.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.5F, -18.0F));
      cannon.addOrReplaceChild("cannonThing", CubeListBuilder.create().texOffs(0, 1).addBox(-15.5F, 0.0F, 6.0F, 15.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, -1.5F, -8.0F));
      cannon.addOrReplaceChild("cannonDeco2", CubeListBuilder.create().texOffs(0, 0).addBox(-11.5F, -3.5F, 6.0F, 7.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, -2.5F, -20.5F));
      return LayerDefinition.create(meshdefinition, 64, 64);
   }

   public void setupAnim(CannonEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      if (entity.getControllingPassenger() != null) {
         LivingEntity var8 = entity.getControllingPassenger();
         if (var8 instanceof Player) {
            Player player = (Player)var8;
            ModelPart var10000 = this.cartWheel1;
            var10000.xRot += 0.02F * player.xxa;
            var10000 = this.cartWheel2;
            var10000.xRot += 0.02F * player.xxa;
            var10000 = this.cartWheel3;
            var10000.xRot += 0.02F * player.xxa;
            var10000 = this.cartWheel4;
            var10000.xRot += 0.02F * player.xxa;
         }
      }

      this.cannon.xRot = (float)Math.toRadians((double)entity.getXRot());
   }

   public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
      this.cart.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
      this.cannon.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
   }
}
