package xyz.pixelatedw.mineminenomi.client.renderers.entities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
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
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;


import xyz.pixelatedw.mineminenomi.api.entities.NuLightningEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuVerticalLightningEntity;
import xyz.pixelatedw.mineminenomi.client.render.ModRenderTypes;

@OnlyIn(Dist.CLIENT)
public class NuLightningEntityRenderer extends EntityRenderer<NuLightningEntity> {
   private static final int MAX_DEPTH = 8;
   private ResourceLocation texture;
   private boolean useArmSkin = false;
   private int renderMode = 0;

   public NuLightningEntityRenderer(EntityRendererProvider.Context ctx) {
      super(ctx);
   }

   @Override
   public void render(NuLightningEntity entity, float entityYaw, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int packedLight) {
      if (!entity.isVisualOnly() || entity.getOwner() != null && entity.getOwner().isAlive()) {
         if (entity.tickCount > 1) {
            VertexConsumer vertex = buffer.getBuffer(this.getRenderType(entity));
            float size = entity.getSize();
            float length = entity.getLength();
            float maxDistance = length / (float)entity.getSegments();
            int branches = entity.getBranches();
            int segments = entity.getSegments();
            int angle = entity.getAngle();
            int targetNumber = (int)((float)segments * Math.min(((float)entity.tickCount + partialTicks) / 2.0F, 1.0F));
            int startDepth = entity.getDepth();
            float[] offsetsY = new float[segments];
            float[] offsetsX = new float[segments];
            float[] segmentLengths = new float[segments];
            float[] segmentAlpha = new float[segments];
            float alpha = (float)entity.getAlpha() / 255.0F;

            for(int segment = 0; segment < segmentLengths.length; ++segment) {
               segmentLengths[segment] = segment == targetNumber ? length - maxDistance * (float)segment : maxDistance;
               segmentAlpha[segment] = alpha;
            }

            matrixStack.pushPose();
            matrixStack.mulPose(Axis.XP.rotationDegrees(entity.getXRot()));
            matrixStack.mulPose(Axis.YP.rotationDegrees(-entity.getYRot()));

            float rotation = entity.getRotation();
            if (rotation > 0.0F) {
               matrixStack.mulPose(Axis.ZP.rotationDegrees((float)((double)entity.tickCount * Math.PI * (double)rotation)));
            }

            if (entity instanceof NuVerticalLightningEntity verticalBeam) {
               matrixStack.translate(0.0F, 0.0F, (float)(-verticalBeam.getOriginPoint().getY()));
            }

            PoseStack.Pose pose = matrixStack.last();

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
                     lastOffsetY = (float)((double)lastOffsetY + Math.sin(Math.toRadians(entity.getRandom().nextInt(angle * 2) - angle) / 2.0));
                     lastOffsetX = (float)((double)lastOffsetX + Math.sin(Math.toRadians(entity.getRandom().nextInt(angle * 2) - angle) / 2.0));
                  }
               }

               for(int segmentIndex = 0; segmentIndex < segments; ++segmentIndex) {
                  float y = offsetsY[segmentIndex];
                  float x = offsetsX[segmentIndex];
                  if (entity.isTrailFading()) {
                     float segmentMaxAge = (float)(segmentIndex * entity.getFadeTime());
                     if ((float)(entity.tickCount * segments) > segmentMaxAge) {
                        float ageCount = Math.max((float)entity.tickCount % segmentMaxAge, segmentMaxAge);
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
                        this.drawSides(pose, vertex, y, x, segmentIndex, segments, endY, endX, entity.getRed(), entity.getGreen(), entity.getBlue(), segmentAlpha[segmentIndex], depth, depth, false, false, true, false, maxDistance, segmentLengths[segmentIndex], packedLight);
                        this.drawSides(pose, vertex, y, x, segmentIndex, segments, endY, endX, entity.getRed(), entity.getGreen(), entity.getBlue(), segmentAlpha[segmentIndex], depth, depth, true, false, true, true, maxDistance, segmentLengths[segmentIndex], packedLight);
                        this.drawSides(pose, vertex, y, x, segmentIndex, segments, endY, endX, entity.getRed(), entity.getGreen(), entity.getBlue(), segmentAlpha[segmentIndex], depth, depth, true, true, false, true, maxDistance, segmentLengths[segmentIndex], packedLight);
                        this.drawSides(pose, vertex, y, x, segmentIndex, segments, endY, endX, entity.getRed(), entity.getGreen(), entity.getBlue(), segmentAlpha[segmentIndex], depth, depth, false, true, false, false, maxDistance, segmentLengths[segmentIndex], packedLight);
                     }
                  }
               }
            }

