package xyz.pixelatedw.mineminenomi.abilities.fishmankarate;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

import java.util.List;

public class KarakusagawaraSeikenAbility extends Ability {

    private static final long COOLDOWN = 500L;
    private static final long CHARGE_TIME = 40L;
    private static final float RANGE = 10.0F;
    private static final float DAMAGE = 20.0F;
    private static final float WATER_DAMAGE_MUL = 2.5F;

    private long chargeStartTime = 0;
    private boolean isCharging = false;

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!this.isUsing(entity) && !this.isCharging) {
            this.isCharging = true;
            this.chargeStartTime = entity.level().getGameTime();
        } else {
            this.stop(entity);
        }
    }

    @Override
    public void onTick(LivingEntity entity, long duration) {
        if (this.isCharging) {
            long currentCharge = entity.level().getGameTime() - this.chargeStartTime;

            if (currentCharge >= CHARGE_TIME) {
                if (entity.level() instanceof ServerLevel serverLevel) {
                    AABB area = entity.getBoundingBox().inflate(RANGE);
                    List<LivingEntity> targets = serverLevel.getEntitiesOfClass(LivingEntity.class, area);
                    targets.remove(entity);

                    boolean isInWater = FishmanKarateHelper.isInWater(entity);

                    for (LivingEntity target : targets) {
                        float finalDamage = DAMAGE;
                        if (isInWater || target.isInWaterOrBubble()) {
                            finalDamage *= WATER_DAMAGE_MUL;
                        }

                        if (target.hurt(entity.damageSources().mobAttack(entity), finalDamage)) {
                            target.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 200, 1, false, false));
                        }
                    }
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
        return Component.literal("Karakusagawara Seiken");
    }
}
