package xyz.pixelatedw.mineminenomi.datagen.loottables.structures;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.data.conditions.RandomizedFruitsCondition;
import xyz.pixelatedw.mineminenomi.data.functions.EncyclopediaCompletionFunction;
import xyz.pixelatedw.mineminenomi.data.functions.SetFruitClueFunction;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;

public class GhostShipLootTable {
   private static final LootPool.Builder SUPPLIES_LEFTOVERS;
   private static final LootPool.Builder CAPTAIN_CHEST;
   private static final LootPool.Builder CAPTAIN_RANDOMIZED_FRUITS;

   public static Pair<String, LootTable.Builder>[] getTables() {
      LootTable.Builder supplies = LootTable.m_79147_().m_79161_(SUPPLIES_LEFTOVERS);
      LootTable.Builder captain = LootTable.m_79147_().m_79161_(CAPTAIN_CHEST).m_79161_(CAPTAIN_RANDOMIZED_FRUITS);
      return new Pair[]{Pair.of("supplies", supplies), Pair.of("captain", captain)};
   }

   static {
      SUPPLIES_LEFTOVERS = LootPool.m_79043_().name("mineminenomi:leftovers").m_165133_(UniformGenerator.m_165780_(0.0F, 5.0F)).m_79076_(LootItem.m_79579_((ItemLike)ModWeapons.CUTLASS.get()).m_79707_(100).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 3.0F)))).m_79076_(LootItem.m_79579_((ItemLike)ModWeapons.KATANA.get()).m_79707_(20).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 3.0F)))).m_79076_(LootItem.m_79579_((ItemLike)ModWeapons.AXE.get()).m_79707_(20).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 3.0F)))).m_79076_(LootItem.m_79579_((ItemLike)ModWeapons.FLINTLOCK.get()).m_79707_(20)).m_79076_(LootItem.m_79579_((ItemLike)ModItems.BULLET.get()).m_79707_(70).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 5.0F)))).m_79076_(LootItem.m_79579_(Items.f_42516_).m_79707_(60).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 5.0F)))).m_79076_(LootItem.m_79579_(Items.f_42403_).m_79707_(70).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(2.0F, 5.0F)))).m_79076_(LootItem.m_79579_(Items.f_42718_).m_79707_(100).m_79711_(-2).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 5.0F)))).m_79076_(LootItem.m_79579_(Items.f_42675_).m_79707_(100).m_79711_(-2).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(2.0F, 5.0F))));
      CAPTAIN_CHEST = LootPool.m_79043_().name("mineminenomi:captain_chest").m_165133_(UniformGenerator.m_165780_(2.0F, 3.0F)).m_79076_(LootItem.m_79579_(Items.f_42718_).m_79707_(100).m_79711_(-2).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 3.0F)))).m_79076_(LootItem.m_79579_((ItemLike)ModWeapons.CUTLASS.get()).m_79707_(20)).m_79076_(LootItem.m_79579_((ItemLike)ModWeapons.KATANA.get()).m_79707_(20)).m_79076_(LootItem.m_79579_((ItemLike)ModWeapons.AXE.get()).m_79707_(20)).m_79076_(LootItem.m_79579_(Items.f_151059_).m_79707_(20)).m_79076_(LootItem.m_79579_((ItemLike)ModBlocks.DEN_DEN_MUSHI.get()).m_79707_(18)).m_79076_(LootItem.m_79579_((ItemLike)ModItems.TIER_1_BOX.get()).m_79707_(3).m_79711_(5)).m_79076_(LootItem.m_79579_((ItemLike)ModItems.BULLET.get()).m_79707_(80).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 5.0F))));
      CAPTAIN_RANDOMIZED_FRUITS = LootPool.m_79043_().name("mineminenomi:randomized_fruits").m_165133_(UniformGenerator.m_165780_(1.0F, 2.0F)).m_79076_(LootItem.m_79579_(ItemStack.f_41583_.m_41720_()).m_79707_(17)).m_79076_(LootItem.m_79579_(Items.f_42516_).m_79707_(2).m_79711_(5).m_79078_(SetFruitClueFunction.builder())).m_79076_(LootItem.m_79579_((ItemLike)ModItems.DEVIL_FRUIT_ENCYCLOPEDIA.get()).m_79707_(1).m_79711_(2).m_79078_(EncyclopediaCompletionFunction.builder())).m_79080_(RandomizedFruitsCondition.builder());
   }
}
