package xyz.pixelatedw.mineminenomi.datagen.loottables.rewards;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetNbtFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore;
import xyz.pixelatedw.mineminenomi.data.conditions.FirstCompletionRewardCondition;
import xyz.pixelatedw.mineminenomi.data.functions.UnlockChallengesFunction;
import xyz.pixelatedw.mineminenomi.init.ModArmors;
import xyz.pixelatedw.mineminenomi.init.ModChallenges;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;

public class AlvidaReward {
   public static final CompoundTag PLUME_HAT_NBT = createBicorneNbt();
   private static final LootPool.Builder HARD_MODE;
   private static final LootPool.Builder ITEMS;

   private static CompoundTag createBicorneNbt() {
      CompoundTag nbt = new CompoundTag();
      CompoundTag display = new CompoundTag();
      display.m_128405_("color", 12788538);
      nbt.m_128365_("display", display);
      return nbt;
   }

   public static Pair<String, LootTable.Builder>[] getTables() {
      LootTable.Builder rewards = LootTable.m_79147_().m_79161_(HARD_MODE).m_79161_(ITEMS);
      return new Pair[]{Pair.of("rewards", rewards)};
   }

   static {
      HARD_MODE = LootPool.m_79043_().name("mineminenomi:hard_mode").m_165133_(ConstantValue.m_165692_(1.0F)).m_79080_(FirstCompletionRewardCondition.builder()).m_79076_(LootItem.m_79579_(Items.f_41852_).m_79078_(UnlockChallengesFunction.builder((ChallengeCore)ModChallenges.ALVIDA_HARD.get())));
      ITEMS = LootPool.m_79043_().name("mineminenomi:items").m_165133_(ConstantValue.m_165692_(1.0F)).m_79076_(EmptyLootItem.m_79533_().m_79707_(97).m_79711_(-5)).m_79076_(LootItem.m_79579_((ItemLike)ModWeapons.MACE.get()).m_79707_(1)).m_79076_(LootItem.m_79579_((ItemLike)ModArmors.PLUME_HAT.get()).m_79078_(SetNbtFunction.m_81187_(PLUME_HAT_NBT)).m_79707_(2));
   }
}
