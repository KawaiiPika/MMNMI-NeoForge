package xyz.pixelatedw.mineminenomi.entities.mobs;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.abilities.blackleg.AntiMannerKickCourseAbility;
import xyz.pixelatedw.mineminenomi.abilities.blackleg.BlackLegPassiveBonusesAbility;
import xyz.pixelatedw.mineminenomi.abilities.blackleg.ConcasseAbility;
import xyz.pixelatedw.mineminenomi.abilities.blackleg.ExtraHachisAbility;
import xyz.pixelatedw.mineminenomi.abilities.blackleg.PartyTableKickCourseAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.BrawlerPassiveBonusesAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.ChargedPunchAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.GenkotsuMeteorAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.HakaiHoAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.JishinHoAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.PunchRushAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.SpinningBrawlAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.SuplexAbility;
import xyz.pixelatedw.mineminenomi.abilities.fishmankarate.FishmanPassiveBonusesAbility;
import xyz.pixelatedw.mineminenomi.abilities.fishmankarate.KachiageHaisokuAbility;
import xyz.pixelatedw.mineminenomi.abilities.fishmankarate.KarakusagawaraSeikenAbility;
import xyz.pixelatedw.mineminenomi.abilities.fishmankarate.MizuOsuAbility;
import xyz.pixelatedw.mineminenomi.abilities.fishmankarate.MizuShuryudanAbility;
import xyz.pixelatedw.mineminenomi.abilities.fishmankarate.PackOfSharksAbility;
import xyz.pixelatedw.mineminenomi.abilities.fishmankarate.SamehadaShoteiAbility;
import xyz.pixelatedw.mineminenomi.abilities.fishmankarate.UchimizuAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.KenbunshokuHakiFutureSightAbility;
import xyz.pixelatedw.mineminenomi.abilities.ittoryu.HiryuKaenAbility;
import xyz.pixelatedw.mineminenomi.abilities.ittoryu.SanjurokuPoundHoAbility;
import xyz.pixelatedw.mineminenomi.abilities.ittoryu.ShiShishiSonsonAbility;
import xyz.pixelatedw.mineminenomi.abilities.ittoryu.YakkodoriAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.GeppoAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.KamieAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.RankyakuAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.ShiganAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.SoruAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.TekkaiAbility;
import xyz.pixelatedw.mineminenomi.abilities.santoryu.OTatsumakiAbility;
import xyz.pixelatedw.mineminenomi.abilities.sniper.KaenBoshiAbility;
import xyz.pixelatedw.mineminenomi.abilities.sniper.KemuriBoshiAbility;
import xyz.pixelatedw.mineminenomi.abilities.sniper.NemuriBoshiAbility;
import xyz.pixelatedw.mineminenomi.abilities.sniper.RenpatsuNamariBoshiAbility;
import xyz.pixelatedw.mineminenomi.abilities.sniper.SakuretsuSabotenBoshiAbility;
import xyz.pixelatedw.mineminenomi.abilities.sniper.TetsuBoshiAbility;
import xyz.pixelatedw.mineminenomi.abilities.sniper.TokuyoAburaBoshiAbility;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.entities.IMobDetails;
import xyz.pixelatedw.mineminenomi.api.entities.ServerOPBossEvent;
import xyz.pixelatedw.mineminenomi.api.entities.ai.NPCPhaseManager;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.helpers.AttributeHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.api.items.IMultiChannelColorItem;
import xyz.pixelatedw.mineminenomi.api.poi.INotoriousTarget;
import xyz.pixelatedw.mineminenomi.api.poi.TrackedNPC;
import xyz.pixelatedw.mineminenomi.api.util.WeightedList;
import xyz.pixelatedw.mineminenomi.config.AbilitiesConfig;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.DashDodgeProjectilesGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.DashDodgeTargetGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.GapCloserGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.ImprovedMeleeAttackGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.JumpOutOfHoleGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.PersonalSpaceTargetGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.SprintTowardsTargetGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.AlwaysActiveAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.CloseMeleeAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.DashAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.GrabAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.HakiAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.JumpAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ProjectileAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ReactiveGuardAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ReactiveRetreatAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.RepeaterAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.init.ModArmors;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModFactions;
import xyz.pixelatedw.mineminenomi.init.ModFightingStyles;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.ModRaces;

