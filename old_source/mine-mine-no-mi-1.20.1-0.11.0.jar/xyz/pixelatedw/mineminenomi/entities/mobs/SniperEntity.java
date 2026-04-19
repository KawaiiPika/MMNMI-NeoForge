package xyz.pixelatedw.mineminenomi.entities.mobs;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import xyz.pixelatedw.mineminenomi.abilities.sniper.KaenBoshiAbility;
import xyz.pixelatedw.mineminenomi.abilities.sniper.KemuriBoshiAbility;
import xyz.pixelatedw.mineminenomi.abilities.sniper.SakuretsuSabotenBoshiAbility;
import xyz.pixelatedw.mineminenomi.abilities.sniper.TetsuBoshiAbility;
import xyz.pixelatedw.mineminenomi.abilities.sniper.TokuyoAburaBoshiAbility;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.entities.IMobDetails;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.entities.ai.NPCPhase;
import xyz.pixelatedw.mineminenomi.api.entities.ai.NPCPhaseManager;
import xyz.pixelatedw.mineminenomi.api.entities.ai.SimplePhase;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.api.util.WeightedList;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.haki.HakiCapability;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.ClimbOutOfHoleGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.ImprovedMeleeAttackGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.StyleSwitchGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.HakiAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ProjectileAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.projectiles.KairosekiBulletProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.NormalBulletProjectile;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModFactions;
import xyz.pixelatedw.mineminenomi.init.ModFightingStyles;
import xyz.pixelatedw.mineminenomi.init.ModRaces;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;

public class SniperEntity extends OPEntity {
   private static final List<IMobDetails<SniperEntity>> STYLES = Lists.newArrayList(new IMobDetails[]{SniperEntity::initSniper});
   private static final int SWITCH_RANGE = 5;
   private final NPCPhaseManager phaseManager = new NPCPhaseManager(this);
   private NPCPhase<SniperEntity> meleePhase;
   private NPCPhase<SniperEntity> rangePhase;

