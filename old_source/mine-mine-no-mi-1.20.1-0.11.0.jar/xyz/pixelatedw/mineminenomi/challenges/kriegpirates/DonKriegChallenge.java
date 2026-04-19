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
import xyz.pixelatedw.mineminenomi.entities.mobs.pirates.kriegpirates.DonKriegEntity;
import xyz.pixelatedw.mineminenomi.init.ModArmors;
import xyz.pixelatedw.mineminenomi.init.ModChallenges;
import xyz.pixelatedw.mineminenomi.init.ModMobs;
import xyz.pixelatedw.mineminenomi.init.ModNPCGroups;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class DonKriegChallenge extends Challenge {
   private static final String TITLE;
   public static final String OBJECTIVE;
   public static final ResourceLocation REWARD;
   public static final ChallengeCore<DonKriegChallenge> INSTANCE;

   public DonKriegChallenge(ChallengeCore<DonKriegChallenge> core) {
      super(core);
   }

   public static Set<ChallengeArena.EnemySpawn> setEnemeySpawns(InProgressChallenge challenge, ChallengeArena.SpawnPosition[] spawns) {
      Set<ChallengeArena.EnemySpawn> set = new HashSet();
      DonKriegEntity boss = new DonKriegEntity(challenge);
      set.add(new ChallengeArena.EnemySpawn(boss, spawns[0]));
      return set;
   }

   public static LivingEntity createKriegShowcase(Level world) {
      LivingEntity entity = (LivingEntity)((EntityType)ModMobs.DON_KRIEG.get()).m_20615_(world);
      entity.m_8061_(EquipmentSlot.CHEST, new ItemStack((ItemLike)ModArmors.WOOTZ_STEEL_ARMOR.get()));
      return entity;
   }

   static {
      TITLE = ModRegistry.registerName(ModRegistry.I18nCategory.CHALLENGE, "krieg_pirates.don_krieg", "Don Krieg");
      OBJECTIVE = ModRegistry.registerName(ModRegistry.I18nCategory.CHALLENGE, "krieg_pirates.don_krieg.objective", "Defeat Don Krieg");
      REWARD = ResourceLocation.fromNamespaceAndPath("mineminenomi", "rewards/don_krieg");
      INSTANCE = (new ChallengeCore.Builder("don_krieg", TITLE, OBJECTIVE, ModNPCGroups.KRIEG_PIRATES.getName(), DonKriegChallenge::new)).setDifficulty(ChallengeDifficulty.STANDARD).setDifficultyStars(1).setRewardsFactor(2.0F).addArena(ArenaStyle.SIMPLE, BaratieSimpleArena.INSTANCE, BaratieSimpleArena::getChallengerSpawnPos, BaratieSimpleArena::getEnemySpawnPos).setEnemySpawns(DonKriegChallenge::setEnemeySpawns).setTargetShowcase(DonKriegChallenge::createKriegShowcase).setTimeLimit(10).setOrder(ModChallenges.Order.DON_KRIEG).setRewards(REWARD).build();
   }
}
