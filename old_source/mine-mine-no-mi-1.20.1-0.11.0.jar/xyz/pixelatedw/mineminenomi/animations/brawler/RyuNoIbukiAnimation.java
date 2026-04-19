package xyz.pixelatedw.mineminenomi.animations.brawler;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;

public class RyuNoIbukiAnimation extends Animation<LivingEntity, HumanoidModel<LivingEntity>> {
   public RyuNoIbukiAnimation(AnimationId<RyuNoIbukiAnimation> animId) {
      super(animId);
      this.setAnimationPostAngles(this::setup);
      this.setAnimationAngles(this::angles);
   }

   public void setup(LivingEntity entity, EntityModel<LivingEntity> entityModel, PoseStack matrixStack, MultiBufferSource buffer, int packedLight, float partialTicks, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      matrixStack.m_85837_((double)0.0F, 0.7, (double)0.0F);
      matrixStack.m_252781_(Axis.f_252529_.m_252977_(20.0F));
   }

   public void angles(LivingEntity player, HumanoidModel<LivingEntity> model, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      model.f_102813_.f_104201_ = 2.0F;
      model.f_102813_.f_104202_ = -4.0F;
      model.f_102813_.f_104203_ = (float)Math.toRadians((double)-20.0F);
      model.f_102814_.f_104201_ = 7.0F;
      model.f_102814_.f_104202_ = -7.0F;
      model.f_102814_.f_104203_ = (float)Math.toRadians((double)50.0F);
      model.f_102812_.f_104201_ = 2.0F;
      model.f_102812_.f_104202_ = -2.0F;
      model.f_102812_.f_104203_ = (float)Math.toRadians((double)-40.0F);
      model.f_102811_.f_104201_ = 2.0F;
      model.f_102811_.f_104202_ = -2.0F;
      model.f_102811_.f_104203_ = (float)Math.toRadians((double)-40.0F);
   }
}
