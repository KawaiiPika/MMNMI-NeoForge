package xyz.pixelatedw.mineminenomi.entities.mobs.pirates.arlongpirates;

import com.google.common.collect.Sets;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.OpenDoorGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;
import xyz.pixelatedw.mineminenomi.abilities.TakedownKickAbility;
import xyz.pixelatedw.mineminenomi.abilities.blackleg.PartyTableKickCourseAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.ChargedPunchAbility;
import xyz.pixelatedw.mineminenomi.abilities.fishmankarate.FishmanPassiveBonusesAbility;
import xyz.pixelatedw.mineminenomi.abilities.fishmankarate.KachiageHaisokuAbility;
import xyz.pixelatedw.mineminenomi.abilities.fishmankarate.PackOfSharksAbility;
import xyz.pixelatedw.mineminenomi.abilities.fishmankarate.SamehadaShoteiAbility;
import xyz.pixelatedw.mineminenomi.abilities.fishmankarate.SharkOnToothAbility;
import xyz.pixelatedw.mineminenomi.abilities.fishmankarate.UchimizuAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiFullBodyHardeningAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiInternalDestructionAbility;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusOperation;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.challenges.InProgressChallenge;
import xyz.pixelatedw.mineminenomi.api.entities.ai.NPCPhase;
import xyz.pixelatedw.mineminenomi.api.entities.ai.SimplePhase;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.entities.revenge.DeadzoneRevengeCheck;
import xyz.pixelatedw.mineminenomi.api.entities.revenge.GankingRevengeCheck;
import xyz.pixelatedw.mineminenomi.api.entities.revenge.PhysicalHitRevengeCheck;
import xyz.pixelatedw.mineminenomi.api.entities.revenge.RevengeMeter;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
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
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.DashAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.HakiAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ProjectileAbilityWrapperGoal;
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
import xyz.pixelatedw.mineminenomi.init.ModWeapons;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncEntityStatsPacket;

public class ArlongEntity extends OPBossEntity {
   private static final UUID STANDARD_UCHIMIZU_COOLDOWN_UUID = UUID.fromString("efa25144-6946-48c2-8e2a-cb2135b98783");
   private static final UUID HARD_MURASAME_COOLDOWN_UUID = UUID.fromString("08737d15-31f0-4103-b09a-99cd98e72cda");
   private static final UUID WATER_UCHIMIZU_COOLDOWN_UUID = UUID.fromString("55220e18-d068-4913-9cf5-ba7c57eeeb4e");
   private static final UUID WATER_SHARK_ON_TOOTH_COOLDOWN_UUID = UUID.fromString("c5c03564-411b-4a21-85e9-e9b003c91123");
   private static final AttributeModifier GCD_MOD;
   private RevengeMeter revengeMeter;
   private float revengeThreshold;
   private MoveControl groundMoveControl;
   private MoveControl waterMoveControl;
   private NPCPhase<ArlongEntity> firstPhase;
   private NPCPhase<ArlongEntity> secondPhase;
   private NPCPhase<ArlongEntity> waterPhase;
   private Optional<CooldownComponent> uchimizuCooldownComponent;
   private DashAbilityWrapperGoal<SharkOnToothAbility> sharkOnToothWrapper;
   private Optional<CooldownComponent> sharkOnToothCooldownComponent;

   public ArlongEntity(EntityType<ArlongEntity> type, Level world) {
      super(type, world);
   }

   public ArlongEntity(InProgressChallenge challenge) {
      super((EntityType)ModMobs.ARLONG.get(), challenge);
      this.revengeThreshold = challenge.isStandardDifficulty() ? 0.5F : 0.3F;
   }

   public void preInit() {
      this.entityStats.setFaction((Faction)ModFactions.PIRATE.get());
      this.entityStats.setRace((Race)ModRaces.FISHMAN.get());
      this.entityStats.setSubRace((Race)ModRaces.FISHMAN_SAWSHARK.get());
      this.entityStats.setSkinTint(12633078);
      this.entityStats.setFightingStyle((FightingStyle)ModFightingStyles.BRAWLER.get());
   }

