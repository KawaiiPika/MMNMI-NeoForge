package xyz.pixelatedw.mineminenomi.entities.mobs.pirates.baroqueworks;

import java.util.UUID;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;
import xyz.pixelatedw.mineminenomi.abilities.brawler.HakaiHoAbility;
import xyz.pixelatedw.mineminenomi.abilities.doru.CandleChampionAbility;
import xyz.pixelatedw.mineminenomi.abilities.doru.CandleLockAbility;
import xyz.pixelatedw.mineminenomi.abilities.doru.CandleWallAbility;
import xyz.pixelatedw.mineminenomi.abilities.doru.DoruDoruArtsKenAbility;
import xyz.pixelatedw.mineminenomi.abilities.doru.DoruDoruArtsMoriAbility;
import xyz.pixelatedw.mineminenomi.abilities.doru.DoruDoruBallAbility;
import xyz.pixelatedw.mineminenomi.abilities.doru.DoruDoruNoYakataAbility;
import xyz.pixelatedw.mineminenomi.abilities.doru.TokudaiCandleAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiFullBodyHardeningAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiInternalDestructionAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.KamieAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.ShiganAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusOperation;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.challenges.InProgressChallenge;
import xyz.pixelatedw.mineminenomi.api.entities.ai.NPCPhase;
import xyz.pixelatedw.mineminenomi.api.entities.ai.SimplePhase;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.data.world.FactionsWorldData;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.ClimbOutOfHoleGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.DashDodgeProjectilesGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.DashDodgeTargetGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.ImprovedMeleeAttackGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.RunAwayFromTargetGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.SprintTowardsTargetGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.TauntTargetGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ActiveBuffAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.AntiProjectileAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.CloseMeleeAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.HakiAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ProjectileAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ReactiveGuardAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.UseAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.mr3.Mr3PhaseSwitcherGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPBossEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModFactions;
import xyz.pixelatedw.mineminenomi.init.ModFruits;
import xyz.pixelatedw.mineminenomi.init.ModMobs;
import xyz.pixelatedw.mineminenomi.init.ModNPCGroups;
import xyz.pixelatedw.mineminenomi.init.ModRaces;
import xyz.pixelatedw.mineminenomi.items.AkumaNoMiItem;

public class Mr3Entity extends OPBossEntity {
   private static final UUID TOKUDAI_YAKATA_COOLDOWN_BONUS = UUID.fromString("26687dbb-506c-4531-996c-ee35db1a2bb3");
   private static final UUID DORU_ARTS_MORI_COOLDOWN_BONUS = UUID.fromString("9b7ff21e-639a-4f1a-9919-6748fc9cf8b2");
   private static final UUID CHAMPION_FAUX_PROT_BONUS = UUID.fromString("6f0e2075-2051-4a3d-8854-0ea55e29b4e9");
   private static final UUID CHAMPION_TOUGHNESS_BONUS = UUID.fromString("ba4237ab-4e77-4ccb-85bd-71a81d248d45");
   private boolean hasColorPaletteBonus;
   private NPCPhase<Mr3Entity> standardPhase;
   private NPCPhase<Mr3Entity> championPhase;
   private NPCPhase<Mr3Entity> tokudaiPhase;
   private NPCPhase<Mr3Entity> tauntPhase;
   private UseAbilityWrapperGoal<DoruDoruNoYakataAbility> doruYakataWrapper;
   private UseAbilityWrapperGoal<TokudaiCandleAbility> tokudaiCandleWrapper;
   private ActiveBuffAbilityWrapperGoal<CandleChampionAbility> candleChampionWrapper;
   private ProjectileAbilityWrapperGoal<DoruDoruArtsMoriAbility> doruDoruArtsMoriWrapper;

   public Mr3Entity(EntityType<Mr3Entity> type, Level world) {
      super(type, world);
   }

   public Mr3Entity(InProgressChallenge challenge) {
      super((EntityType)ModMobs.MR3.get(), challenge);
   }

