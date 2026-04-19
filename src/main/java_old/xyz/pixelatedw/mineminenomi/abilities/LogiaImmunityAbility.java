package xyz.pixelatedw.mineminenomi.abilities;

import net.minecraftforge.registries.RegistryObject;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class LogiaImmunityAbility extends EffectImmunityAbility {
   private static final EffectImmunityAbility.ImmunityInfo IMMUNITY_INFO = (new EffectImmunityAbility.ImmunityInfo()).addLogiaImmunities();
   public static final RegistryObject<AbilityCore<LogiaImmunityAbility>> INSTANCE = ModRegistry.registerAbility("logia_immunities", "Logia Immunities", (id, name) -> (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, AbilityType.PASSIVE, LogiaImmunityAbility::new)).setHidden().build("mineminenomi"));

   public LogiaImmunityAbility(AbilityCore<? extends LogiaImmunityAbility> core) {
      super(core, IMMUNITY_INFO);
   }
}
