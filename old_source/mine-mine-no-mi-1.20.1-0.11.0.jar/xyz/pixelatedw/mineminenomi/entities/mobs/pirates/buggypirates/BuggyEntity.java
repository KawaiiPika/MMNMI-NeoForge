package xyz.pixelatedw.mineminenomi.entities.mobs.pirates.buggypirates;

import java.util.UUID;
import java.util.function.Predicate;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xyz.pixelatedw.mineminenomi.abilities.BombThrowAbility;
import xyz.pixelatedw.mineminenomi.abilities.SlashDamageImmunityAbility;
import xyz.pixelatedw.mineminenomi.abilities.bara.BaraBaraCarAbility;
import xyz.pixelatedw.mineminenomi.abilities.bara.BaraBaraFestivalAbility;
import xyz.pixelatedw.mineminenomi.abilities.bara.BaraBaraHoAbility;
import xyz.pixelatedw.mineminenomi.abilities.bara.BaraSplitAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiFullBodyHardeningAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiInternalDestructionAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.KenbunshokuHakiFutureSightAbility;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusOperation;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.challenges.InProgressChallenge;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.entities.ai.NPCPhase;
import xyz.pixelatedw.mineminenomi.api.entities.ai.SimplePhase;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.entities.revenge.DeadzoneRevengeCheck;
import xyz.pixelatedw.mineminenomi.api.entities.revenge.RevengeMeter;
import xyz.pixelatedw.mineminenomi.api.entities.revenge.TargetFlyStallingRevengeCheck;
import xyz.pixelatedw.mineminenomi.api.entities.revenge.TargetRunningAwayRevengeCheck;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.TargetHelper;
import xyz.pixelatedw.mineminenomi.api.items.IMultiChannelColorItem;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.data.world.FactionsWorldData;
import xyz.pixelatedw.mineminenomi.entities.BombEntity;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.ClimbOutOfHoleGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.DashDodgeProjectilesGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.DashDodgeTargetGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.ImprovedMeleeAttackGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ActiveBuffAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ActiveGuardAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.AlwaysActiveAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.CloseMeleeAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.HakiAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ProjectileAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.buggy.BuggyDashGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.buggy.BuggyPhaseSwitcherGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.buggy.BuggyRunningGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPBossEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModArmors;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModFactions;
import xyz.pixelatedw.mineminenomi.init.ModFruits;
import xyz.pixelatedw.mineminenomi.init.ModMobs;
import xyz.pixelatedw.mineminenomi.init.ModNPCGroups;
import xyz.pixelatedw.mineminenomi.init.ModRaces;
import xyz.pixelatedw.mineminenomi.items.AkumaNoMiItem;

public class BuggyEntity extends OPBossEntity {
   private static final byte BARA_RESET_EVENT = 100;
   private static final byte BARA_HO_EVENT = 101;
   private static final byte BARA_SPLIT_EVENT = 102;
   private static final byte BARA_FESTIVAL_EVENT = 103;
   private static final byte BARA_CAR_START_EVENT = 104;
   private static final byte BARA_CAR_END_EVENT = 105;
   private static final byte FIREWORKS_EVENT = 110;
   private static final CompoundTag FIREWORKS_TAG = createFireworksTag();
   private static final UUID BARA_CAR_COOLDOWN_UUID = UUID.fromString("1d375261-e119-484c-8df3-c92da926cd6c");
   public int clientCarState = 0;
   public int clientState = 0;
   private int clientTick = 0;
   private boolean tickClient = false;
   private NPCPhase<BuggyEntity> normalPhase = new SimplePhase<BuggyEntity>("Normal Phase", this);
   private NPCPhase<BuggyEntity> carPhase = new SimplePhase<BuggyEntity>("Car Phase", this);
   private final RevengeMeter kitingMeter = new RevengeMeter(this, 100, 0);
   private ActiveGuardAbilityWrapperGoal<BaraBaraFestivalAbility> baraFestivalWrapper;
   private ActiveBuffAbilityWrapperGoal<BaraBaraCarAbility> baraCarWrapper;
   private boolean splitBombs = false;

   public BuggyEntity(EntityType<BuggyEntity> type, Level world) {
      super(type, world);
   }

   public BuggyEntity(InProgressChallenge challenge) {
      super((EntityType)ModMobs.BUGGY.get(), challenge);
   }

