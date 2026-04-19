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
import xyz.pixelatedw.mineminenomi.data.functions.SetBellyInPouchFunction;
import xyz.pixelatedw.mineminenomi.data.functions.SetFruitClueFunction;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;

public class CampLootTable {
   private static final LootPool.Builder SMALL_TENT_FOOD;
   private static final LootPool.Builder SMALL_TENT_WEAPONS;
   private static final LootPool.Builder LARGE_TENT_FOOD;
   private static final LootPool.Builder LARGE_TENT_TREASURE;
   private static final LootPool.Builder LARGE_TENT_RANDOMIZED_FRUITS;

   public static Pair<String, LootTable.Builder>[] getTables() {
      LootTable.Builder small_tent = LootTable.m_79147_().m_79161_(SMALL_TENT_WEAPONS).m_79161_(SMALL_TENT_FOOD);
      LootTable.Builder large_tent = LootTable.m_79147_().m_79161_(LARGE_TENT_FOOD).m_79161_(LARGE_TENT_TREASURE).m_79161_(LARGE_TENT_RANDOMIZED_FRUITS);
      return new Pair[]{Pair.of("small_tent", small_tent), Pair.of("large_tent", large_tent)};
   }

   static {
      SMALL_TENT_FOOD = LootPool.m_79043_().name("mineminenomi:food").m_165133_(UniformGenerator.m_165780_(2.0F, 5.0F)).m_79076_(LootItem.m_79579_(Items.f_42410_).m_79707_(100).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(3.0F, 7.0F)))).m_79076_(LootItem.m_79579_((ItemLike)ModItems.TANGERINE.get()).m_79707_(100).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(3.0F, 7.0F)))).m_79076_(LootItem.m_79579_(Items.f_42780_).m_79707_(100).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(2.0F, 5.0F)))).m_79076_(LootItem.m_79579_(Items.f_42572_).m_79707_(80).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(2.0F, 10.0F)))).m_79076_(LootItem.m_79579_(Items.f_42399_).m_79707_(80).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 3.0F)))).m_79076_(LootItem.m_79579_(Items.f_42619_).m_79707_(50).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 3.0F)))).m_79076_(LootItem.m_79579_(Items.f_42732_).m_79707_(50).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 4.0F)))).m_79076_(LootItem.m_79579_(Items.f_42406_).m_79707_(40).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 4.0F))));
      SMALL_TENT_WEAPONS = LootPool.m_79043_().name("mineminenomi:weapons").m_165133_(UniformGenerator.m_165780_(0.0F, 3.0F)).m_79076_(LootItem.m_79579_((ItemLike)ModItems.BULLET.get()).m_79707_(100).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 15.0F)))).m_79076_(LootItem.m_79579_(Items.f_42403_).m_79707_(80).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(3.0F, 7.0F)))).m_79076_(LootItem.m_79579_((ItemLike)ModWeapons.PIPE.get()).m_79707_(90)).m_79076_(LootItem.m_79579_((ItemLike)ModWeapons.FLINTLOCK.get()).m_79707_(90)).m_79076_(LootItem.m_79579_((ItemLike)ModWeapons.MACE.get()).m_79707_(40)).m_79076_(LootItem.m_79579_((ItemLike)ModItems.KAIROSEKI_BULLET.get()).m_79707_(20).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(3.0F, 6.0F))));
      LARGE_TENT_FOOD = LootPool.m_79043_().name("mineminenomi:food").m_165133_(UniformGenerator.m_165780_(2.0F, 4.0F)).m_79076_(LootItem.m_79579_(Items.f_42406_).m_79707_(100).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(2.0F, 4.0F)))).m_79076_(LootItem.m_79579_(Items.f_42400_).m_79707_(100).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 3.0F)))).m_79076_(LootItem.m_79579_(Items.f_42734_).m_79707_(100).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 3.0F)))).m_79076_(LootItem.m_79579_((ItemLike)ModItems.TANGERINE.get()).m_79707_(100).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(3.0F, 6.0F)))).m_79076_(LootItem.m_79579_(Items.f_42697_).m_79707_(50).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 3.0F)))).m_79076_(LootItem.m_79579_(Items.f_42485_).m_79707_(50).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(2.0F, 4.0F)))).m_79076_(LootItem.m_79579_((ItemLike)ModItems.BOTTLE_OF_RUM.get()).m_79707_(20).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 2.0F)))).m_79076_(LootItem.m_79579_((ItemLike)ModItems.COLA.get()).m_79707_(20).m_79711_(2).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 4.0F))));
      LARGE_TENT_TREASURE = LootPool.m_79043_().name("mineminenomi:treasure").m_165133_(UniformGenerator.m_165780_(2.0F, 4.0F)).m_79076_(LootItem.m_79579_((ItemLike)ModItems.BELLY_POUCH.get()).m_79707_(100).m_79078_(SetBellyInPouchFunction.builder(UniformGenerator.m_165780_(10.0F, 100.0F))).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 3.0F)))).m_79076_(LootItem.m_79579_((ItemLike)ModItems.BELLY_POUCH.get()).m_79707_(20).m_79078_(SetBellyInPouchFunction.builder(UniformGenerator.m_165780_(50.0F, 500.0F))).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 2.0F)))).m_79076_(LootItem.m_79579_((ItemLike)ModWeapons.FLINTLOCK.get()).m_79707_(80).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 2.0F)))).m_79076_(LootItem.m_79579_((ItemLike)ModItems.KAIROSEKI_BULLET.get()).m_79707_(80).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(5.0F, 10.0F)))).m_79076_(LootItem.m_79579_((ItemLike)ModItems.WANTED_POSTER_PACKAGE.get()).m_79707_(50)).m_79076_(LootItem.m_79579_((ItemLike)ModBlocks.DEN_DEN_MUSHI.get()).m_79707_(50)).m_79076_(LootItem.m_79579_(Items.f_42522_).m_79707_(50)).m_79076_(LootItem.m_79579_((ItemLike)ModWeapons.SENRIKU.get()).m_79707_(20)).m_79076_(LootItem.m_79579_(Items.f_42450_).m_79707_(40));
      LARGE_TENT_RANDOMIZED_FRUITS = LootPool.m_79043_().name("mineminenomi:randomized_fruits").m_165133_(UniformGenerator.m_165780_(1.0F, 2.0F)).m_79076_(LootItem.m_79579_(ItemStack.f_41583_.m_41720_()).m_79707_(37)).m_79076_(LootItem.m_79579_(Items.f_42516_).m_79707_(2).m_79711_(5).m_79078_(SetFruitClueFunction.builder())).m_79076_(LootItem.m_79579_((ItemLike)ModItems.DEVIL_FRUIT_ENCYCLOPEDIA.get()).m_79707_(1).m_79711_(2).m_79078_(EncyclopediaCompletionFunction.builder())).m_79080_(RandomizedFruitsCondition.builder());
   }
}
