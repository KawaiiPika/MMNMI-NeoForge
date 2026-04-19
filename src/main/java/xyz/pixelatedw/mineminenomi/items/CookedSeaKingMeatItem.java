package xyz.pixelatedw.mineminenomi.items;

import net.minecraft.world.item.Item;
import xyz.pixelatedw.mineminenomi.init.ModFoods;

public class CookedSeaKingMeatItem extends Item {
    public CookedSeaKingMeatItem() {
        super(new Item.Properties().food(ModFoods.COOKED_SEA_KING_MEAT));
    }
}
