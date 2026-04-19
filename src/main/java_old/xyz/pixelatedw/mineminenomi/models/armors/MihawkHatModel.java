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
import net.minecraft.world.entity.LivingEntity;

public class MihawkHatModel<T extends LivingEntity> extends HumanoidArmorModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "mihawk_hat"), "main");
   private final ModelPart mihawkHat;
   private final ModelPart top;
   private final ModelPart top2s;
   private final ModelPart top3s;
   private final ModelPart feather;
   private final ModelPart feather2;
   private final ModelPart feather3;
   private final ModelPart feather4;
   private final ModelPart feather5;
   private final ModelPart feather6;
   private final ModelPart feather7;
   private final ModelPart rightSide;
   private final ModelPart leftSide;

   public MihawkHatModel(ModelPart root) {
      super(root);
      this.mihawkHat = root.m_171324_("mihawkHat");
      this.top = this.mihawkHat.m_171324_("top");
      this.top2s = this.top.m_171324_("top2s");
      this.top3s = this.top.m_171324_("top3s");
      this.feather = this.mihawkHat.m_171324_("feather");
      this.feather2 = this.feather.m_171324_("feather2");
      this.feather3 = this.feather.m_171324_("feather3");
      this.feather4 = this.feather.m_171324_("feather4");
      this.feather5 = this.feather.m_171324_("feather5");
      this.feather6 = this.feather.m_171324_("feather6");
      this.feather7 = this.feather.m_171324_("feather7");
      this.rightSide = this.mihawkHat.m_171324_("rightSide");
      this.leftSide = this.mihawkHat.m_171324_("leftSide");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = HumanoidModel.m_170681_(new CubeDeformation(0.0F), 0.0F);
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition mihawkHat = partdefinition.m_171599_("mihawkHat", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-6.0F, -6.5F, -7.0F, 12.0F, 1.0F, 14.0F, new CubeDeformation(0.0F)).m_171514_(0, 0).m_171488_(-6.0F, -7.0F, -7.0F, 12.0F, 1.0F, 14.0F, new CubeDeformation(-0.001F)), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      PartDefinition top = mihawkHat.m_171599_("top", CubeListBuilder.m_171558_().m_171514_(0, 25).m_171488_(-4.0F, -4.0F, -4.0F, 8.0F, 4.0F, 8.0F, new CubeDeformation(0.52F)).m_171514_(15, 15).m_171488_(-4.0F, -4.75F, -4.0F, 2.0F, 1.0F, 8.0F, new CubeDeformation(0.501F)).m_171514_(15, 15).m_171488_(2.0F, -4.75F, -4.0F, 2.0F, 1.0F, 8.0F, new CubeDeformation(0.501F)), PartPose.m_171419_(0.0F, -7.0F, 0.0F));
      top.m_171599_("top2s", CubeListBuilder.m_171558_().m_171514_(37, 17).m_171488_(-0.75F, 0.0F, -4.0F, 1.0F, 1.0F, 8.0F, new CubeDeformation(0.5F)), PartPose.m_171423_(-1.0F, -4.0F, 0.0F, 0.0F, 0.0F, 0.7854F));
      top.m_171599_("top3s", CubeListBuilder.m_171558_().m_171514_(37, 17).m_171488_(0.0F, -0.75F, -4.0F, 1.0F, 1.0F, 8.0F, new CubeDeformation(0.5F)), PartPose.m_171423_(1.0F, -4.0F, 0.0F, 0.0F, 0.0F, 0.7854F));
      PartDefinition feather = mihawkHat.m_171599_("feather", CubeListBuilder.m_171558_().m_171514_(0, 54).m_171488_(-2.0F, -4.0F, 0.0F, 4.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(4.25F, -6.25F, 0.75F, 0.1745F, 0.0F, 0.0F));
      feather.m_171599_("feather2", CubeListBuilder.m_171558_().m_171514_(0, 54).m_171488_(-2.0F, -4.0F, -1.0F, 4.0F, 4.0F, 5.0F, new CubeDeformation(-0.01F)), PartPose.m_171423_(0.0F, 0.0F, 0.0F, 0.2182F, 0.0F, 0.0F));
      feather.m_171599_("feather3", CubeListBuilder.m_171558_().m_171514_(0, 62).m_171488_(-2.0F, -4.04F, -1.75F, 4.0F, 3.0F, 2.0F, new CubeDeformation(-0.02F)), PartPose.m_171423_(0.0F, -0.25F, 0.25F, 0.6109F, 0.0F, 0.0F));
      feather.m_171599_("feather4", CubeListBuilder.m_171558_().m_171514_(0, 55).m_171488_(-2.0F, -4.2F, -2.25F, 4.0F, 2.0F, 2.0F, new CubeDeformation(-0.03F)), PartPose.m_171423_(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
      feather.m_171599_("feather5", CubeListBuilder.m_171558_().m_171514_(0, 54).m_171488_(-2.0F, -4.0F, -1.0F, 4.0F, 4.0F, 5.0F, new CubeDeformation(-0.01F)), PartPose.m_171423_(0.0F, 0.25F, 3.75F, -0.2182F, 0.0F, 0.0F));
      feather.m_171599_("feather6", CubeListBuilder.m_171558_().m_171514_(0, 55).m_171488_(-2.0F, -2.25F, -2.0F, 4.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 0.8602F, 8.2891F, -0.7418F, 0.0F, 0.0F));
      feather.m_171599_("feather7", CubeListBuilder.m_171558_().m_171514_(0, 55).m_171488_(-2.0F, -2.0F, -2.75F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-0.01F)), PartPose.m_171423_(0.0F, 2.6102F, 8.5391F, -1.5708F, 0.0F, 0.0F));
      mihawkHat.m_171599_("rightSide", CubeListBuilder.m_171558_().m_171514_(20, 28).m_171488_(-1.0F, -1.15F, -7.0F, 3.0F, 1.0F, 14.0F, new CubeDeformation(-0.01F)).m_171514_(20, 28).m_171488_(-1.0F, -1.55F, -7.0F, 3.0F, 1.0F, 14.0F, new CubeDeformation(-0.02F)), PartPose.m_171423_(7.0F, -5.75F, 0.0F, 0.0F, 0.0F, -0.3491F));
      mihawkHat.m_171599_("leftSide", CubeListBuilder.m_171558_().m_171514_(20, 28).m_171488_(-2.0F, -1.15F, -7.0F, 3.0F, 1.0F, 14.0F, new CubeDeformation(-0.01F)).m_171514_(20, 28).m_171488_(-2.0F, -1.55F, -7.0F, 3.0F, 1.0F, 14.0F, new CubeDeformation(-0.02F)), PartPose.m_171423_(-7.0F, -5.75F, 0.0F, 0.0F, 0.0F, 0.3491F));
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   public void m_6973_(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.mihawkHat.m_104315_(this.f_102808_);
      this.mihawkHat.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }
}
