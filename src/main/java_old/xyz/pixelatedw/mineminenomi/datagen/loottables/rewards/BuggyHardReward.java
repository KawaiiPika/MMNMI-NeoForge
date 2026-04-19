package xyz.pixelatedw.mineminenomi.datagen.loottables.rewards;

import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetNbtFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.init.ModArmors;

public class BuggyHardReward {
   private static final LootPool.Builder ITEMS;

   public static Pair<String, LootTable.Builder>[] getTables() {
      LootTable.Builder rewards = LootTable.m_79147_().m_79161_(ITEMS);
      return new Pair[]{Pair.of("rewards", rewards)};
   }

   static {
      ITEMS = LootPool.m_79043_().name("mineminenomi:items").m_165133_(ConstantValue.m_165692_(1.0F)).m_79076_(EmptyLootItem.m_79533_().m_79707_(94).m_79711_(-5)).m_79076_(LootItem.m_79579_((ItemLike)ModArmors.BIG_RED_NOSE.get()).m_79707_(1)).m_79076_(LootItem.m_79579_((ItemLike)ModArmors.BICORNE.get()).m_79078_(SetNbtFunction.m_81187_(BuggyReward.BICORNE_NBT)).m_79707_(5));
   }
}
