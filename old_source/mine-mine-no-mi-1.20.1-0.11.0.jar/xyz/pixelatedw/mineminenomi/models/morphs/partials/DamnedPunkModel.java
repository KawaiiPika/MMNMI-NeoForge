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
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.morph.IFirstPersonHandReplacer;

public class DamnedPunkModel<T extends LivingEntity> extends HumanoidModel<T> implements IFirstPersonHandReplacer {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "damned_punk"), "main");
   private final ModelPart cube1;
   private final ModelPart cube2;
   private final ModelPart bigOctagon;
   private final ModelPart wire1;
   private final ModelPart wire2;
   private final ModelPart wire3;
   private final ModelPart wire4;
   private final ModelPart wire5;
   private final ModelPart wire6;
   private final ModelPart wire7;
   private final ModelPart wire8;
   private final ModelPart wire9;
   private final ModelPart detail7;
   private final ModelPart detail6;
   private final ModelPart detail5;
   private final ModelPart detail4;
   private final ModelPart detail3;
   private final ModelPart detail2;
   private final ModelPart detail1;
   private final ModelPart armSupport;
   private final ModelPart innerGun;
   private final ModelPart armSupport2;
   private final ModelPart octagon;
   private final ModelPart octagon_r1;
   private final ModelPart octagon3;
   private final ModelPart octagon_r2;
   private final ModelPart octagon2;
   private final ModelPart octagon_r3;

   public DamnedPunkModel(ModelPart root) {
      super(root);
      this.cube1 = this.f_102811_.m_171324_("cube1");
      this.cube2 = this.f_102811_.m_171324_("cube2");
      this.bigOctagon = this.f_102811_.m_171324_("bigOctagon");
      this.wire1 = this.f_102811_.m_171324_("wire1");
      this.wire2 = this.f_102811_.m_171324_("wire2");
      this.wire3 = this.f_102811_.m_171324_("wire3");
      this.wire4 = this.f_102811_.m_171324_("wire4");
      this.wire5 = this.f_102811_.m_171324_("wire5");
      this.wire6 = this.f_102811_.m_171324_("wire6");
      this.wire7 = this.f_102811_.m_171324_("wire7");
      this.wire8 = this.f_102811_.m_171324_("wire8");
      this.wire9 = this.f_102811_.m_171324_("wire9");
      this.detail7 = this.f_102811_.m_171324_("detail7");
      this.detail6 = this.f_102811_.m_171324_("detail6");
      this.detail5 = this.f_102811_.m_171324_("detail5");
      this.detail4 = this.f_102811_.m_171324_("detail4");
      this.detail3 = this.f_102811_.m_171324_("detail3");
      this.detail2 = this.f_102811_.m_171324_("detail2");
      this.detail1 = this.f_102811_.m_171324_("detail1");
      this.armSupport = this.f_102811_.m_171324_("armSupport");
      this.innerGun = this.f_102811_.m_171324_("innerGun");
      this.armSupport2 = this.f_102811_.m_171324_("armSupport2");
      this.octagon = this.f_102811_.m_171324_("octagon");
      this.octagon_r1 = this.octagon.m_171324_("octagon_r1");
      this.octagon3 = this.f_102811_.m_171324_("octagon3");
      this.octagon_r2 = this.octagon3.m_171324_("octagon_r2");
      this.octagon2 = this.f_102811_.m_171324_("octagon2");
      this.octagon_r3 = this.octagon2.m_171324_("octagon_r3");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = HumanoidModel.m_170681_(CubeDeformation.f_171458_, 0.0F);
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition rightArm = partdefinition.m_171599_("right_arm", CubeListBuilder.m_171558_().m_171514_(0, 16).m_171488_(-9.0F, -4.0F, -4.0F, 5.0F, 5.0F, 8.0F, new CubeDeformation(0.002F)).m_171514_(24, 47).m_171488_(-9.0F, 1.0F, -4.0F, 3.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)).m_171514_(14, 73).m_171488_(-9.0F, 2.0F, -3.0F, 3.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)).m_171514_(84, 49).m_171488_(-10.0F, -3.0F, -3.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)).m_171514_(75, 14).m_171488_(-10.0F, -1.0F, -4.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).m_171514_(56, 51).m_171488_(-10.0F, 3.0F, 0.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).m_171514_(56, 51).m_171488_(-10.0F, 3.0F, -1.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).m_171514_(56, 51).m_171488_(-10.0F, 3.0F, -2.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).m_171514_(56, 51).m_171488_(-10.0F, 3.0F, 1.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).m_171514_(75, 14).m_171488_(-10.0F, -1.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).m_171514_(75, 14).m_171488_(-10.0F, -1.0F, 3.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).m_171514_(84, 41).m_171488_(-10.0F, 1.0F, -3.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)).m_171514_(57, 0).m_171488_(-11.0F, -5.0F, -5.0F, 8.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)).m_171514_(85, 11).m_171488_(-9.0F, 3.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)).m_171514_(0, 0).m_171488_(-13.0F, -8.0F, -39.0F, 14.0F, 18.0F, 29.0F, new CubeDeformation(0.0F)).m_171514_(87, 23).m_171488_(-4.0F, -5.0F, -46.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.001F)).m_171514_(87, 23).m_171488_(-4.0F, 1.0F, -46.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.001F)).m_171514_(87, 23).m_171488_(-10.0F, 1.0F, -46.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.001F)).m_171514_(87, 23).m_171488_(-10.0F, -5.0F, -46.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.001F)).m_171514_(86, 29).m_171488_(-5.0F, -13.0F, -34.0F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)).m_171514_(30, 67).m_171488_(-10.0F, -9.0F, -24.0F, 8.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)).m_171514_(0, 47).m_171488_(-16.0F, -3.0F, -34.0F, 1.0F, 6.0F, 6.0F, new CubeDeformation(-0.001F)).m_171514_(64, 47).m_171488_(-8.0F, -10.0F, -22.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)).m_171514_(0, 47).m_171488_(2.5F, -4.0F, -20.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).m_171514_(0, 47).m_171488_(-16.5F, -1.0F, -32.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).m_171514_(0, 47).m_171488_(2.5F, 4.0F, -30.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(-5.0F, 2.0F, 0.0F));
      rightArm.m_171599_("cube1", CubeListBuilder.m_171558_().m_171514_(83, 0).m_171488_(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-7.0F, -5.0F, 0.0F, -0.7854F, 0.0F, 0.0F));
      rightArm.m_171599_("cube2", CubeListBuilder.m_171558_().m_171514_(83, 0).m_171488_(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-7.0F, -5.0F, 0.0F, 0.1968F, 0.3527F, 0.5763F));
      rightArm.m_171599_("bigOctagon", CubeListBuilder.m_171558_().m_171514_(0, 47).m_171488_(-0.5F, -3.0F, -3.0F, 1.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-15.5F, 0.0F, -31.0F, 0.7854F, 0.0F, 0.0F));
      rightArm.m_171599_("wire1", CubeListBuilder.m_171558_().m_171514_(57, 0).m_171488_(-1.0F, -1.0F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.001F)), PartPose.m_171423_(-8.0F, 7.0F, -1.0F, -0.829F, 0.0F, 0.0F));
      rightArm.m_171599_("wire2", CubeListBuilder.m_171558_().m_171514_(57, 2).m_171488_(-1.0F, 0.7386F, -0.3149F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-8.0F, 9.7425F, -4.2669F, -1.1781F, 0.0F, 0.0F));
      rightArm.m_171599_("wire3", CubeListBuilder.m_171558_().m_171514_(57, 2).m_171488_(-1.0F, -1.6462F, -1.2493F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.11F)), PartPose.m_171423_(-8.0F, 11.7425F, -9.2669F, -2.3998F, 0.0F, 0.0F));
      rightArm.m_171599_("wire4", CubeListBuilder.m_171558_().m_171514_(25, 56).m_171488_(-2.5F, -1.0F, 0.0F, 5.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-10.9153F, 3.0F, -6.8922F, 0.0F, -2.7053F, 0.0F));
      rightArm.m_171599_("wire5", CubeListBuilder.m_171558_().m_171514_(27, 56).m_171488_(-1.5F, -1.0F, -0.7255F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-13.9323F, 3.0F, -6.7628F, 0.0F, 3.0543F, 0.0F));
      rightArm.m_171599_("wire6", CubeListBuilder.m_171558_().m_171514_(24, 56).m_171488_(-5.9F, -1.5F, -2.0F, 7.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-16.9153F, 6.0F, -11.7922F, 2.5986F, 0.8367F, 2.459F));
      rightArm.m_171599_("wire7", CubeListBuilder.m_171558_().m_171514_(24, 56).m_171488_(-8.4F, -1.0F, -2.0F, 7.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-16.9153F, 6.0F, -19.7922F, 0.0F, 1.5708F, 0.0F));
      rightArm.m_171599_("wire8", CubeListBuilder.m_171558_().m_171514_(25, 56).m_171488_(-3.9043F, -1.0F, -0.6308F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-16.3634F, 6.0F, -21.6518F, 0.0F, 1.0908F, 0.0F));
      rightArm.m_171599_("wire9", CubeListBuilder.m_171558_().m_171514_(25, 56).m_171488_(-3.0F, -1.0F, -1.0F, 5.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-14.0F, 6.0F, -23.0F, 0.0F, 0.6545F, 0.0F));
      rightArm.m_171599_("detail7", CubeListBuilder.m_171558_().m_171514_(25, 15).m_171488_(-0.5F, -1.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).m_171514_(25, 15).m_171488_(-0.5F, -1.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-8.5F, -7.0F, -2.0F, 0.7854F, 0.0F, -0.6981F));
      rightArm.m_171599_("detail6", CubeListBuilder.m_171558_().m_171514_(25, 15).m_171488_(-0.5F, -1.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-7.8572F, -6.234F, 2.0F, -0.6545F, 0.3054F, -0.6981F));
      rightArm.m_171599_("detail5", CubeListBuilder.m_171558_().m_171514_(25, 16).m_171488_(-0.5F, -1.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-9.3893F, -4.9484F, 0.0F, -0.1745F, 0.0F, -0.6981F));
      rightArm.m_171599_("detail4", CubeListBuilder.m_171558_().m_171514_(25, 15).m_171488_(-0.5F, -1.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-5.6823F, -6.7535F, 2.0F, -0.8727F, 0.0F, 0.6109F));
      rightArm.m_171599_("detail3", CubeListBuilder.m_171558_().m_171514_(25, 15).m_171488_(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).m_171514_(25, 15).m_171488_(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-5.6823F, -6.7535F, 0.0F, 0.0F, 0.0F, 0.6109F));
      rightArm.m_171599_("detail2", CubeListBuilder.m_171558_().m_171514_(25, 15).m_171488_(-0.5F, -1.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-8.5F, -7.0F, 0.0F, 0.0F, 0.0F, -0.6981F));
      rightArm.m_171599_("detail1", CubeListBuilder.m_171558_().m_171514_(25, 15).m_171488_(-0.5F, -1.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-6.0747F, -6.6512F, -1.5F, 0.4363F, 0.0F, 0.48F));
      rightArm.m_171599_("armSupport", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-3.0F, -4.5F, -4.5F, 5.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-1.0F, 1.0F, 0.0F, -0.7854F, 0.0F, 0.0F));
      rightArm.m_171599_("innerGun", CubeListBuilder.m_171558_().m_171514_(36, 78).m_171488_(-36.5F, 4.0F, -4.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)).m_171514_(82, 67).m_171488_(-36.5F, -4.0F, -4.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)).m_171514_(24, 47).m_171488_(-36.5F, -2.0F, 2.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)).m_171514_(57, 11).m_171488_(-36.5F, -2.0F, -6.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)).m_171514_(0, 47).m_171488_(-34.5F, -5.0F, -7.0F, 5.0F, 12.0F, 14.0F, new CubeDeformation(0.0F)).m_171514_(0, 73).m_171488_(-0.5F, -3.0F, -5.0F, 3.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)).m_171514_(38, 47).m_171488_(2.5F, -1.0F, -3.0F, 3.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-7.0F, 0.0F, -9.5263F, 0.0F, -1.5708F, -1.5708F));
      rightArm.m_171599_("armSupport2", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-3.0F, -4.0F, -5.0F, 5.0F, 8.0F, 8.0F, new CubeDeformation(-0.001F)), PartPose.m_171423_(-1.0F, 1.0F, 0.0F, -1.5708F, 0.0F, 0.0F));
      PartDefinition octagon = rightArm.m_171599_("octagon", CubeListBuilder.m_171558_().m_171514_(74, 55).m_171488_(-4.0F, -9.2426F, 5.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(-0.003F)).m_171514_(0, 0).m_171488_(-4.0F, -11.0F, 6.7574F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.001F)), PartPose.m_171419_(5.0F, 5.0F, -27.0F));
      octagon.m_171599_("octagon_r1", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(4.0F, -3.0F, -1.2426F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)).m_171514_(74, 55).m_171488_(4.0F, -1.2426F, -3.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(-0.002F)), PartPose.m_171423_(-8.0F, -8.0F, 8.0F, -0.7854F, 0.0F, 0.0F));
      PartDefinition octagon3 = rightArm.m_171599_("octagon3", CubeListBuilder.m_171558_().m_171514_(38, 47).m_171488_(-16.0F, -11.0F, -12.2426F, 2.0F, 6.0F, 14.0F, new CubeDeformation(0.002F)).m_171514_(56, 73).m_171488_(-16.0F, -15.2426F, -8.0F, 2.0F, 14.0F, 6.0F, new CubeDeformation(-0.001F)), PartPose.m_171419_(1.0F, 8.0F, -26.0F));
      octagon3.m_171599_("octagon_r2", CubeListBuilder.m_171558_().m_171514_(56, 73).m_171488_(-8.0F, -7.2426F, -3.0F, 2.0F, 14.0F, 6.0F, new CubeDeformation(-0.002F)).m_171514_(38, 47).m_171488_(-8.0F, -3.0F, -7.2426F, 2.0F, 6.0F, 14.0F, new CubeDeformation(-0.004F)), PartPose.m_171423_(-8.0F, -8.0F, -5.0F, -0.7854F, 0.0F, 0.0F));
      PartDefinition octagon2 = rightArm.m_171599_("octagon2", CubeListBuilder.m_171558_().m_171514_(74, 55).m_171488_(-4.0F, -9.2426F, 5.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.002F)).m_171514_(0, 0).m_171488_(-4.0F, -11.0F, 6.7574F, 2.0F, 6.0F, 2.0F, new CubeDeformation(-0.001F)), PartPose.m_171419_(5.0F, 13.0F, -37.0F));
      octagon2.m_171599_("octagon_r3", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(4.0F, -3.0F, -1.2426F, 2.0F, 6.0F, 2.0F, new CubeDeformation(-0.004F)).m_171514_(74, 55).m_171488_(4.0F, -1.2426F, -3.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)).m_171514_(74, 55).m_171488_(4.0F, -1.2426F, -3.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(-0.002F)), PartPose.m_171423_(-8.0F, -8.0F, 8.0F, -0.7854F, 0.0F, 0.0F));
      return LayerDefinition.m_171565_(meshdefinition, 128, 128);
   }

   public void m_6973_(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      super.m_6973_(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
      this.f_102811_.f_104203_ = 0.0F;
      this.f_102811_.f_104204_ = 0.0F;
      this.f_102811_.f_104205_ = 0.0F;
      this.f_102811_.f_104200_ = -3.0F;
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.f_102811_.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }

   public void renderFirstPersonArm(PoseStack poseStack, VertexConsumer vertex, int packedLight, int overlay, float red, float green, float blue, float alpha, HumanoidArm side, boolean isLeg) {
      poseStack.m_85837_(-0.2, (double)1.0F, (double)0.5F);
      poseStack.m_252781_(Axis.f_252436_.m_252977_(60.0F));
      poseStack.m_252781_(Axis.f_252403_.m_252977_(-10.0F));
      poseStack.m_252781_(Axis.f_252529_.m_252977_(115.0F));
      this.m_7695_(poseStack, vertex, packedLight, overlay, red, green, blue, alpha);
   }
}
