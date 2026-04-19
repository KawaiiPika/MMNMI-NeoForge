package xyz.pixelatedw.mineminenomi.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.resources.ResourceLocation;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ModRenderTypeBuffers {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final ResourceLocation HAKI_AURA_SHADER = ResourceLocation.fromNamespaceAndPath("mineminenomi", "shaders/post/haki_aura.json");
    private static ModRenderTypeBuffers INSTANCE;
    
    private PostChain hakiAuraShader;

    public void initHakiAuraShader(Minecraft mc) {
        if (this.hakiAuraShader != null) {
            this.hakiAuraShader.close();
        }

        try {
            this.hakiAuraShader = new PostChain(mc.getTextureManager(), mc.getResourceManager(), mc.getMainRenderTarget(), HAKI_AURA_SHADER);
            this.hakiAuraShader.resize(mc.getWindow().getWidth(), mc.getWindow().getHeight());
        } catch (IOException e) {
            LOGGER.error("Failed to load haki aura shader: {}", HAKI_AURA_SHADER, e);
        }
    }

    public static ModRenderTypeBuffers getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ModRenderTypeBuffers();
        }
        return INSTANCE;
    }

    public PostChain getHakiAuraPostChain() {
        return this.hakiAuraShader;
    }
}
