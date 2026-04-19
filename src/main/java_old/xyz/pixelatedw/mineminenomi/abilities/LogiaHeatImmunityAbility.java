package xyz.pixelatedw.mineminenomi.abilities;

import net.minecraftforge.registries.RegistryObject;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class LogiaHeatImmunityAbility extends EffectImmunityAbility {
   private static final EffectImmunityAbility.ImmunityInfo IMMUNITY_INFO;
   public static final RegistryObject<AbilityCore<LogiaHeatImmunityAbility>> INSTANCE;

   public LogiaHeatImmunityAbility(AbilityCore<? extends LogiaHeatImmunityAbility> core) {
      super(core, IMMUNITY_INFO);
   }

   static {
      IMMUNITY_INFO = HEAT_RESISTANCES.clone().addLogiaImmunities();
      INSTANCE = ModRegistry.registerAbility("heat_logia_immunities", "Heat Logia Immunities", (id, name) -> (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, AbilityType.PASSIVE, LogiaHeatImmunityAbility::new)).setHidden().build("mineminenomi"));
   }
}
