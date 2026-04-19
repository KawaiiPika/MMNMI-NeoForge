package xyz.pixelatedw.mineminenomi.entities.mobs.pirates.baroqueworks;

import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;
import xyz.pixelatedw.mineminenomi.abilities.TakedownKickAbility;
import xyz.pixelatedw.mineminenomi.abilities.blackleg.PartyTableKickCourseAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.HakaiHoAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiFullBodyHardeningAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiInternalDestructionAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.RankyakuAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.ShiganAbility;
import xyz.pixelatedw.mineminenomi.abilities.supa.AtomicRushAbility;
import xyz.pixelatedw.mineminenomi.abilities.supa.SparClawAbility;
import xyz.pixelatedw.mineminenomi.abilities.supa.SparklingDaisyAbility;
import xyz.pixelatedw.mineminenomi.abilities.supa.SpiderAbility;
import xyz.pixelatedw.mineminenomi.abilities.supa.SpiralHollowAbility;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusOperation;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.challenges.InProgressChallenge;
import xyz.pixelatedw.mineminenomi.api.entities.ai.NPCPhase;
import xyz.pixelatedw.mineminenomi.api.entities.ai.SimplePhase;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.data.world.FactionsWorldData;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.DashDodgeProjectilesGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.ImprovedMeleeAttackGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.JumpOutOfHoleGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.SprintTowardsTargetGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ActiveBuffAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.AntiAirAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.CloseMeleeAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.DashAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.HakiAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ProjectileAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ReactiveGuardAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.mr1.Mr1PhaseSwitcherGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPBossEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModFactions;
import xyz.pixelatedw.mineminenomi.init.ModFruits;
import xyz.pixelatedw.mineminenomi.init.ModMobs;
import xyz.pixelatedw.mineminenomi.init.ModNPCGroups;
import xyz.pixelatedw.mineminenomi.init.ModRaces;
import xyz.pixelatedw.mineminenomi.init.ModSounds;
import xyz.pixelatedw.mineminenomi.items.AkumaNoMiItem;

public class Mr1Entity extends OPBossEntity {
   private static final UUID STANDARD_SPIDER_COOLDOWN_BONUS_UUID = UUID.fromString("1f2944fa-fd28-41b9-8f67-392929011bbd");
   private static final AttributeModifier GCD_MOD;
   private NPCPhase<Mr1Entity> firstPhase;
   private NPCPhase<Mr1Entity> secondPhase;
   private NPCPhase<Mr1Entity> ultiPhase;
   private ReactiveGuardAbilityWrapperGoal<SpiderAbility> spiderWrapper;
   private AtomicRushAbility atomicRushAbility;

   public Mr1Entity(EntityType<Mr1Entity> type, Level world) {
      super(type, world);
   }

   public Mr1Entity(InProgressChallenge challenge) {
      super((EntityType)ModMobs.MR1.get(), challenge);
   }

