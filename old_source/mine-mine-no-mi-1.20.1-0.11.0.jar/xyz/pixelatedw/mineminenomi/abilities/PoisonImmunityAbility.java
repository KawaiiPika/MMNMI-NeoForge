package xyz.pixelatedw.mineminenomi.abilities;

import net.minecraft.world.effect.MobEffects;
import net.minecraftforge.registries.RegistryObject;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class PoisonImmunityAbility extends EffectImmunityAbility {
   private static final EffectImmunityAbility.ImmunityInfo IMMUNITY_INFO;
   public static final RegistryObject<AbilityCore<PoisonImmunityAbility>> INSTANCE;

   public PoisonImmunityAbility(AbilityCore<? extends PoisonImmunityAbility> core) {
      super(core, IMMUNITY_INFO);
   }

   static {
      IMMUNITY_INFO = (new EffectImmunityAbility.ImmunityInfo()).addImmunityEffects(ModEffects.DOKU_POISON, () -> MobEffects.f_19614_);
      INSTANCE = ModRegistry.registerAbility("poison_immunities", "Poison Immunities", (id, name) -> (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, AbilityType.PASSIVE, PoisonImmunityAbility::new)).setHidden().build("mineminenomi"));
   }
}
