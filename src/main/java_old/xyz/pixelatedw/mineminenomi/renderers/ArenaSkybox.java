package xyz.pixelatedw.mineminenomi.renderers;

import com.mojang.blaze3d.platform.GlStateManager.DestFactor;
import com.mojang.blaze3d.platform.GlStateManager.SourceFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import java.awt.Color;
import net.minecraft.Util;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import xyz.pixelatedw.mineminenomi.api.WyHelper;

@OnlyIn(Dist.CLIENT)
public class ArenaSkybox {
   private final Minecraft minecraft;
   private float time;
   private int seed;
   private Color color;
   private ResourceLocation[] textures;
   private int animationSpeed;
   private int detailLevel;
   private float radius;
   private boolean isFullWrapping;
   private boolean isGlobal;
   private long lastTick;
   private int textureId;

   public ArenaSkybox() {
      this.color = Color.WHITE;
      this.textures = new ResourceLocation[]{ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/skyboxes/default.png")};
      this.animationSpeed = 10000;
      this.detailLevel = 8;
      this.radius = 1.0F;
      this.textureId = 0;
      this.minecraft = Minecraft.m_91087_();
   }

   public ArenaSkybox setTexture(boolean isFullWrapping, ResourceLocation... textures) {
      this.textures = textures;
      this.textureId = 0;
      this.isFullWrapping = isFullWrapping;
      return this;
   }

   public ArenaSkybox setAnimationSpeed(int animationSpeed) {
      this.animationSpeed = animationSpeed;
      return this;
   }

   public ArenaSkybox setColor(Color color) {
      this.color = color;
      return this;
   }

   public ArenaSkybox setAlpha(float alpha) {
      this.color = WyHelper.intToRGB(this.color.getRGB(), (int)alpha * 255);
      return this;
   }

   public ArenaSkybox setRadius(float radius) {
      this.radius = radius;
      return this;
   }

   public ArenaSkybox setDetailLevel(int detail) {
      this.detailLevel = detail;
      return this;
   }

   public ArenaSkybox setGlobal() {
      this.isGlobal = true;
      return this;
   }

   public void renderSphere(PoseStack matrixStack) {
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.depthMask(false);
      RenderSystem.enableDepthTest();
      this.renderActualSphere(matrixStack, 0.0F, 0.0F, 0.0F, true);
      RenderSystem.disableDepthTest();
      RenderSystem.depthMask(true);
      RenderSystem.disableBlend();
   }

   public void renderSphereInWorld(PoseStack poseStack, Camera info, double posX, double posY, double posZ) {
      double x = info.m_90583_().f_82479_;
      double y = info.m_90583_().f_82480_;
      double z = info.m_90583_().f_82481_;
      boolean isInisde = false;
      double distance = (x - posX) * (x - posX) + (z - posZ) * (z - posZ) + (y - posY) * (y - posY);
      if (distance < (double)(this.radius * this.radius)) {
         isInisde = true;
      }

      if (this.isGlobal) {
         x = (double)(-this.radius) - x + posX + (double)this.radius;
         y = (double)(-this.radius) - y + posY + (double)this.radius;
         z = (double)(-this.radius) - z + posZ + (double)this.radius;
      } else {
         x = (double)0.0F;
         y = (double)0.0F;
         z = (double)0.0F;
      }

      if (Minecraft.m_91085_()) {
         Minecraft.m_91087_().f_91060_.m_109829_().m_83947_(false);
      }

      RenderSystem.enableBlend();
      RenderSystem.enableDepthTest();
      RenderSystem.blendFuncSeparate(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.ONE, DestFactor.ONE_MINUS_SRC_ALPHA);
      RenderSystem.depthMask(Minecraft.m_91085_());
      poseStack.m_85836_();
      if (!isInisde) {
         if (Minecraft.m_91085_()) {
            RenderSystem.disableCull();
            RenderSystem.depthMask(false);
         } else {
            RenderSystem.disableCull();
         }
      }

      this.renderActualSphere(poseStack, (float)x, (float)y, (float)z, isInisde);
      if (!isInisde) {
         RenderSystem.enableCull();
      }

      RenderSystem.disableBlend();
      poseStack.m_85849_();
      RenderSystem.depthMask(true);
      if (Minecraft.m_91085_()) {
         Minecraft.m_91087_().m_91385_().m_83947_(false);
      }

   }

   private void renderActualSphere(PoseStack matrixStack, float x, float y, float z, boolean isInisde) {
      Tesselator tessellator = Tesselator.m_85913_();
      BufferBuilder vertex = tessellator.m_85915_();
      if (this.textures.length > 1) {
         long currentTick = Util.m_137550_();
         if (currentTick > this.lastTick + 100L) {
            this.lastTick = currentTick;
            ++this.textureId;
            this.textureId %= this.textures.length;
         }
      }

      RenderSystem.setShader(GameRenderer::m_172835_);
      RenderSystem.setShaderTexture(0, this.textures[this.textureId]);
      vertex.m_166779_(Mode.TRIANGLES, DefaultVertexFormat.f_85820_);
      float startU = 0.0F;
      float startV = 0.0F;
      float endU = ((float)Math.PI * 2F);
      float endV = (float)Math.PI;
      float stepU = (endU - startU) / (float)this.detailLevel;
      float stepV = (endV - startV) / (float)this.detailLevel;
      float animU = (float)((Util.m_137550_() + (long)this.seed) % (long)this.animationSpeed) / (float)this.animationSpeed;
      float animV = (float)(Util.m_137550_() % (long)this.animationSpeed) / (float)this.animationSpeed;
      float uAnim = animU + 1.0F;
      float vAnim = 0.0F;
      int red = this.color.getRed();
      int green = this.color.getGreen();
      int blue = this.color.getBlue();
      int alpha = this.color.getAlpha();
      Matrix4f projection = matrixStack.m_85850_().m_252922_();

      for(int i = 0; i < this.detailLevel; ++i) {
         for(int j = 0; j < this.detailLevel; ++j) {
            float u = (float)i * stepU + startU;
            float v = (float)j * stepV + startV;
            float un = i + 1 == this.detailLevel ? endU : (float)(i + 1) * stepU + startU;
            float vn = j + 1 == this.detailLevel ? endV : (float)(j + 1) * stepV + startV;
            Vector3f p0 = this.parametricSphere(u, v, this.radius);
            Vector3f p1 = this.parametricSphere(u, vn, this.radius);
            Vector3f p2 = this.parametricSphere(un, v, this.radius);
            Vector3f p3 = this.parametricSphere(un, vn, this.radius);
            float textureU = u / endU * (this.isFullWrapping ? 1.0F : this.radius);
            float textureV = v / endV * (this.isFullWrapping ? 1.0F : this.radius);
            float textureUN = un / endU * (this.isFullWrapping ? 1.0F : this.radius);
            float textureVN = vn / endV * (this.isFullWrapping ? 1.0F : this.radius);
            textureU += uAnim;
            textureUN += uAnim;
            if (isInisde) {
               this.vertexPosColorUVLight(vertex, projection, x - p0.x(), y - p0.y(), z - p0.z(), red, green, blue, alpha, textureU, textureV, 1);
               this.vertexPosColorUVLight(vertex, projection, x - p2.x(), y - p2.y(), z - p2.z(), red, green, blue, alpha, textureUN, textureV, 1);
               this.vertexPosColorUVLight(vertex, projection, x - p1.x(), y - p1.y(), z - p1.z(), red, green, blue, alpha, textureU, textureVN, 1);
               this.vertexPosColorUVLight(vertex, projection, x - p3.x(), y - p3.y(), z - p3.z(), red, green, blue, alpha, textureUN, textureVN, 1);
               this.vertexPosColorUVLight(vertex, projection, x - p1.x(), y - p1.y(), z - p1.z(), red, green, blue, alpha, textureU, textureVN, 1);
               this.vertexPosColorUVLight(vertex, projection, x - p2.x(), y - p2.y(), z - p2.z(), red, green, blue, alpha, textureUN, textureV, 1);
            } else {
               this.vertexPosColorUVLight(vertex, projection, x + p0.x(), y + p0.y(), z + p0.z(), red, green, blue, alpha, textureU, textureV, 1);
               this.vertexPosColorUVLight(vertex, projection, x + p2.x(), y + p2.y(), z + p2.z(), red, green, blue, alpha, textureUN, textureV, 1);
               this.vertexPosColorUVLight(vertex, projection, x + p1.x(), y + p1.y(), z + p1.z(), red, green, blue, alpha, textureU, textureVN, 1);
               this.vertexPosColorUVLight(vertex, projection, x + p3.x(), y + p3.y(), z + p3.z(), red, green, blue, alpha, textureUN, textureVN, 1);
               this.vertexPosColorUVLight(vertex, projection, x + p1.x(), y + p1.y(), z + p1.z(), red, green, blue, alpha, textureU, textureVN, 1);
               this.vertexPosColorUVLight(vertex, projection, x + p2.x(), y + p2.y(), z + p2.z(), red, green, blue, alpha, textureUN, textureV, 1);
            }
         }
      }

      tessellator.m_85914_();
   }

   private Vector3f parametricSphere(float u, float v, float r) {
      return new Vector3f(Mth.m_14089_(u) * Mth.m_14031_(v) * r, Mth.m_14089_(v) * r, Mth.m_14031_(u) * Mth.m_14031_(v) * r);
   }

   private void vertexPosColorUVLight(VertexConsumer buffer, Matrix4f projection, float x, float y, float z, int r, int g, int b, int a, float u, float v, int light) {
      buffer.m_252986_(projection, x, y, z).m_6122_(r, g, b, a).m_7421_(u, v).m_85969_(light).m_5752_();
   }
}
