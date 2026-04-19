package xyz.pixelatedw.mineminenomi.integrations.clothconfig;

import java.util.function.Function;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;

public class AbilityDropdownEntry {
   public static final Function<String, AbilityCore<?>> ABILITY_FUNCTION = (str) -> {
      try {
         ResourceLocation identifier = ResourceLocation.parse(str);
         return AbilityCore.get(identifier);
      } catch (Exception var2) {
         return null;
      }
   };
}
