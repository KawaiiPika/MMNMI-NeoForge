package xyz.pixelatedw.mineminenomi.abilities.electro;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.electro.ElectricalShowerProjectile;
import xyz.pixelatedw.mineminenomi.init.ModEffects;

public class ElectricalShowerAbility extends Ability {

    private static final long COOLDOWN = 240L;
    private static final long CHARGE_TIME = 60L;

    private long useStartTime = 0;
    private boolean isCharging = false;
    private boolean isShooting = false;
    private int shotCount = 0;

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!this.isCharging && !this.isShooting) {
            this.isCharging = true;
            this.useStartTime = entity.level().getGameTime();
            // Should consume Eleclaw stacks here, omitting for simplicity
        } else if (this.isShooting) {
            this.stop(entity);
        }
    }

    @Override
    public void onTick(LivingEntity entity, long duration) {
        long currentTime = entity.level().getGameTime();

        if (this.isCharging) {
            long chargeTime = currentTime - this.useStartTime;

            entity.addEffect(new MobEffectInstance(net.minecraft.world.effect.MobEffects.MOVEMENT_SLOWDOWN, 5, 0, false, false));
            entity.addEffect(new MobEffectInstance(net.minecraft.world.effect.MobEffects.SLOW_FALLING, 5, 0, false, false));

            if (chargeTime > CHARGE_TIME * 0.65) {
                // Launch user up slightly
                entity.setDeltaMovement(entity.getDeltaMovement().x, 0.5, entity.getDeltaMovement().z);
            }

            if (chargeTime >= CHARGE_TIME) {
                this.isCharging = false;
                this.isShooting = true;
                this.shotCount = 0;
                this.useStartTime = currentTime;

                // Temp invulnerability to fall damage
                entity.addEffect(new MobEffectInstance(net.minecraft.world.effect.MobEffects.SLOW_FALLING, 100, 0, false, false));
            }
        } else if (this.isShooting) {
            entity.addEffect(new MobEffectInstance(net.minecraft.world.effect.MobEffects.SLOW_FALLING, 5, 0, false, false));

            long shootTime = currentTime - this.useStartTime;

            if (shootTime % 10 == 0 && this.shotCount < 5) {
                if (entity.level() instanceof ServerLevel serverLevel) {
                    ElectricalShowerProjectile projectile = new ElectricalShowerProjectile(serverLevel, entity);

                    float damage = 20.0F;
                    if (ElectroHelper.hasSulongActive(entity)) {
                        damage *= 1.25F;
                    }
                    projectile.setDamage(damage);

                    projectile.shootFromRotation(entity, entity.getXRot() + 20, entity.getYRot(), 0.0F, 3.0F, 2.0F);
                    serverLevel.addFreshEntity(projectile);

                    this.shotCount++;
                }
            }

            if (this.shotCount >= 5) {
                this.stop(entity);
            }
        }
    }

    @Override
    protected void stopUsing(LivingEntity entity) {
        this.isCharging = false;
        this.isShooting = false;

        long cooldown = COOLDOWN;
        if (ElectroHelper.hasSulongActive(entity)) {
            cooldown = (long) (cooldown * 0.7F); // 30% reduction
        }
        this.startCooldown(entity, cooldown);
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Electrical Shower");
    }
}
