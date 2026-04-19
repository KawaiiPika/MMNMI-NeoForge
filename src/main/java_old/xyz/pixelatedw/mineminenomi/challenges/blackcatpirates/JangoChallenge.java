package xyz.pixelatedw.mineminenomi.challenges.blackcatpirates;

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
import xyz.pixelatedw.mineminenomi.challenges.arenas.SyrupHillSimpleArena;
import xyz.pixelatedw.mineminenomi.entities.mobs.pirates.blackcatpirates.JangoEntity;
import xyz.pixelatedw.mineminenomi.init.ModArmors;
import xyz.pixelatedw.mineminenomi.init.ModChallenges;
import xyz.pixelatedw.mineminenomi.init.ModMobs;
import xyz.pixelatedw.mineminenomi.init.ModNPCGroups;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class JangoChallenge extends Challenge {
   private static final String TITLE;
   public static final String OBJECTIVE;
   public static final ResourceLocation REWARD;
   public static final ChallengeCore<JangoChallenge> INSTANCE;

   public JangoChallenge(ChallengeCore<JangoChallenge> core) {
      super(core);
   }

   public static Set<ChallengeArena.EnemySpawn> setEnemeySpawns(InProgressChallenge challenge, ChallengeArena.SpawnPosition[] spawns) {
      Set<ChallengeArena.EnemySpawn> set = new HashSet();
      JangoEntity boss = new JangoEntity(challenge);
      set.add(new ChallengeArena.EnemySpawn(boss, spawns[0]));
      return set;
   }

   public static LivingEntity createJangoShowcase(Level world) {
      JangoEntity entity = (JangoEntity)((EntityType)ModMobs.JANGO.get()).m_20615_(world);
      ItemStack headStack = ((Item)ModArmors.WIDE_BRIM_HAT.get()).m_7968_();
      headStack.m_41698_("display").m_128405_("color", 3364244);
      entity.m_8061_(EquipmentSlot.HEAD, headStack);
      return entity;
   }

   static {
      TITLE = ModRegistry.registerName(ModRegistry.I18nCategory.CHALLENGE, "black_cat_pirates.jango", "Jango");
      OBJECTIVE = ModRegistry.registerName(ModRegistry.I18nCategory.CHALLENGE, "black_cat_pirates.jango.objective", "Defeat Jango");
      REWARD = ResourceLocation.fromNamespaceAndPath("mineminenomi", "rewards/jango");
      INSTANCE = (new ChallengeCore.Builder("jango", TITLE, OBJECTIVE, ModNPCGroups.BLACK_CAT_PIRATES.getName(), JangoChallenge::new)).setDifficulty(ChallengeDifficulty.STANDARD).setDifficultyStars(1).addArena(ArenaStyle.SIMPLE, SyrupHillSimpleArena.INSTANCE, SyrupHillSimpleArena::getChallengerSpawnPos, SyrupHillSimpleArena::getEnemySpawnPos).setEnemySpawns(JangoChallenge::setEnemeySpawns).setTargetShowcase(JangoChallenge::createJangoShowcase).setTimeLimit(10).setOrder(ModChallenges.Order.JANGO).setRewards(REWARD).build();
   }
}
