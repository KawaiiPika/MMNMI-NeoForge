package xyz.pixelatedw.mineminenomi.datagen.loottables;

import java.util.function.BiConsumer;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.datagen.loottables.structures.CampLootTable;
import xyz.pixelatedw.mineminenomi.datagen.loottables.structures.GhostShipLootTable;
import xyz.pixelatedw.mineminenomi.datagen.loottables.structures.SkyIslandLootTable;
import xyz.pixelatedw.mineminenomi.datagen.loottables.structures.TavernLootTable;
import xyz.pixelatedw.mineminenomi.datagen.loottables.structures.bandit.BanditArenaLootTable;
import xyz.pixelatedw.mineminenomi.datagen.loottables.structures.bandit.BanditCaveLootTable;
import xyz.pixelatedw.mineminenomi.datagen.loottables.structures.bandit.BanditFortLootTable;
import xyz.pixelatedw.mineminenomi.datagen.loottables.structures.caravan.EasyCaravanLootTable;
import xyz.pixelatedw.mineminenomi.datagen.loottables.structures.caravan.HardCaravanLootTable;
import xyz.pixelatedw.mineminenomi.datagen.loottables.structures.marine.MarineBattleshipLootTable;
import xyz.pixelatedw.mineminenomi.datagen.loottables.structures.marine.MarineLargeBaseLootTable;
import xyz.pixelatedw.mineminenomi.datagen.loottables.structures.marine.MarineSmallBaseLootTable;
import xyz.pixelatedw.mineminenomi.datagen.loottables.structures.marine.MarineWatchTowerLootTable;
import xyz.pixelatedw.mineminenomi.datagen.loottables.structures.pirate.PirateLargeShipLootTable;
import xyz.pixelatedw.mineminenomi.datagen.loottables.structures.pirate.PirateMediumShipLootTable;
import xyz.pixelatedw.mineminenomi.datagen.loottables.structures.pirate.PirateSmallShipLootTable;
import xyz.pixelatedw.mineminenomi.datagen.loottables.structures.vanilla.AbandonedMineshaftLootTable;
import xyz.pixelatedw.mineminenomi.datagen.loottables.structures.vanilla.BuriedTreasureLootTable;
import xyz.pixelatedw.mineminenomi.datagen.loottables.structures.vanilla.DesertPyramidLootTable;
import xyz.pixelatedw.mineminenomi.datagen.loottables.structures.vanilla.JungleTempleLootTable;
import xyz.pixelatedw.mineminenomi.datagen.loottables.structures.vanilla.PillagerOutpostLootTable;
import xyz.pixelatedw.mineminenomi.datagen.loottables.structures.vanilla.ShipwreckLootTable;
import xyz.pixelatedw.mineminenomi.datagen.loottables.structures.vanilla.SpawnBonusChestLootTable;
import xyz.pixelatedw.mineminenomi.datagen.loottables.structures.vanilla.StrongholdLootTable;
import xyz.pixelatedw.mineminenomi.datagen.loottables.structures.vanilla.UnderwaterRuinLootTable;
import xyz.pixelatedw.mineminenomi.datagen.loottables.structures.vanilla.WoodlandMansionLootTable;
import xyz.pixelatedw.mineminenomi.datagen.loottables.structures.vanilla.village.VillageArmorerLootTable;
import xyz.pixelatedw.mineminenomi.datagen.loottables.structures.vanilla.village.VillageButcherLootTable;
import xyz.pixelatedw.mineminenomi.datagen.loottables.structures.vanilla.village.VillageFisherLootTable;
import xyz.pixelatedw.mineminenomi.datagen.loottables.structures.vanilla.village.VillageFletcherLootTable;
import xyz.pixelatedw.mineminenomi.datagen.loottables.structures.vanilla.village.VillageShepherdLootTable;
import xyz.pixelatedw.mineminenomi.datagen.loottables.structures.vanilla.village.VillageTanneryLootTable;
import xyz.pixelatedw.mineminenomi.datagen.loottables.structures.vanilla.village.VillageTempleLootTable;
import xyz.pixelatedw.mineminenomi.datagen.loottables.structures.vanilla.village.VillageWeaponsmithLootTable;

