package xyz.pixelatedw.mineminenomi.abilities.hitodaibutsu;

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
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityOverlay;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.MorphAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChangeStatsComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.SkinOverlayComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AttributeHelper;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModResources;

public class HitoDaibutsuPointAbility extends MorphAbility {
   public static final RegistryObject<AbilityCore<HitoDaibutsuPointAbility>> INSTANCE = ModRegistry.registerAbility("daibutsu_point", "Daibutsu Point", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Allows the user to transforms into a Golden Buddha statue, which focuses on strength and defense.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, HitoDaibutsuPointAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(10.0F), ContinuousComponent.getTooltip(), ChangeStatsComponent.getTooltip()).build("mineminenomi");
   });
   private static final AbilityAttributeModifier SPEED_MODIFIER;
   private static final AbilityAttributeModifier JUMP_BOOST_MODIFIER;
   private static final AbilityAttributeModifier STRENGTH_MODIFIER;
   private static final AbilityAttributeModifier ATTACK_SPEED_MODIFIER;
   private static final AbilityAttributeModifier ARMOR_MODIFIER;
   private static final AbilityAttributeModifier ARMOR_THOUGNESS_MODIFIER;
   private static final AbilityAttributeModifier KNOCKBACK_RESISTANCE_MODIFIER;
   private static final AbilityAttributeModifier REACH_MODIFIER;
   private static final AbilityAttributeModifier FALL_RESISTENCE_MODIFIER;
   private static final AbilityAttributeModifier HEALTH_BOOST_MODIFIER;
   private static final AbilityAttributeModifier STEP_HEIGHT_MODIFIER;
   private static final AbilityAttributeModifier TOUGHNESS_MODIFIER;
   private static final AbilityOverlay OVERLAY;
   private final SkinOverlayComponent skinOverlayComponent;

   public HitoDaibutsuPointAbility(AbilityCore<HitoDaibutsuPointAbility> core) {
      super(core);
      this.skinOverlayComponent = new SkinOverlayComponent(this, OVERLAY, new AbilityOverlay[0]);
      this.addComponents(new AbilityComponent[]{this.skinOverlayComponent});
      this.statsComponent.addAttributeModifier(Attributes.f_22279_, SPEED_MODIFIER);
      this.statsComponent.addAttributeModifier(ModAttributes.PUNCH_DAMAGE, STRENGTH_MODIFIER);
      this.statsComponent.addAttributeModifier(Attributes.f_22283_, ATTACK_SPEED_MODIFIER);
      this.statsComponent.addAttributeModifier(Attributes.f_22284_, ARMOR_MODIFIER);
      this.statsComponent.addAttributeModifier(Attributes.f_22285_, ARMOR_THOUGNESS_MODIFIER);
      this.statsComponent.addAttributeModifier(Attributes.f_22278_, KNOCKBACK_RESISTANCE_MODIFIER);
      this.statsComponent.addAttributeModifier(ModAttributes.JUMP_HEIGHT, JUMP_BOOST_MODIFIER);
      this.statsComponent.addAttributeModifier(ForgeMod.ENTITY_REACH, REACH_MODIFIER);
      this.statsComponent.addAttributeModifier(ForgeMod.BLOCK_REACH, REACH_MODIFIER);
      this.statsComponent.addAttributeModifier(Attributes.f_22276_, HEALTH_BOOST_MODIFIER);
      this.statsComponent.addAttributeModifier(ForgeMod.STEP_HEIGHT_ADDITION, STEP_HEIGHT_MODIFIER);
      this.statsComponent.addAttributeModifier(ModAttributes.FALL_RESISTANCE, FALL_RESISTENCE_MODIFIER);
      this.statsComponent.addAttributeModifier(ModAttributes.TOUGHNESS, TOUGHNESS_MODIFIER);
      this.continuousComponent.addStartEvent(100, this::startContinuityEvent);
      this.continuousComponent.addEndEvent(100, this::endContinuityEvent);
   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      this.skinOverlayComponent.showAll(entity);
   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      this.skinOverlayComponent.hideAll(entity);
   }

   public MorphInfo getTransformation() {
      return (MorphInfo)ModMorphs.DAIBUTSU.get();
   }

   static {
      SPEED_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_MOVEMENT_SPEED_UUID, INSTANCE, "Daibutsu Point Speed Modifier", (double)0.5F, Operation.MULTIPLY_BASE);
      JUMP_BOOST_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_JUMP_BOOST_UUID, INSTANCE, "Daibutsu Point Jump Modifier", (double)5.0F, Operation.MULTIPLY_BASE);
      STRENGTH_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_STRENGTH_UUID, INSTANCE, "Daibutsu Point Strength Modifier", (double)15.0F, Operation.ADDITION);
      ATTACK_SPEED_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_ATTACK_SPEED_UUID, INSTANCE, "Daibutsu Point Attack Speed Modifier", (double)-0.5F, Operation.MULTIPLY_BASE);
      ARMOR_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_ARMOR_UUID, INSTANCE, "Daibutsu Point Armor Modifier", (double)16.0F, Operation.ADDITION);
      ARMOR_THOUGNESS_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_ARMOR_TOUGHNESS_UUID, INSTANCE, "Daibutsu Point Armor Thougness Modifier", (double)10.0F, Operation.ADDITION);
      KNOCKBACK_RESISTANCE_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_KNOCKBACK_RESISTANCE_UUID, INSTANCE, "Daibutsu Point Knockback Resistance Modifier", (double)5.0F, Operation.ADDITION);
      REACH_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_ATTACK_REACH_UUID, INSTANCE, "Daibutsu Point Reach Modifier", (double)12.0F, Operation.ADDITION);
      FALL_RESISTENCE_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_FALL_RESISTANCE_UUID, INSTANCE, "Daibutsu Point Jump Resitance Modifier", (double)30.0F, Operation.ADDITION);
      HEALTH_BOOST_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_HEALTH_UUID, INSTANCE, "Daibutsu Point Health Modifier", (double)20.0F, Operation.ADDITION);
      STEP_HEIGHT_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_STEP_HEIGHT_UUID, INSTANCE, "Daibutsu Point Modifier", (double)1.5F, Operation.ADDITION);
      TOUGHNESS_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_TOUGHNESS_UUID, INSTANCE, "Daibutsu Point Toughness Modifier", (double)6.0F, Operation.ADDITION);
      OVERLAY = (new AbilityOverlay.Builder()).setTexture(ModResources.BUDDHA_COATING).setColor(-1426063361).build();
   }
}
