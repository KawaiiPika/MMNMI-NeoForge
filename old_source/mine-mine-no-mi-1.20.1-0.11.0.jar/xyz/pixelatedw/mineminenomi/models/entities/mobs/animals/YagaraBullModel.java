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
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.YagaraBullEntity;

public class YagaraBullModel<T extends YagaraBullEntity> extends EntityModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "yagara_bull"), "main");
   private final ModelPart body;
   private final ModelPart frontLeftFin;
   private final ModelPart frontRightFin;
   private final ModelPart back;
   private final ModelPart backLeftFin;
   private final ModelPart backRightFin;
   private final ModelPart tail;
   private final ModelPart tail2;
   private final ModelPart tail3;
   private final ModelPart tail4;
   private final ModelPart tail5;
   private final ModelPart tail6;
   private final ModelPart neck;
   private final ModelPart head;
   private final ModelPart saddle;
   private final ModelPart saddleRight1;
   private final ModelPart saddleLeft1;
   private final ModelPart saddleRight2;
   private final ModelPart saddleLeft2;
   private final ModelPart saddleFront;
   private final ModelPart saddleBack;
   private final ModelPart belt1;
   private final ModelPart belt2;

   public YagaraBullModel(ModelPart root) {
      this.body = root.m_171324_("body");
      this.frontLeftFin = this.body.m_171324_("frontLeftFin");
      this.frontRightFin = this.body.m_171324_("frontRightFin");
      this.back = this.body.m_171324_("back");
      this.backLeftFin = this.back.m_171324_("backLeftFin");
      this.backRightFin = this.back.m_171324_("backRightFin");
      this.tail = root.m_171324_("tail");
      this.tail2 = this.tail.m_171324_("tail2");
      this.tail3 = this.tail.m_171324_("tail3");
      this.tail4 = this.tail.m_171324_("tail4");
      this.tail5 = this.tail.m_171324_("tail5");
      this.tail6 = this.tail5.m_171324_("tail6");
      this.neck = root.m_171324_("neck");
      this.head = this.neck.m_171324_("head");
      this.saddle = root.m_171324_("saddle");
      this.saddleRight1 = this.saddle.m_171324_("saddleRight1");
      this.saddleLeft1 = this.saddle.m_171324_("saddleLeft1");
      this.saddleRight2 = this.saddle.m_171324_("saddleRight2");
      this.saddleLeft2 = this.saddle.m_171324_("saddleLeft2");
      this.saddleFront = this.saddle.m_171324_("saddleFront");
      this.saddleBack = this.saddle.m_171324_("saddleBack");
      this.belt1 = this.saddle.m_171324_("belt1");
      this.belt2 = this.saddle.m_171324_("belt2");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition body = partdefinition.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-6.0F, -5.5F, -5.5F, 12.0F, 11.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 18.3F, -4.0F, -0.0698F, 0.0F, 0.0F));
      body.m_171599_("frontLeftFin", CubeListBuilder.m_171558_().m_171514_(99, 41).m_171488_(0.0F, -0.5F, -6.0F, 8.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(3.9F, 2.0F, -2.0F, -0.1922F, -0.6485F, 0.4436F));
      body.m_171599_("frontRightFin", CubeListBuilder.m_171558_().m_171514_(99, 41).m_171488_(-8.0F, -0.5F, -6.0F, 8.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-3.9F, 2.0F, -2.0F, -0.1922F, 0.6485F, -0.4436F));
      PartDefinition back = body.m_171599_("back", CubeListBuilder.m_171558_().m_171514_(0, 23).m_171488_(-6.0F, -5.0F, -4.5F, 12.0F, 10.0F, 9.0F, new CubeDeformation(-0.001F)), PartPose.m_171423_(0.0F, -0.65F, 9.3F, 0.1222F, 0.0F, 0.0F));
      back.m_171599_("backLeftFin", CubeListBuilder.m_171558_().m_171514_(99, 49).m_171488_(0.0F, -0.5F, -5.0F, 6.0F, 1.0F, 5.0F, new CubeDeformation(-0.001F)), PartPose.m_171423_(4.4F, 2.9F, 2.7F, -0.2423F, -0.7268F, 0.4754F));
      back.m_171599_("backRightFin", CubeListBuilder.m_171558_().m_171514_(99, 49).m_171488_(-6.0F, -0.5F, -5.0F, 6.0F, 1.0F, 5.0F, new CubeDeformation(-0.001F)), PartPose.m_171423_(-4.4F, 2.9F, 2.7F, -0.2423F, 0.7268F, -0.4754F));
      PartDefinition tail = partdefinition.m_171599_("tail", CubeListBuilder.m_171558_().m_171514_(47, 0).m_171488_(-5.0F, -4.5F, 0.0F, 10.0F, 9.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 18.0F, 9.5F, 0.0873F, 0.0F, 0.0F));
      tail.m_171599_("tail2", CubeListBuilder.m_171558_().m_171514_(47, 15).m_171488_(-4.0F, -3.5349F, -1.5006F, 8.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 0.0873F, 5.4985F, -0.0349F, 0.0F, 0.0F));
      tail.m_171599_("tail3", CubeListBuilder.m_171558_().m_171514_(47, 28).m_171488_(-2.5F, -2.6045F, -0.5055F, 5.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 0.3138F, 8.989F, -0.1047F, 0.0F, 0.0F));
      tail.m_171599_("tail4", CubeListBuilder.m_171558_().m_171514_(47, 37).m_171488_(-1.5F, -1.6392F, -0.5097F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 0.6271F, 11.469F, -0.1396F, 0.0F, 0.0F));
      PartDefinition tail5 = tail.m_171599_("tail5", CubeListBuilder.m_171558_().m_171514_(47, 44).m_171488_(-0.5F, -0.7249F, -0.5256F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 1.1733F, 14.4159F, -0.2269F, 0.0F, 0.0F));
      tail5.m_171599_("tail6", CubeListBuilder.m_171558_().m_171514_(47, 42).m_171488_(0.0F, -1.247F, -1.281F, 0.0F, 7.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, -0.4505F, -0.1678F, 0.4712F, 0.0F, 0.0F));
      PartDefinition neck = partdefinition.m_171599_("neck", CubeListBuilder.m_171558_().m_171514_(78, 14).m_171488_(-5.5F, -4.5F, 0.0F, 11.0F, 9.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 16.0F, -13.8F, -0.3491F, 0.0F, 0.0F));
      neck.m_171599_("neck2_r1", CubeListBuilder.m_171558_().m_171514_(78, 0).m_171488_(-4.0F, -14.0F, -17.3F, 8.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 16.5467F, 2.6819F, -0.6981F, 0.0F, 0.0F));
      neck.m_171599_("neck3_r1", CubeListBuilder.m_171558_().m_171514_(78, 30).m_171488_(-3.5F, -31.1F, -6.5F, 7.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 10.2663F, -28.8447F, -1.1868F, 0.0F, 0.0F));
      PartDefinition head = neck.m_171599_("head", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      head.m_171599_("lefteye_r1", CubeListBuilder.m_171558_().m_171514_(40, 57).m_171488_(1.5F, -30.0F, -3.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).m_171514_(40, 57).m_171488_(-3.5F, -30.0F, -3.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 15.0144F, 3.3925F, 0.3491F, 0.0F, 0.0F));
      head.m_171599_("mane_r1", CubeListBuilder.m_171558_().m_171514_(67, 29).m_171488_(0.0F, -34.8F, -3.0F, 0.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 17.027F, 4.2006F, 0.2269F, 0.0F, 0.0F));
      head.m_171599_("rightgill_r1", CubeListBuilder.m_171558_().m_171514_(0, 61).m_171488_(-5.2F, -25.0F, -2.5F, 3.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(18.1894F, 5.9943F, -3.424F, 0.1417F, 0.64F, -0.7848F));
      head.m_171599_("leftgill_r1", CubeListBuilder.m_171558_().m_171514_(0, 61).m_171488_(2.2F, -25.5F, -2.0F, 3.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-18.4098F, 5.9091F, -3.5225F, 0.1417F, -0.64F, 0.7848F));
      head.m_171599_("head3_r1", CubeListBuilder.m_171558_().m_171514_(19, 43).m_171488_(-2.5F, -29.0F, -6.5F, 5.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).m_171514_(19, 52).m_171488_(-3.0F, -29.0F, -10.5F, 6.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 11.7941F, 8.8052F, 0.576F, 0.0F, 0.0F));
      head.m_171599_("head1_r1", CubeListBuilder.m_171558_().m_171514_(0, 43).m_171488_(-3.0F, -30.3F, -0.5F, 6.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 14.2732F, 4.8985F, 0.4014F, 0.0F, 0.0F));
      head.m_171599_("head2_r1", CubeListBuilder.m_171558_().m_171514_(0, 52).m_171488_(-3.0F, -29.5F, -3.0F, 6.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 13.9253F, 5.6797F, 0.4363F, 0.0F, 0.0F));
      head.m_171599_("neck4_r1", CubeListBuilder.m_171558_().m_171514_(78, 41).m_171488_(-3.0F, -31.9F, -3.5F, 6.0F, 5.0F, 4.0F, new CubeDeformation(-0.01F)), PartPose.m_171423_(0.0F, -1.1258F, -33.5366F, -1.3963F, 0.0F, 0.0F));
      PartDefinition saddle = partdefinition.m_171599_("saddle", CubeListBuilder.m_171558_().m_171514_(56, 64).m_171488_(-5.5F, -4.0615F, -10.0695F, 11.0F, 1.0F, 21.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 16.2939F, 1.0429F));
      saddle.m_171599_("saddleRight1", CubeListBuilder.m_171558_().m_171514_(100, 78).m_171488_(-5.5F, -5.0F, -0.5F, 11.0F, 5.0F, 1.0F, new CubeDeformation(-0.01F)), PartPose.m_171423_(-5.4446F, -3.8143F, 5.389F, -0.0873F, -1.4835F, 0.0F));
      saddle.m_171599_("saddleLeft1", CubeListBuilder.m_171558_().m_171514_(100, 78).m_171488_(-5.5F, -5.0F, -0.5F, 11.0F, 5.0F, 1.0F, new CubeDeformation(-0.01F)), PartPose.m_171423_(5.4446F, -3.8143F, 5.389F, -0.0873F, 1.4835F, 0.0F));
      saddle.m_171599_("saddleRight2", CubeListBuilder.m_171558_().m_171514_(100, 78).m_171488_(-5.5F, -5.0F, -0.4F, 11.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-5.5446F, -3.8143F, -5.3248F, 0.0873F, 1.4835F, 0.0F));
      saddle.m_171599_("saddleLeft2", CubeListBuilder.m_171558_().m_171514_(100, 78).m_171488_(-5.5F, -5.0F, -0.4F, 11.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(5.5446F, -3.8143F, -5.3248F, 0.0873F, -1.4835F, 0.0F));
      saddle.m_171599_("saddleFront", CubeListBuilder.m_171558_().m_171514_(100, 78).m_171488_(-5.5F, -5.0F, -1.0F, 11.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0554F, -3.8143F, -9.6248F, 0.0873F, 0.0F, 0.0F));
      saddle.m_171599_("saddleBack", CubeListBuilder.m_171558_().m_171514_(100, 78).m_171488_(-5.5F, -5.0F, -0.5F, 11.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0554F, -3.8143F, 10.389F, -0.0873F, 0.0F, 0.0F));
      saddle.m_171599_("belt1", CubeListBuilder.m_171558_().m_171514_(100, 64).m_171488_(-6.5F, -3.4535F, 5.3522F, 13.0F, 11.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 0.0F, 0.0F, 0.0436F, 0.0F, 0.0F));
      saddle.m_171599_("belt2", CubeListBuilder.m_171558_().m_171514_(100, 64).m_171488_(-6.5F, -6.0F, -0.5F, 13.0F, 12.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 2.0056F, -6.1828F, -0.0873F, 0.0F, 0.0F));
      return LayerDefinition.m_171565_(meshdefinition, 128, 128);
   }

   public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.tail.f_104204_ = 0.1F * (-0.2F + Mth.m_14089_(ageInTicks * 0.15F));
      this.tail2.f_104204_ = 0.2F * (-0.2F + Mth.m_14089_(ageInTicks * 0.15F));
      this.tail3.f_104204_ = 0.1F * (-0.2F + Mth.m_14089_(ageInTicks * 0.15F));
      this.tail4.f_104204_ = 0.2F * (-0.2F + Mth.m_14089_(ageInTicks * 0.15F));
      this.tail5.f_104204_ = 0.1F * (-0.2F + Mth.m_14089_(ageInTicks * 0.15F));
      this.frontLeftFin.f_104204_ = 0.2F * -Mth.m_14089_(ageInTicks * 0.15F);
      this.frontLeftFin.f_104203_ = 0.2F * -Mth.m_14089_(ageInTicks * 0.15F);
      this.backLeftFin.f_104204_ = 0.2F * Mth.m_14089_(ageInTicks * 0.15F);
      this.backLeftFin.f_104203_ = 0.2F * Mth.m_14089_(ageInTicks * 0.15F);
      this.frontRightFin.f_104204_ = 0.2F * -Mth.m_14089_(ageInTicks * 0.15F);
      this.frontRightFin.f_104203_ = 0.2F * -Mth.m_14089_(ageInTicks * 0.15F);
      this.backRightFin.f_104204_ = 0.2F * Mth.m_14089_(ageInTicks * 0.15F);
      this.backRightFin.f_104203_ = 0.2F * Mth.m_14089_(ageInTicks * 0.15F);
      if (ageInTicks % 300.0F > 0.0F && ageInTicks % 300.0F < 50.0F) {
         this.head.f_104204_ = 0.4F * Mth.m_14089_(ageInTicks * 0.25F);
      } else {
         this.head.f_104204_ = (float)Math.toDegrees((double)0.0F);
      }

   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.body.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.tail.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.neck.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.saddle.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.saddle.f_104207_ = false;
   }

   public void renderSaddle() {
      this.saddle.f_104207_ = true;
   }
}
