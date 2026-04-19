package xyz.pixelatedw.mineminenomi.animations;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;

public class ChargeNetThrowAnimation extends Animation<LivingEntity, HumanoidModel<LivingEntity>> {
   public ChargeNetThrowAnimation(AnimationId<ChargeNetThrowAnimation> animId) {
      super(animId);
      this.setAnimationAngles(this::angles);
      this.setAnimationHeldItem(this::heldItem);
   }

   public void angles(LivingEntity entity, HumanoidModel<LivingEntity> model, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      ItemStack itemstack = entity.m_21120_(InteractionHand.MAIN_HAND);
      int useTime = itemstack.m_41779_() - entity.m_21212_();
      useTime = Mth.m_14045_(useTime, 0, 20);
      double bodyYRot = (double)20.0F + (double)useTime * 0.2;
      double rightArmXRot = (double)(-10 + useTime);
      double rightArmZRot = (double)5.0F + (double)useTime * 0.3;
      double leftArmXRot = (double)0.0F + (double)useTime * (double)0.5F;
      double leftArmZRot = (double)25.0F + (double)useTime * (double)0.5F;
      model.f_102810_.f_104204_ = (float)Math.toRadians(bodyYRot);
      model.f_102811_.f_104200_ = -4.0F;
      model.f_102811_.f_104205_ = (float)Math.toRadians(rightArmZRot);
      model.f_102811_.f_104203_ = (float)Math.toRadians(rightArmXRot);
      model.f_102812_.f_104200_ = 1.0F;
      model.f_102812_.f_104201_ = 1.75F;
      model.f_102812_.f_104202_ = -2.5F;
      model.f_102812_.f_104205_ = (float)Math.toRadians(leftArmZRot);
      model.f_102812_.f_104203_ = (float)Math.toRadians(leftArmXRot);
   }

   public void heldItem(LivingEntity entity, ItemStack stack, ItemDisplayContext transformType, HumanoidArm handSide, PoseStack matrixStack, MultiBufferSource renderBuffer, int packedLight) {
      matrixStack.m_85837_(-0.1, -0.3, -0.05);
      matrixStack.m_252781_(Axis.f_252436_.m_252977_(-5.0F));
   }
}
