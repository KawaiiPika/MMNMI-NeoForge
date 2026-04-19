package xyz.pixelatedw.mineminenomi.animations;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;

public class ShootSelfForwardAnimation extends Animation<LivingEntity, HumanoidModel<LivingEntity>> {
   public ShootSelfForwardAnimation(AnimationId<ShootSelfForwardAnimation> animId) {
      super(animId);
      this.setAnimationPostAngles(this::setup);
      this.setAnimationAngles(this::angles);
   }

   public void angles(LivingEntity entity, HumanoidModel<LivingEntity> entityModel, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      entityModel.f_102808_.f_104203_ = (float)Math.toRadians((double)0.0F);
      entityModel.f_102808_.f_104204_ = (float)Math.toRadians((double)0.0F);
      entityModel.f_102814_.f_104204_ = 0.0F;
      entityModel.f_102814_.f_104203_ = 0.0F;
      entityModel.f_102813_.f_104203_ = 0.0F;
      entityModel.f_102813_.f_104204_ = 0.0F;
      entityModel.f_102812_.f_104204_ = 0.0F;
      entityModel.f_102812_.f_104203_ = 0.0F;
      entityModel.f_102811_.f_104203_ = 0.0F;
      entityModel.f_102811_.f_104204_ = 0.0F;
   }

   public void setup(LivingEntity entity, EntityModel<LivingEntity> entityModel, PoseStack matrixStack, MultiBufferSource buffer, int packedLight, float partialTicks, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      matrixStack.m_252781_(Axis.f_252529_.m_252977_(Mth.m_14179_(partialTicks, entity.f_19860_, entity.m_146909_()) + 90.0F));
   }
}
