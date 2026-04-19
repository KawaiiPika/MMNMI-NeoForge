package xyz.pixelatedw.mineminenomi.abilities.gura;

import java.util.function.Supplier;
import net.minecraftforge.registries.RegistryObject;
import xyz.pixelatedw.mineminenomi.abilities.EffectImmunityAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class GuraImmunityAbility extends EffectImmunityAbility {
   private static final EffectImmunityAbility.ImmunityInfo IMMUNITY_INFO;
   public static final RegistryObject<AbilityCore<GuraImmunityAbility>> INSTANCE;

   public GuraImmunityAbility(AbilityCore<? extends GuraImmunityAbility> core) {
      super(core, IMMUNITY_INFO);
   }

   static {
      IMMUNITY_INFO = (new EffectImmunityAbility.ImmunityInfo()).addResistanceEffect((Supplier)ModEffects.CANDLE_LOCK, 5).addResistanceEffect((Supplier)ModEffects.CANDY_STUCK, 8).addResistanceEffect((Supplier)ModEffects.FROZEN, 8);
      INSTANCE = ModRegistry.registerAbility("gura_immunities", "Gura Immunities", (id, name) -> (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, AbilityType.PASSIVE, GuraImmunityAbility::new)).setHidden().build("mineminenomi"));
   }
}
