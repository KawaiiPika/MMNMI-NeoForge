package xyz.pixelatedw.mineminenomi.models.entities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.entities.WantedPosterPackageEntity;

public class WantedPosterPackageModel<T extends WantedPosterPackageEntity> extends EntityModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "wanted_poster_package"), "main");
   private final ModelPart posters;
   private final ModelPart packageModel;
   private final ModelPart parachute;
   private final ModelPart parachute_cloth8;
   private final ModelPart parachute_cloth2;
   private final ModelPart parachute_cloth7;
   private final ModelPart parachute_cord1;
   private final ModelPart parachute_cloth3;
   private final ModelPart parachute_cloth6;
   private final ModelPart parachute_cord2;
   private final ModelPart parachute_cord3;
   private final ModelPart parachute_cloth1;
   private final ModelPart parachute_cloth4;
   private final ModelPart parachute_cord4;
   private final ModelPart parachute_cloth5;

   public WantedPosterPackageModel(EntityRendererProvider.Context ctx) {
      ModelPart root = ctx.m_174023_(LAYER_LOCATION);
      this.posters = root.m_171324_("posters");
      this.packageModel = root.m_171324_("packageModel");
      this.parachute = root.m_171324_("parachute");
      this.parachute_cloth8 = this.parachute.m_171324_("parachute_cloth8");
      this.parachute_cloth2 = this.parachute.m_171324_("parachute_cloth2");
      this.parachute_cloth7 = this.parachute.m_171324_("parachute_cloth7");
      this.parachute_cord1 = this.parachute.m_171324_("parachute_cord1");
      this.parachute_cloth3 = this.parachute.m_171324_("parachute_cloth3");
      this.parachute_cloth6 = this.parachute.m_171324_("parachute_cloth6");
      this.parachute_cord2 = this.parachute.m_171324_("parachute_cord2");
      this.parachute_cord3 = this.parachute.m_171324_("parachute_cord3");
      this.parachute_cloth1 = this.parachute.m_171324_("parachute_cloth1");
      this.parachute_cloth4 = this.parachute.m_171324_("parachute_cloth4");
      this.parachute_cord4 = this.parachute.m_171324_("parachute_cord4");
      this.parachute_cloth5 = this.parachute.m_171324_("parachute_cloth5");
   }

   public static LayerDefinition createLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      partdefinition.m_171599_("posters", CubeListBuilder.m_171558_().m_171514_(-16, 0).m_171488_(-6.0F, 0.0F, -8.0F, 12.0F, 0.0F, 16.0F, new CubeDeformation(0.0F)).m_171514_(-16, 0).m_171488_(-6.0F, -0.5F, -8.0F, 12.0F, 0.0F, 16.0F, new CubeDeformation(0.0F)).m_171514_(-16, 0).m_171488_(-6.0F, -1.0F, -8.0F, 12.0F, 0.0F, 16.0F, new CubeDeformation(0.0F)).m_171514_(-16, 0).m_171488_(-6.0F, -1.5F, -8.0F, 12.0F, 0.0F, 16.0F, new CubeDeformation(0.0F)).m_171514_(-16, 0).m_171488_(-6.0F, -2.0F, -8.0F, 12.0F, 0.0F, 16.0F, new CubeDeformation(0.0F)).m_171514_(-16, 0).m_171488_(-6.0F, -2.5F, -8.0F, 12.0F, 0.0F, 16.0F, new CubeDeformation(0.0F)).m_171514_(-16, 0).m_171488_(-6.0F, -3.0F, -8.0F, 12.0F, 0.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 24.0F, 0.0F));
      partdefinition.m_171599_("packageModel", CubeListBuilder.m_171558_().m_171514_(0, 20).m_171488_(-7.0F, -3.0F, 4.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).m_171514_(0, 20).m_171488_(-7.0F, -3.0F, -5.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).m_171514_(0, 20).m_171488_(6.0F, -3.0F, 4.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).m_171514_(0, 20).m_171488_(6.0F, -3.0F, -5.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).m_171514_(30, 0).m_171488_(-7.0F, -4.0F, -5.0F, 14.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).m_171514_(30, 0).m_171488_(-7.0F, -4.0F, 4.0F, 14.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).m_171514_(30, 0).m_171488_(-7.0F, 0.0F, 4.0F, 14.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).m_171514_(7, 0).m_171488_(-0.5F, -4.0F, -9.0F, 1.0F, 1.0F, 18.0F, new CubeDeformation(0.0F)).m_171514_(7, 0).m_171488_(-0.5F, 0.0F, -9.0F, 1.0F, 1.0F, 18.0F, new CubeDeformation(0.0F)).m_171514_(0, 20).m_171488_(-0.5F, -3.0F, -9.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).m_171514_(0, 20).m_171488_(-0.5F, -3.0F, 8.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).m_171514_(30, 0).m_171488_(-7.0F, 0.0F, -5.0F, 14.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 24.0F, 0.0F));
      PartDefinition parachute = partdefinition.m_171599_("parachute", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 19.0F, 0.0F));
      parachute.m_171599_("parachute_cloth8", CubeListBuilder.m_171558_().m_171514_(4, 30).m_171488_(-5.0F, -11.5F, -8.75F, 10.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 0.0F, 0.0F, 0.2618F, 5.4978F, 0.0F));
      parachute.m_171599_("parachute_cloth2", CubeListBuilder.m_171558_().m_171514_(4, 30).m_171488_(-5.0F, -11.5F, -8.75F, 10.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 0.0F, 0.0F, 0.2618F, 0.7854F, 0.0F));
      parachute.m_171599_("parachute_cloth7", CubeListBuilder.m_171558_().m_171514_(4, 29).m_171488_(-4.0F, -11.5F, -9.0F, 8.0F, 1.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 0.0F, 0.0F, 0.2618F, 4.7124F, 0.0F));
      parachute.m_171599_("parachute_cord1", CubeListBuilder.m_171558_().m_171514_(0, 30).m_171488_(0.0F, -11.0F, -1.0F, 1.0F, 12.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 0.0F, 0.0F, 0.4363F, 0.0F, 0.0F));
      parachute.m_171599_("parachute_cloth3", CubeListBuilder.m_171558_().m_171514_(4, 29).m_171488_(-4.0F, -19.5F, -5.5F, 8.0F, 1.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-1.3102F, 8.6333F, 0.0F, 0.2618F, 1.5708F, 0.0F));
      parachute.m_171599_("parachute_cloth6", CubeListBuilder.m_171558_().m_171514_(4, 30).m_171488_(-5.0F, -11.5F, -8.5F, 10.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 0.0F, 0.0F, 0.2618F, 3.927F, 0.0F));
      parachute.m_171599_("parachute_cord2", CubeListBuilder.m_171558_().m_171514_(0, 30).m_171488_(0.0F, -11.0F, -1.0F, 1.0F, 12.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 0.0F, 0.0F, 0.4363F, 3.1416F, 0.0F));
      parachute.m_171599_("parachute_cord3", CubeListBuilder.m_171558_().m_171514_(0, 30).m_171488_(0.0F, -11.0F, -1.0F, 1.0F, 12.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 0.0F, 0.0F, 0.4363F, 1.5708F, 0.0F));
      parachute.m_171599_("parachute_cloth1", CubeListBuilder.m_171558_().m_171514_(4, 29).m_171488_(-4.0F, -11.5F, -9.0F, 8.0F, 1.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 0.0F, 0.0F, 0.2618F, 0.0F, 0.0F));
      parachute.m_171599_("parachute_cloth4", CubeListBuilder.m_171558_().m_171514_(4, 30).m_171488_(-5.0F, -11.5F, -8.5F, 10.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 0.0F, 0.0F, 0.2618F, 2.3562F, 0.0F));
      parachute.m_171599_("parachute_cord4", CubeListBuilder.m_171558_().m_171514_(0, 30).m_171488_(0.0F, -11.0F, -1.0F, 1.0F, 12.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 0.0F, 0.0F, 0.4363F, -1.5708F, 0.0F));
      parachute.m_171599_("parachute_cloth5", CubeListBuilder.m_171558_().m_171514_(4, 29).m_171488_(-4.0F, -11.5F, -9.0F, 8.0F, 1.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 0.0F, 0.0F, 0.2618F, 3.1416F, 0.0F));
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.parachute.f_104207_ = true;
      if (entity.m_20096_()) {
         this.parachute.f_104207_ = false;
      }

   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.posters.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.packageModel.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.parachute.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }
}
