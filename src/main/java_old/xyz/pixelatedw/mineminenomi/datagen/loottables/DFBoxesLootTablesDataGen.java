package xyz.pixelatedw.mineminenomi.datagen.loottables;

import java.util.function.BiConsumer;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import xyz.pixelatedw.mineminenomi.data.conditions.RandomChanceWithLuckCondition;
import xyz.pixelatedw.mineminenomi.data.functions.FruitAlreadyExistsFunction;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.ModLootTables;
import xyz.pixelatedw.mineminenomi.init.ModValues;
import xyz.pixelatedw.mineminenomi.items.AkumaNoMiBoxItem;

public class DFBoxesLootTablesDataGen implements LootTableSubProvider {
   public void m_245126_(BiConsumer<ResourceLocation, LootTable.Builder> writer) {
      writer.accept(ModLootTables.WOODEN_BOX, this.genLootTable((AkumaNoMiBoxItem)ModItems.TIER_1_BOX.get()));
      writer.accept(ModLootTables.IRON_BOX, this.genLootTable((AkumaNoMiBoxItem)ModItems.TIER_2_BOX.get()));
      writer.accept(ModLootTables.GOLDEN_BOX, this.genLootTable((AkumaNoMiBoxItem)ModItems.TIER_3_BOX.get()));
   }

   protected LootTable.Builder genLootTable(AkumaNoMiBoxItem item) {
      LootTable.Builder builder = LootTable.m_79147_();
      LootPool.Builder sameTierPool = LootPool.m_79043_().name("mineminenomi:fruits").m_165133_(ConstantValue.m_165692_(5.0F)).m_79080_(RandomChanceWithLuckCondition.randomChance(0.95F, -0.05F));
      ModValues.DEVIL_FRUITS.stream().filter((df) -> df.getTier() == item.getTierLevel()).forEach((df) -> sameTierPool.m_79076_(LootItem.m_79579_(df).m_79707_(100).m_79078_(FruitAlreadyExistsFunction.builder())));
      if (item.getTierLevel() < 3) {
         LootPool.Builder nextTierFruits = LootPool.m_79043_().name("mineminenomi:next_tier_fruits").m_165133_(ConstantValue.m_165692_(1.0F)).m_79080_(RandomChanceWithLuckCondition.randomChance(0.05F, 0.05F));
         ModValues.DEVIL_FRUITS.stream().filter((df) -> df.getTier() == item.getTierLevel() + 1).forEach((df) -> nextTierFruits.m_79076_(LootItem.m_79579_(df).m_79707_(100).m_79078_(FruitAlreadyExistsFunction.builder())));
         builder.m_79161_(nextTierFruits);
      }

      builder.m_79161_(sameTierPool);
      return builder;
   }

   public static LootTableProvider.SubProviderEntry create() {
      return new LootTableProvider.SubProviderEntry(DFBoxesLootTablesDataGen::new, LootContextParamSets.f_81410_);
   }
}
