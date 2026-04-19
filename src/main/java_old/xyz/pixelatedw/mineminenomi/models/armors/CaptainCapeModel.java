package xyz.pixelatedw.mineminenomi.models.armors;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.HumanoidModel;
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
import net.minecraft.world.entity.LivingEntity;

public class CaptainCapeModel<T extends LivingEntity> extends HumanoidArmorModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "captain_cape"), "main");
   private final ModelPart cape;
   private final ModelPart back;
   private final ModelPart leftCoat;
   private final ModelPart capefrontleft;
   private final ModelPart rightShoulderPad;
   private final ModelPart caperightarm;
   private final ModelPart caperightsholderpad1;
   private final ModelPart leftShoulderPad;
   private final ModelPart capeleftarm;
   private final ModelPart capeleftsholderpad1;
   private final ModelPart rightCoat;
   private final ModelPart capefrontright;
   private final ModelPart rightSholder;
   private final ModelPart leftSholder;
   private final ModelPart rightCollar1;
   private final ModelPart leftCollar1;
   private final ModelPart rightCollar2;
   private final ModelPart leftCollar2;

   public CaptainCapeModel(ModelPart root) {
      super(root);
      this.cape = root.m_171324_("cape");
      this.back = this.cape.m_171324_("back");
      this.leftCoat = this.back.m_171324_("leftCoat");
      this.capefrontleft = this.leftCoat.m_171324_("capefrontleft");
      this.rightShoulderPad = this.back.m_171324_("rightShoulderPad");
      this.caperightarm = this.rightShoulderPad.m_171324_("caperightarm");
      this.caperightsholderpad1 = this.rightShoulderPad.m_171324_("caperightsholderpad1");
      this.leftShoulderPad = this.back.m_171324_("leftShoulderPad");
      this.capeleftarm = this.leftShoulderPad.m_171324_("capeleftarm");
      this.capeleftsholderpad1 = this.leftShoulderPad.m_171324_("capeleftsholderpad1");
      this.rightCoat = this.back.m_171324_("rightCoat");
      this.capefrontright = this.rightCoat.m_171324_("capefrontright");
      this.rightSholder = this.cape.m_171324_("rightSholder");
      this.leftSholder = this.cape.m_171324_("leftSholder");
      this.rightCollar1 = this.cape.m_171324_("rightCollar1");
      this.leftCollar1 = this.cape.m_171324_("leftCollar1");
      this.rightCollar2 = this.cape.m_171324_("rightCollar2");
      this.leftCollar2 = this.cape.m_171324_("leftCollar2");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = HumanoidModel.m_170681_(new CubeDeformation(0.0F), 0.0F);
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition cape = partdefinition.m_171599_("cape", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 0.5F, 0.0F));
      PartDefinition back = cape.m_171599_("back", CubeListBuilder.m_171558_().m_171514_(5, 75).m_171488_(-8.0F, 0.0F, 0.0F, 17.0F, 22.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(-0.5F, -0.5F, 2.5F));
      PartDefinition leftCoat = back.m_171599_("leftCoat", CubeListBuilder.m_171558_().m_171514_(40, 70).m_171488_(0.0F, 0.0F, -5.0F, 0.0F, 22.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(9.0F, 0.0F, 0.0F));
      leftCoat.m_171599_("capefrontleft", CubeListBuilder.m_171558_().m_171514_(28, 98).m_171488_(-3.0F, 0.0F, 0.0F, 3.0F, 22.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 0.0F, -5.0F));
      PartDefinition rightShoulderPad = back.m_171599_("rightShoulderPad", CubeListBuilder.m_171558_().m_171514_(5, 106).m_171488_(-4.0F, 0.0F, -5.5F, 5.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(-6.4F, 0.0F, 0.0F));
      rightShoulderPad.m_171599_("caperightarm", CubeListBuilder.m_171558_().m_171514_(35, 98).m_171488_(-4.1F, 1.0F, -2.0F, 2.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.5F, 0.0F, -2.5F));
      rightShoulderPad.m_171599_("caperightsholderpad1", CubeListBuilder.m_171558_().m_171514_(5, 98).m_171488_(-4.5F, -0.8F, -3.0F, 5.0F, 1.0F, 6.0F, new CubeDeformation(-0.01F)), PartPose.m_171423_(0.5F, 0.0F, -2.5F, 0.0F, 0.0F, -0.1745F));
      PartDefinition leftShoulderPad = back.m_171599_("leftShoulderPad", CubeListBuilder.m_171558_().m_171514_(5, 106).m_171480_().m_171488_(-1.0F, 0.0F, -5.5F, 5.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171419_(7.4F, 0.0F, 0.0F));
      leftShoulderPad.m_171599_("capeleftarm", CubeListBuilder.m_171558_().m_171514_(35, 98).m_171480_().m_171488_(2.1F, 1.0F, -2.0F, 2.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171419_(-0.5F, 0.0F, -2.5F));
      leftShoulderPad.m_171599_("capeleftsholderpad1", CubeListBuilder.m_171558_().m_171514_(5, 98).m_171480_().m_171488_(-0.5F, -0.8F, -3.0F, 5.0F, 1.0F, 6.0F, new CubeDeformation(-0.01F)).m_171555_(false), PartPose.m_171423_(-0.5F, 0.0F, -2.5F, 0.0F, 0.0F, 0.1745F));
      PartDefinition rightCoat = back.m_171599_("rightCoat", CubeListBuilder.m_171558_().m_171514_(40, 70).m_171488_(0.0F, 0.0F, -5.0F, 0.0F, 22.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(-8.0F, 0.0F, 0.0F));
      rightCoat.m_171599_("capefrontright", CubeListBuilder.m_171558_().m_171514_(28, 98).m_171488_(0.0F, 0.0F, 0.0F, 3.0F, 22.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 0.0F, -5.0F));
      cape.m_171599_("rightSholder", CubeListBuilder.m_171558_().m_171514_(51, 75).m_171488_(-6.0F, 0.0F, 0.0F, 6.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(-3.5F, -0.51F, -2.5F));
      cape.m_171599_("leftSholder", CubeListBuilder.m_171558_().m_171514_(51, 75).m_171488_(0.0F, 0.0F, 0.0F, 6.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(3.5F, -0.51F, -2.5F));
      cape.m_171599_("rightCollar1", CubeListBuilder.m_171558_().m_171514_(51, 81).m_171488_(0.0F, 0.0F, 0.0F, 0.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-5.2F, -3.5F, -2.3F, -0.0169F, 0.1913F, -0.0888F));
      cape.m_171599_("leftCollar1", CubeListBuilder.m_171558_().m_171514_(51, 81).m_171480_().m_171488_(0.0F, 0.0F, 0.0F, 0.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(5.2F, -3.5F, -2.3F, -0.0169F, -0.1913F, 0.0888F));
      cape.m_171599_("rightCollar2", CubeListBuilder.m_171558_().m_171514_(51, 90).m_171488_(0.0F, 0.0F, 0.0F, 0.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-5.2F, -3.5F, -2.3F, 0.0202F, 0.1909F, 0.1066F));
      cape.m_171599_("leftCollar2", CubeListBuilder.m_171558_().m_171514_(51, 90).m_171480_().m_171488_(0.0F, 0.0F, 0.0F, 0.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(5.2F, -3.5F, -2.3F, 0.0202F, -0.1909F, -0.1066F));
      return LayerDefinition.m_171565_(meshdefinition, 128, 128);
   }

   public void m_6973_(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      double dist = entity.m_20275_(entity.f_19854_, entity.f_19855_, entity.f_19856_);
      if (dist > (double)0.0F && dist <= 0.02) {
         dist += 0.02;
      }

      double angle = Mth.m_14008_(dist * (double)1000.0F - (double)1.0F, (double)0.0F, (double)70.0F);
      boolean isMoving = dist > 0.02;
      if (isMoving) {
         angle += (double)(Mth.m_14031_((float)Mth.m_14139_(angle, (double)entity.f_19867_, (double)entity.f_19787_)) * 6.0F);
      }

      this.back.f_104203_ = (float)Math.toRadians(angle);
      this.rightShoulderPad.f_104203_ = (float)Math.toRadians(-angle);
      this.leftShoulderPad.f_104203_ = (float)Math.toRadians(-angle);
      this.caperightarm.f_104203_ = (float)Math.toRadians(angle - (double)(!isMoving ? 0 : 20));
      this.capeleftarm.f_104203_ = (float)Math.toRadians(angle - (double)(!isMoving ? 0 : 20));
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.cape.m_104315_(this.f_102810_);
      this.cape.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }
}
