package xyz.pixelatedw.mineminenomi.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;

import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
// import xyz.pixelatedw.mineminenomi.data.entity.IDevilFruitData;
import xyz.pixelatedw.mineminenomi.init.ModFruits;

public class OriBarsBlock extends IronBarsBlock {
   public OriBarsBlock() {
      super(BlockBehaviour.Properties.of().strength(50.0F));
   }

   public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
      if (context instanceof EntityCollisionContext collisionContext) {
         Entity entity = collisionContext.getEntity();
         if (entity instanceof Player player) {
            if (false) {// DevilFruitCapability.hasDevilFruit(player, ModFruits.ORI_ORI_NO_MI)
               return Shapes.empty();
            }
         }

         if (this.getAABBIndex(state) == 15) {
            return Shapes.block();
         }
      }

      return this.shapeByIndex[this.getAABBIndex(state)];
   }
}
