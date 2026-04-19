package xyz.pixelatedw.mineminenomi.datagen.loottables.structures.vanilla.village;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;

public class VillageWeaponsmithLootTable {
   private static final LootPool.Builder ITEMS;

   public static LootTable.Builder getPool() {
      return LootTable.m_79147_().m_79161_(ITEMS);
   }

   static {
      ITEMS = LootPool.m_79043_().name("mineminenomi:items").m_165133_(UniformGenerator.m_165780_(1.0F, 2.0F)).m_79076_(LootItem.m_79579_(ItemStack.f_41583_.m_41720_()).m_79707_(20)).m_79076_(LootItem.m_79579_((ItemLike)ModWeapons.BROADSWORD.get()).m_79707_(5)).m_79076_(LootItem.m_79579_((ItemLike)ModWeapons.KATANA.get()).m_79707_(5)).m_79076_(LootItem.m_79579_((ItemLike)ModWeapons.CUTLASS.get()).m_79707_(5)).m_79076_(LootItem.m_79579_((ItemLike)ModWeapons.AXE.get()).m_79707_(5)).m_79076_(LootItem.m_79579_((ItemLike)ModWeapons.MACE.get()).m_79707_(5)).m_79076_(LootItem.m_79579_((ItemLike)ModItems.BOTTLE_OF_RUM.get()).m_79707_(10)).m_79076_(LootItem.m_79579_((ItemLike)ModItems.ULTRA_COLA.get()).m_79707_(10).m_79711_(2)).m_79076_(LootItem.m_79579_((ItemLike)ModItems.CIGARETTE.get()).m_79707_(10));
   }
}
