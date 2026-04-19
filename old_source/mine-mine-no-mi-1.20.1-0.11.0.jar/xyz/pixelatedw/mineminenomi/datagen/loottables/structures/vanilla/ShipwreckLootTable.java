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

public class ShipwreckLootTable {
   private static final LootPool.Builder TREASURE;
   private static final LootPool.Builder SUPPLY;
   private static final LootPool.Builder ITEMS;

   public static LootTable.Builder getTreasurePool() {
      return LootTable.m_79147_().m_79161_(TREASURE);
   }

   public static LootTable.Builder getSupplyPool() {
      return LootTable.m_79147_().m_79161_(SUPPLY).m_79161_(ITEMS);
   }

   static {
      TREASURE = LootPool.m_79043_().name("mineminenomi:treasure").m_165133_(UniformGenerator.m_165780_(0.0F, 1.0F)).m_79076_(LootItem.m_79579_(ItemStack.f_41583_.m_41720_()).m_79707_(200)).m_79076_(LootItem.m_79579_((ItemLike)ModItems.BELLY_POUCH.get()).m_79707_(100).m_79078_(SetBellyInPouchFunction.builder(UniformGenerator.m_165780_(10.0F, 50.0F)))).m_79076_(LootItem.m_79579_((ItemLike)ModItems.TIER_1_BOX.get()).m_79707_(6).m_79711_(1)).m_79076_(LootItem.m_79579_((ItemLike)ModItems.TIER_2_BOX.get()).m_79707_(3).m_79711_(2));
      SUPPLY = LootPool.m_79043_().name("mineminenomi:weapons").m_165133_(UniformGenerator.m_165780_(1.0F, 2.0F)).m_79076_(LootItem.m_79579_(ItemStack.f_41583_.m_41720_()).m_79707_(200)).m_79076_(LootItem.m_79579_((ItemLike)ModWeapons.BROADSWORD.get()).m_79707_(40)).m_79076_(LootItem.m_79579_((ItemLike)ModWeapons.KATANA.get()).m_79707_(40)).m_79076_(LootItem.m_79579_((ItemLike)ModWeapons.DAGGER.get()).m_79707_(40)).m_79076_(LootItem.m_79579_((ItemLike)ModWeapons.CUTLASS.get()).m_79707_(40)).m_79076_(LootItem.m_79579_((ItemLike)ModWeapons.SPEAR.get()).m_79707_(40));
      ITEMS = LootPool.m_79043_().name("mineminenomi:items").m_165133_(UniformGenerator.m_165780_(1.0F, 2.0F)).m_79076_(LootItem.m_79579_(ItemStack.f_41583_.m_41720_()).m_79707_(50)).m_79076_(LootItem.m_79579_((ItemLike)ModItems.BULLET.get()).m_79707_(40).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(3.0F, 10.0F)))).m_79076_(LootItem.m_79579_((ItemLike)ModItems.KEY.get()).m_79707_(20)).m_79076_(LootItem.m_79579_((ItemLike)ModItems.KAIROSEKI_BULLET.get()).m_79707_(20).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(3.0F, 8.0F)))).m_79076_(LootItem.m_79579_((ItemLike)ModItems.BOTTLE_OF_RUM.get()).m_79707_(30).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 3.0F)))).m_79076_(LootItem.m_79579_((ItemLike)ModItems.CANNON_BALL.get()).m_79707_(40).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(2.0F, 7.0F))));
   }
}
