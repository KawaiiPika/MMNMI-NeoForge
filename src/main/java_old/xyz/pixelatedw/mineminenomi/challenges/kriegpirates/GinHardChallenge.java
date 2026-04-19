package xyz.pixelatedw.mineminenomi.challenges.kriegpirates;

import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.api.challenges.ArenaStyle;
import xyz.pixelatedw.mineminenomi.api.challenges.Challenge;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeDifficulty;
import xyz.pixelatedw.mineminenomi.challenges.arenas.BaratieSimpleArena;
import xyz.pixelatedw.mineminenomi.init.ModChallenges;
import xyz.pixelatedw.mineminenomi.init.ModMobs;
import xyz.pixelatedw.mineminenomi.init.ModNPCGroups;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class GinHardChallenge extends Challenge {
   private static final String TITLE;
   public static final ResourceLocation REWARD;
   public static final ChallengeCore<GinHardChallenge> INSTANCE;

   public GinHardChallenge(ChallengeCore<GinHardChallenge> core) {
      super(core);
   }

   static {
      TITLE = ModRegistry.registerName(ModRegistry.I18nCategory.CHALLENGE, "krieg_pirates.gin_hard", "Gin (Hard)");
      REWARD = ResourceLocation.fromNamespaceAndPath("mineminenomi", "rewards/gin_hard");
      INSTANCE = (new ChallengeCore.Builder("gin_hard", TITLE, GinChallenge.OBJECTIVE, ModNPCGroups.KRIEG_PIRATES.getName(), GinHardChallenge::new)).setDifficulty(ChallengeDifficulty.HARD).setDifficultyStars(1).addArena(ArenaStyle.SIMPLE, BaratieSimpleArena.INSTANCE, BaratieSimpleArena::getChallengerSpawnPos, BaratieSimpleArena::getEnemySpawnPos).setEnemySpawns(GinChallenge::setEnemeySpawns).setTargetShowcase(ModMobs.GIN).setTimeLimit(10).setOrder(ModChallenges.Order.GIN).setRewards(REWARD).build();
   }
}
