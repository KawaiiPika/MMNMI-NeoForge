package xyz.pixelatedw.mineminenomi.datagen.loottables.structures.vanilla;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import xyz.pixelatedw.mineminenomi.data.functions.SetBellyInPouchFunction;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;

public class DesertPyramidLootTable {
   private static final LootPool.Builder TREASURE;

   public static LootTable.Builder getPool() {
      return LootTable.m_79147_().m_79161_(TREASURE);
   }

   static {
      TREASURE = LootPool.m_79043_().name("mineminenomi:treasure").m_165133_(UniformGenerator.m_165780_(0.0F, 2.0F)).m_79076_(LootItem.m_79579_(ItemStack.f_41583_.m_41720_()).m_79707_(6)).m_79076_(LootItem.m_79579_((ItemLike)ModItems.BELLY_POUCH.get()).m_79707_(20).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 3.0F))).m_79078_(SetBellyInPouchFunction.builder(UniformGenerator.m_165780_(100.0F, 200.0F)))).m_79076_(LootItem.m_79579_((ItemLike)ModWeapons.HOOK.get()).m_79707_(7)).m_79076_(LootItem.m_79579_((ItemLike)ModWeapons.SPEAR.get()).m_79707_(10)).m_79076_(LootItem.m_79579_((ItemLike)ModItems.SMOKING_PIPE.get()).m_79707_(7)).m_79076_(LootItem.m_79579_((ItemLike)ModItems.TIER_1_BOX.get()).m_79707_(4).m_79711_(2));
   }
}
