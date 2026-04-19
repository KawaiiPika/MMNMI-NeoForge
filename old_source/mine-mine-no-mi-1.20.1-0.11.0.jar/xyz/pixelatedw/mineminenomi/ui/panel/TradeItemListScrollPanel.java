package xyz.pixelatedw.mineminenomi.ui.panel;

import com.mojang.blaze3d.vertex.Tesselator;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.narration.NarratableEntry.NarrationPriority;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.gui.widget.ScrollPanel;
import xyz.pixelatedw.mineminenomi.api.TradeEntry;
import xyz.pixelatedw.mineminenomi.ui.screens.TraderScreen;

public class TradeItemListScrollPanel extends ScrollPanel {
   private TraderScreen parent;
   private List<TradeEntry> entries = new ArrayList();
   private static final int ENTRY_HEIGHT = 24;

   public TradeItemListScrollPanel(TraderScreen parent, List<TradeEntry> list) {
      super(parent.getMinecraft(), 215, 140, parent.f_96544_ / 2 - 50, parent.f_96543_ / 2 - 109);
      this.parent = parent;
      this.entries = list;

      for(TradeEntry entry : this.entries) {
         CompoundTag tag = entry.getItemStack().m_41784_();
         if (tag.m_128471_("isClone") && !tag.m_128471_("hasCloneTag")) {
            ItemStack var10000 = entry.getItemStack();
            String var10001 = String.valueOf(ChatFormatting.RESET);
            var10000.m_41714_(Component.m_237113_(var10001 + entry.getItemStack().m_41786_().getString() + " (Replica)"));
            tag.m_128379_("hasCloneTag", true);
         }
      }

   }

   public boolean m_6348_(double mouseX, double mouseY, int partialTicks) {
      return true;
   }

   protected int getContentHeight() {
      return this.entries.size() * 24 + 2;
   }

   protected int getScrollAmount() {
      return 10;
   }

   protected void drawPanel(GuiGraphics graphics, int entryRight, int relativeY, Tesselator tess, int mouseX, int mouseY) {
      for(TradeEntry entry : this.entries) {
         int x = this.parent.f_96543_ / 2 - 109 + 40;
         graphics.m_280480_(entry.getItemStack(), x - 30, relativeY - 1);
         if (this.parent.getSelectedStack() != null && entry.equals(this.parent.getSelectedStack())) {
            this.drawGradientRect(graphics, this.left, relativeY - 4, this.right, relativeY + 24 - 4, 1437248170, 581610154);
         }

         this.parent.drawSizedString(graphics, entry.getItemStack().m_41786_(), x + 50, relativeY + 4, 0.8F, -1);
         this.parent.drawSizedString(graphics, Component.m_237113_("" + entry.getPrice()), x + 122, relativeY + 4, 0.8F, -1);
         relativeY += 24;
      }

   }

   public TradeEntry findStackEntry(int mouseX, int mouseY) {
      double offset = (double)((float)(mouseY - this.top) + this.scrollDistance);
      boolean isHovered = mouseX >= this.left && mouseY >= this.top && mouseX < this.left + this.width - 5 && mouseY < this.top + this.height;
      if (!(offset <= (double)0.0F) && isHovered) {
         int lineIdx = (int)(offset / (double)24.0F);
         if (lineIdx >= this.entries.size()) {
            return null;
         } else {
            TradeEntry entry = (TradeEntry)this.entries.get(lineIdx);
            return entry != null && mouseX >= this.left && mouseX <= this.right && mouseY <= this.bottom ? entry : null;
         }
      } else {
         return null;
      }
   }

   public boolean m_6375_(double mouseX, double mouseY, int button) {
      TradeEntry entry = this.findStackEntry((int)mouseX, (int)mouseY);
      boolean isHovered = mouseX >= (double)this.left && mouseY >= (double)this.top && mouseX < (double)(this.left + this.width - 5) && mouseY < (double)(this.top + this.height);
      if (isHovered && entry != null) {
         this.parent.setSelectedStack(entry);
         this.parent.setWantedAmount(1);
      }

      return super.m_6375_(mouseX, mouseY, button);
   }

   public void removeEntry(ItemStack stack) {
      for(int i = 0; i < this.entries.size(); ++i) {
         if (((TradeEntry)this.entries.get(i)).getItemStack().m_41720_() == stack.m_41720_()) {
            this.entries.remove(i);
         }
      }

   }

   public NarratableEntry.NarrationPriority m_142684_() {
      return NarrationPriority.NONE;
   }

   public void m_142291_(NarrationElementOutput p_169152_) {
   }
}
