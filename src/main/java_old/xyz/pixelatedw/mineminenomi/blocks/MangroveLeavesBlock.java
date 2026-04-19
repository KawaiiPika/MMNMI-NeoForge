package xyz.pixelatedw.mineminenomi.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class MangroveLeavesBlock extends LeavesBlock {
   private static final int DISTANCE_VALUE = 15;

   public MangroveLeavesBlock() {
      super(Properties.m_60926_(Blocks.f_220838_));
   }

   public BlockState m_7417_(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
      int i = m_54463_(facingState) + 1;
      if (i != 1 || (Integer)state.m_61143_(f_54418_) != i) {
         level.m_186460_(currentPos, this, 1);
      }

      return state;
   }

   private static int m_54463_(BlockState neighbor) {
      if (neighbor.m_204336_(BlockTags.f_13106_)) {
         return 0;
      } else {
         return neighbor.m_60734_() instanceof LeavesBlock ? (Integer)neighbor.m_61143_(f_54418_) : 15;
      }
   }

   public void m_213898_(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
      if (!(Boolean)state.m_61143_(f_54419_) && (Integer)state.m_61143_(f_54418_) == 15) {
         m_49950_(state, level, pos);
         level.m_7471_(pos, false);
      }

   }

   public boolean m_6724_(BlockState state) {
      return (Integer)state.m_61143_(f_54418_) == 15 && !(Boolean)state.m_61143_(f_54419_);
   }

   public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
      return 30;
   }

   public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
      return 60;
   }
}
