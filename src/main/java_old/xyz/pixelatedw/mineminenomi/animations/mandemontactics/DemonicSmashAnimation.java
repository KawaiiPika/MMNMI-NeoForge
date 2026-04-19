package xyz.pixelatedw.mineminenomi.animations.mandemontactics;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;

public class DemonicSmashAnimation extends Animation<LivingEntity, HumanoidModel<LivingEntity>> {
   public DemonicSmashAnimation(AnimationId<DemonicSmashAnimation> animId) {
      super(animId);
      this.setAnimationPostAngles(this::setup);
      this.setAnimationAngles(this::angles);
      this.setAnimationHeldItem(this::heldItem);
   }

   public void setup(LivingEntity entity, EntityModel<LivingEntity> entityModel, PoseStack matrixStack, MultiBufferSource buffer, int packedLight, float partialTicks, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
   }

   public void angles(LivingEntity entity, HumanoidModel<LivingEntity> model, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      boolean hasTonfaInMain = !entity.m_21205_().m_41619_() && entity.m_21205_().m_41720_() == ModWeapons.TONFA.get();
      boolean hasTonfaInOff = !entity.m_21206_().m_41619_() && entity.m_21206_().m_41720_() == ModWeapons.TONFA.get();
      double rotation = (double)(180L - this.getTime() * 40L);
      rotation = Math.max(rotation, (double)20.0F);
      if (hasTonfaInMain) {
         model.f_102811_.f_104203_ = (float)Math.toRadians(rotation);
         model.f_102811_.f_104205_ = (float)Math.toRadians((double)-90.0F);
         model.f_102811_.f_104204_ = (float)Math.toRadians((double)-180.0F);
      }

      if (hasTonfaInOff) {
         model.f_102812_.f_104203_ = (float)Math.toRadians((double)20.0F + rotation);
         model.f_102812_.f_104205_ = (float)Math.toRadians((double)-90.0F);
         model.f_102812_.f_104204_ = (float)Math.toRadians((double)-180.0F);
      }

   }

   public void heldItem(LivingEntity entity, ItemStack stack, ItemDisplayContext transformType, HumanoidArm handSide, PoseStack matrixStack, MultiBufferSource renderBuffer, int packedLight) {
      if (stack.m_41720_() == ModWeapons.TONFA.get()) {
         matrixStack.m_85837_((double)0.0F, (double)0.0F, 0.2);
         matrixStack.m_252781_(Axis.f_252529_.m_252977_(10.0F));
         matrixStack.m_252781_(Axis.f_252436_.m_252977_(180.0F));
      }
   }
}
