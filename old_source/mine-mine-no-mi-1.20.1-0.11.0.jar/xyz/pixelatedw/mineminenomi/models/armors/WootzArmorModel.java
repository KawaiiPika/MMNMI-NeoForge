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
import xyz.pixelatedw.mineminenomi.abilities.GunArrayAbility;
import xyz.pixelatedw.mineminenomi.abilities.MH5Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;

public class WootzArmorModel<T extends LivingEntity> extends HumanoidArmorModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "wootz_armor"), "main");
   private final ModelPart armor;
   private final ModelPart bodyArmor;
   private final ModelPart rightShoulderBase;
   private final ModelPart rightShoulder1;
   private final ModelPart rightShoulder2;
   private final ModelPart rightShoulderGuns;
   private final ModelPart rightShoulderGun1;
   private final ModelPart rightShoulderGun2;
   private final ModelPart leftShoulderBase;
   private final ModelPart leftShoulder1;
   private final ModelPart leftShoulder2;
   private final ModelPart leftShoulderGuns;
   private final ModelPart leftShoulderGun2;
   private final ModelPart leftShoulderGun1;
   private final ModelPart rightArmArmor;
   private final ModelPart leftArmArmor;
   private boolean isChargingMH5 = false;

   public WootzArmorModel(ModelPart root) {
      super(root);
      this.armor = root.m_171324_("armor");
      this.bodyArmor = this.armor.m_171324_("bodyArmor");
      this.rightShoulderBase = this.armor.m_171324_("rightShoulderBase");
      this.rightShoulder1 = this.rightShoulderBase.m_171324_("rightShoulder1");
      this.rightShoulder2 = this.rightShoulder1.m_171324_("rightShoulder2");
      this.rightShoulderGuns = this.armor.m_171324_("rightShoulderGuns");
      this.rightShoulderGun1 = this.rightShoulderGuns.m_171324_("rightShoulderGun1");
      this.rightShoulderGun2 = this.rightShoulderGuns.m_171324_("rightShoulderGun2");
      this.leftShoulderBase = this.armor.m_171324_("leftShoulderBase");
      this.leftShoulder1 = this.leftShoulderBase.m_171324_("leftShoulder1");
      this.leftShoulder2 = this.leftShoulder1.m_171324_("leftShoulder2");
      this.leftShoulderGuns = this.armor.m_171324_("leftShoulderGuns");
      this.leftShoulderGun2 = this.leftShoulderGuns.m_171324_("leftShoulderGun2");
      this.leftShoulderGun1 = this.leftShoulderGuns.m_171324_("leftShoulderGun1");
      this.rightArmArmor = root.m_171324_("rightArmArmor");
      this.leftArmArmor = root.m_171324_("leftArmArmor");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = HumanoidModel.m_170681_(new CubeDeformation(0.0F), 0.0F);
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition armor = partdefinition.m_171599_("armor", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 0.25F, 0.0F));
      armor.m_171599_("bodyArmor", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-5.5F, 0.0F, -3.0F, 11.0F, 11.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, -0.25F, 0.0F));
      PartDefinition rightShoulderBase = armor.m_171599_("rightShoulderBase", CubeListBuilder.m_171558_().m_171514_(21, 17).m_171488_(-4.0F, 0.0F, -7.0F, 8.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-8.0F, 0.25F, 3.4F, 0.0F, 0.0456F, -0.5236F));
      PartDefinition rightShoulder1 = rightShoulderBase.m_171599_("rightShoulder1", CubeListBuilder.m_171558_().m_171514_(0, 24).m_171488_(-3.5F, -1.0F, -6.5F, 7.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      rightShoulder1.m_171599_("rightShoulder2", CubeListBuilder.m_171558_().m_171514_(0, 17).m_171488_(-11.0531F, -1.5F, -2.6F, 6.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(8.0F, -0.5F, -3.4F));
      PartDefinition rightShoulderGuns = armor.m_171599_("rightShoulderGuns", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(-6.0F, -0.25F, 0.0F));
      rightShoulderGuns.m_171599_("rightShoulderGun1", CubeListBuilder.m_171558_().m_171514_(48, 16).m_171488_(-1.5F, -2.3F, -4.0F, 3.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      rightShoulderGuns.m_171599_("rightShoulderGun2", CubeListBuilder.m_171558_().m_171514_(48, 16).m_171488_(-1.5F, -2.3F, -4.0F, 3.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(-3.9F, 1.0F, 0.0F));
      PartDefinition leftShoulderBase = armor.m_171599_("leftShoulderBase", CubeListBuilder.m_171558_().m_171514_(21, 17).m_171480_().m_171488_(-4.0F, 0.0F, -7.0F, 8.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(8.0F, 0.25F, 3.4F, 0.0F, -0.0456F, 0.5236F));
      PartDefinition leftShoulder1 = leftShoulderBase.m_171599_("leftShoulder1", CubeListBuilder.m_171558_().m_171514_(0, 24).m_171480_().m_171488_(-3.5F, -1.0F, -6.5F, 7.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      leftShoulder1.m_171599_("leftShoulder2", CubeListBuilder.m_171558_().m_171514_(0, 17).m_171480_().m_171488_(5.0531F, -1.5F, -2.6F, 6.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171419_(-8.0F, -0.5F, -3.4F));
      PartDefinition leftShoulderGuns = armor.m_171599_("leftShoulderGuns", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(6.0F, -0.25F, 0.0F));
      leftShoulderGuns.m_171599_("leftShoulderGun2", CubeListBuilder.m_171558_().m_171514_(48, 16).m_171488_(-1.5F, -2.3F, -4.0F, 3.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(3.9F, 1.0F, 0.0F));
      leftShoulderGuns.m_171599_("leftShoulderGun1", CubeListBuilder.m_171558_().m_171514_(48, 16).m_171488_(-1.5F, -2.3F, -4.0F, 3.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      partdefinition.m_171599_("rightArmArmor", CubeListBuilder.m_171558_().m_171514_(34, 0).m_171488_(-3.5F, -2.0F, -2.5F, 5.0F, 10.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(-6.0F, 1.75F, 0.0F));
      partdefinition.m_171599_("leftArmArmor", CubeListBuilder.m_171558_().m_171514_(34, 0).m_171488_(-1.5F, -2.0F, -2.5F, 5.0F, 10.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(6.0F, 1.75F, 0.0F));
      return LayerDefinition.m_171565_(meshdefinition, 64, 32);
   }

   public void m_6973_(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.armor.m_104315_(this.f_102810_);
      this.leftArmArmor.m_104315_(this.f_102812_);
      this.rightArmArmor.m_104315_(this.f_102811_);
      this.armor.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.rightArmArmor.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.leftArmArmor.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      if (this.isChargingMH5) {
         poseStack.m_85836_();
         poseStack.m_85841_(1.5F, 1.5F, 1.5F);
         poseStack.m_85837_((double)0.0F, -0.1, 0.2);
         this.leftShoulderBase.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
         poseStack.m_85849_();
      }

   }

   public void updateState(LivingEntity entity) {
      boolean hasGunsOut = false;
      IAbilityData abilityProps = (IAbilityData)AbilityCapability.get(entity).orElse((Object)null);
      if (abilityProps != null) {
         GunArrayAbility gunArray = (GunArrayAbility)abilityProps.getEquippedAbility((AbilityCore)GunArrayAbility.INSTANCE.get());
         if (gunArray != null) {
            boolean isActive = (Boolean)gunArray.getComponent((AbilityComponentKey)ModAbilityComponents.CONTINUOUS.get()).map((comp) -> comp.isContinuous()).orElse(false);
            if (isActive) {
               hasGunsOut = true;
            }
         }

         MH5Ability mh5 = (MH5Ability)abilityProps.getEquippedAbility((AbilityCore)MH5Ability.INSTANCE.get());
         if (mh5 != null) {
            boolean isActive = (Boolean)mh5.getComponent((AbilityComponentKey)ModAbilityComponents.CHARGE.get()).map((comp) -> comp.isCharging()).orElse(false);
            if (isActive) {
               this.isChargingMH5 = true;
               hasGunsOut = false;
            }
         }

         if (this.isChargingMH5) {
            this.leftShoulderBase.f_104203_ = (float)Math.toRadians((double)80.0F);
            this.leftShoulderBase.f_104200_ = 2.0F;
            this.leftShoulderBase.f_104202_ = -7.0F;
         }

         if (hasGunsOut) {
            this.leftShoulderBase.f_104203_ = (float)Math.toRadians((double)-70.0F);
            this.rightShoulderBase.f_104203_ = (float)Math.toRadians((double)-70.0F);
            this.leftShoulderGuns.f_104207_ = true;
            this.rightShoulderGuns.f_104207_ = true;
         } else {
            this.leftShoulderBase.f_104203_ = (float)Math.toRadians((double)0.0F);
            this.rightShoulderBase.f_104203_ = (float)Math.toRadians((double)0.0F);
            this.leftShoulderGuns.f_104207_ = false;
            this.rightShoulderGuns.f_104207_ = false;
         }

      }
   }
}
