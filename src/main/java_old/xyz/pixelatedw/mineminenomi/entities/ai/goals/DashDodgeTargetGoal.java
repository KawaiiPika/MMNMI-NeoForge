package xyz.pixelatedw.mineminenomi.entities.ai.goals;

import com.google.common.base.Predicates;
import java.util.function.Predicate;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.entities.ai.TickedGoal;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;

public class DashDodgeTargetGoal extends TickedGoal<Mob> {
   private LivingEntity target;
   private float ticksBetweenDashes;
   private float dashDistance;
   private boolean isSurrounded;
   private Predicate<LivingEntity> canUseTest = Predicates.alwaysTrue();

   public DashDodgeTargetGoal(Mob entity, float ticksBetweenDashes, float dashDistance) {
      super(entity);
      this.ticksBetweenDashes = ticksBetweenDashes;
      this.dashDistance = dashDistance;
   }

   public boolean m_8036_() {
      if (!GoalHelper.hasAliveTarget(this.entity)) {
         return false;
      } else {
         this.target = this.entity.m_5448_();
         if (!this.hasTimePassedSinceLastEnd(this.ticksBetweenDashes)) {
            return false;
         } else if (!GoalHelper.canMove(this.entity)) {
            return false;
         } else if (GoalHelper.isOutsideDistance(this.entity, this.target, (double)10.0F)) {
            return false;
         } else if (WyHelper.getDifferenceToFloor(this.entity) > (double)3.0F) {
            return false;
         } else if (!GoalHelper.isEntityInView(this.target, this.entity)) {
            return false;
         } else {
            return this.canUseTest.test(this.entity);
         }
      }
   }

   public boolean m_8045_() {
      return false;
   }

   public void m_8056_() {
      super.m_8056_();
      if (!this.isSurrounded) {
         GoalHelper.lookAtEntity(this.entity, this.target);
         Vec3 dir = this.target.m_20182_().m_82546_(this.entity.m_20182_()).m_82541_().m_82542_((double)this.dashDistance, (double)1.0F, (double)this.dashDistance);
         AbilityHelper.setDeltaMovement(this.entity, -dir.f_82479_, 0.1, -dir.f_82481_);
      }

   }

   public void m_8037_() {
      super.m_8037_();
   }

   public DashDodgeTargetGoal setCanUseTest(Predicate<LivingEntity> test) {
      this.canUseTest = test;
      return this;
   }
}
