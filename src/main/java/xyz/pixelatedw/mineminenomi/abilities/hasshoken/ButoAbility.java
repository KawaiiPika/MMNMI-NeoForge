package xyz.pixelatedw.mineminenomi.abilities.hasshoken;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

import java.util.List;

public class ButoAbility extends Ability {

    private static final long COOLDOWN = 400L;
    private static final long HOLD_TIME = 10L;
    private static final float RANGE = 1.8F;
    private static final float DAMAGE = 30.0F;

    private long continuityStartTime = 0;

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!this.isUsing(entity)) {
            this.continuityStartTime = entity.level().getGameTime();

            // Jump backwards
            Vec3 lookAngle = entity.getLookAngle();
            entity.setDeltaMovement(lookAngle.x * -2.0, 0.3, lookAngle.z * -2.0);

            // Start animation
        }
    }

    @Override
    public void onTick(LivingEntity entity, long duration) {
        long currentDuration = entity.level().getGameTime() - this.continuityStartTime;

        if (currentDuration > HOLD_TIME) {
            this.stop(entity);
            return;
        }

        if (entity.level() instanceof ServerLevel serverLevel) {
            AABB area = entity.getBoundingBox().inflate(RANGE);
            List<LivingEntity> targets = serverLevel.getEntitiesOfClass(LivingEntity.class, area);
            targets.remove(entity);

            for (LivingEntity target : targets) {
                if (target.hurt(entity.damageSources().mobAttack(entity), DAMAGE)) {
                    // Knock target same direction as entity's back jump
                    Vec3 lookAngle = entity.getLookAngle();
                    target.setDeltaMovement(lookAngle.x * -2.0, 0.2, lookAngle.z * -2.0);

                    this.stop(entity);
                    return; // Hit first target only? The old code was a dash ability which could hit multiple if range allowed
                }
            }
        }
    }

    @Override
    protected void stopUsing(LivingEntity entity) {
        this.startCooldown(entity, COOLDOWN);
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Buto");
    }
}