   public void initBoss() {
      this.revengeMeter = new RevengeMeter(this, 100, 1);
      this.groundMoveControl = new MoveControl(this);
      this.waterMoveControl = new HumanoidSwimMoveControl(this);
      this.firstPhase = new SimplePhase<ArlongEntity>("First Phase", this);
      this.secondPhase = new SimplePhase<ArlongEntity>("Second Phase", this, this::startSecondPhaseEvent);
      this.waterPhase = new SimplePhase<ArlongEntity>("Water Phase", this, this::startWaterPhaseEvent, this::stopWaterPhaseEvent);
      ModNetwork.sendToAllAround(new SSyncEntityStatsPacket(this), this);
      FactionsWorldData worldData = FactionsWorldData.get();
      worldData.addTemporaryCrewMember(ModNPCGroups.ARLONG_PIRATES, this);
      this.m_21051_((Attribute)ModAttributes.TOUGHNESS.get()).m_22100_((double)2.0F);
      this.m_21051_((Attribute)ForgeMod.STEP_HEIGHT_ADDITION.get()).m_22100_((double)1.0F);
      this.revengeMeter.addCheck(new PhysicalHitRevengeCheck(1));
      this.revengeMeter.addCheck(new GankingRevengeCheck(5, 5.0F));
      ProjectileAbilityWrapperGoal<UchimizuAbility> uchimizuWrapper = new ProjectileAbilityWrapperGoal<UchimizuAbility>(this, (AbilityCore)UchimizuAbility.INSTANCE.get());
      Optional<CooldownComponent> cooldownComponent = ((UchimizuAbility)uchimizuWrapper.getAbility()).getComponent((AbilityComponentKey)ModAbilityComponents.COOLDOWN.get());
      cooldownComponent.ifPresent((comp) -> {
         comp.getBonusManager().addBonus(STANDARD_UCHIMIZU_COOLDOWN_UUID, "Standard Uchimizu Bonus", BonusOperation.MUL, 2.0F);
         comp.startCooldown(this, 120.0F);
      });
      CloseMeleeAbilityWrapperGoal<KachiageHaisokuAbility> kachiageWrapper = new CloseMeleeAbilityWrapperGoal<KachiageHaisokuAbility>(this, (AbilityCore)KachiageHaisokuAbility.INSTANCE.get());
      ((KachiageHaisokuAbility)kachiageWrapper.getAbility()).addCanUseCheck((entity, ability) -> {
         ChargedPunchAbility abl = (ChargedPunchAbility)AbilityCapability.get(entity).map((props) -> (ChargedPunchAbility)props.getEquippedAbility((AbilityCore)ChargedPunchAbility.INSTANCE.get())).orElse((Object)null);
         return abl != null && abl.isCharging() ? Result.fail((Component)null) : Result.success();
      });
      CloseMeleeAbilityWrapperGoal<ChargedPunchAbility> chargedPunchWrapper = new CloseMeleeAbilityWrapperGoal<ChargedPunchAbility>(this, (AbilityCore)ChargedPunchAbility.INSTANCE.get());
      ((ChargedPunchAbility)chargedPunchWrapper.getAbility()).addCanUseCheck((entity, ability) -> {
         KachiageHaisokuAbility abl = (KachiageHaisokuAbility)AbilityCapability.get(entity).map((props) -> (KachiageHaisokuAbility)props.getEquippedAbility((AbilityCore)KachiageHaisokuAbility.INSTANCE.get())).orElse((Object)null);
         return abl != null && abl.isContinuous() ? Result.fail((Component)null) : Result.success();
      });
      ReactiveGuardAbilityWrapperGoal<SamehadaShoteiAbility> samehadaWrapper = new ReactiveGuardAbilityWrapperGoal<SamehadaShoteiAbility>(this, (AbilityCore)SamehadaShoteiAbility.INSTANCE.get());
      ((SamehadaShoteiAbility)samehadaWrapper.getAbility()).addCanUseCheck((entity, ability) -> {
         SharkOnToothAbility abl = (SharkOnToothAbility)AbilityCapability.get(entity).map((props) -> (SharkOnToothAbility)props.getEquippedAbility((AbilityCore)SharkOnToothAbility.INSTANCE.get())).orElse((Object)null);
         return abl != null && abl.isContinuous() ? Result.fail((Component)null) : Result.success();
      });
      this.sharkOnToothWrapper = new DashAbilityWrapperGoal<SharkOnToothAbility>(this, (AbilityCore)SharkOnToothAbility.INSTANCE.get());
      this.sharkOnToothCooldownComponent = ((SharkOnToothAbility)this.sharkOnToothWrapper.getAbility()).getComponent((AbilityComponentKey)ModAbilityComponents.COOLDOWN.get());
      this.uchimizuCooldownComponent = ((UchimizuAbility)uchimizuWrapper.getAbility()).getComponent((AbilityComponentKey)ModAbilityComponents.COOLDOWN.get());
      SharkOnToothAbility sharkOnToothAbility = (SharkOnToothAbility)this.sharkOnToothWrapper.getAbility();
      sharkOnToothAbility.addCanUseCheck((entity, ability) -> {
         LivingEntity target = this.m_5448_();
         if (this.waterPhase.isActive(this) && target != null) {
            boolean isTargetFishman = (Boolean)EntityStatsCapability.get(target).map((props) -> props.isFishman()).orElse(false);
            if (isTargetFishman) {
               return Result.success();
            }

            if (target.m_20186_() - this.m_20186_() <= (double)2.0F) {
               return Result.fail((Component)null);
            }
         }

         return Result.success();
      });
      sharkOnToothAbility.addTickEvent((entity, ability) -> {
         if (this.waterPhase.isActive(this) && sharkOnToothAbility.isContinuous() && sharkOnToothAbility.hasHitTarget()) {
            LivingEntity target = ((DealDamageComponent)sharkOnToothAbility.getComponent((AbilityComponentKey)ModAbilityComponents.DAMAGE.get()).get()).getLastTarget();
            if (target != null) {
               ((ContinuousComponent)sharkOnToothAbility.getComponent((AbilityComponentKey)ModAbilityComponents.CONTINUOUS.get()).get()).stopContinuity(this);
               ((AnimationComponent)sharkOnToothAbility.getComponent((AbilityComponentKey)ModAbilityComponents.ANIMATION.get()).get()).start(this, ModAnimations.TAKEDOWN_KICK, 7);
               AbilityHelper.setDeltaMovement(target, entity.m_20184_().f_82479_, (double)-5.0F, entity.m_20184_().f_82481_);
            }
         }

      });
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
      this.f_21345_.m_25352_(0, new AlwaysActiveAbilityWrapperGoal(this, (AbilityCore)FishmanPassiveBonusesAbility.INSTANCE.get()));
      this.f_21345_.m_25352_(1, new ImprovedMeleeAttackGoal(this, (double)1.0F, true));
      this.firstPhase.addGoal(1, new SprintTowardsTargetGoal(this));
      this.firstPhase.addGoal(3, uchimizuWrapper);
      this.firstPhase.addGoal(3, new AntiAirAbilityWrapperGoal(this, (AbilityCore)TakedownKickAbility.INSTANCE.get()));
      this.firstPhase.addGoal(4, chargedPunchWrapper);
      this.firstPhase.addGoal(4, samehadaWrapper);
      this.firstPhase.addGoal(4, kachiageWrapper);
      this.firstPhase.addGoal(4, this.sharkOnToothWrapper);
      this.secondPhase.addGoal(1, new SprintTowardsTargetGoal(this));
      this.secondPhase.addGoal(3, uchimizuWrapper);
      this.secondPhase.addGoal(3, new AntiAirAbilityWrapperGoal(this, (AbilityCore)TakedownKickAbility.INSTANCE.get()));
      this.secondPhase.addGoal(4, samehadaWrapper);
      this.secondPhase.addGoal(4, kachiageWrapper);
      this.secondPhase.addGoal(4, this.sharkOnToothWrapper);
      this.waterPhase.addGoal(0, new DashDodgeTargetGoal(this, 100.0F, 5.0F));
      this.waterPhase.addGoal(0, new DashDodgeProjectilesGoal(this, 70.0F, 2.25F));
      this.waterPhase.addGoal(3, uchimizuWrapper);
      this.waterPhase.addGoal(4, this.sharkOnToothWrapper);
      this.getPhaseManager().setPhase(this.firstPhase);
      if (this.getChallengeInfo().isDifficultyStandard()) {
         this.entityStats.setDoriki((double)2000.0F);
         this.m_21051_((Attribute)ModAttributes.GCD.get()).m_22100_((double)40.0F);
         this.m_21051_((Attribute)ModAttributes.PUNCH_DAMAGE.get()).m_22100_((double)2.0F);
         this.m_21051_((Attribute)ForgeMod.SWIM_SPEED.get()).m_22100_((double)3.0F);
         this.revengeMeter.addCheck(new DeadzoneRevengeCheck(5));
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
         this.m_21051_((Attribute)ForgeMod.SWIM_SPEED.get()).m_22100_((double)5.0F);
         this.revengeMeter.addCheck(new DeadzoneRevengeCheck(10));
         ProjectileAbilityWrapperGoal<PackOfSharksAbility> murasameWrapper = new ProjectileAbilityWrapperGoal<PackOfSharksAbility>(this, (AbilityCore)PackOfSharksAbility.INSTANCE.get());
         Optional<CooldownComponent> murasameCooldownComponent = ((PackOfSharksAbility)murasameWrapper.getAbility()).getComponent((AbilityComponentKey)ModAbilityComponents.COOLDOWN.get());
         murasameCooldownComponent.ifPresent((comp) -> {
            comp.getBonusManager().addBonus(HARD_MURASAME_COOLDOWN_UUID, "Murasame Cooldown Bonus", BonusOperation.ADD, -200.0F);
            comp.startCooldown(this, 100.0F);
         });
         Optional<CooldownComponent> uchimizuCooldownComponent = ((UchimizuAbility)uchimizuWrapper.getAbility()).getComponent((AbilityComponentKey)ModAbilityComponents.COOLDOWN.get());
         uchimizuCooldownComponent.ifPresent((comp) -> comp.getBonusManager().removeBonus(STANDARD_UCHIMIZU_COOLDOWN_UUID));
         this.f_21345_.m_25352_(0, new HakiAbilityWrapperGoal(this, (AbilityCore)BusoshokuHakiInternalDestructionAbility.INSTANCE.get()));
         this.f_21345_.m_25352_(0, new HakiAbilityWrapperGoal(this, (AbilityCore)BusoshokuHakiFullBodyHardeningAbility.INSTANCE.get()));
         this.f_21345_.m_25352_(3, murasameWrapper);
         this.firstPhase.addGoal(3, new CloseMeleeAbilityWrapperGoal(this, (AbilityCore)PartyTableKickCourseAbility.INSTANCE.get()));
         this.secondPhase.addGoal(0, new DashDodgeTargetGoal(this, 120.0F, 5.0F));
         this.secondPhase.addGoal(3, new CloseMeleeAbilityWrapperGoal(this, (AbilityCore)PartyTableKickCourseAbility.INSTANCE.get()));
      }

   }

