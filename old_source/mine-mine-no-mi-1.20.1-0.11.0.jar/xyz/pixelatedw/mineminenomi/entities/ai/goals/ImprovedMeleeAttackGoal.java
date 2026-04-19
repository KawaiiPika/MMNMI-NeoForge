package xyz.pixelatedw.mineminenomi.entities.ai.goals;

import java.util.function.Supplier;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;

public class ImprovedMeleeAttackGoal extends MeleeAttackGoal {
   private boolean moveTowardsTarget = true;
   private int attackInterval = 20;
   private int ticksUntilNextAttack;
   private boolean longMemory;
   private Supplier<Boolean> earlyStop = () -> false;

   public ImprovedMeleeAttackGoal(PathfinderMob entity, double speed, boolean useLongMemory) {
      super(entity, speed, useLongMemory);
      this.longMemory = useLongMemory;
   }

   public ImprovedMeleeAttackGoal setAttackInterval(int interval) {
      this.attackInterval = interval;
      return this;
   }

   public ImprovedMeleeAttackGoal setMoveTowardsTarget(boolean flag) {
      this.moveTowardsTarget = flag;
      return this;
   }

   public ImprovedMeleeAttackGoal setEarlyStop(Supplier<Boolean> earlyStop) {
      this.earlyStop = earlyStop;
      return this;
   }

   public boolean m_8036_() {
      if (!super.m_8036_()) {
         return false;
      } else if (!GoalHelper.hasAliveTarget(this.f_25540_)) {
         return false;
      } else {
         LivingEntity target = this.f_25540_.m_5448_();
         if (!EntitySelector.f_20406_.test(target)) {
            return false;
         } else if (this.f_25540_.m_20089_() != Pose.CROUCHING && this.f_25540_.m_20089_() != Pose.DYING && this.f_25540_.m_20089_() != Pose.SLEEPING) {
            return !(Boolean)this.earlyStop.get();
         } else {
            return false;
         }
      }
   }

   public boolean m_8045_() {
      LivingEntity target = this.f_25540_.m_5448_();
      if (target != null && target.m_6084_()) {
         if ((Boolean)this.earlyStop.get()) {
            return false;
         } else if (!EntitySelector.f_20406_.test(target)) {
            return false;
         } else if (target.m_20145_()) {
            return false;
         } else if (this.f_25540_.m_20089_() != Pose.CROUCHING && this.f_25540_.m_20089_() != Pose.DYING && this.f_25540_.m_20089_() != Pose.SLEEPING) {
            return this.longMemory || !this.f_25540_.m_21573_().m_26571_();
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public void m_8056_() {
      super.m_8056_();
      this.ticksUntilNextAttack = 0;
   }

   public void m_8037_() {
      LivingEntity target = this.f_25540_.m_5448_();
      boolean isInvisible = target != null && target.m_21023_(MobEffects.f_19609_);
      if (target != null && target.m_6084_()) {
         boolean lastAttackIsStale = EntitySelector.f_20406_.test(target) && this.f_25540_.m_20280_(target) < (double)5.0F && this.f_25540_.f_19797_ >= this.f_25540_.m_21215_() + 100;
         if (lastAttackIsStale && target.m_20096_() && this.f_25540_.m_20096_()) {
            Vec3 look = this.f_25540_.m_20154_().m_82542_(0.4, 0.4, 0.4);
            AbilityHelper.setDeltaMovement(this.f_25540_, this.f_25540_.m_20184_().m_82549_(look));
         }

         if (isInvisible) {
            this.f_25540_.m_6710_((LivingEntity)null);
         } else {
            super.m_8037_();
            if (!this.moveTowardsTarget) {
               this.f_25540_.m_21573_().m_26573_();
            }

            this.ticksUntilNextAttack = Math.max(this.ticksUntilNextAttack - 1, 0);
         }
      }
   }

   protected void m_6739_(LivingEntity enemy, double distToEnemySqr) {
      if (GoalHelper.canSee(this.f_25540_, enemy)) {
         double d0 = this.m_6639_(enemy);
         if (distToEnemySqr <= d0 && this.m_25564_()) {
            this.m_25563_();
            this.f_25540_.m_6674_(InteractionHand.MAIN_HAND);
            this.f_25540_.m_7327_(enemy);
         }

      }
   }

   protected boolean m_25564_() {
      return this.ticksUntilNextAttack <= 0;
   }

   protected void m_25563_() {
      super.m_25563_();
      this.ticksUntilNextAttack = this.m_25566_();
   }

   protected int m_25566_() {
      return this.attackInterval;
   }

   protected double m_6639_(LivingEntity target) {
      AttributeInstance attr = this.f_25540_.m_21051_((Attribute)ForgeMod.ENTITY_REACH.get());
      double attrReach = (double)1.0F;
      if (attr != null) {
         attrReach = attr.m_22135_();
         attrReach = Math.max((double)1.0F, attrReach);
      }

      return (double)(this.f_25540_.m_20205_() * 2.0F * this.f_25540_.m_20205_() * 2.0F + target.m_20205_()) * attrReach;
   }
}
