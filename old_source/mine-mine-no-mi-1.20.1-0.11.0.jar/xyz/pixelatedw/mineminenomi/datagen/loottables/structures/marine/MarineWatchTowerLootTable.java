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
import xyz.pixelatedw.mineminenomi.data.functions.SetBellyInPouchFunction;
import xyz.pixelatedw.mineminenomi.init.ModArmors;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;

public class MarineWatchTowerLootTable {
   private static final LootPool.Builder SUPPLIES;
   private static final LootPool.Builder BAGS;

   public static Pair<String, LootTable.Builder>[] getTables() {
      LootTable.Builder tower = LootTable.m_79147_().m_79161_(SUPPLIES).m_79161_(BAGS);
      return new Pair[]{Pair.of("tower", tower)};
   }

   static {
      SUPPLIES = LootPool.m_79043_().name("mineminenomi:tower_supplies").m_165133_(UniformGenerator.m_165780_(2.0F, 4.0F)).m_79076_(LootItem.m_79579_(Items.f_42412_).m_79707_(100).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(5.0F, 10.0F)))).m_79076_(LootItem.m_79579_((ItemLike)ModItems.BULLET.get()).m_79707_(100).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(5.0F, 10.0F)))).m_79076_(LootItem.m_79579_((ItemLike)ModItems.KAIROSEKI_BULLET.get()).m_79707_(100).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(2.0F, 5.0F)))).m_79076_(LootItem.m_79579_(Items.f_42403_).m_79707_(80).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(5.0F, 20.0F)))).m_79076_(LootItem.m_79579_(Items.f_42411_).m_79707_(80)).m_79076_(LootItem.m_79579_((ItemLike)ModWeapons.FLINTLOCK.get()).m_79707_(80)).m_79076_(LootItem.m_79579_(Items.f_151059_).m_79707_(80)).m_79076_(LootItem.m_79579_(Items.f_42676_).m_79707_(80)).m_79076_(LootItem.m_79579_((ItemLike)ModWeapons.SENRIKU.get()).m_79707_(40)).m_79076_(LootItem.m_79579_((ItemLike)ModItems.ROPE_NET.get()).m_79707_(10)).m_79076_(LootItem.m_79579_(Items.f_42522_).m_79707_(80)).m_79076_(LootItem.m_79579_(Items.f_42410_).m_79707_(70).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(3.0F, 10.0F)))).m_79076_(LootItem.m_79579_((ItemLike)ModItems.TANGERINE.get()).m_79707_(70).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(2.0F, 5.0F)))).m_79076_(LootItem.m_79579_(Items.f_42572_).m_79707_(70).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(3.0F, 15.0F)))).m_79076_(LootItem.m_79579_((ItemLike)ModArmors.SNIPER_GOGGLES.get()).m_79707_(20).m_79711_(2));
      BAGS = LootPool.m_79043_().name("mineminenomi:bags").m_165133_(UniformGenerator.m_165780_(1.0F, 2.0F)).m_79076_(LootItem.m_79579_(ItemStack.f_41583_.m_41720_()).m_79707_(100)).m_79076_(LootItem.m_79579_((ItemLike)ModItems.BELLY_POUCH.get()).m_79707_(100).m_79078_(SetBellyInPouchFunction.builder(UniformGenerator.m_165780_(200.0F, 500.0F))).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 3.0F)))).m_79076_(LootItem.m_79579_((ItemLike)ModItems.BELLY_POUCH.get()).m_79707_(50).m_79078_(SetBellyInPouchFunction.builder(UniformGenerator.m_165780_(500.0F, 700.0F))));
   }
}
