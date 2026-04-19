package xyz.pixelatedw.mineminenomi.challenges.buggypirates;

import java.util.HashSet;
import java.util.Set;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.challenges.ArenaStyle;
import xyz.pixelatedw.mineminenomi.api.challenges.Challenge;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeArena;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeDifficulty;
import xyz.pixelatedw.mineminenomi.api.challenges.InProgressChallenge;
import xyz.pixelatedw.mineminenomi.challenges.arenas.HigumaSimpleArena;
import xyz.pixelatedw.mineminenomi.entities.mobs.pirates.buggypirates.AlvidaEntity;
import xyz.pixelatedw.mineminenomi.init.ModArmors;
import xyz.pixelatedw.mineminenomi.init.ModChallenges;
import xyz.pixelatedw.mineminenomi.init.ModMobs;
import xyz.pixelatedw.mineminenomi.init.ModNPCGroups;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class AlvidaHardChallenge extends Challenge {
   private static final String TITLE;
   public static final ResourceLocation REWARD;
   public static final ChallengeCore<AlvidaHardChallenge> INSTANCE;

   public AlvidaHardChallenge(ChallengeCore<AlvidaHardChallenge> core) {
      super(core);
   }

   public static Set<ChallengeArena.EnemySpawn> setEnemeySpawns(InProgressChallenge challenge, ChallengeArena.SpawnPosition[] spawns) {
      Set<ChallengeArena.EnemySpawn> set = new HashSet();
      AlvidaEntity boss = new AlvidaEntity((EntityType)ModMobs.ALVIDA_SLIM.get(), challenge);
      set.add(new ChallengeArena.EnemySpawn(boss, spawns[0]));
      return set;
   }

   public static LivingEntity createShowcase(Level world) {
      AlvidaEntity entity = (AlvidaEntity)((EntityType)ModMobs.ALVIDA_SLIM.get()).m_20615_(world);
      ItemStack plumeHeadStack = ((Item)ModArmors.PLUME_HAT.get()).m_7968_();
      plumeHeadStack.m_41698_("display").m_128405_("color", 12788538);
      entity.m_8061_(EquipmentSlot.HEAD, plumeHeadStack);
      return entity;
   }

   static {
      TITLE = ModRegistry.registerName(ModRegistry.I18nCategory.CHALLENGE, "buggy_pirates.alvida_hard", "Alvida (Hard)");
      REWARD = ResourceLocation.fromNamespaceAndPath("mineminenomi", "rewards/alvida_hard");
      INSTANCE = (new ChallengeCore.Builder("alvida_hard", TITLE, AlvidaChallenge.OBJECTIVE, ModNPCGroups.BUGGY_PIRATES.getName(), AlvidaHardChallenge::new)).setDifficulty(ChallengeDifficulty.HARD).setDifficultyStars(1).addArena(ArenaStyle.SIMPLE, HigumaSimpleArena.INSTANCE, HigumaSimpleArena::getChallengerSpawnPos, HigumaSimpleArena::getEnemySpawnPos).setEnemySpawns(AlvidaHardChallenge::setEnemeySpawns).setTargetShowcase(AlvidaHardChallenge::createShowcase).setTimeLimit(10).setOrder(ModChallenges.Order.ALVIDA).setRewards(REWARD).build();
   }
}
