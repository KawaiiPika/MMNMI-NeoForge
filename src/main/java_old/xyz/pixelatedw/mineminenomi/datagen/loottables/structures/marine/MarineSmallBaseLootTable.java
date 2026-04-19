package xyz.pixelatedw.mineminenomi.datagen.loottables.structures.marine;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.data.conditions.RandomizedFruitsCondition;
import xyz.pixelatedw.mineminenomi.data.functions.EncyclopediaCompletionFunction;
import xyz.pixelatedw.mineminenomi.data.functions.SetBellyInPouchFunction;
import xyz.pixelatedw.mineminenomi.data.functions.SetFruitClueFunction;
import xyz.pixelatedw.mineminenomi.data.functions.SetItemColorFunction;
import xyz.pixelatedw.mineminenomi.init.ModArmors;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;

public class MarineSmallBaseLootTable {
   private static final LootPool.Builder SUPPLIES_FOOD;
   private static final LootPool.Builder DORM_CHEST;
   private static final LootPool.Builder CAPTAIN_CHEST;
   private static final LootPool.Builder CAPTAIN_RANDOMIZED_FRUITS;

   public static Pair<String, LootTable.Builder>[] getTables() {
      LootTable.Builder supplies = LootTable.m_79147_().m_79161_(SUPPLIES_FOOD);
      LootTable.Builder dorm = LootTable.m_79147_().m_79161_(DORM_CHEST);
      LootTable.Builder captain = LootTable.m_79147_().m_79161_(CAPTAIN_CHEST).m_79161_(CAPTAIN_RANDOMIZED_FRUITS);
      return new Pair[]{Pair.of("supplies", supplies), Pair.of("dorm", dorm), Pair.of("captain", captain)};
   }

