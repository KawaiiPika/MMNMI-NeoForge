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
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.SeaCowEntity;

public class SeaCowModel<T extends SeaCowEntity> extends EntityModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "sea_cow"), "main");
   public final ModelPart head;
   private final ModelPart mouth;
   private final ModelPart noseRing;
   private final ModelPart leftHorn;
   private final ModelPart leftHorn2;
   private final ModelPart leftHorn3;
   private final ModelPart rightHorn;
   private final ModelPart rightHorn2;
   private final ModelPart rightHorn3;
   private final ModelPart leftEar;
   private final ModelPart rightEar;
   public final ModelPart rightFin;
   public final ModelPart rightFin2;
   public final ModelPart leftFin;
   public final ModelPart leftFin2;
   private final ModelPart body;
   private final ModelPart body2;
   private final ModelPart body3;
   private final ModelPart body4;
   private final ModelPart body5;
   private final ModelPart body6;
   private final ModelPart tail;

   public SeaCowModel(ModelPart root) {
      this.head = root.m_171324_("head");
      this.mouth = this.head.m_171324_("mouth");
      this.noseRing = this.mouth.m_171324_("noseRing");
      this.leftHorn = this.head.m_171324_("leftHorn");
      this.leftHorn2 = this.leftHorn.m_171324_("leftHorn2");
      this.leftHorn3 = this.leftHorn2.m_171324_("leftHorn3");
      this.rightHorn = this.head.m_171324_("rightHorn");
      this.rightHorn2 = this.rightHorn.m_171324_("rightHorn2");
      this.rightHorn3 = this.rightHorn2.m_171324_("rightHorn3");
      this.leftEar = this.head.m_171324_("leftEar");
      this.rightEar = this.head.m_171324_("rightEar");
      this.rightFin = root.m_171324_("rightFin");
      this.rightFin2 = this.rightFin.m_171324_("rightFin2");
      this.leftFin = root.m_171324_("leftFin");
      this.leftFin2 = this.leftFin.m_171324_("leftFin2");
      this.body = root.m_171324_("body");
      this.body2 = this.body.m_171324_("body2");
      this.body3 = this.body2.m_171324_("body3");
      this.body4 = this.body3.m_171324_("body4");
      this.body5 = this.body4.m_171324_("body5");
      this.body6 = this.body5.m_171324_("body6");
      this.tail = this.body6.m_171324_("tail");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition head = partdefinition.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(76, 82).m_171488_(-6.5F, -7.75F, -12.0F, 13.0F, 13.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 7.75F, -18.0F));
      PartDefinition mouth = head.m_171599_("mouth", CubeListBuilder.m_171558_().m_171514_(98, 110).m_171488_(-3.5F, -2.1781F, -4.5795F, 7.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 0.25F, -11.5F, 0.0873F, 0.0F, 0.0F));
      mouth.m_171599_("noseRing", CubeListBuilder.m_171558_().m_171514_(99, 123).m_171488_(-2.5F, -0.8423F, -0.4163F, 5.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, -0.1663F, -4.299F, -0.1222F, 0.0F, 0.0F));
      PartDefinition leftHorn = head.m_171599_("leftHorn", CubeListBuilder.m_171558_().m_171514_(81, 118).m_171480_().m_171488_(1.1337F, 1.3166F, -12.316F, 3.0F, 5.0F, 5.0F, new CubeDeformation(0.4F)).m_171555_(false), PartPose.m_171423_(6.1004F, -10.7588F, 7.2694F, -0.1745F, 0.0F, 0.6981F));
      PartDefinition leftHorn2 = leftHorn.m_171599_("leftHorn2", CubeListBuilder.m_171558_().m_171514_(63, 119).m_171480_().m_171488_(-1.6504F, 3.1528F, -11.816F, 3.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(-0.3542F, -4.5847F, -0.4754F, 0.0F, 0.0F, -0.5236F));
      leftHorn2.m_171599_("leftHorn3", CubeListBuilder.m_171558_().m_171514_(51, 121).m_171480_().m_171488_(-2.9892F, 3.5255F, -10.816F, 2.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(-0.4638F, -4.1606F, 0.0033F, 0.0F, 0.0F, -0.3491F));
      PartDefinition rightHorn = head.m_171599_("rightHorn", CubeListBuilder.m_171558_().m_171514_(81, 118).m_171488_(-4.1337F, 1.3166F, -12.316F, 3.0F, 5.0F, 5.0F, new CubeDeformation(0.4F)), PartPose.m_171423_(-6.1004F, -10.7588F, 7.2694F, -0.1745F, 0.0F, -0.6981F));
      PartDefinition rightHorn2 = rightHorn.m_171599_("rightHorn2", CubeListBuilder.m_171558_().m_171514_(63, 119).m_171488_(-1.3496F, 3.1528F, -11.816F, 3.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.3542F, -4.5847F, -0.4754F, 0.0F, 0.0F, 0.5236F));
      rightHorn2.m_171599_("rightHorn3", CubeListBuilder.m_171558_().m_171514_(51, 121).m_171488_(0.9892F, 3.5255F, -10.816F, 2.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.4638F, -4.1606F, 0.0033F, 0.0F, 0.0F, 0.3491F));
      head.m_171599_("leftEar", CubeListBuilder.m_171558_().m_171514_(110, 122).m_171480_().m_171488_(0.0F, -1.0F, -2.0F, 5.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(6.5F, -6.125F, -8.3125F, -0.1129F, 0.1334F, 1.1618F));
      head.m_171599_("rightEar", CubeListBuilder.m_171558_().m_171514_(110, 122).m_171488_(-5.0F, -1.0F, -2.0F, 5.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-6.5F, -6.125F, -8.3125F, -0.1129F, -0.1334F, -1.1618F));
      PartDefinition rightFin = partdefinition.m_171599_("rightFin", CubeListBuilder.m_171558_(), PartPose.m_171423_(-7.0F, 8.5F, -4.0F, 0.0F, 0.0F, 0.1745F));
      rightFin.m_171599_("rightFin_r1", CubeListBuilder.m_171558_().m_171514_(0, 83).m_171488_(-2.0F, -8.0F, -5.0F, 4.0F, 16.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-0.9285F, 7.2187F, -0.9823F, 0.0F, 0.0F, 0.0F));
      PartDefinition rightFin2 = rightFin.m_171599_("rightFin2", CubeListBuilder.m_171558_(), PartPose.m_171423_(-0.4321F, 13.9255F, -0.9823F, 0.0F, 0.0F, 1.2217F));
      rightFin2.m_171599_("rightFin2_r1", CubeListBuilder.m_171558_().m_171514_(0, 110).m_171488_(-1.5F, -4.0F, -4.5F, 3.0F, 8.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.1636F, 3.1611F, 0.0F, 0.0F, 0.0F, 0.0F));
      PartDefinition leftFin = partdefinition.m_171599_("leftFin", CubeListBuilder.m_171558_(), PartPose.m_171423_(7.0F, 8.25F, -4.0F, 0.0F, 0.0F, -0.1745F));
      leftFin.m_171599_("leftFin_r1", CubeListBuilder.m_171558_().m_171514_(0, 83).m_171480_().m_171488_(-2.0F, -8.0F, -5.0F, 4.0F, 16.0F, 10.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(1.0411F, 7.566F, -0.9823F, 0.0F, 0.0F, 0.0F));
      PartDefinition leftFin2 = leftFin.m_171599_("leftFin2", CubeListBuilder.m_171558_(), PartPose.m_171423_(0.5375F, 14.0228F, -0.9823F, 0.0F, 0.0F, -1.1781F));
      leftFin2.m_171599_("leftFin2_r1", CubeListBuilder.m_171558_().m_171514_(0, 110).m_171480_().m_171488_(-1.5F, -4.0F, -4.5F, 3.0F, 8.0F, 9.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(-0.3364F, 3.1611F, 0.0F, 0.0F, 0.0F, 0.0F));
      PartDefinition body = partdefinition.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-5.5F, -4.268F, -7.9762F, 11.0F, 13.0F, 9.0F, new CubeDeformation(0.0F)).m_171514_(110, -9).m_171488_(0.0F, -8.268F, -7.9762F, 0.0F, 5.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 8.3125F, -10.625F));
      PartDefinition body2 = body.m_171599_("body2", CubeListBuilder.m_171558_().m_171514_(0, 24).m_171488_(-6.5F, -3.4375F, -2.3125F, 13.0F, 15.0F, 19.0F, new CubeDeformation(0.0F)).m_171514_(93, -10).m_171488_(0.0F, -7.4375F, 0.6875F, 0.0F, 5.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 1.3961F, -0.5129F));
      PartDefinition body3 = body2.m_171599_("body3", CubeListBuilder.m_171558_().m_171514_(20, 60).m_171488_(-7.5F, -2.3125F, -0.125F, 15.0F, 14.0F, 13.0F, new CubeDeformation(0.0F)).m_171514_(107, 4).m_171488_(0.0F, -7.3125F, 4.875F, 0.0F, 5.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 1.8493F, 11.443F));
      PartDefinition body4 = body3.m_171599_("body4", CubeListBuilder.m_171558_().m_171514_(42, 0).m_171488_(-7.0F, -3.3125F, -0.4375F, 14.0F, 12.0F, 8.0F, new CubeDeformation(0.0F)).m_171514_(113, 11).m_171488_(0.0F, -9.0625F, 0.5625F, 0.0F, 7.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 3.4819F, 11.5169F));
      PartDefinition body5 = body4.m_171599_("body5", CubeListBuilder.m_171558_().m_171514_(46, 22).m_171488_(-6.0F, -4.0625F, -1.1875F, 12.0F, 9.0F, 11.0F, new CubeDeformation(0.0F)).m_171514_(108, 17).m_171488_(0.0F, -11.0625F, 0.8125F, 0.0F, 7.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 3.5852F, 6.553F));
      PartDefinition body6 = body5.m_171599_("body6", CubeListBuilder.m_171558_().m_171514_(65, 44).m_171488_(-5.0F, -3.1993F, -1.0463F, 10.0F, 7.0F, 12.0F, new CubeDeformation(0.0F)).m_171514_(106, 25).m_171488_(0.0F, -9.1993F, 1.9537F, 0.0F, 7.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 0.875F, 8.8125F, 0.3054F, 0.0F, 0.0F));
      body6.m_171599_("tail", CubeListBuilder.m_171558_().m_171514_(19, 100).m_171488_(-9.5F, -2.308F, -0.5636F, 19.0F, 4.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 0.0426F, 9.893F, 0.6109F, 0.0F, 0.0F));
      return LayerDefinition.m_171565_(meshdefinition, 128, 128);
   }

   public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.head.f_104203_ = 0.0F;
      if (entity.m_20069_()) {
         float backSwingSpeed = 0.4F;
         float backSwingAmount = 0.4F;
         float finSwingSpeed = 0.4F;
         float finSwingAmount = 0.9F;
         float finSwingSpeed2 = 0.4F;
         float finSwingAmount2 = 0.2F;
         this.leftFin2.f_104205_ = 0.0F;
         this.leftFin.f_104203_ = (float)Math.toRadians((double)45.0F);
         this.rightFin2.f_104205_ = 0.0F;
         this.rightFin.f_104203_ = (float)Math.toRadians((double)45.0F);
         this.body6.f_104203_ = 0.0F;
         this.tail.f_104203_ = (float)Math.toRadians((double)5.0F);
         this.body5.f_104203_ = Mth.m_14089_(limbSwing * 0.4F + (float)Math.PI) * 0.4F * limbSwingAmount * 0.7F / 1.0F;
         this.body6.f_104203_ = Mth.m_14089_(limbSwing * 0.4F + (float)Math.PI) * 0.4F * limbSwingAmount * 0.7F / 1.0F;
         this.tail.f_104203_ = Mth.m_14089_(limbSwing * 0.4F + (float)Math.PI) * 0.4F * limbSwingAmount * 0.7F / 1.0F;
         this.rightFin.f_104205_ = (float)(Math.toRadians((double)20.0F) + (double)(Mth.m_14089_(limbSwing * 0.4F + (float)Math.PI) * 0.9F * limbSwingAmount * 0.7F / 1.0F));
         this.rightFin.f_104204_ = (float)(Math.toRadians((double)-20.0F) - (double)(Mth.m_14089_(limbSwing * 0.4F + (float)Math.PI) * 0.2F * limbSwingAmount * 0.7F / 1.0F));
         this.leftFin.f_104205_ = (float)(Math.toRadians((double)-20.0F) - (double)(Mth.m_14089_(limbSwing * 0.4F + (float)Math.PI) * 0.9F * limbSwingAmount * 0.7F / 1.0F));
         this.leftFin.f_104204_ = (float)(Math.toRadians((double)20.0F) + (double)(Mth.m_14089_(limbSwing * 0.4F + (float)Math.PI) * 0.2F * limbSwingAmount * 0.7F / 1.0F));
      } else {
         float swingSpeed = 0.4F;
         float swingAmount = 0.9F;
         this.leftFin2.f_104205_ = (float)Math.toRadians((double)-67.5F);
         this.leftFin.f_104203_ = (float)Math.toRadians((double)0.0F);
         this.rightFin2.f_104205_ = (float)Math.toRadians((double)67.5F);
         this.rightFin.f_104203_ = (float)Math.toRadians((double)0.0F);
         this.body5.f_104203_ = 0.0F;
         this.body6.f_104203_ = (float)Math.toRadians((double)17.5F);
         this.tail.f_104203_ = (float)Math.toRadians((double)35.0F);
         this.rightFin.f_104203_ = Mth.m_14089_(limbSwing * 0.4F + (float)Math.PI) * 0.9F * limbSwingAmount * 0.7F / 1.0F;
         this.rightFin.f_104204_ = 0.0F;
         this.rightFin.f_104205_ = (float)Math.toRadians((double)10.0F);
         this.leftFin.f_104203_ = Mth.m_14089_(limbSwing * 0.4F + (float)Math.PI) * 0.9F * limbSwingAmount * 0.7F / 1.0F;
         this.leftFin.f_104204_ = 0.0F;
         this.leftFin.f_104205_ = (float)Math.toRadians((double)-10.0F);
      }

      Animation.animationAngles(entity, this, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.head.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.rightFin.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.leftFin.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.body.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }
}
