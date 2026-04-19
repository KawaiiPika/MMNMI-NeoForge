package xyz.pixelatedw.mineminenomi.entities.ai.goals;

import java.util.EnumSet;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.entities.ai.TickedGoal;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;
import xyz.pixelatedw.mineminenomi.api.util.Interval;

public class RandomWalkAroundTargetGoal extends TickedGoal<PathfinderMob> {
   private LivingEntity target;
   private BlockPos targetPos;
   private double speed;
   private int ticksRunning;
   private final int defaultRunningTicks;
   private Interval restBetweenRuns;
   private int minDistance = 20;
   private int maxDistance = 30;

   public RandomWalkAroundTargetGoal(PathfinderMob entity, double speed, int ticksRunning, int restBetweenRuns) {
      super(entity);
      this.speed = speed;
      this.ticksRunning = ticksRunning;
      this.defaultRunningTicks = ticksRunning;
      this.restBetweenRuns = Interval.startAtZero(restBetweenRuns);
      this.m_7021_(EnumSet.of(Flag.MOVE));
   }

   public RandomWalkAroundTargetGoal setRunningDistance(int min, int max) {
      this.minDistance = min;
      this.maxDistance = max;
      return this;
   }

   public boolean m_8036_() {
      if (!this.restBetweenRuns.canTick()) {
         return false;
      } else if (!GoalHelper.hasAliveTarget(this.entity)) {
         return false;
      } else {
         this.target = ((PathfinderMob)this.entity).m_5448_();
         return !GoalHelper.isWithinDistance(this.entity, this.target, (double)10.0F);
      }
   }

   public boolean m_8045_() {
      if (!GoalHelper.hasAliveTarget(this.entity)) {
         return false;
      } else {
         return this.ticksRunning > 0;
      }
   }

   public void m_8056_() {
      super.m_8056_();
      this.targetPos = WyHelper.findValidGroundLocation((Entity)this.entity, (BlockPos)this.target.m_20183_(), this.maxDistance, this.minDistance);
   }

   public void m_8037_() {
      super.m_8037_();
      --this.ticksRunning;
      if (this.ticksRunning % 20 == 0) {
         this.findRunPosition(0);
      }

      if (((PathfinderMob)this.entity).m_21573_().m_26571_()) {
         this.findRunPosition(0);
      }

      ((PathfinderMob)this.entity).m_21573_().m_26519_((double)this.targetPos.m_123341_(), (double)this.targetPos.m_123342_(), (double)this.targetPos.m_123343_(), this.speed);
   }

   public void m_8041_() {
      super.m_8041_();
      this.ticksRunning = this.defaultRunningTicks;
   }

   private void findRunPosition(int run) {
      if (run == 0) {
      }

      BlockPos pos = WyHelper.findValidGroundLocation((Entity)this.entity, (BlockPos)this.target.m_20183_(), this.maxDistance, this.minDistance);
      if (run < 5 && !pos.m_123314_(this.target.m_20183_(), (double)this.maxDistance)) {
         this.findRunPosition(run++);
      } else {
         this.targetPos = pos;
      }
   }
}
