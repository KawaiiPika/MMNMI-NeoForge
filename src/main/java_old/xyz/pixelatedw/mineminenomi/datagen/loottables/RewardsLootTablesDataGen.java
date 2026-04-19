package xyz.pixelatedw.mineminenomi.datagen.loottables;

import java.util.function.BiConsumer;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore;
import xyz.pixelatedw.mineminenomi.datagen.loottables.rewards.AlvidaHardReward;
import xyz.pixelatedw.mineminenomi.datagen.loottables.rewards.AlvidaReward;
import xyz.pixelatedw.mineminenomi.datagen.loottables.rewards.ArlongHardReward;
import xyz.pixelatedw.mineminenomi.datagen.loottables.rewards.ArlongReward;
import xyz.pixelatedw.mineminenomi.datagen.loottables.rewards.BuggyHardReward;
import xyz.pixelatedw.mineminenomi.datagen.loottables.rewards.BuggyReward;
import xyz.pixelatedw.mineminenomi.datagen.loottables.rewards.CabajiHardReward;
import xyz.pixelatedw.mineminenomi.datagen.loottables.rewards.CabajiReward;
import xyz.pixelatedw.mineminenomi.datagen.loottables.rewards.ChewHardReward;
import xyz.pixelatedw.mineminenomi.datagen.loottables.rewards.ChewReward;
import xyz.pixelatedw.mineminenomi.datagen.loottables.rewards.DonKriegHardReward;
import xyz.pixelatedw.mineminenomi.datagen.loottables.rewards.DonKriegReward;
import xyz.pixelatedw.mineminenomi.datagen.loottables.rewards.GinHardReward;
import xyz.pixelatedw.mineminenomi.datagen.loottables.rewards.GinReward;
import xyz.pixelatedw.mineminenomi.datagen.loottables.rewards.HigumaHardReward;
import xyz.pixelatedw.mineminenomi.datagen.loottables.rewards.HigumaReward;
import xyz.pixelatedw.mineminenomi.datagen.loottables.rewards.JangoHardReward;
import xyz.pixelatedw.mineminenomi.datagen.loottables.rewards.JangoReward;
import xyz.pixelatedw.mineminenomi.datagen.loottables.rewards.KuroHardReward;
import xyz.pixelatedw.mineminenomi.datagen.loottables.rewards.KuroReward;
import xyz.pixelatedw.mineminenomi.datagen.loottables.rewards.KuroobiHardReward;
import xyz.pixelatedw.mineminenomi.datagen.loottables.rewards.KuroobiReward;
import xyz.pixelatedw.mineminenomi.datagen.loottables.rewards.MorganHardReward;
import xyz.pixelatedw.mineminenomi.datagen.loottables.rewards.MorganReward;
import xyz.pixelatedw.mineminenomi.datagen.loottables.rewards.Mr0HardReward;
import xyz.pixelatedw.mineminenomi.datagen.loottables.rewards.Mr0Reward;
import xyz.pixelatedw.mineminenomi.datagen.loottables.rewards.Mr1HardReward;
import xyz.pixelatedw.mineminenomi.datagen.loottables.rewards.Mr1Reward;
import xyz.pixelatedw.mineminenomi.datagen.loottables.rewards.Mr3HardReward;
import xyz.pixelatedw.mineminenomi.datagen.loottables.rewards.Mr3Reward;
import xyz.pixelatedw.mineminenomi.datagen.loottables.rewards.Mr5MissValentineHardReward;
import xyz.pixelatedw.mineminenomi.datagen.loottables.rewards.Mr5MissValentineReward;
import xyz.pixelatedw.mineminenomi.datagen.loottables.rewards.NyanbanBrothersHardReward;
import xyz.pixelatedw.mineminenomi.datagen.loottables.rewards.NyanbanBrothersReward;
import xyz.pixelatedw.mineminenomi.datagen.loottables.rewards.PearlHardReward;
import xyz.pixelatedw.mineminenomi.datagen.loottables.rewards.PearlReward;
import xyz.pixelatedw.mineminenomi.init.ModChallenges;
import xyz.pixelatedw.mineminenomi.init.ModLootTypes;

