package xyz.pixelatedw.mineminenomi.datagen.loottables.structures.vanilla;

import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import xyz.pixelatedw.mineminenomi.data.functions.SetBellyInPouchFunction;
import xyz.pixelatedw.mineminenomi.init.ModItems;

public class SpawnBonusChestLootTable {
   private static final LootPool.Builder BELLY_BONUS;
   private static final LootPool.Builder KEY_BONUS;

   public static LootTable.Builder getPool() {
      return LootTable.m_79147_().m_79161_(BELLY_BONUS).m_79161_(KEY_BONUS);
   }

   static {
      BELLY_BONUS = LootPool.m_79043_().name("mineminenomi:belly_bonus").m_165133_(ConstantValue.m_165692_(1.0F)).m_79076_(LootItem.m_79579_((ItemLike)ModItems.BELLY_POUCH.get()).m_79078_(SetBellyInPouchFunction.builder(ConstantValue.m_165692_(1000.0F))));
      KEY_BONUS = LootPool.m_79043_().name("mineminenomi:key_bonus").m_165133_(ConstantValue.m_165692_(1.0F)).m_79076_(LootItem.m_79579_((ItemLike)ModItems.KEY.get()).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 2.0F))));
   }
}
