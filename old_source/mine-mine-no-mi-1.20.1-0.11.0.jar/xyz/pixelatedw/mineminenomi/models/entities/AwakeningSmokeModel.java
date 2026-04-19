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
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class AwakeningSmokeModel extends EntityModel<LivingEntity> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "awakening_smoke"), "main");
   private final ModelPart smoke;
   private final ModelPart rightSmoke;
   private final ModelPart rightSmoke2;
   private final ModelPart rightSmoke3;
   private final ModelPart leftSmoke;
   private final ModelPart leftSmoke2;
   private final ModelPart leftSmoke3;
   private final ModelPart backSmoke;
   private final ModelPart backSmoke3;
   private final ModelPart backSmoke2;
   private final ModelPart backSmoke5;
   private final ModelPart backSmoke4;

   public AwakeningSmokeModel(ModelPart root) {
      this.smoke = root.m_171324_("smoke");
      this.rightSmoke = this.smoke.m_171324_("rightSmoke");
      this.rightSmoke2 = this.rightSmoke.m_171324_("rightSmoke2");
      this.rightSmoke3 = this.rightSmoke2.m_171324_("rightSmoke3");
      this.leftSmoke = this.smoke.m_171324_("leftSmoke");
      this.leftSmoke2 = this.leftSmoke.m_171324_("leftSmoke2");
      this.leftSmoke3 = this.leftSmoke2.m_171324_("leftSmoke3");
      this.backSmoke = this.smoke.m_171324_("backSmoke");
      this.backSmoke3 = this.backSmoke.m_171324_("backSmoke3");
      this.backSmoke2 = this.backSmoke.m_171324_("backSmoke2");
      this.backSmoke5 = this.backSmoke.m_171324_("backSmoke5");
      this.backSmoke4 = this.backSmoke.m_171324_("backSmoke4");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition smoke = partdefinition.m_171599_("smoke", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 0.0F, 0.5F));
      PartDefinition rightSmoke = smoke.m_171599_("rightSmoke", CubeListBuilder.m_171558_().m_171514_(0, 15).m_171488_(-2.0F, -1.3844F, 0.0F, 4.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-6.0F, -1.1156F, -2.112F, -0.5672F, 0.0F, 0.0F));
      PartDefinition rightSmoke2 = rightSmoke.m_171599_("rightSmoke2", CubeListBuilder.m_171558_().m_171514_(0, 9).m_171488_(-2.0F, -2.5F, 0.0F, 4.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 4.8844F, 0.888F, 0.3491F, 0.0F, 0.0F));
      rightSmoke2.m_171599_("rightSmoke3", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-2.0F, -2.6188F, -0.0759F, 4.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.0F, 5.0F, 0.75F, 0.2618F, 0.0F, 0.0F));
      PartDefinition leftSmoke = smoke.m_171599_("leftSmoke", CubeListBuilder.m_171558_().m_171514_(0, 15).m_171480_().m_171488_(-2.0F, -1.3844F, 0.0F, 4.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(6.0F, -1.1156F, -2.112F, -0.5672F, 0.0F, 0.0F));
      PartDefinition leftSmoke2 = leftSmoke.m_171599_("leftSmoke2", CubeListBuilder.m_171558_().m_171514_(0, 9).m_171480_().m_171488_(-2.0F, -2.5F, 0.0F, 4.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(0.0F, 4.8844F, 0.888F, 0.3491F, 0.0F, 0.0F));
      leftSmoke2.m_171599_("leftSmoke3", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171480_().m_171488_(-2.0F, -2.6188F, -0.0759F, 4.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(0.0F, 5.0F, 0.75F, 0.2618F, 0.0F, 0.0F));
      PartDefinition backSmoke = smoke.m_171599_("backSmoke", CubeListBuilder.m_171558_().m_171514_(0, 20).m_171488_(-1.5F, -2.435F, 0.1314F, 4.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-6.5F, -3.065F, 1.0686F, -1.309F, 0.0F, 0.0F));
      backSmoke.m_171599_("backSmoke3", CubeListBuilder.m_171558_().m_171514_(0, 26).m_171488_(-2.5F, -3.5F, -0.25F, 16.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(0.9475F, -6.5053F, -1.1009F, 0.5236F, 0.0F, 0.0F));
      backSmoke.m_171599_("backSmoke2", CubeListBuilder.m_171558_().m_171514_(11, 0).m_171488_(-1.5F, -2.75F, -0.0977F, 4.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-0.0525F, -3.5053F, -0.1009F, 0.2618F, 0.0F, 0.0F));
      backSmoke.m_171599_("backSmoke5", CubeListBuilder.m_171558_().m_171514_(11, 0).m_171480_().m_171488_(-1.5F, -2.75F, -0.0977F, 4.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171423_(11.9475F, -3.5053F, -0.1009F, 0.2618F, 0.0F, 0.0F));
      backSmoke.m_171599_("backSmoke4", CubeListBuilder.m_171558_().m_171514_(0, 20).m_171480_().m_171488_(-1.5F, -2.435F, 0.1314F, 4.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)).m_171555_(false), PartPose.m_171419_(12.0F, 0.0F, 0.0F));
      return LayerDefinition.m_171565_(meshdefinition, 32, 32);
   }

   public void setupAnim(LivingEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.smoke.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }
}
