package xyz.pixelatedw.mineminenomi.init;

import java.util.Arrays;
import java.util.List;
import net.minecraftforge.registries.RegistryObject;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.quests.Test2Quest;
import xyz.pixelatedw.mineminenomi.quests.TestQuest;
import xyz.pixelatedw.mineminenomi.quests.swordsman.SwordsmanTrial01Quest;
import xyz.pixelatedw.mineminenomi.quests.swordsman.SwordsmanTrial02Quest;
import xyz.pixelatedw.mineminenomi.quests.swordsman.SwordsmanTrial03Quest;
import xyz.pixelatedw.mineminenomi.quests.swordsman.SwordsmanTrial04Quest;
import xyz.pixelatedw.mineminenomi.quests.swordsman.SwordsmanTrial05Quest;

public class ModQuests {
   public static final RegistryObject<QuestId<TestQuest>> TEST = ModRegistry.registerQuest("test", "Testin", () -> TestQuest.INSTANCE);
   public static final RegistryObject<QuestId<Test2Quest>> TEST2 = ModRegistry.registerQuest("test2", "Testin2", () -> Test2Quest.INSTANCE);
   public static final RegistryObject<QuestId<SwordsmanTrial01Quest>> SWORDSMAN_TRIAL_01 = ModRegistry.registerQuest("trial_shi_shishi_sonson", "Trial: Shi Shishi Sonson", () -> SwordsmanTrial01Quest.INSTANCE);
   public static final RegistryObject<QuestId<SwordsmanTrial02Quest>> SWORDSMAN_TRIAL_02 = ModRegistry.registerQuest("trial_yakkodori", "Trial: Yakkodori", () -> SwordsmanTrial02Quest.INSTANCE);
   public static final RegistryObject<QuestId<SwordsmanTrial03Quest>> SWORDSMAN_TRIAL_03 = ModRegistry.registerQuest("trial_sanbyakurokuju_pound_ho", "Trial: Sanbyakurokuju Pound Ho", () -> SwordsmanTrial03Quest.INSTANCE);
   public static final RegistryObject<QuestId<SwordsmanTrial04Quest>> SWORDSMAN_TRIAL_04 = ModRegistry.registerQuest("trial_tatsu_maki", "Trial: Tatsu Maki", () -> SwordsmanTrial04Quest.INSTANCE);
   public static final RegistryObject<QuestId<SwordsmanTrial05Quest>> SWORDSMAN_TRIAL_05 = ModRegistry.registerQuest("trial_hiryu_kaen", "Trial: Hiryu Kaen", () -> SwordsmanTrial05Quest.INSTANCE);
   public static final List<RegistryObject<? extends QuestId<? extends Quest>>> SWORDSMAN_TRIALS;

   public static void init() {
   }

   static {
      SWORDSMAN_TRIALS = Arrays.asList(SWORDSMAN_TRIAL_01, SWORDSMAN_TRIAL_02, SWORDSMAN_TRIAL_03, SWORDSMAN_TRIAL_04, SWORDSMAN_TRIAL_05);
   }
}
