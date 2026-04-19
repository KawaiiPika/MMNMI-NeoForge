package xyz.pixelatedw.mineminenomi.entities.ai.goals;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import xyz.pixelatedw.mineminenomi.api.entities.ai.TickedGoal;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;

public class TauntTargetGoal extends TickedGoal<Mob> {
   private LivingEntity target;
   private int lastUpdateTick = 20;

   public TauntTargetGoal(Mob entity) {
      super(entity);
   }

   public boolean m_8036_() {
      if (!GoalHelper.hasAliveTarget(this.entity)) {
         return false;
      } else {
         this.target = this.entity.m_5448_();
         if (!GoalHelper.canSee(this.entity, this.target)) {
            return false;
         } else {
            return !GoalHelper.isWithinDistance(this.entity, this.target, (double)10.0F);
         }
      }
   }

   public boolean m_8045_() {
      return GoalHelper.hasAliveTarget(this.entity);
   }

   public void m_8037_() {
      super.m_8037_();
      GoalHelper.lookAtEntity(this.entity, this.target);
      --this.lastUpdateTick;
      if (this.lastUpdateTick <= 10) {
         this.entity.m_20124_(Pose.CROUCHING);
      } else {
         this.entity.m_20124_(Pose.STANDING);
      }

      if (this.lastUpdateTick <= 0) {
         this.lastUpdateTick = 20;
      }

   }

   public void m_8041_() {
      super.m_8041_();
      this.entity.m_20124_(Pose.STANDING);
   }
}
