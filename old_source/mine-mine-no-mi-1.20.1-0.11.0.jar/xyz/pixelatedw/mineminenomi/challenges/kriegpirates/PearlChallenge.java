package xyz.pixelatedw.mineminenomi.challenges.kriegpirates;

import java.util.HashSet;
import java.util.Set;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.challenges.ArenaStyle;
import xyz.pixelatedw.mineminenomi.api.challenges.Challenge;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeArena;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeDifficulty;
import xyz.pixelatedw.mineminenomi.api.challenges.InProgressChallenge;
import xyz.pixelatedw.mineminenomi.challenges.arenas.BaratieSimpleArena;
import xyz.pixelatedw.mineminenomi.entities.mobs.pirates.kriegpirates.PearlEntity;
import xyz.pixelatedw.mineminenomi.init.ModArmors;
import xyz.pixelatedw.mineminenomi.init.ModChallenges;
import xyz.pixelatedw.mineminenomi.init.ModMobs;
import xyz.pixelatedw.mineminenomi.init.ModNPCGroups;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class PearlChallenge extends Challenge {
   private static final String TITLE;
   public static final String OBJECTIVE;
   public static final ResourceLocation REWARD;
   public static final ChallengeCore<PearlChallenge> INSTANCE;

   public PearlChallenge(ChallengeCore<PearlChallenge> core) {
      super(core);
   }

   public static Set<ChallengeArena.EnemySpawn> setEnemeySpawns(InProgressChallenge challenge, ChallengeArena.SpawnPosition[] spawns) {
      Set<ChallengeArena.EnemySpawn> set = new HashSet();
      PearlEntity boss = new PearlEntity(challenge);
      set.add(new ChallengeArena.EnemySpawn(boss, spawns[0]));
      return set;
   }

   public static LivingEntity createPearlShowcase(Level world) {
      LivingEntity entity = (LivingEntity)((EntityType)ModMobs.PEARL.get()).m_20615_(world);
      entity.m_8061_(EquipmentSlot.HEAD, new ItemStack((ItemLike)ModArmors.PEARL_HAT.get()));
      entity.m_8061_(EquipmentSlot.CHEST, new ItemStack((ItemLike)ModArmors.PEARL_ARMOR.get()));
      entity.m_8061_(EquipmentSlot.LEGS, new ItemStack((ItemLike)ModArmors.PEARL_LEGS.get()));
      return entity;
   }

   static {
      TITLE = ModRegistry.registerName(ModRegistry.I18nCategory.CHALLENGE, "krieg_pirates.pearl", "Pearl");
      OBJECTIVE = ModRegistry.registerName(ModRegistry.I18nCategory.CHALLENGE, "krieg_pirates.pearl.objective", "Defeat Pearl");
      REWARD = ResourceLocation.fromNamespaceAndPath("mineminenomi", "rewards/pearl");
      INSTANCE = (new ChallengeCore.Builder("pearl", TITLE, OBJECTIVE, ModNPCGroups.KRIEG_PIRATES.getName(), PearlChallenge::new)).setDifficulty(ChallengeDifficulty.STANDARD).setDifficultyStars(1).addArena(ArenaStyle.SIMPLE, BaratieSimpleArena.INSTANCE, BaratieSimpleArena::getChallengerSpawnPos, BaratieSimpleArena::getEnemySpawnPos).setEnemySpawns(PearlChallenge::setEnemeySpawns).setTargetShowcase(PearlChallenge::createPearlShowcase).setTimeLimit(10).setOrder(ModChallenges.Order.PEARL).setRewards(REWARD).build();
   }
}
