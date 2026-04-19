package xyz.pixelatedw.mineminenomi.entities.mobs.pirates.baroqueworks;

import java.util.UUID;
import net.minecraft.core.BlockPos;
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
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiFullBodyHardeningAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiInternalDestructionAbility;
import xyz.pixelatedw.mineminenomi.abilities.kilo.KiloPress10000Ability;
import xyz.pixelatedw.mineminenomi.abilities.kilo.KiloPress1Ability;
import xyz.pixelatedw.mineminenomi.abilities.kilo.KiloPunch5000Ability;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.KamieAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.RankyakuAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.ShiganAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.SoruAbility;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusOperation;
import xyz.pixelatedw.mineminenomi.api.challenges.InProgressChallenge;
import xyz.pixelatedw.mineminenomi.api.entities.ai.IGoalPartner;
import xyz.pixelatedw.mineminenomi.api.entities.ai.NPCPhase;
import xyz.pixelatedw.mineminenomi.api.entities.ai.SimplePhase;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.data.world.FactionsWorldData;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.DashDodgeProjectilesGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.DashDodgeTargetGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.FindPartnerGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.ImprovedMeleeAttackGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.JumpOutOfHoleGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.RunAwayFromTargetGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ActiveBuffAbilityWrapperGoal;
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
import xyz.pixelatedw.mineminenomi.init.ModFruits;
import xyz.pixelatedw.mineminenomi.init.ModMobs;
import xyz.pixelatedw.mineminenomi.init.ModNPCGroups;
import xyz.pixelatedw.mineminenomi.init.ModRaces;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;
import xyz.pixelatedw.mineminenomi.items.AkumaNoMiItem;

public class MissValentineEntity extends OPBossEntity implements IGoalPartner<Mr5Entity> {
   private static final UUID STANDARD_KILO_PRESS_DAMAGE_BONUS = UUID.fromString("6ee8ad2c-3e36-4a3a-a194-169387e32339");
   private static final int DROP_CHECK_BOUND = 4;
   private Mr5Entity partner;
   private ActiveBuffAbilityWrapperGoal<KiloPress10000Ability> kiloPress10k;
   private JumpAbilityWrapperGoal<KiloPress1Ability> kiloPress1;
   private NPCPhase<MissValentineEntity> helperPhase;
   private NPCPhase<MissValentineEntity> soloPhase;

   public MissValentineEntity(EntityType<MissValentineEntity> type, Level world) {
      super(type, world);
   }

   public MissValentineEntity(InProgressChallenge challenge) {
      super((EntityType)ModMobs.MISS_VALENTINE.get(), challenge);
   }

