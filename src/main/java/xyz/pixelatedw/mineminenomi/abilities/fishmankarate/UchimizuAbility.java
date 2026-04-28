package xyz.pixelatedw.mineminenomi.abilities.fishmankarate;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.fishmankarate.UchimizuProjectile;

public class UchimizuAbility extends Ability {

    private static final long COOLDOWN = 80L;
    private static final float WATER_DAMAGE_MUL = 2.0F;

    private int projectilesSpawned = 8;
    private int currentFired = 0;

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!this.isUsing(entity)) {
            this.currentFired = 0;
        }
    }

    @Override
    public void onTick(LivingEntity entity, long duration) {
        if (duration % 2 == 0) {
            if (this.currentFired < this.projectilesSpawned) {
                if (entity.level() instanceof ServerLevel serverLevel) {
                    UchimizuProjectile projectile = new UchimizuProjectile(serverLevel, entity);
                    if (FishmanKarateHelper.isInWater(entity)) {
                        projectile.setDamage(20.0F * WATER_DAMAGE_MUL);
                    }
                    projectile.shootFromRotation(entity, entity.getXRot(), entity.getYRot(), 0.0F, 2.0F, 1.2F);
                    serverLevel.addFreshEntity(projectile);
                    this.currentFired++;
                }
            } else {
                this.stop(entity);
            }
        }
    }

    @Override
    protected void stopUsing(LivingEntity entity) {
        this.currentFired = 0;
        this.startCooldown(entity, COOLDOWN);
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Uchimizu");
    }
}
