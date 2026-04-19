package xyz.pixelatedw.mineminenomi.handlers.ability;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.abilities.KnockdownAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.init.ModEffects;

public class KnockdownHandler {
   public static boolean tryKnockdown(LivingEntity entity, LivingEntity target, float damage) {
      if (entity != null && entity.m_6084_() && target != null && target.m_6084_()) {
         boolean isFatalHit = target.m_21223_() - damage <= 0.0F;
         if (!isFatalHit) {
            return false;
         } else {
            boolean isActive = (Boolean)AbilityCapability.get(entity).map((props) -> (KnockdownAbility)props.getPassiveAbility((AbilityCore)KnockdownAbility.INSTANCE.get())).map((abl) -> !abl.isAbilityPaused() && !abl.isAbilityDisabled()).orElse(false);
            if (!isActive) {
               return false;
            } else {
               target.m_7292_(new MobEffectInstance((MobEffect)ModEffects.UNCONSCIOUS.get(), 1800, 1));
               target.m_21153_(5.0F);
               target.m_20095_();
               return true;
            }
         }
      } else {
         return false;
      }
   }
}
