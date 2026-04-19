package xyz.pixelatedw.mineminenomi.challenges.marines;

import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.api.challenges.ArenaStyle;
import xyz.pixelatedw.mineminenomi.api.challenges.Challenge;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeDifficulty;
import xyz.pixelatedw.mineminenomi.challenges.arenas.MarineSmallBaseSimpleArena;
import xyz.pixelatedw.mineminenomi.init.ModChallenges;
import xyz.pixelatedw.mineminenomi.init.ModNPCGroups;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class MorganHardChallenge extends Challenge {
   private static final String TITLE;
   public static final ResourceLocation REWARD;
   public static final ChallengeCore<MorganHardChallenge> INSTANCE;

   public MorganHardChallenge(ChallengeCore<MorganHardChallenge> core) {
      super(core);
   }

   static {
      TITLE = ModRegistry.registerName(ModRegistry.I18nCategory.CHALLENGE, "marines.morgan_hard", "Morgan (Hard)");
      REWARD = ResourceLocation.fromNamespaceAndPath("mineminenomi", "rewards/morgan_hard");
      INSTANCE = (new ChallengeCore.Builder("morgan_hard", TITLE, MorganChallenge.OBJECTIVE, ModNPCGroups.MARINES, MorganHardChallenge::new)).setDifficulty(ChallengeDifficulty.HARD).setDifficultyStars(1).addArena(ArenaStyle.SIMPLE, MarineSmallBaseSimpleArena.INSTANCE, MarineSmallBaseSimpleArena::getChallengerSpawnPos, MarineSmallBaseSimpleArena::getEnemySpawnPos).setEnemySpawns(MorganChallenge::setEnemeySpawns).setTargetShowcase(MorganChallenge::createShowcase).setTimeLimit(10).setOrder(ModChallenges.Order.MORGAN).setRewards(REWARD).build();
   }
}
