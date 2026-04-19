package xyz.pixelatedw.mineminenomi.items;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.init.ModDataComponents;

import java.util.List;

public class GoldDenDenMushiItem extends Item {
    public GoldDenDenMushiItem() {
        super(new Item.Properties().stacksTo(1));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        
        if (!itemStack.getOrDefault(ModDataComponents.IN_USE.get(), false) && !world.isClientSide()) {
            // Start Buster Call
            itemStack.set(ModDataComponents.IN_USE.get(), true);
            itemStack.set(ModDataComponents.COUNTDOWN.get(), 600); // 30 seconds
            
            // TODO: Phase 3 - Implement world data coordinates saving
            // itemStack.getOrCreateTag().putIntArray("coords", new int[]{player.getBlockX(), player.getBlockY(), player.getBlockZ()});

            player.displayClientMessage(Component.translatable("item.message.buster_call_initiated"), false);
            return InteractionResultHolder.consume(itemStack);
        }

        return InteractionResultHolder.pass(itemStack);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int itemSlot, boolean isSelected) {
        if (!world.isClientSide() && stack.getOrDefault(ModDataComponents.IN_USE.get(), false)) {
            int countdown = getCountdown(stack);
            
            if (countdown > 0) {
                stack.set(ModDataComponents.COUNTDOWN.get(), countdown - 1);
            }

            int t = Math.max((countdown - 200) / 20, 0);
            if (entity instanceof Player player && countdown % 20 == 0) {
                Component message = Component.translatable("item.message.buster_call_timer", t);
                player.displayClientMessage(message, true);
            }

            // TODO: Phase 3 - Implement projectile spawning and mob spawning
            
            if (countdown <= 0) {
                stack.shrink(1);
            }
        }
    }

    private int getCountdown(ItemStack stack) {
        return stack.getOrDefault(ModDataComponents.COUNTDOWN.get(), -1);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext context, List<Component> lore, TooltipFlag tooltip) {
        lore.add(Component.translatable("item.info.buster_call"));
        int countdown = this.getCountdown(itemStack);
        if (countdown > 0) {
            int t = Math.max((countdown - 200) / 20, 0);
            lore.add(Component.translatable("item.message.buster_call_timer", t).withStyle(ChatFormatting.GRAY));
        }
    }
}
