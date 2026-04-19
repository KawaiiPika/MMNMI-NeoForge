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

public class DonKriegHardChallenge extends Challenge {
   private static final String TITLE;
   public static final ResourceLocation REWARD;
   public static final ChallengeCore<DonKriegHardChallenge> INSTANCE;

   public DonKriegHardChallenge(ChallengeCore<DonKriegHardChallenge> core) {
      super(core);
   }

   static {
      TITLE = ModRegistry.registerName(ModRegistry.I18nCategory.CHALLENGE, "krieg_pirates.don_krieg_hard", "Don Krieg (Hard)");
      REWARD = ResourceLocation.fromNamespaceAndPath("mineminenomi", "rewards/don_krieg_hard");
      INSTANCE = (new ChallengeCore.Builder("don_krieg_hard", TITLE, DonKriegChallenge.OBJECTIVE, ModNPCGroups.KRIEG_PIRATES.getName(), DonKriegHardChallenge::new)).setDifficulty(ChallengeDifficulty.HARD).setDifficultyStars(1).setRewardsFactor(2.0F).addArena(ArenaStyle.SIMPLE, BaratieSimpleArena.INSTANCE, BaratieSimpleArena::getChallengerSpawnPos, BaratieSimpleArena::getEnemySpawnPos).setEnemySpawns(DonKriegChallenge::setEnemeySpawns).setTargetShowcase(DonKriegChallenge::createKriegShowcase).setTimeLimit(10).setOrder(ModChallenges.Order.DON_KRIEG).setRewards(REWARD).build();
   }
}
