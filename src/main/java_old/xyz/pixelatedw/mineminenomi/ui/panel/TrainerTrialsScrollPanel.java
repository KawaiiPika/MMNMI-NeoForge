package xyz.pixelatedw.mineminenomi.ui.panel;

import com.mojang.blaze3d.vertex.Tesselator;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.narration.NarratableEntry.NarrationPriority;
import net.minecraftforge.client.gui.widget.ScrollPanel;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.data.entity.quest.IQuestData;
import xyz.pixelatedw.mineminenomi.data.entity.quest.QuestCapability;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.client.quest.CUpdateQuestStatePacket;
import xyz.pixelatedw.mineminenomi.ui.screens.TrainerTrialsListScreen;
import xyz.pixelatedw.mineminenomi.ui.screens.dialogues.TrainerDialogueScreen;
import xyz.pixelatedw.mineminenomi.ui.widget.ScrollButton;

public class TrainerTrialsScrollPanel extends ScrollPanel {
   private static final int ENTRY_HEIGHT = 75;
   private final TrainerTrialsListScreen parent;
   private final List<TrainerDialogueScreen.QuestEntry> entries;
   private ScrollButton[] listButtons;

   public TrainerTrialsScrollPanel(TrainerTrialsListScreen parent, List<TrainerDialogueScreen.QuestEntry> entries) {
      super(parent.getMinecraft(), parent.f_96543_ / 3, parent.f_96544_ - 80, 20, 20);
      this.parent = parent;
      this.entries = entries;
      this.updateButtonsList();
   }

   protected void drawGradientRect(GuiGraphics guiGraphics, int left, int top, int right, int bottom, int color1, int color2) {
   }

   protected void drawPanel(GuiGraphics guiGraphics, int entryRight, int relativeY, Tesselator tess, int mouseX, int mouseY) {
      Minecraft mc = this.parent.getMinecraft();
      if (mc != null) {
         IQuestData questProps = (IQuestData)QuestCapability.getLazy(mc.f_91074_).orElse((Object)null);
         if (questProps != null) {
            int y = relativeY;
            int x = 25;
            int yOffset = 0;

            for(int i = 0; i < this.entries.size(); ++i) {
               TrainerDialogueScreen.QuestEntry entry = (TrainerDialogueScreen.QuestEntry)this.entries.get(i);
               if (entry != null) {
                  int questTextColor = -1;
                  ScrollButton questButton = this.listButtons[i];
                  questButton.f_93623_ = !entry.isLocked() && !questProps.isQuestOnCooldown(entry.getQuestId());
                  if (entry.isCompleted()) {
                     questTextColor = -16711936;
                  }

                  questButton.m_264152_(x, y + yOffset);
                  questButton.setFGColor(questTextColor);
                  questButton.m_88315_(guiGraphics, mouseX, mouseY, 0.0F);
                  if (entry.getQuestId().isRepeatable()) {
                     QuestId<? extends Quest> quest = entry.getQuestId();
                     long remainingSeconds = Math.max(0L, (questProps.getQuestCooldown(quest) - System.currentTimeMillis()) / 1000L);
                     long maxSeconds = Math.max(0L, quest.getRepeatCooldown() / 1000L);
                     String cooldownText = remainingSeconds + "s / " + maxSeconds + "s";
                     int padding = 4;
                     int textX = questButton.m_252754_() + questButton.m_5711_() - padding - mc.f_91062_.m_92895_(cooldownText) - 4;
                     int textY = questButton.m_252907_() + 12 + padding;
                     int color = remainingSeconds > 0L ? -1 : -5592406;
                     guiGraphics.m_280056_(mc.f_91062_, cooldownText, textX, textY, color, true);
                  }

                  yOffset += 75;
               }
            }

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
      return true;
   }

   @Nullable
   private TrainerDialogueScreen.QuestEntry findQuestEntry(int mouseX, int mouseY) {
      double offset = (double)((float)(mouseY - this.top) + this.scrollDistance);
      boolean isHovered = mouseX >= this.left && mouseY >= this.top && mouseX < this.left + this.width - 5 && mouseY < this.top + this.height;
      if (!(offset <= (double)0.0F) && isHovered) {
         int lineIdx = (int)(offset / (double)206.25F);
         if (lineIdx >= this.entries.size()) {
            return null;
         } else {
            TrainerDialogueScreen.QuestEntry entry = (TrainerDialogueScreen.QuestEntry)this.entries.get(lineIdx);
            return entry != null ? entry : null;
         }
      } else {
         return null;
      }
   }

   private void updateButtonsList() {
      this.listButtons = new ScrollButton[this.entries.size()];

      for(int i = 0; i < this.entries.size(); ++i) {
         TrainerDialogueScreen.QuestEntry entry = (TrainerDialogueScreen.QuestEntry)this.entries.get(i);
         if (entry != null) {
            ScrollButton questButton = new ScrollButton(0, 0, 200, 75, entry.getQuestId().getTitle(), (b) -> {
               if (!entry.isLocked()) {
                  ModNetwork.sendToServer(new CUpdateQuestStatePacket(entry.getQuestId()));
               }

            });
            this.listButtons[i] = questButton;
         }
      }

   }

   protected int getContentHeight() {
      return this.entries.size() * 75 - 2;
   }

   public void removeEntry(QuestId<?> questId) {
      this.entries.removeIf((entry) -> entry.getQuestId().equals(questId));
      this.updateButtonsList();
   }

   protected int getScrollAmount() {
      return 12;
   }

   public NarratableEntry.NarrationPriority m_142684_() {
      return NarrationPriority.NONE;
   }

   public void m_142291_(NarrationElementOutput p_169152_) {
   }
}
