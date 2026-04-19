package xyz.pixelatedw.mineminenomi.entities.mobs.pirates.blackcatpirates;

import java.util.function.Predicate;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.abilities.OneTwoJangoAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiImbuingAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiInternalDestructionAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.GeppoAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.KamieAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.SoruAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.challenges.InProgressChallenge;
import xyz.pixelatedw.mineminenomi.api.entities.ai.NPCPhase;
import xyz.pixelatedw.mineminenomi.api.entities.ai.SimplePhase;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.api.items.IMultiChannelColorItem;
import xyz.pixelatedw.mineminenomi.data.world.FactionsWorldData;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.ClimbOutOfHoleGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.DashDodgeProjectilesGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.DashDodgeTargetGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.ImprovedMeleeAttackGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.TauntTargetGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.ThrowChakramsGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ActiveGuardAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.DashAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.HakiAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ReactiveGuardAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ReactiveRetreatAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPBossEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;
import xyz.pixelatedw.mineminenomi.init.ModArmors;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModFactions;
import xyz.pixelatedw.mineminenomi.init.ModMobs;
import xyz.pixelatedw.mineminenomi.init.ModNPCGroups;
import xyz.pixelatedw.mineminenomi.init.ModRaces;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;
import xyz.pixelatedw.mineminenomi.items.weapons.ChakramItem;

public class JangoEntity extends OPBossEntity {
   private NPCPhase<JangoEntity> standardPhase = new SimplePhase<JangoEntity>("Standard Phase", this);
   private NPCPhase<JangoEntity> tauntPhase = new SimplePhase<JangoEntity>("Taunt Phase", this);

   public JangoEntity(EntityType<JangoEntity> type, Level world) {
      super(type, world);
   }

   public JangoEntity(InProgressChallenge challenge) {
      super((EntityType)ModMobs.JANGO.get(), challenge);
   }

   public void initBoss() {
      ItemStack headStack = ((Item)ModArmors.WIDE_BRIM_HAT.get()).m_7968_();
      IMultiChannelColorItem.dyeArmor(headStack, 0, 1646668);
      this.m_8061_(EquipmentSlot.HEAD, headStack);
      this.m_8061_(EquipmentSlot.MAINHAND, ((ChakramItem)ModWeapons.CHAKRAM.get()).m_7968_());
      if (this.isDifficultyHardOrAbove()) {
         this.m_8061_(EquipmentSlot.OFFHAND, ((ChakramItem)ModWeapons.CHAKRAM.get()).m_7968_());
      }

      FactionsWorldData worldData = FactionsWorldData.get();
      this.entityStats.setFaction((Faction)ModFactions.PIRATE.get());
      this.entityStats.setRace((Race)ModRaces.HUMAN.get());
      worldData.addTemporaryCrewMember(ModNPCGroups.BLACK_CAT_PIRATES, this);
      this.f_21345_.m_25352_(0, new ClimbOutOfHoleGoal(this));
      if (this.getChallengeInfo().isDifficultyStandard()) {
         this.entityStats.setDoriki((double)1000.0F);
         this.m_21051_((Attribute)ModAttributes.GCD.get()).m_22100_((double)40.0F);
         this.m_21051_((Attribute)ModAttributes.PUNCH_DAMAGE.get()).m_22100_((double)4.0F);
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
      }

      MobsHelper.addBasicNPCGoals(this);
      ActiveGuardAbilityWrapperGoal<OneTwoJangoAbility> oneTwoJangoWrapper = new ActiveGuardAbilityWrapperGoal<OneTwoJangoAbility>(this, (AbilityCore)OneTwoJangoAbility.INSTANCE.get());
      this.standardPhase.addGoal(1, new ImprovedMeleeAttackGoal(this, (double)1.0F, true));
      this.standardPhase.addGoal(1, oneTwoJangoWrapper);
      Predicate<LivingEntity> canDash = (entity) -> !((OneTwoJangoAbility)oneTwoJangoWrapper.getAbility()).isCharging();
      this.getPhaseManager().setPhase(this.standardPhase);
      if (this.getChallengeInfo().isDifficultyStandard()) {
         this.standardPhase.addGoal(0, (new DashDodgeProjectilesGoal(this, 400.0F, 1.5F)).setCanUseTest(canDash));
         this.standardPhase.addGoal(0, (new DashDodgeTargetGoal(this, 450.0F, 2.0F)).setCanUseTest(canDash));
         this.standardPhase.addGoal(2, new ThrowChakramsGoal(this, 200));
         ((OneTwoJangoAbility)oneTwoJangoWrapper.getAbility()).setAffectSelf(true);
      } else {
         this.f_21345_.m_25352_(0, new HakiAbilityWrapperGoal(this, (AbilityCore)BusoshokuHakiInternalDestructionAbility.INSTANCE.get()));
         this.f_21345_.m_25352_(0, new HakiAbilityWrapperGoal(this, (AbilityCore)BusoshokuHakiImbuingAbility.INSTANCE.get()));
         this.standardPhase.addGoal(0, (new DashDodgeProjectilesGoal(this, 200.0F, 2.5F)).setCanUseTest(canDash));
         this.standardPhase.addGoal(0, (new DashDodgeTargetGoal(this, 250.0F, 2.5F)).setCanUseTest(canDash));
         this.standardPhase.addGoal(1, new DashAbilityWrapperGoal(this, (AbilityCore)SoruAbility.INSTANCE.get()));
         this.standardPhase.addGoal(1, new ReactiveRetreatAbilityWrapperGoal(this, (AbilityCore)GeppoAbility.INSTANCE.get()));
         this.standardPhase.addGoal(1, new ReactiveGuardAbilityWrapperGoal(this, (AbilityCore)KamieAbility.INSTANCE.get()));
         this.standardPhase.addGoal(2, new ThrowChakramsGoal(this, 100));
         this.tauntPhase.addGoal(0, new TauntTargetGoal(this));
         this.tauntPhase.addGoal(0, new DashDodgeProjectilesGoal(this, 20.0F, 4.0F));
         this.tauntPhase.addGoal(0, new ActiveGuardAbilityWrapperGoal(this, (AbilityCore)KamieAbility.INSTANCE.get()));
         this.tauntPhase.addGoal(1, (new ImprovedMeleeAttackGoal(this, (double)1.0F, true)).setMoveTowardsTarget(false));
         this.tauntPhase.addGoal(1, new DashDodgeTargetGoal(this, 60.0F, 4.0F));
      }

   }

   public static AttributeSupplier.Builder createAttributes() {
      return OPEntity.createAttributes().m_22268_(Attributes.f_22277_, (double)60.0F).m_22268_(Attributes.f_22279_, (double)0.31F).m_22268_(Attributes.f_22281_, (double)4.0F).m_22268_(Attributes.f_22276_, (double)150.0F).m_22268_(Attributes.f_22283_, (double)5.0F);
   }

   public void m_8119_() {
      super.m_8119_();
      if (!this.m_9236_().f_46443_ && this.m_5448_() != null && this.m_5448_().m_6084_() && this.isDifficultyHardOrAbove()) {
         if (this.standardPhase.isActive(this) && this.m_5448_().m_21023_((MobEffect)ModEffects.UNCONSCIOUS.get()) && GoalHelper.hasHealthAbovePercentage(this, (double)70.0F)) {
            this.getPhaseManager().setPhase(this.tauntPhase);
         } else if (this.tauntPhase.isActive(this) && !this.m_5448_().m_21023_((MobEffect)ModEffects.UNCONSCIOUS.get())) {
            this.getPhaseManager().setPhase(this.standardPhase);
         }
      }

   }
}
