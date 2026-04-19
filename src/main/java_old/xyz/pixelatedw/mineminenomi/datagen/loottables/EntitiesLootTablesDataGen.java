package xyz.pixelatedw.mineminenomi.datagen.loottables;

import java.util.function.BiConsumer;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.EntityPredicate.Builder;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import xyz.pixelatedw.mineminenomi.datagen.loottables.entities.BigDuckDrops;
import xyz.pixelatedw.mineminenomi.datagen.loottables.entities.BruteDrops;
import xyz.pixelatedw.mineminenomi.datagen.loottables.entities.CaptainDrops;
import xyz.pixelatedw.mineminenomi.datagen.loottables.entities.DenDenMushiDrops;
import xyz.pixelatedw.mineminenomi.datagen.loottables.entities.GorillaDrops;
import xyz.pixelatedw.mineminenomi.datagen.loottables.entities.GruntDrops;
import xyz.pixelatedw.mineminenomi.datagen.loottables.entities.LapahnDrops;
import xyz.pixelatedw.mineminenomi.datagen.loottables.entities.NormalDugongDrops;
import xyz.pixelatedw.mineminenomi.datagen.loottables.entities.RareDugongDrops;
import xyz.pixelatedw.mineminenomi.datagen.loottables.entities.SeaCowDrops;
import xyz.pixelatedw.mineminenomi.datagen.loottables.entities.SkypieanDrops;
import xyz.pixelatedw.mineminenomi.datagen.loottables.entities.SkypieanTraderDrops;
import xyz.pixelatedw.mineminenomi.datagen.loottables.entities.SniperDrops;
import xyz.pixelatedw.mineminenomi.datagen.loottables.entities.TraderDrops;
import xyz.pixelatedw.mineminenomi.datagen.loottables.entities.WhiteWalkieDrops;
import xyz.pixelatedw.mineminenomi.datagen.loottables.entities.YagaraBullDrops;
import xyz.pixelatedw.mineminenomi.init.ModMobs;

public class EntitiesLootTablesDataGen implements LootTableSubProvider {
   public static final EntityPredicate ON_FIRE_CHECK = Builder.m_36633_().m_36642_(net.minecraft.advancements.critereon.EntityFlagsPredicate.Builder.m_33713_().m_33714_(true).m_33716_()).m_36662_();

   public void m_245126_(BiConsumer<ResourceLocation, LootTable.Builder> writer) {
      this.addLootTable(writer, (EntityType)ModMobs.KUNG_FU_DUGONG.get(), NormalDugongDrops.getTable());
      this.addLootTable(writer, (EntityType)ModMobs.BOXING_DUGONG.get(), NormalDugongDrops.getTable());
      this.addLootTable(writer, (EntityType)ModMobs.WRESTLING_DUGONG.get(), NormalDugongDrops.getTable());
      this.addLootTable(writer, (EntityType)ModMobs.LEGENDARY_MASTER_DUGONG.get(), RareDugongDrops.getTable());
      this.addLootTable(writer, (EntityType)ModMobs.WANDERING_DUGONG.get(), RareDugongDrops.getTable());
      this.addLootTable(writer, (EntityType)ModMobs.LAPAHN.get(), LapahnDrops.getTable());
      this.addLootTable(writer, (EntityType)ModMobs.WHITE_WALKIE.get(), WhiteWalkieDrops.getTable());
      this.addLootTable(writer, (EntityType)ModMobs.SEA_COW.get(), SeaCowDrops.getTable());
      this.addLootTable(writer, (EntityType)ModMobs.DEN_DEN_MUSHI.get(), DenDenMushiDrops.getTable());
      this.addLootTable(writer, (EntityType)ModMobs.YAGARA_BULL.get(), YagaraBullDrops.getTable());
      this.addLootTable(writer, (EntityType)ModMobs.BIG_DUCK.get(), BigDuckDrops.getTable());
      this.addLootTable(writer, (EntityType)ModMobs.BLUGORI.get(), GorillaDrops.getTable());
      this.addLootTable(writer, (EntityType)ModMobs.BLAGORI.get(), GorillaDrops.getTable());
      this.addLootTable(writer, (EntityType)ModMobs.MARINE_GRUNT.get(), GruntDrops.getTable());
      this.addLootTable(writer, (EntityType)ModMobs.PIRATE_GRUNT.get(), GruntDrops.getTable());
      this.addLootTable(writer, (EntityType)ModMobs.BANDIT_GRUNT.get(), GruntDrops.getTable());
      this.addLootTable(writer, (EntityType)ModMobs.MARINE_SNIPER.get(), SniperDrops.getTable());
      this.addLootTable(writer, (EntityType)ModMobs.BANDIT_SNIPER.get(), SniperDrops.getTable());
      this.addLootTable(writer, (EntityType)ModMobs.MARINE_BRUTE.get(), BruteDrops.getTable());
      this.addLootTable(writer, (EntityType)ModMobs.PIRATE_BRUTE.get(), BruteDrops.getTable());
      this.addLootTable(writer, (EntityType)ModMobs.BANDIT_BRUTE.get(), BruteDrops.getTable());
      this.addLootTable(writer, (EntityType)ModMobs.MARINE_CAPTAIN.get(), CaptainDrops.getTable());
      this.addLootTable(writer, (EntityType)ModMobs.PIRATE_CAPTAIN.get(), CaptainDrops.getTable());
      this.addLootTable(writer, (EntityType)ModMobs.BANDIT_CAPTAIN.get(), CaptainDrops.getTable());
      this.addLootTable(writer, (EntityType)ModMobs.MARINE_TRADER.get(), TraderDrops.getTable());
      this.addLootTable(writer, (EntityType)ModMobs.PIRATE_TRADER.get(), TraderDrops.getTable());
      this.addLootTable(writer, (EntityType)ModMobs.SKYPIEAN_TRADER.get(), SkypieanTraderDrops.getTable());
      this.addLootTable(writer, (EntityType)ModMobs.SKYPIEAN_CIVILIAN.get(), SkypieanDrops.getTable());
   }

   public void addLootTable(BiConsumer<ResourceLocation, LootTable.Builder> writer, EntityType<?> type, LootTable.Builder builder) {
      String key = EntityType.m_20613_(type).m_135815_();
      ResourceLocation res = ResourceLocation.fromNamespaceAndPath("mineminenomi", "entities/" + key);
      writer.accept(res, builder);
   }

   public static LootTableProvider.SubProviderEntry create() {
      return new LootTableProvider.SubProviderEntry(EntitiesLootTablesDataGen::new, LootContextParamSets.f_81415_);
   }
}
