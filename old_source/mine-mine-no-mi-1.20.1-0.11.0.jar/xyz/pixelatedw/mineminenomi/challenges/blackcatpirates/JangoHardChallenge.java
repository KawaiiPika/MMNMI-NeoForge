package xyz.pixelatedw.mineminenomi.challenges.blackcatpirates;

import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.api.challenges.ArenaStyle;
import xyz.pixelatedw.mineminenomi.api.challenges.Challenge;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeDifficulty;
import xyz.pixelatedw.mineminenomi.challenges.arenas.SyrupHillSimpleArena;
import xyz.pixelatedw.mineminenomi.init.ModChallenges;
import xyz.pixelatedw.mineminenomi.init.ModNPCGroups;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class JangoHardChallenge extends Challenge {
   private static final String TITLE;
   public static final ResourceLocation REWARD;
   public static final ChallengeCore<JangoHardChallenge> INSTANCE;

   public JangoHardChallenge(ChallengeCore<JangoHardChallenge> core) {
      super(core);
   }

   static {
      TITLE = ModRegistry.registerName(ModRegistry.I18nCategory.CHALLENGE, "black_cat_pirates.jango_hard", "Jango (Hard)");
      REWARD = ResourceLocation.fromNamespaceAndPath("mineminenomi", "rewards/jango_hard");
      INSTANCE = (new ChallengeCore.Builder("jango_hard", TITLE, JangoChallenge.OBJECTIVE, ModNPCGroups.BLACK_CAT_PIRATES.getName(), JangoHardChallenge::new)).setDifficulty(ChallengeDifficulty.HARD).setDifficultyStars(1).addArena(ArenaStyle.SIMPLE, SyrupHillSimpleArena.INSTANCE, SyrupHillSimpleArena::getChallengerSpawnPos, SyrupHillSimpleArena::getEnemySpawnPos).setEnemySpawns(JangoChallenge::setEnemeySpawns).setTargetShowcase(JangoChallenge::createJangoShowcase).setTimeLimit(10).setOrder(ModChallenges.Order.JANGO).setRewards(REWARD).build();
   }
}
