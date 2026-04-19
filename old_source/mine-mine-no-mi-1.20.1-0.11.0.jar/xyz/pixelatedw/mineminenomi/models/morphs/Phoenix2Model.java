package xyz.pixelatedw.mineminenomi.models.morphs;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xyz.pixelatedw.mineminenomi.api.morph.HumanoidMorphModel;

@OnlyIn(Dist.CLIENT)
public class Phoenix2Model<T extends LivingEntity> extends HumanoidMorphModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "phoenix_2"), "main");
   private final ModelPart beak;
   private final ModelPart redThing;

   public Phoenix2Model(ModelPart root) {
      super(root);
      this.beak = root.m_171324_("beak");
      this.redThing = root.m_171324_("red_thing");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = HumanoidMorphModel.m_170681_(CubeDeformation.f_171458_, 0.0F);
      PartDefinition partdefinition = meshdefinition.m_171576_();
      partdefinition.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-2.0F, -6.0F, -2.0F, 4.0F, 6.0F, 3.0F), PartPose.m_171419_(0.0F, 15.0F, -4.0F));
      partdefinition.m_171599_("beak", CubeListBuilder.m_171558_().m_171514_(14, 0).m_171481_(-2.0F, -4.0F, -4.0F, 4.0F, 2.0F, 2.0F), PartPose.m_171419_(0.0F, 15.0F, -4.0F));
      partdefinition.m_171599_("red_thing", CubeListBuilder.m_171558_().m_171514_(14, 4).m_171481_(-1.0F, -2.0F, -3.0F, 2.0F, 2.0F, 2.0F), PartPose.m_171419_(0.0F, 15.0F, -4.0F));
      partdefinition.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(0, 9).m_171481_(-3.0F, -4.0F, -3.0F, 6.0F, 8.0F, 6.0F), PartPose.m_171423_(0.0F, 16.0F, 0.0F, ((float)Math.PI / 2F), 0.0F, 0.0F));
      CubeListBuilder cubelistbuilder = CubeListBuilder.m_171558_().m_171514_(26, 0).m_171481_(-1.0F, 0.0F, -3.0F, 3.0F, 5.0F, 3.0F);
      partdefinition.m_171599_("right_leg", cubelistbuilder, PartPose.m_171419_(-2.0F, 19.0F, 1.0F));
      partdefinition.m_171599_("left_leg", cubelistbuilder, PartPose.m_171419_(1.0F, 19.0F, 1.0F));
      partdefinition.m_171599_("right_arm", CubeListBuilder.m_171558_().m_171514_(24, 13).m_171481_(0.0F, 0.0F, -3.0F, 1.0F, 4.0F, 6.0F), PartPose.m_171419_(-4.0F, 13.0F, 0.0F));
      partdefinition.m_171599_("left_arm", CubeListBuilder.m_171558_().m_171514_(24, 13).m_171481_(-1.0F, 0.0F, -3.0F, 1.0F, 4.0F, 6.0F), PartPose.m_171419_(4.0F, 13.0F, 0.0F));
      return LayerDefinition.m_171565_(meshdefinition, 64, 32);
   }

   protected Iterable<ModelPart> m_5607_() {
      return ImmutableList.of(this.f_102808_, this.beak, this.redThing);
   }

   protected Iterable<ModelPart> m_5608_() {
      return ImmutableList.of(this.f_102810_, this.f_102811_, this.f_102812_, this.f_102813_, this.f_102814_);
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      poseStack.m_85837_((double)0.0F, (double)0.25F, (double)0.0F);
      super.m_7695_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }

   public void m_6973_(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.f_102808_.f_104203_ = headPitch * ((float)Math.PI / 180F);
      this.f_102808_.f_104204_ = netHeadYaw * ((float)Math.PI / 180F);
      this.beak.f_104203_ = this.f_102808_.f_104203_;
      this.beak.f_104204_ = this.f_102808_.f_104204_;
      this.redThing.f_104203_ = this.f_102808_.f_104203_;
      this.redThing.f_104204_ = this.f_102808_.f_104204_;
      this.f_102813_.f_104203_ = Mth.m_14089_(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
      this.f_102814_.f_104203_ = Mth.m_14089_(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
      this.f_102811_.f_104200_ = -3.0F;
      this.f_102812_.f_104200_ = 3.0F;
      this.f_102811_.f_104205_ = 1.3F + Mth.m_14089_(ageInTicks * 0.5F) * 0.9F;
      this.f_102812_.f_104205_ = -1.3F + Mth.m_14089_(ageInTicks * 0.5F + (float)Math.PI) * 0.9F;
   }

   public void m_6002_(HumanoidArm arm, PoseStack poseStack) {
      this.f_102808_.m_104299_(poseStack);
      poseStack.m_85841_(0.7F, 0.7F, 0.7F);
      poseStack.m_252781_(Axis.f_252403_.m_252977_(90.0F));
      poseStack.m_252781_(Axis.f_252529_.m_252977_(260.0F));
      poseStack.m_85837_((double)0.25F, 0.6, 0.2);
   }

   public ModelPart m_5585_() {
      return this.f_102808_;
   }
}
