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
import xyz.pixelatedw.mineminenomi.challenges.arenas.CircusArena;
import xyz.pixelatedw.mineminenomi.entities.mobs.pirates.buggypirates.BuggyEntity;
import xyz.pixelatedw.mineminenomi.init.ModArmors;
import xyz.pixelatedw.mineminenomi.init.ModChallenges;
import xyz.pixelatedw.mineminenomi.init.ModMobs;
import xyz.pixelatedw.mineminenomi.init.ModNPCGroups;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class BuggyChallenge extends Challenge {
   private static final String TITLE;
   public static final String OBJECTIVE;
   public static final ResourceLocation REWARD;
   public static final ChallengeCore<BuggyChallenge> INSTANCE;

   public BuggyChallenge(ChallengeCore<BuggyChallenge> core) {
      super(core);
   }

   public static Set<ChallengeArena.EnemySpawn> setEnemeySpawns(InProgressChallenge challenge, ChallengeArena.SpawnPosition[] spawns) {
      Set<ChallengeArena.EnemySpawn> set = new HashSet();
      BuggyEntity boss = new BuggyEntity(challenge);
      set.add(new ChallengeArena.EnemySpawn(boss, spawns[0]));
      return set;
   }

   public static LivingEntity createShowcase(Level world) {
      BuggyEntity entity = (BuggyEntity)((EntityType)ModMobs.BUGGY.get()).m_20615_(world);
      ItemStack bicorneStack = ((Item)ModArmors.BICORNE.get()).m_7968_();
      bicorneStack.m_41698_("display").m_128405_("color", 13459968);
      entity.m_8061_(EquipmentSlot.HEAD, bicorneStack);
      return entity;
   }

   static {
      TITLE = ModRegistry.registerName(ModRegistry.I18nCategory.CHALLENGE, "buggy_pirates.buggy", "Buggy");
      OBJECTIVE = ModRegistry.registerName(ModRegistry.I18nCategory.CHALLENGE, "buggy_pirates.buggy.objective", "Defeat Buggy");
      REWARD = ResourceLocation.fromNamespaceAndPath("mineminenomi", "rewards/buggy");
      INSTANCE = (new ChallengeCore.Builder("buggy", TITLE, OBJECTIVE, ModNPCGroups.BUGGY_PIRATES.getName(), BuggyChallenge::new)).setDifficulty(ChallengeDifficulty.STANDARD).setDifficultyStars(1).setRewardsFactor(2.0F).addArena(ArenaStyle.SIMPLE, CircusArena.INSTANCE, CircusArena::getChallengerSpawnPos, CircusArena::getEnemySpawnPos).setEnemySpawns(BuggyChallenge::setEnemeySpawns).setTargetShowcase(BuggyChallenge::createShowcase).setTimeLimit(10).setOrder(ModChallenges.Order.BUGGY).setRewards(REWARD).build();
   }
}
