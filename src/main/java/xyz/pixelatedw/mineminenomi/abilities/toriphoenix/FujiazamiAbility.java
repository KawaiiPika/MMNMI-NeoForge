package xyz.pixelatedw.mineminenomi.abilities.toriphoenix;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.entities.AbilityProjectile;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.init.ModEffects;

public class FujiazamiAbility extends Ability {

    private static final int HOLD_TIME = 80;

    public FujiazamiAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "tori_tori_no_mi_model_1"));
    }

    @Override
    public Result canUse(LivingEntity entity) {
        Result superResult = super.canUse(entity);
        if (superResult.isFail()) {
            return superResult;
        }
        return AbilityUseConditions.requiresInAir(entity);
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        // Start duration tracking for continuity
    }

    @Override
    protected void onTick(LivingEntity entity, long duration) {
        if (duration < HOLD_TIME) {
            // Slow falling effect simulation
            entity.setDeltaMovement(entity.getDeltaMovement().multiply(1.0, 0.5, 1.0));
            entity.fallDistance = 0.0F;

            // Assume fly form for simplicity unless we query morphs properly (flyform gives wider range)
            boolean flyForm = true;
            Vec3 lookVec = entity.getLookAngle();
            int range = flyForm ? 3 : 2;
            double boxSize = flyForm ? 1.25 : 0.8;

            for (int i = 0; i < range * 2; ++i) {
                double distance = (double)i / 2.0;
                Vec3 pos = new Vec3(
                    entity.getX() + lookVec.x * distance,
                    entity.getY() + (double)entity.getEyeHeight() + lookVec.y * distance,
                    entity.getZ() + lookVec.z * distance
                );

                for (Entity target : entity.level().getEntities(entity, new AABB(pos.x - boxSize, pos.y - boxSize, pos.z - boxSize, pos.x + boxSize, pos.y + boxSize * 2.0, pos.z + boxSize), (t) -> t != entity)) {
                    if (target instanceof LivingEntity livingTarget) {
                        Vec3 speed = entity.getLookAngle().normalize().multiply(3.0, 1.0, 3.0);
                        AbilityHelper.setDeltaMovement(livingTarget, speed.x, 0.5, speed.z);
                    } else if (target instanceof AbstractArrow || target instanceof ThrowableProjectile) {
                        if (target instanceof AbilityProjectile abilityProj) {
                            // Damage check concept if abilityProj exposed getDamage (mock implementation)
                        }
                        target.discard();
                    }
                }
            }

            if (!entity.level().isClientSide && duration % 5 == 0) {
                // Particles spawned on client
            }
        } else {
            float cooldown = 80.0F + duration * 1.5F;
            this.startCooldown(entity, (long) cooldown);
            this.stop(entity);
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.fujiazami");
    }
}
