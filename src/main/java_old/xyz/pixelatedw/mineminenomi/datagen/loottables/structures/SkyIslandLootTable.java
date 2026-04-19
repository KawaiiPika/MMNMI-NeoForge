package xyz.pixelatedw.mineminenomi.datagen.loottables.structures;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SetPotionFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.init.ModItems;

public class SkyIslandLootTable {
   private static final LootPool.Builder HOUSE_FOOD;
   private static final LootPool.Builder HOUSE_DIALS;
   private static final LootPool.Builder HOUSE_TOOLS;

   public static Pair<String, LootTable.Builder>[] getTables() {
      LootTable.Builder house = LootTable.m_79147_().m_79161_(HOUSE_FOOD).m_79161_(HOUSE_DIALS).m_79161_(HOUSE_TOOLS);
      return new Pair[]{Pair.of("house", house)};
   }

   static {
      HOUSE_FOOD = LootPool.m_79043_().name("mineminenomi:food").m_165133_(UniformGenerator.m_165780_(1.0F, 5.0F)).m_79076_(LootItem.m_79579_(Items.f_42590_).m_79707_(100).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 3.0F))).m_79078_(SetPotionFunction.m_193075_(Potions.f_43599_))).m_79076_(LootItem.m_79579_((ItemLike)ModItems.TANGERINE.get()).m_79707_(80).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(3.0F, 5.0F)))).m_79076_(LootItem.m_79579_(Items.f_42400_).m_79707_(80).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 2.0F)))).m_79076_(LootItem.m_79579_(Items.f_42572_).m_79707_(80).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(4.0F, 8.0F)))).m_79076_(LootItem.m_79579_(Items.f_42406_).m_79707_(70).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(2.0F, 4.0F)))).m_79076_(LootItem.m_79579_(Items.f_42619_).m_79707_(70).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(4.0F, 6.0F)))).m_79076_(LootItem.m_79579_(Items.f_42677_).m_79707_(5).m_79711_(2).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 3.0F))));
      HOUSE_DIALS = LootPool.m_79043_().name("mineminenomi:dials").m_165133_(UniformGenerator.m_165780_(1.0F, 3.0F)).m_79076_(LootItem.m_79579_(ItemStack.f_41583_.m_41720_()).m_79707_(100).m_79711_(-2)).m_79076_(LootItem.m_79579_((ItemLike)ModBlocks.EISEN_DIAL.get()).m_79707_(10).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 3.0F)))).m_79076_(LootItem.m_79579_((ItemLike)ModBlocks.FLASH_DIAL.get()).m_79707_(10).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 3.0F)))).m_79076_(LootItem.m_79579_((ItemLike)ModBlocks.MILKY_DIAL.get()).m_79707_(10).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 3.0F)))).m_79076_(LootItem.m_79579_((ItemLike)ModBlocks.BREATH_DIAL.get()).m_79707_(10).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 3.0F)))).m_79076_(LootItem.m_79579_((ItemLike)ModBlocks.AXE_DIAL.get()).m_79707_(10).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 3.0F))));
      HOUSE_TOOLS = LootPool.m_79043_().name("mineminenomi:tools").m_165133_(UniformGenerator.m_165780_(1.0F, 2.0F)).m_79076_(LootItem.m_79579_(ItemStack.f_41583_.m_41720_()).m_79707_(50).m_79711_(-2)).m_79076_(LootItem.m_79579_(Items.f_42386_).m_79707_(100)).m_79076_(LootItem.m_79579_(Items.f_42384_).m_79707_(100)).m_79076_(LootItem.m_79579_(Items.f_42523_).m_79707_(80)).m_79076_(LootItem.m_79579_(Items.f_271356_).m_79707_(60)).m_79076_(LootItem.m_79579_(Items.f_42446_).m_79707_(60)).m_79076_(LootItem.m_79579_(Items.f_42401_).m_79707_(100).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(3.0F, 6.0F)))).m_79076_(LootItem.m_79579_(Items.f_42402_).m_79707_(100).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(3.0F, 6.0F))));
   }
}
