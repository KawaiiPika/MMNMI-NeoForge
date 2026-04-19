package xyz.pixelatedw.mineminenomi.datagen.loottables.structures.vanilla.village;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import xyz.pixelatedw.mineminenomi.init.ModArmors;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.init.ModItems;

public class VillageTempleLootTable {
   private static final LootPool.Builder ITEMS;

   public static LootTable.Builder getPool() {
      return LootTable.m_79147_().m_79161_(ITEMS);
   }

   static {
      ITEMS = LootPool.m_79043_().name("mineminenomi:items").m_165133_(ConstantValue.m_165692_(1.0F)).m_79076_(LootItem.m_79579_(ItemStack.f_41583_.m_41720_()).m_79707_(90)).m_79076_(LootItem.m_79579_((ItemLike)ModItems.TANGERINE.get()).m_79707_(10).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(3.0F, 5.0F)))).m_79076_(LootItem.m_79579_((ItemLike)ModBlocks.DEN_DEN_MUSHI.get()).m_79707_(50)).m_79076_(LootItem.m_79579_((ItemLike)ModArmors.MEDIC_BAG.get()).m_79707_(20).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 2.0F)))).m_79076_(LootItem.m_79579_((ItemLike)ModItems.TIER_1_BOX.get()).m_79707_(5).m_79711_(2));
   }
}
