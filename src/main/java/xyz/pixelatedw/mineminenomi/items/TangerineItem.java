package xyz.pixelatedw.mineminenomi.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.init.ModFoods;

public class TangerineItem extends ItemNameBlockItem {
    public TangerineItem() {
        super(ModBlocks.TANGERINE_CROP.get(), new Item.Properties().food(ModFoods.TANGERINE));
    }
}
