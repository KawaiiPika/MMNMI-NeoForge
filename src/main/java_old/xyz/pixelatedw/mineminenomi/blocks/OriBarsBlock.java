package xyz.pixelatedw.mineminenomi.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.DevilFruitCapability;
import xyz.pixelatedw.mineminenomi.init.ModFruits;

public class OriBarsBlock extends IronBarsBlock {
   public OriBarsBlock() {
      super(Properties.m_284310_().m_60978_(50.0F));
   }

   public VoxelShape m_5939_(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
      if (context instanceof EntityCollisionContext collisionContext) {
         Entity entity = collisionContext.m_193113_();
         if (entity instanceof Player player) {
            if (DevilFruitCapability.hasDevilFruit(player, ModFruits.ORI_ORI_NO_MI)) {
               return Shapes.m_83040_();
            }
         }

         if (this.m_52363_(state) == 15) {
            return Shapes.m_83144_();
         }
      }

      return this.f_52315_[this.m_52363_(state)];
   }
}
