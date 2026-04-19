package xyz.pixelatedw.mineminenomi.items;

import net.minecraft.world.item.Item;
import xyz.pixelatedw.mineminenomi.init.ModFoods;

public class TangerineItem extends Item {
    public TangerineItem() {
        super(new Item.Properties().food(ModFoods.TANGERINE));
    }
}
