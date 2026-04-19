package xyz.pixelatedw.mineminenomi.items.weapons;

import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;

public class PopGreenBowItem extends BowItem {
    public PopGreenBowItem(int durability) {
        super(new Item.Properties().stacksTo(1).durability(durability));
    }
}
