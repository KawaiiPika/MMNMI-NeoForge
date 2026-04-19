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

public class SpearModel<T extends Entity> extends EntityModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "spear"), "main");
   private final ModelPart harpooncable;
   private final ModelPart harpoon2;
   private final ModelPart harpoon4;
   private final ModelPart harpoon3;
   private final ModelPart harpoon5;
   private final ModelPart harpoon7;
   private final ModelPart harpoon1;

   public SpearModel(ModelPart root) {
      this.harpooncable = root.m_171324_("harpooncable");
      this.harpoon2 = this.harpooncable.m_171324_("harpoon2");
      this.harpoon4 = this.harpoon2.m_171324_("harpoon4");
      this.harpoon3 = this.harpoon2.m_171324_("harpoon3");
      this.harpoon5 = this.harpoon2.m_171324_("harpoon5");
      this.harpoon7 = this.harpoon2.m_171324_("harpoon7");
      this.harpoon1 = this.harpooncable.m_171324_("harpoon1");
   }

   public static LayerDefinition createLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition harpooncable = partdefinition.m_171599_("harpooncable", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-0.5F, -0.5F, -15.0F, 1.0F, 1.0F, 30.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.5F, 0.5F, 0.0F));
      PartDefinition harpoon2 = harpooncable.m_171599_("harpoon2", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, -0.5F, -15.0F));
      harpoon2.m_171599_("harpoon4", CubeListBuilder.m_171558_().m_171514_(-1, 12).m_171488_(-1.5F, 0.0F, -3.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.02F)), PartPose.m_171419_(0.0F, 0.0F, 5.0F));
      harpoon2.m_171599_("harpoon3", CubeListBuilder.m_171558_().m_171514_(0, 8).m_171488_(-1.5F, 0.0F, -2.0F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.03F)), PartPose.m_171419_(0.0F, 0.0F, 5.0F));
      harpoon2.m_171599_("harpoon5", CubeListBuilder.m_171558_().m_171514_(0, 15).m_171488_(-0.6F, 0.0F, -2.75F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.01F)), PartPose.m_171423_(0.0F, 0.0F, 5.0F, 0.0F, 1.1345F, 0.0F));
      harpoon2.m_171599_("harpoon7", CubeListBuilder.m_171558_().m_171514_(0, 15).m_171480_().m_171488_(-4.4F, 0.0F, -2.75F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.01F)).m_171555_(false), PartPose.m_171423_(0.0F, 0.0F, 5.0F, 0.0F, -1.1345F, 0.0F));
      harpooncable.m_171599_("harpoon1", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-0.5F, -0.5F, 0.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(-0.5F, -0.5F, -10.0F));
      return LayerDefinition.m_171565_(meshdefinition, 128, 32);
   }

   public void m_6973_(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.harpooncable.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }
}
