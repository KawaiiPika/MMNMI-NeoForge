package xyz.pixelatedw.mineminenomi.entities.ai.goals;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.NuWorld;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.entities.ai.TickedGoal;

public class RunAwayFromLocationGoal<E extends PathfinderMob> extends TickedGoal<E> {
   private double speed;
   private Vec3 location;
   private int minDistance = 20;
   private int maxDistance = 30;

   public RunAwayFromLocationGoal(E entity, double speed, Vec3 location) {
      super(entity);
      this.speed = speed;
      this.location = location;
   }

   public RunAwayFromLocationGoal setRunningDistance(int min, int max) {
      this.minDistance = min;
      this.maxDistance = max;
      return this;
   }

   public boolean m_8036_() {
      return !(((PathfinderMob)this.entity).m_20238_(this.location) > (double)(this.minDistance * this.minDistance));
   }

   public boolean m_8045_() {
      return !(((PathfinderMob)this.entity).m_20238_(this.location) <= (double)(this.maxDistance * this.maxDistance));
   }

   public void m_8056_() {
      super.m_8056_();
   }

   public void m_8037_() {
      super.m_8037_();
      Mob var2 = this.entity;
      if (var2 instanceof Villager villager) {
         villager.m_6274_().m_21933_((ServerLevel)((PathfinderMob)this.entity).m_9236_(), villager);
      }

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
         BlockPos pos = WyHelper.findValidGroundLocation((Entity)this.entity, (BlockPos)((PathfinderMob)this.entity).m_20183_(), this.maxDistance, this.minDistance);
         boolean isWithinBounds = !NuWorld.isChallengeDimension(((PathfinderMob)this.entity).m_9236_()) || NuWorld.isChallengeDimension(((PathfinderMob)this.entity).m_9236_()) && NuWorld.isWithinChallengeArenaBounds(((PathfinderMob)this.entity).m_9236_(), pos);
         if (isWithinBounds && pos.m_123314_(((PathfinderMob)this.entity).m_20183_(), (double)this.minDistance)) {
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
   }
}
