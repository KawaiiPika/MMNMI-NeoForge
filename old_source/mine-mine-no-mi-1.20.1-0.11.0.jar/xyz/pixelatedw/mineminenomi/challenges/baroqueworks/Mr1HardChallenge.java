package xyz.pixelatedw.mineminenomi.challenges.baroqueworks;

import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.api.challenges.ArenaStyle;
import xyz.pixelatedw.mineminenomi.api.challenges.Challenge;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeDifficulty;
import xyz.pixelatedw.mineminenomi.challenges.arenas.AlabastaDesertSimpleArena;
import xyz.pixelatedw.mineminenomi.init.ModChallenges;
import xyz.pixelatedw.mineminenomi.init.ModMobs;
import xyz.pixelatedw.mineminenomi.init.ModNPCGroups;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class Mr1HardChallenge extends Challenge {
   private static final String TITLE;
   public static final ResourceLocation REWARD;
   public static final ChallengeCore<Mr1HardChallenge> INSTANCE;

   public Mr1HardChallenge(ChallengeCore<Mr1HardChallenge> core) {
      super(core);
   }

   static {
      TITLE = ModRegistry.registerName(ModRegistry.I18nCategory.CHALLENGE, "baroque_works.mr_1_hard", "Mr. 1 (Hard)");
      REWARD = ResourceLocation.fromNamespaceAndPath("mineminenomi", "rewards/mr_1_hard");
      INSTANCE = (new ChallengeCore.Builder("mr_1_hard", TITLE, Mr1Challenge.OBJECTIVE, ModNPCGroups.BAROQUE_WORKS.getName(), Mr1HardChallenge::new)).setDifficulty(ChallengeDifficulty.HARD).setDifficultyStars(3).setRewardsFactor(2.0F).addArena(ArenaStyle.SIMPLE, AlabastaDesertSimpleArena.INSTANCE, AlabastaDesertSimpleArena::getChallengerSpawnPos, AlabastaDesertSimpleArena::getEnemySpawnPos).setEnemySpawns(Mr1Challenge::setEnemeySpawns).setTargetShowcase(ModMobs.MR1).setTimeLimit(10).setOrder(ModChallenges.Order.MR_1).setRewards(REWARD).build();
   }
}
