package xyz.pixelatedw.mineminenomi.entities.mobs.pirates.arlongpirates;

import com.google.common.collect.Sets;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.OpenDoorGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;
import xyz.pixelatedw.mineminenomi.abilities.TakedownKickAbility;
import xyz.pixelatedw.mineminenomi.abilities.blackleg.AntiMannerKickCourseAbility;
import xyz.pixelatedw.mineminenomi.abilities.blackleg.PartyTableKickCourseAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.ChargedPunchAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.HakaiHoAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.SpinningBrawlAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.SuplexAbility;
import xyz.pixelatedw.mineminenomi.abilities.fishmankarate.FishmanPassiveBonusesAbility;
import xyz.pixelatedw.mineminenomi.abilities.fishmankarate.KachiageHaisokuAbility;
import xyz.pixelatedw.mineminenomi.abilities.fishmankarate.KarakusagawaraSeikenAbility;
import xyz.pixelatedw.mineminenomi.abilities.fishmankarate.SamehadaShoteiAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiFullBodyHardeningAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiInternalDestructionAbility;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusOperation;
import xyz.pixelatedw.mineminenomi.api.challenges.InProgressChallenge;
import xyz.pixelatedw.mineminenomi.api.entities.ai.NPCPhase;
import xyz.pixelatedw.mineminenomi.api.entities.ai.SimplePhase;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.entities.revenge.DeadzoneRevengeCheck;
import xyz.pixelatedw.mineminenomi.api.entities.revenge.PhysicalHitRevengeCheck;
import xyz.pixelatedw.mineminenomi.api.entities.revenge.RevengeMeter;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.world.FactionsWorldData;
import xyz.pixelatedw.mineminenomi.entities.ai.controllers.HumanoidSwimMoveControl;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.DashDodgeProjectilesGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.DashDodgeTargetGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.FactionHurtByTargetGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.ImprovedMeleeAttackGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.JumpOutOfHoleGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.SprintTowardsTargetGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.AlwaysActiveAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.AntiAirAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.CloseMeleeAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.GrabAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.HakiAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ReactiveGuardAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPBossEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModEntityPredicates;
import xyz.pixelatedw.mineminenomi.init.ModFactions;
import xyz.pixelatedw.mineminenomi.init.ModFightingStyles;
import xyz.pixelatedw.mineminenomi.init.ModMobs;
import xyz.pixelatedw.mineminenomi.init.ModNPCGroups;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.ModRaces;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncEntityStatsPacket;

public class KuroobiEntity extends OPBossEntity {
   private static final UUID STANDARD_SUPLEX_COOLDOWN_UUID = UUID.fromString("91faa9f6-2082-4c39-acee-bd1468241742");
   private static final UUID HARD_SAMEHADA_COOLDOWN_UUID = UUID.fromString("de69810e-d16f-49d6-8951-240215fe6582");
   private static final UUID HARD_SAMEHADA_DAMAGE_UUID = UUID.fromString("56963b0c-5966-482e-b023-e439905e5146");
   private static final UUID HARD_CHARGED_PUNCH_CHARGING_UUID = UUID.fromString("4e9fd1ef-fce7-4ee5-b6c9-0b15142599b6");
   private RevengeMeter revengeMeter;
   private MoveControl groundMovementController;
   private MoveControl waterMovementController;
   private NPCPhase<KuroobiEntity> groundPhase;
   private NPCPhase<KuroobiEntity> waterPhase;

   public KuroobiEntity(EntityType<KuroobiEntity> type, Level world) {
      super(type, world);
   }

   public KuroobiEntity(InProgressChallenge challenge) {
      super((EntityType)ModMobs.KUROOBI.get(), challenge);
   }

   public void preInit() {
      this.entityStats.setFaction((Faction)ModFactions.PIRATE.get());
      this.entityStats.setRace((Race)ModRaces.FISHMAN.get());
      this.entityStats.setSubRace((Race)ModRaces.FISHMAN_RAY.get());
      this.entityStats.setSkinTint(13555155);
      this.entityStats.setFightingStyle((FightingStyle)ModFightingStyles.BRAWLER.get());
   }

