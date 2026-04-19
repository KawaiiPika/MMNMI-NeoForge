package xyz.pixelatedw.mineminenomi.renderers.buffers;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultedVertexConsumer;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;

public class HakiAuraBuffer implements MultiBufferSource {
   private final MultiBufferSource.BufferSource bufferSource;
   private final MultiBufferSource.BufferSource hakiBufferSource = MultiBufferSource.m_109898_(new BufferBuilder(256));
   private int red = 255;
   private int green = 255;
   private int blue = 255;
   private int alpha = 255;

   public HakiAuraBuffer(MultiBufferSource.BufferSource buffer) {
      this.bufferSource = buffer;
   }

   public VertexConsumer m_6299_(RenderType type) {
      VertexConsumer hakiVertexConsumer = this.bufferSource.m_6299_(type);
      ColorVertexBuilder vertexBuilder = new ColorVertexBuilder(hakiVertexConsumer, this.red, this.green, this.blue, this.alpha);
      return vertexBuilder;
   }

   public void setColor(int red, int green, int blue, int alpha) {
      this.red = red;
      this.green = green;
      this.blue = blue;
      this.alpha = alpha;
   }

   public void endBatch() {
      this.hakiBufferSource.m_109911_();
   }

   static class ColorVertexBuilder extends DefaultedVertexConsumer {
      private final VertexConsumer delegate;
      private double x;
      private double y;
      private double z;
      private float u;
      private float v;

      private ColorVertexBuilder(VertexConsumer delegate, int red, int green, int blue, int alpha) {
         this.delegate = delegate;
         super.m_7404_(red, green, blue, alpha);
      }

      public void m_7404_(int red, int green, int blue, int alpha) {
      }

      public VertexConsumer m_5483_(double pX, double pY, double pZ) {
         this.x = pX;
         this.y = pY;
         this.z = pZ;
         return this;
      }

      public VertexConsumer m_6122_(int red, int green, int blue, int alpha) {
         return this;
      }

      public VertexConsumer m_7421_(float u, float v) {
         this.u = u;
         this.v = v;
         return this;
      }

      public VertexConsumer m_7122_(int u, int v) {
         return this;
      }

      public VertexConsumer m_7120_(int u, int v) {
         return this;
      }

      public VertexConsumer m_5601_(float x, float y, float z) {
         return this;
      }

      public void m_5954_(float x, float y, float z, float red, float green, float blue, float alpha, float texU, float texV, int overlayUV, int lightmapUV, float normalX, float normalY, float normalZ) {
         this.delegate.m_5483_((double)x, (double)y, (double)z).m_6122_(this.f_85825_, this.f_85826_, this.f_85827_, this.f_85828_).m_7421_(texU, texV).m_86008_(overlayUV).m_85969_(lightmapUV).m_5601_(normalX, normalY, normalZ).m_5752_();
      }

      public void m_5752_() {
         this.delegate.m_5483_(this.x, this.y, this.z).m_6122_(this.f_85825_, this.f_85826_, this.f_85827_, this.f_85828_).m_7421_(this.u, this.v).m_5752_();
      }
   }
}
