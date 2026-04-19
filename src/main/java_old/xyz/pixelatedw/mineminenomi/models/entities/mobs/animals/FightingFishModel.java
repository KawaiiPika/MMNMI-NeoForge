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
import net.minecraft.util.Mth;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.FightingFishEntity;

public class FightingFishModel<T extends FightingFishEntity> extends EntityModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "fighting_fish"), "main");
   private final ModelPart body;
   private final ModelPart topFins;
   private final ModelPart tail;
   private final ModelPart flippersLeft;
   private final ModelPart flippersRight;
   private final ModelPart head;
   private final ModelPart rightHorn;
   private final ModelPart leftHorn;

   public FightingFishModel(ModelPart root) {
      this.body = root.m_171324_("body");
      this.topFins = this.body.m_171324_("topFins");
      this.tail = this.body.m_171324_("tail");
      this.flippersLeft = this.body.m_171324_("flippersLeft");
      this.flippersRight = this.body.m_171324_("flippersRight");
      this.head = this.body.m_171324_("head");
      this.rightHorn = this.head.m_171324_("rightHorn");
      this.leftHorn = this.head.m_171324_("leftHorn");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition body = partdefinition.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(83, 83).m_171488_(-18.0F, -56.0F, -7.0F, 36.0F, 50.0F, 26.0F, new CubeDeformation(0.0F)).m_171514_(0, 105).m_171488_(-17.0F, -55.0F, 19.0F, 34.0F, 48.0F, 7.0F, new CubeDeformation(0.0F)).m_171514_(180, 185).m_171488_(-16.0F, -54.0F, 26.0F, 32.0F, 46.0F, 6.0F, new CubeDeformation(0.0F)).m_171514_(0, 161).m_171488_(-13.0F, -51.0F, 32.0F, 26.0F, 40.0F, 9.0F, new CubeDeformation(0.0F)).m_171514_(201, 1).m_171488_(-7.0F, -46.0F, 41.0F, 14.0F, 30.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 16.0F, 0.0F));
      PartDefinition topFins = body.m_171599_("topFins", CubeListBuilder.m_171558_(), PartPose.m_171423_(0.0F, 8.0F, 0.0F, 0.0873F, 0.0F, 0.0F));
      topFins.m_171599_("Fin2_r1_r1", CubeListBuilder.m_171558_().m_171514_(42, 186).m_171488_(-1.0F, -57.0162F, 9.3144F, 0.0F, 14.0F, 29.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, -35.9056F, 53.2801F, 1.2217F, 0.0F, 0.0F));
      topFins.m_171599_("Fin3_r1_r1", CubeListBuilder.m_171558_().m_171514_(190, 32).m_171488_(-1.0F, -48.6147F, 10.8288F, 0.0F, 42.0F, 29.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, -31.4289F, 51.3315F, 1.1345F, 0.0F, 0.0F));
      body.m_171599_("tail", CubeListBuilder.m_171558_().m_171514_(156, 160).m_171488_(-1.0F, -18.0F, -4.0F, 2.0F, 18.0F, 8.0F, new CubeDeformation(0.0F)).m_171514_(0, 0).m_171488_(0.0F, -28.0F, 4.0F, 0.0F, 50.0F, 54.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, -22.0F, 54.0F));
      body.m_171599_("flippersLeft", CubeListBuilder.m_171558_().m_171514_(109, 59).m_171480_().m_171488_(-1.1585F, -7.7967F, 0.0F, 38.0F, 16.0F, 0.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(16.0F, -10.0F, -13.0F, 0.0F, 0.0F, 0.3927F));
      body.m_171599_("flippersRight", CubeListBuilder.m_171558_().m_171514_(109, 59).m_171488_(-35.8839F, -7.9665F, 0.0F, 38.0F, 16.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-17.0F, -10.0F, -13.0F, 0.0F, 0.0F, -0.3927F));
      PartDefinition head = body.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(109, 0).m_171488_(-17.0F, -63.0F, -17.0F, 34.0F, 48.0F, 10.0F, new CubeDeformation(0.0F)).m_171514_(75, 160).m_171488_(-16.0F, -62.0F, -25.0F, 32.0F, 46.0F, 8.0F, new CubeDeformation(0.0F)).m_171514_(0, 0).m_171488_(-15.0F, -58.0F, -33.0F, 30.0F, 38.0F, 8.0F, new CubeDeformation(0.0F)).m_171514_(167, 240).m_171488_(-13.5F, -36.0F, -38.0F, 27.0F, 11.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 8.0F, 0.0F));
      head.m_171599_("eyebrowRight_r1", CubeListBuilder.m_171558_().m_171514_(117, 215).m_171488_(-13.0F, -58.0F, -35.0F, 13.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-14.5302F, 8.9549F, -18.4865F, -0.2967F, 0.0F, 0.2182F));
      head.m_171599_("eyebrowLeft_r1", CubeListBuilder.m_171558_().m_171514_(117, 215).m_171488_(0.0F, -58.0F, -35.0F, 13.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(14.5302F, 8.9549F, -18.4865F, -0.2967F, 0.0F, -0.2182F));
      head.m_171599_("nose_r1", CubeListBuilder.m_171558_().m_171514_(110, 229).m_171488_(-3.0F, -46.0F, -33.0F, 6.0F, 10.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 10.1535F, -24.5092F, -0.48F, 0.0F, 0.0F));
      PartDefinition rightHorn = head.m_171599_("rightHorn", CubeListBuilder.m_171558_(), PartPose.m_171423_(-6.0F, -2.0F, -1.0F, 0.3927F, 0.3054F, 0.0F));
      rightHorn.m_171599_("Horn1_r1_r1", CubeListBuilder.m_171558_().m_171514_(123, 232).m_171480_().m_171488_(-13.3176F, -75.2156F, -16.4664F, 8.0F, 11.0F, 13.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(6.0F, -0.5533F, -15.4788F, -0.3491F, 0.0F, 0.0F));
      rightHorn.m_171599_("Horn2_r1_r1", CubeListBuilder.m_171558_().m_171514_(0, 227).m_171480_().m_171488_(-14.3176F, -74.1982F, -5.345F, 10.0F, 8.0F, 12.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(6.0F, -9.2795F, 26.1106F, 0.5236F, 0.0F, 0.0F));
      rightHorn.m_171599_("Horn3_r1_r1", CubeListBuilder.m_171558_().m_171514_(33, 236).m_171480_().m_171488_(-14.5912F, -75.5682F, -21.7721F, 8.0F, 8.0F, 12.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(8.2298F, 3.39F, -10.4674F, -0.0873F, 0.2182F, 0.0F));
      rightHorn.m_171599_("Horn4_r1_r1", CubeListBuilder.m_171558_().m_171514_(0, 211).m_171480_().m_171488_(-11.5873F, -68.896F, -13.4859F, 6.0F, 9.0F, 6.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(8.1316F, -33.0373F, 27.2738F, 0.7418F, 0.0F, -0.1309F));
      rightHorn.m_171599_("Horn5_r1_r1", CubeListBuilder.m_171558_().m_171514_(75, 232).m_171480_().m_171488_(-14.3176F, -68.3589F, -18.2183F, 10.0F, 11.0F, 13.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(6.0F, -18.2846F, -38.5549F, -0.9599F, 0.0F, 0.0F));
      PartDefinition leftHorn = head.m_171599_("leftHorn", CubeListBuilder.m_171558_(), PartPose.m_171423_(-28.0F, -2.0F, -1.0F, 0.3927F, -0.3054F, 0.0F));
      leftHorn.m_171599_("Horn1_r2_r1", CubeListBuilder.m_171558_().m_171514_(123, 232).m_171488_(3.7443F, -75.6612F, -26.6796F, 8.0F, 11.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(28.0F, -0.5533F, -15.4788F, -0.3491F, 0.0F, 0.0F));
      leftHorn.m_171599_("Horn2_r2_r1", CubeListBuilder.m_171558_().m_171514_(0, 227).m_171488_(2.7443F, -82.3086F, -11.5683F, 10.0F, 8.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(28.0F, -9.2795F, 26.1106F, 0.5236F, 0.0F, 0.0F));
      leftHorn.m_171599_("Horn3_r2_r1", CubeListBuilder.m_171558_().m_171514_(33, 236).m_171488_(2.2045F, -78.0495F, -38.2916F, 8.0F, 8.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(24.964F, 3.39F, -3.1074F, -0.0873F, -0.2182F, 0.0F));
      leftHorn.m_171599_("Horn4_r2_r1", CubeListBuilder.m_171558_().m_171514_(0, 211).m_171488_(3.226F, -81.2571F, -14.969F, 6.0F, 9.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(25.5775F, -28.5994F, 27.2738F, 0.7418F, 0.0F, 0.1309F));
      leftHorn.m_171599_("Horn5_r2_r1", CubeListBuilder.m_171558_().m_171514_(75, 232).m_171488_(2.7443F, -62.8664F, -26.8405F, 10.0F, 11.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(28.0F, -18.2846F, -38.5549F, -0.9599F, 0.0F, 0.0F));
      return LayerDefinition.m_171565_(meshdefinition, 256, 256);
   }

   public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.body.f_104203_ = headPitch * ((float)Math.PI / 180F);
      this.body.f_104204_ = netHeadYaw * ((float)Math.PI / 180F);
      this.tail.f_104204_ = 0.1F * (-0.2F + Mth.m_14089_(ageInTicks * 0.15F));
      this.flippersRight.f_104204_ = 0.1F * (-1.5F + Mth.m_14089_(ageInTicks * 0.35F));
      this.flippersLeft.f_104204_ = 0.1F * (-1.5F + Mth.m_14089_(ageInTicks * 0.35F));
      this.topFins.f_104203_ = 0.01F * (-0.2F + Mth.m_14089_(ageInTicks * 0.15F));
      this.topFins.f_104204_ = 0.02F * (-0.2F + Mth.m_14089_(ageInTicks * 0.15F));
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.body.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }
}
