package xyz.pixelatedw.mineminenomi.items;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

// WantedPosterPackage contains a random wanted poster - deferred to Phase 3.
public class WantedPosterPackageItem extends Item {
    public WantedPosterPackageItem() {
        super(new Item.Properties().stacksTo(1));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (!world.isClientSide()) {
            // TODO: Phase 3 - Give a random WantedPosterItem from loot table
            if (!player.getAbilities().instabuild) itemStack.shrink(1);
        }
        return InteractionResultHolder.sidedSuccess(itemStack, world.isClientSide());
    }
}
