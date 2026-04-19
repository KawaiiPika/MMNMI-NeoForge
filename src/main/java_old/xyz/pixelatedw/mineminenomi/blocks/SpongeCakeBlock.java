package xyz.pixelatedw.mineminenomi.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CakeBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import xyz.pixelatedw.mineminenomi.api.helpers.BlocksHelper;

public class SpongeCakeBlock extends CakeBlock {
   protected static final VoxelShape[] SHAPES = new VoxelShape[]{Block.m_49796_((double)0.0F, (double)0.0F, (double)0.0F, (double)16.0F, (double)8.0F, (double)16.0F), Block.m_49796_((double)3.0F, (double)0.0F, (double)0.0F, (double)16.0F, (double)8.0F, (double)16.0F), Block.m_49796_((double)5.0F, (double)0.0F, (double)0.0F, (double)16.0F, (double)8.0F, (double)16.0F), Block.m_49796_((double)7.0F, (double)0.0F, (double)0.0F, (double)16.0F, (double)8.0F, (double)16.0F), Block.m_49796_((double)9.0F, (double)0.0F, (double)0.0F, (double)16.0F, (double)8.0F, (double)16.0F), Block.m_49796_((double)11.0F, (double)0.0F, (double)0.0F, (double)16.0F, (double)8.0F, (double)16.0F), Block.m_49796_((double)13.0F, (double)0.0F, (double)0.0F, (double)16.0F, (double)8.0F, (double)16.0F)};

   public SpongeCakeBlock() {
      super(Properties.m_284310_().m_280606_().m_60953_((state) -> 0).m_60978_(0.7F).m_60918_(SoundType.f_56745_).m_60922_(BlocksHelper::never));
   }

   public VoxelShape m_5940_(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
      return SHAPES[(Integer)state.m_61143_(f_51180_)];
   }

   public boolean m_7420_(BlockState state, BlockGetter reader, BlockPos pos) {
      return true;
   }

   public void m_142072_(Level worldIn, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
      entity.m_142535_(fallDistance, 0.01F, entity.m_269291_().m_268989_());
   }
}
