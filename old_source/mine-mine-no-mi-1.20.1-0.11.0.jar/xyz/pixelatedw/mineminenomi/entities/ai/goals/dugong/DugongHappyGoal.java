package xyz.pixelatedw.mineminenomi.entities.ai.goals.dugong;

import java.util.EnumSet;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import xyz.pixelatedw.mineminenomi.api.entities.ai.TickedGoal;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;
import xyz.pixelatedw.mineminenomi.api.util.Interval;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.AbstractDugongEntity;

public class DugongHappyGoal extends TickedGoal<AbstractDugongEntity> {
   private Interval canUseInterval = new Interval(20);

   public DugongHappyGoal(AbstractDugongEntity entity) {
      super(entity);
      this.m_7021_(EnumSet.of(Flag.LOOK));
   }

   public boolean m_8036_() {
      if (!this.canUseInterval.canTick()) {
         return false;
      } else if (!((AbstractDugongEntity)this.entity).m_21824_()) {
         return false;
      } else if (!GoalHelper.hasAliveOwner(this.entity)) {
         return false;
      } else if (!((AbstractDugongEntity)this.entity).m_21827_()) {
         return false;
      } else {
         return !(((AbstractDugongEntity)this.entity).m_21223_() <= ((AbstractDugongEntity)this.entity).m_21233_() / 3.0F);
      }
   }

   public boolean m_8045_() {
      if (((AbstractDugongEntity)this.entity).isEnraged()) {
         return false;
      } else if (GoalHelper.hasAliveTarget(this.entity)) {
         return false;
      } else if (!GoalHelper.hasAliveOwner(this.entity)) {
         return false;
      } else {
         return ((AbstractDugongEntity)this.entity).m_21827_();
      }
   }

   public void m_8056_() {
      super.m_8056_();
      ((AbstractDugongEntity)this.entity).setHappy(true);
   }

   public void m_8037_() {
      super.m_8037_();
      GoalHelper.lookAtEntity(this.entity, ((AbstractDugongEntity)this.entity).m_269323_());
   }

   public void m_8041_() {
      super.m_8041_();
      ((AbstractDugongEntity)this.entity).setHappy(false);
   }
}
