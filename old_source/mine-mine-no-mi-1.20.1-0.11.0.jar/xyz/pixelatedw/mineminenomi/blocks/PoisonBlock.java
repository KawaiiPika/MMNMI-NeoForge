package xyz.pixelatedw.mineminenomi.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xyz.pixelatedw.mineminenomi.api.NuWorld;
import xyz.pixelatedw.mineminenomi.init.ModEffects;

public class PoisonBlock extends Block {
   protected static final VoxelShape SHAPE = Block.m_49796_((double)0.0F, (double)0.0F, (double)0.0F, (double)16.0F, (double)1.0F, (double)16.0F);
   private static final int ALIVE_TICKS = 800;
   private static final int RANDOM_TICKS = 400;

   public PoisonBlock() {
      super(Properties.m_284310_().m_60978_(0.5F));
   }

   public VoxelShape m_5939_(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
      return SHAPE;
   }

   public VoxelShape m_5940_(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
      return SHAPE;
   }

   public boolean isFullCube(BlockState state) {
      return false;
   }

   public boolean m_7898_(BlockState state, LevelReader world, BlockPos pos) {
      BlockState below = world.m_8055_(pos.m_7495_());
      return below.m_60783_(world, pos, Direction.UP) && !(below.m_60734_() instanceof PoisonBlock);
   }

   public BlockState m_7417_(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
      return !stateIn.m_60710_(worldIn, currentPos) ? Blocks.f_50016_.m_49966_() : stateIn;
   }

   @OnlyIn(Dist.CLIENT)
   public boolean m_6104_(BlockState state, BlockState adjacentBlockState, Direction side) {
      return adjacentBlockState.m_60734_() == this;
   }

   public void m_7892_(BlockState state, Level world, BlockPos pos, Entity entity) {
      if (entity instanceof LivingEntity living) {
         if (!living.m_21023_((MobEffect)ModEffects.DOKU_POISON.get())) {
            living.m_7292_(new MobEffectInstance((MobEffect)ModEffects.DOKU_POISON.get(), 100, 0));
         }
      }

   }

   public void m_6807_(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean isMoving) {
      if (!this.m_7898_(state, world, pos)) {
         world.m_46796_(2001, pos, Block.m_49956_(state));
         NuWorld.setBlockStateInChunk(world, pos, Blocks.f_50016_.m_49966_(), 2);
      } else {
         world.m_186460_(pos, this, 800 + world.m_213780_().m_188503_(400));
      }
   }

   public void m_213897_(BlockState state, ServerLevel world, BlockPos pos, RandomSource rand) {
      world.m_46796_(2001, pos, Block.m_49956_(state));
      NuWorld.setBlockStateInChunk(world, pos, Blocks.f_50016_.m_49966_(), 2);
   }
}
