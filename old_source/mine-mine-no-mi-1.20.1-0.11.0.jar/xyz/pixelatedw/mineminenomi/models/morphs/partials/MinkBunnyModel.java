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
import net.minecraft.world.entity.player.Player;

public class MinkBunnyModel<T extends LivingEntity> extends HumanoidModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "mink_bunny_partial"), "main");
   private final ModelPart ears;
   private final ModelPart rightEar1;
   private final ModelPart rightEar2;
   private final ModelPart leftEar1;
   private final ModelPart leftEar2;
   private final ModelPart tail;

   public MinkBunnyModel(ModelPart root) {
      super(root);
      this.ears = root.m_171324_("ears");
      this.rightEar1 = this.ears.m_171324_("rightEar1");
      this.rightEar2 = this.rightEar1.m_171324_("rightEar2");
      this.leftEar1 = this.ears.m_171324_("leftEar1");
      this.leftEar2 = this.leftEar1.m_171324_("leftEar2");
      this.tail = root.m_171324_("tail");
   }

   public static LayerDefinition createLayer() {
      MeshDefinition meshdefinition = HumanoidModel.m_170681_(new CubeDeformation(0.0F), 0.0F);
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition ears = partdefinition.m_171599_("ears", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 8.0F, 0.0F));
      PartDefinition rightEar1 = ears.m_171599_("rightEar1", CubeListBuilder.m_171558_().m_171514_(0, 25).m_171488_(-2.0F, -5.0F, 0.0F, 3.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-2.0F, -8.0F, 0.0F, 0.0F, -0.2182F, -0.2182F));
      rightEar1.m_171599_("rightEar2", CubeListBuilder.m_171558_().m_171514_(0, 16).m_171488_(-1.4685F, -5.2761F, -0.1179F, 3.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-0.5315F, -4.4739F, 0.1179F, 0.2967F, 0.0F, 0.0F));
      PartDefinition leftEar1 = ears.m_171599_("leftEar1", CubeListBuilder.m_171558_().m_171514_(0, 25).m_171480_().m_171488_(-1.0F, -5.0F, 0.0F, 3.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(2.0F, -8.0F, 0.0F, 0.0F, 0.2182F, 0.2182F));
      leftEar1.m_171599_("leftEar2", CubeListBuilder.m_171558_().m_171514_(0, 16).m_171480_().m_171488_(-1.5315F, -5.2761F, -0.1179F, 3.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(0.5315F, -4.4739F, 0.1179F, 0.2967F, 0.0F, 0.0F));
      partdefinition.m_171599_("tail", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-3.0F, 7.0F, 2.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 3.0F, 1.0F));
      return LayerDefinition.m_171565_(meshdefinition, 32, 32);
   }

   public void m_6973_(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.tail.f_104204_ = (float)(Math.sin((double)ageInTicks * 0.06) / (double)6.0F);
      this.tail.f_104203_ = -this.f_102810_.f_104203_ + (float)(Math.sin((double)ageInTicks * 0.02) / (double)10.0F);
      if (entity.m_6047_()) {
         this.tail.f_104203_ = -0.2F;
         this.tail.f_104201_ = 9.5F;
      }

      if (entity instanceof Player player) {
         if (player.m_150110_().f_35935_) {
            double posXDiff = Math.abs(entity.f_19854_ - entity.m_20185_());
            double posZDiff = Math.abs(entity.f_19856_ - entity.m_20189_());
            if (posXDiff >= 0.2 || posZDiff >= 0.2) {
               this.tail.f_104203_ = -0.2F;
            }

            return;
         }
      }

      this.tail.f_104201_ = 10.0F;
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.ears.m_104315_(this.f_102808_);
      this.tail.m_104315_(this.f_102810_);
      this.ears.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.tail.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }
}
