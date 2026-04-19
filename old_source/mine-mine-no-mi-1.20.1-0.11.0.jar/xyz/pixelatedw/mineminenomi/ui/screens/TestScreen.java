package xyz.pixelatedw.mineminenomi.ui.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.init.ModResources;

public class TestScreen extends Screen {
   protected TestScreen() {
      super(Component.m_237119_());
   }

   public void m_88315_(GuiGraphics graphics, int x, int y, float f) {
      this.m_280273_(graphics);
      int posX = (this.f_96543_ - 256) / 2;
      int posY = (this.f_96544_ - 256) / 2;
      graphics.m_280168_().m_85836_();
      graphics.m_280168_().m_85841_(2.0F, 2.0F, 2.0F);
      graphics.m_280430_(this.f_96547_, Component.m_237113_("Test"), posX, posY + 10, Color.WHITE.getRGB());
      this.f_96547_.m_168645_(Component.m_237113_("Test2...").m_7532_(), (float)posX, (float)(posY + 20), Color.WHITE.getRGB(), Color.RED.getRGB(), graphics.m_280168_().m_85850_().m_252922_(), graphics.m_280091_(), 255);
      RendererHelper.drawStringWithBorder(this.f_96547_, graphics, (Component)Component.m_237113_("Test"), posX, posY, Color.WHITE.getRGB());
      RenderSystem.setShaderTexture(0, ModResources.WIDGETS);
      graphics.m_280168_().m_85849_();
      super.m_88315_(graphics, x, y, f);
   }

   public void m_7856_() {
   }

   public boolean m_7043_() {
      return true;
   }

   public static void open() {
      Minecraft.m_91087_().m_91152_(new TestScreen());
   }
}
