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

public class MH5GasMaskModel<T extends LivingEntity> extends HumanoidArmorModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "mh5_gas_mask"), "main");
   private final ModelPart mask;
   private final ModelPart leftEar;
   private final ModelPart leftEarConnector;
   private final ModelPart eyes;
   private final ModelPart mouthFilter;
   private final ModelPart rightEar;
   private final ModelPart rightEarConnector;

   public MH5GasMaskModel(ModelPart root) {
      super(root);
      this.mask = root.m_171324_("mask");
      this.leftEar = this.mask.m_171324_("leftEar");
      this.leftEarConnector = this.leftEar.m_171324_("leftEarConnector");
      this.eyes = this.mask.m_171324_("eyes");
      this.mouthFilter = this.mask.m_171324_("mouthFilter");
      this.rightEar = this.mask.m_171324_("rightEar");
      this.rightEarConnector = this.rightEar.m_171324_("rightEarConnector");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = HumanoidModel.m_170681_(new CubeDeformation(0.0F), 0.0F);
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition mask = partdefinition.m_171599_("mask", CubeListBuilder.m_171558_().m_171514_(0, 12).m_171488_(-1.5F, -3.0F, -4.5F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.5F)), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      PartDefinition leftEar = mask.m_171599_("leftEar", CubeListBuilder.m_171558_().m_171514_(17, 10).m_171488_(2.8F, -5.6F, -1.8F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.5F)), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      leftEar.m_171599_("leftEarConnector", CubeListBuilder.m_171558_().m_171514_(8, 10).m_171488_(3.5F, -5.4F, -4.4F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.5F)), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      mask.m_171599_("eyes", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-3.0F, -5.9F, -5.0F, 6.0F, 3.0F, 2.0F, new CubeDeformation(0.5F)), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      mask.m_171599_("mouthFilter", CubeListBuilder.m_171558_().m_171514_(0, 8).m_171488_(-1.0F, -2.5F, -5.4F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.5F)), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      PartDefinition rightEar = mask.m_171599_("rightEar", CubeListBuilder.m_171558_().m_171514_(17, 10).m_171488_(-5.2F, -5.6F, -1.8F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.5F)), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      rightEar.m_171599_("rightEarConnector", CubeListBuilder.m_171558_().m_171514_(8, 10).m_171488_(-4.5F, -5.4F, -4.4F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.5F)), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      return LayerDefinition.m_171565_(meshdefinition, 32, 16);
   }

   public void m_6973_(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.mask.m_104315_(this.f_102808_);
      this.mask.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }
}
