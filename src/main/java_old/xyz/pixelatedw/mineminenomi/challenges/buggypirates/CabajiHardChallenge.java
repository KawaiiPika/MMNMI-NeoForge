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

public class CabajiHardChallenge extends Challenge {
   private static final String TITLE;
   public static final ResourceLocation REWARD;
   public static final ChallengeCore<CabajiHardChallenge> INSTANCE;

   public CabajiHardChallenge(ChallengeCore<CabajiHardChallenge> core) {
      super(core);
   }

   static {
      TITLE = ModRegistry.registerName(ModRegistry.I18nCategory.CHALLENGE, "buggy_pirates.cabaji_hard", "Cabaji (Hard)");
      REWARD = ResourceLocation.fromNamespaceAndPath("mineminenomi", "rewards/cabaji_hard");
      INSTANCE = (new ChallengeCore.Builder("cabaji_hard", TITLE, CabajiChallenge.OBJECTIVE, ModNPCGroups.BUGGY_PIRATES.getName(), CabajiHardChallenge::new)).setDifficulty(ChallengeDifficulty.HARD).setDifficultyStars(1).addArena(ArenaStyle.SIMPLE, CircusArena.INSTANCE, CircusArena::getChallengerSpawnPos, CircusArena::getEnemySpawnPos).setEnemySpawns(CabajiChallenge::setEnemeySpawns).setTargetShowcase(CabajiChallenge::createShowcase).setTimeLimit(10).setOrder(ModChallenges.Order.CABAJI).setRewards(REWARD).build();
   }
}
