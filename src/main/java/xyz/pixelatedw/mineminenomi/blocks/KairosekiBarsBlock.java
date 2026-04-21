package xyz.pixelatedw.mineminenomi.blocks;


import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class KairosekiBarsBlock extends IronBarsBlock {
   public KairosekiBarsBlock() {
      super(BlockBehaviour.Properties.of().strength(5.0F, 6.0F));
   }
}
