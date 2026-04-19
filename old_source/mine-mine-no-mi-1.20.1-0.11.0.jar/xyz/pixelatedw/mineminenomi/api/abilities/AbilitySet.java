package xyz.pixelatedw.mineminenomi.api.abilities;

import java.util.HashSet;
import net.minecraftforge.registries.RegistryObject;

public class AbilitySet extends HashSet<RegistryObject<? extends AbilityCore<?>>> {
   private static final long serialVersionUID = -8128261614648267402L;

   @SafeVarargs
   public AbilitySet(RegistryObject<? extends AbilityCore<?>>... objects) {
      for(RegistryObject<? extends AbilityCore<? extends IAbility>> reg : objects) {
         super.add(reg);
      }

   }
}
