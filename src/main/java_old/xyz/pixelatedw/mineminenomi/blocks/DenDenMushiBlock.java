package xyz.pixelatedw.mineminenomi.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

public class DenDenMushiBlock extends Block implements SimpleWaterloggedBlock {
   private static final VoxelShape SHAPE = Block.m_49796_((double)4.0F, (double)0.0F, (double)4.0F, (double)12.0F, (double)9.0F, (double)12.0F);
   public static final DirectionProperty FACING;
   public static final IntegerProperty TYPE;
   public static final BooleanProperty WATERLOGGED;

   public DenDenMushiBlock() {
      super(Properties.m_284310_().m_60978_(0.4F).m_60910_());
      this.m_49959_((BlockState)((BlockState)((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(FACING, Direction.NORTH)).m_61124_(TYPE, 0)).m_61124_(WATERLOGGED, false));
   }

   public @Nullable BlockState m_5573_(BlockPlaceContext context) {
      BlockState blockstate = this.m_49966_();
      BlockPos blockpos = context.m_8083_();
      Direction[] adirection = context.m_6232_();
      FluidState fluidstate = context.m_43725_().m_6425_(context.m_8083_());
      int type = context.m_43722_().m_41784_().m_128451_("type");
      blockstate = (BlockState)blockstate.m_61124_(TYPE, type);

      for(Direction direction : adirection) {
         if (direction.m_122434_().m_122479_()) {
            Direction direction1 = direction.m_122424_();
            blockstate = (BlockState)blockstate.m_61124_(FACING, direction1);
            blockstate = (BlockState)blockstate.m_61124_(WATERLOGGED, fluidstate.m_205070_(FluidTags.f_13131_) && fluidstate.m_76186_() == 8);
            if (blockstate.m_60710_(context.m_43725_(), blockpos)) {
               return blockstate;
            }
         }
      }

      return null;
   }

   public VoxelShape m_5939_(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
      return SHAPE;
   }

   public VoxelShape m_5940_(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
      return SHAPE;
   }

   public FluidState m_5888_(BlockState state) {
      return (Boolean)state.m_61143_(WATERLOGGED) ? Fluids.f_76193_.m_76068_(false) : super.m_5888_(state);
   }

   public BlockState m_7417_(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
      if ((Boolean)stateIn.m_61143_(WATERLOGGED)) {
         worldIn.m_186469_(currentPos, Fluids.f_76193_, Fluids.f_76193_.m_6718_(worldIn));
      }

      return facing.m_122424_() == stateIn.m_61143_(FACING) && !stateIn.m_60710_(worldIn, currentPos) ? Blocks.f_50016_.m_49966_() : stateIn;
   }

   public BlockState m_6843_(BlockState state, Rotation rot) {
      return (BlockState)state.m_61124_(FACING, rot.m_55954_((Direction)state.m_61143_(FACING)));
   }

   public BlockState m_6943_(BlockState state, Mirror mirrorIn) {
      return state.m_60717_(mirrorIn.m_54846_((Direction)state.m_61143_(FACING)));
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> builder) {
      builder.m_61104_(new Property[]{WATERLOGGED});
      builder.m_61104_(new Property[]{FACING});
      builder.m_61104_(new Property[]{TYPE});
   }

   public boolean m_7420_(BlockState state, BlockGetter reader, BlockPos pos) {
      return true;
   }

   @OnlyIn(Dist.CLIENT)
   public boolean m_6104_(BlockState state, BlockState adjacentBlockState, Direction side) {
      return true;
   }

   static {
      FACING = HorizontalDirectionalBlock.f_54117_;
      TYPE = IntegerProperty.m_61631_("den_type", 0, 2);
      WATERLOGGED = BlockStateProperties.f_61362_;
   }
}
