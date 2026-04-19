package xyz.pixelatedw.mineminenomi.datagen.loottables;

import java.util.function.BiConsumer;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import xyz.pixelatedw.mineminenomi.data.conditions.RandomChanceWithLuckCondition;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.ModLootTables;

public class BlocksLootTablesDataGen implements LootTableSubProvider {
   public void m_245126_(BiConsumer<ResourceLocation, LootTable.Builder> writer) {
      writer.accept(ModLootTables.KAIROSEKI_ORE_BLOCK, this.genLootTable(this.ore((Item)ModItems.KAIROSEKI.get(), 1, 2, 2.0F, 4.0F)));
      writer.accept(ModLootTables.DEEPSLATE_KAIROSEKI_ORE_BLOCK, this.genLootTable(this.ore((Item)ModItems.KAIROSEKI.get(), 1, 2, 3.0F, 5.0F)));
      writer.accept(ModLootTables.AXE_DIAL_BLOCK, this.genLootTable(this.self((Block)ModBlocks.AXE_DIAL.get())));
      writer.accept(ModLootTables.BREATH_DIAL_BLOCK, this.genLootTable(this.self((Block)ModBlocks.BREATH_DIAL.get())));
      writer.accept(ModLootTables.FLAME_DIAL_BLOCK, this.genLootTable(this.self((Block)ModBlocks.FLAME_DIAL.get())));
      writer.accept(ModLootTables.REJECT_DIAL_BLOCK, this.genLootTable(this.self((Block)ModBlocks.REJECT_DIAL.get())));
      writer.accept(ModLootTables.IMPACT_DIAL_BLOCK, this.genLootTable(this.self((Block)ModBlocks.IMPACT_DIAL.get())));
      writer.accept(ModLootTables.FLASH_DIAL_BLOCK, this.genLootTable(this.self((Block)ModBlocks.FLASH_DIAL.get())));
      writer.accept(ModLootTables.EISEN_DIAL_BLOCK, this.genLootTable(this.self((Block)ModBlocks.EISEN_DIAL.get())));
      writer.accept(ModLootTables.MILKY_DIAL_BLOCK, this.genLootTable(this.self((Block)ModBlocks.MILKY_DIAL.get())));
      writer.accept(ModLootTables.DEN_DEN_MUSHI_BLOCK, this.genLootTable(this.self((Block)ModBlocks.DEN_DEN_MUSHI.get())));
      writer.accept(ModLootTables.KAIROSEKI_BLOCK, this.genLootTable(this.self((Block)ModBlocks.KAIROSEKI.get())));
      writer.accept(ModLootTables.KAIROSEKI_BARS_BLOCK, this.genLootTable(this.self((Block)ModBlocks.KAIROSEKI_BARS.get())));
      writer.accept(ModLootTables.MANGROVE_LOG_BLOCK, this.genLootTable(this.self((Block)ModBlocks.MANGROVE_LOG.get())));
      writer.accept(ModLootTables.MANGROVE_PLANKS_BLOCK, this.genLootTable(this.self((Block)ModBlocks.MANGROVE_PLANKS.get())));
      writer.accept(ModLootTables.MANGROVE_WOOD_BLOCK, this.genLootTable(this.self((Block)ModBlocks.MANGROVE_WOOD.get())));
      writer.accept(ModLootTables.STRIPPED_MANGROVE_LOG_BLOCK, this.genLootTable(this.self((Block)ModBlocks.STRIPPED_MANGROVE_LOG.get())));
      writer.accept(ModLootTables.STRIPPED_MANGROVE_WOOD_BLOCK, this.genLootTable(this.self((Block)ModBlocks.STRIPPED_MANGROVE_WOOD.get())));
      writer.accept(ModLootTables.DESIGN_BARREL_BLOCK, this.genLootTable(this.self(Blocks.f_50618_)));
      LootTable.Builder bubblyCoralTable = (new LootTable.Builder()).m_79161_(LootPool.m_79043_().m_165133_(ConstantValue.m_165692_(1.0F)).m_79076_(LootItem.m_79579_((ItemLike)ModItems.BUBBLY_CORAL.get()).m_79080_(RandomChanceWithLuckCondition.randomChance(0.015F, -0.025F))));
      this.addVanillaLootTable(writer, ResourceLocation.parse("blocks/brain_coral_block"), bubblyCoralTable);
   }

   protected void addVanillaLootTable(BiConsumer<ResourceLocation, LootTable.Builder> writer, ResourceLocation key, LootTable.Builder builder) {
      ResourceLocation key2 = ResourceLocation.fromNamespaceAndPath("mineminenomi", "vanilla/" + key.m_135815_());
      writer.accept(key2, builder);
   }

   protected LootPool.Builder basic(Item drop, int roll, float count) {
      return this.basic(drop, roll, roll, count, count);
   }

   protected LootPool.Builder basic(Item drop, int rollMin, int rollMax, float countMin, float countMax) {
      NumberProvider rollRange;
      if (rollMin == rollMax) {
         rollRange = ConstantValue.m_165692_((float)rollMin);
      } else {
         rollRange = UniformGenerator.m_165780_((float)rollMin, (float)rollMax);
      }

      NumberProvider countRange;
      if (countMin == countMax) {
         countRange = ConstantValue.m_165692_((float)((int)countMin));
      } else {
         countRange = UniformGenerator.m_165780_(countMin, countMax);
      }

      return LootPool.m_79043_().m_165133_(rollRange).m_79076_(LootItem.m_79579_(drop).m_79078_(SetItemCountFunction.m_165412_(countRange)));
   }

   protected LootPool.Builder self(Block block) {
      return LootPool.m_79043_().m_165133_(ConstantValue.m_165692_(1.0F)).m_79076_(LootItem.m_79579_(block.m_5456_()).m_79078_(SetItemCountFunction.m_165412_(ConstantValue.m_165692_(1.0F))));
   }

   protected LootPool.Builder ore(Item ore, int roll, float count) {
      return this.ore(ore, roll, roll, count, count);
   }

   protected LootPool.Builder ore(Item ore, int rollMin, int rollMax, float countMin, float countMax) {
      NumberProvider rollRange;
      if (rollMin == rollMax) {
         rollRange = ConstantValue.m_165692_((float)rollMin);
      } else {
         rollRange = UniformGenerator.m_165780_((float)rollMin, (float)rollMax);
      }

      NumberProvider countRange;
      if (countMin == countMax) {
         countRange = ConstantValue.m_165692_((float)((int)countMin));
      } else {
         countRange = UniformGenerator.m_165780_(countMin, countMax);
      }

      return LootPool.m_79043_().m_165133_(rollRange).m_79076_(LootItem.m_79579_(ore).m_79078_(SetItemCountFunction.m_165412_(countRange)).m_79078_(ApplyBonusCount.m_79915_(Enchantments.f_44987_)));
   }

   protected LootTable.Builder genLootTable(LootPool.Builder... pools) {
      LootTable.Builder builder = LootTable.m_79147_();

      for(LootPool.Builder pool : pools) {
         builder.m_79161_(pool);
      }

      return builder;
   }

   public static LootTableProvider.SubProviderEntry create() {
      return new LootTableProvider.SubProviderEntry(BlocksLootTablesDataGen::new, LootContextParamSets.f_81421_);
   }
}
