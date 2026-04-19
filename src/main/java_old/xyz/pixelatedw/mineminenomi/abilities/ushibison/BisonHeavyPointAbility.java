package xyz.pixelatedw.mineminenomi.abilities.ushibison;

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

public class BisonHeavyPointAbility extends MorphAbility {
   public static final RegistryObject<AbilityCore<BisonHeavyPointAbility>> INSTANCE = ModRegistry.registerAbility("bison_heavy_point", "Bison Heavy Point", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Transforms the user into a half-bison hybrid, which focuses on strength.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, BisonHeavyPointAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(10.0F), ContinuousComponent.getTooltip(), ChangeStatsComponent.getTooltip()).build("mineminenomi");
   });
   private static final AbilityAttributeModifier SPEED_MODIFIER;
   private static final AbilityAttributeModifier HEALTH_MODIFIER;
   private static final AbilityAttributeModifier ARMOR_MODIFIER;
   private static final AbilityAttributeModifier STRENGTH_MODIFIER;
   private static final AbilityAttributeModifier ATTACK_SPEED_MODIFIER;
   private static final AbilityAttributeModifier JUMP_BOOST_MODIFIER;
   private static final AbilityAttributeModifier REACH_MODIFIER;
   private static final AbilityAttributeModifier TOUGHNESS_MODIFIER;

   public BisonHeavyPointAbility(AbilityCore<BisonHeavyPointAbility> core) {
      super(core);
      Predicate<LivingEntity> isMorphed = (entity) -> this.morphComponent.isMorphed();
      this.statsComponent.addAttributeModifier(Attributes.f_22279_, SPEED_MODIFIER, isMorphed);
      this.statsComponent.addAttributeModifier(Attributes.f_22276_, HEALTH_MODIFIER, isMorphed);
      this.statsComponent.addAttributeModifier(Attributes.f_22284_, ARMOR_MODIFIER, isMorphed);
      this.statsComponent.addAttributeModifier((Supplier)ModAttributes.PUNCH_DAMAGE, STRENGTH_MODIFIER, isMorphed);
      this.statsComponent.addAttributeModifier(Attributes.f_22283_, ATTACK_SPEED_MODIFIER, isMorphed);
      this.statsComponent.addAttributeModifier((Supplier)ModAttributes.JUMP_HEIGHT, JUMP_BOOST_MODIFIER, isMorphed);
      this.statsComponent.addAttributeModifier((Supplier)ForgeMod.BLOCK_REACH, REACH_MODIFIER, isMorphed);
      this.statsComponent.addAttributeModifier((Supplier)ForgeMod.ENTITY_REACH, REACH_MODIFIER, isMorphed);
      this.statsComponent.addAttributeModifier((Supplier)ModAttributes.TOUGHNESS, TOUGHNESS_MODIFIER, isMorphed);
   }

   public MorphInfo getTransformation() {
      return (MorphInfo)ModMorphs.BISON_HEAVY.get();
   }

   static {
      SPEED_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_MOVEMENT_SPEED_UUID, INSTANCE, "Bison Heavy Point Modifier", 0.3, Operation.MULTIPLY_BASE);
      HEALTH_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_HEALTH_UUID, INSTANCE, "Bison Heavy Point Modifier", (double)10.0F, Operation.ADDITION);
      ARMOR_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_ARMOR_UUID, INSTANCE, "Bison Heavy Point Modifier", (double)6.0F, Operation.ADDITION);
      STRENGTH_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_STRENGTH_UUID, INSTANCE, "Bison Heavy Point Modifier", (double)4.0F, Operation.ADDITION);
      ATTACK_SPEED_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_ATTACK_SPEED_UUID, INSTANCE, "Bison Heavy Point Modifier", (double)-0.8F, Operation.ADDITION);
      JUMP_BOOST_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_JUMP_BOOST_UUID, INSTANCE, "Bison Heavy Point Modifier", (double)1.0F, Operation.ADDITION);
      REACH_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_ATTACK_REACH_UUID, INSTANCE, "Bison Heavy Point Reach Modifier", (double)0.5F, Operation.ADDITION);
      TOUGHNESS_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_TOUGHNESS_UUID, INSTANCE, "Bison Heavy Point Toughness Modifier", (double)2.0F, Operation.ADDITION);
   }
}
