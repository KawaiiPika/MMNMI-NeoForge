package xyz.pixelatedw.mineminenomi.api.entities.revenge;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;

public class TargetRunningAwayRevengeCheck extends BaseRevengeCheck implements ITickRevengeCheck {
   private final int revengeGain;
   private final float distance;

   public TargetRunningAwayRevengeCheck(int revengeGain, float distance) {
      this.revengeGain = revengeGain;
      this.distance = distance;
   }

   public boolean check(LivingEntity entity) {
      if (entity instanceof Mob mob) {
         LivingEntity target = mob.m_5448_();
         if (target == null || !target.m_6084_()) {
            return false;
         }

         if (entity.m_20270_(target) > this.distance * this.distance) {
            return true;
         }
      }

      return false;
   }

   public int revengeMeterGain() {
      return this.revengeGain;
   }
}
