package xyz.pixelatedw.mineminenomi.ui.panel;

import com.mojang.blaze3d.vertex.Tesselator;
import java.awt.Color;
import java.util.List;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.narration.NarratableEntry.NarrationPriority;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.gui.widget.ScrollPanel;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeInvitation;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.client.challenge.CAcceptChallengeInvitationPacket;
import xyz.pixelatedw.mineminenomi.ui.screens.ChallengesScreen;

public class ChallengeInvitesPanel extends ScrollPanel {
   private static final int ENTRY_HEIGHT = 55;
   private static final Color SELECTION_COLOR = new Color(1.0F, 1.0F, 1.0F, 0.2F);
   private final Player player;
   private final Level world;
   private final ChallengesScreen parent;
   private List<ChallengeInvitation> invites;

   public ChallengeInvitesPanel(ChallengesScreen parent, Player player, List<ChallengeInvitation> invites) {
      super(parent.getMinecraft(), 260, 170, parent.f_96544_ / 2 - 70, parent.f_96543_ / 2 - 46);
      this.world = player.m_9236_();
      this.player = player;
      this.parent = parent;
      this.invites = invites;
   }

   public boolean m_6348_(double p_mouseReleased_1_, double p_mouseReleased_3_, int p_mouseReleased_5_) {
      return true;
   }

   protected int getContentHeight() {
      return this.invites.size() * 55 - 2;
   }

   protected int getScrollAmount() {
      return 12;
   }

   protected void drawGradientRect(GuiGraphics graphics, int left, int top, int right, int bottom, int color1, int color2) {
      graphics.m_280120_(0, left, top, right, bottom, color1, color2);
   }

   public void m_88315_(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
      super.m_88315_(graphics, mouseX, mouseY, partialTicks);
   }

   protected void drawPanel(GuiGraphics graphics, int entryRight, int relativeY, Tesselator tess, int mouseX, int mouseY) {
      int y = relativeY + 10;
      int x = this.parent.f_96543_ / 2 + 20;
      ChallengeInvitation hoverTarget = this.findEntry(mouseX, mouseY);
      int yOffset = 0;

      for(ChallengeInvitation invite : this.invites) {
         boolean isHovered = hoverTarget != null && hoverTarget.equals(invite) && this.m_5953_((double)mouseX, (double)mouseY);
         if (isHovered) {
            graphics.m_280120_(0, x - this.right, relativeY + yOffset, x + this.width, relativeY + yOffset + 55, SELECTION_COLOR.getRGB(), SELECTION_COLOR.getRGB());
            graphics.m_280168_().m_85836_();
            graphics.m_280168_().m_252880_(0.0F, -2.0F, 0.0F);
         }

         Player player = this.world.m_46003_(invite.getSenderId());
         if (player != null) {
            RendererHelper.drawPlayerFace(player, graphics, x - 53, y + yOffset + 3, 32, 32);
            RendererHelper.drawStringWithBorder(this.parent.getMinecraft().f_91062_, graphics, (String)player.m_36316_().getName(), x - 10, y + yOffset, -1);
            RendererHelper.drawStringWithBorder(this.parent.getMinecraft().f_91062_, graphics, (String)invite.getChallenge().getLocalizedTitle().getString(), x - 10, y + yOffset + 15, -1);
            RendererHelper.drawStringWithBorder(this.parent.getMinecraft().f_91062_, graphics, (String)invite.getChallenge().getLocalizedObjective().getString(), x - 10, y + yOffset + 30, -1);
            if (isHovered) {
               graphics.m_280168_().m_85849_();
            }

            yOffset += 55;
         }
      }

   }

   private ChallengeInvitation findEntry(int mouseX, int mouseY) {
      double yOffset = (double)((float)(mouseY - this.top - 4) + this.scrollDistance);
      if (yOffset <= (double)0.0F) {
         return null;
      } else {
         int lineIdx = (int)(yOffset / (double)55.0F);
         if (lineIdx >= 0 && lineIdx < this.invites.size()) {
            ChallengeInvitation entry = (ChallengeInvitation)this.invites.get(lineIdx);
            return entry != null ? entry : null;
         } else {
            return null;
         }
      }
   }

   public boolean m_6375_(double mouseX, double mouseY, int button) {
      ChallengeInvitation entry = this.findEntry((int)mouseX, (int)mouseY);
      if (entry != null && this.m_5953_(mouseX, mouseY) && button == 0) {
         ModNetwork.sendToServer(new CAcceptChallengeInvitationPacket(entry));
         this.parent.getMinecraft().m_91152_((Screen)null);
         return true;
      } else {
         return super.m_6375_(mouseX, mouseY, button);
      }
   }

   public boolean m_5953_(double mouseX, double mouseY) {
      return mouseX >= (double)this.left && mouseY >= (double)this.top && mouseX < (double)(this.left + this.width) && mouseY < (double)(this.top + this.height);
   }

   public NarratableEntry.NarrationPriority m_142684_() {
      return NarrationPriority.NONE;
   }

   public void m_142291_(NarrationElementOutput p_169152_) {
   }
}
