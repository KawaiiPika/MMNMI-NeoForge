package xyz.pixelatedw.mineminenomi.api.ui;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;

public class FlickeringString {
   private long tick;
   private Component message;
   private int flicker;
   private boolean isVisible = true;
   private Minecraft mc = Minecraft.m_91087_();

   public FlickeringString(Component str, int flicker) {
      this.message = str;
      this.flicker = flicker;
   }

   public void render(GuiGraphics graphics, int posX, int posY, float partialTicks) {
      this.tick = (long)((float)this.tick + 1.0F + partialTicks);
      if (this.tick % (long)this.flicker == 0L) {
         this.isVisible = !this.isVisible;
      } else {
         Component msg = (Component)(this.isVisible ? this.message : Component.m_237119_());
         RendererHelper.drawStringWithBorder(this.mc.f_91062_, graphics, msg, posX, posY, Color.WHITE.getRGB());
      }

   }
}