public class NotoriousEntity extends OPEntity implements INotoriousTarget {
   private static final List<IMobDetails<NotoriousEntity>> STYLES = Lists.newArrayList(new IMobDetails[]{NotoriousEntity::initSwordsman, NotoriousEntity::initBrawler, NotoriousEntity::initBlackLeg, NotoriousEntity::initSniper});
   private static final List<IMobDetails<NotoriousEntity>> RACES = Lists.newArrayList(new IMobDetails[]{NotoriousEntity::initHuman, NotoriousEntity::initFishman});
   private static final Map<Faction, ArrayList<Supplier<? extends Item>>> MELEE_FACTION_WEAPONS = createFactionWeaponsMap();
   private static final String[] PIRATE_NAMES = new String[]{"Allison", "Bellamy", "Benito", "Barrow", "Caesar", "Bonnet", "Burke", "Chivers", "Coward", "Derdrake", "Frowd", "Hands", "Julian", "Low", "Lyne", "Pargo", "Roberts", "Almeida", "Shih", "Abbas", "Willems", "Veale", "Lewis", "Hamlin", "Andreson", "Wittebol", "Worst"};
   private static final String[] PIRATE_PRE_TITLES = new String[]{"Tyrant", "Sir", "Don", "Iron Hand", "Boss", "Commander", "Black Arm", "Mountain", "Red-Eye", "Dead-Eye", "Savage", "Captain", "Silver Blade", "Black Beard", "Mountain Slasher", "General", "Commodore", "Sharp Fang", "Dreadful"};
   private static final String[] PIRATE_POST_TITLES = new String[]{"the Hyena", "the Beheader", "the Cannibal", "the Mad", "the Mighty", "the Superior", "the Lonely", "the Sea Weasel", "the Commander", "the Merciless", "the Wicked", "the Usurper", "the Mercenary", "the Great", "the Undying", "of the North Blue", "of the South Blue", "of the East Blue", "of the West Blue", "the Sea King", "the Burglar", "the Devil", "the Devil Child", "the Gentleman Pirate", "the Surgeon of Death", "the Massacre Soldier"};
   private static final String[] MARINE_NAMES = new String[]{"Golden", "Goffe", "Griffin", "Hoar", "John", "Liddell", "Penniston", "Aregnaudeau", "Jennings", "Enriquez", "Day", "Crapo", "Burches", "Barnet", "Ashworth", "Wright", "Woolerly", "Ras", "Pardal", "Paine", "Morgan", "Mansfield", "Henley", "Essex", "Browne", "Bequel"};
   private final ServerOPBossEvent bossInfo;
   private final NPCPhaseManager phaseManager;
   private float difficulty;
   private boolean isTriggered;
   private TrackedNPC trackedData;
   private RandomSource trackedRandom;

   private static Map<Faction, ArrayList<Supplier<? extends Item>>> createFactionWeaponsMap() {
      Map<Faction, ArrayList<Supplier<? extends Item>>> map = new HashMap();
      map.put((Faction)ModFactions.PIRATE.get(), MobsHelper.PIRATE_SWORDS);
      map.put((Faction)ModFactions.MARINE.get(), MobsHelper.MARINE_CAPTAIN_SWORDS);
      map.put((Faction)ModFactions.BANDIT.get(), MobsHelper.BANDIT_SWORDS);
      return map;
   }

   public NotoriousEntity(EntityType<? extends NotoriousEntity> type, Level world, IMobDetails<NotoriousEntity> faction) {
      this(type, world, faction, (TrackedNPC)null);
   }

