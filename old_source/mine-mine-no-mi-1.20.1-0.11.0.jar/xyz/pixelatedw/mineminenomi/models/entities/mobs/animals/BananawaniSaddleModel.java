package xyz.pixelatedw.mineminenomi.models.entities.mobs.animals;

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
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.BananawaniEntity;

public class BananawaniSaddleModel<T extends BananawaniEntity> extends EntityModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "bananawani_saddle"), "main");
   private final ModelPart saddle;
   private final ModelPart saddle_seat;
   private final ModelPart saddle_seat2;
   private final ModelPart saddle_seat_border_left;
   private final ModelPart saddle_seat_border_left2;
   private final ModelPart saddle_seat_border_right;
   private final ModelPart saddle_seat_border_right2;
   private final ModelPart saddle_seat_pillow;
   private final ModelPart saddle_roof;
   private final ModelPart saddle_roof_spikes1;
   private final ModelPart saddle_roof_spikes2;
   private final ModelPart saddle_roof_spikes3;
   private final ModelPart saddle_roof_spikes4;
   private final ModelPart saddle_roof_spikes5;
   private final ModelPart saddle_roof_spikes6;
   private final ModelPart saddle_support_back_left;
   private final ModelPart saddle_support_back_left2;
   private final ModelPart saddle_support_front_left;
   private final ModelPart saddle_support_front_left2;
   private final ModelPart saddle_support_front_left3;
   private final ModelPart saddle_support_front_right;
   private final ModelPart saddle_support_front_right2;
   private final ModelPart saddle_support_front_right3;
   private final ModelPart saddle_support_back_right;
   private final ModelPart saddle_support_back_right2;

   public BananawaniSaddleModel(ModelPart root) {
      this.saddle = root.m_171324_("saddle");
      this.saddle_seat = this.saddle.m_171324_("saddle_seat");
      this.saddle_seat2 = this.saddle_seat.m_171324_("saddle_seat2");
      this.saddle_seat_border_left = this.saddle_seat.m_171324_("saddle_seat_border_left");
      this.saddle_seat_border_left2 = this.saddle_seat_border_left.m_171324_("saddle_seat_border_left2");
      this.saddle_seat_border_right = this.saddle_seat.m_171324_("saddle_seat_border_right");
      this.saddle_seat_border_right2 = this.saddle_seat_border_right.m_171324_("saddle_seat_border_right2");
      this.saddle_seat_pillow = this.saddle_seat.m_171324_("saddle_seat_pillow");
      this.saddle_roof = this.saddle.m_171324_("saddle_roof");
      this.saddle_roof_spikes1 = this.saddle_roof.m_171324_("saddle_roof_spikes1");
      this.saddle_roof_spikes2 = this.saddle_roof.m_171324_("saddle_roof_spikes2");
      this.saddle_roof_spikes3 = this.saddle_roof.m_171324_("saddle_roof_spikes3");
      this.saddle_roof_spikes4 = this.saddle_roof.m_171324_("saddle_roof_spikes4");
      this.saddle_roof_spikes5 = this.saddle_roof.m_171324_("saddle_roof_spikes5");
      this.saddle_roof_spikes6 = this.saddle_roof.m_171324_("saddle_roof_spikes6");
      this.saddle_support_back_left = this.saddle.m_171324_("saddle_support_back_left");
      this.saddle_support_back_left2 = this.saddle_support_back_left.m_171324_("saddle_support_back_left2");
      this.saddle_support_front_left = this.saddle.m_171324_("saddle_support_front_left");
      this.saddle_support_front_left2 = this.saddle_support_front_left.m_171324_("saddle_support_front_left2");
      this.saddle_support_front_left3 = this.saddle_support_front_left2.m_171324_("saddle_support_front_left3");
      this.saddle_support_front_right = this.saddle.m_171324_("saddle_support_front_right");
      this.saddle_support_front_right2 = this.saddle_support_front_right.m_171324_("saddle_support_front_right2");
      this.saddle_support_front_right3 = this.saddle_support_front_right2.m_171324_("saddle_support_front_right3");
      this.saddle_support_back_right = this.saddle.m_171324_("saddle_support_back_right");
      this.saddle_support_back_right2 = this.saddle_support_back_right.m_171324_("saddle_support_back_right2");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition saddle = partdefinition.m_171599_("saddle", CubeListBuilder.m_171558_().m_171514_(0, 33).m_171488_(-5.0F, -1.0F, -11.0F, 10.0F, 1.0F, 21.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 8.0F, 3.0F));
      PartDefinition saddle_seat = saddle.m_171599_("saddle_seat", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-5.0F, -7.0F, -0.25F, 10.0F, 7.0F, 1.0F, new CubeDeformation(0.01F)), PartPose.m_171423_(0.0F, 0.25F, 4.75F, -0.5236F, 0.0F, 0.0F));
      saddle_seat.m_171599_("saddle_seat2", CubeListBuilder.m_171558_().m_171514_(0, 8).m_171488_(-5.0F, -6.0F, -0.25F, 10.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, -6.25F, 0.0F, 0.1745F, 0.0F, 0.0F));
      PartDefinition saddle_seat_border_left = saddle_seat.m_171599_("saddle_seat_border_left", CubeListBuilder.m_171558_().m_171514_(4, 19).m_171488_(-1.0F, -6.0F, -0.25F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(5.5F, -1.0F, -0.75F));
      saddle_seat_border_left.m_171599_("saddle_seat_border_left2", CubeListBuilder.m_171558_().m_171514_(8, 19).m_171488_(-1.0F, -4.0F, -0.25F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.01F)), PartPose.m_171423_(0.0F, -7.75F, -0.25F, 0.1745F, 0.0F, 0.0F));
      PartDefinition saddle_seat_border_right = saddle_seat.m_171599_("saddle_seat_border_right", CubeListBuilder.m_171558_().m_171514_(4, 19).m_171488_(-1.0F, -6.0F, -0.25F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(-4.5F, -1.0F, -0.75F));
      saddle_seat_border_right.m_171599_("saddle_seat_border_right2", CubeListBuilder.m_171558_().m_171514_(8, 19).m_171488_(-1.0F, -4.0F, -0.25F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.01F)), PartPose.m_171423_(0.0F, -7.75F, -0.25F, 0.1745F, 0.0F, 0.0F));
      saddle_seat.m_171599_("saddle_seat_pillow", CubeListBuilder.m_171558_().m_171514_(0, 15).m_171488_(-5.0F, -3.0F, 0.0F, 10.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, -10.75F, -1.75F, 0.3491F, 0.0F, 0.0F));
      PartDefinition saddle_roof = saddle.m_171599_("saddle_roof", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-5.0F, -2.0F, -16.75F, 10.0F, 2.0F, 31.0F, new CubeDeformation(0.02F)), PartPose.m_171419_(0.0F, -14.5F, 4.5F));
      saddle_roof.m_171599_("saddle_roof_spikes1", CubeListBuilder.m_171558_().m_171514_(0, 55).m_171488_(-5.0F, -1.0F, -3.5F, 10.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, -2.0F, -13.0F, 0.2618F, 0.0F, 0.0F));
      saddle_roof.m_171599_("saddle_roof_spikes2", CubeListBuilder.m_171558_().m_171514_(0, 55).m_171488_(-5.0F, -1.0F, -3.0F, 10.0F, 2.0F, 7.0F, new CubeDeformation(0.01F)), PartPose.m_171423_(0.0F, -2.0F, -9.0F, 0.2618F, 0.0F, 0.0F));
      saddle_roof.m_171599_("saddle_roof_spikes3", CubeListBuilder.m_171558_().m_171514_(0, 55).m_171488_(-5.0F, -1.0F, -3.5F, 10.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, -2.0F, -4.0F, 0.2618F, 0.0F, 0.0F));
      saddle_roof.m_171599_("saddle_roof_spikes4", CubeListBuilder.m_171558_().m_171514_(0, 55).m_171488_(-5.0F, -1.0F, -3.5F, 10.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, -2.0F, 1.0F, 0.2618F, 0.0F, 0.0F));
      saddle_roof.m_171599_("saddle_roof_spikes5", CubeListBuilder.m_171558_().m_171514_(0, 55).m_171488_(-5.0F, -1.0F, -3.5F, 10.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, -2.0F, 6.0F, 0.2618F, 0.0F, 0.0F));
      saddle_roof.m_171599_("saddle_roof_spikes6", CubeListBuilder.m_171558_().m_171514_(0, 55).m_171488_(-5.0F, -1.0F, -3.5F, 10.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, -2.0F, 11.0F, 0.2618F, 0.0F, 0.0F));
      PartDefinition saddle_support_back_left = saddle.m_171599_("saddle_support_back_left", CubeListBuilder.m_171558_().m_171514_(0, 19).m_171488_(-1.0F, -9.0F, 0.0F, 1.0F, 9.0F, 1.0F, new CubeDeformation(0.01F)), PartPose.m_171423_(5.0F, 0.0F, 9.0F, -0.3491F, 0.0F, 0.0F));
      saddle_support_back_left.m_171599_("saddle_support_back_left2", CubeListBuilder.m_171558_().m_171514_(0, 19).m_171488_(-1.0F, -9.0F, 0.0F, 1.0F, 9.0F, 1.0F, new CubeDeformation(0.01F)), PartPose.m_171423_(0.0F, -9.0F, 0.0F, -0.3491F, 0.0F, 0.0F));
      PartDefinition saddle_support_front_left = saddle.m_171599_("saddle_support_front_left", CubeListBuilder.m_171558_().m_171514_(8, 19).m_171488_(-1.0F, -6.0F, 0.0F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.01F)), PartPose.m_171423_(4.65F, 0.0F, -10.75F, 0.0F, 0.0F, 0.3491F));
      PartDefinition saddle_support_front_left2 = saddle_support_front_left.m_171599_("saddle_support_front_left2", CubeListBuilder.m_171558_().m_171514_(4, 33).m_171488_(-1.0F, -5.0F, 0.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.01F)), PartPose.m_171423_(0.0F, -5.75F, 0.0F, -0.0436F, 0.0F, -0.2618F));
      saddle_support_front_left2.m_171599_("saddle_support_front_left3", CubeListBuilder.m_171558_().m_171514_(8, 19).m_171488_(-1.0F, -6.0F, 0.0F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.01F)), PartPose.m_171423_(0.0F, -5.0F, 0.0F, 0.0F, 0.0F, -0.4363F));
      PartDefinition saddle_support_front_right = saddle.m_171599_("saddle_support_front_right", CubeListBuilder.m_171558_().m_171514_(8, 19).m_171480_().m_171488_(0.0F, -6.0F, 0.0F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.01F)).m_171555_(false), PartPose.m_171423_(-4.65F, 0.0F, -10.75F, 0.0F, 0.0F, -0.3491F));
      PartDefinition saddle_support_front_right2 = saddle_support_front_right.m_171599_("saddle_support_front_right2", CubeListBuilder.m_171558_().m_171514_(4, 33).m_171480_().m_171488_(0.0F, -5.0F, 0.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.01F)).m_171555_(false), PartPose.m_171423_(0.0F, -5.75F, 0.0F, -0.0436F, 0.0F, 0.2618F));
      saddle_support_front_right2.m_171599_("saddle_support_front_right3", CubeListBuilder.m_171558_().m_171514_(8, 19).m_171480_().m_171488_(0.0F, -6.0F, 0.0F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.01F)).m_171555_(false), PartPose.m_171423_(0.0F, -5.0F, 0.0F, 0.0F, 0.0F, 0.4363F));
      PartDefinition saddle_support_back_right = saddle.m_171599_("saddle_support_back_right", CubeListBuilder.m_171558_().m_171514_(0, 19).m_171488_(-1.0F, -9.0F, 0.0F, 1.0F, 9.0F, 1.0F, new CubeDeformation(0.01F)), PartPose.m_171423_(-4.0F, 0.0F, 9.0F, -0.3491F, 0.0F, 0.0F));
      saddle_support_back_right.m_171599_("saddle_support_back_right2", CubeListBuilder.m_171558_().m_171514_(0, 19).m_171488_(-1.0F, -9.0F, 0.0F, 1.0F, 9.0F, 1.0F, new CubeDeformation(0.01F)), PartPose.m_171423_(0.0F, -9.0F, 0.0F, -0.3491F, 0.0F, 0.0F));
      return LayerDefinition.m_171565_(meshdefinition, 128, 64);
   }

   public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.saddle.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }
}
