package xyz.pixelatedw.mineminenomi.entities.mobs.pirates.buggypirates;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeMod;
import xyz.pixelatedw.mineminenomi.abilities.carnivaltricks.IchirinZashiAbility;
import xyz.pixelatedw.mineminenomi.abilities.carnivaltricks.KajiOyajiAbility;
import xyz.pixelatedw.mineminenomi.abilities.carnivaltricks.KamikazeHyakkomaGekijoAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiImbuingAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiInternalDestructionAbility;
import xyz.pixelatedw.mineminenomi.abilities.ittoryu.HiryuKaenAbility;
import xyz.pixelatedw.mineminenomi.abilities.ittoryu.ShiShishiSonsonAbility;
import xyz.pixelatedw.mineminenomi.abilities.ittoryu.YakkodoriAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.GeppoAbility;
import xyz.pixelatedw.mineminenomi.abilities.santoryu.OTatsumakiAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.challenges.InProgressChallenge;
import xyz.pixelatedw.mineminenomi.api.entities.ai.NPCPhase;
import xyz.pixelatedw.mineminenomi.api.entities.ai.SimplePhase;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.entities.revenge.DeadzoneRevengeCheck;
import xyz.pixelatedw.mineminenomi.api.entities.revenge.PhysicalDamageDealtRevengeCheck;
import xyz.pixelatedw.mineminenomi.api.entities.revenge.ProjectileHitRevengeCheck;
import xyz.pixelatedw.mineminenomi.api.entities.revenge.RevengeMeter;
import xyz.pixelatedw.mineminenomi.api.entities.revenge.TargetRunningAwayRevengeCheck;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.data.world.FactionsWorldData;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.DashDodgeProjectilesGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.DashDodgeTargetGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.ImprovedMeleeAttackGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.JumpOutOfHoleGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.JumpOverProjectilesGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.SprintTowardsTargetGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.CloseMeleeAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.DashAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.HakiAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.JumpAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ProjectileAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ReactiveGuardAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.RepeaterAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPBossEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModArmors;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModFactions;
import xyz.pixelatedw.mineminenomi.init.ModFightingStyles;
import xyz.pixelatedw.mineminenomi.init.ModMobs;
import xyz.pixelatedw.mineminenomi.init.ModNPCGroups;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.ModRaces;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;
import xyz.pixelatedw.mineminenomi.items.weapons.ModSwordItem;
import xyz.pixelatedw.mineminenomi.packets.server.SToggleAnimationPacket;

public class CabajiEntity extends OPBossEntity {
   private static final byte NO_UNICYCLE_EVENT = 120;
   private NPCPhase<CabajiEntity> normalPhase = new SimplePhase<CabajiEntity>("Normal Phase", this);
   private NPCPhase<CabajiEntity> noUnicyclePhase = new SimplePhase<CabajiEntity>("No Unicycle Phase", this);
   private RevengeMeter damageTakenMeter = new RevengeMeter(this, 200, 0);
   private boolean hasUnicycle = true;

   public CabajiEntity(EntityType<CabajiEntity> type, Level world) {
      super(type, world);
   }

   public CabajiEntity(InProgressChallenge challenge) {
      super((EntityType)ModMobs.CABAJI.get(), challenge);
   }

