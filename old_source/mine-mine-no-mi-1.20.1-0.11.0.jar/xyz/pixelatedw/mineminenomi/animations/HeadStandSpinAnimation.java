package xyz.pixelatedw.mineminenomi.animations;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;

public class HeadStandSpinAnimation extends BodyRotateAnimation {
   public HeadStandSpinAnimation(AnimationId<HeadStandSpinAnimation> id) {
      super(id, 70.0F);
      super.setAnimationPostAngles(this::setup);
      super.setAnimationAngles(this::angles);
   }

   public void setup(LivingEntity entity, AgeableListModel<LivingEntity> entityModel, PoseStack matrixStack, MultiBufferSource buffer, int packedLight, float partialTicks, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      super.setup(entity, entityModel, matrixStack, buffer, packedLight, partialTicks, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
      matrixStack.m_85837_((double)0.0F, (double)1.0F, (double)0.0F);
      matrixStack.m_252781_(Axis.f_252529_.m_252977_(180.0F));
   }

   public void angles(LivingEntity player, AgeableListModel<LivingEntity> model, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      if (model instanceof HumanoidModel humanoidModel) {
         ModelPart var10000 = humanoidModel.f_102812_;
         var10000.f_104200_ += 5.0F;
         var10000 = humanoidModel.f_102812_;
         var10000.f_104202_ -= 5.0F;
         humanoidModel.f_102812_.f_104203_ = (float)Math.toRadians((double)180.0F);
         humanoidModel.f_102812_.f_104205_ = (float)Math.toRadians((double)-101.25F);
         var10000 = humanoidModel.f_102811_;
         var10000.f_104200_ -= 5.0F;
         var10000 = humanoidModel.f_102811_;
         var10000.f_104202_ -= 5.0F;
         humanoidModel.f_102811_.f_104203_ = (float)Math.toRadians((double)180.0F);
         humanoidModel.f_102811_.f_104205_ = (float)Math.toRadians((double)101.25F);
         humanoidModel.f_102813_.f_104205_ = (float)Math.toRadians((double)67.5F);
         humanoidModel.f_102814_.f_104205_ = (float)Math.toRadians((double)-67.5F);
      }

   }
}
