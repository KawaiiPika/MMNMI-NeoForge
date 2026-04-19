package xyz.pixelatedw.mineminenomi.entities.mobs.pirates.kriegpirates;

import com.google.common.collect.Sets;
import java.util.Set;
import java.util.UUID;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;
import xyz.pixelatedw.mineminenomi.abilities.TakedownKickAbility;
import xyz.pixelatedw.mineminenomi.abilities.blackleg.AntiMannerKickCourseAbility;
import xyz.pixelatedw.mineminenomi.abilities.blackleg.ConcasseAbility;
import xyz.pixelatedw.mineminenomi.abilities.blackleg.PartyTableKickCourseAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.HakaiHoAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.JishinHoAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiFullBodyHardeningAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiImbuingAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiInternalDestructionAbility;
import xyz.pixelatedw.mineminenomi.abilities.mandemontactics.DemonicDanceAbility;
import xyz.pixelatedw.mineminenomi.abilities.mandemontactics.DemonicDashAbility;
import xyz.pixelatedw.mineminenomi.abilities.mandemontactics.DemonicSmashAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.KamieAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.RankyakuAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.ShiganAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.SoruAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
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
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.data.world.FactionsWorldData;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.DashDodgeProjectilesGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.DashDodgeTargetGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.ImprovedMeleeAttackGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.JumpOutOfHoleGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.SprintTowardsTargetGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.AntiAirAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.CloseMeleeAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.DashAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.HakiAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.JumpAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ProjectileAbilityWrapperGoal;
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
import xyz.pixelatedw.mineminenomi.init.ModWeapons;

public class GinEntity extends OPBossEntity {
   private static final UUID HARD_DEMON_DANCE_CHARGE_BONUS_UUID = UUID.fromString("8bde2608-50af-4a26-89a1-28e27ccdb933");
   private static final UUID HARD_DEMON_DASH_CHARGE_BONUS_UUID = UUID.fromString("f62f6b91-677f-4612-8464-f840fd7752a4");
   private static final UUID HARD_CONCASSE_COOLDOWN_BONUS_UUID = UUID.fromString("b88e2563-a90e-4976-b83e-1094b411b0cd");
   private static final UUID HARD_TAKEDOWN_KICK_COOLDOWN_BONUS_UUID = UUID.fromString("6f76d9e5-94ad-48bd-ab28-aae1860ea8f2");
   private RevengeMeter revengeMeter;
   private NPCPhase<GinEntity> normalPhase;
   private NPCPhase<GinEntity> emptyHandedPhase;

   public GinEntity(EntityType<GinEntity> type, Level world) {
      super(type, world);
   }

   public GinEntity(InProgressChallenge challenge) {
      super((EntityType)ModMobs.GIN.get(), challenge);
   }

