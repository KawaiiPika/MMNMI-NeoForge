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
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.ticks.TickPriority;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;

public class OilSpillBlock extends Block {
   protected static final VoxelShape SHAPE = Block.m_49796_((double)0.0F, (double)0.0F, (double)0.0F, (double)16.0F, (double)1.0F, (double)16.0F);
   private int ticks = 400;

   public OilSpillBlock() {
      super(Properties.m_284310_().m_60911_(0.98F).m_60978_(0.5F).m_60918_(SoundType.f_56750_).m_60977_());
   }

   public VoxelShape m_5939_(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
      return SHAPE;
   }

   public VoxelShape m_5940_(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
      return SHAPE;
   }

   public boolean m_7898_(BlockState state, LevelReader world, BlockPos pos) {
      return Block.m_49916_(state.m_60812_(world, pos.m_7495_())) && Block.m_49918_(state.m_60812_(world, pos.m_7495_()), Direction.UP);
   }

   public BlockState m_7417_(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
      return !stateIn.m_60710_(worldIn, currentPos) ? Blocks.f_50016_.m_49966_() : stateIn;
   }

   @OnlyIn(Dist.CLIENT)
   public boolean m_6104_(BlockState state, BlockState adjacentBlockState, Direction side) {
      return adjacentBlockState.m_60734_() == this;
   }

   public void m_6807_(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean isMoving) {
      if (world.m_8055_(pos.m_7495_()).m_60795_()) {
         world.m_7731_(pos, Blocks.f_50016_.m_49966_(), 3);
      } else {
         world.m_186460_(pos, this, 1);
      }
   }

   public void m_7892_(BlockState state, Level worldIn, BlockPos pos, Entity entity) {
      float x = (float)Math.min((double)1.5F, entity.m_20184_().f_82479_ * (double)1.15F);
      float z = (float)Math.min((double)1.5F, entity.m_20184_().f_82481_ * (double)1.15F);
      AbilityHelper.setDeltaMovement(entity, (double)x, entity.m_20184_().f_82480_, (double)z);
   }

   public void m_213897_(BlockState state, ServerLevel world, BlockPos pos, RandomSource rand) {
      if (!world.f_46443_) {
         if (this.ticks > 0) {
            --this.ticks;
            if (this.areFlamesClose(world, pos)) {
               world.m_255391_((Entity)null, (double)pos.m_123341_(), (double)pos.m_123342_(), (double)pos.m_123343_(), 3.0F, true, ServerConfig.isAbilityGriefingEnabled() ? ExplosionInteraction.BLOCK : ExplosionInteraction.NONE);
            }
         } else {
            world.m_46597_(pos, Blocks.f_50016_.m_49966_());
            this.ticks = 400 + rand.m_188503_(10);
         }

         world.m_186464_(pos, this, 10, TickPriority.EXTREMELY_HIGH);
      }
   }

   private boolean areFlamesClose(Level world, BlockPos pos) {
      int range = 2;
      BlockPos.MutableBlockPos mutpos = new BlockPos.MutableBlockPos();

      for(int i = -range; i < range; ++i) {
         for(int j = -range; j < range; ++j) {
            for(int k = -range; k < range; ++k) {
               int posX = pos.m_123341_() + i;
               int posY = pos.m_123342_() + j;
               int posZ = pos.m_123343_() + k;
               mutpos.m_122178_(posX, posY, posZ);
               Block currentBlock = world.m_8055_(mutpos).m_60734_();
               if (currentBlock == Blocks.f_50083_) {
                  return true;
               }
            }
         }
      }

      return false;
   }
}
