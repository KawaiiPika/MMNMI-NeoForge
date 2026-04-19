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

public class Mr3HardChallenge extends Challenge {
   private static final String TITLE;
   public static final ResourceLocation REWARD;
   public static final ChallengeCore<Mr3HardChallenge> INSTANCE;

   public Mr3HardChallenge(ChallengeCore<Mr3HardChallenge> core) {
      super(core);
   }

   static {
      TITLE = ModRegistry.registerName(ModRegistry.I18nCategory.CHALLENGE, "baroque_works.mr_3_hard", "Mr. 3 (Hard)");
      REWARD = ResourceLocation.fromNamespaceAndPath("mineminenomi", "rewards/mr_3_hard");
      INSTANCE = (new ChallengeCore.Builder("mr_3_hard", TITLE, Mr3Challenge.OBJECTIVE, ModNPCGroups.BAROQUE_WORKS.getName(), Mr3HardChallenge::new)).setDifficulty(ChallengeDifficulty.HARD).setDifficultyStars(2).setRewardsFactor(2.0F).addArena(ArenaStyle.SIMPLE, JungleClearingSimpleArena.INSTANCE, JungleClearingSimpleArena::getChallengerSpawnPos, JungleClearingSimpleArena::getEnemySpawnPos).setEnemySpawns(Mr3Challenge::setEnemeySpawns).setTargetShowcase(ModMobs.MR3).setTimeLimit(10).setOrder(ModChallenges.Order.MR_3).setRewards(REWARD).build();
   }
}
