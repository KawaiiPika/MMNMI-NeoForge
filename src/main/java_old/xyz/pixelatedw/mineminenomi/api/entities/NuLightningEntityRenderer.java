package xyz.pixelatedw.mineminenomi.api.entities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import java.util.Arrays;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Matrix4f;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModRenderTypes;

public class NuLightningEntityRenderer extends EntityRenderer<NuLightningEntity> {
   private static final int MAX_DEPTH = 8;
   private static final float U_ARM_SIDE_MIN = 0.65F;
   private static final float U_ARM_SIDE_MAX = 0.68F;
   private static final float V_ARM_SIDE_MIN = 0.317F;
   private static final float V_ARM_SIDE_MAX = 0.5F;
   private static final float V_ARM_SIDE_DIFF = 0.183F;
   private static final float U_ARM_BACK_CAP_MIN = 0.687F;
   private static final float U_ARM_BACK_CAP_MAX = 0.718F;
   private static final float U_ARM_FRONT_CAP_MIN = 0.734F;
   private static final float U_ARM_FRONT_CAP_MAX = 0.781F;
   private static final float V_ARM_CAP_MIN = 0.25F;
   private static final float V_ARM_CAP_MAX = 0.312F;
   private static final float V_ARM_CAP_DIFF = 0.062000006F;
   private ResourceLocation texture;
   private boolean useArmSkin = false;
   private int renderMode = 0;

   protected NuLightningEntityRenderer(EntityRendererProvider.Context ctx) {
      super(ctx);
   }

   public void render(NuLightningEntity entity, float entityYaw, float partialTicks, PoseStack matrixStack, MultiBufferSource bufferIn, int packedLightIn) {
      matrixStack.m_85836_();
      this.renderLightning(entity, partialTicks, matrixStack, bufferIn, packedLightIn);
      matrixStack.m_85849_();
   }

