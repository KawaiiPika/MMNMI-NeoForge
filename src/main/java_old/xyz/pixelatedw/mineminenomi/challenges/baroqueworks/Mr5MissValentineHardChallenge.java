package xyz.pixelatedw.mineminenomi.challenges.baroqueworks;

import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.api.challenges.ArenaStyle;
import xyz.pixelatedw.mineminenomi.api.challenges.Challenge;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeDifficulty;
import xyz.pixelatedw.mineminenomi.challenges.arenas.JungleClearingSimpleArena;
import xyz.pixelatedw.mineminenomi.init.ModChallenges;
import xyz.pixelatedw.mineminenomi.init.ModMobs;
import xyz.pixelatedw.mineminenomi.init.ModNPCGroups;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class Mr5MissValentineHardChallenge extends Challenge {
   private static final String TITLE;
   public static final ResourceLocation REWARD;
   public static final ChallengeCore<Mr5MissValentineHardChallenge> INSTANCE;

   public Mr5MissValentineHardChallenge(ChallengeCore<Mr5MissValentineHardChallenge> core) {
      super(core);
   }

   static {
      TITLE = ModRegistry.registerName(ModRegistry.I18nCategory.CHALLENGE, "baroque_works.mr_5_and_miss_valentine_hard", "Mr. 5 & Miss Valentine (Hard)");
      REWARD = ResourceLocation.fromNamespaceAndPath("mineminenomi", "rewards/mr_5_and_miss_valentine_hard");
      INSTANCE = (new ChallengeCore.Builder("mr_5_and_miss_valentine_hard", TITLE, Mr5MissValentineChallenge.OBJECTIVE, ModNPCGroups.BAROQUE_WORKS.getName(), Mr5MissValentineHardChallenge::new)).setDifficulty(ChallengeDifficulty.HARD).setDifficultyStars(2).addArena(ArenaStyle.SIMPLE, JungleClearingSimpleArena.INSTANCE, JungleClearingSimpleArena::getChallengerSpawnPos, JungleClearingSimpleArena::getEnemySpawnPos).setEnemySpawns(Mr5MissValentineChallenge::setEnemeySpawns).setTargetShowcase(ModMobs.MR5, ModMobs.MISS_VALENTINE).setTimeLimit(10).setOrder(ModChallenges.Order.MR_5_MISS_VALENTINE).setRewards(REWARD).build();
   }
}
