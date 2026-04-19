package xyz.pixelatedw.mineminenomi.entities.ai.goals;

import com.google.common.base.Predicates;
import java.util.function.Predicate;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.entities.ai.TickedGoal;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;
import xyz.pixelatedw.mineminenomi.init.ModEntityPredicates;

public class DashDodgeProjectilesGoal extends TickedGoal<Mob> {
   private static final float CHECK_AREA = 40.0F;
   private float ticksBetweenDashes;
   private float dashDistance;
   private NuProjectileEntity projectileTarget;
   private int hits;
   private int triggerHits = 3;
   private double previousDistance = (double)0.0F;
   private Predicate<LivingEntity> canUseTest = Predicates.alwaysTrue();

   public DashDodgeProjectilesGoal(Mob entity, float ticksBetweenDashes, float dashDistance) {
      super(entity);
      this.ticksBetweenDashes = ticksBetweenDashes;
      this.dashDistance = dashDistance;
   }

   public boolean m_8036_() {
      if (!GoalHelper.hasAliveTarget(this.entity)) {
         return false;
      } else if (!this.hasTimePassedSinceLastEnd(this.ticksBetweenDashes)) {
         return false;
      } else if (!GoalHelper.canMove(this.entity)) {
         return false;
      } else {
         for(NuProjectileEntity proj : WyHelper.getNearbyEntities(this.entity.m_20182_(), this.entity.m_9236_(), (double)40.0F, (Predicate)null, NuProjectileEntity.class)) {
            boolean isEnemyProjectile = ModEntityPredicates.getEnemyFactions(this.entity).test(proj.getOwner());
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
      boolean dodgeRight = this.entity.m_217043_().m_188499_();
      double angle = dodgeRight ? (double)-45.0F : (double)135.0F;
      double yRot = Mth.m_14175_((double)this.entity.m_146908_() * (Math.PI / 180D)) + Math.toRadians(angle);
      double xp = (double)this.dashDistance * Math.cos(yRot) - (double)this.dashDistance * Math.sin(yRot);
      double zp = (double)this.dashDistance * Math.cos(yRot) + (double)this.dashDistance * Math.sin(yRot);
      AbilityHelper.setDeltaMovement(this.entity, -xp, 0.1, -zp);
   }

   public void m_8037_() {
      super.m_8037_();
   }

   public void m_8041_() {
      super.m_8041_();
      this.hits = 0;
      this.previousDistance = (double)0.0F;
   }

   public DashDodgeProjectilesGoal setCanUseTest(Predicate<LivingEntity> test) {
      this.canUseTest = test;
      return this;
   }
}
