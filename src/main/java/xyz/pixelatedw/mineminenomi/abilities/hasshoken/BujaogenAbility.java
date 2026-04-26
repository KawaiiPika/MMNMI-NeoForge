package xyz.pixelatedw.mineminenomi.abilities.hasshoken;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.init.ModEffects;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class BujaogenAbility extends Ability {

    private static final long COOLDOWN = 200L;
    private static final long CHARGE_TIME = 10L;
    private static final float DAMAGE = 30.0F;

    private long chargeStartTime = 0;
    private boolean isCharging = false;

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!this.isCharging && !this.isUsing(entity)) {
            this.isCharging = true;
            this.chargeStartTime = entity.level().getGameTime();
        }
    }

    @Override
    public void onTick(LivingEntity entity, long duration) {
        if (this.isCharging) {
            long currentCharge = entity.level().getGameTime() - this.chargeStartTime;

            if (currentCharge >= CHARGE_TIME) {
                if (entity.level() instanceof ServerLevel serverLevel) {
                    float range = 3.0F;
                    AABB area = entity.getBoundingBox().inflate(range);
                    List<LivingEntity> targets = serverLevel.getEntitiesOfClass(LivingEntity.class, area);
                    targets.remove(entity);

                    Optional<LivingEntity> nearestTarget = targets.stream()
                        .min(Comparator.comparingDouble(e -> e.distanceToSqr(entity)));

                    nearestTarget.ifPresent(target -> {
                        if (target.hurt(entity.damageSources().mobAttack(entity), DAMAGE)) {
                            target.addEffect(new MobEffectInstance(net.minecraft.world.effect.MobEffects.CONFUSION, 20, 0, false, false));
                            target.setDeltaMovement(entity.getDeltaMovement().x, -5.0, entity.getDeltaMovement().z);
                        }
                    });
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
        return Component.literal("Bujaogen");
    }
}
