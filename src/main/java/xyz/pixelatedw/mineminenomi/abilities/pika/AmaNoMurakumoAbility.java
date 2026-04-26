package xyz.pixelatedw.mineminenomi.abilities.pika;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import net.minecraft.world.InteractionHand;

public class AmaNoMurakumoAbility extends Ability {

    public AmaNoMurakumoAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "pika_pika_no_mi"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
             if (entity instanceof net.minecraft.world.entity.player.Player player) {
                 // Check if we need to give weapon
                 player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(xyz.pixelatedw.mineminenomi.init.ModWeapons.AMA_NO_MURAKUMO.get()));
                 this.startCooldown(entity, 100);
             }
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.ama_no_murakumo");
    }
}
