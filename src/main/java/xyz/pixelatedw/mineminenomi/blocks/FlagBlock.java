package xyz.pixelatedw.mineminenomi.blocks;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import xyz.pixelatedw.mineminenomi.api.enums.CanvasSize;
import xyz.pixelatedw.mineminenomi.blocks.blockentity.FlagBlockEntity;
import xyz.pixelatedw.mineminenomi.init.ModBlockEntities;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;

public class FlagBlock extends Block implements EntityBlock {
   public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
   public static final EnumProperty<CanvasSize> SIZE = EnumProperty.create("size", CanvasSize.class);
   private static final Map<Direction, VoxelShape> SHAPES;

   public FlagBlock() {
      super(net.minecraft.world.level.block.state.BlockBehaviour.Properties.of().strength(0.2F).ignitedByLava());
      this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(SIZE, CanvasSize.SMALL));
   }

   @Override
   public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
      return net.minecraft.world.phys.shapes.Shapes.block();
   }

   @Override
   public VoxelShape getCollisionShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
      return net.minecraft.world.phys.shapes.Shapes.empty();
   }

   @Override
   public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
      Direction direction = state.getValue(FACING);
      BlockState supportState = world.getBlockState(pos.relative(direction.getOpposite()));
      return supportState.isSolid();
   }

   @Nullable
   @Override
   public BlockState getStateForPlacement(BlockPlaceContext context) {
      BlockState blockstate = this.defaultBlockState();
      BlockPos blockpos = context.getClickedPos();
      Direction[] adirection = context.getNearestLookingDirections();
      CanvasSize size = CanvasSize.SMALL;
      blockstate = blockstate.setValue(SIZE, size);

      for(Direction direction : adirection) {
         if (direction.getAxis().isHorizontal()) {
            Direction direction1 = direction.getOpposite();
            blockstate = blockstate.setValue(FACING, direction1);
            if (blockstate.canSurvive(context.getLevel(), blockpos)) {
               return blockstate;
            }
         }
      }

      return null;
   }

   @Override
   public void onProjectileHit(Level level, BlockState state, BlockHitResult hit, Projectile projectile) {
   }

   @Override
   public void setPlacedBy(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
   }

   @Override
   public void playerDestroy(Level level, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity tile, ItemStack stack) {
      super.playerDestroy(level, player, pos, state, tile, stack);
   }

   @Override
   public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
      return facing.getOpposite() == stateIn.getValue(FACING) && !stateIn.canSurvive(worldIn, currentPos) ? net.minecraft.world.level.block.Blocks.AIR.defaultBlockState() : stateIn;
   }

   @Override
   public BlockState rotate(BlockState state, Rotation rot) {
      return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
   }

   @Override
   public BlockState mirror(BlockState state, Mirror mirrorIn) {
      return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
   }

   @Override
   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
      builder.add(FACING);
      builder.add(SIZE);
   }

   @Override
   public InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
      return InteractionResult.FAIL;
   }

   @Nullable
   @Override
   public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
      return new FlagBlockEntity(pos, state);
   }

   @Nullable
   @Override
   @SuppressWarnings("unchecked")
   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
      if (level.isClientSide || blockEntityType != ModBlockEntities.FLAG.get()) return null;
      return (BlockEntityTicker<T>) (BlockEntityTicker<FlagBlockEntity>) FlagBlockEntity::tick;
   }

   @Nullable
   protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(BlockEntityType<A> pServerType, BlockEntityType<E> pClientType, BlockEntityTicker<? super E> pTicker) {
      return pClientType == pServerType ? (BlockEntityTicker<A>) pTicker : null;
   }

   static {
      SHAPES = Maps.newEnumMap(ImmutableMap.of(Direction.NORTH, Block.box((double)0.0F, (double)-2.5F, (double)14.0F, (double)16.0F, (double)18.5F, (double)16.0F), Direction.SOUTH, Block.box((double)0.0F, (double)-2.5F, (double)0.0F, (double)16.0F, (double)18.5F, (double)2.0F), Direction.EAST, Block.box((double)0.0F, (double)-2.5F, (double)0.0F, (double)2.0F, (double)18.5F, (double)16.0F), Direction.WEST, Block.box((double)14.0F, (double)-2.5F, (double)0.0F, (double)16.0F, (double)18.5F, (double)16.0F)));
   }
}
