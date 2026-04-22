package xyz.pixelatedw.mineminenomi.client.gui.widgets;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.init.ModResources;

public class PlankButton extends Button {
   public PlankButton(int posX, int posY, int width, int height, Component string, Button.OnPress onPress) {
      super(posX, posY, width, height, string, onPress, Button.DEFAULT_NARRATION);
   }

   public PlankButton(int posX, int posY, int width, int height, Component string, Button.OnPress onPress, Button.CreateNarration narration) {
      super(posX, posY, width, height, string, onPress, narration);
   }

   private int getYImage() {
      int i = 1;
      if (!this.active) {
         i = 0;
      } else if (this.isFocused()) {
         i = 2;
      }

      return 0 + i * 30;
   }

   public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
      RenderSystem.enableDepthTest();
      if (this.isHovered) {
      }

      graphics.pose().pushPose();
      float red = 1.0F;
      float green = 1.0F;
      float blue = 1.0F;
      if (!this.active) {
         blue = 0.4F;
         green = 0.4F;
         red = 0.4F;
      } else if (this.isHovered) {
         graphics.pose().translate((double)0.0F, (double)0.5F, (double)1.0F);
         blue = 0.6F;
         green = 0.6F;
         red = 0.6F;
      }

      Minecraft minecraft = Minecraft.getInstance();
      Font fontrenderer = minecraft.font;
      RenderSystem.setShaderColor(red, green, blue, this.alpha);
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.enableDepthTest();
      graphics.blit(ModResources.WIDGETS, this.getX(), this.getY(), this.getWidth(), this.getHeight(), 20, 4, 132, 29, 123, this.getYImage());
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      int color = this.getFGColor() | Mth.ceil(this.alpha * 255.0F) << 24;
      int texLen = fontrenderer.width(this.getMessage()) / 2;
      RendererHelper.drawStringWithBorder(fontrenderer, graphics, this.getMessage(), this.getX() + this.width / 2 - texLen, this.getY() + (this.height - 8) / 2, color);
      graphics.pose().popPose();
      RenderSystem.disableDepthTest();
   }
}
