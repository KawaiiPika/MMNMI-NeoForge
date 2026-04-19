package xyz.pixelatedw.mineminenomi.entities.mobs;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import xyz.pixelatedw.mineminenomi.abilities.brawler.BrawlerPassiveBonusesAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.ChargedPunchAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.GenkotsuMeteorAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.HakaiHoAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.JishinHoAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.PunchRushAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.SpinningBrawlAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.SuplexAbility;
import xyz.pixelatedw.mineminenomi.abilities.ittoryu.HiryuKaenAbility;
import xyz.pixelatedw.mineminenomi.abilities.ittoryu.SanbyakurokujuPoundHoAbility;
import xyz.pixelatedw.mineminenomi.abilities.ittoryu.ShiShishiSonsonAbility;
import xyz.pixelatedw.mineminenomi.abilities.ittoryu.YakkodoriAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.KamieAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.RankyakuAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.ShiganAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.SoruAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.TekkaiAbility;
import xyz.pixelatedw.mineminenomi.abilities.santoryu.OTatsumakiAbility;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.entities.IMobDetails;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.api.util.WeightedList;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.haki.HakiCapability;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.GapCloserGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.ImprovedMeleeAttackGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.JumpOutOfHoleGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.SprintTowardsTargetGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.ThrowKnifeGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.AlwaysActiveAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.CloseMeleeAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.DashAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.GrabAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.HakiAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.JumpAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ProjectileAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ReactiveGuardAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.init.ModArmors;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModFactions;
import xyz.pixelatedw.mineminenomi.init.ModFightingStyles;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.ModRaces;

public class CaptainEntity extends OPEntity {
   private static final List<IMobDetails<OPEntity>> STYLES = Lists.newArrayList(new IMobDetails[]{CaptainEntity::initSwordsman, CaptainEntity::initBrawler});
   private static final Map<Faction, ArrayList<Supplier<? extends Item>>> MELEE_FACTION_WEAPONS = createFactionWeaponsMap();

   private static Map<Faction, ArrayList<Supplier<? extends Item>>> createFactionWeaponsMap() {
      Map<Faction, ArrayList<Supplier<? extends Item>>> map = new HashMap();
      map.put((Faction)ModFactions.PIRATE.get(), MobsHelper.PIRATE_SWORDS);
      map.put((Faction)ModFactions.MARINE.get(), MobsHelper.MARINE_CAPTAIN_SWORDS);
      map.put((Faction)ModFactions.BANDIT.get(), MobsHelper.BANDIT_SWORDS);
      return map;
   }

