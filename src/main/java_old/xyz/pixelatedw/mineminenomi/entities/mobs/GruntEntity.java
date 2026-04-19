package xyz.pixelatedw.mineminenomi.entities.mobs;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import xyz.pixelatedw.mineminenomi.abilities.SlamAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.ChargedPunchAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.HakaiHoAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.PunchRushAbility;
import xyz.pixelatedw.mineminenomi.abilities.ittoryu.HiryuKaenAbility;
import xyz.pixelatedw.mineminenomi.abilities.ittoryu.ShiShishiSonsonAbility;
import xyz.pixelatedw.mineminenomi.abilities.ittoryu.YakkodoriAbility;
import xyz.pixelatedw.mineminenomi.abilities.sniper.KaenBoshiAbility;
import xyz.pixelatedw.mineminenomi.abilities.sniper.SakuretsuSabotenBoshiAbility;
import xyz.pixelatedw.mineminenomi.abilities.sniper.TetsuBoshiAbility;
import xyz.pixelatedw.mineminenomi.abilities.sniper.TokuyoAburaBoshiAbility;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.entities.IMobDetails;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.api.util.WeightedList;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.haki.HakiCapability;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.HandleCannonGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.ImprovedMeleeAttackGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.RunAwayFromFearGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.CloseMeleeAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.DashAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.HakiAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.JumpAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ProjectileAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.projectiles.KairosekiBulletProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.NormalBulletProjectile;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModFactions;
import xyz.pixelatedw.mineminenomi.init.ModFightingStyles;
import xyz.pixelatedw.mineminenomi.init.ModRaces;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;

public class GruntEntity extends OPEntity {
   private static final List<IMobDetails<OPEntity>> STYLES = Lists.newArrayList(new IMobDetails[]{GruntEntity::initSwordsman, GruntEntity::initSniper, GruntEntity::initBrawler});
   private static final Map<Faction, ArrayList<Supplier<? extends Item>>> MELEE_FACTION_WEAPONS = createFactionWeaponsMap();

   private static Map<Faction, ArrayList<Supplier<? extends Item>>> createFactionWeaponsMap() {
      Map<Faction, ArrayList<Supplier<? extends Item>>> map = new HashMap();
      map.put((Faction)ModFactions.PIRATE.get(), MobsHelper.PIRATE_SWORDS);
      map.put((Faction)ModFactions.MARINE.get(), MobsHelper.MARINE_SWORDS);
      map.put((Faction)ModFactions.BANDIT.get(), MobsHelper.BANDIT_SWORDS);
      return map;
   }

   public GruntEntity(EntityType<? extends GruntEntity> type, Level world, IMobDetails<OPEntity> faction) {
      super(type, world, (ResourceLocation[])null);
      if (world != null && !world.f_46443_) {
         initHuman(this);
         faction.init(this);
         ((IMobDetails)STYLES.get(this.m_217043_().m_188503_(STYLES.size()))).init(this);
         this.chooseTexture();
         boolean isHardDifficulty = this.isAboveNormalDifficulty();
         this.getEntityStats().setDoriki((double)500.0F + WyHelper.randomWithRange(0, 1500) + (double)(isHardDifficulty ? 500 : 0));
         this.getEntityStats().setBelly(5L + (long)WyHelper.randomWithRange(0, 5));
         HakiCapability.get(this).ifPresent((props) -> {
            props.setBusoshokuHakiExp((float)this.m_217043_().m_188503_(isHardDifficulty ? 20 : 10));
            props.setKenbunshokuHakiExp((float)this.m_217043_().m_188503_(isHardDifficulty ? 20 : 10));
         });
         this.m_21051_((Attribute)ModAttributes.GCD.get()).m_22100_((double)60.0F);
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
         if (isHardDifficulty) {
            MobsHelper.getBasicHakiAbilities(this, 1).forEach((core) -> this.f_21345_.m_25352_(0, new HakiAbilityWrapperGoal(this, core)));
         }

         if (this.m_217043_().m_188503_(10) < 3) {
            this.f_21345_.m_25352_(0, new RunAwayFromFearGoal(this, (double)1.5F, (double)3.0F));
         }

         this.f_21345_.m_25352_(1, new ImprovedMeleeAttackGoal(this, 1.15, true));
         if (this.getEntityStats().isMarine() || this.getEntityStats().isPirate()) {
            this.f_21345_.m_25352_(2, new HandleCannonGoal(this));
         }
      }

   }

