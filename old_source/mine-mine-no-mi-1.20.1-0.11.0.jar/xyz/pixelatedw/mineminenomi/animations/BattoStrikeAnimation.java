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

public class BattoStrikeAnimation extends Animation<LivingEntity, HumanoidModel<LivingEntity>> {
   public BattoStrikeAnimation(AnimationId<BattoStrikeAnimation> animId) {
      super(animId);
      this.setAnimationAngles(this::angles);
      this.setAnimationHeldItem(this::heldItem);
   }

   public void angles(LivingEntity player, HumanoidModel<LivingEntity> model, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      float time = (float)this.getTime();
      float xRot = 70.0F - time * 20.0F;
      xRot = Mth.m_14036_(xRot, -50.0F, 50.0F);
      model.f_102811_.f_104200_ = -4.0F;
      model.f_102811_.f_104201_ = 4.0F;
      model.f_102811_.f_104202_ = 1.5F;
      model.f_102811_.f_104203_ = (float)Math.toRadians((double)xRot);
      model.f_102811_.f_104204_ = (float)Math.toRadians((double)20.0F);
      model.f_102811_.f_104205_ = (float)Math.toRadians((double)40.0F);
      ModelPart var10000 = model.f_102812_;
      var10000.f_104200_ = (float)((double)var10000.f_104200_ - (double)5.0F);
      model.f_102812_.f_104201_ = 2.0F;
      var10000 = model.f_102812_;
      var10000.f_104202_ = (float)((double)var10000.f_104202_ - (double)3.0F);
      model.f_102812_.f_104203_ = (float)Math.toRadians((double)(xRot + 15.0F));
      model.f_102812_.f_104204_ = (float)Math.toRadians((double)20.0F);
      model.f_102812_.f_104205_ = (float)Math.toRadians((double)50.0F);
   }

   public void heldItem(LivingEntity entity, ItemStack stack, ItemDisplayContext transformType, HumanoidArm handSide, PoseStack matrixStack, MultiBufferSource renderBuffer, int packedLight) {
      float time = (float)this.getTime();
      float zRot = -95.0F + time * 20.0F;
      zRot = Mth.m_14036_(zRot, -100.0F, -45.0F);
      matrixStack.m_85837_((double)0.0F, (double)0.0F, (double)0.0F);
      matrixStack.m_252781_(Axis.f_252403_.m_252977_(zRot));
      matrixStack.m_252781_(Axis.f_252529_.m_252977_(-35.0F));
   }
}
