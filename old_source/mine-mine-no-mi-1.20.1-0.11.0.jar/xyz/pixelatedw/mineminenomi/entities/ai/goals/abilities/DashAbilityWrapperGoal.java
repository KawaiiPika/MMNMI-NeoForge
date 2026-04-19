package xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.ai.AbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;

public class DashAbilityWrapperGoal<A extends IAbility> extends AbilityWrapperGoal<Mob, A> {
   private LivingEntity target;
   private float activationDistance = 15.0F;
   private float maxDistance = 100.0F;

   public DashAbilityWrapperGoal(Mob entity, AbilityCore<A> ability) {
      super(entity, ability);
   }

   public boolean canUseWrapper() {
      if (!GoalHelper.hasAliveTarget(this.entity)) {
         return false;
      } else if (!GoalHelper.canMove(this.entity)) {
         return false;
      } else if (!this.entity.m_20096_()) {
         return false;
      } else {
         this.target = this.entity.m_5448_();
         if (!GoalHelper.canSee(this.entity, this.target)) {
            return false;
         } else {
            return !GoalHelper.isOutsideDistance(this.entity, this.target, (double)this.activationDistance);
         }
      }
   }

   public boolean canContinueToUseWrapper() {
      if (!GoalHelper.hasAliveTarget(this.entity)) {
         return false;
      } else {
         return !(this.maxDistance > 0.0F) || !GoalHelper.isOutsideDistance(this.entity, this.target, (double)(this.maxDistance * 2.0F));
      }
   }

   public void startWrapper() {
      GoalHelper.lookAtEntity(this.entity, this.target);
   }

   public void tickWrapper() {
      GoalHelper.lookAtEntity(this.entity, this.target);
   }

   public void stopWrapper() {
      this.entity.m_21573_().m_26573_();
   }
}
