package xyz.pixelatedw.mineminenomi.abilities.doru;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;

import java.util.Random;

public class DoruDoruArtsKenAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "doru_doru_no_mi");
    private final Random random = new Random();

    public DoruDoruArtsKenAbility() {
        super(FRUIT);
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide && entity instanceof ServerPlayer player) {
            ItemStack sword = new ItemStack(ModWeapons.DORU_DORU_ARTS_KEN.get());

            int red = 255;
            int green = 255;
            int blue = 255;

            int rgb = (red << 16) | (green << 8) | blue;
            sword.set(net.minecraft.core.component.DataComponents.DYED_COLOR, new DyedItemColor(rgb, true));

            if (player.getMainHandItem().isEmpty()) {
                player.setItemInHand(net.minecraft.world.InteractionHand.MAIN_HAND, sword);
            } else {
                player.getInventory().placeItemBackInInventory(sword);
            }
        }
        this.stop(entity);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.doru_doru_arts_ken");
    }
}
