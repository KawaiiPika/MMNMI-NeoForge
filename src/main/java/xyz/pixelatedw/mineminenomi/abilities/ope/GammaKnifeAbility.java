package xyz.pixelatedw.mineminenomi.abilities.ope;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionHand;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class GammaKnifeAbility extends Ability {

    public GammaKnifeAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "ope_ope_no_mi"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!RoomAbility.isEntityInRoom(entity)) {
            if (entity instanceof net.minecraft.world.entity.player.Player player) {
                player.displayClientMessage(Component.translatable("ability.mineminenomi.message.only_in_room"), true);
            }
            return;
        }

        if (!entity.level().isClientSide) {
             if (entity instanceof net.minecraft.world.entity.player.Player player) {
                 if (player.getMainHandItem().isEmpty()) {
                     player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(xyz.pixelatedw.mineminenomi.init.ModWeapons.GAMMA_KNIFE.get()));
                     this.startCooldown(entity, 500);
                 }
             }
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.gamma_knife");
    }
}
