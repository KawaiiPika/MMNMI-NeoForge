package xyz.pixelatedw.mineminenomi.abilities;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.RegistryObject;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.PassiveAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class NoFallDamageAbility extends PassiveAbility {
   public static final RegistryObject<AbilityCore<NoFallDamageAbility>> INSTANCE = ModRegistry.registerAbility("no_fall_damage", "No Fall Damage", (id, name) -> (new AbilityCore.Builder(id, name, AbilityCategory.STYLE, AbilityType.PASSIVE, NoFallDamageAbility::new)).setHidden().build("mineminenomi"));
   protected final DamageTakenComponent damageTakenComponent = (new DamageTakenComponent(this)).addOnAttackEvent(this::onDamageTaken);

   public NoFallDamageAbility(AbilityCore<? extends NoFallDamageAbility> ability) {
      super(ability);
      this.addComponents(new AbilityComponent[]{this.damageTakenComponent});
   }

   private float onDamageTaken(LivingEntity entity, IAbility ability, DamageSource damageSource, float damage) {
      return damageSource.m_276093_(DamageTypes.f_268671_) ? 0.0F : damage;
   }
}
