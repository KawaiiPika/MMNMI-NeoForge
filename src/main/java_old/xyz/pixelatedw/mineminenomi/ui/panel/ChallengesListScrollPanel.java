package xyz.pixelatedw.mineminenomi.ui.panel;

import com.mojang.blaze3d.vertex.Tesselator;
import java.awt.Color;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.narration.NarratableEntry.NarrationPriority;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.client.gui.widget.ScrollPanel;
import xyz.pixelatedw.mineminenomi.api.challenges.Challenge;
import xyz.pixelatedw.mineminenomi.ui.screens.ChallengesScreen;
import xyz.pixelatedw.mineminenomi.ui.widget.PlankButton;

public class ChallengesListScrollPanel extends ScrollPanel {
   private static final int ENTRY_HEIGHT = 36;
   private final ChallengesScreen parent;
   private final List<Challenge> displayedChallenges;

   public ChallengesListScrollPanel(ChallengesScreen parent, List<Challenge> list) {
      super(parent.getMinecraft(), 170, 210, parent.f_96544_ / 2 - 90, parent.f_96543_ / 2 - 245);
      this.parent = parent;
      this.displayedChallenges = list;
   }

   public boolean m_6348_(double p_mouseReleased_1_, double p_mouseReleased_3_, int p_mouseReleased_5_) {
      return false;
   }

   protected int getContentHeight() {
      return this.displayedChallenges.size() * 36 - 2;
   }

   protected int getScrollAmount() {
      return 12;
   }

   protected void drawGradientRect(GuiGraphics graphics, int left, int top, int right, int bottom, int color1, int color2) {
   }

   public void m_88315_(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
      super.m_88315_(graphics, mouseX, mouseY, partialTicks);
   }

   protected void drawPanel(GuiGraphics graphics, int entryRight, int relativeY, Tesselator tess, int mouseX, int mouseY) {
      int y = relativeY;
      int x = this.parent.f_96543_ / 2 - 280 + 40;
      int yOffset = 0;

      for(Challenge ch : this.displayedChallenges) {
         PlankButton challengeButton = new PlankButton(x, y + yOffset, 160, 31, ch.getCore().getLocalizedTitle(), (btn) -> {
         });
         if (this.parent.getSelectedChallenge() != null && this.parent.getSelectedChallenge().equals(ch)) {
            challengeButton.f_93623_ = false;
         }

         if (ch.isComplete()) {
            challengeButton.setFGColor(Color.YELLOW.getRGB());
         }

         challengeButton.m_88315_(graphics, mouseX, mouseY, 0.0F);
         yOffset += 36;
      }

   }

   private Challenge findEntry(int mouseX, int mouseY) {
      double offset = (double)((float)(mouseY - this.top) + this.scrollDistance);
      if (offset <= (double)0.0F) {
         return null;
      } else {
         int lineIdx = (int)(offset / (double)36.0F);
         if (lineIdx >= this.displayedChallenges.size()) {
            return null;
         } else {
            Challenge entry = (Challenge)this.displayedChallenges.get(lineIdx);
            return entry != null ? entry : null;
         }
      }
   }

   public boolean m_6375_(double mouseX, double mouseY, int button) {
      Challenge ch = this.findEntry((int)mouseX, (int)mouseY);
      if (ch != null && this.m_5953_(mouseX, mouseY) && button == 0) {
         boolean isAlreadySelected = this.parent.getSelectedChallenge() != null && this.parent.getSelectedChallenge().equals(ch);
         if (!isAlreadySelected) {
            Minecraft.m_91087_().m_91106_().m_120367_(SimpleSoundInstance.m_263171_(SoundEvents.f_12490_, 1.0F));
            this.parent.startInfoPanelAnimation();
            this.parent.setSelectedChallenge(ch);
         }
      }

      return super.m_6375_(mouseX, mouseY, button);
   }

   public boolean m_5953_(double mouseX, double mouseY) {
      return mouseX >= (double)this.left && mouseY >= (double)this.top && mouseX < (double)(this.left + this.width - 5) && mouseY < (double)(this.top + this.height);
   }

   public NarratableEntry.NarrationPriority m_142684_() {
      return NarrationPriority.NONE;
   }

   public void m_142291_(NarrationElementOutput output) {
   }
}
