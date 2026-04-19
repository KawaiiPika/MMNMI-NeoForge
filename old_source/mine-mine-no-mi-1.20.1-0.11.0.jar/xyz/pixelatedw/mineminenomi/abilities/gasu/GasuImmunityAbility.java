package xyz.pixelatedw.mineminenomi.abilities.gasu;

import net.minecraft.world.effect.MobEffects;
import net.minecraftforge.registries.RegistryObject;
import xyz.pixelatedw.mineminenomi.abilities.EffectImmunityAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class GasuImmunityAbility extends EffectImmunityAbility {
   private static final EffectImmunityAbility.ImmunityInfo IMMUNITY_INFO;
   public static final RegistryObject<AbilityCore<GasuImmunityAbility>> INSTANCE;

   public GasuImmunityAbility(AbilityCore<? extends GasuImmunityAbility> core) {
      super(core, IMMUNITY_INFO);
   }

   static {
      IMMUNITY_INFO = (new EffectImmunityAbility.ImmunityInfo()).addImmunityEffects(() -> MobEffects.f_19614_, ModEffects.DOKU_POISON).addLogiaImmunities();
      INSTANCE = ModRegistry.registerAbility("gasu_immunities", "Gasu Immunities", (id, name) -> (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, AbilityType.PASSIVE, GasuImmunityAbility::new)).setHidden().build("mineminenomi"));
   }
}
