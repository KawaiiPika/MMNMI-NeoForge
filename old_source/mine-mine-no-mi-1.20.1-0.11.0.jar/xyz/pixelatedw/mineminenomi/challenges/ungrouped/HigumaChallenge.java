package xyz.pixelatedw.mineminenomi.challenges.ungrouped;

import java.util.HashSet;
import java.util.Set;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.api.challenges.ArenaStyle;
import xyz.pixelatedw.mineminenomi.api.challenges.Challenge;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeArena;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeDifficulty;
import xyz.pixelatedw.mineminenomi.api.challenges.InProgressChallenge;
import xyz.pixelatedw.mineminenomi.challenges.arenas.HigumaSimpleArena;
import xyz.pixelatedw.mineminenomi.entities.mobs.bandits.HigumaEntity;
import xyz.pixelatedw.mineminenomi.init.ModChallenges;
import xyz.pixelatedw.mineminenomi.init.ModMobs;
import xyz.pixelatedw.mineminenomi.init.ModNPCGroups;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class HigumaChallenge extends Challenge {
   private static final String TITLE;
   public static final String OBJECTIVE;
   public static final ResourceLocation REWARD;
   public static final ChallengeCore<HigumaChallenge> INSTANCE;

   public HigumaChallenge(ChallengeCore<HigumaChallenge> core) {
      super(core);
   }

   public static Set<ChallengeArena.EnemySpawn> setEnemeySpawns(InProgressChallenge challenge, ChallengeArena.SpawnPosition[] spawns) {
      Set<ChallengeArena.EnemySpawn> set = new HashSet();
      HigumaEntity boss = new HigumaEntity(challenge);
      set.add(new ChallengeArena.EnemySpawn(boss, spawns[0]));
      return set;
   }

   static {
      TITLE = ModRegistry.registerName(ModRegistry.I18nCategory.CHALLENGE, "higuma", "Higuma");
      OBJECTIVE = ModRegistry.registerName(ModRegistry.I18nCategory.CHALLENGE, "higuma.objective", "Defeat Higuma");
      REWARD = ResourceLocation.fromNamespaceAndPath("mineminenomi", "rewards/higuma");
      INSTANCE = (new ChallengeCore.Builder("higuma", TITLE, OBJECTIVE, ModNPCGroups.UNGROUPED, HigumaChallenge::new)).setDifficulty(ChallengeDifficulty.STANDARD).setDifficultyStars(1).addArena(ArenaStyle.SIMPLE, HigumaSimpleArena.INSTANCE, HigumaSimpleArena::getChallengerSpawnPos, HigumaSimpleArena::getEnemySpawnPos).setEnemySpawns(HigumaChallenge::setEnemeySpawns).setTargetShowcase(ModMobs.HIGUMA).setTimeLimit(10).setOrder(ModChallenges.Order.HIGUMA).setRewards(REWARD).build();
   }
}
