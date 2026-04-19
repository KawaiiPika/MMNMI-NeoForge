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
import xyz.pixelatedw.mineminenomi.entities.mobs.pirates.buggypirates.CabajiEntity;
import xyz.pixelatedw.mineminenomi.entities.vehicles.UnicycleEntity;

public class UnicycleModel extends EntityModel<UnicycleEntity> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "unicycle"), "main");
   private final ModelPart seat;
   private final ModelPart support;
   private final ModelPart pedals;
   private final ModelPart left;
   private final ModelPart leftPedal;
   private final ModelPart right;
   private final ModelPart rightPedal;
   private final ModelPart wheel;

   public UnicycleModel(EntityRendererProvider.Context ctx) {
      ModelPart root = ctx.m_174023_(LAYER_LOCATION);
      this.seat = root.m_171324_("seat");
      this.support = this.seat.m_171324_("support");
      this.pedals = this.support.m_171324_("pedals");
      this.left = this.pedals.m_171324_("left");
      this.leftPedal = this.left.m_171324_("leftPedal");
      this.right = this.pedals.m_171324_("right");
      this.rightPedal = this.right.m_171324_("rightPedal");
      this.wheel = this.support.m_171324_("wheel");
   }

   public static LayerDefinition createLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition seat = partdefinition.m_171599_("seat", CubeListBuilder.m_171558_().m_171514_(10, 23).m_171488_(-2.0F, -2.0F, -3.25F, 4.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)).m_171514_(20, 0).m_171488_(-2.0F, -3.0F, 2.75F, 4.0F, 3.0F, 2.0F, new CubeDeformation(0.001F)), PartPose.m_171419_(0.0F, 11.0F, -0.5F));
      PartDefinition support = seat.m_171599_("support", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-1.0F, -4.0F, -0.5F, 2.0F, 4.0F, 2.0F, new CubeDeformation(-0.1F)).m_171514_(20, 11).m_171488_(-2.0F, -1.0F, -0.5F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.001F)).m_171514_(12, 11).m_171488_(1.0F, -1.0F, -0.5F, 1.0F, 6.0F, 2.0F, new CubeDeformation(-0.05F)).m_171514_(12, 11).m_171488_(-2.0F, -1.0F, -0.5F, 1.0F, 6.0F, 2.0F, new CubeDeformation(-0.05F)).m_171514_(0, 20).m_171488_(-1.0F, 3.5F, -0.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 3.5F, 0.0F));
      PartDefinition pedals = support.m_171599_("pedals", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 4.5F, 0.5F));
      PartDefinition left = pedals.m_171599_("left", CubeListBuilder.m_171558_().m_171514_(12, 8).m_171488_(-0.0833F, -0.6667F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(1.0833F, 0.1667F, 0.0F));
      left.m_171599_("leftPedal", CubeListBuilder.m_171558_().m_171514_(11, 1).m_171488_(0.0F, -0.5F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.1F)).m_171514_(11, 5).m_171488_(-1.5F, -0.5F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(2.4167F, 0.8333F, 0.0F));
      PartDefinition right = pedals.m_171599_("right", CubeListBuilder.m_171558_().m_171514_(12, 8).m_171480_().m_171488_(-2.0F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(-1.0F, 0.0F, 0.0F, 3.1416F, 0.0F, 0.0F));
      right.m_171599_("rightPedal", CubeListBuilder.m_171558_().m_171514_(11, 1).m_171480_().m_171488_(-2.0F, -0.5F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.001F)).m_171555_(false).m_171514_(11, 5).m_171480_().m_171488_(-1.5F, -0.5F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(-2.5F, 1.0F, 0.0F, 3.1416F, 0.0F, 0.0F));
      PartDefinition wheel = support.m_171599_("wheel", CubeListBuilder.m_171558_().m_171514_(0, 15).m_171488_(-1.0F, -0.9946F, -5.0F, 2.0F, 1.9891F, 2.0F, new CubeDeformation(0.001F)).m_171514_(1, 9).m_171488_(-0.5F, -0.5054F, -3.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(-1.0E-4F)).m_171514_(0, 15).m_171488_(-1.0F, -0.9946F, 3.0F, 2.0F, 1.9891F, 2.0F, new CubeDeformation(0.001F)).m_171514_(0, 8).m_171488_(-0.5F, -0.5054F, 0.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(-2.0E-4F)).m_171514_(0, 15).m_171488_(-1.0F, 3.0F, -0.9946F, 2.0F, 2.0F, 1.9891F, new CubeDeformation(0.001F)).m_171514_(0, 8).m_171488_(-0.5F, 0.0F, -0.4946F, 1.0F, 3.0F, 1.0F, new CubeDeformation(1.0E-4F)).m_171514_(0, 15).m_171488_(-1.0F, -5.0F, -0.9946F, 2.0F, 2.0F, 1.9891F, new CubeDeformation(0.001F)).m_171514_(0, 8).m_171488_(-0.5F, -3.0F, -0.5054F, 1.0F, 3.0F, 1.0F, new CubeDeformation(-1.0E-4F)), PartPose.m_171419_(0.0F, 4.5F, 0.5F));
      wheel.m_171599_("hexadecagon_r1", CubeListBuilder.m_171558_().m_171514_(0, 15).m_171488_(-1.0F, -5.0F, -0.9946F, 2.0F, 2.0F, 1.9891F, new CubeDeformation(-0.001F)).m_171514_(0, 15).m_171488_(-1.0F, 3.0F, -0.9946F, 2.0F, 2.0F, 1.9891F, new CubeDeformation(-0.001F)).m_171514_(0, 15).m_171488_(-1.0F, -0.9946F, 3.0F, 2.0F, 1.9891F, 2.0F, new CubeDeformation(-0.001F)).m_171514_(0, 15).m_171488_(-1.0F, -0.9946F, -5.0F, 2.0F, 1.9891F, 2.0F, new CubeDeformation(-0.001F)), PartPose.m_171423_(0.0F, 0.0F, 0.0F, -0.3927F, 0.0F, 0.0F));
      wheel.m_171599_("hexadecagon_r2", CubeListBuilder.m_171558_().m_171514_(0, 15).m_171488_(-1.0F, -5.0F, -0.9946F, 2.0F, 2.0F, 1.9891F, new CubeDeformation(-0.001F)).m_171514_(0, 15).m_171488_(-1.0F, 3.0F, -0.9946F, 2.0F, 2.0F, 1.9891F, new CubeDeformation(-0.001F)).m_171514_(0, 15).m_171488_(-1.0F, -0.9946F, 3.0F, 2.0F, 1.9891F, 2.0F, new CubeDeformation(-0.001F)).m_171514_(0, 15).m_171488_(-1.0F, -0.9946F, -5.0F, 2.0F, 1.9891F, 2.0F, new CubeDeformation(-0.001F)), PartPose.m_171423_(0.0F, 0.0F, 0.0F, 0.3927F, 0.0F, 0.0F));
      wheel.m_171599_("inner_r1", CubeListBuilder.m_171558_().m_171514_(1, 9).m_171488_(-1.0F, -0.0054F, 1.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(-1.0E-4F)), PartPose.m_171423_(0.5F, -0.5F, 0.0F, -0.7854F, 0.0F, 0.0F));
      wheel.m_171599_("hexadecagon_r3", CubeListBuilder.m_171558_().m_171514_(0, 15).m_171488_(-1.0F, -0.9946F, 3.0F, 2.0F, 1.9891F, 2.0F, new CubeDeformation(0.001F)).m_171514_(0, 15).m_171488_(-1.0F, -0.9946F, -5.0F, 2.0F, 1.9891F, 2.0F, new CubeDeformation(0.001F)), PartPose.m_171423_(0.0F, 0.0F, 0.0F, -0.7854F, 0.0F, 0.0F));
      wheel.m_171599_("inner_r2", CubeListBuilder.m_171558_().m_171514_(0, 8).m_171488_(-1.0F, -0.0054F, 1.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(-3.0E-4F)), PartPose.m_171423_(0.5F, 0.0F, -0.75F, 0.7854F, 0.0F, 0.0F));
      wheel.m_171599_("hexadecagon_r4", CubeListBuilder.m_171558_().m_171514_(0, 15).m_171488_(-1.0F, -0.9946F, 3.0F, 2.0F, 1.9891F, 2.0F, new CubeDeformation(0.001F)).m_171514_(0, 15).m_171488_(-1.0F, -0.9946F, -5.0F, 2.0F, 1.9891F, 2.0F, new CubeDeformation(0.001F)), PartPose.m_171423_(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
      wheel.m_171599_("inner_r3", CubeListBuilder.m_171558_().m_171514_(0, 8).m_171488_(-1.0F, -0.0054F, -3.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(3.0E-4F)), PartPose.m_171423_(0.5F, -0.5F, 0.25F, -0.7854F, 0.0F, 0.0F));
      wheel.m_171599_("inner_r4", CubeListBuilder.m_171558_().m_171514_(0, 8).m_171488_(-1.0F, -0.0054F, -3.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(2.0E-4F)), PartPose.m_171423_(0.5F, -0.25F, -0.5F, 0.7854F, 0.0F, 0.0F));
      return LayerDefinition.m_171565_(meshdefinition, 32, 32);
   }

   public void setupAnim(UnicycleEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      LivingEntity controller;
      float swing;
      label29: {
         controller = null;
         swing = 0.0F;
         if (entity.m_6688_() != null) {
            LivingEntity var11 = entity.m_6688_();
            if (var11 instanceof Player) {
               Player player = (Player)var11;
               controller = player;
               swing = player.f_20902_;
               break label29;
            }
         }

         LivingEntity var15 = entity.m_6688_();
         if (var15 instanceof CabajiEntity cabaji) {
            double xDiff = Math.abs(entity.m_20185_() - entity.f_19790_);
            double zDiff = Math.abs(entity.m_20189_() - entity.f_19792_);
            if (cabaji.m_5448_() != null) {
               swing = 1.0F;
            } else if (xDiff > (double)0.0F || zDiff > (double)0.0F) {
               swing = 1.0F;
            }
         }
      }

      if (controller != null) {
         ModelPart var10000 = this.wheel;
         var10000.f_104203_ += 0.1F * swing % 360.0F;
         var10000 = this.pedals;
         var10000.f_104203_ += 0.2F * swing % 360.0F;
         var10000 = this.rightPedal;
         var10000.f_104203_ -= 0.2F * swing % 360.0F;
         var10000 = this.leftPedal;
         var10000.f_104203_ -= 0.2F * swing % 360.0F;
         if (swing == 0.0F) {
            this.pedals.f_104203_ = this.rightPedal.f_104203_ = this.leftPedal.f_104203_ = 0.0F;
         }
      }

   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.seat.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }
}