   public void initBoss() {
      this.firstPhase = new SimplePhase<Mr1Entity>("First Phase", this, this::startFirstPhaseEvent, this::stopFirstPhaseEvent);
      this.secondPhase = new SimplePhase<Mr1Entity>("Second Phase", this, this::startSecondPhaseEvent);
      this.ultiPhase = new SimplePhase<Mr1Entity>("Ultimate Phase", this);
      FactionsWorldData worldData = FactionsWorldData.get();
      this.entityStats.setFaction((Faction)ModFactions.PIRATE.get());
      this.entityStats.setRace((Race)ModRaces.HUMAN.get());
      this.devilFruitData.setDevilFruit((AkumaNoMiItem)ModFruits.SUPA_SUPA_NO_MI.get());
      worldData.addTemporaryCrewMember(ModNPCGroups.BAROQUE_WORKS, this);
      this.m_21051_((Attribute)ModAttributes.TOUGHNESS.get()).m_22100_((double)4.0F);
      this.m_21051_((Attribute)ModAttributes.PUNCH_DAMAGE.get()).m_22100_((double)6.0F);
      this.m_21051_((Attribute)ForgeMod.STEP_HEIGHT_ADDITION.get()).m_22100_((double)1.0F);
      MobsHelper.addBasicNPCGoals(this);
      CloseMeleeAbilityWrapperGoal<PartyTableKickCourseAbility> partyTableWrapper = new CloseMeleeAbilityWrapperGoal<PartyTableKickCourseAbility>(this, (AbilityCore)PartyTableKickCourseAbility.INSTANCE.get());
      this.spiderWrapper = new ReactiveGuardAbilityWrapperGoal<SpiderAbility>(this, (AbilityCore)SpiderAbility.INSTANCE.get());
      ((SpiderAbility)this.spiderWrapper.getAbility()).getComponent((AbilityComponentKey)ModAbilityComponents.CONTINUOUS.get()).ifPresent((comp) -> comp.addTickEvent((entity, ability) -> {
            if (entity.m_9236_().m_46467_() >= ((SpiderAbility)this.spiderWrapper.getAbility()).getLastUseGametime() + 60L && GoalHelper.hasEnoughTargetsAround((Mob)entity, 1, 4.0F) && ((PartyTableKickCourseAbility)partyTableWrapper.getAbility()).canUse(entity).isSuccess()) {
               comp.stopContinuity(entity);
               ((PartyTableKickCourseAbility)partyTableWrapper.getAbility()).use(entity);
            }

         }));
      DashAbilityWrapperGoal<SparklingDaisyAbility> sparklingDaisyWrapper = new DashAbilityWrapperGoal<SparklingDaisyAbility>(this, (AbilityCore)SparklingDaisyAbility.INSTANCE.get());
      ((SparklingDaisyAbility)sparklingDaisyWrapper.getAbility()).setProjectilesAmount(this.getChallengeInfo().isDifficultyHard() ? 2 : 1);
      DashAbilityWrapperGoal<AtomicRushAbility> atomicRushWrapper = new DashAbilityWrapperGoal<AtomicRushAbility>(this, (AbilityCore)AtomicRushAbility.INSTANCE.get());
      this.atomicRushAbility = (AtomicRushAbility)atomicRushWrapper.getAbility();
      SprintTowardsTargetGoal sprintGoal = new SprintTowardsTargetGoal(this);
      AntiAirAbilityWrapperGoal<TakedownKickAbility> takedownKickGoal = new AntiAirAbilityWrapperGoal<TakedownKickAbility>(this, (AbilityCore)TakedownKickAbility.INSTANCE.get());
      ActiveBuffAbilityWrapperGoal<SparClawAbility> sparClawGoal = new ActiveBuffAbilityWrapperGoal<SparClawAbility>(this, (AbilityCore)SparClawAbility.INSTANCE.get());
      ProjectileAbilityWrapperGoal<SpiralHollowAbility> spiralHollowGoal = new ProjectileAbilityWrapperGoal<SpiralHollowAbility>(this, (AbilityCore)SpiralHollowAbility.INSTANCE.get());
      this.f_21345_.m_25352_(0, new Mr1PhaseSwitcherGoal(this));
      this.f_21345_.m_25352_(0, new JumpOutOfHoleGoal(this));
      this.firstPhase.addGoal(1, new ImprovedMeleeAttackGoal(this, (double)1.0F, true));
      this.firstPhase.addGoal(1, sprintGoal);
      this.firstPhase.addGoal(2, takedownKickGoal);
      this.firstPhase.addGoal(3, sparClawGoal);
      this.firstPhase.addGoal(3, this.spiderWrapper);
      this.firstPhase.addGoal(3, spiralHollowGoal);
      this.firstPhase.addGoal(4, partyTableWrapper);
      this.secondPhase.addGoal(0, new DashDodgeProjectilesGoal(this, 200.0F, 2.5F));
      this.secondPhase.addGoal(1, new ImprovedMeleeAttackGoal(this, 1.2, true));
      this.secondPhase.addGoal(1, sprintGoal);
      this.secondPhase.addGoal(2, takedownKickGoal);
      this.secondPhase.addGoal(3, sparClawGoal);
      this.secondPhase.addGoal(3, this.spiderWrapper);
      this.secondPhase.addGoal(3, spiralHollowGoal);
      this.secondPhase.addGoal(4, partyTableWrapper);
      this.secondPhase.addGoal(4, sparklingDaisyWrapper);
      this.ultiPhase.addGoal(1, atomicRushWrapper);
      this.getPhaseManager().setPhase(this.firstPhase);
      if (this.getChallengeInfo().isDifficultyStandard()) {
         this.entityStats.setDoriki((double)2000.0F);
         this.m_21051_((Attribute)ModAttributes.GCD.get()).m_22100_((double)40.0F);
         this.m_21051_((Attribute)ModAttributes.PUNCH_DAMAGE.get()).m_22100_((double)5.0F);
         this.atomicRushAbility.setDashWaitTime(10);
         ((SpiderAbility)this.spiderWrapper.getAbility()).getComponent((AbilityComponentKey)ModAbilityComponents.COOLDOWN.get()).ifPresent((comp) -> comp.getBonusManager().addBonus(STANDARD_SPIDER_COOLDOWN_BONUS_UUID, "Standard Spider Cooldown Bonus", BonusOperation.MUL, 1.75F));
         this.secondPhase.addGoal(3, new CloseMeleeAbilityWrapperGoal(this, (AbilityCore)HakaiHoAbility.INSTANCE.get()));
      } else {
         this.entityStats.setDoriki((double)10000.0F);
         this.hakiCapability.setBusoshokuHakiExp(100.0F);
         this.hakiCapability.setKenbunshokuHakiExp(100.0F);
         this.m_21051_((Attribute)ModAttributes.GCD.get()).m_22100_((double)20.0F);
         this.m_21051_((Attribute)ModAttributes.PUNCH_DAMAGE.get()).m_22100_((double)5.0F);
         this.m_21051_(Attributes.f_22281_).m_22100_((double)5.0F);
         this.m_21051_((Attribute)ModAttributes.TOUGHNESS.get()).m_22100_((double)8.0F);
         this.m_21051_(Attributes.f_22276_).m_22100_((double)400.0F);
         this.m_21051_(Attributes.f_22284_).m_22100_((double)20.0F);
         this.m_21051_(Attributes.f_22285_).m_22100_((double)8.0F);
         CloseMeleeAbilityWrapperGoal<HakaiHoAbility> hakaiHoWrapper = new CloseMeleeAbilityWrapperGoal<HakaiHoAbility>(this, (AbilityCore)HakaiHoAbility.INSTANCE.get());
         ((HakaiHoAbility)hakaiHoWrapper.getAbility()).getComponent((AbilityComponentKey)ModAbilityComponents.COOLDOWN.get()).ifPresent((comp) -> comp.startCooldown(this, 60.0F));
         CloseMeleeAbilityWrapperGoal<ShiganAbility> shiganWrapper = new CloseMeleeAbilityWrapperGoal<ShiganAbility>(this, (AbilityCore)ShiganAbility.INSTANCE.get());
         this.f_21345_.m_25352_(0, new HakiAbilityWrapperGoal(this, (AbilityCore)BusoshokuHakiInternalDestructionAbility.INSTANCE.get()));
         this.f_21345_.m_25352_(0, new HakiAbilityWrapperGoal(this, (AbilityCore)BusoshokuHakiFullBodyHardeningAbility.INSTANCE.get()));
         this.firstPhase.addGoal(0, new DashDodgeProjectilesGoal(this, 200.0F, 2.5F));
         this.firstPhase.addGoal(3, hakaiHoWrapper);
         this.firstPhase.addGoal(3, shiganWrapper);
         this.firstPhase.addGoal(3, new ProjectileAbilityWrapperGoal(this, (AbilityCore)RankyakuAbility.INSTANCE.get()));
         this.secondPhase.addGoal(0, new DashDodgeProjectilesGoal(this, 150.0F, 2.5F));
         this.secondPhase.addGoal(3, hakaiHoWrapper);
         this.secondPhase.addGoal(3, shiganWrapper);
         this.secondPhase.addGoal(3, new ProjectileAbilityWrapperGoal(this, (AbilityCore)RankyakuAbility.INSTANCE.get()));
      }

   }

