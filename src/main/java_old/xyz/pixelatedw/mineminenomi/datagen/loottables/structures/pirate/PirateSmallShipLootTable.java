package xyz.pixelatedw.mineminenomi.datagen.loottables.structures.pirate;

import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.data.functions.SetBellyInPouchFunction;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;

public class PirateSmallShipLootTable {
   private static final LootPool.Builder SUPPLIES_WEAPONS;
   private static final LootPool.Builder SUPPLIES_FOOD;
   private static final LootPool.Builder SUPPLIES_TREASURE;

   public static Pair<String, LootTable.Builder>[] getTables() {
      LootTable.Builder supplies = LootTable.m_79147_().m_79161_(SUPPLIES_WEAPONS).m_79161_(SUPPLIES_FOOD).m_79161_(SUPPLIES_TREASURE);
      return new Pair[]{Pair.of("supplies", supplies)};
   }

   static {
      SUPPLIES_WEAPONS = LootPool.m_79043_().name("mineminenomi:weapons").m_165133_(UniformGenerator.m_165780_(1.0F, 2.0F)).m_79076_(LootItem.m_79579_((ItemLike)ModWeapons.CUTLASS.get()).m_79707_(90)).m_79076_(LootItem.m_79579_((ItemLike)ModWeapons.KATANA.get()).m_79707_(90)).m_79076_(LootItem.m_79579_((ItemLike)ModWeapons.AXE.get()).m_79707_(90)).m_79076_(LootItem.m_79579_((ItemLike)ModWeapons.PIPE.get()).m_79707_(70)).m_79076_(LootItem.m_79579_((ItemLike)ModWeapons.MACE.get()).m_79707_(30)).m_79076_(LootItem.m_79579_((ItemLike)ModItems.BULLET.get()).m_79707_(100).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(5.0F, 10.0F)))).m_79076_(LootItem.m_79579_(Items.f_42403_).m_79707_(70).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(3.0F, 5.0F)))).m_79076_(LootItem.m_79579_((ItemLike)ModWeapons.FLINTLOCK.get()).m_79707_(70));
      SUPPLIES_FOOD = LootPool.m_79043_().name("mineminenomi:food").m_165133_(UniformGenerator.m_165780_(2.0F, 5.0F)).m_79076_(LootItem.m_79579_(Items.f_42406_).m_79707_(100).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(2.0F, 4.0F)))).m_79076_(LootItem.m_79579_(Items.f_42527_).m_79707_(80).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(2.0F, 6.0F)))).m_79076_(LootItem.m_79579_(Items.f_42526_).m_79707_(90).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(2.0F, 6.0F)))).m_79076_(LootItem.m_79579_(Items.f_42576_).m_79707_(50).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(10.0F, 20.0F)))).m_79076_(LootItem.m_79579_(Items.f_42400_).m_79707_(50).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 2.0F)))).m_79076_(LootItem.m_79579_((ItemLike)ModItems.COLA.get()).m_79707_(20).m_79711_(2).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 3.0F))));
      SUPPLIES_TREASURE = LootPool.m_79043_().name("mineminenomi:treasure").m_165133_(UniformGenerator.m_165780_(1.0F, 3.0F)).m_79076_(LootItem.m_79579_((ItemLike)ModItems.BELLY_POUCH.get()).m_79707_(100).m_79078_(SetBellyInPouchFunction.builder(UniformGenerator.m_165780_(10.0F, 50.0F)))).m_79076_(LootItem.m_79579_((ItemLike)ModItems.BELLY_POUCH.get()).m_79707_(30).m_79711_(5).m_79078_(SetBellyInPouchFunction.builder(UniformGenerator.m_165780_(50.0F, 100.0F)))).m_79076_(LootItem.m_79579_((ItemLike)ModItems.WANTED_POSTER_PACKAGE.get()).m_79707_(10));
   }
}
