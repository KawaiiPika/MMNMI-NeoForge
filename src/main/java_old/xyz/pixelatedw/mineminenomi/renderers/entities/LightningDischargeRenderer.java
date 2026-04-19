package xyz.pixelatedw.mineminenomi.renderers.entities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Matrix4f;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.entities.LightningDischargeEntity;
import xyz.pixelatedw.mineminenomi.init.ModRenderTypes;

public class LightningDischargeRenderer extends EntityRenderer<LightningDischargeEntity> {
   private List<LightningDischargeEntity.LightningPropieties> entities = new ArrayList();

   protected LightningDischargeRenderer(EntityRendererProvider.Context ctx) {
      super(ctx);
   }

   public void render(LightningDischargeEntity entity, float entityYaw, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int packedLight) {
      if (!Minecraft.m_91087_().m_91104_()) {
         if (entity.f_19797_ % entity.getUpdateRate() == 0) {
            this.entities.clear();
         }

         int lightnings = entity.getDensity();
         if (this.entities.isEmpty()) {
            for(int i = 0; i < lightnings; ++i) {
               LightningDischargeEntity.LightningPropieties e = new LightningDischargeEntity.LightningPropieties(entity, entity.getSeed(), (float)WyHelper.randomWithRange(0, 360), (float)WyHelper.randomWithRange(0, 180), entity.getLightningLength());
               this.entities.add(e);
            }
         }
      }

      Color color = new Color(entity.getColor());
      float alpha = (float)entity.getAlpha() / 255.0F;
      Color outlineColor = new Color(entity.getOutlineColor());
      float outlineAlpha = (float)entity.getOutlineAlpha() / 255.0F;
      VertexConsumer vertex = buffer.m_6299_(this.getRenderType(entity));
      matrixStack.m_85836_();
      matrixStack.m_85841_(entity.getSize() / 2.0F, entity.getSize() / 2.0F, entity.getSize() / 2.0F);
      this.entities.forEach((ex) -> renderLightningDischarge(entity, vertex, ex.random(), ex.length(), ex.xRot(), ex.yRot(), color, alpha, outlineColor, outlineAlpha, matrixStack, packedLight));
      matrixStack.m_85849_();
   }

   public static void renderLightningDischarge(LightningDischargeEntity entity, VertexConsumer vertex, long seed, float len, float pitch, float yaw, Color baseRGB, float baseAlpha, Color outlineRGB, float outlineAlpha, PoseStack matrixStack, int packedLight) {
      float partialTicks = 1.0F;
      Random random = new Random(seed);
      int maxAngle = 90;
      int segments = entity.getDetails();
      float lengthFactor = Math.min(((float)entity.f_19797_ + partialTicks) / 2.0F, 1.0F);
      float size = 0.01F;
      float length = len * lengthFactor;
      float maxDistance = length / (float)segments;
      float[] arr = new float[segments];
      int targetNumber = (int)((float)segments * lengthFactor);
      int renderTime = (entity.getAliveTicks() > 0 ? entity.getAliveTicks() : 100) / 2;
      Minecraft mc = Minecraft.m_91087_();
      float red = (float)baseRGB.getRed() / 255.0F;
      float green = (float)baseRGB.getGreen() / 255.0F;
      float blue = (float)baseRGB.getBlue() / 255.0F;
      float outlineRed = (float)outlineRGB.getRed() / 255.0F;
      float outlineGreen = (float)outlineRGB.getGreen() / 255.0F;
      float outlineBlue = (float)outlineRGB.getBlue() / 255.0F;
      float alpha = 1.0F;
      float alpha2 = 1.0F;

      for(int segment = 0; segment < arr.length; ++segment) {
         arr[segment] = segment == targetNumber ? length - maxDistance * (float)segment : maxDistance;
      }

      float[] offsetsY = new float[segments];
      float[] offsetsX = new float[segments];
      Matrix4f matrix4f = matrixStack.m_85850_().m_252922_();
      matrixStack.m_252781_(Axis.f_252392_.m_252977_(pitch));
      matrixStack.m_252781_(Axis.f_252529_.m_252977_(yaw));
      size = (float)((double)size + WyHelper.randomDouble() / (double)20.0F);
      float lastOffsetY = 0.0F;
      float lastOffsetX = 0.0F;

      for(int j = 0; j < segments; ++j) {
         offsetsY[j] = lastOffsetY;
         offsetsX[j] = lastOffsetX;
         lastOffsetY = (float)((double)lastOffsetY + Math.sin(Math.toRadians((double)((float)random.nextInt(maxAngle) - (float)maxAngle / 2.0F))));
         lastOffsetX = (float)((double)lastOffsetX + Math.sin(Math.toRadians((double)((float)random.nextInt(maxAngle) - (float)maxAngle / 2.0F))));
      }

      for(int j = 0; j < segments; ++j) {
         if ((!entity.isSplit() || (double)j % WyHelper.randomWithRange(1, 3) != (double)0.0F) && j >= entity.getSkipSegmnets()) {
            float y = offsetsY[j];
            float x = offsetsX[j];

            for(int depth = 1; depth < 4; ++depth) {
               float depthY = size / 2.0F + (float)depth * size;
               float depthZ = size / 2.0F + (float)depth * size;
               float endY = j == segments - 1 ? y : offsetsY[j + 1];
               float endX = j == segments - 1 ? x : offsetsX[j + 1];
               if (j <= targetNumber) {
                  drawQuad(matrix4f, vertex, y, x, j, endY, endX, red, green, blue, alpha, depthY, depthZ, false, false, true, false, maxDistance, arr[j], packedLight);
                  drawQuad(matrix4f, vertex, y, x, j, endY, endX, red, green, blue, alpha, depthY, depthZ, true, false, true, true, maxDistance, arr[j], packedLight);
                  drawQuad(matrix4f, vertex, y, x, j, endY, endX, red, green, blue, alpha, depthY, depthZ, true, true, false, true, maxDistance, arr[j], packedLight);
                  drawQuad(matrix4f, vertex, y, x, j, endY, endX, red, green, blue, alpha, depthY, depthZ, false, true, false, false, maxDistance, arr[j], packedLight);
               }

               if (entity.getOutlineColor() != 0) {
                  depthY = size / 2.0F + (float)depth * (size + 0.01F);
                  depthZ = size / 2.0F + (float)depth * (size + 0.01F);
                  if (j <= targetNumber) {
                     drawQuad(matrix4f, vertex, y, x, j, endY, endX, outlineRed, outlineGreen, outlineBlue, alpha2, depthY, depthZ, false, false, true, false, maxDistance, arr[j], packedLight);
                     drawQuad(matrix4f, vertex, y, x, j, endY, endX, outlineRed, outlineGreen, outlineBlue, alpha2, depthY, depthZ, true, false, true, true, maxDistance, arr[j], packedLight);
                     drawQuad(matrix4f, vertex, y, x, j, endY, endX, outlineRed, outlineGreen, outlineBlue, alpha2, depthY, depthZ, true, true, false, true, maxDistance, arr[j], packedLight);
                     drawQuad(matrix4f, vertex, y, x, j, endY, endX, outlineRed, outlineGreen, outlineBlue, alpha2, depthY, depthZ, false, true, false, false, maxDistance, arr[j], packedLight);
                  }
               }
            }
         }
      }

   }

