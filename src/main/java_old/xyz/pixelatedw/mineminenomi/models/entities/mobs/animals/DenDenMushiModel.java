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
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.DenDenMushiEntity;

public class DenDenMushiModel<T extends DenDenMushiEntity> extends EntityModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "den_den_mushi"), "main");
   private final ModelPart leftEyeStem;
   private final ModelPart leftEye;
   private final ModelPart rightEyeStem;
   private final ModelPart rightEye;
   private final ModelPart body;
   private final ModelPart lowerBody;
   private final ModelPart shell;

   public DenDenMushiModel(ModelPart root) {
      this.leftEyeStem = root.m_171324_("leftEyeStem");
      this.leftEye = this.leftEyeStem.m_171324_("leftEye");
      this.rightEyeStem = root.m_171324_("rightEyeStem");
      this.rightEye = this.rightEyeStem.m_171324_("rightEye");
      this.body = root.m_171324_("body");
      this.lowerBody = this.body.m_171324_("lowerBody");
      this.shell = this.body.m_171324_("shell");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition leftEyeStem = partdefinition.m_171599_("leftEyeStem", CubeListBuilder.m_171558_().m_171514_(29, 0).m_171480_().m_171488_(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171419_(1.5F, 20.0F, -2.5F));
      leftEyeStem.m_171599_("leftEye", CubeListBuilder.m_171558_().m_171514_(34, 3).m_171480_().m_171488_(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171419_(0.2F, -3.0F, 0.0F));
      PartDefinition rightEyeStem = partdefinition.m_171599_("rightEyeStem", CubeListBuilder.m_171558_().m_171514_(29, 0).m_171480_().m_171488_(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171419_(-0.5F, 20.0F, -2.5F));
      rightEyeStem.m_171599_("rightEye", CubeListBuilder.m_171558_().m_171514_(34, 3).m_171480_().m_171488_(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171419_(-0.1667F, -3.0F, 0.0F));
      PartDefinition body = partdefinition.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(21, 11).m_171480_().m_171488_(-1.5F, -1.5F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171419_(0.5F, 21.5F, -2.5F));
      body.m_171599_("lowerBody", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171480_().m_171488_(-2.5F, -0.5F, -4.5F, 5.0F, 1.0F, 9.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171419_(0.0F, 2.0F, 3.0F));
      body.m_171599_("shell", CubeListBuilder.m_171558_().m_171514_(0, 11).m_171480_().m_171488_(-2.5F, -2.5F, -2.5F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171419_(0.0F, -1.0F, 4.0F));
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.leftEyeStem.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.rightEyeStem.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.body.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }
}
