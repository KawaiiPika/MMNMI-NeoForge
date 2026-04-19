package xyz.pixelatedw.mineminenomi.models.entities.mobs.animals;

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
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.WhiteWalkieEntity;

public class WhiteWalkieSaddleModel<T extends WhiteWalkieEntity> extends EntityModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "white_walkie_saddle"), "main");
   private final ModelPart leftBag;
   private final ModelPart rightBag;
   private final ModelPart saddle;
   private final ModelPart saddlechair;

   public WhiteWalkieSaddleModel(ModelPart root) {
      this.leftBag = root.m_171324_("leftBag");
      this.rightBag = root.m_171324_("rightBag");
      this.saddle = root.m_171324_("saddle");
      this.saddlechair = this.saddle.m_171324_("saddlechair");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      partdefinition.m_171599_("leftBag", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-2.5F, -2.5F, 1.5F, 5.0F, 5.0F, 4.0F, new CubeDeformation(0.1F)).m_171514_(0, 0).m_171488_(-2.5F, -2.5F, -5.5F, 5.0F, 5.0F, 4.0F, new CubeDeformation(0.1F)).m_171514_(54, 23).m_171488_(-2.5F, -2.5F, -9.5F, 5.0F, 5.0F, 19.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(14.9375F, 5.4511F, 2.7092F));
      partdefinition.m_171599_("rightBag", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-2.5F, -2.5F, 1.5F, 5.0F, 5.0F, 4.0F, new CubeDeformation(0.1F)).m_171514_(54, 23).m_171488_(-2.5F, -2.5F, -9.5F, 5.0F, 5.0F, 19.0F, new CubeDeformation(0.0F)).m_171514_(0, 0).m_171488_(-2.5F, -2.5F, -5.5F, 5.0F, 5.0F, 4.0F, new CubeDeformation(0.1F)), PartPose.m_171419_(-14.9375F, 5.4511F, 2.7092F));
      PartDefinition saddle = partdefinition.m_171599_("saddle", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-11.0F, -11.0385F, -17.5417F, 22.0F, 2.0F, 21.0F, new CubeDeformation(0.4F)).m_171514_(0, 24).m_171488_(-13.4375F, -10.726F, -17.5417F, 3.0F, 16.0F, 21.0F, new CubeDeformation(0.0F)).m_171514_(0, 24).m_171488_(10.4375F, -10.726F, -17.5417F, 3.0F, 16.0F, 21.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 3.4271F, 9.751F));
      saddle.m_171599_("saddlechair", CubeListBuilder.m_171558_().m_171514_(30, 26).m_171488_(-6.0F, -4.5F, -1.0F, 12.0F, 9.0F, 2.0F, new CubeDeformation(0.4F)), PartPose.m_171423_(0.0F, -15.5214F, 1.7193F, -0.1309F, 0.0F, 0.0F));
      return LayerDefinition.m_171565_(meshdefinition, 128, 128);
   }

   public void prepareMobModel(T entity, float pLimbSwing, float pLimbSwingAmount, float pPartialTick) {
      if (entity instanceof WhiteWalkieEntity) {
         if (entity.getChests() <= 0) {
            this.rightBag.f_104207_ = false;
            this.leftBag.f_104207_ = false;
         } else if (entity.getChests() == 1) {
            this.rightBag.f_104207_ = true;
            this.leftBag.f_104207_ = false;
         } else {
            this.rightBag.f_104207_ = true;
            this.leftBag.f_104207_ = true;
         }
      }

   }

   public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.leftBag.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.rightBag.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.saddle.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }
}
