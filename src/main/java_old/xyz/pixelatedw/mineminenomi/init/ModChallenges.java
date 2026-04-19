package xyz.pixelatedw.mineminenomi.init;

import net.minecraftforge.registries.RegistryObject;
import xyz.pixelatedw.mineminenomi.api.WyDebug;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeDifficulty;
import xyz.pixelatedw.mineminenomi.challenges.arlongpirates.ArlongChallenge;
import xyz.pixelatedw.mineminenomi.challenges.arlongpirates.ArlongHardChallenge;
import xyz.pixelatedw.mineminenomi.challenges.arlongpirates.ChewChallenge;
import xyz.pixelatedw.mineminenomi.challenges.arlongpirates.ChewHardChallenge;
import xyz.pixelatedw.mineminenomi.challenges.arlongpirates.KuroobiChallenge;
import xyz.pixelatedw.mineminenomi.challenges.arlongpirates.KuroobiHardChallenge;
import xyz.pixelatedw.mineminenomi.challenges.baroqueworks.Mr0Challenge;
import xyz.pixelatedw.mineminenomi.challenges.baroqueworks.Mr0HardChallenge;
import xyz.pixelatedw.mineminenomi.challenges.baroqueworks.Mr1Challenge;
import xyz.pixelatedw.mineminenomi.challenges.baroqueworks.Mr1HardChallenge;
import xyz.pixelatedw.mineminenomi.challenges.baroqueworks.Mr3Challenge;
import xyz.pixelatedw.mineminenomi.challenges.baroqueworks.Mr3HardChallenge;
import xyz.pixelatedw.mineminenomi.challenges.baroqueworks.Mr5MissValentineChallenge;
import xyz.pixelatedw.mineminenomi.challenges.baroqueworks.Mr5MissValentineHardChallenge;
import xyz.pixelatedw.mineminenomi.challenges.blackcatpirates.JangoChallenge;
import xyz.pixelatedw.mineminenomi.challenges.blackcatpirates.JangoHardChallenge;
import xyz.pixelatedw.mineminenomi.challenges.blackcatpirates.KuroChallenge;
import xyz.pixelatedw.mineminenomi.challenges.blackcatpirates.KuroHardChallenge;
import xyz.pixelatedw.mineminenomi.challenges.blackcatpirates.NyanbanBrothersChallenge;
import xyz.pixelatedw.mineminenomi.challenges.blackcatpirates.NyanbanBrothersHardChallenge;
import xyz.pixelatedw.mineminenomi.challenges.buggypirates.AlvidaChallenge;
import xyz.pixelatedw.mineminenomi.challenges.buggypirates.AlvidaHardChallenge;
import xyz.pixelatedw.mineminenomi.challenges.buggypirates.BuggyChallenge;
import xyz.pixelatedw.mineminenomi.challenges.buggypirates.BuggyHardChallenge;
import xyz.pixelatedw.mineminenomi.challenges.buggypirates.CabajiChallenge;
import xyz.pixelatedw.mineminenomi.challenges.buggypirates.CabajiHardChallenge;
import xyz.pixelatedw.mineminenomi.challenges.kriegpirates.DonKriegChallenge;
import xyz.pixelatedw.mineminenomi.challenges.kriegpirates.DonKriegHardChallenge;
import xyz.pixelatedw.mineminenomi.challenges.kriegpirates.GinChallenge;
import xyz.pixelatedw.mineminenomi.challenges.kriegpirates.GinHardChallenge;
import xyz.pixelatedw.mineminenomi.challenges.kriegpirates.PearlChallenge;
import xyz.pixelatedw.mineminenomi.challenges.kriegpirates.PearlHardChallenge;
import xyz.pixelatedw.mineminenomi.challenges.marines.MorganChallenge;
import xyz.pixelatedw.mineminenomi.challenges.marines.MorganHardChallenge;
import xyz.pixelatedw.mineminenomi.challenges.ungrouped.HigumaChallenge;
import xyz.pixelatedw.mineminenomi.challenges.ungrouped.HigumaHardChallenge;

