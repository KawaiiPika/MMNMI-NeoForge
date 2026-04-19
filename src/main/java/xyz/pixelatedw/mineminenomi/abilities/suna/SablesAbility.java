package xyz.pixelatedw.mineminenomi.abilities.suna;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;

public class SablesAbility extends Ability {

    private static final int MAX_TICKS = 100; // 5 seconds

    public SablesAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "suna_suna_no_mi"));
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
            entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), 
                net.minecraft.sounds.SoundEvents.BEACON_ACTIVATE, 
                net.minecraft.sounds.SoundSource.PLAYERS, 2.0F, 0.5F);
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
        if (entity.level().isClientSide) {
            for (int i = 0; i < 15; i++) {
                double angle = (duration * 0.5) + (i * 0.5);
                double r = 2.0 + (duration * 0.05);
                entity.level().addParticle(new net.minecraft.core.particles.BlockParticleOption(net.minecraft.core.particles.ParticleTypes.FALLING_DUST, net.minecraft.world.level.block.Blocks.SAND.defaultBlockState()), 
                    entity.getX() + Math.cos(angle) * r, 
                    entity.getY() + (i * 0.5), 
                    entity.getZ() + Math.sin(angle) * r, 
                    0, 0, 0);
            }
        } else {
            // Whirlwind effect
            double radius = 8.0;
            entity.level().getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(radius)).forEach(target -> {
                if (target != entity) {
                    Vec3 dir = target.position().subtract(entity.position()).normalize();
                    Vec3 lift = new Vec3(0, 0.5, 0);
                    Vec3 swirl = new Vec3(-dir.z, 0, dir.x).scale(0.5);
                    target.setDeltaMovement(target.getDeltaMovement().add(lift).add(swirl));
                    target.hurt(entity.damageSources().mobAttack(entity), 2.0F);
                }
            });
        }

        if (duration >= MAX_TICKS) {
            stopUsing(entity);
        }
    }

    private ResourceLocation getId() {
        return ResourceLocation.fromNamespaceAndPath("mineminenomi", "sables");
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.sables");
    }
}