   public NotoriousEntity(EntityType<? extends NotoriousEntity> type, Level world, IMobDetails<NotoriousEntity> faction, @Nullable TrackedNPC trackedData) {
      super(type, world);
      this.bossInfo = new ServerOPBossEvent(this.m_7770_());
      this.phaseManager = new NPCPhaseManager(this);
      if (world != null && !world.f_46443_) {
         if (trackedData != null) {
            this.trackedData = trackedData;
            this.trackedRandom = RandomSource.m_216335_(this.trackedData.getSeed());
            this.m_20084_(trackedData.getUUID());
            this.getEntityStats().setFightingStyle(trackedData.getStyle());
            this.getEntityStats().setRace(trackedData.getRace());
            this.getEntityStats().setSubRace(trackedData.getSubRace());
         } else {
            this.trackedRandom = this.f_19796_;
            this.m_20084_(new UUID(this.trackedRandom.m_188505_(), this.trackedRandom.m_188505_()));
            ((IMobDetails)STYLES.get(this.m_217043_().m_188503_(STYLES.size()))).init(this);
            ((IMobDetails)RACES.get(this.m_217043_().m_188503_(RACES.size()))).init(this);
         }

         faction.init(this);
         if (this.trackedRandom.m_188501_() < 0.5F) {
            if (this.trackedRandom.m_188501_() < 0.3F) {
               this.m_8061_(EquipmentSlot.HEAD, new ItemStack((ItemLike)ModItems.THREE_CIGARS.get()));
            } else {
               this.m_8061_(EquipmentSlot.HEAD, new ItemStack((ItemLike)ModItems.CIGAR.get()));
            }
         }

         this.setCustomName(this.trackedRandom);
         this.chooseTexture(this.trackedRandom);
         boolean isHardDifficulty = this.isAboveNormalDifficulty();
         AttributeHelper.updateToughnessAttribute(this);
         this.m_21051_((Attribute)ModAttributes.GCD.get()).m_22100_(isHardDifficulty ? (double)20.0F : (double)40.0F);
         this.m_21051_((Attribute)ModAttributes.TOUGHNESS.get()).m_22100_((double)8.0F);
         MobsHelper.addBasicNPCGoals(this);
         this.f_21345_.m_25352_(0, new JumpOutOfHoleGoal(this));
         this.f_21345_.m_25352_(0, new GapCloserGoal(this));
         this.f_21345_.m_25352_(0, new DashDodgeProjectilesGoal(this, 200.0F, 2.5F));
         this.f_21345_.m_25352_(0, new DashDodgeTargetGoal(this, 250.0F, 2.5F));
         this.f_21345_.m_25352_(0, new SprintTowardsTargetGoal(this));
         this.f_21345_.m_25352_(1, new ImprovedMeleeAttackGoal(this, (double)1.0F, true));
         if (this.getEntityStats().isBandit()) {
         }

         this.f_21346_.m_25352_(1, new PersonalSpaceTargetGoal(this));
         MobsHelper.getBasicHakiAbilities(this, 100).forEach((core) -> this.f_21345_.m_25352_(0, new HakiAbilityWrapperGoal(this, core)));
         MobsHelper.getAdvancedHakiAbilities(this, 100).forEach((core) -> this.f_21345_.m_25352_(0, new HakiAbilityWrapperGoal(this, core)));
         this.f_21345_.m_25352_(1, new HakiAbilityWrapperGoal(this, (AbilityCore)KenbunshokuHakiFutureSightAbility.INSTANCE.get()));
         int fruitChance = (Integer)AbilitiesConfig.OPEN_WORLD_FRUIT_USERS.get();
         if (fruitChance > 0 && this.trackedRandom.m_188503_(100) <= fruitChance) {
         }
      }

   }

   public static NotoriousEntity createMarine(EntityType<? extends NotoriousEntity> type, Level world) {
      return new NotoriousEntity(type, world, NotoriousEntity::initMarine);
   }

   public static NotoriousEntity createPirate(EntityType<? extends NotoriousEntity> type, Level world) {
      return new NotoriousEntity(type, world, NotoriousEntity::initPirate);
   }

