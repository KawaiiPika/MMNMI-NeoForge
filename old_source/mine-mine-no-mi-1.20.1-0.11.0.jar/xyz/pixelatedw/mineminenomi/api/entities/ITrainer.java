package xyz.pixelatedw.mineminenomi.api.entities;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;

public interface ITrainer {
   List<QuestId<?>> EMPTY = Lists.newArrayList();

   List<QuestId<?>> getAvailableQuests(Player var1);
}