   public void initBoss() {
      this.revengeMeter = new RevengeMeter(this, 100, 1);
      this.normalPhase = new SimplePhase<GinEntity>("Normal Phase", this);
      this.emptyHandedPhase = new SimplePhase<GinEntity>("Empty Handed Phase", this, this::startEmptyHandedPhaseEvent);
      this.entityStats.setFaction((Faction)ModFactions.PIRATE.get());
      this.entityStats.setRace((Race)ModRaces.HUMAN.get());
      this.entityStats.setFightingStyle((FightingStyle)ModFightingStyles.BRAWLER.get());
      FactionsWorldData worldData = FactionsWorldData.get();
      worldData.addTemporaryCrewMember(ModNPCGroups.KRIEG_PIRATES, this);
      this.m_8061_(EquipmentSlot.MAINHAND, new ItemStack((ItemLike)ModWeapons.TONFA.get()));
      this.m_8061_(EquipmentSlot.OFFHAND, new ItemStack((ItemLike)ModWeapons.TONFA.get()));
      this.m_21051_((Attribute)ForgeMod.ENTITY_REACH.get()).m_22100_(1.2);
      this.m_21051_((Attribute)ModAttributes.TOUGHNESS.get()).m_22100_((double)2.0F);
      this.m_21051_((Attribute)ForgeMod.SWIM_SPEED.get()).m_22100_((double)3.0F);
      this.revengeMeter.addCheck(new PhysicalHitRevengeCheck(2));
      this.revengeMeter.addCheck(new DeadzoneRevengeCheck(10));
      JumpAbilityWrapperGoal<DemonicDanceAbility> demonicDanceWrapper = new JumpAbilityWrapperGoal<DemonicDanceAbility>(this, (AbilityCore)DemonicDanceAbility.INSTANCE.get());
      DashAbilityWrapperGoal<DemonicDashAbility> demonicDashWrapper = new DashAbilityWrapperGoal<DemonicDashAbility>(this, (AbilityCore)DemonicDashAbility.INSTANCE.get());
      ((DemonicDashAbility)demonicDashWrapper.getAbility()).addCanUseCheck((entity, ability) -> !((DemonicDanceAbility)demonicDanceWrapper.getAbility()).isCharging() && !((DemonicDanceAbility)demonicDanceWrapper.getAbility()).isContinuous() ? Result.success() : Result.fail((Component)null));
      CloseMeleeAbilityWrapperGoal<DemonicSmashAbility> demonicSmashWrapper = new CloseMeleeAbilityWrapperGoal<DemonicSmashAbility>(this, (AbilityCore)DemonicSmashAbility.INSTANCE.get());
      ((DemonicSmashAbility)demonicSmashWrapper.getAbility()).addCanUseCheck((entity, ability) -> !((DemonicDanceAbility)demonicDanceWrapper.getAbility()).isCharging() && !((DemonicDanceAbility)demonicDanceWrapper.getAbility()).isContinuous() ? Result.success() : Result.fail((Component)null));
      ImprovedMeleeAttackGoal meleeAttackGoal = new ImprovedMeleeAttackGoal(this, (double)1.0F, true);
      meleeAttackGoal.setEarlyStop(() -> ((DemonicDanceAbility)demonicDanceWrapper.getAbility()).isCharging());
      AntiAirAbilityWrapperGoal<TakedownKickAbility> takedownKickWrapper = new AntiAirAbilityWrapperGoal<TakedownKickAbility>(this, (AbilityCore)TakedownKickAbility.INSTANCE.get());
      MobsHelper.addBasicNPCGoals(this);
      this.f_21345_.m_25352_(0, new JumpOutOfHoleGoal(this));
      this.f_21345_.m_25352_(0, new DashDodgeProjectilesGoal(this, 300.0F, 2.5F));
      this.f_21345_.m_25352_(0, new DashDodgeTargetGoal(this, 350.0F, 3.0F));
      this.f_21345_.m_25352_(0, new SprintTowardsTargetGoal(this));
      this.f_21345_.m_25352_(1, meleeAttackGoal);
      this.f_21345_.m_25352_(2, takedownKickWrapper);
      this.normalPhase.addGoal(2, demonicDanceWrapper);
      this.normalPhase.addGoal(2, demonicDashWrapper);
      this.normalPhase.addGoal(2, demonicSmashWrapper);
      this.getPhaseManager().setPhase(this.normalPhase);
      if (this.getChallengeInfo().isDifficultyStandard()) {
         this.entityStats.setDoriki((double)2000.0F);
         this.m_21051_((Attribute)ModAttributes.GCD.get()).m_22100_((double)40.0F);
      } else {
         this.entityStats.setDoriki((double)10000.0F);
         this.hakiCapability.setBusoshokuHakiExp(100.0F);
         this.hakiCapability.setKenbunshokuHakiExp(100.0F);
         this.m_21051_((Attribute)ModAttributes.GCD.get()).m_22100_((double)20.0F);
         this.m_21051_((Attribute)ModAttributes.PUNCH_DAMAGE.get()).m_22100_((double)3.0F);
         this.m_21051_(Attributes.f_22281_).m_22100_((double)3.0F);
         this.m_21051_((Attribute)ModAttributes.TOUGHNESS.get()).m_22100_((double)6.0F);
         this.m_21051_(Attributes.f_22276_).m_22100_((double)300.0F);
         this.m_21051_(Attributes.f_22284_).m_22100_((double)20.0F);
         this.m_21051_(Attributes.f_22285_).m_22100_((double)8.0F);
         this.m_21051_((Attribute)ForgeMod.SWIM_SPEED.get()).m_22100_((double)3.5F);
         ((DemonicDanceAbility)demonicDanceWrapper.getAbility()).getComponent((AbilityComponentKey)ModAbilityComponents.CHARGE.get()).ifPresent((comp) -> comp.getMaxChargeBonusManager().addBonus(HARD_DEMON_DANCE_CHARGE_BONUS_UUID, "Hard Demon Dance Charge Bonus", BonusOperation.MUL, 0.5F));
         ((DemonicDashAbility)demonicDashWrapper.getAbility()).getComponent((AbilityComponentKey)ModAbilityComponents.CHARGE.get()).ifPresent((comp) -> comp.getMaxChargeBonusManager().addBonus(HARD_DEMON_DASH_CHARGE_BONUS_UUID, "Hard Demon Dash Charge Bonus", BonusOperation.MUL, 0.0F));
         JumpAbilityWrapperGoal<ConcasseAbility> concasseWrapper = new JumpAbilityWrapperGoal<ConcasseAbility>(this, (AbilityCore)ConcasseAbility.INSTANCE.get());
         ((ConcasseAbility)concasseWrapper.getAbility()).getComponent((AbilityComponentKey)ModAbilityComponents.COOLDOWN.get()).ifPresent((comp) -> comp.getBonusManager().addBonus(HARD_CONCASSE_COOLDOWN_BONUS_UUID, "Hard Concasse Cooldown Bonus", BonusOperation.ADD, 100.0F));
         ((TakedownKickAbility)takedownKickWrapper.getAbility()).getComponent((AbilityComponentKey)ModAbilityComponents.COOLDOWN.get()).ifPresent((comp) -> comp.getBonusManager().addBonus(HARD_TAKEDOWN_KICK_COOLDOWN_BONUS_UUID, "Hard Takedown Kick Cooldown Bonus", BonusOperation.ADD, -40.0F));
         this.f_21345_.m_25352_(0, new HakiAbilityWrapperGoal(this, (AbilityCore)BusoshokuHakiInternalDestructionAbility.INSTANCE.get()));
         this.f_21345_.m_25352_(0, new HakiAbilityWrapperGoal(this, (AbilityCore)BusoshokuHakiImbuingAbility.INSTANCE.get()));
         this.emptyHandedPhase.addGoal(0, new HakiAbilityWrapperGoal(this, (AbilityCore)BusoshokuHakiFullBodyHardeningAbility.INSTANCE.get()));
         this.emptyHandedPhase.addGoal(2, new CloseMeleeAbilityWrapperGoal(this, (AbilityCore)HakaiHoAbility.INSTANCE.get()));
         this.emptyHandedPhase.addGoal(2, new CloseMeleeAbilityWrapperGoal(this, (AbilityCore)JishinHoAbility.INSTANCE.get()));
         this.emptyHandedPhase.addGoal(2, new CloseMeleeAbilityWrapperGoal(this, (AbilityCore)ShiganAbility.INSTANCE.get()));
         this.emptyHandedPhase.addGoal(2, new CloseMeleeAbilityWrapperGoal(this, (AbilityCore)AntiMannerKickCourseAbility.INSTANCE.get()));
         this.f_21345_.m_25352_(2, new DashAbilityWrapperGoal(this, (AbilityCore)SoruAbility.INSTANCE.get()));
         this.f_21345_.m_25352_(2, new CloseMeleeAbilityWrapperGoal(this, (AbilityCore)PartyTableKickCourseAbility.INSTANCE.get()));
         this.f_21345_.m_25352_(3, new ProjectileAbilityWrapperGoal(this, (AbilityCore)RankyakuAbility.INSTANCE.get()));
         this.f_21345_.m_25352_(3, new ReactiveGuardAbilityWrapperGoal(this, (AbilityCore)KamieAbility.INSTANCE.get()));
         this.f_21345_.m_25352_(3, concasseWrapper);
      }

   }

