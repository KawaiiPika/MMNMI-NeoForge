package xyz.pixelatedw.mineminenomi.abilities.moku;

import net.minecraftforge.registries.RegistryObject;
import xyz.pixelatedw.mineminenomi.abilities.EffectImmunityAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class MokuImmunityAbility extends EffectImmunityAbility {
   private static final EffectImmunityAbility.ImmunityInfo IMMUNITY_INFO;
   public static final RegistryObject<AbilityCore<MokuImmunityAbility>> INSTANCE;

   public MokuImmunityAbility(AbilityCore<? extends MokuImmunityAbility> core) {
      super(core, IMMUNITY_INFO);
   }

   static {
      IMMUNITY_INFO = (new EffectImmunityAbility.ImmunityInfo()).addLogiaImmunities().addImmunityEffects(ModEffects.SMOKE);
      INSTANCE = ModRegistry.registerAbility("moku_immunities", "Moku Immunities", (id, name) -> (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, AbilityType.PASSIVE, MokuImmunityAbility::new)).setHidden().build("mineminenomi"));
   }
}
