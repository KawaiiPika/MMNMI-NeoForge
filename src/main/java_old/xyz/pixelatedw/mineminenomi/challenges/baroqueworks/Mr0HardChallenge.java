package xyz.pixelatedw.mineminenomi.challenges.baroqueworks;

import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.api.challenges.ArenaStyle;
import xyz.pixelatedw.mineminenomi.api.challenges.Challenge;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeDifficulty;
import xyz.pixelatedw.mineminenomi.challenges.arenas.AlabastaDesertSimpleArena;
import xyz.pixelatedw.mineminenomi.init.ModChallenges;
import xyz.pixelatedw.mineminenomi.init.ModNPCGroups;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class Mr0HardChallenge extends Challenge {
   private static final String TITLE;
   public static final ResourceLocation REWARD;
   public static final ChallengeCore<Mr0HardChallenge> INSTANCE;

   public Mr0HardChallenge(ChallengeCore<Mr0HardChallenge> core) {
      super(core);
   }

   static {
      TITLE = ModRegistry.registerName(ModRegistry.I18nCategory.CHALLENGE, "baroque_works.mr_0_hard", "Mr. 0 (Hard)");
      REWARD = ResourceLocation.fromNamespaceAndPath("mineminenomi", "rewards/mr_0_hard");
      INSTANCE = (new ChallengeCore.Builder("mr_0_hard", TITLE, Mr0Challenge.OBJECTIVE, ModNPCGroups.BAROQUE_WORKS.getName(), Mr0HardChallenge::new)).setDifficulty(ChallengeDifficulty.HARD).setDifficultyStars(3).setRewardsFactor(3.0F).addArena(ArenaStyle.SIMPLE, AlabastaDesertSimpleArena.INSTANCE, AlabastaDesertSimpleArena::getChallengerSpawnPos, AlabastaDesertSimpleArena::getEnemySpawnPos).setEnemySpawns(Mr0Challenge::setEnemeySpawns).setTargetShowcase(Mr0Challenge::createMr0Showcase).setTimeLimit(10).setOrder(ModChallenges.Order.MR_0).setRewards(REWARD).build();
   }
}
