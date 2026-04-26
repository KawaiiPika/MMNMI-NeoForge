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

    private static final ResourceLocation MORPH_TRANSITION_SHADER = ResourceLocation.fromNamespaceAndPath("mineminenomi", "shaders/post/blur_transition.json");
    private PostChain morphTransitionShader;

    public void initHakiAuraShader(Minecraft mc) {
        if (this.hakiAuraShader != null) {
            this.hakiAuraShader.close();
        }

        try {
            this.hakiAuraShader = new PostChain(mc.getTextureManager(), mc.getResourceManager(), mc.getMainRenderTarget(), HAKI_AURA_SHADER);
            this.hakiAuraShader.resize(mc.getWindow().getWidth(), mc.getWindow().getHeight());
        } catch (Exception e) {
            LOGGER.error("Failed to load haki aura shader: {}", HAKI_AURA_SHADER, e);
        }

        if (this.morphTransitionShader != null) {
            this.morphTransitionShader.close();
        }

        try {
            this.morphTransitionShader = new PostChain(mc.getTextureManager(), mc.getResourceManager(), mc.getMainRenderTarget(), MORPH_TRANSITION_SHADER);
            this.morphTransitionShader.resize(mc.getWindow().getWidth(), mc.getWindow().getHeight());
        } catch (Exception e) {
            LOGGER.error("Failed to load morph transition shader: {}", MORPH_TRANSITION_SHADER, e);
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

    public PostChain getMorphTransitionPostChain() {
        return this.morphTransitionShader;
    }
}
