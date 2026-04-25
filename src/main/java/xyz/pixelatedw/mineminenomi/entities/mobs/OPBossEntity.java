package xyz.pixelatedw.mineminenomi.entities.mobs;


import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

import net.neoforged.neoforge.entity.IEntityWithComplexSpawn;
import net.minecraft.network.RegistryFriendlyByteBuf;
import xyz.pixelatedw.mineminenomi.api.ILivingEntityExtension;

import xyz.pixelatedw.mineminenomi.abilities.blackleg.BlackLegPassiveBonusesAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.BrawlerPassiveBonusesAbility;
import xyz.pixelatedw.mineminenomi.api.NuWorld;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeInfo;
import xyz.pixelatedw.mineminenomi.api.challenges.IChallengeBoss;
import xyz.pixelatedw.mineminenomi.api.challenges.InProgressChallenge;
import xyz.pixelatedw.mineminenomi.api.crew.Crew;
import xyz.pixelatedw.mineminenomi.api.entities.ServerOPBossEvent;
import xyz.pixelatedw.mineminenomi.api.entities.ai.NPCPhaseManager;
import xyz.pixelatedw.mineminenomi.api.entities.revenge.RevengeMeter;
import xyz.pixelatedw.mineminenomi.api.helpers.AttributeHelper;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityDataBase;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.DevilFruitBase;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.DevilFruitCapability;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.IDevilFruit;
import xyz.pixelatedw.mineminenomi.data.entity.haki.HakiCapability;
import xyz.pixelatedw.mineminenomi.data.entity.haki.HakiDataBase;
import xyz.pixelatedw.mineminenomi.data.entity.haki.IHakiData;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsBase;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.data.world.FactionsWorldData;
import xyz.pixelatedw.mineminenomi.entities.ai.controllers.HumanoidSwimMoveControl;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.AlwaysActiveAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.init.ModEffects;

public class OPBossEntity extends PathfinderMob implements IChallengeBoss, IEntityWithComplexSpawn, ILivingEntityExtension {
   private final ServerOPBossEvent bossInfo;
   private final ChallengeInfo challengeInfo;
   private final NPCPhaseManager phaseManager;
   protected final IEntityStats entityStats;
   protected final IAbilityData abilityData;
   protected final IDevilFruit devilFruitData;
   protected final IHakiData hakiCapability;
   private boolean isInitialized;
   private float damageCeiling;
   private boolean hasEmptyTargetsList;
   private int movementType;
   private BlockPos lastSafePosition;
   private MoveControl groundMovementController;
   private MoveControl waterMovementController;

   public OPBossEntity(EntityType<? extends OPBossEntity> type, Level world) {
      this(type, world, (InProgressChallenge)null);
   }

   public OPBossEntity(EntityType<? extends OPBossEntity> type, InProgressChallenge inProgressChallenge) {
      this(type, inProgressChallenge.getShard(), inProgressChallenge);
   }

   private OPBossEntity(EntityType<? extends OPBossEntity> type, Level world, @Nullable InProgressChallenge inProgressChallenge) {
      super(type, world);
      this.bossInfo = new ServerOPBossEvent(this.getName());
      this.challengeInfo = new ChallengeInfo();
      this.phaseManager = new NPCPhaseManager(this);
      this.movementType = 0;
      this.groundMovementController = new MoveControl(this);
      this.waterMovementController = new HumanoidSwimMoveControl(this);
      this.entityStats = (IEntityStats)EntityStatsCapability.get(this).orElse(new EntityStatsBase(this));
      this.abilityData = (IAbilityData)AbilityCapability.get(this).orElse(new AbilityDataBase(this));
      this.devilFruitData = (IDevilFruit)DevilFruitCapability.get(this).orElse(new DevilFruitBase(this));
      this.hakiCapability = (IHakiData)HakiCapability.get(this).orElse(new HakiDataBase(this));
      this.preInit();
      this.challengeInfo.setInProgressChallenge(inProgressChallenge);
   }

   public void preInit() {
   }

   public void initBoss() {
   }

   private final void baseInitBoss() {
      this.m_21051_(net.minecraft.world.entity.ai.attributes.Attributes.STEP_HEIGHT).setBaseValue(1.0D);
      this.setDamageCeiling(this.isDifficultyStandard() ? 20.0F : 30.0F);
      this.initBoss();
      this.applyDifficultyModifiers(this);
      if (this.entityStats.isBrawler()) {
         this.goalSelector.addGoal(0, new AlwaysActiveAbilityWrapperGoal(this, (AbilityCore)BrawlerPassiveBonusesAbility.INSTANCE.get()));
      } else if (this.entityStats.isBlackLeg()) {
         this.goalSelector.addGoal(0, new AlwaysActiveAbilityWrapperGoal(this, (AbilityCore)BlackLegPassiveBonusesAbility.INSTANCE.get()));
      }

      AttributeHelper.updateToughnessAttribute(this);
      this.m_5634_(this.m_21233_());
   }

