package xyz.pixelatedw.mineminenomi.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import net.minecraft.world.level.block.CakeBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
// import xyz.pixelatedw.mineminenomi.api.helpers.BlocksHelper;

public class SpongeCakeBlock extends CakeBlock {
   protected static final VoxelShape[] SHAPES = new VoxelShape[]{Block.box((double)0.0F, (double)0.0F, (double)0.0F, (double)16.0F, (double)8.0F, (double)16.0F), Block.box((double)3.0F, (double)0.0F, (double)0.0F, (double)16.0F, (double)8.0F, (double)16.0F), Block.box((double)5.0F, (double)0.0F, (double)0.0F, (double)16.0F, (double)8.0F, (double)16.0F), Block.box((double)7.0F, (double)0.0F, (double)0.0F, (double)16.0F, (double)8.0F, (double)16.0F), Block.box((double)9.0F, (double)0.0F, (double)0.0F, (double)16.0F, (double)8.0F, (double)16.0F), Block.box((double)11.0F, (double)0.0F, (double)0.0F, (double)16.0F, (double)8.0F, (double)16.0F), Block.box((double)13.0F, (double)0.0F, (double)0.0F, (double)16.0F, (double)8.0F, (double)16.0F)};

   public SpongeCakeBlock() {
      super(BlockBehaviour.Properties.of().lightLevel((state) -> 0).strength(0.7F).sound(net.minecraft.world.level.block.SoundType.WOOL));
   }

   public net.minecraft.world.phys.shapes.VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, net.minecraft.world.phys.shapes.CollisionContext context) {
      return SHAPES[(Integer)state.getValue(BITES)];
   }

   public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
      return true;
   }

   public void fallOn(net.minecraft.world.level.Level worldIn, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
      entity.causeFallDamage(fallDistance, 0.01F, entity.damageSources().fall());
   }
}
