package xyz.pixelatedw.mineminenomi.animations.pteranodon;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;

public class PteraAssaultFlyAnimation extends Animation<LivingEntity, HumanoidModel<LivingEntity>> {
   private static final double THRESHOLD_1 = 0.2;
   private static final double THRESHOLD_2 = 0.6;

   public PteraAssaultFlyAnimation(AnimationId<PteraAssaultFlyAnimation> animId) {
      super(animId);
      this.setAnimationPostAngles(this::setup);
      this.setAnimationAngles(this::angles);
   }

   public void angles(LivingEntity entity, HumanoidModel<LivingEntity> model, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      double posXDiff = Math.abs(entity.f_19854_ - entity.m_20185_());
      double posZDiff = Math.abs(entity.f_19856_ - entity.m_20189_());
      Vec3 look = entity.m_20154_();
      Vec3 oldPos = new Vec3(entity.f_19854_, entity.f_19855_, entity.f_19856_);
      double movementDot = entity.m_20182_().m_82546_(oldPos).m_82541_().m_82526_(look);
      float angles = 0.0F;
      if (movementDot > (double)0.0F) {
         if (posXDiff >= 0.2 || posZDiff >= 0.2) {
            angles = (float)Math.toRadians((double)45.0F);
            model.f_102813_.f_104201_ = model.f_102814_.f_104201_ = 8.0F;
            model.f_102813_.f_104202_ = model.f_102814_.f_104202_ = 8.0F;
            model.f_102813_.f_104203_ = model.f_102814_.f_104203_ = angles + 0.2F;
         }

         if (posXDiff >= 0.6 || posZDiff >= 0.6) {
            angles = (float)Math.toRadians((double)60.0F);
            model.f_102813_.f_104201_ = model.f_102814_.f_104201_ = 5.8F;
            model.f_102813_.f_104202_ = model.f_102814_.f_104202_ = 10.0F;
            model.f_102813_.f_104203_ = model.f_102814_.f_104203_ = angles + 0.3F;
         }
      } else if (movementDot < (double)0.0F) {
         if (posXDiff >= 0.2 || posZDiff >= 0.2) {
            angles = (float)Math.toRadians((double)-45.0F);
            model.f_102813_.f_104201_ = model.f_102814_.f_104201_ = 8.0F;
            model.f_102813_.f_104202_ = model.f_102814_.f_104202_ = -8.0F;
            model.f_102813_.f_104203_ = model.f_102814_.f_104203_ = angles - 0.2F;
         }

         if (posXDiff >= 0.6 || posZDiff >= 0.6) {
            angles = (float)Math.toRadians((double)-60.0F);
            model.f_102813_.f_104201_ = model.f_102814_.f_104201_ = 5.8F;
            model.f_102813_.f_104202_ = model.f_102814_.f_104202_ = -10.0F;
            model.f_102813_.f_104203_ = model.f_102814_.f_104203_ = angles - 0.3F;
         }
      }

      model.f_102810_.f_104203_ = angles;
      if (entity.f_20921_ <= 0.0F) {
         model.f_102811_.f_104203_ = angles;
         model.f_102812_.f_104203_ = angles;
      }

   }

   public void setup(LivingEntity entity, EntityModel<LivingEntity> entityModel, PoseStack matrixStack, MultiBufferSource buffer, int packedLight, float partialTicks, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      double posXDiff = Math.abs(entity.f_19854_ - entity.m_20185_());
      double posZDiff = Math.abs(entity.f_19856_ - entity.m_20189_());
      double drop = (double)0.0F;
      if (posXDiff >= 0.2 || posZDiff >= 0.2) {
         drop = 0.3;
      }

      if (posXDiff >= 0.6 || posZDiff >= 0.6) {
         drop = 0.4;
      }

      matrixStack.m_85837_((double)0.0F, drop, (double)0.0F);
   }
}
