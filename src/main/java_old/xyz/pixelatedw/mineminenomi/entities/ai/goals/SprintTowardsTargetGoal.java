package xyz.pixelatedw.mineminenomi.entities.ai.goals;

import java.util.UUID;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.goal.Goal;
import xyz.pixelatedw.mineminenomi.api.helpers.AttributeHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;

public class SprintTowardsTargetGoal extends Goal {
   private static final UUID SPEED_MODIFIER_SPRINTING_UUID = UUID.fromString("c320875f-76d4-4598-b59d-d63ca72a564c");
   private Mob entity;
   private float speedModifier = 0.3F;
   private double currentSpeedStat;

   public SprintTowardsTargetGoal(Mob entity) {
      this.entity = entity;
   }

   public boolean m_8036_() {
      if (!GoalHelper.hasAliveTarget(this.entity)) {
         return false;
      } else {
         LivingEntity target = this.entity.m_5448_();
         if (!GoalHelper.canSee(this.entity, target)) {
            return false;
         } else if (!this.entity.m_20142_() && GoalHelper.isOutsideDistance(this.entity, target, (double)15.0F)) {
            return true;
         } else {
            return this.entity.m_20142_() && GoalHelper.isWithinDistance(this.entity, target, (double)5.0F) ? false : this.entity.m_20142_();
         }
      }
   }

   public boolean m_8045_() {
      return super.m_8045_();
   }

   public void m_8056_() {
      this.setSprinting(true);
   }

   public void m_8037_() {
   }

   public void m_8041_() {
      this.setSprinting(false);
   }

   public void setSprinting(boolean flag) {
      this.entity.m_6858_(flag);
      AttributeInstance attr = this.entity.m_21051_(Attributes.f_22279_);
      attr.m_22120_(AttributeHelper.SPEED_MODIFIER_SPRINTING_UUID);
      attr.m_22120_(SPEED_MODIFIER_SPRINTING_UUID);
      if (flag) {
         attr.m_22118_(this.getSpeedModifier());
      }

      this.currentSpeedStat = attr.m_22135_();
      this.entity.m_21573_().m_26517_(this.currentSpeedStat);
   }

   public AttributeModifier getSpeedModifier() {
      return new AttributeModifier(SPEED_MODIFIER_SPRINTING_UUID, "Sprinting speed boost", (double)this.speedModifier, Operation.MULTIPLY_TOTAL);
   }
}
