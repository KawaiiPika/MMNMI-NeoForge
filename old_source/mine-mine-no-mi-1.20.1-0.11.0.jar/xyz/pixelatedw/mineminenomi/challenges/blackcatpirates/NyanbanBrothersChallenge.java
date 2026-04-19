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
import xyz.pixelatedw.mineminenomi.entities.mobs.pirates.blackcatpirates.BuchiEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.pirates.blackcatpirates.ShamEntity;
import xyz.pixelatedw.mineminenomi.init.ModChallenges;
import xyz.pixelatedw.mineminenomi.init.ModMobs;
import xyz.pixelatedw.mineminenomi.init.ModNPCGroups;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class NyanbanBrothersChallenge extends Challenge {
   private static final String TITLE;
   public static final String OBJECTIVE;
   public static final ResourceLocation REWARD;
   public static final ChallengeCore<NyanbanBrothersChallenge> INSTANCE;

   public NyanbanBrothersChallenge(ChallengeCore<NyanbanBrothersChallenge> core) {
      super(core);
   }

   public static Set<ChallengeArena.EnemySpawn> setEnemeySpawns(InProgressChallenge challenge, ChallengeArena.SpawnPosition[] spawns) {
      Set<ChallengeArena.EnemySpawn> set = new HashSet();
      ShamEntity sham = new ShamEntity(challenge);
      set.add(new ChallengeArena.EnemySpawn(sham, spawns[0]));
      BuchiEntity buchi = new BuchiEntity(challenge);
      set.add(new ChallengeArena.EnemySpawn(buchi, spawns[1]));
      return set;
   }

   static {
      TITLE = ModRegistry.registerName(ModRegistry.I18nCategory.CHALLENGE, "black_cat_pirates.nyanban_brothers", "Nyanban Brothers");
      OBJECTIVE = ModRegistry.registerName(ModRegistry.I18nCategory.CHALLENGE, "black_cat_pirates.nyanban_brothers.objective", "Defeat Sham and Buchi");
      REWARD = ResourceLocation.fromNamespaceAndPath("mineminenomi", "rewards/nyanban_brothers");
      INSTANCE = (new ChallengeCore.Builder("nyanban_brothers", TITLE, OBJECTIVE, ModNPCGroups.BLACK_CAT_PIRATES.getName(), NyanbanBrothersChallenge::new)).setDifficulty(ChallengeDifficulty.STANDARD).setDifficultyStars(1).addArena(ArenaStyle.SIMPLE, SyrupHillSimpleArena.INSTANCE, SyrupHillSimpleArena::getChallengerSpawnPos, SyrupHillSimpleArena::getEnemySpawnPos).setEnemySpawns(NyanbanBrothersChallenge::setEnemeySpawns).setTargetShowcase(ModMobs.SHAM, ModMobs.BUCHI).setTimeLimit(10).setOrder(ModChallenges.Order.SHAM_BUCHI).setRewards(REWARD).build();
   }
}
