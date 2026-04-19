package xyz.pixelatedw.mineminenomi.entities.mobs.pirates.blackcatpirates;

import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;
import xyz.pixelatedw.mineminenomi.abilities.LeapAbility;
import xyz.pixelatedw.mineminenomi.abilities.StealPunchAbility;
import xyz.pixelatedw.mineminenomi.abilities.StealthFootAbility;
import xyz.pixelatedw.mineminenomi.abilities.TakedownKickAbility;
import xyz.pixelatedw.mineminenomi.abilities.blackleg.PartyTableKickCourseAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiHardeningAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiInternalDestructionAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.KamieAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.ShiganAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.SoruAbility;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusOperation;
import xyz.pixelatedw.mineminenomi.api.challenges.InProgressChallenge;
import xyz.pixelatedw.mineminenomi.api.entities.ai.IGoalPartner;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.data.world.FactionsWorldData;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.ClimbOutOfHoleGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.DashDodgeProjectilesGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.DashDodgeTargetGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.FindPartnerGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.ImprovedMeleeAttackGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.SprintTowardsTargetGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.AntiAirAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.CloseMeleeAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.DashAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.HakiAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.JumpAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ReactiveGuardAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPBossEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModFactions;
import xyz.pixelatedw.mineminenomi.init.ModFightingStyles;
import xyz.pixelatedw.mineminenomi.init.ModMobs;
import xyz.pixelatedw.mineminenomi.init.ModNPCGroups;
import xyz.pixelatedw.mineminenomi.init.ModRaces;

public class ShamEntity extends OPBossEntity implements IGoalPartner<BuchiEntity> {
   private static final UUID STANDARD_STEAL_PUNCH_COOLDOWN_BONUS_UUID = UUID.fromString("2d213d2a-2b98-406e-b4dc-984865936edd");
   private static final UUID HARD_TAKEDOWN_KICK_COOLDOWN_BONUS_UUID = UUID.fromString("ebb94c72-15fd-4b10-b5be-ece547a81259");
   private static final UUID HARD_STEALTH_FOOT_COOLDOWN_BONUS_UUID = UUID.fromString("8b63c5b3-6b2f-4ef6-9397-96c3c5b6946d");
   private BuchiEntity partner;

   public ShamEntity(EntityType<ShamEntity> type, Level world) {
      super(type, world);
   }

   public ShamEntity(InProgressChallenge challenge) {
      super((EntityType)ModMobs.SHAM.get(), challenge);
   }

