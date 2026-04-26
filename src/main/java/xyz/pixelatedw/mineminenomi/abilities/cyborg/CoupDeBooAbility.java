package xyz.pixelatedw.mineminenomi.abilities.cyborg;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;

public class CoupDeBooAbility extends Ability {
    private static final int COLA_REQUIRED = 20;

    @Override
    protected void startUsing(LivingEntity entity) {
        PlayerStats stats = PlayerStats.get(entity);
        if (stats != null) {
            int currentCola = stats.getBasic().cola();
            if (currentCola >= COLA_REQUIRED) {
                stats.setBasic(new PlayerStats.BasicStats(
                    stats.getBasic().doriki(), currentCola - COLA_REQUIRED, stats.getBasic().ultraCola(),
                    stats.getBasic().loyalty(), stats.getBasic().bounty(), stats.getBasic().belly(),
                    stats.getBasic().extol(), stats.getBasic().identity(), stats.getBasic().hasShadow(),
                    stats.getBasic().hasHeart(), stats.getBasic().hasStrawDoll(), stats.getBasic().isRogue(),
                    stats.getBasic().stamina(), stats.getBasic().maxStamina(), stats.getBasic().trainingPoints()
                ));
                if (entity.level() instanceof ServerLevel serverLevel) {
                    stats.sync(entity);
                }
                Vec3 lookAngle = entity.getLookAngle();
                entity.setDeltaMovement(lookAngle.x * -2.0, 3.5, lookAngle.z * -2.0);
                AABB area = entity.getBoundingBox().inflate(5.0);
                for (LivingEntity target : entity.level().getEntitiesOfClass(LivingEntity.class, area)) {
                    if (target != entity) {
                        target.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 200, 1));
                    }
                }
                entity.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 100, 0, false, false));
                this.startCooldown(entity, 200L);
            }
        }
        this.stop(entity);
    }
    @Override
    public Component getDisplayName() { return Component.literal("Coup De Boo"); }
}
