package xyz.pixelatedw.mineminenomi.blocks;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xyz.pixelatedw.mineminenomi.blocks.blockentity.CustomSpawnerBlockEntity;
import xyz.pixelatedw.mineminenomi.init.ModBlockEntities;

public class CustomSpawnerBlock extends BaseEntityBlock {
   public CustomSpawnerBlock() {
      super(Properties.m_284310_().m_60978_(Float.MAX_VALUE).m_60910_());
   }

   public boolean m_7420_(BlockState state, BlockGetter reader, BlockPos pos) {
      return true;
   }

   @OnlyIn(Dist.CLIENT)
   public boolean m_6104_(BlockState state, BlockState adjacentBlockState, Direction side) {
      return true;
   }

   public int m_7753_(BlockState state, BlockGetter worldIn, BlockPos pos) {
      return 0;
   }

   @Nullable
   public <T extends BlockEntity> BlockEntityTicker<T> m_142354_(Level level, BlockState state, BlockEntityType<T> blockEntity) {
      return m_152132_(blockEntity, (BlockEntityType)ModBlockEntities.CUSTOM_SPAWNER.get(), CustomSpawnerBlockEntity::tick);
   }

   public BlockEntity m_142194_(BlockPos pos, BlockState state) {
      return new CustomSpawnerBlockEntity(pos, state);
   }
}
