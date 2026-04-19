package xyz.pixelatedw.mineminenomi.datagen.loottables.structures.marine;

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
import xyz.pixelatedw.mineminenomi.init.ModArmors;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.init.ModItems;

public class MarineBattleshipLootTable {
   private static final LootPool.Builder SUPPLIES_FOOD;
   private static final LootPool.Builder SUPPLIES_COMBAT;
   private static final LootPool.Builder TREASURE_CHEST;
   private static final LootPool.Builder FRUIT_BOX;
   private static final LootPool.Builder RANDOMIZED_FRUITS_CLUES;
   private static final LootPool.Builder RANDOMIZED_FRUITS_BOOK;
   private static final LootPool.Builder RUM_CHEST;

   public static Pair<String, LootTable.Builder>[] getTables() {
      LootTable.Builder supplies = LootTable.m_79147_().m_79161_(SUPPLIES_FOOD).m_79161_(SUPPLIES_COMBAT);
      LootTable.Builder treasure = LootTable.m_79147_().m_79161_(TREASURE_CHEST).m_79161_(FRUIT_BOX).m_79161_(RANDOMIZED_FRUITS_CLUES).m_79161_(RANDOMIZED_FRUITS_BOOK);
      LootTable.Builder rum = LootTable.m_79147_().m_79161_(RUM_CHEST);
      return new Pair[]{Pair.of("supplies", supplies), Pair.of("treasure", treasure), Pair.of("rum", rum)};
   }

