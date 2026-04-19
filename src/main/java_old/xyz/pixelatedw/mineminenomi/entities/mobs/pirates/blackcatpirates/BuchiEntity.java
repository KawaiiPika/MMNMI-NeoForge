package xyz.pixelatedw.mineminenomi.entities.mobs.pirates.blackcatpirates;

import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;
import xyz.pixelatedw.mineminenomi.abilities.BellyFlopAbility;
import xyz.pixelatedw.mineminenomi.abilities.StealthFootAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.ChargedPunchAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.HakaiHoAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.JishinHoAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.TackleAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiFullBodyHardeningAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiInternalDestructionAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.TekkaiAbility;
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
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.CloseMeleeAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.DashAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.HakiAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.JumpAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ReactiveGuardAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPBossEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModArmors;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModFactions;
import xyz.pixelatedw.mineminenomi.init.ModFightingStyles;
import xyz.pixelatedw.mineminenomi.init.ModMobs;
import xyz.pixelatedw.mineminenomi.init.ModNPCGroups;
import xyz.pixelatedw.mineminenomi.init.ModRaces;

public class BuchiEntity extends OPBossEntity implements IGoalPartner<ShamEntity> {
   private static final UUID HARD_BELLY_STOMP_COOLDOWN_BONUS_UUID = UUID.fromString("28edc8a0-6668-4abc-ad2d-885ef10d6806");
   private static final UUID HARD_STEALTH_FOOT_COOLDOWN_BONUS_UUID = UUID.fromString("8b63c5b3-6b2f-4ef6-9397-96c3c5b6946d");
   private ShamEntity partner;

   public BuchiEntity(EntityType<BuchiEntity> type, Level world) {
      super(type, world);
   }

   public BuchiEntity(InProgressChallenge challenge) {
      super((EntityType)ModMobs.BUCHI.get(), challenge);
   }