   public void initBoss() {
      ItemStack capeStack = new ItemStack((ItemLike)ModArmors.PIRATE_CAPTAIN_CAPE.get());
      IMultiChannelColorItem.dyeArmor(capeStack, 0, 16146690);
      ItemStack bicorneStack = ((Item)ModArmors.BICORNE.get()).m_7968_();
      IMultiChannelColorItem.dyeArmor(bicorneStack, 0, 13459968);
      this.m_8061_(EquipmentSlot.HEAD, bicorneStack);
      this.m_8061_(EquipmentSlot.CHEST, capeStack);
      FactionsWorldData worldData = FactionsWorldData.get();
      this.entityStats.setFaction((Faction)ModFactions.PIRATE.get());
      this.entityStats.setRace((Race)ModRaces.HUMAN.get());
      this.devilFruitData.setDevilFruit((AkumaNoMiItem)ModFruits.BARA_BARA_NO_MI.get());
      worldData.addTemporaryCrewMember(ModNPCGroups.BUGGY_PIRATES, this);
      this.m_21051_((Attribute)ModAttributes.TOUGHNESS.get()).m_22100_((double)2.0F);
      if (this.getChallengeInfo().isDifficultyStandard()) {
         this.entityStats.setDoriki((double)2000.0F);
         this.m_21051_((Attribute)ModAttributes.GCD.get()).m_22100_((double)40.0F);
         this.m_21051_((Attribute)ModAttributes.PUNCH_DAMAGE.get()).m_22100_((double)4.0F);
      } else {
         this.entityStats.setDoriki((double)10000.0F);
         this.hakiCapability.setBusoshokuHakiExp(100.0F);
         this.hakiCapability.setKenbunshokuHakiExp(100.0F);
         this.m_21051_((Attribute)ModAttributes.GCD.get()).m_22100_((double)20.0F);
         this.m_21051_((Attribute)ModAttributes.TOUGHNESS.get()).m_22100_((double)4.0F);
         this.m_21051_((Attribute)ModAttributes.PUNCH_DAMAGE.get()).m_22100_((double)4.0F);
         this.m_21051_(Attributes.f_22276_).m_22100_((double)300.0F);
         this.m_21051_(Attributes.f_22284_).m_22100_((double)10.0F);
         this.m_21051_(Attributes.f_22285_).m_22100_((double)8.0F);
      }

      MobsHelper.addBasicNPCGoals(this);
      ProjectileAbilityWrapperGoal<BaraBaraHoAbility> baraHoWrapper = new ProjectileAbilityWrapperGoal<BaraBaraHoAbility>(this, (AbilityCore)BaraBaraHoAbility.INSTANCE.get());
      ((BaraBaraHoAbility)baraHoWrapper.getAbility()).getComponent((AbilityComponentKey)ModAbilityComponents.COOLDOWN.get()).ifPresent((comp) -> {
         comp.addStartEvent((e, a) -> {
            this.m_9236_().m_7605_(this, (byte)101);
            this.clientTick = 20;
            this.tickClient = true;
         });
         comp.addTickEvent((e, a) -> {
            if (this.tickClient && this.clientTick-- <= 0) {
               this.m_9236_().m_7605_(this, (byte)100);
               this.tickClient = false;
            }

         });
      });
      ((BaraBaraHoAbility)baraHoWrapper.getAbility()).getComponent((AbilityComponentKey)ModAbilityComponents.PROJECTILE.get()).ifPresent((comp) -> comp.addAfterShootEvent(100, (e) -> this.m_9236_().m_7605_(this, (byte)100)));
      this.baraFestivalWrapper = new ActiveGuardAbilityWrapperGoal<BaraBaraFestivalAbility>(this, (AbilityCore)BaraBaraFestivalAbility.INSTANCE.get());
      ((BaraBaraFestivalAbility)this.baraFestivalWrapper.getAbility()).addTickEvent((e, a) -> {
         LivingEntity target = this.m_5448_();
         if (target != null) {
            boolean hasRecentHit = this.m_21213_() > 0 && this.m_9236_().m_46467_() - (long)this.m_21213_() > 100L;
            boolean hasEnemyNearby = GoalHelper.isWithinDistance(this, target, (double)5.0F);
            if (hasRecentHit && hasEnemyNearby) {
               ((BaraBaraFestivalAbility)this.baraFestivalWrapper.getAbility()).switchModeToShield(this);
            } else {
               ((BaraBaraFestivalAbility)this.baraFestivalWrapper.getAbility()).switchModeToAttack(this);
            }
         }

      });
      ((BaraBaraFestivalAbility)this.baraFestivalWrapper.getAbility()).getComponent((AbilityComponentKey)ModAbilityComponents.CONTINUOUS.get()).ifPresent((comp) -> {
         comp.addStartEvent((e, a) -> this.m_9236_().m_7605_(this, (byte)103));
         comp.addEndEvent((e, a) -> this.m_9236_().m_7605_(this, (byte)100));
      });
      CloseMeleeAbilityWrapperGoal<BaraSplitAbility> baraSplitWrapper = new CloseMeleeAbilityWrapperGoal<BaraSplitAbility>(this, (AbilityCore)BaraSplitAbility.INSTANCE.get());
      ((BaraSplitAbility)baraSplitWrapper.getAbility()).addCanUseCheck((e, a) -> GoalHelper.isOutsideDistance(this, this.m_5448_(), (double)20.0F) ? Result.fail((Component)null) : Result.success());
      ((BaraSplitAbility)baraSplitWrapper.getAbility()).getComponent((AbilityComponentKey)ModAbilityComponents.CONTINUOUS.get()).ifPresent(this::setBaraSplitLogic);
      ProjectileAbilityWrapperGoal<BombThrowAbility> bombThrowWrapper = new ProjectileAbilityWrapperGoal<BombThrowAbility>(this, (AbilityCore)BombThrowAbility.INSTANCE.get());
      ((BombThrowAbility)bombThrowWrapper.getAbility()).getComponent((AbilityComponentKey)ModAbilityComponents.COOLDOWN.get()).ifPresent((comp) -> comp.getBonusManager().addBonus(BARA_CAR_COOLDOWN_UUID, "Bara Car Cooldown Bonus", BonusOperation.MUL, 0.5F));
      this.baraCarWrapper = new ActiveBuffAbilityWrapperGoal<BaraBaraCarAbility>(this, (AbilityCore)BaraBaraCarAbility.INSTANCE.get());
      Predicate<LivingEntity> canDash = (entity) -> {
         if (((BaraBaraCarAbility)this.baraCarWrapper.getAbility()).isContinuous()) {
            return false;
         } else {
            return !((BaraSplitAbility)baraSplitWrapper.getAbility()).isContinuous();
         }
      };
      this.kitingMeter.addCheck(new TargetFlyStallingRevengeCheck(1));
      this.kitingMeter.addCheck(new TargetRunningAwayRevengeCheck(10, 10.0F));
      this.kitingMeter.addCheck(new DeadzoneRevengeCheck(5));
      this.f_21345_.m_25352_(0, new ClimbOutOfHoleGoal(this));
      this.f_21345_.m_25352_(0, new BuggyPhaseSwitcherGoal(this));
      this.f_21345_.m_25352_(0, new AlwaysActiveAbilityWrapperGoal(this, (AbilityCore)SlashDamageImmunityAbility.INSTANCE.get()));
      this.normalPhase.addGoal(0, new BuggyRunningGoal(this, (double)1.5F));
      this.normalPhase.addGoal(1, new ImprovedMeleeAttackGoal(this, (double)1.0F, true));
      this.normalPhase.addGoal(2, baraHoWrapper);
      this.normalPhase.addGoal(2, this.baraFestivalWrapper);
      this.normalPhase.addGoal(2, bombThrowWrapper);
      this.normalPhase.addGoal(3, baraSplitWrapper);
      this.carPhase.addGoal(0, new BuggyRunningGoal(this, (double)2.5F));
      this.carPhase.addGoal(2, bombThrowWrapper);
      this.getPhaseManager().setPhase(this.normalPhase);
      if (this.getChallengeInfo().isDifficultyStandard()) {
         this.normalPhase.addGoal(1, (new DashDodgeTargetGoal(this, 500.0F, 2.0F)).setCanUseTest(canDash));
         this.normalPhase.addGoal(1, (new DashDodgeProjectilesGoal(this, 500.0F, 2.0F)).setCanUseTest(canDash));
         this.carPhase.addGoal(0, new BuggyDashGoal(this, (double)2.5F, 10.0F));
      } else {
         this.f_21345_.m_25352_(0, new HakiAbilityWrapperGoal(this, (AbilityCore)BusoshokuHakiInternalDestructionAbility.INSTANCE.get()));
         this.f_21345_.m_25352_(0, new HakiAbilityWrapperGoal(this, (AbilityCore)BusoshokuHakiFullBodyHardeningAbility.INSTANCE.get()));
         this.f_21345_.m_25352_(0, new HakiAbilityWrapperGoal(this, (AbilityCore)KenbunshokuHakiFutureSightAbility.INSTANCE.get()));
         this.normalPhase.addGoal(1, (new DashDodgeTargetGoal(this, 250.0F, 3.0F)).setCanUseTest(canDash));
         this.normalPhase.addGoal(1, (new DashDodgeProjectilesGoal(this, 250.0F, 3.0F)).setCanUseTest(canDash));
         this.carPhase.addGoal(0, new BuggyDashGoal(this, (double)5.0F, 25.0F));
      }

   }

