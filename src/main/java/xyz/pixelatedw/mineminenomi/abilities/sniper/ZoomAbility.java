package xyz.pixelatedw.mineminenomi.abilities.sniper;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.init.ModArmors;

public class ZoomAbility extends Ability {

    public ZoomAbility() {}

    @Override
    public Result canUse(LivingEntity entity) {
        ItemStack head = entity.getItemBySlot(EquipmentSlot.HEAD);
        if (head.isEmpty() || !head.is(ModArmors.SNIPER_GOGGLES.get())) {
            return Result.fail(Component.literal("You need Sniper Goggles to use this!"));
        }
        return super.canUse(entity);
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            entity.sendSystemMessage(Component.literal("Zoom Active"));
        }
    }

    @Override
    protected void stopUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            entity.sendSystemMessage(Component.literal("Zoom Deactivated"));
        }
    }

    @Override
    public void onTick(LivingEntity entity, long duration) {
        ItemStack head = entity.getItemBySlot(EquipmentSlot.HEAD);
        if (head.isEmpty() || !head.is(ModArmors.SNIPER_GOGGLES.get())) {
            this.stop(entity);
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Zoom");
    }
}
