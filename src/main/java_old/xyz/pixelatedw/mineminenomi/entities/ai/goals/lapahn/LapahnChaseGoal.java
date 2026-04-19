package xyz.pixelatedw.mineminenomi.entities.ai.goals.lapahn;

import java.util.UUID;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.goal.Goal;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;
import xyz.pixelatedw.mineminenomi.api.util.Interval;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.LapahnEntity;

public class LapahnChaseGoal extends Goal {
   private static final AttributeModifier CHASE_MODIFIER;
   private Interval interval = new Interval(10);
   private LapahnEntity entity;

   public LapahnChaseGoal(LapahnEntity entity) {
      this.entity = entity;
   }

   public boolean m_8036_() {
      if (!this.interval.canTick()) {
         return false;
      } else if (this.entity.isChasing()) {
         return false;
      } else {
         return GoalHelper.hasAliveTarget(this.entity);
      }
   }

   public boolean m_8045_() {
      return GoalHelper.hasAliveTarget(this.entity);
   }

   public void m_8056_() {
      super.m_8056_();
      this.entity.setChasing(true);
      AttributeInstance attr = this.entity.m_21051_(Attributes.f_22279_);
      if (attr != null) {
         attr.m_22130_(CHASE_MODIFIER);
         attr.m_22118_(CHASE_MODIFIER);
      }

   }

   public void m_8041_() {
      super.m_8041_();
      this.entity.setChasing(false);
      AttributeInstance attr = this.entity.m_21051_(Attributes.f_22279_);
      if (attr != null) {
         attr.m_22130_(CHASE_MODIFIER);
      }

   }

   static {
      CHASE_MODIFIER = new AttributeModifier(UUID.fromString("0524f5ca-28d0-487b-ab03-b77c4047cb25"), "Sprinting speed boost", (double)1.0F, Operation.MULTIPLY_BASE);
   }
}
