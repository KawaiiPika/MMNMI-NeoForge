package xyz.pixelatedw.mineminenomi.animations.swordsman;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.api.math.EasingFunctionHelper;

public class IttoryuChargeAnimation extends Animation<LivingEntity, HumanoidModel<LivingEntity>> {
   public IttoryuChargeAnimation(AnimationId<IttoryuChargeAnimation> animId) {
      super(animId);
      this.setAnimationAngles(this::angles);
      this.setAnimationHeldItem(this::heldItem);
   }

   public void angles(LivingEntity entity, HumanoidModel<LivingEntity> model, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      float max = (float)Math.min(this.getAnimationInitialDuration(), 10) * 0.8F;
      float c = Math.min((float)this.getTime(), max) / max;
      float ease = EasingFunctionHelper.easeOutCubic(c);
      model.f_102808_.f_104201_ = 3.0F * ease;
      model.f_102808_.f_104202_ = -4.5F * ease;
      model.f_102810_.f_104202_ = -5.0F * ease;
      model.f_102810_.f_104201_ = 3.0F * ease;
      model.f_102810_.f_104203_ = (float)Math.toRadians((double)20.0F) * ease;
      model.f_102811_.f_104205_ = (float)Math.toRadians((double)-5.0F) * ease;
      model.f_102811_.f_104203_ = (float)Math.toRadians((double)-10.0F) * ease;
      model.f_102811_.f_104202_ = -4.0F * ease;
      model.f_102811_.f_104201_ = 3.0F * ease;
      model.f_102812_.f_104202_ = -5.0F;
      ModelPart var10000 = model.f_102812_;
      var10000.f_104201_ += 2.0F;
      var10000 = model.f_102812_;
      var10000.f_104200_ -= 2.0F;
      model.f_102812_.f_104203_ = (float)Math.toRadians((double)-40.0F) * ease;
      model.f_102812_.f_104205_ = (float)Math.toRadians((double)20.0F) * ease;
      model.f_102812_.f_104204_ = (float)Math.toRadians((double)40.0F) * ease;
      model.f_102813_.f_104202_ = -2.0F * ease;
      model.f_102813_.f_104203_ = (float)Math.toRadians((double)-5.0F) * ease;
      model.f_102814_.f_104202_ = -0.5F * ease;
      model.f_102814_.f_104203_ = (float)Math.toRadians((double)15.0F) * ease;
      model.f_102814_.f_104204_ = (float)Math.toRadians((double)-10.0F) * ease;
      model.f_102814_.f_104205_ = (float)Math.toRadians((double)-5.0F) * ease;
   }

   public void heldItem(LivingEntity entity, ItemStack stack, ItemDisplayContext transformType, HumanoidArm handSide, PoseStack matrixStack, MultiBufferSource renderBuffer, int packedLight) {
      float max = (float)Math.min(this.getAnimationInitialDuration(), 10) * 0.8F;
      float c = Math.min((float)this.getTime(), max) / max;
      float ease = EasingFunctionHelper.easeOutCubic(c);
      matrixStack.m_85837_((double)0.0F, -0.3, 0.1);
      matrixStack.m_252781_(Axis.f_252403_.m_252977_(-180.0F * ease));
      matrixStack.m_252781_(Axis.f_252529_.m_252977_(30.0F * ease));
   }
}
