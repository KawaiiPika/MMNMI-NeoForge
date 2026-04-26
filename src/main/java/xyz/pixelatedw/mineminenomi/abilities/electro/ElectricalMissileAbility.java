package xyz.pixelatedw.mineminenomi.abilities.electro;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

import java.util.List;

public class ElectricalMissileAbility extends Ability {

    private static final long COOLDOWN = 180L;
    private static final float RANGE = 1.6F;
    private static final float DAMAGE = 20.0F;

    private long continuityStartTime = 0;
    private float dashSpeed = 4.0F;

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!this.isUsing(entity)) {
            // Should consume Eleclaw stacks here, omitting for simplicity of porting dash logic

            boolean hasSulongActive = ElectroHelper.hasSulongActive(entity);
            this.dashSpeed = hasSulongActive ? 5.0F : 4.0F;

            Vec3 lookAngle = entity.getLookAngle();
            entity.setDeltaMovement(lookAngle.x * this.dashSpeed, lookAngle.y * this.dashSpeed, lookAngle.z * this.dashSpeed);

            this.continuityStartTime = entity.level().getGameTime();
        } else {
            this.stop(entity);
        }
    }

    @Override
    public void onTick(LivingEntity entity, long duration) {
        long currentDuration = entity.level().getGameTime() - this.continuityStartTime;

        if (currentDuration > 10L) { // Max dash duration 10 ticks
            this.stop(entity);
            return;
        }

        // Check collisions
        if (entity.level() instanceof ServerLevel serverLevel) {
            AABB area = entity.getBoundingBox().inflate(RANGE);
            List<LivingEntity> targets = serverLevel.getEntitiesOfClass(LivingEntity.class, area);
            targets.remove(entity);

            for (LivingEntity target : targets) {
                float damage = DAMAGE;
                if (ElectroHelper.hasSulongActive(entity)) {
                    damage *= 2.5F;
                }

                if (target.hurt(entity.damageSources().mobAttack(entity), damage)) {
                    // Simulate paralysis
                    target.addEffect(new MobEffectInstance(net.minecraft.world.effect.MobEffects.MOVEMENT_SLOWDOWN, 40, 5, false, false));
                    this.stop(entity);
                    return;
                }
            }
        }
    }

    @Override
    protected void stopUsing(LivingEntity entity) {
        long cooldown = COOLDOWN;
        if (ElectroHelper.hasSulongActive(entity)) {
            cooldown = (long) (cooldown * 0.5F);
        }
        this.startCooldown(entity, cooldown);
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Electrical Missile");
    }
}
