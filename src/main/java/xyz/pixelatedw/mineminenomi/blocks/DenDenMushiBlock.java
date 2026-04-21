package xyz.pixelatedw.mineminenomi.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class DenDenMushiBlock extends Block implements SimpleWaterloggedBlock {
   private static final VoxelShape SHAPE = Block.box((double)4.0F, (double)0.0F, (double)4.0F, (double)12.0F, (double)9.0F, (double)12.0F);
   public static final DirectionProperty FACING;
   public static final IntegerProperty TYPE;
   public static final BooleanProperty WATERLOGGED;

   public DenDenMushiBlock() {
      super(BlockBehaviour.Properties.of().strength(0.4F).noCollission());
      this.registerDefaultState((BlockState)((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(FACING, Direction.NORTH)).setValue(TYPE, 0)).setValue(WATERLOGGED, false));
   }

   public @javax.annotation.Nullable BlockState getStateForPlacement(net.minecraft.world.item.context.BlockPlaceContext context) {
      BlockState blockstate = this.defaultBlockState();
      BlockPos blockpos = context.getClickedPos();
      Direction[] adirection = context.getNearestLookingDirections();
      FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
      int type = 0;
      blockstate = (BlockState)blockstate.setValue(TYPE, type);

      for(Direction direction : adirection) {
         if (direction.getAxis().isHorizontal()) {
            Direction direction1 = direction.getOpposite();
            blockstate = (BlockState)blockstate.setValue(FACING, direction1);
            blockstate = (BlockState)blockstate.setValue(WATERLOGGED, fluidstate.is(net.minecraft.tags.FluidTags.WATER) && fluidstate.getAmount() == 8);
            if (blockstate.canSurvive(context.getLevel(), blockpos)) {
               return blockstate;
            }
         }
      }

      return null;
   }

   public net.minecraft.world.phys.shapes.VoxelShape getCollisionShape(BlockState state, BlockGetter worldIn, BlockPos pos, net.minecraft.world.phys.shapes.CollisionContext context) {
      return SHAPE;
   }

   public net.minecraft.world.phys.shapes.VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, net.minecraft.world.phys.shapes.CollisionContext context) {
      return SHAPE;
   }

   public net.minecraft.world.level.material.FluidState getFluidState(BlockState state) {
      return (Boolean)state.getValue(WATERLOGGED) ? net.minecraft.world.level.material.Fluids.WATER.defaultFluidState() : super.getFluidState(state);
   }

   public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
      if ((Boolean)stateIn.getValue(WATERLOGGED)) {
         worldIn.scheduleTick(currentPos, net.minecraft.world.level.material.Fluids.WATER, net.minecraft.world.level.material.Fluids.WATER.getTickDelay(worldIn));
      }

      return facing.getOpposite() == stateIn.getValue(FACING) && !stateIn.canSurvive(worldIn, currentPos) ? net.minecraft.world.level.block.Blocks.AIR.defaultBlockState() : stateIn;
   }

   public BlockState rotate(BlockState state, net.minecraft.world.level.block.Rotation rot) {
      return (BlockState)state.setValue(FACING, rot.rotate((Direction)state.getValue(FACING)));
   }

   public BlockState mirror(BlockState state, net.minecraft.world.level.block.Mirror mirrorIn) {
      return state.setValue(FACING, mirrorIn.mirror((Direction)state.getValue(FACING)));
   }

   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
      builder.add(new Property[]{WATERLOGGED});
      builder.add(new Property[]{FACING});
      builder.add(new Property[]{TYPE});
   }

   public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
      return true;
   }


   public boolean skipRendering(BlockState state, BlockState adjacentBlockState, net.minecraft.core.Direction side) {
      return true;
   }

   static {
      FACING = HorizontalDirectionalBlock.FACING;
      TYPE = net.minecraft.world.level.block.state.properties.IntegerProperty.create("den_type", 0, 2);
      WATERLOGGED = net.minecraft.world.level.block.state.properties.BlockStateProperties.WATERLOGGED;
   }
}
