package xyz.pixelatedw.mineminenomi.animations;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;

public class TakedownKickAnimation extends Animation<LivingEntity, HumanoidModel<LivingEntity>> {
   public TakedownKickAnimation(AnimationId<TakedownKickAnimation> animId) {
      super(animId);
      this.setAnimationPostAngles(this::setup);
      this.setAnimationAngles(this::angles);
   }

   public void setup(LivingEntity entity, EntityModel<LivingEntity> entityModel, PoseStack matrixStack, MultiBufferSource buffer, int packedLight, float partialTicks, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      matrixStack.m_252781_(Axis.f_252403_.m_252977_(80.0F));
      matrixStack.m_252781_(Axis.f_252436_.m_252977_(180.0F - ((float)this.getTime() + partialTicks) * 40.0F));
   }

   public void angles(LivingEntity player, HumanoidModel<LivingEntity> model, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      model.f_102813_.f_104203_ = (float)Math.toRadians((double)-80.0F);
      model.f_102813_.f_104205_ = (float)Math.toRadians((double)80.0F);
      model.f_102810_.f_104204_ = (float)Math.toRadians((double)50.0F);
      ModelPart var10000 = model.f_102811_;
      var10000.f_104202_ += 2.0F;
      var10000 = model.f_102812_;
      var10000.f_104202_ -= 2.0F;
      model.f_102811_.f_104203_ = (float)Math.toRadians((double)-20.0F);
      model.f_102812_.f_104203_ = (float)Math.toRadians((double)-20.0F);
      model.f_102812_.f_104204_ = (float)Math.toRadians((double)40.0F);
      var10000 = model.f_102813_;
      var10000.f_104201_ -= 2.0F;
      var10000 = model.f_102814_;
      var10000.f_104201_ -= 4.0F;
      var10000 = model.f_102814_;
      var10000.f_104202_ -= 3.0F;
   }
}
