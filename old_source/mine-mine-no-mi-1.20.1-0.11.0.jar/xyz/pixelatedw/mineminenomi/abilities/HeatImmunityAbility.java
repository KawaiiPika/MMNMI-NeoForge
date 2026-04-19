package xyz.pixelatedw.mineminenomi.abilities;

import net.minecraftforge.registries.RegistryObject;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class HeatImmunityAbility extends EffectImmunityAbility {
   public static final RegistryObject<AbilityCore<HeatImmunityAbility>> INSTANCE = ModRegistry.registerAbility("heat_immunities", "Heat Immunities", (id, name) -> (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, AbilityType.PASSIVE, HeatImmunityAbility::new)).setHidden().build("mineminenomi"));

   public HeatImmunityAbility(AbilityCore<? extends HeatImmunityAbility> core) {
      super(core, EffectImmunityAbility.HEAT_RESISTANCES);
   }
}