   public void renderLightning(NuLightningEntity entity, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int packedLight) {
      if (entity.f_19797_ >= 1 && entity.getSegments() >= 0) {
         entity.getRandom().m_188584_(entity.seed);
         int startDepth = entity.getDepth();
         int angle = entity.getAngle();
         int segments = entity.getSegments();
         if (segments != 0) {
            int branches = entity.getBranches();
            float lengthFactor = Math.min(((float)entity.f_19797_ + partialTicks) / 2.0F, 1.0F);
            float length = entity.getLength() * lengthFactor;
            float size = entity.getSize();
            float maxDistance = entity.getLength() / (float)segments;
            float rotation = entity.getRotation();
            float[] segmentLengths = new float[segments];
            int targetNumber = (int)((float)segments * lengthFactor);
            float defAlpha = (float)entity.getAlpha() / 255.0F;
            float fadeAlpha = (float)entity.getFadeTime() / (float)entity.getStartFadeTime();
            float alpha = defAlpha;
            if (entity.getFadeTime() >= 0) {
               alpha = Math.min(fadeAlpha, defAlpha);
            }

            int segmentLength = entity.getSegmentLength();

            for(int segment = 0; segment < segments; ++segment) {
               if (segmentLength <= 0) {
                  segmentLengths[segment] = segment == targetNumber ? length - maxDistance * (float)segment : maxDistance;
               } else {
                  segmentLengths[segment] = (float)segmentLength;
               }
            }

            float[] offsetsX = new float[segments];
            float[] offsetsY = new float[segments];
            float[] segmentAlpha = new float[segments];
            Arrays.fill(segmentAlpha, alpha);
            VertexConsumer vertex = buffer.m_6299_(this.getRenderType(entity));
            Matrix4f matrix4f = matrixStack.m_85850_().m_252922_();
            matrixStack.m_252781_(Axis.f_252392_.m_252977_(entity.m_146908_()));
            matrixStack.m_252781_(Axis.f_252529_.m_252977_(entity.m_146909_()));
            matrixStack.m_252781_(Axis.f_252403_.m_252977_((float)((double)entity.f_19797_ * Math.PI * (double)rotation)));
            if (entity instanceof NuVerticalLightningEntity) {
               NuVerticalLightningEntity verticalBeam = (NuVerticalLightningEntity)entity;
               matrixStack.m_252880_(0.0F, 0.0F, (float)(-verticalBeam.getOriginPoint().m_123342_()));
            }

            float r = (float)entity.getRed() / 255.0F;
            float g = (float)entity.getGreen() / 255.0F;
            float b = (float)entity.getBlue() / 255.0F;

            for(int i = 0; i < branches; ++i) {
               float lastOffsetY = 0.0F;
               float lastOffsetX = 0.0F;

               for(int j = 0; j < segments; ++j) {
                  offsetsY[j] = lastOffsetY;
                  offsetsX[j] = lastOffsetX;
                  if (segments > 1 && angle > 0) {
                     lastOffsetY = (float)((double)lastOffsetY + Math.sin(Math.toRadians(WyHelper.randomWithRange(entity.getRandom(), -angle, angle) / (double)2.0F)));
                     lastOffsetX = (float)((double)lastOffsetX + Math.sin(Math.toRadians(WyHelper.randomWithRange(entity.getRandom(), -angle, angle) / (double)2.0F)));
                  }
               }

               for(int segmentIndex = 0; segmentIndex < segments; ++segmentIndex) {
                  float y = offsetsY[segmentIndex];
                  float x = offsetsX[segmentIndex];
                  if (entity.isTrailFading()) {
                     float segmentMaxAge = (float)(segmentIndex * entity.getFadeTime());
                     if ((float)(entity.f_19797_ * segments) > segmentMaxAge) {
                        float ageCount = Math.max((float)entity.f_19797_ % segmentMaxAge, segmentMaxAge);
                        segmentAlpha[segmentIndex] -= ageCount / segmentMaxAge * 2.0F;
                        if (Float.isNaN(segmentAlpha[segmentIndex]) || segmentAlpha[segmentIndex] <= 0.0F) {
                           continue;
                        }
                     }
                  }

                  for(int layer = startDepth; layer > 0; --layer) {
                     float depth = (float)(8 - layer) * size;
                     float endY = segmentIndex == segments - 1 ? y : offsetsY[segmentIndex + 1];
                     float endX = segmentIndex == segments - 1 ? x : offsetsX[segmentIndex + 1];
                     if (segmentIndex <= targetNumber) {
                        this.drawSides(matrix4f, vertex, y, x, segmentIndex, segments, endY, endX, entity.getRed(), entity.getGreen(), entity.getBlue(), segmentAlpha[segmentIndex], depth, depth, false, false, true, false, maxDistance, segmentLengths[segmentIndex], packedLight);
                        this.drawSides(matrix4f, vertex, y, x, segmentIndex, segments, endY, endX, entity.getRed(), entity.getGreen(), entity.getBlue(), segmentAlpha[segmentIndex], depth, depth, true, false, true, true, maxDistance, segmentLengths[segmentIndex], packedLight);
                        this.drawSides(matrix4f, vertex, y, x, segmentIndex, segments, endY, endX, entity.getRed(), entity.getGreen(), entity.getBlue(), segmentAlpha[segmentIndex], depth, depth, true, true, false, true, maxDistance, segmentLengths[segmentIndex], packedLight);
                        this.drawSides(matrix4f, vertex, y, x, segmentIndex, segments, endY, endX, entity.getRed(), entity.getGreen(), entity.getBlue(), segmentAlpha[segmentIndex], depth, depth, false, true, false, false, maxDistance, segmentLengths[segmentIndex], packedLight);
                     }
                  }
               }
            }

            this.drawCaps(matrix4f, vertex, offsetsX[0], offsetsY[0], size, length, r, g, b, alpha, packedLight);
         }
      }
   }

   private void drawSides(Matrix4f matrix4f, VertexConsumer builder, float startY, float startX, int segmentIndex, int maxSegments, float endY, float endX, int r, int g, int b, float alpha, float firstOffset, float secondOffset, boolean negativeOffset, boolean bl2, boolean bl3, boolean bl4, float segmentLength, float segmentLengthAdded, int light) {
      float red = (float)r / 255.0F;
      float green = (float)g / 255.0F;
      float blue = (float)b / 255.0F;
      alpha = Mth.m_14036_(alpha, 0.0F, 1.0F);
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
      float segmentFloat = (float)segmentIndex / (float)maxSegments;
      float u0 = 0.0F;
      float v0 = 0.0F;
      float u1 = 1.0F;
      float v1 = 1.0F;
      if (this.useArmSkin) {
         u0 = 0.65F;
         v0 = 0.317F + segmentFloat * 0.183F;
         u1 = 0.68F;
         v1 = Math.min(0.5F, v0 + segmentFloat * 0.183F);
      }

      builder.m_252986_(matrix4f, x1, y1, z1).m_85950_(red, green, blue, alpha).m_7421_(u0, v0).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5752_();
      builder.m_252986_(matrix4f, x2, y2, z2).m_85950_(red, green, blue, alpha).m_7421_(u0, v1).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5752_();
      builder.m_252986_(matrix4f, x3, y3, z2).m_85950_(red, green, blue, alpha).m_7421_(u1, v1).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5752_();
      builder.m_252986_(matrix4f, x4, y4, z1).m_85950_(red, green, blue, alpha).m_7421_(u1, v0).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5752_();
   }

