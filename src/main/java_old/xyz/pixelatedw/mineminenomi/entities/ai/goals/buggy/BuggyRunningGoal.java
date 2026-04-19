package xyz.pixelatedw.mineminenomi.entities.ai.goals.buggy;

import xyz.pixelatedw.mineminenomi.entities.ai.goals.RunAroundTargetGoal;
import xyz.pixelatedw.mineminenomi.entities.mobs.pirates.buggypirates.BuggyEntity;

public class BuggyRunningGoal extends RunAroundTargetGoal<BuggyEntity> {
   public BuggyRunningGoal(BuggyEntity entity, double speed) {
      super(entity, speed, 200, 20);
   }

   public boolean m_8036_() {
      return !super.m_8036_() ? false : ((BuggyEntity)this.entity).shouldRun();
   }

   public boolean m_8045_() {
      return !super.m_8045_() ? false : ((BuggyEntity)this.entity).shouldRun();
   }
}
