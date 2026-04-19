package xyz.pixelatedw.mineminenomi.challenges.kriegpirates;

import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.api.challenges.ArenaStyle;
import xyz.pixelatedw.mineminenomi.api.challenges.Challenge;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeDifficulty;
import xyz.pixelatedw.mineminenomi.challenges.arenas.BaratieSimpleArena;
import xyz.pixelatedw.mineminenomi.init.ModChallenges;
import xyz.pixelatedw.mineminenomi.init.ModNPCGroups;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class PearlHardChallenge extends Challenge {
   private static final String TITLE;
   public static final ResourceLocation REWARD;
   public static final ChallengeCore<PearlHardChallenge> INSTANCE;

   public PearlHardChallenge(ChallengeCore<PearlHardChallenge> core) {
      super(core);
   }

   static {
      TITLE = ModRegistry.registerName(ModRegistry.I18nCategory.CHALLENGE, "krieg_pirates.pearl_hard", "Pearl (Hard)");
      REWARD = ResourceLocation.fromNamespaceAndPath("mineminenomi", "rewards/pearl_hard");
      INSTANCE = (new ChallengeCore.Builder("pearl_hard", TITLE, PearlChallenge.OBJECTIVE, ModNPCGroups.KRIEG_PIRATES.getName(), PearlHardChallenge::new)).setDifficulty(ChallengeDifficulty.HARD).setDifficultyStars(1).addArena(ArenaStyle.SIMPLE, BaratieSimpleArena.INSTANCE, BaratieSimpleArena::getChallengerSpawnPos, BaratieSimpleArena::getEnemySpawnPos).setEnemySpawns(PearlChallenge::setEnemeySpawns).setTargetShowcase(PearlChallenge::createPearlShowcase).setTimeLimit(10).setOrder(ModChallenges.Order.PEARL).setRewards(REWARD).build();
   }
}
