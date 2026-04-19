package xyz.pixelatedw.mineminenomi.entities.mobs.pirates.baroqueworks;

import java.util.UUID;
import net.minecraft.network.chat.Component;
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
import xyz.pixelatedw.mineminenomi.abilities.HookGrabAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.HakaiHoAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiFullBodyHardeningAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiInternalDestructionAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.KenbunshokuHakiFutureSightAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.KamieAbility;
import xyz.pixelatedw.mineminenomi.abilities.suna.BarjanAbility;
import xyz.pixelatedw.mineminenomi.abilities.suna.DesertEncierroAbility;
import xyz.pixelatedw.mineminenomi.abilities.suna.DesertGrandeEspadaAbility;
import xyz.pixelatedw.mineminenomi.abilities.suna.DesertSpadaAbility;
import xyz.pixelatedw.mineminenomi.abilities.suna.GroundSeccoAbility;
import xyz.pixelatedw.mineminenomi.abilities.suna.SablesGuardAbility;
import xyz.pixelatedw.mineminenomi.abilities.suna.SablesPesadoAbility;
import xyz.pixelatedw.mineminenomi.abilities.suna.SunaLogiaAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusOperation;
import xyz.pixelatedw.mineminenomi.api.challenges.InProgressChallenge;
import xyz.pixelatedw.mineminenomi.api.entities.ai.NPCPhase;
import xyz.pixelatedw.mineminenomi.api.entities.ai.SimplePhase;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.entities.revenge.PhysicalDamageDealtRevengeCheck;
import xyz.pixelatedw.mineminenomi.api.entities.revenge.PhysicalHitRevengeCheck;
import xyz.pixelatedw.mineminenomi.api.entities.revenge.RevengeMeter;
import xyz.pixelatedw.mineminenomi.api.entities.revenge.TargetFlyStallingRevengeCheck;
import xyz.pixelatedw.mineminenomi.api.entities.revenge.TargetRunningAwayRevengeCheck;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.api.items.IMultiChannelColorItem;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.world.FactionsWorldData;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.DashDodgeProjectilesGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.DashDodgeTargetGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.ImprovedMeleeAttackGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.JumpOutOfHoleGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ActiveGuardAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.AlwaysActiveAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.CloseMeleeAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.GrabAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.HakiAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ProjectileAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ReactiveGuardAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.UseAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.mr0.Mr0PhaseSwitcherGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPBossEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModArmors;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModFactions;
import xyz.pixelatedw.mineminenomi.init.ModFruits;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.ModMobs;
import xyz.pixelatedw.mineminenomi.init.ModNPCGroups;
import xyz.pixelatedw.mineminenomi.init.ModRaces;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;
import xyz.pixelatedw.mineminenomi.items.AkumaNoMiItem;
import xyz.pixelatedw.mineminenomi.items.weapons.ModSwordItem;

public class Mr0Entity extends OPBossEntity {
   private static final UUID HARD_GRANDE_SPADA_COOLDOWN_BONUS = UUID.fromString("5f6e5fd9-7292-42f0-895c-f0b3ff8da686");
   private static final UUID HARD_SABLES_GUARD_COOLDOWN_BONUS = UUID.fromString("c467addb-27c4-4865-9a01-655c621690e8");
   private static final UUID HARD_SABLES_PESADO_CHARGE_BONUS = UUID.fromString("8f324bfd-f8cd-434d-9467-3001fe7c92df");
   private static final AttributeModifier GCD_MOD;
   private static final int STANDARD_PAIN_THRESHOLD = 100;
   private static final int HARD_PAIN_THRESHOLD = 150;
   private static final int PAIN_GUARD_COOLDOWN = 200;
   private static final int KITING_THRESHOLD = 20;
   private final NPCPhase<Mr0Entity> firstPhase = new SimplePhase<Mr0Entity>("First Phase", this);
   private final NPCPhase<Mr0Entity> secondPhase = new SimplePhase<Mr0Entity>("Second Phase", this, this::startSecondPhaseEvent);
   private final NPCPhase<Mr0Entity> thirdPhase = new SimplePhase<Mr0Entity>("Third Phase", this, this::startThirdPhaseEvent);
   private final RevengeMeter painMeter = new RevengeMeter(this, 999, 0);
   private final RevengeMeter kitingMeter = new RevengeMeter(this, 100, 1);
   private GrabAbilityWrapperGoal<HookGrabAbility> hookGrabWrapper;
   private ActiveGuardAbilityWrapperGoal<SablesGuardAbility> sablesGuardWrapper;
   private GroundSeccoAbility groundSeccoAbility;
   private int painThreshold = 100;

