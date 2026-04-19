package xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities;

import java.util.function.Predicate;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.projectile.Projectile;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.ai.AbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;
import xyz.pixelatedw.mineminenomi.init.ModEntityPredicates;

public class AntiProjectileAbilityWrapperGoal<A extends IAbility> extends AbilityWrapperGoal<Mob, A> {
   private static final float CHECK_AREA = 40.0F;
   private Projectile projectileTarget;
   private int hits;
   private int triggerHits = 3;
   private double previousDistance = (double)0.0F;

   public AntiProjectileAbilityWrapperGoal(Mob entity, AbilityCore<A> ability) {
      super(entity, ability);
   }

   public boolean canUseWrapper() {
      if (!GoalHelper.hasAliveTarget(this.entity)) {
         return false;
      } else if (!GoalHelper.canMove(this.entity)) {
         return false;
      } else {
         for(Projectile proj : WyHelper.getNearbyEntities(this.entity.m_20182_(), this.entity.m_9236_(), (double)40.0F, (Predicate)null, Projectile.class)) {
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

         return this.hits >= this.triggerHits;
      }
   }

   public boolean canContinueToUseWrapper() {
      return false;
   }

   public void startWrapper() {
      GoalHelper.lookAtEntity(this.entity, this.projectileTarget);
   }

   public void tickWrapper() {
   }

   public void stopWrapper() {
      this.hits = 0;
   }
}
