package xyz.pixelatedw.mineminenomi.entities.mobs.pirates.kriegpirates;

import com.google.common.collect.Iterables;
import java.util.UUID;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;
import xyz.pixelatedw.mineminenomi.abilities.BakudanAbility;
import xyz.pixelatedw.mineminenomi.abilities.ChargedCleaveAbility;
import xyz.pixelatedw.mineminenomi.abilities.GunArrayAbility;
import xyz.pixelatedw.mineminenomi.abilities.MH5Ability;
import xyz.pixelatedw.mineminenomi.abilities.SlamAbility;
import xyz.pixelatedw.mineminenomi.abilities.WootzNetLauncherAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.ChargedPunchAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.HakaiHoAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.JishinHoAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiFullBodyHardeningAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiHardeningAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiImbuingAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiInternalDestructionAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.KamieAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusOperation;
import xyz.pixelatedw.mineminenomi.api.challenges.InProgressChallenge;
import xyz.pixelatedw.mineminenomi.api.entities.ai.NPCPhase;
import xyz.pixelatedw.mineminenomi.api.entities.ai.SimplePhase;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.data.world.FactionsWorldData;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.DashDodgeProjectilesGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.DashDodgeTargetGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.ImprovedMeleeAttackGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.SprintTowardsTargetGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ActiveBuffAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ActiveGuardAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.CloseMeleeAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.HakiAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.JumpAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ReactiveGuardAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.donkrieg.DonKriegPhaseSwitcherGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.donkrieg.MH5PhaseGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPBossEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModArmors;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModFactions;
import xyz.pixelatedw.mineminenomi.init.ModFightingStyles;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.ModMobs;
import xyz.pixelatedw.mineminenomi.init.ModNPCGroups;
import xyz.pixelatedw.mineminenomi.init.ModRaces;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;

public class DonKriegEntity extends OPBossEntity {
   private static final UUID STANDARD_BAKUDAN_COOLDOWN_BONUS_UUID = UUID.fromString("5eeb8a76-d0d9-4fdd-816d-7a8ff4e903ad");
   private static final UUID STANDARD_KAEN_HOSHA_COOLDOWN_BONUS_UUID = UUID.fromString("6445513d-f018-42db-9be5-4f3f08037a50");
   private static final AttributeModifier GCD_MOD;
   private final NPCPhase<DonKriegEntity> fistPhase = new SimplePhase<DonKriegEntity>("Fist Phase", this);
   private final NPCPhase<DonKriegEntity> daisensoPhase = new SimplePhase<DonKriegEntity>("Daisenso Phase", this, this::startDaisensoPhaseEvent);
   private final NPCPhase<DonKriegEntity> mh5Phase = new SimplePhase<DonKriegEntity>("MH5 Phase", this, this::startMH5PhaseEvent);
   private final NonNullList<ItemStack> bulletsStash;
   private final NonNullList<ItemStack> netsStash;

   public DonKriegEntity(EntityType<DonKriegEntity> type, Level world) {
      super(type, world);
      this.bulletsStash = NonNullList.m_122780_(5, ItemStack.f_41583_);
      this.netsStash = NonNullList.m_122780_(5, ItemStack.f_41583_);
   }

   public DonKriegEntity(InProgressChallenge challenge) {
      super((EntityType)ModMobs.DON_KRIEG.get(), challenge);
      this.bulletsStash = NonNullList.m_122780_(5, ItemStack.f_41583_);
      this.netsStash = NonNullList.m_122780_(5, ItemStack.f_41583_);
   }

