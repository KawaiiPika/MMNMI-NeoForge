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

public class HydraModel<T extends Entity> extends EntityModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "hydra"), "main");
   private final ModelPart head;
   private final ModelPart head1;
   private final ModelPart head2;
   private final ModelPart head3;
   private final ModelPart head4;
   private final ModelPart rightHorn1;
   private final ModelPart rightHorn2;
   private final ModelPart leftHorn1;
   private final ModelPart leftHorn2;
   private final ModelPart jaw1;
   private final ModelPart jaw2;
   private final ModelPart teeth1;
   private final ModelPart teeth2;

   public HydraModel(ModelPart root) {
      this.head = root.m_171324_("head");
      this.head1 = this.head.m_171324_("head1");
      this.head2 = this.head.m_171324_("head2");
      this.head3 = this.head.m_171324_("head3");
      this.head4 = this.head.m_171324_("head4");
      this.rightHorn1 = this.head.m_171324_("rightHorn1");
      this.rightHorn2 = this.head.m_171324_("rightHorn2");
      this.leftHorn1 = this.head.m_171324_("leftHorn1");
      this.leftHorn2 = this.head.m_171324_("leftHorn2");
      this.jaw1 = this.head.m_171324_("jaw1");
      this.jaw2 = this.head.m_171324_("jaw2");
      this.teeth1 = this.head.m_171324_("teeth1");
      this.teeth2 = this.head.m_171324_("teeth2");
   }

   public static LayerDefinition createLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition head = partdefinition.m_171599_("head", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, -4.6083F, 1.5417F));
      head.m_171599_("head1", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-4.0F, -5.0F, -10.0F, 8.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 2.6083F, 6.4583F));
      head.m_171599_("head2", CubeListBuilder.m_171558_().m_171514_(0, 21).m_171488_(-4.0F, -0.5F, -0.5F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, -1.6917F, -5.2417F, -0.0447F, 0.0869F, 0.0876F));
      head.m_171599_("head3", CubeListBuilder.m_171558_().m_171514_(0, 21).m_171488_(0.0F, -0.5F, -0.5F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, -1.6917F, -5.2417F, -0.0447F, -0.0869F, -0.0876F));
      head.m_171599_("head4", CubeListBuilder.m_171558_().m_171514_(11, 21).m_171488_(-4.0F, -1.5F, -2.0F, 8.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, -0.8917F, -3.3417F));
      head.m_171599_("rightHorn1", CubeListBuilder.m_171558_().m_171514_(33, 16).m_171480_().m_171488_(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(-2.6F, -0.9917F, 3.4583F, 0.7046F, -0.122F, -0.0528F));
      head.m_171599_("rightHorn2", CubeListBuilder.m_171558_().m_171514_(33, 26).m_171488_(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-3.5F, -4.3917F, 7.2583F, 0.9923F, -0.1213F, -0.1231F));
      head.m_171599_("leftHorn1", CubeListBuilder.m_171558_().m_171514_(33, 16).m_171488_(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(2.6F, -0.9917F, 3.4583F, 0.7046F, 0.122F, 0.0528F));
      head.m_171599_("leftHorn2", CubeListBuilder.m_171558_().m_171514_(33, 26).m_171480_().m_171488_(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(3.5F, -4.3917F, 7.2583F, 0.9923F, 0.1213F, 0.1231F));
      head.m_171599_("jaw1", CubeListBuilder.m_171558_().m_171514_(0, 27).m_171488_(-4.0F, 0.0F, -10.0F, 8.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 0.6083F, -3.5417F));
      head.m_171599_("jaw2", CubeListBuilder.m_171558_().m_171514_(0, 27).m_171480_().m_171488_(-4.0F, 0.0F, -10.0F, 8.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(0.0F, 5.6083F, -3.5417F, 0.2269F, 0.0F, 0.0F));
      head.m_171599_("teeth1", CubeListBuilder.m_171558_().m_171514_(28, 39).m_171488_(-4.0F, 0.0F, -10.0F, 8.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 2.6083F, -3.4417F));
      head.m_171599_("teeth2", CubeListBuilder.m_171558_().m_171514_(28, 52).m_171488_(-4.0F, 0.0F, -10.3F, 8.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 3.6083F, -3.5417F, 0.2269F, 0.0F, 0.0F));
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   public void m_6973_(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.head.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }
}
