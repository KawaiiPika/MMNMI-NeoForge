package xyz.pixelatedw.mineminenomi.items;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class CharacterCreatorItem extends Item {

    public CharacterCreatorItem() {
        super(new Item.Properties().stacksTo(1));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide) {
            xyz.pixelatedw.mineminenomi.networking.ModNetworking.sendTo(
                    new xyz.pixelatedw.mineminenomi.networking.packets.SOpenCharacterCreatorScreenPacket(true, true),
                    (net.minecraft.server.level.ServerPlayer) player
            );
        }
        return InteractionResultHolder.success(player.getItemInHand(hand));
    }
}
