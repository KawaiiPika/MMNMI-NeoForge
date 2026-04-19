package xyz.pixelatedw.mineminenomi.items;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ChallengePosterItem extends Item {
    public ChallengePosterItem() {
        super(new Item.Properties().stacksTo(1));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (!world.isClientSide()) {
            // TODO: Phase 3 - Restore challenge capability lookup and unlock logic
            // IChallengeData props = ChallengeCapability.get(player).orElse(null);
            // String challengeStrId = itemStack.getOrDefault(ModDataComponents.CHALLENGE_ID.get(), "");
            // ... unlock challenge logic ...
        }
        return InteractionResultHolder.sidedSuccess(itemStack, world.isClientSide());
    }
}