   public CaptainEntity(EntityType<? extends CaptainEntity> type, Level world, IMobDetails<OPEntity> faction) {
      super(type, world, (ResourceLocation[])null);
      if (world != null && !world.f_46443_) {
         faction.init(this);
         ((IMobDetails)STYLES.get(this.m_217043_().m_188503_(STYLES.size()))).init(this);
         initHuman(this);
         this.chooseTexture();
         if (this.f_19796_.m_188501_() < 0.7F) {
            if (this.f_19796_.m_188501_() < 0.3F) {
               this.m_8061_(EquipmentSlot.HEAD, new ItemStack((ItemLike)ModItems.THREE_CIGARS.get()));
            } else {
               this.m_8061_(EquipmentSlot.HEAD, new ItemStack((ItemLike)ModItems.CIGAR.get()));
            }
         }

         boolean isHardDifficulty = this.isAboveNormalDifficulty();
         this.getEntityStats().setDoriki((double)4000.0F + WyHelper.randomWithRange(0, 2000) + (double)(isHardDifficulty ? 500 : 0));
         this.getEntityStats().setBelly(50L + (long)WyHelper.randomWithRange(0, 20));
         HakiCapability.get(this).ifPresent((props) -> {
            props.setBusoshokuHakiExp((float)(55 + this.m_217043_().m_188503_(isHardDifficulty ? 20 : 10)));
            props.setKenbunshokuHakiExp((float)(55 + this.m_217043_().m_188503_(isHardDifficulty ? 20 : 10)));
         });
         this.m_21051_((Attribute)ModAttributes.TOUGHNESS.get()).m_22100_((double)6.0F);
         this.m_21051_((Attribute)ModAttributes.GCD.get()).m_22100_((double)40.0F);
         if (this.m_9236_().m_46791_().m_19028_() <= 2) {
            float var10000;
            switch (this.m_9236_().m_46791_()) {
               case EASY -> var10000 = 2.0F;
               case NORMAL -> var10000 = 1.25F;
               default -> var10000 = 1.0F;
            }

            float mod = var10000;
            AttributeInstance attr = this.m_21051_(Attributes.f_22277_);
            attr.m_22100_(attr.m_22115_() / (double)mod);
         }

         MobsHelper.addBasicNPCGoals(this);
         MobsHelper.getBasicHakiAbilities(this, 50).forEach((core) -> this.f_21345_.m_25352_(0, new HakiAbilityWrapperGoal(this, core)));
         if (isHardDifficulty) {
            MobsHelper.getAdvancedHakiAbilities(this, 10).forEach((core) -> this.f_21345_.m_25352_(0, new HakiAbilityWrapperGoal(this, core)));
         }

         this.f_21345_.m_25352_(0, new JumpOutOfHoleGoal(this));
         this.f_21345_.m_25352_(0, new GapCloserGoal(this));
         if (this.f_19796_.m_188501_() < 0.6F) {
            this.f_21345_.m_25352_(0, new SprintTowardsTargetGoal(this));
         }

         this.f_21345_.m_25352_(1, new ImprovedMeleeAttackGoal(this, (double)1.0F, true));
      }

   }

   public static CaptainEntity createMarineCaptain(EntityType<? extends CaptainEntity> type, Level world) {
      return new CaptainEntity(type, world, CaptainEntity::initMarine);
   }

   public static CaptainEntity createPirateCaptain(EntityType<? extends CaptainEntity> type, Level world) {
      return new CaptainEntity(type, world, CaptainEntity::initPirate);
   }

   public static CaptainEntity createBanditCaptain(EntityType<? extends CaptainEntity> type, Level world) {
      return new CaptainEntity(type, world, CaptainEntity::initBandit);
   }

   public static AttributeSupplier.Builder createAttributes() {
      return OPEntity.createAttributes().m_22268_(Attributes.f_22277_, (double)50.0F).m_22268_(Attributes.f_22279_, (double)0.3F).m_22268_(Attributes.f_22281_, WyHelper.randomWithRange(3, 6)).m_22268_(Attributes.f_22276_, WyHelper.randomWithRange(80, 120)).m_22268_(Attributes.f_22284_, WyHelper.randomWithRange(8, 12));
   }

   protected boolean m_8028_() {
      return true;
   }

   public static boolean checkSpawnRules(EntityType<? extends OPEntity> type, ServerLevelAccessor world, MobSpawnType reason, BlockPos pos, RandomSource random) {
      if (reason != MobSpawnType.SPAWNER) {
         BlockPos worldSpawn = new BlockPos(world.m_6106_().m_6789_(), world.m_6106_().m_6527_(), world.m_6106_().m_6526_());
         if (pos.m_123314_(worldSpawn, (double)512.0F)) {
            return false;
         }
      }

      double chance = random.m_188500_() * (double)100.0F;
      return chance > ServerConfig.getCaptainSpawnChance() ? false : OPEntity.checkSpawnRules(type, world, reason, pos, random);
   }

   private static void initMarine(OPEntity entity) {
      entity.getEntityStats().setFaction((Faction)ModFactions.MARINE.get());
      entity.setTextures(MobsHelper.MARINE_CAPTAINS_TEXTURES);
      ItemStack marineCapeStack = new ItemStack((ItemLike)ModArmors.MARINE_CAPTAIN_CAPE.get());
      marineCapeStack.m_41698_("display").m_128405_("color", 33980);
      entity.m_8061_(EquipmentSlot.CHEST, marineCapeStack);
   }

