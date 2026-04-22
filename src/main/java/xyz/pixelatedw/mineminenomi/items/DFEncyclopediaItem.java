package xyz.pixelatedw.mineminenomi.items;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.DFEncyclopediaEntry;
import net.minecraft.resources.ResourceLocation;

// DFEncyclopediaItem stores information about discovered devil fruits.
// The NBT data was complex compound tags - full port deferred to Phase 3.
public class DFEncyclopediaItem extends Item {
    public DFEncyclopediaItem() {
        super(new Item.Properties().stacksTo(1));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (world.isClientSide()) {
            return InteractionResultHolder.consume(itemStack);
        } else {
            xyz.pixelatedw.mineminenomi.networking.ModNetworking.sendTo(
                    new xyz.pixelatedw.mineminenomi.networking.packets.SOpenEncyclopediaScreenPacket(itemStack),
                    (net.minecraft.server.level.ServerPlayer) player
            );
            return InteractionResultHolder.consume(itemStack);
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("item.mineminenomi.devil_fruit_encyclopedia.desc").withStyle(ChatFormatting.GRAY));
        // Fallback for Phase 3
    }

    public static float getCompletion(ItemStack itemStack) {
        // Fallback
        return 0.0f;
    }

    public static boolean updateEncyclopediae(Player player, ResourceLocation fruit, DFEncyclopediaEntry entry) {
        // Fallback
        return false;
    }
}
