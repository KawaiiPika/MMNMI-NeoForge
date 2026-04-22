package xyz.pixelatedw.mineminenomi.handlers.entity;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.WyDebug;
import xyz.pixelatedw.mineminenomi.commands.FGCommand;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.init.ModEntityPredicates;
import xyz.pixelatedw.mineminenomi.init.ModTags;

public class FactionHandler {
   public static boolean tryBlockingFriendlyFire(LivingEntity attacker, LivingEntity target, DamageSource source, float amount) {
      if (!ServerConfig.isFriendlyDamageDisabled()) {
         return false;
      } else if (source.m_269533_(ModTags.DamageTypes.BYPASSES_FRIENDLY_PROTECTION)) {
         return false;
      } else {
         boolean sameGroup = ModEntityPredicates.getFriendlyFactions(attacker).test(target);
         if (sameGroup) {
            if (FGCommand.SHOW_DEBUG_FRIENDLY) {
               WyDebug.debug(target.m_5446_().getString() + " is in the same group as the attacker!");
            }

            return true;
         } else {
            return false;
         }
      }
   }
}