   public void initBoss() {
      this.entityStats.setFaction((Faction)ModFactions.PIRATE.get());
      this.entityStats.setRace((Race)ModRaces.HUMAN.get());
      this.entityStats.setFightingStyle((FightingStyle)ModFightingStyles.BRAWLER.get());
      FactionsWorldData worldData = FactionsWorldData.get();
      worldData.addTemporaryCrewMember(ModNPCGroups.BLACK_CAT_PIRATES, this);
      this.m_21051_((Attribute)ModAttributes.TOUGHNESS.get()).m_22100_((double)2.0F);
      this.m_21051_((Attribute)ForgeMod.SWIM_SPEED.get()).m_22100_((double)1.0F);
      ItemStack capeStack = new ItemStack((ItemLike)ModArmors.FLUFFY_CAPE.get());
      capeStack.m_41698_("display").m_128405_("color", WyHelper.hexToRGB("#E69FEA").getRGB());
      this.m_8061_(EquipmentSlot.CHEST, capeStack);
      JumpAbilityWrapperGoal<BellyFlopAbility> bellyStompWrapper = new JumpAbilityWrapperGoal<BellyFlopAbility>(this, (AbilityCore)BellyFlopAbility.INSTANCE.get());
      DashAbilityWrapperGoal<TackleAbility> tackleWrapper = new DashAbilityWrapperGoal<TackleAbility>(this, (AbilityCore)TackleAbility.INSTANCE.get());
      MobsHelper.addBasicNPCGoals(this);
      this.f_21345_.m_25352_(0, new FindPartnerGoal(this, (EntityType)ModMobs.SHAM.get()));
      this.f_21345_.m_25352_(0, new ClimbOutOfHoleGoal(this));
      this.f_21345_.m_25352_(1, new ImprovedMeleeAttackGoal(this, (double)1.0F, true));
      this.f_21345_.m_25352_(2, new CloseMeleeAbilityWrapperGoal(this, (AbilityCore)ChargedPunchAbility.INSTANCE.get()));
      this.f_21345_.m_25352_(2, bellyStompWrapper);
      this.f_21345_.m_25352_(2, tackleWrapper);
      if (this.getChallengeInfo().isDifficultyStandard()) {
         this.entityStats.setDoriki((double)2000.0F);
         this.m_21051_((Attribute)ModAttributes.GCD.get()).m_22100_((double)40.0F);
      } else {
         this.entityStats.setDoriki((double)10000.0F);
         this.hakiCapability.setBusoshokuHakiExp(100.0F);
         this.hakiCapability.setKenbunshokuHakiExp(100.0F);
         this.m_21051_((Attribute)ModAttributes.GCD.get()).m_22100_((double)20.0F);
         this.m_21051_((Attribute)ModAttributes.PUNCH_DAMAGE.get()).m_22100_((double)4.0F);
         this.m_21051_(Attributes.f_22281_).m_22100_((double)4.0F);
         this.m_21051_((Attribute)ModAttributes.TOUGHNESS.get()).m_22100_((double)5.0F);
         this.m_21051_(Attributes.f_22276_).m_22100_((double)300.0F);
         this.m_21051_(Attributes.f_22284_).m_22100_((double)20.0F);
         this.m_21051_(Attributes.f_22285_).m_22100_((double)6.0F);
         this.m_21051_((Attribute)ForgeMod.SWIM_SPEED.get()).m_22100_((double)2.0F);
         CloseMeleeAbilityWrapperGoal<StealthFootAbility> stealthFootWrapper = new CloseMeleeAbilityWrapperGoal<StealthFootAbility>(this, (AbilityCore)StealthFootAbility.INSTANCE.get());
         ((StealthFootAbility)stealthFootWrapper.getAbility()).addCanUseCheck((entity, ability) -> (double)this.m_21223_() <= WyHelper.percentage((double)30.0F, (double)this.m_21233_()) ? Result.success() : Result.fail((Component)null));
         ((StealthFootAbility)stealthFootWrapper.getAbility()).getComponent((AbilityComponentKey)ModAbilityComponents.COOLDOWN.get()).ifPresent((comp) -> comp.getBonusManager().addBonus(HARD_STEALTH_FOOT_COOLDOWN_BONUS_UUID, "Hard Stealth Foot Cooldown Bonus", BonusOperation.ADD, 300.0F));
         ((BellyFlopAbility)bellyStompWrapper.getAbility()).getComponent((AbilityComponentKey)ModAbilityComponents.COOLDOWN.get()).ifPresent((comp) -> comp.getBonusManager().addBonus(HARD_BELLY_STOMP_COOLDOWN_BONUS_UUID, "Belly Stomp Cooldown Bonus", BonusOperation.ADD, -80.0F));
         DashDodgeTargetGoal dashDodgeGoal = new DashDodgeTargetGoal(this, 300.0F, 3.0F);
         dashDodgeGoal.setCanUseTest((entity) -> {
            if (((BellyFlopAbility)bellyStompWrapper.getAbility()).isContinuous()) {
               return false;
            } else {
               return !((TackleAbility)tackleWrapper.getAbility()).isContinuous();
            }
         });
         DashDodgeProjectilesGoal dashProjGoal = new DashDodgeProjectilesGoal(this, 250.0F, 3.0F);
         dashProjGoal.setCanUseTest((entity) -> {
            if (((BellyFlopAbility)bellyStompWrapper.getAbility()).isContinuous()) {
               return false;
            } else {
               return !((TackleAbility)tackleWrapper.getAbility()).isContinuous();
            }
         });
         this.f_21345_.m_25352_(0, new HakiAbilityWrapperGoal(this, (AbilityCore)BusoshokuHakiInternalDestructionAbility.INSTANCE.get()));
         this.f_21345_.m_25352_(0, new HakiAbilityWrapperGoal(this, (AbilityCore)BusoshokuHakiFullBodyHardeningAbility.INSTANCE.get()));
         this.f_21345_.m_25352_(0, dashProjGoal);
         this.f_21345_.m_25352_(0, dashDodgeGoal);
         this.f_21345_.m_25352_(2, new CloseMeleeAbilityWrapperGoal(this, (AbilityCore)JishinHoAbility.INSTANCE.get()));
         this.f_21345_.m_25352_(2, new CloseMeleeAbilityWrapperGoal(this, (AbilityCore)HakaiHoAbility.INSTANCE.get()));
         this.f_21345_.m_25352_(2, new ReactiveGuardAbilityWrapperGoal(this, (AbilityCore)TekkaiAbility.INSTANCE.get()));
         this.f_21345_.m_25352_(2, stealthFootWrapper);
      }

   }

   public static AttributeSupplier.Builder createAttributes() {
      return OPEntity.createAttributes().m_22268_(Attributes.f_22277_, (double)60.0F).m_22268_(Attributes.f_22279_, (double)0.3F).m_22268_(Attributes.f_22281_, (double)4.0F).m_22268_(Attributes.f_22276_, (double)150.0F).m_22268_(Attributes.f_22278_, 0.1).m_22268_(Attributes.f_22284_, (double)4.0F);
   }

   @Nullable
   public ShamEntity getPartner() {
      return this.partner;
   }

   public void setPartner(ShamEntity partner) {
      this.partner = partner;
   }
}
