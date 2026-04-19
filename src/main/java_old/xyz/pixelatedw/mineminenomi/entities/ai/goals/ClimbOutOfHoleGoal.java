package xyz.pixelatedw.mineminenomi.entities.ai.goals;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.AABB;
import xyz.pixelatedw.mineminenomi.api.entities.ai.TickedGoal;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;

public class ClimbOutOfHoleGoal extends TickedGoal<Mob> {
   private static final int COOLDOWN = 60;
   private LivingEntity target;
   private int blocksHeight = 0;
   private BlockPos targetPos;

   public ClimbOutOfHoleGoal(Mob entity) {
      super(entity);
   }

   public boolean m_8036_() {
      if (!GoalHelper.hasAliveTarget(this.entity)) {
         return false;
      } else if (!this.hasTimePassedSinceLastEnd(60.0F)) {
         return false;
      } else {
         this.target = this.entity.m_5448_();
         if (this.target.m_20186_() <= this.entity.m_20186_()) {
            return false;
         } else {
            this.blocksHeight = GoalHelper.getFrontWallHeight(this.entity);
            if (this.blocksHeight <= 0) {
               return false;
            } else {
               BlockPos start = this.entity.m_20183_();
               BlockPos check = start.m_121955_(this.entity.m_6350_().m_122436_());
               this.targetPos = check.m_6630_(this.blocksHeight + 1);
               if (!this.canFit(this.entity, this.targetPos)) {
                  return false;
               } else if (GoalHelper.hasSolidBlockAbove(this.entity)) {
                  return false;
               } else {
                  return GoalHelper.hasBlockInFace(this.entity);
               }
            }
         }
      }
   }

   private boolean canFit(LivingEntity entity, BlockPos pos) {
      AABB entityAABB = entity.m_20191_();
      AABB aabb = new AABB((double)pos.m_123341_() - entityAABB.m_82362_(), (double)(pos.m_123342_() + 1) - entityAABB.m_82376_(), (double)pos.m_123343_() - entityAABB.m_82385_(), (double)pos.m_123341_() + entityAABB.m_82362_(), (double)pos.m_123342_() + entityAABB.m_82376_(), (double)pos.m_123343_() + entityAABB.m_82385_());
      BlockPos.MutableBlockPos mutpos = new BlockPos.MutableBlockPos();

      for(int y = (int)aabb.f_82289_ + 1; (double)y < aabb.f_82292_ + (double)1.0F; ++y) {
         for(int x = (int)aabb.f_82288_ - 1; (double)x < aabb.f_82291_ + (double)1.0F; ++x) {
            for(int z = (int)aabb.f_82290_ - 1; (double)z < aabb.f_82293_ + (double)1.0F; ++z) {
               mutpos.m_122178_(x, y, z);
               if (!entity.m_9236_().m_8055_(mutpos).m_60795_()) {
                  return false;
               }
            }
         }
      }

      return true;
   }

   public boolean m_8045_() {
      return false;
   }

   public void m_8056_() {
      super.m_8056_();
      this.entity.m_20035_(this.targetPos, this.entity.m_146908_(), this.entity.m_146909_());
      this.entity.m_21573_().m_26573_();
   }

   public void m_8037_() {
      super.m_8037_();
   }

   public void m_8041_() {
      super.m_8041_();
   }
}
