package xyz.pixelatedw.mineminenomi.mixin.client;

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
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.pixelatedw.mineminenomi.client.render.ModRenderTypeBuffers;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin {

    @Inject(method = "initOutline", at = @At("TAIL"))
    public void mineminenomi$initBuffers(CallbackInfo ci) {
        ModRenderTypeBuffers.getInstance().initHakiAuraShader(Minecraft.getInstance());
    }

    @Inject(method = "resize", at = @At("TAIL"))
    public void mineminenomi$resize(int width, int height, CallbackInfo ci) {
        if (ModRenderTypeBuffers.getInstance() != null && ModRenderTypeBuffers.getInstance().getHakiAuraPostChain() != null) {
            ModRenderTypeBuffers.getInstance().getHakiAuraPostChain().resize(width, height);
        }
    }

    @Inject(method = "renderLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/OutlineBufferSource;endOutlineBatch()V", shift = At.Shift.BEFORE))
    public void mineminenomi$processShaders(net.minecraft.client.DeltaTracker deltaTracker, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightTexture lightTexture, Matrix4f frustumMatrix, Matrix4f projectionMatrix, CallbackInfo ci) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (ModRenderTypeBuffers.getInstance().getHakiAuraBuffer() != null) {
            ModRenderTypeBuffers.getInstance().getHakiAuraBuffer().endBatch();
        }

        boolean shouldRenderAura = false;
        if (player != null) {
            PlayerStats stats = PlayerStats.get(player);
            if (stats != null && stats.isKenbunshokuActive()) {
                shouldRenderAura = true;
            }
        }

        if (shouldRenderAura && ModRenderTypeBuffers.getInstance().getHakiAuraPostChain() != null) {
            ModRenderTypeBuffers.getInstance().getHakiAuraPostChain().process(deltaTracker.getGameTimeDeltaPartialTick(true));
            Minecraft.getInstance().getMainRenderTarget().bindWrite(false);
        }
    }
}