   public void initBoss() {
      FactionsWorldData worldData = FactionsWorldData.get();
      this.entityStats.setFaction((Faction)ModFactions.PIRATE.get());
      this.entityStats.setRace((Race)ModRaces.HUMAN.get());
      this.entityStats.setFightingStyle((FightingStyle)ModFightingStyles.SWORDSMAN.get());
      worldData.addTemporaryCrewMember(ModNPCGroups.BUGGY_PIRATES, this);
      this.m_21051_((Attribute)ModAttributes.TOUGHNESS.get()).m_22100_((double)2.0F);
      this.m_21051_((Attribute)ForgeMod.STEP_HEIGHT_ADDITION.get()).m_22100_((double)1.0F);
      this.m_21051_((Attribute)ForgeMod.SWIM_SPEED.get()).m_22100_((double)2.5F);
      this.m_8061_(EquipmentSlot.HEAD, ((Item)ModArmors.CABAJI_SCARF.get()).m_7968_());
      this.m_8061_(EquipmentSlot.MAINHAND, ((ModSwordItem)ModWeapons.CUTLASS.get()).m_7968_());
      ModNetwork.sendToAllTrackingAndSelf(SToggleAnimationPacket.playAnimation(this, ModAnimations.RIDE_UNICYCLE, -1, true), this);
      MobsHelper.addBasicNPCGoals(this);
      this.f_21345_.m_25352_(0, new JumpOutOfHoleGoal(this));
      this.f_21345_.m_25352_(1, new ImprovedMeleeAttackGoal(this, 1.3, true));
      this.f_21345_.m_25352_(1, new SprintTowardsTargetGoal(this));
      this.f_21345_.m_25352_(2, new RepeaterAbilityWrapperGoal(this, (AbilityCore)KajiOyajiAbility.INSTANCE.get()));
      this.f_21345_.m_25352_(2, new RepeaterAbilityWrapperGoal(this, (AbilityCore)KamikazeHyakkomaGekijoAbility.INSTANCE.get()));
      this.normalPhase.addGoal(3, new JumpAbilityWrapperGoal(this, (AbilityCore)IchirinZashiAbility.INSTANCE.get()));
      this.getPhaseManager().setPhase(this.normalPhase);
      if (this.getChallengeInfo().isDifficultyStandard()) {
         this.damageTakenMeter.addCheck(new PhysicalDamageDealtRevengeCheck());
         this.damageTakenMeter.addCheck(new ProjectileHitRevengeCheck(10));
         this.damageTakenMeter.addCheck(new DeadzoneRevengeCheck(10));
         this.damageTakenMeter.addCheck(new TargetRunningAwayRevengeCheck(10, 10.0F));
         this.entityStats.setDoriki((double)1000.0F);
         this.m_21051_((Attribute)ModAttributes.GCD.get()).m_22100_((double)40.0F);
         this.normalPhase.addGoal(0, new JumpOverProjectilesGoal(this, 150.0F, 1.5F));
         this.noUnicyclePhase.addGoal(0, new DashDodgeTargetGoal(this, 250.0F, 1.5F));
         this.noUnicyclePhase.addGoal(2, new DashAbilityWrapperGoal(this, (AbilityCore)ShiShishiSonsonAbility.INSTANCE.get()));
      } else {
         this.damageTakenMeter.addCheck(new PhysicalDamageDealtRevengeCheck(1.25F));
         this.damageTakenMeter.addCheck(new ProjectileHitRevengeCheck(25));
         this.damageTakenMeter.addCheck(new DeadzoneRevengeCheck(25));
         this.damageTakenMeter.addCheck(new TargetRunningAwayRevengeCheck(25, 10.0F));
         this.entityStats.setDoriki((double)10000.0F);
         this.hakiCapability.setBusoshokuHakiExp(100.0F);
         this.hakiCapability.setKenbunshokuHakiExp(100.0F);
         this.m_21051_((Attribute)ModAttributes.GCD.get()).m_22100_((double)20.0F);
         this.m_21051_((Attribute)ModAttributes.PUNCH_DAMAGE.get()).m_22100_((double)6.0F);
         this.m_21051_(Attributes.f_22281_).m_22100_((double)12.0F);
         this.m_21051_((Attribute)ModAttributes.TOUGHNESS.get()).m_22100_((double)4.0F);
         this.m_21051_(Attributes.f_22284_).m_22100_((double)16.0F);
         this.m_21051_(Attributes.f_22285_).m_22100_((double)8.0F);
         this.m_21051_(Attributes.f_22276_).m_22100_((double)250.0F);
         this.m_21051_((Attribute)ForgeMod.SWIM_SPEED.get()).m_22100_((double)3.0F);
         this.m_8061_(EquipmentSlot.OFFHAND, ((ModSwordItem)ModWeapons.CUTLASS.get()).m_7968_());
         this.f_21345_.m_25352_(0, new HakiAbilityWrapperGoal(this, (AbilityCore)BusoshokuHakiInternalDestructionAbility.INSTANCE.get()));
         this.f_21345_.m_25352_(0, new HakiAbilityWrapperGoal(this, (AbilityCore)BusoshokuHakiImbuingAbility.INSTANCE.get()));
         this.f_21345_.m_25352_(2, new ProjectileAbilityWrapperGoal(this, (AbilityCore)YakkodoriAbility.INSTANCE.get()));
         this.f_21345_.m_25352_(2, new JumpAbilityWrapperGoal(this, (AbilityCore)HiryuKaenAbility.INSTANCE.get()));
         this.normalPhase.addGoal(0, new JumpOverProjectilesGoal(this, 70.0F, 1.5F));
         this.noUnicyclePhase.addGoal(0, new DashDodgeTargetGoal(this, 200.0F, 3.0F));
         this.noUnicyclePhase.addGoal(0, new DashDodgeProjectilesGoal(this, 200.0F, 1.25F));
         this.noUnicyclePhase.addGoal(0, new ReactiveGuardAbilityWrapperGoal(this, (AbilityCore)GeppoAbility.INSTANCE.get()));
         this.noUnicyclePhase.addGoal(2, new DashAbilityWrapperGoal(this, (AbilityCore)ShiShishiSonsonAbility.INSTANCE.get()));
         this.noUnicyclePhase.addGoal(2, new CloseMeleeAbilityWrapperGoal(this, (AbilityCore)OTatsumakiAbility.INSTANCE.get()));
      }

   }

   public static AttributeSupplier.Builder createAttributes() {
      return OPEntity.createAttributes().m_22268_(Attributes.f_22277_, (double)60.0F).m_22268_(Attributes.f_22279_, (double)0.31F).m_22268_(Attributes.f_22281_, (double)2.0F).m_22268_(Attributes.f_22276_, (double)150.0F).m_22268_(Attributes.f_22284_, (double)2.0F);
   }

   public void m_8119_() {
      super.m_8119_();
      this.damageTakenMeter.tick();
      if (!this.m_9236_().f_46443_ && this.isDifficultyHardOrAbove() && this.normalPhase.isActive(this) && this.damageTakenMeter.isRevengeMaxed()) {
         this.getPhaseManager().setPhase(this.noUnicyclePhase);
         this.m_9236_().m_7605_(this, (byte)120);
         ModNetwork.sendToAllTrackingAndSelf(SToggleAnimationPacket.stopAnimation(this, ModAnimations.RIDE_UNICYCLE), this);
         this.hasUnicycle = false;
      }

   }

   @OnlyIn(Dist.CLIENT)
   public void m_7822_(byte id) {
      switch (id) {
         case 120:
            this.hasUnicycle = false;
         default:
            super.m_7822_(id);
      }
   }

   public boolean hasUnicycle() {
      return this.hasUnicycle;
   }
}
