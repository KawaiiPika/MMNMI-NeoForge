package xyz.pixelatedw.mineminenomi.datagen.loottables.structures.vanilla;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import xyz.pixelatedw.mineminenomi.init.ModItems;

public class UnderwaterRuinLootTable {
   private static final LootPool.Builder TREASURE;

   public static LootTable.Builder getPool() {
      return LootTable.m_79147_().m_79161_(TREASURE);
   }

   static {
      TREASURE = LootPool.m_79043_().name("mineminenomi:items").m_165133_(UniformGenerator.m_165780_(0.0F, 1.0F)).m_79076_(LootItem.m_79579_(ItemStack.f_41583_.m_41720_()).m_79707_(384)).m_79076_(LootItem.m_79579_((ItemLike)ModItems.TIER_1_BOX.get()).m_79707_(2).m_79711_(1)).m_79076_(LootItem.m_79579_((ItemLike)ModItems.TIER_2_BOX.get()).m_79707_(1).m_79711_(2));
   }
}
