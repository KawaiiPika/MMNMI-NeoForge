package xyz.pixelatedw.mineminenomi.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.MapColor;

public class WaxBlock extends Block {
   public WaxBlock() {
      super(BlockBehaviour.Properties.of().mapColor(net.minecraft.world.level.material.MapColor.SNOW).strength(8.0F, 3.0F).ignitedByLava());
   }

   public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
      return 100;
   }

   public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
      return 200;
   }
}
