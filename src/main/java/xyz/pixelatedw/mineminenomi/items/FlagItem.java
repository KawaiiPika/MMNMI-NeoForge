package xyz.pixelatedw.mineminenomi.items;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.init.ModDataComponents;


// Flag item stores crew affiliation and canvas size.
// Canvas size ported to Data Component. Crew data deferred to Phase 3.
public class FlagItem extends Item {

    public enum CanvasSize {
        SMALL, MEDIUM, LARGE;

        public boolean isMaximumSize() {
            return this == LARGE;
        }
    }

    public FlagItem() {
        super(new Item.Properties().stacksTo(1));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (!world.isClientSide()) {
            if (!itemStack.has(ModDataComponents.OWNER_UUID.get())) {
                itemStack.set(ModDataComponents.OWNER_UUID.get(), player.getUUID().toString());
                // TODO: Phase 3 - Also store crew data from FactionsCapability
            }
        }
        return InteractionResultHolder.sidedSuccess(itemStack, world.isClientSide());
    }

    public static void setCanvasSize(ItemStack stack, CanvasSize size) {
        stack.set(ModDataComponents.CANVAS_SIZE.get(), size.ordinal());
    }

    public static boolean upgradeCanvasSize(ItemStack stack) {
        CanvasSize currentSize = getCanvasSize(stack);
        if (currentSize.isMaximumSize()) return false;
        setCanvasSize(stack, CanvasSize.values()[currentSize.ordinal() + 1]);
        return true;
    }

    public static CanvasSize getCanvasSize(ItemStack stack) {
        int idx = stack.getOrDefault(ModDataComponents.CANVAS_SIZE.get(), 0);
        return CanvasSize.values()[idx];
    }
}
