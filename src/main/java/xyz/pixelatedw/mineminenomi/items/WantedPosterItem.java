package xyz.pixelatedw.mineminenomi.items;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import xyz.pixelatedw.mineminenomi.init.ModDataComponents;

import java.util.List;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.entities.WantedPosterData;

// WantedPosterItem stores the canvas size as a Data Component.
// WantedData (the player photo/canvas) will require a custom component codec in Phase 3.
public class WantedPosterItem extends Item {
    public WantedPosterItem() {
        super(new Item.Properties().stacksTo(1));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (!level.isClientSide() && itemstack.has(net.minecraft.core.component.DataComponents.CUSTOM_DATA)) {
            net.minecraft.world.item.component.CustomData customData = itemstack.get(net.minecraft.core.component.DataComponents.CUSTOM_DATA);
            if (customData != null && customData.contains("WPData")) {
                WantedPosterData wantedPosterData = WantedPosterData.from(customData.copyTag().getCompound("WPData"));
                wantedPosterData.checkIfExpired();
                // TODO: SOpenWantedPosterScreenPacket
            }
        }
        return InteractionResultHolder.consume(itemstack);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        FlagItem.CanvasSize size = getCanvasSize(stack);
        tooltip.add(Component.literal("Size: " + size.name()).withStyle(ChatFormatting.GRAY));
    }

    public static void setCanvasSize(ItemStack stack, FlagItem.CanvasSize size) {
        stack.set(ModDataComponents.CANVAS_SIZE.get(), size.ordinal());
        String sizeName = size.name().charAt(0) + size.name().substring(1).toLowerCase();
        stack.set(net.minecraft.core.component.DataComponents.CUSTOM_NAME,
                Component.literal(sizeName + " " + stack.getHoverName().getString()));
    }

    public static boolean upgradeCanvasSize(ItemStack stack) {
        FlagItem.CanvasSize currentSize = getCanvasSize(stack);
        if (currentSize.isMaximumSize()) return false;
        setCanvasSize(stack, FlagItem.CanvasSize.values()[currentSize.ordinal() + 1]);
        return true;
    }

    public static FlagItem.CanvasSize getCanvasSize(ItemStack stack) {
        int idx = stack.getOrDefault(ModDataComponents.CANVAS_SIZE.get(), 0);
        return FlagItem.CanvasSize.values()[Math.min(idx, FlagItem.CanvasSize.values().length - 1)];
    }
}
