package xyz.pixelatedw.mineminenomi.animations;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.AgeableListModel;
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
import xyz.pixelatedw.mineminenomi.init.ModWeapons;

public class CrossedAttackAnimation extends Animation<LivingEntity, AgeableListModel<LivingEntity>> {
   public CrossedAttackAnimation(AnimationId<CrossedAttackAnimation> animId) {
      super(animId);
      this.setAnimationAngles(this::angles);
      this.setAnimationHeldItem(this::heldItem);
   }

   public void angles(LivingEntity entity, AgeableListModel<LivingEntity> model, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      ModelPart rightArm = null;
      ModelPart leftArm = null;
      if (model instanceof HumanoidModel<?> biped) {
         rightArm = biped.f_102811_;
         leftArm = biped.f_102812_;
      }

      float xRot = Mth.m_14179_((float)this.getTime() / 4.0F, -180.0F, -40.0F);
      xRot = Mth.m_14036_(xRot, -180.0F, -40.0F);
      if (rightArm != null) {
         rightArm.f_104203_ = (float)Math.toRadians((double)xRot);
         rightArm.f_104204_ = (float)Math.toRadians((double)0.0F);
         rightArm.f_104205_ = (float)Math.toRadians((double)-45.0F);
      }

      if (leftArm != null) {
         leftArm.f_104203_ = (float)Math.toRadians((double)xRot);
         leftArm.f_104204_ = (float)Math.toRadians((double)0.0F);
         leftArm.f_104205_ = (float)Math.toRadians((double)45.0F);
      }

   }

   public void heldItem(LivingEntity entity, ItemStack stack, ItemDisplayContext transformType, HumanoidArm handSide, PoseStack matrixStack, MultiBufferSource renderBuffer, int packedLight) {
      if (!stack.m_41619_() && stack.m_41720_() == ModWeapons.CAT_CLAWS.get()) {
         matrixStack.m_85837_(-0.11, -0.11, (double)0.0F);
         matrixStack.m_252781_(Axis.f_252403_.m_252977_(90.0F));
      }

   }
}
