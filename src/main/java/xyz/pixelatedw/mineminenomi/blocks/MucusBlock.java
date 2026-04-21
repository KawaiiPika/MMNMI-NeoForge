package xyz.pixelatedw.mineminenomi.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class MucusBlock extends Block {
   public MucusBlock() {
      super(net.minecraft.world.level.block.state.BlockBehaviour.Properties.of().strength(0.5F).speedFactor(0.4F).jumpFactor(0.5F));
   }

   @Override
   public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
      entity.setDeltaMovement(entity.getDeltaMovement().multiply(0.4D, 1.0D, 0.4D));
      super.stepOn(level, pos, state, entity);
   }
}
