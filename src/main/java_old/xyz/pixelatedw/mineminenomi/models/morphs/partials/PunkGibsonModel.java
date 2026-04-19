package xyz.pixelatedw.mineminenomi.models.morphs.partials;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.EntityModel;
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
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.projectile.Projectile;
import xyz.pixelatedw.mineminenomi.api.morph.IFirstPersonHandReplacer;

public class PunkGibsonModel<T extends Entity> extends EntityModel<T> implements IFirstPersonHandReplacer {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("mineminenomi", "punk_gibson"), "main");
   public final ModelPart rightArm;
   private final ModelPart smallArm;
   private final ModelPart bigArm;
   private final ModelPart finger4;
   private final ModelPart finger4b;
   private final ModelPart finger3;
   private final ModelPart finger3b;
   private final ModelPart finger2;
   private final ModelPart finger2b;
   private final ModelPart finger1;
   private final ModelPart finger1b;

   public PunkGibsonModel(ModelPart root) {
      this.rightArm = root.m_171324_("right_arm");
      this.smallArm = this.rightArm.m_171324_("smallArm");
      this.bigArm = this.rightArm.m_171324_("bigArm");
      this.finger4 = this.bigArm.m_171324_("finger4");
      this.finger4b = this.finger4.m_171324_("finger4b");
      this.finger3 = this.bigArm.m_171324_("finger3");
      this.finger3b = this.finger3.m_171324_("finger3b");
      this.finger2 = this.bigArm.m_171324_("finger2");
      this.finger2b = this.finger2.m_171324_("finger2b");
      this.finger1 = this.bigArm.m_171324_("finger1");
      this.finger1b = this.finger1.m_171324_("finger1b");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = HumanoidModel.m_170681_(CubeDeformation.f_171458_, 0.0F);
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition rightArm = partdefinition.m_171599_("right_arm", CubeListBuilder.m_171558_(), PartPose.m_171423_(-2.75F, -0.75F, -0.5F, -1.5272F, 0.0F, 0.0F));
      rightArm.m_171599_("smallArm", CubeListBuilder.m_171558_().m_171514_(64, 83).m_171488_(-16.0F, -7.0F, -6.0F, 16.0F, 29.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.m_171419_(-1.0019F, -0.0872F, 2.5F));
      PartDefinition bigArm = rightArm.m_171599_("bigArm", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-12.7334F, 0.2782F, -11.8359F, 24.0F, 58.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-8.0019F, 19.9128F, 5.0F, 0.0452F, 0.3127F, -0.1608F));
      PartDefinition finger4 = bigArm.m_171599_("finger4", CubeListBuilder.m_171558_().m_171514_(0, 103).m_171488_(-3.5F, -2.0F, -7.0F, 7.0F, 4.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(11.3785F, 55.124F, -6.8285F, 0.7105F, -0.7987F, -0.3516F));
      finger4.m_171599_("finger4b", CubeListBuilder.m_171558_().m_171514_(0, 85).m_171488_(-3.5F, -1.2224F, -14.1691F, 7.0F, 4.0F, 14.0F, new CubeDeformation(0.01F)), PartPose.m_171423_(0.0F, -1.0418F, -6.3826F, 1.0472F, 0.0F, 0.0F));
      PartDefinition finger3 = bigArm.m_171599_("finger3", CubeListBuilder.m_171558_().m_171514_(0, 103).m_171488_(-3.2897F, -0.6527F, -11.7155F, 7.0F, 4.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(6.6941F, 56.5544F, -11.7482F, 0.8733F, -0.028F, -0.0259F));
      finger3.m_171599_("finger3b", CubeListBuilder.m_171558_().m_171514_(0, 85).m_171488_(-3.2897F, 0.9734F, -15.8321F, 7.0F, 4.0F, 14.0F, new CubeDeformation(0.01F)), PartPose.m_171423_(0.0F, -2.6608F, -11.5565F, 1.0036F, 0.0F, 0.0F));
      PartDefinition finger2 = bigArm.m_171599_("finger2", CubeListBuilder.m_171558_().m_171514_(0, 103).m_171488_(-3.299F, -0.6527F, -11.7065F, 7.0F, 4.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-1.3059F, 56.5544F, -11.7482F, 0.8728F, 0.0F, 0.0076F));
      finger2.m_171599_("finger2b", CubeListBuilder.m_171558_().m_171514_(0, 85).m_171488_(-3.299F, 1.5005F, -15.4963F, 7.0F, 4.0F, 14.0F, new CubeDeformation(0.01F)), PartPose.m_171423_(0.0F, -2.9108F, -12.3065F, 1.0036F, 0.0F, 0.0F));
      PartDefinition finger1 = bigArm.m_171599_("finger1", CubeListBuilder.m_171558_().m_171514_(0, 103).m_171488_(-3.1633F, -0.6465F, -11.6975F, 7.0F, 4.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.m_171423_(-9.3674F, 56.552F, -11.763F, 0.8728F, 0.043F, 0.0076F));
      finger1.m_171599_("finger1b", CubeListBuilder.m_171558_().m_171514_(0, 85).m_171488_(-3.3781F, 1.5072F, -15.4983F, 7.0F, 4.0F, 14.0F, new CubeDeformation(0.01F)), PartPose.m_171423_(0.2148F, -2.91F, -12.3019F, 1.0036F, 0.0F, 0.0F));
      return LayerDefinition.m_171565_(meshdefinition, 128, 128);
   }

   public void m_6973_(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.rightArm.f_104203_ = (float)Math.toRadians((double)-100.0F);
      this.rightArm.f_104205_ = 0.0F;
      this.rightArm.f_104204_ = 0.25F;
      this.rightArm.f_104200_ = -3.0F;
      if (this.f_102608_ > 0.0F) {
         ModelPart var10000 = this.rightArm;
         var10000.f_104203_ = (float)((double)var10000.f_104203_ + (double)Mth.m_14031_((float)((double)this.f_102608_ * Math.PI)) / (double)3.0F);
         var10000 = this.rightArm;
         var10000.f_104204_ = (float)((double)var10000.f_104204_ - (double)Mth.m_14031_((float)((double)this.f_102608_ * Math.PI)) / (double)3.0F);
         var10000 = this.rightArm;
         var10000.f_104205_ = (float)((double)var10000.f_104205_ - (double)Mth.m_14031_((float)((double)this.f_102608_ * Math.PI)) / (double)3.0F);
      }

      if (entity instanceof Projectile) {
         this.rightArm.f_104204_ = 0.0F;
         this.rightArm.f_104200_ = 8.0F;
      }

   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.rightArm.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }

   public void renderFirstPersonArm(PoseStack poseStack, VertexConsumer vertex, int packedLight, int overlay, float red, float green, float blue, float alpha, HumanoidArm side, boolean isLeg) {
      poseStack.m_85837_((double)-0.5F, 0.2, 0.9);
      poseStack.m_252781_(Axis.f_252436_.m_252977_(70.0F));
      poseStack.m_252781_(Axis.f_252529_.m_252977_(20.0F));
      this.rightArm.f_104203_ = this.rightArm.f_104204_ = this.rightArm.f_104205_ = 0.0F;
      this.m_7695_(poseStack, vertex, packedLight, overlay, red, green, blue, alpha);
   }
}
