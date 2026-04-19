package xyz.pixelatedw.mineminenomi.abilities.beta;

import net.minecraft.world.effect.MobEffects;
import net.minecraftforge.registries.RegistryObject;
import xyz.pixelatedw.mineminenomi.abilities.EffectImmunityAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class BetaImmunityAbility extends EffectImmunityAbility {
   public static final RegistryObject<AbilityCore<BetaImmunityAbility>> INSTANCE = ModRegistry.registerAbility("beta_immunities", "Beta Immunities", (id, name) -> (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, AbilityType.PASSIVE, BetaImmunityAbility::new)).setHidden().build("mineminenomi"));
   private static final EffectImmunityAbility.ImmunityInfo IMMUNITY_INFO;

   public BetaImmunityAbility(AbilityCore<? extends BetaImmunityAbility> core) {
      super(core, IMMUNITY_INFO);
   }

   static {
      IMMUNITY_INFO = (new EffectImmunityAbility.ImmunityInfo()).addImmunityEffects(ModEffects.STICKY).addImmunityEffects(MobEffects.f_19604_, MobEffects.f_19597_);
   }
}
