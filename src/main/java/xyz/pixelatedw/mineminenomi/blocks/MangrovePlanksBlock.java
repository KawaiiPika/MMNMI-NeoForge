package xyz.pixelatedw.mineminenomi.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class MangrovePlanksBlock extends Block {
   public MangrovePlanksBlock() {
      super(BlockBehaviour.Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.MANGROVE_PLANKS));
   }

   public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
      return 5;
   }

   public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
      return 20;
   }
}
