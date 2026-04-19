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

public class WideBrimHatModel<T extends LivingEntity> extends HumanoidArmorModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "wide_brim_hat"), "main");
   private final ModelPart wideBrimHat;
   private final ModelPart top;
   private final ModelPart leftSide;
   private final ModelPart rightSide;

   public WideBrimHatModel(ModelPart root) {
      super(root);
      this.wideBrimHat = root.m_171324_("wideBrimHat");
      this.top = this.wideBrimHat.m_171324_("top");
      this.leftSide = this.wideBrimHat.m_171324_("leftSide");
      this.rightSide = this.wideBrimHat.m_171324_("rightSide");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = HumanoidModel.m_170681_(new CubeDeformation(0.0F), 0.0F);
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition wideBrimHat = partdefinition.m_171599_("wideBrimHat", CubeListBuilder.m_171558_().m_171514_(0, 4).m_171488_(-8.0F, -6.5F, -8.0F, 16.0F, 1.0F, 16.0F, new CubeDeformation(0.0F)).m_171514_(0, 4).m_171488_(-8.0F, -7.25F, -8.0F, 16.0F, 1.0F, 16.0F, new CubeDeformation(-0.2F)), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      wideBrimHat.m_171599_("top", CubeListBuilder.m_171558_().m_171514_(0, 35).m_171488_(-5.0F, -3.0F, -5.0F, 10.0F, 3.0F, 10.0F, new CubeDeformation(0.0F)).m_171514_(29, 23).m_171488_(-5.0F, -0.5F, -5.5F, 10.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).m_171514_(29, 23).m_171488_(-5.0F, -0.5F, 4.5F, 10.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).m_171514_(32, 27).m_171488_(4.5F, -0.5F, -5.5F, 1.0F, 1.0F, 11.0F, new CubeDeformation(0.01F)).m_171514_(32, 27).m_171488_(-5.5F, -0.5F, -5.5F, 1.0F, 1.0F, 11.0F, new CubeDeformation(0.01F)).m_171514_(0, 23).m_171488_(-4.5F, -3.5F, -4.5F, 9.0F, 1.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, -7.0F, 0.0F));
      PartDefinition leftSide = wideBrimHat.m_171599_("leftSide", CubeListBuilder.m_171558_(), PartPose.m_171419_(-5.0F, -5.75F, -1.0F));
      PartDefinition rightSide = wideBrimHat.m_171599_("rightSide", CubeListBuilder.m_171558_(), PartPose.m_171419_(5.0F, -5.75F, -1.0F));
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   public void m_6973_(LivingEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.wideBrimHat.m_104315_(this.f_102808_);
      this.wideBrimHat.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }
}
