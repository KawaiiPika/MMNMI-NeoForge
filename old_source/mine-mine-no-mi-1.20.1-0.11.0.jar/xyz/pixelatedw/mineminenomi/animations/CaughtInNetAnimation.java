package xyz.pixelatedw.mineminenomi.animations;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;

public class CaughtInNetAnimation extends Animation<LivingEntity, EntityModel<LivingEntity>> {
   public CaughtInNetAnimation(AnimationId<CaughtInNetAnimation> animId) {
      super(animId);
      this.setAnimationPostAngles(this::setup);
   }

   public void setup(LivingEntity entity, EntityModel<LivingEntity> entityModel, PoseStack matrixStack, MultiBufferSource buffer, int packedLight, float partialTicks, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      matrixStack.m_252781_(Axis.f_252403_.m_252977_(90.0F));
      matrixStack.m_85837_(1.4, (double)-0.5F, (double)0.0F);
   }
}