   public void initBoss() {
      this.entityStats.setFaction((Faction)ModFactions.PIRATE.get());
      this.entityStats.setRace((Race)ModRaces.HUMAN.get());
      this.entityStats.setFightingStyle((FightingStyle)ModFightingStyles.BRAWLER.get());
      FactionsWorldData worldData = FactionsWorldData.get();
      worldData.addTemporaryCrewMember(ModNPCGroups.KRIEG_PIRATES, this);
      this.m_8061_(EquipmentSlot.CHEST, new ItemStack((ItemLike)ModArmors.WOOTZ_STEEL_ARMOR.get()));
      this.m_21051_((Attribute)ForgeMod.ENTITY_REACH.get()).m_22100_(1.1);
      this.m_21051_((Attribute)ModAttributes.TOUGHNESS.get()).m_22100_((double)2.0F);
      this.m_21051_((Attribute)ForgeMod.SWIM_SPEED.get()).m_22100_((double)1.5F);
      SprintTowardsTargetGoal sprintGoal = new SprintTowardsTargetGoal(this);
      ActiveGuardAbilityWrapperGoal<BakudanAbility> bakudanWrapper = new ActiveGuardAbilityWrapperGoal<BakudanAbility>(this, (AbilityCore)BakudanAbility.INSTANCE.get());
      ((BakudanAbility)bakudanWrapper.getAbility()).getComponent((AbilityComponentKey)ModAbilityComponents.COOLDOWN.get()).ifPresent((comp) -> comp.startCooldown(this, 100.0F));
      ActiveBuffAbilityWrapperGoal<MH5Ability> mh5Wrapper = new ActiveBuffAbilityWrapperGoal<MH5Ability>(this, (AbilityCore)MH5Ability.INSTANCE.get());
      ((MH5Ability)mh5Wrapper.getAbility()).getComponent((AbilityComponentKey)ModAbilityComponents.CHARGE.get()).ifPresent((comp) -> comp.addStartEvent(90, (_e, _a) -> ((MH5Ability)mh5Wrapper.getAbility()).setTargetPosition(this.m_20183_())));
      ImprovedMeleeAttackGoal meleeAttackGoal = new ImprovedMeleeAttackGoal(this, (double)1.0F, true);
      meleeAttackGoal.setEarlyStop(() -> ((MH5Ability)mh5Wrapper.getAbility()).isCharging());
      ActiveGuardAbilityWrapperGoal<GunArrayAbility> gunArrayWrapper = new ActiveGuardAbilityWrapperGoal<GunArrayAbility>(this, (AbilityCore)GunArrayAbility.INSTANCE.get());
      ActiveGuardAbilityWrapperGoal<WootzNetLauncherAbility> wootzNetLauncherWrapper = new ActiveGuardAbilityWrapperGoal<WootzNetLauncherAbility>(this, (AbilityCore)WootzNetLauncherAbility.INSTANCE.get());
      MobsHelper.addBasicNPCGoals(this);
      this.f_21345_.m_25352_(0, new DonKriegPhaseSwitcherGoal(this));
      this.f_21345_.m_25352_(0, new MH5PhaseGoal(this));
      this.fistPhase.addGoal(1, meleeAttackGoal);
      this.fistPhase.addGoal(1, sprintGoal);
      this.fistPhase.addGoal(3, new CloseMeleeAbilityWrapperGoal(this, (AbilityCore)ChargedPunchAbility.INSTANCE.get()));
      this.fistPhase.addGoal(3, bakudanWrapper);
      this.daisensoPhase.addGoal(0, new DashDodgeProjectilesGoal(this, 350.0F, 2.5F));
      this.daisensoPhase.addGoal(0, new DashDodgeTargetGoal(this, 350.0F, 2.5F));
      this.daisensoPhase.addGoal(1, meleeAttackGoal);
      this.daisensoPhase.addGoal(1, sprintGoal);
      this.daisensoPhase.addGoal(1, wootzNetLauncherWrapper);
      this.daisensoPhase.addGoal(2, gunArrayWrapper);
      this.daisensoPhase.addGoal(3, new CloseMeleeAbilityWrapperGoal(this, (AbilityCore)ChargedCleaveAbility.INSTANCE.get()));
      this.daisensoPhase.addGoal(3, bakudanWrapper);
      this.mh5Phase.addGoal(1, mh5Wrapper);
      if (this.getChallengeInfo().isDifficultyStandard()) {
         this.entityStats.setDoriki((double)2000.0F);
         this.m_21051_((Attribute)ModAttributes.GCD.get()).m_22100_((double)40.0F);
         this.m_21051_((Attribute)ModAttributes.PUNCH_DAMAGE.get()).m_22100_((double)4.0F);
         this.fillBulletStash((Item)ModItems.BULLET.get());
         this.fillNetStash((Item)ModItems.ROPE_NET.get());
         ((BakudanAbility)bakudanWrapper.getAbility()).getComponent((AbilityComponentKey)ModAbilityComponents.COOLDOWN.get()).ifPresent((comp) -> comp.getBonusManager().addBonus(STANDARD_BAKUDAN_COOLDOWN_BONUS_UUID, "Standard Bakudan Cooldown Bonus", BonusOperation.MUL, 2.0F));
         this.getPhaseManager().setPhase(this.fistPhase);
      } else {
         this.entityStats.setDoriki((double)10000.0F);
         this.hakiCapability.setBusoshokuHakiExp(100.0F);
         this.hakiCapability.setKenbunshokuHakiExp(100.0F);
         this.m_21051_((Attribute)ModAttributes.GCD.get()).m_22100_((double)20.0F);
         this.m_21051_((Attribute)ModAttributes.TOUGHNESS.get()).m_22100_((double)3.0F);
         this.m_21051_((Attribute)ModAttributes.PUNCH_DAMAGE.get()).m_22100_((double)4.0F);
         this.m_21051_(Attributes.f_22276_).m_22100_((double)400.0F);
         this.m_21051_(Attributes.f_22284_).m_22100_((double)20.0F);
         this.m_21051_(Attributes.f_22285_).m_22100_((double)8.0F);
         this.fillBulletStash((Item)ModItems.KAIROSEKI_BULLET.get());
         this.fillNetStash((Item)ModItems.KAIROSEKI_NET.get());
         this.f_21345_.m_25352_(0, new HakiAbilityWrapperGoal(this, (AbilityCore)BusoshokuHakiInternalDestructionAbility.INSTANCE.get()));
         this.fistPhase.addGoal(0, new HakiAbilityWrapperGoal(this, (AbilityCore)BusoshokuHakiHardeningAbility.INSTANCE.get()));
         this.fistPhase.addGoal(3, new CloseMeleeAbilityWrapperGoal(this, (AbilityCore)HakaiHoAbility.INSTANCE.get()));
         this.fistPhase.addGoal(3, new CloseMeleeAbilityWrapperGoal(this, (AbilityCore)JishinHoAbility.INSTANCE.get()));
         this.daisensoPhase.addGoal(0, new HakiAbilityWrapperGoal(this, (AbilityCore)BusoshokuHakiImbuingAbility.INSTANCE.get()));
         this.daisensoPhase.addGoal(3, new CloseMeleeAbilityWrapperGoal(this, (AbilityCore)JishinHoAbility.INSTANCE.get()));
         this.daisensoPhase.addGoal(3, new JumpAbilityWrapperGoal(this, (AbilityCore)SlamAbility.INSTANCE.get()));
         this.mh5Phase.addGoal(0, new HakiAbilityWrapperGoal(this, (AbilityCore)BusoshokuHakiFullBodyHardeningAbility.INSTANCE.get()));
         this.mh5Phase.addGoal(0, new ReactiveGuardAbilityWrapperGoal(this, (AbilityCore)KamieAbility.INSTANCE.get()));
         this.getPhaseManager().setPhase(this.daisensoPhase);
      }

   }

