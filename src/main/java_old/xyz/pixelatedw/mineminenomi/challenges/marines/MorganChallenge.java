package xyz.pixelatedw.mineminenomi.challenges.marines;

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
import xyz.pixelatedw.mineminenomi.challenges.arenas.MarineSmallBaseSimpleArena;
import xyz.pixelatedw.mineminenomi.entities.mobs.marines.MorganEntity;
import xyz.pixelatedw.mineminenomi.init.ModArmors;
import xyz.pixelatedw.mineminenomi.init.ModChallenges;
import xyz.pixelatedw.mineminenomi.init.ModMobs;
import xyz.pixelatedw.mineminenomi.init.ModNPCGroups;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;
import xyz.pixelatedw.mineminenomi.items.weapons.ModSwordItem;

public class MorganChallenge extends Challenge {
   private static final String TITLE;
   public static final String OBJECTIVE;
   public static final ResourceLocation REWARD;
   public static final ChallengeCore<MorganChallenge> INSTANCE;

   public MorganChallenge(ChallengeCore<MorganChallenge> core) {
      super(core);
   }

   public static Set<ChallengeArena.EnemySpawn> setEnemeySpawns(InProgressChallenge challenge, ChallengeArena.SpawnPosition[] spawns) {
      Set<ChallengeArena.EnemySpawn> set = new HashSet();
      MorganEntity boss = new MorganEntity(challenge);
      set.add(new ChallengeArena.EnemySpawn(boss, spawns[0]));
      return set;
   }

   public static LivingEntity createShowcase(Level world) {
      MorganEntity entity = (MorganEntity)((EntityType)ModMobs.MORGAN.get()).m_20615_(world);
      entity.m_8061_(EquipmentSlot.HEAD, ((Item)ModArmors.IRON_JAW.get()).m_7968_());
      entity.m_8061_(EquipmentSlot.MAINHAND, ((ModSwordItem)ModWeapons.AXE_HAND.get()).m_7968_());
      return entity;
   }

   static {
      TITLE = ModRegistry.registerName(ModRegistry.I18nCategory.CHALLENGE, "marines.morgan", "Morgan");
      OBJECTIVE = ModRegistry.registerName(ModRegistry.I18nCategory.CHALLENGE, "marines.morgan.objective", "Defeat Morgan");
      REWARD = ResourceLocation.fromNamespaceAndPath("mineminenomi", "rewards/morgan");
      INSTANCE = (new ChallengeCore.Builder("morgan", TITLE, OBJECTIVE, ModNPCGroups.MARINES, MorganChallenge::new)).setDifficulty(ChallengeDifficulty.STANDARD).setDifficultyStars(1).addArena(ArenaStyle.SIMPLE, MarineSmallBaseSimpleArena.INSTANCE, MarineSmallBaseSimpleArena::getChallengerSpawnPos, MarineSmallBaseSimpleArena::getEnemySpawnPos).setEnemySpawns(MorganChallenge::setEnemeySpawns).setTargetShowcase(MorganChallenge::createShowcase).setTimeLimit(10).setOrder(ModChallenges.Order.MORGAN).setRewards(REWARD).build();
   }
}
