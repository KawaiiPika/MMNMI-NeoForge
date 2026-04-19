package xyz.pixelatedw.mineminenomi.abilities.blackleg;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class ExtraHachisAbility extends Ability {

    private static final long DURATION = 40; // 2 seconds

    public ExtraHachisAbility() {}

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), 
                net.minecraft.sounds.SoundEvents.PLAYER_ATTACK_SWEEP, 
                net.minecraft.sounds.SoundSource.PLAYERS, 1.0f, 1.0f);
        }
    }

    @Override
    public void onTick(LivingEntity entity, long duration) {
        if (duration > DURATION) {
            this.stop(entity);
            this.startCooldown(entity, 200);
            return;
        }

        if (duration % 4 == 0) { // Hit every 0.2 seconds
            boolean diableJambeActive = false;
            xyz.pixelatedw.mineminenomi.data.entity.PlayerStats stats = xyz.pixelatedw.mineminenomi.data.entity.PlayerStats.get(entity);
            if (stats != null) {
                diableJambeActive = stats.isAbilityActive("mineminenomi:diable_jambe");
            }

            AABB area = entity.getBoundingBox().inflate(3.0);
            for (LivingEntity target : entity.level().getEntitiesOfClass(LivingEntity.class, area)) {
                if (target != entity) {
                    float damage = diableJambeActive ? 4.0f : 2.0f;
                    target.hurt(entity.damageSources().mobAttack(entity), damage);
                    if (diableJambeActive) {
                        target.setRemainingFireTicks(20);
                    }
                }
            }
            
            if (entity.level() instanceof net.minecraft.server.level.ServerLevel serverLevel) {
                serverLevel.sendParticles(net.minecraft.core.particles.ParticleTypes.SWEEP_ATTACK, 
                    entity.getX() + (entity.getRandom().nextDouble() - 0.5) * 2, 
                    entity.getY() + 1.0, 
                    entity.getZ() + (entity.getRandom().nextDouble() - 0.5) * 2, 
                    1, 0, 0, 0, 0);
            }
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Extra Hachis");
    }
}
