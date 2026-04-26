package xyz.pixelatedw.mineminenomi.abilities.zushi;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.init.ModEffects;

import java.util.List;

public class GraviZoneAbility extends Ability {
    private boolean isRejectMode = false;

    public GraviZoneAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "zushi_zushi_no_mi"));
    }

    @Override
    public Component getDisplayName() {
        return isRejectMode ? Component.translatable("ability.mineminenomi.gravi_zone_reject") : Component.translatable("ability.mineminenomi.gravi_zone");
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        // Mode could be determined by a data attachment or key combination, defaulting to false for now
    }

    @Override
    protected void onTick(LivingEntity entity, long duration) {
        if (!entity.level().isClientSide()) {
            if (!isRejectMode) {
                // Guard Mode
                int range = 8;
                AABB aabb = entity.getBoundingBox().inflate(range);
                List<LivingEntity> targets = entity.level().getEntitiesOfClass(LivingEntity.class, aabb, e -> e != entity && e.isAlive());

                for (LivingEntity target : targets) {
                    target.setPos(target.xOld, target.yOld, target.zOld); // Lock position
                    target.addEffect(new MobEffectInstance(ModEffects.MOVEMENT_BLOCKED, 5, 0, false, false));
                }

                if (duration > 100) {
                    this.stop(entity);
                }
            } else {
                // Reject Mode
                int range = 3;
                AABB aabb = entity.getBoundingBox().inflate(range);
                List<LivingEntity> targets = entity.level().getEntitiesOfClass(LivingEntity.class, aabb, e -> e != entity && e.isAlive());

                for (LivingEntity target : targets) {
                    target.hurt(entity.damageSources().mobAttack(entity), 10.0F);

                    Vec3 dist = target.position().subtract(entity.position()).multiply(1, 0, 1).normalize();
                    double power = 4.5;
                    double xSpeed = dist.x * power;
                    double zSpeed = dist.z * power;
                    target.setDeltaMovement(xSpeed, 0.2, zSpeed);
                }

                if (duration > 160) {
                    this.stop(entity);
                }
            }
        }
    }

    @Override
    public void stop(LivingEntity entity) {
        super.stop(entity);
        startCooldown(entity, 200);
    }
}
