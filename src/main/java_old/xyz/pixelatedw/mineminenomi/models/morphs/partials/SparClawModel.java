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
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;

public class SparClawModel<T extends LivingEntity> extends HumanoidModel<T> {
   public static final ModelLayerLocation WIDE_LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "wide_spar_claw"), "main");
   public static final ModelLayerLocation SLIM_LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "slim_spar_claw"), "main");
   private final ModelPart rightArmBlade;
   private final ModelPart leftArmBlade;
   private final ModelPart leftLegBlade;
   private final ModelPart rightLegBlade;

   public SparClawModel(ModelPart root) {
      super(root);
      this.rightArmBlade = this.f_102811_.m_171324_("rightArmBlade");
      this.leftArmBlade = this.f_102812_.m_171324_("leftArmBlade");
      this.leftLegBlade = this.f_102814_.m_171324_("leftLegBlade");
      this.rightLegBlade = this.f_102813_.m_171324_("rightLegBlade");
   }

   public static LayerDefinition createWideBodyLayer() {
      MeshDefinition meshdefinition = HumanoidModel.m_170681_(new CubeDeformation(0.0F), 0.0F);
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition rightArmBase = partdefinition.m_171599_("right_arm", CubeListBuilder.m_171558_(), PartPose.m_171419_(-5.0F, 2.0F, 0.0F));
      rightArmBase.m_171599_("rightArmBlade", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171480_().m_171488_(-1.5F, -6.0F, -1.5F, 3.0F, 12.0F, 3.0F, new CubeDeformation(-0.08F)).m_171555_(false), PartPose.m_171423_(-2.8789F, 4.0F, -0.0075F, 0.0F, 0.7854F, 0.0F));
      PartDefinition leftArmBase = partdefinition.m_171599_("left_arm", CubeListBuilder.m_171558_(), PartPose.m_171419_(5.0F, 2.0F, 0.0F));
      leftArmBase.m_171599_("leftArmBlade", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-1.5F, -6.0F, -1.5F, 3.0F, 12.0F, 3.0F, new CubeDeformation(-0.08F)), PartPose.m_171423_(2.9698F, 4.0F, 0.0228F, 0.0F, -0.7854F, 0.0F));
      addLegLayers(partdefinition);
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   public static LayerDefinition createSlimBodyLayer() {
      MeshDefinition meshdefinition = HumanoidModel.m_170681_(new CubeDeformation(0.0F), 0.0F);
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition rightArmBase = partdefinition.m_171599_("right_arm", CubeListBuilder.m_171558_(), PartPose.m_171419_(-5.0F, 2.0F, 0.0F));
      rightArmBase.m_171599_("rightArmBlade", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171480_().m_171488_(-1.5F, -6.0F, -1.5F, 3.0F, 12.0F, 3.0F, new CubeDeformation(-0.08F)).m_171555_(false), PartPose.m_171423_(-1.8789F, 4.0F, -0.0075F, 0.0F, 0.7854F, 0.0F));
      PartDefinition leftArmBase = partdefinition.m_171599_("left_arm", CubeListBuilder.m_171558_(), PartPose.m_171419_(5.0F, 2.0F, 0.0F));
      leftArmBase.m_171599_("leftArmBlade", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-1.5F, -6.0F, -1.5F, 3.0F, 12.0F, 3.0F, new CubeDeformation(-0.08F)), PartPose.m_171423_(1.9698F, 4.0F, 0.0228F, 0.0F, -0.7854F, 0.0F));
      addLegLayers(partdefinition);
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   private static void addLegLayers(PartDefinition partdefinition) {
      PartDefinition leftLegBase = partdefinition.m_171599_("left_leg", CubeListBuilder.m_171558_(), PartPose.m_171419_(1.9F, 12.0F, 0.0F));
      leftLegBase.m_171599_("leftLegBlade", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-0.9929F, -6.0F, 0.1858F, 3.0F, 12.0F, 3.0F, new CubeDeformation(-0.08F)), PartPose.m_171423_(0.9929F, 6.0F, -3.6858F, 0.0F, -0.7854F, 0.0F));
      PartDefinition rightLegBase = partdefinition.m_171599_("right_leg", CubeListBuilder.m_171558_(), PartPose.m_171419_(-1.9F, 12.0F, 0.0F));
      rightLegBase.m_171599_("rightLegBlade", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-0.9929F, -6.0F, 0.1858F, 3.0F, 12.0F, 3.0F, new CubeDeformation(-0.08F)), PartPose.m_171423_(0.7429F, 6.0F, -3.6858F, 0.0F, -0.7854F, 0.0F));
   }

   public void m_6973_(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      super.m_6973_(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
      boolean showLeg = (Boolean)EntityStatsCapability.get(entity).map((props) -> props.isBlackLeg()).orElse(false);
      if (showLeg) {
         this.f_102814_.f_104207_ = true;
         this.f_102813_.f_104207_ = true;
         this.f_102812_.f_104207_ = false;
         this.f_102811_.f_104207_ = false;
      } else {
         this.f_102812_.f_104207_ = true;
         this.f_102811_.f_104207_ = true;
         this.f_102814_.f_104207_ = false;
         this.f_102813_.f_104207_ = false;
      }

   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.f_102812_.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.f_102811_.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.f_102814_.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.f_102813_.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }
}
