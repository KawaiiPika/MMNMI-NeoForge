package xyz.pixelatedw.mineminenomi.entities.mobs.pirates.arlongpirates;

import com.google.common.collect.Sets;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
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
import xyz.pixelatedw.mineminenomi.abilities.fishmankarate.FishmanPassiveBonusesAbility;
import xyz.pixelatedw.mineminenomi.abilities.fishmankarate.MizuOsuAbility;
import xyz.pixelatedw.mineminenomi.abilities.fishmankarate.MizuShuryudanAbility;
import xyz.pixelatedw.mineminenomi.abilities.fishmankarate.MizuTaihoAbility;
import xyz.pixelatedw.mineminenomi.abilities.fishmankarate.PackOfSharksAbility;
import xyz.pixelatedw.mineminenomi.abilities.fishmankarate.UchimizuAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiImbuingAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiInternalDestructionAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusOperation;
import xyz.pixelatedw.mineminenomi.api.challenges.InProgressChallenge;
import xyz.pixelatedw.mineminenomi.api.entities.ai.NPCPhase;
import xyz.pixelatedw.mineminenomi.api.entities.ai.SimplePhase;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.entities.revenge.RevengeMeter;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;
import xyz.pixelatedw.mineminenomi.data.world.FactionsWorldData;
import xyz.pixelatedw.mineminenomi.entities.ai.controllers.HumanoidSwimMoveControl;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.ClimbOutOfHoleGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.DashDodgeProjectilesGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.DashDodgeTargetGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.FactionHurtByTargetGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.ImprovedMeleeAttackGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.RunAwayFromTargetGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.AlwaysActiveAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.HakiAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ProjectileAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ReactiveGuardAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.RepeaterAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPBossEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModEntityPredicates;
import xyz.pixelatedw.mineminenomi.init.ModFactions;
import xyz.pixelatedw.mineminenomi.init.ModFightingStyles;
import xyz.pixelatedw.mineminenomi.init.ModMobs;
import xyz.pixelatedw.mineminenomi.init.ModNPCGroups;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.ModRaces;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncEntityStatsPacket;

public class ChewEntity extends OPBossEntity {
   private static final UUID MIZU_SHURYUDAN_COOLDOWN_UUID = UUID.fromString("01fa80f8-1ce1-4456-8696-ed2a6ebd1468");
   private static final UUID MIZU_OSU_COOLDOWN_UUID = UUID.fromString("a5f2b30b-1526-4af7-a38b-ec1b0e1922a1");
   private static final UUID HARD_MIZU_TAIHO_COOLDOWN_UUID = UUID.fromString("beccb13e-c813-4d8e-b846-0b05b4ee7557");
   private static final UUID HARD_MURSAMAE_COOLDOWN_UUID = UUID.fromString("52afe78f-6be1-43be-89d5-12f3e405f9ff");
   private RevengeMeter revengeMeter;
   private MoveControl groundMovementController;
   private MoveControl waterMovementController;
   private NPCPhase<ChewEntity> groundPhase;
   private NPCPhase<ChewEntity> waterPhase;

   public ChewEntity(EntityType<ChewEntity> type, Level world) {
      super(type, world);
   }

   public ChewEntity(InProgressChallenge challenge) {
      super((EntityType)ModMobs.CHEW.get(), challenge);
   }

   public void preInit() {
      this.entityStats.setFaction((Faction)ModFactions.PIRATE.get());
      this.entityStats.setRace((Race)ModRaces.FISHMAN.get());
      this.entityStats.setSubRace((Race)ModRaces.FISHMAN_GARFISH.get());
      this.entityStats.setSkinTint(11394009);
      this.entityStats.setFightingStyle((FightingStyle)ModFightingStyles.SNIPER.get());
   }

