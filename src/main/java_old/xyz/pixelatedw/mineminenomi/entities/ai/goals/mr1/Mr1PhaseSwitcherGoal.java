package xyz.pixelatedw.mineminenomi.entities.ai.goals.mr1;

import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.entities.ai.TickedGoal;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;
import xyz.pixelatedw.mineminenomi.entities.mobs.pirates.baroqueworks.Mr1Entity;

public class Mr1PhaseSwitcherGoal extends TickedGoal<Mr1Entity> {
   private float ultiReuseTime;

   public Mr1PhaseSwitcherGoal(Mr1Entity entity) {
      super(entity);
      this.ultiReuseTime = entity.getChallengeInfo().isDifficultyHard() ? WyHelper.minutesToTicks(3.0F) : WyHelper.minutesToTicks(6.0F);
   }

   public boolean m_8036_() {
      if (!GoalHelper.hasAliveTarget(this.entity)) {
         return false;
      } else if ((double)((Mr1Entity)this.entity).m_21223_() > WyHelper.percentage((double)50.0F, (double)((Mr1Entity)this.entity).m_21233_())) {
         return false;
      } else if (!((Mr1Entity)this.entity).isUltiAvailable()) {
         return false;
      } else {
         return this.getLastEndTick() <= 0L || this.hasTimePassedSinceLastEnd(this.ultiReuseTime);
      }
   }

   public boolean m_8045_() {
      return ((Mr1Entity)this.entity).isUltiAvailable();
   }

   public void m_8056_() {
      super.m_8056_();
      ((Mr1Entity)this.entity).startUltiPhase();
   }

   public void m_8037_() {
      super.m_8037_();
   }

   public void m_8041_() {
      super.m_8041_();
      ((Mr1Entity)this.entity).startSecondPhase();
   }
}
