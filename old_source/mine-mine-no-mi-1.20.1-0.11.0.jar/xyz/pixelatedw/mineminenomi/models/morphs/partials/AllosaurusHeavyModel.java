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
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.api.morph.HumanoidMorphModel;

public class AllosaurusHeavyModel<T extends LivingEntity> extends HumanoidMorphModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "allosaurus_heavy"), "main");
   private final ModelPart lowerleftleg1;
   private final ModelPart lowerleftleg2;
   private final ModelPart lefttalon;
   private final ModelPart leftToe1;
   private final ModelPart leftSubToe;
   private final ModelPart leftToe2;
   private final ModelPart leftSubToe2;
   private final ModelPart leftToe3;
   private final ModelPart leftSubToe3;
   private final ModelPart lowerrightleg1;
   private final ModelPart lowerrightleg;
   private final ModelPart rightToe1;
   private final ModelPart rightSubToe;
   private final ModelPart rightToe2;
   private final ModelPart rightSubToe2;
   private final ModelPart rightToe3;
   private final ModelPart rightSubToe3;
   private final ModelPart righttalon;
   private final ModelPart tail;
   private final ModelPart tail2;
   private final ModelPart tail3;
   private final ModelPart tail4;
   private final ModelPart tail5;

   public AllosaurusHeavyModel(ModelPart root) {
      super(root);
      this.lowerleftleg1 = this.f_102814_.m_171324_("lowerleftleg1");
      this.lowerleftleg2 = this.lowerleftleg1.m_171324_("lowerleftleg2");
      this.lefttalon = this.lowerleftleg2.m_171324_("lefttalon");
      this.leftToe1 = this.lowerleftleg2.m_171324_("leftToe1");
      this.leftSubToe = this.leftToe1.m_171324_("leftSubToe");
      this.leftToe2 = this.lowerleftleg2.m_171324_("leftToe2");
      this.leftSubToe2 = this.leftToe2.m_171324_("leftSubToe2");
      this.leftToe3 = this.lowerleftleg2.m_171324_("leftToe3");
      this.leftSubToe3 = this.leftToe3.m_171324_("leftSubToe3");
      this.lowerrightleg1 = this.f_102813_.m_171324_("lowerrightleg1");
      this.lowerrightleg = this.lowerrightleg1.m_171324_("lowerrightleg");
      this.rightToe1 = this.lowerrightleg.m_171324_("rightToe1");
      this.rightSubToe = this.rightToe1.m_171324_("rightSubToe");
      this.rightToe2 = this.lowerrightleg.m_171324_("rightToe2");
      this.rightSubToe2 = this.rightToe2.m_171324_("rightSubToe2");
      this.rightToe3 = this.lowerrightleg.m_171324_("rightToe3");
      this.rightSubToe3 = this.rightToe3.m_171324_("rightSubToe3");
      this.righttalon = this.lowerrightleg.m_171324_("righttalon");
      this.tail = this.f_102810_.m_171324_("tail");
      this.tail2 = this.tail.m_171324_("tail2");
      this.tail3 = this.tail2.m_171324_("tail3");
      this.tail4 = this.tail3.m_171324_("tail4");
      this.tail5 = this.tail4.m_171324_("tail5");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = HumanoidModel.m_170681_(CubeDeformation.f_171458_, 0.0F);
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition leftLeg = partdefinition.m_171599_("left_leg", CubeListBuilder.m_171558_().m_171514_(0, 32).m_171488_(-1.9F, -1.8F, -1.8F, 3.8F, 6.6F, 3.6F, new CubeDeformation(0.2F)), PartPose.m_171423_(2.3F, 11.697F, -0.3532F, -0.1047F, 0.0F, 0.0F));
      PartDefinition lowerleftleg1 = leftLeg.m_171599_("lowerleftleg1", CubeListBuilder.m_171558_().m_171514_(0, 43).m_171488_(-1.4958F, -0.5156F, -1.6517F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0083F, 3.7578F, -0.2068F, 0.7679F, 0.0F, 0.0F));
      PartDefinition lowerleftleg2 = lowerleftleg1.m_171599_("lowerleftleg2", CubeListBuilder.m_171558_().m_171514_(0, 50).m_171488_(-1.4958F, -0.9112F, -0.4336F, 3.0F, 9.0F, 3.0F, new CubeDeformation(0.1F)), PartPose.m_171423_(0.0F, 0.4599F, 0.318F, -0.7941F, 0.0F, 0.0F));
      lowerleftleg2.m_171599_("lefttalon", CubeListBuilder.m_171558_().m_171514_(10, 60).m_171488_(-0.6F, -0.6039F, -0.5317F, 1.2F, 1.2F, 2.8F, new CubeDeformation(0.0F)), PartPose.m_171423_(-0.0083F, 6.5021F, 2.1765F, -0.6109F, 0.0F, 0.0F));
      PartDefinition leftToe1 = lowerleftleg2.m_171599_("leftToe1", CubeListBuilder.m_171558_().m_171514_(13, 56).m_171488_(-0.1632F, -0.957F, -1.7595F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.m_171423_(0.6789F, 7.8758F, -0.2283F, 0.262F, -0.043F, -0.0076F));
      leftToe1.m_171599_("leftSubToe", CubeListBuilder.m_171558_().m_171514_(13, 56).m_171488_(-0.1632F, -0.957F, -1.7595F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.2F)), PartPose.m_171423_(0.0112F, -0.139F, -1.2439F, 0.3927F, 0.0F, 0.0F));
      PartDefinition leftToe2 = lowerleftleg2.m_171599_("leftToe2", CubeListBuilder.m_171558_().m_171514_(13, 56).m_171488_(-0.1632F, -0.957F, -1.7595F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.m_171423_(-0.3211F, 7.8758F, -0.2283F, 0.2618F, 0.0F, 0.0F));
      leftToe2.m_171599_("leftSubToe2", CubeListBuilder.m_171558_().m_171514_(13, 56).m_171488_(-0.1632F, -0.957F, -1.7595F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.2F)), PartPose.m_171423_(0.0112F, -0.139F, -1.2439F, 0.3927F, 0.0F, 0.0F));
      PartDefinition leftToe3 = lowerleftleg2.m_171599_("leftToe3", CubeListBuilder.m_171558_().m_171514_(13, 56).m_171488_(-0.1632F, -0.957F, -1.7595F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.m_171423_(-1.3211F, 7.8758F, -0.2283F, 0.262F, 0.043F, 0.0076F));
      leftToe3.m_171599_("leftSubToe3", CubeListBuilder.m_171558_().m_171514_(13, 56).m_171488_(-0.1632F, -0.957F, -1.7595F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.2F)), PartPose.m_171423_(0.0112F, -0.139F, -1.2439F, 0.3927F, 0.0F, 0.0F));
      PartDefinition rightLeg = partdefinition.m_171599_("right_leg", CubeListBuilder.m_171558_().m_171514_(0, 32).m_171480_().m_171488_(-1.9F, -1.8F, -1.8F, 3.8F, 6.6F, 3.6F, new CubeDeformation(0.2F)).m_171555_(false), PartPose.m_171423_(-2.3F, 11.697F, -0.3532F, -0.1047F, 0.0F, 0.0F));
      PartDefinition lowerrightleg1 = rightLeg.m_171599_("lowerrightleg1", CubeListBuilder.m_171558_().m_171514_(0, 43).m_171480_().m_171488_(-1.5042F, -0.5156F, -1.6517F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(-0.0083F, 3.7578F, -0.2068F, 0.7679F, 0.0F, 0.0F));
      PartDefinition lowerrightleg = lowerrightleg1.m_171599_("lowerrightleg", CubeListBuilder.m_171558_().m_171514_(0, 50).m_171480_().m_171488_(-1.5042F, -0.9112F, -0.4336F, 3.0F, 9.0F, 3.0F, new CubeDeformation(0.1F)).m_171555_(false), PartPose.m_171423_(0.0F, 0.4599F, 0.318F, -0.7941F, 0.0F, 0.0F));
      PartDefinition rightToe1 = lowerrightleg.m_171599_("rightToe1", CubeListBuilder.m_171558_().m_171514_(13, 56).m_171480_().m_171488_(-0.8368F, -0.957F, -1.7595F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.1F)).m_171555_(false), PartPose.m_171423_(-0.6789F, 7.8758F, -0.2283F, 0.262F, 0.043F, 0.0076F));
      rightToe1.m_171599_("rightSubToe", CubeListBuilder.m_171558_().m_171514_(13, 56).m_171480_().m_171488_(-0.8368F, -0.957F, -1.7595F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.2F)).m_171555_(false), PartPose.m_171423_(-0.0112F, -0.139F, -1.2439F, 0.3927F, 0.0F, 0.0F));
      PartDefinition rightToe2 = lowerrightleg.m_171599_("rightToe2", CubeListBuilder.m_171558_().m_171514_(13, 56).m_171480_().m_171488_(-0.8368F, -0.957F, -1.7595F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.1F)).m_171555_(false), PartPose.m_171423_(0.3211F, 7.8758F, -0.2283F, 0.2618F, 0.0F, 0.0F));
      rightToe2.m_171599_("rightSubToe2", CubeListBuilder.m_171558_().m_171514_(13, 56).m_171480_().m_171488_(-0.8368F, -0.957F, -1.7595F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.2F)).m_171555_(false), PartPose.m_171423_(-0.0112F, -0.139F, -1.2439F, 0.3927F, 0.0F, 0.0F));
      PartDefinition rightToe3 = lowerrightleg.m_171599_("rightToe3", CubeListBuilder.m_171558_().m_171514_(13, 56).m_171480_().m_171488_(-0.8368F, -0.957F, -1.7595F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.1F)).m_171555_(false), PartPose.m_171423_(1.3211F, 7.8758F, -0.2283F, 0.262F, -0.043F, -0.0076F));
      rightToe3.m_171599_("rightSubToe3", CubeListBuilder.m_171558_().m_171514_(13, 56).m_171480_().m_171488_(-0.8368F, -0.957F, -1.7595F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.2F)).m_171555_(false), PartPose.m_171423_(-0.0112F, -0.139F, -1.2439F, 0.3927F, 0.0F, 0.0F));
      lowerrightleg.m_171599_("righttalon", CubeListBuilder.m_171558_().m_171514_(10, 60).m_171480_().m_171488_(-0.6F, -0.6039F, -0.5317F, 1.2F, 1.2F, 2.8F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(0.0083F, 6.5021F, 2.1765F, -0.6109F, 0.0F, 0.0F));
      PartDefinition body = partdefinition.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(19, 32).m_171488_(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.1F)), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      PartDefinition tail = body.m_171599_("tail", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-2.75F, -3.0F, -1.25F, 5.5F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-0.0625F, 10.125F, 1.625F, -0.4363F, 0.0F, 0.0F));
      PartDefinition tail2 = tail.m_171599_("tail2", CubeListBuilder.m_171558_().m_171514_(10, 3).m_171488_(-2.5F, -2.5F, -0.8125F, 5.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 0.0F, 0.5625F));
      PartDefinition tail3 = tail2.m_171599_("tail3", CubeListBuilder.m_171558_().m_171514_(0, 15).m_171488_(-2.25F, -2.25F, 0.1875F, 4.5F, 4.5F, 5.5F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 0.0F, 3.75F, 0.0873F, 0.0F, 0.0F));
      PartDefinition tail4 = tail3.m_171599_("tail4", CubeListBuilder.m_171558_().m_171514_(13, 18).m_171488_(-2.0F, -2.0F, 0.4375F, 4.0F, 4.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 0.0F, 4.25F, 0.1745F, 0.0F, 0.0F));
      tail4.m_171599_("tail5", CubeListBuilder.m_171558_().m_171514_(39, 18).m_171488_(-1.75F, -1.75F, 0.25F, 3.5F, 3.5F, 7.5F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 0.0F, 6.9375F, 0.1309F, 0.0F, 0.0F));
      partdefinition.m_171599_("left_arm", CubeListBuilder.m_171558_().m_171514_(48, 48).m_171480_().m_171488_(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.1F)).m_171555_(false), PartPose.m_171419_(5.0F, 2.0F, 0.0F));
      partdefinition.m_171599_("right_arm", CubeListBuilder.m_171558_().m_171514_(48, 32).m_171488_(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.1F)), PartPose.m_171419_(-5.0F, 2.0F, 0.0F));
      partdefinition.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(32, 0).m_171488_(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.1F)), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   public void m_6973_(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      RendererHelper.resetModelToDefaultPivots(this);
      super.m_6973_(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
      float limbSpeed = 0.5F;
      if (entity.m_20142_()) {
         limbSpeed = 0.7F;
      }

      ModelPart var10000 = this.f_102813_;
      var10000.f_104203_ -= 0.1F;
      var10000 = this.f_102814_;
      var10000.f_104203_ -= 0.1F;
      this.tail.f_104204_ = (float)Math.sin((double)ageInTicks * 0.01) / 20.0F;
      this.tail.f_104203_ = (float)Math.sin((double)ageInTicks * 0.005) / 10.0F;
      this.tail2.f_104204_ = (float)Math.sin((double)ageInTicks * 0.09) / 20.0F;
      this.tail2.f_104203_ = (float)Math.sin((double)ageInTicks * 0.01) / 10.0F;
      this.tail3.f_104204_ = (float)Math.sin((double)ageInTicks * 0.1) / 20.0F;
      this.tail3.f_104203_ = (float)Math.sin((double)ageInTicks * 0.05) / 10.0F;
      if (entity.m_6047_()) {
         this.f_102813_.f_104202_ = 2.0F;
         this.f_102813_.f_104201_ = 11.0F;
         this.f_102814_.f_104202_ = 2.0F;
         this.f_102814_.f_104201_ = 11.0F;
         this.tail.f_104202_ = 2.0F;
         this.tail.f_104201_ = 10.0F;
      }

      this.m_7884_(entity, ageInTicks);
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      super.m_7695_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }

   public void renderFirstPersonArm(PoseStack stack, VertexConsumer vertex, int packedLight, int overlay, float red, float green, float blue, float alpha, HumanoidArm side, boolean isLeg) {
      float sideMod = side == HumanoidArm.RIGHT ? 1.0F : -1.0F;
      stack.m_85837_(-0.2 * (double)sideMod, -0.4, -0.4);
      stack.m_252781_(Axis.f_252529_.m_252977_(20.0F));
      if (isLeg) {
         stack.m_85837_(0.2 * (double)sideMod, 0.3, 0.2);
      }

      super.renderFirstPersonArm(stack, vertex, packedLight, overlay, red, green, blue, alpha, side, isLeg);
   }
}
