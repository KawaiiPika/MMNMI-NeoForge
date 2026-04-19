package xyz.pixelatedw.mineminenomi.animations.ito;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;

public class ShufukuSagyoAnimation extends Animation<LivingEntity, EntityModel<LivingEntity>> {
   public ShufukuSagyoAnimation(AnimationId<ShufukuSagyoAnimation> animId) {
      super(animId);
      this.setAnimationAngles(this::angles);
   }

   public void angles(LivingEntity player, EntityModel<LivingEntity> entityModel, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      if (entityModel instanceof HumanoidModel model) {
         boolean hpCheck = (double)player.m_21223_() > (double)player.m_21233_() * 0.7;
         if (!hpCheck) {
            model.f_102811_.f_104203_ = model.f_102811_.f_104203_ * 0.5F - (float)Math.PI + 1.8F;
            model.f_102811_.f_104204_ = -1.1F;
            model.f_102811_.f_104205_ = -0.8F;
         }

         model.f_102812_.f_104203_ = model.f_102812_.f_104203_ * 0.5F - (float)Math.PI + 1.8F;
         model.f_102812_.f_104204_ = 1.1F;
         model.f_102812_.f_104205_ = 0.8F;
         if (model instanceof PlayerModel playerModel) {
            if (!hpCheck) {
               playerModel.f_103375_.m_104315_(model.f_102811_);
            }

            playerModel.f_103374_.m_104315_(model.f_102812_);
         }
      }

   }

   public void setup(LivingEntity player, PoseStack matrixStack, MultiBufferSource buffer, int packedLight, float rotationYaw, float partialTicks) {
   }
}
