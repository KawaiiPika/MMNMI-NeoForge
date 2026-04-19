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
      ModelPart root = ctx.m_174023_(LAYER_LOCATION);
      this.cart = root.m_171324_("cart");
      this.cartBottom = this.cart.m_171324_("cartBottom");
      this.cartWheel1 = this.cartBottom.m_171324_("cartWheel1");
      this.cartWheel3 = this.cartBottom.m_171324_("cartWheel3");
      this.cartWheel2 = this.cartBottom.m_171324_("cartWheel2");
      this.cartWheel4 = this.cartBottom.m_171324_("cartWheel4");
      this.cartWallFront = this.cart.m_171324_("cartWallFront");
      this.cartWallRight = this.cart.m_171324_("cartWallRight");
      this.cartWallRight1 = this.cartWallRight.m_171324_("cartWallRight1");
      this.cartWallRight2 = this.cartWallRight.m_171324_("cartWallRight2");
      this.cartWallLeft = this.cart.m_171324_("cartWallLeft");
      this.cartWallLeft1 = this.cartWallLeft.m_171324_("cartWallLeft1");
      this.cartWallLeft2 = this.cartWallLeft.m_171324_("cartWallLeft2");
      this.cannon = root.m_171324_("cannon");
      this.cannonDeco3 = this.cannon.m_171324_("cannonDeco3");
      this.cannonBack2 = this.cannon.m_171324_("cannonBack2");
      this.cannonBackKnob = this.cannon.m_171324_("cannonBackKnob");
      this.cannonBack = this.cannon.m_171324_("cannonBack");
      this.cannonDeco = this.cannon.m_171324_("cannonDeco");
      this.cannonThing = this.cannon.m_171324_("cannonThing");
      this.cannonDeco2 = this.cannon.m_171324_("cannonDeco2");
   }

   public static LayerDefinition createLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition cart = partdefinition.m_171599_("cart", CubeListBuilder.m_171558_().m_171514_(24, 44).m_171488_(-2.5F, 0.0F, -6.0F, 5.0F, 5.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 16.0F, 0.0F));
      PartDefinition cartBottom = cart.m_171599_("cartBottom", CubeListBuilder.m_171558_().m_171514_(0, 43).m_171488_(-6.0F, -0.5F, -10.0F, 12.0F, 1.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 5.5F, 3.0F));
      cartBottom.m_171599_("cartWheel1", CubeListBuilder.m_171558_().m_171514_(0, 12).m_171488_(-1.0F, -2.5F, -2.5F, 2.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(-6.0F, 0.5F, -5.5F));
      cartBottom.m_171599_("cartWheel3", CubeListBuilder.m_171558_().m_171514_(0, 12).m_171488_(-1.0F, -2.5F, -2.5F, 2.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(6.0F, 0.5F, -5.5F));
      cartBottom.m_171599_("cartWheel2", CubeListBuilder.m_171558_().m_171514_(0, 12).m_171488_(-1.0F, -2.5F, -2.5F, 2.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(-6.0F, 0.5F, 5.0F));
      cartBottom.m_171599_("cartWheel4", CubeListBuilder.m_171558_().m_171514_(0, 12).m_171488_(-1.0F, -2.5F, -2.5F, 2.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(6.0F, 0.5F, 5.0F));
      cart.m_171599_("cartWallFront", CubeListBuilder.m_171558_().m_171514_(0, 45).m_171488_(-6.0F, -3.0F, -0.5F, 12.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 2.0F, -6.5F));
      PartDefinition cartWallRight = cart.m_171599_("cartWallRight", CubeListBuilder.m_171558_().m_171514_(0, 44).m_171488_(-1.0F, -1.0F, -9.0F, 2.0F, 2.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(5.0F, 4.0F, 3.0F));
      cartWallRight.m_171599_("cartWallRight1", CubeListBuilder.m_171558_().m_171514_(0, 44).m_171488_(-8.0F, 15.0F, 6.0F, 2.0F, 2.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(7.0F, -18.0F, -15.0F));
      cartWallRight.m_171599_("cartWallRight2", CubeListBuilder.m_171558_().m_171514_(0, 44).m_171488_(-8.0F, 15.0F, 6.0F, 2.0F, 2.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(7.0F, -20.0F, -15.0F));
      PartDefinition cartWallLeft = cart.m_171599_("cartWallLeft", CubeListBuilder.m_171558_().m_171514_(0, 44).m_171488_(-1.0F, -1.0F, -9.0F, 2.0F, 2.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(-5.0F, 4.0F, 3.0F));
      cartWallLeft.m_171599_("cartWallLeft1", CubeListBuilder.m_171558_().m_171514_(0, 44).m_171488_(-8.0F, 0.0F, 6.0F, 2.0F, 2.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(7.0F, -5.0F, -15.0F));
      cartWallLeft.m_171599_("cartWallLeft2", CubeListBuilder.m_171558_().m_171514_(0, 44).m_171488_(-8.0F, 0.0F, 6.0F, 2.0F, 2.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(7.0F, -3.0F, -15.0F));
      PartDefinition cannon = partdefinition.m_171599_("cannon", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-3.0F, -5.5F, -18.0F, 6.0F, 6.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 15.0F, 3.0F));
      cannon.m_171599_("cannonDeco3", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-3.5F, -3.5F, -0.5F, 7.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, -2.5F, -5.0F));
      cannon.m_171599_("cannonBack2", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-3.0F, -3.0F, -1.0F, 6.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, -2.5F, 8.0F));
      cannon.m_171599_("cannonBackKnob", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, -2.5F, 10.0F));
      cannon.m_171599_("cannonBack", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-3.5F, -3.5F, -2.0F, 7.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, -2.5F, 5.0F));
      cannon.m_171599_("cannonDeco", CubeListBuilder.m_171558_().m_171514_(46, 0).m_171488_(-3.5F, -3.5F, -1.0F, 7.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, -2.5F, -18.0F));
      cannon.m_171599_("cannonThing", CubeListBuilder.m_171558_().m_171514_(0, 1).m_171488_(-15.5F, 0.0F, 6.0F, 15.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(8.0F, -1.5F, -8.0F));
      cannon.m_171599_("cannonDeco2", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-11.5F, -3.5F, 6.0F, 7.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(8.0F, -2.5F, -20.5F));
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   public void setupAnim(CannonEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      if (entity.m_6688_() != null) {
         LivingEntity var8 = entity.m_6688_();
         if (var8 instanceof Player) {
            Player player = (Player)var8;
            ModelPart var10000 = this.cartWheel1;
            var10000.f_104203_ += 0.02F * player.f_20902_;
            var10000 = this.cartWheel2;
            var10000.f_104203_ += 0.02F * player.f_20902_;
            var10000 = this.cartWheel3;
            var10000.f_104203_ += 0.02F * player.f_20902_;
            var10000 = this.cartWheel4;
            var10000.f_104203_ += 0.02F * player.f_20902_;
         }
      }

      this.cannon.f_104203_ = (float)Math.toRadians((double)entity.m_146909_());
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.cart.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.cannon.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }
}
