package xyz.pixelatedw.mineminenomi.entities.mobs;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.UUID;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import xyz.pixelatedw.mineminenomi.abilities.ChargedCleaveAbility;
import xyz.pixelatedw.mineminenomi.abilities.SlamAbility;
import xyz.pixelatedw.mineminenomi.abilities.WeaponSpinAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.BrawlerPassiveBonusesAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.ChargedPunchAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.GenkotsuMeteorAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.HakaiHoAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.SpinningBrawlAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.SuplexAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.TackleAbility;
import xyz.pixelatedw.mineminenomi.abilities.ittoryu.ShiShishiSonsonAbility;
import xyz.pixelatedw.mineminenomi.abilities.ittoryu.YakkodoriAbility;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityExplosion;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusOperation;
import xyz.pixelatedw.mineminenomi.api.entities.IMobDetails;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.api.util.WeightedList;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.haki.HakiCapability;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.ClimbOutOfHoleGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.ImprovedMeleeAttackGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.AlwaysActiveAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.CloseMeleeAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.DashAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.GrabAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.HakiAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.JumpAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ProjectileAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.projectiles.CannonBallProjectile;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModFactions;
import xyz.pixelatedw.mineminenomi.init.ModFightingStyles;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRaces;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class BruteEntity extends OPEntity {
   private static final UUID TACKLE_COOLDOWN_BONUS_UUID = UUID.fromString("2839290d-9070-45fb-a7ac-465405562ec6");
   private static final List<IMobDetails<OPEntity>> STYLES = Lists.newArrayList(new IMobDetails[]{BruteEntity::initSwordsman, BruteEntity::initSniper, BruteEntity::initBrawler});

   public BruteEntity(EntityType<? extends BruteEntity> type, Level world, IMobDetails<OPEntity> faction) {
      super(type, world, (ResourceLocation[])null);
      if (world != null && !world.f_46443_) {
         initHuman(this);
         faction.init(this);
         ((IMobDetails)STYLES.get(this.m_217043_().m_188503_(STYLES.size()))).init(this);
         this.chooseTexture();
         boolean isHardDifficulty = this.isAboveNormalDifficulty();
         this.getEntityStats().setDoriki((double)3500.0F + WyHelper.randomWithRange(0, 1500) + (double)(isHardDifficulty ? 500 : 0));
         this.getEntityStats().setBelly(15L + (long)WyHelper.randomWithRange(0, 30));
         HakiCapability.get(this).ifPresent((props) -> {
            props.setBusoshokuHakiExp((float)(35 + this.m_217043_().m_188503_(isHardDifficulty ? 20 : 10)));
            props.setKenbunshokuHakiExp((float)(25 + this.m_217043_().m_188503_(isHardDifficulty ? 20 : 10)));
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
         this.f_21345_.m_25352_(1, new ImprovedMeleeAttackGoal(this, (double)1.0F, true));
         this.m_21051_((Attribute)ModAttributes.TOUGHNESS.get()).m_22100_((double)4.0F);
      }

   }

   public static BruteEntity createMarineBrute(EntityType<? extends BruteEntity> type, Level world) {
      return new BruteEntity(type, world, BruteEntity::initMarine);
   }

   public static BruteEntity createPirateBrute(EntityType<? extends BruteEntity> type, Level world) {
      return new BruteEntity(type, world, BruteEntity::initPirate);
   }

   public static BruteEntity createBanditBrute(EntityType<? extends BruteEntity> type, Level world) {
      return new BruteEntity(type, world, BruteEntity::initBandit);
   }

   public static AttributeSupplier.Builder createAttributes() {
      return OPEntity.createAttributes().m_22268_(Attributes.f_22277_, (double)40.0F).m_22268_(Attributes.f_22279_, (double)0.25F).m_22268_(Attributes.f_22281_, WyHelper.randomWithRange(3, 5)).m_22268_(Attributes.f_22276_, WyHelper.randomWithRange(50, 80)).m_22268_(Attributes.f_22284_, WyHelper.randomWithRange(8, 12));
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
      return chance > ServerConfig.getBruteSpawnChance() ? false : OPEntity.checkSpawnRules(type, world, reason, pos, random);
   }

   private static void initSwordsman(OPEntity entity) {
      entity.getEntityStats().setFightingStyle((FightingStyle)ModFightingStyles.SWORDSMAN.get());
      ItemStack randomSword = entity.getRandomSword(MobsHelper.BRUTE_SWORDS);
      if (entity.getEntityStats().isMarine() && randomSword.m_41720_() instanceof DyeableLeatherItem) {
         randomSword.m_41698_("display").m_128405_("color", 33980);
      }

      entity.m_8061_(EquipmentSlot.MAINHAND, randomSword);
      WeightedList<Supplier<Goal>> list = new WeightedList<Supplier<Goal>>(new Object[0]);
      list.addEntry((Supplier)() -> new CloseMeleeAbilityWrapperGoal(entity, (AbilityCore)ChargedCleaveAbility.INSTANCE.get()), 3);
      list.addEntry((Supplier)() -> new JumpAbilityWrapperGoal(entity, (AbilityCore)SlamAbility.INSTANCE.get()), 3);
      list.addEntry((Supplier)() -> new DashAbilityWrapperGoal(entity, (AbilityCore)ShiShishiSonsonAbility.INSTANCE.get()), 2);
      list.addEntry((Supplier)() -> {
         ProjectileAbilityWrapperGoal<YakkodoriAbility> goal = new ProjectileAbilityWrapperGoal<YakkodoriAbility>(entity, (AbilityCore)YakkodoriAbility.INSTANCE.get());
         ((YakkodoriAbility)goal.getAbility()).addCanUseCheck(MobsHelper.BELOW_90_HP_CHECK);
         return goal;
      }, 2);
      list.addEntry((Supplier)() -> new CloseMeleeAbilityWrapperGoal(entity, (AbilityCore)WeaponSpinAbility.INSTANCE.get()), 2);
      list.pickN(entity.m_217043_(), entity.isAboveNormalDifficulty() ? 2 : 1).forEach((goal) -> entity.f_21345_.m_25352_(2, (Goal)goal.get()));
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

   private static void initSniper(OPEntity entity) {
      entity.getEntityStats().setFightingStyle((FightingStyle)ModFightingStyles.SNIPER.get());
      entity.m_8061_(EquipmentSlot.MAINHAND, new ItemStack((ItemLike)ModWeapons.BAZOOKA.get()));
      entity.f_21345_.m_25352_(1, new RangedAttackGoal(entity, (double)1.0F, entity.isAboveNormalDifficulty() ? 40 : 60, 40.0F));
   }

   private static void initBrawler(OPEntity entity) {
      entity.getEntityStats().setFightingStyle((FightingStyle)ModFightingStyles.BRAWLER.get());
      entity.f_21345_.m_25352_(0, new AlwaysActiveAbilityWrapperGoal(entity, (AbilityCore)BrawlerPassiveBonusesAbility.INSTANCE.get()));
      WeightedList<Supplier<Goal>> list = new WeightedList<Supplier<Goal>>(new Object[0]);
      list.addEntry((Supplier)() -> new CloseMeleeAbilityWrapperGoal(entity, (AbilityCore)ChargedPunchAbility.INSTANCE.get()), 4);
      list.addEntry((Supplier)() -> new ProjectileAbilityWrapperGoal(entity, (AbilityCore)GenkotsuMeteorAbility.INSTANCE.get()), 4);
      list.addEntry((Supplier)() -> new JumpAbilityWrapperGoal(entity, (AbilityCore)SlamAbility.INSTANCE.get()), 3);
      list.addEntry((Supplier)() -> new GrabAbilityWrapperGoal(entity, (AbilityCore)SuplexAbility.INSTANCE.get()), 2);
      list.addEntry((Supplier)() -> new GrabAbilityWrapperGoal(entity, (AbilityCore)SpinningBrawlAbility.INSTANCE.get()), 2);
      list.addEntry((Supplier)() -> {
         DashAbilityWrapperGoal<TackleAbility> tackleWrapper = new DashAbilityWrapperGoal<TackleAbility>(entity, (AbilityCore)TackleAbility.INSTANCE.get());
         ((TackleAbility)tackleWrapper.getAbility()).getComponent((AbilityComponentKey)ModAbilityComponents.COOLDOWN.get()).ifPresent((comp) -> comp.getBonusManager().addBonus(TACKLE_COOLDOWN_BONUS_UUID, "Tackle Cooldown Bonus", BonusOperation.MUL, 2.0F));
         return tackleWrapper;
      }, 2);
      list.addEntry((Supplier)() -> new CloseMeleeAbilityWrapperGoal(entity, (AbilityCore)HakaiHoAbility.INSTANCE.get()), 2);
      list.pickN(entity.m_217043_(), entity.isAboveNormalDifficulty() ? 2 : 1).forEach((goal) -> entity.f_21345_.m_25352_(2, (Goal)goal.get()));
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
      return this.m_21205_().m_41720_().equals(ModWeapons.BAZOOKA.get());
   }

   @Nullable
   public Projectile getRangedProjectile(LivingEntity target) {
      NuProjectileEntity proj = new CannonBallProjectile(this.m_9236_(), this);
      proj.setDamage(5.0F);
      if (this.isAboveNormalDifficulty()) {
         proj.setDamage(10.0F);
      }

      proj.clearBlockHitEvents();
      proj.addBlockHitEvent(100, (result) -> {
         AbilityExplosion explosion = new AbilityExplosion(this, (IAbility)null, proj.m_20185_(), proj.m_20186_(), proj.m_20189_(), 2.0F);
         explosion.setStaticDamage(5.0F);
         explosion.setDestroyBlocks(false);
         explosion.setSmokeParticles((ParticleEffect)ModParticleEffects.COMMON_EXPLOSION.get(), CommonExplosionParticleEffect.EXPLOSION2);
         explosion.m_46061_();
      });
      return proj;
   }
}
