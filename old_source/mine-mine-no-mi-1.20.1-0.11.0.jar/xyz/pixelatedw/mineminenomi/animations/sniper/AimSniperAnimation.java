package xyz.pixelatedw.mineminenomi.animations.sniper;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;

public class AimSniperAnimation extends Animation<LivingEntity, HumanoidModel<LivingEntity>> {
   public AimSniperAnimation(AnimationId<AimSniperAnimation> animId) {
      super(animId);
      this.setAnimationAngles(this::angles);
      this.setAnimationHeldItem(this::heldItem);
   }

   public void angles(LivingEntity entity, HumanoidModel<LivingEntity> model, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      boolean isFlintlock = !entity.m_21205_().m_41619_() && entity.m_21205_().m_41720_() == ModWeapons.FLINTLOCK.get();
      if (isFlintlock) {
         HumanoidArm side = entity.m_6844_(EquipmentSlot.MAINHAND).m_41720_() == ModWeapons.FLINTLOCK.get() ? HumanoidArm.RIGHT : HumanoidArm.LEFT;
         HumanoidArm opposide = entity.m_6844_(EquipmentSlot.MAINHAND).m_41720_() == ModWeapons.FLINTLOCK.get() ? HumanoidArm.LEFT : HumanoidArm.RIGHT;
         this.getArm(model, side).f_104204_ = -0.1F + model.f_102808_.f_104204_ - 0.4F;
         this.getArm(model, opposide).f_104204_ = 0.5F + model.f_102808_.f_104204_;
         this.getArm(model, side).f_104203_ = (-(float)Math.PI / 2F) + model.f_102808_.f_104203_;
         this.getArm(model, opposide).f_104203_ = (-(float)Math.PI / 2F) + model.f_102808_.f_104203_;
      } else {
         model.f_102811_.f_104204_ = 0.3F + model.f_102808_.f_104204_ - 0.4F;
         model.f_102812_.f_104204_ = 0.8F + model.f_102808_.f_104204_;
         model.f_102811_.f_104203_ = (-(float)Math.PI / 2F) + model.f_102808_.f_104203_;
         model.f_102812_.f_104203_ = (-(float)Math.PI / 2F) + model.f_102808_.f_104203_;
      }

   }

   public void heldItem(LivingEntity entity, ItemStack stack, ItemDisplayContext transformType, HumanoidArm handSide, PoseStack matrixStack, MultiBufferSource renderBuffer, int packedLight) {
      boolean isFlintlock = !entity.m_21205_().m_41619_() && entity.m_21205_().m_41720_() == ModWeapons.FLINTLOCK.get();
      if (isFlintlock) {
         matrixStack.m_85837_(-0.05, (double)0.0F, -0.15);
         matrixStack.m_252781_(Axis.f_252392_.m_252977_(20.0F));
      } else {
         matrixStack.m_85837_(-0.1, 0.1, 0.3);
         matrixStack.m_252781_(Axis.f_252436_.m_252977_(-75.0F));
         matrixStack.m_252781_(Axis.f_252529_.m_252977_(-90.0F));
      }

   }

   protected ModelPart getArm(HumanoidModel<LivingEntity> model, HumanoidArm pSide) {
      return pSide == HumanoidArm.LEFT ? model.f_102812_ : model.f_102811_;
   }
}
