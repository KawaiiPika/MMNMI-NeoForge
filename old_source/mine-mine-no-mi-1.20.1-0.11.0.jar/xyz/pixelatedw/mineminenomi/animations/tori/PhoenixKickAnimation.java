package xyz.pixelatedw.mineminenomi.animations.tori;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.models.morphs.partials.PhoenixAssaultModel;

public class PhoenixKickAnimation extends Animation<LivingEntity, HumanoidModel<LivingEntity>> {
   public PhoenixKickAnimation(AnimationId<PhoenixKickAnimation> animId) {
      super(animId);
      this.setAnimationPostAngles(this::setup);
      this.setAnimationAngles(this::angles);
   }

   public void angles(LivingEntity entity, HumanoidModel<LivingEntity> model, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      model.f_102813_.f_104203_ = model.f_102813_.f_104204_ = model.f_102813_.f_104205_ = 0.0F;
      model.f_102814_.f_104203_ = model.f_102814_.f_104204_ = model.f_102814_.f_104205_ = 0.0F;
      model.f_102813_.f_104203_ = model.f_102813_.f_104203_ * 0.5F - (float)Math.PI + 2.3F;
      model.f_102813_.f_104204_ = model.f_102813_.f_104204_ * 0.5F - (float)Math.PI - 2.2F;
      model.f_102814_.f_104203_ = model.f_102814_.f_104203_ * 0.5F - (float)Math.PI + 2.5F;
      model.f_102814_.f_104203_ = model.f_102814_.f_104203_ * 0.5F - (float)Math.PI + 2.5F;
      if (model instanceof PhoenixAssaultModel phoenixModel) {
         phoenixModel.f_102814_.f_104204_ = phoenixModel.f_102814_.f_104204_ * 0.5F - (float)Math.PI + 2.1F;
         phoenixModel.f_102814_.f_104205_ = phoenixModel.f_102814_.f_104205_ * 0.5F - (float)Math.PI - 2.1F;
      }

   }

   public void setup(LivingEntity entity, EntityModel<LivingEntity> entityModel, PoseStack matrixStack, MultiBufferSource buffer, int packedLight, float partialTicks, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      matrixStack.m_252781_(Axis.f_252529_.m_252977_(-45.0F));
      matrixStack.m_252781_(Axis.f_252436_.m_252977_(-45.0F));
   }
}
