package xyz.pixelatedw.mineminenomi.challenges.arlongpirates;

import java.util.HashSet;
import java.util.Set;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.api.challenges.ArenaStyle;
import xyz.pixelatedw.mineminenomi.api.challenges.Challenge;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeArena;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeDifficulty;
import xyz.pixelatedw.mineminenomi.api.challenges.InProgressChallenge;
import xyz.pixelatedw.mineminenomi.challenges.arenas.ArlongParkSimpleArena;
import xyz.pixelatedw.mineminenomi.entities.mobs.pirates.arlongpirates.KuroobiEntity;
import xyz.pixelatedw.mineminenomi.init.ModChallenges;
import xyz.pixelatedw.mineminenomi.init.ModMobs;
import xyz.pixelatedw.mineminenomi.init.ModNPCGroups;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class KuroobiChallenge extends Challenge {
   private static final String TITLE;
   public static final String OBJECTIVE;
   public static final ResourceLocation REWARD;
   public static final ChallengeCore<KuroobiChallenge> INSTANCE;

   public KuroobiChallenge(ChallengeCore<KuroobiChallenge> core) {
      super(core);
   }

   public static Set<ChallengeArena.EnemySpawn> setEnemeySpawns(InProgressChallenge challenge, ChallengeArena.SpawnPosition[] spawns) {
      Set<ChallengeArena.EnemySpawn> set = new HashSet();
      KuroobiEntity boss = new KuroobiEntity(challenge);
      set.add(new ChallengeArena.EnemySpawn(boss, spawns[0]));
      return set;
   }

   static {
      TITLE = ModRegistry.registerName(ModRegistry.I18nCategory.CHALLENGE, "kuroobi", "Kuroobi");
      OBJECTIVE = ModRegistry.registerName(ModRegistry.I18nCategory.CHALLENGE, "kuroobi.objective", "Defeat Kuroobi");
      REWARD = ResourceLocation.fromNamespaceAndPath("mineminenomi", "rewards/kuroobi");
      INSTANCE = (new ChallengeCore.Builder("kuroobi", TITLE, OBJECTIVE, ModNPCGroups.ARLONG_PIRATES.getName(), KuroobiChallenge::new)).setDifficulty(ChallengeDifficulty.STANDARD).setDifficultyStars(1).addArena(ArenaStyle.SIMPLE, ArlongParkSimpleArena.INSTANCE, ArlongParkSimpleArena::getChallengerSpawnPos, ArlongParkSimpleArena::getEnemySpawnPos).setEnemySpawns(KuroobiChallenge::setEnemeySpawns).setTargetShowcase(ModMobs.KUROOBI).setTimeLimit(10).setOrder(ModChallenges.Order.KUROOBI).setRewards(REWARD).build();
   }
}
