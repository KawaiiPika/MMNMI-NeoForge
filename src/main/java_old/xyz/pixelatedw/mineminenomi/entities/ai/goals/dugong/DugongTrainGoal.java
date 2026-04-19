package xyz.pixelatedw.mineminenomi.entities.ai.goals.dugong;

import java.util.EnumSet;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.entities.ai.TickedGoal;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;
import xyz.pixelatedw.mineminenomi.api.util.Interval;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.AbstractDugongEntity;

public class DugongTrainGoal extends TickedGoal<AbstractDugongEntity> {
   private Interval interval = new Interval(10);
   private int trainingTime;
   private int nextTrainingTime = 2;

   public DugongTrainGoal(AbstractDugongEntity entity) {
      super(entity);
      this.m_7021_(EnumSet.of(Flag.JUMP, Flag.MOVE, Flag.LOOK));
   }

   public boolean m_8036_() {
      if (!this.interval.canTick()) {
         return false;
      } else if (!((AbstractDugongEntity)this.entity).isIdling()) {
         return false;
      } else {
         if (this.getLastEndTick() <= 0L) {
            this.setLastEndTick(((AbstractDugongEntity)this.entity).m_9236_().m_46467_());
         }

         if (!this.hasTimePassedSinceLastEnd(WyHelper.minutesToTicks((float)this.nextTrainingTime))) {
            return false;
         } else {
            return !GoalHelper.hasAliveTarget(this.entity);
         }
      }
   }

   public boolean m_8045_() {
      if (GoalHelper.shouldMove(this.entity)) {
         return false;
      } else if (GoalHelper.hasAliveTarget(this.entity)) {
         return false;
      } else {
         return !this.hasTimePassedSinceStart(WyHelper.secondsToTicks((float)this.trainingTime));
      }
   }

   public void m_8056_() {
      super.m_8056_();
      int mode = 1 + AbstractDugongEntity.TrainingMethod.values()[((AbstractDugongEntity)this.entity).m_217043_().m_188503_(AbstractDugongEntity.TrainingMethod.values().length)].ordinal();
      ((AbstractDugongEntity)this.entity).setTraining(mode);
      this.trainingTime = 10 + ((AbstractDugongEntity)this.entity).m_217043_().m_188503_(5);
      this.nextTrainingTime = 2 + ((AbstractDugongEntity)this.entity).m_217043_().m_188503_(3);
      ((AbstractDugongEntity)this.entity).m_21573_().m_26573_();
   }

   public void m_8041_() {
      super.m_8041_();
      ((AbstractDugongEntity)this.entity).stopTraining();
   }
}
