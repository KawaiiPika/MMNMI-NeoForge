package xyz.pixelatedw.mineminenomi.ui.components;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.narration.NarratableEntry.NarrationPriority;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.gui.widget.ForgeSlider;
import xyz.pixelatedw.mineminenomi.api.crew.JollyRogerElement;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;
import xyz.pixelatedw.mineminenomi.ui.screens.JollyRogerEditorScreen;

public class JollyRogerElementColorComponent implements Renderable, GuiEventListener, NarratableEntry {
   private final JollyRogerEditorScreen parentScreen;
   private boolean isVisible = true;
   private List<GuiEventListener> buttons = new ArrayList();
   private List<Renderable> renderable = new ArrayList();
   private int posX;
   private int posY;
   private ForgeSlider redSlider;
   private ForgeSlider greenSlider;
   private ForgeSlider blueSlider;

   public JollyRogerElementColorComponent(JollyRogerEditorScreen parentScreen, int posX, int posY) {
      this.parentScreen = parentScreen;
      this.posX = posX;
      this.posY = posY;
      this.init(posX, posY);
   }

   public void init(int posX, int posY) {
      this.buttons.clear();
      this.renderable.clear();
      posX -= this.parentScreen.isSmallScreen() ? 0 : 70;
      posY += this.parentScreen.isSmallScreen() ? 30 : 0;
      int height = this.parentScreen.isSmallScreen() ? 15 : 20;
      this.redSlider = new ForgeSlider(posX, posY, 50, height, Component.m_237119_(), Component.m_237119_(), (double)0.0F, (double)255.0F, (double)255.0F, true);
      this.addWidget(this.redSlider);
      posY += 17 + (this.parentScreen.isSmallScreen() ? 0 : 5);
      this.greenSlider = new ForgeSlider(posX, posY, 50, height, Component.m_237119_(), Component.m_237119_(), (double)0.0F, (double)255.0F, (double)255.0F, true);
      this.addWidget(this.greenSlider);
      posY += 17 + (this.parentScreen.isSmallScreen() ? 0 : 5);
      this.blueSlider = new ForgeSlider(posX, posY, 50, height, Component.m_237119_(), Component.m_237119_(), (double)0.0F, (double)255.0F, (double)255.0F, true);
      this.addWidget(this.blueSlider);
   }

   public void m_88315_(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
      if (this.isVisible) {
         Component red = ModI18n.GUI_RED;
         int posX = this.posX - this.parentScreen.getMinecraft().f_91062_.m_92852_(red) - 75;
         int var10004 = this.posY + 6;
         RendererHelper.drawStringWithBorder(this.parentScreen.getMinecraft().f_91062_, graphics, red, posX, var10004, 16777215);
         Component green = ModI18n.GUI_GREEN;
         posX = this.posX - this.parentScreen.getMinecraft().f_91062_.m_92852_(green) - 75;
         var10004 = this.posY + 28;
         RendererHelper.drawStringWithBorder(this.parentScreen.getMinecraft().f_91062_, graphics, ModI18n.GUI_GREEN, posX, var10004, 16777215);
         Component blue = ModI18n.GUI_BLUE;
         posX = this.posX - this.parentScreen.getMinecraft().f_91062_.m_92852_(blue) - 75;
         var10004 = this.posY + 50;
         RendererHelper.drawStringWithBorder(this.parentScreen.getMinecraft().f_91062_, graphics, ModI18n.GUI_BLUE, posX, var10004, 16777215);

         for(Renderable btn : this.renderable) {
            btn.m_88315_(graphics, mouseX, mouseY, partialTicks);
         }

      }
   }

   public boolean m_6375_(double mouseX, double mouseY, int mouseType) {
      if (!this.isVisible) {
         return false;
      } else {
         for(GuiEventListener btn : this.buttons) {
            if (btn.m_6375_(mouseX, mouseY, mouseType)) {
               if (btn instanceof AbstractSliderButton) {
                  this.parentScreen.m_7522_(btn);
                  if (mouseType == 0) {
                     this.parentScreen.m_7897_(true);
                  }
               }

               return false;
            }
         }

         return false;
      }
   }

   public boolean m_5953_(double mouseX, double mouseY) {
      return this.redSlider.m_93696_() || this.greenSlider.m_93696_() || this.blueSlider.m_93696_();
   }

   public void setVisible(boolean flag) {
      this.isVisible = flag;
   }

   public void setRGBValues(@Nullable JollyRogerElement element) {
      if (element != null) {
         this.redSlider.m_93611_((double)element.getRed());
         this.greenSlider.m_93611_((double)element.getGreen());
         this.blueSlider.m_93611_((double)element.getBlue());
      } else {
         this.redSlider.m_93611_((double)255.0F);
         this.greenSlider.m_93611_((double)255.0F);
         this.blueSlider.m_93611_((double)255.0F);
      }

   }

   public int getRed() {
      return this.redSlider.getValueInt();
   }

   public int getGreen() {
      return this.greenSlider.getValueInt();
   }

   public int getBlue() {
      return this.blueSlider.getValueInt();
   }

   public <T extends GuiEventListener & Renderable> void addWidget(T widget) {
      this.buttons.add(widget);
      this.renderable.add(widget);
   }

   public NarratableEntry.NarrationPriority m_142684_() {
      return NarrationPriority.NONE;
   }

   public void m_93692_(boolean p_265728_) {
   }

   public boolean m_93696_() {
      return false;
   }

   public void m_142291_(NarrationElementOutput output) {
   }
}
