package xyz.pixelatedw.mineminenomi.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.MapColor;

public class CandyBlock extends Block {
   public CandyBlock() {
      super(BlockBehaviour.Properties.of().mapColor(net.minecraft.world.level.material.MapColor.COLOR_PINK).strength(10.0F, 4.0F).ignitedByLava().noOcclusion());
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

   public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
      return 30;
   }

   public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
      return 60;
   }
}
