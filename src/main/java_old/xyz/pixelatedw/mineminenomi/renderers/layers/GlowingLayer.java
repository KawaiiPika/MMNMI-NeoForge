package xyz.pixelatedw.mineminenomi.renderers.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import java.util.Random;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.LightLayer;
import org.joml.Matrix4f;

public class GlowingLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
   private static final float DISTANCE = (float)(Math.sqrt((double)3.0F) / (double)2.0F);
   private float size = 9.6F;
   private int color;

   public GlowingLayer(RenderLayerParent<T, M> parent, float size, int color) {
      super(parent);
      this.size = size;
      this.color = color;
   }

   public void render(PoseStack matrixStack, MultiBufferSource buffer, int packedLight, T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
      int lightLevel = entity.m_20193_().m_45517_(LightLayer.SKY, entity.m_20183_()) - entity.m_20193_().m_7445_();
      if (lightLevel > 7) {
         float rays = (float)(20 + lightLevel * 16);
         float randMovement = ((float)entity.f_19797_ + partialTicks) / 500.0F;
         Random rng = new Random(500L);
         VertexConsumer vertexBuilder = buffer.m_6299_(RenderType.m_110502_());
         Matrix4f matrix4f = matrixStack.m_85850_().m_252922_();
         int red = this.color >> 16 & 255;
         int green = this.color >> 8 & 255;
         int blue = this.color & 255;
         int alpha = 8 + lightLevel / 2;
         int red2 = Math.round((float)red * 0.8F);
         int green2 = Math.round((float)green * 0.8F);
         int blue2 = Math.round((float)blue * 0.8F);
         int alpha2 = Math.round((float)alpha * 0.4F);

         for(int i = 0; (float)i < rays; ++i) {
            matrixStack.m_252781_(Axis.f_252529_.m_252977_(rng.nextFloat() * 360.0F));
            matrixStack.m_252781_(Axis.f_252436_.m_252977_(rng.nextFloat() * 360.0F));
            matrixStack.m_252781_(Axis.f_252403_.m_252977_(rng.nextFloat() * 360.0F));
            matrixStack.m_252781_(Axis.f_252529_.m_252977_(rng.nextFloat() * 360.0F));
            matrixStack.m_252781_(Axis.f_252436_.m_252977_(rng.nextFloat() * 360.0F));
            matrixStack.m_252781_(Axis.f_252403_.m_252977_(rng.nextFloat() * 360.0F + randMovement * 90.0F));
            float f3 = this.size * rng.nextFloat();
            float f4 = this.size * rng.nextFloat();
            drawA(vertexBuilder, matrix4f, red, green, blue, alpha2);
            drawB(vertexBuilder, matrix4f, f3, f4, red2, green2, blue2, alpha2);
            drawC(vertexBuilder, matrix4f, f3, f4, red, green, blue, alpha);
            drawA(vertexBuilder, matrix4f, red, green, blue, alpha2);
            drawC(vertexBuilder, matrix4f, f3, f4, red2, green2, blue2, alpha2);
            drawD(vertexBuilder, matrix4f, f3, f4, red, green, blue, alpha);
            drawA(vertexBuilder, matrix4f, red, green, blue, alpha2);
            drawD(vertexBuilder, matrix4f, f3, f4, red, green, blue, alpha);
            drawB(vertexBuilder, matrix4f, f3, f4, red2, green2, blue2, alpha2);
         }
      }

   }

   private static void drawA(VertexConsumer vertexBuilder, Matrix4f matrix4f, int red, int green, int blue, int alpha) {
      vertexBuilder.m_252986_(matrix4f, 0.0F, 0.0F, 0.0F).m_6122_(red, green, blue, alpha).m_5752_();
      vertexBuilder.m_252986_(matrix4f, 0.0F, 0.0F, 0.0F).m_6122_(red, green, blue, alpha).m_5752_();
   }

   private static void drawB(VertexConsumer vertexBuilder, Matrix4f matrix4f, float y, float xz, int red, int green, int blue, int alpha) {
      vertexBuilder.m_252986_(matrix4f, -DISTANCE * xz, y, -0.5F * xz).m_6122_(red, green, blue, alpha).m_5752_();
   }

   private static void drawC(VertexConsumer vertexBuilder, Matrix4f matrix4f, float y, float xz, int red, int green, int blue, int alpha) {
      vertexBuilder.m_252986_(matrix4f, DISTANCE * xz, y, -0.5F * xz).m_6122_(red, green, blue, alpha).m_5752_();
   }

   private static void drawD(VertexConsumer vertexBuilder, Matrix4f matrix4f, float y, float z, int red, int green, int blue, int alpha) {
      vertexBuilder.m_252986_(matrix4f, 0.0F, y, z).m_6122_(red, green, blue, alpha).m_5752_();
   }
}
