package xyz.pixelatedw.mineminenomi.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.minecraft.client.renderer.PostChain;

public class ModRenderTypeBuffers {
    private static final Logger LOGGER = LogManager.getLogger();
    private static ModRenderTypeBuffers INSTANCE;

    public void initHakiAuraShader(Minecraft mc) {
        // Obsolete in 1.21.1: replaced by RenderPipeline setup in rendering classes
    }

    public static ModRenderTypeBuffers getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ModRenderTypeBuffers();
        }
        return INSTANCE;
    }

    public PostChain getHakiAuraPostChain() {
        return null; // Obsolete in 1.21.1
    }
}
