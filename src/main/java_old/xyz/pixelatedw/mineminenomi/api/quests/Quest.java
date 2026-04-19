package xyz.pixelatedw.mineminenomi.api.quests;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.registries.IForgeRegistry;
import xyz.pixelatedw.mineminenomi.api.WyRegistry;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.Objective;
import xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool;

public abstract class Quest {
   private QuestId<? extends Quest> core;
   private List<Objective> objectives = new ArrayList();
   private final PriorityEventPool<IStartQuestEvent> startEvents = new PriorityEventPool<IStartQuestEvent>();
   private final PriorityEventPool<ICompleteQuestEvent> completeEvents = new PriorityEventPool<ICompleteQuestEvent>();
   private final PriorityEventPool<ITurnInQuestEvent> turnInEvents = new PriorityEventPool<ITurnInQuestEvent>();

   public Quest(QuestId<? extends Quest> core) {
      this.core = core;
   }

   @Nullable
   public static Quest from(CompoundTag tag) {
      String coreId = tag.m_128461_("id");
      QuestId<?> core = QuestId.get(ResourceLocation.parse(coreId));
      if (core == null) {
         return null;
      } else {
         Quest quest = core.createQuest();
         if (quest == null) {
            return null;
         } else {
            quest.load(tag);
            return quest;
         }
      }
   }

   public void addStartEvent(int priority, IStartQuestEvent event) {
      this.startEvents.addEvent(priority, event);
   }

   public void addCompleteEvent(int priority, ICompleteQuestEvent event) {
      this.completeEvents.addEvent(priority, event);
   }

   public void addTurnInEvent(int priority, ITurnInQuestEvent event) {
      this.turnInEvents.addEvent(priority, event);
   }

   public void addObjectives(Objective... objs) {
      for(Objective obj : objs) {
         this.objectives.add(obj);
      }

   }

   public boolean tryComplete(Player player) {
      if (this.isComplete()) {
         this.completeEvents.dispatch((ev) -> ev.complete(player));
         return true;
      } else {
         return false;
      }
   }

   public void triggerStartEvents(Player player) {
      this.startEvents.dispatch((ev) -> ev.start(player));
   }

   public void triggerCompleteEvents(Player player) {
      this.completeEvents.dispatch((ev) -> ev.complete(player));
   }

   public void triggerTurnInEvents(Player player) {
      this.turnInEvents.dispatch((ev) -> ev.turnIn(player));
   }

   public boolean isComplete() {
      return this.getProgress() >= 1.0F;
   }

   public float getProgress() {
      int maxProgress = this.objectives.size();
      long completed = this.objectives.stream().filter((objective) -> !objective.isOptional() && objective.isComplete()).count();
      float progress = (float)completed / (float)maxProgress;
      return progress;
   }

   public void resetProgress(Player player) {
      this.objectives.stream().forEach((o) -> o.setProgress(player, 0.0F, true));
   }

   public List<Objective> getObjectives() {
      return this.objectives;
   }

   public QuestId<? extends Quest> getCore() {
      return this.core;
   }

   public CompoundTag save(CompoundTag nbt) {
      ResourceLocation key = ((IForgeRegistry)WyRegistry.QUESTS.get()).getKey(this.core);
      nbt.m_128359_("id", key.toString());
      ListTag objectivesData = new ListTag();

      for(Objective obj : this.getObjectives()) {
         objectivesData.add(obj.save());
      }

      nbt.m_128365_("objectives", objectivesData);
      return nbt;
   }

   public void load(CompoundTag nbt) {
      ListTag objectivesData = nbt.m_128437_("objectives", 10);

      for(int i = 0; i < objectivesData.size(); ++i) {
         CompoundTag questData = objectivesData.m_128728_(i);
         ((Objective)this.getObjectives().get(i)).load(questData);
      }

   }

   public boolean equals(Object object) {
      if (object != null && this.getCore() != null) {
         if (object instanceof Quest) {
            Quest quest = (Quest)object;
            if (quest.getCore() == null) {
               return false;
            }

            if (this.getCore().equals(quest.getCore())) {
               return true;
            }
         } else if (object instanceof QuestId) {
            QuestId<?> questId = (QuestId)object;
            if (this.getCore().equals(questId)) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   @FunctionalInterface
   public interface ICompleteQuestEvent {
      void complete(Player var1);
   }

   @FunctionalInterface
   public interface IStartQuestEvent {
      void start(Player var1);
   }

   @FunctionalInterface
   public interface ITurnInQuestEvent {
      void turnIn(Player var1);
   }
}
