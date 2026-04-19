package xyz.pixelatedw.mineminenomi.entities.ai.goals;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;
import xyz.pixelatedw.mineminenomi.data.entity.kairosekicoating.KairosekiCoatingCapability;

public class AvoidCoatedBoatGoal extends Goal {
   private PathfinderMob entity;
   private Vec3 toAvoid;
   private long lastCheck;
   private Path path;
   private final PathNavigation pathNav;
   private double speed = (double)2.0F;
   private double sprintSpeed = (double)4.0F;

   public AvoidCoatedBoatGoal(PathfinderMob entity) {
      this.entity = entity;
      this.pathNav = entity.m_21573_();
   }

   public void setSpeed(double speed) {
      this.speed = speed;
   }

   public void setSprintSpeed(double speed) {
      this.sprintSpeed = speed;
   }

   public boolean m_8036_() {
      if (this.entity != null && this.entity.m_6084_()) {
         long now = System.currentTimeMillis();
         if (now - this.lastCheck < 1000L) {
            return false;
         } else {
            for(Boat boat : this.entity.m_9236_().m_45976_(Boat.class, this.entity.m_20191_().m_82377_((double)20.0F, (double)20.0F, (double)20.0F))) {
               Entity entity = boat.m_6688_();
               if (entity != null && entity instanceof LivingEntity && boat.m_6084_() && GoalHelper.canSee(this.entity, boat)) {
                  int coatingLevel = (Integer)KairosekiCoatingCapability.get(boat).map((props) -> props.getCoatingLevel()).orElse(0);
                  if (coatingLevel > 0) {
                     int distance = (int)(128.0F * ((float)coatingLevel / 5.0F));
                     distance = Math.max(24, distance);
                     this.toAvoid = DefaultRandomPos.m_148412_(this.entity, distance, distance / 2, new Vec3(boat.m_20185_(), boat.m_20186_(), boat.m_20189_()), (Math.PI / 2D));
                     if (this.toAvoid != null) {
                        this.path = this.entity.m_21573_().m_26524_(this.toAvoid.f_82479_, this.toAvoid.f_82480_, this.toAvoid.f_82481_, 0);
                        if (this.path != null) {
                           return true;
                        }
                     }
                  }
               }
            }

            return false;
         }
      } else {
         return false;
      }
   }

   public boolean m_8045_() {
      return !this.pathNav.m_26571_();
   }

   public void m_8056_() {
      this.pathNav.m_26536_(this.path, this.speed);
   }

   public void m_8041_() {
      this.toAvoid = null;
   }

   public void m_8037_() {
      if (this.entity.m_20238_(this.toAvoid) < (double)49.0F) {
         this.entity.m_21573_().m_26517_(this.sprintSpeed);
      } else {
         this.entity.m_21573_().m_26517_(this.speed);
      }

      this.pathNav.m_26536_(this.path, this.speed);
   }
}
