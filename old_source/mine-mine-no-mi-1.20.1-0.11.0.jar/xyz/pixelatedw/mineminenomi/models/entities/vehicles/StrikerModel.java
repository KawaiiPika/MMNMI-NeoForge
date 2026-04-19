package xyz.pixelatedw.mineminenomi.models.entities.vehicles;

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
import xyz.pixelatedw.mineminenomi.entities.vehicles.StrikerEntity;

public class StrikerModel extends EntityModel<StrikerEntity> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "striker"), "main");
   private final ModelPart body;
   private final ModelPart tip;
   private final ModelPart tip2;
   private final ModelPart engine;
   private final ModelPart sail;
   private final ModelPart pole;
   private final ModelPart cloth;

   public StrikerModel(EntityRendererProvider.Context ctx) {
      ModelPart root = ctx.m_174023_(LAYER_LOCATION);
      this.body = root.m_171324_("body");
      this.tip = this.body.m_171324_("tip");
      this.tip2 = this.tip.m_171324_("tip2");
      this.engine = this.body.m_171324_("engine");
      this.sail = root.m_171324_("sail");
      this.pole = this.sail.m_171324_("pole");
      this.cloth = this.pole.m_171324_("cloth");
   }

   public static LayerDefinition createLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition body = partdefinition.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(100, 11).m_171488_(10.1563F, -3.7984F, -2.5F, 5.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)).m_171514_(40, 78).m_171488_(2.9063F, -3.5109F, -4.0F, 9.0F, 7.0F, 8.0F, new CubeDeformation(0.0F)).m_171514_(85, 38).m_171488_(2.9063F, -3.1984F, -4.5F, 7.0F, 3.0F, 9.0F, new CubeDeformation(0.0F)).m_171514_(50, 38).m_171488_(-14.0938F, -0.1984F, -4.5F, 20.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).m_171514_(0, 37).m_171488_(-14.0938F, -0.1984F, 3.5F, 21.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).m_171514_(50, 0).m_171488_(-20.0938F, 0.8016F, -4.0F, 23.0F, 3.0F, 8.0F, new CubeDeformation(0.0F)).m_171514_(84, 33).m_171488_(-14.0938F, -3.1984F, 4.5F, 18.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).m_171514_(90, 110).m_171488_(-14.0938F, -3.1984F, -5.5F, 18.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0938F, 20.1984F, -6.0F, 0.0F, 1.5708F, 0.0F));
      PartDefinition tip = body.m_171599_("tip", CubeListBuilder.m_171558_().m_171514_(75, 100).m_171488_(-3.5F, -2.0F, -2.0F, 6.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(17.1641F, -2.6046F, 0.0F, 0.0F, 0.0F, -0.2182F));
      tip.m_171599_("tip2", CubeListBuilder.m_171558_().m_171514_(66, 78).m_171488_(-1.726F, -1.0764F, -1.5F, 6.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(2.1676F, -1.0169F, 0.0F, 0.0F, 0.0F, -0.2618F));
      body.m_171599_("engine", CubeListBuilder.m_171558_().m_171514_(27, 0).m_171488_(12.5625F, -2.5F, 0.5F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).m_171514_(100, 85).m_171488_(10.5625F, -2.85F, -0.5F, 4.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)).m_171514_(100, 50).m_171488_(9.5625F, -3.5F, -1.0F, 3.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)).m_171514_(40, 93).m_171488_(7.75F, -2.5F, -2.0F, 2.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)).m_171514_(0, 47).m_171488_(-5.0F, -2.5F, 6.5F, 14.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).m_171514_(0, 41).m_171488_(-5.0F, -2.5F, -2.5F, 14.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).m_171514_(90, 68).m_171488_(-7.0F, -5.5F, -2.5F, 3.0F, 7.0F, 10.0F, new CubeDeformation(0.0F)).m_171514_(50, 23).m_171488_(-17.0F, -5.0F, -1.0F, 10.0F, 8.0F, 7.0F, new CubeDeformation(0.0F)).m_171514_(53, 95).m_171488_(-16.0F, -6.0F, 0.0F, 8.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)).m_171514_(0, 22).m_171488_(-17.0F, -4.0F, -2.0F, 10.0F, 6.0F, 9.0F, new CubeDeformation(0.0F)).m_171514_(50, 11).m_171488_(-5.0F, -0.5F, -2.5F, 13.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)).m_171514_(0, 0).m_171488_(-25.0F, -7.0F, -3.0F, 8.0F, 11.0F, 11.0F, new CubeDeformation(0.0F)).m_171514_(0, 78).m_171488_(-23.0F, -7.5F, -3.5F, 4.0F, 12.0F, 12.0F, new CubeDeformation(0.0F)).m_171514_(86, 13).m_171488_(-27.0F, -6.5F, -2.5F, 2.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)).m_171514_(74, 78).m_171488_(-28.0F, -7.0F, -3.0F, 2.0F, 11.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(-6.0938F, -1.1984F, -2.5F));
      PartDefinition sail = partdefinition.m_171599_("sail", CubeListBuilder.m_171558_().m_171514_(27, 6).m_171488_(-1.5F, -1.0F, -1.5F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 12.0F, 12.0F, 0.0F, 1.5708F, 0.0F));
      PartDefinition pole = sail.m_171599_("pole", CubeListBuilder.m_171558_().m_171514_(32, 78).m_171488_(-1.025F, -41.0417F, -1.0F, 2.0F, 41.0F, 2.0F, new CubeDeformation(0.0F)).m_171514_(0, 0).m_171488_(-1.525F, -31.0417F, -22.0F, 3.0F, 3.0F, 44.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.025F, -0.9583F, 0.0F));
      pole.m_171599_("cloth", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(0.0F, -1.0F, -25.0F, 0.0F, 28.0F, 50.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(1.175F, -28.6042F, 0.0F));
      return LayerDefinition.m_171565_(meshdefinition, 128, 128);
   }

   public void setupAnim(StrikerEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.body.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.sail.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }
}
