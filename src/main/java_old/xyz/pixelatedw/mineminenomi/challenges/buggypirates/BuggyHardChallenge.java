package xyz.pixelatedw.mineminenomi.challenges.buggypirates;

import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.api.challenges.ArenaStyle;
import xyz.pixelatedw.mineminenomi.api.challenges.Challenge;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeDifficulty;
import xyz.pixelatedw.mineminenomi.challenges.arenas.CircusArena;
import xyz.pixelatedw.mineminenomi.init.ModChallenges;
import xyz.pixelatedw.mineminenomi.init.ModNPCGroups;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class BuggyHardChallenge extends Challenge {
   private static final String TITLE;
   public static final ResourceLocation REWARD;
   public static final ChallengeCore<BuggyHardChallenge> INSTANCE;

   public BuggyHardChallenge(ChallengeCore<BuggyHardChallenge> core) {
      super(core);
   }

   static {
      TITLE = ModRegistry.registerName(ModRegistry.I18nCategory.CHALLENGE, "buggy_pirates.buggy_hard", "Buggy (Hard)");
      REWARD = ResourceLocation.fromNamespaceAndPath("mineminenomi", "rewards/buggy_hard");
      INSTANCE = (new ChallengeCore.Builder("buggy_hard", TITLE, BuggyChallenge.OBJECTIVE, ModNPCGroups.BUGGY_PIRATES.getName(), BuggyHardChallenge::new)).setDifficulty(ChallengeDifficulty.HARD).setDifficultyStars(1).setRewardsFactor(2.0F).addArena(ArenaStyle.SIMPLE, CircusArena.INSTANCE, CircusArena::getChallengerSpawnPos, CircusArena::getEnemySpawnPos).setEnemySpawns(BuggyChallenge::setEnemeySpawns).setTargetShowcase(BuggyChallenge::createShowcase).setTimeLimit(10).setOrder(ModChallenges.Order.BUGGY).setRewards(REWARD).build();
   }
}
