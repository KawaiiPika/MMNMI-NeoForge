package xyz.pixelatedw.mineminenomi.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.level.block.Block;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.init.ModFoods;

public class TangerineItem extends ItemNameBlockItem {
   public TangerineItem() {
      super((Block)ModBlocks.TANGERINE_CROP.get(), (new Item.Properties()).m_41489_(ModFoods.TANGERINE));
   }
}