   public static NotoriousEntity createBandit(EntityType<? extends NotoriousEntity> type, Level world) {
      return new NotoriousEntity(type, world, NotoriousEntity::initBandit);
   }

   public static AttributeSupplier.Builder createAttributes() {
      return OPEntity.createAttributes().m_22268_(Attributes.f_22277_, (double)60.0F).m_22268_(Attributes.f_22279_, (double)0.3F).m_22268_(Attributes.f_22281_, (double)6.0F).m_22268_(Attributes.f_22276_, WyHelper.randomWithRange(200, 250)).m_22268_(Attributes.f_22284_, (double)20.0F).m_22268_(Attributes.f_22285_, (double)10.0F);
   }

   private void setCustomName(RandomSource random) {
      IEntityStats props = (IEntityStats)EntityStatsCapability.get(this).orElse((Object)null);
      if (props != null) {
         if (props.isMarine()) {
            String name = MARINE_NAMES[random.m_188503_(MARINE_NAMES.length)];
            this.m_6593_(Component.m_237113_("Vice Admiral " + name));
         } else if (random.m_188503_(64) == 0) {
            if (random.m_188499_()) {
               this.m_6593_(Component.m_237113_("Jack the Sparrow"));
            } else {
               this.m_6593_(Component.m_237113_("Captain Jack Sparrow"));
            }
         } else {
            String name = PIRATE_NAMES[random.m_188503_(PIRATE_NAMES.length)];
            String prefix = "";
            String suffix = "";
            if (random.m_188503_(3) == 0) {
               if (random.m_188499_()) {
                  String[] var10000 = PIRATE_PRE_TITLES;
                  prefix = var10000[random.m_188503_(PIRATE_PRE_TITLES.length)] + " ";
                  if (random.m_188503_(4) == 0) {
                     suffix = PIRATE_POST_TITLES[random.m_188503_(PIRATE_POST_TITLES.length)];
                  }
               } else {
                  suffix = PIRATE_POST_TITLES[random.m_188503_(PIRATE_POST_TITLES.length)];
               }
            } else {
               prefix = "Captain ";
            }

            this.m_6593_(Component.m_237113_(prefix + name + (suffix.isEmpty() ? "" : " " + suffix)));
         }

         this.bossInfo.m_6456_(this.m_7770_());
      }
   }

   public void m_8119_() {
      this.bossInfo.tick(this);
      super.m_8119_();
   }

   public void m_6457_(ServerPlayer player) {
      super.m_6457_(player);
      if (this.bossInfo != null) {
         this.bossInfo.m_6543_(player);
      }

      if (!this.isTriggered) {
         this.isTriggered = true;
      }

      this.bossInfo.m_6456_(this.m_7770_());
   }

   public void m_6452_(ServerPlayer player) {
      super.m_6452_(player);
      if (this.bossInfo != null) {
         this.bossInfo.m_6539_(player);
      }

      if (this.isTriggered) {
         this.isTriggered = false;
      }

   }

