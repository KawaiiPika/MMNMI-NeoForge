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
import xyz.pixelatedw.mineminenomi.entities.mobs.pirates.baroqueworks.MissValentineEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.pirates.baroqueworks.Mr5Entity;
import xyz.pixelatedw.mineminenomi.init.ModChallenges;
import xyz.pixelatedw.mineminenomi.init.ModMobs;
import xyz.pixelatedw.mineminenomi.init.ModNPCGroups;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class Mr5MissValentineChallenge extends Challenge {
   private static final String TITLE;
   public static final String OBJECTIVE;
   public static final ResourceLocation REWARD;
   public static final ChallengeCore<Mr5MissValentineChallenge> INSTANCE;

   public Mr5MissValentineChallenge(ChallengeCore<Mr5MissValentineChallenge> core) {
      super(core);
   }

   public static Set<ChallengeArena.EnemySpawn> setEnemeySpawns(InProgressChallenge challenge, ChallengeArena.SpawnPosition[] spawns) {
      Set<ChallengeArena.EnemySpawn> set = new HashSet();
      Mr5Entity mr5Boss = new Mr5Entity(challenge);
      set.add(new ChallengeArena.EnemySpawn(mr5Boss, spawns[0]));
      MissValentineEntity missValBoss = new MissValentineEntity(challenge);
      set.add(new ChallengeArena.EnemySpawn(missValBoss, spawns[1]));
      return set;
   }

   static {
      TITLE = ModRegistry.registerName(ModRegistry.I18nCategory.CHALLENGE, "baroque_works.mr_5_and_miss_valentine", "Mr. 5 & Miss Valentine");
      OBJECTIVE = ModRegistry.registerName(ModRegistry.I18nCategory.CHALLENGE, "baroque_works.mr_5_and_miss_valentine.objective", "Defeat both Mr. 5 and Miss Valentine");
      REWARD = ResourceLocation.fromNamespaceAndPath("mineminenomi", "rewards/mr_5_and_miss_valentine");
      INSTANCE = (new ChallengeCore.Builder("mr_5_and_miss_valentine", TITLE, OBJECTIVE, ModNPCGroups.BAROQUE_WORKS.getName(), Mr5MissValentineChallenge::new)).setDifficulty(ChallengeDifficulty.STANDARD).setDifficultyStars(2).addArena(ArenaStyle.SIMPLE, JungleClearingSimpleArena.INSTANCE, JungleClearingSimpleArena::getChallengerSpawnPos, JungleClearingSimpleArena::getEnemySpawnPos).setEnemySpawns(Mr5MissValentineChallenge::setEnemeySpawns).setTargetShowcase(ModMobs.MR5, ModMobs.MISS_VALENTINE).setTimeLimit(10).setOrder(ModChallenges.Order.MR_5_MISS_VALENTINE).setRewards(REWARD).build();
   }
}