public class ModChallenges {
   public static final RegistryObject<ChallengeCore<HigumaChallenge>> HIGUMA;
   public static final RegistryObject<ChallengeCore<HigumaHardChallenge>> HIGUMA_HARD;
   public static final RegistryObject<ChallengeCore<MorganChallenge>> MORGAN;
   public static final RegistryObject<ChallengeCore<MorganHardChallenge>> MORGAN_HARD;
   public static final RegistryObject<ChallengeCore<BuggyChallenge>> BUGGY;
   public static final RegistryObject<ChallengeCore<BuggyHardChallenge>> BUGGY_HARD;
   public static final RegistryObject<ChallengeCore<CabajiChallenge>> CABAJI;
   public static final RegistryObject<ChallengeCore<CabajiHardChallenge>> CABAJI_HARD;
   public static final RegistryObject<ChallengeCore<AlvidaChallenge>> ALVIDA;
   public static final RegistryObject<ChallengeCore<AlvidaHardChallenge>> ALVIDA_HARD;
   public static final RegistryObject<ChallengeCore<KuroChallenge>> KURO;
   public static final RegistryObject<ChallengeCore<KuroHardChallenge>> KURO_HARD;
   public static final RegistryObject<ChallengeCore<JangoChallenge>> JANGO;
   public static final RegistryObject<ChallengeCore<JangoHardChallenge>> JANGO_HARD;
   public static final RegistryObject<ChallengeCore<NyanbanBrothersChallenge>> NYANBAN_BROTHERS;
   public static final RegistryObject<ChallengeCore<NyanbanBrothersHardChallenge>> NYANBAN_BROTHERS_HARD;
   public static final RegistryObject<ChallengeCore<DonKriegChallenge>> DON_KRIEG;
   public static final RegistryObject<ChallengeCore<DonKriegHardChallenge>> DON_KRIEG_HARD;
   public static final RegistryObject<ChallengeCore<GinChallenge>> GIN;
   public static final RegistryObject<ChallengeCore<GinHardChallenge>> GIN_HARD;
   public static final RegistryObject<ChallengeCore<PearlChallenge>> PEARL;
   public static final RegistryObject<ChallengeCore<PearlHardChallenge>> PEARL_HARD;
   public static final RegistryObject<ChallengeCore<ArlongChallenge>> ARLONG;
   public static final RegistryObject<ChallengeCore<ArlongHardChallenge>> ARLONG_HARD;
   public static final RegistryObject<ChallengeCore<KuroobiChallenge>> KUROOBI;
   public static final RegistryObject<ChallengeCore<KuroobiHardChallenge>> KUROOBI_HARD;
   public static final RegistryObject<ChallengeCore<ChewChallenge>> CHEW;
   public static final RegistryObject<ChallengeCore<ChewHardChallenge>> CHEW_HARD;
   public static final RegistryObject<ChallengeCore<Mr0Challenge>> MR_0;
   public static final RegistryObject<ChallengeCore<Mr0HardChallenge>> MR_0_HARD;
   public static final RegistryObject<ChallengeCore<Mr1Challenge>> MR_1;
   public static final RegistryObject<ChallengeCore<Mr1HardChallenge>> MR_1_HARD;
   public static final RegistryObject<ChallengeCore<Mr3Challenge>> MR_3;
   public static final RegistryObject<ChallengeCore<Mr3HardChallenge>> MR_3_HARD;
   public static final RegistryObject<ChallengeCore<Mr5MissValentineChallenge>> MR_5_AND_MISS_VALENTINE;
   public static final RegistryObject<ChallengeCore<Mr5MissValentineHardChallenge>> MR_5_AND_MISS_VALENTINE_HARD;

   public static void init() {
   }

