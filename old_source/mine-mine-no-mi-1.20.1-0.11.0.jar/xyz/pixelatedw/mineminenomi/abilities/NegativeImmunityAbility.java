package xyz.pixelatedw.mineminenomi.abilities;

import net.minecraftforge.registries.RegistryObject;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class NegativeImmunityAbility extends EffectImmunityAbility {
   private static final EffectImmunityAbility.ImmunityInfo IMMUNITY_INFO;
   public static final RegistryObject<AbilityCore<NegativeImmunityAbility>> INSTANCE;

   public NegativeImmunityAbility(AbilityCore<? extends NegativeImmunityAbility> core) {
      super(core, IMMUNITY_INFO);
   }

   static {
      IMMUNITY_INFO = (new EffectImmunityAbility.ImmunityInfo()).addImmunityEffects(ModEffects.NEGATIVE);
      INSTANCE = ModRegistry.registerAbility("negative_immunities", "Negative Immunities", (id, name) -> (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, AbilityType.PASSIVE, NegativeImmunityAbility::new)).setHidden().build("mineminenomi"));
   }
}
