package xyz.pixelatedw.mineminenomi.entities.mobs.pirates.buggypirates;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;
import xyz.pixelatedw.mineminenomi.abilities.BattoStrikeAbility;
import xyz.pixelatedw.mineminenomi.abilities.BellyFlopAbility;
import xyz.pixelatedw.mineminenomi.abilities.SlamAbility;
import xyz.pixelatedw.mineminenomi.abilities.WeaponSpinAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiHardeningAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiInternalDestructionAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.GeppoAbility;
import xyz.pixelatedw.mineminenomi.abilities.sube.SubeSubeDeflectAbility;
import xyz.pixelatedw.mineminenomi.abilities.sube.SubeSubeSpurAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityPool;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.PoolComponent;
import xyz.pixelatedw.mineminenomi.api.challenges.InProgressChallenge;
import xyz.pixelatedw.mineminenomi.api.entities.ai.NPCPhase;
import xyz.pixelatedw.mineminenomi.api.entities.ai.SimplePhase;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.data.world.FactionsWorldData;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.ClimbOutOfHoleGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.DashDodgeProjectilesGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.ImprovedMeleeAttackGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.CloseMeleeAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.DashAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.HakiAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.JumpAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ReactiveGuardAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ReactiveRetreatAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPBossEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModArmors;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModFactions;
import xyz.pixelatedw.mineminenomi.init.ModFruits;
import xyz.pixelatedw.mineminenomi.init.ModNPCGroups;
import xyz.pixelatedw.mineminenomi.init.ModRaces;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;
import xyz.pixelatedw.mineminenomi.items.AkumaNoMiItem;
import xyz.pixelatedw.mineminenomi.items.weapons.ModSwordItem;

public class AlvidaEntity extends OPBossEntity {
   private static final AbilityPool ALVIDA = (new AbilityPool()).addFlag("ignoreCooldown", true);
   private NPCPhase<AlvidaEntity> standardPhase = new SimplePhase<AlvidaEntity>("Standard Phase", this);
   private NPCPhase<AlvidaEntity> slidePhase = new SimplePhase<AlvidaEntity>("Slide Phase", this);

   public AlvidaEntity(EntityType<AlvidaEntity> type, Level world) {
      super(type, world);
   }

   public AlvidaEntity(EntityType<AlvidaEntity> type, InProgressChallenge challenge) {
      super(type, challenge);
   }

