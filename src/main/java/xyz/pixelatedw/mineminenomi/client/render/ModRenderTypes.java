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

    public static final RenderType ENERGY = RenderType.create("mineminenomi:energy", DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP, Mode.QUADS, 256, false, true, CompositeState.builder().setShaderState(RENDERTYPE_ENERGY_SWIRL_SHADER).setTextureState(new RenderStateShard.TextureStateShard(ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/entity/energy.png"), false, false)).setTransparencyState(TRANSLUCENT_TRANSPARENCY).setCullState(NO_CULL).createCompositeState(true));
    public static final RenderType TRANSPARENT_COLOR = RenderType.create("mineminenomi:transparent_color", DefaultVertexFormat.POSITION_COLOR_LIGHTMAP, Mode.QUADS, 256, false, true, CompositeState.builder().setShaderState(RENDERTYPE_ENTITY_TRANSLUCENT_SHADER).setTransparencyState(TRANSLUCENT_TRANSPARENCY).setCullState(NO_CULL).setLightmapState(LIGHTMAP).createCompositeState(true));

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

    public static RenderType getAbilityBody(ResourceLocation texture) {
        return getAbilityBody(texture, false, false);
    }

    public static RenderType getAbilityBody(ResourceLocation texture, boolean equalDepthTest, boolean culling) {
        return RenderType.create("mineminenomi:ability_body", DefaultVertexFormat.NEW_ENTITY, Mode.QUADS, 256, true, true,
            CompositeState.builder()
                .setShaderState(RENDERTYPE_ENTITY_TRANSLUCENT_CULL_SHADER)
                .setTextureState(new RenderStateShard.TextureStateShard(texture, false, false))
                .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                .setCullState(culling ? CULL : NO_CULL)
                .setDepthTestState(equalDepthTest ? EQUAL_DEPTH_TEST : LEQUAL_DEPTH_TEST)
                .setLightmapState(LIGHTMAP)
                .setOverlayState(OVERLAY)
                .createCompositeState(true));
    }

    public static RenderType getAbilityHand(ResourceLocation texture) {
        return RenderType.create("mineminenomi:ability_hand", DefaultVertexFormat.NEW_ENTITY, Mode.QUADS, 256, true, true,
            CompositeState.builder()
                .setShaderState(RENDERTYPE_ENTITY_TRANSLUCENT_CULL_SHADER)
                .setTextureState(new RenderStateShard.TextureStateShard(texture, false, false))
                .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                .setCullState(NO_CULL)
                .setLightmapState(LIGHTMAP)
                .createCompositeState(true));
    }




}