   public static AttributeSupplier.Builder createAttributes() {
      return OPEntity.createAttributes().m_22268_(Attributes.f_22277_, (double)60.0F).m_22268_(Attributes.f_22279_, (double)0.31F).m_22268_(Attributes.f_22281_, (double)4.0F).m_22268_(Attributes.f_22276_, (double)200.0F).m_22268_(Attributes.f_22284_, (double)8.0F).m_22268_(Attributes.f_22278_, 0.1).m_22268_(Attributes.f_22282_, (double)1.0F).m_22268_(Attributes.f_22283_, (double)5.0F);
   }

   public boolean m_7327_(Entity target) {
      if (target instanceof LivingEntity livingTarget) {
         boolean hasTonfaInMain = !this.m_21205_().m_41619_() && this.m_21205_().m_41720_() == ModWeapons.TONFA.get();
         boolean hasTonfaInOff = !this.m_21206_().m_41619_() && this.m_21206_().m_41720_() == ModWeapons.TONFA.get();
         if (hasTonfaInMain || hasTonfaInOff) {
            ItemsHelper.stopShieldAndStartCooldown(livingTarget, 100);
            return super.m_7327_(target);
         }
      }

      return super.m_7327_(target);
   }

   private void startEmptyHandedPhaseEvent(GinEntity entity) {
      this.m_21051_((Attribute)ModAttributes.PUNCH_DAMAGE.get()).m_22100_((double)4.0F);
      this.m_21051_(Attributes.f_22281_).m_22100_((double)4.0F);
   }

   public void m_8119_() {
      super.m_8119_();
      if (!this.m_9236_().f_46443_ && this.m_6084_() && !this.emptyHandedPhase.isActive(this) && this.m_21205_().m_41619_()) {
         this.getPhaseManager().setPhase(this.emptyHandedPhase);
      }

   }

   public Set<RevengeMeter> getRevengeMeters() {
      return Sets.newHashSet(new RevengeMeter[]{this.revengeMeter});
   }
}
