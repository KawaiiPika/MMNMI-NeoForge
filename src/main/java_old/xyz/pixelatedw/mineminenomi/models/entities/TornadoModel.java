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
import xyz.pixelatedw.mineminenomi.entities.TornadoEntity;

public class TornadoModel<T extends TornadoEntity> extends EntityModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "tornado"), "main");
   private final ModelPart base;
   private final ModelPart funnel1;
   private final ModelPart funnel2;
   private final ModelPart funnel3;
   private final ModelPart top;

   public TornadoModel(ModelPart root) {
      this.base = root.m_171324_("base");
      this.funnel1 = this.base.m_171324_("funnel1");
      this.funnel2 = this.funnel1.m_171324_("funnel2");
      this.funnel3 = this.funnel2.m_171324_("funnel3");
      this.top = this.funnel3.m_171324_("top");
   }

   public static LayerDefinition createLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition base = partdefinition.m_171599_("base", CubeListBuilder.m_171558_().m_171514_(0, 42).m_171488_(-1.5F, -2.5F, -1.5F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.5F, 21.5F, 0.5F));
      PartDefinition funnel1 = base.m_171599_("funnel1", CubeListBuilder.m_171558_().m_171514_(0, 33).m_171488_(-2.5F, -2.0F, -2.5F, 5.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, -4.5F, 0.0F));
      PartDefinition funnel2 = funnel1.m_171599_("funnel2", CubeListBuilder.m_171558_().m_171514_(0, 18).m_171488_(-4.5F, -3.0F, -4.5F, 9.0F, 6.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, -5.0F, 0.0F));
      PartDefinition funnel3 = funnel2.m_171599_("funnel3", CubeListBuilder.m_171558_().m_171514_(20, 33).m_171488_(-5.5F, -4.5F, -5.5F, 11.0F, 9.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, -7.5F, 0.0F));
      funnel3.m_171599_("top", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-7.5F, -1.5F, -7.5F, 15.0F, 3.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, -6.0F, 0.0F));
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.base.f_104204_ = ageInTicks * 0.1F * entity.getSpeed() % 360.0F;
      this.funnel1.f_104204_ = ageInTicks * -0.2F * entity.getSpeed() % 360.0F;
      this.funnel2.f_104204_ = ageInTicks * 0.2F * entity.getSpeed() % 360.0F;
      this.funnel3.f_104204_ = ageInTicks * -0.2F * entity.getSpeed() % 360.0F;
      this.top.f_104204_ = ageInTicks * 0.2F * entity.getSpeed() % 360.0F;
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.base.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }
}
