package xyz.pixelatedw.mineminenomi.ui.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.math.Axis;
import java.awt.Color;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;

public class AnimatedTextButton extends Button {
   private boolean isMarked = false;

   public AnimatedTextButton(int posX, int posY, int width, int height, Component string, Button.OnPress onPress) {
      super(posX, posY, width, height, string, onPress, Button.f_252438_);
   }

   public AnimatedTextButton(int posX, int posY, int width, int height, Component string, Button.OnPress onPress, Button.CreateNarration narration) {
      super(posX, posY, width, height, string, onPress, narration);
   }

   private int m_274533_() {
      int i = 1;
      if (!this.f_93623_) {
         i = 0;
      } else if (this.m_198029_()) {
         i = 2;
      }

      return 46 + i * 20;
   }

   public void m_87963_(GuiGraphics graphics, int mouseX, int mouseY, float partial) {
      if (this.f_93624_) {
         Minecraft mc = Minecraft.m_91087_();
         graphics.m_280027_(f_93617_, this.m_252754_(), this.m_252907_(), this.m_5711_(), this.m_93694_(), 20, 4, 200, 20, 0, this.m_274533_());
         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
         int scale = 1;
         int color = this.getFGColor();
         float angle = 0.0F;
         if (this.m_274382_()) {
            this.isMarked = false;
         }

         if (this.isMarked) {
            scale = 2;
            color = Color.YELLOW.getRGB();
            angle = (float)(Util.m_137550_() / 40L);
            angle = (float)Math.sin((double)(angle / 10.0F)) / 2.0F;
         }

         Component buttonText = this.m_6035_();
         int strWidth = mc.f_91062_.m_92852_(buttonText);
         int ellipsisWidth = mc.f_91062_.m_92895_("...");
         if (strWidth > this.f_93618_ - 6 && strWidth > ellipsisWidth) {
            FormattedText var10000 = mc.f_91062_.m_92854_(buttonText, this.f_93618_ - 6 - ellipsisWidth);
            buttonText = Component.m_237113_(var10000.getString() + "...");
         }

         graphics.m_280168_().m_85836_();
         graphics.m_280168_().m_252880_((float)(this.m_252754_() + this.f_93618_ / 2), (float)(this.m_252907_() + this.f_93619_ / 2), 1.0F);
         graphics.m_280168_().m_252781_(Axis.f_252403_.m_252961_(angle));
         graphics.m_280168_().m_85841_((float)scale, (float)scale, (float)scale);
         graphics.m_280653_(mc.f_91062_, buttonText, 0, -4, color);
         graphics.m_280168_().m_85849_();
      }

   }

   public void setMarked() {
      this.isMarked = true;
   }
}
