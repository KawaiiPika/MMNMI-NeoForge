package xyz.pixelatedw.mineminenomi.data.entity.quest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.registries.IForgeRegistry;
import xyz.pixelatedw.mineminenomi.api.WyRegistry;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.Objective;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.init.ModAdvancements;

public class QuestDataBase implements IQuestData {
   private Player owner;
   private Quest[] inProgressQuests = new Quest[4];
   private Map<QuestId<? extends Quest>, Long> onCooldownQuests = new HashMap();
   private List<QuestId<?>> finishedQuests = new ArrayList();

   public QuestDataBase(Player owner) {
      this.owner = owner;
   }

   public boolean isQuestOnCooldown(QuestId<? extends Quest> questId) {
      return this.getQuestCooldown(questId) > System.currentTimeMillis();
   }

   public long getQuestCooldown(QuestId<? extends Quest> questId) {
      return (Long)this.onCooldownQuests.getOrDefault(questId, 0L);
   }

   public boolean addQuestOnCooldown(QuestId<? extends Quest> questId) {
      if (questId.isRepeatable() && !this.isQuestOnCooldown(questId)) {
         long cooldown = questId.getRepeatCooldown();
         if (cooldown > 0L) {
            this.onCooldownQuests.put(questId, System.currentTimeMillis() + cooldown);
         }

         return true;
      } else {
         return false;
      }
   }

   public boolean addInProgressQuest(QuestId<?> quest) {
      for(int i = 0; i < this.inProgressQuests.length; ++i) {
         Quest ogQuest = this.inProgressQuests[i];
         if (ogQuest == null) {
            this.inProgressQuests[i] = quest.createQuest();
            return true;
         }
      }

      return false;
   }

   public boolean addInProgressQuest(Quest quest) {
      for(int i = 0; i < this.inProgressQuests.length; ++i) {
         Quest ogQuest = this.inProgressQuests[i];
         if (ogQuest == null) {
            this.inProgressQuests[i] = quest;
            return true;
         }
      }

      return false;
   }

   public boolean setInProgressQuest(int slot, Quest quest) {
      Quest ogQuest = this.getInProgressQuest(quest.getCore());
      if (ogQuest == null && slot <= 4) {
         this.inProgressQuests[slot] = quest;
         return true;
      } else {
         return false;
      }
   }

   public boolean removeInProgressQuest(QuestId<?> quest) {
      Quest ogQuest = this.getInProgressQuest(quest);
      if (ogQuest != null) {
         for(int i = 0; i < this.inProgressQuests.length; ++i) {
            Quest inProgressQuest = this.inProgressQuests[i];
            if (inProgressQuest != null && inProgressQuest.equals(ogQuest)) {
               inProgressQuest.resetProgress(this.owner);
               this.inProgressQuests[i] = null;
               return true;
            }
         }
      }

      return false;
   }

   public boolean hasInProgressQuest(QuestId<?> quest) {
      return Arrays.stream(this.inProgressQuests).filter((qst) -> qst != null).anyMatch((qst) -> qst.getCore().equals(quest));
   }

   public <T extends Quest> T getInProgressQuest(QuestId<T> quest) {
      return (T)(Arrays.stream(this.inProgressQuests).filter((qst) -> qst != null && qst.getCore().equals(quest)).findFirst().orElse((Object)null));
   }

   public Quest getInProgressQuest(int slot) {
      return this.inProgressQuests[slot];
   }

   public int getInProgressQuestSlot(Quest quest) {
      for(int i = 0; i < this.inProgressQuests.length; ++i) {
         if (this.inProgressQuests[i] != null && this.inProgressQuests[i].equals(quest)) {
            return i;
         }
      }

      return -1;
   }

   public <T extends Objective> List<T> getInProgressObjectives() {
      return (List)Arrays.stream(this.getInProgressQuests()).filter((q) -> q != null && !q.isComplete()).flatMap((q) -> q.getObjectives().stream()).filter((o) -> !o.isHidden() && !o.isLocked()).collect(Collectors.toList());
   }

   public Quest[] getInProgressQuests() {
      return this.inProgressQuests;
   }

   public void clearInProgressQuests() {
      for(int i = 0; i < this.inProgressQuests.length; ++i) {
         Quest quest = this.inProgressQuests[i];
         if (quest != null) {
            this.inProgressQuests[i] = null;
         }
      }

   }

   public int countInProgressQuests() {
      return Arrays.stream(this.inProgressQuests).filter((quest) -> quest != null).mapToInt((q) -> 1).sum();
   }

   public boolean addFinishedQuest(QuestId<?> quest) {
      QuestId<?> ogQuest = this.getFinishedQuest(quest);
      if (ogQuest != null) {
         return false;
      } else {
         Player var4 = this.owner;
         if (var4 instanceof ServerPlayer) {
            ServerPlayer serverPlayer = (ServerPlayer)var4;
            ModAdvancements.COMPLETE_QUEST.trigger(serverPlayer, quest);
         }

         this.finishedQuests.add(quest);
         return true;
      }
   }

