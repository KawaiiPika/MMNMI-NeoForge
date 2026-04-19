package xyz.pixelatedw.mineminenomi.ui.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.init.ModResources;

public class BookSignSideButton extends Button {
   private boolean isPressed;
   private boolean isFlipped;
   private int textureWidth = 120;
   private ResourceLocation iconTexture;
   private float iconScale = 1.0F;
   private int iconPosX;
   private int iconPosY;

   public BookSignSideButton(int posX, int posY, int width, int height, Component string, Button.OnPress onPress) {
      super(posX, posY, width, height, string, onPress, Button.f_252438_);
   }

   public BookSignSideButton(int posX, int posY, int width, int height, Component string, Button.OnPress onPress, Button.CreateNarration narration) {
      super(posX, posY, width, height, string, onPress, narration);
   }

   public void m_87963_(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
      RenderSystem.enableDepthTest();
      graphics.m_280168_().m_85836_();
      float red = 1.0F;
      float green = 1.0F;
      float blue = 1.0F;
      if (this.f_93623_ && !this.isPressed) {
         if (this.f_93622_) {
            graphics.m_280168_().m_85837_((double)0.0F, (double)0.5F, (double)1.0F);
            blue = 0.6F;
            green = 0.6F;
            red = 0.6F;
         }
      } else {
         blue = 0.4F;
         green = 0.4F;
         red = 0.4F;
      }

      Minecraft minecraft = Minecraft.m_91087_();
      Font fontrenderer = minecraft.f_91062_;
      RenderSystem.setShaderColor(red, green, blue, this.f_93625_);
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.enableDepthTest();
      graphics.m_280163_(ModResources.BUTTON, this.m_252754_(), this.m_252907_(), (float)(this.isFlipped ? this.textureWidth : 0), 8.0F, this.textureWidth, 50, this.isFlipped ? -120 : 120, 50);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      if (this.iconTexture != null) {
         graphics.m_280168_().m_85836_();
         RenderSystem.enableBlend();
         graphics.m_280168_().m_252880_((float)this.iconPosX, (float)this.iconPosY, 151.0F);
         graphics.m_280168_().m_85841_(this.iconScale, this.iconScale, this.iconScale);
         RendererHelper.drawIcon(this.iconTexture, graphics.m_280168_(), 0.0F, 0.0F, 1.0F, 16.0F, 16.0F);
         graphics.m_280168_().m_85849_();
      }

      int color = this.getFGColor() | Mth.m_14167_(this.f_93625_ * 255.0F) << 24;
      int texLen = fontrenderer.m_92852_(this.m_6035_()) / 2;
      RendererHelper.drawStringWithBorder(fontrenderer, graphics, this.m_6035_(), this.m_252754_() + 10 + this.f_93618_ / 2 - texLen, this.m_252907_() + (this.f_93619_ - 8) / 2, color);
      graphics.m_280168_().m_85849_();
      RenderSystem.disableDepthTest();
   }

   public void setIsPressed(boolean isPressed) {
      this.isPressed = isPressed;
   }

   public void setFlipped() {
      this.isFlipped = true;
   }

   public void setTextureWidth(int width) {
      this.textureWidth = width;
   }

   public void setIcon(ResourceLocation texture, int iconPosX, int iconPosY, float scale) {
      this.iconTexture = texture;
      this.iconPosX = iconPosX;
      this.iconPosY = iconPosY;
      this.iconScale = scale;
   }
}
