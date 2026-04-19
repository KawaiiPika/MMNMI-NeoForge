package xyz.pixelatedw.mineminenomi.items.weapons;

import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Item;

public class DoruPickaxeItem extends PickaxeItem {
    public DoruPickaxeItem(Tier tier, int damage, float speed) {
        super(tier, new Item.Properties().attributes(PickaxeItem.createAttributes(tier, damage, speed)));
    }
}
