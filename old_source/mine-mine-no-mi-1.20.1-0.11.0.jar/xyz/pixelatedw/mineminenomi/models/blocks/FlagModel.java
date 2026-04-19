package xyz.pixelatedw.mineminenomi.models.blocks;

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
import net.minecraft.world.level.Level;

public class FlagModel extends EntityModel<Entity> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "flag"), "main");
   private final ModelPart flag1;
   private final ModelPart flag2;
   private final ModelPart flag3;

   public FlagModel(ModelPart root) {
      this.flag1 = root.m_171324_("flag1");
      this.flag2 = this.flag1.m_171324_("flag2");
      this.flag3 = this.flag2.m_171324_("flag3");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition flag1 = partdefinition.m_171599_("flag1", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(0.0F, -8.0F, 0.0F, 8.0F, 16.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(-8.0F, 16.0F, 0.0F));
      PartDefinition flag2 = flag1.m_171599_("flag2", CubeListBuilder.m_171558_().m_171514_(8, 0).m_171488_(0.0F, -8.0F, 0.0F, 8.0F, 16.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(8.0F, 0.0F, 0.0F));
      flag2.m_171599_("flag3", CubeListBuilder.m_171558_().m_171514_(16, 0).m_171488_(0.0F, -8.0F, 0.0F, 8.0F, 16.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(8.0F, 0.0F, 0.0F));
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   public void m_6973_(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
   }

   public void flagAnimation(Level world, long flutterTick) {
      this.flag1.f_104201_ = 16.0F;
      this.flag1.f_104200_ = -8.0F;
      this.flag2.f_104201_ = 0.0F;
      this.flag2.f_104200_ = 8.0F;
      this.flag3.f_104201_ = 0.0F;
      this.flag3.f_104200_ = 8.0F;
      ModelPart var10000 = this.flag1;
      var10000.f_104201_ += (float)(Math.cos((double)((float)flutterTick * 0.05F) + Math.PI) * (double)0.2F);
      var10000 = this.flag2;
      var10000.f_104201_ += (float)(Math.cos((double)((float)flutterTick * 0.1F) + Math.PI) * (double)0.3F);
      var10000 = this.flag3;
      var10000.f_104201_ += (float)(Math.cos((double)((float)flutterTick * 0.1F) + Math.PI) * (double)0.4F);
      this.flag1.f_104204_ = (float)(Math.cos((double)((float)flutterTick * 0.12F + (float)Math.PI)) * (double)0.01F);
      this.flag2.f_104204_ = (float)(Math.cos((double)((float)flutterTick * 0.1F + (float)Math.PI)) * (double)0.02F);
      this.flag3.f_104204_ = (float)(Math.cos((double)((float)flutterTick * 0.05F + (float)Math.PI)) * (double)0.02F);
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.flag1.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }
}