   public Mr0Entity(EntityType<Mr0Entity> type, Level world) {
      super(type, world);
   }

   public Mr0Entity(InProgressChallenge challenge) {
      super((EntityType)ModMobs.MR0.get(), challenge);
   }

   public void initBoss() {
      FactionsWorldData worldData = FactionsWorldData.get();
      this.entityStats.setFaction((Faction)ModFactions.PIRATE.get());
      this.entityStats.setRace((Race)ModRaces.HUMAN.get());
      this.devilFruitData.setDevilFruit((AkumaNoMiItem)ModFruits.SUNA_SUNA_NO_MI.get());
      worldData.addTemporaryCrewMember(ModNPCGroups.BAROQUE_WORKS, this);
      this.m_21051_((Attribute)ModAttributes.TOUGHNESS.get()).m_22100_((double)2.0F);
      this.m_21051_((Attribute)ForgeMod.STEP_HEIGHT_ADDITION.get()).m_22100_((double)1.0F);
      ItemStack capeStack = new ItemStack((ItemLike)ModArmors.FLUFFY_CAPE.get());
      IMultiChannelColorItem.dyeArmor(capeStack, 0, 2040097);
      this.m_8061_(EquipmentSlot.CHEST, capeStack);
      this.m_8061_(EquipmentSlot.OFFHAND, ((ModSwordItem)ModWeapons.HOOK.get()).m_7968_());
      this.m_8061_(EquipmentSlot.HEAD, ((Item)ModItems.CIGAR.get()).m_7968_());
      this.painMeter.addCheck(new PhysicalHitRevengeCheck(1));
      this.painMeter.addCheck(new PhysicalDamageDealtRevengeCheck());
      MobsHelper.addBasicNPCGoals(this);
      ProjectileAbilityWrapperGoal<BarjanAbility> barjanWrapper = new ProjectileAbilityWrapperGoal<BarjanAbility>(this, (AbilityCore)BarjanAbility.INSTANCE.get());
      GrabAbilityWrapperGoal<DesertEncierroAbility> desertEncierroWrapper = new GrabAbilityWrapperGoal<DesertEncierroAbility>(this, (AbilityCore)DesertEncierroAbility.INSTANCE.get());
      ((DesertEncierroAbility)desertEncierroWrapper.getAbility()).addCanUseCheck((entity, ability) -> {
         if (((SablesGuardAbility)this.sablesGuardWrapper.getAbility()).isContinuous()) {
            return Result.fail((Component)null);
         } else {
            return GoalHelper.hasEnoughTargetsAround(this, 2, 20.0F) ? Result.fail((Component)null) : Result.success();
         }
      });
      ProjectileAbilityWrapperGoal<DesertGrandeEspadaAbility> desertGrandeEspadaWrapper = new ProjectileAbilityWrapperGoal<DesertGrandeEspadaAbility>(this, (AbilityCore)DesertGrandeEspadaAbility.INSTANCE.get());
      ((DesertGrandeEspadaAbility)desertGrandeEspadaWrapper.getAbility()).getComponent((AbilityComponentKey)ModAbilityComponents.COOLDOWN.get()).ifPresent((comp) -> comp.startCooldown(this, 400.0F));
      this.hookGrabWrapper = new GrabAbilityWrapperGoal<HookGrabAbility>(this, (AbilityCore)HookGrabAbility.INSTANCE.get());
      UseAbilityWrapperGoal<GroundSeccoAbility> groundSeccoWrapper = new UseAbilityWrapperGoal<GroundSeccoAbility>(this, (AbilityCore)GroundSeccoAbility.INSTANCE.get());
      this.groundSeccoAbility = (GroundSeccoAbility)groundSeccoWrapper.getAbility();
      this.groundSeccoAbility.getComponent((AbilityComponentKey)ModAbilityComponents.COOLDOWN.get()).ifPresent((comp) -> comp.startCooldown(this, 600.0F));
      this.groundSeccoAbility.addCanUseCheck((entity, ability) -> {
         if (((SablesGuardAbility)this.sablesGuardWrapper.getAbility()).isContinuous()) {
            return Result.fail((Component)null);
         } else {
            return ((HookGrabAbility)this.hookGrabWrapper.getAbility()).isContinuous() ? Result.fail((Component)null) : Result.success();
         }
      });
      ProjectileAbilityWrapperGoal<SablesPesadoAbility> sablesPesadoWrapper = new ProjectileAbilityWrapperGoal<SablesPesadoAbility>(this, (AbilityCore)SablesPesadoAbility.INSTANCE.get());
      ProjectileAbilityWrapperGoal<DesertSpadaAbility> desertSpadaWrapper = new ProjectileAbilityWrapperGoal<DesertSpadaAbility>(this, (AbilityCore)DesertSpadaAbility.INSTANCE.get());
      ((DesertSpadaAbility)desertSpadaWrapper.getAbility()).getComponent((AbilityComponentKey)ModAbilityComponents.COOLDOWN.get()).ifPresent((comp) -> comp.startCooldown(this, 200.0F));
      this.sablesGuardWrapper = new ActiveGuardAbilityWrapperGoal<SablesGuardAbility>(this, (AbilityCore)SablesGuardAbility.INSTANCE.get());
      ((SablesGuardAbility)this.sablesGuardWrapper.getAbility()).addCanUseCheck((entity, ability) -> ((DesertEncierroAbility)desertEncierroWrapper.getAbility()).isContinuous() ? Result.fail((Component)null) : Result.success());
      ImprovedMeleeAttackGoal meleeAttackGoal = new ImprovedMeleeAttackGoal(this, (double)1.0F, true);
      meleeAttackGoal.setEarlyStop(() -> ((GroundSeccoAbility)groundSeccoWrapper.getAbility()).isCharging() || ((SablesGuardAbility)this.sablesGuardWrapper.getAbility()).isContinuous() || ((SablesPesadoAbility)sablesPesadoWrapper.getAbility()).isCharging() || ((DesertEncierroAbility)desertEncierroWrapper.getAbility()).isCharging() || ((HookGrabAbility)this.hookGrabWrapper.getAbility()).isContinuous());
      this.f_21345_.m_25352_(0, new Mr0PhaseSwitcherGoal(this));
      this.f_21345_.m_25352_(0, new AlwaysActiveAbilityWrapperGoal(this, (AbilityCore)SunaLogiaAbility.INSTANCE.get()));
      this.f_21345_.m_25352_(0, this.hookGrabWrapper);
      this.f_21345_.m_25352_(0, new JumpOutOfHoleGoal(this));
      this.firstPhase.addGoal(1, meleeAttackGoal);
      this.firstPhase.addGoal(3, barjanWrapper);
      this.firstPhase.addGoal(3, this.sablesGuardWrapper);
      this.secondPhase.addGoal(0, new DashDodgeProjectilesGoal(this, 300.0F, 2.0F));
      this.secondPhase.addGoal(0, new DashDodgeTargetGoal(this, 300.0F, 2.0F));
      this.secondPhase.addGoal(1, meleeAttackGoal);
      this.secondPhase.addGoal(3, barjanWrapper);
      this.secondPhase.addGoal(3, this.sablesGuardWrapper);
      this.secondPhase.addGoal(3, desertEncierroWrapper);
      this.secondPhase.addGoal(3, groundSeccoWrapper);
      this.secondPhase.addGoal(3, desertGrandeEspadaWrapper);
      if (this.getChallengeInfo().isDifficultyStandard()) {
         this.entityStats.setDoriki((double)2000.0F);
         this.m_21051_((Attribute)ModAttributes.GCD.get()).m_22100_((double)40.0F);
         this.m_21051_((Attribute)ModAttributes.PUNCH_DAMAGE.get()).m_22100_((double)4.0F);
         this.getPhaseManager().setPhase(this.firstPhase);
         ((HookGrabAbility)this.hookGrabWrapper.getAbility()).getComponent((AbilityComponentKey)ModAbilityComponents.COOLDOWN.get()).ifPresent((comp) -> comp.startCooldown(this, 400.0F));
      } else {
         this.entityStats.setDoriki((double)10000.0F);
         this.hakiCapability.setBusoshokuHakiExp(100.0F);
         this.hakiCapability.setKenbunshokuHakiExp(100.0F);
         this.m_21051_((Attribute)ModAttributes.GCD.get()).m_22100_((double)20.0F);
         this.m_21051_((Attribute)ModAttributes.TOUGHNESS.get()).m_22100_((double)6.0F);
         this.m_21051_((Attribute)ModAttributes.PUNCH_DAMAGE.get()).m_22100_((double)4.0F);
         this.m_21051_(Attributes.f_22276_).m_22100_((double)400.0F);
         this.m_21051_(Attributes.f_22284_).m_22100_((double)20.0F);
         this.m_21051_(Attributes.f_22285_).m_22100_((double)8.0F);
         this.painThreshold = 150;
         this.kitingMeter.addCheck(new TargetFlyStallingRevengeCheck(1));
         this.kitingMeter.addCheck(new TargetRunningAwayRevengeCheck(1, 10.0F));
         CloseMeleeAbilityWrapperGoal<HakaiHoAbility> hakaiHoWrapper = new CloseMeleeAbilityWrapperGoal<HakaiHoAbility>(this, (AbilityCore)HakaiHoAbility.INSTANCE.get());
         ((HakaiHoAbility)hakaiHoWrapper.getAbility()).addCanUseCheck((entity, ability) -> {
            DesertEncierroAbility abl = (DesertEncierroAbility)AbilityCapability.get(entity).map((props) -> (DesertEncierroAbility)props.getEquippedAbility((AbilityCore)DesertEncierroAbility.INSTANCE.get())).orElse((Object)null);
            return abl != null && abl.isContinuous() ? Result.fail((Component)null) : Result.success();
         });
         ((HakaiHoAbility)hakaiHoWrapper.getAbility()).getComponent((AbilityComponentKey)ModAbilityComponents.COOLDOWN.get()).ifPresent((comp) -> comp.startCooldown(this, 200.0F));
         ReactiveGuardAbilityWrapperGoal<KamieAbility> kamieWrapper = new ReactiveGuardAbilityWrapperGoal<KamieAbility>(this, (AbilityCore)KamieAbility.INSTANCE.get());
         ((KamieAbility)kamieWrapper.getAbility()).addCanUseCheck((entity, ability) -> {
            if (this.groundSeccoAbility.isOnCooldown()) {
               float cooldown = (Float)this.groundSeccoAbility.getComponent((AbilityComponentKey)ModAbilityComponents.COOLDOWN.get()).map((comp) -> comp.getCooldown()).orElse(0.0F);
               if (cooldown <= 100.0F) {
                  return Result.fail((Component)null);
               }
            }

            return Result.success();
         });
         ((SablesPesadoAbility)sablesPesadoWrapper.getAbility()).getComponent((AbilityComponentKey)ModAbilityComponents.COOLDOWN.get()).ifPresent((comp) -> comp.getBonusManager().addBonus(HARD_SABLES_PESADO_CHARGE_BONUS, "Hard Sables Pesado Charge Bonus", BonusOperation.ADD, -40.0F));
         ((DesertGrandeEspadaAbility)desertGrandeEspadaWrapper.getAbility()).getComponent((AbilityComponentKey)ModAbilityComponents.COOLDOWN.get()).ifPresent((comp) -> comp.getBonusManager().addBonus(HARD_GRANDE_SPADA_COOLDOWN_BONUS, "Hard Grande Spada Cooldown Bonus", BonusOperation.ADD, -100.0F));
         this.groundSeccoAbility.setSizeModifier(1.5F);
         this.f_21345_.m_25352_(0, new HakiAbilityWrapperGoal(this, (AbilityCore)BusoshokuHakiInternalDestructionAbility.INSTANCE.get()));
         this.f_21345_.m_25352_(0, new HakiAbilityWrapperGoal(this, (AbilityCore)BusoshokuHakiFullBodyHardeningAbility.INSTANCE.get()));
         this.secondPhase.addGoal(0, new HakiAbilityWrapperGoal(this, (AbilityCore)KenbunshokuHakiFutureSightAbility.INSTANCE.get()));
         this.secondPhase.addGoal(0, new DashDodgeProjectilesGoal(this, 250.0F, 2.5F));
         this.secondPhase.addGoal(0, new DashDodgeTargetGoal(this, 250.0F, 2.5F));
         this.secondPhase.addGoal(0, kamieWrapper);
         this.secondPhase.addGoal(3, hakaiHoWrapper);
         this.thirdPhase.addGoal(0, new HakiAbilityWrapperGoal(this, (AbilityCore)KenbunshokuHakiFutureSightAbility.INSTANCE.get()));
         this.thirdPhase.addGoal(0, new DashDodgeProjectilesGoal(this, 250.0F, 2.5F));
         this.thirdPhase.addGoal(0, new DashDodgeTargetGoal(this, 250.0F, 2.5F));
         this.thirdPhase.addGoal(0, kamieWrapper);
         this.thirdPhase.addGoal(1, meleeAttackGoal);
         this.thirdPhase.addGoal(3, hakaiHoWrapper);
         this.thirdPhase.addGoal(3, barjanWrapper);
         this.thirdPhase.addGoal(3, this.sablesGuardWrapper);
         this.thirdPhase.addGoal(3, desertEncierroWrapper);
         this.thirdPhase.addGoal(3, groundSeccoWrapper);
         this.thirdPhase.addGoal(3, sablesPesadoWrapper);
         this.thirdPhase.addGoal(3, desertGrandeEspadaWrapper);
         this.getPhaseManager().setPhase(this.secondPhase);
      }

   }

