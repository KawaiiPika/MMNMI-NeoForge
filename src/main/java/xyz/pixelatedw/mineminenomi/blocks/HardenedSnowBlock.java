package xyz.pixelatedw.mineminenomi.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
// import xyz.pixelatedw.mineminenomi.data.entity.IDevilFruitData;
import xyz.pixelatedw.mineminenomi.init.ModFruits;

public class HardenedSnowBlock extends Block {
   public HardenedSnowBlock() {
      super(BlockBehaviour.Properties.of().noCollission().requiresCorrectToolForDrops().strength(8.0F, 4.0F).sound(net.minecraft.world.level.block.SoundType.SNOW));
   }

   public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
      if (context instanceof EntityCollisionContext collisionContext) {
         Entity entity = collisionContext.getEntity();
         if (entity instanceof LivingEntity living) {
            if (false) {// missing DevilFruitCapability
               return Shapes.empty();
            }
         }
      }

      return Shapes.block();
   }

   public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
      return 5;
   }

   public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
      return 60;
   }
}