   public void m_8119_() {
      if (!this.isInitialized) {
         if (!this.m_9236_().f_46443_) {
            this.baseInitBoss();
            this.lastSafePosition = this.m_20183_();
         }

         this.isInitialized = true;
      }

      this.bossInfo.tick(this);
      super.m_8119_();
      if (!this.m_9236_().f_46443_) {
         if (this.m_6084_()) {
            for(RevengeMeter meter : this.getRevengeMeters()) {
               meter.tick();
            }
         }

         this.trySwitchingMovementMode();
         if (this.m_9236_().m_46467_() % 20L == 0L) {
            boolean isSuffocating = this.m_9236_().m_8055_(this.m_20183_()).m_60828_(this.m_9236_(), this.m_20183_());
            boolean isOutsideBounds = false; // TODO: Fix NuWorld.isWithinChallengeArenaBounds(this.m_9236_(), this.m_20183_());
            if (!isSuffocating) {
               this.lastSafePosition = this.m_20183_();
            } else if (isSuffocating || isOutsideBounds) {
               this.m_20035_(this.lastSafePosition, this.m_146908_(), this.m_146909_());
            }
         }

         if (this.m_5448_() == null && !this.hasEmptyTargetsList) {
            List<LivingEntity> challengers = this.challengeInfo.getChallengerGroup();
            if (challengers.isEmpty()) {
               this.hasEmptyTargetsList = true;
               return;
            }

            for(LivingEntity target : challengers) {
               if (this.canTarget(target)) {
                  this.m_6710_(target);
                  break;
               }
            }
         } else if (this.m_5448_() != null) {
            LivingEntity target = this.m_5448_();
            if (!this.canTarget(target)) {
               this.m_6710_((LivingEntity)null);
            }
         }
      }

   }

   private boolean canTarget(LivingEntity target) {
      return !target.m_21023_((MobEffect)ModEffects.CHALLENGE_FAILED.get());
   }

   private void trySwitchingMovementMode() {
      if (this.m_20069_() && this.movementType == 0) {
         this.switchToWaterMovement();
      } else if (!this.m_20069_() && this.movementType == 1) {
         this.switchToGroundMovement();
      }

   }

   public void m_8107_() {
      this.m_21203_();
      super.m_8107_();
   }

   public void m_8024_() {
      this.phaseManager.tick();
   }

   public void m_6457_(ServerPlayer player) {
      super.m_6457_(player);
      if (this.bossInfo != null) {
         this.bossInfo.m_6543_(player);
      }

   }

   public void m_6452_(ServerPlayer player) {
      super.m_6452_(player);
      if (this.bossInfo != null) {
         this.bossInfo.m_6539_(player);
      }

   }

   public void m_142687_(Entity.RemovalReason reason) {
      if (this.entityStats.isPirate()) {
         FactionsWorldData worldData = FactionsWorldData.get();
         if (worldData != null) {
            Crew crew = worldData.getCrewWithMember(this.m_20148_());
            if (crew != null) {
               crew.removeMember(this.m_9236_(), this.m_20148_());
            }
         }
      }

      super.m_142687_(reason);
   }

   public SpawnGroupData m_6518_(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag dataTag) {
      spawnData = super.m_6518_(world, difficulty, reason, spawnData, dataTag);
      this.m_21559_(false);
      return spawnData;
   }

   public boolean m_6785_(double distance) {
      return false;
   }

   public void writeSpawnData(RegistryFriendlyByteBuf buffer) {
   }

   public void readSpawnData(RegistryFriendlyByteBuf buffer) {
   }

   public ChallengeInfo getChallengeInfo() {
      return this.challengeInfo;
   }

   public NPCPhaseManager getPhaseManager() {
      return this.phaseManager;
   }

   public void setDamageCeiling(float damageCeiling) {
      this.damageCeiling = damageCeiling;
   }

   public float getDamageCeiling() {
      return this.damageCeiling;
   }

   public float getDifficultyScaling() {
      return this.challengeInfo.getScaling();
   }

   public void switchToWaterMovement() {
      this.f_21342_ = this.waterMovementController;
      this.movementType = 1;
   }

   public void switchToGroundMovement() {
      this.f_21342_ = this.groundMovementController;
      this.movementType = 0;
   }

   @Override
   public void onDamageTake(net.minecraft.world.damagesource.DamageSource source, float amount) {
      // Subclasses can override this to implement custom damage responses
   }
   public java.util.List<RevengeMeter> getRevengeMeters() {
      return java.util.Collections.emptyList();
   }

   public void applyDifficultyModifiers(LivingEntity entity) {}
}
