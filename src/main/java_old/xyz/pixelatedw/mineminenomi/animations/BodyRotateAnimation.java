package xyz.pixelatedw.mineminenomi.animations;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;

public class BodyRotateAnimation extends Animation<LivingEntity, AgeableListModel<LivingEntity>> {
   protected float speed = 40.0F;

   public BodyRotateAnimation(AnimationId<? extends BodyRotateAnimation> animId, float speed) {
      super(animId);
      this.speed = speed;
      this.setAnimationPostAngles(this::setup);
   }

   public void setup(LivingEntity entity, AgeableListModel<LivingEntity> entityModel, PoseStack matrixStack, MultiBufferSource buffer, int packedLight, float partialTicks, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      float rot = (float)this.getTime() * this.speed + partialTicks;
      matrixStack.m_252781_(Axis.f_252436_.m_252977_(rot));
   }
}
