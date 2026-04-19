package xyz.pixelatedw.mineminenomi.datagen.loottables.structures.vanilla;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;

public class StrongholdLootTable {
   private static final LootPool.Builder ITEMS;

   public static LootTable.Builder getPool() {
      return LootTable.m_79147_().m_79161_(ITEMS);
   }

   static {
      ITEMS = LootPool.m_79043_().name("mineminenomi:items").m_165133_(UniformGenerator.m_165780_(1.0F, 2.0F)).m_79076_(LootItem.m_79579_(ItemStack.f_41583_.m_41720_()).m_79707_(20)).m_79076_(LootItem.m_79579_((ItemLike)ModWeapons.BROADSWORD.get()).m_79707_(20)).m_79076_(LootItem.m_79579_((ItemLike)ModWeapons.BISENTO.get()).m_79707_(20)).m_79076_(LootItem.m_79579_((ItemLike)ModWeapons.KATANA.get()).m_79707_(20)).m_79076_(LootItem.m_79579_((ItemLike)ModWeapons.CLEAVER.get()).m_79707_(20)).m_79076_(LootItem.m_79579_((ItemLike)ModItems.BOTTLE_OF_RUM.get()).m_79707_(10)).m_79076_(LootItem.m_79579_((ItemLike)ModItems.BULLET.get()).m_79707_(50).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(5.0F, 10.0F)))).m_79076_(LootItem.m_79579_((ItemLike)ModItems.KAIROSEKI_BULLET.get()).m_79707_(20).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(2.0F, 5.0F)))).m_79076_(LootItem.m_79579_((ItemLike)ModItems.NORMAL_HANDCUFFS.get()).m_79707_(20)).m_79076_(LootItem.m_79579_((ItemLike)ModItems.KAIROSEKI_NET.get()).m_79707_(5)).m_79076_(LootItem.m_79579_((ItemLike)ModItems.ROPE_NET.get()).m_79707_(5));
   }
}
