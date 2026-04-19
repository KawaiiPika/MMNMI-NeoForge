package xyz.pixelatedw.mineminenomi.entities.ai.goals.mr0;

import xyz.pixelatedw.mineminenomi.api.entities.ai.TickedGoal;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;
import xyz.pixelatedw.mineminenomi.entities.mobs.pirates.baroqueworks.Mr0Entity;

public class Mr0PhaseSwitcherGoal extends TickedGoal<Mr0Entity> {
   public Mr0PhaseSwitcherGoal(Mr0Entity entity) {
      super(entity);
   }

   public boolean m_8036_() {
      if (!GoalHelper.hasAliveTarget(this.entity)) {
         return false;
      } else if (((Mr0Entity)this.entity).isFirstPhaseActive() && this.trySwitchToSecondPhase()) {
         ((Mr0Entity)this.entity).startSecondPhase();
         return true;
      } else if (((Mr0Entity)this.entity).isSecondPhaseActive() && this.trySwitchToThirdPhase()) {
         ((Mr0Entity)this.entity).startThirdPhase();
         return true;
      } else {
         return false;
      }
   }

   private boolean trySwitchToSecondPhase() {
      return !GoalHelper.hasHealthAbovePercentage(this.entity, (double)70.0F);
   }

   private boolean trySwitchToThirdPhase() {
      if (!((Mr0Entity)this.entity).isDifficultyHardOrAbove()) {
         return false;
      } else if (((Mr0Entity)this.entity).getPainMeter().getRevengePercentage() > 0.5F) {
         return true;
      } else {
         return !GoalHelper.hasHealthAbovePercentage(this.entity, (double)50.0F);
      }
   }

   public boolean m_8045_() {
      return false;
   }
}