   private static void initPirate(OPEntity entity) {
      entity.getEntityStats().setFaction((Faction)ModFactions.PIRATE.get());
      entity.setTextures(MobsHelper.PIRATE_CAPTAINS_TEXTURES);
      if (entity.m_217043_().m_188501_() < 0.7F) {
         ItemStack pirateCapeStack = new ItemStack((ItemLike)ModArmors.PIRATE_CAPTAIN_CAPE.get());
         entity.m_8061_(EquipmentSlot.CHEST, pirateCapeStack);
      }

   }

   private static void initBandit(OPEntity entity) {
      entity.getEntityStats().setFaction((Faction)ModFactions.BANDIT.get());
      entity.setTextures(MobsHelper.BANDIT_TEXTURES);
      entity.f_21345_.m_25352_(2, (new ThrowKnifeGoal(entity)).setAmount(3));
   }

   private static void initSwordsman(OPEntity entity) {
      entity.getEntityStats().setFightingStyle((FightingStyle)ModFightingStyles.SWORDSMAN.get());
      Faction faction = (Faction)entity.getEntityStats().getFaction().orElse((Object)null);
      if (faction != null) {
         ItemStack randomSword = entity.getRandomSword((List)MELEE_FACTION_WEAPONS.get(faction));
         entity.m_8061_(EquipmentSlot.MAINHAND, randomSword);
         float dualWeildChance = entity.getEntityStats().isPirate() ? 0.4F : 0.2F;
         if (entity.m_217043_().m_188501_() < dualWeildChance) {
            entity.m_8061_(EquipmentSlot.OFFHAND, randomSword.m_41777_());
         }

         int maxGoals = entity.getEntityStats().isBandit() ? 2 : 4;
         WeightedList<Supplier<Goal>> list = new WeightedList<Supplier<Goal>>(new Object[0]);
         list.addEntry((Supplier)() -> new DashAbilityWrapperGoal(entity, (AbilityCore)ShiShishiSonsonAbility.INSTANCE.get()), 4);
         list.addEntry((Supplier)() -> new JumpAbilityWrapperGoal(entity, (AbilityCore)HiryuKaenAbility.INSTANCE.get()), 4);
         list.addEntry((Supplier)() -> new CloseMeleeAbilityWrapperGoal(entity, (AbilityCore)OTatsumakiAbility.INSTANCE.get()), 2);
         list.addEntry((Supplier)() -> {
            ProjectileAbilityWrapperGoal<SanbyakurokujuPoundHoAbility> goal = new ProjectileAbilityWrapperGoal<SanbyakurokujuPoundHoAbility>(entity, (AbilityCore)SanbyakurokujuPoundHoAbility.INSTANCE.get());
            ((SanbyakurokujuPoundHoAbility)goal.getAbility()).addCanUseCheck(MobsHelper.BELOW_90_HP_CHECK);
            return goal;
         }, 2);
         list.addEntry((Supplier)() -> {
            ProjectileAbilityWrapperGoal<YakkodoriAbility> goal = new ProjectileAbilityWrapperGoal<YakkodoriAbility>(entity, (AbilityCore)YakkodoriAbility.INSTANCE.get());
            ((YakkodoriAbility)goal.getAbility()).addCanUseCheck(MobsHelper.BELOW_90_HP_CHECK);
            return goal;
         }, 1);
         Set<Supplier<Goal>> goals = list.pickN(entity.m_217043_(), maxGoals);
         goals.forEach((goal) -> entity.f_21345_.m_25352_(4, (Goal)goal.get()));
      }
   }

