package xyz.pixelatedw.mineminenomi.blocks;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xyz.pixelatedw.mineminenomi.api.enums.CanvasSize;
import xyz.pixelatedw.mineminenomi.api.helpers.BlocksHelper;
import xyz.pixelatedw.mineminenomi.blocks.blockentity.WantedPosterBlockEntity;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.items.WantedPosterItem;

public class WantedPosterBlock extends Block implements EntityBlock {
   public static final DirectionProperty FACING;
   public static final EnumProperty<CanvasSize> SIZE;
   private static final Map<Direction, VoxelShape> SHAPES;

   public WantedPosterBlock() {
      super(Properties.m_284310_().m_60978_(0.2F).m_60910_().m_60960_(BlocksHelper::never));
      this.m_49959_((BlockState)((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(FACING, Direction.NORTH)).m_61124_(SIZE, CanvasSize.SMALL));
   }

   public VoxelShape m_5940_(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
      return (VoxelShape)SHAPES.get(state.m_61143_(FACING));
   }

   public boolean m_7898_(BlockState state, LevelReader world, BlockPos pos) {
      Direction direction = (Direction)state.m_61143_(FACING);
      BlockState supportState = world.m_8055_(pos.m_121955_(direction.m_122424_().m_122436_()));
      return supportState.m_60783_(world, pos, direction);
   }

   @Nullable
   public BlockState m_5573_(BlockPlaceContext context) {
      BlockState blockstate = this.m_49966_();
      BlockPos blockpos = context.m_8083_();
      Direction[] adirection = context.m_6232_();
      CanvasSize size = CanvasSize.values()[context.m_43722_().m_41784_().m_128451_("canvasSize")];
      blockstate = (BlockState)blockstate.m_61124_(SIZE, size);

      for(Direction direction : adirection) {
         if (direction.m_122434_().m_122479_()) {
            Direction direction1 = direction.m_122424_();
            blockstate = (BlockState)blockstate.m_61124_(FACING, direction1);
            if (blockstate.m_60710_(context.m_43725_(), blockpos)) {
               return blockstate;
            }
         }
      }

      return null;
   }

   public BlockState m_7417_(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
      return facing.m_122424_() == stateIn.m_61143_(FACING) && !stateIn.m_60710_(worldIn, currentPos) ? Blocks.f_50016_.m_49966_() : stateIn;
   }

   public BlockState m_6843_(BlockState state, Rotation rot) {
      return (BlockState)state.m_61124_(FACING, rot.m_55954_((Direction)state.m_61143_(FACING)));
   }

   public BlockState m_6943_(BlockState state, Mirror mirrorIn) {
      return state.m_60717_(mirrorIn.m_54846_((Direction)state.m_61143_(FACING)));
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> builder) {
      builder.m_61104_(new Property[]{FACING});
      builder.m_61104_(new Property[]{SIZE});
   }

   public boolean m_7420_(BlockState state, BlockGetter reader, BlockPos pos) {
      return true;
   }

   @OnlyIn(Dist.CLIENT)
   public boolean m_6104_(BlockState state, BlockState adjacentBlockState, Direction side) {
      return true;
   }

   public void m_6240_(Level level, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity tile, ItemStack stack) {
      ItemStack poster = ((Block)ModBlocks.WANTED_POSTER.get()).m_5456_().m_7968_();
      CompoundTag nbt = new CompoundTag();
      if (tile != null) {
         WantedPosterBlockEntity posterTile = (WantedPosterBlockEntity)tile;
         nbt.m_128365_("WPData", posterTile.getWantedPosterData().write());
      }

      poster.m_41751_(nbt);
      WantedPosterItem.setCanvasSize(poster, (CanvasSize)state.m_61143_(SIZE));
      Block.m_49840_(level, pos, poster);
   }

   public InteractionResult m_6227_(BlockState state, Level world, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
      return InteractionResult.FAIL;
   }

   public BlockEntity m_142194_(BlockPos pos, BlockState state) {
      return new WantedPosterBlockEntity(pos, state);
   }

   static {
      FACING = BlockStateProperties.f_61372_;
      SIZE = BlocksHelper.CANVAS_SIZE;
      SHAPES = Maps.newEnumMap(ImmutableMap.of(Direction.NORTH, Block.m_49796_((double)0.0F, (double)-2.5F, (double)14.0F, (double)16.0F, (double)18.5F, (double)16.0F), Direction.SOUTH, Block.m_49796_((double)0.0F, (double)-2.5F, (double)0.0F, (double)16.0F, (double)18.5F, (double)2.0F), Direction.EAST, Block.m_49796_((double)0.0F, (double)-2.5F, (double)0.0F, (double)2.0F, (double)18.5F, (double)16.0F), Direction.WEST, Block.m_49796_((double)14.0F, (double)-2.5F, (double)0.0F, (double)16.0F, (double)18.5F, (double)16.0F)));
   }
}
