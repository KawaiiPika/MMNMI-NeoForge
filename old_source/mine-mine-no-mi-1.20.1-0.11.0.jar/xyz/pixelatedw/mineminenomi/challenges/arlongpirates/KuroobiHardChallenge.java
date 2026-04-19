package xyz.pixelatedw.mineminenomi.challenges.arlongpirates;

import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.api.challenges.ArenaStyle;
import xyz.pixelatedw.mineminenomi.api.challenges.Challenge;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeDifficulty;
import xyz.pixelatedw.mineminenomi.challenges.arenas.ArlongParkSimpleArena;
import xyz.pixelatedw.mineminenomi.init.ModChallenges;
import xyz.pixelatedw.mineminenomi.init.ModMobs;
import xyz.pixelatedw.mineminenomi.init.ModNPCGroups;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class KuroobiHardChallenge extends Challenge {
   private static final String TITLE;
   public static final ResourceLocation REWARD;
   public static final ChallengeCore<KuroobiHardChallenge> INSTANCE;

   public KuroobiHardChallenge(ChallengeCore<KuroobiHardChallenge> core) {
      super(core);
   }

   static {
      TITLE = ModRegistry.registerName(ModRegistry.I18nCategory.CHALLENGE, "kuroobi_hard", "Kuroobi (Hard)");
      REWARD = ResourceLocation.fromNamespaceAndPath("mineminenomi", "rewards/kuroobi_hard");
      INSTANCE = (new ChallengeCore.Builder("kuroobi_hard", TITLE, KuroobiChallenge.OBJECTIVE, ModNPCGroups.ARLONG_PIRATES.getName(), KuroobiHardChallenge::new)).setDifficulty(ChallengeDifficulty.HARD).setDifficultyStars(1).addArena(ArenaStyle.SIMPLE, ArlongParkSimpleArena.INSTANCE, ArlongParkSimpleArena::getChallengerSpawnPos, ArlongParkSimpleArena::getEnemySpawnPos).setEnemySpawns(KuroobiChallenge::setEnemeySpawns).setTargetShowcase(ModMobs.KUROOBI).setTimeLimit(10).setOrder(ModChallenges.Order.KUROOBI).setRewards(REWARD).build();
   }
}