   public static AttributeSupplier.Builder createAttributes() {
      return OPEntity.createAttributes().m_22268_(Attributes.f_22277_, (double)60.0F).m_22268_(Attributes.f_22279_, (double)0.3F).m_22268_(Attributes.f_22281_, (double)5.0F).m_22268_(Attributes.f_22276_, (double)300.0F).m_22268_(Attributes.f_22284_, (double)4.0F).m_22268_(Attributes.f_22285_, (double)2.0F).m_22268_(Attributes.f_22278_, 0.8);
   }

   public void startUltiPhase() {
      this.getPhaseManager().setPhase(this.ultiPhase);
   }

   public void startSecondPhase() {
      this.getPhaseManager().setPhase(this.secondPhase);
   }

   public void m_8119_() {
      super.m_8119_();
      if (!this.m_9236_().f_46443_ && this.m_5448_() != null) {
         boolean canSee = this.m_142582_(this.m_5448_());
         double yDiff = Math.abs(this.m_20186_() - this.m_5448_().m_20186_());
         if (yDiff > (double)20.0F && !canSee) {
            BlockPos tpPos = WyHelper.findValidGroundLocation((Entity)this, (BlockPos)this.m_5448_().m_20183_(), 10, 10);
            this.m_20324_((double)tpPos.m_123341_(), (double)(tpPos.m_123342_() + 5), (double)tpPos.m_123343_());
         }
      }

   }

