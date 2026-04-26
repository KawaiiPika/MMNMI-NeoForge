package xyz.pixelatedw.mineminenomi.abilities.doru;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;

public class DoruDoruArtsPickaxeAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "doru_doru_no_mi");
    private static final long COOLDOWN = 10;

    public DoruDoruArtsPickaxeAbility() {
        super(FRUIT);
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide && entity instanceof ServerPlayer player) {
            ItemStack pickaxe = new ItemStack(ModWeapons.DORU_PICKAXE.get());

            int rgb = (255 << 16) | (255 << 8) | 255;
            pickaxe.set(net.minecraft.core.component.DataComponents.DYED_COLOR, new DyedItemColor(rgb, true));

            if (player.getMainHandItem().isEmpty()) {
                player.setItemInHand(net.minecraft.world.InteractionHand.MAIN_HAND, pickaxe);
            } else {
                player.getInventory().placeItemBackInInventory(pickaxe);
            }
            this.startCooldown(entity, COOLDOWN);
        }
        this.stop(entity);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.doru_doru_arts_pickaxe");
    }
}
