package xyz.pixelatedw.mineminenomi.datagen.loottables.structures.vanilla;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import xyz.pixelatedw.mineminenomi.data.functions.SetBellyInPouchFunction;
import xyz.pixelatedw.mineminenomi.init.ModItems;

public class BuriedTreasureLootTable {
   private static final LootPool.Builder TREASURE;

   public static LootTable.Builder getPool() {
      return LootTable.m_79147_().m_79161_(TREASURE);
   }

   static {
      TREASURE = LootPool.m_79043_().name("mineminenomi:treasure").m_165133_(ConstantValue.m_165692_(1.0F)).m_79076_(LootItem.m_79579_(ItemStack.f_41583_.m_41720_()).m_79707_(5)).m_79076_(LootItem.m_79579_((ItemLike)ModItems.BELLY_POUCH.get()).m_79707_(10).m_79078_(SetBellyInPouchFunction.builder(UniformGenerator.m_165780_(1000.0F, 1250.0F)))).m_79076_(LootItem.m_79579_((ItemLike)ModItems.BELLY_POUCH.get()).m_79707_(3).m_79711_(5).m_79078_(SetBellyInPouchFunction.builder(UniformGenerator.m_165780_(1000.0F, 2000.0F)))).m_79076_(LootItem.m_79579_((ItemLike)ModItems.BUBBLY_CORAL.get()).m_79707_(3).m_79711_(2)).m_79076_(LootItem.m_79579_((ItemLike)ModItems.BOTTLE_OF_RUM.get()).m_79707_(6).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 3.0F)))).m_79076_(LootItem.m_79579_((ItemLike)ModItems.TIER_1_BOX.get()).m_79707_(2).m_79711_(5));
   }
}
