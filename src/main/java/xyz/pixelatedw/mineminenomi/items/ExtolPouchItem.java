package xyz.pixelatedw.mineminenomi.items;

import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;

import java.util.List;

public class ExtolPouchItem extends Item {

    public ExtolPouchItem() {
        super(new Item.Properties().stacksTo(1));
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        if (!level.isClientSide && !stack.has(DataComponents.CUSTOM_DATA)) {
            CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> {
                tag.putLong("extol", (long) (Math.random() * 245 + 5));
            });
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        PlayerStats stats = PlayerStats.get(player);

        if (stats == null) return InteractionResultHolder.pass(stack);

        if (!level.isClientSide) {
            CustomData customData = stack.get(DataComponents.CUSTOM_DATA);
            if (customData != null) {
                long amount = customData.copyTag().getLong("extol");
                long currentExtol = stats.getExtol();
                
                if (currentExtol + amount <= 999999999L) {
                    stats.setExtol(currentExtol + amount);
                    stats.sync(player);
                    stack.shrink(1);
                    player.displayClientMessage(Component.translatable("item.mineminenomi.extol_pouch.gained", amount), true);
                    return InteractionResultHolder.consume(stack);
                } else {
                    player.displayClientMessage(Component.translatable("item.mineminenomi.extol_pouch.full"), true);
                }
            }
        }

        return InteractionResultHolder.pass(stack);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        CustomData customData = stack.get(DataComponents.CUSTOM_DATA);
        if (customData != null) {
            long amount = customData.copyTag().getLong("extol");
            tooltip.add(Component.translatable("item.mineminenomi.extol_pouch.amount", amount).withStyle(ChatFormatting.GRAY));
        }
    }
}
