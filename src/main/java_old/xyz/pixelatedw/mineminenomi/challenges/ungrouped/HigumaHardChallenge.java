package xyz.pixelatedw.mineminenomi.challenges.ungrouped;

import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.api.challenges.ArenaStyle;
import xyz.pixelatedw.mineminenomi.api.challenges.Challenge;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeDifficulty;
import xyz.pixelatedw.mineminenomi.challenges.arenas.HigumaSimpleArena;
import xyz.pixelatedw.mineminenomi.init.ModChallenges;
import xyz.pixelatedw.mineminenomi.init.ModMobs;
import xyz.pixelatedw.mineminenomi.init.ModNPCGroups;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class HigumaHardChallenge extends Challenge {
   private static final String TITLE;
   public static final ResourceLocation REWARD;
   public static final ChallengeCore<HigumaHardChallenge> INSTANCE;

   public HigumaHardChallenge(ChallengeCore<HigumaHardChallenge> core) {
      super(core);
   }

   static {
      TITLE = ModRegistry.registerName(ModRegistry.I18nCategory.CHALLENGE, "higuma_hard", "Higuma (Hard)");
      REWARD = ResourceLocation.fromNamespaceAndPath("mineminenomi", "rewards/higuma_hard");
      INSTANCE = (new ChallengeCore.Builder("higuma_hard", TITLE, HigumaChallenge.OBJECTIVE, ModNPCGroups.UNGROUPED, HigumaHardChallenge::new)).setDifficulty(ChallengeDifficulty.HARD).setDifficultyStars(1).addArena(ArenaStyle.SIMPLE, HigumaSimpleArena.INSTANCE, HigumaSimpleArena::getChallengerSpawnPos, HigumaSimpleArena::getEnemySpawnPos).setEnemySpawns(HigumaChallenge::setEnemeySpawns).setTargetShowcase(ModMobs.HIGUMA).setTimeLimit(10).setOrder(ModChallenges.Order.HIGUMA).setRewards(REWARD).build();
   }
}
