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

public class FluffyCapeModel<T extends LivingEntity> extends HumanoidArmorModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "fluffy_cape"), "main");
   private final ModelPart cape;
   private final ModelPart capeback;
   private final ModelPart rightSleeve;
   private final ModelPart leftSleeve;
   private final ModelPart capeback3;
   private final ModelPart capeback2;
   private final ModelPart rightSholderPad;
   private final ModelPart leftSholderPad;
   private final ModelPart neck;

   public FluffyCapeModel(ModelPart root) {
      super(root);
      this.cape = root.m_171324_("cape");
      this.capeback = this.cape.m_171324_("capeback");
      this.rightSleeve = this.capeback.m_171324_("rightSleeve");
      this.leftSleeve = this.capeback.m_171324_("leftSleeve");
      this.capeback3 = this.capeback.m_171324_("capeback3");
      this.capeback2 = this.capeback.m_171324_("capeback2");
      this.rightSholderPad = this.capeback.m_171324_("rightSholderPad");
      this.leftSholderPad = this.capeback.m_171324_("leftSholderPad");
      this.neck = this.capeback.m_171324_("neck");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = HumanoidModel.m_170681_(new CubeDeformation(0.0F), 0.0F);
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition cape = partdefinition.m_171599_("cape", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, -1.0F, 0.0F));
      PartDefinition capeback = cape.m_171599_("capeback", CubeListBuilder.m_171558_().m_171514_(0, 25).m_171488_(-10.0F, -3.25F, -0.25F, 21.0F, 22.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-0.5F, 3.25F, 1.25F, 0.0436F, 0.0F, 0.0F));
      capeback.m_171599_("rightSleeve", CubeListBuilder.m_171558_().m_171514_(0, 50).m_171488_(-6.1F, 0.0F, -2.75F, 4.0F, 16.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(-4.9F, -3.0F, 0.0F));
      capeback.m_171599_("leftSleeve", CubeListBuilder.m_171558_().m_171514_(0, 50).m_171488_(0.9F, 0.0F, -2.75F, 4.0F, 16.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(7.1F, -3.0F, 0.0F));
      PartDefinition capeback3 = capeback.m_171599_("capeback3", CubeListBuilder.m_171558_(), PartPose.m_171423_(0.5F, 12.1287F, 3.3518F, -0.0436F, 0.0F, 0.0F));
      capeback3.m_171599_("capeback2_r1", CubeListBuilder.m_171558_().m_171514_(45, 47).m_171488_(-10.5F, -2.25F, 1.75F, 21.0F, 12.0F, 3.0F, new CubeDeformation(-0.02F)), PartPose.m_171423_(0.0F, -3.1287F, -3.8518F, 0.1745F, 0.0F, 0.0F));
      PartDefinition capeback2 = capeback.m_171599_("capeback2", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      capeback2.m_171599_("capeback2_r2", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-10.5F, -11.25F, 1.75F, 21.0F, 21.0F, 4.0F, new CubeDeformation(-0.01F)), PartPose.m_171423_(0.5F, 8.0F, 1.0F, 0.1745F, 0.0F, 0.0F));
      capeback.m_171599_("rightSholderPad", CubeListBuilder.m_171558_().m_171514_(22, 0).m_171488_(-2.0F, -2.6F, -1.55F, 6.0F, 3.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(6.9F, -2.75F, -2.25F, -0.192F, 0.0F, 0.0F));
      capeback.m_171599_("leftSholderPad", CubeListBuilder.m_171558_().m_171514_(22, 8).m_171488_(-16.8F, -1.6F, -1.3F, 6.0F, 3.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(6.9F, -3.75F, -2.25F, -0.192F, 0.0F, 0.0F));
      capeback.m_171599_("neck", CubeListBuilder.m_171558_().m_171514_(22, 8).m_171488_(-16.8F, -1.6F, 3.7F, 10.0F, 3.0F, 3.0F, new CubeDeformation(-0.01F)), PartPose.m_171423_(11.9F, -3.75F, -2.25F, -0.192F, 0.0F, 0.0F));
      return LayerDefinition.m_171565_(meshdefinition, 128, 128);
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

      this.capeback.f_104203_ = (float)Math.toRadians(angle);
      this.rightSleeve.f_104203_ = (float)Math.toRadians(angle - (double)(!isMoving ? 0 : 50));
      this.leftSleeve.f_104203_ = (float)Math.toRadians(angle - (double)(!isMoving ? 0 : 50));
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.cape.m_104315_(this.f_102810_);
      this.cape.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }
}