   public void initBoss() {
      this.revengeMeter = new RevengeMeter(this, 100, 1);
      this.groundMovementController = new MoveControl(this);
      this.waterMovementController = new HumanoidSwimMoveControl(this);
      this.groundPhase = new SimplePhase<KuroobiEntity>("Ground Phase", this);
      this.waterPhase = new SimplePhase<KuroobiEntity>("Water Phase", this, this::startWaterPhaseEvent, this::stopWaterPhaseEvent);
      ModNetwork.sendToAllAround(new SSyncEntityStatsPacket(this), this);
      FactionsWorldData worldData = FactionsWorldData.get();
      worldData.addTemporaryCrewMember(ModNPCGroups.ARLONG_PIRATES, this);
      this.m_21051_((Attribute)ModAttributes.TOUGHNESS.get()).m_22100_((double)2.0F);
      this.m_21051_((Attribute)ForgeMod.STEP_HEIGHT_ADDITION.get()).m_22100_((double)1.0F);
      this.revengeMeter.addCheck(new PhysicalHitRevengeCheck(2));
      this.revengeMeter.addCheck(new DeadzoneRevengeCheck(10));
      ((GroundPathNavigation)this.m_21573_()).m_26477_(true);
      this.f_21345_.m_25352_(0, new OpenDoorGoal(this, true));
      this.f_21345_.m_25352_(0, new JumpOutOfHoleGoal(this));
      this.f_21345_.m_25352_(3, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.f_21345_.m_25352_(3, new RandomLookAroundGoal(this));
      Predicate<Entity> factionScope = ModEntityPredicates.getEnemyFactions(this).and(ModEntityPredicates.IS_ENTITY_HARMLESS.negate());
      Predicate<LivingEntity> invisibleCheck = (t) -> factionScope.and(ModEntityPredicates.IS_INVISIBLE.negate()).test(t);
      this.f_21346_.m_25352_(1, new FactionHurtByTargetGoal(this, factionScope, new Class[0]));
      this.f_21346_.m_25352_(2, new NearestAttackableTargetGoal(this, Mob.class, 10, true, true, invisibleCheck));
      this.f_21346_.m_25352_(2, new NearestAttackableTargetGoal(this, Player.class, 10, true, true, invisibleCheck));
      CloseMeleeAbilityWrapperGoal<KachiageHaisokuAbility> kachiageWrapper = new CloseMeleeAbilityWrapperGoal<KachiageHaisokuAbility>(this, (AbilityCore)KachiageHaisokuAbility.INSTANCE.get());
      ((KachiageHaisokuAbility)kachiageWrapper.getAbility()).addCanUseCheck((entity, ability) -> {
         ChargedPunchAbility abl = (ChargedPunchAbility)AbilityCapability.get(entity).map((props) -> (ChargedPunchAbility)props.getEquippedAbility((AbilityCore)ChargedPunchAbility.INSTANCE.get())).orElse((Object)null);
         return abl != null && abl.isCharging() ? Result.fail((Component)null) : Result.success();
      });
      ((KachiageHaisokuAbility)kachiageWrapper.getAbility()).getComponent((AbilityComponentKey)ModAbilityComponents.DAMAGE.get()).ifPresent((comp) -> {
         if (this.waterPhase.isActive(this)) {
            LivingEntity lastHit = comp.getLastTarget();
            if (lastHit != null) {
               AbilityHelper.setDeltaMovement(lastHit, this.m_20184_().f_82479_, (double)-2.0F, this.m_20184_().f_82481_);
            }
         }

      });
      CloseMeleeAbilityWrapperGoal<ChargedPunchAbility> chargedPunchWrapper = new CloseMeleeAbilityWrapperGoal<ChargedPunchAbility>(this, (AbilityCore)ChargedPunchAbility.INSTANCE.get());
      ((ChargedPunchAbility)chargedPunchWrapper.getAbility()).addCanUseCheck((entity, ability) -> {
         KachiageHaisokuAbility kachiage = (KachiageHaisokuAbility)AbilityCapability.get(entity).map((props) -> (KachiageHaisokuAbility)props.getEquippedAbility((AbilityCore)KachiageHaisokuAbility.INSTANCE.get())).orElse((Object)null);
         SamehadaShoteiAbility samehada = (SamehadaShoteiAbility)AbilityCapability.get(entity).map((props) -> (SamehadaShoteiAbility)props.getEquippedAbility((AbilityCore)SamehadaShoteiAbility.INSTANCE.get())).orElse((Object)null);
         if (kachiage != null && kachiage.isContinuous()) {
            return Result.fail((Component)null);
         } else {
            return samehada != null && samehada.isContinuous() ? Result.fail((Component)null) : Result.success();
         }
      });
      ReactiveGuardAbilityWrapperGoal<SamehadaShoteiAbility> samehadaWrapper = new ReactiveGuardAbilityWrapperGoal<SamehadaShoteiAbility>(this, (AbilityCore)SamehadaShoteiAbility.INSTANCE.get());
      CloseMeleeAbilityWrapperGoal<KarakusagawaraSeikenAbility> karakusagawaraWrapper = new CloseMeleeAbilityWrapperGoal<KarakusagawaraSeikenAbility>(this, (AbilityCore)KarakusagawaraSeikenAbility.INSTANCE.get());
      ((KarakusagawaraSeikenAbility)karakusagawaraWrapper.getAbility()).getComponent((AbilityComponentKey)ModAbilityComponents.COOLDOWN.get()).ifPresent((comp) -> comp.startCooldown(this, 100.0F));
      GrabAbilityWrapperGoal<SuplexAbility> suplexWrapper = new GrabAbilityWrapperGoal<SuplexAbility>(this, (AbilityCore)SuplexAbility.INSTANCE.get());
      ((SuplexAbility)suplexWrapper.getAbility()).addCanUseCheck((entity, ability) -> {
         SamehadaShoteiAbility abl = (SamehadaShoteiAbility)AbilityCapability.get(entity).map((props) -> (SamehadaShoteiAbility)props.getEquippedAbility((AbilityCore)SamehadaShoteiAbility.INSTANCE.get())).orElse((Object)null);
         return abl != null && abl.isContinuous() ? Result.fail((Component)null) : Result.success();
      });
      ((SuplexAbility)suplexWrapper.getAbility()).getComponent((AbilityComponentKey)ModAbilityComponents.CHARGE.get()).ifPresent((comp) -> comp.addTickEvent(90, (entity, ability) -> {
            if (this.waterPhase.isActive(this) && !(comp.getChargePercentage() < 0.8F)) {
               LivingEntity target = (LivingEntity)((SuplexAbility)suplexWrapper.getAbility()).getComponent((AbilityComponentKey)ModAbilityComponents.GRAB.get()).flatMap((grabComp) -> Optional.ofNullable(grabComp.getGrabbedEntity())).orElse((Object)null);
               if (target != null) {
                  AbilityHelper.setDeltaMovement(this, this.m_20184_().m_82520_((double)0.0F, -1.3, (double)0.0F));
               }

            }
         }));
      this.f_21345_.m_25352_(0, new AlwaysActiveAbilityWrapperGoal(this, (AbilityCore)FishmanPassiveBonusesAbility.INSTANCE.get()));
      this.f_21345_.m_25352_(1, new ImprovedMeleeAttackGoal(this, (double)1.0F, true));
      this.groundPhase.addGoal(1, new SprintTowardsTargetGoal(this));
      this.groundPhase.addGoal(3, new AntiAirAbilityWrapperGoal(this, (AbilityCore)TakedownKickAbility.INSTANCE.get()));
      this.groundPhase.addGoal(3, suplexWrapper);
      this.groundPhase.addGoal(4, samehadaWrapper);
      this.groundPhase.addGoal(4, chargedPunchWrapper);
      this.groundPhase.addGoal(4, kachiageWrapper);
      this.groundPhase.addGoal(5, karakusagawaraWrapper);
      this.waterPhase.addGoal(0, new DashDodgeTargetGoal(this, 120.0F, 5.0F));
      this.waterPhase.addGoal(0, new DashDodgeProjectilesGoal(this, 100.0F, 2.25F));
      this.waterPhase.addGoal(3, suplexWrapper);
      this.waterPhase.addGoal(4, chargedPunchWrapper);
      this.waterPhase.addGoal(4, kachiageWrapper);
      this.waterPhase.addGoal(5, karakusagawaraWrapper);
      this.getPhaseManager().setPhase(this.groundPhase);
      if (this.getChallengeInfo().isDifficultyStandard()) {
         this.entityStats.setDoriki((double)2000.0F);
         this.m_21051_((Attribute)ModAttributes.GCD.get()).m_22100_((double)40.0F);
         this.m_21051_((Attribute)ModAttributes.PUNCH_DAMAGE.get()).m_22100_((double)2.0F);
         this.m_21051_((Attribute)ForgeMod.SWIM_SPEED.get()).m_22100_((double)3.0F);
         this.revengeMeter.addCheck(new DeadzoneRevengeCheck(5));
         ((KarakusagawaraSeikenAbility)karakusagawaraWrapper.getAbility()).addCanUseCheck((entity, ability) -> (double)entity.m_21223_() <= WyHelper.percentage((double)30.0F, (double)entity.m_21233_()) ? Result.success() : Result.fail((Component)null));
         ((SuplexAbility)suplexWrapper.getAbility()).getComponent((AbilityComponentKey)ModAbilityComponents.COOLDOWN.get()).ifPresent((comp) -> comp.getBonusManager().addBonus(STANDARD_SUPLEX_COOLDOWN_UUID, "Standard Suplex Cooldown Bonus", BonusOperation.ADD, 40.0F));
      } else {
         this.entityStats.setDoriki((double)10000.0F);
         this.hakiCapability.setBusoshokuHakiExp(100.0F);
         this.hakiCapability.setKenbunshokuHakiExp(100.0F);
         this.m_21051_((Attribute)ModAttributes.GCD.get()).m_22100_((double)20.0F);
         this.m_21051_((Attribute)ModAttributes.PUNCH_DAMAGE.get()).m_22100_((double)5.0F);
         this.m_21051_(Attributes.f_22281_).m_22100_((double)5.0F);
         this.m_21051_((Attribute)ModAttributes.TOUGHNESS.get()).m_22100_((double)7.0F);
         this.m_21051_(Attributes.f_22276_).m_22100_((double)350.0F);
         this.m_21051_(Attributes.f_22284_).m_22100_((double)20.0F);
         this.m_21051_(Attributes.f_22285_).m_22100_((double)8.0F);
         this.m_21051_((Attribute)ForgeMod.SWIM_SPEED.get()).m_22100_((double)5.0F);
         this.revengeMeter.addCheck(new DeadzoneRevengeCheck(10));
         ((SamehadaShoteiAbility)samehadaWrapper.getAbility()).getComponent((AbilityComponentKey)ModAbilityComponents.COOLDOWN.get()).ifPresent((comp) -> comp.getBonusManager().addBonus(HARD_SAMEHADA_COOLDOWN_UUID, "Hard Samehada Cooldown Bonus", BonusOperation.MUL, 0.75F));
         ((SamehadaShoteiAbility)samehadaWrapper.getAbility()).getComponent((AbilityComponentKey)ModAbilityComponents.DAMAGE.get()).ifPresent((comp) -> comp.getBonusManager().addBonus(HARD_SAMEHADA_DAMAGE_UUID, "Hard Samehada Damage Bonus", BonusOperation.MUL, 2.0F));
         ((ChargedPunchAbility)chargedPunchWrapper.getAbility()).getComponent((AbilityComponentKey)ModAbilityComponents.CHARGE.get()).ifPresent((comp) -> comp.getMaxChargeBonusManager().addBonus(HARD_CHARGED_PUNCH_CHARGING_UUID, "Hard Charged Punch Charging Bonus", BonusOperation.MUL, 0.5F));
         CloseMeleeAbilityWrapperGoal<HakaiHoAbility> hakaiHoWrapper = new CloseMeleeAbilityWrapperGoal<HakaiHoAbility>(this, (AbilityCore)HakaiHoAbility.INSTANCE.get());
         ((HakaiHoAbility)hakaiHoWrapper.getAbility()).getComponent((AbilityComponentKey)ModAbilityComponents.COOLDOWN.get()).ifPresent((comp) -> comp.startCooldown(this, 100.0F));
         ((HakaiHoAbility)hakaiHoWrapper.getAbility()).getComponent((AbilityComponentKey)ModAbilityComponents.DAMAGE.get()).ifPresent((comp) -> {
            if (this.waterPhase.isActive(this)) {
               LivingEntity lastHit = comp.getLastTarget();
               if (lastHit != null) {
                  AbilityHelper.setDeltaMovement(lastHit, this.m_20184_().f_82479_, (double)-2.0F, this.m_20184_().f_82481_);
               }
            }

         });
         CloseMeleeAbilityWrapperGoal<AntiMannerKickCourseAbility> antiMannerWrapper = new CloseMeleeAbilityWrapperGoal<AntiMannerKickCourseAbility>(this, (AbilityCore)AntiMannerKickCourseAbility.INSTANCE.get());
         AnimationComponent antiMannerAnimationComp = new AnimationComponent(antiMannerWrapper.getAbility());
         ((AntiMannerKickCourseAbility)antiMannerWrapper.getAbility()).addComponents(new AbilityComponent[]{antiMannerAnimationComp});
         ((AntiMannerKickCourseAbility)antiMannerWrapper.getAbility()).getComponent((AbilityComponentKey)ModAbilityComponents.COOLDOWN.get()).ifPresent((comp) -> comp.startCooldown(this, 120.0F));
         ((AntiMannerKickCourseAbility)antiMannerWrapper.getAbility()).getComponent((AbilityComponentKey)ModAbilityComponents.DAMAGE.get()).ifPresent((comp) -> {
            if (this.waterPhase.isActive(this)) {
               LivingEntity lastHit = comp.getLastTarget();
               if (lastHit != null) {
                  antiMannerAnimationComp.start(this, ModAnimations.TAKEDOWN_KICK, 7);
                  AbilityHelper.setDeltaMovement(lastHit, this.m_20184_().f_82479_, (double)-2.0F, this.m_20184_().f_82481_);
               }
            }

         });
         this.f_21345_.m_25352_(0, new HakiAbilityWrapperGoal(this, (AbilityCore)BusoshokuHakiInternalDestructionAbility.INSTANCE.get()));
         this.f_21345_.m_25352_(0, new HakiAbilityWrapperGoal(this, (AbilityCore)BusoshokuHakiFullBodyHardeningAbility.INSTANCE.get()));
         this.groundPhase.addGoal(3, new CloseMeleeAbilityWrapperGoal(this, (AbilityCore)PartyTableKickCourseAbility.INSTANCE.get()));
         this.groundPhase.addGoal(3, antiMannerWrapper);
         this.groundPhase.addGoal(3, hakaiHoWrapper);
         this.groundPhase.addGoal(3, new GrabAbilityWrapperGoal(this, (AbilityCore)SpinningBrawlAbility.INSTANCE.get()));
         this.waterPhase.addGoal(3, antiMannerWrapper);
         this.waterPhase.addGoal(3, hakaiHoWrapper);
      }

   }

   public static AttributeSupplier.Builder createAttributes() {
      return OPEntity.createAttributes().m_22268_(Attributes.f_22277_, (double)60.0F).m_22268_(Attributes.f_22279_, (double)0.29F).m_22268_(Attributes.f_22281_, (double)3.0F).m_22268_(Attributes.f_22276_, (double)250.0F).m_22268_(Attributes.f_22278_, 0.2);
   }

   public void m_8119_() {
      super.m_8119_();
      if (!this.m_9236_().f_46443_ && this.m_6084_()) {
         this.revengeMeter.tick();
         if (this.m_20069_()) {
            this.getPhaseManager().setPhase(this.waterPhase);
         } else {
            this.getPhaseManager().setPhase(this.groundPhase);
         }
      }

   }

   private void startWaterPhaseEvent(KuroobiEntity entity) {
      this.f_21342_ = this.waterMovementController;
   }

   private void stopWaterPhaseEvent(KuroobiEntity entity) {
      this.f_21342_ = this.groundMovementController;
   }

   public Set<RevengeMeter> getRevengeMeters() {
      return Sets.newHashSet(new RevengeMeter[]{this.revengeMeter});
   }
}