   private static void drawQuad(Matrix4f matrix4f, VertexConsumer builder, float startY, float startX, int segmentIndex, float endY, float endX, float red, float green, float blue, float alpha, float firstOffset, float secondOffset, boolean negativeOffset, boolean bl2, boolean bl3, boolean bl4, float segmentLength, float segmentLengthAdded, int light) {
      float x1 = startX + (bl2 ? secondOffset : -secondOffset);
      float y1 = startY + (negativeOffset ? secondOffset : -secondOffset);
      float x2 = endX + (bl2 ? firstOffset : -firstOffset);
      float y2 = endY + (negativeOffset ? firstOffset : -firstOffset);
      float x3 = endX + (bl4 ? firstOffset : -firstOffset);
      float y3 = endY + (bl3 ? firstOffset : -firstOffset);
      float x4 = startX + (bl4 ? secondOffset : -secondOffset);
      float y4 = startY + (bl3 ? secondOffset : -secondOffset);
      float z1 = (float)segmentIndex * segmentLength;
      float z2 = z1 + segmentLengthAdded;
      builder.m_252986_(matrix4f, x1, y1, z1).m_85950_(red, green, blue, alpha).m_85969_(light).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5752_();
      builder.m_252986_(matrix4f, x2, y2, z2).m_85950_(red, green, blue, alpha).m_85969_(light).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5752_();
      builder.m_252986_(matrix4f, x3, y3, z2).m_85950_(red, green, blue, alpha).m_85969_(light).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5752_();
      builder.m_252986_(matrix4f, x4, y4, z1).m_85950_(red, green, blue, alpha).m_85969_(light).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5752_();
   }

   public boolean shouldRender(LightningDischargeEntity livingEntityIn, Frustum camera, double camX, double camY, double camZ) {
      return true;
   }

   @OnlyIn(Dist.CLIENT)
   public RenderType getRenderType(LightningDischargeEntity entity) {
      RenderType var10000;
      switch (entity.getRenderMode()) {
         case 1 -> var10000 = ModRenderTypes.LIGHTNING;
         case 2 -> var10000 = ModRenderTypes.getPosColorTexLightmap(this.getTextureLocation(entity));
         default -> var10000 = ModRenderTypes.ENERGY;
      }

      return var10000;
   }

   public ResourceLocation getTextureLocation(LightningDischargeEntity entity) {
      return null;
   }

   public static class Factory implements EntityRendererProvider<LightningDischargeEntity> {
      public EntityRenderer<LightningDischargeEntity> m_174009_(EntityRendererProvider.Context ctx) {
         return new LightningDischargeRenderer(ctx);
      }
   }
}
