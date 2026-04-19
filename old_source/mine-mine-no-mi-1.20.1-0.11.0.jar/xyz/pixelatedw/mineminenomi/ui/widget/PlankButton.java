package xyz.pixelatedw.mineminenomi.ui.widget;

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
      super(posX, posY, width, height, string, onPress, Button.f_252438_);
   }

   public PlankButton(int posX, int posY, int width, int height, Component string, Button.OnPress onPress, Button.CreateNarration narration) {
      super(posX, posY, width, height, string, onPress, narration);
   }

   private int m_274533_() {
      int i = 1;
      if (!this.f_93623_) {
         i = 0;
      } else if (this.m_198029_()) {
         i = 2;
      }

      return 0 + i * 30;
   }

   public void m_87963_(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
      RenderSystem.enableDepthTest();
      if (this.f_93622_) {
      }

      graphics.m_280168_().m_85836_();
      float red = 1.0F;
      float green = 1.0F;
      float blue = 1.0F;
      if (!this.f_93623_) {
         blue = 0.4F;
         green = 0.4F;
         red = 0.4F;
      } else if (this.f_93622_) {
         graphics.m_280168_().m_85837_((double)0.0F, (double)0.5F, (double)1.0F);
         blue = 0.6F;
         green = 0.6F;
         red = 0.6F;
      }

      Minecraft minecraft = Minecraft.m_91087_();
      Font fontrenderer = minecraft.f_91062_;
      RenderSystem.setShaderColor(red, green, blue, this.f_93625_);
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.enableDepthTest();
      graphics.m_280027_(ModResources.WIDGETS, this.m_252754_(), this.m_252907_(), this.m_5711_(), this.m_93694_(), 20, 4, 132, 29, 123, this.m_274533_());
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      int color = this.getFGColor() | Mth.m_14167_(this.f_93625_ * 255.0F) << 24;
      int texLen = fontrenderer.m_92852_(this.m_6035_()) / 2;
      RendererHelper.drawStringWithBorder(fontrenderer, graphics, this.m_6035_(), this.m_252754_() + this.f_93618_ / 2 - texLen, this.m_252907_() + (this.f_93619_ - 8) / 2, color);
      graphics.m_280168_().m_85849_();
      RenderSystem.disableDepthTest();
   }
}
