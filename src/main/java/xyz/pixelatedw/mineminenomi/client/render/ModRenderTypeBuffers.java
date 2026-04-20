package xyz.pixelatedw.mineminenomi.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.resources.ResourceLocation;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xyz.pixelatedw.mineminenomi.client.render.buffers.HakiAuraBuffer;

public class ModRenderTypeBuffers {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final ResourceLocation HAKI_AURA_SHADER = ResourceLocation.fromNamespaceAndPath("mineminenomi", "shaders/post/haki_aura.json");
    private static ModRenderTypeBuffers INSTANCE;
    
    private PostChain hakiAuraShader;
    private HakiAuraBuffer hakiAuraBufferSource;

    public void initHakiAuraShader(Minecraft mc) {
        if (this.hakiAuraShader != null) {
            this.hakiAuraShader.close();
        }

        try {
            this.hakiAuraShader = new PostChain(mc.getTextureManager(), mc.getResourceManager(), mc.getMainRenderTarget(), HAKI_AURA_SHADER);
            this.hakiAuraShader.resize(mc.getWindow().getWidth(), mc.getWindow().getHeight());
            // Need to set EntityTarget equivalent but we can't easily without access transformers in 1.21.1
            // We'll rely on our modified AURA_TARGET state instead.
        } catch (IOException e) {
            LOGGER.error("Failed to load haki aura shader: {}", HAKI_AURA_SHADER, e);
        }
    }

    public static ModRenderTypeBuffers getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ModRenderTypeBuffers();
            Minecraft mc = Minecraft.getInstance();
            INSTANCE.hakiAuraBufferSource = new HakiAuraBuffer(mc.renderBuffers().bufferSource());
        }
        return INSTANCE;
    }

    public PostChain getHakiAuraPostChain() {
        return this.hakiAuraShader;
    }

    public HakiAuraBuffer getHakiAuraBuffer() {
        return this.hakiAuraBufferSource;
    }
}