   public static AttributeSupplier.Builder createAttributes() {
      return OPEntity.createAttributes().m_22268_(Attributes.f_22277_, (double)30.0F).m_22268_(Attributes.f_22279_, (double)0.3F).m_22268_(Attributes.f_22281_, (double)2.0F).m_22268_(Attributes.f_22276_, (double)200.0F).m_22268_(Attributes.f_22284_, (double)2.0F);
   }

   public EntityDimensions m_6972_(Pose pose) {
      return !this.isCarPhaseActive() && this.clientCarState != 1 ? super.m_6972_(pose) : EntityDimensions.m_20398_(1.3F, 0.9F);
   }

   public void m_8119_() {
      this.f_19789_ = 0.0F;
      super.m_8119_();
      if (!this.m_9236_().f_46443_) {
         this.kitingMeter.tick();
      }

      if (this.isCarPhaseActive() || this.clientCarState == 1) {
         this.m_6210_();
      }

   }

   @OnlyIn(Dist.CLIENT)
   public void m_7822_(byte id) {
      switch (id) {
         case 100:
         case 101:
         case 102:
         case 103:
            this.clientState = id - 100;
            break;
         case 104:
            this.clientState = 4;
            this.clientCarState = 1;
            break;
         case 105:
            this.clientState = 4;
            this.clientCarState = -1;
         case 106:
         case 107:
         case 108:
         case 109:
         default:
            break;
         case 110:
            this.m_9236_().m_7228_(this.m_20185_(), this.m_20186_(), this.m_20189_(), (double)0.0F, (double)0.0F, (double)0.0F, FIREWORKS_TAG);
      }

      super.m_7822_(id);
   }