   static {
      SUPPLIES_FOOD = LootPool.m_79043_().name("mineminenomi:food").m_165133_(UniformGenerator.m_165780_(3.0F, 6.0F)).m_79076_(LootItem.m_79579_((ItemLike)ModItems.COLA.get()).m_79707_(50).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 3.0F)))).m_79076_(LootItem.m_79579_(Items.f_42572_).m_79707_(80).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(5.0F, 10.0F)))).m_79076_(LootItem.m_79579_(Items.f_42410_).m_79707_(80).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(2.0F, 5.0F)))).m_79076_(LootItem.m_79579_((ItemLike)ModItems.TANGERINE.get()).m_79707_(70).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 2.0F)))).m_79076_(LootItem.m_79579_(Items.f_42455_).m_79707_(80)).m_79076_(LootItem.m_79579_(Items.f_42406_).m_79707_(60).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 3.0F)))).m_79076_(LootItem.m_79579_(Items.f_42787_).m_79707_(50).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 3.0F)))).m_79076_(LootItem.m_79579_(Items.f_42486_).m_79707_(30).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 5.0F)))).m_79076_(LootItem.m_79579_(Items.f_42527_).m_79707_(20).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 5.0F)))).m_79076_(LootItem.m_79579_(Items.f_42526_).m_79707_(50).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 5.0F)))).m_79076_(LootItem.m_79579_(Items.f_42699_).m_79707_(30).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 3.0F)))).m_79076_(LootItem.m_79579_(Items.f_42502_).m_79707_(30)).m_79076_(LootItem.m_79579_((ItemLike)ModItems.BOTTLE_OF_RUM.get()).m_79707_(30).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 3.0F)))).m_79076_(LootItem.m_79579_((ItemLike)ModItems.SAKE_BOTTLE.get()).m_79707_(20).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 3.0F))));
      DORM_CHEST = LootPool.m_79043_().name("mineminenomi:dorm").m_165133_(UniformGenerator.m_165780_(1.0F, 3.0F)).m_79076_(LootItem.m_79579_((ItemLike)ModItems.BULLET.get()).m_79707_(100).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(5.0F, 15.0F)))).m_79076_(LootItem.m_79579_((ItemLike)ModItems.BELLY_POUCH.get()).m_79707_(50).m_79078_(SetBellyInPouchFunction.builder(UniformGenerator.m_165780_(50.0F, 150.0F))).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 3.0F)))).m_79076_(LootItem.m_79579_((ItemLike)ModWeapons.BROADSWORD.get()).m_79707_(90).m_79078_(SetItemColorFunction.builder(ItemsHelper.MARINE_HANDLE_COLOR.getRGB()))).m_79076_(LootItem.m_79579_((ItemLike)ModWeapons.KATANA.get()).m_79707_(90).m_79078_(SetItemColorFunction.builder(ItemsHelper.MARINE_HANDLE_COLOR.getRGB()))).m_79076_(LootItem.m_79579_((ItemLike)ModWeapons.SPEAR.get()).m_79707_(90).m_79078_(SetItemColorFunction.builder(ItemsHelper.MARINE_HANDLE_COLOR.getRGB()))).m_79076_(LootItem.m_79579_((ItemLike)ModWeapons.CLEAVER.get()).m_79707_(90).m_79078_(SetItemColorFunction.builder(ItemsHelper.MARINE_HANDLE_COLOR.getRGB()))).m_79076_(LootItem.m_79579_((ItemLike)ModWeapons.FLINTLOCK.get()).m_79707_(90)).m_79076_(LootItem.m_79579_(Items.f_42403_).m_79707_(60).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(10.0F, 15.0F)))).m_79076_(LootItem.m_79579_(Items.f_42383_).m_79707_(70)).m_79076_(LootItem.m_79579_(Items.f_42740_).m_79707_(70)).m_79076_(LootItem.m_79579_((ItemLike)ModItems.NORMAL_HANDCUFFS.get()).m_79707_(40).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 2.0F)))).m_79076_(LootItem.m_79579_((ItemLike)ModArmors.MEDIC_BAG.get()).m_79707_(20)).m_79076_(LootItem.m_79579_(Items.f_42572_).m_79707_(80)).m_79076_(LootItem.m_79579_(Items.f_42502_).m_79707_(30)).m_79076_(LootItem.m_79579_(Items.f_42450_).m_79707_(60).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 2.0F))));
      CAPTAIN_CHEST = LootPool.m_79043_().name("mineminenomi:captain").m_165133_(UniformGenerator.m_165780_(2.0F, 4.0F)).m_79076_(LootItem.m_79579_((ItemLike)ModItems.BELLY_POUCH.get()).m_79707_(100).m_79078_(SetBellyInPouchFunction.builder(UniformGenerator.m_165780_(20.0F, 200.0F))).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 3.0F)))).m_79076_(LootItem.m_79579_((ItemLike)ModBlocks.DEN_DEN_MUSHI.get()).m_79707_(30)).m_79076_(LootItem.m_79579_((ItemLike)ModItems.NORMAL_HANDCUFFS.get()).m_79707_(40).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 2.0F)))).m_79076_(LootItem.m_79579_((ItemLike)ModItems.WANTED_POSTER_PACKAGE.get()).m_79707_(55)).m_79076_(LootItem.m_79579_((ItemLike)ModWeapons.FLINTLOCK.get()).m_79707_(90)).m_79076_(LootItem.m_79579_((ItemLike)ModItems.CIGAR.get()).m_79707_(50).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(2.0F, 4.0F)))).m_79076_(LootItem.m_79579_((ItemLike)ModItems.SMOKING_PIPE.get()).m_79707_(40).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 2.0F)))).m_79076_(LootItem.m_79579_((ItemLike)ModItems.KAIROSEKI_BULLET.get()).m_79707_(60).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(3.0F, 10.0F)))).m_79076_(LootItem.m_79579_((ItemLike)ModItems.SAKE_BOTTLE.get()).m_79707_(30)).m_79076_(LootItem.m_79579_((ItemLike)ModItems.BOTTLE_OF_RUM.get()).m_79707_(30)).m_79076_(LootItem.m_79579_((ItemLike)ModItems.TIER_1_BOX.get()).m_79707_(50).m_79711_(2)).m_79076_(LootItem.m_79579_((ItemLike)ModItems.TIER_2_BOX.get()).m_79707_(25).m_79711_(5));
      CAPTAIN_RANDOMIZED_FRUITS = LootPool.m_79043_().name("mineminenomi:randomized_fruits").m_165133_(UniformGenerator.m_165780_(1.0F, 2.0F)).m_79076_(LootItem.m_79579_(ItemStack.f_41583_.m_41720_()).m_79707_(21)).m_79076_(LootItem.m_79579_(Items.f_42516_).m_79707_(6).m_79711_(5).m_79078_(SetFruitClueFunction.builder())).m_79076_(LootItem.m_79579_((ItemLike)ModItems.DEVIL_FRUIT_ENCYCLOPEDIA.get()).m_79707_(5).m_79711_(2).m_79078_(EncyclopediaCompletionFunction.builder())).m_79080_(RandomizedFruitsCondition.builder());
   }
}