   public static AttributeSupplier.Builder createAttributes() {
      return OPEntity.createAttributes().m_22268_(Attributes.f_22277_, (double)60.0F).m_22268_(Attributes.f_22279_, (double)0.28F).m_22268_(Attributes.f_22281_, (double)6.0F).m_22268_(Attributes.f_22276_, (double)300.0F).m_22268_(Attributes.f_22284_, (double)2.0F).m_22268_(Attributes.f_22278_, 0.2);
   }

   public void m_8119_() {
      super.m_8119_();
      if (!this.m_9236_().f_46443_) {
         this.painMeter.tick();
         this.kitingMeter.tick();
         if (!((SablesGuardAbility)this.sablesGuardWrapper.getAbility()).isContinuous() && this.painMeter.getRevengeValue() > this.painThreshold && this.sablesGuardWrapper.hasTimePassedSinceLastEnd(200.0F)) {
            this.painMeter.addRevengeValue(-this.painThreshold);
            ((SablesGuardAbility)this.sablesGuardWrapper.getAbility()).getComponent((AbilityComponentKey)ModAbilityComponents.COOLDOWN.get()).ifPresent((comp) -> comp.stopCooldown(this));
            ((SablesGuardAbility)this.sablesGuardWrapper.getAbility()).use(this);
         }

         if (!((HookGrabAbility)this.hookGrabWrapper.getAbility()).isContinuous() && this.kitingMeter.getRevengeValue() > 20) {
            this.kitingMeter.addRevengeValue(-20);
            ((HookGrabAbility)this.hookGrabWrapper.getAbility()).getComponent((AbilityComponentKey)ModAbilityComponents.COOLDOWN.get()).ifPresent((comp) -> comp.stopCooldown(this));
         }
      }

   }

