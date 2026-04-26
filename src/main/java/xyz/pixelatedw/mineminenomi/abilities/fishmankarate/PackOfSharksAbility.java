package xyz.pixelatedw.mineminenomi.abilities.fishmankarate;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.fishmankarate.PackOfSharksProjectile;

public class PackOfSharksAbility extends Ability {

    private static final long COOLDOWN = 300L;
    private static final float WATER_DAMAGE_MUL = 1.25F;

    private int projectilesSpawned = 5;
    private int currentFired = 0;

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!this.isUsing(entity)) {
            this.currentFired = 0;
        }
    }

    @Override
    public void onTick(LivingEntity entity, long duration) {
        if (duration % 3 == 0) {
            if (this.currentFired < this.projectilesSpawned) {
                if (entity.level() instanceof ServerLevel serverLevel) {
                    PackOfSharksProjectile projectile = new PackOfSharksProjectile(serverLevel, entity);

                    if (FishmanKarateHelper.isInWater(entity)) {
                        projectile.setDamage(20.0F * WATER_DAMAGE_MUL);
                    }

                    projectile.shootFromRotation(entity, entity.getXRot(), entity.getYRot(), 0.0F, 1.5F, 0.0F);
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
        return Component.literal("Pack of Sharks");
    }
}
