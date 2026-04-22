package xyz.pixelatedw.mineminenomi.client.gui.panels;

import com.mojang.blaze3d.vertex.Tesselator;
import java.util.List;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.narration.NarratableEntry.NarrationPriority;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.client.gui.widget.ScrollPanel;
import xyz.pixelatedw.mineminenomi.api.crew.Crew;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.networking.ModNetworking;

import xyz.pixelatedw.mineminenomi.networking.packets.CKickFromCrewPacket;
import xyz.pixelatedw.mineminenomi.client.gui.screens.CrewDetailsScreen;
import xyz.pixelatedw.mineminenomi.client.gui.widgets.SimpleButton;

public class CrewMembersScrollPanel extends ScrollPanel {
   private static final int ENTRY_HEIGHT = 20;
   private static final Component KICK_BUTTON_TEXT = Component.literal("X");
   private final CrewDetailsScreen parent;
   private final List<Crew.Member> displayedMembers;
   private final SimpleButton[] listButtons;

   public CrewMembersScrollPanel(CrewDetailsScreen parent, List<Crew.Member> list) {
      super(parent.getMinecraft(), parent.width / 3, parent.height - 100, 50, parent.width - parent.width / 3 - 10);
      this.parent = parent;
      this.displayedMembers = list;
      this.listButtons = new SimpleButton[this.displayedMembers.size()];
      if (this.parent.isClientCaptain()) {
         for(int i = 0; i < this.displayedMembers.size(); ++i) {
            final int index = i;
            Crew.Member member = (Crew.Member)this.displayedMembers.get(index);
            if (member != null && !member.isCaptain()) {
               SimpleButton kickButton = new SimpleButton(0, 0, 10, 10, KICK_BUTTON_TEXT, (b) -> {
                  net.neoforged.neoforge.network.PacketDistributor.sendToServer(new CKickFromCrewPacket(member.getUUID()));
                  this.displayedMembers.remove(index);
               });
               this.listButtons[index] = kickButton;
            }
         }
      }

   }

   protected void drawPanel(GuiGraphics guiGraphics, int entryRight, int relativeY, Tesselator tess, int mouseX, int mouseY) {
      int y = relativeY;
      int x = this.parent.width - this.parent.width / 3 + 20;
      int yOffset = 0;

      for(int i = 0; i < this.displayedMembers.size(); ++i) {
         Crew.Member member = (Crew.Member)this.displayedMembers.get(i);
         if (member != null) {
            String memberName = member.getUsername();
            if (memberName.length() >= 20) {
               memberName = memberName.substring(0, 20) + "...";
            }

            memberName = memberName + (member.isCaptain() ? " (" + Component.translatable("crew.rank.captain").getString() + ")" : "");
            RendererHelper.drawStringWithBorder(this.parent.getMinecraft().font, guiGraphics, Component.literal(memberName), x, y + 1 + yOffset, -1);
            SimpleButton kickButton = this.listButtons[i];
            if (!member.isCaptain() && kickButton != null) {
               kickButton.setPosition(x - 20, y + yOffset);
               kickButton.setHoverTextColor(16711680);
               kickButton.render(guiGraphics, mouseX, mouseY, 0.0F);
            }

            yOffset += 20;
         }
      }

   }

   private Crew.Member findEntry(int mouseX, int mouseY) {
      double offset = (double)((float)(mouseY - this.top) + this.scrollDistance);
      if (offset <= (double)0.0F) {
         return null;
      } else {
         int lineIdx = (int)(offset / (double)20.0F);
         if (lineIdx >= this.displayedMembers.size()) {
            return null;
         } else {
            Crew.Member entry = (Crew.Member)this.displayedMembers.get(lineIdx);
            return entry != null ? entry : null;
         }
      }
   }

   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      for(GuiEventListener btn : this.listButtons) {
         if (btn != null && btn.mouseClicked(mouseX, mouseY, button)) {
            this.parent.addRenderableWidgetPublic((net.minecraft.client.gui.components.AbstractWidget) btn);
            if (button == 0) {
               this.setFocused(true);
            }

            return true;
         }
      }

      return false;
   }

   public boolean mouseScrolled(double mouseX, double mouseY, double scroll) {
      return false;
   }

   public boolean m_6348_(double p_mouseReleased_1_, double p_mouseReleased_3_, int p_mouseReleased_5_) {
      return false;
   }

   public boolean m_5953_(double mouseX, double mouseY) {
      return mouseX >= (double)this.left && mouseY >= (double)this.top && mouseX < (double)(this.left + this.width - 5) && mouseY < (double)(this.top + this.height);
   }

   protected int getContentHeight() {
      return this.displayedMembers.size() * 20 - 2;
   }

   @Override
   public net.minecraft.client.gui.narration.NarratableEntry.NarrationPriority narrationPriority() {
      return net.minecraft.client.gui.narration.NarratableEntry.NarrationPriority.NONE;
   }
   @Override
   public void updateNarration(net.minecraft.client.gui.narration.NarrationElementOutput output) {}
}
