package xyz.pixelatedw.mineminenomi.client.particles;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;

public class SimpleParticleRenderType implements ParticleRenderType {
    public static final ParticleRenderType INSTANCE = new SimpleParticleRenderType(ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/particle/chiyu.png")); // Placeholder or dynamic if needed

    private ResourceLocation texture;

    public SimpleParticleRenderType(ResourceLocation texture) {
        this.texture = texture;
    }

    @Override
    public BufferBuilder begin(Tesselator tess, TextureManager textureManager) {
        RenderSystem.depthMask(true);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableCull();
        RenderSystem.setShader(GameRenderer::getParticleShader);
        RenderSystem.setShaderTexture(0, this.texture);
        return tess.begin(VertexFormat.Mode.QUADS, com.mojang.blaze3d.vertex.DefaultVertexFormat.PARTICLE);
    }
}
