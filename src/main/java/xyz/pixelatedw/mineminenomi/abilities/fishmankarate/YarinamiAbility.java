package xyz.pixelatedw.mineminenomi.abilities.fishmankarate;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.fishmankarate.YarinamiProjectile;

public class YarinamiAbility extends Ability {

    private static final long COOLDOWN = 240L;
    private static final long CHARGE_TIME = 60L;
    private static final float WATER_DAMAGE_MUL = 1.2F;

    private long chargeStartTime = 0;
    private boolean isCharging = false;

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!this.isCharging && !this.isUsing(entity)) {
            this.isCharging = true;
            this.chargeStartTime = entity.level().getGameTime();
        } else {
            this.stop(entity);
        }
    }

    @Override
    public void onTick(LivingEntity entity, long duration) {
        if (this.isCharging) {
            long currentCharge = entity.level().getGameTime() - this.chargeStartTime;
            if (currentCharge >= CHARGE_TIME) {
                if (entity.level() instanceof ServerLevel serverLevel) {
                    YarinamiProjectile projectile = new YarinamiProjectile(serverLevel, entity);
                    if (FishmanKarateHelper.isInWater(entity)) {
                        projectile.setDamage(20.0F * WATER_DAMAGE_MUL);
                    }
                    projectile.shootFromRotation(entity, entity.getXRot(), entity.getYRot(), 0.0F, 2.5F, 0.0F);
                    serverLevel.addFreshEntity(projectile);
                }
                this.isCharging = false;
                this.startCooldown(entity, COOLDOWN);
                this.stop(entity);
            }
        }
    }

    @Override
    protected void stopUsing(LivingEntity entity) {
        this.isCharging = false;
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Yarinami");
    }
}
