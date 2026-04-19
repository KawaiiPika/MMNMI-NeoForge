package xyz.pixelatedw.mineminenomi.api.quests;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.Objective;
import xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool;

public abstract class Quest {
    private QuestId<? extends Quest> core;
    private List<Objective> objectives = new ArrayList<>();
    private final PriorityEventPool<IStartQuestEvent> startEvents = new PriorityEventPool<>();
    private final PriorityEventPool<ICompleteQuestEvent> completeEvents = new PriorityEventPool<>();
    private final PriorityEventPool<ITurnInQuestEvent> turnInEvents = new PriorityEventPool<>();

    public Quest(QuestId<? extends Quest> core) {
        this.core = core;
    }

    @Nullable
    public static Quest from(CompoundTag tag) {
        String coreId = tag.getString("id");
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
        for (Objective obj : objs) {
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
        if (this.objectives.isEmpty()) return 1.0F;
        int maxProgress = this.objectives.size();
        long completed = this.objectives.stream().filter((objective) -> !objective.isOptional() && objective.isComplete()).count();
        return (float) completed / (float) maxProgress;
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
        if (this.core != null) {
            nbt.putString("id", this.core.getId().toString());
        }
        ListTag objectivesData = new ListTag();

        for (Objective obj : this.getObjectives()) {
            objectivesData.add(obj.save());
        }

        nbt.put("objectives", objectivesData);
        return nbt;
    }

    public void load(CompoundTag nbt) {
        ListTag objectivesData = nbt.getList("objectives", 10);

        for (int i = 0; i < objectivesData.size(); ++i) {
            if (i < this.getObjectives().size()) {
                CompoundTag questData = objectivesData.getCompound(i);
                this.getObjectives().get(i).load(questData);
            }
        }
    }

    @Override
    public boolean equals(Object object) {
        if (object != null && this.getCore() != null) {
            if (object instanceof Quest) {
                Quest quest = (Quest) object;
                return this.getCore().equals(quest.getCore());
            } else if (object instanceof QuestId) {
                QuestId<?> questId = (QuestId<?>) object;
                return this.getCore().equals(questId);
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.core != null ? this.core.hashCode() : 0;
    }

    @FunctionalInterface
    public interface ICompleteQuestEvent {
        void complete(Player player);
    }

    @FunctionalInterface
    public interface IStartQuestEvent {
        void start(Player player);
    }

    @FunctionalInterface
    public interface ITurnInQuestEvent {
        void turnIn(Player player);
    }
}