   public void initBoss() {
      FactionsWorldData worldData = FactionsWorldData.get();
      this.entityStats.setFaction((Faction)ModFactions.PIRATE.get());
      this.entityStats.setRace((Race)ModRaces.HUMAN.get());
      this.devilFruitData.setDevilFruit((AkumaNoMiItem)ModFruits.DORU_DORU_NO_MI.get());
      worldData.addTemporaryCrewMember(ModNPCGroups.BAROQUE_WORKS, this);
      this.entityStats.setDoriki((double)2000.0F);
      this.m_21051_((Attribute)ModAttributes.GCD.get()).m_22100_((double)40.0F);
      this.m_21051_((Attribute)ModAttributes.PUNCH_DAMAGE.get()).m_22100_((double)5.0F);
      this.m_21051_((Attribute)ModAttributes.TOUGHNESS.get()).m_22100_((double)1.0F);
      this.m_21051_((Attribute)ForgeMod.STEP_HEIGHT_ADDITION.get()).m_22100_((double)1.0F);
      if (this.isDifficultyHardOrAbove()) {
         this.entityStats.setDoriki((double)10000.0F);
         this.hakiCapability.setBusoshokuHakiExp(100.0F);
         this.hakiCapability.setKenbunshokuHakiExp(100.0F);
         this.m_21051_((Attribute)ModAttributes.GCD.get()).m_22100_((double)20.0F);
         this.m_21051_((Attribute)ModAttributes.PUNCH_DAMAGE.get()).m_22100_((double)5.0F);
         this.m_21051_(Attributes.f_22281_).m_22100_((double)5.0F);
         this.m_21051_((Attribute)ModAttributes.TOUGHNESS.get()).m_22100_((double)6.0F);
         this.m_21051_(Attributes.f_22276_).m_22100_((double)400.0F);
         this.m_21051_(Attributes.f_22284_).m_22100_((double)20.0F);
         this.m_21051_(Attributes.f_22285_).m_22100_((double)8.0F);
      }

      this.standardPhase = new SimplePhase<Mr3Entity>("Standard Phase", this);
      this.championPhase = new SimplePhase<Mr3Entity>("Champion Phase", this, this::onStartChampionPhaseEvent, this::onStopChampionPhaseEvent);
      this.tokudaiPhase = new SimplePhase<Mr3Entity>("Tokudai Phase", this, this::onStartTokudaiPhaseEvent, this::onStopTokudaiPhaseEvent);
      this.tauntPhase = new SimplePhase<Mr3Entity>("Taunt Phase", this, this::onStartTauntPhaseEvent, this::onStopTauntPhaseEvent);
      this.doruYakataWrapper = new UseAbilityWrapperGoal<DoruDoruNoYakataAbility>(this, (AbilityCore)DoruDoruNoYakataAbility.INSTANCE.get());
      this.tokudaiCandleWrapper = new UseAbilityWrapperGoal<TokudaiCandleAbility>(this, (AbilityCore)TokudaiCandleAbility.INSTANCE.get());
      ((TokudaiCandleAbility)this.tokudaiCandleWrapper.getAbility()).setBlocksPerTick(100);
      this.candleChampionWrapper = new ActiveBuffAbilityWrapperGoal<CandleChampionAbility>(this, (AbilityCore)CandleChampionAbility.INSTANCE.get());
      this.doruDoruArtsMoriWrapper = new ProjectileAbilityWrapperGoal<DoruDoruArtsMoriAbility>(this, (AbilityCore)DoruDoruArtsMoriAbility.INSTANCE.get());
      ReactiveGuardAbilityWrapperGoal<DoruDoruBallAbility> doruBallWrapper = new ReactiveGuardAbilityWrapperGoal<DoruDoruBallAbility>(this, (AbilityCore)DoruDoruBallAbility.INSTANCE.get());
      ProjectileAbilityWrapperGoal<CandleLockAbility> candleLockWrapper = new ProjectileAbilityWrapperGoal<CandleLockAbility>(this, (AbilityCore)CandleLockAbility.INSTANCE.get());
      ((CandleLockAbility)candleLockWrapper.getAbility()).getComponent((AbilityComponentKey)ModAbilityComponents.COOLDOWN.get()).ifPresent((comp) -> comp.startCooldown(this, 60.0F));
      MobsHelper.addBasicNPCGoals(this);
      this.f_21345_.m_25352_(0, new Mr3PhaseSwitcherGoal(this));
      this.f_21345_.m_25352_(0, new ClimbOutOfHoleGoal(this));
      this.standardPhase.addGoal(1, new DashDodgeTargetGoal(this, 100.0F, 3.0F));
      this.standardPhase.addGoal(1, new ImprovedMeleeAttackGoal(this, (double)1.0F, true));
      this.standardPhase.addGoal(2, new AntiProjectileAbilityWrapperGoal(this, (AbilityCore)CandleWallAbility.INSTANCE.get()));
      this.standardPhase.addGoal(2, doruBallWrapper);
      this.standardPhase.addGoal(3, new ActiveBuffAbilityWrapperGoal(this, (AbilityCore)DoruDoruArtsKenAbility.INSTANCE.get()));
      this.standardPhase.addGoal(3, this.doruDoruArtsMoriWrapper);
      this.standardPhase.addGoal(3, candleLockWrapper);
      this.standardPhase.addGoal(3, this.doruYakataWrapper);
      this.championPhase.addGoal(0, new SprintTowardsTargetGoal(this));
      this.championPhase.addGoal(1, new ImprovedMeleeAttackGoal(this, (double)1.1F, true));
      this.championPhase.addGoal(3, this.candleChampionWrapper);
      this.championPhase.addGoal(4, new ProjectileAbilityWrapperGoal(this, (AbilityCore)CandleLockAbility.INSTANCE.get()));
      this.championPhase.addGoal(4, new ProjectileAbilityWrapperGoal(this, (AbilityCore)DoruDoruArtsMoriAbility.INSTANCE.get()));
      this.tokudaiPhase.addGoal(0, new RunAwayFromTargetGoal(this, 1.7, 500, 250));
      this.tokudaiPhase.addGoal(3, this.doruYakataWrapper);
      this.tokudaiPhase.addGoal(4, this.tokudaiCandleWrapper);
      this.getPhaseManager().setPhase(this.standardPhase);
      if (this.isDifficultyStandard()) {
         this.standardPhase.addGoal(0, new DashDodgeProjectilesGoal(this, 200.0F, 3.0F));
         this.tokudaiPhase.addGoal(0, new DashDodgeProjectilesGoal(this, 100.0F, 3.0F));
      } else {
         doruBallWrapper.setActivationHits(3);
         ((TokudaiCandleAbility)this.tokudaiCandleWrapper.getAbility()).setBlocksPerTick(200);
         this.f_21345_.m_25352_(0, new HakiAbilityWrapperGoal(this, (AbilityCore)BusoshokuHakiInternalDestructionAbility.INSTANCE.get()));
         this.f_21345_.m_25352_(0, new HakiAbilityWrapperGoal(this, (AbilityCore)BusoshokuHakiFullBodyHardeningAbility.INSTANCE.get()));
         this.standardPhase.addGoal(0, new DashDodgeProjectilesGoal(this, 100.0F, 3.0F));
         this.standardPhase.addGoal(3, new CloseMeleeAbilityWrapperGoal(this, (AbilityCore)ShiganAbility.INSTANCE.get()));
         this.tauntPhase.addGoal(0, new TauntTargetGoal(this));
         this.tauntPhase.addGoal(1, (new ImprovedMeleeAttackGoal(this, (double)1.0F, true)).setMoveTowardsTarget(false));
         this.tauntPhase.addGoal(1, new DashDodgeTargetGoal(this, 60.0F, 4.5F));
         this.tauntPhase.addGoal(0, new DashDodgeProjectilesGoal(this, 20.0F, 4.5F));
         this.tauntPhase.addGoal(3, new ReactiveGuardAbilityWrapperGoal(this, (AbilityCore)KamieAbility.INSTANCE.get()));
         this.tauntPhase.addGoal(3, this.doruDoruArtsMoriWrapper);
         this.championPhase.addGoal(3, new CloseMeleeAbilityWrapperGoal(this, (AbilityCore)HakaiHoAbility.INSTANCE.get()));
         this.tokudaiPhase.addGoal(0, new DashDodgeProjectilesGoal(this, 80.0F, 4.0F));
         this.tokudaiPhase.addGoal(3, new ReactiveGuardAbilityWrapperGoal(this, (AbilityCore)KamieAbility.INSTANCE.get()));
      }

   }

