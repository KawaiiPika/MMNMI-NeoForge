cat << 'INNER_EOF' > src/main/java/xyz/pixelatedw/mineminenomi/client/render/ModRenderTypes.java
package xyz.pixelatedw.mineminenomi.client.render;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.RenderStateShard;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.Minecraft;

public class ModRenderTypes extends RenderType {

    public static ShaderInstance auraHakiShaderInstance;
    protected static final RenderStateShard.ShaderStateShard AURA_HAKI_SHADER = new RenderStateShard.ShaderStateShard(() -> auraHakiShaderInstance);

    public static final RenderStateShard.OutputStateShard AURA_TARGET = new RenderStateShard.OutputStateShard("mineminenomi:aura_target",
        () -> {}, () -> {}
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
                .setWriteMaskState(COLOR_WRITE)
                .createCompositeState(true)
        );
    }

    public static final RenderType LIGHTNING = RenderType.create("mineminenomi:lightning",
        DefaultVertexFormat.POSITION_COLOR,
        VertexFormat.Mode.QUADS,
        256,
        false,
        true,
        RenderType.CompositeState.builder()
            .setShaderState(RENDERTYPE_LIGHTNING_SHADER)
            .setTransparencyState(LIGHTNING_TRANSPARENCY)
            .setOutputState(ITEM_ENTITY_TARGET)
            .setWriteMaskState(COLOR_WRITE)
            .createCompositeState(true)
    );

    public static final RenderType ENERGY = RenderType.create("mineminenomi:energy",
        DefaultVertexFormat.POSITION_COLOR,
        VertexFormat.Mode.QUADS,
        256,
        false,
        true,
        RenderType.CompositeState.builder()
            .setShaderState(RENDERTYPE_ENERGY_SWIRL_SHADER)
            .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
            .setOutputState(ITEM_ENTITY_TARGET)
            .setWriteMaskState(COLOR_WRITE)
            .createCompositeState(true)
    );

    public static RenderType getPosColorTexLightmap(ResourceLocation texture) {
        RenderType.CompositeState state = RenderType.CompositeState.builder()
            .setShaderState(POSITION_COLOR_TEX_LIGHTMAP_SHADER)
            .setTextureState(new RenderStateShard.TextureStateShard(texture, false, false))
            .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
            .setOutputState(ITEM_ENTITY_TARGET)
            .createCompositeState(true);

        return RenderType.create("mineminenomi:position_color_texture_lightmap", DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP, VertexFormat.Mode.QUADS, 256, true, true, state);
    }
}
INNER_EOF
