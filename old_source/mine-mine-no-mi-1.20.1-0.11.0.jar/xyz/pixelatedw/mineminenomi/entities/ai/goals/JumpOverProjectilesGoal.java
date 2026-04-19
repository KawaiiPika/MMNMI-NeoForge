package xyz.pixelatedw.mineminenomi.entities.ai.goals;

import com.google.common.base.Predicates;
import java.util.function.Predicate;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.entities.ai.TickedGoal;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;
import xyz.pixelatedw.mineminenomi.init.ModEntityPredicates;

public class JumpOverProjectilesGoal extends TickedGoal<Mob> {
   private static final float CHECK_AREA = 60.0F;
   private float cooldown;
   private float jumpHeight;
   private Projectile projectileTarget;
   private int hits;
   private int triggerHits = 3;
   private double previousDistance = (double)0.0F;
   private Predicate<LivingEntity> canUseTest = Predicates.alwaysTrue();

   public JumpOverProjectilesGoal(Mob entity, float cooldown, float jumpHeight) {
      super(entity);
      this.cooldown = cooldown;
      this.jumpHeight = jumpHeight;
   }

   public boolean m_8036_() {
      if (!GoalHelper.hasAliveTarget(this.entity)) {
         return false;
      } else if (!this.hasTimePassedSinceLastEnd(this.cooldown)) {
         return false;
      } else if (AbilityUseConditions.canUseMomentumAbilities(this.entity).isFail()) {
         return false;
      } else {
         for(Projectile proj : WyHelper.getNearbyEntities(this.entity.m_20182_(), this.entity.m_9236_(), (double)60.0F, (Predicate)null, Projectile.class)) {
            boolean isEnemyProjectile = ModEntityPredicates.getEnemyFactions(this.entity).test(proj.m_19749_());
            if (isEnemyProjectile) {
               this.projectileTarget = proj;
               double distance = proj.m_20280_(this.entity);
               if (this.previousDistance == (double)0.0F) {
                  this.previousDistance = distance;
               }

               if (distance < this.previousDistance) {
                  ++this.hits;
                  this.previousDistance = distance;
               }
               break;
            }
         }

         if (this.hits < this.triggerHits) {
            return false;
         } else if (this.projectileTarget == null) {
            return false;
         } else {
            return this.canUseTest.test(this.entity);
         }
      }
   }

   public boolean m_8045_() {
      return false;
   }

   public void m_8056_() {
      super.m_8056_();
      GoalHelper.lookAtEntity(this.entity, this.projectileTarget);
      Vec3 lookVec = this.entity.m_20154_().m_82542_((double)3.0F, (double)0.0F, (double)3.0F);
      AbilityHelper.setDeltaMovement(this.entity, lookVec.f_82479_, (double)this.jumpHeight, lookVec.f_82481_);
   }

   public void m_8037_() {
      super.m_8037_();
   }

   public void m_8041_() {
      super.m_8041_();
      this.hits = 0;
      this.previousDistance = (double)0.0F;
   }

   public void setCanUseTest(Predicate<LivingEntity> test) {
      this.canUseTest = test;
   }
}