   static {
      SUPPLIES_FOOD = LootPool.m_79043_().name("mineminenomi:food").m_165133_(UniformGenerator.m_165780_(3.0F, 6.0F)).m_79076_(LootItem.m_79579_(Items.f_42406_).m_79707_(100).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(5.0F, 10.0F)))).m_79076_(LootItem.m_79579_(Items.f_42734_).m_79707_(100).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 3.0F)))).m_79076_(LootItem.m_79579_((ItemLike)ModItems.TANGERINE.get()).m_79707_(70).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(3.0F, 5.0F)))).m_79076_(LootItem.m_79579_(Items.f_42400_).m_79707_(100).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 3.0F)))).m_79076_(LootItem.m_79579_((ItemLike)ModItems.BOTTLE_OF_RUM.get()).m_79707_(40).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 2.0F)))).m_79076_(LootItem.m_79579_((ItemLike)ModItems.SAKE_BOTTLE.get()).m_79707_(60).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 3.0F)))).m_79076_(LootItem.m_79579_((ItemLike)ModItems.COLA.get()).m_79707_(40).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 5.0F)))).m_79076_(LootItem.m_79579_(Items.f_42530_).m_79707_(60).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(2.0F, 5.0F)))).m_79076_(LootItem.m_79579_(Items.f_42531_).m_79707_(60).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(2.0F, 5.0F)))).m_79076_(LootItem.m_79579_((ItemLike)ModItems.CIGAR.get()).m_79707_(60).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 2.0F)))).m_79076_(LootItem.m_79579_((ItemLike)ModItems.SEA_KING_MEAT.get()).m_79707_(20).m_79711_(5).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 2.0F))));
      SUPPLIES_COMBAT = LootPool.m_79043_().name("mineminenomi:combat_supplies").m_165133_(UniformGenerator.m_165780_(2.0F, 6.0F)).m_79076_(LootItem.m_79579_((ItemLike)ModItems.BULLET.get()).m_79707_(100).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(10.0F, 15.0F)))).m_79076_(LootItem.m_79579_((ItemLike)ModItems.KAIROSEKI_BULLET.get()).m_79707_(20).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(5.0F, 15.0F)))).m_79076_(LootItem.m_79579_((ItemLike)ModItems.CANNON_BALL.get()).m_79707_(60).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(5.0F, 15.0F)))).m_79076_(LootItem.m_79579_(Items.f_42403_).m_79707_(60).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(10.0F, 15.0F)))).m_79076_(LootItem.m_79579_(Items.f_42749_).m_79707_(60).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(5.0F, 15.0F)))).m_79076_(LootItem.m_79579_((ItemLike)ModArmors.MEDIC_BAG.get()).m_79707_(50).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 3.0F)))).m_79076_(LootItem.m_79579_((ItemLike)ModArmors.SNIPER_GOGGLES.get()).m_79707_(10)).m_79076_(LootItem.m_79579_((ItemLike)ModItems.NORMAL_HANDCUFFS.get()).m_79707_(60).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 2.0F)))).m_79076_(LootItem.m_79579_((ItemLike)ModItems.KAIROSEKI_HANDCUFFS.get()).m_79707_(10)).m_79076_(LootItem.m_79579_((ItemLike)ModBlocks.AXE_DIAL.get()).m_79707_(30).m_79711_(2).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 3.0F)))).m_79076_(LootItem.m_79579_((ItemLike)ModBlocks.FLASH_DIAL.get()).m_79707_(30).m_79711_(2).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 3.0F)))).m_79076_(LootItem.m_79579_((ItemLike)ModBlocks.FLAME_DIAL.get()).m_79707_(30).m_79711_(2).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 3.0F))));
      TREASURE_CHEST = LootPool.m_79043_().name("mineminenomi:treasure").m_165133_(UniformGenerator.m_165780_(2.0F, 4.0F)).m_79076_(LootItem.m_79579_((ItemLike)ModItems.BELLY_POUCH.get()).m_79707_(100).m_79078_(SetBellyInPouchFunction.builder(UniformGenerator.m_165780_(100.0F, 150.0F))).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 3.0F)))).m_79076_(LootItem.m_79579_((ItemLike)ModItems.BELLY_POUCH.get()).m_79707_(50).m_79078_(SetBellyInPouchFunction.builder(UniformGenerator.m_165780_(250.0F, 1000.0F)))).m_79076_(LootItem.m_79579_((ItemLike)ModItems.BELLY_POUCH.get()).m_79707_(100).m_79078_(SetBellyInPouchFunction.builder(UniformGenerator.m_165780_(100.0F, 200.0F))).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 3.0F)))).m_79076_(LootItem.m_79579_(Items.f_42417_).m_79707_(60).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(5.0F, 10.0F)))).m_79076_(LootItem.m_79579_(Items.f_42616_).m_79707_(60).m_79711_(2).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(5.0F, 10.0F)))).m_79076_(LootItem.m_79579_(Items.f_42716_).m_79707_(40).m_79711_(2)).m_79076_(LootItem.m_79579_(Items.f_42695_).m_79707_(40).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(2.0F, 5.0F)))).m_79076_(LootItem.m_79579_(Items.f_151059_).m_79707_(50)).m_79076_(LootItem.m_79579_(Items.f_42522_).m_79707_(30)).m_79076_(LootItem.m_79579_(Items.f_42524_).m_79707_(30));
      FRUIT_BOX = LootPool.m_79043_().name("mineminenomi:fruit_box").m_165133_(ConstantValue.m_165692_(1.0F)).m_79076_(LootItem.m_79579_(ItemStack.f_41583_.m_41720_()).m_79707_(50)).m_79076_(LootItem.m_79579_((ItemLike)ModItems.TIER_1_BOX.get()).m_79707_(25)).m_79076_(LootItem.m_79579_((ItemLike)ModItems.TIER_2_BOX.get()).m_79707_(15).m_79711_(2)).m_79076_(LootItem.m_79579_((ItemLike)ModItems.TIER_3_BOX.get()).m_79707_(10).m_79711_(5));
      RANDOMIZED_FRUITS_CLUES = LootPool.m_79043_().name("mineminenomi:randomized_fruits").m_165133_(UniformGenerator.m_165780_(1.0F, 4.0F)).m_79076_(LootItem.m_79579_(ItemStack.f_41583_.m_41720_()).m_79707_(15)).m_79076_(LootItem.m_79579_(Items.f_42516_).m_79707_(5).m_79711_(5).m_79078_(SetFruitClueFunction.builder())).m_79080_(RandomizedFruitsCondition.builder());
      RANDOMIZED_FRUITS_BOOK = LootPool.m_79043_().name("mineminenomi:randomized_fruits_book").m_165133_(ConstantValue.m_165692_(1.0F)).m_79076_(LootItem.m_79579_(ItemStack.f_41583_.m_41720_()).m_79707_(16)).m_79076_(LootItem.m_79579_((ItemLike)ModItems.DEVIL_FRUIT_ENCYCLOPEDIA.get()).m_79707_(4).m_79711_(5).m_79078_(EncyclopediaCompletionFunction.builder())).m_79080_(RandomizedFruitsCondition.builder());
      RUM_CHEST = LootPool.m_79043_().name("mineminenomi:rum_chest").m_165133_(UniformGenerator.m_165780_(5.0F, 10.0F)).m_79076_(LootItem.m_79579_((ItemLike)ModItems.BOTTLE_OF_RUM.get()).m_79707_(100).m_79078_(SetItemCountFunction.m_165412_(ConstantValue.m_165692_(1.0F)))).m_79076_(LootItem.m_79579_((ItemLike)ModItems.ULTRA_COLA.get()).m_79707_(10).m_79711_(5).m_79078_(SetItemCountFunction.m_165412_(ConstantValue.m_165692_(1.0F))));
   }
}
