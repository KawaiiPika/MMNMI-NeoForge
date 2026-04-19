package xyz.pixelatedw.mineminenomi.items;

import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.helpers.SoulboundItemHelper;

import java.util.UUID;

public class VivreCardItem extends Item {

    public VivreCardItem() {
        super(new Item.Properties().stacksTo(1));
    }

    @Override
    public boolean onDroppedByPlayer(ItemStack itemStack, Player player) {
        // TODO: Spawn VivreCardEntity that moves towards the owner
        return true;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        if (!level.isClientSide) {
            UUID ownerUUID = SoulboundItemHelper.getOwnerUUID(level, stack);
            if (ownerUUID == null) {
                if (entity instanceof LivingEntity living) {
                    setOwner(stack, living);
                }
            } else {
                LivingEntity owner = null;
                if (level instanceof ServerLevel serverLevel) {
                    Entity e = serverLevel.getEntity(ownerUUID);
                    if (e instanceof LivingEntity) owner = (LivingEntity) e;
                }
                
                if (owner != null && owner.getHealth() <= 0.0F) {
                    stack.shrink(1);
                }
            }
        }
    }

    public void setOwner(ItemStack stack, LivingEntity owner) {
        SoulboundItemHelper.setOwner(stack, owner);
        stack.set(DataComponents.CUSTOM_NAME, Component.translatable("item.mineminenomi.vivre_card.named", owner.getDisplayName()));
    }
}
