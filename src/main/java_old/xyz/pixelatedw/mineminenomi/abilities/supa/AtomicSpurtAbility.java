package xyz.pixelatedw.mineminenomi.abilities.supa;

import net.minecraft.network.chat.Component;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.SlidingAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class AtomicSpurtAbility extends SlidingAbility {
   public static final RegistryObject<AbilityCore<AtomicSpurtAbility>> INSTANCE = ModRegistry.registerAbility("atomic_spurt", "Atomic Spurt", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Allows the user to skate around using bladed feet", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, AtomicSpurtAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, ContinuousComponent.getTooltip()).build("mineminenomi");
   });

   public AtomicSpurtAbility(AbilityCore<AtomicSpurtAbility> core) {
      super(core);
   }

   public double getSpeedModifier() {
      return (double)1.5F;
   }

   public double getSlidingModifier() {
      return 0.85;
   }
}
