package xyz.pixelatedw.mineminenomi.handlers.entity;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.FactionHelper;
import xyz.pixelatedw.mineminenomi.config.FactionsConfig;
import xyz.pixelatedw.mineminenomi.init.ModTags;

public class FactionHandler {
   public static boolean tryBlockingFriendlyFire(LivingEntity attacker, LivingEntity target, DamageSource source, float amount) {
      if (FactionsConfig.DISABLE_FRIENDLY_FIRE == null || !FactionsConfig.DISABLE_FRIENDLY_FIRE.get()) {
         return false;
      } else if (source.is(ModTags.DamageTypes.BYPASSES_FRIENDLY_PROTECTION)) {
         return false;
      } else {
         boolean sameGroup = FactionHelper.isFriendlyFactions(attacker, target);
         if (sameGroup) {
            return true;
         } else {
            return false;
         }
      }
   }
}
