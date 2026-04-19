package xyz.pixelatedw.mineminenomi.abilities.gomu;

import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.abilities.NoFallDamageAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class BouncyAbility extends NoFallDamageAbility {
   public static final RegistryObject<AbilityCore<BouncyAbility>> INSTANCE = ModRegistry.registerAbility("bouncy", "Bouncy", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Makes the user bounce upon landing", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, AbilityType.PASSIVE, BouncyAbility::new)).addDescriptionLine(desc).build("mineminenomi");
   });
   private boolean touchedGround = true;
   private float bounceValue = 0.0F;

   public BouncyAbility(AbilityCore<BouncyAbility> ability) {
      super(ability);
      super.damageTakenComponent.addOnAttackEvent(this::onDamageTaken);
      super.addDuringPassiveEvent(this::duringPassiveEvent);
   }

   protected void duringPassiveEvent(LivingEntity entity) {
      if (entity.f_19789_ > 12.0F || !this.touchedGround) {
         this.touchedGround = false;
         if (entity.f_19789_ != 0.0F) {
            this.bounceValue = entity.f_19789_;
         }

         if (entity.m_20096_() && this.bounceValue / 30.0F > 0.0F) {
            this.touchedGround = true;
            AbilityHelper.setDeltaMovement(entity, entity.m_20184_().f_82479_, (double)this.bounceValue / (double)30.0F, entity.m_20184_().f_82481_);
         }
      }

   }

   private float onDamageTaken(LivingEntity entity, IAbility ability, DamageSource damageSource, float damage) {
      return damageSource.m_276093_(DamageTypes.f_268576_) ? 0.0F : damage;
   }
}
