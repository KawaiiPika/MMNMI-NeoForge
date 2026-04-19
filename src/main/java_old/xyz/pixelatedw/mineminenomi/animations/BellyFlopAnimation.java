package xyz.pixelatedw.mineminenomi.animations;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.models.entities.mobs.animals.GorillaModel;
import xyz.pixelatedw.mineminenomi.models.entities.mobs.animals.SeaCowModel;

public class BellyFlopAnimation extends Animation<LivingEntity, EntityModel<LivingEntity>> {
   public BellyFlopAnimation(AnimationId<BellyFlopAnimation> animId) {
      super(animId);
      this.setAnimationPostAngles(this::setup);
      this.setAnimationAngles(this::angles);
   }

   public void setup(LivingEntity entity, EntityModel<LivingEntity> entityModel, PoseStack matrixStack, MultiBufferSource buffer, int packedLight, float partialTicks, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      if (!(entityModel instanceof SeaCowModel) && entityModel instanceof HumanoidModel) {
         matrixStack.m_252781_(Axis.f_252529_.m_252977_(90.0F));
         matrixStack.m_85837_((double)0.0F, (double)0.0F, -0.8);
      }

   }

   public void angles(LivingEntity entity, EntityModel<LivingEntity> model, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      if (model instanceof HumanoidModel bipedModel) {
         bipedModel.f_102808_.f_104203_ = 0.0F;
         bipedModel.f_102808_.f_104204_ = 0.0F;
         bipedModel.f_102808_.f_104205_ = 0.0F;
         bipedModel.f_102809_.m_104315_(bipedModel.f_102808_);
         bipedModel.f_102811_.f_104205_ = (float)Math.toRadians((double)45.0F);
         bipedModel.f_102811_.f_104203_ = (float)Math.toRadians((double)8.0F);
         bipedModel.f_102812_.f_104205_ = (float)Math.toRadians((double)-45.0F);
         bipedModel.f_102812_.f_104203_ = (float)Math.toRadians((double)8.0F);
         bipedModel.f_102813_.f_104205_ = (float)Math.toRadians((double)15.0F);
         bipedModel.f_102813_.f_104203_ = (float)Math.toRadians((double)8.0F);
         bipedModel.f_102814_.f_104205_ = (float)Math.toRadians((double)-15.0F);
         bipedModel.f_102814_.f_104203_ = (float)Math.toRadians((double)8.0F);
         if (model instanceof GorillaModel) {
            bipedModel.f_102811_.f_104205_ = (float)Math.toRadians((double)75.0F);
            bipedModel.f_102812_.f_104205_ = (float)Math.toRadians((double)-75.0F);
         }
      } else if (model instanceof SeaCowModel seaCowModel) {
         seaCowModel.leftFin.f_104205_ = (float)Math.toRadians((double)-60.0F);
         seaCowModel.leftFin2.f_104205_ = (float)Math.toRadians((double)0.0F);
         seaCowModel.rightFin.f_104205_ = (float)Math.toRadians((double)60.0F);
         seaCowModel.rightFin2.f_104205_ = (float)Math.toRadians((double)0.0F);
      }

   }
}
