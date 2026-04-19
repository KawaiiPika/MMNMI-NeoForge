package xyz.pixelatedw.mineminenomi.datagen.loottables.structures.bandit;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.data.conditions.RandomizedFruitsCondition;
import xyz.pixelatedw.mineminenomi.data.functions.EncyclopediaCompletionFunction;
import xyz.pixelatedw.mineminenomi.data.functions.SetBellyInPouchFunction;
import xyz.pixelatedw.mineminenomi.data.functions.SetFruitClueFunction;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;

public class BanditFortLootTable {
   private static final LootPool.Builder SECRET_STASH;
   private static final LootPool.Builder RANDOMIZED_FRUITS_CLUES;
   private static final LootPool.Builder RANDOMIZED_FRUITS_BOOK;
   private static final LootPool.Builder FOOD_SUPPLIES;
   private static final LootPool.Builder DORM_SUPPLIES;

   public static Pair<String, LootTable.Builder>[] getTables() {
      LootTable.Builder secret_stash = LootTable.m_79147_().m_79161_(SECRET_STASH).m_79161_(RANDOMIZED_FRUITS_CLUES).m_79161_(RANDOMIZED_FRUITS_BOOK);
      LootTable.Builder food = LootTable.m_79147_().m_79161_(FOOD_SUPPLIES);
      LootTable.Builder dorm = LootTable.m_79147_().m_79161_(DORM_SUPPLIES);
      return new Pair[]{Pair.of("secret_stash", secret_stash), Pair.of("food", food), Pair.of("dorm", dorm)};
   }

