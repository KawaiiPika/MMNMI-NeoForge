package xyz.pixelatedw.mineminenomi.entities.mobs.marines;

import java.util.UUID;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusOperation;
import xyz.pixelatedw.mineminenomi.api.challenges.InProgressChallenge;
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
import xyz.pixelatedw.mineminenomi.init.ModMobs;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;

public class MorganEntity extends OPBossEntity {
   private static final UUID HARD_TATSUMAKI_COOLDOWN_BONUS_UUID = UUID.fromString("4bd574cc-c5a2-4e73-8230-763ccf4544d5");

   public MorganEntity(EntityType<? extends MorganEntity> type, Level world) {
      super(type, world);
   }

   public MorganEntity(InProgressChallenge challenge) {
      super(ModMobs.MORGAN.get(), challenge);
   }

   @Override
   public void initBoss() {
      this.getStats().getIdentity().setFaction("marine");
      this.getStats().getIdentity().setRace("human");
      this.getStats().getIdentity().setFightingStyle("brawler");
      this.getAttribute(ModAttributes.TOUGHNESS.get()).setBaseValue(2.0D);
      // Removed ForgeMod dependencies
      this.setItemSlot(EquipmentSlot.HEAD, ModArmors.IRON_JAW.get().getDefaultInstance());
      this.setItemSlot(EquipmentSlot.MAINHAND, ModWeapons.AXE_HAND.get().getDefaultInstance());

      if (this.isDifficultyHardOrAbove()) {
         ItemStack marineCapeStack = ModArmors.MARINE_CAPTAIN_CAPE.get().getDefaultInstance();
         IMultiChannelColorItem.dyeArmor(marineCapeStack, 0, 33980);
         this.setItemSlot(EquipmentSlot.CHEST, marineCapeStack);
      }

      MobsHelper.addBasicNPCGoals(this);
      CloseMeleeAbilityWrapperGoal<ChargedCleaveAbility> chargedCleaveWrapper = new CloseMeleeAbilityWrapperGoal<>(this, ChargedCleaveAbility.INSTANCE.get());
      ((ChargedCleaveAbility)chargedCleaveWrapper.getAbility()).addCanUseCheck((entity, ability) -> {
         TackleAbility abl = (TackleAbility)AbilityCapability.getEquippedAbility(entity, TackleAbility.INSTANCE.get());
         return abl != null && abl.isContinuous() ? Result.fail((Component)null) : Result.success();
      });
      DashAbilityWrapperGoal<TackleAbility> tackleWrapper = new DashAbilityWrapperGoal<>(this, TackleAbility.INSTANCE.get());
      this.goalSelector.addGoal(0, new ClimbOutOfHoleGoal(this));
      this.goalSelector.addGoal(1, new ImprovedMeleeAttackGoal(this, 1.0D, true));
      this.goalSelector.addGoal(1, new SprintTowardsTargetGoal(this));
      this.goalSelector.addGoal(3, tackleWrapper);
      this.goalSelector.addGoal(3, chargedCleaveWrapper);
      this.goalSelector.addGoal(3, new JumpAbilityWrapperGoal<>(this, SlamAbility.INSTANCE.get()));

      if (this.getChallengeInfo().isDifficultyStandard()) {
         this.getStats().setDoriki(500.0F);
         this.getAttribute(ModAttributes.GCD.get()).setBaseValue(40.0D);
         this.getAttribute(ModAttributes.PUNCH_DAMAGE.get()).setBaseValue(3.0D);
      } else {
         this.getStats().setDoriki(10000.0F);
         this.getAttribute(ModAttributes.GCD.get()).setBaseValue(20.0D);
         this.getAttribute(ModAttributes.PUNCH_DAMAGE.get()).setBaseValue(6.0D);
         this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(6.0D);
         this.getAttribute(ModAttributes.TOUGHNESS.get()).setBaseValue(4.0D);
         this.getAttribute(Attributes.ARMOR).setBaseValue(10.0D);
         this.getAttribute(Attributes.ARMOR_TOUGHNESS).setBaseValue(8.0D);
         this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(250.0D);

         CloseMeleeAbilityWrapperGoal<OTatsumakiAbility> tatsumakiWrapper = new CloseMeleeAbilityWrapperGoal<>(this, OTatsumakiAbility.INSTANCE.get());
         ((OTatsumakiAbility)tatsumakiWrapper.getAbility()).getComponent(ModAbilityComponents.COOLDOWN.get()).ifPresent((comp) -> comp.getBonusManager().addBonus(HARD_TATSUMAKI_COOLDOWN_BONUS_UUID, "Tatsumaki Cooldown Bonus", BonusOperation.ADD, -80.0F));
         ((ChargedCleaveAbility)chargedCleaveWrapper.getAbility()).addCanUseCheck((entity, ability) -> {
            TekkaiAbility abl = (TekkaiAbility)AbilityCapability.getEquippedAbility(entity, TekkaiAbility.INSTANCE.get());
            return abl != null && abl.isContinuous() ? Result.fail((Component)null) : Result.success();
         });
         CloseMeleeAbilityWrapperGoal<ShiganAbility> shiganWrapper = new CloseMeleeAbilityWrapperGoal<>(this, ShiganAbility.INSTANCE.get());
         ((ShiganAbility)shiganWrapper.getAbility()).addCanUseCheck((entity, ability) -> {
            ChargedCleaveAbility abl = (ChargedCleaveAbility)AbilityCapability.getEquippedAbility(entity, ChargedCleaveAbility.INSTANCE.get());
            return abl != null && abl.isCharging() ? Result.fail((Component)null) : Result.success();
         });

         this.goalSelector.addGoal(0, new HakiAbilityWrapperGoal<>(this, BusoshokuHakiInternalDestructionAbility.INSTANCE.get()));
         this.goalSelector.addGoal(0, new HakiAbilityWrapperGoal<>(this, BusoshokuHakiFullBodyHardeningAbility.INSTANCE.get()));
         this.goalSelector.addGoal(0, new MorganMusterGoal(this));
         this.goalSelector.addGoal(2, shiganWrapper);
         this.goalSelector.addGoal(2, new DashAbilityWrapperGoal<>(this, SoruAbility.INSTANCE.get()));
         this.goalSelector.addGoal(2, new ProjectileAbilityWrapperGoal<>(this, RankyakuAbility.INSTANCE.get()));
         this.goalSelector.addGoal(3, tatsumakiWrapper);
         this.goalSelector.addGoal(3, new CloseMeleeAbilityWrapperGoal<>(this, JishinHoAbility.INSTANCE.get()));
      }
   }

   public static AttributeSupplier.Builder createAttributes() {
      return OPEntity.createAttributes()
         .add(Attributes.MAX_HEALTH, 200.0D)
         .add(Attributes.MOVEMENT_SPEED, 0.29D)
         .add(Attributes.ATTACK_DAMAGE, 6.0D)
         .add(Attributes.FOLLOW_RANGE, 200.0D)
         .add(Attributes.ARMOR, 10.0D)
         .add(Attributes.ATTACK_SPEED, 0.3D)
         .add(Attributes.KNOCKBACK_RESISTANCE, 1.5D);
   }

   @Override
   public void tick() {
      super.tick();
   }

   @Override
   public void swing(InteractionHand hand, boolean updateSelf) {
      super.swing(hand, updateSelf);
   }
}
