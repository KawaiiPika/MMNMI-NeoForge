package xyz.pixelatedw.mineminenomi.client.gui.widgets;

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
      super(posX, posY, width, height, string, onPress, Button.DEFAULT_NARRATION);
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

   public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
      RenderSystem.enableDepthTest();
      graphics.pose().pushPose();
      if (this.visible) {
         this.isHovered = mouseX >= this.getX() && mouseY >= this.getY() && mouseX < this.getX() + this.width && mouseY < this.getY() + this.height;
         int rgb = this.defaultTextColor;
         int topGrad = this.defaultTopGradientColor;
         int bottomGrad = this.defaultBottomGradientColor;
         if (this.isPressed) {
            rgb = this.pressedTextColor;
            topGrad = this.pressedTopGradientColor;
            bottomGrad = this.pressedBottomGradientColor;
         }

         if (this.isHovered) {
            graphics.pose().translate((double)0.0F, (double)0.5F, (double)0.0F);
            rgb = this.hoverTextColor;
            topGrad = this.hoverTopGradientColor;
            bottomGrad = this.hoverBottomGradientColor;
         }

         int outlineSize = 1;
         if (!this.textOnly) {
            graphics.fillGradient(this.getX() - outlineSize, this.getY() - outlineSize, this.width + this.getX() + outlineSize, this.height + this.getY() + outlineSize, this.outlineColor, this.outlineColor);
            graphics.fillGradient(this.getX(), this.getY(), this.width + this.getX(), this.height + this.getY(), topGrad, bottomGrad);
         }

         Minecraft minecraft = Minecraft.getInstance();
         Font fontrenderer = minecraft.font;
         graphics.pose().pushPose();
         int x = (int)((float)this.getX() - (float)fontrenderer.width(this.getMessage()) * this.fontSize / 2.0F + (float)(this.width / 2));
         int y = this.getY() + this.height / 2 - 4;
         graphics.pose().translate((float)x, (float)y, 2.0F);
         graphics.pose().scale(this.fontSize, this.fontSize, this.fontSize);
         RendererHelper.drawStringWithBorder(fontrenderer, graphics, Component.literal(this.getMessage().getString()), 0, 0, rgb);
         graphics.pose().popPose();
      }

      graphics.pose().popPose();
   }

   public void select() {
      this.isPressed = !this.isPressed;
   }

   public void setIsPressed(boolean flag) {
      this.isPressed = flag;
   }
}
