package xyz.pixelatedw.mineminenomi.abilities.gomu;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityAttributeModifier;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.MorphAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChangeStatsComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AttributeHelper;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.DevilFruitCapability;
import xyz.pixelatedw.mineminenomi.init.ModAbilityPools;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class GomuGomuNoGigantAbility extends MorphAbility {
   private static final int HOLD_TIME = 1200;
   private static final int MIN_COOLDOWN = 100;
   private static final int MAX_COOLDOWN = 1200;
   public static final RegistryObject<AbilityCore<GomuGomuNoGigantAbility>> INSTANCE = ModRegistry.registerAbility("gomu_gomu_no_gigant", "Gomu Gomu no Gigant", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Allows the user to increase their size to that of a giant.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, GomuGomuNoGigantAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(100.0F, 1200.0F), ContinuousComponent.getTooltip(1200.0F), ChangeStatsComponent.getTooltip()).setUnlockCheck(GomuGomuNoGigantAbility::canUnlock).build("mineminenomi");
   });
   private static final AbilityAttributeModifier SPEED_MODIFIER;
   private static final AbilityAttributeModifier JUMP_MODIFIER;
   private static final AbilityAttributeModifier ARMOR_MODIFIER;
   private static final AbilityAttributeModifier STRENGTH_MODIFIER;
   private static final AbilityAttributeModifier REACH_MODIFIER;
   private static final AbilityAttributeModifier STEP_HEIGHT;
   private static final AbilityAttributeModifier KNOCKBACK_RESISTANCE;
   private static final AbilityAttributeModifier FALL_RESISTANCE_MODIFIER;
   private static final AbilityAttributeModifier TOUGHNESS_MODIFIER;

   public GomuGomuNoGigantAbility(AbilityCore<GomuGomuNoGigantAbility> core) {
      super(core);
      this.statsComponent.addAttributeModifier(Attributes.f_22279_, SPEED_MODIFIER);
      this.statsComponent.addAttributeModifier(ModAttributes.JUMP_HEIGHT, JUMP_MODIFIER);
      this.statsComponent.addAttributeModifier(Attributes.f_22284_, ARMOR_MODIFIER);
      this.statsComponent.addAttributeModifier(ModAttributes.PUNCH_DAMAGE, STRENGTH_MODIFIER);
      this.statsComponent.addAttributeModifier(ForgeMod.ENTITY_REACH, REACH_MODIFIER);
      this.statsComponent.addAttributeModifier(ForgeMod.BLOCK_REACH, REACH_MODIFIER);
      this.statsComponent.addAttributeModifier(ForgeMod.STEP_HEIGHT_ADDITION, STEP_HEIGHT);
      this.statsComponent.addAttributeModifier(Attributes.f_22278_, KNOCKBACK_RESISTANCE);
      this.statsComponent.addAttributeModifier(ModAttributes.FALL_RESISTANCE, FALL_RESISTANCE_MODIFIER);
      this.statsComponent.addAttributeModifier(ModAttributes.TOUGHNESS, TOUGHNESS_MODIFIER);
      this.addCanUseCheck(GomuHelper::requiresGearFifth);
      this.addContinueUseCheck(GomuHelper::requiresGearFifth);
      this.poolComponent.removeFromPool(ModAbilityPools.MORPHS);
      this.continuousComponent.addEndEvent(100, this::endContinuityEvent);
   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      float cooldown = Math.max(100.0F, this.continuousComponent.getContinueTime());
      this.cooldownComponent.startCooldown(entity, cooldown);
   }

   public MorphInfo getTransformation() {
      return (MorphInfo)ModMorphs.GOMU_GIGANT.get();
   }

   public float getContinuityHoldTime() {
      return 1200.0F;
   }

   private static boolean canUnlock(LivingEntity user) {
      return (Boolean)DevilFruitCapability.get(user).map((props) -> props.hasAwakenedFruit()).orElse(false);
   }

   static {
      SPEED_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_MOVEMENT_SPEED_UUID, INSTANCE, "Gomu Gomu no Gigant Speed Modifier", (double)1.02F, Operation.MULTIPLY_BASE);
      JUMP_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_JUMP_BOOST_UUID, INSTANCE, "Gomu Gomu no Gigant Jump Modifier", (double)2.0F, Operation.ADDITION);
      ARMOR_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_ARMOR_UUID, INSTANCE, "Gomu Gomu no Gigant Armor Modifier", (double)5.0F, Operation.ADDITION);
      STRENGTH_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_STRENGTH_UUID, INSTANCE, "Gomu Gomu no Gigant Strength Modifier", (double)3.0F, Operation.ADDITION);
      REACH_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_ATTACK_REACH_UUID, INSTANCE, "Gomu Gomu no Gigant Reach Modifier", (double)5.0F, Operation.ADDITION);
      STEP_HEIGHT = new AbilityAttributeModifier(AttributeHelper.MORPH_STEP_HEIGHT_UUID, INSTANCE, "Gomu Gomu no Gigant Step Height Modifier", (double)1.5F, Operation.ADDITION);
      KNOCKBACK_RESISTANCE = new AbilityAttributeModifier(AttributeHelper.MORPH_KNOCKBACK_RESISTANCE_UUID, INSTANCE, "Gomu Gomu no Gigant Knockback Resistance Modifier", (double)1.0F, Operation.ADDITION);
      FALL_RESISTANCE_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_FALL_RESISTANCE_UUID, INSTANCE, "Gomu Gomu no Gigant Fall Resistance Modifier", (double)10.0F, Operation.ADDITION);
      TOUGHNESS_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_TOUGHNESS_UUID, INSTANCE, "Gomu Gomu no Gigant Toughness Modifier", (double)2.0F, Operation.ADDITION);
   }
}
