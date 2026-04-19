package xyz.pixelatedw.mineminenomi.entities.ai.goals.dugong;

import java.util.EnumSet;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.entities.ai.TickedGoal;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;
import xyz.pixelatedw.mineminenomi.api.util.Interval;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.AbstractDugongEntity;

public class DugongCheerGoal extends TickedGoal<AbstractDugongEntity> {
   private Interval canUseInterval = new Interval(10);

   public DugongCheerGoal(AbstractDugongEntity entity) {
      super(entity);
      this.m_7021_(EnumSet.of(Flag.TARGET, Flag.MOVE, Flag.JUMP, Flag.LOOK));
   }

   public boolean m_8036_() {
      if (!this.canUseInterval.canTick()) {
         return false;
      } else if (((AbstractDugongEntity)this.entity).isEnraged()) {
         return false;
      } else if (((AbstractDugongEntity)this.entity).getCheerTarget() == null) {
         return false;
      } else {
         return !GoalHelper.hasAliveTarget(this.entity);
      }
   }

   public boolean m_8045_() {
      if (((AbstractDugongEntity)this.entity).isEnraged()) {
         return false;
      } else if (GoalHelper.shouldMove(this.entity)) {
         return false;
      } else if (!((AbstractDugongEntity)this.entity).isCheering()) {
         return false;
      } else if (((AbstractDugongEntity)this.entity).getCheerTarget() != null && ((AbstractDugongEntity)this.entity).getCheerTarget().m_6084_()) {
         if (GoalHelper.hasAliveTarget(this.entity)) {
            return false;
         } else {
            return !this.hasTimePassedSinceStart(WyHelper.secondsToTicks(30.0F));
         }
      } else {
         return false;
      }
   }

   public void m_8056_() {
      super.m_8056_();
      ((AbstractDugongEntity)this.entity).m_21573_().m_26573_();
   }

   public void m_8037_() {
      if (((AbstractDugongEntity)this.entity).m_9236_().m_46467_() % 5L == 0L) {
         ((AbstractDugongEntity)this.entity).m_21569_().m_24901_();
      }

      GoalHelper.lookAtEntity(this.entity, ((AbstractDugongEntity)this.entity).getCheerTarget());
      super.m_8037_();
   }

   public void m_8041_() {
      super.m_8041_();
      ((AbstractDugongEntity)this.entity).setCheering((LivingEntity)null);
   }
}
