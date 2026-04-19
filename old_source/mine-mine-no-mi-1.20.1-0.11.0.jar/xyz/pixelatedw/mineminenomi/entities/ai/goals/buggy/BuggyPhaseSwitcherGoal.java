package xyz.pixelatedw.mineminenomi.entities.ai.goals.buggy;

import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.entities.ai.TickedGoal;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;
import xyz.pixelatedw.mineminenomi.entities.mobs.pirates.buggypirates.BuggyEntity;

public class BuggyPhaseSwitcherGoal extends TickedGoal<BuggyEntity> {
   private static final int COOLDOWN = 600;
   private final double carPhaseHPThreshold;
   private final int carPhaseDuration;
   private boolean startNormalPhase;
   private boolean startCarPhase;
   private long carPhaseStartTick;

   public BuggyPhaseSwitcherGoal(BuggyEntity entity) {
      super(entity);
      this.carPhaseHPThreshold = entity.isDifficultyStandard() ? (double)20.0F : (double)40.0F;
      this.carPhaseDuration = entity.isDifficultyStandard() ? 600 : 1200;
   }

   public boolean m_8036_() {
      if (!GoalHelper.hasAliveTarget(this.entity)) {
         return false;
      } else if (!this.hasTimePassedSinceLastEnd(600.0F)) {
         return false;
      } else {
         boolean carSwitchHPThreshold = (double)((BuggyEntity)this.entity).m_21223_() < WyHelper.percentage(this.carPhaseHPThreshold, (double)((BuggyEntity)this.entity).m_21233_());
         boolean carSwitchKitingThreshold = ((BuggyEntity)this.entity).getKitingMeterCompletion() > (((BuggyEntity)this.entity).isDifficultyStandard() ? 0.7F : 0.5F);
         if (!((BuggyEntity)this.entity).isNormalPhaseActive() || !carSwitchHPThreshold && !carSwitchKitingThreshold) {
            if (((BuggyEntity)this.entity).isCarPhaseActive() && this.hasCarPhaseTimePassed()) {
               this.startNormalPhase = true;
               return true;
            } else {
               return false;
            }
         } else {
            this.startCarPhase = true;
            return true;
         }
      }
   }

   private boolean hasCarPhaseTimePassed() {
      return ((BuggyEntity)this.entity).m_9236_().m_46467_() >= this.carPhaseStartTick + (long)this.carPhaseDuration;
   }

   public boolean m_8045_() {
      return false;
   }

   public void m_8056_() {
      super.m_8056_();
      if (this.startCarPhase) {
         ((BuggyEntity)this.entity).startCarPhase();
         this.carPhaseStartTick = ((BuggyEntity)this.entity).m_9236_().m_46467_();
      } else if (this.startNormalPhase) {
         ((BuggyEntity)this.entity).startNormalPhase();
      }

      this.startNormalPhase = false;
      this.startCarPhase = false;
   }

   public void m_8037_() {
      super.m_8037_();
   }

   public void m_8041_() {
      super.m_8041_();
   }
}