            this.drawCaps(pose, vertex, offsetsX[0], offsetsY[0], size, length, r, g, b, alpha, packedLight);
            matrixStack.popPose();
         }
      }
   }

   private void drawSides(PoseStack.Pose pose, VertexConsumer builder, float startY, float startX, int segmentIndex, int maxSegments, float endY, float endX, int r, int g, int b, float alpha, float firstOffset, float secondOffset, boolean negativeOffset, boolean bl2, boolean bl3, boolean bl4, float segmentLength, float segmentLengthAdded, int light) {
      float red = (float)r / 255.0F;
      float green = (float)g / 255.0F;
      float blue = (float)b / 255.0F;
      alpha = Mth.clamp(alpha, 0.0F, 1.0F);
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

      builder.addVertex(pose, x1, y1, z1).setColor(red, green, blue, alpha).setUv(u0, v0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(pose, 0, 1, 0);
      builder.addVertex(pose, x2, y2, z2).setColor(red, green, blue, alpha).setUv(u0, v1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(pose, 0, 1, 0);
      builder.addVertex(pose, x3, y3, z2).setColor(red, green, blue, alpha).setUv(u1, v1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(pose, 0, 1, 0);
      builder.addVertex(pose, x4, y4, z1).setColor(red, green, blue, alpha).setUv(u1, v0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(pose, 0, 1, 0);
   }

   private void drawCaps(PoseStack.Pose pose, VertexConsumer builder, float startX, float startY, float size, float length, float r, float g, float b, float alpha, int light) {
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

         builder.addVertex(pose, x0, y0, 0.0F).setColor(r, g, b, alpha).setUv(u0, v0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(pose, 0, 1, 0);
         builder.addVertex(pose, x0, y1, 0.0F).setColor(r, g, b, alpha).setUv(u0, v1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(pose, 0, 1, 0);
         builder.addVertex(pose, x1, y1, 0.0F).setColor(r, g, b, alpha).setUv(u1, v1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(pose, 0, 1, 0);
         builder.addVertex(pose, x1, y0, 0.0F).setColor(r, g, b, alpha).setUv(u1, v0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(pose, 0, 1, 0);
         if (this.useArmSkin) {
            u0 = 0.734F;
            v0 = 0.312F;
            u1 = 0.781F;
            v1 = Math.min(0.312F, v0 + 0.062000006F);
         }

         builder.addVertex(pose, x0, y0, length).setColor(r, g, b, alpha).setUv(u0, v0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(pose, 0, 1, 0);
         builder.addVertex(pose, x0, y1, length).setColor(r, g, b, alpha).setUv(u0, v1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(pose, 0, 1, 0);
         builder.addVertex(pose, x1, y1, length).setColor(r, g, b, alpha).setUv(u1, v1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(pose, 0, 1, 0);
         builder.addVertex(pose, x1, y0, length).setColor(r, g, b, alpha).setUv(u1, v0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(pose, 0, 1, 0);
      }
   }

   @Override
   public ResourceLocation getTextureLocation(NuLightningEntity entity) {
      if (this.useArmSkin) {
         if (entity.getOwner() != null) {
            LivingEntity owner = (LivingEntity) entity.getOwner();
            if (owner instanceof AbstractClientPlayer player) {
               return player.getSkin().texture();
            }
         }
         return DefaultPlayerSkin.get(entity.getUUID()).texture();
      } else {
         return this.texture;
      }
   }

   public RenderType getRenderType(NuLightningEntity entity) {
      return switch (this.renderMode) {
         case 1 -> ModRenderTypes.LIGHTNING;
         case 2 -> ModRenderTypes.getPosColorTexLightmap(this.getTextureLocation(entity));
         default -> ModRenderTypes.ENERGY;
      };
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

      @Override
      public EntityRenderer<NuLightningEntity> create(EntityRendererProvider.Context ctx) {
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
