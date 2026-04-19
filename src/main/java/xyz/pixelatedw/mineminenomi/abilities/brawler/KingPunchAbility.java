package xyz.pixelatedw.mineminenomi.abilities.brawler;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class KingPunchAbility extends Ability {

    private boolean isCharging = false;
    private long chargeStartTime = 0;

    public KingPunchAbility() {}

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!this.isCharging) {
            this.isCharging = true;
            this.chargeStartTime = entity.level().getGameTime();
            if (!entity.level().isClientSide) {
                entity.sendSystemMessage(Component.literal("Warming up the King Punch... This will take a while."));
            }
        }
    }

    @Override
    public void onTick(LivingEntity entity, long duration) {
        if (this.isCharging) {
            long currentTime = entity.level().getGameTime();
            long elapsed = currentTime - this.chargeStartTime;
            
            if (elapsed % 100 == 0 && elapsed > 0) { // Every 5 seconds
                if (!entity.level().isClientSide) {
                    entity.sendSystemMessage(Component.literal("King Punch Charging... " + (elapsed / 20) + "s / 30s"));
                }
            }

            if (elapsed >= 600) { // 30 seconds charge
                this.performKingPunch(entity);
                this.isCharging = false;
                this.startCooldown(entity, 3600); // 3 minutes cooldown
                this.stop(entity);
            }
        }
    }

    private void performKingPunch(LivingEntity entity) {
        if (entity.level().isClientSide) return;

        AABB area = entity.getBoundingBox().inflate(20.0);
        for (LivingEntity target : entity.level().getEntitiesOfClass(LivingEntity.class, area)) {
            if (target != entity) {
                target.hurt(entity.damageSources().mobAttack(entity), 100.0f);
                Vec3 knockback = target.position().subtract(entity.position()).normalize().scale(5.0);
                target.setDeltaMovement(knockback.x, 1.5, knockback.z);
            }
        }
        entity.sendSystemMessage(Component.literal("KING PUNCH!!!"));
        // Potentially add screen shake or massive particles here
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("King Punch");
    }
}
