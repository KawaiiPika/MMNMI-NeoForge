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

public class SuplexAbility extends Ability {

    private static final long COOLDOWN = 140L;
    private static final long CHARGE_TIME = 20L;
    private static final float DAMAGE = 20.0F;

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
                // Lift target backwards
                Vec3 lookVec = entity.getLookAngle();
                Vec3 pos = entity.position().add(lookVec.x * -1.0, entity.getBbHeight() * (grabDuration / (float)CHARGE_TIME), lookVec.z * -1.0);
                this.grabbedTarget.teleportTo(pos.x, pos.y, pos.z);
            } else {
                // Slam target
                if (this.grabbedTarget.hurt(entity.damageSources().mobAttack(entity), DAMAGE)) {
                    this.grabbedTarget.addEffect(new MobEffectInstance(net.minecraft.world.effect.MobEffects.CONFUSION, 40, 0, false, false));
                }

                if (entity.level() instanceof ServerLevel serverLevel) {
                    serverLevel.explode(entity, this.grabbedTarget.getX(), this.grabbedTarget.getY(), this.grabbedTarget.getZ(), 1.0F, net.minecraft.world.level.Level.ExplosionInteraction.NONE);
                }

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
        return Component.literal("Suplex");
    }
}
