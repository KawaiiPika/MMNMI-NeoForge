package xyz.pixelatedw.mineminenomi.client.gui.overlays;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.gui.LayeredDraw;

public class HaoshokuOverlay implements LayeredDraw.Layer {
    public static final HaoshokuOverlay INSTANCE = new HaoshokuOverlay();
    
    private float alpha = 0.0F;

    public void trigger() {
        this.alpha = 1.0F;
    }

    @Override
    public void render(GuiGraphics guiGraphics, net.minecraft.client.DeltaTracker deltaTracker) {
        float partialTick = deltaTracker.getGameTimeDeltaPartialTick(true);
        if (alpha <= 0) return;

        int width = Minecraft.getInstance().getWindow().getGuiScaledWidth();
        int height = Minecraft.getInstance().getWindow().getGuiScaledHeight();

        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 0.0F, 0.0F, alpha * 0.5F); // Red flash

        guiGraphics.fill(0, 0, width, height, 0x00FF0000 | ((int)(alpha * 127) << 24));

        RenderSystem.disableBlend();
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();

        alpha -= 0.05F * partialTick;
        if (alpha < 0) alpha = 0;
    }
}
