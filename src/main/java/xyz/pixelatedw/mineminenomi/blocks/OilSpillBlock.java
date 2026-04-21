package xyz.pixelatedw.mineminenomi.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.Level.ExplosionInteraction;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.ticks.TickPriority;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;

public class OilSpillBlock extends Block {
   protected static final VoxelShape SHAPE = Block.box((double)0.0F, (double)0.0F, (double)0.0F, (double)16.0F, (double)1.0F, (double)16.0F);
   private int ticks = 400;

   public OilSpillBlock() {
      super(BlockBehaviour.Properties.of().friction(0.98F).strength(0.5F).sound(net.minecraft.world.level.block.SoundType.SLIME_BLOCK).noOcclusion());
   }

   public net.minecraft.world.phys.shapes.VoxelShape getCollisionShape(BlockState state, BlockGetter worldIn, BlockPos pos, net.minecraft.world.phys.shapes.CollisionContext context) {
      return SHAPE;
   }

   public net.minecraft.world.phys.shapes.VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, net.minecraft.world.phys.shapes.CollisionContext context) {
      return SHAPE;
   }

   public boolean canSurvive(BlockState state, net.minecraft.world.level.LevelReader world, BlockPos pos) {
      return Block.isShapeFullBlock(state.getCollisionShape(world, pos.below())) && net.minecraft.world.level.block.Block.isFaceFull(state.getCollisionShape(world, pos.below()), Direction.UP);
   }

   public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
      return !stateIn.canSurvive(worldIn, currentPos) ? net.minecraft.world.level.block.Blocks.AIR.defaultBlockState() : stateIn;
   }


   public boolean skipRendering(BlockState state, BlockState adjacentBlockState, net.minecraft.core.Direction side) {
      return adjacentBlockState.getBlock() == this;
   }

   public void onPlace(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean isMoving) {
      if (world.getBlockState(pos.below()).isAir()) {
         world.setBlock(pos, net.minecraft.world.level.block.Blocks.AIR.defaultBlockState(), 3);
      } else {
         world.scheduleTick(pos, this, 1);
      }
   }

   public void entityInside(BlockState state, net.minecraft.world.level.Level worldIn, BlockPos pos, Entity entity) {
      float x = (float)Math.min((double)1.5F, entity.getDeltaMovement().x() * (double)1.15F);
      float z = (float)Math.min((double)1.5F, entity.getDeltaMovement().z() * (double)1.15F);
      if (entity instanceof net.minecraft.world.entity.LivingEntity) AbilityHelper.setDeltaMovement((net.minecraft.world.entity.LivingEntity)entity, (double)x, entity.getDeltaMovement().y(), (double)z);
   }

   public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource rand) {
      if (!world.isClientSide()) {
         if (this.ticks > 0) {
            --this.ticks;
            if (this.areFlamesClose(world, pos)) {
               world.explode((Entity)null, (double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), 3.0F, true, Level.ExplosionInteraction.NONE);
            }
         } else {
            world.setBlockAndUpdate(pos, net.minecraft.world.level.block.Blocks.AIR.defaultBlockState());
            this.ticks = 400 + rand.nextInt(10);
         }

         world.scheduleTick(pos, this, 10, net.minecraft.world.ticks.TickPriority.EXTREMELY_HIGH);
      }
   }

   private boolean areFlamesClose(Level world, BlockPos pos) {
      int range = 2;
      BlockPos.MutableBlockPos mutpos = new BlockPos.MutableBlockPos();

      for(int i = -range; i < range; ++i) {
         for(int j = -range; j < range; ++j) {
            for(int k = -range; k < range; ++k) {
               int posX = pos.getX() + i;
               int posY = pos.getY() + j;
               int posZ = pos.getZ() + k;
               mutpos.set(posX, posY, posZ);
               Block currentBlock = world.getBlockState(mutpos).getBlock();
               if (currentBlock == net.minecraft.world.level.block.Blocks.FIRE) {
                  return true;
               }
            }
         }
      }

      return false;
   }
}
