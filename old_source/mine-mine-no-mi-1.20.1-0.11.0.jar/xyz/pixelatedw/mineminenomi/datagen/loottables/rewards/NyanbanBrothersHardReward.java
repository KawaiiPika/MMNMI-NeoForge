package xyz.pixelatedw.mineminenomi.datagen.loottables.rewards;

import net.minecraft.world.level.storage.loot.LootTable;
import org.apache.commons.lang3.tuple.Pair;

public class NyanbanBrothersHardReward {
   public static Pair<String, LootTable.Builder>[] getTables() {
      LootTable.Builder rewards = LootTable.m_79147_();
      return new Pair[]{Pair.of("rewards", rewards)};
   }
}
