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
import xyz.pixelatedw.mineminenomi.abilities.ZoomAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;

public class SniperGogglesModel<T extends LivingEntity> extends HumanoidArmorModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "sniper_goggles"), "main");
   private final ModelPart base;
   private final ModelPart rightEye;
   private final ModelPart rightEye2;
   private final ModelPart rightEyeDetail;
   private final ModelPart rightEyeDetail2;
   private final ModelPart leftEye;
   private final ModelPart leftEyeDetail;
   private final ModelPart leftEye2;
   private final ModelPart leftEyeDetail2;

   public SniperGogglesModel(ModelPart root) {
      super(root);
      this.base = root.m_171324_("base");
      this.rightEye = this.base.m_171324_("rightEye");
      this.rightEye2 = this.rightEye.m_171324_("rightEye2");
      this.rightEyeDetail = this.rightEye.m_171324_("rightEyeDetail");
      this.rightEyeDetail2 = this.rightEye.m_171324_("rightEyeDetail2");
      this.leftEye = this.base.m_171324_("leftEye");
      this.leftEyeDetail = this.leftEye.m_171324_("leftEyeDetail");
      this.leftEye2 = this.leftEye.m_171324_("leftEye2");
      this.leftEyeDetail2 = this.leftEye.m_171324_("leftEyeDetail2");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = HumanoidModel.m_170681_(new CubeDeformation(0.0F), 0.0F);
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition base = partdefinition.m_171599_("base", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.25F)).m_171514_(24, 0).m_171488_(-4.85F, -5.0F, -0.8F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).m_171514_(24, 0).m_171480_().m_171488_(3.85F, -5.0F, -0.8F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      PartDefinition rightEye = base.m_171599_("rightEye", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-0.95F, -1.0F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.01F)), PartPose.m_171419_(-1.9F, -4.0F, -4.25F));
      rightEye.m_171599_("rightEye2", CubeListBuilder.m_171558_().m_171514_(0, 5).m_171488_(-0.45F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 0.0F, -0.3F));
      rightEye.m_171599_("rightEyeDetail", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-1.45F, -0.5F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      rightEye.m_171599_("rightEyeDetail2", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-0.45F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      PartDefinition leftEye = base.m_171599_("leftEye", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-1.05F, -1.0F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.01F)), PartPose.m_171419_(1.9F, -4.0F, -4.25F));
      leftEye.m_171599_("leftEyeDetail", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-1.55F, -0.5F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      leftEye.m_171599_("leftEye2", CubeListBuilder.m_171558_().m_171514_(0, 5).m_171488_(-0.55F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 0.0F, -0.3F));
      leftEye.m_171599_("leftEyeDetail2", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-0.55F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      return LayerDefinition.m_171565_(meshdefinition, 32, 16);
   }

   public void m_6973_(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.base.m_104315_(this.f_102808_);
      this.base.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }

   public void updateState(LivingEntity entity) {
      IAbilityData abilityProps = (IAbilityData)AbilityCapability.get(entity).orElse((Object)null);
      if (abilityProps != null) {
         this.leftEye.f_104201_ = -5.5F;
         this.rightEye.f_104201_ = -5.5F;
         ZoomAbility zoomAbility = (ZoomAbility)abilityProps.getEquippedAbility((AbilityCore)ZoomAbility.INSTANCE.get());
         if (zoomAbility != null && zoomAbility.isContinuous()) {
            this.leftEye.f_104201_ = -3.5F;
            this.rightEye.f_104201_ = -3.5F;
         }

      }
   }
}