   public void m_21011_(InteractionHand hand, boolean updateSelf) {
      if (this.m_5448_() != null) {
         WyHelper.spawnParticles(ParticleTypes.f_123766_, (ServerLevel)this.m_9236_(), this.m_5448_().m_20185_(), this.m_5448_().m_20186_() + (double)this.m_5448_().m_20192_(), this.m_5448_().m_20189_());
         this.m_9236_().m_5594_((Player)null, this.m_5448_().m_20183_(), (SoundEvent)ModSounds.DASH_ABILITY_SWOOSH_SFX.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
      }

      super.m_21011_(hand, updateSelf);
   }

   public boolean isUltiAvailable() {
      return !(Boolean)this.atomicRushAbility.getComponent((AbilityComponentKey)ModAbilityComponents.COOLDOWN.get()).map(CooldownComponent::isOnCooldown).orElse(true);
   }

   public double getUltiTicks() {
      return (double)(Float)this.atomicRushAbility.getComponent((AbilityComponentKey)ModAbilityComponents.CONTINUOUS.get()).map(ContinuousComponent::getThresholdTime).orElse(-1.0F);
   }

   private void startFirstPhaseEvent(Mr1Entity entity) {
      if (this.isDifficultyHardOrAbove()) {
         ((SpiderAbility)this.spiderWrapper.getAbility()).getComponent((AbilityComponentKey)ModAbilityComponents.COOLDOWN.get()).ifPresent((comp) -> comp.getBonusManager().addBonus(STANDARD_SPIDER_COOLDOWN_BONUS_UUID, "Standard Spider Cooldown Bonus", BonusOperation.MUL, 1.5F));
      }

   }

   private void stopFirstPhaseEvent(Mr1Entity entity) {
      if (this.isDifficultyHardOrAbove()) {
         ((SpiderAbility)this.spiderWrapper.getAbility()).getComponent((AbilityComponentKey)ModAbilityComponents.COOLDOWN.get()).ifPresent((comp) -> comp.getBonusManager().removeBonus(STANDARD_SPIDER_COOLDOWN_BONUS_UUID));
      }

   }

   private void startSecondPhaseEvent(Mr1Entity entity) {
      if (this.isDifficultyHardOrAbove()) {
         AttributeInstance attr = entity.m_21051_((Attribute)ModAttributes.GCD.get());
         if (attr != null && !attr.m_22109_(GCD_MOD)) {
            attr.m_22118_(GCD_MOD);
         }
      }

   }

   static {
      GCD_MOD = new AttributeModifier(UUID.fromString("e478aceb-9865-40da-a137-6359ba503bf0"), "GCD Modifier", (double)-5.0F, Operation.ADDITION);
   }
}