   public void initBoss() {
      ItemStack plumeHeadStack = ((Item)ModArmors.PLUME_HAT.get()).m_7968_();
      plumeHeadStack.m_41698_("display").m_128405_("color", 12788538);
      this.m_8061_(EquipmentSlot.HEAD, plumeHeadStack);
      this.m_8061_(EquipmentSlot.MAINHAND, ((ModSwordItem)ModWeapons.MACE.get()).m_7968_());
      FactionsWorldData worldData = FactionsWorldData.get();
      this.entityStats.setFaction((Faction)ModFactions.PIRATE.get());
      this.entityStats.setRace((Race)ModRaces.HUMAN.get());
      worldData.addTemporaryCrewMember(ModNPCGroups.BUGGY_PIRATES, this);
      this.m_21051_((Attribute)ModAttributes.TOUGHNESS.get()).m_22100_((double)3.0F);
      this.m_21051_((Attribute)ForgeMod.STEP_HEIGHT_ADDITION.get()).m_22100_((double)1.0F);
      this.f_21345_.m_25352_(0, new ClimbOutOfHoleGoal(this));
      if (this.getChallengeInfo().isDifficultyStandard()) {
         this.entityStats.setDoriki((double)1000.0F);
         this.m_21051_((Attribute)ModAttributes.GCD.get()).m_22100_((double)40.0F);
         this.m_21051_((Attribute)ModAttributes.PUNCH_DAMAGE.get()).m_22100_((double)4.0F);
      } else {
         this.devilFruitData.setDevilFruit((AkumaNoMiItem)ModFruits.SUBE_SUBE_NO_MI.get());
         this.entityStats.setDoriki((double)10000.0F);
         this.hakiCapability.setBusoshokuHakiExp(100.0F);
         this.hakiCapability.setKenbunshokuHakiExp(100.0F);
         this.m_21051_((Attribute)ModAttributes.GCD.get()).m_22100_((double)20.0F);
         this.m_21051_((Attribute)ModAttributes.TOUGHNESS.get()).m_22100_((double)3.0F);
         this.m_21051_((Attribute)ModAttributes.PUNCH_DAMAGE.get()).m_22100_((double)4.0F);
         this.m_21051_(Attributes.f_22276_).m_22100_((double)300.0F);
         this.m_21051_(Attributes.f_22284_).m_22100_((double)10.0F);
         this.m_21051_(Attributes.f_22285_).m_22100_((double)8.0F);
      }

      MobsHelper.addBasicNPCGoals(this);
      JumpAbilityWrapperGoal<SlamAbility> slamWrapper = new JumpAbilityWrapperGoal<SlamAbility>(this, (AbilityCore)SlamAbility.INSTANCE.get());
      ((SlamAbility)slamWrapper.getAbility()).addComponents(new AbilityComponent[]{new PoolComponent(slamWrapper.getAbility(), ALVIDA, new AbilityPool[0])});
      CloseMeleeAbilityWrapperGoal<WeaponSpinAbility> weaponSpinWrapper = new CloseMeleeAbilityWrapperGoal<WeaponSpinAbility>(this, (AbilityCore)WeaponSpinAbility.INSTANCE.get());
      ((WeaponSpinAbility)weaponSpinWrapper.getAbility()).addComponents(new AbilityComponent[]{new PoolComponent(weaponSpinWrapper.getAbility(), ALVIDA, new AbilityPool[0])});
      this.standardPhase.addGoal(1, new ImprovedMeleeAttackGoal(this, (double)1.0F, true));
      this.standardPhase.addGoal(2, slamWrapper);
      this.standardPhase.addGoal(2, weaponSpinWrapper);
      this.standardPhase.addGoal(2, new CloseMeleeAbilityWrapperGoal(this, (AbilityCore)BattoStrikeAbility.INSTANCE.get()));
      this.getPhaseManager().setPhase(this.standardPhase);
      if (this.getChallengeInfo().isDifficultyStandard()) {
         JumpAbilityWrapperGoal<BellyFlopAbility> bellyFlopWrapper = new JumpAbilityWrapperGoal<BellyFlopAbility>(this, (AbilityCore)BellyFlopAbility.INSTANCE.get());
         ((BellyFlopAbility)bellyFlopWrapper.getAbility()).addComponents(new AbilityComponent[]{new PoolComponent(bellyFlopWrapper.getAbility(), ALVIDA, new AbilityPool[0])});
         ((BellyFlopAbility)bellyFlopWrapper.getAbility()).getComponent((AbilityComponentKey)ModAbilityComponents.COOLDOWN.get()).ifPresent((comp) -> comp.startCooldown(this, 100.0F));
         this.standardPhase.addGoal(2, bellyFlopWrapper);
      } else {
         this.f_21345_.m_25352_(0, new HakiAbilityWrapperGoal(this, (AbilityCore)BusoshokuHakiInternalDestructionAbility.INSTANCE.get()));
         this.f_21345_.m_25352_(0, new HakiAbilityWrapperGoal(this, (AbilityCore)BusoshokuHakiHardeningAbility.INSTANCE.get()));
         this.standardPhase.addGoal(0, new DashDodgeProjectilesGoal(this, 250.0F, 1.25F));
         this.standardPhase.addGoal(0, new ReactiveRetreatAbilityWrapperGoal(this, (AbilityCore)GeppoAbility.INSTANCE.get()));
         this.standardPhase.addGoal(2, new DashAbilityWrapperGoal(this, (AbilityCore)SubeSubeSpurAbility.INSTANCE.get()));
         this.standardPhase.addGoal(2, new ReactiveGuardAbilityWrapperGoal(this, (AbilityCore)SubeSubeDeflectAbility.INSTANCE.get()));
      }

   }

   public static AttributeSupplier.Builder createAttributes() {
      return OPEntity.createAttributes().m_22268_(Attributes.f_22277_, (double)60.0F).m_22268_(Attributes.f_22279_, (double)0.29F).m_22268_(Attributes.f_22281_, (double)2.0F).m_22268_(Attributes.f_22276_, (double)200.0F).m_22268_(Attributes.f_22284_, (double)4.0F);
   }
}
