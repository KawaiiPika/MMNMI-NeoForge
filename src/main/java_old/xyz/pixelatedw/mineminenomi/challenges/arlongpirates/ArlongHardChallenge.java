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

public class ArlongHardChallenge extends Challenge {
   private static final String TITLE;
   public static final ResourceLocation REWARD;
   public static final ChallengeCore<ArlongHardChallenge> INSTANCE;

   public ArlongHardChallenge(ChallengeCore<ArlongHardChallenge> core) {
      super(core);
   }

   static {
      TITLE = ModRegistry.registerName(ModRegistry.I18nCategory.CHALLENGE, "arlong_hard", "Arlong (Hard)");
      REWARD = ResourceLocation.fromNamespaceAndPath("mineminenomi", "rewards/arlong_hard");
      INSTANCE = (new ChallengeCore.Builder("arlong_hard", TITLE, ArlongChallenge.OBJECTIVE, ModNPCGroups.ARLONG_PIRATES.getName(), ArlongHardChallenge::new)).setDifficulty(ChallengeDifficulty.HARD).setDifficultyStars(1).setRewardsFactor(2.0F).addArena(ArenaStyle.SIMPLE, ArlongParkSimpleArena.INSTANCE, ArlongParkSimpleArena::getChallengerSpawnPos, ArlongParkSimpleArena::getEnemySpawnPos).setEnemySpawns(ArlongChallenge::setEnemeySpawns).setTargetShowcase(ModMobs.ARLONG).setTimeLimit(10).setOrder(ModChallenges.Order.ARLONG).setRewards(REWARD).build();
   }
}
