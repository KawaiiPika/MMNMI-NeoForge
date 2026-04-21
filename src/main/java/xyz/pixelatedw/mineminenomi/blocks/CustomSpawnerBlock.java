package xyz.pixelatedw.mineminenomi.blocks;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import com.mojang.serialization.MapCodec;
import xyz.pixelatedw.mineminenomi.blocks.blockentity.CustomSpawnerBlockEntity;
import xyz.pixelatedw.mineminenomi.init.ModBlockEntities;

public class CustomSpawnerBlock extends BaseEntityBlock {
   public static final MapCodec<CustomSpawnerBlock> CODEC = simpleCodec(properties -> new CustomSpawnerBlock());

   public CustomSpawnerBlock() {
      super(net.minecraft.world.level.block.state.BlockBehaviour.Properties.of().strength(5.0F).requiresCorrectToolForDrops().noLootTable());
   }

   @Override
   public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
      return new CustomSpawnerBlockEntity(pos, state);
   }

   @SuppressWarnings("unchecked")
   @Nullable
   @Override
   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
      if (blockEntityType != ModBlockEntities.CUSTOM_SPAWNER.get()) return null;
      return (BlockEntityTicker<T>) (BlockEntityTicker<CustomSpawnerBlockEntity>) CustomSpawnerBlockEntity::tick;
   }

   @Override
   public void spawnAfterBreak(BlockState state, ServerLevel level, BlockPos pos, ItemStack stack, boolean dropExperience) {
      super.spawnAfterBreak(state, level, pos, stack, dropExperience);
   }

   @Override
   public RenderShape getRenderShape(BlockState state) {
      return RenderShape.MODEL;
   }

   @Override
   protected MapCodec<? extends BaseEntityBlock> codec() {
      return CODEC;
   }
}
