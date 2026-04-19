package xyz.pixelatedw.mineminenomi.abilities;

import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.PassiveAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.IDamageSourceHandler;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class SlashDamageImmunityAbility extends PassiveAbility {
   public static final RegistryObject<AbilityCore<SlashDamageImmunityAbility>> INSTANCE = ModRegistry.registerAbility("slash_damage_immunity", "Slash Damage Immunity", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Makes the user immune to slash based attacks", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, AbilityType.PASSIVE, SlashDamageImmunityAbility::new)).addDescriptionLine(desc).build("mineminenomi");
   });
   private final DamageTakenComponent damageTakenComponent = (new DamageTakenComponent(this)).addOnAttackEvent(this::onDamageTaken);

   public SlashDamageImmunityAbility(AbilityCore<SlashDamageImmunityAbility> ability) {
      super(ability);
      this.addComponents(new AbilityComponent[]{this.damageTakenComponent});
   }

   private float onDamageTaken(LivingEntity entity, IAbility ability, DamageSource damageSource, float damage) {
      IDamageSourceHandler handler = (IDamageSourceHandler)damageSource;
      boolean isSlashDamage = handler.hasType(SourceType.SLASH);
      boolean isSwordDamage = false;
      Entity directEntity = damageSource.m_7640_();
      if (directEntity != null && directEntity.m_6084_() && directEntity instanceof LivingEntity livingAttacker) {
         isSwordDamage = ItemsHelper.isSword(livingAttacker.m_21205_());
      }

      return !isSlashDamage && !isSwordDamage ? damage : 0.0F;
   }
}
