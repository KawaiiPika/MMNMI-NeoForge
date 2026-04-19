package xyz.pixelatedw.mineminenomi.ui.screens;

import java.util.List;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.ui.IEventReceiverScreen;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;
import xyz.pixelatedw.mineminenomi.ui.events.TrialsScreenEvent;
import xyz.pixelatedw.mineminenomi.ui.panel.TrainerTrialsScrollPanel;
import xyz.pixelatedw.mineminenomi.ui.screens.dialogues.TrainerDialogueScreen;
import xyz.pixelatedw.mineminenomi.ui.widget.FactionButton;

public class TrainerTrialsListScreen extends Screen implements IEventReceiverScreen<TrialsScreenEvent> {
   private final Screen previousScreen;
   private final LivingEntity trainer;
   private final List<TrainerDialogueScreen.QuestEntry> entries;
   private final boolean isInCombat;
   private TrainerTrialsScrollPanel questsPanel;

   public TrainerTrialsListScreen(Screen previousScreen, LivingEntity trainer, List<TrainerDialogueScreen.QuestEntry> entries, boolean isInCombat) {
      super(Component.m_237119_());
      this.previousScreen = previousScreen;
      this.trainer = trainer;
      this.entries = entries;
      this.isInCombat = isInCombat;
   }

   public void m_88315_(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
      this.m_280273_(graphics);
      int posX = this.f_96543_ / 2;
      int posY = this.f_96544_ / 2;
      this.questsPanel.m_88315_(graphics, mouseX, mouseY, partialTicks);
      super.m_88315_(graphics, mouseX, mouseY, partialTicks);
   }

   public void m_7856_() {
      int posX = this.f_96543_ / 2;
      int posY = this.f_96544_ / 2;
      this.questsPanel = new TrainerTrialsScrollPanel(this, this.entries);
      this.m_142416_(this.questsPanel);
      this.m_7522_(this.questsPanel);
      FactionButton cancelButton = new FactionButton(80, this.f_96544_ - 40, 100, 20, ModI18n.GUI_CANCEL, this::goToPrevious);
      this.m_142416_(cancelButton);
   }

   public void goToPrevious(Button btn) {
      this.f_96541_.m_91152_(this.previousScreen);
   }

   public void handleEvent(TrialsScreenEvent event) {
      if (event.getQuestId() != null) {
         this.questsPanel.removeEntry(event.getQuestId());
      }

   }

   public TrialsScreenEvent decode(CompoundTag data) {
      TrialsScreenEvent event = new TrialsScreenEvent();
      event.deserializeNBT(data);
      return event;
   }
}
