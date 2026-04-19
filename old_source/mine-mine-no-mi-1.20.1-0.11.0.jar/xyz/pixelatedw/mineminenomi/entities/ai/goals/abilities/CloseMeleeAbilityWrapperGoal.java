package xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraftforge.common.ForgeMod;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.entities.ai.AbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;

public class CloseMeleeAbilityWrapperGoal<A extends IAbility> extends AbilityWrapperGoal<Mob, A> {
   private LivingEntity target;
   private float activationDistance = 6.0F;
   private float maxDistance = 100.0F;
   private boolean isCharging = false;

   public CloseMeleeAbilityWrapperGoal(Mob entity, AbilityCore<A> ability) {
      super(entity, ability);
      AttributeInstance attr = entity.m_21051_((Attribute)ForgeMod.ENTITY_REACH.get());
      if (attr != null && attr.m_22135_() > (double)this.activationDistance) {
         this.activationDistance = (float)attr.m_22135_();
      }

   }

   public CloseMeleeAbilityWrapperGoal<A> setActivationDistance(float dist) {
      this.activationDistance = dist;
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
   }

   public void tickWrapper() {
      this.isCharging = (Boolean)this.getAbility().getComponent((AbilityComponentKey)ModAbilityComponents.CHARGE.get()).map(ChargeComponent::isCharging).orElse(false);
      if (this.isCharging) {
         GoalHelper.lookAtEntity(this.entity, this.target);
      }

   }

   public void stopWrapper() {
   }
}
