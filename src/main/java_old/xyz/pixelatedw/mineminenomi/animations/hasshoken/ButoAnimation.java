package xyz.pixelatedw.mineminenomi.animations.hasshoken;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;

public class ButoAnimation extends Animation<LivingEntity, EntityModel<LivingEntity>> {
   public ButoAnimation(AnimationId<ButoAnimation> id) {
      super(id);
      super.setAnimationPostAngles(this::setup);
      super.setAnimationAngles(this::angles);
   }

   public void setup(LivingEntity entity, EntityModel<LivingEntity> entityModel, PoseStack matrixStack, MultiBufferSource buffer, int packedLight, float partialTicks, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      matrixStack.m_252781_(Axis.f_252529_.m_252977_(-90.0F));
      matrixStack.m_85837_((double)0.0F, (double)0.0F, 0.8);
   }

   public void angles(LivingEntity entity, EntityModel<LivingEntity> model, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      if (model instanceof HumanoidModel<LivingEntity> bipedModel) {
         bipedModel.f_102808_.f_104203_ = 0.0F;
         bipedModel.f_102808_.f_104204_ = 0.0F;
         bipedModel.f_102808_.f_104205_ = 0.0F;
         bipedModel.f_102809_.m_104315_(bipedModel.f_102808_);
         bipedModel.f_102811_.f_104205_ = (float)Math.toRadians((double)0.0F);
         bipedModel.f_102811_.f_104203_ = (float)Math.toRadians((double)8.0F);
         bipedModel.f_102812_.f_104205_ = (float)Math.toRadians((double)0.0F);
         bipedModel.f_102812_.f_104203_ = (float)Math.toRadians((double)8.0F);
         bipedModel.f_102813_.f_104205_ = (float)Math.toRadians((double)0.0F);
         bipedModel.f_102813_.f_104203_ = (float)Math.toRadians((double)8.0F);
         bipedModel.f_102814_.f_104205_ = (float)Math.toRadians((double)0.0F);
         bipedModel.f_102814_.f_104203_ = (float)Math.toRadians((double)8.0F);
      }

   }
}
