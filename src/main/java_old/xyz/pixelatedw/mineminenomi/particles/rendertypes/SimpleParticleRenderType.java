package xyz.pixelatedw.mineminenomi.particles.rendertypes;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.api.NuWorld;
import xyz.pixelatedw.mineminenomi.init.ModRenderTypes;

public class SimpleParticleRenderType implements ParticleRenderType {
   private ResourceLocation texture;

   public SimpleParticleRenderType(ResourceLocation texture) {
      this.texture = texture;
   }

   public void m_6505_(BufferBuilder buffer, TextureManager textureManager) {
      RenderSystem.depthMask(true);
      if (NuWorld.isChallengeDimension(Minecraft.m_91087_().f_91073_)) {
         RenderSystem.depthMask(true);
      }

      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.disableCull();
      RenderSystem.setShader(GameRenderer::m_172829_);
      RenderSystem.setShaderTexture(0, this.texture);
      buffer.m_166779_(Mode.QUADS, ModRenderTypes.PARTICLE_POSITION_TEX_COLOR_LMAP);
   }

   public void m_6294_(Tesselator tess) {
      tess.m_85914_();
   }
}