   public void initBoss() {
      this.entityStats.setFaction((Faction)ModFactions.PIRATE.get());
      this.entityStats.setRace((Race)ModRaces.HUMAN.get());
      this.entityStats.setFightingStyle((FightingStyle)ModFightingStyles.BRAWLER.get());
      FactionsWorldData worldData = FactionsWorldData.get();
      worldData.addTemporaryCrewMember(ModNPCGroups.BLACK_CAT_PIRATES, this);
      this.m_21051_((Attribute)ModAttributes.TOUGHNESS.get()).m_22100_((double)1.0F);
      this.m_21051_((Attribute)ForgeMod.SWIM_SPEED.get()).m_22100_((double)3.0F);
      CloseMeleeAbilityWrapperGoal<StealPunchAbility> stealPunchWrapper = new CloseMeleeAbilityWrapperGoal<StealPunchAbility>(this, (AbilityCore)StealPunchAbility.INSTANCE.get());
      AntiAirAbilityWrapperGoal<TakedownKickAbility> takedownKickWrapper = new AntiAirAbilityWrapperGoal<TakedownKickAbility>(this, (AbilityCore)TakedownKickAbility.INSTANCE.get());
      JumpAbilityWrapperGoal<LeapAbility> leapWrapper = new JumpAbilityWrapperGoal<LeapAbility>(this, (AbilityCore)LeapAbility.INSTANCE.get());
      LeapAbility leapAbility = (LeapAbility)leapWrapper.getAbility();
      leapAbility.setLeapHeight((double)0.3F);
      MobsHelper.addBasicNPCGoals(this);
      this.f_21345_.m_25352_(0, new FindPartnerGoal(this, (EntityType)ModMobs.BUCHI.get()));
      this.f_21345_.m_25352_(0, new SprintTowardsTargetGoal(this));
      this.f_21345_.m_25352_(0, new ClimbOutOfHoleGoal(this));
      this.f_21345_.m_25352_(1, new ImprovedMeleeAttackGoal(this, (double)1.0F, true));
      this.f_21345_.m_25352_(2, stealPunchWrapper);
      this.f_21345_.m_25352_(2, leapWrapper);
      this.f_21345_.m_25352_(2, takedownKickWrapper);
      if (this.getChallengeInfo().isDifficultyStandard()) {
         this.entityStats.setDoriki((double)2000.0F);
         this.m_21051_((Attribute)ModAttributes.GCD.get()).m_22100_((double)40.0F);
         ((StealPunchAbility)stealPunchWrapper.getAbility()).getComponent((AbilityComponentKey)ModAbilityComponents.COOLDOWN.get()).ifPresent((comp) -> comp.getBonusManager().addBonus(STANDARD_STEAL_PUNCH_COOLDOWN_BONUS_UUID, "Standard Steal Punch Cooldown Bonus", BonusOperation.ADD, 80.0F));
         this.f_21345_.m_25352_(0, new DashDodgeProjectilesGoal(this, 300.0F, 2.5F));
         this.f_21345_.m_25352_(0, new DashDodgeTargetGoal(this, 350.0F, 3.0F));
      } else {
         this.entityStats.setDoriki((double)10000.0F);
         this.hakiCapability.setBusoshokuHakiExp(100.0F);
         this.hakiCapability.setKenbunshokuHakiExp(100.0F);
         this.m_21051_((Attribute)ModAttributes.GCD.get()).m_22100_((double)20.0F);
         this.m_21051_((Attribute)ModAttributes.PUNCH_DAMAGE.get()).m_22100_((double)3.0F);
         this.m_21051_(Attributes.f_22281_).m_22100_((double)3.0F);
         this.m_21051_((Attribute)ModAttributes.TOUGHNESS.get()).m_22100_((double)5.0F);
         this.m_21051_(Attributes.f_22276_).m_22100_((double)250.0F);
         this.m_21051_(Attributes.f_22284_).m_22100_((double)20.0F);
         this.m_21051_(Attributes.f_22285_).m_22100_((double)4.0F);
         this.m_21051_(Attributes.f_22283_).m_22100_((double)6.0F);
         CloseMeleeAbilityWrapperGoal<StealthFootAbility> stealthFootWrapper = (new CloseMeleeAbilityWrapperGoal<StealthFootAbility>(this, (AbilityCore)StealthFootAbility.INSTANCE.get())).setActivationDistance(10.0F);
         ((StealthFootAbility)stealthFootWrapper.getAbility()).addCanUseCheck((entity, ability) -> (double)this.m_21223_() <= WyHelper.percentage((double)30.0F, (double)this.m_21233_()) ? Result.success() : Result.fail((Component)null));
         ((StealthFootAbility)stealthFootWrapper.getAbility()).getComponent((AbilityComponentKey)ModAbilityComponents.COOLDOWN.get()).ifPresent((comp) -> comp.getBonusManager().addBonus(HARD_STEALTH_FOOT_COOLDOWN_BONUS_UUID, "Hard Stealth Foot Cooldown Bonus", BonusOperation.ADD, 200.0F));
         ((TakedownKickAbility)takedownKickWrapper.getAbility()).getComponent((AbilityComponentKey)ModAbilityComponents.COOLDOWN.get()).ifPresent((comp) -> comp.getBonusManager().addBonus(HARD_TAKEDOWN_KICK_COOLDOWN_BONUS_UUID, "Hard Takedown Kick Cooldown Bonus", BonusOperation.ADD, -40.0F));
         this.f_21345_.m_25352_(0, new HakiAbilityWrapperGoal(this, (AbilityCore)BusoshokuHakiInternalDestructionAbility.INSTANCE.get()));
         this.f_21345_.m_25352_(0, new HakiAbilityWrapperGoal(this, (AbilityCore)BusoshokuHakiHardeningAbility.INSTANCE.get()));
         this.f_21345_.m_25352_(0, new DashDodgeProjectilesGoal(this, 150.0F, 3.0F));
         this.f_21345_.m_25352_(0, new DashDodgeTargetGoal(this, 300.0F, 3.0F));
         this.f_21345_.m_25352_(1, new DashAbilityWrapperGoal(this, (AbilityCore)SoruAbility.INSTANCE.get()));
         this.f_21345_.m_25352_(1, new ReactiveGuardAbilityWrapperGoal(this, (AbilityCore)KamieAbility.INSTANCE.get()));
         this.f_21345_.m_25352_(2, new CloseMeleeAbilityWrapperGoal(this, (AbilityCore)ShiganAbility.INSTANCE.get()));
         this.f_21345_.m_25352_(2, new CloseMeleeAbilityWrapperGoal(this, (AbilityCore)PartyTableKickCourseAbility.INSTANCE.get()));
         this.f_21345_.m_25352_(2, stealthFootWrapper);
      }

   }

   public static AttributeSupplier.Builder createAttributes() {
      return OPEntity.createAttributes().m_22268_(Attributes.f_22277_, (double)60.0F).m_22268_(Attributes.f_22279_, (double)0.31F).m_22268_(Attributes.f_22281_, (double)4.0F).m_22268_(Attributes.f_22276_, (double)150.0F).m_22268_(Attributes.f_22283_, (double)5.0F);
   }

   @Nullable
   public BuchiEntity getPartner() {
      return this.partner;
   }

   public void setPartner(BuchiEntity partner) {
      this.partner = partner;
   }
}
