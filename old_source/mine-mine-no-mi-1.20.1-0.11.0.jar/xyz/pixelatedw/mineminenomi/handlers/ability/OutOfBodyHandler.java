package xyz.pixelatedw.mineminenomi.handlers.ability;

import java.util.Optional;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.abilities.horo.MiniHollowAbility;
import xyz.pixelatedw.mineminenomi.abilities.horo.NegativeHollowAbility;
import xyz.pixelatedw.mineminenomi.abilities.horo.TokuHollowAbility;
import xyz.pixelatedw.mineminenomi.abilities.horo.YutaiRidatsuAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.OutOfBodyAbility;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;

public class OutOfBodyHandler {
   public static boolean canUseAbility(LivingEntity entity, IAbility usedAbility) {
      Optional<OutOfBodyAbility> ability = getOOBAbility(entity);
      if (ability.isPresent()) {
         if (usedAbility.equals(ability.get())) {
            return true;
         }

         if (ability.get() instanceof YutaiRidatsuAbility && (usedAbility instanceof MiniHollowAbility || usedAbility instanceof NegativeHollowAbility || usedAbility instanceof TokuHollowAbility)) {
            return true;
         }
      }

      return false;
   }

   public static boolean isOutOfBody(LivingEntity entity) {
      if (entity instanceof Player player) {
         if (player.m_7500_() || entity.m_5833_()) {
            return false;
         }
      }

      Optional<OutOfBodyAbility> ability = getOOBAbility(entity);
      if (ability != null && ability.isPresent() && !((OutOfBodyAbility)ability.get()).isPhysical()) {
         return true;
      } else {
         return false;
      }
   }

   public static Optional<OutOfBodyAbility> getOOBAbility(LivingEntity entity) {
      IAbilityData props = (IAbilityData)AbilityCapability.get(entity).orElse((Object)null);
      if (props == null) {
         return Optional.empty();
      } else {
         Optional<OutOfBodyAbility> optional = props.getEquippedAbilities((abl) -> {
            boolean var10000;
            if (abl instanceof OutOfBodyAbility oob) {
               if (oob.isContinuous()) {
                  var10000 = true;
                  return var10000;
               }
            }

            var10000 = false;
            return var10000;
         }).stream().map((abl) -> (OutOfBodyAbility)abl).findFirst();
         return optional;
      }
   }
}