   static {
      HIGUMA = ModRegistry.registerChallenge(HigumaChallenge.INSTANCE);
      HIGUMA_HARD = ModRegistry.registerChallenge(HigumaHardChallenge.INSTANCE);
      MORGAN = ModRegistry.registerChallenge(MorganChallenge.INSTANCE);
      MORGAN_HARD = ModRegistry.registerChallenge(MorganHardChallenge.INSTANCE);
      BUGGY = ModRegistry.registerChallenge(BuggyChallenge.INSTANCE);
      BUGGY_HARD = ModRegistry.registerChallenge(BuggyHardChallenge.INSTANCE);
      CABAJI = ModRegistry.registerChallenge(CabajiChallenge.INSTANCE);
      CABAJI_HARD = ModRegistry.registerChallenge(CabajiHardChallenge.INSTANCE);
      ALVIDA = ModRegistry.registerChallenge(AlvidaChallenge.INSTANCE);
      ALVIDA_HARD = ModRegistry.registerChallenge(AlvidaHardChallenge.INSTANCE);
      KURO = ModRegistry.registerChallenge(KuroChallenge.INSTANCE);
      KURO_HARD = ModRegistry.registerChallenge(KuroHardChallenge.INSTANCE);
      JANGO = ModRegistry.registerChallenge(JangoChallenge.INSTANCE);
      JANGO_HARD = ModRegistry.registerChallenge(JangoHardChallenge.INSTANCE);
      NYANBAN_BROTHERS = ModRegistry.registerChallenge(NyanbanBrothersChallenge.INSTANCE);
      NYANBAN_BROTHERS_HARD = ModRegistry.registerChallenge(NyanbanBrothersHardChallenge.INSTANCE);
      DON_KRIEG = ModRegistry.registerChallenge(DonKriegChallenge.INSTANCE);
      DON_KRIEG_HARD = ModRegistry.registerChallenge(DonKriegHardChallenge.INSTANCE);
      GIN = ModRegistry.registerChallenge(GinChallenge.INSTANCE);
      GIN_HARD = ModRegistry.registerChallenge(GinHardChallenge.INSTANCE);
      PEARL = ModRegistry.registerChallenge(PearlChallenge.INSTANCE);
      PEARL_HARD = ModRegistry.registerChallenge(PearlHardChallenge.INSTANCE);
      ARLONG = ModRegistry.registerChallenge(ArlongChallenge.INSTANCE);
      ARLONG_HARD = ModRegistry.registerChallenge(ArlongHardChallenge.INSTANCE);
      KUROOBI = ModRegistry.registerChallenge(KuroobiChallenge.INSTANCE);
      KUROOBI_HARD = ModRegistry.registerChallenge(KuroobiHardChallenge.INSTANCE);
      CHEW = ModRegistry.registerChallenge(ChewChallenge.INSTANCE);
      CHEW_HARD = ModRegistry.registerChallenge(ChewHardChallenge.INSTANCE);
      MR_0 = ModRegistry.registerChallenge(Mr0Challenge.INSTANCE);
      MR_0_HARD = ModRegistry.registerChallenge(Mr0HardChallenge.INSTANCE);
      MR_1 = ModRegistry.registerChallenge(Mr1Challenge.INSTANCE);
      MR_1_HARD = ModRegistry.registerChallenge(Mr1HardChallenge.INSTANCE);
      MR_3 = ModRegistry.registerChallenge(Mr3Challenge.INSTANCE);
      MR_3_HARD = ModRegistry.registerChallenge(Mr3HardChallenge.INSTANCE);
      MR_5_AND_MISS_VALENTINE = ModRegistry.registerChallenge(Mr5MissValentineChallenge.INSTANCE);
      MR_5_AND_MISS_VALENTINE_HARD = ModRegistry.registerChallenge(Mr5MissValentineHardChallenge.INSTANCE);
   }

   public static class Order {
      private static int idx = 0;
      public static final int ALVIDA = orderIndex();
      public static final int HIGUMA = orderIndex();
      public static final int MORGAN = orderIndex();
      public static final int MOHJI_RICHIE = orderIndex();
      public static final int CABAJI = orderIndex();
      public static final int BUGGY = orderIndex();
      public static final int SHAM_BUCHI = orderIndex();
      public static final int JANGO = orderIndex();
      public static final int KURO = orderIndex();
      public static final int PEARL = orderIndex();
      public static final int GIN = orderIndex();
      public static final int DON_KRIEG = orderIndex();
      public static final int CHEW = orderIndex();
      public static final int KUROOBI = orderIndex();
      public static final int HATCHAN = orderIndex();
      public static final int ARLONG = orderIndex();
      public static final int MR_5_MISS_VALENTINE = orderIndex();
      public static final int MR_4_MISS_MERRY_CHRISTMAS = orderIndex();
      public static final int MR_3 = orderIndex();
      public static final int MR_2 = orderIndex();
      public static final int MISS_DOUBLEFINGER = orderIndex();
      public static final int MR_1 = orderIndex();
      public static final int MR_0 = orderIndex();

      private static int orderIndex() {
         int currentIdx = idx;
         idx += ChallengeDifficulty.values().length;
         WyDebug.debug("Challenges Order Index: " + currentIdx + "/" + idx);
         return currentIdx;
      }
   }
}
