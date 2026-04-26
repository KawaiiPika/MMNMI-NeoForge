package xyz.pixelatedw.mineminenomi.abilities.awa;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class RelaxHourAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "awa_awa_no_mi");
    private static final long COOLDOWN = 80;
    private static final int MAX_SHOTS = 5;
    private static final int TICKS_BETWEEN = 5;

    private int shotsFired = 0;
    private long lastShotTick = 0;

    public RelaxHourAbility() {
        super(FRUIT);
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        this.shotsFired = 0;
        this.lastShotTick = entity.level().getGameTime();
    }

    @Override
    protected void onTick(LivingEntity entity, long duration) {
        if (!entity.level().isClientSide) {
            long currentTick = entity.level().getGameTime();

            if (shotsFired < MAX_SHOTS) {
                if (currentTick - lastShotTick >= TICKS_BETWEEN) {
                    shotsFired++;
                    lastShotTick = currentTick;
                }
            } else {
                this.stop(entity);
            }
        }
    }

    @Override
    protected void stopUsing(LivingEntity entity) {
        this.startCooldown(entity, COOLDOWN);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.relax_hour");
    }
}
