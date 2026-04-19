package xyz.pixelatedw.mineminenomi.models.morphs;

import com.google.common.collect.ImmutableSet;
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

public class CandleChampionModel<T extends LivingEntity> extends HumanoidMorphModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "candle_champion"), "main");
   private final ModelPart rightBoot;
   private final ModelPart leftBoot;
   private final ModelPart leftGlove;
   private final ModelPart rightGlove;
   private final ModelPart upperBody;
   private final ModelPart spikes;

   public CandleChampionModel(ModelPart root) {
      super(root);
      this.rightBoot = this.f_102813_.m_171324_("RightBoot");
      this.leftBoot = this.f_102814_.m_171324_("LeftBoot");
      this.leftGlove = this.f_102812_.m_171324_("LeftGlove");
      this.rightGlove = this.f_102811_.m_171324_("RightGlove");
      this.upperBody = this.f_102810_.m_171324_("UpperBody");
      this.spikes = this.upperBody.m_171324_("Spikes");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = HumanoidModel.m_170681_(new CubeDeformation(0.0F), 0.0F);
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition RightLeg = partdefinition.m_171599_("right_leg", CubeListBuilder.m_171558_().m_171514_(84, 44).m_171488_(1.3139F, -2.0468F, -0.5179F, 1.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(2.5449F, 10.9997F, 4.0179F));
      RightLeg.m_171599_("RightBoot", CubeListBuilder.m_171558_().m_171514_(22, 83).m_171488_(-1.45F, -0.1625F, -1.4625F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).m_171514_(0, 65).m_171488_(-2.95F, 1.8375F, -2.9625F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)).m_171514_(52, 67).m_171488_(-2.95F, 1.8375F, -10.9625F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)).m_171514_(24, 72).m_171488_(-2.45F, 2.775F, -7.9625F, 5.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)).m_171514_(56, 79).m_171488_(-3.2F, 2.8375F, -1.9625F, 1.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(1.6551F, 5.1628F, -0.0554F));
      PartDefinition LeftLeg = partdefinition.m_171599_("left_leg", CubeListBuilder.m_171558_().m_171514_(70, 87).m_171488_(-2.3139F, -2.0468F, -0.5179F, 1.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(-2.5449F, 10.9997F, 4.0179F));
      LeftLeg.m_171599_("LeftBoot", CubeListBuilder.m_171558_().m_171514_(34, 83).m_171488_(-1.55F, -0.1625F, -1.4625F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).m_171514_(68, 20).m_171488_(-3.05F, 1.8375F, -2.9625F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)).m_171514_(68, 32).m_171488_(-3.05F, 1.8375F, -10.9625F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)).m_171514_(0, 77).m_171488_(-2.55F, 2.775F, -7.9625F, 5.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)).m_171514_(66, 79).m_171488_(2.2F, 2.8375F, -1.9625F, 1.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(-1.6551F, 5.1628F, -0.0554F));
      PartDefinition LeftArm = partdefinition.m_171599_("left_arm", CubeListBuilder.m_171558_().m_171514_(52, 60).m_171488_(-0.525F, -0.2F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).m_171514_(24, 65).m_171488_(-0.525F, 3.8F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(11.2F, -1.525F, 4.0F, 0.0F, 0.0F, -0.5236F));
      LeftArm.m_171599_("LeftGlove", CubeListBuilder.m_171558_().m_171514_(76, 78).m_171488_(-3.5F, 3.8333F, -2.0F, 1.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).m_171514_(82, 55).m_171488_(-1.5F, 0.8333F, -1.5F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).m_171514_(58, 55).m_171488_(-3.0F, 2.8333F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(-0.025F, 5.9667F, 0.0F));
      PartDefinition RightArm = partdefinition.m_171599_("right_arm", CubeListBuilder.m_171558_().m_171514_(46, 72).m_171488_(-0.475F, -0.2F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).m_171514_(8, 88).m_171488_(-0.475F, 3.8F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-11.2F, -1.525F, 4.0F, 0.0F, 0.0F, 0.5236F));
      RightArm.m_171599_("RightGlove", CubeListBuilder.m_171558_().m_171514_(82, 60).m_171488_(-1.5F, 0.8333F, -1.5F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).m_171514_(28, 60).m_171488_(-3.0F, 2.8333F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)).m_171514_(46, 79).m_171488_(2.5F, 3.8333F, -2.0F, 1.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.025F, 5.9667F, 0.0F));
      PartDefinition Body = partdefinition.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(58, 44).m_171488_(-2.5F, -0.5F, -4.0F, 5.0F, 3.0F, 8.0F, new CubeDeformation(0.0F)).m_171514_(58, 9).m_171488_(-4.0F, -2.0F, -3.5F, 8.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)).m_171514_(58, 0).m_171488_(-4.5F, -2.1F, -4.0F, 9.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)).m_171514_(76, 67).m_171488_(-2.5F, -7.1F, -3.0F, 5.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)).m_171514_(58, 20).m_171488_(-2.0F, -3.7F, -4.01F, 4.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 11.0F, 4.0F));
      PartDefinition UpperBody = Body.m_171599_("UpperBody", CubeListBuilder.m_171558_().m_171514_(28, 44).m_171488_(-3.0F, -6.84F, -4.45F, 6.0F, 7.0F, 9.0F, new CubeDeformation(0.0F)).m_171514_(0, 0).m_171488_(-8.5F, -11.84F, -5.95F, 17.0F, 11.0F, 12.0F, new CubeDeformation(0.0F)).m_171514_(0, 23).m_171488_(-4.5F, -12.84F, -6.95F, 9.0F, 10.0F, 11.0F, new CubeDeformation(0.0F)).m_171514_(40, 23).m_171488_(-11.5F, -11.24F, -5.45F, 3.0F, 10.0F, 11.0F, new CubeDeformation(0.0F)).m_171514_(0, 44).m_171488_(8.5F, -11.24F, -5.45F, 3.0F, 10.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, -6.26F, -0.05F));
      UpperBody.m_171599_("Spikes", CubeListBuilder.m_171558_().m_171514_(84, 86).m_171488_(-11.0F, -1.0F, -7.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).m_171514_(86, 82).m_171488_(-11.0F, -1.0F, 5.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).m_171514_(11, 87).m_171488_(-10.5F, -0.5F, 7.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).m_171514_(11, 87).m_171488_(-10.5F, -0.5F, -9.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).m_171514_(86, 78).m_171488_(-11.0F, 5.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).m_171514_(88, 9).m_171488_(-10.5F, 7.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).m_171514_(76, 86).m_171488_(-11.0F, -7.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).m_171514_(84, 52).m_171488_(-10.5F, -9.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).m_171514_(0, 88).m_171488_(9.0F, -1.0F, -7.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).m_171514_(23, 87).m_171488_(9.5F, -0.5F, -9.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).m_171514_(62, 87).m_171488_(9.0F, -1.0F, 5.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).m_171514_(19, 87).m_171488_(9.5F, -0.5F, 7.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).m_171514_(54, 87).m_171488_(9.0F, 5.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).m_171514_(16, 88).m_171488_(9.5F, 7.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).m_171514_(46, 87).m_171488_(9.0F, -7.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).m_171514_(88, 15).m_171488_(9.5F, -9.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, -6.24F, 0.05F));
      return LayerDefinition.m_171565_(meshdefinition, 128, 128);
   }

   public void m_6973_(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      RendererHelper.resetModelToDefaultPivots(this);
      float f = 1.0F;
      this.f_102811_.f_104203_ = Mth.m_14089_(limbSwing * 0.6F + (float)Math.PI) * 0.8F * limbSwingAmount * 0.5F / f;
      this.f_102812_.f_104203_ = Mth.m_14089_(limbSwing * 0.6F) * 0.8F * limbSwingAmount * 0.5F / f;
      this.f_102813_.f_104203_ = Mth.m_14089_(limbSwing * 0.6F) * 0.7F * limbSwingAmount / f;
      this.f_102814_.f_104203_ = Mth.m_14089_(limbSwing * 0.6F + (float)Math.PI) * 0.7F * limbSwingAmount / f;
      this.m_7884_(entity, ageInTicks);
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.f_102813_.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.f_102814_.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.f_102812_.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.f_102811_.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.f_102810_.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }

   protected Iterable<ModelPart> m_5608_() {
      return ImmutableSet.of(this.f_102810_, this.upperBody, this.spikes, this.f_102812_, this.leftGlove, this.f_102811_, new ModelPart[]{this.rightGlove, this.f_102814_, this.f_102813_});
   }

   public void m_6002_(HumanoidArm side, PoseStack poseStack) {
      super.m_6002_(side, poseStack);
      float sideMod = side == HumanoidArm.RIGHT ? 1.0F : -1.0F;
      poseStack.m_85837_(0.05 * (double)sideMod, 0.35, (double)0.0F);
   }

   public void renderFirstPersonArm(PoseStack stack, VertexConsumer vertex, int packedLight, int overlay, float red, float green, float blue, float alpha, HumanoidArm side, boolean isLeg) {
      float sideMod = side == HumanoidArm.RIGHT ? 1.0F : -1.0F;
      stack.m_85837_(0.4 * (double)sideMod, (double)0.0F, -0.1);
      stack.m_252781_(Axis.f_252403_.m_252977_(-20.0F));
      super.renderFirstPersonArm(stack, vertex, packedLight, overlay, red, green, blue, alpha, side, isLeg);
   }
}
