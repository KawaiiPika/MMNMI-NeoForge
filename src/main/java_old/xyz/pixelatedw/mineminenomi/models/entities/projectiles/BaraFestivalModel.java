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
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.bara.BaraFestivalEntity;

public class BaraFestivalModel extends EntityModel<BaraFestivalEntity> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "bara_festival"), "main");
   private final ModelPart limb1;
   private final ModelPart limb2;
   private final ModelPart limb3;
   private final ModelPart limb4;

   public BaraFestivalModel(ModelPart root) {
      this.limb1 = root.m_171324_("limb1");
      this.limb2 = root.m_171324_("limb2");
      this.limb3 = root.m_171324_("limb3");
      this.limb4 = root.m_171324_("limb4");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      partdefinition.m_171599_("limb1", CubeListBuilder.m_171558_().m_171514_(0, 16).m_171488_(-18.0F, -6.0F, -6.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 18.0F, 0.0F));
      partdefinition.m_171599_("limb2", CubeListBuilder.m_171558_().m_171514_(0, 16).m_171488_(4.0F, -6.0F, 12.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 18.0F, 0.0F));
      partdefinition.m_171599_("limb3", CubeListBuilder.m_171558_().m_171514_(40, 16).m_171488_(-4.0F, -6.0F, -20.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 18.0F, 0.0F));
      partdefinition.m_171599_("limb4", CubeListBuilder.m_171558_().m_171514_(40, 16).m_171488_(16.0F, -6.0F, 2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 18.0F, 0.0F));
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   public void setupAnim(BaraFestivalEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      ModelPart var10000 = this.limb1;
      var10000.f_104204_ = (float)((double)var10000.f_104204_ + Math.sin((double)ageInTicks * 0.02) / (double)10.0F);
      var10000 = this.limb1;
      var10000.f_104205_ = (float)((double)var10000.f_104205_ + Math.sin((double)ageInTicks * 0.1) / (double)3.0F);
      var10000 = this.limb2;
      var10000.f_104204_ = (float)((double)var10000.f_104204_ + Math.cos((double)ageInTicks * 0.2) / (double)10.0F);
      var10000 = this.limb2;
      var10000.f_104203_ = (float)((double)var10000.f_104203_ + Math.sin((double)ageInTicks * 0.1) / (double)3.0F);
      var10000 = this.limb3;
      var10000.f_104204_ = (float)((double)var10000.f_104204_ - Math.cos((double)ageInTicks * 0.02) / (double)10.0F);
      var10000 = this.limb3;
      var10000.f_104205_ = (float)((double)var10000.f_104205_ + Math.sin((double)ageInTicks * 0.1) / (double)3.0F);
      var10000 = this.limb4;
      var10000.f_104204_ = (float)((double)var10000.f_104204_ - Math.sin((double)ageInTicks * (double)0.5F) / (double)10.0F);
      var10000 = this.limb4;
      var10000.f_104203_ = (float)((double)var10000.f_104203_ - Math.sin((double)ageInTicks * 0.1) / (double)3.0F);
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.limb1.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.limb2.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.limb3.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.limb4.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }
}
