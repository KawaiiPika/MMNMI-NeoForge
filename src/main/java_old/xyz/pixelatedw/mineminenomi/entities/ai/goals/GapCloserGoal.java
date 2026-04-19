package xyz.pixelatedw.mineminenomi.entities.ai.goals;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.entities.ai.TickedGoal;
import xyz.pixelatedw.mineminenomi.api.entities.revenge.PhysicalHitRevengeCheck;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;

public class GapCloserGoal extends TickedGoal<Mob> {
   private LivingEntity target;
   private PhysicalHitRevengeCheck hitCheck;
   private int maxCount;
   private double speed;

   public GapCloserGoal(Mob entity) {
      this(entity, 1.7, 3);
   }

   public GapCloserGoal(Mob entity, double speed, int hitCount) {
      super(entity);
      this.hitCheck = new PhysicalHitRevengeCheck(0);
      this.speed = speed;
      this.maxCount = hitCount;
   }

   public boolean m_8036_() {
      if (!GoalHelper.hasAliveTarget(this.entity)) {
         return false;
      } else {
         this.target = this.entity.m_5448_();
         if (!GoalHelper.canMove(this.entity)) {
            return false;
         } else {
            return !GoalHelper.isWithinDistance(this.entity, this.target, (double)2.0F) && !GoalHelper.isOutsideDistance(this.entity, this.target, (double)5.0F);
         }
      }
   }

   public void m_8056_() {
      this.hitCheck.resetMarkers();
      double mX = (double)(-Mth.m_14031_(this.entity.m_146909_() / 180.0F * (float)Math.PI) * Mth.m_14089_(this.entity.m_146908_() / 180.0F * (float)Math.PI)) * 0.4;
      double mZ = (double)(Mth.m_14089_(this.entity.m_146909_() / 180.0F * (float)Math.PI) * Mth.m_14089_(this.entity.m_146908_() / 180.0F * (float)Math.PI)) * 0.4;
      double f2 = (double)Mth.m_14116_((float)(mX * mX + this.entity.m_20184_().f_82480_ * this.entity.m_20184_().f_82480_ + mZ * mZ));
      mX /= f2;
      mZ /= f2;
      mX += this.entity.m_9236_().f_46441_.m_188583_() * (double)0.0075F * (double)1.0F;
      mZ += this.entity.m_9236_().f_46441_.m_188583_() * (double)0.0075F * (double)1.0F;
      mX *= this.speed;
      mZ *= this.speed;
      AbilityHelper.setDeltaMovement(this.entity, new Vec3(mX, 0.3, mZ));
   }
}
