package xyz.pixelatedw.mineminenomi.client.render.buffers;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import com.mojang.blaze3d.vertex.ByteBufferBuilder;

public class HakiAuraBuffer implements MultiBufferSource {
   private final MultiBufferSource.BufferSource bufferSource;
   private final MultiBufferSource.BufferSource hakiBufferSource = MultiBufferSource.immediate(new ByteBufferBuilder(256));
   private int red = 255;
   private int green = 255;
   private int blue = 255;
   private int alpha = 255;

   public HakiAuraBuffer(MultiBufferSource.BufferSource buffer) {
      this.bufferSource = buffer;
   }

   public VertexConsumer getBuffer(RenderType type) {
      VertexConsumer hakiVertexConsumer = this.bufferSource.getBuffer(type);
      return new ColorVertexBuilder(hakiVertexConsumer, this.red, this.green, this.blue, this.alpha);
   }

   public void setColor(int red, int green, int blue, int alpha) {
      this.red = red;
      this.green = green;
      this.blue = blue;
      this.alpha = alpha;
   }

   public void endBatch() {
      this.hakiBufferSource.endBatch();
   }

   static class ColorVertexBuilder implements VertexConsumer {
      private final VertexConsumer delegate;
      private int defaultRed, defaultGreen, defaultBlue, defaultAlpha;

      private ColorVertexBuilder(VertexConsumer delegate, int red, int green, int blue, int alpha) {
         this.delegate = delegate;
         this.defaultRed = red;
         this.defaultGreen = green;
         this.defaultBlue = blue;
         this.defaultAlpha = alpha;
      }

      public VertexConsumer addVertex(float pX, float pY, float pZ) {
         this.delegate.addVertex(pX, pY, pZ);
         return this;
      }

      public VertexConsumer setColor(int red, int green, int blue, int alpha) {
         // Override the given color with our custom color
         this.delegate.setColor(this.defaultRed, this.defaultGreen, this.defaultBlue, this.defaultAlpha);
         return this;
      }

      public VertexConsumer setUv(float u, float v) {
         this.delegate.setUv(u, v);
         return this;
      }

      public VertexConsumer setUv1(int u, int v) {
         this.delegate.setUv1(u, v);
         return this;
      }

      public VertexConsumer setUv2(int u, int v) {
         this.delegate.setUv2(u, v);
         return this;
      }

      public VertexConsumer setNormal(float x, float y, float z) {
         this.delegate.setNormal(x, y, z);
         return this;
      }
   }
}
