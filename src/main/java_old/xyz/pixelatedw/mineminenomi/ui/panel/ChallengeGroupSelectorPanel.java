package xyz.pixelatedw.mineminenomi.ui.panel;

import com.google.common.primitives.Ints;
import com.mojang.blaze3d.vertex.Tesselator;
import java.awt.Color;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.narration.NarratableEntry.NarrationPriority;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.gui.widget.ScrollPanel;
import xyz.pixelatedw.mineminenomi.api.helpers.FactionHelper;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.client.challenge.CSendChallengeInvitationPacket;
import xyz.pixelatedw.mineminenomi.ui.screens.ChallengesScreen;

public class ChallengeGroupSelectorPanel extends ScrollPanel {
   private static final int ENTRY_HEIGHT = 55;
   private static final int ENTRY_WIDTH = 120;
   private static final int SETUP_TIME = 20;
   private final Color topColor;
   private static final Color BOTTOM_COLOR = new Color(0.0F, 0.0F, 0.0F, 0.8F);
   private final Player player;
   private final ChallengesScreen parent;
   private final List<LivingEntity> nearbyGroupMembers;
   private int setupTime;

   public ChallengeGroupSelectorPanel(ChallengesScreen parent, Player player, int[] nearbyIds) {
      super(parent.getMinecraft(), 260, 170, parent.f_96544_ / 2 - 70, parent.f_96543_ / 2 - 46);
      this.player = player;
      this.parent = parent;
      this.setupTime = 20;
      this.topColor = FactionHelper.getFactionColor((IEntityStats)EntityStatsCapability.get(player).orElse((Object)null));
      this.nearbyGroupMembers = (List)Ints.asList(nearbyIds).stream().map((id) -> player.m_9236_().m_6815_(id)).filter((e) -> e instanceof LivingEntity).map((e) -> (LivingEntity)e).collect(Collectors.toList());
   }

   public boolean m_6348_(double p_mouseReleased_1_, double p_mouseReleased_3_, int p_mouseReleased_5_) {
      return true;
   }

   protected int getContentHeight() {
      return this.nearbyGroupMembers.size() * 27 + 55 - 25;
   }

   protected int getScrollAmount() {
      return 55;
   }

   protected void drawGradientRect(GuiGraphics graphics, int left, int top, int right, int bottom, int color1, int color2) {
   }

   public void m_88315_(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
      if (this.setupTime > 0) {
         --this.setupTime;
      } else {
         super.m_88315_(graphics, mouseX, mouseY, partialTicks);
      }

   }

   protected void drawPanel(GuiGraphics graphics, int entryRight, int relativeY, Tesselator tess, int mouseX, int mouseY) {
      int y = relativeY + 65;
      int x = this.parent.f_96543_ / 2 + 20;
      LivingEntity entity = this.findEntry(mouseX, mouseY);
      int row = 0;
      int xOffset = 0;
      int yOffset = 0;

      for(LivingEntity target : this.nearbyGroupMembers) {
         if (row == 2) {
            xOffset = 0;
            yOffset += 55;
            row = 0;
         }

         boolean isHovered = entity != null && entity.equals(target) && this.m_5953_((double)mouseX, (double)mouseY);
         if (isHovered) {
            graphics.m_280120_(0, x - 60 + xOffset, relativeY + yOffset, x + 60 + xOffset, relativeY + yOffset + 55, this.topColor.getRGB(), BOTTOM_COLOR.getRGB());
            graphics.m_280168_().m_85836_();
            graphics.m_280168_().m_252880_(0.0F, -2.0F, 0.0F);
         }

         if (y + yOffset > this.top + 55 && y + yOffset < this.bottom + 55) {
            this.parent.renderEntityBust(target, graphics, x + xOffset, y + 5 + yOffset);
         }

         if (isHovered) {
            graphics.m_280168_().m_85849_();
         }

         xOffset += 120;
         ++row;
      }

   }

   private LivingEntity findEntry(int mouseX, int mouseY) {
      double yOffset = (double)((float)(mouseY - this.top - 4) + this.scrollDistance);
      double xOffset = (double)(mouseX - this.left - 6);
      if (yOffset <= (double)0.0F) {
         return null;
      } else if (xOffset <= (double)0.0F) {
         return null;
      } else {
         int lineIdx = (int)(yOffset / (double)55.0F);
         int rowIdx = (int)(xOffset / (double)120.0F);
         if (rowIdx > 1) {
            return null;
         } else {
            int id = lineIdx * 2 + rowIdx;
            if (id >= 0 && id < this.nearbyGroupMembers.size()) {
               LivingEntity entry = (LivingEntity)this.nearbyGroupMembers.get(id);
               return entry != null ? entry : null;
            } else {
               return null;
            }
         }
      }
   }

   public boolean m_6375_(double mouseX, double mouseY, int button) {
      LivingEntity entity = this.findEntry((int)mouseX, (int)mouseY);
      if (entity != null && this.m_5953_(mouseX, mouseY) && button == 0 && this.setupTime <= 0) {
         if (entity instanceof Player) {
            Player player = (Player)entity;
            ModNetwork.sendToServer(new CSendChallengeInvitationPacket(player, this.parent.getSelectedChallenge().getCore(), this.parent.getSelectedMemberSlot()));
            this.parent.showGroupStep();
         } else {
            this.parent.setGroupMember(entity);
         }

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
