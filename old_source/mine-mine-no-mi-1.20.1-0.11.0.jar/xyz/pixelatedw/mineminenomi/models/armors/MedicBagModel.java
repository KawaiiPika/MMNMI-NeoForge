package xyz.pixelatedw.mineminenomi.models.armors;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.HumanoidModel;
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

public class MedicBagModel<T extends LivingEntity> extends HumanoidArmorModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "medic_bag"), "main");
   private final ModelPart backpack;
   private final ModelPart backpack_2;
   private final ModelPart backpack_strings;

   public MedicBagModel(ModelPart root) {
      super(root);
      this.backpack = root.m_171324_("backpack");
      this.backpack_2 = this.backpack.m_171324_("backpack_2");
      this.backpack_strings = this.backpack.m_171324_("backpack_strings");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = HumanoidModel.m_170681_(new CubeDeformation(0.0F), 0.0F);
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition backpack = partdefinition.m_171599_("backpack", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-2.5F, 1.5F, 2.0F, 5.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 3.7F, 2.0F));
      backpack.m_171599_("backpack_2", CubeListBuilder.m_171558_().m_171514_(0, 9).m_171488_(-3.5F, 0.5F, 0.0F, 7.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 3.7F, 2.0F));
      backpack.m_171599_("backpack_strings", CubeListBuilder.m_171558_().m_171514_(20, 0).m_171488_(-5.0F, -4.0F, 0.1F, 10.0F, 9.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(0.0F, 3.7F, 2.0F));
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   public void m_6973_(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.backpack.m_104315_(this.f_102810_);
      this.backpack.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }
}
