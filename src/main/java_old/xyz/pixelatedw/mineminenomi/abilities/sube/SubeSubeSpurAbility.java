package xyz.pixelatedw.mineminenomi.abilities.sube;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityAttributeModifier;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.SlidingAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AttributeHelper;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class SubeSubeSpurAbility extends SlidingAbility {
   public static final RegistryObject<AbilityCore<SubeSubeSpurAbility>> INSTANCE = ModRegistry.registerAbility("sube_sube_spur", "Sube Sube Spur", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Allows the user to slide on the ground around using their feet.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, SubeSubeSpurAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, ContinuousComponent.getTooltip()).build("mineminenomi");
   });
   private static final AbilityAttributeModifier STEP_HEIGHT_MODIFIER;

   public SubeSubeSpurAbility(AbilityCore<SubeSubeSpurAbility> core) {
      super(core);
      this.changeStatsComponent.addAttributeModifier(ForgeMod.STEP_HEIGHT_ADDITION, STEP_HEIGHT_MODIFIER);
   }

   public double getSpeedModifier() {
      return (double)2.5F;
   }

   public double getSlidingModifier() {
      return 0.99;
   }

   static {
      STEP_HEIGHT_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_STEP_HEIGHT_UUID, INSTANCE, "Sube Sube Spur Step Height Modifier", (double)1.0F, Operation.ADDITION);
   }
}
