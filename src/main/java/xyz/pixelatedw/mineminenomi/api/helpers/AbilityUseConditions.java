package xyz.pixelatedw.mineminenomi.api.helpers;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import xyz.pixelatedw.mineminenomi.api.util.Result;

public class AbilityUseConditions {
    
    public static Result requiresOnGround(LivingEntity entity) {
        return entity.onGround() ? Result.success() : Result.fail(Component.literal("Only usable while on ground!"));
    }

    public static Result requiresSword(LivingEntity entity) {
        ItemStack stack = entity.getMainHandItem();
        boolean hasSwordInHand = ItemsHelper.isSword(stack);
        if (hasSwordInHand) {
            return Result.success();
        } else {
            return Result.fail(Component.literal("You need a sword to use this ability!"));
        }
    }

    public static Result requiresInAir(LivingEntity entity) {
        return !entity.onGround() ? Result.success() : Result.fail(Component.translatable("ability.error.only_in_air"));
    }
}
