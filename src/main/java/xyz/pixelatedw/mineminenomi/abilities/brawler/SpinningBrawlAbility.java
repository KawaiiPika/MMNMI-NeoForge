package xyz.pixelatedw.mineminenomi.abilities.brawler;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.init.ModEffects;

import java.util.List;

public class SpinningBrawlAbility extends Ability {

    private static final long COOLDOWN = 240L;
    private static final long CHARGE_TIME = 60L;
    private static final float GRAB_DAMAGE = 30.0F;
    private static final float COLLISION_DAMAGE = 10.0F;

    private LivingEntity grabbedTarget;
    private long grabStartTime;

    @Override
    protected void startUsing(LivingEntity entity) {
        if (entity.level() instanceof ServerLevel serverLevel) {
            AABB area = entity.getBoundingBox().inflate(2.0);
            List<LivingEntity> targets = serverLevel.getEntitiesOfClass(LivingEntity.class, area);
            targets.remove(entity);

            if (!targets.isEmpty()) {
                this.grabbedTarget = targets.get(0);
                this.grabStartTime = serverLevel.getGameTime();

                this.grabbedTarget.addEffect(new MobEffectInstance(net.minecraft.world.effect.MobEffects.MOVEMENT_SLOWDOWN, (int)CHARGE_TIME, 3, false, false));
                entity.addEffect(new MobEffectInstance(net.minecraft.world.effect.MobEffects.MOVEMENT_SLOWDOWN, (int)CHARGE_TIME, 1, false, false));
            } else {
                this.stop(entity);
            }
        }
    }

    @Override
    public void onTick(LivingEntity entity, long duration) {
        if (this.grabbedTarget != null && this.grabbedTarget.isAlive()) {
            long grabDuration = entity.level().getGameTime() - this.grabStartTime;

            if (grabDuration < CHARGE_TIME) {
                // Spin entity and target
                float spinYaw = (entity.getYRot() + 20.0F) % 360.0F;
                entity.setYRot(spinYaw);
                entity.setYHeadRot(spinYaw);
                entity.setYBodyRot(spinYaw);

                Vec3 lookVec = entity.getLookAngle();
                Vec3 newPos = entity.position().add(lookVec.x * 2.0, entity.getBbHeight() / 2.0, lookVec.z * 2.0);
                this.grabbedTarget.teleportTo(newPos.x, newPos.y, newPos.z);

                // Deal damage to others hit during spin
                if (entity.level() instanceof ServerLevel serverLevel) {
                    AABB hitArea = entity.getBoundingBox().inflate(3.0);
                    List<LivingEntity> hitTargets = serverLevel.getEntitiesOfClass(LivingEntity.class, hitArea);
                    hitTargets.remove(entity);
                    hitTargets.remove(this.grabbedTarget);

                    for (LivingEntity hitTarget : hitTargets) {
                        if (hitTarget.hurt(entity.damageSources().mobAttack(entity), COLLISION_DAMAGE)) {
                            Vec3 knockback = lookVec.scale(2.0);
                            hitTarget.setDeltaMovement(knockback.x, 0.5, knockback.z);
                        }
                    }
                }
            } else {
                // Throw
                if (this.grabbedTarget.hurt(entity.damageSources().mobAttack(entity), GRAB_DAMAGE)) {
                    this.grabbedTarget.addEffect(new MobEffectInstance(net.minecraft.world.effect.MobEffects.CONFUSION, 80, 0, false, false));
                }

                Vec3 throwVec = entity.getLookAngle().scale(2.0);
                this.grabbedTarget.setDeltaMovement(throwVec.x, 0.5, throwVec.z);

                this.grabbedTarget = null;
                this.stop(entity);
            }
        } else {
            this.grabbedTarget = null;
            this.stop(entity);
        }
    }

    @Override
    protected void stopUsing(LivingEntity entity) {
        this.grabbedTarget = null;
        this.startCooldown(entity, COOLDOWN);
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Spinning Brawl");
    }
}
