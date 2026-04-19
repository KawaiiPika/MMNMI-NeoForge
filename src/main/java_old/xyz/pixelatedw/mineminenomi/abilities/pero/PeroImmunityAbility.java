package xyz.pixelatedw.mineminenomi.abilities.pero;

import net.minecraftforge.registries.RegistryObject;
import xyz.pixelatedw.mineminenomi.abilities.EffectImmunityAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class PeroImmunityAbility extends EffectImmunityAbility {
   private static final EffectImmunityAbility.ImmunityInfo IMMUNITY_INFO;
   public static final RegistryObject<AbilityCore<PeroImmunityAbility>> INSTANCE;

   public PeroImmunityAbility(AbilityCore<? extends PeroImmunityAbility> core) {
      super(core, IMMUNITY_INFO);
   }

   static {
      IMMUNITY_INFO = (new EffectImmunityAbility.ImmunityInfo()).addImmunityEffects(ModEffects.CANDY_STUCK);
      INSTANCE = ModRegistry.registerAbility("pero_immunities", "Pero Immunities", (id, name) -> (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, AbilityType.PASSIVE, PeroImmunityAbility::new)).setHidden().build("mineminenomi"));
   }
}
