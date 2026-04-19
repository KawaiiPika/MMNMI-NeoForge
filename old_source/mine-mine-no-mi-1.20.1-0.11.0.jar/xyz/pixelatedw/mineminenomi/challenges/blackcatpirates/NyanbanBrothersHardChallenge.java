package xyz.pixelatedw.mineminenomi.challenges.blackcatpirates;

import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.api.challenges.ArenaStyle;
import xyz.pixelatedw.mineminenomi.api.challenges.Challenge;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeDifficulty;
import xyz.pixelatedw.mineminenomi.challenges.arenas.SyrupHillSimpleArena;
import xyz.pixelatedw.mineminenomi.init.ModChallenges;
import xyz.pixelatedw.mineminenomi.init.ModMobs;
import xyz.pixelatedw.mineminenomi.init.ModNPCGroups;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class NyanbanBrothersHardChallenge extends Challenge {
   private static final String TITLE;
   public static final ResourceLocation REWARD;
   public static final ChallengeCore<NyanbanBrothersHardChallenge> INSTANCE;

   public NyanbanBrothersHardChallenge(ChallengeCore<NyanbanBrothersHardChallenge> core) {
      super(core);
   }

   static {
      TITLE = ModRegistry.registerName(ModRegistry.I18nCategory.CHALLENGE, "black_cat_pirates.nyanban_brothers_hard", "Nyanban Brothers (Hard)");
      REWARD = ResourceLocation.fromNamespaceAndPath("mineminenomi", "rewards/nyanban_brothers_hard");
      INSTANCE = (new ChallengeCore.Builder("nyanban_brothers_hard", TITLE, NyanbanBrothersChallenge.OBJECTIVE, ModNPCGroups.BLACK_CAT_PIRATES.getName(), NyanbanBrothersHardChallenge::new)).setDifficulty(ChallengeDifficulty.HARD).setDifficultyStars(1).addArena(ArenaStyle.SIMPLE, SyrupHillSimpleArena.INSTANCE, SyrupHillSimpleArena::getChallengerSpawnPos, SyrupHillSimpleArena::getEnemySpawnPos).setEnemySpawns(NyanbanBrothersChallenge::setEnemeySpawns).setTargetShowcase(ModMobs.SHAM, ModMobs.BUCHI).setTimeLimit(10).setOrder(ModChallenges.Order.SHAM_BUCHI).setRewards(REWARD).build();
   }
}
