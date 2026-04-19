package xyz.pixelatedw.mineminenomi.items;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModItems;

public class HandcuffsItem extends Item {

    public HandcuffsItem() {
        super(new Item.Properties().stacksTo(1));
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {
        if (player.level().isClientSide) return InteractionResult.SUCCESS;

        // Handcuffs can be applied if the target is unconscious or at low health
        // Simplified for now: apply if target is below 2 hearts
        if (target.getHealth() <= 4.0F) {
            MobEffectInstance effect = null;
            if (stack.getItem() == ModItems.NORMAL_HANDCUFFS.get()) {
                effect = new MobEffectInstance(ModEffects.HANDCUFFED, 2400, 0);
            } else if (stack.getItem() == ModItems.KAIROSEKI_HANDCUFFS.get()) {
                effect = new MobEffectInstance(ModEffects.HANDCUFFED_KAIROSEKI, 2400, 0);
            } else if (stack.getItem() == ModItems.EXPLOSIVE_HANDCUFFS.get()) {
                effect = new MobEffectInstance(ModEffects.HANDCUFFED_EXPLOSIVE, 2400, 0);
            }

            if (effect != null) {
                target.addEffect(effect);
                stack.shrink(1);
                return InteractionResult.CONSUME;
            }
        } else {
            player.displayClientMessage(net.minecraft.network.chat.Component.translatable("item.mineminenomi.handcuffs.too_strong"), true);
        }

        return InteractionResult.PASS;
    }
}