   private static void initSwordsman(NotoriousEntity entity) {
      entity.getEntityStats().setFightingStyle((FightingStyle)ModFightingStyles.SWORDSMAN.get());
      Faction faction = (Faction)entity.getEntityStats().getFaction().orElse((Object)null);
      if (faction != null) {
         ItemStack randomSword = entity.getRandomSword((List)MELEE_FACTION_WEAPONS.get(faction));
         entity.m_8061_(EquipmentSlot.MAINHAND, randomSword);
         float dualWeildChance = entity.getEntityStats().isPirate() ? 0.4F : 0.2F;
         if (entity.m_217043_().m_188501_() < dualWeildChance) {
            entity.m_8061_(EquipmentSlot.OFFHAND, randomSword.m_41777_());
         }

         int maxGoals = 4 + entity.trackedRandom.m_188503_(2);
         WeightedList<Supplier<Goal>> list = new WeightedList<Supplier<Goal>>(new Object[0]);
         list.addEntry((Supplier)() -> new DashAbilityWrapperGoal(entity, (AbilityCore)ShiShishiSonsonAbility.INSTANCE.get()), 4);
         list.addEntry((Supplier)() -> new JumpAbilityWrapperGoal(entity, (AbilityCore)HiryuKaenAbility.INSTANCE.get()), 4);
         list.addEntry((Supplier)() -> new CloseMeleeAbilityWrapperGoal(entity, (AbilityCore)OTatsumakiAbility.INSTANCE.get()), 3);
         list.addEntry((Supplier)() -> new ProjectileAbilityWrapperGoal(entity, (AbilityCore)SanjurokuPoundHoAbility.INSTANCE.get()), 3);
         list.addEntry((Supplier)() -> new ProjectileAbilityWrapperGoal(entity, (AbilityCore)YakkodoriAbility.INSTANCE.get()), 3);
         Set<Supplier<Goal>> goals = list.pickN(entity.trackedRandom, maxGoals);
         goals.forEach((goal) -> entity.f_21345_.m_25352_(3, (Goal)goal.get()));
      }
   }

   private static void initBrawler(NotoriousEntity entity) {
      entity.getEntityStats().setFightingStyle((FightingStyle)ModFightingStyles.BRAWLER.get());
      int maxGoals = 4 + entity.trackedRandom.m_188503_(2);
      entity.f_21345_.m_25352_(0, new AlwaysActiveAbilityWrapperGoal(entity, (AbilityCore)BrawlerPassiveBonusesAbility.INSTANCE.get()));
      WeightedList<Supplier<Goal>> list = new WeightedList<Supplier<Goal>>(new Object[0]);
      list.addEntry((Supplier)() -> new ProjectileAbilityWrapperGoal(entity, (AbilityCore)GenkotsuMeteorAbility.INSTANCE.get()), 5);
      list.addEntry((Supplier)() -> new CloseMeleeAbilityWrapperGoal(entity, (AbilityCore)ChargedPunchAbility.INSTANCE.get()), 5);
      list.addEntry((Supplier)() -> new CloseMeleeAbilityWrapperGoal(entity, (AbilityCore)HakaiHoAbility.INSTANCE.get()), 4);
      list.addEntry((Supplier)() -> new CloseMeleeAbilityWrapperGoal(entity, (AbilityCore)JishinHoAbility.INSTANCE.get()), 4);
      list.addEntry((Supplier)() -> new CloseMeleeAbilityWrapperGoal(entity, (AbilityCore)PunchRushAbility.INSTANCE.get()), 3);
      list.addEntry((Supplier)() -> new GrabAbilityWrapperGoal(entity, (AbilityCore)SpinningBrawlAbility.INSTANCE.get()), 2);
      list.addEntry((Supplier)() -> new GrabAbilityWrapperGoal(entity, (AbilityCore)SuplexAbility.INSTANCE.get()), 2);
      Set<Supplier<Goal>> goals = list.pickN(entity.trackedRandom, maxGoals);
      goals.forEach((goal) -> entity.f_21345_.m_25352_(3, (Goal)goal.get()));
   }

   private static void initBlackLeg(NotoriousEntity entity) {
      entity.getEntityStats().setFightingStyle((FightingStyle)ModFightingStyles.BLACK_LEG.get());
      int maxGoals = 4 + entity.trackedRandom.m_188503_(2);
      entity.f_21345_.m_25352_(0, new AlwaysActiveAbilityWrapperGoal(entity, (AbilityCore)BlackLegPassiveBonusesAbility.INSTANCE.get()));
      WeightedList<Supplier<Goal>> list = new WeightedList<Supplier<Goal>>(new Object[0]);
      list.addEntry((Supplier)() -> new CloseMeleeAbilityWrapperGoal(entity, (AbilityCore)PartyTableKickCourseAbility.INSTANCE.get()), 3);
      list.addEntry((Supplier)() -> new CloseMeleeAbilityWrapperGoal(entity, (AbilityCore)AntiMannerKickCourseAbility.INSTANCE.get()), 3);
      list.addEntry((Supplier)() -> new JumpAbilityWrapperGoal(entity, (AbilityCore)ConcasseAbility.INSTANCE.get()), 3);
      list.addEntry((Supplier)() -> new RepeaterAbilityWrapperGoal(entity, (AbilityCore)ExtraHachisAbility.INSTANCE.get()), 3);
      Set<Supplier<Goal>> goals = list.pickN(entity.trackedRandom, maxGoals);
      goals.forEach((goal) -> entity.f_21345_.m_25352_(3, (Goal)goal.get()));
   }

