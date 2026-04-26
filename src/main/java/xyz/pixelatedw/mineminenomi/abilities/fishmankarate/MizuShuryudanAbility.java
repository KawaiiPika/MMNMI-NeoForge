package xyz.pixelatedw.mineminenomi.abilities.fishmankarate;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.fishmankarate.MizuShuryudanProjectile;

public class MizuShuryudanAbility extends Ability {

    private static final long COOLDOWN = 240L;
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
        if (duration % 5 == 0) {
            if (this.currentFired < this.projectilesSpawned) {
                if (entity.level() instanceof ServerLevel serverLevel) {
                    float speed = 1.0F + entity.getRandom().nextFloat();

                    MizuShuryudanProjectile projectile = new MizuShuryudanProjectile(serverLevel, entity);
                    projectile.shootFromRotation(entity, entity.getXRot(), entity.getYRot(), 0.0F, speed, 10.0F);
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
        return Component.literal("Mizu Shuryudan");
    }
}
