package xyz.pixelatedw.mineminenomi.entities.ai.goals;

import java.util.EnumSet;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.level.pathfinder.Path;
import xyz.pixelatedw.mineminenomi.api.NuWorld;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.entities.ai.TickedGoal;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;

public class RunAroundTargetGoal<E extends PathfinderMob> extends TickedGoal<E> {
   private LivingEntity target;
   private double speed;
   private int ticksRunning;
   private final int defaultRunningTicks;
   private final int restBetweenRuns;
   private int restTicks;
   private int minDistance = 10;
   private int maxDistance = 30;
   private int uses = 0;

   public RunAroundTargetGoal(E entity, double speed, int ticksRunning, int restBetweenRuns) {
      super(entity);
      this.speed = speed;
      this.ticksRunning = ticksRunning;
      this.defaultRunningTicks = ticksRunning;
      this.restBetweenRuns = restBetweenRuns;
      this.m_7021_(EnumSet.of(Flag.MOVE));
   }

   public RunAroundTargetGoal<E> setRunningDistance(int min, int max) {
      this.minDistance = min;
      this.maxDistance = max;
      return this;
   }

   public boolean m_8036_() {
      if (this.restTicks > 0) {
         --this.restTicks;
         return false;
      } else if (!GoalHelper.hasAliveTarget(this.entity)) {
         return false;
      } else {
         this.target = ((PathfinderMob)this.entity).m_5448_();
         return true;
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
   }

   public void m_8037_() {
      super.m_8037_();
      --this.ticksRunning;
      BlockPos pathTarget = ((PathfinderMob)this.entity).m_21573_().m_26567_();
      boolean isNearTarget = pathTarget != null && ((PathfinderMob)this.entity).m_20275_((double)pathTarget.m_123341_(), (double)pathTarget.m_123342_(), (double)pathTarget.m_123343_()) < (double)25.0F;
      if (((PathfinderMob)this.entity).m_21573_().m_26571_() || isNearTarget) {
         Path path = this.findRunPosition();
         if (path != null) {
            ((PathfinderMob)this.entity).m_21573_().m_26536_(path, this.speed);
         }
      }

   }

   @Nullable
   private Path findRunPosition() {
      for(int run = 0; run < 20; ++run) {
         BlockPos pos = WyHelper.findValidGroundLocation((Entity)this.target, (BlockPos)this.target.m_20183_(), this.maxDistance, this.minDistance);
         boolean isWithinBounds = NuWorld.isChallengeDimension(((PathfinderMob)this.entity).m_9236_()) && NuWorld.isWithinChallengeArenaBounds(((PathfinderMob)this.entity).m_9236_(), pos);
         if (isWithinBounds && !pos.m_123314_(this.target.m_20183_(), (double)this.minDistance)) {
            Path path = ((PathfinderMob)this.entity).m_21573_().m_7864_(pos, 1);
            if (path != null && path.m_77403_()) {
               return path;
            }
         }
      }

      return null;
   }

   public void m_8041_() {
      super.m_8041_();
      this.ticksRunning = this.defaultRunningTicks;
      this.restTicks = this.restBetweenRuns;
   }
}
