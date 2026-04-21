package xyz.pixelatedw.mineminenomi.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.MapColor;

public class BarrierBlock extends Block {
   public BarrierBlock() {
      super(BlockBehaviour.Properties.of().mapColor(net.minecraft.world.level.material.MapColor.NONE).strength(-1.0F, 10000.0F).ignitedByLava().noOcclusion());
   }

   public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
      return true;
   }


   public boolean skipRendering(BlockState state, BlockState adjacentBlockState, net.minecraft.core.Direction side) {
      return adjacentBlockState.getBlock() == this;
   }

   public int getLightBlock(BlockState state, BlockGetter worldIn, BlockPos pos) {
      return 0;
   }
}
