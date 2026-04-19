package xyz.pixelatedw.mineminenomi.models.entities.projectiles;

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
import net.minecraft.world.entity.Entity;

public class TokuHollowModel<T extends Entity> extends EntityModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "toku_hollow"), "main");
   private final ModelPart head;
   private final ModelPart head2;
   private final ModelPart head3;
   private final ModelPart head4;
   private final ModelPart body;
   private final ModelPart rightarm;
   private final ModelPart leftarm;
   private final ModelPart rightleg;
   private final ModelPart leftleg;

   public TokuHollowModel(ModelPart root) {
      this.head = root.m_171324_("head");
      this.head2 = root.m_171324_("head2");
      this.head3 = root.m_171324_("head3");
      this.head4 = root.m_171324_("head4");
      this.body = root.m_171324_("body");
      this.rightarm = root.m_171324_("rightarm");
      this.leftarm = root.m_171324_("leftarm");
      this.rightleg = root.m_171324_("rightleg");
      this.leftleg = root.m_171324_("leftleg");
   }

   public static LayerDefinition createLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      partdefinition.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(0.0F, 0.0F, 0.0F, 24.0F, 24.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(-12.0F, -14.0F, -5.0F));
      partdefinition.m_171599_("head2", CubeListBuilder.m_171558_().m_171514_(0, 50).m_171488_(0.0F, 0.0F, 0.0F, 22.0F, 22.0F, 25.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(-11.0F, -13.0F, -5.5F));
      partdefinition.m_171599_("head3", CubeListBuilder.m_171558_().m_171514_(98, 50).m_171488_(0.0F, 0.0F, 0.0F, 25.0F, 22.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(-12.5F, -13.0F, -4.0F));
      partdefinition.m_171599_("head4", CubeListBuilder.m_171558_().m_171514_(98, 0).m_171488_(0.0F, 0.0F, 0.0F, 22.0F, 25.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(-11.0F, -14.5F, -4.0F));
      partdefinition.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(188, 0).m_171488_(0.0F, 0.0F, 0.0F, 8.0F, 4.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(-4.0F, -2.0F, 19.5F));
      partdefinition.m_171599_("rightarm", CubeListBuilder.m_171558_().m_171514_(188, 22).m_171488_(0.0F, 0.0F, 0.0F, 3.0F, 3.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-17.0F, 2.0F, -9.0F, 0.1396F, 0.4189F, 0.0F));
      partdefinition.m_171599_("leftarm", CubeListBuilder.m_171558_().m_171514_(188, 22).m_171488_(0.0F, 0.0F, 0.0F, 3.0F, 3.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(14.2F, 2.0F, -10.0F, 0.1396F, -0.4189F, 0.0F));
      partdefinition.m_171599_("rightleg", CubeListBuilder.m_171558_().m_171514_(188, 14).m_171488_(0.0F, 0.0F, 0.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(-3.25F, -1.0F, 28.5F));
      partdefinition.m_171599_("leftleg", CubeListBuilder.m_171558_().m_171514_(188, 14).m_171488_(0.0F, 0.0F, 0.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(1.25F, -1.0F, 28.5F));
      return LayerDefinition.m_171565_(meshdefinition, 256, 128);
   }

   public void m_6973_(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.head.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.head2.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.head3.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.head4.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.body.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.rightarm.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.leftarm.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.rightleg.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.leftleg.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }
}
