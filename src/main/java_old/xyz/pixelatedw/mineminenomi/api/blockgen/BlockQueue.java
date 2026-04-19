package xyz.pixelatedw.mineminenomi.api.blockgen;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import xyz.pixelatedw.mineminenomi.api.NuWorld;
import xyz.pixelatedw.mineminenomi.api.protection.BlockProtectionRule;
import xyz.pixelatedw.mineminenomi.api.util.HashBlockPos;

public class BlockQueue {
   private static Map<Level, BlockQueue> queueMap = new ConcurrentHashMap();
   private Set<BlockQueueData> queue = new HashSet();
   private final Supplier<Boolean> queueAction;
   private final ServerLevel world;
   private boolean isRunning = false;
   private int speed = 50;
   private int blocksPerTick = 0;

   public void add(BlockQueueData block) {
      this.queue.add(block);
   }

   public void add(BlockPos pos, BlockState state) {
      this.add(new BlockQueueData(pos, state, 0));
   }

   public void add(BlockPos pos, BlockState state, int flag) {
      this.add(new BlockQueueData(pos, state, flag));
   }

   public void add(HashBlockPos pos, BlockState state, int flag) {
      this.add(new BlockQueueData(pos, state, flag));
   }

   public void addAll(Set<BlockPos> set, BlockState state) {
      List<BlockQueueData> list = set.stream().map((pos) -> new BlockQueueData(pos, state, 0)).toList();
      this.queue.addAll(list);
   }

   public BlockQueue(ServerLevel world) {
      this.world = world;
      this.queueAction = () -> {
         BlockQueueData block = null;
         boolean hasTime = true;
         boolean hasBlocksToPlace = this.blocksPerTick > 0;
         long start = System.currentTimeMillis();
         int blocksPlaced = 0;
         Iterator<BlockQueueData> iter = this.queue.iterator();

         while((hasTime || hasBlocksToPlace) && iter.hasNext() && (block = (BlockQueueData)iter.next()) != null) {
            hasTime = System.currentTimeMillis() - start < (long)this.speed;
            if (this.blocksPerTick > 0) {
               hasBlocksToPlace = blocksPlaced < this.blocksPerTick;
               hasTime = hasBlocksToPlace;
            }

            boolean canPlace = block.canPlaceCheck == null || block.canPlaceCheck.check(world, block.pos, block.state);
            if (!canPlace) {
               iter.remove();
            } else {
               BlockState oldState = world.m_8055_(block.pos);
               boolean isPlaced = NuWorld.setBlockState((Level)world, block.pos, block.state, block.flag, block.protectionRule);
               if (isPlaced) {
                  if (block.afterPlaceEvent != null) {
                     block.afterPlaceEvent.placed(block.pos, oldState, block.state);
                  }

                  if (block.tileEntity != null) {
                     BlockEntity blockEntity = world.m_7702_(block.pos);
                     block.tileEntity.accept(blockEntity);
                  }

                  ++blocksPlaced;
               }

               iter.remove();
            }
         }

         return true;
      };
   }

   public BlockQueue setSpeed(int speed) {
      this.speed = speed;
      return this;
   }

   public BlockQueue setBlocksPerTick(int blocksPerTick) {
      this.blocksPerTick = blocksPerTick;
      return this;
   }

   public static BlockQueue getLevelQueue(ServerLevel world) {
      if (!queueMap.containsKey(world)) {
         BlockQueue blockQueue = new BlockQueue(world);
         queueMap.put(world, blockQueue);
         return blockQueue;
      } else {
         return (BlockQueue)queueMap.get(world);
      }
   }

   public void start() {
      this.isRunning = true;
   }

   public void stop() {
      this.isRunning = false;
   }

   public boolean isRunning() {
      return this.isRunning;
   }

   public boolean isDone() {
      return this.isRunning && this.queue.size() <= 0;
   }

   public boolean hasNext() {
      return this.queue.size() > 0;
   }

   public int getQueueSize() {
      return this.queue.size();
   }

   public void clearQueue() {
      this.queue.clear();
   }

   public void tick() {
      if (this.isRunning && this.hasNext()) {
         this.queueAction.get();
      }

   }

   public static class BlockQueueData {
      private final HashBlockPos pos;
      private final BlockState state;
      private final int flag;
      private Consumer<BlockEntity> tileEntity;
      private BlockProtectionRule protectionRule;
      private ICanPlaceBlock canPlaceCheck;
      private IAfterPlaceBlock afterPlaceEvent;

      public BlockQueueData(HashBlockPos pos, BlockState state, int flag) {
         this.tileEntity = null;
         this.protectionRule = null;
         this.canPlaceCheck = null;
         this.afterPlaceEvent = null;
         this.pos = pos;
         this.state = state;
         this.flag = flag;
      }

      public BlockQueueData(BlockPos pos, BlockState state, int flag) {
         this(HashBlockPos.of(pos), state, flag);
      }

      public BlockQueueData setTileEntity(Consumer<BlockEntity> consumer) {
         this.tileEntity = consumer;
         return this;
      }

      public BlockQueueData setCanPlaceCheck(ICanPlaceBlock canPlace) {
         this.canPlaceCheck = canPlace;
         return this;
      }

      public BlockQueueData setAfterPlaceEvent(IAfterPlaceBlock event) {
         this.afterPlaceEvent = event;
         return this;
      }

      public BlockQueueData setProtectionRule(BlockProtectionRule rule) {
         this.protectionRule = rule;
         return this;
      }

      public int hashCode() {
         return this.pos.hashCode();
      }
   }

   @FunctionalInterface
   public interface IAfterPlaceBlock {
      void placed(BlockPos var1, BlockState var2, BlockState var3);
   }

   @FunctionalInterface
   public interface ICanPlaceBlock {
      boolean check(LevelAccessor var1, BlockPos var2, BlockState var3);
   }
}
