package xyz.pixelatedw.mineminenomi.abilities.saraaxolotl;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityAttributeModifier;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.MorphAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChangeStatsComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AttributeHelper;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class AxolotlHeavyPointAbility extends MorphAbility {
   public static final RegistryObject<AbilityCore<AxolotlHeavyPointAbility>> INSTANCE = ModRegistry.registerAbility("axolotl_heavy_point", "Axolotl Heavy Point", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Transforms the user into a half-axolotl hybrid, which focuses on strength.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, AxolotlHeavyPointAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(10.0F), ContinuousComponent.getTooltip(), ChangeStatsComponent.getTooltip()).build("mineminenomi");
   });
   private static final AbilityAttributeModifier SPEED_MODIFIER;
   private static final AbilityAttributeModifier ARMOR_MODIFIER;
   private static final AbilityAttributeModifier STRENGTH_MODIFIER;
   private static final AbilityAttributeModifier ATTACK_SPEED_MODIFIER;
   private static final AbilityAttributeModifier JUMP_BOOST;
   private static final AbilityAttributeModifier REACH_MODIFIER;
   private static final AbilityAttributeModifier REGEN_RATE_MODIFIER;
   private static final AbilityAttributeModifier KNOCKBACK_RESISTANCE;
   private static final AbilityAttributeModifier TOUGHNESS_MODIFIER;
   private static final AbilityAttributeModifier FALL_RESISTANCE_MODIFIER;

   public AxolotlHeavyPointAbility(AbilityCore<AxolotlHeavyPointAbility> core) {
      super(core);
      this.statsComponent.addAttributeModifier(Attributes.f_22279_, SPEED_MODIFIER);
      this.statsComponent.addAttributeModifier(Attributes.f_22284_, ARMOR_MODIFIER);
      this.statsComponent.addAttributeModifier(ModAttributes.PUNCH_DAMAGE, STRENGTH_MODIFIER);
      this.statsComponent.addAttributeModifier(Attributes.f_22283_, ATTACK_SPEED_MODIFIER);
      this.statsComponent.addAttributeModifier(ModAttributes.JUMP_HEIGHT, JUMP_BOOST);
      this.statsComponent.addAttributeModifier(ForgeMod.ENTITY_REACH, REACH_MODIFIER);
      this.statsComponent.addAttributeModifier(ForgeMod.BLOCK_REACH, REACH_MODIFIER);
      this.statsComponent.addAttributeModifier(ModAttributes.REGEN_RATE, REGEN_RATE_MODIFIER);
      this.statsComponent.addAttributeModifier(Attributes.f_22278_, KNOCKBACK_RESISTANCE);
      this.statsComponent.addAttributeModifier(ModAttributes.TOUGHNESS, TOUGHNESS_MODIFIER);
      this.statsComponent.addAttributeModifier(ModAttributes.FALL_RESISTANCE, FALL_RESISTANCE_MODIFIER);
   }

   public MorphInfo getTransformation() {
      return (MorphInfo)ModMorphs.AXOLOTL_HEAVY.get();
   }

   static {
      SPEED_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_MOVEMENT_SPEED_UUID, INSTANCE, "Axolotl Heavy Point Modifier", 0.3, Operation.MULTIPLY_BASE);
      ARMOR_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_ARMOR_UUID, INSTANCE, "Axolotl Heavy Point Modifier", (double)20.0F, Operation.ADDITION);
      STRENGTH_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_STRENGTH_UUID, INSTANCE, "Axolotl Heavy Point Modifier", (double)5.0F, Operation.ADDITION);
      ATTACK_SPEED_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_ATTACK_SPEED_UUID, INSTANCE, "Axolotl Heavy Point Modifier", (double)-0.6F, Operation.ADDITION);
      JUMP_BOOST = new AbilityAttributeModifier(AttributeHelper.MORPH_JUMP_BOOST_UUID, INSTANCE, "Axolotl Heavy Point Modifier", (double)1.25F, Operation.ADDITION);
      REACH_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_ATTACK_REACH_UUID, INSTANCE, "Axolotl Heavy Point Reach Modifier", 0.6, Operation.ADDITION);
      REGEN_RATE_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_REGEN_RATE_UUID, INSTANCE, "Axolotl Heavy Point Health Regeneration Modifier", (double)0.25F, Operation.ADDITION);
      KNOCKBACK_RESISTANCE = new AbilityAttributeModifier(AttributeHelper.MORPH_KNOCKBACK_RESISTANCE_UUID, INSTANCE, "Axolotl Heavy Point Knockback Resistance Modifier", (double)0.5F, Operation.ADDITION);
      TOUGHNESS_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_TOUGHNESS_UUID, INSTANCE, "Axolotl Heavy Point Toughness Modifier", (double)2.0F, Operation.ADDITION);
      FALL_RESISTANCE_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_FALL_RESISTANCE_UUID, INSTANCE, "Axolotl Heavy Point Fall Resistance Modifier", (double)1.25F, Operation.ADDITION);
   }
}
