package xyz.pixelatedw.mineminenomi.api.abilities.components;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.blockgen.BlockQueue;
import xyz.pixelatedw.mineminenomi.api.protection.BlockProtectionRule;
import xyz.pixelatedw.mineminenomi.api.util.HashBlockPos;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;

public class BlockTrackerComponent extends AbilityComponent<IAbility> {
   private Set<BlockPos> positions = new HashSet();
   private Set<HashBlockPos> queuePositions = new HashSet();
   private BlockQueue blockQueue;
   private boolean runBlockQueueUntilFinished;

   public BlockTrackerComponent(IAbility ability) {
      super((AbilityComponentKey)ModAbilityComponents.BLOCK_TRACKER.get(), ability);
   }

   protected void doTick(LivingEntity entity) {
      Level level = entity.m_9236_();
      if (level != null && !level.f_46443_) {
         if (this.blockQueue != null && this.blockQueue.hasNext() && this.runBlockQueueUntilFinished) {
            this.tickBlockQueue(entity.m_9236_());
            if (!this.blockQueue.hasNext()) {
               this.runBlockQueueUntilFinished = false;
            }
         }

      }
   }

   public void addBlockPos(BlockPos pos) {
      this.positions.add(pos);
   }

   public void addPositions(Set<BlockPos> set) {
      this.positions.addAll(set);
   }

   public void clearPositions() {
      this.positions.clear();
   }

   public Set<BlockPos> getPositions() {
      return this.positions;
   }

   @Nullable
   public BlockQueue getQueue() {
      return this.blockQueue;
   }

   public void initQueue(Level level, int blocksPerTick) {
      if (level instanceof ServerLevel serverLevel) {
         if (this.blockQueue == null) {
            this.blockQueue = new BlockQueue(serverLevel);
         }

         this.blockQueue.clearQueue();
         this.blockQueue.setBlocksPerTick(blocksPerTick);
         this.blockQueue.start();
         this.queuePositions.clear();
      }
   }

   public void addBlockToQueue(HashBlockPos pos, BlockState state, int flag) {
      this.addBlockToQueue(pos, state, flag, (BlockProtectionRule)null, (BlockQueue.IAfterPlaceBlock)null);
   }

   public void addBlockToQueue(HashBlockPos pos, BlockState state, int flag, @Nullable BlockProtectionRule protectionRule, @Nullable BlockQueue.IAfterPlaceBlock callback) {
      this.ensureQueueIsInitialized();
      BlockQueue.BlockQueueData queueData = new BlockQueue.BlockQueueData(pos, state, flag);
      queueData.setProtectionRule(protectionRule);
      if (callback != null) {
         queueData.setAfterPlaceEvent((pos2, oldState, newState) -> {
            this.queuePositions.add(pos);
            callback.placed(pos2, oldState, newState);
         });
      }

      this.blockQueue.add(queueData);
   }

   public void runBlockQueueUntilFinished() {
      this.runBlockQueueUntilFinished = true;
   }

   public void tickBlockQueue(Level level) {
      if (!level.f_46443_) {
         this.ensureQueueIsInitialized();
         if (this.blockQueue != null) {
            this.blockQueue.tick();
         }

      }
   }

   public List<BlockPos> getNearbyBlockPositions(BlockPos pos, LevelAccessor world, int radius, @Nullable TagKey<Block> tag) {
      return this.getNearbyBlockPositions(pos, world, radius, radius, tag);
   }

   public List<BlockPos> getNearbyBlockPositions(BlockPos pos, LevelAccessor world, int radius, int depth, @Nullable TagKey<Block> tag) {
      List<BlockPos> set = new ArrayList();
      BlockPos.MutableBlockPos mutpos = new BlockPos.MutableBlockPos();

      for(int x = -radius; x <= radius; ++x) {
         for(int y = -depth; y <= depth; ++y) {
            for(int z = -radius; z <= radius; ++z) {
               mutpos.m_122178_(pos.m_123341_() + x, pos.m_123342_() + y, pos.m_123343_() + z);
               BlockState state = world.m_8055_(mutpos);
               if (!state.m_60795_() && (tag == null || state.m_204336_(tag))) {
                  set.add(mutpos.m_7949_());
               }
            }
         }
      }

      return set;
   }

   public void ensureQueueIsInitialized() {
      boolean hasQueue = this.blockQueue != null;
      if (!hasQueue) {
         throw new RuntimeException("Block Queue was not initialized!");
      }
   }
}
