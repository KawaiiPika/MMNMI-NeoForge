package xyz.pixelatedw.mineminenomi.mixins.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import java.util.Optional;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.effects.IBindBodyEffect;
import xyz.pixelatedw.mineminenomi.handlers.ability.MorphsHandler;

@Mixin({LivingEntityRenderer.class})
public class LivingEntityRendererMixin<T extends LivingEntity, M extends EntityModel<T>> {
   @Shadow
   protected M f_115290_;
   private Optional<Float> prevYaw = Optional.empty();

   @Inject(
      method = {"render"},
      at = {@At(
   value = "INVOKE",
   target = "Lnet/minecraft/client/model/EntityModel;setupAnim(Lnet/minecraft/world/entity/Entity;FFFFF)V",
   shift = Shift.AFTER
)},
      locals = LocalCapture.CAPTURE_FAILHARD
   )
   public void mineminenomi$postSetupAnim(T entity, float entityYaw, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int packedLight, CallbackInfo callback, boolean _shouldSit, float _yBodyYaw, float _yHeadYaw, float netHeadYaw, float headPitch, float ageInTicks, float limbSwingAmount, float limbSwing) {
      LivingEntityRenderer<T, M> renderer = (LivingEntityRenderer)this;
      MorphsHandler.postRenderHook(entity, this.f_115290_, renderer, matrixStack, ageInTicks, netHeadYaw, partialTicks);
      Animation.animationPostAngles(entity, this.f_115290_, matrixStack, buffer, packedLight, partialTicks, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
   }

   @Inject(
      method = {"render"},
      at = {@At("TAIL")}
   )
   public void mineminenomi$render(T entity, float entityYaw, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int packedLight, CallbackInfo callback) {
      if (entity instanceof Player var8) {
         ;
      }

   }

   @Inject(
      method = {"setupRotations"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void mineminenomi$applyRotations(T entity, PoseStack matrixStack, float ageInTicks, float yaw, float partialTicks, CallbackInfo callback) {
      LivingEntityRenderer<T, M> renderer = (LivingEntityRenderer)this;
      MorphsHandler.preRenderHook(entity, this.f_115290_, renderer, matrixStack, ageInTicks, yaw, partialTicks);
      boolean isRotationBlocked = entity.m_21220_().stream().anyMatch((instance) -> {
         MobEffect patt5471$temp = instance.m_19544_();
         boolean var10000;
         if (patt5471$temp instanceof IBindBodyEffect bodyBindingEffect) {
            if (bodyBindingEffect.isBlockingRotation()) {
               var10000 = true;
               return var10000;
            }
         }

         var10000 = false;
         return var10000;
      });
      if (isRotationBlocked) {
         if (!this.prevYaw.isPresent()) {
            this.prevYaw = Optional.of(yaw);
         }

         Pose pose = entity.m_20089_();
         if (pose != Pose.SLEEPING) {
            matrixStack.m_252781_(Axis.f_252436_.m_252977_(180.0F - (Float)this.prevYaw.get()));
         }

         callback.cancel();
      } else if (this.prevYaw.isPresent()) {
         this.prevYaw = Optional.empty();
      }

   }
}
