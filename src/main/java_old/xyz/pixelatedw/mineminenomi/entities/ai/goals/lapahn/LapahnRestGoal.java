package xyz.pixelatedw.mineminenomi.entities.ai.goals.lapahn;

import java.util.EnumSet;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.entities.ai.TickedGoal;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;
import xyz.pixelatedw.mineminenomi.api.util.Interval;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.LapahnEntity;

public class LapahnRestGoal extends TickedGoal<LapahnEntity> {
   private Interval interval = new Interval(40);
   private int restTime;
   private int nextRestTime = 2;
   private float startHealth;

   public LapahnRestGoal(LapahnEntity entity) {
      super(entity);
      this.m_7021_(EnumSet.of(Flag.JUMP, Flag.MOVE, Flag.LOOK));
   }

   public boolean m_8036_() {
      if (!((LapahnEntity)this.entity).isIdling()) {
         return false;
      } else if (!this.interval.canTick()) {
         return false;
      } else {
         if (this.getLastEndTick() <= 0L) {
            this.setLastEndTick(((LapahnEntity)this.entity).m_9236_().m_46467_());
         }

         if (!this.hasTimePassedSinceLastEnd(WyHelper.minutesToTicks((float)this.nextRestTime))) {
            return false;
         } else {
            return !GoalHelper.hasAliveTarget(this.entity);
         }
      }
   }

   public boolean m_8045_() {
      if (GoalHelper.hasAliveTarget(this.entity)) {
         return false;
      } else if (((LapahnEntity)this.entity).m_21223_() < this.startHealth) {
         return false;
      } else {
         return !this.hasTimePassedSinceStart(WyHelper.secondsToTicks((float)this.restTime));
      }
   }

   public void m_8056_() {
      super.m_8056_();
      ((LapahnEntity)this.entity).setResting(true);
      this.restTime = 20 + ((LapahnEntity)this.entity).m_217043_().m_188503_(20);
      this.nextRestTime = 2 + ((LapahnEntity)this.entity).m_217043_().m_188503_(3);
      ((LapahnEntity)this.entity).m_21573_().m_26573_();
      this.startHealth = ((LapahnEntity)this.entity).m_21223_();
   }

   public void m_8041_() {
      super.m_8041_();
      ((LapahnEntity)this.entity).setResting(false);
   }
}
