package xyz.pixelatedw.mineminenomi.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class DialBlock extends Block implements SimpleWaterloggedBlock {
   public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
   public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

   public DialBlock() {
      super(net.minecraft.world.level.block.state.BlockBehaviour.Properties.of().strength(0.5F).noCollission().sound(net.minecraft.world.level.block.SoundType.METAL));
      this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, false));
   }

   public DialBlock(net.minecraft.world.level.block.state.BlockBehaviour.Properties prop) {
      super(prop.noCollission());
      this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, false));
   }

   @Override
   public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
      switch (state.getValue(FACING)) {
         case EAST:
            return Block.box(0.0D, 3.0D, 3.0D, 2.0D, 13.0D, 13.0D);
         case WEST:
            return Block.box(14.0D, 3.0D, 3.0D, 16.0D, 13.0D, 13.0D);
         case SOUTH:
            return Block.box(3.0D, 3.0D, 0.0D, 13.0D, 13.0D, 2.0D);
         case NORTH:
         default:
            return Block.box(3.0D, 3.0D, 14.0D, 13.0D, 13.0D, 16.0D);
      }
   }

   @Override
   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
      builder.add(FACING, WATERLOGGED);
   }

   @Override
   public BlockState getStateForPlacement(BlockPlaceContext ctx) {
      FluidState fluidstate = ctx.getLevel().getFluidState(ctx.getClickedPos());
      return this.defaultBlockState().setValue(FACING, ctx.getHorizontalDirection().getOpposite()).setValue(WATERLOGGED, fluidstate.is(net.minecraft.tags.FluidTags.WATER) && fluidstate.getAmount() == 8);
   }

   @Override
   public FluidState getFluidState(BlockState state) {
      return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
   }

   @Override
   public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
      if (stateIn.getValue(WATERLOGGED)) {
         worldIn.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(worldIn));
      }
      return super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
   }
}
