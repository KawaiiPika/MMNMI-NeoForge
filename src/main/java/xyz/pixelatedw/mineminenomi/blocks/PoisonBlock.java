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
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
// import xyz.pixelatedw.mineminenomi.api.NuWorld;
import xyz.pixelatedw.mineminenomi.init.ModEffects;

public class PoisonBlock extends Block {
   protected static final VoxelShape SHAPE = Block.box((double)0.0F, (double)0.0F, (double)0.0F, (double)16.0F, (double)1.0F, (double)16.0F);
   private static final int ALIVE_TICKS = 800;
   private static final int RANDOM_TICKS = 400;

   public PoisonBlock() {
      super(BlockBehaviour.Properties.of().strength(0.5F));
   }

   public net.minecraft.world.phys.shapes.VoxelShape getCollisionShape(BlockState state, BlockGetter worldIn, BlockPos pos, net.minecraft.world.phys.shapes.CollisionContext context) {
      return SHAPE;
   }

   public net.minecraft.world.phys.shapes.VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, net.minecraft.world.phys.shapes.CollisionContext context) {
      return SHAPE;
   }

   public boolean isFullCube(BlockState state) {
      return false;
   }

   public boolean canSurvive(BlockState state, net.minecraft.world.level.LevelReader world, BlockPos pos) {
      BlockState below = world.getBlockState(pos.below());
      return below.isFaceSturdy(world, pos, Direction.UP) && !(below.getBlock() instanceof PoisonBlock);
   }

   public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
      return !stateIn.canSurvive(worldIn, currentPos) ? net.minecraft.world.level.block.Blocks.AIR.defaultBlockState() : stateIn;
   }


   public boolean skipRendering(BlockState state, BlockState adjacentBlockState, net.minecraft.core.Direction side) {
      return adjacentBlockState.getBlock() == this;
   }

   public void entityInside(BlockState state, net.minecraft.world.level.Level world, BlockPos pos, Entity entity) {
      if (entity instanceof LivingEntity living) {
         if (!living.hasEffect(net.minecraft.core.registries.BuiltInRegistries.MOB_EFFECT.wrapAsHolder((MobEffect)ModEffects.DOKU_POISON.get()))) {
            living.addEffect(new MobEffectInstance(net.minecraft.core.registries.BuiltInRegistries.MOB_EFFECT.wrapAsHolder((MobEffect)ModEffects.DOKU_POISON.get()), 100, 0));
         }
      }

   }

   public void onPlace(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean isMoving) {
      if (!this.canSurvive(state, world, pos)) {
         world.levelEvent(2001, pos, Block.getId(state));
         world.setBlock(pos, net.minecraft.world.level.block.Blocks.AIR.defaultBlockState(), 2);
      } else {
         world.scheduleTick(pos, this, 800 + world.getRandom().nextInt(400));
      }
   }

   public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource rand) {
      world.levelEvent(2001, pos, Block.getId(state));
      world.setBlock(pos, net.minecraft.world.level.block.Blocks.AIR.defaultBlockState(), 2);
   }
}
