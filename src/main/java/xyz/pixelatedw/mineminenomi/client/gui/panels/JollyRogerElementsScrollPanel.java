package xyz.pixelatedw.mineminenomi.client.gui.panels;

import com.mojang.blaze3d.vertex.Tesselator;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.narration.NarratableEntry.NarrationPriority;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.client.gui.widget.ScrollPanel;
import xyz.pixelatedw.mineminenomi.api.crew.Crew;
import xyz.pixelatedw.mineminenomi.api.crew.JollyRogerElement;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.api.ui.TexturedRectUI;
import xyz.pixelatedw.mineminenomi.init.ModResources;

import xyz.pixelatedw.mineminenomi.client.gui.screens.JollyRogerEditorScreen;

public class JollyRogerElementsScrollPanel extends ScrollPanel {
   private static final int ENTRY_WIDTH = 64;
   private static final int ENTRY_HEIGHT = 64;
   private static final Entry REMOVE_ELEMENT = new Entry((JollyRogerElement)null);
   private final Player player;
   private final Crew crew;
   private final JollyRogerEditorScreen parent;
   private final int elementsPerRow;
   private List<Entry> entries;
   private int rows;

   public JollyRogerElementsScrollPanel(JollyRogerEditorScreen parent, int listWidth, int elementsPerRow) {
      super(parent.getMinecraft(), listWidth, parent.height, 0, parent.width - listWidth);
      this.parent = parent;
      this.player = parent.getPlayer();
      this.crew = parent.getCrew();
      this.elementsPerRow = elementsPerRow;
      this.setEntries(this.parent.getSelectedLayerType());
   }

   public void setEntries(JollyRogerElement.LayerType layer) {
      this.entries = new ArrayList();
      this.entries.add(REMOVE_ELEMENT);
      List<JollyRogerElement> entries = this.parent.getListFromType(layer);
      this.entries.addAll(entries.stream().filter((elem) -> elem.canUse(this.player, this.crew)).map(Entry::new).toList());
      this.rows = (int)Math.ceil((double)((float)this.entries.size() / (float)this.elementsPerRow));
      this.scrollDistance = 0.0f;
   }

   protected void drawGradientRect(GuiGraphics graphics, int left, int top, int right, int bottom, int color1, int color2) {
      super.drawGradientRect(graphics, left, top, right, bottom, color1, color2);
   }

   public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
      super.render(graphics, mouseX, mouseY, partialTicks);
      if (this.isMouseOver((double)mouseX, (double)mouseY)) {
         Entry hoveredEntry = this.findEntry(mouseX, mouseY);
         if (hoveredEntry != null) {
         }
      }

   }

   protected void drawPanel(GuiGraphics graphics, int entryRight, int relativeY, Tesselator tess, int mouseX, int mouseY) {
      int y = relativeY + 4;
      int skips = 0;
      Entry entry = this.findEntry(mouseX, mouseY);

      for(int row = 0; row < this.rows; ++row) {
         int x = this.left + 16;

         for(Entry elemEntry : this.entries.stream().skip((long)skips).limit((long)this.elementsPerRow).toList()) {
            int hoveredX = 0;
            int hoveredY = 0;
            float iconSize = 0.2F;
            int textColor = 16777215;
            if (elemEntry.equals(entry) && !entry.isLocked) {
               hoveredX = 6;
               hoveredY = 2;
               iconSize = 0.25F;
               textColor = 7203230;
            }

            if (elemEntry.element != null) {
               TexturedRectUI rect = new TexturedRectUI(elemEntry.element.getTexture());
               rect.setScale(iconSize);
               rect.draw(graphics.pose(), (float)(x - 1 - hoveredX), (float)(y - 4 - hoveredY));
               Component localizedName = elemEntry.element.getLocalizedName();
               int centerX = x - this.parent.getMinecraft().font.width(localizedName) / 2;
               RendererHelper.drawStringWithBorder(this.parent.getMinecraft().font, graphics, localizedName, centerX + 25, y + 42, textColor);
            } else if (elemEntry.equals(REMOVE_ELEMENT)) {
               iconSize *= 0.8F;
               TexturedRectUI rect = new TexturedRectUI(net.minecraft.resources.ResourceLocation.withDefaultNamespace("textures/item/barrier.png"));
               rect.setScale(iconSize);
               rect.draw(graphics.pose(), (float)(x + 3 - hoveredX), (float)(y - hoveredY));
               Component localizedName = Component.translatable("gui.remove");
               int centerX = x - this.parent.getMinecraft().font.width(localizedName) / 2;
               RendererHelper.drawStringWithBorder(this.parent.getMinecraft().font, graphics, localizedName, centerX + 24, y + 42, textColor);
            }

            x += 64;
            ++skips;
         }

         y += 64;
      }

   }

   private Entry findEntry(int mouseX, int mouseY) {
      if (mouseX < this.left) {
         return null;
      } else {
         double xOffset = (double)(mouseX - this.left);
         double yOffset = (double)((float)(mouseY - this.top) + this.scrollDistance);
         if (!(xOffset <= (double)0.0F) && !(yOffset <= (double)0.0F)) {
            int colIdx = (int)(xOffset / (double)64.0F);
            int rowIdx = (int)(yOffset / (double)64.0F);
            int elemIdx = colIdx + rowIdx * this.elementsPerRow;
            int maxRows = this.entries.size() / this.elementsPerRow;
            if (rowIdx <= maxRows && colIdx < this.elementsPerRow && elemIdx < this.entries.size()) {
               Entry entry = (Entry)this.entries.get(elemIdx);
               return entry != null ? entry : null;
            } else {
               return null;
            }
         } else {
            return null;
         }
      }
   }

   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      Entry entry = this.findEntry((int)mouseX, (int)mouseY);
      if (entry != null) {
         if (entry.element != null) {
            this.parent.updateJollyRoger(entry.element.copy());
         } else if (entry.equals(REMOVE_ELEMENT)) {
            this.parent.updateJollyRoger((JollyRogerElement)null);
         }

         return true;
      } else {
         return false;
      }
   }

   public boolean mouseScrolled(double mouseX, double mouseY, double scroll) {
      return false;
   }

   @Override
   protected int getContentHeight() {
      return this.entries.size() / this.elementsPerRow * 64 + 64;
   }

   @Override
   public net.minecraft.client.gui.narration.NarratableEntry.NarrationPriority narrationPriority() {
      return net.minecraft.client.gui.narration.NarratableEntry.NarrationPriority.NONE;
   }
   @Override
   public void updateNarration(net.minecraft.client.gui.narration.NarrationElementOutput output) {}

   private static class Entry {
      public final JollyRogerElement element;
      public boolean isLocked = false;

      public Entry(JollyRogerElement element) {
         this.element = element;
      }
   }
}
