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

public class FishmanRayModel<T extends LivingEntity> extends HumanoidModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "fishman_ray"), "main");
   private final ModelPart rightGills;
   private final ModelPart leftGills;
   private final ModelPart leftBlade;
   private final ModelPart rightBlade;

   public FishmanRayModel(ModelPart root) {
      super(root);
      this.rightGills = this.f_102808_.m_171324_("rightGills");
      this.leftGills = this.f_102808_.m_171324_("leftGills");
      this.leftBlade = this.f_102812_.m_171324_("leftBlade");
      this.rightBlade = this.f_102811_.m_171324_("rightBlade");
   }

   public static LayerDefinition createLayer() {
      MeshDefinition meshdefinition = HumanoidModel.m_170681_(CubeDeformation.f_171458_, 0.0F);
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition head = partdefinition.m_171599_("head", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      head.m_171599_("rightGills", CubeListBuilder.m_171558_().m_171514_(0, -2).m_171488_(-0.001F, -1.5F, -2.0F, 0.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-4.0F, -2.2F, -1.0F, 0.1745F, 0.0F, 0.0F));
      head.m_171599_("leftGills", CubeListBuilder.m_171558_().m_171514_(0, -2).m_171480_().m_171488_(0.001F, -1.5F, -2.0F, 0.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(4.0F, -2.2F, -1.0F, 0.1745F, 0.0F, 0.0F));
      PartDefinition leftArm = partdefinition.m_171599_("left_arm", CubeListBuilder.m_171558_(), PartPose.m_171419_(5.0F, 2.0F, 0.0F));
      leftArm.m_171599_("leftBlade", CubeListBuilder.m_171558_().m_171514_(2, 2).m_171480_().m_171488_(-0.5F, -2.5F, -4.0F, 1.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(1.0F, 4.15F, 3.05F, -0.7854F, 0.0F, 0.0F));
      PartDefinition rightArm = partdefinition.m_171599_("right_arm", CubeListBuilder.m_171558_(), PartPose.m_171419_(-5.0F, 2.0F, 0.0F));
      rightArm.m_171599_("rightBlade", CubeListBuilder.m_171558_().m_171514_(2, 2).m_171488_(-0.5F, -2.5F, -4.0F, 1.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-1.0F, 4.15F, 3.05F, -0.7854F, 0.0F, 0.0F));
      return LayerDefinition.m_171565_(meshdefinition, 16, 16);
   }

   public void m_6973_(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.f_102808_.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.f_102812_.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.f_102811_.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }
}
