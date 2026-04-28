package xyz.pixelatedw.mineminenomi.abilities.electro;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.electro.ElectricalLunaProjectile;
import xyz.pixelatedw.mineminenomi.init.ModEffects;

public class ElectricalLunaAbility extends Ability {

    private static final long COOLDOWN = 200L;
    private static final long CHARGE_TIME = 40L;

    private long chargeStartTime = 0;
    private boolean isCharged = false;

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!this.isCharged && !this.isUsing(entity)) {
            // Need to check for Eleclaw stacks in the future if we port Eleclaw correctly,
            // for now just allow charging.
            this.chargeStartTime = entity.level().getGameTime();
        } else if (this.isCharged) {
            // Fire projectile on second click if charged
            this.fireProjectile(entity);
        }
    }

    @Override
    public void onTick(LivingEntity entity, long duration) {
        if (!this.isCharged) {
            long currentCharge = entity.level().getGameTime() - this.chargeStartTime;

            entity.addEffect(new MobEffectInstance(net.minecraft.world.effect.MobEffects.MOVEMENT_SLOWDOWN, 5, 0, false, false));

            // Spawn charging particles here if needed

            if (currentCharge >= CHARGE_TIME) {
                this.isCharged = true;
                if (entity.level() instanceof ServerLevel) {
                    entity.sendSystemMessage(Component.literal("Electrical Luna charged. Swing to fire!"));
                }
            }
        }
    }

    private void fireProjectile(LivingEntity entity) {
        if (entity.level() instanceof ServerLevel serverLevel) {
            ElectricalLunaProjectile projectile = new ElectricalLunaProjectile(serverLevel, entity);

            float damage = 40.0F;
            if (ElectroHelper.hasSulongActive(entity)) {
                damage *= 2.0F;
            }
            projectile.setDamage(damage);

            projectile.shootFromRotation(entity, entity.getXRot(), entity.getYRot(), 0.0F, 3.0F, 0.0F);
            serverLevel.addFreshEntity(projectile);

            this.isCharged = false;

            long cooldown = COOLDOWN;
            if (ElectroHelper.hasSulongActive(entity)) {
                cooldown = (long) (cooldown * 0.3F);
            }

            this.stop(entity);
            this.startCooldown(entity, cooldown);
        }
    }

    // Allow swinging arm to fire (simulate punch ability)
    public void onSwing(LivingEntity entity) {
        if (this.isUsing(entity) && this.isCharged) {
            this.fireProjectile(entity);
        }
    }

    @Override
    protected void stopUsing(LivingEntity entity) {
        this.isCharged = false;
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Electrical Luna");
    }
}
