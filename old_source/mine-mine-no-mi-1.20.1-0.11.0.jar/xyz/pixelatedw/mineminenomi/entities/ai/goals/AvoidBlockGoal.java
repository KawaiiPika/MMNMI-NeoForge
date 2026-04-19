package xyz.pixelatedw.mineminenomi.entities.ai.goals;

import com.google.common.collect.Sets;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.WyHelper;

public class AvoidBlockGoal extends Goal {
   private PathfinderMob entity;
   private Vec3 toAvoid;
   private Set<Block> blocksToAvoid = Sets.newHashSet();
   private TagKey<Block> tagToAvoid;
   private Set<BlockPos> cachedPositions = new HashSet();
   private int radius = 15;
   private long lastCheck;
   private Path path;
   private final PathNavigation pathNav;
   private double speed = (double)2.0F;
   private double sprintSpeed = (double)4.0F;

   public AvoidBlockGoal(PathfinderMob entity, Set<Block> toAvoid) {
      this.entity = entity;
      this.blocksToAvoid = toAvoid;
      this.pathNav = entity.m_21573_();
   }

   public AvoidBlockGoal(PathfinderMob entity, TagKey<Block> toAvoid) {
      this.entity = entity;
      this.tagToAvoid = toAvoid;
      this.pathNav = entity.m_21573_();
   }

   public void setSpeed(double speed) {
      this.speed = speed;
   }

   public void setSprintSpeed(double speed) {
      this.sprintSpeed = speed;
   }

   public boolean m_8036_() {
      if (this.entity != null && this.entity.m_6084_()) {
         long now = System.currentTimeMillis();
         if (now - this.lastCheck < 1000L) {
            return false;
         } else if (this.cachedPositions.size() > 0) {
            Iterator<BlockPos> positions = this.cachedPositions.iterator();

            while(positions.hasNext()) {
               BlockPos pos = (BlockPos)positions.next();
               boolean isSameBlock = false;
               isSameBlock |= this.entity.m_9236_().m_8055_(pos).m_204336_(this.tagToAvoid);
               isSameBlock |= this.blocksToAvoid.contains(this.entity.m_9236_().m_8055_(pos).m_60734_());
               if (isSameBlock && this.entity.m_20275_((double)pos.m_123341_(), (double)pos.m_123342_(), (double)pos.m_123343_()) < (double)(this.radius * this.radius)) {
                  this.toAvoid = DefaultRandomPos.m_148412_(this.entity, 128, 64, new Vec3((double)pos.m_123341_(), (double)pos.m_123342_(), (double)pos.m_123343_()), (Math.PI / 2D));
                  if (this.toAvoid == null) {
                     return false;
                  }

                  this.path = this.pathNav.m_26524_(this.toAvoid.f_82479_, this.toAvoid.f_82480_, this.toAvoid.f_82481_, 0);
                  return this.path != null;
               }

               if (!isSameBlock) {
                  positions.remove();
               }
            }

            return false;
         } else {
            List<BlockPos> blocks = WyHelper.getNearbyBlocks(this.entity.m_20183_(), this.entity.m_9236_(), this.radius, this.radius, this.radius, (b) -> this.blocksToAvoid.contains(b.m_60734_()) || b.m_204336_(this.tagToAvoid));
            this.lastCheck = System.currentTimeMillis();
            this.cachedPositions.clear();
            this.cachedPositions.addAll(blocks);
            return false;
         }
      } else {
         return false;
      }
   }

   public boolean m_8045_() {
      return !this.pathNav.m_26571_();
   }

   public void m_8056_() {
      this.pathNav.m_26536_(this.path, this.speed);
   }

   public void m_8041_() {
      this.toAvoid = null;
   }

   public void m_8037_() {
      if (this.entity.m_20238_(this.toAvoid) < (double)49.0F) {
         this.entity.m_21573_().m_26517_(this.sprintSpeed);
      } else {
         this.entity.m_21573_().m_26517_(this.speed);
      }

      this.pathNav.m_26536_(this.path, this.speed);
   }
}