   public static AttributeSupplier.Builder createAttributes() {
      return OPEntity.createAttributes().m_22268_(Attributes.f_22277_, (double)60.0F).m_22268_(Attributes.f_22279_, (double)0.3F).m_22268_(Attributes.f_22281_, (double)2.0F).m_22268_(Attributes.f_22276_, (double)300.0F).m_22268_(Attributes.f_22284_, (double)2.0F);
   }

   public void m_8119_() {
      super.m_8119_();
      if (!this.m_9236_().f_46443_ && this.m_5448_() != null && this.m_5448_().m_6084_() && this.isDifficultyHardOrAbove()) {
         if (this.standardPhase.isActive(this) && this.m_5448_().m_21023_((MobEffect)ModEffects.CANDLE_LOCK.get()) && GoalHelper.hasHealthAbovePercentage(this, (double)70.0F)) {
            this.getPhaseManager().setPhase(this.tauntPhase);
         } else if (this.tauntPhase.isActive(this) && !this.m_5448_().m_21023_((MobEffect)ModEffects.CANDLE_LOCK.get())) {
            this.getPhaseManager().setPhase(this.standardPhase);
         }
      }

   }

   public boolean hasColorPaletteBonus() {
      return this.hasColorPaletteBonus;
   }

   public boolean isStandardPhase() {
      return this.standardPhase.isActive(this);
   }