   public static AttributeSupplier.Builder createAttributes() {
      return OPEntity.createAttributes().m_22268_(Attributes.f_22277_, (double)60.0F).m_22268_(Attributes.f_22279_, (double)0.3F).m_22268_(Attributes.f_22281_, (double)4.0F).m_22268_(Attributes.f_22276_, (double)300.0F).m_22268_(Attributes.f_22278_, 0.2);
   }

   public void m_8119_() {
      super.m_8119_();
      if (!this.m_9236_().f_46443_ && this.m_6084_()) {
         this.revengeMeter.tick();
         if (this.secondPhase.isActive(this) && this.revengeMeter.getRevengePercentage() > this.revengeThreshold && this.sharkOnToothCooldownComponent.isPresent() && ((CooldownComponent)this.sharkOnToothCooldownComponent.get()).isOnCooldown()) {
            ((CooldownComponent)this.sharkOnToothCooldownComponent.get()).stopCooldown(this);
            this.revengeMeter.reduceRevengeValue(25);
         }

         if (this.m_20069_()) {
            this.getPhaseManager().setPhase(this.waterPhase);
         } else if (this.waterPhase.isActive(this) && this.getPhaseManager().getPreviousPhase() != null) {
            this.getPhaseManager().setPhase(this.getPhaseManager().getPreviousPhase());
         } else if (this.firstPhase.isActive(this) && (double)this.m_21223_() <= WyHelper.percentage((double)50.0F, (double)this.m_21233_())) {
            this.getPhaseManager().setPhase(this.secondPhase);
         } else if (!this.secondPhase.isActive(this)) {
            this.getPhaseManager().setPhase(this.firstPhase);
         }
      }

   }

