package xyz.pixelatedw.mineminenomi.abilities.gomu;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.RegistryObject;
import xyz.pixelatedw.mineminenomi.abilities.SourceImmunityAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class GomuImmunityAbility extends SourceImmunityAbility {
   public static final RegistryObject<AbilityCore<GomuImmunityAbility>> INSTANCE = ModRegistry.registerAbility("gomu_immunity", "Gomu Immunity", (id, name) -> (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, AbilityType.PASSIVE, GomuImmunityAbility::new)).setHidden().build("mineminenomi"));

   public GomuImmunityAbility(AbilityCore<? extends SourceImmunityAbility> ability) {
      super(ability, SourceImmunityAbility.LIGHTNING_IMMUNITY);
   }

   public boolean isDamageTaken(LivingEntity entity, DamageSource source, float amount) {
      return true;
   }
}
