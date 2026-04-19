package xyz.pixelatedw.mineminenomi.datagen.loottables.rewards;

import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore;
import xyz.pixelatedw.mineminenomi.data.conditions.FirstCompletionRewardCondition;
import xyz.pixelatedw.mineminenomi.data.functions.UnlockChallengesFunction;
import xyz.pixelatedw.mineminenomi.init.ModArmors;
import xyz.pixelatedw.mineminenomi.init.ModChallenges;

public class KuroobiReward {
   private static final LootPool.Builder HARD_MODE;
   private static final LootPool.Builder ITEMS;

   public static Pair<String, LootTable.Builder>[] getTables() {
      LootTable.Builder rewards = LootTable.m_79147_().m_79161_(HARD_MODE).m_79161_(ITEMS);
      return new Pair[]{Pair.of("rewards", rewards)};
   }

   static {
      HARD_MODE = LootPool.m_79043_().name("mineminenomi:hard_mode").m_165133_(ConstantValue.m_165692_(1.0F)).m_79080_(FirstCompletionRewardCondition.builder()).m_79076_(LootItem.m_79579_(Items.f_41852_).m_79078_(UnlockChallengesFunction.builder((ChallengeCore)ModChallenges.KUROOBI_HARD.get())));
      ITEMS = LootPool.m_79043_().name("mineminenomi:items").m_165133_(ConstantValue.m_165692_(1.0F)).m_79076_(EmptyLootItem.m_79533_().m_79707_(94).m_79711_(-5)).m_79076_(LootItem.m_79579_((ItemLike)ModArmors.KUROOBI_CHEST.get()).m_79707_(2)).m_79076_(LootItem.m_79579_((ItemLike)ModArmors.KUROOBI_FEET.get()).m_79707_(2)).m_79076_(LootItem.m_79579_((ItemLike)ModArmors.KUROOBI_LEGS.get()).m_79707_(2));
   }
}
