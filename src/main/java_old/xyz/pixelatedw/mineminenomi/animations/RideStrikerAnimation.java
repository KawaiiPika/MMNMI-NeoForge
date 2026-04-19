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
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.DevilFruitCapability;
import xyz.pixelatedw.mineminenomi.init.ModFruits;

public class RideStrikerAnimation extends Animation<LivingEntity, HumanoidModel<LivingEntity>> {
   public RideStrikerAnimation(AnimationId<RideStrikerAnimation> animId) {
      super(animId);
      this.setAnimationPostAngles(this::setup);
      this.setAnimationAngles(this::angles);
   }

   public void angles(LivingEntity entity, HumanoidModel<LivingEntity> model, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      if (DevilFruitCapability.hasDevilFruit(entity, ModFruits.MERA_MERA_NO_MI)) {
         ModelPart var10000 = model.f_102808_;
         var10000.f_104204_ = (float)((double)var10000.f_104204_ + Math.toRadians((double)45.0F));
         model.f_102814_.f_104203_ = 0.0F;
         model.f_102813_.f_104203_ = 0.0F;
         var10000 = model.f_102813_;
         var10000.f_104204_ = (float)((double)var10000.f_104204_ + Math.toRadians((double)40.0F));
      }
   }

   public void setup(LivingEntity entity, EntityModel<LivingEntity> entityModel, PoseStack matrixStack, MultiBufferSource buffer, int packedLight, float partialTicks, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      if (DevilFruitCapability.hasDevilFruit(entity, ModFruits.MERA_MERA_NO_MI)) {
         matrixStack.m_252781_(Axis.f_252436_.m_252977_(-50.0F));
      }
   }
}
