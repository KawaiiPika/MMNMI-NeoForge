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

public class MiniHollowModel<T extends Entity> extends EntityModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "mini_hollow"), "main");
   private final ModelPart mini;
   private final ModelPart head;
   private final ModelPart body;
   private final ModelPart rightarm;
   private final ModelPart leftarm;
   private final ModelPart rightleg;
   private final ModelPart leftleg;

   public MiniHollowModel(ModelPart root) {
      this.mini = root.m_171324_("mini");
      this.head = this.mini.m_171324_("head");
      this.body = this.mini.m_171324_("body");
      this.rightarm = this.mini.m_171324_("rightarm");
      this.leftarm = this.mini.m_171324_("leftarm");
      this.rightleg = this.mini.m_171324_("rightleg");
      this.leftleg = this.mini.m_171324_("leftleg");
   }

   public static LayerDefinition createLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition mini = partdefinition.m_171599_("mini", CubeListBuilder.m_171558_(), PartPose.m_171423_(0.0F, 2.0F, 0.0F, 1.5708F, 0.0F, 0.0F));
      mini.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, -2.0F, 0.0F));
      mini.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(9, 0).m_171488_(-0.5F, -1.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.01F)), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      mini.m_171599_("rightarm", CubeListBuilder.m_171558_().m_171514_(0, 5).m_171488_(-0.5F, -1.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-1.0151F, -0.3077F, 0.0F, 0.0F, 0.0F, 0.9425F));
      mini.m_171599_("leftarm", CubeListBuilder.m_171558_().m_171514_(0, 5).m_171488_(-0.5F, -1.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(1.1029F, -0.3167F, 0.0F, 0.0F, 0.0F, -0.9425F));
      mini.m_171599_("rightleg", CubeListBuilder.m_171558_().m_171514_(6, 5).m_171488_(-0.5F, -1.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-0.564F, 1.6059F, 0.0F, 0.0F, 0.0F, 0.6109F));
      mini.m_171599_("leftleg", CubeListBuilder.m_171558_().m_171514_(6, 5).m_171488_(-0.5F, -1.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.m_171423_(0.5831F, 1.6324F, 0.0F, 0.0F, 0.0F, -0.6109F));
      return LayerDefinition.m_171565_(meshdefinition, 16, 16);
   }

   public void m_6973_(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.mini.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }
}
