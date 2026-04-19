package xyz.pixelatedw.mineminenomi.api;

import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;

public class ComponentNotRegisteredException extends RuntimeException {
   public ComponentNotRegisteredException(AbilityComponent<?> comp) {
      String var10001 = String.valueOf(comp.getKey().getId());
      super("Component " + var10001 + " is not registered for " + String.valueOf(comp.getAbility().getCore()));
   }

   public ComponentNotRegisteredException(AbilityComponentKey<?> key) {
      super("Component " + String.valueOf(key.getId()) + " is not registered");
   }
}
