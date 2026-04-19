package xyz.pixelatedw.mineminenomi.api.entities.revenge;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;

public class TargetFlyStallingRevengeCheck extends BaseRevengeCheck implements ITickRevengeCheck {
   private final int revengeGain;

   public TargetFlyStallingRevengeCheck(int revengeGain) {
      this.revengeGain = revengeGain;
   }

   public boolean check(LivingEntity entity) {
      if (entity instanceof Mob mob) {
         LivingEntity target = mob.m_5448_();
         if (target == null || !target.m_6084_()) {
            return false;
         }

         if (target instanceof Player player) {
            if (player.m_150110_().f_35935_) {
               return true;
            }
         }

         if (AbilityHelper.getDifferenceToFloor(target) > (double)10.0F) {
            return true;
         }
      }

      return false;
   }

   public int revengeMeterGain() {
      return this.revengeGain;
   }
}