public class StructuresLootTablesDataGen implements LootTableSubProvider {
   public void m_245126_(BiConsumer<ResourceLocation, LootTable.Builder> writer) {
      this.addLootTable(writer, "pirate/small_ship", PirateSmallShipLootTable.getTables());
      this.addLootTable(writer, "pirate/medium_ship", PirateMediumShipLootTable.getTables());
      this.addLootTable(writer, "pirate/large_ship", PirateLargeShipLootTable.getTables());
      this.addLootTable(writer, "marine/battleship", MarineBattleshipLootTable.getTables());
      this.addLootTable(writer, "ghost_ship", GhostShipLootTable.getTables());
      this.addLootTable(writer, "marine/small_base", MarineSmallBaseLootTable.getTables());
      this.addLootTable(writer, "marine/large_base", MarineLargeBaseLootTable.getTables());
      this.addLootTable(writer, "marine/watch_tower", MarineWatchTowerLootTable.getTables());
      this.addLootTable(writer, "marine/camp", CampLootTable.getTables());
      this.addLootTable(writer, "bandit/cave", BanditCaveLootTable.getTables());
      this.addLootTable(writer, "bandit/arena", BanditArenaLootTable.getTables());
      this.addLootTable(writer, "bandit/fort", BanditFortLootTable.getTables());
      this.addLootTable(writer, "bandit/camp", CampLootTable.getTables());
      this.addLootTable(writer, "caravan", EasyCaravanLootTable.getTables());
      this.addLootTable(writer, "caravan", HardCaravanLootTable.getTables());
      this.addLootTable(writer, "tavern", TavernLootTable.getTables());
      this.addLootTable(writer, "sky_island", SkyIslandLootTable.getTables());
      this.addVanillaLootTable(writer, BuiltInLootTables.f_78691_, UnderwaterRuinLootTable.getPool());
      this.addVanillaLootTable(writer, BuiltInLootTables.f_78690_, UnderwaterRuinLootTable.getPool());
      this.addVanillaLootTable(writer, BuiltInLootTables.f_78695_, ShipwreckLootTable.getTreasurePool());
      this.addVanillaLootTable(writer, BuiltInLootTables.f_78694_, ShipwreckLootTable.getSupplyPool());
      this.addVanillaLootTable(writer, BuiltInLootTables.f_78759_, AbandonedMineshaftLootTable.getPool());
      this.addVanillaLootTable(writer, BuiltInLootTables.f_78692_, BuriedTreasureLootTable.getPool());
      this.addVanillaLootTable(writer, BuiltInLootTables.f_78764_, DesertPyramidLootTable.getPool());
      this.addVanillaLootTable(writer, BuiltInLootTables.f_78686_, JungleTempleLootTable.getPool());
      this.addVanillaLootTable(writer, BuiltInLootTables.f_78696_, PillagerOutpostLootTable.getPool());
      this.addVanillaLootTable(writer, BuiltInLootTables.f_78689_, WoodlandMansionLootTable.getPool());
      this.addVanillaLootTable(writer, BuiltInLootTables.f_78740_, SpawnBonusChestLootTable.getPool());
      this.addVanillaLootTable(writer, BuiltInLootTables.f_78763_, StrongholdLootTable.getPool());
      this.addVanillaLootTable(writer, BuiltInLootTables.f_78745_, VillageArmorerLootTable.getPool());
      this.addVanillaLootTable(writer, BuiltInLootTables.f_78749_, VillageButcherLootTable.getPool());
      this.addVanillaLootTable(writer, BuiltInLootTables.f_78751_, VillageFisherLootTable.getPool());
      this.addVanillaLootTable(writer, BuiltInLootTables.f_78750_, VillageFletcherLootTable.getPool());
      this.addVanillaLootTable(writer, BuiltInLootTables.f_78752_, VillageTanneryLootTable.getPool());
      this.addVanillaLootTable(writer, BuiltInLootTables.f_78753_, VillageTempleLootTable.getPool());
      this.addVanillaLootTable(writer, BuiltInLootTables.f_78743_, VillageWeaponsmithLootTable.getPool());
      this.addVanillaLootTable(writer, BuiltInLootTables.f_78748_, VillageShepherdLootTable.getPool());
   }

   protected void addLootTable(BiConsumer<ResourceLocation, LootTable.Builder> writer, String structureId, Pair<String, LootTable.Builder>[] pairs) {
      for(Pair<String, LootTable.Builder> pair : pairs) {
         ResourceLocation key2 = ResourceLocation.fromNamespaceAndPath("mineminenomi", "chests/" + structureId + "/" + (String)pair.getLeft());
         writer.accept(key2, (LootTable.Builder)pair.getRight());
      }

   }

   protected void addVanillaLootTable(BiConsumer<ResourceLocation, LootTable.Builder> writer, ResourceLocation key, LootTable.Builder builder) {
      ResourceLocation key2 = ResourceLocation.fromNamespaceAndPath("mineminenomi", "vanilla/" + key.m_135815_());
      writer.accept(key2, builder);
   }

   public static LootTableProvider.SubProviderEntry create() {
      return new LootTableProvider.SubProviderEntry(StructuresLootTablesDataGen::new, LootContextParamSets.f_81411_);
   }
}