   public void initBoss() {
      this.helperPhase = new SimplePhase<MissValentineEntity>("Helper Phase", this);
      this.soloPhase = new SimplePhase<MissValentineEntity>("Solo Phase", this);
      FactionsWorldData worldData = FactionsWorldData.get();
      this.entityStats.setFaction((Faction)ModFactions.PIRATE.get());
      this.entityStats.setRace((Race)ModRaces.HUMAN.get());
      this.devilFruitData.setDevilFruit((AkumaNoMiItem)ModFruits.KILO_KILO_NO_MI.get());
      worldData.addTemporaryCrewMember(ModNPCGroups.BAROQUE_WORKS, this);
      this.m_21051_((Attribute)ForgeMod.STEP_HEIGHT_ADDITION.get()).m_22100_((double)1.0F);
      this.m_8061_(EquipmentSlot.MAINHAND, new ItemStack((ItemLike)ModWeapons.UMBRELLA.get()));
      this.kiloPress10k = new ActiveBuffAbilityWrapperGoal<KiloPress10000Ability>(this, (AbilityCore)KiloPress10000Ability.INSTANCE.get());
      ((KiloPress10000Ability)this.kiloPress10k.getAbility()).addCanUseCheck(100, (user, abl) -> {
         if (this.m_20096_()) {
            return Result.fail((Component)null);
         } else if (!((KiloPress1Ability)this.kiloPress1.getAbility()).isContinuous()) {
            return Result.fail((Component)null);
         } else {
            LivingEntity target = this.m_5448_();
            boolean isXInBound = target.m_20185_() - (double)4.0F <= this.m_20185_() && this.m_20185_() <= target.m_20185_() + (double)4.0F;
            boolean isZInBound = target.m_20189_() - (double)4.0F <= this.m_20189_() && this.m_20189_() <= target.m_20189_() + (double)4.0F;
            return isXInBound && isZInBound ? Result.success() : Result.fail((Component)null);
         }
      });
      this.kiloPress1 = new JumpAbilityWrapperGoal<KiloPress1Ability>(this, (AbilityCore)KiloPress1Ability.INSTANCE.get());
      ((KiloPress1Ability)this.kiloPress1.getAbility()).addUseEvent(90, (user, abl) -> this.m_20334_((double)0.0F, (double)2.0F, (double)0.0F));
      MobsHelper.addBasicNPCGoals(this);
      this.f_21345_.m_25352_(0, new JumpOutOfHoleGoal(this));
      this.f_21345_.m_25352_(3, new CloseMeleeAbilityWrapperGoal(this, (AbilityCore)KiloPunch5000Ability.INSTANCE.get()));
      this.f_21345_.m_25352_(4, this.kiloPress1);
      this.f_21345_.m_25352_(4, this.kiloPress10k);
      this.helperPhase.addGoal(0, new FindPartnerGoal(this, (EntityType)ModMobs.MR5.get()));
      this.helperPhase.addGoal(1, (new ImprovedMeleeAttackGoal(this, (double)0.85F, true)).setEarlyStop(this::isInRangeForAbilities));
      this.soloPhase.addGoal(1, new ImprovedMeleeAttackGoal(this, (double)1.1F, true));
      this.getPhaseManager().setPhase(this.helperPhase);
      if (this.getChallengeInfo().isDifficultyStandard()) {
         this.entityStats.setDoriki((double)2000.0F);
         this.m_21051_((Attribute)ModAttributes.GCD.get()).m_22100_((double)40.0F);
         ((KiloPress10000Ability)this.kiloPress10k.getAbility()).getComponent((AbilityComponentKey)ModAbilityComponents.DAMAGE.get()).ifPresent((comp) -> comp.getBonusManager().addBonus(STANDARD_KILO_PRESS_DAMAGE_BONUS, "Standard Kilo Press Damage Bonus", BonusOperation.MUL, 0.5F));
         this.helperPhase.addGoal(0, new DashDodgeTargetGoal(this, 200.0F, 4.0F));
         this.helperPhase.addGoal(0, new DashDodgeProjectilesGoal(this, 100.0F, 3.0F));
         this.helperPhase.addGoal(1, new RunAwayFromTargetGoal(this, (double)1.5F, 100, 200));
         this.soloPhase.addGoal(0, new DashDodgeTargetGoal(this, 250.0F, 3.0F));
         this.soloPhase.addGoal(0, new DashDodgeProjectilesGoal(this, 150.0F, 2.5F));
      } else {
         this.entityStats.setDoriki((double)10000.0F);
         this.hakiCapability.setBusoshokuHakiExp(100.0F);
         this.hakiCapability.setKenbunshokuHakiExp(100.0F);
         this.m_21051_((Attribute)ModAttributes.GCD.get()).m_22100_((double)20.0F);
         this.m_21051_((Attribute)ModAttributes.PUNCH_DAMAGE.get()).m_22100_((double)3.0F);
         this.m_21051_(Attributes.f_22281_).m_22100_((double)3.0F);
         this.m_21051_((Attribute)ModAttributes.TOUGHNESS.get()).m_22100_((double)4.0F);
         this.m_21051_(Attributes.f_22276_).m_22100_((double)350.0F);
         this.m_21051_(Attributes.f_22284_).m_22100_((double)20.0F);
         this.m_21051_(Attributes.f_22285_).m_22100_((double)8.0F);
         this.f_21345_.m_25352_(0, new HakiAbilityWrapperGoal(this, (AbilityCore)BusoshokuHakiInternalDestructionAbility.INSTANCE.get()));
         this.f_21345_.m_25352_(0, new HakiAbilityWrapperGoal(this, (AbilityCore)BusoshokuHakiFullBodyHardeningAbility.INSTANCE.get()));
         this.f_21345_.m_25352_(3, new CloseMeleeAbilityWrapperGoal(this, (AbilityCore)ShiganAbility.INSTANCE.get()));
         this.f_21345_.m_25352_(3, new ProjectileAbilityWrapperGoal(this, (AbilityCore)RankyakuAbility.INSTANCE.get()));
         this.f_21345_.m_25352_(3, new DashAbilityWrapperGoal(this, (AbilityCore)SoruAbility.INSTANCE.get()));
         this.helperPhase.addGoal(0, new DashDodgeTargetGoal(this, 100.0F, 4.0F));
         this.helperPhase.addGoal(0, new DashDodgeProjectilesGoal(this, 60.0F, 3.0F));
         this.helperPhase.addGoal(1, new RunAwayFromTargetGoal(this, 1.7, 100, 100));
         this.soloPhase.addGoal(0, new DashDodgeTargetGoal(this, 200.0F, 4.0F));
         this.soloPhase.addGoal(0, new DashDodgeProjectilesGoal(this, 150.0F, 3.0F));
         this.soloPhase.addGoal(3, new ReactiveGuardAbilityWrapperGoal(this, (AbilityCore)KamieAbility.INSTANCE.get()));
      }

   }

   public static AttributeSupplier.Builder createAttributes() {
      return OPEntity.createAttributes().m_22268_(Attributes.f_22277_, (double)60.0F).m_22268_(Attributes.f_22279_, (double)0.3F).m_22268_(Attributes.f_22281_, (double)2.0F).m_22268_(Attributes.f_22276_, (double)80.0F).m_22268_(Attributes.f_22284_, (double)0.0F);
   }

   public void m_8119_() {
      super.m_8119_();
      if (!this.m_9236_().f_46443_ && this.m_5448_() != null) {
         if (this.f_19797_ > 100 && this.helperPhase.isActive(this) && this.partner != null && !this.partner.m_6084_()) {
            this.getPhaseManager().setPhase(this.soloPhase);
         }

         boolean canSee = this.m_142582_(this.m_5448_());
         double yDiff = Math.abs(this.m_20186_() - this.m_5448_().m_20186_());
         if (yDiff > (double)10.0F && !canSee) {
            ((KiloPress1Ability)this.kiloPress1.getAbility()).getComponent((AbilityComponentKey)ModAbilityComponents.COOLDOWN.get()).ifPresent((comp) -> comp.stopCooldown(this));
            ((KiloPress10000Ability)this.kiloPress10k.getAbility()).getComponent((AbilityComponentKey)ModAbilityComponents.COOLDOWN.get()).ifPresent((comp) -> comp.stopCooldown(this));
            BlockPos tpPos = WyHelper.findValidGroundLocation((Entity)this, (BlockPos)this.m_5448_().m_20183_(), 10, 10);
            this.m_20324_((double)tpPos.m_123341_(), (double)(tpPos.m_123342_() + 35), (double)tpPos.m_123343_());
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

   public Mr5Entity getPartner() {
      return this.partner;
   }

   public void setPartner(Mr5Entity partner) {
      this.partner = partner;
   }
}
