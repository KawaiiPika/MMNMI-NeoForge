package xyz.pixelatedw.mineminenomi.models.morphs;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
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
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.api.morph.HumanoidMorphModel;

public class ShinokuniModel<T extends LivingEntity> extends HumanoidMorphModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "shinokuni"), "main");
   private final ModelPart neck;

   public ShinokuniModel(ModelPart root) {
      super(root);
      this.neck = this.f_102810_.m_171324_("neck");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = HumanoidModel.m_170681_(new CubeDeformation(0.0F), 0.0F);
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition body = partdefinition.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(46, 0).m_171488_(-7.0F, -6.0F, -1.0F, 11.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)).m_171514_(42, 27).m_171488_(-8.0F, -11.0F, -2.0F, 13.0F, 5.0F, 8.0F, new CubeDeformation(0.0F)).m_171514_(0, 16).m_171488_(-9.0F, -19.0F, -3.0F, 15.0F, 9.0F, 10.0F, new CubeDeformation(0.0F)).m_171514_(40, 16).m_171488_(-8.0F, -22.0F, -0.5F, 13.0F, 3.0F, 7.0F, new CubeDeformation(0.0F)).m_171514_(52, 51).m_171488_(-7.5F, -21.0F, -2.5F, 12.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).m_171514_(42, 40).m_171488_(-8.0F, -3.0F, -2.0F, 13.0F, 3.0F, 8.0F, new CubeDeformation(0.0F)).m_171514_(0, 35).m_171488_(-9.0F, 0.0F, -3.0F, 15.0F, 3.0F, 10.0F, new CubeDeformation(0.0F)).m_171514_(0, 0).m_171488_(-10.0F, 3.0F, -4.0F, 17.0F, 4.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(1.5F, 17.0F, -2.0F));
      body.m_171599_("neck", CubeListBuilder.m_171558_().m_171514_(36, 74).m_171488_(-2.0F, -2.5F, -2.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-1.5F, -19.6013F, -1.2589F, 0.48F, 0.0F, 0.0F));
      partdefinition.m_171599_("left_arm", CubeListBuilder.m_171558_().m_171514_(0, 48).m_171480_().m_171488_(-0.4739F, -4.8191F, -5.0F, 7.0F, 7.0F, 8.0F, new CubeDeformation(0.0F)).m_171555_(false).m_171514_(0, 90).m_171480_().m_171488_(12.5261F, -2.8191F, -2.5F, 7.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).m_171555_(false).m_171514_(0, 76).m_171480_().m_171488_(6.5261F, -3.8191F, -1.0F, 11.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).m_171555_(false).m_171514_(0, 63).m_171480_().m_171488_(6.5261F, -3.8191F, -3.0F, 10.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).m_171555_(false).m_171514_(0, 66).m_171480_().m_171488_(6.5261F, 0.1809F, -1.0F, 10.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).m_171555_(false).m_171514_(0, 86).m_171480_().m_171488_(6.5261F, 0.1809F, -3.0F, 9.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).m_171555_(false).m_171514_(0, 80).m_171480_().m_171488_(6.5261F, -3.8191F, 1.0F, 11.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).m_171555_(false).m_171514_(0, 69).m_171480_().m_171488_(6.5261F, -3.8191F, -4.0F, 9.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(7.0F, -1.0F, 1.0F, 0.0F, 0.0F, 1.2217F));
      partdefinition.m_171599_("right_arm", CubeListBuilder.m_171558_().m_171514_(0, 48).m_171488_(-6.5261F, -4.8191F, -5.0F, 7.0F, 7.0F, 8.0F, new CubeDeformation(0.0F)).m_171514_(0, 90).m_171488_(-19.5261F, -2.8191F, -2.5F, 7.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).m_171514_(0, 76).m_171488_(-17.5261F, -3.8191F, -1.0F, 11.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).m_171514_(0, 63).m_171488_(-16.5261F, -3.8191F, -3.0F, 10.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).m_171514_(0, 66).m_171488_(-16.5261F, 0.1809F, -1.0F, 10.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).m_171514_(0, 86).m_171488_(-15.5261F, 0.1809F, -3.0F, 9.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).m_171514_(0, 80).m_171488_(-17.5261F, -3.8191F, 1.0F, 11.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).m_171514_(0, 69).m_171488_(-15.5261F, -3.8191F, -4.0F, 9.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-7.0F, -1.0F, 1.0F, 0.0F, 0.0F, -1.2217F));
      return LayerDefinition.m_171565_(meshdefinition, 128, 128);
   }

   public void m_6973_(LivingEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      RendererHelper.resetModelToDefaultPivots(this);
      float f = 1.0F;
      this.f_102811_.f_104204_ = Mth.m_14089_(limbSwing * 0.6F + (float)Math.PI) * 0.5F * limbSwingAmount * 0.5F / f;
      this.f_102812_.f_104204_ = -Mth.m_14089_(limbSwing * 0.6F) * 0.5F * limbSwingAmount * 0.5F / f;
      this.f_102608_ = entity.f_20921_;
      if (this.f_102608_ > 0.0F) {
         this.f_102810_.f_104204_ = Mth.m_14031_(Mth.m_14116_(this.f_102608_) * ((float)Math.PI * 2F)) * 0.2F;
         this.f_102811_.f_104202_ = Mth.m_14031_(this.f_102810_.f_104204_) * 5.0F;
         this.f_102811_.f_104200_ = -Mth.m_14089_(this.f_102810_.f_104204_) * 8.0F;
         this.f_102812_.f_104202_ = -Mth.m_14031_(this.f_102810_.f_104204_) * 5.0F;
         this.f_102812_.f_104200_ = Mth.m_14089_(this.f_102810_.f_104204_) * 5.0F;
         ModelPart var10000 = this.f_102811_;
         var10000.f_104204_ += this.f_102810_.f_104204_;
         var10000 = this.f_102812_;
         var10000.f_104204_ += this.f_102810_.f_104204_;
         var10000 = this.f_102812_;
         var10000.f_104203_ += this.f_102810_.f_104204_;
         float f1 = 1.0F - this.f_102608_;
         f1 *= f1;
         f1 *= f1;
         f1 = 1.0F - f1;
         float f2 = Mth.m_14031_(f1 * (float)Math.PI);
         float f3 = Mth.m_14031_(this.f_102608_ * (float)Math.PI) * 0.75F;
         this.f_102811_.f_104203_ = (float)((double)this.f_102811_.f_104203_ - ((double)f2 * (double)1.5F + (double)f3));
         var10000 = this.f_102811_;
         var10000.f_104204_ += this.f_102810_.f_104204_ * 5.0F;
         var10000 = this.f_102811_;
         var10000.f_104205_ += Mth.m_14031_(this.f_102608_ * (float)Math.PI) * -0.9F;
      }

   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.f_102810_.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.f_102812_.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.f_102811_.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }

   public void renderFirstPersonArm(PoseStack stack, VertexConsumer vertex, int packedLight, int overlay, float red, float green, float blue, float alpha, HumanoidArm side, boolean isLeg) {
      float sideMod = side == HumanoidArm.RIGHT ? 1.0F : -1.0F;
      stack.m_85837_((double)0.0F, -0.2, 0.4);
      stack.m_252781_(Axis.f_252403_.m_252977_(-90.0F * sideMod));
      super.renderFirstPersonArm(stack, vertex, packedLight, overlay, red, green, blue, alpha, side, isLeg);
   }
}
