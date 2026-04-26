package xyz.pixelatedw.mineminenomi.abilities.fishmankarate;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class SamehadaShoteiAbility extends Ability {

    private static final long HOLD_TIME = 200L;
    private static final long MIN_COOLDOWN = 100L;
    private static final float DAMAGE = 15.0F;

    private long continuityStartTime = 0;

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!this.isUsing(entity)) {
            this.continuityStartTime = entity.level().getGameTime();
        } else {
            this.stop(entity);
        }
    }

    @Override
    public void onTick(LivingEntity entity, long duration) {
        long currentDuration = entity.level().getGameTime() - this.continuityStartTime;
        if (currentDuration > HOLD_TIME) {
            this.stop(entity);
            return;
        }
    }

    @Override
    protected void stopUsing(LivingEntity entity) {
        long currentDuration = entity.level().getGameTime() - this.continuityStartTime;
        this.startCooldown(entity, MIN_COOLDOWN + (currentDuration * 2L));
    }

    public boolean counterAttack(LivingEntity entity, LivingEntity attacker, float originalDamage) {
        if (this.isUsing(entity)) {
            float damage = Math.max(DAMAGE, originalDamage / 2.0F);
            if (attacker.hurt(entity.damageSources().mobAttack(entity), damage)) {
                Vec3 knockback = attacker.position().subtract(entity.position()).normalize().scale(2.0);
                attacker.setDeltaMovement(knockback.x, 0.5, knockback.z);
            }
            this.stop(entity);
            this.startCooldown(entity, 200L);
            return true;
        }
        return false;
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Samehada Shotei");
    }
}
