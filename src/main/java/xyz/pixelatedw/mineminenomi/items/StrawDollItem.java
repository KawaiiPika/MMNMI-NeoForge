package xyz.pixelatedw.mineminenomi.items;

import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.helpers.SoulboundItemHelper;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;
import xyz.pixelatedw.mineminenomi.init.ModItems;

import java.util.UUID;

import xyz.pixelatedw.mineminenomi.api.items.IMultiChannelColorItem;

public class StrawDollItem extends Item implements IMultiChannelColorItem {

    public StrawDollItem() {
        super(new Item.Properties().stacksTo(1));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (level.isClientSide) {
            return InteractionResultHolder.success(itemStack);
        }

        Pair<UUID, LivingEntity> ownerData = SoulboundItemHelper.getOwner(level, itemStack);
        if (ownerData == null) {
            return InteractionResultHolder.pass(itemStack);
        }

        LivingEntity owner = ownerData.getRight();
        if (owner == null) {
            return InteractionResultHolder.pass(itemStack);
        }

        PlayerStats ownerStats = PlayerStats.get(owner);
        if (ownerStats == null) {
            return InteractionResultHolder.pass(itemStack);
        }

        if (owner == player) {
            // Self-doll (Wara Wara ability usage)
            ownerStats.setHasStrawDoll(true);
            ownerStats.sync(owner);
            itemStack.shrink(1);
            player.displayClientMessage(Component.translatable("item.mineminenomi.straw_doll.applied"), true);
            return InteractionResultHolder.consume(itemStack);
        }

        return InteractionResultHolder.pass(itemStack);
    }

    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
        Level level = entity.level();
        if (level.isClientSide) return false;

        if (entity.isOnFire() || entity.getY() < level.getMinBuildHeight()) {
            Pair<UUID, LivingEntity> ownerData = SoulboundItemHelper.getOwner(level, stack);
            if (ownerData != null) {
                LivingEntity owner = ownerData.getRight();
                if (owner != null) {
                    PlayerStats stats = PlayerStats.get(owner);
                    if (stats != null) {
                        stats.setHasStrawDoll(false); // Damage redirection fails if doll is destroyed
                        stats.sync(owner);
                    }
                }
            }
        }
        return false;
    }

    public static ItemStack createDollStack(LivingEntity target) {
        ItemStack stack = new ItemStack(ModItems.STRAW_DOLL.get());
        SoulboundItemHelper.setOwner(stack, target);
        // Phase 3 - Custom color component assignment
        stack.set(DataComponents.CUSTOM_NAME, Component.translatable("item.mineminenomi.straw_doll.named", target.getDisplayName()));
        return stack;
    }

    @Override
    public int getDefaultLayerColor(int layer) {
        return -1;
    }

    @Override
    public boolean canBeDyed() {
        return true;
    }

    @Override
    public int getMaxLayers() {
        return 1;
    }
}
