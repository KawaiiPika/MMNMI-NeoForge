package xyz.pixelatedw.mineminenomi.entities.mobs.marines;

import java.util.UUID;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;
import xyz.pixelatedw.mineminenomi.abilities.ChargedCleaveAbility;
import xyz.pixelatedw.mineminenomi.abilities.SlamAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.JishinHoAbility;
import xyz.pixelatedw.mineminenomi.abilities.brawler.TackleAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiFullBodyHardeningAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiInternalDestructionAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.RankyakuAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.ShiganAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.SoruAbility;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.TekkaiAbility;
import xyz.pixelatedw.mineminenomi.abilities.santoryu.OTatsumakiAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusOperation;
import xyz.pixelatedw.mineminenomi.api.challenges.InProgressChallenge;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.api.items.IMultiChannelColorItem;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.ClimbOutOfHoleGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.ImprovedMeleeAttackGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.SprintTowardsTargetGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.CloseMeleeAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.DashAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.HakiAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.JumpAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ProjectileAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.morgan.MorganMusterGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPBossEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModArmors;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModFactions;
import xyz.pixelatedw.mineminenomi.init.ModFightingStyles;
import xyz.pixelatedw.mineminenomi.init.ModMobs;
import xyz.pixelatedw.mineminenomi.init.ModRaces;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;
import xyz.pixelatedw.mineminenomi.items.weapons.ModSwordItem;

public class MorganEntity extends OPBossEntity {
   private static final UUID HARD_TATSUMAKI_COOLDOWN_BONUS_UUID = UUID.fromString("4bd574cc-c5a2-4e73-8230-763ccf4544d5");
   private boolean usedMuster;

   public MorganEntity(EntityType<? extends MorganEntity> type, Level world) {
      super(type, world);
   }

   public MorganEntity(InProgressChallenge challenge) {
      super((EntityType)ModMobs.MORGAN.get(), challenge);
   }

