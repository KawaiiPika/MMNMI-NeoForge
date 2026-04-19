package xyz.pixelatedw.mineminenomi.api.blockgen;

import io.netty.util.internal.ThreadLocalRandom;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.SingleThreadedRandomSource;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.NuWorld;
import xyz.pixelatedw.mineminenomi.api.protection.BlockProtectionRule;
import xyz.pixelatedw.mineminenomi.api.util.WeightedList;

public class RandomBlockPlacer implements IBlockPlacer {
   private int[] size = new int[]{0, 0, 0};
   private boolean hollow = false;
   private int thickness = 1;
   private WeightedList<BlockState> states = new WeightedList<BlockState>(new Object[0]);
   private int flag = 3;
   private boolean coordsOnly = false;
   private @Nullable BlockProtectionRule rule = null;
   private RandomSource rand = new SingleThreadedRandomSource(ThreadLocalRandom.current().nextLong());
   private @Nullable BlockQueue blockQueue = null;
   private Set<BlockPos> placed = new HashSet();

   public RandomBlockPlacer setSize(int x) {
      return this.setSize(x, x, x);
   }

   public RandomBlockPlacer setSize(int x, int y) {
      return this.setSize(x, y, x);
   }

   public RandomBlockPlacer setSize(int x, int y, int z) {
      this.size = new int[]{x, y, z};
      return this;
   }

   public RandomBlockPlacer setHollow() {
      this.hollow = true;
      return this;
   }

   public RandomBlockPlacer setThickness(int thickness) {
      this.thickness = thickness;
      return this;
   }

   public RandomBlockPlacer addBlock(BlockState state, int weight) {
      this.states.addEntry(state, weight);
      return this;
   }

   public RandomBlockPlacer setFlag(int flag) {
      this.flag = flag;
      return this;
   }

   public RandomBlockPlacer setCoordsOnly() {
      this.coordsOnly = true;
      return this;
   }

   public RandomBlockPlacer setRule(@Nullable BlockProtectionRule rule) {
      this.rule = rule;
      return this;
   }

   public RandomBlockPlacer setBlockQueue(@Nullable BlockQueue queue) {
      this.blockQueue = queue;
      return this;
   }

   public int getSizeX() {
      return this.size[0];
   }

   public int getSizeY() {
      return this.size[1];
   }

   public int getSizeZ() {
      return this.size[2];
   }

   public boolean isHollow() {
      return this.hollow;
   }

   public int getThickness() {
      return this.thickness;
   }

   public BlockState getState() {
      BlockState state = this.states.pick(this.rand);
      return state == null ? Blocks.f_50016_.m_49966_() : state;
   }

   public int getFlag() {
      return this.flag;
   }

   public boolean onlyCoords() {
      return this.coordsOnly;
   }

   public @Nullable BlockProtectionRule getRule() {
      return this.rule;
   }

   public void generate(Level world, BlockPos pos, IBlockGenerator gen) {
      gen.generate(this, world, pos);
   }

   public void placeNext(Level world, BlockPos pos, BlockState state, int flag, @Nullable BlockProtectionRule rule) {
      if (this.blockQueue != null) {
         BlockQueue.BlockQueueData queueData = new BlockQueue.BlockQueueData(pos, this.getState(), this.getFlag());
         queueData.setAfterPlaceEvent((pos2, oldState, newState) -> this.placed.add(pos2));
         this.blockQueue.add(queueData);
      } else {
         if (NuWorld.setBlockState(world, pos, state, flag, rule)) {
            this.placed.add(pos);
         }

      }
   }

   public int countPlaced() {
      return this.placed.size();
   }

   public Set<BlockPos> getPlacedPositions() {
      return new HashSet(this.placed);
   }
}
