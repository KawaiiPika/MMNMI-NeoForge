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

public class KuroHardChallenge extends Challenge {
   private static final String TITLE;
   public static final ResourceLocation REWARD;
   public static final ChallengeCore<KuroHardChallenge> INSTANCE;

   public KuroHardChallenge(ChallengeCore<KuroHardChallenge> core) {
      super(core);
   }

   static {
      TITLE = ModRegistry.registerName(ModRegistry.I18nCategory.CHALLENGE, "black_cat_pirates.kuro_hard", "Kuro (Hard)");
      REWARD = ResourceLocation.fromNamespaceAndPath("mineminenomi", "rewards/kuro_hard");
      INSTANCE = (new ChallengeCore.Builder("kuro_hard", TITLE, KuroChallenge.OBJECTIVE, ModNPCGroups.BLACK_CAT_PIRATES.getName(), KuroHardChallenge::new)).setDifficulty(ChallengeDifficulty.HARD).setDifficultyStars(1).setRewardsFactor(2.0F).addArena(ArenaStyle.SIMPLE, SyrupHillSimpleArena.INSTANCE, SyrupHillSimpleArena::getChallengerSpawnPos, SyrupHillSimpleArena::getEnemySpawnPos).setEnemySpawns(KuroChallenge::setEnemeySpawns).setTargetShowcase(ModMobs.KURO).setTimeLimit(10).setOrder(ModChallenges.Order.KURO).setRewards(REWARD).build();
   }
}
