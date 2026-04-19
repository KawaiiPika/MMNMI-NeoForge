package xyz.pixelatedw.mineminenomi.datagen.loottables.entities;

import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootContext.EntityTarget;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.LootingEnchantFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SmeltItemFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import xyz.pixelatedw.mineminenomi.datagen.loottables.EntitiesLootTablesDataGen;

public class SeaCowDrops {
   private static final LootPool.Builder ITEMS;

   public static LootTable.Builder getTable() {
      LootTable.Builder drops = LootTable.m_79147_().m_79161_(ITEMS);
      return drops;
   }

   static {
      ITEMS = LootPool.m_79043_().name("mineminenomi:items").m_165133_(ConstantValue.m_165692_(1.0F)).m_79076_(LootItem.m_79579_(Items.f_41867_).m_79707_(100).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(0.0F, 5.0F)))).m_79076_(LootItem.m_79579_(Items.f_42454_).m_79707_(90).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 4.0F))).m_79078_(LootingEnchantFunction.m_165229_(UniformGenerator.m_165780_(0.0F, 2.0F)))).m_79076_(LootItem.m_79579_(Items.f_42579_).m_79707_(90).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 4.0F))).m_79078_(SmeltItemFunction.m_81271_().m_79080_(LootItemEntityPropertyCondition.m_81867_(EntityTarget.THIS, EntitiesLootTablesDataGen.ON_FIRE_CHECK))).m_79078_(LootingEnchantFunction.m_165229_(UniformGenerator.m_165780_(0.0F, 2.0F))));
   }
}
