package xyz.pixelatedw.mineminenomi.data.entity.quest;

import java.util.List;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.Objective;

public interface IQuestData extends INBTSerializable<CompoundTag> {
   boolean isQuestOnCooldown(QuestId<? extends Quest> var1);

   long getQuestCooldown(QuestId<? extends Quest> var1);

   boolean addQuestOnCooldown(QuestId<? extends Quest> var1);

   boolean addInProgressQuest(QuestId<?> var1);

   boolean addInProgressQuest(Quest var1);

   boolean setInProgressQuest(int var1, Quest var2);

   boolean removeInProgressQuest(QuestId<?> var1);

   boolean hasInProgressQuest(QuestId<?> var1);

   <T extends Quest> T getInProgressQuest(QuestId<T> var1);

   Quest getInProgressQuest(int var1);

   int getInProgressQuestSlot(Quest var1);

   <T extends Objective> List<T> getInProgressObjectives();

   Quest[] getInProgressQuests();

   void clearInProgressQuests();

   int countInProgressQuests();

   boolean addFinishedQuest(QuestId<?> var1);

   boolean removeFinishedQuest(QuestId<?> var1);

   boolean hasFinishedQuest(QuestId<?> var1);

   <T extends QuestId<?>> T getFinishedQuest(T var1);

   List<QuestId<?>> getFinishedQuests();

   void clearFinishedQuests();

   int countFinishedQuests();
}
