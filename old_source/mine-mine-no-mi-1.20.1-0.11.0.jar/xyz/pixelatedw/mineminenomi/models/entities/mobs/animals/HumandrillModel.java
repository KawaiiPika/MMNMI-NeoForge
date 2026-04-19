package xyz.pixelatedw.mineminenomi.models.entities.mobs.animals;

import com.google.common.collect.ImmutableSet;
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
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import xyz.pixelatedw.mineminenomi.api.IAgeableListModelExtension;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.HumandrillEntity;

public class HumandrillModel<T extends HumandrillEntity> extends HumanoidModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "humandrill"), "main");
   private final ModelPart snout;
   private final ModelPart forehead;
   private final ModelPart leftArmArmor;
   private final ModelPart leftArmArmorPlate;
   private final ModelPart lowerLeftArm;
   private final ModelPart rightArmArmor;
   private final ModelPart rightArmArmorPlate;
   private final ModelPart lowerRightArm;
   private final ModelPart lowerLeftLeg;
   private final ModelPart leftLegArmor;
   private final ModelPart leftLegArmorPlate;
   private final ModelPart leftFoot;
   private final ModelPart lowerRightLeg;
   private final ModelPart rightLegArmor;
   private final ModelPart rightLegArmorPlate;
   private final ModelPart rightFoot;

   public HumandrillModel(ModelPart root) {
      super(root);
      this.snout = this.f_102808_.m_171324_("snout");
      this.forehead = this.f_102808_.m_171324_("forehead");
      this.leftArmArmor = this.f_102812_.m_171324_("leftArmArmor");
      this.leftArmArmorPlate = this.leftArmArmor.m_171324_("leftArmArmorPlate");
      this.lowerLeftArm = this.f_102812_.m_171324_("lowerLeftArm");
      this.rightArmArmor = this.f_102811_.m_171324_("rightArmArmor");
      this.rightArmArmorPlate = this.rightArmArmor.m_171324_("rightArmArmorPlate");
      this.lowerRightArm = this.f_102811_.m_171324_("lowerRightArm");
      this.lowerLeftLeg = this.f_102814_.m_171324_("lowerLeftLeg");
      this.leftLegArmor = this.lowerLeftLeg.m_171324_("leftLegArmor");
      this.leftLegArmorPlate = this.leftLegArmor.m_171324_("leftLegArmorPlate");
      this.leftFoot = this.lowerLeftLeg.m_171324_("leftFoot");
      this.lowerRightLeg = this.f_102813_.m_171324_("lowerRightLeg");
      this.rightLegArmor = this.lowerRightLeg.m_171324_("rightLegArmor");
      this.rightLegArmorPlate = this.rightLegArmor.m_171324_("rightLegArmorPlate");
      this.rightFoot = this.lowerRightLeg.m_171324_("rightFoot");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = HumanoidModel.m_170681_(new CubeDeformation(0.0F), 0.0F);
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition head = partdefinition.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(16, 0).m_171488_(-3.0625F, -4.125F, -6.4375F, 6.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, -9.0F, 0.0F));
      head.m_171599_("snout", CubeListBuilder.m_171558_().m_171514_(0, 16).m_171488_(-2.5F, -2.0F, -3.0F, 5.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, -1.8376F, -3.9299F, 0.1309F, 0.0F, 0.0F));
      head.m_171599_("forehead", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-1.5F, -5.25F, -2.0313F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).m_171514_(0, 7).m_171488_(-2.5F, -3.25F, -2.4688F, 5.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, -3.4375F, -3.5938F, -0.1309F, 0.0F, 0.0F));
      PartDefinition leftArm = partdefinition.m_171599_("left_arm", CubeListBuilder.m_171558_().m_171514_(96, 4).m_171480_().m_171488_(-0.7187F, -4.208F, -3.9572F, 6.0F, 14.0F, 8.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171419_(9.0F, -6.0F, 2.25F));
      PartDefinition leftArmArmor = leftArm.m_171599_("leftArmArmor", CubeListBuilder.m_171558_().m_171514_(75, 20).m_171480_().m_171488_(-3.0F, -1.5F, -4.0F, 6.0F, 3.0F, 8.0F, new CubeDeformation(0.3F)).m_171555_(false), PartPose.m_171419_(2.2813F, 4.3159F, 0.0567F));
      leftArmArmor.m_171599_("leftArmArmorPlate", CubeListBuilder.m_171558_().m_171514_(85, 35).m_171480_().m_171488_(-0.5F, -6.0F, -5.0F, 1.0F, 12.0F, 10.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(3.75F, -0.4659F, -0.1552F, 0.3054F, 0.0F, 0.0F));
      leftArm.m_171599_("lowerLeftArm", CubeListBuilder.m_171558_().m_171514_(99, 27).m_171480_().m_171488_(-3.0F, -1.2157F, -2.8679F, 5.0F, 10.0F, 6.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171419_(2.7188F, 9.1563F, -0.25F));
      PartDefinition rightArm = partdefinition.m_171599_("right_arm", CubeListBuilder.m_171558_().m_171514_(96, 4).m_171488_(-5.2813F, -4.208F, -3.9572F, 6.0F, 14.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(-9.0F, -6.0F, 2.25F));
      PartDefinition rightArmArmor = rightArm.m_171599_("rightArmArmor", CubeListBuilder.m_171558_().m_171514_(75, 20).m_171488_(-3.0F, -1.5F, -4.0F, 6.0F, 3.0F, 8.0F, new CubeDeformation(0.3F)), PartPose.m_171419_(-2.2813F, 4.3159F, 0.0567F));
      rightArmArmor.m_171599_("rightArmArmorPlate", CubeListBuilder.m_171558_().m_171514_(85, 35).m_171488_(-0.5F, -6.0F, -5.0F, 1.0F, 12.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-3.75F, -0.4659F, -0.1552F, 0.3054F, 0.0F, 0.0F));
      rightArm.m_171599_("lowerRightArm", CubeListBuilder.m_171558_().m_171514_(99, 27).m_171488_(-2.0F, -1.2157F, -2.8679F, 5.0F, 10.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(-2.7188F, 9.1563F, -0.25F));
      PartDefinition leftLeg = partdefinition.m_171599_("left_leg", CubeListBuilder.m_171558_().m_171514_(0, 33).m_171480_().m_171488_(-3.125F, 0.3775F, -2.8619F, 6.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(4.0F, 4.0F, 1.5F, -0.0436F, 0.0F, 0.0F));
      PartDefinition lowerLeftLeg = leftLeg.m_171599_("lowerLeftLeg", CubeListBuilder.m_171558_().m_171514_(0, 47).m_171480_().m_171488_(-2.0F, 0.0153F, -2.0871F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(-0.125F, 7.0907F, -0.2903F, 0.0873F, 0.0F, 0.0F));
      PartDefinition leftLegArmor = lowerLeftLeg.m_171599_("leftLegArmor", CubeListBuilder.m_171558_().m_171514_(12, 27).m_171480_().m_171488_(-2.0F, -1.0F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.3F)).m_171555_(false), PartPose.m_171419_(0.0F, 4.3278F, -0.0871F));
      leftLegArmor.m_171599_("leftLegArmorPlate", CubeListBuilder.m_171558_().m_171514_(1, 28).m_171480_().m_171488_(-2.0F, -2.0F, -0.5F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(-6.0E-4F, -0.0054F, -2.8125F, 0.0F, 0.0F, -0.0873F));
      lowerLeftLeg.m_171599_("leftFoot", CubeListBuilder.m_171558_().m_171514_(17, 44).m_171480_().m_171488_(-2.5F, -0.9618F, -4.3742F, 5.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(-0.0625F, 11.7406F, 0.0398F, -0.0436F, 0.0F, 0.0F));
      PartDefinition rightLeg = partdefinition.m_171599_("right_leg", CubeListBuilder.m_171558_().m_171514_(0, 33).m_171488_(-2.875F, 0.3775F, -2.8619F, 6.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-4.0F, 4.0F, 1.5F, -0.0436F, 0.0F, 0.0F));
      PartDefinition lowerRightLeg = rightLeg.m_171599_("lowerRightLeg", CubeListBuilder.m_171558_().m_171514_(0, 47).m_171488_(-2.0F, 0.0153F, -2.0871F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.125F, 7.0907F, -0.2903F, 0.0873F, 0.0F, 0.0F));
      PartDefinition rightLegArmor = lowerRightLeg.m_171599_("rightLegArmor", CubeListBuilder.m_171558_().m_171514_(12, 27).m_171488_(-2.0F, -1.0F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.3F)), PartPose.m_171419_(0.0F, 4.3278F, -0.0871F));
      rightLegArmor.m_171599_("rightLegArmorPlate", CubeListBuilder.m_171558_().m_171514_(1, 28).m_171488_(-2.0F, -2.0F, -0.5F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(6.0E-4F, -0.0054F, -2.8125F, 0.0F, 0.0F, 0.0873F));
      lowerRightLeg.m_171599_("rightFoot", CubeListBuilder.m_171558_().m_171514_(17, 44).m_171488_(-2.5F, -0.9618F, -4.3742F, 5.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0625F, 11.7406F, 0.0398F, -0.0436F, 0.0F, 0.0F));
      partdefinition.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(30, 26).m_171488_(-9.0F, -9.125F, -2.9375F, 17.0F, 9.0F, 8.0F, new CubeDeformation(0.0F)).m_171514_(28, 0).m_171488_(-8.0F, -7.6875F, -5.0F, 15.0F, 13.0F, 12.0F, new CubeDeformation(0.0F)).m_171514_(31, 44).m_171488_(-7.0F, 4.3125F, -4.0F, 13.0F, 3.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.5F, -2.0F, 1.0F, 0.0873F, 0.0F, 0.0F));
      return LayerDefinition.m_171565_(meshdefinition, 128, 64);
   }

   public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      ((IAgeableListModelExtension)this).getParts().forEach(ModelPart::m_233569_);
      this.f_102811_.f_104203_ = Mth.m_14089_(limbSwing * 0.6662F + (float)Math.PI) * 0.8F * limbSwingAmount * 0.5F / 1.0F;
      this.f_102812_.f_104203_ = Mth.m_14089_(limbSwing * 0.6662F) * 0.8F * limbSwingAmount * 0.5F / 1.0F;
      this.f_102813_.f_104203_ = Mth.m_14089_(limbSwing * 0.6662F) * 0.7F * limbSwingAmount / 1.0F;
      this.f_102814_.f_104203_ = Mth.m_14089_(limbSwing * 0.6662F + (float)Math.PI) * 0.7F * limbSwingAmount / 1.0F;
      if (!entity.m_21205_().m_41619_()) {
         ModelPart var10000 = this.f_102811_;
         var10000.f_104204_ -= -0.15F;
      }

      this.m_7884_(entity, ageInTicks);
      Animation.animationAngles(entity, this, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.f_102808_.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.f_102812_.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.f_102811_.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.f_102814_.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.f_102813_.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.f_102810_.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.leftArmArmor.f_104207_ = false;
      this.rightArmArmor.f_104207_ = false;
      this.leftLegArmor.f_104207_ = false;
      this.rightLegArmor.f_104207_ = false;
   }

   public ModelPart m_5585_() {
      return this.f_102808_;
   }

   protected ModelPart m_102851_(HumanoidArm side) {
      return side == HumanoidArm.LEFT ? this.f_102812_ : this.f_102811_;
   }

   public void m_6002_(HumanoidArm side, PoseStack matrixStack) {
      this.m_102851_(side).m_104299_(matrixStack);
      matrixStack.m_85837_(side == HumanoidArm.RIGHT ? -0.1 : 0.2, 0.4, -0.1);
   }

   protected Iterable<ModelPart> m_5607_() {
      return ImmutableSet.of(this.f_102808_, this.forehead);
   }

   protected Iterable<ModelPart> m_5608_() {
      return ImmutableSet.of(this.f_102810_, this.f_102812_, this.lowerLeftArm, this.f_102811_, this.lowerRightArm, this.f_102814_, new ModelPart[]{this.lowerLeftLeg, this.f_102813_, this.lowerRightLeg});
   }
}
