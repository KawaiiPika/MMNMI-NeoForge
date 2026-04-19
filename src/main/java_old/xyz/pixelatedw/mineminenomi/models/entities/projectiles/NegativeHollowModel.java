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

public class NegativeHollowModel<T extends Entity> extends EntityModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "negative_hollow"), "main");
   private final ModelPart hollow;
   private final ModelPart head;
   private final ModelPart body;
   private final ModelPart rightArm;
   private final ModelPart rightArm2;
   private final ModelPart leftArm;
   private final ModelPart leftArm2;
   private final ModelPart rightLeg;
   private final ModelPart leftLeg;

   public NegativeHollowModel(ModelPart root) {
      this.hollow = root.m_171324_("hollow");
      this.head = this.hollow.m_171324_("head");
      this.body = this.hollow.m_171324_("body");
      this.rightArm = this.hollow.m_171324_("rightArm");
      this.rightArm2 = this.rightArm.m_171324_("rightArm2");
      this.leftArm = this.hollow.m_171324_("leftArm");
      this.leftArm2 = this.leftArm.m_171324_("leftArm2");
      this.rightLeg = this.hollow.m_171324_("rightLeg");
      this.leftLeg = this.hollow.m_171324_("leftLeg");
   }

   public static LayerDefinition createLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition hollow = partdefinition.m_171599_("hollow", CubeListBuilder.m_171558_(), PartPose.m_171423_(0.0F, 1.0F, 0.0F, 1.5708F, 0.0F, 0.0F));
      hollow.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171480_().m_171488_(-1.0F, -4.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171419_(-1.0F, -2.0F, -0.5F));
      hollow.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(17, 0).m_171488_(1.0F, 0.0F, 0.0F, 3.0F, 8.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.m_171419_(-2.5F, -2.0F, -1.0F));
      PartDefinition rightArm = hollow.m_171599_("rightArm", CubeListBuilder.m_171558_().m_171514_(13, 10).m_171488_(0.0F, -1.0F, -1.0F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-2.5F, -0.5F, 0.0F, 3.1416F, 0.0F, 0.0F));
      rightArm.m_171599_("rightArm2", CubeListBuilder.m_171558_().m_171514_(0, 10).m_171488_(0.0F, 0.0F, -1.5F, 2.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(-0.5F, 3.0F, 0.0F));
      PartDefinition leftArm = hollow.m_171599_("leftArm", CubeListBuilder.m_171558_().m_171514_(13, 10).m_171480_().m_171488_(1.0F, -1.0F, -1.0F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(0.5F, -0.5F, 0.0F, 3.1416F, 0.0F, 0.0F));
      leftArm.m_171599_("leftArm2", CubeListBuilder.m_171558_().m_171514_(0, 10).m_171480_().m_171488_(0.0F, 0.0F, -1.5F, 2.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171419_(0.5F, 3.0F, 0.0F));
      hollow.m_171599_("rightLeg", CubeListBuilder.m_171558_().m_171514_(8, 9).m_171488_(0.5F, 0.0F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(-1.75F, 6.0F, 0.0F));
      hollow.m_171599_("leftLeg", CubeListBuilder.m_171558_().m_171514_(8, 9).m_171488_(0.5F, 0.0F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(-0.25F, 6.0F, 0.0F));
      return LayerDefinition.m_171565_(meshdefinition, 32, 32);
   }

   public void m_6973_(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.hollow.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }
}