   public SniperEntity(EntityType<? extends SniperEntity> type, Level world, IMobDetails<OPEntity> faction) {
      super(type, world, (ResourceLocation[])null);
      if (world != null && !world.f_46443_) {
         this.meleePhase = new SimplePhase<SniperEntity>("Melee Phase", this);
         this.rangePhase = new SimplePhase<SniperEntity>("Range Phase", this);
         initHuman(this);
         faction.init(this);
         ((IMobDetails)STYLES.get(this.m_217043_().m_188503_(STYLES.size()))).init(this);
         this.chooseTexture();
         boolean isHardDifficulty = this.isAboveNormalDifficulty();
         this.getEntityStats().setDoriki((double)3000.0F + WyHelper.randomWithRange(0, 1500) + (double)(isHardDifficulty ? 500 : 0));
         this.getEntityStats().setBelly(15L + (long)WyHelper.randomWithRange(0, 30));
         HakiCapability.get(this).ifPresent((props) -> {
            props.setBusoshokuHakiExp((float)(25 + this.m_217043_().m_188503_(isHardDifficulty ? 20 : 10)));
            props.setKenbunshokuHakiExp((float)(35 + this.m_217043_().m_188503_(isHardDifficulty ? 20 : 10)));
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
         MobsHelper.getBasicHakiAbilities(this, 30).forEach((core) -> this.f_21345_.m_25352_(0, new HakiAbilityWrapperGoal(this, core)));
         this.f_21345_.m_25352_(0, new ClimbOutOfHoleGoal(this));
         this.meleePhase.addGoal(1, new ImprovedMeleeAttackGoal(this, (double)1.0F, true));
         this.phaseManager.setPhase(this.rangePhase);
      }

   }

   public static SniperEntity createMarineSniper(EntityType<? extends SniperEntity> type, Level world) {
      return new SniperEntity(type, world, SniperEntity::initMarine);
   }

   public static SniperEntity createPirateSniper(EntityType<? extends SniperEntity> type, Level world) {
      return new SniperEntity(type, world, SniperEntity::initPirate);
   }

   public static SniperEntity createBanditSniper(EntityType<? extends SniperEntity> type, Level world) {
      return new SniperEntity(type, world, SniperEntity::initBandit);
   }

   public static AttributeSupplier.Builder createAttributes() {
      return OPEntity.createAttributes().m_22268_(Attributes.f_22277_, (double)50.0F).m_22268_(Attributes.f_22279_, (double)0.25F).m_22268_(Attributes.f_22281_, WyHelper.randomWithRange(2, 4)).m_22268_(Attributes.f_22276_, WyHelper.randomWithRange(20, 40)).m_22268_(Attributes.f_22284_, WyHelper.randomWithRange(1, 3));
   }

   private FightingStyle onStyleSwitched(Mob entity, LivingEntity target, FightingStyle currentStyle) {
      if (currentStyle.equals(ModFightingStyles.SNIPER.get())) {
         boolean hasTargetNear = GoalHelper.isWithinDistance(entity, target, (double)5.0F);
         if (hasTargetNear) {
            this.phaseManager.setPhase(this.meleePhase);
            return (FightingStyle)ModFightingStyles.BRAWLER.get();
         }
      } else {
         boolean hasTargetOutsideRange = GoalHelper.isOutsideDistance(entity, target, (double)5.0F);
         if (hasTargetOutsideRange) {
            this.phaseManager.setPhase(this.rangePhase);
            return (FightingStyle)ModFightingStyles.SNIPER.get();
         }
      }

      return currentStyle;
   }

   public void m_8024_() {
      this.phaseManager.tick();
   }

   protected boolean m_8028_() {
      return true;
   }

   public static boolean checkSpawnRules(EntityType<? extends OPEntity> type, ServerLevelAccessor world, MobSpawnType reason, BlockPos pos, RandomSource random) {
      if (reason != MobSpawnType.SPAWNER) {
         BlockPos worldSpawn = new BlockPos(world.m_6106_().m_6789_(), world.m_6106_().m_6527_(), world.m_6106_().m_6526_());
         if (pos.m_123314_(worldSpawn, (double)256.0F)) {
            return false;
         }
      }

      double chance = random.m_188500_() * (double)100.0F;
      return chance > ServerConfig.getSniperSpawnChance() ? false : OPEntity.checkSpawnRules(type, world, reason, pos, random);
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

   private static void initSniper(SniperEntity entity) {
      entity.getEntityStats().setFightingStyle((FightingStyle)ModFightingStyles.SNIPER.get());
      entity.m_8061_(EquipmentSlot.MAINHAND, new ItemStack((ItemLike)ModWeapons.SENRIKU.get()));
      GoalSelector var10000 = entity.f_21345_;
      Objects.requireNonNull(entity);
      var10000.m_25352_(0, new StyleSwitchGoal(entity, entity::onStyleSwitched));
      entity.rangePhase.addGoal(1, new RangedAttackGoal(entity, (double)1.0F, 60, 60.0F));
      WeightedList<Supplier<Goal>> list = new WeightedList<Supplier<Goal>>(new Object[0]);
      list.addEntry((Supplier)() -> new ProjectileAbilityWrapperGoal(entity, (AbilityCore)KaenBoshiAbility.INSTANCE.get()), 6);
      list.addEntry((Supplier)() -> new ProjectileAbilityWrapperGoal(entity, (AbilityCore)TokuyoAburaBoshiAbility.INSTANCE.get()), 4);
      list.addEntry((Supplier)() -> new ProjectileAbilityWrapperGoal(entity, (AbilityCore)KemuriBoshiAbility.INSTANCE.get()), 4);
      list.addEntry((Supplier)() -> new ProjectileAbilityWrapperGoal(entity, (AbilityCore)TetsuBoshiAbility.INSTANCE.get()), 3);
      list.addEntry((Supplier)() -> new ProjectileAbilityWrapperGoal(entity, (AbilityCore)SakuretsuSabotenBoshiAbility.INSTANCE.get()), 2);
      list.pickN(entity.m_217043_(), entity.isAboveNormalDifficulty() ? 3 : 2).forEach((goal) -> entity.rangePhase.addGoal(2, (Goal)goal.get()));
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
      if (this.isAboveNormalDifficulty()) {
         proj = new KairosekiBulletProjectile(this.m_9236_(), this);
      }

      return proj;
   }
}
