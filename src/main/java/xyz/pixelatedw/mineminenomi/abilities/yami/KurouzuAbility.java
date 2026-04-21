package xyz.pixelatedw.mineminenomi.abilities.yami;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;

public class KurouzuAbility extends Ability {

    private static final int MAX_TICKS = 40; // 2 seconds

    public KurouzuAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "yami_yami_no_mi"));
    }

    @Override
    public boolean isUsing(LivingEntity entity) {
        PlayerStats stats = PlayerStats.get(entity);
        return stats != null && stats.isAbilityActive(this.getId().toString());
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        PlayerStats stats = PlayerStats.get(entity);
        if (stats != null) {
            stats.setAbilityActive(this.getId().toString(), true);
            stats.sync(entity);
        }
    }

    @Override
    protected void stopUsing(LivingEntity entity) {
        PlayerStats stats = PlayerStats.get(entity);
        if (stats != null) {
            stats.setAbilityActive(this.getId().toString(), false);
            stats.sync(entity);
        }
    }

    @Override
    protected void onTick(LivingEntity entity, long duration) {
        if (!entity.level().isClientSide) {
            // Pick a target in front
            net.minecraft.world.phys.EntityHitResult hit = xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper.pickEntity(entity, 20.0);
            if (hit != null && hit.getEntity() instanceof LivingEntity target) {
                Vec3 dir = entity.position().subtract(target.position()).normalize().scale(0.8);
                target.setDeltaMovement(target.getDeltaMovement().add(dir));
                
                if (target.distanceTo(entity) < 2.0) {
                    target.hurt(entity.damageSources().mobAttack(entity), 15.0F);
                    // Disable Devil Fruit powers (simplified for now)
                    target.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.WEAKNESS, 100, 5));
                    stopUsing(entity);
                }
            }
        }
        
        if (entity.level().isClientSide && duration % 2 == 0) {
             entity.level().addParticle(net.minecraft.core.particles.ParticleTypes.SQUID_INK, 
                entity.getX() + entity.getLookAngle().x * 2, 
                entity.getEyeY() + entity.getLookAngle().y * 2, 
                entity.getZ() + entity.getLookAngle().z * 2, 
                0, 0, 0);
        }

        if (duration >= MAX_TICKS) {
            stopUsing(entity);
        }
    }

    private ResourceLocation getId() {
        return ResourceLocation.fromNamespaceAndPath("mineminenomi", "kurouzu");
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.kurouzu");
    }
}
