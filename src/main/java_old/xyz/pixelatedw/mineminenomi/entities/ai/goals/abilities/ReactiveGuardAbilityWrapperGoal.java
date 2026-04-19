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

public class ReactiveGuardAbilityWrapperGoal<A extends IAbility> extends AbilityWrapperGoal<Mob, A> {
   private static final int HURT_COOLDOWN = 100;
   private LivingEntity target;
   private float activationDistance;
   private float maxDistance = 10.0F;
   private float prevHealth;
   private int activationHits = 3;
   private int hits = 0;

   public ReactiveGuardAbilityWrapperGoal(Mob entity, AbilityCore<A> ability) {
      super(entity, ability);
      AttributeInstance attr = entity.m_21051_((Attribute)ForgeMod.ENTITY_REACH.get());
      this.activationDistance = attr != null ? (float)attr.m_22135_() : 6.0F;
   }

   public ReactiveGuardAbilityWrapperGoal<A> setActivationHits(int hits) {
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
         } else if (GoalHelper.isOutsideDistance(this.entity, this.target, (double)this.activationDistance)) {
            return false;
         } else if (this.entity.m_21213_() > 0 && this.entity.f_19797_ <= this.entity.m_21213_() + 100) {
            if (this.prevHealth == 0.0F || this.entity.m_21223_() < this.prevHealth) {
               ++this.hits;
            }

            this.prevHealth = this.entity.m_21223_();
            return this.hits >= this.activationHits;
         } else {
            return false;
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
      this.hits = 0;
   }
}
