package xyz.pixelatedw.mineminenomi.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.DevilFruitCapability;
import xyz.pixelatedw.mineminenomi.init.ModFruits;

public class HardenedSnowBlock extends Block {
   public HardenedSnowBlock() {
      super(Properties.m_284310_().m_280606_().m_60910_().m_60988_().m_60913_(8.0F, 4.0F).m_60918_(SoundType.f_56747_));
   }

   public VoxelShape m_5939_(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
      if (context instanceof EntityCollisionContext collisionContext) {
         Entity entity = collisionContext.m_193113_();
         if (entity instanceof LivingEntity living) {
            if (DevilFruitCapability.hasDevilFruit(living, ModFruits.YUKI_YUKI_NO_MI)) {
               return Shapes.m_83040_();
            }
         }
      }

      return Shapes.m_83144_();
   }

   public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
      return 5;
   }

   public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
      return 60;
   }
}
