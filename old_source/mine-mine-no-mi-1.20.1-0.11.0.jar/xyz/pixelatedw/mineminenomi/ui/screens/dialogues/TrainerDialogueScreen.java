package xyz.pixelatedw.mineminenomi.ui.screens.dialogues;

import java.util.List;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.entities.mobs.civilians.DoctorTrainerEntity;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nInteractions;
import xyz.pixelatedw.mineminenomi.packets.client.entity.interaction.CDoctorHealPacket;
import xyz.pixelatedw.mineminenomi.ui.screens.DialogueScreen;
import xyz.pixelatedw.mineminenomi.ui.screens.TrainerTrialsListScreen;

public class TrainerDialogueScreen extends DialogueScreen {
   private final List<QuestEntry> entries;
   private final boolean isInCombat;

   public TrainerDialogueScreen(LivingEntity entity, List<QuestEntry> entries, boolean isInCombat) {
      super(entity);
      this.entries = entries;
      this.isInCombat = isInCombat;
   }

   public void setupDialogue() {
      this.addInteraction(new DialogueScreen.InteractionAction(ModI18n.GUI_TRIALS, this::openTrialsMenu, (Tooltip)null));
      if (this.getTarget() instanceof DoctorTrainerEntity) {
         int payment = (int)(this.getPlayer().m_21233_() - this.getPlayer().m_21223_()) * 10;
         if (this.getPlayer().m_21233_() == this.getPlayer().m_21223_()) {
            payment = 0;
         }

         DialogueScreen.InteractionAction healInteraction = new DialogueScreen.InteractionAction(Component.m_237110_(ModI18n.GUI_HEAL_COST, new Object[]{payment}), this::heal);
         if (this.isInCombat) {
            healInteraction.setTooltip(Tooltip.m_257550_(ModI18n.GUI_COMBAT_CANNOT_USE));
            healInteraction.disable();
         } else if (this.getPlayer().m_21233_() != this.getPlayer().m_21223_()) {
            healInteraction.setTooltip(Tooltip.m_257550_(ModI18nInteractions.ALREADY_MAX_HEALTH_MESSAGE));
            healInteraction.disable();
         }

         this.addInteraction(healInteraction);
      }

      this.addInteraction(new DialogueScreen.InteractionAction(ModI18n.GUI_QUIT, (btn) -> this.m_7379_()));
   }

   private void heal(Button btn) {
      ModNetwork.sendToServer(new CDoctorHealPacket(this.getPlayer().m_19879_()));
   }

   private void openTrialsMenu(Button btn) {
      boolean hasQuests = false;

      for(int i = 0; i <= this.entries.size() - 1; ++i) {
         QuestEntry entry = (QuestEntry)this.entries.get(i);
         if (entry != null) {
            hasQuests = true;
            break;
         }
      }

      if (hasQuests) {
         this.f_96541_.m_91152_(new TrainerTrialsListScreen(this, this.getTarget(), this.entries, this.isInCombat));
      } else {
         this.setMessage(ModI18n.TRAINER_NO_TRIALS_AVAILABLE);
      }

   }

   public static class QuestEntry {
      private final QuestId<?> id;
      private boolean complete;
      private boolean locked;

      public QuestEntry(QuestId<?> id) {
         this.id = id;
      }

      public void setComplete() {
         this.complete = true;
      }

      public void setLocked() {
         this.locked = true;
      }

      public QuestId<?> getQuestId() {
         return this.id;
      }

      public boolean isCompleted() {
         return this.complete;
      }

      public boolean isLocked() {
         return this.locked;
      }
   }
}
