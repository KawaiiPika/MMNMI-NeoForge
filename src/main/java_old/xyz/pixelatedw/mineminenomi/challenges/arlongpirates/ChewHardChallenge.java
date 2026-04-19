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

public class ChewHardChallenge extends Challenge {
   private static final String TITLE;
   public static final ResourceLocation REWARD;
   public static final ChallengeCore<ChewHardChallenge> INSTANCE;

   public ChewHardChallenge(ChallengeCore<ChewHardChallenge> core) {
      super(core);
   }

   static {
      TITLE = ModRegistry.registerName(ModRegistry.I18nCategory.CHALLENGE, "chew_hard", "Chew (Hard)");
      REWARD = ResourceLocation.fromNamespaceAndPath("mineminenomi", "rewards/chew_hard");
      INSTANCE = (new ChallengeCore.Builder("chew_hard", TITLE, ChewChallenge.OBJECTIVE, ModNPCGroups.ARLONG_PIRATES.getName(), ChewHardChallenge::new)).setDifficulty(ChallengeDifficulty.HARD).setDifficultyStars(1).addArena(ArenaStyle.SIMPLE, ArlongParkSimpleArena.INSTANCE, ArlongParkSimpleArena::getChallengerSpawnPos, ArlongParkSimpleArena::getEnemySpawnPos).setEnemySpawns(ChewChallenge::setEnemeySpawns).setTargetShowcase(ModMobs.CHEW).setTimeLimit(10).setOrder(ModChallenges.Order.CHEW).setRewards(REWARD).build();
   }
}
