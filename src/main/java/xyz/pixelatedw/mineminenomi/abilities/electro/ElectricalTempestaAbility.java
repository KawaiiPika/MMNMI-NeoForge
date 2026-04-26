package xyz.pixelatedw.mineminenomi.abilities.electro;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.init.ModEffects;

import java.util.List;

public class ElectricalTempestaAbility extends Ability {

    private static final long COOLDOWN = 160L;
    private static final long CHARGE_TIME = 10L;

    private long chargeStartTime = 0;
    private boolean isCharging = false;

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!this.isCharging && !this.isUsing(entity)) {
            // Should consume Eleclaw stacks here
            this.isCharging = true;
            this.chargeStartTime = entity.level().getGameTime();
        }
    }

    @Override
    public void onTick(LivingEntity entity, long duration) {
        if (this.isCharging) {
            long currentCharge = entity.level().getGameTime() - this.chargeStartTime;

            entity.addEffect(new MobEffectInstance(net.minecraft.world.effect.MobEffects.MOVEMENT_SLOWDOWN, 5, 0, false, false));

            if (currentCharge >= CHARGE_TIME) {
                if (entity.level() instanceof ServerLevel serverLevel) {
                    float range = 10.0F;
                    float damage = 20.0F;
                    long cooldown = COOLDOWN;

                    if (ElectroHelper.hasSulongActive(entity)) {
                        range *= 2.0F;
                        damage *= 2.0F;
                        cooldown = (long) (cooldown * 0.5F);
                    }

                    AABB area = entity.getBoundingBox().inflate(range);
                    List<LivingEntity> targets = serverLevel.getEntitiesOfClass(LivingEntity.class, area);
                    targets.remove(entity);

                    for (LivingEntity target : targets) {
                        if (target.hurt(entity.damageSources().mobAttack(entity), damage)) {
                            Vec3 dirVec = entity.position().subtract(target.position()).normalize();
                            target.addEffect(new MobEffectInstance(net.minecraft.world.effect.MobEffects.MOVEMENT_SLOWDOWN, 10, 5, false, false));
                            target.setDeltaMovement(-dirVec.x * 4.0, 1.0, -dirVec.z * 4.0);
                        }
                    }

                    this.startCooldown(entity, cooldown);
                }
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
        return Component.literal("Electrical Tempesta");
    }
}
