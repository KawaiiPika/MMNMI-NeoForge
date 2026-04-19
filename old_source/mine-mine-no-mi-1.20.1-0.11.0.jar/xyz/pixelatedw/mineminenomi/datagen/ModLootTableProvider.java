package xyz.pixelatedw.mineminenomi.datagen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import xyz.pixelatedw.mineminenomi.datagen.loottables.BlocksLootTablesDataGen;
import xyz.pixelatedw.mineminenomi.datagen.loottables.DFBoxesLootTablesDataGen;
import xyz.pixelatedw.mineminenomi.datagen.loottables.EntitiesLootTablesDataGen;
import xyz.pixelatedw.mineminenomi.datagen.loottables.RewardsLootTablesDataGen;
import xyz.pixelatedw.mineminenomi.datagen.loottables.StructuresLootTablesDataGen;
import xyz.pixelatedw.mineminenomi.datagen.loottables.TraderLootTablesDataGen;

public class ModLootTableProvider extends LootTableProvider {
   public ModLootTableProvider(PackOutput output) {
      super(output, Collections.emptySet(), getLootTables());
   }

   public static List<LootTableProvider.SubProviderEntry> getLootTables() {
      List<LootTableProvider.SubProviderEntry> list = new ArrayList();
      list.add(DFBoxesLootTablesDataGen.create());
      list.add(TraderLootTablesDataGen.create());
      list.add(BlocksLootTablesDataGen.create());
      list.add(EntitiesLootTablesDataGen.create());
      list.add(StructuresLootTablesDataGen.create());
      list.add(RewardsLootTablesDataGen.create());
      return list;
   }
}