   private static void initSniper(NotoriousEntity entity) {
      entity.getEntityStats().setFightingStyle((FightingStyle)ModFightingStyles.SNIPER.get());
      int maxGoals = 4 + entity.trackedRandom.m_188503_(2);
      entity.f_21345_.m_25352_(0, new AlwaysActiveAbilityWrapperGoal(entity, (AbilityCore)BlackLegPassiveBonusesAbility.INSTANCE.get()));
      WeightedList<Supplier<Goal>> list = new WeightedList<Supplier<Goal>>(new Object[0]);
      list.addEntry((Supplier)() -> new ProjectileAbilityWrapperGoal(entity, (AbilityCore)KaenBoshiAbility.INSTANCE.get()), 3);
      list.addEntry((Supplier)() -> new ProjectileAbilityWrapperGoal(entity, (AbilityCore)SakuretsuSabotenBoshiAbility.INSTANCE.get()), 3);
      list.addEntry((Supplier)() -> new ProjectileAbilityWrapperGoal(entity, (AbilityCore)TokuyoAburaBoshiAbility.INSTANCE.get()), 3);
      list.addEntry((Supplier)() -> new ProjectileAbilityWrapperGoal(entity, (AbilityCore)TetsuBoshiAbility.INSTANCE.get()), 2);
      list.addEntry((Supplier)() -> new ProjectileAbilityWrapperGoal(entity, (AbilityCore)RenpatsuNamariBoshiAbility.INSTANCE.get()), 2);
      list.addEntry((Supplier)() -> new ProjectileAbilityWrapperGoal(entity, (AbilityCore)KemuriBoshiAbility.INSTANCE.get()), 1);
      list.addEntry((Supplier)() -> new ProjectileAbilityWrapperGoal(entity, (AbilityCore)NemuriBoshiAbility.INSTANCE.get()), 1);
      Set<Supplier<Goal>> goals = list.pickN(entity.trackedRandom, maxGoals);
      goals.forEach((goal) -> entity.f_21345_.m_25352_(3, (Goal)goal.get()));
   }

   private static void initHuman(NotoriousEntity entity) {
      entity.getEntityStats().setRace((Race)ModRaces.HUMAN.get());
      boolean isHardDifficulty = entity.isAboveNormalDifficulty();
      int maxGoals = 3 + (isHardDifficulty ? 2 : 0);
      WeightedList<Supplier<Goal>> list = new WeightedList<Supplier<Goal>>(new Object[0]);
      list.addEntry((Supplier)() -> new DashAbilityWrapperGoal(entity, (AbilityCore)SoruAbility.INSTANCE.get()), 5);
      list.addEntry((Supplier)() -> new CloseMeleeAbilityWrapperGoal(entity, (AbilityCore)ShiganAbility.INSTANCE.get()), 5);
      list.addEntry((Supplier)() -> new ReactiveGuardAbilityWrapperGoal(entity, (AbilityCore)TekkaiAbility.INSTANCE.get()), 5);
      list.addEntry((Supplier)() -> new ProjectileAbilityWrapperGoal(entity, (AbilityCore)RankyakuAbility.INSTANCE.get()), 4);
      list.addEntry((Supplier)() -> new ReactiveRetreatAbilityWrapperGoal(entity, (AbilityCore)GeppoAbility.INSTANCE.get()), 4);
      list.addEntry((Supplier)() -> new ReactiveGuardAbilityWrapperGoal(entity, (AbilityCore)KamieAbility.INSTANCE.get()), 4);
      Set<Supplier<Goal>> goals = list.pickN(entity.trackedRandom, maxGoals);
      goals.forEach((goal) -> entity.f_21345_.m_25352_(3, (Goal)goal.get()));
   }

