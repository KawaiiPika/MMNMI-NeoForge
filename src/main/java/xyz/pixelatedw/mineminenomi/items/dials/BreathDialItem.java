package xyz.pixelatedw.mineminenomi.items.dials;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class BreathDialItem extends BlockItem {
   public BreathDialItem(Block block) {
      super(block, (new Item.Properties()).stacksTo(16).component(xyz.pixelatedw.mineminenomi.init.ModDataComponents.ENERGY.get(), 0));
   }
}