   public void initBoss() {
      this.revengeMeter = new RevengeMeter(this, 100, 1);
      this.groundMovementController = new MoveControl(this);
      this.waterMovementController = new HumanoidSwimMoveControl(this);
      this.groundPhase = new SimplePhase<ChewEntity>("Ground Phase", this);
      this.waterPhase = new SimplePhase<ChewEntity>("Water Phase", this, this::startWaterPhaseEvent, this::stopWaterPhaseEvent);
      ModNetwork.sendToAllAround(new SSyncEntityStatsPacket(this), this);
      FactionsWorldData worldData = FactionsWorldData.get();
      worldData.addTemporaryCrewMember(ModNPCGroups.ARLONG_PIRATES, this);
      this.m_21051_((Attribute)ModAttributes.TOUGHNESS.get()).m_22100_((double)1.0F);
      this.m_21051_((Attribute)ForgeMod.STEP_HEIGHT_ADDITION.get()).m_22100_((double)1.0F);
      ProjectileAbilityWrapperGoal<UchimizuAbility> uchimizuWrapper = new ProjectileAbilityWrapperGoal<UchimizuAbility>(this, (AbilityCore)UchimizuAbility.INSTANCE.get());
      ((UchimizuAbility)uchimizuWrapper.getAbility()).getComponent((AbilityComponentKey)ModAbilityComponents.COOLDOWN.get()).ifPresent((comp) -> comp.startCooldown(this, 60.0F));
      ProjectileAbilityWrapperGoal<MizuTaihoAbility> mizuTaihoWrapper = new ProjectileAbilityWrapperGoal<MizuTaihoAbility>(this, (AbilityCore)MizuTaihoAbility.INSTANCE.get());
      ReactiveGuardAbilityWrapperGoal<MizuOsuAbility> mizuOsuWrapper = new ReactiveGuardAbilityWrapperGoal<MizuOsuAbility>(this, (AbilityCore)MizuOsuAbility.INSTANCE.get());
      ((MizuOsuAbility)mizuOsuWrapper.getAbility()).getComponent((AbilityComponentKey)ModAbilityComponents.COOLDOWN.get()).ifPresent((comp) -> comp.getBonusManager().addBonus(MIZU_OSU_COOLDOWN_UUID, "Mizu Osu Cooldown Bonus", BonusOperation.ADD, -60.0F));
      RepeaterAbilityWrapperGoal<MizuShuryudanAbility> mizuShuryudanWrapper = new RepeaterAbilityWrapperGoal<MizuShuryudanAbility>(this, (AbilityCore)MizuShuryudanAbility.INSTANCE.get());
      ((MizuShuryudanAbility)mizuShuryudanWrapper.getAbility()).getComponent((AbilityComponentKey)ModAbilityComponents.COOLDOWN.get()).ifPresent((comp) -> comp.getBonusManager().addBonus(MIZU_SHURYUDAN_COOLDOWN_UUID, "Mizu Shuryudan Cooldown Bonus", BonusOperation.ADD, -40.0F));
      ((GroundPathNavigation)this.m_21573_()).m_26477_(true);
      this.f_21345_.m_25352_(0, new OpenDoorGoal(this, true));
      this.f_21345_.m_25352_(0, new ClimbOutOfHoleGoal(this));
      this.f_21345_.m_25352_(3, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.f_21345_.m_25352_(3, new RandomLookAroundGoal(this));
      Predicate<Entity> factionScope = ModEntityPredicates.getEnemyFactions(this).and(ModEntityPredicates.IS_ENTITY_HARMLESS.negate());
      Predicate<LivingEntity> invisibleCheck = (t) -> factionScope.and(ModEntityPredicates.IS_INVISIBLE.negate()).test(t);
      this.f_21346_.m_25352_(1, new FactionHurtByTargetGoal(this, factionScope, new Class[0]));
      this.f_21346_.m_25352_(2, new NearestAttackableTargetGoal(this, Mob.class, 10, true, true, invisibleCheck));
      this.f_21346_.m_25352_(2, new NearestAttackableTargetGoal(this, Player.class, 10, true, true, invisibleCheck));
      this.f_21345_.m_25352_(0, new AlwaysActiveAbilityWrapperGoal(this, (AbilityCore)FishmanPassiveBonusesAbility.INSTANCE.get()));
      this.f_21345_.m_25352_(1, (new ImprovedMeleeAttackGoal(this, (double)0.8F, true)).setEarlyStop(this::isInRangeForAbilities));
      this.f_21345_.m_25352_(3, uchimizuWrapper);
      this.f_21345_.m_25352_(4, mizuShuryudanWrapper);
      this.f_21345_.m_25352_(4, mizuOsuWrapper);
      this.f_21345_.m_25352_(4, mizuTaihoWrapper);
      this.getPhaseManager().setPhase(this.groundPhase);
      if (this.getChallengeInfo().isDifficultyStandard()) {
         this.entityStats.setDoriki((double)2000.0F);
         this.m_21051_((Attribute)ModAttributes.GCD.get()).m_22100_((double)40.0F);
         this.m_21051_((Attribute)ModAttributes.PUNCH_DAMAGE.get()).m_22100_((double)2.0F);
         this.m_21051_((Attribute)ForgeMod.SWIM_SPEED.get()).m_22100_((double)3.0F);
         this.f_21345_.m_25352_(0, new RunAwayFromTargetGoal(this, (double)1.5F, 100, 200));
         this.f_21345_.m_25352_(0, new DashDodgeTargetGoal(this, 200.0F, 5.0F));
         this.f_21345_.m_25352_(0, new DashDodgeProjectilesGoal(this, 70.0F, 2.25F));
      } else {
         this.entityStats.setDoriki((double)10000.0F);
         this.hakiCapability.setBusoshokuHakiExp(100.0F);
         this.hakiCapability.setKenbunshokuHakiExp(100.0F);
         this.m_21051_((Attribute)ModAttributes.GCD.get()).m_22100_((double)20.0F);
         this.m_21051_((Attribute)ModAttributes.PUNCH_DAMAGE.get()).m_22100_((double)3.0F);
         this.m_21051_(Attributes.f_22281_).m_22100_((double)3.0F);
         this.m_21051_((Attribute)ModAttributes.TOUGHNESS.get()).m_22100_((double)5.0F);
         this.m_21051_(Attributes.f_22276_).m_22100_((double)300.0F);
         this.m_21051_(Attributes.f_22284_).m_22100_((double)20.0F);
         this.m_21051_(Attributes.f_22285_).m_22100_((double)4.0F);
         this.m_21051_((Attribute)ForgeMod.SWIM_SPEED.get()).m_22100_((double)5.0F);
         ((MizuTaihoAbility)mizuTaihoWrapper.getAbility()).getComponent((AbilityComponentKey)ModAbilityComponents.COOLDOWN.get()).ifPresent((comp) -> comp.getBonusManager().addBonus(HARD_MIZU_TAIHO_COOLDOWN_UUID, "Hard Mizu Taiho Cooldown Bonus", BonusOperation.ADD, -100.0F));
         ProjectileAbilityWrapperGoal<PackOfSharksAbility> murasameWrapper = new ProjectileAbilityWrapperGoal<PackOfSharksAbility>(this, (AbilityCore)PackOfSharksAbility.INSTANCE.get());
         ((PackOfSharksAbility)murasameWrapper.getAbility()).getComponent((AbilityComponentKey)ModAbilityComponents.COOLDOWN.get()).ifPresent((comp) -> {
            comp.getBonusManager().addBonus(HARD_MURSAMAE_COOLDOWN_UUID, "Hard Murasame Cooldown Bonus", BonusOperation.ADD, -150.0F);
            comp.startCooldown(this, 80.0F);
         });
         this.f_21345_.m_25352_(0, new HakiAbilityWrapperGoal(this, (AbilityCore)BusoshokuHakiInternalDestructionAbility.INSTANCE.get()));
         this.f_21345_.m_25352_(0, new HakiAbilityWrapperGoal(this, (AbilityCore)BusoshokuHakiImbuingAbility.INSTANCE.get()));
         this.f_21345_.m_25352_(0, new RunAwayFromTargetGoal(this, 1.7, 100, 100));
         this.f_21345_.m_25352_(0, new DashDodgeTargetGoal(this, 100.0F, 4.0F));
         this.f_21345_.m_25352_(0, new DashDodgeProjectilesGoal(this, 60.0F, 3.0F));
         this.f_21345_.m_25352_(3, murasameWrapper);
      }

   }

   public static AttributeSupplier.Builder createAttributes() {
      return OPEntity.createAttributes().m_22268_(Attributes.f_22277_, (double)60.0F).m_22268_(Attributes.f_22279_, (double)0.3F).m_22268_(Attributes.f_22281_, (double)3.0F).m_22268_(Attributes.f_22276_, (double)250.0F).m_22268_(Attributes.f_22278_, 0.1);
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

   private boolean isInRangeForAbilities() {
      if (!GoalHelper.hasAliveTarget(this)) {
         return true;
      } else {
         return Math.abs(this.m_20270_(this.m_5448_())) < 30.0F;
      }
   }

   private void startWaterPhaseEvent(ChewEntity entity) {
      this.f_21342_ = this.waterMovementController;
   }

   private void stopWaterPhaseEvent(ChewEntity entity) {
      this.f_21342_ = this.groundMovementController;
   }

   public Set<RevengeMeter> getRevengeMeters() {
      return Sets.newHashSet(new RevengeMeter[]{this.revengeMeter});
   }
}
