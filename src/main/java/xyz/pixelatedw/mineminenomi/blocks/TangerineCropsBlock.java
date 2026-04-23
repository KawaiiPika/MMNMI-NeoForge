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
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.neoforge.common.CommonHooks;
import net.neoforged.neoforge.common.SpecialPlantable;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.init.ModItems;

public class TangerineCropsBlock extends BushBlock implements BonemealableBlock, SpecialPlantable {
   public static final IntegerProperty AGE = IntegerProperty.create("age", 0, 4);
   public static final com.mojang.serialization.MapCodec<TangerineCropsBlock> CODEC = simpleCodec(TangerineCropsBlock::new);

   public TangerineCropsBlock() {
      super(Properties.of().noCollission().randomTicks().instabreak().sound(SoundType.CROP));
   }

   public TangerineCropsBlock(Properties properties) {
      super(properties);
   }

   @Override
   protected com.mojang.serialization.MapCodec<? extends BushBlock> codec() {
      return CODEC;
   }

   @Override
   public boolean canPlacePlantAtPosition(ItemStack itemStack, LevelReader levelReader, BlockPos blockPos, net.minecraft.core.Direction direction) {
      return levelReader.getBlockState(blockPos.below()).is(net.minecraft.tags.BlockTags.create(net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("neoforge", "villager_farmlands")));
   }

   @Override
   public void spawnPlantAtPosition(ItemStack itemStack, net.minecraft.world.level.LevelAccessor levelAccessor, BlockPos blockPos, net.minecraft.core.Direction direction) {
      levelAccessor.setBlock(blockPos, ModBlocks.TANGERINE_CROP.get().defaultBlockState(), 3);
   }

   public ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state) {
      return new ItemStack((ItemLike)ModItems.TANGERINE.get());
   }

   @Override
   protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
      return state.is(net.minecraft.tags.BlockTags.create(net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("neoforge", "villager_farmlands"))) || state.is(ModBlocks.TANGERINE_CROP.get());
   }

   @Override
   protected net.minecraft.world.ItemInteractionResult useItemOn(ItemStack handItemStack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
      if (hand == InteractionHand.OFF_HAND) {
         return net.minecraft.world.ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
      } else {
         int age = (Integer)state.getValue(AGE);
         boolean isFullyGrown = age == this.getMaxAge();
         if (!isFullyGrown && handItemStack.getItem().equals(Items.BONE_MEAL)) {
            return net.minecraft.world.ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
         } else if (isFullyGrown && handItemStack.isEmpty()) {
            BlockState bottomState = level.getBlockState(pos.below());
            BlockState aboveState = level.getBlockState(pos.above());
            this.dropFruit(level, pos, state);
            if (!bottomState.isAir() && bottomState.getBlock() == ModBlocks.TANGERINE_CROP.get()) {
               this.dropFruit(level, pos.below(), bottomState);
            } else if (!aboveState.isAir() && aboveState.getBlock() == ModBlocks.TANGERINE_CROP.get()) {
               this.dropFruit(level, pos.above(), aboveState);
            }

            return net.minecraft.world.ItemInteractionResult.sidedSuccess(level.isClientSide);
         } else {
            return super.useItemOn(handItemStack, state, level, pos, player, hand, hit);
         }
      }
   }

   private void dropFruit(Level level, BlockPos pos, BlockState state) {
      int fruits = 1 + level.random.nextInt(2);
      popResource(level, pos, new ItemStack((ItemLike)ModItems.TANGERINE.get(), fruits));
      level.playSound((Player)null, pos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS, 1.0F, 1.2F + level.random.nextFloat() * 0.4F);
      level.setBlock(pos, (BlockState)state.setValue(AGE, 2), 2);
   }

   public int getMaxAge() {
      return 4;
   }

   public boolean isValidBonemealTarget(BlockState state) {
      return (Integer)state.getValue(AGE) < this.getMaxAge();
   }

   @Override
   public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
      if (level.isAreaLoaded(pos, 1)) {
         int age = (Integer)state.getValue(AGE);
         boolean canGrowThisTick = random.nextInt(25) == 0;
         if (age < this.getMaxAge() && level.getRawBrightness(pos.above(), 0) >= 9 && CommonHooks.canCropGrow(level, pos, state, canGrowThisTick)) {
            BlockState bottomState = level.getBlockState(pos.below());
            BlockState aboveState = level.getBlockState(pos.above());
            this.grow(level, pos, state);
            if (!bottomState.isAir() && bottomState.getBlock() == ModBlocks.TANGERINE_CROP.get()) {
               this.grow(level, pos.below(), bottomState);
            } else if (!aboveState.isAir() && aboveState.getBlock() == ModBlocks.TANGERINE_CROP.get()) {
               this.grow(level, pos.above(), aboveState);
            }

            CommonHooks.fireCropGrowPost(level, pos, state);
         }

      }
   }

   @Override
   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
      super.createBlockStateDefinition(builder);
      builder.add(new Property[]{AGE});
   }

   @Override
   public boolean isValidBonemealTarget(LevelReader pLevel, BlockPos pPos, BlockState pState) {
      return (Integer)pState.getValue(AGE) < this.getMaxAge();
   }

   @Override
   public boolean isBonemealSuccess(Level pLevel, RandomSource pRand, BlockPos pPos, BlockState pState) {
      return true;
   }

   @Override
   public void performBonemeal(ServerLevel level, RandomSource rand, BlockPos pos, BlockState state) {
      BlockState bottomState = level.getBlockState(pos.below());
      BlockState aboveState = level.getBlockState(pos.above());
      this.grow(level, pos, state);
      if (!bottomState.isAir() && bottomState.getBlock() == ModBlocks.TANGERINE_CROP.get()) {
         this.grow(level, pos.below(), bottomState);
      } else if (!aboveState.isAir() && aboveState.getBlock() == ModBlocks.TANGERINE_CROP.get()) {
         this.grow(level, pos.above(), aboveState);
      }

   }

   private void grow(Level level, BlockPos pos, BlockState state) {
      int age = Math.min(this.getMaxAge(), (Integer)state.getValue(AGE) + 1);
      level.setBlock(pos, (BlockState)state.setValue(AGE, age), 2);
   }
}
