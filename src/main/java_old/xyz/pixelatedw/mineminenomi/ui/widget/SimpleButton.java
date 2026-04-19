package xyz.pixelatedw.mineminenomi.ui.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;

public class SimpleButton extends Button {
   private boolean isPressed;
   private boolean textOnly;
   private float fontSize = 1.0F;
   private int defaultTextColor = 16777215;
   private int outlineColor = 0;
   private int defaultTopGradientColor = -5000269;
   private int defaultBottomGradientColor = -7105645;
   private int hoverTextColor = 65365;
   private int hoverTopGradientColor = -5000269;
   private int hoverBottomGradientColor = -11513776;
   private int pressedTextColor;
   private int pressedTopGradientColor;
   private int pressedBottomGradientColor;

   public SimpleButton(int posX, int posY, int width, int height, Component string, Button.OnPress onPress) {
      super(posX, posY, width, height, string, onPress, Button.f_252438_);
      this.pressedTextColor = this.defaultTextColor;
      this.pressedTopGradientColor = -16724992;
      this.pressedBottomGradientColor = -16755456;
   }

   public void setFontSize(float size) {
      this.fontSize = size;
   }

   public void setTextOnly() {
      this.textOnly = true;
   }

   public void setDefaultTextColor(int color) {
      this.defaultTextColor = color;
   }

   public void setHoverTextColor(int color) {
      this.hoverTextColor = color;
   }

   public void setPressedTextColor(int color) {
      this.pressedTextColor = color;
   }

   public void m_87963_(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
      RenderSystem.enableDepthTest();
      graphics.m_280168_().m_85836_();
      if (this.f_93624_) {
         this.f_93622_ = mouseX >= this.m_252754_() && mouseY >= this.m_252907_() && mouseX < this.m_252754_() + this.f_93618_ && mouseY < this.m_252907_() + this.f_93619_;
         int rgb = this.defaultTextColor;
         int topGrad = this.defaultTopGradientColor;
         int bottomGrad = this.defaultBottomGradientColor;
         if (this.isPressed) {
            rgb = this.pressedTextColor;
            topGrad = this.pressedTopGradientColor;
            bottomGrad = this.pressedBottomGradientColor;
         }

         if (this.f_93622_) {
            graphics.m_280168_().m_85837_((double)0.0F, (double)0.5F, (double)0.0F);
            rgb = this.hoverTextColor;
            topGrad = this.hoverTopGradientColor;
            bottomGrad = this.hoverBottomGradientColor;
         }

         int outlineSize = 1;
         if (!this.textOnly) {
            graphics.m_280024_(this.m_252754_() - outlineSize, this.m_252907_() - outlineSize, this.f_93618_ + this.m_252754_() + outlineSize, this.f_93619_ + this.m_252907_() + outlineSize, this.outlineColor, this.outlineColor);
            graphics.m_280024_(this.m_252754_(), this.m_252907_(), this.f_93618_ + this.m_252754_(), this.f_93619_ + this.m_252907_(), topGrad, bottomGrad);
         }

         Minecraft minecraft = Minecraft.m_91087_();
         Font fontrenderer = minecraft.f_91062_;
         graphics.m_280168_().m_85836_();
         int x = (int)((float)this.m_252754_() - (float)fontrenderer.m_92852_(this.m_6035_()) * this.fontSize / 2.0F + (float)(this.f_93618_ / 2));
         int y = this.m_252907_() + this.f_93619_ / 2 - 4;
         graphics.m_280168_().m_252880_((float)x, (float)y, 2.0F);
         graphics.m_280168_().m_85841_(this.fontSize, this.fontSize, this.fontSize);
         RendererHelper.drawStringWithBorder(fontrenderer, graphics, (String)this.m_6035_().getString(), 0, 0, rgb);
         graphics.m_280168_().m_85849_();
      }

      graphics.m_280168_().m_85849_();
   }

   public void select() {
      this.isPressed = !this.isPressed;
   }

   public void setIsPressed(boolean flag) {
      this.isPressed = flag;
   }
}
