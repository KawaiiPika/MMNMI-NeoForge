package xyz.pixelatedw.mineminenomi.datagen.loottables.entities;

import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.LootingEnchantFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import xyz.pixelatedw.mineminenomi.data.functions.SetBellyInPouchFunction;
import xyz.pixelatedw.mineminenomi.init.ModItems;

public class GruntDrops {
   private static final LootPool.Builder ITEMS;

   public static LootTable.Builder getTable() {
      LootTable.Builder drops = LootTable.m_79147_().m_79161_(ITEMS);
      return drops;
   }

   static {
      ITEMS = LootPool.m_79043_().name("mineminenomi:items").m_165133_(ConstantValue.m_165692_(1.0F)).m_79076_(EmptyLootItem.m_79533_().m_79707_(100)).m_79076_(LootItem.m_79579_((ItemLike)ModItems.BELLY_POUCH.get()).m_79707_(100).m_79078_(SetItemCountFunction.m_165412_(ConstantValue.m_165692_(1.0F))).m_79078_(SetBellyInPouchFunction.builder(UniformGenerator.m_165780_(5.0F, 10.0F)))).m_79076_(LootItem.m_79579_((ItemLike)ModItems.BULLET.get()).m_79707_(80).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(3.0F, 5.0F))).m_79078_(LootingEnchantFunction.m_165229_(UniformGenerator.m_165780_(0.0F, 5.0F)))).m_79076_(LootItem.m_79579_((ItemLike)ModItems.BULLET.get()).m_79707_(40).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 3.0F))).m_79078_(LootingEnchantFunction.m_165229_(UniformGenerator.m_165780_(0.0F, 2.0F))));
   }
}
