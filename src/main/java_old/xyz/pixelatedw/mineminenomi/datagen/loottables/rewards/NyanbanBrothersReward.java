package xyz.pixelatedw.mineminenomi.datagen.loottables.rewards;

import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore;
import xyz.pixelatedw.mineminenomi.data.conditions.FirstCompletionRewardCondition;
import xyz.pixelatedw.mineminenomi.data.functions.UnlockChallengesFunction;
import xyz.pixelatedw.mineminenomi.init.ModChallenges;

public class NyanbanBrothersReward {
   private static final LootPool.Builder HARD_MODE;

   public static Pair<String, LootTable.Builder>[] getTables() {
      LootTable.Builder rewards = LootTable.m_79147_().m_79161_(HARD_MODE);
      return new Pair[]{Pair.of("rewards", rewards)};
   }

   static {
      HARD_MODE = LootPool.m_79043_().name("mineminenomi:hard_mode").m_165133_(ConstantValue.m_165692_(1.0F)).m_79080_(FirstCompletionRewardCondition.builder()).m_79076_(LootItem.m_79579_(Items.f_41852_).m_79078_(UnlockChallengesFunction.builder((ChallengeCore)ModChallenges.NYANBAN_BROTHERS_HARD.get())));
   }
}