   public boolean removeFinishedQuest(QuestId<?> quest) {
      QuestId<?> ogQuest = this.getFinishedQuest(quest);
      if (ogQuest != null) {
         this.finishedQuests.remove(ogQuest);
         return true;
      } else {
         return false;
      }
   }

   public boolean hasFinishedQuest(QuestId<?> quest) {
      return !ServerConfig.isQuestProgressionEnabled() ? true : this.finishedQuests.stream().filter((q) -> {
         QuestId.get(q.getRegistryKey());
         return q != null;
      }).anyMatch((qst) -> qst.equals(quest));
   }

   public <T extends QuestId<?>> T getFinishedQuest(T quest) {
      return (T)(this.finishedQuests.stream().filter((qst) -> qst != null && qst.equals(quest)).findFirst().orElse((Object)null));
   }

   public List<QuestId<?>> getFinishedQuests() {
      return (List)this.finishedQuests.stream().filter((q) -> q != null).collect(Collectors.toList());
   }

   public void clearFinishedQuests() {
      this.finishedQuests.clear();
   }

   public int countFinishedQuests() {
      return this.finishedQuests.size();
   }

   public CompoundTag serializeNBT() {
      CompoundTag nbt = new CompoundTag();
      ListTag questsOnCooldownTag = new ListTag();

      for(Map.Entry<QuestId<? extends Quest>, Long> entry : this.onCooldownQuests.entrySet()) {
         QuestId<? extends Quest> questId = (QuestId)entry.getKey();
         ResourceLocation key = ((IForgeRegistry)WyRegistry.QUESTS.get()).getKey(questId);
         if (key != null) {
            long cooldown = (Long)entry.getValue();
            CompoundTag questOnCooldownTag = new CompoundTag();
            questOnCooldownTag.m_128359_("id", key.toString());
            questOnCooldownTag.m_128356_("cooldown", cooldown);
            questsOnCooldownTag.add(questOnCooldownTag);
         }
      }

      nbt.m_128365_("questsOnCooldownTag", questsOnCooldownTag);
      ListTag questsInTracker = new ListTag();

      for(int i = 0; i < this.getInProgressQuests().length; ++i) {
         Quest quest = this.getInProgressQuests()[i];
         if (quest != null) {
            CompoundTag tag = quest.save(new CompoundTag());
            questsInTracker.add(tag);
         }
      }

      nbt.m_128365_("quests_in_tracker", questsInTracker);
      ListTag finishedQuests = new ListTag();

      for(int i = 0; i < this.getFinishedQuests().size(); ++i) {
         QuestId<?> quest = (QuestId)this.getFinishedQuests().get(i);
         ResourceLocation key = ((IForgeRegistry)WyRegistry.QUESTS.get()).getKey(quest);
         if (key != null) {
            CompoundTag tag = new CompoundTag();
            tag.m_128359_("id", key.toString());
            finishedQuests.add(tag);
         }
      }

      nbt.m_128365_("finished_quests", finishedQuests);
      return nbt;
   }

   public void deserializeNBT(CompoundTag nbt) {
      this.onCooldownQuests.clear();
      this.clearInProgressQuests();
      this.clearFinishedQuests();
      ListTag questsOnCooldownTag = nbt.m_128437_("questsOnCooldownTag", 10);

      for(int i = 0; i < questsOnCooldownTag.size(); ++i) {
         CompoundTag questOnCooldownTag = questsOnCooldownTag.m_128728_(i);
         ResourceLocation key = ResourceLocation.parse(questOnCooldownTag.m_128461_("id"));
         QuestId<?> questId = (QuestId)((IForgeRegistry)WyRegistry.QUESTS.get()).getValue(key);
         if (questId != null) {
            long cooldown = questOnCooldownTag.m_128454_("cooldown");
            this.onCooldownQuests.put(questId, cooldown);
         }
      }

      ListTag trackerQuests = nbt.m_128437_("quests_in_tracker", 10);

      for(int i = 0; i < trackerQuests.size(); ++i) {
         try {
            CompoundTag tag = trackerQuests.m_128728_(i);
            Quest quest = Quest.from(tag);
            if (quest != null) {
               this.setInProgressQuest(i, quest);
            }
         } catch (Exception var10) {
         }
      }

      ListTag finishedQuests = nbt.m_128437_("finished_quests", 10);

      for(int i = 0; i < finishedQuests.size(); ++i) {
         try {
            CompoundTag tag = finishedQuests.m_128728_(i);
            ResourceLocation key = ResourceLocation.parse(tag.m_128461_("id"));
            QuestId<?> quest = (QuestId)((IForgeRegistry)WyRegistry.QUESTS.get()).getValue(key);
            this.addFinishedQuest(quest);
         } catch (Exception var9) {
         }
      }

   }
}
