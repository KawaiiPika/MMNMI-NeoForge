package xyz.pixelatedw.mineminenomi.datagen.loottables.structures.vanilla.village;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import xyz.pixelatedw.mineminenomi.init.ModArmors;

public class VillageTanneryLootTable {
   private static final LootPool.Builder ITEMS;

   public static LootTable.Builder getPool() {
      return LootTable.m_79147_().m_79161_(ITEMS);
   }

   static {
      ITEMS = LootPool.m_79043_().name("mineminenomi:items").m_165133_(ConstantValue.m_165692_(1.0F)).m_79076_(LootItem.m_79579_(ItemStack.f_41583_.m_41720_()).m_79707_(75)).m_79076_(LootItem.m_79579_((ItemLike)ModArmors.FLUFFY_CAPE.get()).m_79707_(5).m_79711_(2)).m_79076_(LootItem.m_79579_((ItemLike)ModArmors.TRICORNE.get()).m_79707_(5).m_79711_(2)).m_79076_(LootItem.m_79579_((ItemLike)ModArmors.BANDANA.get()).m_79707_(5).m_79711_(2)).m_79076_(LootItem.m_79579_(Items.f_42450_).m_79707_(10).m_79711_(2));
   }
}
