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
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;

public class CabajiScarfModel<T extends LivingEntity> extends HumanoidArmorModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "cabaji_scarf"), "main");
   private final ModelPart scarfbase;
   private final ModelPart scarftail;
   private final ModelPart scarftail2;

   public CabajiScarfModel(ModelPart root) {
      super(root);
      this.scarfbase = root.m_171324_("scarfbase");
      this.scarftail = this.scarfbase.m_171324_("scarftail");
      this.scarftail2 = this.scarftail.m_171324_("scarftail2");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = HumanoidModel.m_170681_(new CubeDeformation(0.0F), 0.0F);
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition scarfbase = partdefinition.m_171599_("scarfbase", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-5.0F, -3.0F, -5.0F, 10.0F, 4.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      PartDefinition scarftail = scarfbase.m_171599_("scarftail", CubeListBuilder.m_171558_().m_171514_(14, 14).m_171488_(-3.0F, 0.0F, 0.0F, 6.0F, 11.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(1.75F, -0.75F, 3.75F));
      scarftail.m_171599_("scarftail2", CubeListBuilder.m_171558_().m_171514_(0, 14).m_171488_(-3.0F, 0.0F, 0.0F, 6.0F, 11.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 11.0F, 0.0F));
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   public void m_6973_(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      double dist = entity.m_20275_(entity.f_19854_, entity.f_19855_, entity.f_19856_);
      if (dist > (double)0.0F && dist <= 0.02) {
         dist += 0.02;
      }

      double angle = Mth.m_14008_(dist * (double)1000.0F - (double)1.0F, (double)0.0F, (double)70.0F);
      boolean isMoving = dist > 0.02;
      if (isMoving) {
         angle += (double)(Mth.m_14031_((float)Mth.m_14139_(angle, (double)entity.f_19867_, (double)entity.f_19787_)) * 6.0F);
      }

      if (entity.m_146909_() >= 45.0F) {
         float headRotation = 90.0F - entity.m_146909_();
         angle += (double)(headRotation - 45.0F);
      } else if (entity.m_146909_() < -10.0F) {
         float headRotation = 90.0F - entity.m_146909_();
         angle += (double)(headRotation - 85.0F);
      }

      this.scarftail.f_104203_ = (float)Math.toRadians(angle);
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.scarfbase.m_104315_(this.f_102808_);
      this.scarfbase.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }
}
