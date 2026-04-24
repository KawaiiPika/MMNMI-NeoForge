package xyz.pixelatedw.mineminenomi.client.gui.components;

import java.util.ArrayList;
import java.util.List;
import org.jspecify.annotations.Nullable;
import net.minecraft.client.gui.GuiGraphics;
import net.neoforged.neoforge.client.gui.widget.ExtendedSlider;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.narration.NarratableEntry.NarrationPriority;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.client.gui.widget.ExtendedSlider;
import xyz.pixelatedw.mineminenomi.api.crew.JollyRogerElement;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;

import xyz.pixelatedw.mineminenomi.client.gui.screens.JollyRogerEditorScreen;

public class JollyRogerElementColorComponent implements Renderable, GuiEventListener, net.minecraft.client.gui.narration.NarratableEntry {
   private final JollyRogerEditorScreen parentScreen;
   private boolean isVisible = true;
   private List<GuiEventListener> buttons = new ArrayList();
   private List<Renderable> renderable = new ArrayList();
   private int posX;
   private int posY;
   private net.neoforged.neoforge.client.gui.widget.ExtendedSlider redSlider;
   private net.neoforged.neoforge.client.gui.widget.ExtendedSlider greenSlider;
   private net.neoforged.neoforge.client.gui.widget.ExtendedSlider blueSlider;

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
      this.redSlider = new net.neoforged.neoforge.client.gui.widget.ExtendedSlider(posX, posY, 50, height, Component.empty(), Component.empty(), (double)0.0F, (double)255.0F, (double)255.0F, true);
      this.parentScreen.addRenderableWidgetPublic(this.redSlider);
      posY += 17 + (this.parentScreen.isSmallScreen() ? 0 : 5);
      this.greenSlider = new net.neoforged.neoforge.client.gui.widget.ExtendedSlider(posX, posY, 50, height, Component.empty(), Component.empty(), (double)0.0F, (double)255.0F, (double)255.0F, true);
      this.parentScreen.addRenderableWidgetPublic(this.greenSlider);
      posY += 17 + (this.parentScreen.isSmallScreen() ? 0 : 5);
      this.blueSlider = new net.neoforged.neoforge.client.gui.widget.ExtendedSlider(posX, posY, 50, height, Component.empty(), Component.empty(), (double)0.0F, (double)255.0F, (double)255.0F, true);
      this.parentScreen.addRenderableWidgetPublic(this.blueSlider);
   }

   public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
      if (this.isVisible) {
         Component red = Component.translatable("gui.red");
         int posX = this.posX - this.parentScreen.getMinecraft().font.width(red) - 75;
         int var10004 = this.posY + 6;
         RendererHelper.drawStringWithBorder(this.parentScreen.getMinecraft().font, graphics, red, posX, var10004, 16777215);
         Component green = Component.translatable("gui.green");
         posX = this.posX - this.parentScreen.getMinecraft().font.width(green) - 75;
         var10004 = this.posY + 28;
         RendererHelper.drawStringWithBorder(this.parentScreen.getMinecraft().font, graphics, Component.translatable("gui.green"), posX, var10004, 16777215);
         Component blue = Component.translatable("gui.blue");
         posX = this.posX - this.parentScreen.getMinecraft().font.width(blue) - 75;
         var10004 = this.posY + 50;
         RendererHelper.drawStringWithBorder(this.parentScreen.getMinecraft().font, graphics, Component.translatable("gui.blue"), posX, var10004, 16777215);

         for(Renderable btn : this.renderable) {
            btn.render(graphics, mouseX, mouseY, partialTicks);
         }

      }
   }

   public boolean mouseClicked(double mouseX, double mouseY, int mouseType) {
      if (!this.isVisible) {
         return false;
      } else {
         for(GuiEventListener btn : this.buttons) {
            if (btn.mouseClicked(mouseX, mouseY, mouseType)) {
               if (btn instanceof net.neoforged.neoforge.client.gui.widget.ExtendedSlider) {
                  this.parentScreen.addRenderableWidgetPublic((net.minecraft.client.gui.components.AbstractWidget) btn);
                  if (mouseType == 0) {
                     this.parentScreen.setFocused(this);
                  }
               }

               return false;
            }
         }

         return false;
      }
   }

   public boolean m_5953_(double mouseX, double mouseY) {
      return this.redSlider.isHovered() || this.greenSlider.isHovered() || this.blueSlider.isHovered();
   }

   public void setVisible(boolean flag) {
      this.isVisible = flag;
   }

   public void setRGBValues(@Nullable JollyRogerElement element) {
      if (element != null) {
         this.redSlider.setValue((double)element.getRed());
         this.greenSlider.setValue((double)element.getGreen());
         this.blueSlider.setValue((double)element.getBlue());
      } else {
         this.redSlider.setValue((double)255.0F);
         this.greenSlider.setValue((double)255.0F);
         this.blueSlider.setValue((double)255.0F);
      }
   }

   public int getRed() {
      return this.redSlider != null ? (int) this.redSlider.getValue() : 255;
   }

   public int getGreen() {
      return this.greenSlider != null ? (int) this.greenSlider.getValue() : 255;
   }

   public int getBlue() {
      return this.blueSlider != null ? (int) this.blueSlider.getValue() : 255;
   }

   @Override
   public net.minecraft.client.gui.narration.NarratableEntry.NarrationPriority narrationPriority() {
      return net.minecraft.client.gui.narration.NarratableEntry.NarrationPriority.NONE;
   }
   @Override
   public void updateNarration(net.minecraft.client.gui.narration.NarrationElementOutput output) {}
   @Override
   public void setFocused(boolean p_265728_) {}
   @Override
   public boolean isFocused() { return false; }
}