   public static GruntEntity createMarineGrunt(EntityType<? extends GruntEntity> type, Level world) {
      return new GruntEntity(type, world, GruntEntity::initMarine);
   }

   public static GruntEntity createPirateGrunt(EntityType<? extends GruntEntity> type, Level world) {
      return new GruntEntity(type, world, GruntEntity::initPirate);
   }

   public static GruntEntity createBanditGrunt(EntityType<? extends GruntEntity> type, Level world) {
      return new GruntEntity(type, world, GruntEntity::initBandit);
   }

   public static AttributeSupplier.Builder createAttributes() {
      return OPEntity.createAttributes().m_22268_(Attributes.f_22277_, (double)30.0F).m_22268_(Attributes.f_22279_, (double)0.3F).m_22268_(Attributes.f_22281_, WyHelper.randomWithRange(1, 2)).m_22268_(Attributes.f_22276_, WyHelper.randomWithRange(10, 20)).m_22268_(Attributes.f_22284_, WyHelper.randomWithRange(0, 4));
   }

   protected boolean m_8028_() {
      return true;
   }

   public static boolean checkSpawnRules(EntityType<? extends OPEntity> type, ServerLevelAccessor world, MobSpawnType reason, BlockPos pos, RandomSource random) {
      double chance = random.m_188500_() * (double)100.0F;
      return chance > ServerConfig.getGruntSpawnChance() ? false : OPEntity.checkSpawnRules(type, world, reason, pos, random);
   }

   private static void initHuman(OPEntity entity) {
      entity.getEntityStats().setRace((Race)ModRaces.HUMAN.get());
   }

   private static void initFishman(OPEntity entity) {
      entity.getEntityStats().setRace((Race)ModRaces.FISHMAN.get());
      if (entity.getEntityStats().isPirate()) {
         entity.setTextures(MobsHelper.PIRATE_FISHMEN_TEXTURES);
      }

   }

   private static void initSwordsman(OPEntity entity) {
      entity.getEntityStats().setFightingStyle((FightingStyle)ModFightingStyles.SWORDSMAN.get());
      Faction faction = (Faction)entity.getEntityStats().getFaction().orElse((Object)null);
      if (faction != null) {
         ItemStack randomSword = entity.getRandomSword((List)MELEE_FACTION_WEAPONS.get(faction));
         if (entity.getEntityStats().isMarine() && randomSword.m_41720_() instanceof DyeableLeatherItem) {
            randomSword.m_41698_("display").m_128405_("color", 33980);
         }

         entity.m_8061_(EquipmentSlot.MAINHAND, randomSword);
         if (entity.isAboveNormalDifficulty()) {
            WeightedList<Supplier<Goal>> list = new WeightedList<Supplier<Goal>>(new Object[0]);
            list.addEntry((Supplier)() -> new JumpAbilityWrapperGoal(entity, (AbilityCore)HiryuKaenAbility.INSTANCE.get()), 3);
            list.addEntry((Supplier)() -> new DashAbilityWrapperGoal(entity, (AbilityCore)ShiShishiSonsonAbility.INSTANCE.get()), 3);
            list.addEntry((Supplier)() -> {
               ProjectileAbilityWrapperGoal<YakkodoriAbility> goal = new ProjectileAbilityWrapperGoal<YakkodoriAbility>(entity, (AbilityCore)YakkodoriAbility.INSTANCE.get());
               ((YakkodoriAbility)goal.getAbility()).addCanUseCheck(MobsHelper.BELOW_90_HP_CHECK);
               return goal;
            }, 1);
            list.pickN(entity.m_217043_(), 1).forEach((goal) -> entity.f_21345_.m_25352_(2, (Goal)goal.get()));
         }
      }
   }

