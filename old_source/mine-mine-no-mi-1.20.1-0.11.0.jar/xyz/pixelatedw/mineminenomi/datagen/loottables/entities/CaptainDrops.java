package xyz.pixelatedw.mineminenomi.datagen.loottables.entities;

import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceWithLootingCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import xyz.pixelatedw.mineminenomi.data.functions.SetBellyInPouchFunction;
import xyz.pixelatedw.mineminenomi.init.ModItems;

public class CaptainDrops {
   private static final LootPool.Builder ITEMS;
   private static final LootPool.Builder RARE_ITEMS;
   private static final LootPool.Builder KEY;

   public static LootTable.Builder getTable() {
      LootTable.Builder drops = LootTable.m_79147_().m_79161_(ITEMS).m_79161_(RARE_ITEMS).m_79161_(KEY);
      return drops;
   }

   static {
      ITEMS = LootPool.m_79043_().name("mineminenomi:items").m_165133_(ConstantValue.m_165692_(1.0F)).m_79076_(EmptyLootItem.m_79533_().m_79707_(100)).m_79076_(LootItem.m_79579_((ItemLike)ModItems.BELLY_POUCH.get()).m_79707_(100).m_79078_(SetItemCountFunction.m_165412_(ConstantValue.m_165692_(1.0F))).m_79078_(SetBellyInPouchFunction.builder(UniformGenerator.m_165780_(50.0F, 100.0F))));
      RARE_ITEMS = LootPool.m_79043_().name("mineminenomi:items2").m_165133_(ConstantValue.m_165692_(1.0F)).m_79080_(LootItemKilledByPlayerCondition.m_81901_()).m_79076_(LootItem.m_79579_((ItemLike)ModItems.BOTTLE_OF_RUM.get()).m_79707_(100).m_79078_(SetItemCountFunction.m_165412_(ConstantValue.m_165692_(1.0F)))).m_79076_(LootItem.m_79579_((ItemLike)ModItems.CIGAR.get()).m_79707_(43).m_79078_(SetItemCountFunction.m_165412_(ConstantValue.m_165692_(1.0F))));
      KEY = LootPool.m_79043_().name("mineminenomi:key").m_165133_(ConstantValue.m_165692_(1.0F)).m_79080_(LootItemKilledByPlayerCondition.m_81901_()).m_79080_(LootItemRandomChanceWithLootingCondition.m_81963_(0.5F, 0.1F)).m_79076_(LootItem.m_79579_((ItemLike)ModItems.KEY.get()).m_79707_(100).m_79078_(SetItemCountFunction.m_165412_(ConstantValue.m_165692_(1.0F))));
   }
}