   private void startSecondPhaseEvent(ArlongEntity entity) {
      this.m_8061_(EquipmentSlot.MAINHAND, new ItemStack((ItemLike)ModWeapons.KIRIBACHI.get()));
      this.m_21051_((Attribute)ForgeMod.ENTITY_REACH.get()).m_22100_((double)1.0F);
      if (this.isDifficultyHardOrAbove()) {
         AttributeInstance attr = entity.m_21051_((Attribute)ModAttributes.GCD.get());
         if (attr != null && !attr.m_22109_(GCD_MOD)) {
            attr.m_22118_(GCD_MOD);
         }
      }

   }

   private void startWaterPhaseEvent(ArlongEntity entity) {
      this.f_21342_ = this.waterMoveControl;
      this.uchimizuCooldownComponent.ifPresent((comp) -> {
         if (!comp.getBonusManager().hasBonus(WATER_UCHIMIZU_COOLDOWN_UUID)) {
            comp.getBonusManager().addBonus(WATER_UCHIMIZU_COOLDOWN_UUID, "Uchimizu Cooldown Water Bonus", BonusOperation.MUL, 0.5F);
         }

      });
      this.sharkOnToothCooldownComponent.ifPresent((comp) -> {
         if (!comp.getBonusManager().hasBonus(WATER_SHARK_ON_TOOTH_COOLDOWN_UUID)) {
            comp.getBonusManager().addBonus(WATER_SHARK_ON_TOOTH_COOLDOWN_UUID, "Shark on Tooth Cooldown Water Bonus", BonusOperation.MUL, 0.5F);
         }

      });
   }

   private void stopWaterPhaseEvent(ArlongEntity entity) {
      this.f_21342_ = this.groundMoveControl;
      this.uchimizuCooldownComponent.ifPresent((comp) -> comp.getBonusManager().removeBonus(WATER_UCHIMIZU_COOLDOWN_UUID));
      this.sharkOnToothCooldownComponent.ifPresent((comp) -> comp.getBonusManager().removeBonus(WATER_SHARK_ON_TOOTH_COOLDOWN_UUID));
   }

   public Set<RevengeMeter> getRevengeMeters() {
      return Sets.newHashSet(new RevengeMeter[]{this.revengeMeter});
   }

   static {
      GCD_MOD = new AttributeModifier(UUID.fromString("e478aceb-9865-40da-a137-6359ba503bf0"), "GCD Modifier", (double)-5.0F, Operation.ADDITION);
   }
}
