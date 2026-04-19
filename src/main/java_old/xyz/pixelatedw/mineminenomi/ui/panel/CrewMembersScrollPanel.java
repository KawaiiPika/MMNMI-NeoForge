package xyz.pixelatedw.mineminenomi.ui.panel;

import com.mojang.blaze3d.vertex.Tesselator;
import java.util.List;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.narration.NarratableEntry.NarrationPriority;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.gui.widget.ScrollPanel;
import xyz.pixelatedw.mineminenomi.api.crew.Crew;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;
import xyz.pixelatedw.mineminenomi.packets.client.crew.CKickFromCrewPacket;
import xyz.pixelatedw.mineminenomi.ui.screens.CrewDetailsScreen;
import xyz.pixelatedw.mineminenomi.ui.widget.SimpleButton;

public class CrewMembersScrollPanel extends ScrollPanel {
   private static final int ENTRY_HEIGHT = 20;
   private static final Component KICK_BUTTON_TEXT = Component.m_237113_("X");
   private final CrewDetailsScreen parent;
   private final List<Crew.Member> displayedMembers;
   private final SimpleButton[] listButtons;

   public CrewMembersScrollPanel(CrewDetailsScreen parent, List<Crew.Member> list) {
      super(parent.getMinecraft(), parent.f_96543_ / 3, parent.f_96544_ - 100, 50, parent.f_96543_ - parent.f_96543_ / 3 - 10);
      this.parent = parent;
      this.displayedMembers = list;
      this.listButtons = new SimpleButton[this.displayedMembers.size()];
      if (this.parent.isClientCaptain()) {
         for(int i = 0; i < this.displayedMembers.size(); ++i) {
            Crew.Member member = (Crew.Member)this.displayedMembers.get(i);
            if (member != null && !member.isCaptain()) {
               SimpleButton kickButton = new SimpleButton(0, 0, 10, 10, KICK_BUTTON_TEXT, (b) -> {
                  ModNetwork.sendToServer(new CKickFromCrewPacket(member.getUUID()));
                  this.displayedMembers.remove(i);
               });
               this.listButtons[i] = kickButton;
            }
         }
      }

   }

   protected void drawPanel(GuiGraphics guiGraphics, int entryRight, int relativeY, Tesselator tess, int mouseX, int mouseY) {
      int y = relativeY;
      int x = this.parent.f_96543_ - this.parent.f_96543_ / 3 + 20;
      int yOffset = 0;

      for(int i = 0; i < this.displayedMembers.size(); ++i) {
         Crew.Member member = (Crew.Member)this.displayedMembers.get(i);
         if (member != null) {
            String memberName = member.getUsername();
            if (memberName.length() >= 20) {
               memberName = memberName.substring(0, 20) + "...";
            }

            memberName = memberName + (member.isCaptain() ? " (" + ModI18n.CREW_CAPTAIN.getString() + ")" : "");
            RendererHelper.drawStringWithBorder(this.parent.getMinecraft().f_91062_, guiGraphics, (String)memberName, x, y + 1 + yOffset, -1);
            SimpleButton kickButton = this.listButtons[i];
            if (!member.isCaptain() && kickButton != null) {
               kickButton.m_264152_(x - 20, y + yOffset);
               kickButton.setHoverTextColor(16711680);
               kickButton.m_88315_(guiGraphics, mouseX, mouseY, 0.0F);
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

   public boolean m_6375_(double mouseX, double mouseY, int button) {
      for(GuiEventListener btn : this.listButtons) {
         if (btn != null && btn.m_6375_(mouseX, mouseY, button)) {
            this.m_7522_(btn);
            if (button == 0) {
               this.m_7897_(true);
            }

            return true;
         }
      }

      return super.m_6375_(mouseX, mouseY, button);
   }

   public boolean m_6050_(double mouseX, double mouseY, double scroll) {
      return this.getContentHeight() < this.height ? false : super.m_6050_(mouseX, mouseY, scroll);
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

   protected int getScrollAmount() {
      return 12;
   }

   public NarratableEntry.NarrationPriority m_142684_() {
      return NarrationPriority.NONE;
   }

   public void m_142291_(NarrationElementOutput narration) {
   }
}
