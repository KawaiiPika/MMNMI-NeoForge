package xyz.pixelatedw.mineminenomi.abilities.mini;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Items;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class PaperFloatAbility extends Ability {

    public PaperFloatAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "mini_mini_no_mi"));
    }

    @Override
    public boolean isPassive() {
        return true;
    }

    @Override
    public void onTick(LivingEntity entity, long duration) {
        if (!entity.level().isClientSide) {
            // Simplified check: if holding paper and falling, slow descent
            boolean hasPaper = entity.getMainHandItem().is(Items.PAPER) || entity.getOffhandItem().is(Items.PAPER);
            boolean isFalling = !entity.onGround() && entity.getDeltaMovement().y < 0.0;

            if (hasPaper && isFalling) {
                entity.fallDistance = 0.0F;
                entity.setDeltaMovement(entity.getDeltaMovement().x, entity.getDeltaMovement().y / 2.0, entity.getDeltaMovement().z);
            }
        }
    }

    @Override
    protected void startUsing(LivingEntity entity) {}

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.paper_float");
    }
}
