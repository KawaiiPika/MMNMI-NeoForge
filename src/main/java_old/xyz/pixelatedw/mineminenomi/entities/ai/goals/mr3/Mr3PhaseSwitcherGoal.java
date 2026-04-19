package xyz.pixelatedw.mineminenomi.entities.ai.goals.mr3;

import xyz.pixelatedw.mineminenomi.api.entities.ai.TickedGoal;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;
import xyz.pixelatedw.mineminenomi.entities.mobs.pirates.baroqueworks.Mr3Entity;

public class Mr3PhaseSwitcherGoal extends TickedGoal<Mr3Entity> {
   private boolean startStandardPhase;
   private boolean startChampionPhase;
   private boolean startTokudaiPhase;
   private boolean isPostTokudai;

   public Mr3PhaseSwitcherGoal(Mr3Entity entity) {
      super(entity);
   }

   public boolean m_8036_() {
      if (!GoalHelper.hasAliveTarget(this.entity)) {
         return false;
      } else if (((Mr3Entity)this.entity).isStandardPhase() && !this.isPostTokudai) {
         if (GoalHelper.hasHealthAbovePercentage(this.entity, (double)50.0F)) {
            return false;
         } else if (GoalHelper.hasHealthUnderPercentage(this.entity, (double)50.0F) && ((Mr3Entity)this.entity).isChampionOnCooldown()) {
            this.startTokudaiPhase = true;
            return true;
         } else {
            this.startChampionPhase = true;
            return true;
         }
      } else if (((Mr3Entity)this.entity).isChampionPhase()) {
         if (((Mr3Entity)this.entity).isChampionOnCooldown()) {
            this.startStandardPhase = true;
            return true;
         } else if (GoalHelper.hasHealthAbovePercentage(this.entity, (double)30.0F)) {
            return false;
         } else {
            this.startTokudaiPhase = true;
            return true;
         }
      } else if (((Mr3Entity)this.entity).isTokudaiPhase()) {
         if (((Mr3Entity)this.entity).isTokudaiOnCooldown()) {
            this.startStandardPhase = true;
            return true;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public boolean m_8045_() {
      return false;
   }

   public void m_8056_() {
      super.m_8056_();
      this.switchPhase();
   }

   public void m_8037_() {
      super.m_8037_();
   }

   public void m_8041_() {
      super.m_8041_();
   }

   public void switchPhase() {
      if (this.startStandardPhase) {
         ((Mr3Entity)this.entity).startStandardPhase();
      } else if (this.startChampionPhase) {
         ((Mr3Entity)this.entity).startChampionPhase();
      } else if (this.startTokudaiPhase) {
         ((Mr3Entity)this.entity).startTokudaiPhase();
         this.isPostTokudai = true;
      }

      this.startStandardPhase = false;
      this.startChampionPhase = false;
      this.startTokudaiPhase = false;
   }
}