   private void drawCaps(Matrix4f matrix4f, VertexConsumer builder, float startX, float startY, float size, float length, float r, float g, float b, float alpha, int light) {
      if (this.renderMode == 2) {
         int depth = 7;
         float x0 = startX - (float)depth * size;
         float y0 = startY - (float)depth * size;
         float x1 = x0 + (float)depth * size * 2.0F;
         float y1 = y0 + (float)depth * size * 2.0F;
         float u0 = 0.0F;
         float v0 = 0.0F;
         float u1 = 1.0F;
         float v1 = 1.0F;
         if (this.useArmSkin) {
            u0 = 0.687F;
            v0 = 0.312F;
            u1 = 0.718F;
            v1 = Math.min(0.312F, v0 + 0.062000006F);
         }

         builder.m_252986_(matrix4f, x0, y0, 0.0F).m_85950_(r, g, b, alpha).m_7421_(u0, v0).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5752_();
         builder.m_252986_(matrix4f, x0, y1, 0.0F).m_85950_(r, g, b, alpha).m_7421_(u0, v1).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5752_();
         builder.m_252986_(matrix4f, x1, y1, 0.0F).m_85950_(r, g, b, alpha).m_7421_(u1, v1).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5752_();
         builder.m_252986_(matrix4f, x1, y0, 0.0F).m_85950_(r, g, b, alpha).m_7421_(u1, v0).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5752_();
         if (this.useArmSkin) {
            u0 = 0.734F;
            v0 = 0.312F;
            u1 = 0.781F;
            v1 = Math.min(0.312F, v0 + 0.062000006F);
         }

         builder.m_252986_(matrix4f, x0, y0, length).m_85950_(r, g, b, alpha).m_7421_(u0, v0).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5752_();
         builder.m_252986_(matrix4f, x0, y1, length).m_85950_(r, g, b, alpha).m_7421_(u0, v1).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5752_();
         builder.m_252986_(matrix4f, x1, y1, length).m_85950_(r, g, b, alpha).m_7421_(u1, v1).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5752_();
         builder.m_252986_(matrix4f, x1, y0, length).m_85950_(r, g, b, alpha).m_7421_(u1, v0).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5752_();
      }
   }

   @OnlyIn(Dist.CLIENT)
   public RenderType getRenderType(NuLightningEntity entity) {
      RenderType var10000;
      switch (this.renderMode) {
         case 1 -> var10000 = ModRenderTypes.LIGHTNING;
         case 2 -> var10000 = ModRenderTypes.getPosColorTexLightmap(this.getTextureLocation(entity));
         default -> var10000 = ModRenderTypes.ENERGY;
      }

      return var10000;
   }

   public ResourceLocation getTextureLocation(NuLightningEntity entity) {
      if (this.useArmSkin) {
         if (entity.getOwner() != null) {
            LivingEntity var3 = entity.getOwner();
            if (var3 instanceof AbstractClientPlayer) {
               AbstractClientPlayer player = (AbstractClientPlayer)var3;
               return player.m_108560_();
            }
         }

         return DefaultPlayerSkin.m_118627_(entity.m_20148_());
      } else {
         return this.texture;
      }
   }

   public void setTexture(ResourceLocation texture) {
      this.texture = texture;
      this.renderMode = 2;
   }

   public void setUseArmSkin() {
      this.useArmSkin = true;
      this.renderMode = 2;
   }

   public void setRenderMode(int mode) {
      this.renderMode = mode;
   }

   public static class Factory implements EntityRendererProvider<NuLightningEntity> {
      private ResourceLocation texture;
      private boolean useArmSkin = false;
      private int renderMode = 0;

      public Factory setTexture(ResourceLocation texture) {
         this.texture = texture;
         this.renderMode = 2;
         return this;
      }

      public Factory setLightningMode() {
         this.renderMode = 1;
         return this;
      }

      public Factory setUseArmSkin() {
         this.useArmSkin = true;
         this.renderMode = 2;
         return this;
      }

      public EntityRenderer<NuLightningEntity> m_174009_(EntityRendererProvider.Context ctx) {
         NuLightningEntityRenderer renderer = new NuLightningEntityRenderer(ctx);
         renderer.setTexture(this.texture);
         if (this.useArmSkin) {
            renderer.setUseArmSkin();
         }

         renderer.setRenderMode(this.renderMode);
         return renderer;
      }
   }
}
