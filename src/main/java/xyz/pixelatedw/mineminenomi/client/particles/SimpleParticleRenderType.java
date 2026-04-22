package xyz.pixelatedw.mineminenomi.client.particles;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;

public class SimpleParticleRenderType implements ParticleRenderType {
    private final ResourceLocation texture;

    public SimpleParticleRenderType(ResourceLocation texture) {
        this.texture = texture;
    }

    @Override
    public com.mojang.blaze3d.vertex.BufferBuilder begin(com.mojang.blaze3d.vertex.Tesselator tesselator, net.minecraft.client.renderer.texture.TextureManager textureManager) {
        RenderSystem.depthMask(false);
        RenderSystem.setShaderTexture(0, this.texture);
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(770, 771);
        return tesselator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE);
    }

    @Override
    public String toString() {
        return "SIMPLE_PARTICLE_TYPE[" + texture + "]";
    }
}
