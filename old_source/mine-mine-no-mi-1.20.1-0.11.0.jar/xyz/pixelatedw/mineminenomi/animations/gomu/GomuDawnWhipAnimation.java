package xyz.pixelatedw.mineminenomi.animations.gomu;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.animations.BodyRotateAnimation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;

public class GomuDawnWhipAnimation extends BodyRotateAnimation {
   public GomuDawnWhipAnimation(AnimationId<GomuDawnWhipAnimation> animId) {
      super(animId, 75.0F);
      this.setAnimationAngles(this::angles);
      this.setAnimationPostAngles(this::setup);
   }

   public void angles(LivingEntity player, AgeableListModel<LivingEntity> model, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      if (model instanceof HumanoidModel humanoidModel) {
         humanoidModel.f_102812_.f_104205_ = (float)Math.toRadians((double)-90.0F);
         humanoidModel.f_102811_.f_104205_ = (float)Math.toRadians((double)90.0F);
         humanoidModel.f_102813_.f_104205_ = (float)Math.toRadians((double)90.0F);
         humanoidModel.f_102814_.f_104205_ = (float)Math.toRadians((double)-90.0F);
      }

   }

   public void setup(LivingEntity entity, AgeableListModel<LivingEntity> entityModel, PoseStack matrixStack, MultiBufferSource buffer, int packedLight, float partialTicks, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      float rot = (float)(Math.sin((double)((float)this.getTime() * 0.1F) + Math.PI) * (double)16.0F);
      matrixStack.m_252781_(Axis.f_252436_.m_252961_(rot));
   }
}
