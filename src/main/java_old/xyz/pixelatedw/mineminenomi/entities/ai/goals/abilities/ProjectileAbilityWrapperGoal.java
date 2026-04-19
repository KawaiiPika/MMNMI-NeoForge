package xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities;

import javax.annotation.Nullable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BowTriggerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.entities.ai.AbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;

public class ProjectileAbilityWrapperGoal<A extends IAbility> extends AbilityWrapperGoal<Mob, A> {
   private static final int MAX_PULL_TIME = 40;
   private LivingEntity target;
   private float minDistance = 0.0F;
   private float maxDistance = 100.0F;
   @Nullable
   protected final BowTriggerComponent bowTrigger;
   @Nullable
   protected final ChargeComponent chargeComponent;
   private int useTime = 0;

   public ProjectileAbilityWrapperGoal(Mob entity, AbilityCore<A> ability) {
      super(entity, ability);
      this.bowTrigger = (BowTriggerComponent)this.getAbility().getComponent((AbilityComponentKey)ModAbilityComponents.BOW_TRIGGER.get()).orElse((Object)null);
      this.chargeComponent = (ChargeComponent)this.getAbility().getComponent((AbilityComponentKey)ModAbilityComponents.CHARGE.get()).orElse((Object)null);
      if (this.chargeComponent != null) {
         this.chargeComponent.addStartEvent(200, (_entity, _ability) -> this.useTime = Math.round(this.chargeComponent.getMaxChargeTime()));
      }

   }

   public boolean canUseWrapper() {
      if (!GoalHelper.hasAliveTarget(this.entity)) {
         return false;
      } else {
         this.target = this.entity.m_5448_();
         if (!GoalHelper.canSee(this.entity, this.target)) {
            return false;
         } else if (this.minDistance > 0.0F && GoalHelper.isWithinDistance(this.entity, this.target, (double)this.minDistance)) {
            return false;
         } else {
            return !(this.maxDistance > 0.0F) || !GoalHelper.isOutsideDistance(this.entity, this.target, (double)this.maxDistance);
         }
      }
   }

   public boolean canContinueToUseWrapper() {
      return this.useTime > 0;
   }

   public void startWrapper() {
      GoalHelper.lookAtEntity(this.entity, this.target);
      ItemStack heldItem = this.entity.m_21205_();
      if (this.bowTrigger != null && ItemsHelper.isBowOrGun(heldItem)) {
         this.entity.m_6672_(InteractionHand.MAIN_HAND);
         this.useTime = 40;
      }

   }

   public void tickWrapper() {
      --this.useTime;
   }

   public void stopWrapper() {
      ItemStack heldItem = this.entity.m_21205_();
      if (this.useTime <= 0 && this.bowTrigger != null && ItemsHelper.isBowOrGun(heldItem)) {
         this.entity.m_21253_();
      }

   }

   public LivingEntity getTarget() {
      return this.target;
   }
}
