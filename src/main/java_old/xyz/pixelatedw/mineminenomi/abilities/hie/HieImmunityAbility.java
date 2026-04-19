package xyz.pixelatedw.mineminenomi.abilities.hie;

import net.minecraftforge.registries.RegistryObject;
import xyz.pixelatedw.mineminenomi.abilities.EffectImmunityAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class HieImmunityAbility extends EffectImmunityAbility {
   private static final EffectImmunityAbility.ImmunityInfo IMMUNITY_INFO;
   public static final RegistryObject<AbilityCore<HieImmunityAbility>> INSTANCE;

   public HieImmunityAbility(AbilityCore<? extends HieImmunityAbility> core) {
      super(core, IMMUNITY_INFO);
   }

   static {
      IMMUNITY_INFO = (new EffectImmunityAbility.ImmunityInfo()).addImmunityEffects(ModEffects.FROZEN, ModEffects.FROSTBITE).addLogiaImmunities();
      INSTANCE = ModRegistry.registerAbility("hie_immunities", "Hie Immunities", (id, name) -> (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, AbilityType.PASSIVE, HieImmunityAbility::new)).setHidden().build("mineminenomi"));
   }
}