   private static CompoundTag createFireworksTag() {
      CompoundTag fireworkData = new CompoundTag();
      ListTag explosionList = new ListTag();
      CompoundTag explosion1Data = new CompoundTag();
      explosion1Data.m_128405_("Type", 1);
      explosion1Data.m_128379_("Flicker", true);
      explosion1Data.m_128385_("Colors", new int[]{16351261, 11546150});
      explosion1Data.m_128385_("FadeColors", new int[]{16701501, 16383998});
      CompoundTag explosion2Data = new CompoundTag();
      explosion2Data.m_128405_("Type", 4);
      explosion2Data.m_128379_("Trail", true);
      explosion2Data.m_128385_("Colors", new int[]{16701501, 16383998});
      explosionList.add(explosion1Data);
      explosionList.add(explosion2Data);
      fireworkData.m_128365_("Explosions", explosionList);
      return fireworkData;
   }

   public boolean shouldRun() {
      boolean isCarActive = this.getPhaseManager().isCurrentPhase(this.carPhase);
      if (isCarActive) {
         return true;
      } else {
         boolean isFestivalActive = (Boolean)((BaraBaraFestivalAbility)this.baraFestivalWrapper.getAbility()).getComponent((AbilityComponentKey)ModAbilityComponents.CONTINUOUS.get()).map((comp) -> comp.isContinuous()).orElse(false);
         boolean isFestivalAttackMode = ((BaraBaraFestivalAbility)this.baraFestivalWrapper.getAbility()).isAttackMode();
         return isFestivalActive && isFestivalAttackMode;
      }
   }