   private static void initBrawler(OPEntity entity) {
      entity.getEntityStats().setFightingStyle((FightingStyle)ModFightingStyles.BRAWLER.get());
      int maxGoals = entity.getEntityStats().isBandit() ? 4 : 3;
      entity.f_21345_.m_25352_(0, new AlwaysActiveAbilityWrapperGoal(entity, (AbilityCore)BrawlerPassiveBonusesAbility.INSTANCE.get()));
      WeightedList<Supplier<Goal>> list = new WeightedList<Supplier<Goal>>(new Object[0]);
      list.addEntry((Supplier)() -> {
         ProjectileAbilityWrapperGoal<GenkotsuMeteorAbility> goal = new ProjectileAbilityWrapperGoal<GenkotsuMeteorAbility>(entity, (AbilityCore)GenkotsuMeteorAbility.INSTANCE.get());
         ((GenkotsuMeteorAbility)goal.getAbility()).addCanUseCheck(MobsHelper.BELOW_90_HP_CHECK);
         return goal;
      }, 5);
      list.addEntry((Supplier)() -> new CloseMeleeAbilityWrapperGoal(entity, (AbilityCore)ChargedPunchAbility.INSTANCE.get()), 5);
      list.addEntry((Supplier)() -> new CloseMeleeAbilityWrapperGoal(entity, (AbilityCore)HakaiHoAbility.INSTANCE.get()), 4);
      list.addEntry((Supplier)() -> new CloseMeleeAbilityWrapperGoal(entity, (AbilityCore)JishinHoAbility.INSTANCE.get()), 4);
      list.addEntry((Supplier)() -> new CloseMeleeAbilityWrapperGoal(entity, (AbilityCore)PunchRushAbility.INSTANCE.get()), 3);
      list.addEntry((Supplier)() -> new GrabAbilityWrapperGoal(entity, (AbilityCore)SpinningBrawlAbility.INSTANCE.get()), 2);
      list.addEntry((Supplier)() -> {
         GrabAbilityWrapperGoal<SuplexAbility> goal = new GrabAbilityWrapperGoal<SuplexAbility>(entity, (AbilityCore)SuplexAbility.INSTANCE.get());
         ((SuplexAbility)goal.getAbility()).addCanUseCheck(MobsHelper.BELOW_90_HP_CHECK);
         return goal;
      }, 2);
      Set<Supplier<Goal>> goals = list.pickN(entity.m_217043_(), maxGoals);
      goals.forEach((goal) -> entity.f_21345_.m_25352_(4, (Goal)goal.get()));
   }

   private static void initHuman(OPEntity entity) {
      entity.getEntityStats().setRace((Race)ModRaces.HUMAN.get());
      boolean isBandit = entity.getEntityStats().isBandit();
      int maxGoals = isBandit ? 2 : 3;
      WeightedList<Supplier<Goal>> list = new WeightedList<Supplier<Goal>>(new Object[0]);
      list.addEntry((Supplier)() -> new DashAbilityWrapperGoal(entity, (AbilityCore)SoruAbility.INSTANCE.get()), 10);
      list.addEntry((Supplier)() -> new CloseMeleeAbilityWrapperGoal(entity, (AbilityCore)ShiganAbility.INSTANCE.get()), 7);
      list.addEntry((Supplier)() -> new ReactiveGuardAbilityWrapperGoal(entity, (AbilityCore)TekkaiAbility.INSTANCE.get()), 7);
      list.addEntry((Supplier)() -> {
         ProjectileAbilityWrapperGoal<RankyakuAbility> goal = new ProjectileAbilityWrapperGoal<RankyakuAbility>(entity, (AbilityCore)RankyakuAbility.INSTANCE.get());
         ((RankyakuAbility)goal.getAbility()).addCanUseCheck(MobsHelper.BELOW_90_HP_CHECK);
         return goal;
      }, 4);
      list.addEntry((Supplier)() -> new ReactiveGuardAbilityWrapperGoal(entity, (AbilityCore)KamieAbility.INSTANCE.get()), 1);
      Set<Supplier<Goal>> goals = list.pickN(entity.m_217043_(), maxGoals);
      goals.forEach((goal) -> entity.f_21345_.m_25352_(4, (Goal)goal.get()));
   }
}