   public boolean isChampionPhase() {
      return this.championPhase.isActive(this);
   }

   public boolean isTokudaiPhase() {
      return this.tokudaiPhase.isActive(this);
   }

   public void startStandardPhase() {
      this.getPhaseManager().setPhase(this.standardPhase);
   }

   public void startChampionPhase() {
      this.getPhaseManager().setPhase(this.championPhase);
   }

   public void startTokudaiPhase() {
      this.getPhaseManager().setPhase(this.tokudaiPhase);
   }

   public void onStartChampionPhaseEvent(Mr3Entity entity) {
      this.m_21051_((Attribute)ModAttributes.TOUGHNESS.get()).m_22118_(new AttributeModifier(CHAMPION_TOUGHNESS_BONUS, "Toughness Bonus", (double)4.0F, Operation.ADDITION));
      ((DoruDoruArtsMoriAbility)this.doruDoruArtsMoriWrapper.getAbility()).getComponent((AbilityComponentKey)ModAbilityComponents.COOLDOWN.get()).ifPresent((comp) -> comp.stopCooldown(entity));
   }

   public void onStopChampionPhaseEvent(Mr3Entity entity) {
      this.m_21051_((Attribute)ModAttributes.TOUGHNESS.get()).m_22120_(CHAMPION_TOUGHNESS_BONUS);
   }

   public void onStartTokudaiPhaseEvent(Mr3Entity entity) {
      ((DoruDoruNoYakataAbility)this.doruYakataWrapper.getAbility()).getComponent((AbilityComponentKey)ModAbilityComponents.COOLDOWN.get()).ifPresent((comp) -> comp.stopCooldown(entity));
      ((DoruDoruNoYakataAbility)this.doruYakataWrapper.getAbility()).getComponent((AbilityComponentKey)ModAbilityComponents.COOLDOWN.get()).ifPresent((comp) -> comp.getBonusManager().addBonus(TOKUDAI_YAKATA_COOLDOWN_BONUS, "Tokudai Phase Doru Yakata Cooldown Bonus", BonusOperation.ADD, -400.0F));
   }

   public void onStopTokudaiPhaseEvent(Mr3Entity entity) {
      ((DoruDoruNoYakataAbility)this.doruYakataWrapper.getAbility()).getComponent((AbilityComponentKey)ModAbilityComponents.COOLDOWN.get()).ifPresent((comp) -> comp.getBonusManager().removeBonus(TOKUDAI_YAKATA_COOLDOWN_BONUS));
   }

   public void onStartTauntPhaseEvent(Mr3Entity entity) {
      ((DoruDoruArtsMoriAbility)this.doruDoruArtsMoriWrapper.getAbility()).getComponent((AbilityComponentKey)ModAbilityComponents.COOLDOWN.get()).ifPresent((comp) -> {
         comp.getBonusManager().addBonus(DORU_ARTS_MORI_COOLDOWN_BONUS, "Taunt Doru Arts Mori Cooldown Bonus", BonusOperation.MUL, 0.5F);
         comp.stopCooldown(entity);
      });
   }

   public void onStopTauntPhaseEvent(Mr3Entity entity) {
      ((DoruDoruArtsMoriAbility)this.doruDoruArtsMoriWrapper.getAbility()).getComponent((AbilityComponentKey)ModAbilityComponents.COOLDOWN.get()).ifPresent((comp) -> comp.getBonusManager().removeBonus(DORU_ARTS_MORI_COOLDOWN_BONUS));
   }

   public boolean isChampionOnCooldown() {
      return ((CooldownComponent)((CandleChampionAbility)this.candleChampionWrapper.getAbility()).getComponent((AbilityComponentKey)ModAbilityComponents.COOLDOWN.get()).get()).isOnCooldown();
   }

   public boolean isTokudaiOnCooldown() {
      return ((CooldownComponent)((TokudaiCandleAbility)this.tokudaiCandleWrapper.getAbility()).getComponent((AbilityComponentKey)ModAbilityComponents.COOLDOWN.get()).get()).isOnCooldown();
   }
}
