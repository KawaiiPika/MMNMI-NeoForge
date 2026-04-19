package xyz.pixelatedw.mineminenomi.api.blockgen;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.NuWorld;
import xyz.pixelatedw.mineminenomi.api.protection.BlockProtectionRule;

public class SimpleBlockPlacer implements IBlockPlacer {
   private int[] size = new int[]{0, 0, 0};
   private boolean hollow = false;
   private int thickness = 1;
   private BlockState state;
   private int flag;
   private boolean coordsOnly;
   private @Nullable BlockProtectionRule rule;
   private @Nullable BlockQueue blockQueue;
   private @Nullable Predicate<BlockPos> posPredicate;
   private Set<BlockPos> placed;

   public SimpleBlockPlacer() {
      this.state = Blocks.f_50016_.m_49966_();
      this.flag = 3;
      this.coordsOnly = false;
      this.rule = null;
      this.blockQueue = null;
      this.posPredicate = null;
      this.placed = new HashSet();
   }

   public SimpleBlockPlacer setSize(int x) {
      return this.setSize(x, x, x);
   }

   public SimpleBlockPlacer setSize(int x, int y) {
      return this.setSize(x, y, x);
   }

   public SimpleBlockPlacer setSize(int x, int y, int z) {
      this.size = new int[]{x, y, z};
      return this;
   }

   public SimpleBlockPlacer setHollow() {
      this.hollow = true;
      return this;
   }

   public SimpleBlockPlacer setThickness(int thickness) {
      this.thickness = thickness;
      return this;
   }

   public SimpleBlockPlacer setBlock(BlockState state) {
      this.state = state;
      return this;
   }

   public SimpleBlockPlacer setFlag(int flag) {
      this.flag = flag;
      return this;
   }

   public SimpleBlockPlacer setCoordsOnly() {
      this.coordsOnly = true;
      return this;
   }

   public SimpleBlockPlacer setRule(@Nullable BlockProtectionRule rule) {
      this.rule = rule;
      return this;
   }

   public SimpleBlockPlacer setBlockQueue(@Nullable BlockQueue queue) {
      this.blockQueue = queue;
      return this;
   }

   public SimpleBlockPlacer setPositionPredicate(@Nullable Predicate<BlockPos> pred) {
      this.posPredicate = pred;
      return this;
   }

   public @Nullable BlockQueue getBlockQueue() {
      return this.blockQueue;
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
      return this.state;
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
      this.cleanPlacedPositions();
      gen.generate(this, world, pos);
   }

   public void placeNext(Level world, BlockPos pos, BlockState state, int flag, @Nullable BlockProtectionRule rule) {
      if (this.posPredicate == null || this.posPredicate.test(pos)) {
         if (this.blockQueue != null) {
            BlockQueue.BlockQueueData queueData = new BlockQueue.BlockQueueData(pos, this.getState(), this.getFlag());
            queueData.setAfterPlaceEvent((pos2, oldState, newState) -> this.placed.add(pos2));
            queueData.setProtectionRule(this.getRule());
            this.blockQueue.add(queueData);
         } else {
            boolean placed = NuWorld.setBlockState(world, pos, state, flag, rule);
            if (placed) {
               this.placed.add(pos);
            }

         }
      }
   }

   public int countPlaced() {
      return this.placed.size();
   }

   public void cleanPlacedPositions() {
      this.placed.clear();
   }

   public Set<BlockPos> getPlacedPositions() {
      return this.placed;
   }
}
