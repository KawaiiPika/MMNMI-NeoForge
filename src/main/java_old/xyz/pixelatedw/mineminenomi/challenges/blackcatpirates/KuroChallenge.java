package xyz.pixelatedw.mineminenomi.challenges.blackcatpirates;

import java.util.HashSet;
import java.util.Set;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.api.challenges.ArenaStyle;
import xyz.pixelatedw.mineminenomi.api.challenges.Challenge;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeArena;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeDifficulty;
import xyz.pixelatedw.mineminenomi.api.challenges.InProgressChallenge;
import xyz.pixelatedw.mineminenomi.challenges.arenas.SyrupHillSimpleArena;
import xyz.pixelatedw.mineminenomi.entities.mobs.pirates.blackcatpirates.KuroEntity;
import xyz.pixelatedw.mineminenomi.init.ModChallenges;
import xyz.pixelatedw.mineminenomi.init.ModMobs;
import xyz.pixelatedw.mineminenomi.init.ModNPCGroups;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class KuroChallenge extends Challenge {
   private static final String TITLE;
   public static final String OBJECTIVE;
   public static final ResourceLocation REWARD;
   public static final ChallengeCore<KuroChallenge> INSTANCE;

   public KuroChallenge(ChallengeCore<KuroChallenge> core) {
      super(core);
   }

   public static Set<ChallengeArena.EnemySpawn> setEnemeySpawns(InProgressChallenge challenge, ChallengeArena.SpawnPosition[] spawns) {
      Set<ChallengeArena.EnemySpawn> set = new HashSet();
      KuroEntity boss = new KuroEntity(challenge);
      set.add(new ChallengeArena.EnemySpawn(boss, spawns[0]));
      return set;
   }

   static {
      TITLE = ModRegistry.registerName(ModRegistry.I18nCategory.CHALLENGE, "black_cat_pirates.kuro", "Kuro");
      OBJECTIVE = ModRegistry.registerName(ModRegistry.I18nCategory.CHALLENGE, "black_cat_pirates.kuro.objective", "Defeat Kuro");
      REWARD = ResourceLocation.fromNamespaceAndPath("mineminenomi", "rewards/kuro");
      INSTANCE = (new ChallengeCore.Builder("kuro", TITLE, OBJECTIVE, ModNPCGroups.BLACK_CAT_PIRATES.getName(), KuroChallenge::new)).setDifficulty(ChallengeDifficulty.STANDARD).setDifficultyStars(1).setRewardsFactor(2.0F).addArena(ArenaStyle.SIMPLE, SyrupHillSimpleArena.INSTANCE, SyrupHillSimpleArena::getChallengerSpawnPos, SyrupHillSimpleArena::getEnemySpawnPos).setEnemySpawns(KuroChallenge::setEnemeySpawns).setTargetShowcase(ModMobs.KURO).setTimeLimit(10).setOrder(ModChallenges.Order.KURO).setRewards(REWARD).build();
   }
}
