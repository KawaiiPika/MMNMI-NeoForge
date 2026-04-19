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
import xyz.pixelatedw.mineminenomi.entities.mobs.pirates.arlongpirates.ArlongEntity;

public class FishmanSawsharkModel<T extends LivingEntity> extends HumanoidModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "fishman_sawshark"), "main");
   private final ModelPart nose;
   private final ModelPart nose2;
   private final ModelPart backBlade;

   public FishmanSawsharkModel(ModelPart root) {
      super(root);
      this.nose = this.f_102808_.m_171324_("nose");
      this.nose2 = this.nose.m_171324_("nose2");
      this.backBlade = this.f_102810_.m_171324_("backBlade");
   }

   public static LayerDefinition createLayer() {
      MeshDefinition meshdefinition = HumanoidModel.m_170681_(CubeDeformation.f_171458_, 0.0F);
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition head = partdefinition.m_171599_("head", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      PartDefinition nose = head.m_171599_("nose", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-2.0F, -0.5F, -2.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, -2.75F, -3.0F, 0.0F, -0.7854F, 0.0F));
      nose.m_171599_("nose2", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171480_().m_171488_(-2.0F, -0.5F, -2.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.001F)).m_171555_(false).m_171514_(0, 0).m_171488_(-3.0F, -0.5F, -3.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(-1.0F, 0.0F, -1.0F));
      PartDefinition body = partdefinition.m_171599_("body", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      body.m_171599_("backBlade", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-0.5F, -2.75F, 0.25F, 1.0F, 7.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 0.0F, 0.0F, -0.7854F, 0.0F, 0.0F));
      return LayerDefinition.m_171565_(meshdefinition, 32, 16);
   }

   public void m_6973_(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      if (entity instanceof ArlongEntity) {
         this.nose.f_104201_ = -3.75F;
      }

   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.f_102808_.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.f_102810_.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }
}