   public static AttributeSupplier.Builder createAttributes() {
      return OPEntity.createAttributes().m_22268_(Attributes.f_22277_, (double)60.0F).m_22268_(Attributes.f_22279_, (double)0.3F).m_22268_(Attributes.f_22281_, (double)6.0F).m_22268_(Attributes.f_22276_, (double)200.0F).m_22268_(Attributes.f_22284_, (double)10.0F).m_22268_(Attributes.f_22278_, 0.9).m_22268_(Attributes.f_22282_, (double)1.0F);
   }

   private void fillBulletStash(Item bulletType) {
      for(int i = 0; i < this.bulletsStash.size(); ++i) {
         ItemStack newStack = bulletType.m_7968_();
         newStack.m_41764_(1);
         this.bulletsStash.set(i, newStack);
      }

   }

   private void fillNetStash(Item netType) {
      for(int i = 0; i < this.netsStash.size(); ++i) {
         ItemStack newStack = netType.m_7968_();
         newStack.m_41764_(64);
         this.netsStash.set(i, newStack);
      }

   }

   public void m_8119_() {
      super.m_8119_();
   }

   public Iterable<ItemStack> m_20158_() {
      return Iterables.concat(super.m_20158_(), this.bulletsStash, this.netsStash);
   }

   public void startDaisensoPhase() {
      this.getPhaseManager().setPhase(this.daisensoPhase);
      MH5Ability mh5Ability = (MH5Ability)this.abilityData.getEquippedAbility((AbilityCore)MH5Ability.INSTANCE.get());
      if (mh5Ability != null) {
         int startCooldown = this.isDifficultyHardOrAbove() ? 800 : 400;
         mh5Ability.getComponent((AbilityComponentKey)ModAbilityComponents.COOLDOWN.get()).ifPresent((comp) -> comp.startCooldown(this, (float)startCooldown));
      }

   }

   public void startMH5Phase() {
      this.getPhaseManager().setPhase(this.mh5Phase);
   }

   public boolean hasFistPhaseActive() {
      return this.fistPhase.isActive(this);
   }

   public boolean hasDaisensoPhaseActive() {
      return this.daisensoPhase.isActive(this);
   }

   public boolean hasMH5PhaseActive() {
      return this.mh5Phase.isActive(this);
   }

   private void startDaisensoPhaseEvent(DonKriegEntity entity) {
      this.m_8061_(EquipmentSlot.MAINHAND, new ItemStack((ItemLike)ModWeapons.DAISENSO.get()));
      if (this.isDifficultyHardOrAbove()) {
         AttributeInstance attr = entity.m_21051_((Attribute)ModAttributes.GCD.get());
         if (attr != null && !attr.m_22109_(GCD_MOD)) {
            attr.m_22118_(GCD_MOD);
         }
      }

   }

   private void startMH5PhaseEvent(DonKriegEntity entity) {
      this.m_8061_(EquipmentSlot.HEAD, ((Item)ModArmors.MH5_GAS_MASK.get()).m_7968_());
      this.m_8061_(EquipmentSlot.MAINHAND, ItemStack.f_41583_);
   }

   static {
      GCD_MOD = new AttributeModifier(UUID.fromString("e478aceb-9865-40da-a137-6359ba503bf0"), "GCD Modifier", (double)-5.0F, Operation.ADDITION);
   }
}
