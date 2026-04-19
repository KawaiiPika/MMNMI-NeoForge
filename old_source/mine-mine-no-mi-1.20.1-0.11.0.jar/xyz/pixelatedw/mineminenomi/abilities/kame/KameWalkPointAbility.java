package xyz.pixelatedw.mineminenomi.abilities.kame;

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

public class KameWalkPointAbility extends MorphAbility {
   public static final RegistryObject<AbilityCore<KameWalkPointAbility>> INSTANCE = ModRegistry.registerAbility("kame_walk_point", "Kame Walk Point", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Transforms the user into a turtle hybrid, which focuses on defense.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, KameWalkPointAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(10.0F), ContinuousComponent.getTooltip(), ChangeStatsComponent.getTooltip()).build("mineminenomi");
   });
   private static final AbilityAttributeModifier SPEED_MODIFIER;
   private static final AbilityAttributeModifier ARMOR_MODIFIER;
   private static final AbilityAttributeModifier KNOCKBACK_RESISTANCE_MODIFIER;
   private static final AbilityAttributeModifier JUMP_BOOST_MODIFIER;
   private static final AbilityAttributeModifier WATER_SPEED_MODIFIER;
   private static final AbilityAttributeModifier TOUGHNESS_MODIFIER;

   public KameWalkPointAbility(AbilityCore<KameWalkPointAbility> core) {
      super(core);
      this.statsComponent.addAttributeModifier(Attributes.f_22279_, SPEED_MODIFIER);
      this.statsComponent.addAttributeModifier(Attributes.f_22284_, ARMOR_MODIFIER);
      this.statsComponent.addAttributeModifier(Attributes.f_22278_, KNOCKBACK_RESISTANCE_MODIFIER);
      this.statsComponent.addAttributeModifier(ModAttributes.JUMP_HEIGHT, JUMP_BOOST_MODIFIER);
      this.statsComponent.addAttributeModifier(ForgeMod.SWIM_SPEED, WATER_SPEED_MODIFIER);
      this.statsComponent.addAttributeModifier(ModAttributes.TOUGHNESS, TOUGHNESS_MODIFIER);
   }

   public MorphInfo getTransformation() {
      return (MorphInfo)ModMorphs.KAME_WALK.get();
   }

   static {
      SPEED_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_MOVEMENT_SPEED_UUID, INSTANCE, "Kame Walk Point Modifier", (double)-0.4F, Operation.MULTIPLY_BASE);
      ARMOR_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_ARMOR_UUID, INSTANCE, "Kame Walk Point Modifier", (double)15.0F, Operation.ADDITION);
      KNOCKBACK_RESISTANCE_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_KNOCKBACK_RESISTANCE_UUID, INSTANCE, "Kame Walk Point Knockback Resistance Modifier", (double)1.0F, Operation.ADDITION);
      JUMP_BOOST_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_JUMP_BOOST_UUID, INSTANCE, "Kame Walk Point Jump Modifier", (double)-0.25F, Operation.ADDITION);
      WATER_SPEED_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_SWIM_SPEED_UUID, INSTANCE, "Kame Walk Point Water Speed Modifier", (double)1.0F, Operation.ADDITION);
      TOUGHNESS_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_TOUGHNESS_UUID, INSTANCE, "Kame Walk Point Toughness Modifier", (double)10.0F, Operation.ADDITION);
   }
}