   public void startSecondPhase() {
      this.getPhaseManager().setPhase(this.secondPhase);
   }

   public void startThirdPhase() {
      this.getPhaseManager().setPhase(this.thirdPhase);
   }

   public boolean isFirstPhaseActive() {
      return this.firstPhase.isActive(this);
   }

   public boolean isSecondPhaseActive() {
      return this.secondPhase.isActive(this);
   }

   public boolean isThirdPhaseActive() {
      return this.thirdPhase.isActive(this);
   }

   private void startSecondPhaseEvent(Mr0Entity entity) {
      if (this.isDifficultyHardOrAbove()) {
         AttributeInstance attr = entity.m_21051_((Attribute)ModAttributes.GCD.get());
         if (attr != null && !attr.m_22109_(GCD_MOD)) {
            attr.m_22118_(GCD_MOD);
         }
      }

   }

   private void startThirdPhaseEvent(Mr0Entity entity) {
      this.groundSeccoAbility.setSizeModifier(2.0F);
   }

   public RevengeMeter getPainMeter() {
      return this.painMeter;
   }

   static {
      GCD_MOD = new AttributeModifier(UUID.fromString("e478aceb-9865-40da-a137-6359ba503bf0"), "GCD Modifier", (double)-5.0F, Operation.ADDITION);
   }
}
