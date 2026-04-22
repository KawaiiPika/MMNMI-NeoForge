package xyz.pixelatedw.mineminenomi.abilities.yami;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;

public class BlackHoleAbility extends Ability {

    private static final int MAX_TICKS = 100; // 5 seconds

    public BlackHoleAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "yami_yami_no_mi"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
             entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), 
                xyz.pixelatedw.mineminenomi.init.ModSounds.YAMI_SFX.get(), 
                net.minecraft.sounds.SoundSource.PLAYERS, 2.0F, 0.5F);
        }
    }

    @Override
    protected void onTick(LivingEntity entity, long duration) {
        if (entity.level().isClientSide) {
            for (int i = 0; i < 20; i++) {
                entity.level().addParticle(net.minecraft.core.particles.ParticleTypes.SQUID_INK, 
                    entity.getX() + (entity.getRandom().nextDouble() - 0.5) * 12, 
                    entity.getY() + 0.1, 
                    entity.getZ() + (entity.getRandom().nextDouble() - 0.5) * 12, 
                    0, 0.05, 0);
                entity.level().addParticle(net.minecraft.core.particles.ParticleTypes.SMOKE, 
                    entity.getX() + (entity.getRandom().nextDouble() - 0.5) * 8, 
                    entity.getY() + 0.5, 
                    entity.getZ() + (entity.getRandom().nextDouble() - 0.5) * 8, 
                    0, 0.1, 0);
            }
        } else {
            // Pull entities
            double radius = 12.0;
            entity.level().getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(radius)).forEach(target -> {
                if (target != entity) {
                    Vec3 dir = entity.position().subtract(target.position()).normalize().scale(0.4);
                    target.setDeltaMovement(target.getDeltaMovement().add(dir));
                    target.hurt(entity.damageSources().magic(), 2.0F); // Darkness damage
                    target.hurtMarked = true;
                }
            });
        }

        if (duration >= MAX_TICKS) {
            this.startCooldown(entity, 600);
            this.stop(entity);
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.black_hole");
    }
}
