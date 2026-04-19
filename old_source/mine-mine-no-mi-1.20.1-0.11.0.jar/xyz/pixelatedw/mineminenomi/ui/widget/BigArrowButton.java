package xyz.pixelatedw.mineminenomi.ui.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import xyz.pixelatedw.mineminenomi.init.ModResources;

public class BigArrowButton extends Button {
   private boolean isPressed;
   private boolean isFlipped;

   public BigArrowButton(int posX, int posY, int width, int height, Button.OnPress onPress) {
      super(posX, posY, width, height, Component.m_237119_(), onPress, Button.f_252438_);
   }

   public BigArrowButton(int posX, int posY, int width, int height, Button.OnPress onPress, Button.CreateNarration narration) {
      super(posX, posY, width, height, Component.m_237119_(), onPress, narration);
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

      RenderSystem.setShaderColor(red, green, blue, this.f_93625_);
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.enableDepthTest();
      graphics.m_280163_(this.isFlipped ? ModResources.BIG_WOOD_BUTTON_RIGHT : ModResources.BIG_WOOD_BUTTON_LEFT, this.m_252754_(), this.m_252907_(), 5.0F, 15.0F, 32, 120, 32, 128);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      graphics.m_280168_().m_85849_();
      RenderSystem.disableDepthTest();
   }

   public void setIsPressed(boolean isPressed) {
      this.isPressed = isPressed;
   }

   public void setFlipped() {
      this.isFlipped = true;
   }
}
