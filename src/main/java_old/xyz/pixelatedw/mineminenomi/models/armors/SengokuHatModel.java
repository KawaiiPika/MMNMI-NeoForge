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

public class SengokuHatModel<T extends LivingEntity> extends HumanoidArmorModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "sengoku_hat"), "main");
   private final ModelPart sengokuHat;
   private final ModelPart base;
   private final ModelPart tip;
   private final ModelPart bird;
   private final ModelPart birbTail;
   private final ModelPart birbTail1;
   private final ModelPart birbTail5;
   private final ModelPart birbTail2;
   private final ModelPart birbTail4;
   private final ModelPart birbTail3;
   private final ModelPart birbRightWing;
   private final ModelPart birbRightWing1;
   private final ModelPart birbRightWing2;
   private final ModelPart birbLeftWing;
   private final ModelPart birbLeftWing1;
   private final ModelPart birbLeftWing2;
   private final ModelPart birbHead;

   public SengokuHatModel(ModelPart root) {
      super(root);
      this.sengokuHat = root.m_171324_("sengokuHat");
      this.base = this.sengokuHat.m_171324_("base");
      this.tip = this.base.m_171324_("tip");
      this.bird = this.sengokuHat.m_171324_("bird");
      this.birbTail = this.bird.m_171324_("birbTail");
      this.birbTail1 = this.birbTail.m_171324_("birbTail1");
      this.birbTail5 = this.birbTail.m_171324_("birbTail5");
      this.birbTail2 = this.birbTail.m_171324_("birbTail2");
      this.birbTail4 = this.birbTail.m_171324_("birbTail4");
      this.birbTail3 = this.birbTail.m_171324_("birbTail3");
      this.birbRightWing = this.bird.m_171324_("birbRightWing");
      this.birbRightWing1 = this.birbRightWing.m_171324_("birbRightWing1");
      this.birbRightWing2 = this.birbRightWing.m_171324_("birbRightWing2");
      this.birbLeftWing = this.bird.m_171324_("birbLeftWing");
      this.birbLeftWing1 = this.birbLeftWing.m_171324_("birbLeftWing1");
      this.birbLeftWing2 = this.birbLeftWing.m_171324_("birbLeftWing2");
      this.birbHead = this.bird.m_171324_("birbHead");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = HumanoidModel.m_170681_(new CubeDeformation(0.0F), 0.0F);
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition sengokuHat = partdefinition.m_171599_("sengokuHat", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      PartDefinition base = sengokuHat.m_171599_("base", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-4.0F, -4.0F, -4.0F, 8.0F, 4.0F, 8.0F, new CubeDeformation(0.52F)), PartPose.m_171419_(0.0F, -6.0F, 0.0F));
      base.m_171599_("tip", CubeListBuilder.m_171558_().m_171514_(0, 12).m_171488_(-4.0F, -1.0F, -1.0F, 8.0F, 1.0F, 2.0F, new CubeDeformation(0.501F)), PartPose.m_171419_(0.0F, -0.6F, -5.0F));
      PartDefinition bird = sengokuHat.m_171599_("bird", CubeListBuilder.m_171558_().m_171514_(0, 24).m_171488_(-0.5F, -1.0F, -0.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).m_171514_(0, 27).m_171488_(-0.5F, -2.0F, 0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).m_171514_(0, 24).m_171488_(1.5F, -1.0F, -0.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).m_171514_(0, 27).m_171488_(1.5F, -2.0F, 0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).m_171514_(0, 15).m_171488_(-0.5F, -4.0F, -1.5F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(-1.0F, -10.5F, -0.75F));
      PartDefinition birbTail = bird.m_171599_("birbTail", CubeListBuilder.m_171558_(), PartPose.m_171423_(0.0F, -2.0F, 3.0F, -0.1745F, 0.0F, 0.0F));
      birbTail.m_171599_("birbTail1", CubeListBuilder.m_171558_().m_171514_(4, 26).m_171488_(-0.25F, -0.5F, -0.5F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.25F, -0.75F, -0.75F, 0.0F, -0.7418F, 0.0F));
      birbTail.m_171599_("birbTail5", CubeListBuilder.m_171558_().m_171514_(4, 26).m_171488_(-0.25F, -0.5F, -0.5F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(1.25F, -0.75F, -0.5F, 0.0F, 0.7418F, 0.0F));
      birbTail.m_171599_("birbTail2", CubeListBuilder.m_171558_().m_171514_(4, 26).m_171488_(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.01F)), PartPose.m_171423_(0.75F, -0.75F, -0.5F, 0.0F, -0.3927F, 0.0F));
      birbTail.m_171599_("birbTail4", CubeListBuilder.m_171558_().m_171514_(4, 26).m_171488_(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.01F)), PartPose.m_171423_(1.25F, -0.75F, -0.5F, 0.0F, 0.3927F, 0.0F));
      birbTail.m_171599_("birbTail3", CubeListBuilder.m_171558_().m_171514_(4, 26).m_171488_(-0.75F, -0.5F, -0.5F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.02F)), PartPose.m_171419_(1.25F, -0.75F, -0.5F));
      PartDefinition birbRightWing = bird.m_171599_("birbRightWing", CubeListBuilder.m_171558_(), PartPose.m_171419_(-0.5F, -2.75F, -0.25F));
      birbRightWing.m_171599_("birbRightWing1", CubeListBuilder.m_171558_().m_171514_(10, 15).m_171488_(-3.0F, -0.75F, -0.5F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.05F)), PartPose.m_171423_(0.0F, -0.25F, 0.0F, 0.0F, 0.0F, 0.4363F));
      birbRightWing.m_171599_("birbRightWing2", CubeListBuilder.m_171558_().m_171514_(0, 21).m_171488_(-2.9F, -1.0F, -0.5F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.04F)), PartPose.m_171423_(-2.0F, -1.25F, 0.0F, 0.0F, 0.0F, -0.48F));
      PartDefinition birbLeftWing = bird.m_171599_("birbLeftWing", CubeListBuilder.m_171558_(), PartPose.m_171419_(2.5F, -2.75F, -0.25F));
      birbLeftWing.m_171599_("birbLeftWing1", CubeListBuilder.m_171558_().m_171514_(10, 15).m_171480_().m_171488_(-1.0F, -0.75F, -0.5F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.05F)).m_171555_(false), PartPose.m_171423_(0.0F, -0.25F, 0.0F, 0.0F, 0.0F, -0.4363F));
      birbLeftWing.m_171599_("birbLeftWing2", CubeListBuilder.m_171558_().m_171514_(0, 21).m_171480_().m_171488_(0.15F, -1.15F, -0.5F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.04F)).m_171555_(false), PartPose.m_171423_(1.75F, -1.25F, 0.0F, 0.0F, 0.0F, 0.48F));
      bird.m_171599_("birbHead", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-2.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).m_171514_(12, 21).m_171488_(-1.5F, -1.25F, -2.75F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(2.0F, -3.0F, -2.0F));
      return LayerDefinition.m_171565_(meshdefinition, 32, 32);
   }

   public void m_6973_(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.sengokuHat.m_104315_(this.f_102808_);
      this.sengokuHat.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }
}
