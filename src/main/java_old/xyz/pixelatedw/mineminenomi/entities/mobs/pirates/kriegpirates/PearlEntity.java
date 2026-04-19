package xyz.pixelatedw.mineminenomi.entities.mobs.pirates.kriegpirates;

import com.google.common.collect.Sets;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import xyz.pixelatedw.mineminenomi.abilities.BellyFlopAbility;
import xyz.pixelatedw.mineminenomi.abilities.PearlFireAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.ChargedPunchAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.HakaiHoAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.JishinHoAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiFullBodyHardeningAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiInternalDestructionAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.TekkaiAbility;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.challenges.InProgressChallenge;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.entities.ai.NPCPhase;
import xyz.pixelatedw.mineminenomi.api.entities.ai.SimplePhase;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.entities.revenge.PhysicalHitRevengeCheck;
import xyz.pixelatedw.mineminenomi.api.entities.revenge.RevengeMeter;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.TargetHelper;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.data.world.FactionsWorldData;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.ImprovedMeleeAttackGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ActiveBuffAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ActiveGuardAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.CloseMeleeAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.HakiAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.JumpAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPBossEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModArmors;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModFactions;
import xyz.pixelatedw.mineminenomi.init.ModFightingStyles;
import xyz.pixelatedw.mineminenomi.init.ModMobs;
import xyz.pixelatedw.mineminenomi.init.ModNPCGroups;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRaces;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class PearlEntity extends OPBossEntity {
   private static final AttributeModifier ATTACK_MODIFIER;
   private static final AttributeModifier PUNCH_MODIFIER;
   private static final AttributeModifier ARMOR_TOUGHNESS_MODIFIER;
   private static final UUID REVENGE_ATTACK_MODIFIER;
   private static final UUID REVENGE_PUNCH_MODIFIER;
   private RevengeMeter revengeMeter;
   private NPCPhase<PearlEntity> normalPhase;
   private NPCPhase<PearlEntity> flamingPhase;
   private int tekkaiAbuseStartCounter;
   private int tekkaiWaitCounter;
   private int prevRevengeVal;

   public PearlEntity(EntityType<PearlEntity> type, Level world) {
      super(type, world);
   }

   public PearlEntity(InProgressChallenge challenge) {
      super((EntityType)ModMobs.PEARL.get(), challenge);
   }

   public void initBoss() {
      this.revengeMeter = new RevengeMeter(this, 1000, 1);
      this.normalPhase = new SimplePhase<PearlEntity>("Normal Phase", this);
      this.flamingPhase = new SimplePhase<PearlEntity>("Flaming Phase", this, this::startFlamingPhaseEvent, this::stopFlamingPhaseEvent);
      this.entityStats.setFaction((Faction)ModFactions.PIRATE.get());
      this.entityStats.setRace((Race)ModRaces.HUMAN.get());
      this.entityStats.setFightingStyle((FightingStyle)ModFightingStyles.BRAWLER.get());
      FactionsWorldData worldData = FactionsWorldData.get();
      worldData.addTemporaryCrewMember(ModNPCGroups.KRIEG_PIRATES, this);
      this.m_8061_(EquipmentSlot.HEAD, new ItemStack((ItemLike)ModArmors.PEARL_HAT.get()));
      this.m_8061_(EquipmentSlot.CHEST, new ItemStack((ItemLike)ModArmors.PEARL_ARMOR.get()));
      this.m_8061_(EquipmentSlot.LEGS, new ItemStack((ItemLike)ModArmors.PEARL_LEGS.get()));
      this.m_21051_((Attribute)ForgeMod.ENTITY_REACH.get()).m_22100_(1.1);
      this.m_21051_((Attribute)ModAttributes.TOUGHNESS.get()).m_22100_((double)2.0F);
      this.m_21051_((Attribute)ForgeMod.SWIM_SPEED.get()).m_22100_((double)1.5F);
      this.revengeMeter.addCheck(new PhysicalHitRevengeCheck(5));
      CloseMeleeAbilityWrapperGoal<ChargedPunchAbility> chargedPunchWrapper = new CloseMeleeAbilityWrapperGoal<ChargedPunchAbility>(this, (AbilityCore)ChargedPunchAbility.INSTANCE.get());
      ActiveGuardAbilityWrapperGoal<TekkaiAbility> tekkaiWrapper = new ActiveGuardAbilityWrapperGoal<TekkaiAbility>(this, (AbilityCore)TekkaiAbility.INSTANCE.get());
      ((TekkaiAbility)tekkaiWrapper.getAbility()).getComponent((AbilityComponentKey)ModAbilityComponents.CONTINUOUS.get()).ifPresent((comp) -> {
         comp.addStartEvent(110, (entity, ability) -> {
            this.tekkaiWaitCounter = 0;
            this.tekkaiAbuseStartCounter = this.revengeMeter.countCheckTriggers(PhysicalHitRevengeCheck.class);
         });
         comp.addTickEvent(110, (entity, ability) -> {
            if (this.f_19797_ % 10 == 0) {
               List<LivingEntity> targets = TargetHelper.<LivingEntity>getEntitiesInArea(this.m_9236_(), this, (double)7.0F, TargetPredicate.DEFAULT_AREA_CHECK, LivingEntity.class);
               if (targets.size() > 0) {
                  this.tekkaiWaitCounter += targets.size();
               }

               int currentCounter = this.revengeMeter.countCheckTriggers(PhysicalHitRevengeCheck.class);
               if (this.tekkaiWaitCounter > 6 || currentCounter >= this.tekkaiAbuseStartCounter + 5) {
                  double pushPower = this.isDifficultyHard() ? (double)7.0F : (double)5.0F;
                  this.pushEnemiesAway(pushPower);
                  this.resetCooldowns();
                  this.revengeMeter.addRevengeValue(30);
                  comp.stopContinuity(this);
               }
            }

         });
      });
      ActiveBuffAbilityWrapperGoal<PearlFireAbility> pearlFireWrapper = new ActiveBuffAbilityWrapperGoal<PearlFireAbility>(this, (AbilityCore)PearlFireAbility.INSTANCE.get());
      ((PearlFireAbility)pearlFireWrapper.getAbility()).getComponent((AbilityComponentKey)ModAbilityComponents.CONTINUOUS.get()).ifPresent((comp) -> comp.addTickEvent((entity, ability) -> comp.increaseContinuityTime(2.0F)));
      MobsHelper.addBasicNPCGoals(this);
      this.f_21345_.m_25352_(1, new ImprovedMeleeAttackGoal(this, (double)1.0F, true));
      this.f_21345_.m_25352_(2, chargedPunchWrapper);
      this.f_21345_.m_25352_(2, tekkaiWrapper);
      this.flamingPhase.addGoal(0, pearlFireWrapper);
      this.getPhaseManager().setPhase(this.normalPhase);
      if (this.getChallengeInfo().isDifficultyStandard()) {
         this.entityStats.setDoriki((double)2000.0F);
         this.m_21051_((Attribute)ModAttributes.GCD.get()).m_22100_((double)40.0F);
         this.m_21051_((Attribute)ModAttributes.PUNCH_DAMAGE.get()).m_22100_((double)4.0F);
      } else {
         this.entityStats.setDoriki((double)10000.0F);
         this.hakiCapability.setBusoshokuHakiExp(100.0F);
         this.hakiCapability.setKenbunshokuHakiExp(100.0F);
         this.m_21051_((Attribute)ModAttributes.GCD.get()).m_22100_((double)20.0F);
         this.m_21051_((Attribute)ModAttributes.TOUGHNESS.get()).m_22100_((double)3.0F);
         this.m_21051_((Attribute)ModAttributes.PUNCH_DAMAGE.get()).m_22100_((double)4.0F);
         this.m_21051_(Attributes.f_22276_).m_22100_((double)250.0F);
         this.m_21051_(Attributes.f_22284_).m_22100_((double)20.0F);
         this.m_21051_(Attributes.f_22285_).m_22100_((double)8.0F);
         this.f_21345_.m_25352_(0, new HakiAbilityWrapperGoal(this, (AbilityCore)BusoshokuHakiInternalDestructionAbility.INSTANCE.get()));
         this.f_21345_.m_25352_(0, new HakiAbilityWrapperGoal(this, (AbilityCore)BusoshokuHakiFullBodyHardeningAbility.INSTANCE.get()));
         this.f_21345_.m_25352_(2, new CloseMeleeAbilityWrapperGoal(this, (AbilityCore)HakaiHoAbility.INSTANCE.get()));
         this.f_21345_.m_25352_(2, new CloseMeleeAbilityWrapperGoal(this, (AbilityCore)JishinHoAbility.INSTANCE.get()));
         this.f_21345_.m_25352_(2, new JumpAbilityWrapperGoal(this, (AbilityCore)BellyFlopAbility.INSTANCE.get()));
      }

   }

   public static AttributeSupplier.Builder createAttributes() {
      return OPEntity.createAttributes().m_22268_(Attributes.f_22277_, (double)60.0F).m_22268_(Attributes.f_22279_, (double)0.28F).m_22268_(Attributes.f_22281_, (double)4.0F).m_22268_(Attributes.f_22276_, (double)200.0F).m_22268_(Attributes.f_22284_, (double)10.0F).m_22268_(Attributes.f_22285_, (double)4.0F).m_22268_(Attributes.f_22278_, (double)1.0F).m_22268_(Attributes.f_22282_, (double)1.0F).m_22268_(Attributes.f_22283_, (double)3.0F);
   }

   public void m_8119_() {
      super.m_8119_();
      if (!this.m_9236_().f_46443_ && this.m_6084_() && this.f_19797_ % 10 == 0) {
         int physicalHits = this.revengeMeter.countCheckTriggers(PhysicalHitRevengeCheck.class);
         if (this.normalPhase.isActive(this) && ((double)this.m_21223_() <= WyHelper.percentage((double)30.0F, (double)this.m_21233_()) || physicalHits >= 20)) {
            this.getPhaseManager().setPhase(this.flamingPhase);
         }

         if (this.flamingPhase.isActive(this) && (this.m_6844_(EquipmentSlot.HEAD).m_41619_() || this.m_6844_(EquipmentSlot.CHEST).m_41619_() || this.m_6844_(EquipmentSlot.LEGS).m_41619_())) {
            this.getPhaseManager().setPhase(this.normalPhase);
         }

         if (this.isDifficultyHard()) {
            int revengeVal = this.revengeMeter.getRevengeValue();
            if (revengeVal > 10 && revengeVal != this.prevRevengeVal) {
               this.prevRevengeVal = revengeVal;
               double damageBonus = (double)revengeVal / (double)15.0F;
               this.m_21051_(Attributes.f_22281_).m_22120_(REVENGE_ATTACK_MODIFIER);
               this.m_21051_((Attribute)ModAttributes.PUNCH_DAMAGE.get()).m_22120_(REVENGE_PUNCH_MODIFIER);
               this.m_21051_(Attributes.f_22281_).m_22118_(new AttributeModifier(REVENGE_ATTACK_MODIFIER, "Revenge Attack Bonus", damageBonus, Operation.ADDITION));
               this.m_21051_((Attribute)ModAttributes.PUNCH_DAMAGE.get()).m_22118_(new AttributeModifier(REVENGE_PUNCH_MODIFIER, "Revenge Punch Bonus", damageBonus, Operation.ADDITION));
            }
         }
      }

   }

   private void startFlamingPhaseEvent(PearlEntity entity) {
      this.m_21051_(Attributes.f_22281_).m_22118_(ATTACK_MODIFIER);
      this.m_21051_((Attribute)ModAttributes.PUNCH_DAMAGE.get()).m_22118_(PUNCH_MODIFIER);
      this.m_21051_(Attributes.f_22285_).m_22118_(ARMOR_TOUGHNESS_MODIFIER);
      this.pushEnemiesAway((double)5.0F);
   }

   private void stopFlamingPhaseEvent(PearlEntity entity) {
      this.m_21051_(Attributes.f_22281_).m_22130_(ATTACK_MODIFIER);
      this.m_21051_((Attribute)ModAttributes.PUNCH_DAMAGE.get()).m_22130_(PUNCH_MODIFIER);
      this.m_21051_(Attributes.f_22285_).m_22130_(ARMOR_TOUGHNESS_MODIFIER);
   }

   private void pushEnemiesAway(double power) {
      for(LivingEntity target : TargetHelper.getEntitiesInArea(this.m_9236_(), this, (double)15.0F, TargetPredicate.DEFAULT_AREA_CHECK, LivingEntity.class)) {
         Vec3 dirVec = target.m_20182_().m_82546_(this.m_20182_()).m_82541_().m_82542_(power, (double)1.0F, power);
         AbilityHelper.setDeltaMovement(target, dirVec.f_82479_, (double)0.0F, dirVec.f_82481_);
      }

      WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.BERSERKER_POWERUP.get(), this, this.m_20185_(), this.m_20186_(), this.m_20189_());
   }

   private void resetCooldowns() {
      for(IAbility ability : ((IAbilityData)AbilityCapability.get(this).get()).getEquippedAbilities()) {
         if (ability != null) {
            ability.getComponent((AbilityComponentKey)ModAbilityComponents.COOLDOWN.get()).ifPresent((comp) -> comp.stopCooldown(this));
         }
      }

   }

   public Set<RevengeMeter> getRevengeMeters() {
      return Sets.newHashSet(new RevengeMeter[]{this.revengeMeter});
   }

   static {
      ATTACK_MODIFIER = new AttributeModifier(UUID.fromString("989e4cf0-cada-442c-b6e1-c3eddca0545f"), "Attack Damage Bonus", (double)2.0F, Operation.ADDITION);
      PUNCH_MODIFIER = new AttributeModifier(UUID.fromString("2cba6879-f98d-404f-b169-0d3e2a3dd903"), "Punch Damage Bonus", (double)2.0F, Operation.ADDITION);
      ARMOR_TOUGHNESS_MODIFIER = new AttributeModifier(UUID.fromString("30afa6b8-6318-428b-89a8-fad41d228d3a"), "Armor Toughness Bonus", (double)2.0F, Operation.MULTIPLY_BASE);
      REVENGE_ATTACK_MODIFIER = UUID.fromString("127f4823-6440-4175-b590-5011b2fbe67c");
      REVENGE_PUNCH_MODIFIER = UUID.fromString("dba9b47e-8611-4245-a7e5-f46f7a0924e3");
   }
}
