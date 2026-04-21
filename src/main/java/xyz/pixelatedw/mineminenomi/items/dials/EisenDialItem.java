package xyz.pixelatedw.mineminenomi.items.dials;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class EisenDialItem extends BlockItem {
   public EisenDialItem(Block block) {
      super(block, (new Item.Properties()).stacksTo(16));
   }
}
