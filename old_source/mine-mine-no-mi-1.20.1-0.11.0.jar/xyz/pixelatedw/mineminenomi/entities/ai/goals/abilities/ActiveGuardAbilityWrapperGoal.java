package xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.ai.AbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;

public class ActiveGuardAbilityWrapperGoal<A extends IAbility> extends AbilityWrapperGoal<Mob, A> {
   private LivingEntity target;
   private float minDistance = 10.0F;
   private float maxDistance = 30.0F;

   public ActiveGuardAbilityWrapperGoal(Mob entity, AbilityCore<A> ability) {
      super(entity, ability);
   }

   public ActiveGuardAbilityWrapperGoal<A> setMinDistance(float dist) {
      this.minDistance = dist;
      return this;
   }

   public ActiveGuardAbilityWrapperGoal<A> setMaxDistance(float dist) {
      this.maxDistance = dist;
      return this;
   }

   public boolean canUseWrapper() {
      if (!GoalHelper.hasAliveTarget(this.entity)) {
         return false;
      } else {
         this.target = this.entity.m_5448_();
         if (!GoalHelper.canSee(this.entity, this.target)) {
            return false;
         } else {
            return !GoalHelper.isOutsideDistance(this.entity, this.target, (double)this.minDistance);
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
   }

   public void tickWrapper() {
   }

   public void stopWrapper() {
   }
}
