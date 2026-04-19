package xyz.pixelatedw.mineminenomi.mixins.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.OutlineBufferSource;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.MinecraftForge;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.pixelatedw.mineminenomi.abilities.haki.KenbunshokuHakiAuraAbility;
import xyz.pixelatedw.mineminenomi.api.NuWorld;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.events.entity.OutlineColorEvent;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.handlers.protection.AbilityProtectionHandler;
import xyz.pixelatedw.mineminenomi.handlers.world.ChallengesHandler;
import xyz.pixelatedw.mineminenomi.init.ModRenderTypeBuffers;

@Mixin({LevelRenderer.class})
public class LevelRendererMixin {
   @Shadow
   @Final
   private Minecraft f_109461_;
   @Shadow
   private ClientLevel f_109465_;

   @Inject(
      method = {"initOutline"},
      at = {@At("TAIL")}
   )
   public void mineminenomi$initBuffers(CallbackInfo ci) {
      ModRenderTypeBuffers.getInstance().initHakiAuraShader();
   }

   @Inject(
      method = {"shouldShowEntityOutlines"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void mineminenomi$shouldShowEntityOutlines(CallbackInfoReturnable<Boolean> cir) {
      if (Minecraft.m_91087_().f_91060_.m_109827_() != null && ModRenderTypeBuffers.getInstance().getHakiAuraPostChain() != null && Minecraft.m_91087_().f_91074_ != null) {
         cir.setReturnValue(true);
      }
   }

   @Inject(
      method = {"resize"},
      at = {@At("TAIL")}
   )
   public void mineminenomi$resize(int width, int height, CallbackInfo ci) {
      if (ModRenderTypeBuffers.getInstance() != null) {
         if (ModRenderTypeBuffers.getInstance().getHakiAuraPostChain() != null) {
            ModRenderTypeBuffers.getInstance().getHakiAuraPostChain().m_110025_(width, height);
         }

      }
   }

   @Inject(
      method = {"renderLevel"},
      at = {@At(
   value = "INVOKE",
   target = "Lnet/minecraft/client/renderer/OutlineBufferSource;endOutlineBatch()V",
   shift = Shift.BEFORE
)}
   )
   public void mineminenomi$processShaders(PoseStack matrixStack, float partialTicks, long finishTimeNano, boolean drawBlockOutline, Camera activeRenderInfo, GameRenderer gameRenderer, LightTexture lightmap, Matrix4f projection, CallbackInfo ci) {
      LocalPlayer player = Minecraft.m_91087_().f_91074_;
      ModRenderTypeBuffers.getInstance().getHakiAuraBuffer().endBatch();
      boolean shouldRenderAura = ModRenderTypeBuffers.getInstance().getHakiAuraPostChain() != null && player != null && (Boolean)AbilityCapability.get(player).map((props) -> (KenbunshokuHakiAuraAbility)props.getEquippedAbility((AbilityCore)KenbunshokuHakiAuraAbility.INSTANCE.get())).map(Ability::isContinuous).orElse(false);
      if (shouldRenderAura) {
         ModRenderTypeBuffers.getInstance().getHakiAuraPostChain().m_110023_(partialTicks);
         Minecraft.m_91087_().m_91385_().m_83947_(false);
      }

   }

   @Inject(
      method = {"renderLevel"},
      at = {@At(
   value = "INVOKE",
   target = "Lnet/minecraft/client/renderer/LevelRenderer;renderWorldBorder(Lnet/minecraft/client/Camera;)V",
   shift = Shift.AFTER
)}
   )
   private void mineminenomi$renderBounds(PoseStack matrixStack, float partialTicks, long finishTimeNano, boolean drawBlockOutline, Camera camera, GameRenderer gameRenderer, LightTexture lightmap, Matrix4f projection, CallbackInfo ci) {
      ChallengesHandler.Client.renderMainSkysphere(matrixStack, camera);
      AbilityProtectionHandler.renderAbilityProtections(matrixStack, camera);
   }

   @Inject(
      method = {"renderEntity"},
      at = {@At("HEAD")}
   )
   public void mineminenomi$renderEntity(Entity entity, double camX, double camY, double camZ, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, CallbackInfo callback) {
      if (buffer instanceof OutlineBufferSource outlineBuffer) {
         OutlineColorEvent event = new OutlineColorEvent(entity);
         MinecraftForge.EVENT_BUS.post(event);
         outlineBuffer.m_109929_(event.getColor().getRed(), event.getColor().getGreen(), event.getColor().getBlue(), event.getColor().getAlpha());
      }

   }

   @Inject(
      method = {"renderClouds"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void mineminenomi$renderClouds(PoseStack matrixStack, Matrix4f matrix, float partialTick, double camX, double camY, double camZ, CallbackInfo callback) {
      if (NuWorld.isChallengeDimension(this.f_109465_)) {
         callback.cancel();
      }
   }

   @ModifyVariable(
      method = {"renderSnowAndRain"},
      at = @At("STORE"),
      ordinal = 1
   )
   private float mineminenomi$getRainLevel(float rainLevel) {
      return rainLevel;
   }
}
