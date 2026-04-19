package xyz.pixelatedw.mineminenomi.effects;

import java.util.function.Predicate;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.abilities.jiki.GenocideRaidAbility;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.jiki.GenocideRaidEffectEntity;
import xyz.pixelatedw.mineminenomi.init.ModDamageSources;

public class GenocideRaidEffect extends DamageOverTimeEffect {
   public GenocideRaidEffect() {
      super(GenocideRaidEffect::createDamageSource, 10.0F, 20);
   }

   private static DamageSource createDamageSource(LivingEntity entity) {
      LivingEntity attacker = null;

      for(GenocideRaidEffectEntity effectEntity : WyHelper.getNearbyEntities(entity.m_20182_(), entity.m_9236_(), (double)1.0F, (Predicate)null, GenocideRaidEffectEntity.class)) {
         LivingEntity owner = effectEntity.getOwner();
         if (owner != null && owner.m_6084_()) {
            attacker = owner;
            break;
         }
      }

      return ModDamageSources.getInstance().ability(attacker, (AbilityCore)GenocideRaidAbility.INSTANCE.get());
   }

   public boolean shouldUpdateClient() {
      return true;
   }
}
