package xyz.pixelatedw.mineminenomi.abilities.zushi;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class AbareHimatsuriAbility extends Ability {
    private static final int COOLDOWN = 300;
    private static final int HOLD_TIME = 1200;

    public AbareHimatsuriAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "zushi_zushi_no_mi"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
    }

    @Override
    protected void onTick(LivingEntity entity, long duration) {
        if (duration >= HOLD_TIME) {
            this.stop(entity);
            return;
        }
    }

    @Override
    protected void stopUsing(LivingEntity entity) {
        this.startCooldown(entity, COOLDOWN);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.abare_himatsuri");
    }
}