   private static void initSniper(OPEntity entity) {
      entity.getEntityStats().setFightingStyle((FightingStyle)ModFightingStyles.SNIPER.get());
      entity.m_8061_(EquipmentSlot.MAINHAND, new ItemStack((ItemLike)ModWeapons.FLINTLOCK.get()));
      entity.f_21345_.m_25352_(1, new RangedAttackGoal(entity, (double)1.0F, 40, 15.0F));
      if (entity.isAboveNormalDifficulty()) {
         WeightedList<Supplier<Goal>> list = new WeightedList<Supplier<Goal>>(new Object[0]);
         list.addEntry((Supplier)() -> new ProjectileAbilityWrapperGoal(entity, (AbilityCore)KaenBoshiAbility.INSTANCE.get()), 5);
         list.addEntry((Supplier)() -> new ProjectileAbilityWrapperGoal(entity, (AbilityCore)TokuyoAburaBoshiAbility.INSTANCE.get()), 3);
         list.addEntry((Supplier)() -> new ProjectileAbilityWrapperGoal(entity, (AbilityCore)TetsuBoshiAbility.INSTANCE.get()), 1);
         list.addEntry((Supplier)() -> new ProjectileAbilityWrapperGoal(entity, (AbilityCore)SakuretsuSabotenBoshiAbility.INSTANCE.get()), 1);
         list.pickN(entity.m_217043_(), 1).forEach((goal) -> entity.f_21345_.m_25352_(2, (Goal)goal.get()));
      }
   }

   private static void initBrawler(OPEntity entity) {
      entity.getEntityStats().setFightingStyle((FightingStyle)ModFightingStyles.BRAWLER.get());
      if (entity.isAboveNormalDifficulty()) {
         WeightedList<Supplier<Goal>> list = new WeightedList<Supplier<Goal>>(new Object[0]);
         list.addEntry((Supplier)() -> new CloseMeleeAbilityWrapperGoal(entity, (AbilityCore)ChargedPunchAbility.INSTANCE.get()), 5);
         list.addEntry((Supplier)() -> new CloseMeleeAbilityWrapperGoal(entity, (AbilityCore)PunchRushAbility.INSTANCE.get()), 3);
         list.addEntry((Supplier)() -> new JumpAbilityWrapperGoal(entity, (AbilityCore)SlamAbility.INSTANCE.get()), 3);
         list.addEntry((Supplier)() -> new CloseMeleeAbilityWrapperGoal(entity, (AbilityCore)HakaiHoAbility.INSTANCE.get()), 1);
         list.pickN(entity.m_217043_(), 1).forEach((goal) -> entity.f_21345_.m_25352_(2, (Goal)goal.get()));
      }
   }

   private static void initMarine(OPEntity entity) {
      entity.getEntityStats().setFaction((Faction)ModFactions.MARINE.get());
      entity.setTextures(MobsHelper.MARINE_TEXTURES);
   }

   private static void initPirate(OPEntity entity) {
      entity.getEntityStats().setFaction((Faction)ModFactions.PIRATE.get());
      entity.setTextures(MobsHelper.PIRATE_TEXTURES);
      if (entity.m_9236_().m_213780_().m_188503_(10) < 3) {
         initFishman(entity);
      }

   }

   private static void initBandit(OPEntity entity) {
      entity.getEntityStats().setFaction((Faction)ModFactions.BANDIT.get());
      entity.setTextures(MobsHelper.BANDIT_TEXTURES);
   }

   public boolean canPerformRangedAttack(LivingEntity target) {
      return ItemsHelper.isBowOrGun(this.m_21205_());
   }

   @Nullable
   public Projectile getRangedProjectile(LivingEntity target) {
      NuProjectileEntity proj = new NormalBulletProjectile(this.m_9236_(), this);
      proj.setDamage(3.0F);
      if (this.isAboveNormalDifficulty()) {
         proj = new KairosekiBulletProjectile(this.m_9236_(), this);
         proj.setDamage(6.0F);
      }

      return proj;
   }
}
