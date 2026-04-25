package xyz.pixelatedw.mineminenomi.client.render;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.RenderStateShard;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.Minecraft;
import xyz.pixelatedw.mineminenomi.client.render.ModRenderTypeBuffers;
import net.minecraft.client.renderer.PostChain;


public class ModRenderTypes extends RenderType {

    public static ShaderInstance auraHakiShaderInstance;
    protected static final RenderStateShard.ShaderStateShard AURA_HAKI_SHADER = new RenderStateShard.ShaderStateShard(() -> auraHakiShaderInstance);
    
    // In 1.21.1, the output state shard might need adjustment depending on how we handle the target
    public static final RenderStateShard.OutputStateShard AURA_TARGET = new RenderStateShard.OutputStateShard("mineminenomi:aura_target", 
        () -> {
            PostChain chain = ModRenderTypeBuffers.getInstance().getHakiAuraPostChain();
            if (chain != null) {
                chain.getTempTarget("final").bindWrite(false);
            } else {
                Minecraft.getInstance().getMainRenderTarget().bindWrite(false);
            }
        },
        () -> {
            Minecraft.getInstance().getMainRenderTarget().bindWrite(false);
        }
    );

    public ModRenderTypes(String name, VertexFormat format, VertexFormat.Mode drawMode, int bufferSize, boolean useDelegate, boolean needsSorting, Runnable setupTask, Runnable clearTask) {
        super(name, format, drawMode, bufferSize, useDelegate, needsSorting, setupTask, clearTask);
    }

    public static RenderType getAuraRenderType() {
        return RenderType.create("mineminenomi:aura", 
            DefaultVertexFormat.POSITION_TEX_COLOR, 
            VertexFormat.Mode.QUADS, 
            256, 
            true, 
            true, 
            RenderType.CompositeState.builder()
                .setShaderState(AURA_HAKI_SHADER)
                .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                .setDepthTestState(LEQUAL_DEPTH_TEST)
                .setOutputState(AURA_TARGET)
                .setWriteMaskState(COLOR_WRITE)
                .createCompositeState(true)
        );
    }

}
