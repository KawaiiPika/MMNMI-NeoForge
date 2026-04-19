package xyz.pixelatedw.mineminenomi.models.morphs.partials;

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
import net.minecraft.world.entity.LivingEntity;

public class HanaCalendulaModel<T extends LivingEntity> extends HumanoidModel<T> {
   public static final ModelLayerLocation WIDE_LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "hana_wide_calendula"), "main");
   public static final ModelLayerLocation SLIM_LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "hana_slim_calendula"), "main");
   private final ModelPart calendula;
   private final ModelPart arms;
   private final ModelPart calendula1;
   private final ModelPart calendula2;
   private final ModelPart calendula3;
   private final ModelPart calendula4;
   private final ModelPart calendula5;
   private final ModelPart calendula6;
   private final ModelPart calendula7;
   private final ModelPart calendula8;
   private final ModelPart overlay;
   private final ModelPart calendula9;
   private final ModelPart calendula10;
   private final ModelPart calendula11;
   private final ModelPart calendula12;
   private final ModelPart calendula13;
   private final ModelPart calendula14;
   private final ModelPart calendula15;
   private final ModelPart calendula16;

   public HanaCalendulaModel(ModelPart root) {
      super(root);
      this.calendula = root.m_171324_("calendula");
      this.arms = this.calendula.m_171324_("arms");
      this.calendula1 = this.arms.m_171324_("calendula1");
      this.calendula2 = this.arms.m_171324_("calendula2");
      this.calendula3 = this.arms.m_171324_("calendula3");
      this.calendula4 = this.arms.m_171324_("calendula4");
      this.calendula5 = this.arms.m_171324_("calendula5");
      this.calendula6 = this.arms.m_171324_("calendula6");
      this.calendula7 = this.arms.m_171324_("calendula7");
      this.calendula8 = this.arms.m_171324_("calendula8");
      this.overlay = this.calendula.m_171324_("overlay");
      this.calendula9 = this.overlay.m_171324_("calendula9");
      this.calendula10 = this.overlay.m_171324_("calendula10");
      this.calendula11 = this.overlay.m_171324_("calendula11");
      this.calendula12 = this.overlay.m_171324_("calendula12");
      this.calendula13 = this.overlay.m_171324_("calendula13");
      this.calendula14 = this.overlay.m_171324_("calendula14");
      this.calendula15 = this.overlay.m_171324_("calendula15");
      this.calendula16 = this.overlay.m_171324_("calendula16");
   }

   public static LayerDefinition createSlimBodyLayer() {
      MeshDefinition meshdefinition = HumanoidModel.m_170681_(new CubeDeformation(0.0F), 0.0F);
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition calendula = partdefinition.m_171599_("calendula", CubeListBuilder.m_171558_(), PartPose.m_171419_(-9.0F, 7.0F, 0.0F));
      PartDefinition arms = calendula.m_171599_("arms", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      arms.m_171599_("calendula1", CubeListBuilder.m_171558_().m_171514_(40, 16).m_171488_(-2.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      arms.m_171599_("calendula2", CubeListBuilder.m_171558_().m_171514_(40, 16).m_171488_(-2.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.01F)), PartPose.m_171423_(0.0F, 0.0F, 0.0F, -0.7854F, 0.0F, 0.0F));
      arms.m_171599_("calendula3", CubeListBuilder.m_171558_().m_171514_(40, 16).m_171488_(-2.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.011F)), PartPose.m_171423_(0.0F, 0.0F, 0.0F, -1.5708F, 0.0F, 0.0F));
      arms.m_171599_("calendula4", CubeListBuilder.m_171558_().m_171514_(40, 16).m_171488_(-2.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.012F)), PartPose.m_171423_(0.0F, 0.0F, 0.0F, -2.3562F, 0.0F, 0.0F));
      arms.m_171599_("calendula5", CubeListBuilder.m_171558_().m_171514_(40, 16).m_171488_(-2.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.013F)), PartPose.m_171423_(0.0F, 0.0F, 0.0F, 3.1416F, 0.0F, 0.0F));
      arms.m_171599_("calendula6", CubeListBuilder.m_171558_().m_171514_(40, 16).m_171488_(-2.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.014F)), PartPose.m_171423_(0.0F, 0.0F, 0.0F, 2.3562F, 0.0F, 0.0F));
      arms.m_171599_("calendula7", CubeListBuilder.m_171558_().m_171514_(40, 16).m_171488_(-2.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.015F)), PartPose.m_171423_(0.0F, 0.0F, 0.0F, 1.5708F, 0.0F, 0.0F));
      arms.m_171599_("calendula8", CubeListBuilder.m_171558_().m_171514_(40, 16).m_171488_(-2.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.016F)), PartPose.m_171423_(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
      PartDefinition overlay = calendula.m_171599_("overlay", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      overlay.m_171599_("calendula9", CubeListBuilder.m_171558_().m_171514_(40, 32).m_171488_(-2.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      overlay.m_171599_("calendula10", CubeListBuilder.m_171558_().m_171514_(40, 32).m_171488_(-2.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.m_171423_(0.0F, 0.0F, 0.0F, -0.7854F, 0.0F, 0.0F));
      overlay.m_171599_("calendula11", CubeListBuilder.m_171558_().m_171514_(40, 32).m_171488_(-2.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.m_171423_(0.0F, 0.0F, 0.0F, -1.5708F, 0.0F, 0.0F));
      overlay.m_171599_("calendula12", CubeListBuilder.m_171558_().m_171514_(40, 32).m_171488_(-2.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.m_171423_(0.0F, 0.0F, 0.0F, -2.3562F, 0.0F, 0.0F));
      overlay.m_171599_("calendula13", CubeListBuilder.m_171558_().m_171514_(40, 32).m_171488_(-2.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.m_171423_(0.0F, 0.0F, 0.0F, 3.1416F, 0.0F, 0.0F));
      overlay.m_171599_("calendula14", CubeListBuilder.m_171558_().m_171514_(40, 32).m_171488_(-2.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.m_171423_(0.0F, 0.0F, 0.0F, 2.3562F, 0.0F, 0.0F));
      overlay.m_171599_("calendula15", CubeListBuilder.m_171558_().m_171514_(40, 32).m_171488_(-2.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.m_171423_(0.0F, 0.0F, 0.0F, 1.5708F, 0.0F, 0.0F));
      overlay.m_171599_("calendula16", CubeListBuilder.m_171558_().m_171514_(40, 32).m_171488_(-2.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.m_171423_(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   public static LayerDefinition createWideBodyLayer() {
      MeshDefinition meshdefinition = HumanoidModel.m_170681_(new CubeDeformation(0.0F), 0.0F);
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition calendula = partdefinition.m_171599_("calendula", CubeListBuilder.m_171558_(), PartPose.m_171419_(-9.0F, 7.0F, 0.0F));
      PartDefinition arms = calendula.m_171599_("arms", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      arms.m_171599_("calendula1", CubeListBuilder.m_171558_().m_171514_(40, 16).m_171488_(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      arms.m_171599_("calendula2", CubeListBuilder.m_171558_().m_171514_(40, 16).m_171488_(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.01F)), PartPose.m_171423_(0.0F, 0.0F, 0.0F, -0.7854F, 0.0F, 0.0F));
      arms.m_171599_("calendula3", CubeListBuilder.m_171558_().m_171514_(40, 16).m_171488_(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.011F)), PartPose.m_171423_(0.0F, 0.0F, 0.0F, -1.5708F, 0.0F, 0.0F));
      arms.m_171599_("calendula4", CubeListBuilder.m_171558_().m_171514_(40, 16).m_171488_(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.012F)), PartPose.m_171423_(0.0F, 0.0F, 0.0F, -2.3562F, 0.0F, 0.0F));
      arms.m_171599_("calendula5", CubeListBuilder.m_171558_().m_171514_(40, 16).m_171488_(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.013F)), PartPose.m_171423_(0.0F, 0.0F, 0.0F, 3.1416F, 0.0F, 0.0F));
      arms.m_171599_("calendula6", CubeListBuilder.m_171558_().m_171514_(40, 16).m_171488_(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.014F)), PartPose.m_171423_(0.0F, 0.0F, 0.0F, 2.3562F, 0.0F, 0.0F));
      arms.m_171599_("calendula7", CubeListBuilder.m_171558_().m_171514_(40, 16).m_171488_(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.015F)), PartPose.m_171423_(0.0F, 0.0F, 0.0F, 1.5708F, 0.0F, 0.0F));
      arms.m_171599_("calendula8", CubeListBuilder.m_171558_().m_171514_(40, 16).m_171488_(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.016F)), PartPose.m_171423_(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
      PartDefinition overlay = calendula.m_171599_("overlay", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      overlay.m_171599_("calendula9", CubeListBuilder.m_171558_().m_171514_(40, 32).m_171488_(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      overlay.m_171599_("calendula10", CubeListBuilder.m_171558_().m_171514_(40, 32).m_171488_(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.m_171423_(0.0F, 0.0F, 0.0F, -0.7854F, 0.0F, 0.0F));
      overlay.m_171599_("calendula11", CubeListBuilder.m_171558_().m_171514_(40, 32).m_171488_(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.m_171423_(0.0F, 0.0F, 0.0F, -1.5708F, 0.0F, 0.0F));
      overlay.m_171599_("calendula12", CubeListBuilder.m_171558_().m_171514_(40, 32).m_171488_(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.m_171423_(0.0F, 0.0F, 0.0F, -2.3562F, 0.0F, 0.0F));
      overlay.m_171599_("calendula13", CubeListBuilder.m_171558_().m_171514_(40, 32).m_171488_(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.m_171423_(0.0F, 0.0F, 0.0F, 3.1416F, 0.0F, 0.0F));
      overlay.m_171599_("calendula14", CubeListBuilder.m_171558_().m_171514_(40, 32).m_171488_(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.m_171423_(0.0F, 0.0F, 0.0F, 2.3562F, 0.0F, 0.0F));
      overlay.m_171599_("calendula15", CubeListBuilder.m_171558_().m_171514_(40, 32).m_171488_(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.m_171423_(0.0F, 0.0F, 0.0F, 1.5708F, 0.0F, 0.0F));
      overlay.m_171599_("calendula16", CubeListBuilder.m_171558_().m_171514_(40, 32).m_171488_(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.m_171423_(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   public void m_6973_(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.calendula.f_104203_ = -ageInTicks * 0.15F;
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      poseStack.m_85836_();
      poseStack.m_85837_((double)0.0F, -0.4, 0.2);
      poseStack.m_252781_(Axis.f_252436_.m_252977_(-90.0F));
      this.calendula.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      poseStack.m_85849_();
   }
}
