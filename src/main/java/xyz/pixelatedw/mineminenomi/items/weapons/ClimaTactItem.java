package xyz.pixelatedw.mineminenomi.items.weapons;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;

public class ClimaTactItem extends Item {
    public ClimaTactItem(Tier tier, int damage) {
        super(new Item.Properties().stacksTo(1).durability(tier.getUses()));
    }
}
