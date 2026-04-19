package xyz.pixelatedw.mineminenomi.entities.ai.goals;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.entities.ai.TickedGoal;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;

public class JumpOutOfHoleGoal extends TickedGoal<Mob> {
   private static final int COOLDOWN = 60;
   private LivingEntity target;
   private boolean pushed;
   private final float jumpHeight;

   public JumpOutOfHoleGoal(Mob entity) {
      this(entity, 1.3F);
   }

   public JumpOutOfHoleGoal(Mob entity, float jumpHeight) {
      super(entity);
      this.jumpHeight = jumpHeight;
   }

   public boolean m_8036_() {
      if (!GoalHelper.hasAliveTarget(this.entity)) {
         return false;
      } else if (!this.hasTimePassedSinceLastEnd(60.0F)) {
         return false;
      } else {
         this.target = this.entity.m_5448_();
         if (GoalHelper.hasSolidBlockAbove(this.entity)) {
            return false;
         } else {
            return GoalHelper.hasBlockInFace(this.entity);
         }
      }
   }

   public boolean m_8045_() {
      if (this.hasTimePassedSinceStart(100.0F)) {
         return false;
      } else {
         return !this.pushed;
      }
   }

   public void m_8056_() {
      super.m_8056_();
      this.pushed = false;
   }

   public void m_8037_() {
      super.m_8037_();
      if (!this.pushed) {
         if (this.getTickCount() % 40L == 0L) {
            AbilityHelper.setDeltaMovement(this.entity, (double)0.0F, (double)this.jumpHeight, (double)0.0F);
         }

         if (GoalHelper.canSee(this.entity, this.target) && !GoalHelper.hasBlockInFace(this.entity)) {
            GoalHelper.lookAtEntity(this.entity, this.target);
            Vec3 push = this.target.m_20182_().m_82546_(this.entity.m_20182_()).m_82541_().m_82542_((double)2.0F, (double)0.0F, (double)2.0F);
            AbilityHelper.setDeltaMovement(this.entity, push.f_82479_, this.entity.m_20184_().m_7098_() + 0.2, push.f_82481_);
            this.pushed = true;
         }
      }

   }

   public void m_8041_() {
      super.m_8041_();
   }
}
