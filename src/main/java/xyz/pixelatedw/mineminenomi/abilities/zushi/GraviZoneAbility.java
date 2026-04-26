package xyz.pixelatedw.mineminenomi.abilities.zushi;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;
import xyz.pixelatedw.mineminenomi.init.ModEffects;

public class GraviZoneAbility extends Ability {

    public GraviZoneAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "zushi_zushi_no_mi"));
    }

    @Override
    public void use(LivingEntity entity) {
        if (entity.level().isClientSide) return;
        if (entity.isShiftKeyDown() && !this.isUsing(entity)) {
            PlayerStats stats = PlayerStats.get(entity);
            if (stats != null) {
                int mode = stats.getAbilityState("gravi_zone_mode");
                stats.setAbilityState("gravi_zone_mode", mode == 0 ? 1 : 0);
                entity.sendSystemMessage(Component.literal("Mode changed to: " + (mode == 0 ? "Reject" : "Guard")));
            }
            return;
        }
        super.use(entity);
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        // Continuous effect
    }

    @Override
    protected void stopUsing(LivingEntity entity) {
        this.startCooldown(entity, 200);
    }

    @Override
    public void onTick(LivingEntity entity, long duration) {
        PlayerStats stats = PlayerStats.get(entity);
        int mode = stats != null ? stats.getAbilityState("gravi_zone_mode") : 0;

        long maxDuration = mode == 0 ? 100 : 160;
        if (duration > maxDuration) {
            this.stop(entity);
            return;
        }

        if (!entity.level().isClientSide) {
            if (mode == 0) { // GUARD
                int range = 8;
                var aabb = entity.getBoundingBox().inflate(range);
                for (LivingEntity target : entity.level().getEntitiesOfClass(LivingEntity.class, aabb, e -> e != entity)) {
                    target.setDeltaMovement(0, target.getDeltaMovement().y, 0);
                    target.addEffect(new MobEffectInstance(ModEffects.MOVEMENT_BLOCKED, 5, 0, false, false));
                }
                if (duration % 10 == 0) {
                    // spawn particles
                }
            } else { // REJECT
                int range = 3;
                var aabb = entity.getBoundingBox().inflate(range);
                for (LivingEntity target : entity.level().getEntitiesOfClass(LivingEntity.class, aabb, e -> e != entity)) {
                    if (target.hurt(entity.damageSources().mobAttack(entity), 10.0F)) {
                        Vec3 dist = target.position().subtract(entity.position()).multiply(1, 0, 1).normalize();
                        double power = 4.5;
                        double xSpeed = dist.x * power;
                        double zSpeed = dist.z * power;
                        target.setDeltaMovement(xSpeed, 0.2, zSpeed);
                    }
                }
                if (duration % 10 == 0) {
                    // spawn particles
                }
            }
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.gravi_zone");
    }
}
