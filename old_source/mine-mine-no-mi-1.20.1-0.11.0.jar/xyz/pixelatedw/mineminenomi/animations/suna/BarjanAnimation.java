package xyz.pixelatedw.mineminenomi.animations.suna;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;

public class BarjanAnimation extends Animation<LivingEntity, HumanoidModel<LivingEntity>> {
   public BarjanAnimation(AnimationId<BarjanAnimation> animId) {
      super(animId);
      this.setAnimationPostAngles(this::setup);
      this.setAnimationAngles(this::angles);
   }

   public void angles(LivingEntity entity, HumanoidModel<LivingEntity> entityModel, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      double angle = (double)90.0F;
      if (this.getTime() > 20L) {
         angle -= (double)((this.getTime() - 20L) * 20L);
      }

      double yAngle = Mth.m_14008_(angle, (double)0.0F, (double)180.0F) - (double)130.0F;
      double xAngle = -Mth.m_14008_(angle, (double)0.0F, (double)180.0F) + (double)20.0F;
      if (!(angle <= (double)0.0F)) {
         entityModel.f_102811_.f_104204_ = (float)Math.toRadians(yAngle);
         entityModel.f_102811_.f_104203_ = (float)Math.toRadians(xAngle);
      }
   }

   public void setup(LivingEntity entity, EntityModel<LivingEntity> entityModel, PoseStack matrixStack, MultiBufferSource buffer, int packedLight, float partialTicks, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
   }
}
