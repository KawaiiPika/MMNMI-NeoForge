package xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.ai.AbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;

public class RepeaterAbilityWrapperGoal<A extends IAbility> extends AbilityWrapperGoal<Mob, A> {
   private LivingEntity target;
   private float minDistance = 0.0F;

   public RepeaterAbilityWrapperGoal(Mob entity, AbilityCore<A> ability) {
      super(entity, ability);
   }

   public boolean canUseWrapper() {
      if (!GoalHelper.hasAliveTarget(this.entity)) {
         return false;
      } else {
         this.target = this.entity.m_5448_();
         if (!GoalHelper.canSee(this.entity, this.target)) {
            return false;
         } else {
            return !(this.minDistance > 0.0F) || !GoalHelper.isWithinDistance(this.entity, this.target, (double)this.minDistance);
         }
      }
   }

   public boolean canContinueToUseWrapper() {
      return true;
   }

   public void startWrapper() {
      GoalHelper.lookAtEntity(this.entity, this.target);
   }

   public void tickWrapper() {
   }

   public void stopWrapper() {
   }
}
