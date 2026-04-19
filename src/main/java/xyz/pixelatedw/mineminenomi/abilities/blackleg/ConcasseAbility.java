package xyz.pixelatedw.mineminenomi.abilities.blackleg;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class ConcasseAbility extends Ability {

    private boolean falling = false;

    public ConcasseAbility() {}

    @Override
    protected void startUsing(LivingEntity entity) {
        Vec3 look = entity.getLookAngle();
        entity.setDeltaMovement(new Vec3(look.x * 0.8, 1.2, look.z * 0.8));
        entity.hurtMarked = true;
        falling = false;
        
        if (!entity.level().isClientSide) {
            entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), 
                net.minecraft.sounds.SoundEvents.PLAYER_ATTACK_STRONG, 
                net.minecraft.sounds.SoundSource.PLAYERS, 1.0f, 1.0f);
        }
    }

    @Override
    public void onTick(LivingEntity entity, long duration) {
        if (duration > 100) {
            this.stop(entity);
            this.startCooldown(entity, 300);
            return;
        }

        if (entity.getDeltaMovement().y < 0) {
            falling = true;
            entity.setDeltaMovement(entity.getDeltaMovement().add(0, -0.1, 0)); // Fast fall
        }

        if (falling && (entity.onGround() || duration > 20)) {
            boolean diableJambeActive = false;
            xyz.pixelatedw.mineminenomi.data.entity.PlayerStats stats = xyz.pixelatedw.mineminenomi.data.entity.PlayerStats.get(entity);
            if (stats != null) {
                diableJambeActive = stats.isAbilityActive("mineminenomi:diable_jambe");
            }

            AABB area = entity.getBoundingBox().inflate(3.0);
            for (LivingEntity target : entity.level().getEntitiesOfClass(LivingEntity.class, area)) {
                if (target != entity) {
                    target.hurt(entity.damageSources().mobAttack(entity), diableJambeActive ? 20.0f : 12.0f);
                    target.setDeltaMovement(new Vec3(0, -0.5, 0)); // Slam down
                    if (diableJambeActive) {
                        target.setRemainingFireTicks(40);
                    }
                }
            }
            
            if (entity.level() instanceof net.minecraft.server.level.ServerLevel serverLevel) {
                serverLevel.sendParticles(net.minecraft.core.particles.ParticleTypes.EXPLOSION, 
                    entity.getX(), entity.getY(), entity.getZ(), 
                    1, 0, 0, 0, 0);
            }
            
            this.stop(entity);
            this.startCooldown(entity, 300);
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Concasse");
    }
}
