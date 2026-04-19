package xyz.pixelatedw.mineminenomi.challenges.baroqueworks;

import java.util.HashSet;
import java.util.Set;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.challenges.ArenaStyle;
import xyz.pixelatedw.mineminenomi.api.challenges.Challenge;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeArena;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeDifficulty;
import xyz.pixelatedw.mineminenomi.api.challenges.InProgressChallenge;
import xyz.pixelatedw.mineminenomi.api.items.IMultiChannelColorItem;
import xyz.pixelatedw.mineminenomi.challenges.arenas.AlabastaDesertSimpleArena;
import xyz.pixelatedw.mineminenomi.entities.mobs.pirates.baroqueworks.Mr0Entity;
import xyz.pixelatedw.mineminenomi.init.ModArmors;
import xyz.pixelatedw.mineminenomi.init.ModChallenges;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.ModMobs;
import xyz.pixelatedw.mineminenomi.init.ModNPCGroups;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;
import xyz.pixelatedw.mineminenomi.items.weapons.ModSwordItem;

public class Mr0Challenge extends Challenge {
   private static final String TITLE;
   public static final String OBJECTIVE;
   public static final ResourceLocation REWARD;
   public static final ChallengeCore<Mr0Challenge> INSTANCE;

   public Mr0Challenge(ChallengeCore<Mr0Challenge> core) {
      super(core);
   }

   public static Set<ChallengeArena.EnemySpawn> setEnemeySpawns(InProgressChallenge challenge, ChallengeArena.SpawnPosition[] spawns) {
      Set<ChallengeArena.EnemySpawn> set = new HashSet();
      Mr0Entity boss = new Mr0Entity(challenge);
      set.add(new ChallengeArena.EnemySpawn(boss, spawns[0]));
      return set;
   }

   public static LivingEntity createMr0Showcase(Level world) {
      Mr0Entity entity = (Mr0Entity)((EntityType)ModMobs.MR0.get()).m_20615_(world);
      entity.m_21559_(false);
      ItemStack capeStack = new ItemStack((ItemLike)ModArmors.FLUFFY_CAPE.get());
      IMultiChannelColorItem.dyeArmor(capeStack, 0, 2040097);
      entity.m_8061_(EquipmentSlot.CHEST, capeStack);
      entity.m_8061_(EquipmentSlot.OFFHAND, ((ModSwordItem)ModWeapons.HOOK.get()).m_7968_());
      entity.m_8061_(EquipmentSlot.HEAD, ((Item)ModItems.CIGAR.get()).m_7968_());
      return entity;
   }

   static {
      TITLE = ModRegistry.registerName(ModRegistry.I18nCategory.CHALLENGE, "baroque_works.mr_0", "Mr. 0");
      OBJECTIVE = ModRegistry.registerName(ModRegistry.I18nCategory.CHALLENGE, "baroque_works.mr_0.objective", "Defeat Mr. 0");
      REWARD = ResourceLocation.fromNamespaceAndPath("mineminenomi", "rewards/mr_0");
      INSTANCE = (new ChallengeCore.Builder("mr_0", TITLE, OBJECTIVE, ModNPCGroups.BAROQUE_WORKS.getName(), Mr0Challenge::new)).setDifficulty(ChallengeDifficulty.STANDARD).setDifficultyStars(3).setRewardsFactor(3.0F).addArena(ArenaStyle.SIMPLE, AlabastaDesertSimpleArena.INSTANCE, AlabastaDesertSimpleArena::getChallengerSpawnPos, AlabastaDesertSimpleArena::getEnemySpawnPos).setEnemySpawns(Mr0Challenge::setEnemeySpawns).setTargetShowcase(Mr0Challenge::createMr0Showcase).setTimeLimit(10).setOrder(ModChallenges.Order.MR_0).setRewards(REWARD).build();
   }
}
