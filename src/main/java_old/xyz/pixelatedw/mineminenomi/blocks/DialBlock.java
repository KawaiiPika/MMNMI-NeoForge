package xyz.pixelatedw.mineminenomi.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class DialBlock extends HorizontalDirectionalBlock implements SimpleWaterloggedBlock {
   protected static final VoxelShape SHAPE = Block.m_49796_((double)4.0F, (double)0.0F, (double)4.0F, (double)12.0F, (double)4.0F, (double)12.0F);
   public static final BooleanProperty WATERLOGGED;

   public DialBlock() {
      super(Properties.m_284310_().m_60978_(0.2F));
      this.m_49959_((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(WATERLOGGED, false));
   }

   public DialBlock(BlockBehaviour.Properties props) {
      super(props);
      this.m_49959_((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(WATERLOGGED, false));
   }

   public VoxelShape m_5940_(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
      return SHAPE;
   }

   public boolean m_7420_(BlockState state, BlockGetter reader, BlockPos pos) {
      return true;
   }

   @OnlyIn(Dist.CLIENT)
   public boolean m_6104_(BlockState state, BlockState adjacentBlockState, Direction side) {
      return true;
   }

   public int m_7753_(BlockState state, BlockGetter worldIn, BlockPos pos) {
      return 0;
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> builder) {
      builder.m_61104_(new Property[]{WATERLOGGED});
      builder.m_61104_(new Property[]{f_54117_});
   }

   public BlockState m_7417_(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
      if ((Boolean)stateIn.m_61143_(WATERLOGGED)) {
         worldIn.m_186469_(currentPos, Fluids.f_76193_, Fluids.f_76193_.m_6718_(worldIn));
      }

      return super.m_7417_(stateIn, facing, facingState, worldIn, currentPos, facingPos);
   }

   public FluidState m_5888_(BlockState state) {
      return (Boolean)state.m_61143_(WATERLOGGED) ? Fluids.f_76193_.m_76068_(false) : super.m_5888_(state);
   }

   public BlockState m_5573_(BlockPlaceContext ctx) {
      FluidState fluidstate = ctx.m_43725_().m_6425_(ctx.m_8083_());
      return (BlockState)((BlockState)this.m_49966_().m_61124_(f_54117_, ctx.m_8125_())).m_61124_(WATERLOGGED, fluidstate.m_205070_(FluidTags.f_13131_) && fluidstate.m_76186_() == 8);
   }

   static {
      WATERLOGGED = BlockStateProperties.f_61362_;
   }
}