public class RewardsLootTablesDataGen implements LootTableSubProvider {
   public void m_245126_(BiConsumer<ResourceLocation, LootTable.Builder> writer) {
      this.addLootTable(writer, (ChallengeCore)ModChallenges.HIGUMA.get(), HigumaReward.getTables());
      this.addLootTable(writer, (ChallengeCore)ModChallenges.HIGUMA_HARD.get(), HigumaHardReward.getTables());
      this.addLootTable(writer, (ChallengeCore)ModChallenges.MORGAN.get(), MorganReward.getTables());
      this.addLootTable(writer, (ChallengeCore)ModChallenges.MORGAN_HARD.get(), MorganHardReward.getTables());
      this.addLootTable(writer, (ChallengeCore)ModChallenges.BUGGY.get(), BuggyReward.getTables());
      this.addLootTable(writer, (ChallengeCore)ModChallenges.BUGGY_HARD.get(), BuggyHardReward.getTables());
      this.addLootTable(writer, (ChallengeCore)ModChallenges.ALVIDA.get(), AlvidaReward.getTables());
      this.addLootTable(writer, (ChallengeCore)ModChallenges.ALVIDA_HARD.get(), AlvidaHardReward.getTables());
      this.addLootTable(writer, (ChallengeCore)ModChallenges.CABAJI.get(), CabajiReward.getTables());
      this.addLootTable(writer, (ChallengeCore)ModChallenges.CABAJI_HARD.get(), CabajiHardReward.getTables());
      this.addLootTable(writer, (ChallengeCore)ModChallenges.KURO.get(), KuroReward.getTables());
      this.addLootTable(writer, (ChallengeCore)ModChallenges.KURO_HARD.get(), KuroHardReward.getTables());
      this.addLootTable(writer, (ChallengeCore)ModChallenges.JANGO.get(), JangoReward.getTables());
      this.addLootTable(writer, (ChallengeCore)ModChallenges.JANGO_HARD.get(), JangoHardReward.getTables());
      this.addLootTable(writer, (ChallengeCore)ModChallenges.NYANBAN_BROTHERS.get(), NyanbanBrothersReward.getTables());
      this.addLootTable(writer, (ChallengeCore)ModChallenges.NYANBAN_BROTHERS_HARD.get(), NyanbanBrothersHardReward.getTables());
      this.addLootTable(writer, (ChallengeCore)ModChallenges.DON_KRIEG.get(), DonKriegReward.getTables());
      this.addLootTable(writer, (ChallengeCore)ModChallenges.DON_KRIEG_HARD.get(), DonKriegHardReward.getTables());
      this.addLootTable(writer, (ChallengeCore)ModChallenges.GIN.get(), GinReward.getTables());
      this.addLootTable(writer, (ChallengeCore)ModChallenges.GIN_HARD.get(), GinHardReward.getTables());
      this.addLootTable(writer, (ChallengeCore)ModChallenges.PEARL.get(), PearlReward.getTables());
      this.addLootTable(writer, (ChallengeCore)ModChallenges.PEARL_HARD.get(), PearlHardReward.getTables());
      this.addLootTable(writer, (ChallengeCore)ModChallenges.ARLONG.get(), ArlongReward.getTables());
      this.addLootTable(writer, (ChallengeCore)ModChallenges.ARLONG_HARD.get(), ArlongHardReward.getTables());
      this.addLootTable(writer, (ChallengeCore)ModChallenges.CHEW.get(), ChewReward.getTables());
      this.addLootTable(writer, (ChallengeCore)ModChallenges.CHEW_HARD.get(), ChewHardReward.getTables());
      this.addLootTable(writer, (ChallengeCore)ModChallenges.KUROOBI.get(), KuroobiReward.getTables());
      this.addLootTable(writer, (ChallengeCore)ModChallenges.KUROOBI_HARD.get(), KuroobiHardReward.getTables());
      this.addLootTable(writer, (ChallengeCore)ModChallenges.MR_0.get(), Mr0Reward.getTables());
      this.addLootTable(writer, (ChallengeCore)ModChallenges.MR_0_HARD.get(), Mr0HardReward.getTables());
      this.addLootTable(writer, (ChallengeCore)ModChallenges.MR_1.get(), Mr1Reward.getTables());
      this.addLootTable(writer, (ChallengeCore)ModChallenges.MR_1_HARD.get(), Mr1HardReward.getTables());
      this.addLootTable(writer, (ChallengeCore)ModChallenges.MR_3.get(), Mr3Reward.getTables());
      this.addLootTable(writer, (ChallengeCore)ModChallenges.MR_3_HARD.get(), Mr3HardReward.getTables());
      this.addLootTable(writer, (ChallengeCore)ModChallenges.MR_5_AND_MISS_VALENTINE.get(), Mr5MissValentineReward.getTables());
      this.addLootTable(writer, (ChallengeCore)ModChallenges.MR_5_AND_MISS_VALENTINE_HARD.get(), Mr5MissValentineHardReward.getTables());
   }

   protected void addLootTable(BiConsumer<ResourceLocation, LootTable.Builder> writer, ChallengeCore<?> challenge, Pair<String, LootTable.Builder>[] pairs) {
      for(Pair<String, LootTable.Builder> pair : pairs) {
         ResourceLocation key2 = ResourceLocation.fromNamespaceAndPath("mineminenomi", "rewards/" + challenge.getRegistryKey().m_135815_());
         writer.accept(key2, (LootTable.Builder)pair.getRight());
      }

   }

   public static LootTableProvider.SubProviderEntry create() {
      return new LootTableProvider.SubProviderEntry(RewardsLootTablesDataGen::new, ModLootTypes.CHALLENGE);
   }
}
