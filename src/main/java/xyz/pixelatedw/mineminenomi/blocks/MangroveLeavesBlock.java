package xyz.pixelatedw.mineminenomi.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class MangroveLeavesBlock extends LeavesBlock {
   private static final int DISTANCE_VALUE = 15;

   public MangroveLeavesBlock() {
      super(BlockBehaviour.Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.MANGROVE_LEAVES));
   }

   public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
      int i = getDistance(facingState) + 1;
      if (i != 1 || (Integer)state.getValue(DISTANCE) != i) {
         level.scheduleTick(currentPos, this, 1);
      }

      return state;
   }

   private static int getDistance(BlockState neighbor) {
      if (neighbor.is(BlockTags.LOGS)) {
         return 0;
      } else {
         return neighbor.getBlock() instanceof LeavesBlock ? (Integer)neighbor.getValue(DISTANCE) : 15;
      }
   }

   public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
      if (!(Boolean)state.getValue(PERSISTENT) && (Integer)state.getValue(DISTANCE) == 15) {
         dropResources(state, level, pos);
         level.removeBlock(pos, false);
      }

   }

   public boolean isRandomlyTicking(BlockState state) {
      return (Integer)state.getValue(DISTANCE) == 15 && !(Boolean)state.getValue(PERSISTENT);
   }

   public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
      return 30;
   }

   public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
      return 60;
   }
}
