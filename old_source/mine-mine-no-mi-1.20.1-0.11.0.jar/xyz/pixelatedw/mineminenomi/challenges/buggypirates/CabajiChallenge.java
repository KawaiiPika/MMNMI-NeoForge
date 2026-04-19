package xyz.pixelatedw.mineminenomi.challenges.buggypirates;

import java.util.HashSet;
import java.util.Set;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.challenges.ArenaStyle;
import xyz.pixelatedw.mineminenomi.api.challenges.Challenge;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeArena;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeDifficulty;
import xyz.pixelatedw.mineminenomi.api.challenges.InProgressChallenge;
import xyz.pixelatedw.mineminenomi.challenges.arenas.CircusArena;
import xyz.pixelatedw.mineminenomi.entities.mobs.pirates.buggypirates.CabajiEntity;
import xyz.pixelatedw.mineminenomi.init.ModArmors;
import xyz.pixelatedw.mineminenomi.init.ModChallenges;
import xyz.pixelatedw.mineminenomi.init.ModMobs;
import xyz.pixelatedw.mineminenomi.init.ModNPCGroups;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class CabajiChallenge extends Challenge {
   private static final String TITLE;
   public static final String OBJECTIVE;
   public static final ResourceLocation REWARD;
   public static final ChallengeCore<CabajiChallenge> INSTANCE;

   public CabajiChallenge(ChallengeCore<CabajiChallenge> core) {
      super(core);
   }

   public static Set<ChallengeArena.EnemySpawn> setEnemeySpawns(InProgressChallenge challenge, ChallengeArena.SpawnPosition[] spawns) {
      Set<ChallengeArena.EnemySpawn> set = new HashSet();
      CabajiEntity boss = new CabajiEntity(challenge);
      set.add(new ChallengeArena.EnemySpawn(boss, spawns[0]));
      return set;
   }

   public static LivingEntity createShowcase(Level world) {
      CabajiEntity entity = (CabajiEntity)((EntityType)ModMobs.CABAJI.get()).m_20615_(world);
      entity.m_8061_(EquipmentSlot.HEAD, ((Item)ModArmors.CABAJI_SCARF.get()).m_7968_());
      return entity;
   }

   static {
      TITLE = ModRegistry.registerName(ModRegistry.I18nCategory.CHALLENGE, "buggy_pirates.cabaji", "Cabaji");
      OBJECTIVE = ModRegistry.registerName(ModRegistry.I18nCategory.CHALLENGE, "buggy_pirates.cabaji.objective", "Defeat Cabaji");
      REWARD = ResourceLocation.fromNamespaceAndPath("mineminenomi", "rewards/cabaji");
      INSTANCE = (new ChallengeCore.Builder("cabaji", TITLE, OBJECTIVE, ModNPCGroups.BUGGY_PIRATES.getName(), CabajiChallenge::new)).setDifficulty(ChallengeDifficulty.STANDARD).setDifficultyStars(1).addArena(ArenaStyle.SIMPLE, CircusArena.INSTANCE, CircusArena::getChallengerSpawnPos, CircusArena::getEnemySpawnPos).setEnemySpawns(CabajiChallenge::setEnemeySpawns).setTargetShowcase(CabajiChallenge::createShowcase).setTimeLimit(10).setOrder(ModChallenges.Order.CABAJI).setRewards(REWARD).build();
   }
}
