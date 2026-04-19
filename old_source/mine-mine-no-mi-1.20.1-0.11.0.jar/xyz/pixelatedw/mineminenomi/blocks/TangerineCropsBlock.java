package xyz.pixelatedw.mineminenomi.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.common.ForgeHooks;
import xyz.pixelatedw.mineminenomi.api.helpers.BlocksHelper;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.init.ModItems;

public class TangerineCropsBlock extends DoublePlantBlock implements BonemealableBlock {
   public static final IntegerProperty AGE;

   public TangerineCropsBlock() {
      super(Properties.m_284310_().m_60910_().m_60977_().m_60966_().m_60918_(SoundType.f_56740_));
   }

   public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter world, BlockPos pos, Player player) {
      return this.m_7397_(world, pos, state);
   }

   public ItemStack m_7397_(BlockGetter level, BlockPos pos, BlockState state) {
      return new ItemStack((ItemLike)ModItems.TANGERINE.get());
   }

   protected boolean m_6266_(BlockState state, BlockGetter level, BlockPos pos) {
      return state.m_60713_(Blocks.f_50093_);
   }

   public InteractionResult m_6227_(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
      if (hand == InteractionHand.OFF_HAND) {
         return InteractionResult.PASS;
      } else {
         int age = (Integer)state.m_61143_(AGE);
         boolean isFullyGrown = age == this.getMaxAge();
         ItemStack handItemStack = player.m_21120_(hand);
         if (!isFullyGrown && handItemStack.m_41720_().equals(Items.f_42499_)) {
            return InteractionResult.PASS;
         } else if (isFullyGrown && handItemStack.m_41619_()) {
            BlockState bottomState = level.m_8055_(pos.m_7495_());
            BlockState aboveState = level.m_8055_(pos.m_7494_());
            this.dropFruit(level, pos, state);
            if (!bottomState.m_60795_() && bottomState.m_60734_() == ModBlocks.TANGERINE_CROP.get()) {
               this.dropFruit(level, pos.m_7495_(), bottomState);
            } else if (!aboveState.m_60795_() && aboveState.m_60734_() == ModBlocks.TANGERINE_CROP.get()) {
               this.dropFruit(level, pos.m_7494_(), aboveState);
            }

            return InteractionResult.m_19078_(level.f_46443_);
         } else {
            return super.m_6227_(state, level, pos, player, hand, hit);
         }
      }
   }

   private void dropFruit(Level level, BlockPos pos, BlockState state) {
      int fruits = 1 + level.f_46441_.m_188503_(2);
      m_49840_(level, pos, new ItemStack((ItemLike)ModItems.TANGERINE.get(), fruits));
      level.m_5594_((Player)null, pos, SoundEvents.f_12457_, SoundSource.BLOCKS, 1.0F, 1.2F + level.f_46441_.m_188501_() * 0.4F);
      level.m_7731_(pos, (BlockState)state.m_61124_(AGE, 2), 2);
   }

   public int getMaxAge() {
      return 4;
   }

   public boolean m_6724_(BlockState state) {
      return (Integer)state.m_61143_(AGE) < this.getMaxAge();
   }

   public void m_213898_(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
      if (level.isAreaLoaded(pos, 1)) {
         int age = (Integer)state.m_61143_(AGE);
         boolean canGrowThisTick = random.m_188503_(25) == 0;
         if (age < this.getMaxAge() && level.m_45524_(pos.m_7494_(), 0) >= 9 && ForgeHooks.onCropsGrowPre(level, pos, state, canGrowThisTick)) {
            BlockState bottomState = level.m_8055_(pos.m_7495_());
            BlockState aboveState = level.m_8055_(pos.m_7494_());
            this.grow(level, pos, state);
            if (!bottomState.m_60795_() && bottomState.m_60734_() == ModBlocks.TANGERINE_CROP.get()) {
               this.grow(level, pos.m_7495_(), bottomState);
            } else if (!aboveState.m_60795_() && aboveState.m_60734_() == ModBlocks.TANGERINE_CROP.get()) {
               this.grow(level, pos.m_7494_(), aboveState);
            }

            ForgeHooks.onCropsGrowPost(level, pos, state);
         }

      }
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> builder) {
      super.m_7926_(builder);
      builder.m_61104_(new Property[]{AGE});
   }

   public boolean m_7370_(LevelReader pLevel, BlockPos pPos, BlockState pState, boolean pIsClient) {
      return (Integer)pState.m_61143_(AGE) < this.getMaxAge();
   }

   public boolean m_214167_(Level pLevel, RandomSource pRand, BlockPos pPos, BlockState pState) {
      return true;
   }

   public void m_214148_(ServerLevel level, RandomSource rand, BlockPos pos, BlockState state) {
      BlockState bottomState = level.m_8055_(pos.m_7495_());
      BlockState aboveState = level.m_8055_(pos.m_7494_());
      this.grow(level, pos, state);
      if (!bottomState.m_60795_() && bottomState.m_60734_() == ModBlocks.TANGERINE_CROP.get()) {
         this.grow(level, pos.m_7495_(), bottomState);
      } else if (!aboveState.m_60795_() && aboveState.m_60734_() == ModBlocks.TANGERINE_CROP.get()) {
         this.grow(level, pos.m_7494_(), aboveState);
      }

   }

   private void grow(Level level, BlockPos pos, BlockState state) {
      int age = Math.min(this.getMaxAge(), (Integer)state.m_61143_(AGE) + 1);
      level.m_7731_(pos, (BlockState)state.m_61124_(AGE, age), 2);
   }

   static {
      AGE = BlocksHelper.AGE_4;
   }
}
