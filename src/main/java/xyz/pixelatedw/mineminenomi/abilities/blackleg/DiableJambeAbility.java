package xyz.pixelatedw.mineminenomi.abilities.blackleg;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class DiableJambeAbility extends Ability {

    private static final long MAX_DURATION = 600; // 30 seconds

    public DiableJambeAbility() {}

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), 
                xyz.pixelatedw.mineminenomi.init.ModSounds.DIABLE_JAMBE_ACTIVATE.get(), 
                net.minecraft.sounds.SoundSource.PLAYERS, 1.0f, 1.0f);
        }
    }

    @Override
    public void onTick(LivingEntity entity, long duration) {
        if (duration > MAX_DURATION) {
            this.stop(entity);
            this.startCooldown(entity, 200);
            return;
        }

        if (entity.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.FLAME, entity.getX(), entity.getY(), entity.getZ(), 2, 0.2, 0.1, 0.2, 0.02);
            if (duration % 10 == 0) {
                serverLevel.sendParticles(ParticleTypes.LARGE_SMOKE, entity.getX(), entity.getY(), entity.getZ(), 1, 0.1, 0.1, 0.1, 0.01);
            }
        }
    }

    @Override
    public float onAttack(LivingEntity entity, LivingEntity target, DamageSource source, float amount) {
        if (this.isUsing(entity)) {
            target.setRemainingFireTicks(60); // 3 seconds of fire
            return amount * 1.5f; // 50% damage boost
        }
        return amount;
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Diable Jambe");
    }
}
