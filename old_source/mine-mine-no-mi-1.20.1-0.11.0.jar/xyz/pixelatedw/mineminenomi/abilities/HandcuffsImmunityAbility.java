package xyz.pixelatedw.mineminenomi.abilities;

import net.minecraftforge.registries.RegistryObject;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class HandcuffsImmunityAbility extends EffectImmunityAbility {
   private static final EffectImmunityAbility.ImmunityInfo IMMUNITY_INFO;
   public static final RegistryObject<AbilityCore<HandcuffsImmunityAbility>> INSTANCE;

   public HandcuffsImmunityAbility(AbilityCore<? extends HandcuffsImmunityAbility> core) {
      super(core, IMMUNITY_INFO);
   }

   static {
      IMMUNITY_INFO = (new EffectImmunityAbility.ImmunityInfo()).addImmunityEffects(ModEffects.HANDCUFFED);
      INSTANCE = ModRegistry.registerAbility("handcuff_immunities", "Handcuff Immunities", (id, name) -> (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, AbilityType.PASSIVE, HandcuffsImmunityAbility::new)).setHidden().build("mineminenomi"));
   }
}
