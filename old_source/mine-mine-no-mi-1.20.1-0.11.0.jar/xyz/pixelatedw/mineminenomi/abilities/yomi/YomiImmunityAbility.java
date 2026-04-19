package xyz.pixelatedw.mineminenomi.abilities.yomi;

import java.util.function.Supplier;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.RegistryObject;
import xyz.pixelatedw.mineminenomi.abilities.EffectImmunityAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.DevilFruitCapability;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class YomiImmunityAbility extends EffectImmunityAbility {
   private static final EffectImmunityAbility.ImmunityInfo IMMUNITY_INFO;
   public static final RegistryObject<AbilityCore<YomiImmunityAbility>> INSTANCE;

   public YomiImmunityAbility(AbilityCore<? extends YomiImmunityAbility> core) {
      super(core, IMMUNITY_INFO);
   }

   private static boolean canUnlock(LivingEntity entity) {
      return (Boolean)DevilFruitCapability.get(entity).map((props) -> props.hasYomiPower()).orElse(false);
   }

   static {
      IMMUNITY_INFO = (new EffectImmunityAbility.ImmunityInfo()).addImmunityEffects(ModEffects.FROSTBITE, ModEffects.DIZZY, ModEffects.DRUNK, ModEffects.UNCONSCIOUS).addImmunityEffects(MobEffects.f_19602_, MobEffects.f_19605_, MobEffects.f_19612_, MobEffects.f_19614_).addResistanceEffect((Supplier)ModEffects.FROZEN, 2).addResistanceEffect((Supplier)ModEffects.PARALYSIS, 2);
      INSTANCE = ModRegistry.registerAbility("yomi_immunities", "Yomi Immunities", (id, name) -> (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, AbilityType.PASSIVE, YomiImmunityAbility::new)).setUnlockCheck(YomiImmunityAbility::canUnlock).setHidden().build("mineminenomi"));
   }
}
