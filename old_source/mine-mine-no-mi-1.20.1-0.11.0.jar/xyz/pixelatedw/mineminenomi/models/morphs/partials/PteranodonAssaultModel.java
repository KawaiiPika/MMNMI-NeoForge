package xyz.pixelatedw.mineminenomi.models.morphs.partials;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
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
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;

public class PteranodonAssaultModel<T extends LivingEntity> extends HumanoidModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "pteranodon_assault"), "main");
   private final ModelPart leftWingTip;
   private final ModelPart leftWing2;
   private final ModelPart rightWingTip;
   private final ModelPart rightWing2;
   private final ModelPart horn;

   public PteranodonAssaultModel(ModelPart root) {
      super(root);
      this.leftWingTip = this.f_102812_.m_171324_("leftWingTip");
      this.leftWing2 = this.f_102812_.m_171324_("leftWing2");
      this.rightWingTip = this.f_102811_.m_171324_("rightWingTip");
      this.rightWing2 = this.f_102811_.m_171324_("rightWing2");
      this.horn = this.f_102808_.m_171324_("horn");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = HumanoidModel.m_170681_(CubeDeformation.f_171458_, 0.0F);
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition leftWing = partdefinition.m_171599_("left_arm", CubeListBuilder.m_171558_().m_171514_(24, 0).m_171480_().m_171488_(-0.0557F, -1.5F, -5.0F, 1.0F, 18.0F, 10.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(3.8557F, 1.6409F, -1.0F, -0.2967F, -0.0262F, -0.0873F));
      leftWing.m_171599_("leftWingTip", CubeListBuilder.m_171558_().m_171514_(4, 7).m_171480_().m_171488_(-0.8057F, -0.192F, -0.3995F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(1.0F, 15.75F, -4.5F, -1.2217F, 0.0F, 0.0F));
      leftWing.m_171599_("leftWing2", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171480_().m_171488_(-0.8057F, -20.6626F, -0.2402F, 1.0F, 21.0F, 10.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(1.25F, 16.25F, -4.5F, -0.6977F, 0.028F, 0.0334F));
      PartDefinition rightWing = partdefinition.m_171599_("right_arm", CubeListBuilder.m_171558_().m_171514_(24, 0).m_171488_(-0.9557F, -1.5F, -5.0F, 1.0F, 18.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-3.8943F, 1.6409F, -1.0F, -0.2967F, -0.0262F, 0.0873F));
      rightWing.m_171599_("rightWingTip", CubeListBuilder.m_171558_().m_171514_(0, 7).m_171488_(-0.2057F, -0.192F, -0.3995F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-1.0F, 15.75F, -4.5F, -1.2217F, 0.0F, 0.0F));
      rightWing.m_171599_("rightWing2", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(0.2943F, -20.6626F, -0.2402F, 1.0F, 21.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-1.75F, 16.25F, -4.5F, -0.6977F, -0.028F, -0.0334F));
      PartDefinition head = partdefinition.m_171599_("head", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      head.m_171599_("horn", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-1.0F, -9.2193F, -7.8714F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)).m_171514_(12, 0).m_171488_(-1.0F, -12.2193F, -7.8714F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 0.75F, 0.0F, -0.8727F, 0.0F, 0.0F));
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   public void m_6973_(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      RendererHelper.resetModelToDefaultPivots(this);
      super.m_6973_(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
      if (!entity.m_20096_()) {
         this.rightWing2.f_104203_ = (float)Math.toRadians((double)190.0F);
         this.rightWing2.f_104202_ = 4.5F;
         this.rightWing2.f_104201_ = 15.0F;
         this.rightWing2.f_104200_ = -1.5F;
         this.leftWing2.f_104203_ = (float)Math.toRadians((double)190.0F);
         this.leftWing2.f_104202_ = 4.5F;
         this.leftWing2.f_104201_ = 15.0F;
         this.leftWing2.f_104200_ = 0.8F;
         this.f_102811_.f_104202_ = 2.0F;
         this.f_102812_.f_104202_ = 2.0F;
         this.f_102811_.f_104203_ = 0.0F;
         ModelPart var10000 = this.f_102811_;
         var10000.f_104204_ += 0.9F + Mth.m_14089_((float)((double)entity.f_19797_ * 0.3 + Math.PI));
         var10000 = this.f_102811_;
         var10000.f_104205_ += 1.3F + Mth.m_14089_((float)((double)entity.f_19797_ * 0.3 + Math.PI));
         var10000 = this.rightWing2;
         var10000.f_104205_ += Mth.m_14089_((float)((double)entity.f_19797_ * 0.3 + Math.PI)) / 3.0F;
         this.f_102812_.f_104203_ = 0.0F;
         var10000 = this.f_102812_;
         var10000.f_104204_ -= 0.9F + Mth.m_14089_((float)((double)entity.f_19797_ * 0.3 + Math.PI));
         var10000 = this.f_102812_;
         var10000.f_104205_ -= 1.3F + Mth.m_14089_((float)((double)entity.f_19797_ * 0.3 + Math.PI));
         var10000 = this.leftWing2;
         var10000.f_104205_ -= Mth.m_14089_((float)((double)entity.f_19797_ * 0.3 + Math.PI)) / 3.0F;
      }

      if (entity.m_6047_()) {
         this.f_102808_.f_104201_ = 4.2F;
      }

   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.f_102812_.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.f_102811_.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.f_102808_.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }
}
