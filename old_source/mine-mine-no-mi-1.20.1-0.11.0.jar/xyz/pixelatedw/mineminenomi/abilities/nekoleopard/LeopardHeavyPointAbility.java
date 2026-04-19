package xyz.pixelatedw.mineminenomi.abilities.nekoleopard;

import java.util.function.Predicate;
import java.util.function.Supplier;
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

public class LeopardHeavyPointAbility extends MorphAbility {
   public static final RegistryObject<AbilityCore<LeopardHeavyPointAbility>> INSTANCE = ModRegistry.registerAbility("leopard_heavy_point", "Leopard Heavy Point", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Transforms the user into a half-leopard hybrid, which focuses on speed and strength.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, LeopardHeavyPointAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(10.0F), ContinuousComponent.getTooltip(), ChangeStatsComponent.getTooltip()).build("mineminenomi");
   });
   private static final AbilityAttributeModifier SPEED_MODIFIER;
   private static final AbilityAttributeModifier ARMOR_MODIFIER;
   private static final AbilityAttributeModifier STRENGTH_MODIFIER;
   private static final AbilityAttributeModifier ATTACK_SPEED_MODIFIER;
   private static final AbilityAttributeModifier JUMP_BOOST_MODIFIER;
   private static final AbilityAttributeModifier REACH_MODIFIER;
   private static final AbilityAttributeModifier TOUGHNESS_MODIFIER;
   private static final AbilityAttributeModifier FALL_RESISTANCE_MODIFIER;

   public LeopardHeavyPointAbility(AbilityCore<LeopardHeavyPointAbility> core) {
      super(core);
      Predicate<LivingEntity> isActive = (entity) -> this.morphComponent.isMorphed();
      this.statsComponent.addAttributeModifier(Attributes.f_22279_, SPEED_MODIFIER, isActive);
      this.statsComponent.addAttributeModifier(Attributes.f_22284_, ARMOR_MODIFIER, isActive);
      this.statsComponent.addAttributeModifier((Supplier)ModAttributes.PUNCH_DAMAGE, STRENGTH_MODIFIER, isActive);
      this.statsComponent.addAttributeModifier(Attributes.f_22283_, ATTACK_SPEED_MODIFIER, isActive);
      this.statsComponent.addAttributeModifier((Supplier)ModAttributes.JUMP_HEIGHT, JUMP_BOOST_MODIFIER, isActive);
      this.statsComponent.addAttributeModifier((Supplier)ForgeMod.BLOCK_REACH, REACH_MODIFIER, isActive);
      this.statsComponent.addAttributeModifier((Supplier)ForgeMod.ENTITY_REACH, REACH_MODIFIER, isActive);
      this.statsComponent.addAttributeModifier((Supplier)ModAttributes.TOUGHNESS, TOUGHNESS_MODIFIER, isActive);
      this.statsComponent.addAttributeModifier((Supplier)ModAttributes.FALL_RESISTANCE, FALL_RESISTANCE_MODIFIER, isActive);
   }

   public MorphInfo getTransformation() {
      return (MorphInfo)ModMorphs.LEOPARD_HEAVY.get();
   }

   static {
      SPEED_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_MOVEMENT_SPEED_UUID, INSTANCE, "Leopard Heavy Point Modifier", (double)0.5F, Operation.MULTIPLY_BASE);
      ARMOR_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_ARMOR_UUID, INSTANCE, "Leopard Heavy Point Modifier", (double)4.0F, Operation.ADDITION);
      STRENGTH_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_STRENGTH_UUID, INSTANCE, "Leopard Heavy Point Modifier", (double)10.0F, Operation.ADDITION);
      ATTACK_SPEED_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_ATTACK_SPEED_UUID, INSTANCE, "Leopard Heavy Point Modifier", (double)-0.5F, Operation.ADDITION);
      JUMP_BOOST_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_JUMP_BOOST_UUID, INSTANCE, "Leopard Heavy Point Modifier", 1.9, Operation.ADDITION);
      REACH_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_ATTACK_REACH_UUID, INSTANCE, "Leopard Heavy Reach Modifier", (double)0.5F, Operation.ADDITION);
      TOUGHNESS_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_TOUGHNESS_UUID, INSTANCE, "Leopard Heavy Point Toughness Modifier", (double)2.0F, Operation.ADDITION);
      FALL_RESISTANCE_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_FALL_RESISTANCE_UUID, INSTANCE, "Leopard Heavy Point Fall Resistance Modifier", (double)3.25F, Operation.ADDITION);
   }
}
