package xyz.pixelatedw.mineminenomi.abilities.doru;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class CandleLockAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "doru_doru_no_mi");
    private static final long COOLDOWN = 240;

    public CandleLockAbility() {
        super(FRUIT);
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            this.startCooldown(entity, COOLDOWN);
        }
        this.stop(entity);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.candle_lock");
    }
}
