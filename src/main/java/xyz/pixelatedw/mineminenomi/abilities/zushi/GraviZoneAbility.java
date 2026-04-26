package xyz.pixelatedw.mineminenomi.abilities.zushi;

import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModEffects;

public class GraviZoneAbility extends Ability {
    private static final int COOLDOWN = 200;
    private static final int GUARD_HOLD_TIME = 100;
    private static final int REJECT_HOLD_TIME = 160;
    private static final int GUARD_RANGE = 8;
    private static final int REJECT_RANGE = 3;
    private static final int REJECT_DAMAGE = 10;

    public enum Mode {
        GUARD,
        REJECT
    }

    private Mode currentMode = Mode.GUARD;

    public GraviZoneAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "zushi_zushi_no_mi"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
    }

    @Override
    protected void onTick(LivingEntity entity, long duration) {
        if (!entity.level().isClientSide) {
            boolean spawnGravityRings = (duration % 10 == 0);

            if (this.currentMode == Mode.GUARD) {
                if (duration >= GUARD_HOLD_TIME) {
                    this.stop(entity);
                    this.startCooldown(entity, COOLDOWN);
                    return;
                }

                int range = GUARD_RANGE;
                List<LivingEntity> list = entity.level().getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(range), target -> target != entity && target.isAlive());
                for (LivingEntity target : list) {
                    if (target.distanceToSqr(entity) <= range * range) {
                        target.setPos(target.xo, target.yo, target.zo);
                        target.addEffect(new MobEffectInstance(ModEffects.MOVEMENT_BLOCKED, 5, 0, false, false));
                    }
                }
            } else if (this.currentMode == Mode.REJECT) {
                if (duration >= REJECT_HOLD_TIME) {
                    this.stop(entity);
                    this.startCooldown(entity, COOLDOWN);
                    return;
                }

                int range = REJECT_RANGE;
                List<LivingEntity> list = entity.level().getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(range), target -> target != entity && target.isAlive());
                for (LivingEntity target : list) {
                    if (target.distanceToSqr(entity) <= range * range) {
                        boolean causedDamage = target.hurt(entity.damageSources().mobAttack(entity), REJECT_DAMAGE);
                        if (causedDamage) {
                            Vec3 dist = target.position().subtract(entity.position()).multiply(1, 0, 1).normalize();
                            double power = 4.5D;
                            double xSpeed = dist.x * power;
                            double zSpeed = dist.z * power;
                            AbilityHelper.setDeltaMovement(target, xSpeed, 0.2D, zSpeed);
                        }
                    }
                }
            }
        }
    }

    @Override
    public Component getDisplayName() {
        return this.currentMode == Mode.GUARD ? Component.translatable("ability.mineminenomi.gravi_zone_guard") : Component.translatable("ability.mineminenomi.gravi_zone_reject");
    }
}
