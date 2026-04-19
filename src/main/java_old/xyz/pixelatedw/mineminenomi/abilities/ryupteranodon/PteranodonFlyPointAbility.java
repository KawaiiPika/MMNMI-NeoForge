package xyz.pixelatedw.mineminenomi.abilities.ryupteranodon;

import java.util.function.Predicate;
import java.util.function.Supplier;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.abilities.PropelledFlightAbility;
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
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class PteranodonFlyPointAbility extends MorphAbility {
   public static final RegistryObject<AbilityCore<PteranodonFlyPointAbility>> INSTANCE = ModRegistry.registerAbility("pteranodon_fly_point", "Pteranodon Fly Point", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Transforms the user into an ancient pteranodon.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, PteranodonFlyPointAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(10.0F), ContinuousComponent.getTooltip(), ChangeStatsComponent.getTooltip()).build("mineminenomi");
   });
   private static final AbilityAttributeModifier SPEED_MODIFIER;
   private static final AbilityAttributeModifier ARMOR_MODIFIER;
   private static final AbilityAttributeModifier STRENGTH_MODIFIER;
   private static final AbilityAttributeModifier TOUGHNESS_MODIFIER;

   public PteranodonFlyPointAbility(AbilityCore<PteranodonFlyPointAbility> core) {
      super(core);
      Predicate<LivingEntity> isMorphed = (entity) -> super.morphComponent.isMorphed();
      super.statsComponent.addAttributeModifier(Attributes.f_22283_, SPEED_MODIFIER, isMorphed);
      super.statsComponent.addAttributeModifier(Attributes.f_22284_, ARMOR_MODIFIER, isMorphed);
      super.statsComponent.addAttributeModifier((Supplier)ModAttributes.PUNCH_DAMAGE, STRENGTH_MODIFIER, isMorphed);
      super.statsComponent.addAttributeModifier((Supplier)ModAttributes.TOUGHNESS, TOUGHNESS_MODIFIER, isMorphed);
      super.continuousComponent.addStartEvent(this::onContinuityStart).addEndEvent(this::onContinuityEnd);
   }

   private void onContinuityStart(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         IAbilityData props = (IAbilityData)AbilityCapability.get(entity).orElse((Object)null);
         if (props != null) {
            PropelledFlightAbility flightAbility = (PropelledFlightAbility)props.getPassiveAbility((AbilityCore)PteranodonFlightAbility.INSTANCE.get());
            if (flightAbility != null && !flightAbility.isPaused()) {
               flightAbility.enableFlight((Player)entity);
            }

         }
      }
   }

   private void onContinuityEnd(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         IAbilityData props = (IAbilityData)AbilityCapability.get(entity).orElse((Object)null);
         if (props != null) {
            PropelledFlightAbility flightAbility = (PropelledFlightAbility)props.getPassiveAbility((AbilityCore)PteranodonFlightAbility.INSTANCE.get());
            if (flightAbility != null) {
               flightAbility.disableFlight((Player)entity);
            }

         }
      }
   }

   public MorphInfo getTransformation() {
      return (MorphInfo)ModMorphs.PTERA_FLY.get();
   }

   static {
      SPEED_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_MOVEMENT_SPEED_UUID, INSTANCE, "Pteranodon Fly Point Speed Modifier", (double)1.0F, Operation.ADDITION);
      ARMOR_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_ARMOR_UUID, INSTANCE, "Pteranodon Fly Point Armor Modifier", (double)2.0F, Operation.ADDITION);
      STRENGTH_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_STRENGTH_UUID, INSTANCE, "Pteranodon Fly Point Strength Modifier", (double)12.0F, Operation.ADDITION);
      TOUGHNESS_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_TOUGHNESS_UUID, INSTANCE, "Pteranodon Fly Point Toughness Modifier", (double)2.0F, Operation.ADDITION);
   }
}