   public void initBoss() {
      this.entityStats.setFaction((Faction)ModFactions.MARINE.get());
      this.entityStats.setRace((Race)ModRaces.HUMAN.get());
      this.entityStats.setFightingStyle((FightingStyle)ModFightingStyles.BRAWLER.get());
      this.m_21051_((Attribute)ModAttributes.TOUGHNESS.get()).m_22100_((double)2.0F);
      this.m_21051_((Attribute)ForgeMod.STEP_HEIGHT_ADDITION.get()).m_22100_((double)1.0F);
      this.m_21051_((Attribute)ForgeMod.SWIM_SPEED.get()).m_22100_((double)2.5F);
      this.m_8061_(EquipmentSlot.HEAD, ((Item)ModArmors.IRON_JAW.get()).m_7968_());
      this.m_8061_(EquipmentSlot.MAINHAND, ((ModSwordItem)ModWeapons.AXE_HAND.get()).m_7968_());
      if (this.isDifficultyHardOrAbove()) {
         ItemStack marineCapeStack = new ItemStack((ItemLike)ModArmors.MARINE_CAPTAIN_CAPE.get());
         IMultiChannelColorItem.dyeArmor(marineCapeStack, 0, 33980);
         this.m_8061_(EquipmentSlot.CHEST, marineCapeStack);
      }

      MobsHelper.addBasicNPCGoals(this);
      CloseMeleeAbilityWrapperGoal<ChargedCleaveAbility> chargedCleaveWrapper = new CloseMeleeAbilityWrapperGoal<ChargedCleaveAbility>(this, (AbilityCore)ChargedCleaveAbility.INSTANCE.get());
      ((ChargedCleaveAbility)chargedCleaveWrapper.getAbility()).addCanUseCheck((entity, ability) -> {
         TackleAbility abl = (TackleAbility)AbilityCapability.getEquippedAbility(entity, (AbilityCore)TackleAbility.INSTANCE.get());
         return abl != null && abl.isContinuous() ? Result.fail((Component)null) : Result.success();
      });
      DashAbilityWrapperGoal<TackleAbility> tackleWrapper = new DashAbilityWrapperGoal<TackleAbility>(this, (AbilityCore)TackleAbility.INSTANCE.get());
      this.f_21345_.m_25352_(0, new ClimbOutOfHoleGoal(this));
      this.f_21345_.m_25352_(1, new ImprovedMeleeAttackGoal(this, (double)1.0F, true));
      this.f_21345_.m_25352_(1, new SprintTowardsTargetGoal(this));
      this.f_21345_.m_25352_(3, tackleWrapper);
      this.f_21345_.m_25352_(3, chargedCleaveWrapper);
      this.f_21345_.m_25352_(3, new JumpAbilityWrapperGoal(this, (AbilityCore)SlamAbility.INSTANCE.get()));
      if (this.getChallengeInfo().isDifficultyStandard()) {
         this.entityStats.setDoriki((double)500.0F);
         this.m_21051_((Attribute)ModAttributes.GCD.get()).m_22100_((double)40.0F);
         this.m_21051_((Attribute)ModAttributes.PUNCH_DAMAGE.get()).m_22100_((double)3.0F);
      } else {
         this.entityStats.setDoriki((double)10000.0F);
         this.hakiCapability.setBusoshokuHakiExp(100.0F);
         this.hakiCapability.setKenbunshokuHakiExp(100.0F);
         this.m_21051_((Attribute)ModAttributes.GCD.get()).m_22100_((double)20.0F);
         this.m_21051_((Attribute)ModAttributes.PUNCH_DAMAGE.get()).m_22100_((double)6.0F);
         this.m_21051_(Attributes.f_22281_).m_22100_((double)6.0F);
         this.m_21051_((Attribute)ModAttributes.TOUGHNESS.get()).m_22100_((double)4.0F);
         this.m_21051_(Attributes.f_22284_).m_22100_((double)10.0F);
         this.m_21051_(Attributes.f_22285_).m_22100_((double)8.0F);
         this.m_21051_(Attributes.f_22276_).m_22100_((double)250.0F);
         CloseMeleeAbilityWrapperGoal<OTatsumakiAbility> tatsumakiWrapper = new CloseMeleeAbilityWrapperGoal<OTatsumakiAbility>(this, (AbilityCore)OTatsumakiAbility.INSTANCE.get());
         ((OTatsumakiAbility)tatsumakiWrapper.getAbility()).getComponent((AbilityComponentKey)ModAbilityComponents.COOLDOWN.get()).ifPresent((comp) -> comp.getBonusManager().addBonus(HARD_TATSUMAKI_COOLDOWN_BONUS_UUID, "Tatsumaki Cooldown Bonus", BonusOperation.ADD, -80.0F));
         ((ChargedCleaveAbility)chargedCleaveWrapper.getAbility()).addCanUseCheck((entity, ability) -> {
            TekkaiAbility abl = (TekkaiAbility)AbilityCapability.getEquippedAbility(entity, (AbilityCore)TekkaiAbility.INSTANCE.get());
            return abl != null && abl.isContinuous() ? Result.fail((Component)null) : Result.success();
         });
         CloseMeleeAbilityWrapperGoal<ShiganAbility> shiganWrapper = new CloseMeleeAbilityWrapperGoal<ShiganAbility>(this, (AbilityCore)ShiganAbility.INSTANCE.get());
         ((ShiganAbility)shiganWrapper.getAbility()).addCanUseCheck((entity, ability) -> {
            ChargedCleaveAbility abl = (ChargedCleaveAbility)AbilityCapability.getEquippedAbility(entity, (AbilityCore)ChargedCleaveAbility.INSTANCE.get());
            return abl != null && abl.isCharging() ? Result.fail((Component)null) : Result.success();
         });
         this.f_21345_.m_25352_(0, new HakiAbilityWrapperGoal(this, (AbilityCore)BusoshokuHakiInternalDestructionAbility.INSTANCE.get()));
         this.f_21345_.m_25352_(0, new HakiAbilityWrapperGoal(this, (AbilityCore)BusoshokuHakiFullBodyHardeningAbility.INSTANCE.get()));
         this.f_21345_.m_25352_(0, new MorganMusterGoal(this));
         this.f_21345_.m_25352_(2, shiganWrapper);
         this.f_21345_.m_25352_(2, new DashAbilityWrapperGoal(this, (AbilityCore)SoruAbility.INSTANCE.get()));
         this.f_21345_.m_25352_(2, new ProjectileAbilityWrapperGoal(this, (AbilityCore)RankyakuAbility.INSTANCE.get()));
         this.f_21345_.m_25352_(3, tatsumakiWrapper);
         this.f_21345_.m_25352_(3, new CloseMeleeAbilityWrapperGoal(this, (AbilityCore)JishinHoAbility.INSTANCE.get()));
      }

   }

   public static AttributeSupplier.Builder createAttributes() {
      return OPEntity.createAttributes().m_22268_(Attributes.f_22277_, (double)200.0F).m_22268_(Attributes.f_22279_, (double)0.29F).m_22268_(Attributes.f_22281_, (double)6.0F).m_22268_(Attributes.f_22276_, (double)200.0F).m_22268_(Attributes.f_22284_, (double)10.0F).m_22268_(Attributes.f_22278_, 0.3).m_22268_(Attributes.f_22282_, (double)1.5F);
   }

   public void m_8119_() {
      super.m_8119_();
   }

   public void m_21011_(InteractionHand hand, boolean updateSelf) {
      super.m_21011_(hand, updateSelf);
   }
}