   private static void initFishman(NotoriousEntity entity) {
      entity.getEntityStats().setRace((Race)ModRaces.FISHMAN.get());
      boolean isHardDifficulty = entity.isAboveNormalDifficulty();
      int maxGoals = 3 + (isHardDifficulty ? 2 : 0);
      entity.f_21345_.m_25352_(0, new AlwaysActiveAbilityWrapperGoal(entity, (AbilityCore)FishmanPassiveBonusesAbility.INSTANCE.get()));
      WeightedList<Supplier<Goal>> list = new WeightedList<Supplier<Goal>>(new Object[0]);
      list.addEntry((Supplier)() -> new CloseMeleeAbilityWrapperGoal(entity, (AbilityCore)KarakusagawaraSeikenAbility.INSTANCE.get()), 5);
      list.addEntry((Supplier)() -> new CloseMeleeAbilityWrapperGoal(entity, (AbilityCore)KachiageHaisokuAbility.INSTANCE.get()), 5);
      list.addEntry((Supplier)() -> new ProjectileAbilityWrapperGoal(entity, (AbilityCore)PackOfSharksAbility.INSTANCE.get()), 5);
      list.addEntry((Supplier)() -> new ReactiveGuardAbilityWrapperGoal(entity, (AbilityCore)MizuOsuAbility.INSTANCE.get()), 3);
      list.addEntry((Supplier)() -> new ReactiveGuardAbilityWrapperGoal(entity, (AbilityCore)SamehadaShoteiAbility.INSTANCE.get()), 5);
      list.addEntry((Supplier)() -> new ProjectileAbilityWrapperGoal(entity, (AbilityCore)UchimizuAbility.INSTANCE.get()), 5);
      list.addEntry((Supplier)() -> new ProjectileAbilityWrapperGoal(entity, (AbilityCore)MizuShuryudanAbility.INSTANCE.get()), 3);
      Set<Supplier<Goal>> goals = list.pickN(entity.trackedRandom, maxGoals);
      goals.forEach((goal) -> entity.f_21345_.m_25352_(3, (Goal)goal.get()));
   }

   public static void initMarine(NotoriousEntity entity) {
      entity.getEntityStats().setFaction((Faction)ModFactions.MARINE.get());
      entity.setTextures(MobsHelper.MARINE_VICE_ADMIRAL_TEXTURES);
      ItemStack marineCapeStack = new ItemStack((ItemLike)ModArmors.MARINE_CAPTAIN_CAPE.get());
      IMultiChannelColorItem.dyeArmor(marineCapeStack, 0, 33980);
      entity.m_8061_(EquipmentSlot.CHEST, marineCapeStack);
   }

   public static void initPirate(NotoriousEntity entity) {
      entity.getEntityStats().setFaction((Faction)ModFactions.PIRATE.get());
      entity.setTextures(MobsHelper.PIRATE_CAPTAINS_TEXTURES);
      if (entity.trackedRandom.m_188501_() < 0.4F) {
         ItemStack pirateCapeStack = new ItemStack((ItemLike)ModArmors.PIRATE_CAPTAIN_CAPE.get());
         entity.m_8061_(EquipmentSlot.CHEST, pirateCapeStack);
      }

   }

   private static void initBandit(NotoriousEntity entity) {
      entity.getEntityStats().setFaction((Faction)ModFactions.BANDIT.get());
      entity.setTextures(MobsHelper.BANDIT_TEXTURES);
   }

   public RandomSource getTrackedRandom() {
      return this.trackedRandom;
   }

   @Nullable
   public TrackedNPC getTrackedData() {
      return this.trackedData;
   }
}
