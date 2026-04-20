package xyz.pixelatedw.mineminenomi.items;

import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.helpers.SoulboundItemHelper;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;
import xyz.pixelatedw.mineminenomi.init.ModDamageSources;
import xyz.pixelatedw.mineminenomi.init.ModItems;

import java.util.UUID;

public class HeartItem extends Item {

    public HeartItem() {
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
            // Owner is not loaded, but we have the UUID.
            // In a real mod, we might want to damage them even if they are offline or far away if the system supports it.
            return InteractionResultHolder.pass(itemStack);
        }

        PlayerStats ownerStats = PlayerStats.get(owner);
        if (ownerStats == null) {
            return InteractionResultHolder.pass(itemStack);
        }

        if (owner == player) {
            // Restore heart to self
            ownerStats.setHasHeart(true);
            ownerStats.sync(owner);
            itemStack.shrink(1);
            player.displayClientMessage(Component.translatable("item.mineminenomi.heart.restored"), true);
            return InteractionResultHolder.consume(itemStack);
        } else {
            // Squeeze heart
            owner.hurt(ModDamageSources.getInstance().heartAttack(player), 5.0F);
            owner.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 1));
            owner.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 40, 1));
            
            player.displayClientMessage(Component.translatable("item.mineminenomi.heart.squeezed", owner.getDisplayName()), true);
            
            if (owner.getHealth() <= 0.0F) {
                itemStack.shrink(1);
            }
            return InteractionResultHolder.consume(itemStack);
        }
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
                    owner.hurt(ModDamageSources.getInstance().heartAttack(null), Float.MAX_VALUE);
                }
                // Handle marking dead if owner not found/loaded? 
                // In 1.21.1 we might need a persistent data manager for this.
            }
        }
        return false;
    }

    public static ItemStack createHeartStack(LivingEntity target) {
        ItemStack stack = new ItemStack(ModItems.HEART.get());
        SoulboundItemHelper.setOwner(stack, target);
        stack.set(DataComponents.CUSTOM_NAME, Component.translatable("item.mineminenomi.heart.named", target.getDisplayName()));
        return stack;
    }
}