   static {
      SECRET_STASH = LootPool.m_79043_().name("mineminenomi:secret_stash").m_165133_(UniformGenerator.m_165780_(1.0F, 4.0F)).m_79076_(LootItem.m_79579_((ItemLike)ModItems.BELLY_POUCH.get()).m_79707_(100).m_79078_(SetBellyInPouchFunction.builder(UniformGenerator.m_165780_(100.0F, 200.0F))).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 2.0F)))).m_79076_(LootItem.m_79579_((ItemLike)ModItems.BELLY_POUCH.get()).m_79707_(20).m_79078_(SetBellyInPouchFunction.builder(UniformGenerator.m_165780_(1000.0F, 2000.0F)))).m_79076_(LootItem.m_79579_(Items.f_42415_).m_79707_(70).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 7.0F)))).m_79076_(LootItem.m_79579_(Items.f_42616_).m_79707_(60).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(5.0F, 10.0F)))).m_79076_(LootItem.m_79579_(Items.f_42593_).m_79707_(30).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 3.0F)))).m_79076_(LootItem.m_79579_(Items.f_41912_).m_79707_(30).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(5.0F, 10.0F)))).m_79076_(LootItem.m_79579_((ItemLike)ModBlocks.EISEN_DIAL.get()).m_79707_(20).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 3.0F)))).m_79076_(LootItem.m_79579_((ItemLike)ModBlocks.BREATH_DIAL.get()).m_79707_(15).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 3.0F)))).m_79076_(LootItem.m_79579_((ItemLike)ModBlocks.IMPACT_DIAL.get()).m_79707_(15).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 3.0F)))).m_79076_(LootItem.m_79579_((ItemLike)ModItems.TIER_1_BOX.get()).m_79707_(45).m_79711_(2)).m_79076_(LootItem.m_79579_((ItemLike)ModItems.TIER_2_BOX.get()).m_79707_(25).m_79711_(2));
      RANDOMIZED_FRUITS_CLUES = LootPool.m_79043_().name("mineminenomi:randomized_fruits").m_165133_(UniformGenerator.m_165780_(1.0F, 4.0F)).m_79076_(LootItem.m_79579_(ItemStack.f_41583_.m_41720_()).m_79707_(15)).m_79076_(LootItem.m_79579_(Items.f_42516_).m_79707_(5).m_79711_(2).m_79078_(SetFruitClueFunction.builder())).m_79080_(RandomizedFruitsCondition.builder());
      RANDOMIZED_FRUITS_BOOK = LootPool.m_79043_().name("mineminenomi:randomized_fruits_book").m_165133_(ConstantValue.m_165692_(1.0F)).m_79076_(LootItem.m_79579_(ItemStack.f_41583_.m_41720_()).m_79707_(18)).m_79076_(LootItem.m_79579_((ItemLike)ModItems.DEVIL_FRUIT_ENCYCLOPEDIA.get()).m_79707_(2).m_79711_(2).m_79078_(EncyclopediaCompletionFunction.builder())).m_79080_(RandomizedFruitsCondition.builder());
      FOOD_SUPPLIES = LootPool.m_79043_().name("mineminenomi:food_supplies").m_165133_(UniformGenerator.m_165780_(1.0F, 3.0F)).m_79076_(LootItem.m_79579_(Items.f_42406_).m_79707_(100).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(5.0F, 7.0F)))).m_79076_(LootItem.m_79579_(Items.f_42572_).m_79707_(100).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(3.0F, 12.0F)))).m_79076_(LootItem.m_79579_(Items.f_42410_).m_79707_(100).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(3.0F, 7.0F)))).m_79076_(LootItem.m_79579_((ItemLike)ModItems.TANGERINE.get()).m_79707_(50).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 3.0F)))).m_79076_(LootItem.m_79579_((ItemLike)ModItems.TANGERINE.get()).m_79707_(100).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(3.0F, 7.0F)))).m_79076_(LootItem.m_79579_(Items.f_42674_).m_79707_(60).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(2.0F, 7.0F)))).m_79076_(LootItem.m_79579_((ItemLike)ModItems.BOTTLE_OF_RUM.get()).m_79707_(60).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 4.0F)))).m_79076_(LootItem.m_79579_(Items.f_42699_).m_79707_(60).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 3.0F)))).m_79076_(LootItem.m_79579_(Items.f_42659_).m_79707_(60).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(2.0F, 7.0F)))).m_79076_(LootItem.m_79579_((ItemLike)ModItems.COLA.get()).m_79707_(60).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(2.0F, 5.0F)))).m_79076_(LootItem.m_79579_(Items.f_42502_).m_79707_(30)).m_79076_(LootItem.m_79579_((ItemLike)ModItems.ULTRA_COLA.get()).m_79707_(20).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 3.0F)))).m_79076_(LootItem.m_79579_((ItemLike)ModItems.SAKE_BOTTLE.get()).m_79707_(20).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 2.0F)))).m_79076_(LootItem.m_79579_((ItemLike)ModItems.SEA_KING_MEAT.get()).m_79707_(10));
      DORM_SUPPLIES = LootPool.m_79043_().name("mineminenomi:dorm_supplies").m_165133_(UniformGenerator.m_165780_(3.0F, 5.0F)).m_79076_(LootItem.m_79579_((ItemLike)ModItems.BELLY_POUCH.get()).m_79707_(100).m_79078_(SetBellyInPouchFunction.builder(UniformGenerator.m_165780_(100.0F, 300.0F))).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 3.0F)))).m_79076_(LootItem.m_79579_((ItemLike)ModItems.CIGARETTE.get()).m_79707_(100).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 2.0F)))).m_79076_(LootItem.m_79579_((ItemLike)ModWeapons.DAGGER.get()).m_79707_(80)).m_79076_(LootItem.m_79579_((ItemLike)ModWeapons.AXE.get()).m_79707_(80)).m_79076_(LootItem.m_79579_((ItemLike)ModWeapons.KATANA.get()).m_79707_(80)).m_79076_(LootItem.m_79579_((ItemLike)ModWeapons.CLEAVER.get()).m_79707_(60)).m_79076_(LootItem.m_79579_((ItemLike)ModWeapons.MACE.get()).m_79707_(60)).m_79076_(LootItem.m_79579_((ItemLike)ModWeapons.FLINTLOCK.get()).m_79707_(80)).m_79076_(LootItem.m_79579_((ItemLike)ModWeapons.SENRIKU.get()).m_79707_(60)).m_79076_(LootItem.m_79579_(Items.f_42740_).m_79707_(60)).m_79076_(LootItem.m_79579_((ItemLike)ModItems.ROPE_NET.get()).m_79707_(10)).m_79076_(LootItem.m_79579_(Items.f_42383_).m_79707_(60)).m_79076_(LootItem.m_79579_(Items.f_42502_).m_79707_(20));
   }
}
