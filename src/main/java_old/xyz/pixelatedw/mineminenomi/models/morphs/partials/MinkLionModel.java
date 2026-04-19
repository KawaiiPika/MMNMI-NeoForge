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

public class MinkLionModel<T extends LivingEntity> extends HumanoidModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "mink_lion_partial"), "main");
   private final ModelPart mane;
   private final ModelPart mane2;
   private final ModelPart tailBase;
   private final ModelPart tail;
   private final ModelPart tail2;
   private final ModelPart tailTip;

   public MinkLionModel(ModelPart root) {
      super(root);
      this.mane = root.m_171324_("mane");
      this.mane2 = this.mane.m_171324_("mane2");
      this.tailBase = root.m_171324_("tailBase");
      this.tail = this.tailBase.m_171324_("tail");
      this.tail2 = this.tail.m_171324_("tail2");
      this.tailTip = this.tail2.m_171324_("tailTip");
   }

   public static LayerDefinition createLayer() {
      MeshDefinition meshdefinition = HumanoidModel.m_170681_(new CubeDeformation(0.0F), 0.0F);
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition mane = partdefinition.m_171599_("mane", CubeListBuilder.m_171558_().m_171514_(0, 15).m_171488_(-6.5F, -10.5F, -2.0F, 13.0F, 13.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 6.5F, 1.0F));
      mane.m_171599_("mane2", CubeListBuilder.m_171558_().m_171514_(0, 15).m_171488_(-10.0F, -9.0F, -2.0F, 11.0F, 11.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(4.5F, -0.5F, -1.0F));
      PartDefinition tailBase = partdefinition.m_171599_("tailBase", CubeListBuilder.m_171558_(), PartPose.m_171423_(0.0F, 11.0F, 1.0F, 0.3054F, 0.0F, 0.0F));
      PartDefinition tail = tailBase.m_171599_("tail", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-0.5F, -0.5F, -1.5F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 10.5953F, 3.1299F, 0.3054F, 0.0F, 0.0F));
      PartDefinition tail2 = tail.m_171599_("tail2", CubeListBuilder.m_171558_().m_171514_(0, 6).m_171488_(-0.5F, -0.5F, -2.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(-0.01F)), PartPose.m_171423_(0.0F, 1.2826F, 2.6897F, -0.7417F, 0.0F, 0.0F));
      tail2.m_171599_("tailTip", CubeListBuilder.m_171558_().m_171514_(11, 0).m_171488_(-1.0F, -1.0F, -1.5F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 0.2532F, 3.4293F, -0.1309F, 0.0F, 0.0F));
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   public void m_6973_(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.tail.f_104204_ = (float)(Math.sin((double)ageInTicks * 0.06) / (double)10.0F);
      this.tail.f_104203_ = (float)((double)(-this.f_102810_.f_104203_) + Math.sin((double)ageInTicks * 0.02) / (double)5.0F);
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
      this.mane.m_104315_(this.f_102808_);
      this.tailBase.m_104315_(this.f_102810_);
      this.mane.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.tailBase.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }
}
