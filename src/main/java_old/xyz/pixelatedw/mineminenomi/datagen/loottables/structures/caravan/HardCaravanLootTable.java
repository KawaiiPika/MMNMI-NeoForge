package xyz.pixelatedw.mineminenomi.datagen.loottables.structures.caravan;

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

public class HardCaravanLootTable {
   private static final LootPool.Builder FOOD;
   private static final LootPool.Builder TREASURE;
   private static final LootPool.Builder FRUIT_BOX;
   private static final LootPool.Builder RANDOMIZED_FRUITS_CLUES;
   private static final LootPool.Builder RANDOMIZED_FRUITS_BOOK;

   public static Pair<String, LootTable.Builder>[] getTables() {
      LootTable.Builder caravan = LootTable.m_79147_().m_79161_(FOOD).m_79161_(TREASURE).m_79161_(FRUIT_BOX).m_79161_(RANDOMIZED_FRUITS_CLUES).m_79161_(RANDOMIZED_FRUITS_BOOK);
      return new Pair[]{Pair.of("hard", caravan)};
   }

   static {
      FOOD = LootPool.m_79043_().name("mineminenomi:food").m_165133_(UniformGenerator.m_165780_(3.0F, 5.0F)).m_79076_(LootItem.m_79579_(Items.f_42410_).m_79707_(100).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(3.0F, 7.0F)))).m_79076_(LootItem.m_79579_(Items.f_42659_).m_79707_(100).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(2.0F, 5.0F)))).m_79076_(LootItem.m_79579_(Items.f_42582_).m_79707_(100).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(2.0F, 5.0F)))).m_79076_(LootItem.m_79579_(Items.f_42572_).m_79707_(80).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(4.0F, 10.0F)))).m_79076_(LootItem.m_79579_(Items.f_42399_).m_79707_(80).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 3.0F)))).m_79076_(LootItem.m_79579_(Items.f_42687_).m_79707_(80).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 2.0F)))).m_79076_(LootItem.m_79579_(Items.f_42619_).m_79707_(50).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(2.0F, 5.0F)))).m_79076_(LootItem.m_79579_(Items.f_42732_).m_79707_(50).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(2.0F, 4.0F)))).m_79076_(LootItem.m_79579_(Items.f_42406_).m_79707_(40).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(2.0F, 5.0F)))).m_79076_(LootItem.m_79579_((ItemLike)ModItems.COLA.get()).m_79707_(20).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(2.0F, 4.0F)))).m_79076_(LootItem.m_79579_((ItemLike)ModItems.ULTRA_COLA.get()).m_79707_(10).m_79711_(5).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 2.0F)))).m_79076_(LootItem.m_79579_((ItemLike)ModItems.BOTTLE_OF_RUM.get()).m_79707_(20).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 2.0F))));
      TREASURE = LootPool.m_79043_().name("mineminenomi:treasure").m_165133_(UniformGenerator.m_165780_(1.0F, 3.0F)).m_79076_(LootItem.m_79579_((ItemLike)ModItems.BELLY_POUCH.get()).m_79707_(100).m_79078_(SetBellyInPouchFunction.builder(UniformGenerator.m_165780_(100.0F, 300.0F))).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 4.0F)))).m_79076_(LootItem.m_79579_((ItemLike)ModItems.BELLY_POUCH.get()).m_79707_(20).m_79078_(SetBellyInPouchFunction.builder(UniformGenerator.m_165780_(100.0F, 500.0F))).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 3.0F)))).m_79076_(LootItem.m_79579_(Items.f_42415_).m_79707_(50).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(3.0F, 6.0F)))).m_79076_(LootItem.m_79579_(Items.f_42417_).m_79707_(60).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(3.0F, 6.0F)))).m_79076_(LootItem.m_79579_((ItemLike)ModWeapons.WALKER.get()).m_79707_(20)).m_79076_(LootItem.m_79579_((ItemLike)ModItems.KAIROSEKI_BULLET.get()).m_79707_(70).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(5.0F, 10.0F)))).m_79076_(LootItem.m_79579_((ItemLike)ModItems.WANTED_POSTER_PACKAGE.get()).m_79707_(40)).m_79076_(LootItem.m_79579_((ItemLike)ModBlocks.DEN_DEN_MUSHI.get()).m_79707_(40)).m_79076_(LootItem.m_79579_(Items.f_42522_).m_79707_(40)).m_79076_(LootItem.m_79579_(Items.f_42450_).m_79707_(40));
      FRUIT_BOX = LootPool.m_79043_().name("mineminenomi:fruit_box").m_165133_(ConstantValue.m_165692_(1.0F)).m_79076_(LootItem.m_79579_((ItemLike)ModItems.TIER_1_BOX.get()).m_79707_(50)).m_79076_(LootItem.m_79579_((ItemLike)ModItems.TIER_2_BOX.get()).m_79707_(35).m_79711_(2)).m_79076_(LootItem.m_79579_((ItemLike)ModItems.TIER_3_BOX.get()).m_79707_(15).m_79711_(5));
      RANDOMIZED_FRUITS_CLUES = LootPool.m_79043_().name("mineminenomi:randomized_fruits").m_165133_(UniformGenerator.m_165780_(1.0F, 4.0F)).m_79076_(LootItem.m_79579_(ItemStack.f_41583_.m_41720_()).m_79707_(16)).m_79076_(LootItem.m_79579_(Items.f_42516_).m_79707_(4).m_79078_(SetFruitClueFunction.builder())).m_79080_(RandomizedFruitsCondition.builder());
      RANDOMIZED_FRUITS_BOOK = LootPool.m_79043_().name("mineminenomi:randomized_fruits_book").m_165133_(ConstantValue.m_165692_(1.0F)).m_79076_(LootItem.m_79579_(ItemStack.f_41583_.m_41720_()).m_79707_(17)).m_79076_(LootItem.m_79579_((ItemLike)ModItems.DEVIL_FRUIT_ENCYCLOPEDIA.get()).m_79707_(3).m_79711_(5).m_79078_(EncyclopediaCompletionFunction.builder())).m_79080_(RandomizedFruitsCondition.builder());
   }
}
