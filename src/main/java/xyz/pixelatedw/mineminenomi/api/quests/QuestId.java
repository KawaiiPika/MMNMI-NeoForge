package xyz.pixelatedw.mineminenomi.api.quests;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.init.ModRegistries;

public class QuestId<A extends Quest> {
    private ResourceLocation key;
    private Component title;
    private IFactory<A> factory;
    private long repeatCooldown = -1L;
    private List<Supplier<QuestId<?>>> requirements = new ArrayList<>();

    protected QuestId(IFactory<A> factory) {
        this.factory = factory;
    }

    public List<Supplier<QuestId<?>>> getRequirements() {
        return this.requirements;
    }

    private void setRequirements(List<Supplier<QuestId<?>>> requirements) {
        this.requirements = requirements;
    }

    public void setKey(ResourceLocation key) {
        this.key = key;
    }

    public ResourceLocation getId() {
        return this.key;
    }

    public Component getTitle() {
        if (this.title == null) {
            this.title = Component.translatable("quest." + this.key.getNamespace() + "." + this.key.getPath());
        }
        return this.title;
    }

    public boolean isLocked(Player player) {
        // Quest data logic needs implementation in PlayerStats or similar
        return false;
    }

    public boolean isRepeatable() {
        return this.repeatCooldown >= 0L;
    }

    public long getRepeatCooldown() {
        return this.repeatCooldown;
    }

    public void setRepeatCooldown(long repeatCooldown) {
        this.repeatCooldown = repeatCooldown;
    }

    public A createQuest() {
        return this.factory.create(this);
    }

    @Nullable
    public static QuestId<?> get(ResourceLocation res) {
        return ModRegistries.QUESTS.get(res);
    }

    public static class Builder<A extends Quest> {
        private IFactory<A> factory;
        private long repeatCooldown = -1L;
        private List<Supplier<QuestId<?>>> requirements = new ArrayList<>();

        public Builder(IFactory<A> factory) {
            this.factory = factory;
        }

        public Builder<A> setRepeatCooldown(long repeatCooldown) {
            this.repeatCooldown = repeatCooldown;
            return this;
        }

        @SafeVarargs
        public final Builder<A> addRequirements(Supplier<? extends QuestId<?>>... requirements) {
            for (Supplier<? extends QuestId<?>> req : requirements) {
                this.requirements.add((Supplier<QuestId<?>>) req);
            }
            return this;
        }

        public QuestId<A> build() {
            QuestId<A> core = new QuestId<>(this.factory);
            core.setRepeatCooldown(this.repeatCooldown);
            core.setRequirements(this.requirements);
            return core;
        }
    }

    public interface IFactory<A extends Quest> {
        A create(QuestId<A> core);
    }
}
