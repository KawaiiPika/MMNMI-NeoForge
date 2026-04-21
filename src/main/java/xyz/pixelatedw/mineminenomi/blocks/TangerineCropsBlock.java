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
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.common.CommonHooks;
// // import xyz.pixelatedw.mineminenomi.api.helpers.BlocksHelper;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.init.ModItems;

public class TangerineCropsBlock extends CropBlock {
   public static final IntegerProperty AGE = net.minecraft.world.level.block.state.properties.BlockStateProperties.AGE_7;

   public TangerineCropsBlock() {
      super(net.minecraft.world.level.block.state.BlockBehaviour.Properties.of().noCollission().randomTicks().instabreak().sound(net.minecraft.world.level.block.SoundType.CROP));
   }

   @Override
   public IntegerProperty getAgeProperty() {
      return AGE;
   }

   @Override
   public int getMaxAge() {
      return 4;
   }

   @Override
   protected net.minecraft.world.level.ItemLike getBaseSeedId() {
      return ModItems.TANGERINE.get();
   }

   @Override
   protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
      return state.is(net.minecraft.world.level.block.Blocks.FARMLAND);
   }

   @Override
   public InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
      int age = state.getValue(AGE);
      boolean isFullyGrown = age == this.getMaxAge();
      ItemStack handItemStack = player.getItemInHand(InteractionHand.MAIN_HAND);

      if (!isFullyGrown && handItemStack.is(net.minecraft.world.item.Items.BONE_MEAL)) {
         return InteractionResult.PASS;
      } else if (isFullyGrown && handItemStack.isEmpty()) {
         BlockState bottomState = level.getBlockState(pos.below());
         BlockState aboveState = level.getBlockState(pos.above());

         if (!bottomState.isAir() && bottomState.getBlock() == ModBlocks.TANGERINE_CROP.get()) {
            this.dropFruit(level, pos.below(), bottomState);
         } else if (!aboveState.isAir() && aboveState.getBlock() == ModBlocks.TANGERINE_CROP.get()) {
            this.dropFruit(level, pos.above(), aboveState);
         } else {
            this.dropFruit(level, pos, state);
         }
         return InteractionResult.sidedSuccess(level.isClientSide());
      } else {
         return super.useWithoutItem(state, level, pos, player, hit);
      }
   }

   private void dropFruit(Level level, BlockPos pos, BlockState state) {
      if (!level.isClientSide()) {
         int fruits = 1 + level.random.nextInt(2);
         popResource(level, pos, new ItemStack(ModItems.TANGERINE.get(), fruits));
         level.playSound(null, pos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS, 1.0F, 1.2F + level.random.nextFloat() * 0.4F);
         level.setBlock(pos, state.setValue(AGE, 0), 2);
      }
   }

   @Override
   public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
      if (!level.isAreaLoaded(pos, 1)) return;
      if (level.getRawBrightness(pos, 0) >= 9) {
         int age = state.getValue(this.getAgeProperty());
         if (age < this.getMaxAge()) {
            float f = getGrowthSpeed(this.defaultBlockState(), level, pos);
            if (CommonHooks.canCropGrow(level, pos, state, random.nextInt((int)(25.0F / f) + 1) == 0)) {
               level.setBlock(pos, this.getStateForAge(age + 1), 2);
               CommonHooks.fireCropGrowPost(level, pos, state);
            }
         }
      }
   }

   @Override
   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
      builder.add(AGE);
   }
}
