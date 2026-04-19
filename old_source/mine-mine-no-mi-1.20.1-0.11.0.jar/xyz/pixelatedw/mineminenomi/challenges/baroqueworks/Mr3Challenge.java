package xyz.pixelatedw.mineminenomi.challenges.baroqueworks;

import java.util.HashSet;
import java.util.Set;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.api.challenges.ArenaStyle;
import xyz.pixelatedw.mineminenomi.api.challenges.Challenge;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeArena;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeDifficulty;
import xyz.pixelatedw.mineminenomi.api.challenges.InProgressChallenge;
import xyz.pixelatedw.mineminenomi.challenges.arenas.JungleClearingSimpleArena;
import xyz.pixelatedw.mineminenomi.entities.mobs.pirates.baroqueworks.Mr3Entity;
import xyz.pixelatedw.mineminenomi.init.ModChallenges;
import xyz.pixelatedw.mineminenomi.init.ModMobs;
import xyz.pixelatedw.mineminenomi.init.ModNPCGroups;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class Mr3Challenge extends Challenge {
   private static final String TITLE;
   public static final String OBJECTIVE;
   public static final ResourceLocation REWARD;
   public static final ChallengeCore<Mr3Challenge> INSTANCE;

   public Mr3Challenge(ChallengeCore<Mr3Challenge> core) {
      super(core);
   }

   public static Set<ChallengeArena.EnemySpawn> setEnemeySpawns(InProgressChallenge challenge, ChallengeArena.SpawnPosition[] spawns) {
      Set<ChallengeArena.EnemySpawn> set = new HashSet();
      Mr3Entity boss = new Mr3Entity(challenge);
      set.add(new ChallengeArena.EnemySpawn(boss, spawns[0]));
      return set;
   }

   static {
      TITLE = ModRegistry.registerName(ModRegistry.I18nCategory.CHALLENGE, "baroque_works.mr_3", "Mr. 3");
      OBJECTIVE = ModRegistry.registerName(ModRegistry.I18nCategory.CHALLENGE, "baroque_works.mr_3.objective", "Defeat Mr. 3");
      REWARD = ResourceLocation.fromNamespaceAndPath("mineminenomi", "rewards/mr_3");
      INSTANCE = (new ChallengeCore.Builder("mr_3", TITLE, OBJECTIVE, ModNPCGroups.BAROQUE_WORKS.getName(), Mr3Challenge::new)).setDifficulty(ChallengeDifficulty.STANDARD).setDifficultyStars(2).setRewardsFactor(2.0F).addArena(ArenaStyle.SIMPLE, JungleClearingSimpleArena.INSTANCE, JungleClearingSimpleArena::getChallengerSpawnPos, JungleClearingSimpleArena::getEnemySpawnPos).setEnemySpawns(Mr3Challenge::setEnemeySpawns).setTargetShowcase(ModMobs.MR3).setTimeLimit(10).setOrder(ModChallenges.Order.MR_3).setRewards(REWARD).build();
   }
}
