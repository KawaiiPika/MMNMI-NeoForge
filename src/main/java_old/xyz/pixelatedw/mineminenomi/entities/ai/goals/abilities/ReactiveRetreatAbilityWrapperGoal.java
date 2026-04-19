package xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities;

import javax.annotation.Nullable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.ai.AbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.api.entities.revenge.RevengeMeter;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;

public class ReactiveRetreatAbilityWrapperGoal<A extends IAbility> extends AbilityWrapperGoal<Mob, A> {
   private LivingEntity target;
   private float minDistance;
   private float maxDistance;
   @Nullable
   private RevengeMeter revengeMeter;
   private int activationHits;
   private int hits;

   public ReactiveRetreatAbilityWrapperGoal(Mob entity, AbilityCore<A> ability) {
      this(entity, (RevengeMeter)null, ability);
   }

   public ReactiveRetreatAbilityWrapperGoal(Mob entity, @Nullable RevengeMeter hitsMeter, AbilityCore<A> ability) {
      super(entity, ability);
      this.minDistance = 10.0F;
      this.maxDistance = 30.0F;
      this.activationHits = 3;
      this.hits = 0;
      this.revengeMeter = hitsMeter;
      if (hitsMeter != null) {
         this.activationHits = this.revengeMeter.getMaxRevengeValue();
      }

   }

   public ReactiveRetreatAbilityWrapperGoal<A> setMinDistance(float min) {
      this.minDistance = min;
      return this;
   }

   public ReactiveRetreatAbilityWrapperGoal<A> setMaxDistance(float max) {
      this.maxDistance = max;
      return this;
   }

   public ReactiveRetreatAbilityWrapperGoal<A> setActivationHits(int hits) {
      this.activationHits = hits;
      return this;
   }

   public boolean canUseWrapper() {
      if (!GoalHelper.hasAliveTarget(this.entity)) {
         return false;
      } else {
         this.target = this.entity.m_5448_();
         if (!GoalHelper.canSee(this.entity, this.target)) {
            return false;
         } else if (GoalHelper.isOutsideDistance(this.entity, this.target, (double)this.minDistance)) {
            return false;
         } else {
            if (this.revengeMeter != null) {
               if (this.revengeMeter.getRevengeValue() < this.activationHits) {
                  return false;
               }
            } else if (this.hits < this.activationHits) {
               return false;
            }

            return true;
         }
      }
   }

   public boolean canContinueToUseWrapper() {
      if (!GoalHelper.hasAliveTarget(this.entity)) {
         return false;
      } else {
         return !(this.maxDistance > 0.0F) || !GoalHelper.isOutsideDistance(this.entity, this.target, (double)this.maxDistance);
      }
   }

   public void startWrapper() {
      this.entity.m_21573_().m_26573_();
      if (this.target.m_142582_(this.entity)) {
         this.entity.m_146922_(this.target.m_146908_());
      } else {
         this.entity.m_146922_(this.entity.m_146908_() + 180.0F);
      }

      this.revengeMeter.resetMarkers();
   }

   public void tickWrapper() {
   }

   public void stopWrapper() {
   }
}
