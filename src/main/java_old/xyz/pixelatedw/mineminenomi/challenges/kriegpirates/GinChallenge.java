package xyz.pixelatedw.mineminenomi.challenges.kriegpirates;

import java.util.HashSet;
import java.util.Set;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.api.challenges.ArenaStyle;
import xyz.pixelatedw.mineminenomi.api.challenges.Challenge;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeArena;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeDifficulty;
import xyz.pixelatedw.mineminenomi.api.challenges.InProgressChallenge;
import xyz.pixelatedw.mineminenomi.challenges.arenas.BaratieSimpleArena;
import xyz.pixelatedw.mineminenomi.entities.mobs.pirates.kriegpirates.GinEntity;
import xyz.pixelatedw.mineminenomi.init.ModChallenges;
import xyz.pixelatedw.mineminenomi.init.ModMobs;
import xyz.pixelatedw.mineminenomi.init.ModNPCGroups;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class GinChallenge extends Challenge {
   private static final String TITLE;
   public static final String OBJECTIVE;
   public static final ResourceLocation REWARD;
   public static final ChallengeCore<GinChallenge> INSTANCE;

   public GinChallenge(ChallengeCore<GinChallenge> core) {
      super(core);
   }

   public static Set<ChallengeArena.EnemySpawn> setEnemeySpawns(InProgressChallenge challenge, ChallengeArena.SpawnPosition[] spawns) {
      Set<ChallengeArena.EnemySpawn> set = new HashSet();
      GinEntity boss = new GinEntity(challenge);
      set.add(new ChallengeArena.EnemySpawn(boss, spawns[0]));
      return set;
   }

   static {
      TITLE = ModRegistry.registerName(ModRegistry.I18nCategory.CHALLENGE, "krieg_pirates.gin", "Gin");
      OBJECTIVE = ModRegistry.registerName(ModRegistry.I18nCategory.CHALLENGE, "krieg_pirates.gin.objective", "Defeat Gin");
      REWARD = ResourceLocation.fromNamespaceAndPath("mineminenomi", "rewards/gin");
      INSTANCE = (new ChallengeCore.Builder("gin", TITLE, OBJECTIVE, ModNPCGroups.KRIEG_PIRATES.getName(), GinChallenge::new)).setDifficulty(ChallengeDifficulty.STANDARD).setDifficultyStars(1).addArena(ArenaStyle.SIMPLE, BaratieSimpleArena.INSTANCE, BaratieSimpleArena::getChallengerSpawnPos, BaratieSimpleArena::getEnemySpawnPos).setEnemySpawns(GinChallenge::setEnemeySpawns).setTargetShowcase(ModMobs.GIN).setTimeLimit(10).setOrder(ModChallenges.Order.GIN).setRewards(REWARD).build();
   }
}
