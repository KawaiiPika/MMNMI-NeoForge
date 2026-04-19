package xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraftforge.common.ForgeMod;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.ai.AbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;

public class GrabAbilityWrapperGoal<A extends IAbility> extends AbilityWrapperGoal<Mob, A> {
   private LivingEntity target;
   private float activationDistance;
   private float maxDistance = 30.0F;

   public GrabAbilityWrapperGoal(Mob entity, AbilityCore<A> ability) {
      super(entity, ability);
      AttributeInstance attr = entity.m_21051_((Attribute)ForgeMod.ENTITY_REACH.get());
      this.activationDistance = attr != null ? (float)attr.m_22135_() : 6.0F;
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
      GoalHelper.lookAtEntity(this.entity, this.target);
   }

   public void tickWrapper() {
      this.entity.m_21573_().m_26573_();
      GoalHelper.lookAtEntity(this.entity, this.target);
   }

   public void stopWrapper() {
   }
}
