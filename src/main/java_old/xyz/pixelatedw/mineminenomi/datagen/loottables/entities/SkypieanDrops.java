package xyz.pixelatedw.mineminenomi.datagen.loottables.entities;

import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import xyz.pixelatedw.mineminenomi.data.functions.SetExtolInPouchFunction;
import xyz.pixelatedw.mineminenomi.init.ModItems;

public class SkypieanDrops {
   private static final LootPool.Builder ITEMS;
   private static final LootPool.Builder ITEMS2;

   public static LootTable.Builder getTable() {
      LootTable.Builder drops = LootTable.m_79147_().m_79161_(ITEMS).m_79161_(ITEMS2);
      return drops;
   }

   static {
      ITEMS = LootPool.m_79043_().name("mineminenomi:items").m_165133_(UniformGenerator.m_165780_(0.0F, 1.0F)).m_79076_(LootItem.m_79579_((ItemLike)ModItems.EXTOL_POUCH.get()).m_79707_(100).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(0.0F, 1.0F))).m_79078_(SetExtolInPouchFunction.builder(UniformGenerator.m_165780_(10000.0F, 50000.0F))));
      ITEMS2 = LootPool.m_79043_().name("mineminenomi:items2").m_165133_(UniformGenerator.m_165780_(0.0F, 1.0F)).m_79076_(LootItem.m_79579_(Items.f_42402_).m_79707_(100).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 2.0F))));
   }
}
