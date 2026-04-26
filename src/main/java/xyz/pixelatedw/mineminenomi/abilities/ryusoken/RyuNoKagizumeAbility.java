package xyz.pixelatedw.mineminenomi.abilities.ryusoken;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.init.ModEffects;

import java.util.List;

public class RyuNoKagizumeAbility extends Ability {

    private static final long COOLDOWN = 400L;
    private static final long CHARGE_TIME = 20L;
    private static final float DAMAGE = 50.0F;

    private LivingEntity grabbedTarget;
    private long grabStartTime;

    @Override
    protected void startUsing(LivingEntity entity) {
        if (entity.level() instanceof ServerLevel serverLevel) {
            AABB area = entity.getBoundingBox().inflate(1.0); // very short range grab
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
                // Keep target at same position
                this.grabbedTarget.setDeltaMovement(0, 0, 0);
            } else {
                // Execute attack
                this.grabbedTarget.addEffect(new MobEffectInstance(net.minecraft.world.effect.MobEffects.WEAKNESS, 40, 0, false, false)); // We assume this effect exists from init/ModEffects

                if (this.grabbedTarget.hurt(entity.damageSources().mobAttack(entity), DAMAGE)) {
                    ItemStack headItem = this.grabbedTarget.getItemBySlot(EquipmentSlot.HEAD);
                    if (!headItem.isEmpty() && headItem.isDamageableItem()) {
                        headItem.hurtAndBreak(15, this.grabbedTarget, EquipmentSlot.HEAD);
                    }
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
        return Component.literal("Ryu no Kagizume");
    }
}