   private void setBaraSplitLogic(ContinuousComponent comp) {
      comp.addStartEvent(101, (e, a) -> {
         this.splitBombs = false;
         this.m_9236_().m_7605_(this, (byte)102);
         this.m_21195_((MobEffect)ModEffects.MOVEMENT_BLOCKED.get());
         AbilityHelper.setDeltaMovement(this, (double)0.0F, (double)1.5F, (double)0.0F);

         for(LivingEntity target : TargetHelper.getEntitiesInArea(this.m_9236_(), this, (double)3.0F, TargetPredicate.DEFAULT_AREA_CHECK, LivingEntity.class)) {
            Vec3 speed = this.m_20154_().m_82542_((double)2.5F, (double)1.0F, (double)2.5F);
            AbilityHelper.setDeltaMovement(target, speed.f_82479_, (double)0.25F, speed.f_82481_);
         }

      });
      comp.addTickEvent((e, a) -> {
         if (!this.splitBombs && !(comp.getContinueTime() < 20.0F)) {
            boolean hasSecondWave = false;
            int radius = 3;
            int bombs = 4;
            if (this.isDifficultyHardOrAbove()) {
               bombs = 12;
               radius = 5;
               hasSecondWave = true;
            } else if ((double)this.m_21223_() < WyHelper.percentage((double)40.0F, (double)this.m_21233_())) {
               bombs = 8;
            }

            if (bombs > 0) {
               if (hasSecondWave) {
                  for(double phi = (double)0.0F; phi <= (Math.PI * 2D); phi += Math.PI / ((double)bombs / (double)2.0F)) {
                     double x = (double)radius / (double)2.0F * Math.cos(phi) + WyHelper.randomDouble() / (double)5.0F;
                     double z = (double)radius / (double)2.0F * Math.sin(phi) + WyHelper.randomDouble() / (double)5.0F;
                     Vec3 pos = this.m_20182_().m_82549_(new Vec3(x, this.m_20182_().f_82480_, z));
                     Vec3 dirVec = this.m_20182_().m_82546_(pos).m_82541_().m_82542_((double)7.5F, (double)1.0F, (double)7.5F);
                     BombEntity bomb = new BombEntity(this.m_9236_(), this);
                     bomb.setExplodeOnImpact();
                     bomb.m_7678_(this.m_20182_().f_82479_ + x, this.m_20182_().f_82480_, this.m_20182_().f_82481_ + z, (float)this.m_217043_().m_188503_(360), (float)this.m_217043_().m_188503_(360));
                     AbilityHelper.setDeltaMovement(bomb, -dirVec.f_82479_, (double)1.0F, -dirVec.f_82481_);
                     this.m_9236_().m_7967_(bomb);
                  }
               }

               for(double phi = (double)0.0F; phi <= (Math.PI * 2D); phi += Math.PI / (double)bombs) {
                  double x = (double)radius * Math.cos(phi) + WyHelper.randomDouble() / (double)5.0F;
                  double z = (double)radius * Math.sin(phi) + WyHelper.randomDouble() / (double)5.0F;
                  Vec3 pos = this.m_20182_().m_82549_(new Vec3(x, this.m_20182_().f_82480_, z));
                  Vec3 dirVec = this.m_20182_().m_82546_(pos).m_82541_().m_82542_((double)7.5F, (double)1.0F, (double)7.5F);
                  BombEntity bomb = new BombEntity(this.m_9236_(), this);
                  bomb.setExplodeOnImpact();
                  bomb.m_7678_(this.m_20182_().f_82479_ + x, this.m_20182_().f_82480_, this.m_20182_().f_82481_ + z, (float)this.m_217043_().m_188503_(360), (float)this.m_217043_().m_188503_(360));
                  AbilityHelper.setDeltaMovement(bomb, -dirVec.f_82479_, (double)1.0F, -dirVec.f_82481_);
                  this.m_9236_().m_7967_(bomb);
               }

               this.m_9236_().m_7605_(this, (byte)110);
               this.splitBombs = true;
            }
         }
      });
      comp.addEndEvent((e, a) -> this.m_9236_().m_7605_(this, (byte)100));
   }

   public void startNormalPhase() {
      this.getPhaseManager().setPhase(this.normalPhase);
      this.m_9236_().m_7605_(this, (byte)105);
   }

   public void startCarPhase() {
      this.getPhaseManager().setPhase(this.carPhase);
      this.m_9236_().m_7605_(this, (byte)104);
      this.kitingMeter.reduceRevengeValue(-10);
   }

   public boolean isNormalPhaseActive() {
      return this.normalPhase.isActive(this);
   }

   public boolean isCarPhaseActive() {
      return this.carPhase == null ? false : this.carPhase.isActive(this);
   }

   public float getKitingMeterCompletion() {
      return this.kitingMeter.getRevengePercentage();
   }
}
