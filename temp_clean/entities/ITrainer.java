package xyz.pixelatedw.mineminenomi.api.entities;

import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;

import java.util.ArrayList;
import java.util.List;

public interface ITrainer {
    List<QuestId<?>> EMPTY = new ArrayList<>();

    List<QuestId<?>> getAvailableQuests(Player player);
}
