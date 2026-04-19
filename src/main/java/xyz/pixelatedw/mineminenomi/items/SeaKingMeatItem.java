package xyz.pixelatedw.mineminenomi.items;

import net.minecraft.world.item.Item;
import xyz.pixelatedw.mineminenomi.init.ModFoods;

public class SeaKingMeatItem extends Item {
    public SeaKingMeatItem() {
        super(new Item.Properties().food(ModFoods.SEA_KING_MEAT));
    }
}
