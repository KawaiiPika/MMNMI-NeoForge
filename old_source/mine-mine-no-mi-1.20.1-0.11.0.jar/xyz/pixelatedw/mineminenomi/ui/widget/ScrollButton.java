package xyz.pixelatedw.mineminenomi.ui.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.api.ui.TexturedRectUI;
import xyz.pixelatedw.mineminenomi.init.ModResources;

public class ScrollButton extends Button {
   private TexturedRectUI rect;

   public ScrollButton(int posX, int posY, int width, int height, Component string, Button.OnPress onPress) {
      this(posX, posY, width, height, string, onPress, Button.f_252438_);
   }

   public ScrollButton(int posX, int posY, int width, int height, Component string, Button.OnPress onPress, Button.CreateNarration narration) {
      super(posX, posY, width, height, string, onPress, narration);
      this.rect = new TexturedRectUI(ModResources.SCROLL);
      this.rect.setScale(0.8F, 0.4F);
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
         graphics.m_280168_().m_85837_((double)-10.5F, (double)-0.5F, (double)0.0F);
         blue = 1.1F;
         green = 1.1F;
         red = 1.1F;
      }

      Minecraft minecraft = Minecraft.m_91087_();
      Font fontrenderer = minecraft.f_91062_;
      RenderSystem.setShaderColor(red, green, blue, this.f_93625_);
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.enableDepthTest();
      this.rect.draw(graphics.m_280168_(), (float)this.m_252754_(), (float)(this.m_252907_() - 12));
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      int color = this.getFGColor() | Mth.m_14167_(this.f_93625_ * 255.0F) << 24;
      RendererHelper.drawStringWithBorder(fontrenderer, graphics, this.m_6035_(), this.m_252754_() + 40, this.m_252907_() + (this.f_93619_ - 4) / 2, color);
      graphics.m_280168_().m_85849_();
      RenderSystem.disableDepthTest();
   }
}
