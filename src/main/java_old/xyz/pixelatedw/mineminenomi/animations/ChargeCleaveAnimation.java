package xyz.pixelatedw.mineminenomi.animations;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;

public class ChargeCleaveAnimation extends Animation<LivingEntity, HumanoidModel<LivingEntity>> {
   public ChargeCleaveAnimation(AnimationId<ChargeCleaveAnimation> animId) {
      super(animId);
      this.setAnimationAngles(this::angles);
      this.setAnimationHeldItem(this::heldItem);
   }

   public void angles(LivingEntity player, HumanoidModel<LivingEntity> model, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      float multiplier = 50.0F;
      float time = (float)this.getTime();
      float waitTime = 5.0F;
      double rightArmX = (double)-200.0F;
      double rightArmY = (double)20.0F;
      double leftArmX = (double)-130.0F;
      double leftArmY = (double)110.0F;
      if (time > waitTime) {
         double t2 = (double)((time - waitTime) * multiplier);
         rightArmX = Mth.m_14008_((double)-200.0F + t2, (double)-200.0F, (double)-20.0F);
         rightArmY = Mth.m_14008_((double)20.0F - t2 / (double)3.0F, (double)-10.0F, (double)20.0F);
         leftArmX = Mth.m_14008_((double)-130.0F + t2 / (double)2.0F, (double)-130.0F, (double)-50.0F);
         leftArmY = Mth.m_14008_((double)110.0F - t2 / (double)3.0F, (double)50.0F, (double)110.0F);
      }

      ModelPart var10000 = model.f_102811_;
      var10000.f_104202_ += 0.0F;
      var10000 = model.f_102811_;
      var10000.f_104203_ = (float)((double)var10000.f_104203_ + Math.toRadians(rightArmX));
      var10000 = model.f_102811_;
      var10000.f_104204_ = (float)((double)var10000.f_104204_ + Math.toRadians(rightArmY));
      var10000 = model.f_102812_;
      var10000.f_104200_ -= 2.0F;
      var10000 = model.f_102812_;
      var10000.f_104202_ -= 2.0F;
      var10000 = model.f_102812_;
      var10000.f_104203_ = (float)((double)var10000.f_104203_ + Math.toRadians(leftArmX));
      var10000 = model.f_102812_;
      var10000.f_104204_ = (float)((double)var10000.f_104204_ + Math.toRadians(leftArmY));
      var10000 = model.f_102810_;
      var10000.f_104204_ = (float)((double)var10000.f_104204_ + Math.toRadians((double)30.0F));
      ++model.f_102813_.f_104202_;
      var10000 = model.f_102813_;
      var10000.f_104204_ = (float)((double)var10000.f_104204_ + Math.toRadians((double)40.0F));
      --model.f_102814_.f_104202_;
      var10000 = model.f_102814_;
      var10000.f_104204_ = (float)((double)var10000.f_104204_ - Math.toRadians((double)20.0F));
   }

   public void heldItem(LivingEntity entity, ItemStack stack, ItemDisplayContext transformType, HumanoidArm handSide, PoseStack matrixStack, MultiBufferSource renderBuffer, int packedLight) {
      matrixStack.m_85837_(-0.1, -0.2, -0.05);
      matrixStack.m_252781_(Axis.f_252529_.m_252977_(-75.0F));
   }
}
