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
import xyz.pixelatedw.mineminenomi.challenges.arenas.AlabastaDesertSimpleArena;
import xyz.pixelatedw.mineminenomi.entities.mobs.pirates.baroqueworks.Mr1Entity;
import xyz.pixelatedw.mineminenomi.init.ModChallenges;
import xyz.pixelatedw.mineminenomi.init.ModMobs;
import xyz.pixelatedw.mineminenomi.init.ModNPCGroups;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class Mr1Challenge extends Challenge {
   private static final String TITLE;
   public static final String OBJECTIVE;
   public static final ResourceLocation REWARD;
   public static final ChallengeCore<Mr1Challenge> INSTANCE;

   public Mr1Challenge(ChallengeCore<Mr1Challenge> core) {
      super(core);
   }

   public static Set<ChallengeArena.EnemySpawn> setEnemeySpawns(InProgressChallenge challenge, ChallengeArena.SpawnPosition[] spawns) {
      Set<ChallengeArena.EnemySpawn> set = new HashSet();
      Mr1Entity boss = new Mr1Entity(challenge);
      set.add(new ChallengeArena.EnemySpawn(boss, spawns[0]));
      return set;
   }

   static {
      TITLE = ModRegistry.registerName(ModRegistry.I18nCategory.CHALLENGE, "baroque_works.mr_1", "Mr. 1");
      OBJECTIVE = ModRegistry.registerName(ModRegistry.I18nCategory.CHALLENGE, "baroque_works.mr_1.objective", "Defeat Mr. 1");
      REWARD = ResourceLocation.fromNamespaceAndPath("mineminenomi", "rewards/mr_1");
      INSTANCE = (new ChallengeCore.Builder("mr_1", TITLE, OBJECTIVE, ModNPCGroups.BAROQUE_WORKS.getName(), Mr1Challenge::new)).setDifficulty(ChallengeDifficulty.STANDARD).setDifficultyStars(3).setRewardsFactor(2.0F).addArena(ArenaStyle.SIMPLE, AlabastaDesertSimpleArena.INSTANCE, AlabastaDesertSimpleArena::getChallengerSpawnPos, AlabastaDesertSimpleArena::getEnemySpawnPos).setEnemySpawns(Mr1Challenge::setEnemeySpawns).setTargetShowcase(ModMobs.MR1).setTimeLimit(10).setOrder(ModChallenges.Order.MR_1).setRewards(REWARD).build();
   }
}
