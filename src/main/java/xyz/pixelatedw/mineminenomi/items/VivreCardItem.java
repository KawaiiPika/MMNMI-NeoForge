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
    public net.minecraft.world.InteractionResultHolder<ItemStack> use(Level level, Player player, net.minecraft.world.InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);

        if (!level.isClientSide) {
            UUID ownerUUID = SoulboundItemHelper.getOwnerUUID(level, itemStack);
            if (ownerUUID != null) {
                xyz.pixelatedw.mineminenomi.entities.VivreCardEntity vivreCard = new xyz.pixelatedw.mineminenomi.entities.VivreCardEntity(level);
                vivreCard.setPos(player.getX(), player.getEyeY() - 0.1, player.getZ());

                // Add initial look/movement logic to match legacy 1.20.1 implementation
                float f8 = net.minecraft.util.Mth.sin(player.getXRot() * ((float)Math.PI / 180F));
                float f2 = net.minecraft.util.Mth.cos(player.getXRot() * ((float)Math.PI / 180F));
                float f3 = net.minecraft.util.Mth.sin(player.getYRot() * ((float)Math.PI / 180F));
                float f4 = net.minecraft.util.Mth.cos(player.getYRot() * ((float)Math.PI / 180F));
                float f5 = level.random.nextFloat() * ((float)Math.PI * 2F);
                float f6 = 0.02F * level.random.nextFloat();
                vivreCard.setDeltaMovement((double)(-f3 * f2 * 0.3F) + Math.cos((double)f5) * (double)f6, (double)(-f8 * 0.3F + 0.1F + (level.random.nextFloat() - level.random.nextFloat()) * 0.1F), (double)(f4 * f2 * 0.3F) + Math.sin((double)f5) * (double)f6);

                vivreCard.setOwner(ownerUUID);
                level.addFreshEntity(vivreCard);
                itemStack.shrink(1);
            }
        }

        return net.minecraft.world.InteractionResultHolder.success(itemStack);
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
