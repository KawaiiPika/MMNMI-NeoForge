package xyz.pixelatedw.mineminenomi.entities.ai.goals;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.entities.ai.TickedGoal;

public class RunTowardsLocationGoal<E extends PathfinderMob> extends TickedGoal<E> {
   private Path path;
   private double speed;
   private Vec3 location;

   public RunTowardsLocationGoal(E entity, double speed, Vec3 location) {
      super(entity);
      this.speed = speed;
      this.location = location;
   }

   public boolean m_8036_() {
      if (((PathfinderMob)this.entity).m_20238_(this.location) <= (double)25.0F) {
         return false;
      } else {
         this.path = ((PathfinderMob)this.entity).m_21573_().m_7864_(BlockPos.m_274446_(this.location), 1);
         return this.path != null;
      }
   }

   public boolean m_8045_() {
      return !(((PathfinderMob)this.entity).m_20238_(this.location) <= (double)25.0F);
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

      ((PathfinderMob)this.entity).m_21573_().m_26536_(this.path, this.speed);
   }

   public void m_8041_() {
      super.m_8041_();
   }
}
